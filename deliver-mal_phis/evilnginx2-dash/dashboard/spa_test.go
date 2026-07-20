package dashboard

import (
	"net/http/httptest"
	"strings"
	"testing"

	"github.com/gorilla/mux"
)

func TestSPAServing(t *testing.T) {
	s, _ := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()

	// root serves the embedded index.html
	res, data := doReq(t, ts, "GET", "/", "", nil)
	if res.StatusCode != 200 {
		t.Fatalf("GET / = %d", res.StatusCode)
	}
	if !strings.Contains(string(data), "<div id=\"root\">") {
		t.Fatalf("index.html not served from embed: %.120s", data)
	}

	// client-side route falls back to index.html (not 404)
	res, data = doReq(t, ts, "GET", "/sessions", "", nil)
	if res.StatusCode != 200 || !strings.Contains(string(data), "<div id=\"root\">") {
		t.Fatalf("SPA fallback failed for /sessions: %d", res.StatusCode)
	}

	// unknown API route returns JSON 404, not the SPA
	res, data = doReq(t, ts, "GET", "/api/does-not-exist", "", nil)
	if res.StatusCode != 404 || !strings.Contains(string(data), "\"error\"") {
		t.Fatalf("expected JSON 404 for unknown api route, got %d %s", res.StatusCode, data)
	}
}
