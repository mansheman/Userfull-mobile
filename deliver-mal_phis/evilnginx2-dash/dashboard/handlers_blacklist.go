package dashboard

import (
	"net/http"
	"strconv"
	"strings"

	"github.com/kgretzky/evilginx2/core"
)

// blacklistEntryView is a blacklist entry enriched with geolocation.
type blacklistEntryView struct {
	Value  string   `json:"value"`
	IsMask bool     `json:"is_mask"`
	Geo    *GeoInfo `json:"geo,omitempty"`
}

// blacklistEntryViews attaches geo to each entry — for a single IP directly, for
// a CIDR via its network address (the part before the "/").
func (s *Server) blacklistEntryViews(entries []core.BlacklistEntry) []blacklistEntryView {
	out := make([]blacklistEntryView, 0, len(entries))
	for _, e := range entries {
		ip := e.Value
		if e.IsMask {
			if i := strings.IndexByte(ip, '/'); i >= 0 {
				ip = ip[:i]
			}
		}
		out = append(out, blacklistEntryView{Value: e.Value, IsMask: e.IsMask, Geo: s.geoLookup(ip)})
	}
	return out
}

func (s *Server) blacklistResponse(w http.ResponseWriter, status int) {
	var entries []core.BlacklistEntry
	ipCount, maskCount := 0, 0
	if s.blacklist != nil {
		entries = s.blacklist.Entries()
		ipCount, maskCount = s.blacklist.GetStats()
	}
	writeJSON(w, status, map[string]interface{}{
		"entries":    s.blacklistEntryViews(entries),
		"ip_count":   ipCount,
		"mask_count": maskCount,
		"mode":       s.cfg.GetBlacklistMode(),
	})
}

func (s *Server) handleListBlacklist(w http.ResponseWriter, r *http.Request) {
	s.blacklistResponse(w, http.StatusOK)
}

type blacklistEntryRequest struct {
	Entry string `json:"entry"`
}

func (s *Server) handleAddBlacklist(w http.ResponseWriter, r *http.Request) {
	if s.blacklist == nil {
		writeError(w, http.StatusServiceUnavailable, "blacklist not available")
		return
	}
	u := userFromContext(r.Context())
	var req blacklistEntryRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if err := s.blacklist.AddEntry(strings.TrimSpace(req.Entry)); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(u.Username, "blacklist_add", strings.TrimSpace(req.Entry), "", clientIP(r))
	s.blacklistResponse(w, http.StatusOK)
}

func (s *Server) handleRemoveBlacklist(w http.ResponseWriter, r *http.Request) {
	if s.blacklist == nil {
		writeError(w, http.StatusServiceUnavailable, "blacklist not available")
		return
	}
	u := userFromContext(r.Context())
	var req blacklistEntryRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if err := s.blacklist.RemoveEntry(strings.TrimSpace(req.Entry)); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(u.Username, "blacklist_remove", strings.TrimSpace(req.Entry), "", clientIP(r))
	s.blacklistResponse(w, http.StatusOK)
}

type blacklistImportRequest struct {
	Entries string `json:"entries"`
}

func (s *Server) handleImportBlacklist(w http.ResponseWriter, r *http.Request) {
	if s.blacklist == nil {
		writeError(w, http.StatusServiceUnavailable, "blacklist not available")
		return
	}
	u := userFromContext(r.Context())
	var req blacklistImportRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	added, errs := s.blacklist.ImportEntries(req.Entries)
	s.store.Audit(u.Username, "blacklist_import", "", "added="+strconv.Itoa(added), clientIP(r))

	entries := s.blacklist.Entries()
	ipCount, maskCount := s.blacklist.GetStats()
	writeJSON(w, http.StatusOK, map[string]interface{}{
		"entries":    s.blacklistEntryViews(entries),
		"ip_count":   ipCount,
		"mask_count": maskCount,
		"mode":       s.cfg.GetBlacklistMode(),
		"added":      added,
		"errors":     errs,
	})
}

func (s *Server) handleClearBlacklist(w http.ResponseWriter, r *http.Request) {
	if s.blacklist == nil {
		writeError(w, http.StatusServiceUnavailable, "blacklist not available")
		return
	}
	u := userFromContext(r.Context())
	if err := s.blacklist.Clear(); err != nil {
		writeError(w, http.StatusInternalServerError, err.Error())
		return
	}
	s.store.Audit(u.Username, "blacklist_clear", "", "", clientIP(r))
	s.blacklistResponse(w, http.StatusOK)
}
