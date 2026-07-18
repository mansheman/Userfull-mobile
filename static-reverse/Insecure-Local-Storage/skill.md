---
name: Android-Insecure-Local-Storage-Lab
description: >
  Lab 3 — Insecure Local Storage pada aplikasi Android InsecureBankv2.
  Mencakup analisis penyimpanan data sensitif di SharedPreferences (CWE-312),
  eksploitasi hardcoded encryption key (CWE-321), identificasi MODE_WORLD_READABLE
  (CWE-200), serta proof-of-concept ekstraksi kredensial via ADB (run-as, adb backup,
  root access). Gunakan saat user menyebut "lab insecure storage", "lab 3 insecurebankv2",
  "sharedpreferences exploit", "MODE_WORLD_READABLE", atau "android storage vulnerability".
allowed-tools: "Bash(jadx:*) Bash(adb:*) Bash(java:*) Grep Read Edit Write Glob"
metadata:
  author: "Mobile Security Lab"
  version: "1.0.0"
  category: mobile-security
  tags:
    - android
    - insecure-storage
    - cwe-312
    - cwe-321
    - sharedpreferences
    - mode-world-readable
    - hardcoded-key
    - insecurebankv2
    - lab-pentest
---

# Lab 3: Insecure Local Storage

## Overview

Lab ini mendemonstrasikan celah **insecure local storage** pada aplikasi Android **InsecureBankv2**. Aplikasi menyimpan kredensial pengguna di **SharedPreferences** dengan proteksi yang sangat lemah:
- Username: hanya Base64 (bukan enkripsi — CWE-312)
- Password: AES-256 dengan **kunci hardcoded** di source code (CWE-321) dan IV all-zeros (CWE-325)
- File preferences dibuka dengan **MODE_WORLD_READABLE** (CWE-200)
- Flag `debuggable=true` + `allowBackup=true` menyediakan jalur ekstraksi tambahan

## Prerequisites

| Tool | Min Version | Cek |
|------|-------------|-----|
| Java JDK | 17+ | `java -version` |
| JADX | 1.5+ | `jadx --version` |
| ADB | 34+ | `adb version` |

## Alur Lab

### Fase 0 — Persiapan & Dekompilasi

```powershell
$env:JAVA_HOME = "C:\jdk-17"
& "C:\jadx\bin\jadx.bat" -d decompiled InsecureBankv2.apk
```

### Fase 1 — Static Analysis

**A. Cari semua penggunaan SharedPreferences:**

```powershell
$sources = "decompiled\sources\com\android\insecurebankv2"
Get-ChildItem -LiteralPath $sources -Filter "*.java" |
    Select-String -Pattern 'getSharedPreferences|SharedPreferences|MODE_WORLD'
```

**B. File target yang harus dianalisis:**

| File | Baris Kunci | Fungsi | Temuan |
|------|-------------|--------|--------|
| `DoLogin.java` | 140-149 | `saveCreds()` | Write credentials |
| `CryptoClass.java` | 22-23 | Key + IV | Hardcoded secret |
| `MyBroadCastReceiver.java` | 21 | MODE_WORLD_READABLE | World-readable prefs |
| `LoginActivity.java` | 74-88 | `fillData()` | Read + auto-fill credentials |
| `DoTransfer.java` | 104-112 | prefs read | Read credentials for transaction |

### Fase 2 — Analysis Data Protection Strength

| Data Element | Storage Method | Effective Security | CWE |
|-------------|---------------|-------------------|-----|
| Username | Base64 | NONE — encoding, not encryption | CWE-312 |
| Password | AES-256/CBC | BREAKABLE — key hardcoded in APK | CWE-321 |
| IV | `{0,0,...,0}` | NONE — static, predictable | CWE-325 |
| File mode | MODE_WORLD_READABLE | NONE — other apps can read | CWE-200 |

### Fase 3 — Exploit Vectors (Dynamic Analysis)

**Vector A: run-as (No Root Required)**

Prasyarat: `android:debuggable="true"`

