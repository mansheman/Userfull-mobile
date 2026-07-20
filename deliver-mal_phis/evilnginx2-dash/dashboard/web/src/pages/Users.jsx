import { useEffect, useState } from 'react'
import { api } from '../api'
import { useAuth } from '../auth'
import { Card, Button, Input, Select, Badge, Modal, Spinner, fmtTime } from '../ui'

const ROLES = ['viewer', 'operator', 'admin']

function CreateUserModal({ onClose, onCreated }) {
  const [form, setForm] = useState({ username: '', password: '', role: 'viewer' })
  const [err, setErr] = useState(''); const [busy, setBusy] = useState(false)
  const set = (k) => (e) => setForm({ ...form, [k]: e.target.value })
  async function submit(e) {
    e.preventDefault(); setBusy(true); setErr('')
    try { await api.createUser(form); onCreated() } catch (er) { setErr(er.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title="New user">
      <form onSubmit={submit} className="space-y-3">
        <div><label className="mb-1 block text-xs text-[var(--text-muted)]">Username</label><Input value={form.username} onChange={set('username')} autoFocus /></div>
        <div><label className="mb-1 block text-xs text-[var(--text-muted)]">Password</label><Input type="password" value={form.password} onChange={set('password')} placeholder="min 8 chars" /></div>
        <div><label className="mb-1 block text-xs text-[var(--text-muted)]">Role</label><Select value={form.role} onChange={set('role')}>{ROLES.map((r) => <option key={r} value={r}>{r}</option>)}</Select></div>
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        <div className="flex justify-end gap-2 pt-2">
          <Button type="button" variant="ghost" onClick={onClose}>Cancel</Button>
          <Button type="submit" variant="primary" disabled={busy}>Create</Button>
        </div>
      </form>
    </Modal>
  )
}

function EditUserModal({ user, onClose, onSaved }) {
  const [role, setRole] = useState(user.role)
  const [password, setPassword] = useState('')
  const [err, setErr] = useState(''); const [busy, setBusy] = useState(false)
  async function save() {
    setBusy(true); setErr('')
    try {
      const patch = {}
      if (role !== user.role) patch.role = role
      if (password) patch.password = password
      if (Object.keys(patch).length) await api.updateUser(user.username, patch)
      onSaved()
    } catch (er) { setErr(er.message) } finally { setBusy(false) }
  }
  return (
    <Modal open={true} onClose={onClose} title={`Edit ${user.username}`}>
      <div className="space-y-3">
        <div><label className="mb-1 block text-xs text-[var(--text-muted)]">Role</label><Select value={role} onChange={(e) => setRole(e.target.value)}>{ROLES.map((r) => <option key={r} value={r}>{r}</option>)}</Select></div>
        <div><label className="mb-1 block text-xs text-[var(--text-muted)]">Reset password</label><Input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="leave blank to keep" /></div>
        {err && <div className="rounded-lg bg-[var(--danger-weak)] px-3 py-2 text-sm text-[var(--danger)]">{err}</div>}
        <div className="flex justify-end gap-2 pt-2">
          <Button variant="ghost" onClick={onClose}>Cancel</Button>
          <Button variant="primary" onClick={save} disabled={busy}>Save</Button>
        </div>
      </div>
    </Modal>
  )
}

export default function Users() {
  const { user: me } = useAuth()
  const [users, setUsers] = useState(null)
  const [err, setErr] = useState('')
  const [creating, setCreating] = useState(false)
  const [editing, setEditing] = useState(null)

  const load = () => api.users().then((r) => setUsers(r.users)).catch((e) => setErr(e.message))
  useEffect(() => { load() }, [])

  async function toggleDisabled(u) {
    try { await api.updateUser(u.username, { disabled: !u.disabled }); load() } catch (e) { setErr(e.message) }
  }
  async function remove(u) {
    if (!confirm(`Delete user ${u.username}?`)) return
    try { await api.deleteUser(u.username); load() } catch (e) { setErr(e.message) }
  }

  return (
    <div>
      <div className="mb-5 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-[var(--text)]">Users</h1>
        <Button variant="primary" onClick={() => setCreating(true)}>+ New user</Button>
      </div>
      {err && <div className="mb-4 rounded-lg bg-[var(--danger-weak)] px-4 py-3 text-[var(--danger)]">{err}</div>}
      {!users ? <Spinner /> : (
        <Card className="overflow-hidden">
          <table className="w-full text-sm">
            <thead className="border-b border-[var(--border)] text-left text-xs uppercase tracking-wide text-[var(--text-dim)]">
              <tr><th className="px-4 py-3">Username</th><th className="px-4 py-3">Role</th><th className="px-4 py-3">Status</th><th className="px-4 py-3">Last login</th><th className="px-4 py-3 text-right"></th></tr>
            </thead>
            <tbody>
              {users.map((u) => (
                <tr key={u.username} className="border-b border-[var(--border)] hover:bg-[var(--surface-2)]">
                  <td className="px-4 py-3 font-medium text-[var(--text)]">{u.username} {u.username === me.username && <span className="text-xs text-[var(--text-dim)]">(you)</span>}</td>
                  <td className="px-4 py-3"><Badge color={u.role === 'admin' ? 'red' : u.role === 'operator' ? 'blue' : 'gray'}>{u.role}</Badge></td>
                  <td className="px-4 py-3">{u.disabled ? <Badge color="gray">disabled</Badge> : <Badge color="green">active</Badge>}</td>
                  <td className="px-4 py-3 text-xs text-[var(--text-dim)]">{u.last_login ? fmtTime(u.last_login) : 'never'}</td>
                  <td className="px-4 py-3 text-right">
                    <Button variant="ghost" onClick={() => setEditing(u)}>Edit</Button>
                    {u.username !== me.username && (
                      <>
                        <Button variant="ghost" onClick={() => toggleDisabled(u)}>{u.disabled ? 'Enable' : 'Disable'}</Button>
                        <Button variant="ghost" className="text-[var(--danger)]" onClick={() => remove(u)}>Delete</Button>
                      </>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </Card>
      )}
      {creating && <CreateUserModal onClose={() => setCreating(false)} onCreated={() => { setCreating(false); load() }} />}
      {editing && <EditUserModal user={editing} onClose={() => setEditing(null)} onSaved={() => { setEditing(null); load() }} />}
    </div>
  )
}
