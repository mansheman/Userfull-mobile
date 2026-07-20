package dashboard

import (
	"bytes"
	"encoding/json"
	"io"
	"net/http"
	"net/http/httptest"
	"os"
	"path/filepath"
	"strconv"
	"testing"

	"github.com/gorilla/mux"

	"github.com/kgretzky/evilginx2/core"
	"github.com/kgretzky/evilginx2/database"
)

func newTestServer(t *testing.T) (*Server, *database.Database) {
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
	bl, err := core.NewBlacklist(filepath.Join(dir, "blacklist.txt"))
	if err != nil {
		t.Fatalf("blacklist: %v", err)
	}
	os.Setenv("EVILGINX_DASH_PASSWORD", "testpass123")
	s, err := New(Config{
		Addr:         "127.0.0.1:0",
		StorePath:    filepath.Join(dir, "dashboard.db"),
		PhishletsDir: dir,
		Version:      "test",
	}, cfg, db, nil, bl)
	if err != nil {
		t.Fatalf("dashboard.New: %v", err)
	}
	return s, db
}

func doReq(t *testing.T, ts *httptest.Server, method, path, token string, body interface{}) (*http.Response, []byte) {
	t.Helper()
	var rdr io.Reader
	if body != nil {
		b, _ := json.Marshal(body)
		rdr = bytes.NewReader(b)
	}
	req, _ := http.NewRequest(method, ts.URL+path, rdr)
	if token != "" {
		req.Header.Set("Authorization", "Bearer "+token)
	}
	if body != nil {
		req.Header.Set("Content-Type", "application/json")
	}
	res, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatalf("%s %s: %v", method, path, err)
	}
	data, _ := io.ReadAll(res.Body)
	res.Body.Close()
	return res, data
}

func login(t *testing.T, ts *httptest.Server, user, pass string) string {
	res, data := doReq(t, ts, "POST", "/api/auth/login", "", map[string]string{"username": user, "password": pass})
	if res.StatusCode != 200 {
		t.Fatalf("login %s failed: %d %s", user, res.StatusCode, data)
	}
	var lr struct {
		Token string `json:"token"`
	}
	json.Unmarshal(data, &lr)
	if lr.Token == "" {
		t.Fatalf("login returned empty token")
	}
	return lr.Token
}

func TestSmoke(t *testing.T) {
	s, db := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()

	// seed a captured session
	sid := "testsid123"
	if err := db.CreateSession(sid, "example", "https://login.example.com/", "Mozilla/5.0", "1.2.3.4"); err != nil {
		t.Fatalf("create session: %v", err)
	}
	db.SetSessionUsername(sid, "victim@example.com")
	db.SetSessionPassword(sid, "hunter2")
	db.SetSessionCookieTokens(sid, map[string]map[string]*database.CookieToken{
		".example.com": {"sid": {Name: "sid", Value: "abc", Path: "/", HttpOnly: true}},
	})

	// unauthorized
	if res, _ := doReq(t, ts, "GET", "/api/sessions", "", nil); res.StatusCode != 401 {
		t.Fatalf("expected 401 without token, got %d", res.StatusCode)
	}

	// bad creds
	if res, _ := doReq(t, ts, "POST", "/api/auth/login", "", map[string]string{"username": "admin", "password": "wrong"}); res.StatusCode != 401 {
		t.Fatalf("expected 401 for bad creds, got %d", res.StatusCode)
	}

	admin := login(t, ts, "admin", "testpass123")

	// stats
	res, data := doReq(t, ts, "GET", "/api/stats", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("stats: %d %s", res.StatusCode, data)
	}
	var stats map[string]interface{}
	json.Unmarshal(data, &stats)
	if stats["captured_sessions"].(float64) != 1 {
		t.Fatalf("expected 1 captured session, got %v", stats["captured_sessions"])
	}

	// sessions list
	res, data = doReq(t, ts, "GET", "/api/sessions", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("sessions: %d %s", res.StatusCode, data)
	}
	var sl struct {
		Sessions []sessionView `json:"sessions"`
		Total    int           `json:"total"`
	}
	json.Unmarshal(data, &sl)
	if sl.Total != 1 || sl.Sessions[0].Username != "victim@example.com" {
		t.Fatalf("unexpected sessions: %s", data)
	}
	if sl.Sessions[0].CookieCount != 1 {
		t.Fatalf("expected cookie_count 1, got %d", sl.Sessions[0].CookieCount)
	}

	// session detail with cookie json
	id := sl.Sessions[0].Id
	res, data = doReq(t, ts, "GET", "/api/sessions/"+strconv.Itoa(id), admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("session detail: %d %s", res.StatusCode, data)
	}
	if !bytes.Contains(data, []byte("\"cookies_json\"")) || !bytes.Contains(data, []byte("expirationDate")) {
		t.Fatalf("cookie export missing: %s", data)
	}

	// create a viewer and verify RBAC: viewer cannot delete sessions
	res, data = doReq(t, ts, "POST", "/api/users", admin, map[string]string{"username": "view1", "password": "viewerpass1", "role": "viewer"})
	if res.StatusCode != 201 {
		t.Fatalf("create user: %d %s", res.StatusCode, data)
	}
	viewer := login(t, ts, "view1", "viewerpass1")
	if res, _ := doReq(t, ts, "DELETE", "/api/sessions/"+strconv.Itoa(id), viewer, nil); res.StatusCode != 403 {
		t.Fatalf("expected 403 for viewer delete, got %d", res.StatusCode)
	}
	// viewer cannot list users (admin only)
	if res, _ := doReq(t, ts, "GET", "/api/users", viewer, nil); res.StatusCode != 403 {
		t.Fatalf("expected 403 for viewer users list, got %d", res.StatusCode)
	}

	// admin can delete session
	if res, _ := doReq(t, ts, "DELETE", "/api/sessions/"+strconv.Itoa(id), admin, nil); res.StatusCode != 200 {
		t.Fatalf("admin delete session failed")
	}

	// audit log should now contain entries
	res, data = doReq(t, ts, "GET", "/api/audit", admin, nil)
	if res.StatusCode != 200 || !bytes.Contains(data, []byte("login")) {
		t.Fatalf("audit log: %d %s", res.StatusCode, data)
	}

	// admin cannot delete self
	if res, _ := doReq(t, ts, "DELETE", "/api/users/admin", admin, nil); res.StatusCode != 400 {
		t.Fatalf("expected 400 deleting self, got %d", res.StatusCode)
	}
}
