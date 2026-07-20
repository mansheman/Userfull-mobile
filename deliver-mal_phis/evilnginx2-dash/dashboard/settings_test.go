package dashboard

import (
	"encoding/json"
	"net/http/httptest"
	"strings"
	"testing"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/core"
)

func TestComprehensiveSettings(t *testing.T) {
	s, _ := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()
	admin := login(t, ts, "admin", "testpass123")

	// GET returns all sections + enum lists
	res, data := doReq(t, ts, "GET", "/api/config", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("get config: %d", res.StatusCode)
	}
	for _, key := range []string{"general", "blacklist", "proxy", "gophish", "blacklist_modes", "proxy_types"} {
		var m map[string]json.RawMessage
		json.Unmarshal(data, &m)
		if _, ok := m[key]; !ok {
			t.Fatalf("config missing key %q: %s", key, data)
		}
	}

	// update every section
	patch := map[string]interface{}{
		"general":   map[string]interface{}{"domain": "corp.test", "external_ipv4": "203.0.113.9", "https_port": 8443, "dns_port": 5353, "autocert": false},
		"blacklist": map[string]interface{}{"mode": "noadd"},
		"proxy":     map[string]interface{}{"enabled": true, "type": "socks5", "address": "127.0.0.1", "port": 9050},
		"gophish":   map[string]interface{}{"admin_url": "https://gophish.local:3333", "api_key": "abc123", "insecure_tls": true},
	}
	res, data = doReq(t, ts, "PATCH", "/api/config", admin, patch)
	if res.StatusCode != 200 {
		t.Fatalf("patch config: %d %s", res.StatusCode, data)
	}
	var st core.SettingsInfo
	json.Unmarshal(data, &st)
	if st.General.Domain != "corp.test" || st.General.HttpsPort != 8443 || st.General.DnsPort != 5353 || st.General.Autocert {
		t.Fatalf("general not applied: %+v", st.General)
	}
	if st.Blacklist.Mode != "noadd" {
		t.Fatalf("blacklist mode not applied: %+v", st.Blacklist)
	}
	if !st.Proxy.Enabled || st.Proxy.Type != "socks5" || st.Proxy.Port != 9050 {
		t.Fatalf("proxy not applied: %+v", st.Proxy)
	}
	if st.GoPhish.AdminUrl == "" || st.GoPhish.ApiKey != "abc123" || !st.GoPhish.InsecureTLS {
		t.Fatalf("gophish not applied: %+v", st.GoPhish)
	}

	// validation failures
	bad := []map[string]interface{}{
		{"general": map[string]interface{}{"https_port": 0}},
		{"general": map[string]interface{}{"external_ipv4": "999.1.1.1"}},
		{"blacklist": map[string]interface{}{"mode": "bogus"}},
		{"proxy": map[string]interface{}{"type": "carrier-pigeon"}},
		{"gophish": map[string]interface{}{"admin_url": "not a url"}},
	}
	for i, b := range bad {
		if res, _ := doReq(t, ts, "PATCH", "/api/config", admin, b); res.StatusCode != 400 {
			t.Fatalf("bad patch %d expected 400, got %d", i, res.StatusCode)
		}
	}

	// proxy port persists correctly (regression: upstream SetProxyPort bug)
	res, data = doReq(t, ts, "GET", "/api/config", admin, nil)
	var got struct {
		Proxy core.ProxyInfo `json:"proxy"`
	}
	json.Unmarshal(data, &got)
	if got.Proxy.Port != 9050 || got.Proxy.Type != "socks5" {
		t.Fatalf("proxy not persisted intact: %+v", got.Proxy)
	}

	// operator cannot touch settings
	doReq(t, ts, "POST", "/api/users", admin, map[string]string{"username": "op9", "password": "operatorpw1", "role": "operator"})
	op := login(t, ts, "op9", "operatorpw1")
	if res, _ := doReq(t, ts, "PATCH", "/api/config", op, map[string]interface{}{"general": map[string]interface{}{"domain": "x.test"}}); res.StatusCode != 403 {
		t.Fatalf("operator should be 403 on config, got %d", res.StatusCode)
	}
}

