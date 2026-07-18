# Lab 1: Insecure Logging — Vulnerability Assessment Report

**Target**: InsecureBankv2 (`com.android.insecurebankv2`)  
**Date**: 2026-07-13  
**Methodology**: Static Analysis via JADX Decompilation + Grep  
**CWE**: CWE-532 — Insertion of Sensitive Information into Log File

---

## Executive Summary

Aplikasi **InsecureBankv2** (`com.android.insecurebankv2`) mengandung celah **insecure logging** (CWE-532) di mana data sensitif nasabah — termasuk username, password (old + new), nomor telepon, nomor rekening, nominal transfer, dan path file statemen — dicetak ke **Logcat** dan **System.out** secara plaintext via `Log.d()` dan `System.out.println`. Data ini dapat dibaca oleh siapa pun yang memiliki akses ADB ke perangkat, mengekspos informasi finansial dan kredensial pengguna ke pihak tidak berwenang.

**Total Findings**: 5 (Critical: 1, High: 3, Medium: 1)

---

## Findings

### Finding 1: Insecure Logging — Data Transfer via System.out.println

| Field | Value |
|-------|-------|
| **ID** | ISB-LOG-001 |
| **Confidence** | Confirmed |
| **Severity** | High (CVSS 4.0: 7.5) |
| **CWE** | CWE-532 |
| **OWASP MASVS** | MASVS-STORAGE-1 |

#### Description

Aplikasi mencetak detail transfer (nomor rekening pengirim, penerima, dan nominal) ke `System.out.println` yang outputnya masuk ke Logcat dan dapat dibaca via ADB.

#### Affected Components

- **File**: `com/android/insecurebankv2/DoTransfer.java`
- **Lines**: 159, 178

#### Code Evidence — Transfer Sukses (Line 159)

```java
System.out.println("Message:" + DoTransfer.this.jsonObject.getString("message") +
    " From:" + DoTransfer.this.from.getText().toString() +
    " To:" + DoTransfer.this.to.getText().toString() +
    " Amount:" + DoTransfer.this.amount.getText().toString());
```

#### Code Evidence — Transfer Gagal (Line 178)

```java
System.out.println("Message:Failure From:" + DoTransfer.this.from.getText().toString() +
    " To:" + DoTransfer.this.to.getText().toString() +
    " Amount:" + DoTransfer.this.amount.getText().toString());
```

#### Data Terekspos

| Data | Content |
|------|---------|
| From | Nomor rekening pengirim |
| To | Nomor rekening penerima |
| Amount | Nominal transfer |

#### Attack Scenario

1. Attacker memperoleh akses ADB ke perangkat korban (USB debugging aktif)
2. Attacker memonitor Logcat secara real-time: `adb logcat \| findstr "Message:"`
3. Korban melakukan transfer dana melalui aplikasi
4. Data `From`, `To`, `Amount` tercetak ke log dan tertangkap oleh attacker

#### Proof of Concept

```powershell
# Pastikan emulator/perangkat terhubung
adb devices

# Monitor log real-time dan filter data transfer
adb logcat | findstr "Message:"
```

#### Impact

| CIA Triad | Level |
|-----------|-------|
| Confidentiality | High — nomor rekening dan nominal transfer terekspos |
| Integrity | None |
| Availability | None |

#### Remediation

```java
// Hapus System.out.println data sensitif di production build
if (BuildConfig.DEBUG) {
    Log.d("Transfer", "Transfer processed successfully");
}

// ProGuard/R8 rule:
// -assumenosideeffects class java.io.PrintStream {
//     public void println(java.lang.String);
// }
```

---

### Finding 2: Insecure Logging — Credentials via Log.d

| Field | Value |
|-------|-------|
| **ID** | ISB-LOG-002 |
| **Confidence** | Confirmed |
| **Severity** | Critical (CVSS 4.0: 8.8) |
| **CWE** | CWE-532 |
| **OWASP MASVS** | MASVS-STORAGE-1 |

#### Description

Aplikasi mencetak username dan password pengguna yang berhasil login ke `Log.d()`. Ini adalah temuan paling kritis karena mengekspos kredensial plaintext yang dapat digunakan untuk takeover akun.

#### Affected Components

- **File**: `com/android/insecurebankv2/DoLogin.java`
- **Line**: 115

#### Code Evidence

```java
Log.d("Successful Login:", ", account=" + DoLogin.this.username + ":" + DoLogin.this.password);
```

#### Data Terekspos

| Data | Content |
|------|---------|
| username | Username pengguna yang login |
| password | Password plaintext |

#### Attack Scenario

1. Attacker dengan akses ADB menjalankan `adb logcat \| findstr "Successful Login"`
2. Korban melakukan login ke aplikasi
3. Username dan password plaintext tertangkap di log attacker
4. Attacker dapat menggunakan kredensial tersebut untuk login dan melakukan transaksi ilegal

#### Proof of Concept

```powershell
adb logcat | findstr "Successful Login"
```

