package dashboard

import (
	"net/http"
	"os"
	"path/filepath"
	"regexp"
	"strings"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/core"
)

// phishlet file names are restricted to a safe charset to prevent path traversal
// and to match the loader's expectations (<name>.yaml in the phishlets dir).
var phishletNameRe = regexp.MustCompile(`^[a-zA-Z0-9_-]{1,64}$`)

func validPhishletName(name string) bool {
	return phishletNameRe.MatchString(name)
}

func (s *Server) phishletPath(name string) string {
	return filepath.Join(s.phishletsDir, name+".yaml")
}

// handleGetPhishletYaml returns the raw YAML source of a loaded phishlet.
func (s *Server) handleGetPhishletYaml(w http.ResponseWriter, r *http.Request) {
	name := mux.Vars(r)["name"]
	pl, err := s.cfg.GetPhishlet(name)
	if err != nil {
		writeError(w, http.StatusNotFound, "phishlet not found")
		return
	}
	path := pl.FilePath()
	if path == "" {
		writeError(w, http.StatusBadRequest, "phishlet has no source file")
		return
	}
	b, err := os.ReadFile(path)
	if err != nil {
		writeError(w, http.StatusInternalServerError, "failed to read phishlet file")
		return
	}
	writeJSON(w, http.StatusOK, map[string]interface{}{
		"name":      name,
		"yaml":      string(b),
		"parent":    pl.ParentName,
		"read_only": pl.ParentName != "",
	})
}

type phishletYamlRequest struct {
	Yaml string `json:"yaml"`
}

// handleUpdatePhishletYaml validates and writes new YAML for an existing
// phishlet, then hot-reloads it. The previous file is restored on any failure so
// a broken edit never takes down a working phishlet.
func (s *Server) handleUpdatePhishletYaml(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	name := mux.Vars(r)["name"]
	pl, err := s.cfg.GetPhishlet(name)
	if err != nil {
		writeError(w, http.StatusNotFound, "phishlet not found")
		return
	}
	if pl.ParentName != "" {
		writeError(w, http.StatusBadRequest, "child phishlets are derived from a parent and cannot be edited directly")
		return
	}
	var req phishletYamlRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if len(req.Yaml) == 0 {
		writeError(w, http.StatusBadRequest, "yaml cannot be empty")
		return
	}
	path := pl.FilePath()
	if path == "" {
		path = s.phishletPath(name)
	}

	backup, _ := os.ReadFile(path) // may be empty if missing
	if err := os.WriteFile(path, []byte(req.Yaml), 0600); err != nil {
		writeError(w, http.StatusInternalServerError, "failed to write phishlet file")
		return
	}
	if err := s.cfg.ImportPhishlet(name, path); err != nil {
		// roll back to the previous content
		if backup != nil {
			os.WriteFile(path, backup, 0600)
			s.cfg.ImportPhishlet(name, path)
		}
		writeError(w, http.StatusBadRequest, "invalid phishlet: "+err.Error())
		return
	}
	if s.cfg.IsSiteEnabled(name) {
		s.manageCertificates()
	}
	s.store.Audit(u.Username, "edit_phishlet", name, "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}

// handleDeletePhishlet removes a phishlet. Child phishlets are unregistered from
// config; regular phishlets are disabled, unregistered, and their backing YAML
// file is deleted from disk. Refuses if child phishlets still derive from it.
func (s *Server) handleDeletePhishlet(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	name := mux.Vars(r)["name"]
	pl, err := s.cfg.GetPhishlet(name)
	if err != nil {
		writeError(w, http.StatusNotFound, "phishlet not found")
		return
	}
	isChild := pl.ParentName != ""
	filePath := pl.FilePath()

	if err := s.cfg.RemovePhishlet(name); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	// for regular phishlets, delete the YAML file so it doesn't reload on restart
	if !isChild && filePath != "" {
		if err := os.Remove(filePath); err != nil && !os.IsNotExist(err) {
			// already unregistered in memory; report file issue but don't fail hard
			s.store.Audit(u.Username, "delete_phishlet_file_error", name, err.Error(), clientIP(r))
		}
	}
	// active hostnames may have changed
	s.manageCertificates()
	s.store.Audit(u.Username, "delete_phishlet", name, "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}

// handleValidatePhishlet runs the real evilginx parser against YAML WITHOUT
// saving, registering, or touching any file — so the editor can validate live as
// you type and the result is identical to what import/edit would accept.
func (s *Server) handleValidatePhishlet(w http.ResponseWriter, r *http.Request) {
	var req phishletYamlRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if strings.TrimSpace(req.Yaml) == "" {
		writeJSON(w, http.StatusOK, map[string]interface{}{"valid": false, "error": "phishlet is empty"})
		return
	}
	tmp, err := os.CreateTemp("", "phishlet-validate-*.yaml")
	if err != nil {
		writeError(w, http.StatusInternalServerError, "could not create temp file")
		return
	}
	defer os.Remove(tmp.Name())
	if _, err := tmp.WriteString(req.Yaml); err != nil {
		tmp.Close()
		writeError(w, http.StatusInternalServerError, "could not write temp file")
		return
	}
	tmp.Close()

	// NewPhishlet validates structure/regex/version but does NOT register it.
	if _, err := core.NewPhishlet("__validate__", tmp.Name(), nil, s.cfg); err != nil {
		writeJSON(w, http.StatusOK, map[string]interface{}{"valid": false, "error": err.Error()})
		return
	}
	writeJSON(w, http.StatusOK, map[string]interface{}{"valid": true})
}

type importPhishletRequest struct {
	Name string `json:"name"`
	Yaml string `json:"yaml"`
}

// handleImportPhishlet creates a brand-new phishlet from uploaded YAML.
func (s *Server) handleImportPhishlet(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	var req importPhishletRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if !validPhishletName(req.Name) {
		writeError(w, http.StatusBadRequest, "invalid name: use letters, digits, '-' or '_' (max 64)")
		return
	}
	if len(req.Yaml) == 0 {
		writeError(w, http.StatusBadRequest, "yaml cannot be empty")
		return
	}
	if _, err := s.cfg.GetPhishlet(req.Name); err == nil {
		writeError(w, http.StatusConflict, "a phishlet named '"+req.Name+"' already exists; edit it instead")
		return
	}
	path := s.phishletPath(req.Name)
	if _, err := os.Stat(path); err == nil {
		writeError(w, http.StatusConflict, "a phishlet file already exists at that name")
		return
	}
	if err := os.WriteFile(path, []byte(req.Yaml), 0600); err != nil {
		writeError(w, http.StatusInternalServerError, "failed to write phishlet file")
		return
	}
	if err := s.cfg.ImportPhishlet(req.Name, path); err != nil {
		os.Remove(path)
		writeError(w, http.StatusBadRequest, "invalid phishlet: "+err.Error())
		return
	}
	s.store.Audit(u.Username, "import_phishlet", req.Name, "", clientIP(r))
	writeJSON(w, http.StatusCreated, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}