func TestPhishletOps(t *testing.T) {
	s, _ := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()
	admin := login(t, ts, "admin", "testpass123")

	// set base domain so hostname-dependent ops can pass
	doReq(t, ts, "PATCH", "/api/config", admin, map[string]interface{}{"general": map[string]interface{}{"domain": "corp.test"}})
	// import a phishlet
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "acme", "yaml": validPhishlet}); res.StatusCode != 201 {
		t.Fatalf("import failed")
	}
	// set hostname so get-hosts works
	doReq(t, ts, "POST", "/api/phishlets/acme/hostname", admin, map[string]string{"hostname": "corp.test"})

	// hide / unhide
	if res, data := doReq(t, ts, "POST", "/api/phishlets/acme/hide", admin, nil); res.StatusCode != 200 {
		t.Fatalf("hide: %d %s", res.StatusCode, data)
	}
	var pl struct {
		Phishlets []map[string]interface{} `json:"phishlets"`
	}
	_, data := doReq(t, ts, "GET", "/api/phishlets", admin, nil)
	json.Unmarshal(data, &pl)
	for _, p := range pl.Phishlets {
		if p["name"] == "acme" && p["hidden"] != true {
			t.Fatalf("acme should be hidden: %+v", p)
		}
	}
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/acme/unhide", admin, nil); res.StatusCode != 200 {
		t.Fatalf("unhide failed")
	}

	// unauth_url: valid + invalid
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/acme/unauth_url", admin, map[string]string{"unauth_url": "https://example.org"}); res.StatusCode != 200 {
		t.Fatalf("set unauth_url failed")
	}
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/acme/unauth_url", admin, map[string]string{"unauth_url": "not a url"}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for bad unauth_url")
	}

	// get-hosts
	res, data := doReq(t, ts, "GET", "/api/phishlets/acme/hosts", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("get-hosts: %d %s", res.StatusCode, data)
	}
	var hosts struct {
		Hosts []string `json:"hosts"`
		Lines []string `json:"lines"`
	}
	json.Unmarshal(data, &hosts)
	if len(hosts.Hosts) == 0 {
		t.Fatalf("expected at least one host line: %s", data)
	}
}

func TestPhishletCollisions(t *testing.T) {
	s, _ := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()
	admin := login(t, ts, "admin", "testpass123")

	doReq(t, ts, "PATCH", "/api/config", admin, map[string]interface{}{"general": map[string]interface{}{"domain": "corp.test"}})
	// two phishlets that resolve to the SAME host (same proxy_hosts) collide
	doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "dup1", "yaml": validPhishlet})
	doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "dup2", "yaml": validPhishlet})
	doReq(t, ts, "POST", "/api/phishlets/dup1/hostname", admin, map[string]string{"hostname": "corp.test"})
	doReq(t, ts, "POST", "/api/phishlets/dup2/hostname", admin, map[string]string{"hostname": "corp.test"})

	doReq(t, ts, "POST", "/api/phishlets/dup1/enable", admin, nil)
	// enabling the second colliding phishlet should return a warning (non-blocking)
	res, data := doReq(t, ts, "POST", "/api/phishlets/dup2/enable", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("enable dup2: %d %s", res.StatusCode, data)
	}
	var er struct {
		Warning string `json:"warning"`
	}
	json.Unmarshal(data, &er)
	if er.Warning == "" || !strings.Contains(er.Warning, "dup1") {
		t.Fatalf("expected collision warning naming dup1, got %q", er.Warning)
	}

	// both phishlets report the collision in their export
	_, data = doReq(t, ts, "GET", "/api/phishlets", admin, nil)
	var pl struct {
		Phishlets []core.PhishletInfo `json:"phishlets"`
	}
	json.Unmarshal(data, &pl)
	found := 0
	for _, p := range pl.Phishlets {
		if p.Name == "dup1" || p.Name == "dup2" {
			if len(p.Collisions) == 0 {
				t.Fatalf("%s should report a collision", p.Name)
			}
			found++
		}
	}
	if found != 2 {
		t.Fatalf("expected both dup phishlets present, found %d", found)
	}

	// disabling one clears the collision
	doReq(t, ts, "POST", "/api/phishlets/dup2/disable", admin, nil)
	_, data = doReq(t, ts, "GET", "/api/phishlets", admin, nil)
	var pl2 struct {
		Phishlets []core.PhishletInfo `json:"phishlets"`
	}
	json.Unmarshal(data, &pl2)
	for _, p := range pl2.Phishlets {
		if p.Name == "dup1" && len(p.Collisions) != 0 {
			t.Fatalf("dup1 collision should clear after dup2 disabled, got %v", p.Collisions)
		}
	}
}
