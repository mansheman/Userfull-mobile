import { createContext, useContext, useEffect, useRef, useState, useCallback } from 'react'
import { getToken } from './api'

// LiveProvider keeps a single Server-Sent-Events connection to /api/events open
// while the user is authenticated. It uses fetch streaming (not EventSource) so
// the Bearer token travels in the Authorization header rather than the URL.
//
// Components subscribe to event types via useLiveEvent(type, handler).

const LiveContext = createContext(null)

function parseSSE(buffer, onEvent) {
  // returns the unconsumed tail; calls onEvent({event, data}) per complete frame
  let idx
  while ((idx = buffer.indexOf('\n\n')) >= 0) {
    const frame = buffer.slice(0, idx)
    buffer = buffer.slice(idx + 2)
    let event = 'message'
    const dataLines = []
    for (const line of frame.split('\n')) {
      if (line.startsWith(':')) continue // comment / keepalive
      if (line.startsWith('event:')) event = line.slice(6).trim()
      else if (line.startsWith('data:')) dataLines.push(line.slice(5).replace(/^ /, ''))
    }
    if (dataLines.length) {
      let data = dataLines.join('\n')
      try { data = JSON.parse(data) } catch { /* leave as string */ }
      onEvent({ event, data })
    }
  }
  return buffer
}

export function LiveProvider({ children }) {
  const [connected, setConnected] = useState(false)
  const subsRef = useRef(new Map()) // type -> Set<cb>

  const subscribe = useCallback((type, cb) => {
    const map = subsRef.current
    if (!map.has(type)) map.set(type, new Set())
    map.get(type).add(cb)
    return () => {
      const set = map.get(type)
      if (set) { set.delete(cb); if (set.size === 0) map.delete(type) }
    }
  }, [])

  const dispatch = useCallback((evt) => {
    const fire = (set) => set && set.forEach((cb) => { try { cb(evt.data, evt) } catch {} })
    fire(subsRef.current.get(evt.event))
    fire(subsRef.current.get('*'))
  }, [])

  useEffect(() => {
    let aborted = false
    let controller = null
    let backoff = 1000

    async function connect() {
      if (aborted) return
      const token = getToken()
      if (!token) { setTimeout(connect, 1000); return }
      controller = new AbortController()
      try {
        const res = await fetch('/api/events', {
          headers: { Authorization: 'Bearer ' + token, Accept: 'text/event-stream' },
          signal: controller.signal,
        })
        if (!res.ok || !res.body) throw new Error('events stream failed: ' + res.status)
        setConnected(true)
        backoff = 1000
        const reader = res.body.getReader()
        const decoder = new TextDecoder()
        let buffer = ''
        while (!aborted) {
          const { value, done } = await reader.read()
          if (done) break
          buffer += decoder.decode(value, { stream: true })
          buffer = parseSSE(buffer, dispatch)
        }
      } catch (e) {
        // network drop, server restart, etc. — fall through to reconnect
      } finally {
        setConnected(false)
        if (!aborted) {
          setTimeout(connect, backoff)
          backoff = Math.min(backoff * 2, 15000)
        }
      }
    }

    connect()
    return () => { aborted = true; if (controller) controller.abort() }
  }, [dispatch])

  return (
    <LiveContext.Provider value={{ connected, subscribe }}>
      {children}
    </LiveContext.Provider>
  )
}

export function useLive() {
  return useContext(LiveContext) || { connected: false, subscribe: () => () => {} }
}

// useLiveEvent registers a handler for a given event type for the component's
// lifetime. The handler is kept in a ref so changing it doesn't reopen the sub.
export function useLiveEvent(type, handler) {
  const { subscribe } = useLive()
  const ref = useRef(handler)
  ref.current = handler
  useEffect(() => {
    return subscribe(type, (data, evt) => ref.current && ref.current(data, evt))
  }, [type, subscribe])
}
