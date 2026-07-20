# Evilginx2 + Dashboard

**Evilginx** adalah man-in-the-middle attack framework untuk phishing login credentials beserta session cookies, memungkinkan bypass 2-factor authentication.

Fork ini menambahkan **Web Reporting Dashboard** (embedded React SPA) untuk manajemen kampanye tanpa perlu interactive terminal.

> **Peringatan:** Hanya untuk penetration testing authorized. Penggunaan untuk tujuan ilegal adalah melanggar hukum.

---

## Fitur

- **MITM reverse proxy** — single binary, tanpa dependency eksternal
- **Built-in DNS nameserver** — handle DNS resolution untuk domain phising
- **Automatic TLS** — Let's Encrypt certificates via certmagic (auto-renew)
- **Phishlet system** — YAML-based target definitions
- **Lures** — Custom phishing URLs dengan OpenGraph, redirectors, UA filter
- **IP Blacklist** — Auto/manual blocking dengan multiple modes
- **IP Geolocation** — MaxMind GeoLite2 integration (negara, kota, ASN)
- **Gophish integration** — Kirim phishing emails via API
- **Web Dashboard** — Full CRUD phishlets, lures, sessions export
- **RBAC** — viewer / operator / admin roles
- **Live SSE updates** — Captures muncul real-time tanpa refresh
- **Agent API** — Fleet management (satu dashboard manage banyak engine)
- **Headless mode** — Run as systemd service tanpa terminal
- **Child phishlets** — Template-based parameterized phishlets
- **PROXY protocol** — Behind nginx L4 SNI pass-through

---

## Quick Start

### Prasyarat

- Go 1.22+
- Node.js 25.x + npm 11.x (hanya untuk rebuild frontend)

### Build & Run

```bash
# 1. Build backend
make build

# 2. Run developer mode (self-signed certs, no dashboard)
./build/evilginx -p ./phishlets -t ./redirectors -developer

# 3. With dashboard
EVILGINX_DASH_PASSWORD=Pass123 ./build/evilginx -p ./phishlets -developer -dashboard
# Browse: http://127.0.0.1:8080  (user: admin / pass: Pass123)
```

### Setup Cepat Dashboard

