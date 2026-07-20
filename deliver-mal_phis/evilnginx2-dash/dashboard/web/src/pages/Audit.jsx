import { useEffect, useState } from 'react'
import { api } from '../api'
import { Card, Badge, Spinner, fmtTime } from '../ui'

const ACTION_COLOR = (a) => {
  if (a.includes('delete')) return 'red'
  if (a.includes('login_failed')) return 'red'
  if (a.includes('create') || a.includes('enable')) return 'green'
  if (a.includes('export')) return 'yellow'
  return 'gray'
}

export default function Audit() {
  const [entries, setEntries] = useState(null)
  const [err, setErr] = useState('')

  useEffect(() => { api.audit(300).then((r) => setEntries(r.entries)).catch((e) => setErr(e.message)) }, [])

  return (
    <div>
      <h1 className="mb-5 text-2xl font-bold text-[var(--text)]">Audit Log</h1>
      {err && <div className="mb-4 rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{err}</div>}
      {!entries ? <Spinner /> : (
        <Card className="overflow-hidden">
          <table className="w-full text-sm">
            <thead className="border-b border-[var(--border)] text-left text-xs uppercase tracking-wide text-[var(--text-dim)]">
              <tr><th className="px-4 py-3">Time</th><th className="px-4 py-3">User</th><th className="px-4 py-3">Action</th><th className="px-4 py-3">Target</th><th className="px-4 py-3">Detail</th><th className="px-4 py-3">IP</th></tr>
            </thead>
            <tbody>
              {entries.length === 0 && <tr><td colSpan={6} className="px-4 py-10 text-center text-[var(--text-dim)]">No audit entries.</td></tr>}
              {entries.map((e) => (
                <tr key={e.id} className="border-b border-[var(--border)]">
                  <td className="px-4 py-2.5 whitespace-nowrap text-xs text-[var(--text-dim)]">{fmtTime(e.time)}</td>
                  <td className="px-4 py-2.5 text-[var(--text-muted)]">{e.user || '—'}</td>
                  <td className="px-4 py-2.5"><Badge color={ACTION_COLOR(e.action)}>{e.action}</Badge></td>
                  <td className="px-4 py-2.5 font-mono text-xs text-[var(--text-muted)]">{e.target || '—'}</td>
                  <td className="px-4 py-2.5 text-xs text-[var(--text-dim)]">{e.detail || '—'}</td>
                  <td className="px-4 py-2.5 font-mono text-xs text-[var(--text-dim)]">{e.ip}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      )}
    </div>
  )
}
