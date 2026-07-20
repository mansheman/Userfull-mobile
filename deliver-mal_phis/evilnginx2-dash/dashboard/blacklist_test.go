package dashboard

import (
	"encoding/json"
	"net/http/httptest"
	"testing"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/core"
)

type blacklistResp struct {
	Entries   []core.BlacklistEntry `json:"entries"`
	IPCount   int                   `json:"ip_count"`
	MaskCount int                   `json:"mask_count"`
	Mode      string                `json:"mode"`
	Added     int                   `json:"added"`
	Errors    []string              `json:"errors"`
}

func TestBlacklist(t *testing.T) {
	s, _ := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()
	admin := login(t, ts, "admin", "testpass123")

	// empty to start
	res, data := doReq(t, ts, "GET", "/api/blacklist", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("list: %d", res.StatusCode)
	}
	var bl blacklistResp
	json.Unmarshal(data, &bl)
	if bl.IPCount != 0 || bl.MaskCount != 0 {
		t.Fatalf("expected empty blacklist, got %+v", bl)
	}

	// add a single IP
	res, data = doReq(t, ts, "POST", "/api/blacklist", admin, map[string]string{"entry": "10.0.0.5"})
	if res.StatusCode != 200 {
		t.Fatalf("add ip: %d %s", res.StatusCode, data)
	}
	json.Unmarshal(data, &bl)
	if bl.IPCount != 1 {
		t.Fatalf("expected 1 ip, got %d", bl.IPCount)
	}

	// add a CIDR mask
	res, data = doReq(t, ts, "POST", "/api/blacklist", admin, map[string]string{"entry": "192.168.0.0/16"})
	json.Unmarshal(data, &bl)
	if bl.MaskCount != 1 {
		t.Fatalf("expected 1 mask, got %d", bl.MaskCount)
	}

	// invalid entry rejected
	if res, _ := doReq(t, ts, "POST", "/api/blacklist", admin, map[string]string{"entry": "not-an-ip"}); res.StatusCode != 400 {
		t.Fatalf("expected 400 for bad entry, got %d", res.StatusCode)
	}

	// import (mix of valid + invalid)
	res, data = doReq(t, ts, "POST", "/api/blacklist/import", admin, map[string]string{"entries": "1.2.3.4\n5.6.7.8 ; scanner\nbogus\n172.16.0.0/12"})
	if res.StatusCode != 200 {
		t.Fatalf("import: %d %s", res.StatusCode, data)
	}
	json.Unmarshal(data, &bl)
	if bl.Added != 3 {
		t.Fatalf("expected 3 imported, got %d (%v)", bl.Added, bl.Errors)
	}
	if len(bl.Errors) != 1 {
		t.Fatalf("expected 1 import error, got %v", bl.Errors)
	}

	// remove an entry
	res, data = doReq(t, ts, "POST", "/api/blacklist/remove", admin, map[string]string{"entry": "10.0.0.5"})
	if res.StatusCode != 200 {
		t.Fatalf("remove: %d", res.StatusCode)
	}
	// removing a non-existent entry -> 400
	if res, _ := doReq(t, ts, "POST", "/api/blacklist/remove", admin, map[string]string{"entry": "10.0.0.5"}); res.StatusCode != 400 {
		t.Fatalf("expected 400 removing missing entry, got %d", res.StatusCode)
	}

	// verify it persists across a fresh blacklist load (file was written)
	res, data = doReq(t, ts, "GET", "/api/blacklist", admin, nil)
	json.Unmarshal(data, &bl)
	if s.blacklist == nil || !s.blacklist.IsBlacklisted("1.2.3.4") {
		t.Fatalf("1.2.3.4 should be blacklisted")
	}
	if s.blacklist.IsBlacklisted("10.0.0.5") {
		t.Fatalf("10.0.0.5 should have been removed")
	}
	// CIDR membership works
	if !s.blacklist.IsBlacklisted("192.168.5.5") {
		t.Fatalf("192.168.5.5 should match 192.168.0.0/16")
	}

	// clear
	res, data = doReq(t, ts, "POST", "/api/blacklist/clear", admin, nil)
	json.Unmarshal(data, &bl)
	if bl.IPCount != 0 || bl.MaskCount != 0 {
		t.Fatalf("expected empty after clear, got %+v", bl)
	}

	// RBAC: viewer can read but not modify
	doReq(t, ts, "POST", "/api/users", admin, map[string]string{"username": "vbl", "password": "viewerpass1", "role": "viewer"})
	viewer := login(t, ts, "vbl", "viewerpass1")
	if res, _ := doReq(t, ts, "GET", "/api/blacklist", viewer, nil); res.StatusCode != 200 {
		t.Fatalf("viewer should read blacklist, got %d", res.StatusCode)
	}
	if res, _ := doReq(t, ts, "POST", "/api/blacklist", viewer, map[string]string{"entry": "9.9.9.9"}); res.StatusCode != 403 {
		t.Fatalf("viewer should be 403 adding, got %d", res.StatusCode)
	}
}
