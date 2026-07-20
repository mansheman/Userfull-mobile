import { useEffect, useState } from 'react'
import { api } from '../api'
import { Card, Button, Input, Select, Badge, Spinner } from '../ui'

function Section({ title, desc, children, onSave, busy, msg, err }) {
  return (
    <Card className="mb-4 p-5">
      <div className="mb-4 flex items-start justify-between">
        <div>
          <h2 className="text-sm font-semibold text-[var(--text)]">{title}</h2>
          {desc && <p className="mt-0.5 text-xs text-[var(--text-dim)]">{desc}</p>}
        </div>
        <div className="flex items-center gap-3">
          {msg && <span className="text-xs text-[var(--accent)]">{msg}</span>}
          {err && <span className="text-xs text-[var(--danger)]">{err}</span>}
          <Button variant="primary" onClick={onSave} disabled={busy}>{busy ? 'Saving…' : 'Save'}</Button>
        </div>
      </div>
      <div className="space-y-3">{children}</div>
    </Card>
  )
}

function Field({ label, hint, children }) {
  return (
    <div className="grid grid-cols-3 items-center gap-4">
      <div>
        <div className="text-sm text-[var(--text-muted)]">{label}</div>
        {hint && <div className="text-xs text-[var(--text-dim)]">{hint}</div>}
      </div>
      <div className="col-span-2">{children}</div>
    </div>
  )
}

function Toggle({ checked, onChange }) {
  return (
    <label className="inline-flex cursor-pointer items-center gap-2 text-sm text-[var(--text-muted)]">
      <input type="checkbox" checked={!!checked} onChange={(e) => onChange(e.target.checked)} className="accent-[var(--accent)]" />
      {checked ? 'enabled' : 'disabled'}
    </label>
  )
}

