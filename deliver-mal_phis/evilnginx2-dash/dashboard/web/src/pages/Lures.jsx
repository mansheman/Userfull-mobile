import { useEffect, useState } from 'react'
import { api } from '../api'
import { useAuth } from '../auth'
import { Card, Button, Input, Select, Badge, Modal, Spinner } from '../ui'

function CopyButton({ text, label = 'Copy' }) {
  const [copied, setCopied] = useState(false)
  return (
    <Button variant="ghost" onClick={async () => { try { await navigator.clipboard.writeText(text); setCopied(true); setTimeout(() => setCopied(false), 1500) } catch {} }}>
      {copied ? '✓ Copied' : label}
    </Button>
  )
}

function Row({ label, children }) {
  return (
    <div>
      <label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">{label}</label>
      {children}
    </div>
  )
}

function LureForm({ initial, phishlets, onSave, onClose, isEdit }) {
  const [form, setForm] = useState({
    phishlet: initial?.phishlet || (phishlets[0]?.name ?? ''),
    path: initial?.path || '',
    hostname: initial?.hostname || '',
    redirect_url: initial?.redirect_url || '',
    redirector: initial?.redirector || '',
    ua_filter: initial?.ua_filter || '',
    info: initial?.info || '',
    og_title: initial?.og_title || '',
    og_desc: initial?.og_desc || '',
    og_image: initial?.og_image || '',
    og_url: initial?.og_url || '',
  })
  const [showOg, setShowOg] = useState(!!(initial?.og_title || initial?.og_desc || initial?.og_image || initial?.og_url))
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)
  const set = (k) => (e) => setForm({ ...form, [k]: e.target.value })

  async function submit(e) {
    e.preventDefault()
    setBusy(true); setErr('')
    try { await onSave(form) } catch (er) { setErr(er.message) } finally { setBusy(false) }
  }

  return (
    <form onSubmit={submit} className="space-y-3">
      {!isEdit && (
        <Row label="Phishlet">
          <Select value={form.phishlet} onChange={set('phishlet')}>
            {phishlets.map((p) => <option key={p.name} value={p.name}>{p.name}</option>)}
          </Select>
        </Row>
      )}
      <div className="grid grid-cols-2 gap-3">
        <Row label="Path"><Input placeholder="(auto if empty)" value={form.path} onChange={set('path')} /></Row>
        <Row label="Hostname"><Input placeholder="(optional override)" value={form.hostname} onChange={set('hostname')} /></Row>
      </div>
      <Row label="Redirect URL"><Input placeholder="https://… after capture" value={form.redirect_url} onChange={set('redirect_url')} /></Row>
      <div className="grid grid-cols-2 gap-3">
        <Row label="Redirector"><Input value={form.redirector} onChange={set('redirector')} /></Row>
        <Row label="UA Filter"><Input placeholder="regex (optional)" value={form.ua_filter} onChange={set('ua_filter')} /></Row>
      </div>
      <Row label="Info"><Input placeholder="note for your team" value={form.info} onChange={set('info')} /></Row>

      <div className="rounded-lg border border-[var(--border)]">
        <button type="button" onClick={() => setShowOg(!showOg)} className="flex w-full items-center justify-between px-3 py-2 text-left text-sm text-[var(--text-muted)] hover:bg-[var(--surface-2)]">
          <span>SEO / Open Graph (link preview)</span>
          <span className="text-[var(--text-dim)]">{showOg ? '▾' : '▸'}</span>
        </button>
        {showOg && (
          <div className="space-y-3 border-t border-[var(--border)] p-3">
            <Row label="OG title"><Input value={form.og_title} onChange={set('og_title')} /></Row>
            <Row label="OG description"><Input value={form.og_desc} onChange={set('og_desc')} /></Row>
            <div className="grid grid-cols-2 gap-3">
              <Row label="OG image URL"><Input value={form.og_image} onChange={set('og_image')} /></Row>
              <Row label="OG URL"><Input value={form.og_url} onChange={set('og_url')} /></Row>
            </div>
          </div>
        )}
      </div>

      {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
      <div className="flex justify-end gap-2 pt-2">
        <Button type="button" variant="ghost" onClick={onClose}>Cancel</Button>
        <Button type="submit" variant="primary" disabled={busy}>{busy ? 'Saving…' : 'Save'}</Button>
      </div>
    </form>
  )
}

function GetUrlModal({ lure, onClose }) {
  const [paramsText, setParamsText] = useState('')
  const [url, setUrl] = useState(null)
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)

  async function generate() {
    setBusy(true); setErr('')
    const params = {}
    for (const line of paramsText.split('\n')) {
      const i = line.indexOf('=')
      if (i > 0) params[line.slice(0, i).trim()] = line.slice(i + 1).trim()
    }
    try { const r = await api.lureUrl(lure.index, params); setUrl(r.url) }
    catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  useEffect(() => { generate() }, []) // base URL on open

  return (
    <Modal open={true} onClose={onClose} title={`Phishing URL — lure #${lure.index}`} width="max-w-2xl">
      <div className="space-y-3">
        <div>
          <label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Custom parameters (optional)</label>
          <textarea value={paramsText} onChange={(e) => setParamsText(e.target.value)} spellCheck={false}
            placeholder={'email=victim@corp.com\nname=Jane'} className="h-20 w-full resize-y rounded-lg border border-[var(--border)] bg-[var(--bg)] p-3 font-mono text-xs text-[var(--text)]" />
          <div className="mt-1 text-xs text-[var(--text-dim)]">One <span className="font-mono">key=value</span> per line — encrypted into the link.</div>
        </div>
        <Button onClick={generate} disabled={busy}>{busy ? 'Generating…' : 'Generate'}</Button>
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        {url && (
          <div>
            <div className="mb-1 flex items-center justify-between">
              <span className="text-xs font-medium uppercase tracking-wide text-[var(--text-dim)]">URL</span>
              <CopyButton text={url} label="Copy URL" />
            </div>
            <pre className="overflow-auto rounded-lg border border-[var(--border)] bg-[var(--bg)] p-3 text-xs text-[var(--accent)]">{url}</pre>
          </div>
        )}
      </div>
    </Modal>
  )
}

