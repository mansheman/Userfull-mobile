import { useState } from 'react'
import { api } from '../api'
import { useAuth } from '../auth'
import { Card, Button, Input, Badge } from '../ui'

export default function Account() {
  const { user } = useAuth()
  const [oldp, setOldp] = useState('')
  const [newp, setNewp] = useState('')
  const [confirm, setConfirm] = useState('')
  const [msg, setMsg] = useState('')
  const [err, setErr] = useState('')
  const [busy, setBusy] = useState(false)

  async function submit(e) {
    e.preventDefault()
    setErr(''); setMsg('')
    if (newp !== confirm) { setErr('new passwords do not match'); return }
    setBusy(true)
    try {
      await api.changePassword(oldp, newp)
      setMsg('Password updated.')
      setOldp(''); setNewp(''); setConfirm('')
    } catch (e) { setErr(e.message) } finally { setBusy(false) }
  }

  return (
    <div className="max-w-md">
      <h1 className="mb-5 text-2xl font-bold text-[var(--text)]">Account</h1>
      <Card className="mb-4 p-5">
        <div className="flex items-center justify-between">
          <div>
            <div className="text-lg font-semibold text-[var(--text)]">{user.username}</div>
            <div className="text-sm text-[var(--text-dim)]">Signed in</div>
          </div>
          <Badge color={user.role === 'admin' ? 'red' : user.role === 'operator' ? 'blue' : 'gray'}>{user.role}</Badge>
        </div>
      </Card>
      <Card className="p-5">
        <h2 className="mb-4 text-sm font-semibold text-[var(--text)]">Change password</h2>
        <form onSubmit={submit} className="space-y-3">
          <Input type="password" placeholder="Current password" value={oldp} onChange={(e) => setOldp(e.target.value)} autoComplete="current-password" />
          <Input type="password" placeholder="New password (min 8)" value={newp} onChange={(e) => setNewp(e.target.value)} autoComplete="new-password" />
          <Input type="password" placeholder="Confirm new password" value={confirm} onChange={(e) => setConfirm(e.target.value)} autoComplete="new-password" />
          {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
          {msg && <div className="rounded-lg bg-[var(--ok-weak)] px-3 py-2 text-sm text-[var(--ok)]">{msg}</div>}
          <Button type="submit" variant="primary" disabled={busy}>{busy ? 'Updating…' : 'Update password'}</Button>
        </form>
      </Card>
    </div>
  )
}
