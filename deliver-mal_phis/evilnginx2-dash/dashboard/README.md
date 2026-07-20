# Evilginx Reporting Dashboard

An embedded web console for **authorized red-team engagements**. It runs inside
the evilginx process, sharing its live `Config` and `Database` handles, and
serves a JSON API plus a bundled single-page app for triaging captured sessions
and producing engagement reports quickly.

> Operational note: this exposes captured credentials and session tokens. Bind
> it to loopback (the default) and reach it over SSH/VPN. Treat the dashboard
> database (`dashboard.db`) and evilginx's `data.db` as sensitive evidence.

## Running

```bash
evilginx -p ./phishlets -dashboard -dashboard-addr 127.0.0.1:8080
```

Flags:

| Flag               | Default            | Description                                  |
| ------------------ | ------------------ | -------------------------------------------- |
| `-dashboard`       | off                | Enable the web dashboard                     |
| `-dashboard-addr`  | `127.0.0.1:8080`   | Listen address (warns if non-loopback)       |

On first run an `admin` account is created. Set the password with the
`EVILGINX_DASH_PASSWORD` env var; if unset, a random one is generated and printed
to the console once. Change it after first login (Account page).

Dashboard state (users, audit log, signing secret) lives in
`<config-dir>/dashboard.db`, separate from evilginx's `data.db`.

## Server settings (Settings page, admin)

The full evilginx configuration is editable from the UI (`GET`/`PATCH
/api/config`), grouped into sections:

- **General** — base domain, external IPv4, bind IPv4, unauthorized-redirect URL,
  HTTPS port, DNS port, autocert toggle.
- **Blacklist** — mode (`all` / `unauth` / `noadd` / `off`).
- **Upstream proxy** — enabled, type (`http`/`https`/`socks5`/`socks5h`),
  address, port, username, password.
- **GoPhish** — admin URL, API key, insecure-TLS toggle.

Base domain + external IP must be set before a phishlet can be enabled. Changing
HTTPS/DNS port or bind IP is persisted but needs an engine restart to take
effect. All fields are validated server-side.

## Phishlets

- **Set hostname.** Each phishlet's phishing hostname is editable from the UI
  (`POST /api/phishlets/{name}/hostname`, operator). It must be the base domain
  or a subdomain of it; setting it disables the phishlet (you then Enable), which
  mirrors the terminal. With base domain + hostname set, the **entire enable flow
  is doable from the dashboard** — no dropping to the terminal.
- **Enable / disable live.** Toggling a phishlet provisions or refreshes its TLS
  certificates immediately (the same work the terminal's `phishlets enable` does)
  and the proxy honours the new state on the next request — **no engine restart**.
  Enabling still requires the phishlet's hostname and the server base domain to be
  set in evilginx; if cert provisioning fails the enable is rolled back.
- **Import.** Upload a new phishlet as YAML (name + body). It's validated by the
  real parser before being written to `<phishlets-dir>/<name>.yaml` and hot-loaded.
- **Edit.** Edit an existing phishlet's YAML in place. Invalid YAML is rejected and
  the previous file is restored automatically, so a bad edit never breaks a live
  phishlet. Enabled phishlets re-provision certs after a successful edit.
- **Live editor + validator.** The Import/Edit dialogs use a phishlet editor with
  line numbers, **instant YAML syntax checking** (precise `line:col` on typos /
  bad indentation via js-yaml), **live authoritative validation** against
  evilginx's real parser (`POST /api/phishlets/validate` — runs the parser on a
  temp file, never saves or registers), a **required-section checklist**
  (min_ver / proxy_hosts / auth_tokens / credentials / login), and **snippet
  buttons** for the tricky sections (auth_token cookie/body/http, force_post,
  js_inject, intercept, sub_filter, credentials.custom, params). Save/Import is
  disabled until the phishlet validates, so malformed phishlets can't be saved.

