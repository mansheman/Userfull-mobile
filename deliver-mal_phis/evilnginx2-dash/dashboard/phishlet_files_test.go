package dashboard

import (
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"os"
	"strings"
	"testing"

	"github.com/gorilla/mux"
)

const validPhishlet = `min_ver: '3.0.0'
proxy_hosts:
  - {phish_sub: 'www', orig_sub: 'www', domain: 'example.com', session: true, is_landing: true, auto_filter: true}
sub_filters: []
auth_tokens:
  - domain: '.example.com'
    keys: ['session']
credentials:
  username:
    key: 'email'
    search: '(.*)'
    type: 'post'
  password:
    key: 'password'
    search: '(.*)'
    type: 'post'
login:
  domain: 'www.example.com'
  path: '/login'
`

func TestPhishletImportEdit(t *testing.T) {
	s, _ := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()
	admin := login(t, ts, "admin", "testpass123")

	// invalid name rejected
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "bad name!", "yaml": validPhishlet}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for bad name, got %d", res.StatusCode)
	}

	// invalid yaml rejected, no phishlet created
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "broken", "yaml": "not: [valid"}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for broken yaml, got %d", res.StatusCode)
	}
	if _, err := s.cfg.GetPhishlet("broken"); err == nil {
		t.Fatalf("broken phishlet should not have been registered")
	}

	// valid import
	res, data := doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "acme", "yaml": validPhishlet})
	if res.StatusCode != 201 {
		t.Fatalf("import failed: %d %s", res.StatusCode, data)
	}
	if _, err := s.cfg.GetPhishlet("acme"); err != nil {
		t.Fatalf("acme not registered after import")
	}

	// duplicate import rejected
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "acme", "yaml": validPhishlet}); res.StatusCode != 409 {
		t.Fatalf("expected 409 for duplicate, got %d", res.StatusCode)
	}

	// read back yaml
	res, data = doReq(t, ts, "GET", "/api/phishlets/acme/yaml", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("get yaml: %d", res.StatusCode)
	}
	var yr struct {
		Yaml string `json:"yaml"`
	}
	json.Unmarshal(data, &yr)
	if !strings.Contains(yr.Yaml, "www.example.com") {
		t.Fatalf("yaml content unexpected: %s", yr.Yaml)
	}

	// edit with broken yaml -> 400 and original preserved
	if res, _ := doReq(t, ts, "PUT", "/api/phishlets/acme/yaml", admin, map[string]string{"yaml": "not: [valid"}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for broken edit, got %d", res.StatusCode)
	}
	res, data = doReq(t, ts, "GET", "/api/phishlets/acme/yaml", admin, nil)
	json.Unmarshal(data, &yr)
	if !strings.Contains(yr.Yaml, "www.example.com") {
		t.Fatalf("original yaml should be preserved after failed edit: %s", yr.Yaml)
	}

	// successful edit
	edited := strings.Replace(validPhishlet, "/login", "/signin", 1)
	if res, _ := doReq(t, ts, "PUT", "/api/phishlets/acme/yaml", admin, map[string]string{"yaml": edited}); res.StatusCode != 200 {
		t.Fatalf("valid edit failed")
	}
	res, data = doReq(t, ts, "GET", "/api/phishlets/acme/yaml", admin, nil)
	json.Unmarshal(data, &yr)
	if !strings.Contains(yr.Yaml, "/signin") {
		t.Fatalf("edit not persisted: %s", yr.Yaml)
	}

	// delete a regular phishlet: unregistered + file removed from disk
	phPath := s.phishletPath("acme")
	if _, err := os.Stat(phPath); err != nil {
		t.Fatalf("expected phishlet file on disk before delete: %v", err)
	}
	if res, data := doReq(t, ts, "DELETE", "/api/phishlets/acme", admin, nil); res.StatusCode != 200 {
		t.Fatalf("delete phishlet: %d %s", res.StatusCode, data)
	}
	if _, err := s.cfg.GetPhishlet("acme"); err == nil {
		t.Fatalf("acme should be unregistered after delete")
	}
	if _, err := os.Stat(phPath); !os.IsNotExist(err) {
		t.Fatalf("acme.yaml should be removed from disk")
	}

	// validate endpoint: authoritative, no save/register
	res, data = doReq(t, ts, "POST", "/api/phishlets/validate", admin, map[string]string{"yaml": validPhishlet})
	if res.StatusCode != 200 {
		t.Fatalf("validate ok: %d %s", res.StatusCode, data)
	}
	var vr struct {
		Valid bool   `json:"valid"`
		Error string `json:"error"`
	}
	json.Unmarshal(data, &vr)
	if !vr.Valid {
		t.Fatalf("valid yaml should validate, got error: %s", vr.Error)
	}
	// broken yaml -> valid:false with a message, and nothing registered
	res, data = doReq(t, ts, "POST", "/api/phishlets/validate", admin, map[string]string{"yaml": "min_ver: '3.0.0'\nproxy_hosts: []"})
	json.Unmarshal(data, &vr)
	if vr.Valid || vr.Error == "" {
		t.Fatalf("broken yaml should be invalid with a message: %s", data)
	}
	if _, err := s.cfg.GetPhishlet("__validate__"); err == nil {
		t.Fatalf("validate must not register the phishlet")
	}

	// RBAC: viewer cannot import
	doReq(t, ts, "POST", "/api/users", admin, map[string]string{"username": "v2", "password": "viewerpass1", "role": "viewer"})
	viewer := login(t, ts, "v2", "viewerpass1")
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/import", viewer, map[string]string{"name": "nope", "yaml": validPhishlet}); res.StatusCode != 403 {
		t.Fatalf("expected 403 for viewer import, got %d", res.StatusCode)
	}
	_ = http.StatusOK
}
