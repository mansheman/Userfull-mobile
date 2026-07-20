# Deploying to a VPS behind Cloudflare DNS

Scenario: 1 VPS, domain `anugan.com` managed in Cloudflare, operating subdomain
`drilltest.anugan.com`.

> **The one trap that breaks everything: Cloudflare proxy must be OFF.** Evilginx
> terminates TLS and reverse-proxies the real site itself. If Cloudflare proxies
> the traffic (orange cloud ☁️), it terminates TLS at Cloudflare, blocks the
> ACME HTTP challenge, and mangles the MITM. **Every record below must be
> "DNS only" (grey cloud).**

---

## 1. Cloudflare DNS records

In the `anugan.com` zone, add (Proxy status = **DNS only / grey cloud** on all):

| Type | Name | Content | Proxy |
| --- | --- | --- | --- |
| A | `drilltest` | `<VPS_IP>` | DNS only |
| A | `*.drilltest` | `<VPS_IP>` | DNS only |

The wildcard `*.drilltest.anugan.com` covers single-level phishlet hosts (e.g.
`academy.drilltest.anugan.com`). If a phishlet needs a **multi-level** host
(e.g. `www.login.drilltest.anugan.com`), add that exact A record too — use the
dashboard's **Phishlets → Manage → Load hosts** (or `phishlets get-hosts`) to see
the precise hostnames a phishlet requires, then add a grey-cloud A record for
each.

Evilginx's base domain will be `drilltest.anugan.com`, so phishlet/lure hostnames
are subdomains of it. You do **not** need to delegate nameservers to the VPS with
this wildcard-A-record approach; evilginx's built-in `:53` server is unused (its
startup warning if it can't bind 53 is harmless here).

---

## 2. Build (on your Mac) and copy to the VPS

```bash
./deploy/build-linux.sh                      # -> deploy/out/{evilginx,phishlets,redirectors}
ssh root@<VPS_IP> 'mkdir -p /opt/evilginx'
scp -r deploy/out/* root@<VPS_IP>:/opt/evilginx/
```

The binary is static (CGO off) with the dashboard embedded — nothing else to
install on the VPS.

---

## 3. Firewall (VPS)

Open only what's needed; keep the dashboard private.

```bash
ufw allow 22/tcp        # SSH (for the dashboard tunnel)
ufw allow 80/tcp        # ACME HTTP-01 + redirect
ufw allow 443/tcp       # evilginx HTTPS
ufw enable
# Do NOT open 8080 — the dashboard stays on loopback.
```

---

## 4. systemd service

```bash
scp deploy/evilginx.service root@<VPS_IP>:/etc/systemd/system/evilginx.service
ssh root@<VPS_IP>
# edit the unit: set EVILGINX_DASH_PASSWORD to a strong value
nano /etc/systemd/system/evilginx.service
systemctl daemon-reload
systemctl enable --now evilginx
journalctl -u evilginx -f          # watch startup; note the dashboard admin line
```

---

## 5. Configure the engagement (via the dashboard)

Open an **SSH tunnel** from your laptop (the dashboard is loopback-only):

```bash
ssh -N -L 8080:127.0.0.1:8080 root@<VPS_IP>
# then browse http://127.0.0.1:8080  (log in as admin / your password)
```

In the dashboard:
1. **Settings** → Base domain `drilltest.anugan.com`, External IPv4 `<VPS_IP>`, save.
2. **Phishlets** → pick a phishlet → **Manage** → set **Hostname**
   (e.g. `drilltest.anugan.com` or a subdomain), then **Load hosts** and make sure
   each listed host has a grey-cloud A record in Cloudflare.
3. Click **Enable** — evilginx provisions Let's Encrypt certs live (needs 80/443
   reachable + DNS resolving; first issuance can take up to ~60s).
4. **Lures** → create a lure, **Get URL** (optionally with params) to get the link.

CLI equivalent if you prefer the terminal: `config domain drilltest.anugan.com`,
`config ipv4 external <VPS_IP>`, `phishlets hostname <name> drilltest.anugan.com`,
`phishlets enable <name>`, `lures create <name>`, `lures get-url 0`.

---

## 6. Verify

```bash
# certs issued and TLS served by evilginx (not Cloudflare):
curl -vI https://<phishlet-host>.drilltest.anugan.com 2>&1 | grep -i "issuer\|HTTP/"
# DNS resolves to the VPS (grey cloud), not a Cloudflare IP:
dig +short academy.drilltest.anugan.com
```

If `dig` returns Cloudflare IPs (104.x / 172.67.x), the record is still proxied —
switch it to DNS only.

---

## 7. Dashboard over Cloudflare (`sambat.anugan.com`)

> **Opposite rule from the engine.** The engine records MUST be grey-cloud. The
> dashboard is a normal web app, so you *want* it fronted by Cloudflare — but via
> a **Tunnel + Access**, not a bare proxied A record. A Tunnel keeps the dashboard
> on loopback (no inbound port), hides the origin IP, terminates TLS at the edge,
> and lets **Cloudflare Access** gate it with identity before any request reaches
> the app. This control plane holds captured sessions + full fleet control — never
> put it on the public internet behind only a password.