1. Set **Settings → Base domain** (e.g. `phish.domain.com`)
2. Set **Settings → External IPv4** (VPS public IP)
3. **Phishlets → Manage → Hostname** (e.g. `phish.domain.com`)
4. **Phishlets → Enable** (provisions TLS certs via Let's Encrypt)
5. **Lures → Create → Get URL** → kirim ke target

---

## Perintah Terminal (tanpa dashboard)

| Perintah | Fungsi |
|----------|--------|
| `config domain <domain>` | Set base domain phising |
| `config ipv4 external <ip>` | Set external IP |
| `phishlets hostname <name> <host>` | Set phishlet hostname |
| `phishlets enable <name>` | Enable phishlet + provision cert |
| `lures create <name>` | Create lure |
| `lures get-url <id>` | Generate phishing URL |
| `sessions` | List captured sessions |
| `blacklist` | Manage IP blacklist |
| `proxy` | Configure upstream SOCKS5/HTTP proxy |

---

## Deployment VPS

```bash
# Build untuk linux
./deploy/build-linux.sh

# Deploy ke VPS (dengan systemd service)
./deploy/push.sh root@<VPS_IP>

# SSH tunnel untuk akses dashboard aman
ssh -N -L 8080:127.0.0.1:8080 root@<VPS_IP>
# Buka http://127.0.0.1:8080
```

### Firewall (VPS)

```bash
ufw allow 22/tcp
ufw allow 80/tcp        # ACME HTTP-01
ufw allow 443/tcp       # Evilginx HTTPS
# Jangan buka 8080 — dashboard loopback-only
```

### Cloudflare DNS

- **DNS only (grey cloud)** — jangan proxy (orange cloud breaks TLS)
- Wildcard A record: `*.drilltest.domain.com` → VPS_IP

### Dashboard via Cloudflare Tunnel (opsional)

```bash
cloudflared tunnel create evilginx-dash
# config.yml: service http://127.0.0.1:8080
cloudflared tunnel route dns evilginx-dash dash.domain.com
# + Cloudflare Access (OTP) untuk auth layer tambahan
```

---

## Arsitektur

```
                        ┌──────────────────────┐
                        │    evilginx process   │
                        │                        │
  victim browser ──►  :443  MITM HTTPS Proxy    │
                          │                      │
                     ┌────▼─────┐                │
                     │  Target  │                │
                     │  Website │                │
                     └──────────┘                │
                        │                        │
  operator ──► :8080  Dashboard API + SPA       │
                        │                        │
                     ┌──▼───┐                    │
                     │ BuntDB │                   │
                     │ (sessions)                 │
                     └───────┘                    │
┌──────────────────────────────────────────────────┐
```

### Data Flow

1. **Victim** mengklik lure URL → DNS resolve ke VPS
2. **Evilginx** terminate TLS, proxy request ke target asli
3. **Victim** login di halaman phising — credentials + cookies dicapture
4. **Attacker** replay session cookie dari IP berbeda → bypass MFA
5. **Dashboard** menampilkan captured sessions real-time via SSE

### Storage

| File | Isi |
|------|-----|
| `~/.evilginx/config.json` | Server & phishlet config |
| `~/.evilginx/data.db` | Captured sessions |
| `~/.evilginx/dashboard.db` | Users, audit log |
| `~/.evilginx/blacklist.txt` | IP blacklist |
| `phishlets/*.yaml` | Phishlet definitions |

---

## Phishlet System

Phishlet adalah file YAML yang mendefinisikan bagaimana evilginx meniru target website.

### Fields

| Field | Required | Deskripsi |
|-------|----------|-----------|
| `min_ver` | ✅ | Minimal evilginx version (>= 2.3.0) |
| `proxy_hosts` | ✅ | Map subdomain phising → target |
| `auth_tokens` | ✅ | Cookie/body/header yang dicapture |
| `credentials` | ✅ | Form field mapping |
| `login` | ✅ | Login page domain + path |
| `sub_filters` | ➖ | Content rewriting rules |
| `js_inject` | ➖ | Custom JavaScript injection |
| `force_post` | ➖ | Force POST field values |
| `intercept` | ➖ | Canned responses |
| `params` | ➖ | Template params untuk child phishlets |

### Token Capture Types

- **cookie**: Session cookies dari response Set-Cookie
- **body**: Nilai dari response body (regex capture group)
- **http**: Nilai dari response headers

### Child Phishlets (Templates)

Phishlet dengan `params` menjadi template. Child phishlet dibuat dengan nilai parameter spesifik, melakukan substitusi `{param_name}` di seluruh YAML.

---

## Dashboard API

### Authentication

- Login: `POST /api/auth/login` → `{"token":"<HS256-JWT>"}`
- Semua endpoint (kecuali `/api/auth/login`, `/api/health`) memerlukan `Authorization: Bearer <token>`

### Roles

| Role | Hak Akses |
|------|-----------|
| `viewer` | Read dashboard, sessions, lures, phishlets |
| `operator` | Viewer + manage lures/phishlets, delete sessions, blacklist |
| `admin` | Operator + user management, audit log |

### Key Endpoints

| Method | Path | Role | Fungsi |
|--------|------|------|--------|
| GET | `/api/stats` | any | Dashboard counters |
| GET | `/api/sessions` | any | List captured sessions |
| GET | `/api/sessions/export?format=csv\|json` | any | Export sessions |
| DELETE | `/api/sessions/{id}` | operator | Delete session |
| GET | `/api/phishlets` | any | List phishlets |
| POST | `/api/phishlets/{name}/enable` | operator | Enable phishlet |
| POST | `/api/phishlets/{name}/disable` | operator | Disable phishlet |
| POST | `/api/phishlets/import` | operator | Import phishlet YAML |
| POST | `/api/phishlets/validate` | operator | Validate YAML |
| PUT | `/api/phishlets/{name}/yaml` | operator | Edit phishlet YAML |
| POST | `/api/phishlets/{name}/hostname` | operator | Set hostname |
| GET | `/api/lures` | any | List lures |
| POST | `/api/lures` | operator | Create lure |
| POST | `/api/lures/{id}/url` | any | Generate phishing URL |
| GET/PATCH | `/api/config` | any/admin | Read/update config |
| POST | `/api/blacklist/import` | operator | Bulk import IPs |
| GET/POST | `/api/users` | admin | User management |
| GET | `/api/audit` | admin | Audit log |
| GET | `/api/events` | any | SSE live updates |

---

## Fleet Architecture (Multi-Engine)

Satu dashboard control plane mengelola multiple engine di VPS berbeda via WireGuard:

```
Dashboard Host (control plane)       VPS A (engine)
  evilginx-dash                        evilginx + agent API
  :8080 (loopback)                     :8081 (WG interface)
       │                                    │
       └──── WireGuard ──── agent API ──────┘
                          token auth
```

- Engine: `-agent -agent-addr <wg-ip>:8081 -agent-token <secret>`
- Dashboard: register engines via UI, proxy management, mirror sessions
- Mirror store: durable copy data dari semua engines

---

## nginx di Depan Engine

Evilginx memerlukan **L4 SNI pass-through** (bukan L7 reverse proxy) karena:
- Engine terminates TLS sendiri (per-host certificate)
- Engine membaca real client IP dari koneksi TCP

### Konfigurasi nginx

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
        proxy_protocol on;        # penting: bawa real client IP
    }
}
```

Engine config: `config ipv4 bind 127.0.0.1`, `config https_port 8443`

PROXY protocol diperlukan agar engine melihat real victim IP.

---

## GeoIP Integration

1. Download GeoLite2 dari MaxMind
2. Letakkan di `~/.evilginx/GeoLite2-City.mmdb`
3. Engine auto-detect saat startup
4. Sessions tampilkan country + city + ASN

Atau via flag: `-geoip-db /path/GeoLite2-City.mmdb -geoip-asn-db /path/GeoLite2-ASN.mmdb`

---

## AiTM Detection (untuk Purple Team)

7 Elasticsearch detection rules untuk mendeteksi evilginx:

1. **Session cookie dari multiple source IPs** — signal utama AiTM
2. **Session dari dua ASN berbeda** — lower false positives
3. **Auth sukses dari hosting/VPS ASN** — proxy terdeteksi
4. **Satu IP auth ke banyak user** — proxy fan-in
5. **User-agent berubah dalam session** — replay detection
6. **DNS lookalike domain** — phishing domain visit
7. **Post-auth persistence** — password/email change setelah login

Lihat `docs/AITM_DETECTION_ELASTIC.md` untuk detail lengkap.

---

## Troubleshooting

| Masalah | Penyebab | Solusi |
|---------|----------|--------|
| TLS cert gagal | Cloudflare proxy (orange cloud) | Set DNS only |
| Phishlet enable gagal | Domain/hostname/external IP kosong | Isi di Settings & Manage |
| Dashboard lupa password | - | Hapus `~/.evilginx/dashboard.db`, restart |
| `127.0.0.1` di sessions | Behind nginx tanpa PROXY protocol | Set `proxy_protocol on` |
| Port 53 bind error | Non-root atau port terpakai | Bisa diabaikan (pake A record) |
| Let's Encrypt rate limit | Enable/disable berulang | Test dengan 1 phishlet |

---

## Development

### Backend Tests

```bash
go test ./dashboard/
```

### Frontend Dev

```bash
cd dashboard/web
npm install
npm run dev    # Vite dev server, proxy /api ke 127.0.0.1:8080
npm run build  # Build ke dist/, lalu rebuild Go binary
```

### Build Commands

```bash
make build       # Go build
make clean       # Clean build artifacts
```

### Windows

```bash
build.bat        # Build evilginx.exe
build_run.bat    # Build + run developer mode
```

### Cross-compile

```bash
./deploy/build-linux.sh  # linux/amd64 static binary
```

---

## Referensi

- [Evilginx Official](https://evilginx.com)
- [Evilginx Pro](https://evilginx.com)
- [Gophish Integration](https://github.com/kgretzky/gophish/)
- [Official Documentation](https://help.evilginx.com)
- [Evilginx Mastery Course](https://academy.breakdev.org/evilginx-mastery)

### Write-ups

- [Evilginx 3.0](https://breakdev.org/evilginx-3-0-evilginx-mastery/)
- [Evilginx 3.3 - GoPhish](https://breakdev.org/evilginx-3-3-go-phish/)
- [Evilginx 2.0 - Release](https://breakdev.org/evilginx-2-next-generation-of-phishing-2fa-tokens)

---

## Sumber Daya dalam Repository

| File | Deskripsi |
|------|-----------|
| `TECH_STACK.md` | Tech stack detail |
| `CHANGELOG` | Riwayat perubahan |
| `dashboard/README.md` | Dashboard operation & API |
| `deploy/DEPLOY.md` | Deploy guide ke VPS |
| `docs/PHISHLET_FORMAT.md` | Phishlet YAML reference |
| `docs/FLEET_ARCHITECTURE.md` | Fleet multi-engine |
| `docs/NGINX_TOPOLOGY.md` | nginx L4 topology |
| `docs/AITM_DETECTION_ELASTIC.md` | Detection playbook |
| `skills.md` | Build & operations skill |

---

## Lisensi

BSD-3 License. Dibuat oleh Kuba Gretzky ([@mrgretzky](https://twitter.com/mrgretzky)).