#### Impact

| CIA Triad | Level |
|-----------|-------|
| Confidentiality | Critical — kredensial login plaintext terekspos |
| Integrity | High — attacker dapat melakukan transaksi dengan kredensial curian |
| Availability | None |

#### Remediation

```java
// Jangan pernah log kredensial
Log.d(TAG, "Login successful for user: " + userId);
// Gunakan user ID atau token, bukan password
```

---

### Finding 3: Insecure Logging — Change Password via System.out.println

| Field | Value |
|-------|-------|
| **ID** | ISB-LOG-003 |
| **Confidence** | Confirmed |
| **Severity** | High (CVSS 4.0: 7.5) |
| **CWE** | CWE-532 |
| **OWASP MASVS** | MASVS-STORAGE-1 |

#### Description

Aplikasi mencetak username (di-labeli "newpassword") dan nomor telepon ke `System.out.println` saat proses change password.

#### Affected Components

- **File**: `com/android/insecurebankv2/ChangePassword.java`
- **Lines**: 69, 128, 168

#### Code Evidence — Line 69 (onCreate)

```java
System.out.println("newpassword=" + this.uname);
```

#### Code Evidence — Line 128 (phone number leak)

```java
System.out.println("phonno:" + phoneNumber);
```

#### Code Evidence — Line 168 (informational, non-sensitive)

```java
System.out.println("Phone number Invalid.");
```

#### Data Terekspos

| Line | Data |
|------|------|
| 69 | Username (misleadingly labeled "newpassword") |
| 128 | Nomor telepon pengguna (via TelephonyManager) |

#### Attack Scenario

1. Attacker menjalankan `adb logcat \| findstr "newpassword="` atau `adb logcat \| findstr "phonno:"`
2. Korban mengakses halaman change password
3. Username dan nomor telepon tercetak di log

#### Proof of Concept

```powershell
adb logcat | findstr "newpassword="
adb logcat | findstr "phonno:"
```

#### Remediation

```java
// Hapus semua System.out.println dari ChangePassword.java
if (BuildConfig.DEBUG) {
    Log.d(TAG, "Change password initiated for user");
}
```

---

### Finding 4: Insecure Logging — BroadcastReceiver via System.out.println

| Field | Value |
|-------|-------|
| **ID** | ISB-LOG-004 |
| **Confidence** | Confirmed |
| **Severity** | High (CVSS 4.0: 8.1) |
| **CWE** | CWE-532 |
| **OWASP MASVS** | MASVS-STORAGE-1 |

#### Description

`MyBroadCastReceiver` menangkap intent broadcast yang berisi nomor telepon dan password baru, lalu mencetak seluruh data — termasuk password lama yang didekripsi dan password baru — ke `System.out.println`.

#### Affected Components

- **File**: `com/android/insecurebankv2/MyBroadCastReceiver.java`
- **Lines**: 31, 39

#### Code Evidence — Line 31 (sensitive)

```java
System.out.println("For the changepassword - phonenumber: " + textPhoneno +
    " password is: " + textMessage);
```

Di mana `textMessage` berisi:
```java
"Updated Password from: " + decryptedPassword + " to: " + newpass
```

#### Data Terekspos

| Data | Content |
|------|---------|
| textPhoneno | Nomor telepon target SMS |
| decryptedPassword | Password lama (didekripsi dari SharedPreferences) |
| newpass | Password baru (dari intent broadcast) |

#### Attack Scenario

1. Attacker menjalankan `adb logcat \| findstr "phonenumber"`
2. Korban melakukan change password (yang memicu broadcast `theBroadcast`)
3. `MyBroadCastReceiver` menerima intent dan mencetak: nomor telepon + password lama + password baru
4. Kedua password (lama dan baru) terekspos bersamaan

#### Proof of Concept

```powershell
adb logcat | findstr "phonenumber"
```

#### Remediation

```java
// Hapus System.out.println dari BroadcastReceiver
// BroadcastReceiver seharusnya tidak melakukan logging apapun
```

---

### Finding 5: Insecure Logging — File Path Disclosure via System.out.println

| Field | Value |
|-------|-------|
| **ID** | ISB-LOG-005 |
| **Confidence** | Confirmed |
| **Severity** | Medium (CVSS 4.0: 5.3) |
| **CWE** | CWE-532 |
| **OWASP MASVS** | MASVS-STORAGE-1 |

#### Description

`ViewStatement` mencetak full path file statemen bank ke `System.out.println`, yang mencakup username dan lokasi penyimpanan eksternal (`/sdcard/`). Ini membocorkan informasi struktur file system yang dapat digunakan untuk eksploitasi lebih lanjut (path traversal, file manipulation).

#### Affected Components

- **File**: `com/android/insecurebankv2/ViewStatement.java`
- **Line**: 26

#### Code Evidence

```java
String FILENAME = "Statements_" + this.uname + ".html";
File fileToCheck = new File(Environment.getExternalStorageDirectory(), FILENAME);
System.out.println(fileToCheck.toString());
```