export default function Settings() {
  const [cfg, setCfg] = useState(null)
  const [loadErr, setLoadErr] = useState('')
  // per-section form state
  const [general, setGeneral] = useState({})
  const [blacklist, setBlacklist] = useState({})
  const [proxy, setProxy] = useState({})
  const [gophish, setGophish] = useState({})
  // per-section status
  const [status, setStatus] = useState({})

  const load = () => api.config().then((c) => {
    setCfg(c)
    setGeneral({ ...c.general })
    setBlacklist({ ...c.blacklist })
    setProxy({ ...c.proxy })
    setGophish({ ...c.gophish })
  }).catch((e) => setLoadErr(e.message))

  useEffect(() => { load() }, [])

  async function save(section, patch) {
    setStatus((s) => ({ ...s, [section]: { busy: true } }))
    try {
      const updated = await api.updateConfig(patch)
      setCfg(updated)
      setStatus((s) => ({ ...s, [section]: { msg: 'Saved.' } }))
      setTimeout(() => setStatus((s) => ({ ...s, [section]: {} })), 2000)
    } catch (e) {
      setStatus((s) => ({ ...s, [section]: { err: e.message } }))
    }
  }

  if (loadErr) return <div className="rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{loadErr}</div>
  if (!cfg) return <Spinner />

  const st = (k) => status[k] || {}

  return (
    <div className="max-w-3xl">
      <h1 className="mb-1 text-2xl font-bold text-[var(--text)]">Server Settings</h1>
      <p className="mb-6 text-sm text-[var(--text-dim)]">Full evilginx configuration. evilginx {cfg.version}.</p>

      <Section title="General" desc="Base domain & external IP are required before enabling phishlets."
        onSave={() => save('general', { general })} busy={st('general').busy} msg={st('general').msg} err={st('general').err}>
        <Field label="Base domain" hint="Phishlet hostnames must be subdomains of this.">
          <Input value={general.domain || ''} onChange={(e) => setGeneral({ ...general, domain: e.target.value })} placeholder="example.com" />
        </Field>
        <Field label="External IPv4" hint="Public IP phishing hosts resolve to.">
          <Input value={general.external_ipv4 || ''} onChange={(e) => setGeneral({ ...general, external_ipv4: e.target.value })} placeholder="203.0.113.10" />
        </Field>
        <Field label="Bind IPv4" hint="Interface to bind to (restart required).">
          <Input value={general.bind_ipv4 || ''} onChange={(e) => setGeneral({ ...general, bind_ipv4: e.target.value })} placeholder="0.0.0.0" />
        </Field>
        <Field label="Unauthorized redirect">
          <Input value={general.unauth_url || ''} onChange={(e) => setGeneral({ ...general, unauth_url: e.target.value })} placeholder="https://example.org" />
        </Field>
        <Field label="HTTPS port" hint="Restart required.">
          <Input type="number" value={general.https_port ?? ''} onChange={(e) => setGeneral({ ...general, https_port: Number(e.target.value) })} className="w-32" />
        </Field>
        <Field label="DNS port" hint="Restart required.">
          <Input type="number" value={general.dns_port ?? ''} onChange={(e) => setGeneral({ ...general, dns_port: Number(e.target.value) })} className="w-32" />
        </Field>
        <Field label="Autocert" hint="Obtain Let's Encrypt certs automatically.">
          <Toggle checked={general.autocert} onChange={(v) => setGeneral({ ...general, autocert: v })} />
        </Field>
      </Section>

      <Section title="Blacklist" desc="How requests from blacklisted IPs are handled."
        onSave={() => save('blacklist', { blacklist })} busy={st('blacklist').busy} msg={st('blacklist').msg} err={st('blacklist').err}>
        <Field label="Mode">
          <Select value={blacklist.mode || ''} onChange={(e) => setBlacklist({ ...blacklist, mode: e.target.value })} className="w-48">
            {(cfg.blacklist_modes || []).map((m) => <option key={m} value={m}>{m}</option>)}
          </Select>
        </Field>
      </Section>

      <Section title="Upstream proxy" desc="Route evilginx's outbound traffic through a proxy."
        onSave={() => save('proxy', { proxy })} busy={st('proxy').busy} msg={st('proxy').msg} err={st('proxy').err}>
        <Field label="Enabled"><Toggle checked={proxy.enabled} onChange={(v) => setProxy({ ...proxy, enabled: v })} /></Field>
        <Field label="Type">
          <Select value={proxy.type || ''} onChange={(e) => setProxy({ ...proxy, type: e.target.value })} className="w-48">
            <option value="">—</option>
            {(cfg.proxy_types || []).map((t) => <option key={t} value={t}>{t}</option>)}
          </Select>
        </Field>
        <Field label="Address"><Input value={proxy.address || ''} onChange={(e) => setProxy({ ...proxy, address: e.target.value })} placeholder="127.0.0.1" /></Field>
        <Field label="Port"><Input type="number" value={proxy.port ?? ''} onChange={(e) => setProxy({ ...proxy, port: Number(e.target.value) })} className="w-32" /></Field>
        <Field label="Username"><Input value={proxy.username || ''} onChange={(e) => setProxy({ ...proxy, username: e.target.value })} /></Field>
        <Field label="Password"><Input type="password" value={proxy.password || ''} onChange={(e) => setProxy({ ...proxy, password: e.target.value })} /></Field>
      </Section>

      <Section title="GoPhish integration" desc="Report results back to a GoPhish campaign."
        onSave={() => save('gophish', { gophish })} busy={st('gophish').busy} msg={st('gophish').msg} err={st('gophish').err}>
        <Field label="Admin URL"><Input value={gophish.admin_url || ''} onChange={(e) => setGophish({ ...gophish, admin_url: e.target.value })} placeholder="https://gophish.local:3333" /></Field>
        <Field label="API key"><Input value={gophish.api_key || ''} onChange={(e) => setGophish({ ...gophish, api_key: e.target.value })} /></Field>
        <Field label="Insecure TLS" hint="Skip cert verification for GoPhish."><Toggle checked={gophish.insecure_tls} onChange={(v) => setGophish({ ...gophish, insecure_tls: v })} /></Field>
      </Section>

      <Card className="p-5 text-xs text-[var(--text-dim)]">
        <Badge color="yellow">note</Badge> Ports and bind address are written to config but require an engine restart to take effect.
      </Card>
    </div>
  )
}
