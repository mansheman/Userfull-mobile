# Android 14 PIN Brute-Force — Self-Contained Skill

Recover forgotten Android 14 lock screen PIN by brute-forcing the Synthetic
Password Blob (`.spblob`) offline. No throttle, no rate-limit, no Google
account. Only requires `adb root` and one known file path.

---

## 1. Prerequisites

| Requirement | Check |
|---|---|
| Android 14 device/emulator with PIN set | `adb shell dumpsys lock_settings` shows `CredentialType: PIN` |
| `adb root` access | `adb root` → `restarting adbd as root` |
| Python 3.8+ | `python3 --version` |
| `cryptography` library | `pip install cryptography` |

---

## 2. Architecture (How It Works)

Android 14 does **not** store `SHA1(salt+PIN)`. Instead, it uses **proof-by-decryption**:

```
PIN → scrypt(PIN, salt, N=2048, r=8, p=2) → stretchedLskf (32B)
       │
       │  + secdiscardable_hash (SHA512 of 16KB random file)
       ▼
protectorSecret (96B)
       │
       ▼ SHA512(pad128("application-id") || protectorSecret)
inner AES key (32B)
       │
       ├── decrypt outer layer spblob (Keystore key from persistent.sqlite)
       │
       ▼ AES-GCM decrypt inner layer → ✅ tag match = PIN benar
```

**4 files needed** from `/data/system_de/0/spblob/<PROTECTOR_ID>/`:

| File | Purpose |
|---|---|
| `*.pwd` | Salt + scrypt parameters |
| `*.spblob` | Target brute-force (encrypted synthetic password) |
| `*.secdis` | Secdiscardable (16KB random entropy) |
| `/data/misc/keystore/persistent.sqlite` | Keystore AES key for outer layer |

---

## 3. Workflow (5 Steps)

### Step 1 — Root the device

```bash
adb root
```

Expected output: `restarting adbd as root`

### Step 2 — Identify the protector

```bash
adb shell dumpsys lock_settings | grep -E "LSKF-based|CredentialType"
```

Expected output:
```
LSKF-based SP protector ID: fffef2d5e301f3d4
CredentialType: PIN
```

### Step 3 — Create `extract_params.py`

Copy the **entire source code** from **Section 4** below into a file named
`extract_params.py`. Then run:

```bash
python3 extract_params.py --adb --output params.json
```

This will:
1. Auto-detect the protector ID via ADB
2. Pull `.pwd`, `.spblob`, `.secdis`, `persistent.sqlite`
3. Parse salt, scrypt params, secdiscardable hash, Keystore key
4. Save everything to `params.json`

### Step 4 — Create `gpu_brute.py`

Copy the **entire source code** from **Section 5** below into a file named
`gpu_brute.py`.

### Step 5 — Run the brute-force

```bash
python3 gpu_brute.py --params params.json -L 6 -M 6 -w 8
```

The script will print progress and **auto-stop** when the PIN is found:

```
[*] Brute-forcing 6-digit PINs (1,000,000 combinations)...
    80,000/1,000,000 (8%) rate=523/s ETA=29.3m

============================================================
 ✅ PIN FOUND: 123321
    Checked: 80,000 PINs
    Time:    153s (2.6m)
============================================================
```

**Performance:**

| PIN Length | Combinations | Workers | Worst-case time |
|---|---|---|---|
| 4 digits | 10,000 | 8 | ~2 min |
| 5 digits | 100,000 | 8 | ~15 min |
| 6 digits | 1,000,000 | 8 | ~30 min |

**Useful flags:**

| Flag | Purpose |
|---|---|
| `-L 6 -M 6` | Only try 6-digit PINs |
| `-w 8` | Use 8 CPU workers |
| `--start 500000` | Start from PIN 500000 |
| `--resume` | Continue from last checkpoint |
| `--no-checkpoint` | Disable auto-save |

---

## 4. Script Files

The full source code is already available as standalone `.py` files in this
directory — no need to copy-paste from this document:

| File | Purpose |
|---|---|
| `extract_params.py` | Auto-extract cryptographic parameters from SP Blob files |
| `gpu_brute.py` | PIN brute-force engine with checkpoint/resume support |

See the file headers and `--help` for full usage documentation.

---

## 5. Quick Start

```bash
# 1. Root device
adb root

# 2. Verify PIN is set
adb shell dumpsys lock_settings | grep -E "LSKF-based|CredentialType"

# 3. Install dependency
pip install cryptography

# 4. Extract parameters
python3 extract_params.py --adb --output params.json

# 5. Brute-force (auto-stop when PIN found)
python3 gpu_brute.py --params params.json -L 6 -M 6 -w 8
```

---

## 6. Troubleshooting

| Problem | Solution |
|---|---|
| `protector ID not found` | PIN might not be set. Check: `adb shell dumpsys lock_settings` |
| `Keystore key not found` | Try `ls /data/misc/keystore/persistent.sqlite`. If missing, device may use hardware Keystore (not brute-forceable offline) |
| `ModuleNotFoundError: cryptography` | `pip install cryptography` |
| `Permission denied` on pull | Run `adb root` first |
| Brute-force too slow | Use `-w 16`, or `--start 500000` to skip already-checked range |
| Script killed mid-run | Re-run with `--resume` to continue from checkpoint |
| Wrong PIN length | Use `-L 4 -M 6` to try 4, 5, and 6 digits |
