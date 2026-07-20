package dashboard

import (
	"encoding/json"
	"net/http/httptest"
	"testing"

	"github.com/gorilla/mux"
)

// mountServer wraps a *Server in an httptest server.
func mountServer(s *Server) *httptest.Server {
	r := mux.NewRouter()
	s.routes(r)
	return httptest.NewServer(securityHeaders(r))
}

func TestFleetEngineRegistry(t *testing.T) {
	const engineToken = "engine-agent-token-123"

	// the "engine": a server in agent mode
	engine := newAgentServer(t, engineToken)
	engineTS := mountServer(engine)
	defer engineTS.Close()

	// the "control plane": a normal dashboard
	cp, _ := newTestServer(t)
	cpTS := mountServer(cp)
	defer cpTS.Close()
	admin := login(t, cpTS, "admin", "testpass123")

	// register the engine
	res, data := doReq(t, cpTS, "POST", "/api/engines", admin, map[string]string{
		"name": "vps-a", "url": engineTS.URL, "token": engineToken,
	})
	if res.StatusCode != 201 {
		t.Fatalf("create engine: %d %s", res.StatusCode, data)
	}
	var cr struct {
		Engine struct {
			Id    string `json:"id"`
			Name  string `json:"name"`
			Token string `json:"token"` // must be absent
		} `json:"engine"`
		Status EngineStatus `json:"status"`
	}
	json.Unmarshal(data, &cr)
	if cr.Engine.Id == "" || cr.Engine.Name != "vps-a" {
		t.Fatalf("unexpected engine: %+v", cr.Engine)
	}
	if cr.Engine.Token != "" {
		t.Fatalf("token must never be serialized: %s", data)
	}
	if !cr.Status.Online || !cr.Status.AuthOK {
		t.Fatalf("engine should be online + auth ok on register: %+v", cr.Status)
	}
	id := cr.Engine.Id

	// list (no token leaked)
	res, data = doReq(t, cpTS, "GET", "/api/engines", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("list engines: %d", res.StatusCode)
	}

	// check
	res, data = doReq(t, cpTS, "GET", "/api/engines/"+id+"/check", admin, nil)
	var st EngineStatus
	json.Unmarshal(data, &st)
	if !st.Online || !st.AuthOK {
		t.Fatalf("check should be online + auth ok: %+v", st)
	}

	// forward: read the engine's phishlets through the control plane
	res, data = doReq(t, cpTS, "GET", "/api/engines/"+id+"/r/phishlets", admin, nil)
	if res.StatusCode != 200 {
		t.Fatalf("forward phishlets: %d %s", res.StatusCode, data)
	}
	if _, ok := map[string]json.RawMessage{}["phishlets"]; ok {
		_ = ok
	}
	var pl map[string]json.RawMessage
	json.Unmarshal(data, &pl)
	if _, ok := pl["phishlets"]; !ok {
		t.Fatalf("forwarded response should contain phishlets: %s", data)
	}

	// bad token engine -> check shows online but auth failure
	res, data = doReq(t, cpTS, "POST", "/api/engines", admin, map[string]string{
		"name": "vps-bad", "url": engineTS.URL, "token": "wrong",
	})
	json.Unmarshal(data, &cr)
	if cr.Status.Online && cr.Status.AuthOK {
		t.Fatalf("bad-token engine should not be auth ok: %+v", cr.Status)
	}

	// unreachable engine -> offline
	res, data = doReq(t, cpTS, "POST", "/api/engines", admin, map[string]string{
		"name": "vps-down", "url": "http://127.0.0.1:1", "token": "x",
	})
	json.Unmarshal(data, &cr)
	if cr.Status.Online {
		t.Fatalf("unreachable engine should be offline: %+v", cr.Status)
	}

	// validation: bad url rejected
	if res, _ := doReq(t, cpTS, "POST", "/api/engines", admin, map[string]string{"name": "x", "url": "not-a-url", "token": "t"}); res.StatusCode != 400 {
		t.Fatalf("bad url should be 400, got %d", res.StatusCode)
	}

	// RBAC: operator can list/check but not create
	doReq(t, cpTS, "POST", "/api/users", admin, map[string]string{"username": "op", "password": "operatorpw1", "role": "operator"})
	op := login(t, cpTS, "op", "operatorpw1")
	if res, _ := doReq(t, cpTS, "GET", "/api/engines", op, nil); res.StatusCode != 200 {
		t.Fatalf("operator should list engines, got %d", res.StatusCode)
	}
	if res, _ := doReq(t, cpTS, "POST", "/api/engines", op, map[string]string{"name": "x", "url": engineTS.URL, "token": "t"}); res.StatusCode != 403 {
		t.Fatalf("operator should not create engines, got %d", res.StatusCode)
	}

	// delete
	if res, _ := doReq(t, cpTS, "DELETE", "/api/engines/"+id, admin, nil); res.StatusCode != 200 {
		t.Fatalf("delete engine failed")
	}
}
