package dashboard

import (
	"net/http/httptest"
	"os"
	"path/filepath"
	"testing"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/core"
	"github.com/kgretzky/evilginx2/database"
)

func newAgentServer(t *testing.T, token string) *Server {
	t.Helper()
	dir := t.TempDir()
	cfg, err := core.NewConfig(dir, "")
	if err != nil {
		t.Fatalf("config: %v", err)
	}
	db, err := database.NewDatabase(filepath.Join(dir, "data.db"))
	if err != nil {
		t.Fatalf("database: %v", err)
	}
	bl, _ := core.NewBlacklist(filepath.Join(dir, "blacklist.txt"))
	os.Setenv("EVILGINX_DASH_PASSWORD", "testpass123")
	s, err := New(Config{
		Addr:         "127.0.0.1:0",
		StorePath:    filepath.Join(dir, "agent.db"),
		PhishletsDir: dir,
		Version:      "test",
		AgentToken:   token,
	}, cfg, db, nil, bl)
	if err != nil {
		t.Fatalf("New: %v", err)
	}
	return s
}

func TestAgentTokenAuth(t *testing.T) {
	const token = "agent-secret-token-xyz"
	s := newAgentServer(t, token)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()

	// valid agent token → access to a protected resource endpoint
	if res, data := doReq(t, ts, "GET", "/api/phishlets", token, nil); res.StatusCode != 200 {
		t.Fatalf("agent token should grant access, got %d %s", res.StatusCode, data)
	}
	// agent token reaches admin-only endpoints (synthetic admin actor)
	if res, _ := doReq(t, ts, "GET", "/api/users", token, nil); res.StatusCode != 200 {
		t.Fatalf("agent token should reach admin endpoints, got %d", res.StatusCode)
	}
	// wrong token → 401
	if res, _ := doReq(t, ts, "GET", "/api/phishlets", "wrong-token", nil); res.StatusCode != 401 {
		t.Fatalf("wrong token should be 401, got %d", res.StatusCode)
	}
	// no token → 401
	if res, _ := doReq(t, ts, "GET", "/api/phishlets", "", nil); res.StatusCode != 401 {
		t.Fatalf("missing token should be 401, got %d", res.StatusCode)
	}
}

func TestAgentTokenDisabledByDefault(t *testing.T) {
	// when no agent token is configured, only user JWTs work
	s, _ := newTestServer(t) // no AgentToken
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()

	if res, _ := doReq(t, ts, "GET", "/api/phishlets", "any-random-token", nil); res.StatusCode != 401 {
		t.Fatalf("random token must not authenticate when agent mode is off, got %d", res.StatusCode)
	}
}