```powershell
adb shell run-as com.android.insecurebankv2 cat \
    /data/data/com.android.insecurebankv2/shared_prefs/mySharedPreferences.xml
```

**Vector B: adb backup**

Prasyarat: `android:allowBackup="true"`

```powershell
adb backup -f backup.ab com.android.insecurebankv2
```

**Vector C: Root Access / MODE_WORLD_READABLE**

Prasyarat: Perangkat rooted ATAU targetSdkVersion < 24

```powershell
adb shell
su
cat /data/data/com.android.insecurebankv2/shared_prefs/mySharedPreferences.xml
```

### Fase 4 — Post-Exploitation (Decrypt Password)

Setelah mengekstrak SharedPreferences XML:

1. Decode username (Base64 → plaintext)
2. Extract AES key dari source code (`"This is the super secret key 123"`)
3. Decrypt password menggunakan AES-256/CBC dengan IV all-zeros

---

## Decision Rules

| Rule | Condition | Action |
|------|-----------|--------|
| 1 | `getSharedPreferences(..., 1)` ditemukan | Report MODE_WORLD_READABLE sebagai High |
| 2 | `Base64.encodeToString()` digunakan untuk kredensial | Report CWE-312 sebagai High |
| 3 | Kunci enkripsi hardcoded dalam source | Report CWE-321 sebagai Critical |
| 4 | `debuggable="true"` di production build | Report CWE-215 sebagai Medium |
| 5 | `allowBackup="true"` tanpa proteksi | Report CWE-200 sebagai Medium |

## Confidence Levels

| Level | Kapan Digunakan |
|-------|----------------|
| **Confirmed** | Static analysis code evidence + Dynamic PoC berhasil via ADB |
| **Likely** | Static analysis jelas tapi dynamic test terbatas |
| **Needs Dynamic Confirmation** | Memerlukan runtime test spesifik |

## Finding Template

```markdown
## [ID] - [Title]
**Confidence**: [Confirmed/Likely]
**Severity**: [Critical/High/Medium] (CVSS 4.0: [X.X])
**CWE**: CWE-[XXX]

### Description
### Affected Components
### Code Evidence
### Storage Evidence
### Attack Scenario
### Proof of Concept
### Remediation
```

---

## References

- [CWE-312: Cleartext Storage of Sensitive Information](https://cwe.mitre.org/data/definitions/312.html)
- [CWE-321: Use of Hard-coded Cryptographic Key](https://cwe.mitre.org/data/definitions/321.html)
- [CWE-200: Exposure of Sensitive Information to an Unauthorized Actor](https://cwe.mitre.org/data/definitions/200.html)
- [Android MODE_WORLD_READABLE (deprecated)](https://developer.android.com/reference/android/content/Context#MODE_WORLD_READABLE)
- [Android EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences)
- [Android Keystore System](https://developer.android.com/privacy-and-security/cryptography#android-keystore)
- [OWASP Mobile Top 10](https://owasp.org/www-project-mobile-top-10/)
- [Android-InsecureBankv2](https://github.com/dineshshetty/Android-InsecureBankv2)

## Struktur Direktori Lab

```
Insecure-Local-Storage/
├── decompiled/             # Hasil dekompilasi JADX
│   ├── resources/
│   │   └── AndroidManifest.xml
│   └── sources/
│       └── com/android/insecurebankv2/
│           ├── DoLogin.java           (saveCreds:139-150)
│           ├── CryptoClass.java        (hardcoded key:22-23)
│           ├── MyBroadCastReceiver.java (MODE_WORLD_READABLE:21)
│           ├── LoginActivity.java      (fillData:73-97)
│           └── DoTransfer.java         (reads prefs)
├── evidence/               # Screenshot, command log, analisis
│   ├── bypass_commands.txt
│   ├── hardcoded_sharedprefs.txt
│   ├── env_info.txt
│   ├── login_screen.png
│   └── postlogin_bypass.png
├── reports/                # Laporan temuan
│   └── report.md
├── skill.md               # File ini
└── prompt.txt              # Prompt eksekusi lab
```
