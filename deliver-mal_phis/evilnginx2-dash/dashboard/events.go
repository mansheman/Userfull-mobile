package dashboard

import (
	"context"
	"encoding/json"
	"fmt"
	"net/http"
	"strings"
	"sync"
	"time"
)

// sseMessage is a single server-sent event.
type sseMessage struct {
	Event string
	Data  string
}

// hub fans server-sent events out to all connected dashboard clients.
type hub struct {
	mu   sync.Mutex
	subs map[chan sseMessage]struct{}
}

func newHub() *hub {
	return &hub{subs: make(map[chan sseMessage]struct{})}
}

func (h *hub) subscribe() chan sseMessage {
	ch := make(chan sseMessage, 16)
	h.mu.Lock()
	h.subs[ch] = struct{}{}
	h.mu.Unlock()
	return ch
}

func (h *hub) unsubscribe(ch chan sseMessage) {
	h.mu.Lock()
	if _, ok := h.subs[ch]; ok {
		delete(h.subs, ch)
		close(ch)
	}
	h.mu.Unlock()
}

// broadcast delivers a message to every subscriber, dropping it for any client
// whose buffer is full (a slow client must never block capture/event flow).
func (h *hub) broadcast(m sseMessage) {
	h.mu.Lock()
	defer h.mu.Unlock()
	for ch := range h.subs {
		select {
		case ch <- m:
		default:
		}
	}
}

func (h *hub) count() int {
	h.mu.Lock()
	defer h.mu.Unlock()
	return len(h.subs)
}

// sessionsEvent is the payload sent when the captured-session set changes.
type sessionsEvent struct {
	Total    int   `json:"total"`
	Captured int   `json:"captured"`
	LatestId int   `json:"latest_id"`
	At       int64 `json:"at"`
}

// sessionsSnapshot summarises the DB cheaply enough to poll on a short interval.
func (s *Server) sessionsSnapshot() (sig string, ev sessionsEvent) {
	sessions, err := s.db.ListSessions()
	if err != nil {
		return "", ev
	}
	var maxUpdate int64
	for _, sess := range sessions {
		ev.Total++
		if isCaptured(sess) {
			ev.Captured++
		}
		if sess.Id > ev.LatestId {
			ev.LatestId = sess.Id
		}
		if sess.UpdateTime > maxUpdate {
			maxUpdate = sess.UpdateTime
		}
	}
	// signature changes when a session is added, removed, or updated
	sig = fmt.Sprintf("%d:%d:%d:%d", ev.Total, ev.Captured, ev.LatestId, maxUpdate)
	return sig, ev
}

// notifySessions emits a sessions event immediately (used after a
// dashboard-driven mutation so the change shows without waiting for the poll).
func (s *Server) notifySessions() {
	_, ev := s.sessionsSnapshot()
	ev.At = nowUnix()
	b, _ := json.Marshal(ev)
	s.hub.broadcast(sseMessage{Event: "sessions", Data: string(b)})
}

// watchSessions polls the DB and broadcasts when the session set changes.
func (s *Server) watchSessions(ctx context.Context) {
	ticker := time.NewTicker(s.pollInterval)
	defer ticker.Stop()
	lastSig, _ := s.sessionsSnapshot()
	for {
		select {
		case <-ctx.Done():
			return
		case <-ticker.C:
			if s.hub.count() == 0 {
				continue // nobody listening; skip the scan
			}
			sig, ev := s.sessionsSnapshot()
			if sig != "" && sig != lastSig {
				lastSig = sig
				ev.At = nowUnix()
				b, _ := json.Marshal(ev)
				s.hub.broadcast(sseMessage{Event: "sessions", Data: string(b)})
			}
		}
	}
}

func nowUnix() int64 {
	return time.Now().UTC().Unix()
}

// handleEvents streams server-sent events to an authenticated client.
func (s *Server) handleEvents(w http.ResponseWriter, r *http.Request) {
	flusher, ok := w.(http.Flusher)
	if !ok {
		writeError(w, http.StatusInternalServerError, "streaming unsupported")
		return
	}
	// SSE connections are long-lived: clear the server's write deadline for
	// this request only, so the global WriteTimeout doesn't sever the stream.
	if rc := http.NewResponseController(w); rc != nil {
		rc.SetWriteDeadline(time.Time{})
	}

	w.Header().Set("Content-Type", "text/event-stream")
	w.Header().Set("Cache-Control", "no-cache")
	w.Header().Set("Connection", "keep-alive")
	w.Header().Set("X-Accel-Buffering", "no") // disable proxy buffering
	w.WriteHeader(http.StatusOK)

	sub := s.hub.subscribe()
	defer s.hub.unsubscribe(sub)

	// prime the client with the current snapshot
	_, ev := s.sessionsSnapshot()
	ev.At = nowUnix()
	if b, err := json.Marshal(ev); err == nil {
		writeSSE(w, flusher, sseMessage{Event: "sessions", Data: string(b)})
	}

	keepalive := time.NewTicker(25 * time.Second)
	defer keepalive.Stop()

	ctx := r.Context()
	for {
		select {
		case <-ctx.Done():
			return
		case m, ok := <-sub:
			if !ok {
				return
			}
			writeSSE(w, flusher, m)
		case <-keepalive.C:
			fmt.Fprint(w, ": keepalive\n\n")
			flusher.Flush()
		}
	}
}

// writeSSE writes one event frame, escaping newlines across data lines per spec.
func writeSSE(w http.ResponseWriter, flusher http.Flusher, m sseMessage) {
	if m.Event != "" {
		fmt.Fprintf(w, "event: %s\n", m.Event)
	}
	for _, line := range strings.Split(m.Data, "\n") {
		fmt.Fprintf(w, "data: %s\n", line)
	}
	fmt.Fprint(w, "\n")
	flusher.Flush()
}