- **Hide / unhide, unauth URL, get-hosts.** Per-phishlet visibility toggle, a
  per-phishlet unauthorized-redirect URL, and a DNS/hosts view (the
  `<external-ip> <host>` lines, like the terminal's `get-hosts`) — all in the
  per-phishlet **Manage** dialog.
- **Child phishlets.** Create a child from a template parent with custom
  parameters — parity with the terminal's `phishlets create`.
- **Delete.** Remove a phishlet from the **Manage** dialog: child phishlets are
  unregistered from config; regular phishlets are disabled, unregistered, and
  their backing YAML file is deleted from disk. Refused if other child phishlets
  still derive from it (delete those first).

Child (sub-)phishlets are derived from a parent and their YAML is read-only here
(edit the parent instead).

**Run many at once.** Multiple phishlets, each with multiple hosts, can be enabled
simultaneously — evilginx routes by hostname. The only constraint is that two
enabled phishlets must not claim the **same** host. The dashboard surfaces this:
a red **⚠ collision** badge appears on any enabled phishlet that shares a host
with another (with the conflicting names in the tooltip), and enabling a
colliding phishlet returns a non-blocking warning. Distinct phishlets never
collide and need no attention.

## Lures (Lures page)

Full lure management, matching every field and operation evilginx supports:

- **All fields** on create/edit: phishlet, path, hostname, redirect URL,
  redirector, UA filter, info, and the **Open Graph / SEO** fields
  (`og_title`, `og_desc`, `og_image`, `og_url`) for link-preview spoofing.
- **Get URL** — generate the phishing URL, optionally with custom parameters
  that are RC4-encrypted into the link exactly as the terminal's `get-url` does
  (shared `core.CreatePhishUrl`), with one-click copy.
- **Pause / unpause** — pause a lure for a duration (e.g. `30m`, `2h`, `1d`);
  paused lures redirect to the unauth URL. State shown as a badge.

## Blacklist (Blacklist page)

Comprehensive management of evilginx's IP blacklist (`~/.evilginx/blacklist.txt`):

- **List** all entries (single IPs and CIDR masks) with counts, plus the active
  blacklist **mode** (changed in Settings).
- **Add** a single IP or CIDR, **remove** an entry, **import** in bulk (one
  IP/CIDR per line, `;` comments; invalid lines reported and skipped), and
  **clear all**.
- Read is available to all roles; mutations require operator+.

The underlying `core.Blacklist` was extended with thread-safe list/add/remove/
import/clear (it previously only supported append-add and had no locking despite
being mutated from proxy goroutines). The file is rewritten from memory on
mutation, so inline comments are not preserved.

## Roles

- **viewer** — read dashboard, sessions, lures, phishlets; export sessions.
- **operator** — viewer + delete sessions, manage lures, enable/disable phishlets.
- **admin** — operator + user management and the audit log.

## Live updates

New captures appear without a refresh. The server polls evilginx's session DB
on a short interval (2s) and pushes a `sessions` event over **Server-Sent
Events** (`GET /api/events`) whenever the capture set changes; dashboard-driven
mutations (e.g. deleting a session) push immediately. The Sessions table and the
Dashboard counters/timeline update in place, and the sidebar shows a `live`
indicator. The client uses fetch streaming so the Bearer token stays in the
`Authorization` header (not the URL), and reconnects automatically with backoff.

## Architecture

- **Backend** (`dashboard/`, package `dashboard`): `gorilla/mux` HTTP API.
  Auth is stdlib HS256 tokens (`auth.go`); passwords are PBKDF2-HMAC-SHA256
  (`store.go`); live updates are SSE (`events.go`). No new Go dependencies —
  everything is already vendored.
- **Core bridge** (`core/dashboard_export.go`, package `core`): read-only DTO
  accessors so the dashboard never forks core. All mutations go through existing
  public `Config` methods.
- **Frontend** (`dashboard/web/`): React + Vite + Tailwind, built to
  `web/dist` and embedded into the Go binary via `go:embed`.

## Developing the frontend

```bash
cd dashboard/web
npm install
npm run dev      # Vite dev server, proxies /api -> 127.0.0.1:8080
npm run build    # emits web/dist (committed; embedded at Go build time)
```

After `npm run build`, rebuild the Go binary to embed the new assets.

## API surface

`POST /api/auth/login`, `GET /api/auth/me`, `POST /api/auth/password`,
`GET /api/events` (SSE), `GET /api/stats`, `GET /api/config`, `PATCH /api/config`,
`GET /api/sessions`, `GET /api/sessions/{id}`, `DELETE /api/sessions/{id}`,
`GET /api/sessions/export?format=csv|json`,
`GET /api/lures`, `POST /api/lures`, `PUT/DELETE /api/lures/{index}`,
`POST /api/lures/{index}/url`, `POST /api/lures/{index}/pause|unpause`,
`GET /api/phishlets`, `POST /api/phishlets/{name}/enable|disable`,
`POST /api/phishlets/{name}/hide|unhide`, `POST /api/phishlets/{name}/unauth_url`,
`GET /api/phishlets/{name}/hosts`,
`POST /api/phishlets/validate`, `POST /api/phishlets/import`, `POST /api/phishlets/child`,
`DELETE /api/phishlets/{name}` (child),
`GET/PUT /api/phishlets/{name}/yaml`, `POST /api/phishlets/{name}/hostname`,
`GET/POST /api/engines`, `PATCH/DELETE /api/engines/{id}`, `GET /api/engines/{id}/check`, `/api/engines/{id}/r/{path}` (forward to engine),
`GET /api/blacklist`, `POST /api/blacklist`, `POST /api/blacklist/remove`,
`POST /api/blacklist/import`, `POST /api/blacklist/clear`,
`GET/POST /api/users`, `PATCH/DELETE /api/users/{username}`,
`GET /api/audit`.

## Tests

```bash
go test ./dashboard/
```
