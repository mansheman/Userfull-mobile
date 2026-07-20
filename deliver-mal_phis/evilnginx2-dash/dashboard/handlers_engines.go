package dashboard

import (
	"io"
	"net/http"

	"github.com/gorilla/mux"
)

type engineView struct {
	Id         string `json:"id"`
	Name       string `json:"name"`
	URL        string `json:"url"`
	CreateTime int64  `json:"create_time"`
}

func toEngineView(e *Engine) engineView {
	return engineView{Id: e.Id, Name: e.Name, URL: e.URL, CreateTime: e.CreateTime}
}

func (s *Server) handleListEngines(w http.ResponseWriter, r *http.Request) {
	engines, err := s.store.ListEngines()
	if err != nil {
		writeError(w, http.StatusInternalServerError, "failed to list engines")
		return
	}
	views := make([]engineView, 0, len(engines))
	for _, e := range engines {
		views = append(views, toEngineView(e))
	}
	writeJSON(w, http.StatusOK, map[string]interface{}{"engines": views})
}

type engineRequest struct {
	Name  string `json:"name"`
	URL   string `json:"url"`
	Token string `json:"token"`
}

func (s *Server) handleCreateEngine(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	var req engineRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	e, err := s.store.CreateEngine(req.Name, req.URL, req.Token)
	if err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(u.Username, "create_engine", e.Name, e.URL, clientIP(r))
	// probe immediately so the UI shows status without a second call
	writeJSON(w, http.StatusCreated, map[string]interface{}{
		"engine": toEngineView(e),
		"status": s.client.Check(e),
	})
}

func (s *Server) handleUpdateEngine(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	id := mux.Vars(r)["id"]
	var req engineRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	e, err := s.store.UpdateEngine(id, req.Name, req.URL, req.Token)
	if err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(u.Username, "update_engine", e.Name, e.URL, clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"engine": toEngineView(e)})
}

func (s *Server) handleDeleteEngine(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	id := mux.Vars(r)["id"]
	if _, err := s.store.GetEngine(id); err != nil {
		writeError(w, http.StatusNotFound, "engine not found")
		return
	}
	if err := s.store.DeleteEngine(id); err != nil {
		writeError(w, http.StatusInternalServerError, "failed to delete engine")
		return
	}
	s.store.Audit(u.Username, "delete_engine", id, "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]string{"status": "ok"})
}

// handleCheckEngine probes connectivity + token validity + version.
func (s *Server) handleCheckEngine(w http.ResponseWriter, r *http.Request) {
	id := mux.Vars(r)["id"]
	e, err := s.store.GetEngine(id)
	if err != nil {
		writeError(w, http.StatusNotFound, "engine not found")
		return
	}
	writeJSON(w, http.StatusOK, s.client.Check(e))
}

// handleEngineForward proxies a request to a registered engine's agent API.
// Route: /api/engines/{id}/r/{path:.*}  ->  engine /api/{path}
func (s *Server) handleEngineForward(w http.ResponseWriter, r *http.Request) {
	id := mux.Vars(r)["id"]
	path := mux.Vars(r)["path"]
	e, err := s.store.GetEngine(id)
	if err != nil {
		writeError(w, http.StatusNotFound, "engine not found")
		return
	}
	// preserve the query string
	if r.URL.RawQuery != "" {
		path += "?" + r.URL.RawQuery
	}
	resp, err := s.client.Forward(e, r.Method, path, r.Body, r.Header.Get("Content-Type"))
	if err != nil {
		writeError(w, http.StatusBadGateway, "engine unreachable: "+err.Error())
		return
	}
	defer resp.Body.Close()
	if ct := resp.Header.Get("Content-Type"); ct != "" {
		w.Header().Set("Content-Type", ct)
	}
	w.WriteHeader(resp.StatusCode)
	io.Copy(w, resp.Body)
}