This assumes the dashboard runs on this VPS (all-in-one: the evilginx unit has
`-dashboard -dashboard-addr 127.0.0.1:8080`). For a split control-plane host, run
these steps there instead.

### 7.1 Install cloudflared + create the tunnel (on the VPS)

```bash
curl -L https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64 \
  -o /usr/local/bin/cloudflared && chmod +x /usr/local/bin/cloudflared
cloudflared tunnel login                     # browser auth -> pick the anugan.com zone
cloudflared tunnel create evilginx-dash      # prints a tunnel UUID + creds json
```

### 7.2 Map the hostname to the loopback dashboard

`/etc/cloudflared/config.yml`:

```yaml
tunnel: <TUNNEL-UUID>
credentials-file: /root/.cloudflared/<TUNNEL-UUID>.json
ingress:
  - hostname: sambat.anugan.com
    service: http://127.0.0.1:8080
  - service: http_status:404
```

Create the (auto-proxied) DNS route and run it as a service:

```bash
cloudflared tunnel route dns evilginx-dash sambat.anugan.com   # makes the CNAME for you
cloudflared service install
systemctl enable --now cloudflared
```

Do **not** add an A record by hand and do **not** open 8080 in `ufw` — the Tunnel
is the only path in.

### 7.3 Gate it with Cloudflare Access (before testing publicly)

Cloudflare dashboard -> **Zero Trust -> Access -> Applications -> Add ->
Self-hosted**:

- Application domain: `sambat.anugan.com`
- Policy: **Action = Allow**, **Include -> Emails -> `<operator-email>`**
- Identity method: **One-time PIN** (email OTP — no IdP needed)

Now Cloudflare challenges for your email *before* the dashboard login loads — two
layers (Access OTP, then the dashboard's own JWT login).

### 7.4 Verify

```bash
ss -ltnp | grep 8080            # 127.0.0.1:8080 only, NOT 0.0.0.0
dig +short sambat.anugan.com    # Cloudflare IPs (proxied) — origin not revealed
```

Then browse `https://sambat.anugan.com` -> Access OTP -> dashboard login. The SSH
tunnel from §5 still works as a fallback and is unaffected.

---

## 8. Session IP geolocation (optional)

The dashboard can show each session's **country / city / ASN** next to the IP
(and add `country,city,asn` columns to the CSV export). It reads a local MaxMind
**GeoLite2** `.mmdb` — fully offline, no victim IP ever leaves the box, no new
dependency. The feature is off until a DB file is present.

1. Get the free databases (MaxMind account → "Download Files"):
   - `GeoLite2-City.mmdb` (country + city + lat/long) — or `GeoLite2-Country.mmdb`
   - `GeoLite2-ASN.mmdb` (optional, for the AS number/org)

2. Drop them in the engine's config dir (auto-detected by exact filename):

   ```bash
   scp GeoLite2-City.mmdb GeoLite2-ASN.mmdb sysadmin@<VPS_IP>:/tmp/
   ssh sysadmin@<VPS_IP> 'sudo mv /tmp/GeoLite2-*.mmdb /opt/evilginx/.evilginx/'
   sudo systemctl restart evilginx
   ```

   The startup log should show `geoip: loaded city/country database: …`. Geo then
   appears in **Sessions** (flag + city/country, ASN on hover) and the detail view.
   A custom path can be forced with `-geoip-db /path/...mmdb` /
   `-geoip-asn-db /path/...mmdb` in the unit's `ExecStart` instead.

Notes:
- Country-level still works fine if you only ship `GeoLite2-Country.mmdb`.
- A missing or corrupt `.mmdb` just disables the feature — it never breaks the
  dashboard (the reader is panic-safe and degrades to "no location").
- The DB files are **not** in the repo (MaxMind license + size); provision them
  per box. Re-download periodically to keep them current.
- Private/loopback IPs return no location — relevant if you later front the engine
  with nginx (see `docs/NGINX_TOPOLOGY.md`): without PROXY-protocol the engine
  sees `127.0.0.1` and geo (like the blacklist) shows nothing useful.

---

## Notes / gotchas

- **Never run `-developer` on the VPS** — that's self-signed certs for local
  testing only. Production uses autocert (the default).
- **Dashboard stays private.** Reach it via the SSH tunnel (§5) or the Cloudflare
  Tunnel + Access setup (§7). Never bind it to `0.0.0.0`, open 8080, or front it
  with a bare proxied A record (no auth gate).
- **Let's Encrypt rate limits**: test with one phishlet first; repeated
  enable/disable churn can hit issuance limits.
- **Cloudflare Universal SSL / Always Use HTTPS / WAF** don't apply to grey-cloud
  records (traffic bypasses Cloudflare), so no extra config needed there.
- The captured data (`/opt/evilginx/.evilginx/data.db`, `dashboard.db`) is
  sensitive engagement evidence — restrict the VPS and back it up deliberately.
```
