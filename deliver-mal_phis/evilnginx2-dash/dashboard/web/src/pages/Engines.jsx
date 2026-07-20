import { useEffect, useState, useCallback } from 'react'
import { api } from '../api'
import { useAuth } from '../auth'
import { Card, Button, Input, Badge, Modal, Spinner, fmtTime } from '../ui'

function AddEngineModal({ onClose, onDone }) {
  const [form, setForm] = useState({ name: '', url: '', token: '' })
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)
  const set = (k) => (e) => setForm({ ...form, [k]: e.target.value })
  async function submit() {
    setBusy(true); setErr('')
    try { await api.createEngine({ ...form, name: form.name.trim(), url: form.url.trim(), token: form.token.trim() }); onDone() }
    catch (e) { setErr(e.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title="Register engine">
      <div className="space-y-3">
        <div><label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Name</label><Input value={form.name} onChange={set('name')} placeholder="vps-a" autoFocus /></div>
        <div>
          <label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Agent URL</label>
          <Input value={form.url} onChange={set('url')} placeholder="http://10.66.0.2:8081" />
          <div className="mt-1 text-xs text-[var(--text-dim)]">The engine's <span className="font-mono">-agent-addr</span> over WireGuard/private network.</div>
        </div>
        <div>
          <label className="mb-1 block text-xs font-medium text-[var(--text-muted)]">Agent token</label>
          <Input type="password" value={form.token} onChange={set('token')} placeholder="EVILGINX_AGENT_TOKEN" />
        </div>
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        <div className="flex justify-end gap-2 pt-1">
          <Button variant="ghost" onClick={onClose}>Cancel</Button>
          <Button variant="primary" onClick={submit} disabled={busy || !form.name.trim() || !form.url.trim() || !form.token.trim()}>{busy ? 'Registering…' : 'Register'}</Button>
        </div>
      </div>
    </Modal>
  )
}

function StatusBadge({ st }) {
  if (!st) return <Badge color="gray">checking…</Badge>
  if (!st.online) return <Badge color="red">offline</Badge>
  if (!st.auth_ok) return <Badge color="yellow">bad token</Badge>
  return <Badge color="green">online{st.version ? ` · ${st.version}` : ''}</Badge>
}

export default function Engines() {
  const { hasRole } = useAuth()
  const [engines, setEngines] = useState(null)
  const [status, setStatus] = useState({}) // id -> EngineStatus
  const [err, setErr] = useState('')
  const [adding, setAdding] = useState(false)

  const isAdmin = hasRole('admin')

  const checkAll = useCallback((list) => {
    list.forEach((e) => {
      setStatus((s) => ({ ...s, [e.id]: null }))
      api.checkEngine(e.id).then((st) => setStatus((s) => ({ ...s, [e.id]: st }))).catch(() => setStatus((s) => ({ ...s, [e.id]: { online: false } })))
    })
  }, [])

  const load = useCallback(() => {
    api.engines().then((r) => { setEngines(r.engines); checkAll(r.engines) }).catch((e) => setErr(e.message))
  }, [checkAll])

  useEffect(() => { load() }, [load])

  async function remove(e) {
    if (!confirm(`Remove engine "${e.name}"? (the engine keeps running; only the registration is removed)`)) return
    setErr('')
    try { await api.deleteEngine(e.id); load() } catch (er) { setErr(er.message) }
  }

  return (
    <div>
      <div className="mb-5 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-[var(--text)]">Engines</h1>
        <div className="flex gap-2">
          <Button variant="ghost" onClick={() => engines && checkAll(engines)}>↻ Recheck</Button>
          {isAdmin && <Button variant="primary" onClick={() => setAdding(true)}>+ Register engine</Button>}
        </div>
      </div>
      {err && <div className="mb-4 rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{err}</div>}
      {!engines ? <Spinner /> : (
        <Card className="overflow-hidden">
          <table className="w-full text-sm">
            <thead className="border-b border-[var(--border)] text-left text-xs uppercase tracking-wide text-[var(--text-dim)]">
              <tr>
                <th className="px-4 py-3">Name</th>
                <th className="px-4 py-3">Agent URL</th>
                <th className="px-4 py-3">Status</th>
                <th className="px-4 py-3">Registered</th>
                {isAdmin && <th className="px-4 py-3 text-right"></th>}
              </tr>
            </thead>
            <tbody>
              {engines.length === 0 && <tr><td colSpan={5} className="px-4 py-10 text-center text-[var(--text-dim)]">No engines registered. Add one to manage it from here.</td></tr>}
              {engines.map((e) => (
                <tr key={e.id} className="border-b border-[var(--border)] hover:bg-[var(--surface-2)]">
                  <td className="px-4 py-3 font-medium text-[var(--text)]">{e.name}</td>
                  <td className="px-4 py-3 font-mono text-xs text-[var(--text-muted)]">{e.url}</td>
                  <td className="px-4 py-3">
                    <StatusBadge st={status[e.id]} />
                    {status[e.id] && status[e.id].error && <span className="ml-2 text-xs text-[var(--danger)]">{status[e.id].error}</span>}
                  </td>
                  <td className="px-4 py-3 text-xs text-[var(--text-dim)]">{fmtTime(e.create_time)}</td>
                  {isAdmin && (
                    <td className="px-4 py-3 text-right">
                      <Button variant="ghost" className="text-[var(--danger)]" onClick={() => remove(e)}>Remove</Button>
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      )}
      <p className="mt-3 text-xs text-[var(--text-dim)]">Engines run <span className="font-mono">evilginx … -agent -agent-addr &lt;wg-ip&gt;:8081</span>. Reach them over WireGuard/SSH only; the agent API is token-authed and should never be public.</p>

      {adding && <AddEngineModal onClose={() => setAdding(false)} onDone={() => { setAdding(false); load() }} />}
    </div>
  )
}
