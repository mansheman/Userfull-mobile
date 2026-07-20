import { Routes, Route, Navigate, NavLink, useNavigate } from 'react-router-dom'
import {
  Radar, Crosshair, Fish, FileCode2, ShieldBan, ServerCog,
  Users as UsersIcon, ScrollText, SlidersHorizontal,
  Sun, Moon, LogOut, UserCircle2,
} from 'lucide-react'
import { useAuth } from './auth'
import { useTheme } from './theme'
import { LiveProvider, useLive } from './live'
import { Spinner } from './ui'
import logo from './assets/evilginx-logo.png'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import Sessions from './pages/Sessions'
import Lures from './pages/Lures'
import Phishlets from './pages/Phishlets'
import Users from './pages/Users'
import Audit from './pages/Audit'
import Account from './pages/Account'
import Settings from './pages/Settings'
import Blacklist from './pages/Blacklist'
import Engines from './pages/Engines'

const NAV = [
  { to: '/', label: 'Dashboard', Icon: Radar, end: true, role: 'viewer' },
  { to: '/sessions', label: 'Sessions', Icon: Crosshair, role: 'viewer' },
  { to: '/lures', label: 'Lures', Icon: Fish, role: 'viewer' },
  { to: '/phishlets', label: 'Phishlets', Icon: FileCode2, role: 'viewer' },
  { to: '/blacklist', label: 'Blacklist', Icon: ShieldBan, role: 'viewer' },
  { to: '/engines', label: 'Engines', Icon: ServerCog, role: 'operator' },
  { to: '/users', label: 'Users', Icon: UsersIcon, role: 'admin' },
  { to: '/audit', label: 'Audit Log', Icon: ScrollText, role: 'admin' },
  { to: '/settings', label: 'Settings', Icon: SlidersHorizontal, role: 'admin' },
]

function LiveDot() {
  const { connected } = useLive()
  return (
    <span className="inline-flex items-center gap-1.5 text-xs" title={connected ? 'Live updates connected' : 'Reconnecting…'}>
      <span className="h-2 w-2 rounded-full" style={{ background: connected ? 'var(--ok)' : 'var(--text-dim)' }} />
      <span style={{ color: connected ? 'var(--ok)' : 'var(--text-dim)' }}>{connected ? 'live' : 'offline'}</span>
    </span>
  )
}

function ThemeToggle() {
  const { theme, toggle } = useTheme()
  return (
    <button
      onClick={toggle}
      title={theme === 'dark' ? 'Switch to light' : 'Switch to dark'}
      className="rounded-lg p-1.5 text-[var(--text-dim)] hover:bg-[var(--surface-2)] hover:text-[var(--text)]"
      aria-label="Toggle theme"
    >
      {theme === 'dark' ? <Sun size={16} /> : <Moon size={16} />}
    </button>
  )
}

function Sidebar() {
  const { user, hasRole, logout } = useAuth()
  const navigate = useNavigate()
  return (
    <aside className="flex w-60 shrink-0 flex-col border-r border-[var(--border)] bg-[var(--surface)]">
      <div className="px-5 py-5">
        <div className="flex items-center gap-2.5">
          <img src={logo} alt="evilginx" className="h-7 w-7" />
          <div className="text-lg font-bold tracking-tight text-[var(--text)]">evilginx<span className="text-[var(--text-dim)]">/dash</span></div>
        </div>
        <div className="mt-2 flex items-center justify-between">
          <LiveDot />
          <ThemeToggle />
        </div>
      </div>
      <nav className="flex-1 space-y-1 px-3">
        {NAV.filter((n) => hasRole(n.role)).map(({ to, label, Icon, end }) => (
          <NavLink
            key={to}
            to={to}
            end={end}
            className={({ isActive }) =>
              `flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors ${
                isActive
                  ? 'bg-[var(--accent-weak)] text-[var(--accent)]'
                  : 'text-[var(--text-muted)] hover:bg-[var(--surface-2)] hover:text-[var(--text)]'
              }`
            }
          >
            <Icon size={17} className="shrink-0" />
            {label}
          </NavLink>
        ))}
      </nav>
      <div className="border-t border-[var(--border)] p-3">
        <NavLink to="/account" className="flex items-center gap-2.5 rounded-lg px-3 py-2 text-sm hover:bg-[var(--surface-2)]">
          <UserCircle2 size={20} className="text-[var(--text-dim)]" />
          <div>
            <div className="font-medium text-[var(--text)]">{user?.username}</div>
            <div className="text-xs capitalize text-[var(--text-dim)]">{user?.role}</div>
          </div>
        </NavLink>
        <button
          onClick={() => { logout(); navigate('/login') }}
          className="mt-1 flex w-full items-center gap-2.5 rounded-lg px-3 py-2 text-left text-sm text-[var(--text-muted)] hover:bg-[var(--surface-2)] hover:text-[var(--accent)]"
        >
          <LogOut size={16} /> Sign out
        </button>
      </div>
    </aside>
  )
}

function RequireRole({ role, children }) {
  const { hasRole } = useAuth()
  if (!hasRole(role)) return <Navigate to="/" replace />
  return children
}

function Shell() {
  return (
    <LiveProvider>
    <div className="flex h-screen overflow-hidden">
      <Sidebar />
      <main className="flex-1 overflow-y-auto">
        <div className="mx-auto max-w-7xl px-8 py-7">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/sessions" element={<Sessions />} />
            <Route path="/lures" element={<Lures />} />
            <Route path="/phishlets" element={<Phishlets />} />
            <Route path="/blacklist" element={<Blacklist />} />
            <Route path="/engines" element={<RequireRole role="operator"><Engines /></RequireRole>} />
            <Route path="/account" element={<Account />} />
            <Route path="/users" element={<RequireRole role="admin"><Users /></RequireRole>} />
            <Route path="/audit" element={<RequireRole role="admin"><Audit /></RequireRole>} />
            <Route path="/settings" element={<RequireRole role="admin"><Settings /></RequireRole>} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </div>
      </main>
    </div>
    </LiveProvider>
  )
}

export default function App() {
  const { user, loading } = useAuth()
  if (loading) return <div className="flex h-screen items-center justify-center"><Spinner /></div>
  if (!user) {
    return (
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    )
  }
  return (
    <Routes>
      <Route path="/login" element={<Navigate to="/" replace />} />
      <Route path="*" element={<Shell />} />
    </Routes>
  )
}
