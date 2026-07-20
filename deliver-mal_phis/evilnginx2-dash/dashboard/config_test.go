package dashboard

import (
	"encoding/json"
	"net/http/httptest"
	"testing"

	"github.com/gorilla/mux"
)

func TestConfigAndHostname(t *testing.T) {
	s, _ := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()
	admin := login(t, ts, "admin", "testpass123")

	// import a phishlet to operate on
	if res, data := doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "acme", "yaml": validPhishlet}); res.StatusCode != 201 {
		t.Fatalf("import: %d %s", res.StatusCode, data)
	}

	// hostname before base domain is set -> 400
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/acme/hostname", admin, map[string]string{"hostname": "login.corp.test"}); res.StatusCode != 400 {
		t.Fatalf("expected 400 setting hostname with no base domain, got %d", res.StatusCode)
	}

	// invalid domain rejected
	if res, _ := doReq(t, ts, "PATCH", "/api/config", admin, map[string]interface{}{"general": map[string]interface{}{"domain": "not a domain"}}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for invalid domain, got %d", res.StatusCode)
	}

	// set base domain + external ip
	res, data := doReq(t, ts, "PATCH", "/api/config", admin, map[string]interface{}{"general": map[string]interface{}{"domain": "corp.test", "external_ipv4": "203.0.113.5"}})
	if res.StatusCode != 200 {
		t.Fatalf("set config: %d %s", res.StatusCode, data)
	}
	var cr struct {
		General struct {
			Domain       string `json:"domain"`
			ExternalIpv4 string `json:"external_ipv4"`
		} `json:"general"`
	}
	json.Unmarshal(data, &cr)
	if cr.General.Domain != "corp.test" || cr.General.ExternalIpv4 != "203.0.113.5" {
		t.Fatalf("config not applied: %+v", cr.General)
	}

	// invalid IP rejected
	if res, _ := doReq(t, ts, "PATCH", "/api/config", admin, map[string]interface{}{"general": map[string]interface{}{"external_ipv4": "999.1.1.1"}}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for bad ip, got %d", res.StatusCode)
	}

	// hostname not under base domain -> 400
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/acme/hostname", admin, map[string]string{"hostname": "login.other.com"}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for hostname outside base domain, got %d", res.StatusCode)
	}

	// valid hostname accepted
	if res, data := doReq(t, ts, "POST", "/api/phishlets/acme/hostname", admin, map[string]string{"hostname": "login.corp.test"}); res.StatusCode != 200 {
		t.Fatalf("valid hostname set failed: %d %s", res.StatusCode, data)
	}
	if h, _ := s.cfg.GetSiteDomain("acme"); h != "login.corp.test" {
		t.Fatalf("hostname not persisted, got %q", h)
	}

	// now enabling should pass config validation (proxy is nil so cert step is a no-op)
	if res, data := doReq(t, ts, "POST", "/api/phishlets/acme/enable", admin, nil); res.StatusCode != 200 {
		t.Fatalf("enable after hostname set failed: %d %s", res.StatusCode, data)
	}
	if !s.cfg.IsSiteEnabled("acme") {
		t.Fatalf("phishlet should be enabled")
	}

	// RBAC: operator cannot change server config
	doReq(t, ts, "POST", "/api/users", admin, map[string]string{"username": "op1", "password": "operatorpw1", "role": "operator"})
	op := login(t, ts, "op1", "operatorpw1")
	if res, _ := doReq(t, ts, "PATCH", "/api/config", op, map[string]string{"domain": "evil.test"}); res.StatusCode != 403 {
		t.Fatalf("expected 403 for operator config edit, got %d", res.StatusCode)
	}
	// but operator CAN set a phishlet hostname
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/acme/hostname", op, map[string]string{"hostname": "portal.corp.test"}); res.StatusCode != 200 {
		t.Fatalf("operator should be able to set hostname, got %d", res.StatusCode)
	}
}
