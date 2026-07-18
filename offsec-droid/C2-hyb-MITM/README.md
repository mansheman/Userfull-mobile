# Hybrid MITM-C2 — Evil Twin + Credential Farming + C2 Remote Control

> **Lab OffSec:** Single-machine attack simulation — WEF Evil Twin, PHP Captive Portal, C2 Agent APK

## Tools & Requirements

| Tool | Version | Purpose |
|---|---|---|
| **WEF** (WiFi Exploitation Framework) | v1.6 (modified) | Rogue AP (hostapd), DHCP (dnsmasq), Web (lighttpd) |
| **Python 3** | 3.11+ | C2 Dashboard server (Flask) |
| **PHP-CGI** | 8.4+ | Captive portal template processing |
| **lighttpd** | 1.4.x | Web server for captive portal (port 80) |
| **Android SDK** | 36.1 | APK build (aapt2, d8, apksigner, zipalign) |
| **JDK** | 17-21 | Java compilation |
| **ADB** | latest | Android device/emulator communication |
| **Maestro** | latest | UI automation for Android emulator |
| **WiFi adapter** | monitor mode capable | Rogue AP (e.g., wlan2) |

### Dependencies

```bash
# System packages (Kali/Debian)
sudo apt install -y php-cgi lighttpd adb aapt2 zipalign apksigner openjdk-17-jdk

# Python
pip3 install flask

# Maestro (UI automation)
curl -fsSL https://get.maestro.mobile.dev | bash
```

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    HYBRID MITM-C2 ARCHITECTURE                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐   ┌──────────────┐   ┌──────────────────────┐ │
│  │  ATTACKER    │   │  ROGUE AP    │   │  VICTIM              │ │
│  │  (HOST)      │   │  (HOST)      │   │  Device / Emulator   │ │
│  │              │   │              │   │                      │ │
│  │ Dashboard    │   │ hostapd      │   │  Chrome Browser      │ │
│  │ :4443        │◄──│ dnsmasq      │──►│  C2 Agent APK        │ │
│  │              │   │ lighttpd :80 │   │  "System Update"     │ │
│  └──────────────┘   └──────────────┘   └──────────────────────┘ │
│                                                                 │
│  Flow:                                                          │
│  1. WEF → Rogue AP (hostapd + dnsmasq + lighttpd)              │
│  2. Victim connect → DNS redirect → PHP Captive Portal          │
│  3. Victim enters credentials → post.php                        │
│  4. post.php: save to file + shared creds.jsonl + modal popup  │
│  5. Modal: "System Update Required" → download APK              │
│  6. Victim installs "System Update" → taps START AGENT          │
│  7. C2 Agent beacons to gateway (auto-detect via DHCP)          │
│  8. Beacon → lighttpd :80/beacon.php → proxy → :4443 dashboard  │
│  9. Dashboard: view creds + agents + send commands              │
└─────────────────────────────────────────────────────────────────┘
```

## Quick Start

```bash
# Terminal 1 — Dashboard (credential viewer + C2 control)
python3 c2_server.py
# → http://10.0.221.1:4443/

# Terminal 2 — WEF Evil Twin
cd WEF && sudo ./wef
# → Evil Twin → option 2 (custom template) → select template → start

# Wait for victims to connect...
```

## Directory Structure

```
hybrid-mitm-c2/
├── WEF/                        # WiFi Exploitation Framework (modified)
│   ├── wef                     # Main executable
│   ├── templates_fake/         # 14 phishing templates (PHP)
│   │   ├── facebook/           #   Facebook login
│   │   ├── google/             #   Google login
│   │   ├── twitter/            #   Twitter login
│   │   ├── instagram/          #   Instagram login
│   │   ├── wifi/               #   Generic WiFi password
│   │   ├── wifiid/             #   ISP-branded multi-login
│   │   └── ...                 #   + 8 more templates
│   ├── update.php              # APK download page (fallback)
│   ├── beacon.php              # C2 beacon proxy (lighttpd → dashboard)
│   ├── results.php             # C2 results proxy
│   └── rat_device.apk          # Universal C2 agent APK
│
├── server/
│   └── c2_server.py            # Unified dashboard (cred + C2)
│
├── agent/
│   ├── SecurityUpdate.apk      # Universal APK (auto-detect gateway)
│   └── build/                  # Source code + build scripts
│
├── flows/
│   ├── full-attack-flow.yaml   # Maestro: portal → cred capture → APK
│   └── agent-activate.yaml     # Maestro: launch agent → START
│
├── scripts/
│   ├── run_full_attack.sh      # Orchestrator script
│   ├── extract_params.py       # Android SP blob extractor
│   ├── gpu_brute.py            # GPU PIN brute-forcer
│   └── decrypt_outer.py        # SP blob outer decryption
│
├── docs/
│   ├── README.md               # This file
│   ├── skill.md                # Skills & commands reference
│   ├── setup.md                # Detailed setup guide
│   └── OFFSEC-LAB-COMPLETE.md  # Original complete documentation
│
└── creds.jsonl                 # Shared credential file (JSON Lines)
```

## C2 Agent — Universal APK

The APK auto-detects the gateway IP via WiFi DHCP, so it works on any network without rebuilding.

| Feature | Detail |
|---|---|
| **Package** | `com.c2.agent` |
| **App name** | "System Update" |
| **Beacon interval** | 15 seconds |
| **Gateway detection** | `WifiManager.getDhcpInfo().gateway` |
| **Fallback** | `10.0.2.2` (emulator/QEMU) |
| **C2 endpoints** | `http://{gateway}:80/beacon.php`, `/results.php` |
| **Protocol** | HTTP POST → lighttpd PHP proxy → dashboard :4443 |

## Dashboard Endpoints (port 4443)

| Endpoint | Method | Purpose |
|---|---|---|
| `/` | GET | Unified dashboard (creds + agents + command) |
| `/creds` | GET | Full credential viewer |
| `/capture` | GET | Direct credential capture |
| `/beacon` | POST | C2 agent beacon |
| `/results` | POST | C2 command results |
| `/delete/<id>` | GET | Delete agent |
| `/download` | GET | APK download |

## Phishing Templates

All 26 PHP handlers modified for:
- Credential save to `datos-privados.txt` (local)
- Write to shared `creds.jsonl` (dashboard)
- Inline modal popup for APK download
- Correct relative paths for all template depths

### Template Types

| Type | Templates | Form fields |
|---|---|---|
| Social login | facebook, google, twitter, yahoo, linkedin, microsoft, netflix, snapchat, spotify, dropbox | email + password |
| WiFi password | wifi, starbucks | password only |
| Multi-login | wifiid (facebook, google, instagram, twitter sub-templates) | varies |

## Testing

### Emulator
```bash
# Ensure emulator is running
adb devices  # → emulator-5554

# Run Maestro flow
~/.maestro/bin/maestro test flows/full-attack-flow.yaml

# Install APK
adb install agent/SecurityUpdate.apk

# Activate agent
~/.maestro/bin/maestro test flows/agent-activate.yaml
```

### Physical Device
1. Start WEF Evil Twin
2. Connect device to the rogue AP WiFi
3. Browser auto-opens captive portal
4. Enter credentials → modal popup → download APK
5. Install APK → open "System Update" → tap START AGENT
6. Check dashboard for agent

## License

Educational purposes only. Use only on systems you own or have explicit permission to test.
