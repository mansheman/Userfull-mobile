# Android Forensic Tool

**Deskripsi:** Flask backend + single-page HTML frontend untuk ekstraksi data forensik dari device Android via ADB (Android Debug Bridge). Menarik kontak, SMS, call log, daftar aplikasi, dan riwayat WiFi kemudian ditampilkan dalam dashboard dark-theme.

Dibuat dengan: Python 3 / Flask / Vanilla CSS / Vanilla JS.

---

## Daftar Isi

- [Prasyarat](#prasyarat)
- [Struktur File](#struktur-file)
- [Langkah Pembuatan](#langkah-pembuatan)
  - [1. Setup Environment](#1-setup-environment)
  - [2. Backend — app.py](#2-backend--apppy)
  - [3. Frontend — static/index.html](#3-frontend--staticindexhtml)
- [Cara Menjalankan](#cara-menjalankan)
- [API Reference](#api-reference)
- [Alur Kerja](#alur-kerja)
- [Error Handling & Edge Cases](#error-handling--edge-cases)
- [Testing](#testing)

---

## Prasyarat

| Komponen | Keterangan |
|---|---|
| Python | 3.10+ |
| ADB | Android Debug Bridge terinstal & ada di `$PATH` |
| Device Android | USB debugging aktif, authorized host |
| Browser | Chrome / Firefox / Edge (modern) |

Cek ADB:

```bash
adb --version
adb devices -l
```

---

## Struktur File

```
/home/syariful/android-forensic/
├── app.py              # Flask backend (248 baris, 7.7 KB)
├── requirements.txt    # Python dependencies
├── SKILL.md            # Dokumentasi ini
├── static/
│   └── index.html      # Frontend single-page (1069 baris, 33 KB)
└── venv/               # Python virtual environment
```

---

## Langkah Pembuatan

### 1. Setup Environment

```bash
mkdir -p /home/syariful/android-forensic/static
cd /home/syariful/android-forensic

# Virtual environment
python3 -m venv venv

# Install dependencies
./venv/bin/pip install flask flask-cors

# requirements.txt
cat > requirements.txt << 'EOF'
flask
flask-cors
EOF
```

### 2. Backend — app.py

#### 2.1 Impor & Konfigurasi

```python
from flask import Flask, jsonify, send_file
from flask_cors import CORS
import subprocess, json, re, io
from datetime import datetime

app = Flask(__name__, static_folder='static', static_url_path='')
CORS(app)
ADB_TIMEOUT = 60
```

- `static_folder='static'` → Flask serve file dari folder `static/`
- `static_url_path=''` → route `/` mapping ke `index.html`
- `CORS(app)` → izinkan akses dari origin mana pun

#### 2.2 Helper `run_adb()`

```python
def run_adb(args):
    result = subprocess.run(args, capture_output=True, text=True, timeout=ADB_TIMEOUT)
    if result.returncode != 0 and result.stderr:
        raise RuntimeError(result.stderr.strip())
    return result.stdout
```

Menggunakan `subprocess.run()` dengan `capture_output=True` sebagaimana disyaratkan. Semua command ADB lewat fungsi ini → error handling terpusat.

#### 2.3 Parser `parse_devices()`

Input: output `adb devices -l`

```
List of devices attached
emulator-5554          device product:sdk_gphone64_arm64 model:pixel_6 ...
```

Logika:

```python
def parse_devices(output):
    devices = []
    for line in lines[1:]:          # skip header
        parts = line.split()
        serial = parts[0]
        status = parts[1]
        # cari token "model:xxx"
        model = next((p.split(':',1)[1] for p in parts if p.startswith('model:')), '')
        devices.append({'serial': serial, 'model': model, 'status': status})
    return devices
```

#### 2.4 Parser `parse_content_rows()` — Kunci Utama

Ini parser generik untuk output `adb shell content query`. Format baris:

```
Row: 0 _id=1, display_name=John Doe, data1=+62812, ...
```

**Tantangan:** nilai bisa mengandung koma (terutama SMS body). Solusi: split hanya pada `, ` yang diikuti `kata=` (tanda key-value berikutnya).

```python
pairs = re.split(r',\s*(?=\w+=)', remaining)
```

Contoh:

- Input: `body=Hi there, how are you?, type=1`
- Split regex → `['body=Hi there, how are you?', 'type=1']` ✅
- Koma di dalam "Hi there, how are you?" tetap utuh karena setelahnya bukan `\w+=`

#### 2.5 Parser Spesifik

| Parser | ADB Command | Field Mapping |
|---|---|---|
| `parse_contacts()` | `content query --uri content://contacts/phones` | `display_name`→`name`, `data1`→`phone` |
| `parse_sms()` | `content query --uri content://sms` | `address`, `body`, `date`, `type` |
| `parse_call_logs()` | `content query --uri content://call_log/calls` | `number`, `date`, `duration`, `type` |
| `parse_packages()` | `pm list packages` | Strip `package:` prefix |
| `parse_wifi()` | `dumpsys wifi` | 3 regex pattern untuk SSID |

**WiFi parser** menggunakan 3 fallback pattern karena output `dumpsys wifi` sangat bervariasi antar versi Android:

```python
# Pattern 1:  SSID: "MyWiFi"
re.findall(r'SSID[:\s]+"([^"]+)"', output)

# Pattern 2:  SSID: MyWiFi
re.findall(r'(?<![<\w])SSID[:\s]+(\S+)', output)

# Pattern 3:  <string name="SSID">"MyWiFi"</string>
re.findall(r'<string\s+name="SSID">"?([^"<]+)"?</string>', output)
```

#### 2.6 Fungsi `extract_data(serial)`

Menjalankan 5 ADB commands secara berurutan, return dict besar:

```python
def extract_data(serial):
    base = ['adb', '-s', serial, 'shell']
    contacts = parse_contacts(run_adb(base + ['content', 'query', '--uri', 'content://contacts/phones']))
    sms       = parse_sms(run_adb(base + ['content', 'query', '--uri', 'content://sms']))
    calls     = parse_call_logs(run_adb(base + ['content', 'query', '--uri', 'content://call_log/calls']))
    apps      = parse_packages(run_adb(base + ['pm', 'list', 'packages']))
    wifi      = parse_wifi(run_adb(base + ['dumpsys', 'wifi']))
    return {'contacts': contacts, 'sms': sms, 'call_logs': calls, 'apps': apps, 'wifi': wifi}
```

#### 2.7 Endpoints

| Method | Route | Logic |
|---|---|---|
| `GET` | `/` | `app.send_static_file('index.html')` |
| `GET` | `/api/devices` | `run_adb(['adb', 'devices', '-l'])` → `parse_devices()` |
| `POST` | `/api/extract/<serial>` | `extract_data(serial)` → `jsonify()` |
| `GET` | `/api/export/<serial>` | `extract_data(serial)` → `send_file()` sebagai attachment `.json` |

**Export endpoint** menggunakan `io.BytesIO` agar tidak perlu file sementara di disk:

```python
buf = io.StringIO()
json.dump(data, buf, indent=2)
buf.seek(0)
return send_file(
    io.BytesIO(buf.getvalue().encode('utf-8')),
    as_attachment=True,
    download_name=f'evidence_{serial}_{timestamp}.json',
    mimetype='application/json',
)
```

#### 2.8 Error Handling

Setiap endpoint dibungkus `try/except`:

```python
@app.route('/api/devices', methods=['GET'])
def get_devices():
    try:
        ...
    except Exception as e:
        return jsonify({'error': str(e)}), 500
```

Kode 500 + pesan error eksplisit dari ADB (misal: `device not found`, `device offline`).

### 3. Frontend — static/index.html

#### 3.1 Arsitektur HTML

```
<header>
  └─ Title + Export JSON button + Refresh button

<div class="main-container">
  <aside class="sidebar">
    ├─ "Devices" header + count badge
    └─ #device-list (device cards, scrollable)

  <main class="content">
    ├─ .content-placeholder (saat no device selected)
    └─ #data-views (hidden sampai device dipilih)
        ├─ .tab-bar (5 tab buttons)
        ├─ .toolbar (search input + stats badges)
        └─ .tab-panels
            ├─ #panel-contacts  → table
            ├─ #panel-sms       → table
            ├─ #panel-call_logs → table
            ├─ #panel-apps      → grid cards
            └─ #panel-wifi      → list items
```

#### 3.2 CSS — Dark Theme

| Variabel CSS | Value |
|---|---|
| `--bg-body` | `#0d1117` |
| `--bg-sidebar` | `#161b22` |
| `--bg-card` | `#21262d` |
| `--bg-card-hover` | `#30363d` |
| `--accent` | `#00e676` (hijau) |
| `--accent-hover` | `#00c853` |
| `--text-primary` | `#c9d1d9` |
| `--text-secondary` | `#8b949e` |
| `--border` | `#30363d` |

**Font:** Inter via Google Fonts.

```css
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
```

**Transisi:** `transition: all 0.2s ease` pada:

- Device card (border-left, background)
- Tab button (border-bottom, color)
- Search input (border-color)
- App card, WiFi item (border-color)

**Responsive breakpoints:**

| Lebar | Perubahan |
|---|---|
| `< 820px` | Sidebar mengecil ke 220px |
| `< 640px` | Layout jadi vertikal, sidebar di atas (max-height 40vh), font & padding mengecil |

#### 3.3 Sidebar — Device List

- `loadDevices()` dipanggil saat `DOMContentLoaded` + `setInterval(15000)` auto-refresh
- Fetch `GET /api/devices` → render device cards
- Setiap card menampilkan: model (bold), serial (monospace), status (badge warna)
- Klik card → `selectDevice(serial)`
- Status colors: `device`=hijau, `offline`=merah, `unauthorized`=kuning

Edge cases:

```
Tidak ada device     → "Tidak ada device terdeteksi. Hubungkan device..."
Gagal fetch          → "Gagal memuat devices" + pesan error
Loading              → spinner + "Scanning..."
```

#### 3.4 Ekstraksi Data

Saat device diklik:

1. Tampilkan `loadingOverlay` dengan spinner
2. `fetch(POST /api/extract/<serial>)`
3. Sukses → `renderAll(json)` → isi semua tab, enable Export button, toast notifikasi
4. Gagal → `showError()` di panel aktif + toast merah

#### 3.5 Tab System

```javascript
function switchTab(tab) {
    // 1. Update active class di tab buttons
    // 2. Show/hide panel dengan class 'active'
    // 3. Re-apply search filter ke tab baru
}
```

Ada 5 tab:

| Tab | Data Source | Tampilan | Kolom |
|---|---|---|---|
| **Kontak** | `data.contacts` | Table | Name, Phone |
| **SMS** | `data.sms` | Table | From/To, Message, Date, Type |
| **Call Log** | `data.call_logs` | Table | Number, Date, Duration, Type |
| **Apps** | `data.apps` | Grid cards | Icon (first letter) + package name |
| **WiFi** | `data.wifi` | List items | Icon + SSID |

**Type labels:**

| SMS type | Label |
|---|---|
| `1` | Inbox |
| `2` | Sent |
| `3` | Draft |
| `4` | Outbox |

| Call type | Label |
|---|---|
| `1` | Incoming |
| `2` | Outgoing |
| `3` | Missed |
| `5` | Voicemail |

**Date formatting:** UNIX timestamp (ms) → `DD/MM/YYYY HH:MM:SS`

**Duration formatting:** detik → `Xh Xm Xs` atau `Xm Xs` atau `Xs`

#### 3.6 Search / Filter

- Input di toolbar, debounce 250ms (`setTimeout`/`clearTimeout`)
- Filter re-render tab aktif dengan data yang cocok
- Search by: name/phone (contacts), address/body/type (SMS), number/type (calls), package name (apps), SSID (wifi)
- Tidak ada hasil → "Tidak ada [data] yang cocok dengan filter"
- Reset search saat ganti device

#### 3.7 Export JSON

```javascript
function exportJSON() {
    window.open(`/api/export/${encodeURIComponent(currentDevice)}`, '_blank');
}
```

Browser akan mendownload file `evidence_<serial>_<timestamp>.json`.

#### 3.8 Stats

Toolbar menampilkan badge:

```
Kontak: 42 | SMS: 156 | Calls: 89 | Apps: 213 | WiFi: 5
```

Angka di-update otomatis setelah ekstraksi.

#### 3.9 XSS Prevention

Semua data dari ADB di-render dengan fungsi `esc()` yang meng-escape HTML entities:

```javascript
function esc(str) {
    return String(str || '')
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
}
```

Tidak ada `innerHTML` yang menggunakan data ADB — hanya `textContent` untuk data variabel (via template literal yang sudah di-escape).

---

## Cara Menjalankan

```bash
cd /home/syariful/android-forensic
./venv/bin/python app.py
```

Akses di browser: **http://localhost:5000**

Output:

```
 * Serving Flask app 'app'
 * Running on http://127.0.0.1:5000
```

Untuk stop: `Ctrl+C` atau `kill <PID>`.

---

## API Reference

### `GET /`

Serve halaman utama `index.html`.

**Response:** `200 OK` — HTML document

---

### `GET /api/devices`

Menampilkan daftar device Android yang terdeteksi ADB.

**Response 200:**

```json
{
  "devices": [
    {
      "serial": "emulator-5554",
      "model": "sdk_gphone64_arm64",
      "status": "device"
    }
  ]
}
```

**Response 500:**

```json
{
  "error": "ADB not found. Is it installed?"
}
```

---

### `POST /api/extract/<serial>`

Ekstrak semua data forensik dari device tertentu.

**Path parameter:** `serial` — serial ADB device

**Response 200:**

```json
{
  "contacts": [
    { "name": "John Doe", "phone": "+628123456789" }
  ],
  "sms": [
    { "address": "+628123456789", "body": "Pesan teks", "date": "1700000000000", "type": "1" }
  ],
  "call_logs": [
    { "number": "+628123456789", "date": "1700000000000", "duration": "120", "type": "2" }
  ],
  "apps": [
    "com.android.chrome",
    "com.whatsapp"
  ],
  "wifi": [
    "MyHomeWiFi",
    "Office_Guest"
  ]
}
```

**Response 500:**

```json
{
  "error": "adb: device 'FAKE_SERIAL' not found"
}
```

---

### `GET /api/export/<serial>`

Sama seperti extract, tapi return sebagai downloadable JSON file.

**Path parameter:** `serial` — serial ADB device

**Response:** `200 OK` — File download `evidence_<serial>_<timestamp>.json`

**Headers:**

```
Content-Disposition: attachment; filename=evidence_emulator-5554_20260717_091500.json
Content-Type: application/json
```

---

## Alur Kerja

```
Browser                      Flask Server                   ADB
  │                              │                           │
  ├─ GET / ────────────────────► │                           │
  │ ◄─── index.html ────────────┤                           │
  │                              │                           │
  ├─ GET /api/devices ─────────► │                           │
  │                              ├─ adb devices -l ────────► │
  │                              │ ◄─── output ─────────────┤
  │                              ├─ parse_devices()          │
  │ ◄─── {devices: [...]} ──────┤                           │
  │                              │                           │
  ├─ Klik device card ─────────► │                           │
  │                              │                           │
  ├─ POST /api/extract/X ──────► │                           │
  │                              ├─ adb content query ─────► │
  │                              ├─ adb content query ─────► │
  │                              ├─ adb content query ─────► │
  │                              ├─ adb pm list packages ──► │
  │                              ├─ adb dumpsys wifi ──────► │
  │ ◄─── {contacts, sms, ...} ──┤                           │
  │                              │                           │
  ├─ Render tabs + stats ──────►│                           │
  │                              │                           │
  ├─ Klik Export ──────────────►│                           │
  ├─ GET /api/export/X ────────►│                           │
  │                              ├─ (same extract flow)      │
  │ ◄─── evidence_X.json.dl ────┤                           │
```

---

## Error Handling & Edge Cases

| Skenario | Handling |
|---|---|
| **ADB tidak terinstal** | `subprocess.run` raise `FileNotFoundError` → 500 "No such file or directory" |
| **Device tidak ditemukan** | ADB return error → 500 "device 'X' not found" |
| **Device offline** | Status `offline` di daftar device, extract gagal → 500 |
| **Content query kosong** | `parse_content_rows()` return `[]` → tabel kosong |
| **Koma di SMS body** | Regex split `,\s*(?=\w+=)` mencegah false split |
| **SSID tidak dikenal** | Filter `'<unknown ssid>', '<unknown>'` di WiFi parser |
| **Serial dengan karakter spesial** | `encodeURIComponent()` di frontend |
| **Data sangat besar** | Virtual scrolling tidak diterapkan (tabel render semua), tapi DOM API (createDocumentFragment) efisien |
| **Permission denied** | ADB return error → tampil di error state panel |
| **XSS dari data ADB** | Semua output di-escape via `esc()` / `textContent` |
| **Browser refresh** | State hilang → fetch ulang devices, pilih ulang device |
| **Koneksi terputus** | Fetch error → tampil toast + error state |
| **Dumpsys WiFi berbeda antar versi** | 3 regex pattern fallback |

---

## Testing

### Syntax Check

```bash
./venv/bin/python -m py_compile app.py
# tidak ada output = OK
```

### API Test (via curl)

```bash
# 1. Root
curl -s -o /dev/null -w "%{http_code}" http://localhost:5000/
# → 200

# 2. Devices
curl -s http://localhost:5000/api/devices | python3 -m json.tool
# → {"devices": [...]}

# 3. Extract (device harus terhubung)
curl -s -X POST http://localhost:5000/api/extract/emulator-5554 | python3 -m json.tool

# 4. Export
curl -s -o evidence.json http://localhost:5000/api/export/emulator-5554
```

### Visual Test (via browser)

1. Buka `http://localhost:5000`
2. Sidebar menampilkan device (atau "Tidak ada device")
3. Klik device → loading spinner → data tampil di tab
4. Ganti antar tab, gunakan search
5. Klik Export → file terdownload
6. Resize browser ke mobile → layout berubah
7. Disconnect device → sidebar refresh (15 detik) → device hilang

---

## Catatan Pengembangan

- **Port**: 5000 (`app.run(port=5000)`)
- **Host**: `0.0.0.0` (accessible dari LAN)
- **Debug mode**: `False` (production-like)
- **ADB timeout**: 60 detik per command
- **Auto-refresh devices**: 15 detik via `setInterval`
- **Search debounce**: 250ms
