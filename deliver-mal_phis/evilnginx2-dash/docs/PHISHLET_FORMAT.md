# Phishlet File Format — Complete Reference (for the generator)

Extracted directly from the parser (`core/phishlet.go` → `LoadFromFile` and the
`Config*` structs). This is the source of truth for building a **phishlet
generator/wizard** in the dashboard. Every field below is exactly what evilginx
accepts; required/optional and defaults match the parser's validation.

`min_ver` must be **≥ 2.3.0** (older formats are rejected with a migration
message). Version is `major.minor.build`.

---

## Top-level keys

| Key | Required | Type | Notes |
| --- | --- | --- | --- |
| `min_ver` | ✅ | string | e.g. `'3.0.0'`. Must be ≥ 2.3.0. |
| `author` | ➖ | string | Freeform. |
| `redirect_url` | ➖ | string | Global post-auth redirect (lures can override). |
| `params` | ➖ | list | Template parameters — see **Templates** below. |
| `proxy_hosts` | ✅ | list | Must be non-empty. |
| `sub_filters` | ➖ | list | Content rewriting. |
| `auth_tokens` | ✅ | list | What marks a session "captured". |
| `auth_urls` | ➖ | [regex] | Visiting a matching URL marks session authenticated. |
| `credentials` | ✅ | object | `username` + `password` required; `custom` optional. |
| `force_post` | ➖ | list | Inject/override POST fields. |
| `login` | ✅ | object | `domain` + `path`. |
| `js_inject` | ➖ | list | Inject JS on matching pages. |
| `intercept` | ➖ | list | Return a canned response for a path. |
| `landing_path` | ➖ | [string] | Legacy; `login` is the modern replacement. |

---

## `proxy_hosts` (required, non-empty)

Maps phishing subdomains to the real target's subdomains.

| Field | Required | Type | Default | Notes |
| --- | --- | --- | --- | --- |
| `phish_sub` | ✅ | string | | Subdomain on YOUR phishing domain. |
| `orig_sub` | ✅ | string | | Subdomain on the TARGET's domain. |
| `domain` | ✅ | string | | Target's base domain. |
| `session` | ➖ | bool | false | Host that handles the session cookie. |
| `is_landing` | ➖ | bool | false | The host the lure URL points at. |
| `auto_filter` | ➖ | bool | true | Auto-rewrite this host in responses. |

Rules: if no host has `session: true`, the **first** host gets it; same for
`is_landing`. `orig_sub` may be empty (apex).

```yaml
proxy_hosts:
  - {phish_sub: 'login', orig_sub: 'login', domain: 'target.com', session: true, is_landing: true, auto_filter: true}
  - {phish_sub: 'account', orig_sub: 'account', domain: 'target.com', auto_filter: true}
```

---

## `auth_tokens` (required) — the "captured" trigger

A list; each item has a `type` (default `cookie`). Three variants:

**cookie** — capture session cookies:
| Field | Required | Notes |
| --- | --- | --- |
| `type` | ➖ | `cookie` (default) |
| `domain` | ✅ | cookie domain, e.g. `.target.com` |
| `keys` | ✅ | list of cookie names (regex-capable) |

**body** — capture a value from a response body:
| Field | Required | Notes |
| --- | --- | --- |
| `type` | ✅ | `body` |
| `domain` / `path` / `name` / `search` | ✅ | `search` is a regex with a capture group |

**http** — capture a value from a response header:
| Field | Required | Notes |
| --- | --- | --- |
| `type` | ✅ | `http` |
| `domain` / `path` / `name` / `header` | ✅ | |

```yaml
auth_tokens:
  - domain: '.target.com'
    keys: ['SID','SSID','HSID']
  - type: body
    domain: 'target.com'
    path: '/api/session'
    name: 'csrf'
    search: 'token":"([^"]+)"'
```

---

## `credentials` (required)

`username` and `password` are required `PostField`s; `custom` is an optional list
of `PostField`s (for extra fields like OTP, security answers).

**PostField:**
| Field | Required | Type | Default | Notes |
| --- | --- | --- | --- | --- |
| `key` | ✅ | regex | | Matches the form field name. |
| `search` | ✅ | regex | | Capture group extracts the value. |
| `type` | ➖ | `post`\|`json` | `post` | How the request body is parsed. |

```yaml
credentials:
  username: {key: 'username', search: '(.*)', type: 'post'}
  password: {key: 'password', search: '(.*)', type: 'post'}
  custom:
    - {key: 'otc', search: '(.*)', type: 'post'}
```

---

## `login` (required)

| Field | Required | Default | Notes |
| --- | --- | --- | --- |
| `domain` | ✅ | | Must equal one of the proxy_hosts' `orig_sub`+`domain`. |
| `path` | ✅ | `/` | Leading `/` enforced. |

---

## `sub_filters` (optional) — content rewriting

Rewrites response content (e.g. hardcoded URLs the auto-filter misses).

