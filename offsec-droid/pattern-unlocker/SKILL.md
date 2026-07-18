# Android 14 Pattern Brute-Force — Self-Contained Skill

Recover forgotten Android 14 lock screen **pattern** by brute-forcing the Synthetic
Password Blob (`.spblob`) offline. No throttle, no rate-limit, no Google
account. Only requires `adb root` and one known file path.

---

## 1. Prerequisites

| Requirement | Check |
|---|---|
| Android 14 device/emulator with **Pattern** set | `adb shell dumpsys lock_settings` shows `CredentialType: Pattern` |
| `adb root` access | `adb root` → `restarting adbd as root` |
| Python 3.8+ | `python3 --version` |
| `cryptography` library | `pip install cryptography` |

---

## 2. Architecture (How It Works)

Android 14 converts the lock pattern (3×3 grid, dots 1-9) to a numeric string
and processes it through the same **proof-by-decryption** pipeline as PIN:

```
Pattern → numeric string (e.g. 1→5→9→6→3 → "15963")
   │
   ▼ scrypt(pattern, salt, N=2048, r=8, p=2) → stretchedLskf (32B)
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
   ▼ AES-GCM decrypt inner layer → ✅ tag match = pattern benar
```

**4 files needed** from `/data/system_de/0/spblob/<PROTECTOR_ID>/`:

| File | Purpose |
|---|---|
| `*.pwd` | Salt + scrypt parameters |
| `*.spblob` | Target brute-force (encrypted synthetic password) |
| `*.secdis` | Secdiscardable (16KB random entropy) |
| `/data/misc/keystore/persistent.sqlite` | Keystore AES key for outer layer |

### Pattern Encoding (3×3 Grid)

```
1  2  3
4  5  6
7  8  9
```

**Rules:**
- Minimum 4 dots connected
- Cannot revisit a dot
- Cannot jump over an unvisited dot that lies on the straight line between two dots

**Invalid move examples:**
- 1→3 (must visit 2 first), 1→7 (must visit 4 first), 1→9 (must visit 5 first)
- 2→8 (must visit 5 first), 4→6 (must visit 5 first), 7→9 (must visit 8 first)

**Valid move examples:**
- 1→6, 1→8, 2→7, 2→9, 3→4 (knight-like moves, no middle dot on line)

| Pattern Length | Valid Combinations |
|---|---|
| 4 dots | 1,624 |
| 5 dots | 7,152 |
| 6 dots | 26,016 |
| 7 dots | 72,912 |
| 8 dots | 140,704 |
| 9 dots | 140,704 |
| **Total (4-9)** | **389,112** |

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
CredentialType: Pattern
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

### Step 4 — Create `pattern_brute.py`

Copy the **entire source code** from **Section 5** below into a file named
`pattern_brute.py`.

### Step 5 — Run the brute-force

```bash
# Brute-force all pattern lengths (4-9 dots)
python3 pattern_brute.py --params params.json -L 4 -M 9 -w 8

# Only 5-dot patterns (faster if you know the length)
python3 pattern_brute.py --params params.json -L 5 -M 5 -w 8

# Show pattern space statistics
python3 pattern_brute.py --params params.json --info
```

The script will print progress and **auto-stop** when the pattern is found:

```
[*] Pattern space: 389,112 valid patterns (4-9 dots)
      4 dots: 1,624 patterns
      5 dots: 7,152 patterns
      ...
[*] Brute-forcing 5-dot patterns (7,152 combinations)...
      1,500/7,152 (21%) rate=115/s ETA=0.8m

============================================================
 PATTERN FOUND: 15963
    Dots:       5 points
    Sequence:   1 -> 5 -> 9 -> 6 -> 3
    Time:       13s (0.2m)
============================================================
```

**Performance:**

| Pattern Length | Combinations | Workers | Worst-case time |
|---|---|---|---|
| 4 dots | 1,624 | 8 | ~15 detik |
| 5 dots | 7,152 | 8 | ~1 menit |
| 6 dots | 26,016 | 8 | ~4 menit |
| 7 dots | 72,912 | 8 | ~11 menit |
| 8 dots | 140,704 | 8 | ~21 menit |
| 9 dots | 140,704 | 8 | ~21 menit |
| **Total (4-9)** | **389,112** | 8 | **~55-65 menit** |

**Useful flags:**

| Flag | Purpose |
|---|---|
| `-L 5 -M 5` | Only try 5-dot patterns |
| `-w 8` | Use 8 CPU workers |
| `--start 1000` | Skip first 1000 patterns in the first length group |
| `--resume` | Continue from last checkpoint |
| `--no-checkpoint` | Disable auto-save |
| `--auto-unlock` | Automatically draw pattern on device when found |
| `--device SERIAL` | Target device for auto-unlock (default: auto-detect) |
| `--info` | Show pattern space statistics and exit |
| `--engine bench` | Run GPU feasibility analysis |

**Auto-unlock (new):** With `--auto-unlock`, once the pattern is found the tool
automatically draws it on the lock screen via `uiautomator2`. Requires:

```bash
pip install uiautomator2
```

**CPU-only note:** The scrypt function is intentionally GPU-resistant (memory-hard, N=2048).
GPU does not help for this workload. Multiprocessing CPU is the optimal approach.

---

## 4. Script Files

The full source code is already available as standalone `.py` files in this
directory — no need to copy-paste from this document:

| File | Purpose |
|---|---|
| `extract_params.py` | Auto-extract cryptographic parameters from SP Blob files |
| `pattern_brute.py` | Pattern brute-force engine with checkpoint/resume + auto-unlock |

See the file headers and `--help` for full usage documentation.

---

## 5. Quick Start

```bash
# 1. Root device
adb root

# 2. Verify Pattern is set
adb shell dumpsys lock_settings | grep -E "LSKF-based|CredentialType"

# 3. Install dependencies
pip install cryptography
pip install uiautomator2  # optional, for --auto-unlock

# 4. Extract parameters
python3 extract_params.py --adb --output params.json

# 5. Brute-force + auto-unlock (pattern directly drawn on device)
python3 pattern_brute.py --params params.json -L 7 -M 7 -w 16 --auto-unlock

# Or manually — pattern printed, draw it yourself
python3 pattern_brute.py --params params.json -L 4 -M 9 -w 8
```


---

## 6. Troubleshooting

| Problem | Solution |
|---|---|
| `protector ID not found` | Pattern might not be set. Check: `adb shell dumpsys lock_settings` |
| `Keystore key not found` | Try `ls /data/misc/keystore/persistent.sqlite`. If missing, device may use hardware Keystore (not brute-forceable offline) |
| `ModuleNotFoundError: cryptography` | `pip install cryptography` |
| `Permission denied` on pull | Run `adb root` first |
| Brute-force too slow | Narrow the length range (`-L 5 -M 7`), or use `-w 16` |
| Script killed mid-run | Re-run with `--resume` to continue from checkpoint |
| Wrong pattern length | Use `--info` to see pattern counts and time estimates |
| Android 13 or older | This tool targets Android 14 SP blob format. Older versions may use different mechanisms |
| `--auto-unlock` fails: `uiautomator2 not found` | `pip install uiautomator2` — this is optional, only needed for auto-unlock |
| Auto-unlock draws but device stays locked | Pattern may be wrong. Verify with: `adb shell cmd lock_settings get-disabled --old <pattern>` |
