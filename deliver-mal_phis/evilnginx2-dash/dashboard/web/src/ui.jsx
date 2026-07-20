// Shared UI primitives, themed via CSS variables (dark-red / muted-light).
import { useEffect } from 'react'

export function Card({ children, className = '' }) {
  return (
    <div className={`rounded-xl border border-[var(--border)] bg-[var(--surface)] ${className}`}>{children}</div>
  )
}

export function Button({ children, variant = 'default', className = '', ...props }) {
  const variants = {
    default: 'bg-[var(--surface-2)] hover:bg-[var(--surface-3)] text-[var(--text)] border border-[var(--border)]',
    primary: 'bg-[var(--accent)] hover:bg-[var(--accent-strong)] text-white border border-transparent',
    danger: 'bg-[var(--danger)] hover:opacity-90 text-white border border-transparent',
    ghost: 'bg-transparent hover:bg-[var(--surface-2)] text-[var(--text-muted)] border border-transparent',
  }
  return (
    <button
      className={`inline-flex items-center justify-center gap-1.5 rounded-lg px-3 py-1.5 text-sm font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed ${variants[variant]} ${className}`}
      {...props}
    >
      {children}
    </button>
  )
}

export function Input(props) {
  return (
    <input
      {...props}
      className={`w-full rounded-lg border border-[var(--border)] bg-[var(--bg)] px-3 py-2 text-sm text-[var(--text)] placeholder-[var(--text-dim)] outline-none focus:border-[var(--accent)] ${props.className || ''}`}
    />
  )
}

export function Select(props) {
  return (
    <select
      {...props}
      className={`w-full rounded-lg border border-[var(--border)] bg-[var(--bg)] px-3 py-2 text-sm text-[var(--text)] outline-none focus:border-[var(--accent)] ${props.className || ''}`}
    />
  )
}

export function Badge({ children, color = 'gray' }) {
  const styles = {
    gray: { bg: 'var(--surface-3)', fg: 'var(--text-muted)' },
    green: { bg: 'var(--ok-weak)', fg: 'var(--ok)' },
    red: { bg: 'var(--danger-weak)', fg: 'var(--danger)' },
    blue: { bg: 'var(--info-weak)', fg: 'var(--info)' },
    yellow: { bg: 'var(--warn-weak)', fg: 'var(--warn)' },
  }
  const s = styles[color] || styles.gray
  return (
    <span className="inline-flex items-center rounded-full px-2 py-0.5 text-xs font-medium" style={{ background: s.bg, color: s.fg }}>
      {children}
    </span>
  )
}

export function Modal({ open, onClose, title, children, width = 'max-w-lg' }) {
  useEffect(() => {
    if (!open) return
    const onKey = (e) => { if (e.key === 'Escape') onClose() }
    window.addEventListener('keydown', onKey)
    return () => window.removeEventListener('keydown', onKey)
  }, [open, onClose])
  if (!open) return null
  return (
    <div className="fixed inset-0 z-50 flex items-start justify-center bg-black/60 p-4 pt-10 sm:pt-16" onClick={onClose}>
      <div
        className={`flex max-h-[88vh] w-full ${width} flex-col rounded-xl border border-[var(--border)] bg-[var(--surface)] shadow-2xl`}
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex shrink-0 items-center justify-between border-b border-[var(--border)] px-5 py-3">
          <h3 className="text-sm font-semibold text-[var(--text)]">{title}</h3>
          <button onClick={onClose} className="text-[var(--text-dim)] hover:text-[var(--text)]">✕</button>
        </div>
        <div className="overflow-y-auto p-5">{children}</div>
      </div>
    </div>
  )
}

export function Spinner() {
  return (
    <div className="flex items-center justify-center p-10 text-[var(--text-dim)]">
      <div className="h-6 w-6 animate-spin rounded-full border-2 border-[var(--border)] border-t-[var(--accent)]" />
    </div>
  )
}

export function fmtTime(unix) {
  if (!unix) return '—'
  return new Date(unix * 1000).toLocaleString()
}

export function timeAgo(unix) {
  if (!unix) return '—'
  const s = Math.floor(Date.now() / 1000) - unix
  if (s < 60) return s + 's ago'
  if (s < 3600) return Math.floor(s / 60) + 'm ago'
  if (s < 86400) return Math.floor(s / 3600) + 'h ago'
  return Math.floor(s / 86400) + 'd ago'
}

// flagEmoji turns a 2-letter ISO country code into its flag emoji.
export function flagEmoji(cc) {
  if (!cc || cc.length !== 2) return ''
  const base = 0x1f1e6
  return String.fromCodePoint(...[...cc.toUpperCase()].map((c) => base + c.charCodeAt(0) - 65))
}

// GeoCell renders compact geolocation (flag + city/country, ASN on hover). Shows
// a dash when geo is unavailable (no DB, or a private/loopback IP).
export function GeoCell({ geo }) {
  if (!geo) return <span className="text-[var(--text-dim)]">—</span>
  const parts = []
  if (geo.city) parts.push(geo.city)
  if (geo.country || geo.country_code) parts.push(geo.country || geo.country_code)
  const asn = geo.asn ? `AS${geo.asn}${geo.as_org ? ' · ' + geo.as_org : ''}` : ''
  return (
    <span className="text-xs text-[var(--text-muted)]" title={asn}>
      {flagEmoji(geo.country_code)} {parts.join(', ') || geo.country_code || '—'}
    </span>
  )
}
