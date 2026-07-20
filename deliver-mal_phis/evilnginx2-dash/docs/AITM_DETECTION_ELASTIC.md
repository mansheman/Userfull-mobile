# AiTM (evilginx) Detection Gap Playbook — Internal App + Elasticsearch

Purple-team deliverable: run a realistic evilginx/adversary-in-the-middle (AiTM)
attack against an internal application, find what the SOC **doesn't** see, and
close each gap with an Elastic detection. The goal is detection coverage — every
"blind spot" below is paired with the telemetry + rule that lights it up.

> Scope: the target is an in-house web app (its own auth/session), logs shipped to
> Elasticsearch (Filebeat/Elastic Agent, ECS fields). Validate each rule by running
> the engagement against a **test account** and confirming it fires.

---

## 1. Why an internal app is *more* detectable than people think

In an evilginx AiTM, the victim's browser talks to the **proxy**, and the proxy
talks to your app. So from the app's point of view:

- The victim's "real" traffic arrives from the **evilginx server IP** (a VPS /
  hosting ASN), not the victim's normal network.
- After capture, the **attacker replays the stolen session cookie from their own
  IP** — so the *same session ID appears from two different source IPs*.
- MFA is bypassed because the cookie is already authenticated — there's **no fresh
  interactive auth** for the replayed session.

You own the access logs, so all three are observable. Most SOCs just aren't
looking — that's the gap.

---

## 2. Telemetry you need (ECS mapping)

Ship app reverse-proxy / application access logs with at least these ECS fields.
If your app logs a session identifier, map it to a keyword field — this is the
linchpin for the best detection.

| Signal | ECS field | Notes |
|---|---|---|
| Source IP | `source.ip` | true client IP (mind `X-Forwarded-For` from your edge) |
| User | `user.name` | authenticated principal |
| Session ID | `session.id` (custom keyword) | hash of the session cookie / app session id — **required for Rule 1** |
| User agent | `user_agent.original` (+ parsed `user_agent.*`) | |
| URL | `url.domain`, `url.path` | |
| Method / status | `http.request.method`, `http.response.status_code` | |
| Auth outcome | `event.action` / `event.outcome` | e.g. `authentication`/`success` |
| GeoIP / ASN | `source.geo.*`, `source.as.number`, `source.as.organization` | enrich with the GeoIP + ASN ingest processors |
| TLS JA3 (if you have Packetbeat/Zeek) | `tls.client.ja3` | network-layer evilginx fingerprint |

> Enrich `source.ip` with the **geoip** and **as** ingest processors so you get
> `source.as.organization` (hosting/VPS ASN) and `source.geo.country_name`.

---

## 3. The detections (Elastic)

Each is a Kibana **Security → Detection rule**. KQL is given for query/threshold
rules; raw Elasticsearch DSL is provided where the logic needs an aggregation.
Adjust index pattern (`logs-app-*`) and field names to your environment.

### Rule 1 — Session cookie used from multiple source IPs (the core AiTM signal)
**What the SOC misses:** nobody correlates a session ID across requests, so a
cookie replayed from a new IP looks like normal traffic.

**Rule type:** Threshold
**Index:** `logs-app-*`
**Query (KQL):**
```
event.category:"authentication" or url.path:* and session.id:*
```
**Threshold:**
- Group by: `session.id`
- Cardinality: `source.ip` **>= 2**
- Window / interval: 30m (tune to your session length)

**Equivalent raw aggregation** (for a watcher / transform / validation):
```json
POST logs-app-*/_search
{
  "size": 0,
  "query": { "range": { "@timestamp": { "gte": "now-30m" } } },
  "aggs": {
    "by_session": {
      "terms": { "field": "session.id", "size": 10000 },
      "aggs": {
        "distinct_ips": { "cardinality": { "field": "source.ip" } },
        "ips": { "terms": { "field": "source.ip", "size": 10 } },
        "aitm": { "bucket_selector": {
          "buckets_path": { "n": "distinct_ips" },
          "script": "params.n >= 2"
        }}
      }
    }
  }
}
```
A session legitimately roaming (mobile↔wifi) can also trip this — tune by adding
`source.as.number` cardinality, or excluding same-ASN/same-/24 changes (see Rule 1b).

### Rule 1b — Session used from two different ASNs (lower false positives)
Same as Rule 1 but **Cardinality: `source.as.number` >= 2**. A session that hops
between a hosting ASN (the proxy) and a residential ISP ASN (the attacker, or
victim+attacker) is far more suspicious than a same-ISP IP change.

