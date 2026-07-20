package dashboard

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"time"
)

// AgentClient calls a registered engine's agent API over the (WireGuard) network,
// authenticating with the engine's agent token.
type AgentClient struct {
	http *http.Client
}

func newAgentClient() *AgentClient {
	return &AgentClient{
		http: &http.Client{Timeout: 15 * time.Second},
	}
}

// EngineStatus is the result of a connectivity/auth probe against an engine.
type EngineStatus struct {
	Online  bool   `json:"online"`
	AuthOK  bool   `json:"auth_ok"`
	Version string `json:"version"`
	Error   string `json:"error,omitempty"`
}

// Check probes an engine: reachable? token valid? what version?
func (c *AgentClient) Check(e *Engine) EngineStatus {
	st := EngineStatus{}
	// authed endpoint verifies both reachability and token validity
	req, err := http.NewRequest("GET", e.URL+"/api/config", nil)
	if err != nil {
		st.Error = err.Error()
		return st
	}
	req.Header.Set("Authorization", "Bearer "+e.Token)
	resp, err := c.http.Do(req)
	if err != nil {
		st.Error = "unreachable: " + err.Error()
		return st
	}
	defer resp.Body.Close()
	st.Online = true
	if resp.StatusCode == http.StatusUnauthorized {
		st.Error = "invalid agent token"
		return st
	}
	if resp.StatusCode != http.StatusOK {
		st.Error = fmt.Sprintf("unexpected status %d", resp.StatusCode)
		return st
	}
	st.AuthOK = true
	var body map[string]interface{}
	if json.NewDecoder(resp.Body).Decode(&body) == nil {
		if v, ok := body["version"].(string); ok {
			st.Version = v
		}
	}
	return st
}

// Forward proxies a request to an engine's agent API and returns the raw
// response. Path is relative to the engine's /api root (e.g. "phishlets").
func (c *AgentClient) Forward(e *Engine, method, apiPath string, body io.Reader, contentType string) (*http.Response, error) {
	req, err := http.NewRequest(method, e.URL+"/api/"+apiPath, body)
	if err != nil {
		return nil, err
	}
	req.Header.Set("Authorization", "Bearer "+e.Token)
	if contentType != "" {
		req.Header.Set("Content-Type", contentType)
	}
	return c.http.Do(req)
}