function PauseModal({ lure, onClose, onDone }) {
  const [duration, setDuration] = useState('1h')
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)
  async function submit() {
    setBusy(true); setErr('')
    try { await api.pauseLure(lure.index, duration.trim()); onDone() }
    catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title={`Pause lure #${lure.index}`}>
      <div className="space-y-3">
        <Row label="Duration">
          <Input value={duration} onChange={(e) => setDuration(e.target.value)} placeholder="e.g. 30m, 2h, 1d" autoFocus />
        </Row>
        <div className="text-xs text-[var(--text-dim)]">While paused, the lure redirects to the unauthorized URL instead of serving the phishlet.</div>
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        <div className="flex justify-end gap-2 pt-1">
          <Button variant="ghost" onClick={onClose}>Cancel</Button>
          <Button variant="primary" onClick={submit} disabled={busy || !duration.trim()}>{busy ? 'Pausing…' : 'Pause'}</Button>
        </div>
      </div>
    </Modal>
  )
}

export default function Lures() {
  const { hasRole } = useAuth()
  const [lures, setLures] = useState(null)
  const [phishlets, setPhishlets] = useState([])
  const [err, setErr] = useState('')
  const [modal, setModal] = useState(null) // {mode:'create'|'edit', lure}
  const [urlFor, setUrlFor] = useState(null)
  const [pauseFor, setPauseFor] = useState(null)

  const load = () => api.lures().then((r) => setLures(r.lures)).catch((e) => setErr(e.message))
  useEffect(() => { load(); api.phishlets().then((r) => setPhishlets(r.phishlets.filter((p) => !p.template))).catch(() => {}) }, [])

  const canEdit = hasRole('operator')

  async function handleSave(form) {
    if (modal.mode === 'create') await api.createLure(form)
    else await api.updateLure(modal.lure.index, form)
    setModal(null); load()
  }
  async function unpause(l) {
    setErr('')
    try { await api.unpauseLure(l.index); load() } catch (e) { setErr(e.message) }
  }

  return (
    <div>
      <div className="mb-5 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-[var(--text)]">Lures</h1>
        {canEdit && <Button variant="primary" onClick={() => setModal({ mode: 'create' })}>+ New lure</Button>}
      </div>
      {err && <div className="mb-4 rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{err}</div>}
      {!lures ? <Spinner /> : (
        <Card className="overflow-hidden">
          <table className="w-full text-sm">
            <thead className="border-b border-[var(--border)] text-left text-xs uppercase tracking-wide text-[var(--text-dim)]">
              <tr>
                <th className="px-4 py-3">#</th>
                <th className="px-4 py-3">Phishlet</th>
                <th className="px-4 py-3">URL</th>
                <th className="px-4 py-3">Info</th>
                <th className="px-4 py-3">State</th>
                {canEdit && <th className="px-4 py-3"></th>}
              </tr>
            </thead>
            <tbody>
              {lures.length === 0 && <tr><td colSpan={6} className="px-4 py-10 text-center text-[var(--text-dim)]">No lures configured.</td></tr>}
              {lures.map((l) => (
                <tr key={l.index} className="border-b border-[var(--border)] hover:bg-[var(--surface-2)]">
                  <td className="px-4 py-3 font-mono text-[var(--text-dim)]">{l.index}</td>
                  <td className="px-4 py-3 text-[var(--text-muted)]">{l.phishlet}{l.og_title && <span className="ml-1" title="has OG preview">🔖</span>}</td>
                  <td className="px-4 py-3 font-mono text-xs text-[var(--accent)]">{l.url || l.path}</td>
                  <td className="px-4 py-3 text-[var(--text-muted)]">{l.info || '—'}</td>
                  <td className="px-4 py-3">
                    {l.paused ? <Badge color="yellow">paused</Badge> : l.enabled ? <Badge color="green">live</Badge> : <Badge color="gray">phishlet off</Badge>}
                  </td>
                  {canEdit && (
                    <td className="px-4 py-3 text-right whitespace-nowrap">
                      <Button variant="ghost" onClick={() => setUrlFor(l)}>Get URL</Button>
                      {l.paused
                        ? <Button variant="ghost" onClick={() => unpause(l)}>Unpause</Button>
                        : <Button variant="ghost" onClick={() => setPauseFor(l)}>Pause</Button>}
                      <Button variant="ghost" onClick={() => setModal({ mode: 'edit', lure: l })}>Edit</Button>
                      <Button variant="ghost" className="text-[var(--danger)]" onClick={async () => { if (!confirm(`Delete lure #${l.index}?`)) return; await api.deleteLure(l.index); load() }}>Delete</Button>
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      )}

      {modal && (
        <Modal open={true} onClose={() => setModal(null)} title={modal.mode === 'create' ? 'New lure' : `Edit lure #${modal.lure.index}`} width="max-w-2xl">
          <LureForm initial={modal.lure} phishlets={phishlets} isEdit={modal.mode === 'edit'} onSave={handleSave} onClose={() => setModal(null)} />
        </Modal>
      )}
      {urlFor && <GetUrlModal lure={urlFor} onClose={() => setUrlFor(null)} />}
      {pauseFor && <PauseModal lure={pauseFor} onClose={() => setPauseFor(null)} onDone={() => { setPauseFor(null); load() }} />}
    </div>
  )
}
