import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { api } from '../api'
import { useAuth } from '../auth'
import { Card, Button, Input, Badge, Modal, Spinner, GeoCell } from '../ui'

function ImportModal({ onClose, onDone }) {
  const [text, setText] = useState('')
  const [busy, setBusy] = useState(false)
  const [result, setResult] = useState(null)
  const [err, setErr] = useState('')
  async function submit() {
    setBusy(true); setErr('')
    try {
      const r = await api.blacklistImport(text)
      setResult({ added: r.added, errors: r.errors || [] })
      if (!r.errors || r.errors.length === 0) onDone()
    } catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title="Import blacklist entries" width="max-w-2xl">
      <div className="space-y-3">
        <p className="text-xs text-[var(--text-dim)]">One IP or CIDR per line. <span className="font-mono">;</span> starts a comment. Invalid lines are reported and skipped.</p>
        <textarea value={text} onChange={(e) => setText(e.target.value)} spellCheck={false}
          placeholder={'10.0.0.5\n192.168.0.0/16\n203.0.113.7 ; scanner'}
          className="h-56 w-full resize-y rounded-lg border border-[var(--border)] bg-[var(--bg)] p-3 font-mono text-xs text-[var(--text)] outline-none focus:border-[var(--accent)]" />
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        {result && (
          <div className="rounded-lg border border-[var(--border)] bg-[var(--bg)] p-3 text-sm">
            <div className="text-[var(--accent)]">Added {result.added} entr{result.added === 1 ? 'y' : 'ies'}.</div>
            {result.errors.length > 0 && (
              <div className="mt-2 text-[var(--danger)]">
                <div className="text-xs uppercase tracking-wide text-[var(--text-dim)]">Skipped</div>
                {result.errors.map((e, i) => <div key={i} className="font-mono text-xs">{e}</div>)}
              </div>
            )}
          </div>
        )}
        <div className="flex justify-end gap-2 pt-1">
          <Button variant="ghost" onClick={onClose}>{result ? 'Close' : 'Cancel'}</Button>
          <Button variant="primary" onClick={submit} disabled={busy || !text.trim()}>{busy ? 'Importing…' : 'Import'}</Button>
        </div>
      </div>
    </Modal>
  )
}

export default function Blacklist() {
  const { hasRole } = useAuth()
  const [data, setData] = useState(null)
  const [err, setErr] = useState('')
  const [entry, setEntry] = useState('')
  const [busy, setBusy] = useState(false)
  const [importing, setImporting] = useState(false)
  const [filter, setFilter] = useState('')

  const canEdit = hasRole('operator')
  const load = () => api.blacklist().then(setData).catch((e) => setErr(e.message))
  useEffect(() => { load() }, [])

  async function add() {
    if (!entry.trim()) return
    setBusy(true); setErr('')
    try { const r = await api.blacklistAdd(entry.trim()); setData(r); setEntry('') }
    catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  async function remove(value) {
    setErr('')
    try { const r = await api.blacklistRemove(value); setData(r) } catch (e) { setErr(e.message) }
  }
  async function clearAll() {
    if (!confirm('Remove ALL blacklist entries? This cannot be undone.')) return
    setErr('')
    try { const r = await api.blacklistClear(); setData(r) } catch (e) { setErr(e.message) }
  }

  if (!data && !err) return <Spinner />

  const entries = (data?.entries || []).filter((e) => !filter || e.value.includes(filter))

  return (
    <div>
      <div className="mb-5 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-[var(--text)]">Blacklist</h1>
        <div className="flex items-center gap-2">
          <Badge color="blue">mode: {data?.mode}</Badge>
          {hasRole('admin') && <Link to="/settings" className="text-xs text-[var(--text-dim)] underline">change mode</Link>}
        </div>
      </div>

      {err && <div className="mb-4 rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{err}</div>}

      <Card className="mb-4 p-4">
        <div className="flex items-center justify-between">
          <div className="text-sm text-[var(--text-muted)]">
            <span className="font-mono text-[var(--text)]">{data?.ip_count ?? 0}</span> IPs ·
            <span className="ml-1 font-mono text-[var(--text)]">{data?.mask_count ?? 0}</span> CIDR masks
          </div>
          {canEdit && (
            <div className="flex gap-2">
              <Button onClick={() => setImporting(true)}>Import</Button>
              <Button variant="danger" onClick={clearAll}>Clear all</Button>
            </div>
          )}
        </div>
        {canEdit && (
          <div className="mt-3 flex gap-2">
            <Input value={entry} onChange={(e) => setEntry(e.target.value)} placeholder="IP (10.0.0.5) or CIDR (192.168.0.0/16)"
              onKeyDown={(e) => { if (e.key === 'Enter') add() }} />
            <Button variant="primary" onClick={add} disabled={busy || !entry.trim()}>Add</Button>
          </div>
        )}
      </Card>

      <Card className="overflow-hidden">
        <div className="border-b border-[var(--border)] p-3">
          <Input value={filter} onChange={(e) => setFilter(e.target.value)} placeholder="Filter…" className="max-w-xs" />
        </div>
        <table className="w-full text-sm">
          <thead className="border-b border-[var(--border)] text-left text-xs uppercase tracking-wide text-[var(--text-dim)]">
            <tr><th className="px-4 py-3">Entry</th><th className="px-4 py-3">Type</th><th className="px-4 py-3">Location</th>{canEdit && <th className="px-4 py-3 text-right"></th>}</tr>
          </thead>
          <tbody>
            {entries.length === 0 && <tr><td colSpan={canEdit ? 4 : 3} className="px-4 py-10 text-center text-[var(--text-dim)]">No entries.</td></tr>}
            {entries.map((e) => (
              <tr key={e.value} className="border-b border-[var(--border)] hover:bg-[var(--surface-2)]">
                <td className="px-4 py-2.5 font-mono text-[var(--text)]">{e.value}</td>
                <td className="px-4 py-2.5">{e.is_mask ? <Badge color="yellow">CIDR</Badge> : <Badge color="gray">IP</Badge>}</td>
                <td className="px-4 py-2.5"><GeoCell geo={e.geo} /></td>
                {canEdit && <td className="px-4 py-2.5 text-right"><Button variant="ghost" className="text-[var(--danger)]" onClick={() => remove(e.value)}>Remove</Button></td>}
              </tr>
            ))}
          </tbody>
        </table>
      </Card>

      {importing && <ImportModal onClose={() => setImporting(false)} onDone={() => { setImporting(false); load() }} />}
    </div>
  )
}