### Rule 2 — Successful auth from a hosting/VPS ASN
**What the SOC misses:** internal users normally come from corp/residential ranges;
the proxy sits in a datacenter.

**Rule type:** Custom query
**Query (KQL):**
```
event.category:"authentication" and event.outcome:"success"
and source.as.organization:(*amazon* or *digitalocean* or *hetzner* or *ovh* or *linode* or *vultr* or *contabo* or *google* or *microsoft* or *choopa* or *m247*)
and not source.ip:(10.0.0.0/8 or 172.16.0.0/12 or 192.168.0.0/16)
```
Better: maintain a hosting-ASN list as an enrichment/lookup and match on it.

### Rule 3 — One source IP authenticating to many distinct users (proxy fan-in)
**What the SOC misses:** a single proxy harvesting many victims shows up as one IP
authenticating as many accounts.

**Rule type:** Threshold
**Query (KQL):** `event.category:"authentication" and event.outcome:"success"`
**Threshold:**
- Group by: `source.ip`
- Cardinality: `user.name` **>= 5** (tune)
- Window: 1h

### Rule 4 — User-agent change within a single session
**What the SOC misses:** the victim's browser UA (via proxy) vs the attacker's
replay UA differ on the same session.

**Rule type:** EQL
```
sequence by session.id with maxspan=1h
  [ any where user_agent.original != null ]
  [ any where user_agent.original != null ]
```
Then alert when the two UAs in the sequence differ — or use the aggregation form:
group by `session.id`, `cardinality(user_agent.original) >= 2`.

### Rule 5 — Employees resolving/visiting a lookalike phishing domain
**What the SOC misses:** corp DNS / web-proxy logs show users hitting the external
lookalike domain *before* they're phished.

**Rule type:** Custom query over DNS/proxy logs (`logs-dns-*`, `logs-proxy-*`)
**Query (KQL):**
```
dns.question.name:(*yourbrand* or *yourapp*) and not dns.question.name:(*.yourdomain.com)
```
Pair with a **New Terms** rule on `dns.question.name` (first-seen domains) and/or a
newly-registered-domain enrichment feed for brand keywords + homoglyphs.

### Rule 6 — (network) evilginx Go client JA3
**What the SOC misses:** the proxy's TLS client fingerprint on egress to the app.
If you run Packetbeat/Zeek with JA3:
```
tls.client.ja3:("<observed-evilginx-ja3>")
```
Capture the JA3 during your own test run, then alert on it. (JA3 changes across
Go versions — derive it from your engagement, don't hardcode blindly.)

### Rule 7 — Post-auth persistence in the app
**What the SOC misses:** right after an AiTM login, the attacker often changes
email/password, adds an API token, or registers a new device. Add detections on
your app's sensitive actions correlated to a recent suspicious session:
```
event.action:("password_change" or "email_change" or "api_token_created" or "mfa_method_added")
```
Prioritize when the acting `session.id` also matched Rule 1/1b.

---

## 4. Purple-team validation plan

1. Stand up the engagement infra (authorized, scoped) and a **test user**.
2. Phish the test user through evilginx; capture the session.
3. Replay the captured cookie from a **different IP** (your attacker box).
4. Confirm in Kibana which rules fired:
   - Rule 1/1b should fire on the session reuse (proxy IP → attacker IP).
   - Rule 2 on the datacenter-ASN login.
   - Rule 4 if the replay UA differs.
   - Rule 5 if corp DNS/proxy saw the domain.
5. For anything that **didn't** fire → that's a confirmed gap. Note missing
   telemetry (e.g., `session.id` not logged) and fix the pipeline, then re-test.
6. Deliverable to the SOC: the rules above (tuned), the telemetry gaps closed, and
   a before/after of detection coverage across the kill chain.

---

## 5. Coverage summary (close the loop)

| Kill-chain stage | Detection | Primary gap closed |
|---|---|---|
| Domain recon/visit | Rule 5 + NRD/CT feeds | No lookalike-domain visibility |
| Proxy TLS | Rule 6 (JA3) | No egress TLS fingerprinting |
| Auth at app | Rule 2, Rule 3 | No ASN / fan-in analytics |
| **Token capture & reuse** | **Rule 1 / 1b** | **No session-ID-to-source-IP correlation (the big one)** |
| Session anomaly | Rule 4 | No per-session UA tracking |
| Post-auth | Rule 7 | Persistence actions not tied to risky sessions |

The headline: instrument `session.id` and run **Rule 1b**. That converts the
attack's defining behavior — one session, two networks — from invisible into a
high-fidelity alert.
```
