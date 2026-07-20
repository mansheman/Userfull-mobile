// Lightweight API client. Token is kept in localStorage and sent as a Bearer
// header. All calls return parsed JSON or throw an Error with the server message.

const TOKEN_KEY = 'evilginx_dash_token'

export function getToken() {
  return localStorage.getItem(TOKEN_KEY) || ''
}
export function setToken(t) {
  if (t) localStorage.setItem(TOKEN_KEY, t)
  else localStorage.removeItem(TOKEN_KEY)
}

async function request(method, path, body) {
  const headers = {}
  const token = getToken()
  if (token) headers['Authorization'] = 'Bearer ' + token
  let opts = { method, headers }
  if (body !== undefined) {
    headers['Content-Type'] = 'application/json'
    opts.body = JSON.stringify(body)
  }
  const res = await fetch('/api' + path, opts)
  if (res.status === 401) {
    setToken('')
    if (!path.startsWith('/auth/login')) {
      window.dispatchEvent(new Event('evilginx-unauthorized'))
    }
  }
  const text = await res.text()
  let data = null
  try { data = text ? JSON.parse(text) : null } catch { data = text }
  if (!res.ok) {
    const msg = (data && data.error) || res.statusText || 'request failed'
    throw new Error(msg)
  }
  return data
}

export const api = {
  login: (username, password) => request('POST', '/auth/login', { username, password }),
  me: () => request('GET', '/auth/me'),
  changePassword: (old_password, new_password) =>
    request('POST', '/auth/password', { old_password, new_password }),

  stats: () => request('GET', '/stats'),
  config: () => request('GET', '/config'),
  updateConfig: (patch) => request('PATCH', '/config', patch),

  sessions: (params = {}) => {
    const qs = new URLSearchParams(params).toString()
    return request('GET', '/sessions' + (qs ? '?' + qs : ''))
  },
  session: (id) => request('GET', '/sessions/' + id),
  deleteSession: (id) => request('DELETE', '/sessions/' + id),
  exportSessionsUrl: (params = {}) => {
    const qs = new URLSearchParams(params).toString()
    return '/api/sessions/export' + (qs ? '?' + qs : '')
  },

  lures: () => request('GET', '/lures'),
  createLure: (l) => request('POST', '/lures', l),
  updateLure: (i, l) => request('PUT', '/lures/' + i, l),
  deleteLure: (i) => request('DELETE', '/lures/' + i),
  lureUrl: (i, params) => request('POST', '/lures/' + i + '/url', { params: params || {} }),
  pauseLure: (i, duration) => request('POST', '/lures/' + i + '/pause', { duration }),
  unpauseLure: (i) => request('POST', '/lures/' + i + '/unpause'),

  phishlets: () => request('GET', '/phishlets'),
  enablePhishlet: (name) => request('POST', '/phishlets/' + encodeURIComponent(name) + '/enable'),
  disablePhishlet: (name) => request('POST', '/phishlets/' + encodeURIComponent(name) + '/disable'),
  phishletYaml: (name) => request('GET', '/phishlets/' + encodeURIComponent(name) + '/yaml'),
  savePhishletYaml: (name, yaml) => request('PUT', '/phishlets/' + encodeURIComponent(name) + '/yaml', { yaml }),
  importPhishlet: (name, yaml) => request('POST', '/phishlets/import', { name, yaml }),
  validatePhishlet: (yaml) => request('POST', '/phishlets/validate', { yaml }),
  setPhishletHostname: (name, hostname) => request('POST', '/phishlets/' + encodeURIComponent(name) + '/hostname', { hostname }),
  hidePhishlet: (name) => request('POST', '/phishlets/' + encodeURIComponent(name) + '/hide'),
  unhidePhishlet: (name) => request('POST', '/phishlets/' + encodeURIComponent(name) + '/unhide'),
  setPhishletUnauthUrl: (name, unauth_url) => request('POST', '/phishlets/' + encodeURIComponent(name) + '/unauth_url', { unauth_url }),
  phishletHosts: (name) => request('GET', '/phishlets/' + encodeURIComponent(name) + '/hosts'),
  createChildPhishlet: (name, parent, params) => request('POST', '/phishlets/child', { name, parent, params }),
  deletePhishlet: (name) => request('DELETE', '/phishlets/' + encodeURIComponent(name)),

  engines: () => request('GET', '/engines'),
  createEngine: (e) => request('POST', '/engines', e),
  updateEngine: (id, patch) => request('PATCH', '/engines/' + encodeURIComponent(id), patch),
  deleteEngine: (id) => request('DELETE', '/engines/' + encodeURIComponent(id)),
  checkEngine: (id) => request('GET', '/engines/' + encodeURIComponent(id) + '/check'),

  blacklist: () => request('GET', '/blacklist'),
  blacklistAdd: (entry) => request('POST', '/blacklist', { entry }),
  blacklistRemove: (entry) => request('POST', '/blacklist/remove', { entry }),
  blacklistImport: (entries) => request('POST', '/blacklist/import', { entries }),
  blacklistClear: () => request('POST', '/blacklist/clear'),

  users: () => request('GET', '/users'),
  createUser: (u) => request('POST', '/users', u),
  updateUser: (name, patch) => request('PATCH', '/users/' + encodeURIComponent(name), patch),
  deleteUser: (name) => request('DELETE', '/users/' + encodeURIComponent(name)),

  audit: (limit = 200) => request('GET', '/audit?limit=' + limit),
}

// For authenticated file downloads we need to attach the token; fetch as blob.
export async function downloadFile(url, filename) {
  const res = await fetch(url, { headers: { Authorization: 'Bearer ' + getToken() } })
  if (!res.ok) throw new Error('download failed')
  const blob = await res.blob()
  const a = document.createElement('a')
  a.href = URL.createObjectURL(blob)
  a.download = filename
  document.body.appendChild(a)
  a.click()
  a.remove()
  URL.revokeObjectURL(a.href)
}
