package dashboard

import (
	"encoding/json"
	"net/http/httptest"
	"strings"
	"testing"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/core"
)

func TestLuresComprehensive(t *testing.T) {
	s, _ := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()
	admin := login(t, ts, "admin", "testpass123")

	// base domain + a phishlet with a hostname (needed for get-url)
	doReq(t, ts, "PATCH", "/api/config", admin, map[string]interface{}{"general": map[string]interface{}{"domain": "corp.test"}})
	if res, _ := doReq(t, ts, "POST", "/api/phishlets/import", admin, map[string]string{"name": "acme", "yaml": validPhishlet}); res.StatusCode != 201 {
		t.Fatalf("import phishlet failed")
	}
	doReq(t, ts, "POST", "/api/phishlets/acme/hostname", admin, map[string]string{"hostname": "corp.test"})

	// create a lure with OG fields
	body := map[string]string{
		"phishlet": "acme", "path": "/go", "redirect_url": "https://example.org",
		"og_title": "Important", "og_desc": "Read this", "og_image": "https://img/x.png", "og_url": "https://corp.test/go",
	}
	res, data := doReq(t, ts, "POST", "/api/lures", admin, body)
	if res.StatusCode != 201 {
		t.Fatalf("create lure: %d %s", res.StatusCode, data)
	}
	var lr struct {
		Lures []core.LureInfo `json:"lures"`
	}
	json.Unmarshal(data, &lr)
	if len(lr.Lures) != 1 {
		t.Fatalf("expected 1 lure")
	}
	l := lr.Lures[0]
	if l.OgTitle != "Important" || l.OgDescription != "Read this" || l.OgImageUrl == "" || l.OgUrl == "" {
		t.Fatalf("OG fields not stored: %+v", l)
	}

	// get-url (base, no params)
	res, data = doReq(t, ts, "POST", "/api/lures/0/url", admin, map[string]interface{}{})
	if res.StatusCode != 200 {
		t.Fatalf("get-url base: %d %s", res.StatusCode, data)
	}
	var u1 struct {
		Url string `json:"url"`
	}
	json.Unmarshal(data, &u1)
	if !strings.HasPrefix(u1.Url, "https://") || !strings.Contains(u1.Url, "corp.test/go") {
		t.Fatalf("unexpected base url: %s", u1.Url)
	}
	if strings.Contains(u1.Url, "?") {
		t.Fatalf("base url should have no query: %s", u1.Url)
	}

	// get-url with params -> encrypted query appended
	res, data = doReq(t, ts, "POST", "/api/lures/0/url", admin, map[string]interface{}{"params": map[string]string{"email": "victim@corp.test"}})
	var u2 struct {
		Url string `json:"url"`
	}
	json.Unmarshal(data, &u2)
	if !strings.Contains(u2.Url, "?") || u2.Url == u1.Url {
		t.Fatalf("expected params-encoded url, got: %s", u2.Url)
	}

	// pause / unpause
	if res, _ := doReq(t, ts, "POST", "/api/lures/0/pause", admin, map[string]string{"duration": "2h"}); res.StatusCode != 200 {
		t.Fatalf("pause failed")
	}
	_, data = doReq(t, ts, "GET", "/api/lures", admin, nil)
	json.Unmarshal(data, &lr)
	if !lr.Lures[0].Paused || lr.Lures[0].PausedUntil == 0 {
		t.Fatalf("lure should be paused: %+v", lr.Lures[0])
	}
	// invalid duration
	if res, _ := doReq(t, ts, "POST", "/api/lures/0/pause", admin, map[string]string{"duration": "soon"}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for bad duration, got %d", res.StatusCode)
	}
	if res, _ := doReq(t, ts, "POST", "/api/lures/0/unpause", admin, nil); res.StatusCode != 200 {
		t.Fatalf("unpause failed")
	}
	_, data = doReq(t, ts, "GET", "/api/lures", admin, nil)
	json.Unmarshal(data, &lr)
	if lr.Lures[0].Paused || lr.Lures[0].PausedUntil != 0 {
		t.Fatalf("lure should be unpaused: %+v", lr.Lures[0])
	}

	// edit preserves/updates OG
	body["og_title"] = "Changed"
	if res, _ := doReq(t, ts, "PUT", "/api/lures/0", admin, body); res.StatusCode != 200 {
		t.Fatalf("edit lure failed")
	}
	_, data = doReq(t, ts, "GET", "/api/lures", admin, nil)
	json.Unmarshal(data, &lr)
	if lr.Lures[0].OgTitle != "Changed" {
		t.Fatalf("OG edit not applied: %+v", lr.Lures[0])
	}

	// RBAC: viewer can get-url (read) but not pause
	doReq(t, ts, "POST", "/api/users", admin, map[string]string{"username": "vl", "password": "viewerpass1", "role": "viewer"})
	viewer := login(t, ts, "vl", "viewerpass1")
	if res, _ := doReq(t, ts, "POST", "/api/lures/0/url", viewer, map[string]interface{}{}); res.StatusCode != 200 {
		t.Fatalf("viewer get-url should be 200, got %d", res.StatusCode)
	}
	if res, _ := doReq(t, ts, "POST", "/api/lures/0/pause", viewer, map[string]string{"duration": "1h"}); res.StatusCode != 403 {
		t.Fatalf("viewer pause should be 403, got %d", res.StatusCode)
	}
}