| Field | Required | Type | Notes |
| --- | --- | --- | --- |
| `triggers_on` | ✅ | string | Hostname whose responses to scan. |
| `orig_sub` | ✅ | string | Target subdomain to match. |
| `domain` | ✅ | string | Target domain to match. |
| `search` | ✅ | string/regex | What to find. |
| `replace` | ✅ | string | Replacement (use `{hostname}`-style tokens). |
| `mimes` | ✅ | [string] | e.g. `['text/html','application/json']`. |
| `redirect_only` | ➖ | bool | Only rewrite on redirects. |
| `with_params` | ➖ | [string] | Apply only when these custom params are set. |

---

## `js_inject` (optional) — inject JavaScript

| Field | Required | Notes |
| --- | --- | --- |
| `trigger_domains` | ✅ | list of hostnames |
| `trigger_paths` | ✅ | list of path regexes (auto-anchored `^…$`) |
| `trigger_params` | ➖ | list; ties injected JS to lure custom params |
| `script` | ✅ | the JS to inject |

`trigger_params` is how injected JS receives per-victim data passed via the lure
`get-url` parameters — a key "custom internal" mechanism.

---

## `force_post` (optional) — force/override POST fields

Force values into a POST request (e.g. disable "remember device", force a flag).

| Field | Required | Notes |
| --- | --- | --- |
| `path` | ✅ | regex of the request path |
| `type` | ✅ | only `post` is supported |
| `force` | ✅ | list of `{key, value}` to set |
| `search` | ➖ | list of `{key, search}` gates — only force when these match |

```yaml
force_post:
  - path: '/login'
    type: post
    search:
      - {key: 'remember', search: '.*'}
    force:
      - {key: 'remember', value: 'false'}
```

---

## `intercept` (optional) — canned responses

Return a fixed response for a path instead of proxying.

| Field | Required | Notes |
| --- | --- | --- |
| `domain` | ✅ | non-empty hostname |
| `path` | ✅ | regex |
| `http_status` | ✅ | int |
| `body` | ➖ | response body |
| `mime` | ➖ | content type |

---

## Templates & params — the "custom internal tools"

This is the part that's awkward by hand. A phishlet becomes a **template** when it
declares `params`. Templates can't be enabled directly — you create a **child**
from a template supplying parameter values (dashboard: Phishlets → Create child;
API: `POST /api/phishlets/child`).

**`params` items:**
| Field | Required | Notes |
| --- | --- | --- |
| `name` | ✅ | parameter name |
| `default` | ➖ | default value |
| `required` | ➖ | if true, a child must supply it |

**Substitution:** anywhere in the YAML, `{name}` is replaced with the param value
(`paramVal`) — but **only for child phishlets**, never while it's still a template.
So a template's literal YAML keeps `{name}` placeholders; the child resolves them.

```yaml
min_ver: '3.0.0'
params:
  - {name: 'target_domain', required: true}
  - {name: 'lure_path', default: '/login'}
proxy_hosts:
  - {phish_sub: 'login', orig_sub: 'login', domain: '{target_domain}', session: true, is_landing: true}
auth_tokens:
  - {domain: '.{target_domain}', keys: ['SID']}
credentials:
  username: {key: 'user', search: '(.*)', type: 'post'}
  password: {key: 'pass', search: '(.*)', type: 'post'}
login: {domain: 'login.{target_domain}', path: '{lure_path}'}
```

---

## Minimal valid skeleton

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

---

## Generator design notes (how to build it cheaply)

The dashboard already has the pieces a generator needs:

1. **Validation is free.** `POST /api/phishlets/import` (and `PUT …/yaml`) run the
   real parser (`NewPhishlet`) and return exact error messages. The generator can
   emit YAML and round-trip it through import for instant, authoritative
   validation — no new backend validation needed.
2. **A wizard, not a blank editor.** Steps that map 1:1 to sections:
   - Target domain(s) → `proxy_hosts` (auto-fill `phish_sub = orig_sub`, mark a
     session/landing host).
   - Login URL → `login.domain` + `login.path` (validate against proxy_hosts).
   - Credential field names → `credentials` (default `search: '(.*)'`, type post).
   - Session cookies → `auth_tokens` (cookie type). Offer body/http as advanced.
   - Advanced (collapsible): `sub_filters`, `force_post`, `js_inject`,
     `intercept`, `auth_urls`.
   - Templatize toggle → `params` + `{name}` placeholders.
3. **Emit YAML, then import.** The wizard serializes to YAML and calls import;
   show parser errors inline. Editing reuses the existing YAML editor.
4. **Capture-helper (future).** A "learn from a session" mode could inspect a
   captured `database.Session` (cookies/body/http tokens already stored) to
   suggest `auth_tokens` and credential field names automatically — the biggest
   real time-saver, and it reuses data we already collect.

### Field-source quick map (parser references)
- structs: `core/phishlet.go` `Config*` (lines ~136–235)
- validation/required: `LoadFromFile` (lines ~271–761)
- token types: `AUTH_TOKEN_TYPES = ["cookie","body","http"]`
- param substitution: `paramVal` (`{name}` → value, child only)
- credential types: `post` | `json` (default `post`)
- force_post type: `post` only
