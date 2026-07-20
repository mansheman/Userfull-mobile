import { createContext, useContext, useEffect, useState, useCallback } from 'react'
import { api, getToken, setToken } from './api'

const AuthContext = createContext(null)

const ROLE_RANK = { viewer: 1, operator: 2, admin: 3 }

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  const logout = useCallback(() => {
    setToken('')
    setUser(null)
  }, [])

  useEffect(() => {
    let active = true
    async function boot() {
      if (!getToken()) { setLoading(false); return }
      try {
        const me = await api.me()
        if (active) setUser(me)
      } catch {
        setToken('')
      } finally {
        if (active) setLoading(false)
      }
    }
    boot()
    const onUnauth = () => { setUser(null) }
    window.addEventListener('evilginx-unauthorized', onUnauth)
    return () => { active = false; window.removeEventListener('evilginx-unauthorized', onUnauth) }
  }, [])

  const login = useCallback(async (username, password) => {
    const res = await api.login(username, password)
    setToken(res.token)
    setUser(res.user)
    return res.user
  }, [])

  const hasRole = useCallback((min) => {
    if (!user) return false
    return (ROLE_RANK[user.role] || 0) >= (ROLE_RANK[min] || 0)
  }, [user])

  return (
    <AuthContext.Provider value={{ user, setUser, loading, login, logout, hasRole }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}