#### Data Terekspos

| Data | Content |
|------|---------|
| fileToCheck | Full path: `/sdcard/Statements_{username}.html` |
| username | Terkandung dalam nama file |

#### Attack Scenario

1. Attacker menjalankan `adb logcat \| findstr "Statements_"`
2. Korban membuka halaman View Statement
3. Attacker mengetahui lokasi dan nama file statemen di external storage
4. Attacker dapat mengakses/memanipulasi file statemen tersebut

#### Proof of Concept

```powershell
adb logcat | findstr "Statements_"
```

#### Remediation

```java
// Jangan log path file
// Gunakan internal storage atau scoped storage untuk file sensitif
```

---

## Remediation Summary

| ID | File | Fix |
|----|------|-----|
| ISB-LOG-001 | DoTransfer.java | Hapus `System.out.println`, gunakan guarded `Log.d` hanya di DEBUG |
| ISB-LOG-002 | DoLogin.java | Hapus `Log.d` kredensial, ganti log dengan non-sensitive user ID |
| ISB-LOG-003 | ChangePassword.java | Hapus `System.out.println` password & phone number |
| ISB-LOG-004 | MyBroadCastReceiver.java | Hapus semua `System.out.println` |
| ISB-LOG-005 | ViewStatement.java | Hapus `System.out.println` file path |

### Global Remediation

```java
// 1. Gunakan wrapper logger yang terproteksi build variant
public class AppLogger {
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }
}

// 2. ProGuard/R8 rule untuk strip semua log di release build
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

// 3. Jangan gunakan System.out.println di production Android app
// 4. Gunakan obfuscation (ProGuard/R8) pada release build
```

---

## Coverage Statement

```
Coverage Analysis:
- Static Analysis: Complete (all 1,738 classes decompiled)
- Dynamic Analysis: Pending (requires emulator/device with AVD running)
- Scope: com.android.insecurebankv2.* namespace (app code only)
- Excluded: com.google.*, android.*, org.apache.*, 3rd-party libraries
- Framework: Native Android (no Flutter, React Native, Xamarin)
- Obfuscation: None (plain source code)

Files Scanned:
  - com/android/insecurebankv2/*.java (all app source files)

Search Patterns:
  1. "Log\.d\(" — found 1 match in app namespace
  2. "System\.out\.println" — found 8 matches in app namespace
     (6 sensitive + 2 informational/non-sensitive)

Limitations:
- Dynamic testing (logcat PoC) requires AVD emulator running with app installed
- Native code analysis: N/A (no .so libraries relevant to logging)
- Obfuscated builds: N/A (APK not obfuscated)

Total Findings: 5 (Critical: 1, High: 3, Medium: 1)
```

---

## Tools Used

| Tool | Version | Path |
|------|---------|------|
| Java JDK (Temurin) | 17.0.14 | `C:\jdk-17` |
| JADX | 1.5.3 | `C:\jadx` |
| ADB | 37.0.0 | `C:\Users\LENOVO\AppData\Local\Android\Sdk\platform-tools` |
| grep (PowerShell Select-String) | — | Built-in |

---

## References

- [CWE-532: Insertion of Sensitive Information into Log File](https://cwe.mitre.org/data/definitions/532.html)
- [OWASP MASVS-STORAGE-1](https://mas.owasp.org/MASVS/06-MASVS-STORAGE/)
- [Android Log Best Practices](https://developer.android.com/reference/android/util/Log)
- [OWASP Mobile Top 10 (2024): M8 — Security Misconfiguration](https://owasp.org/www-project-mobile-top-10/)
- [Android-InsecureBankv2 Repository](https://github.com/dineshshetty/Android-InsecureBankv2)

---

## Appendix: Complete Grep Results

### Log.d() — 1 match

```
DoLogin.java:115: Log.d("Successful Login:", ", account=" + DoLogin.this.username + ":" + DoLogin.this.password);
```

### System.out.println() — 8 matches (6 sensitive + 2 informational)

| # | File | Line | Content | Sensitive |
|---|------|------|---------|-----------|
| 1 | DoTransfer.java | 159 | `"Message:" + message + " From:" + from + " To:" + to + " Amount:" + amount` | Yes |
| 2 | DoTransfer.java | 178 | `"Message:Failure From:" + from + " To:" + to + " Amount:" + amount` | Yes |
| 3 | ChangePassword.java | 69 | `"newpassword=" + uname` | Yes |
| 4 | ChangePassword.java | 128 | `"phonno:" + phoneNumber` | Yes |
| 5 | ChangePassword.java | 168 | `"Phone number Invalid."` | No |
| 6 | MyBroadCastReceiver.java | 31 | `"phonenumber: " + textPhoneno + " password is: " + textMessage` | Yes |
| 7 | MyBroadCastReceiver.java | 39 | `"Phone number is null"` | No |
| 8 | ViewStatement.java | 26 | `fileToCheck.toString()` (path disclosure) | Yes |
