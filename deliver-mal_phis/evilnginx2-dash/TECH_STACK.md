# Tech Stack

This document describes the technology stack of this repository: the **evilginx2
engine** (upstream) and the **reporting/management dashboard** added on top of it.

> Authorized-use tooling for red-team engagements. See `dashboard/README.md` for
> dashboard operation and `README.md` for the engine.

---

## 1. Overview

| Layer | Technology |
| --- | --- |
| Language (backend) | Go `1.22` (toolchain builds with Go 1.25) |
| Engine | evilginx2 — MITM reverse-proxy phishing framework (v`3.3.0`) |
| Dashboard API | Go, `gorilla/mux`, standard library `net/http` |
| Dashboard frontend | React 18 + Vite 5 + Tailwind CSS 4 (SPA) |
| Frontend delivery | Compiled to static assets, embedded in the Go binary via `go:embed` |
| Engine data store | BuntDB (`~/.evilginx/data.db`) |
| Dashboard data store | BuntDB (`~/.evilginx/dashboard.db`) |
| Auth | Stdlib HS256 tokens + PBKDF2-HMAC-SHA256 password hashing |
| Live updates | Server-Sent Events (SSE) |
| Build | `go build` / `make`, Go module vendoring (`vendor/`); `npm` for the frontend |

Single binary, single process: the dashboard runs inside the evilginx process and
shares its live `Config` and `Database` handles.

---

## 2. Engine (upstream evilginx2)

Core Go packages: `core/`, `database/`, `log/`, `parser/`.

Key dependencies (`go.mod`):

