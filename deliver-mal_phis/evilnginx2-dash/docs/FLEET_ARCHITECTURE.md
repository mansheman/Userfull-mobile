# Fleet Architecture — Control Plane / Data Plane Split

Target design for running **one dashboard managing many evilginx engines**, with
**captured data mirrored to the dashboard host** (survives a burned/seized engine).
This is the architecture-of-record for the fleet work (P1–P5).

---

## Topology

```
                    ┌──────────────────────────────────────────────┐
                    │   Dashboard host  (durable, private, NOT      │
                    │   internet-facing — behind WG / SSH only)     │
                    │                                               │
                    │   evilginx-dash  (control plane)              │
                    │    • SPA + login / users / roles / audit      │
                    │    • engine registry  (name, WG URL, token)   │
                    │    • mirror store  (all engines' sessions —    │
                    │      durable, survives engine takedown)       │
                    │    • forwards management to the right engine  │
                    └───────────────────────┬──────────────────────┘
                                            │
                         WireGuard (encrypted mesh, NAT-friendly)
                         per-engine bearer token on the agent API
                ┌───────────────────────────┼───────────────────────────┐
                │                           │                           │
        ┌───────▼────────┐         ┌────────▼───────┐         ┌─────────▼──────┐
        │   VPS A         │         │   VPS B         │         │   VPS C         │
        │  (burnable)     │         │  (burnable)     │         │  (burnable)     │
        │                 │         │                 │         │                 │
        │ evilginx engine │         │ evilginx engine │         │ evilginx engine │
        │  • DNS / proxy  │         │  • DNS / proxy  │         │  • DNS / proxy  │
        │  • terminal     │         │  • terminal     │         │  • terminal     │
        │  + agent API    │         │  + agent API    │         │  + agent API    │
        │   token-auth,   │         │   token-auth,   │         │   token-auth,   │
        │   WG-bound:8081 │         │   WG-bound:8081 │         │   WG-bound:8081 │
        └───────┬─────────┘         └───────┬─────────┘         └───────┬─────────┘
                │ public 80/443 (grey-cloud DNS → victims)               │
           victims/targets                victims/targets           victims/targets
```

---

## Why this shape (the hard constraint)

The dashboard's management actions mutate **live in-memory engine state** and call
the running proxy:
- `*database.Database` is **BuntDB** — single-process file lock; another process
  can't open `data.db`.
- enable phishlet → **provision TLS certs** via the live `*core.HttpProxy`; set
  hostname, blacklist, lures → live `*core.Config`.

So the **control logic must stay inside each engine process.** "Separation" means
splitting out the *operator-facing* layer, not the control layer:
- **Engine = agent**: exposes the resource API (sessions/lures/phishlets/blacklist/
  config/events) as a token-authed machine API, bound to the WireGuard interface.
- **Dashboard = control plane**: no engine; registers N engines, **pulls + mirrors**
  their data into its own durable store, and **proxies management** to the chosen
  engine.

---

## Components & responsibilities

| Component | Runs on | Responsibilities |
|---|---|---|
| **evilginx engine + agent API** | each phishing VPS | DNS, MITM proxy, terminal; serve the resource API over WireGuard, token-auth. Holds the *live* state. |
| **evilginx-dash (control plane)** | dashboard host | SPA, users/roles/audit, engine registry, agent HTTP client, **session mirror store**, management forwarding, aggregate stats. The durable copy of captured data. |
| **WireGuard** | both | encrypted mesh between dash and engines; NAT-friendly; the agent API is bound to the WG IP and never exposed publicly. |

---

## Auth & transport
- **Engine ↔ dashboard:** WireGuard tunnel (recommended) or SSH tunnels. Agent API
  binds the WG interface (`-agent-addr <wg-ip>:8081`), never `0.0.0.0` public.
- **Agent API auth:** a **per-engine static token** (`-agent-token` / `EVILGINX_AGENT_TOKEN`).
  The dashboard stores `(name, url, token)` per engine. The token maps to a
  synthetic admin actor inside the engine (P1, done).
- **Operator ↔ dashboard:** the existing user accounts / JWT / roles.
- Public exposure: **only 80/443 on the engines** (grey-cloud DNS → victims). The
  dashboard + all agent APIs stay on the private WG network.

---

## Data flow

**Management (write):** operator → dashboard UI → dashboard forwards the call to the
selected engine's agent API over WG → engine mutates live state → returns result.

**Mirror (read, durable):** the dashboard **pulls** each engine's sessions on an
interval (and/or subscribes to the engine's SSE `/api/events`) and writes them into
its **own store, keyed by engine**. Sessions/stats/export read the mirror — so the
data survives even if a phishing VPS is taken down between syncs. (Pull chosen over
push: reuses the same management channel, no inbound endpoint on the dashboard, and
the dashboard is the trusted durable side.)

---

## Phased plan & status

| Phase | Scope | Status |
|---|---|---|
| **P1** | Engine **agent API** — token auth on the resource endpoints (`-agent/-agent-addr/-agent-token`) | ✅ done |
| **P2** | Fleet dashboard: **engine registry** (name/url/token) + **agent HTTP client** + connectivity check + generic forwarding (`/api/engines/{id}/r/{path}`) + Engines UI | ✅ done |
| **P3** | **Session mirror/sync**: pull each engine's sessions (poll + SSE) into the dashboard store; views/exports read the mirror | |
| **P4** | **Multi-engine UX**: engine switcher, per-engine online/offline status, aggregate stats | |
| **P5** | **WireGuard + split deploy**: WG setup, separate `agent` and `dash` systemd units / push scripts | |

---

## Deploy roles (P5 preview)
- **Engine role** (each VPS): `evilginx -p ... -agent -agent-addr <wg-ip>:8081` (+ `-dashboard` optional for local fallback). Public 80/443; agent API on WG only.
- **Dashboard role** (mgmt host): runs `evilginx-dash` (control-plane mode); reachable only via WG/SSH; holds the mirror store + accounts.
- WireGuard config ties them; each engine registered in the dashboard with its WG URL + token.
