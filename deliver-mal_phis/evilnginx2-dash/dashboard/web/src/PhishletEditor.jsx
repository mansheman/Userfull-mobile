import { useEffect, useMemo, useRef, useState, useCallback } from 'react'
import yaml from 'js-yaml'
import { api } from './api'

// Section snippets for the tricky parts of the phishlet format.
const SNIPPETS = {
  'proxy_host': `  - {phish_sub: 'www', orig_sub: 'www', domain: 'target.com', session: true, is_landing: true, auto_filter: true}\n`,
  'sub_filter': `  - {triggers_on: 'www.target.com', orig_sub: 'www', domain: 'target.com', search: 'FIND', replace: 'REPLACE', mimes: ['text/html','application/json']}\n`,
  'auth_token (cookie)': `  - domain: '.target.com'\n    keys: ['session']\n`,
  'auth_token (body)': `  - type: body\n    domain: 'target.com'\n    path: '/api/x'\n    name: 'csrf'\n    search: 'token":"([^"]+)"'\n`,
  'auth_token (http)': `  - type: http\n    domain: 'target.com'\n    path: '/api/x'\n    name: 'auth'\n    header: 'Authorization'\n`,
  'credentials.custom': `  custom:\n    - {key: 'otp', search: '(.*)', type: 'post'}\n`,
  'force_post': `  - path: '/login'\n    type: post\n    force:\n      - {key: 'remember', value: 'false'}\n`,
  'js_inject': `  - trigger_domains: ['www.target.com']\n    trigger_paths: ['/login']\n    trigger_params: []\n    script: |\n      console.log('hi');\n`,
  'intercept': `  - {domain: 'www.target.com', path: '/blocked', http_status: 200, body: 'nope', mime: 'text/plain'}\n`,
  'params (template)': `  - {name: 'target_domain', required: true}\n  - {name: 'lure_path', default: '/login'}\n`,
}

// required top-level sections we can check client-side from the parsed object
function structureChecklist(doc) {
  if (!doc || typeof doc !== 'object') return []
  const has = (v) => v !== undefined && v !== null
  const arr = (v) => Array.isArray(v) && v.length > 0
  const creds = doc.credentials || {}
  return [
    { label: 'min_ver', ok: has(doc.min_ver) },
    { label: 'proxy_hosts', ok: arr(doc.proxy_hosts) },
    { label: 'auth_tokens', ok: arr(doc.auth_tokens) },
    { label: 'credentials.username', ok: has(creds.username) },
    { label: 'credentials.password', ok: has(creds.password) },
    { label: 'login', ok: has(doc.login && doc.login.domain) },
  ]
}