| Module | Version | Role |
| --- | --- | --- |
| `github.com/caddyserver/certmagic` | v0.20.0 | Automatic TLS (Let's Encrypt / autocert) |
| `github.com/go-acme/lego/v3` | v3.1.0 | ACME client |
| `github.com/elazarl/goproxy` | (pinned) | MITM HTTP/HTTPS proxy |
| `github.com/miekg/dns` | v1.1.58 | Built-in DNS nameserver |
| `github.com/inconshreveable/go-vhost` | (pinned) | TLS SNI vhost demux |
| `github.com/chzyer/readline` | (pinned) | Interactive terminal UI |
| `github.com/spf13/viper` | v1.10.1 | Config file (`config.json`) management |
| `github.com/tidwall/buntdb` | v1.1.0 | Embedded key-value store (sessions) |
| `github.com/fatih/color` | v1.13.0 | Terminal colors |
| `go.uber.org/zap` | v1.27.0 | Logging (silenced for certmagic) |
| `golang.org/x/net` | v0.22.0 | HTTP/networking helpers |

Long-running components started in `main.go`: DNS nameserver, HTTPS MITM proxy,
HTTP (ACME/redirect) server, and the interactive readline terminal.

---

## 3. Dashboard backend (`dashboard/`)

Pure Go, **no new third-party dependencies** beyond what the engine already
vendors. Package `dashboard` imports `core` and `database`; it never modifies the
engine's capture path.

| Concern | Implementation | File |
| --- | --- | --- |
| HTTP routing | `github.com/gorilla/mux` (already vendored) | `server.go` |
| Auth tokens | Hand-rolled JWT **HS256** using stdlib `crypto/hmac` + `crypto/sha256` | `auth.go` |
| Password hashing | **PBKDF2-HMAC-SHA256** via vendored `golang.org/x/crypto/pbkdf2` (120k iters, 16-byte salt) | `store.go` |
| RBAC | Roles `viewer` < `operator` < `admin`, enforced by middleware | `auth.go` |
| Dashboard store | **BuntDB** — users, audit log, JWT secret, settings | `store.go` |
| Live updates | **SSE** hub + 2s DB-diff poller; fetch-stream client (Bearer header) | `events.go` |
| Static SPA | `go:embed all:web/dist` with SPA fallback | `embed.go` |
| Core bridge | Read-only DTO accessors in **package `core`** (avoids import cycle) | `core/dashboard_export.go` |

Handlers are split by area: `handlers_auth.go`, `handlers_sessions.go`,
`handlers_assets.go` (lures/phishlets), `handlers_phishlet_files.go` (YAML
import/edit), `handlers_config.go` (server settings + phishlet hostname),
`handlers_admin.go` (users/audit).

### Why these choices
- **Zero new Go deps / vendored build** — the binary is cross-compiled and
  deployed to engagement hosts; an offline-reliable build matters more than
  conveniences. Auth is stdlib; storage reuses BuntDB.
- **BuntDB (not SQLite) for the dashboard store** — the store only holds
  users/audit/settings; session data lives in the engine's BuntDB and is read via
  the live handle. Kept behind a `Store` type so SQLite is a drop-in later.
- **Embedded API (not a separate service)** — shares the live in-memory DB handle,
  so there is no BuntDB file-lock conflict and data is real-time.

---

## 4. Dashboard frontend (`dashboard/web/`)

Single-page app, built to `dashboard/web/dist` and embedded into the Go binary.

| Dependency | Version | Role |
| --- | --- | --- |
| `react` / `react-dom` | ^18.3.1 | UI library |
| `react-router-dom` | ^6.26.2 | Client-side routing (HashRouter) |
| `vite` | ^5.4.8 | Build tool / dev server |
| `@vitejs/plugin-react` | ^4.3.1 | React plugin for Vite |
| `tailwindcss` + `@tailwindcss/vite` | ^4.0.0 | Utility-first CSS (v4, Vite plugin) |

Toolchain: Node `25.x`, npm `11.x`. No component library — UI primitives are
hand-built (`src/ui.jsx`). Auth/token handling in `src/api.js` + `src/auth.jsx`;
live SSE client in `src/live.jsx`. Pages under `src/pages/`: Login, Dashboard,
Sessions, Lures, Phishlets, Users, Audit, Account, Settings.

`vite.config.js` uses `base: './'` (relative) so the bundle works from the
embedded mount, and proxies `/api` → `127.0.0.1:8080` in dev.

---

## 5. Data & storage

| Path | Store | Contents |
| --- | --- | --- |
| `~/.evilginx/config.json` | Viper/JSON | Server config, phishlet config, lures |
| `~/.evilginx/data.db` | BuntDB | Captured sessions (creds, cookies, tokens) |
| `~/.evilginx/dashboard.db` | BuntDB | Dashboard users, audit log, JWT secret |
| `phishlets/*.yaml` | YAML | Phishlet definitions (importable/editable via UI) |

All hold sensitive engagement data — treat as evidence; keep loopback-bound.

---

## 6. Build & run

```bash
# Frontend (only when dashboard/web/src changes)
cd dashboard/web && npm install && npm run build   # -> dashboard/web/dist

# Backend (embeds dist via go:embed)
make build                # -> ./build/evilginx
# or: go build -o build/evilginx main.go

# Run with the dashboard
EVILGINX_DASH_PASSWORD=YourPass123 ./build/evilginx -p ./phishlets -dashboard
# local testing without ACME:
#   add -developer (self-signed certs)
# real engagement:
#   run as root (ports 53/80/443), set base domain + external IP + DNS records
```

Flags: `-dashboard` (enable, default off), `-dashboard-addr` (default
`127.0.0.1:8080`). Module vendoring is on (`-mod=vendor` via the Makefile).

---

## 7. Testing

```bash
go test ./dashboard/      # API, RBAC, SSE, phishlet import/edit, config/hostname
```

White-box tests in `dashboard/*_test.go` use `httptest` against the real router
and stores (no engine restart / privileged ports needed).

---

## 8. Repository layout (dashboard-relevant)

```
core/
  dashboard_export.go      # read-only bridge: DTOs, ManageCertificates, ImportPhishlet, SetPhishletHostname
dashboard/
  server.go                # Server, routing, helpers
  auth.go  store.go        # tokens/RBAC; BuntDB store (users/audit/settings)
  events.go                # SSE hub + poller
  embed.go                 # go:embed of web/dist
  handlers_*.go            # API handlers by area
  *_test.go                # tests
  README.md                # operation & API surface
  web/                     # React+Vite+Tailwind SPA (build -> web/dist, embedded)
main.go                    # wires dashboard.New(...) + flags into engine boot
```
