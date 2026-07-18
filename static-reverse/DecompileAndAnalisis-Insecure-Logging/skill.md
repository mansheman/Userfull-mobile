---
name: Android-Insecure-Logging-Lab
description: >
  Lab 1 — Dekompilasi & Analisis Insecure Logging pada aplikasi Android.
  Mencakup reverse engineering APK dengan JADX-GUI, identifikasi celah
  logging data sensitif (CWE-532) via Log.d() dan System.out.println,
  serta proof-of-concept eksploitasi melalui ADB logcat.
  Gunakan saat user menyebut "lab insecure logging", "lab 1 insecurebankv2",
  "decompile APK", "cari Log.d", atau "android logging vulnerability".
allowed-tools: "Bash(jadx:*) Bash(adb:*) Bash(java:*) Grep Read Edit Write Glob"
metadata:
  author: "Mobile Security Lab"
  version: "1.0.0"
  category: mobile-security
  tags:
    - android
    - insecure-logging
    - cwe-532
    - reverse-engineering
    - jadx
    - logcat
    - insecurebankv2
    - lab-pentest
---

# Lab 1: Dekompilasi & Analisis Insecure Logging

## Overview

Lab ini mendemonstrasikan celah insecure logging pada aplikasi Android finansial **InsecureBankv2**. Aplikasi secara tidak sengaja mencetak data sensitif (username, password, nomor rekening, nominal transfer) ke Logcat melalui `Log.d()` dan `System.out.println`. Data ini dapat dibaca oleh pihak tidak berwenang melalui akses ADB.

## Prerequisites

| Tool | Min Version | Cek |
|------|-------------|-----|
| Java JDK | 17+ | `java -version` |
| JADX | 1.5+ | `jadx --version` |
| ADB | 34+ | `adb version` |

## Alur Lab

### Fase 0 — Persiapan & Dekompilasi

```powershell
# Verifikasi tools
java -version
jadx --version
adb version

# Dekompilasi APK
jadx --no-res -d jadx_output InsecureBankv2.apk
```

### Fase 1 — Identifikasi Celah (Static Analysis)

```powershell
# Via JADX-GUI:
# 1. Buka JADX-GUI: jadx-gui.bat InsecureBankv2.apk
# 2. Navigasi: Source code → com.android.insecurebankv2
# 3. Ctrl+Shift+F → cari: Log.d(   atau   System.out.println

# Via CLI grep:
grep -rn "Log\.d(" --include="*.java" jadx_output/sources/com/android/insecurebankv2/
grep -rn "System\.out\.println" --include="*.java" jadx_output/sources/com/android/insecurebankv2/
```

### Fase 2 — Analisis Temuan

File yang teridentifikasi mengandung insecure logging:

| File | Baris | Method | Data Terekspos |
|------|-------|--------|----------------|
| `DoTransfer.java` | 159, 178 | `System.out.println` | From, To, Amount |
| `DoLogin.java` | 115 | `Log.d` | username, password |
| `ChangePassword.java` | 69, 128, 168 | `System.out.println` | password baru, phone |
| `MyBroadCastReceiver.java` | 31 | `System.out.println` | phonenumber, password |

### Fase 3 — Eksploitasi (Dynamic Analysis)

```powershell
# 1. Pastikan emulator/perangkat terhubung
adb devices

# 2. Monitor log real-time untuk data transfer
adb logcat | findstr "Message:"

# 3. Monitor log untuk kredensial login
adb logcat | findstr "Successful Login"

# 4. Monitor log untuk password change
adb logcat | findstr "newpassword="
```

---

## Decision Rules

| Rule | Condition | Action |
|------|-----------|--------|
| 1 | `Log.d()` ditemukan di namespace app | Report sebagai **Confirmed** |
| 2 | `System.out.println()` ditemukan mencetak user input | Report sebagai **Confirmed** |
| 3 | Log di library (non-app namespace) | Abaikan — bukan celah app |
| 4 | Log level VERBOSE/DEBUG dalam production | Eskalasi severity |

## Confidence Levels

| Level | Kapan Digunakan |
|-------|----------------|
| **Confirmed** | Direct call dari code app ke `Log.d()`/`System.out.println()` dengan argumen sensitif |
| **Likely** | Ada log tapi perlu tracing sumber data |
| **Needs Dynamic Confirmation** | Perlu runtime test via logcat |

## Finding Template

```markdown
## [ID] - [Title]
**Confidence**: [Confirmed/Likely/Needs Dynamic Confirmation]
**Severity**: [Critical/High/Medium/Low] (CVSS: [X.X])
**CWE**: CWE-532

### Description
### Affected Components
### Code Evidence
### Attack Scenario
### Proof of Concept
### Remediation
```

---

## References

- [CWE-532: Insertion of Sensitive Information into Log File](https://cwe.mitre.org/data/definitions/532.html)
- [Android Log Best Practices](https://developer.android.com/reference/android/util/Log)
- [Android-InsecureBankv2](https://github.com/dineshshetty/Android-InsecureBankv2)
- [OWASP Mobile Top 10](https://owasp.org/www-project-mobile-top-10/)

## Struktur Direktori Lab

```
Mobile-userfull/
├── apk/                    # APK target
│   └── InsecureBankv2.apk
├── decompiled/             # Hasil dekompilasi JADX
│   └── sources/
│       └── com/android/insecurebankv2/
├── evidence/               # Screenshot, log file, bukti PoC
├── reports/                # Laporan temuan
│   └── report.md
├── tools/                  # Dokumentasi tools
├── skill.md               # File ini
└── prompt.txt              # Prompt eksekusi lab
```