export default function PhishletEditor({ value, onChange, readOnly = false, onValidity }) {
  const taRef = useRef(null)
  const gutterRef = useRef(null)
  const [syntax, setSyntax] = useState(null)    // {line,col,message} | null
  const [server, setServer] = useState(null)    // {valid,error} | null
  const [checking, setChecking] = useState(false)
  const debounceRef = useRef(null)

  const lineCount = useMemo(() => value.split('\n').length, [value])

  // parse for syntax errors on every change (instant)
  const parsed = useMemo(() => {
    try {
      const doc = yaml.load(value)
      return { doc, err: null }
    } catch (e) {
      const mark = e.mark || {}
      return { doc: null, err: { line: (mark.line ?? 0) + 1, col: (mark.column ?? 0) + 1, message: e.reason || e.message } }
    }
  }, [value])

  useEffect(() => { setSyntax(parsed.err) }, [parsed.err])

  // debounced authoritative server validation (only when syntax is OK)
  const runServerValidate = useCallback((text) => {
    setChecking(true)
    api.validatePhishlet(text)
      .then((r) => setServer(r))
      .catch((e) => setServer({ valid: false, error: e.message }))
      .finally(() => setChecking(false))
  }, [])

  useEffect(() => {
    if (debounceRef.current) clearTimeout(debounceRef.current)
    if (parsed.err) { setServer(null); return }       // skip server if syntax broken
    debounceRef.current = setTimeout(() => runServerValidate(value), 600)
    return () => debounceRef.current && clearTimeout(debounceRef.current)
  }, [value, parsed.err, runServerValidate])

  // report current validity to the parent (for gating Save/Import)
  useEffect(() => {
    if (onValidity) onValidity(!syntax && server != null && server.valid === true)
  }, [syntax, server, onValidity])

  const checklist = structureChecklist(parsed.doc)

  function insertSnippet(text) {
    const ta = taRef.current
    if (!ta) { onChange(value + '\n' + text); return }
    const start = ta.selectionStart ?? value.length
    const next = value.slice(0, start) + (value.slice(0, start).endsWith('\n') || start === 0 ? '' : '\n') + text + value.slice(start)
    onChange(next)
  }

  function syncScroll() {
    if (gutterRef.current && taRef.current) gutterRef.current.scrollTop = taRef.current.scrollTop
  }

  // status line
  let status, statusColor
  if (syntax) {
    status = `YAML syntax error — line ${syntax.line}:${syntax.col}: ${syntax.message}`
    statusColor = 'text-[var(--danger)]'
  } else if (checking) {
    status = 'Validating…'; statusColor = 'text-[var(--text-dim)]'
  } else if (server && server.valid) {
    status = '✓ Valid phishlet'; statusColor = 'text-[var(--accent)]'
  } else if (server && !server.valid) {
    status = `✗ ${server.error}`; statusColor = 'text-[var(--danger)]'
  } else {
    status = 'Edit YAML…'; statusColor = 'text-[var(--text-dim)]'
  }

  return (
    <div>
      {!readOnly && (
        <div className="mb-2 flex flex-wrap gap-1">
          {Object.keys(SNIPPETS).map((k) => (
            <button key={k} type="button" onClick={() => insertSnippet(SNIPPETS[k])}
              className="rounded border border-[var(--border)] bg-[var(--surface-2)] px-2 py-0.5 text-xs text-[var(--text-muted)] hover:bg-[var(--surface-3)]">
              + {k}
            </button>
          ))}
        </div>
      )}

      {/* The whole editor (gutter + textarea) resizes as one block, so the line
          numbers can never desync from the text. */}
      <div
        className="flex overflow-hidden rounded-lg border border-[var(--border)] bg-[var(--bg)] focus-within:border-[var(--accent)]"
        style={{ resize: 'vertical', height: '60vh', minHeight: '320px', maxHeight: '80vh' }}
      >
        <pre
          ref={gutterRef}
          className="m-0 h-full select-none overflow-hidden border-r border-[var(--border)] bg-[var(--surface-2)] px-2 py-3 text-right font-mono text-xs leading-relaxed text-[var(--text-dim)]"
          style={{ minWidth: '3rem' }}
        >
          {Array.from({ length: lineCount }, (_, i) => i + 1).join('\n')}
        </pre>
        <textarea
          ref={taRef}
          value={value}
          onChange={(e) => onChange(e.target.value)}
          onScroll={syncScroll}
          readOnly={readOnly}
          spellCheck={false}
          wrap="off"
          className="h-full w-full resize-none bg-transparent p-3 font-mono text-xs leading-relaxed text-[var(--text)] outline-none"
        />
      </div>

      <div className="mt-2 flex items-start justify-between gap-3">
        <div className={`flex-1 font-mono text-xs ${statusColor}`}>{status}</div>
      </div>

      {parsed.doc && (
        <div className="mt-2 flex flex-wrap gap-1.5">
          {checklist.map((c) => (
            <span key={c.label} className={`rounded px-1.5 py-0.5 text-xs ${c.ok ? 'bg-[var(--ok-weak)] text-[var(--ok)]' : 'bg-[var(--danger-weak)] text-[var(--danger)]'}`}>
              {c.ok ? '✓' : '✗'} {c.label}
            </span>
          ))}
        </div>
      )}
    </div>
  )
}
