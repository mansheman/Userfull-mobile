# Hyperlink Spoofing + Drive-by Download Framework v2.2

Framework dinamis untuk simulasi hyperlink spoofing + drive-by download. Link akan menampilkan preview (thumbnail, judul, deskripsi) dari URL target (YouTube, Detik, Vimeo, dll) saat dikirim ke WhatsApp/Telegram, namun sebenarnya mengarah ke halaman auto-download.

> **DISCLAIMER**: Tool ini untuk penetration testing dan edukasi hanya. Gunakan hanya pada sistem yang Anda miliki atau memiliki izin tertulis.

## Fitur

- **Universal OG Fetcher** - Support YouTube, Vimeo, Detik, Kompas, GitHub, dll via oEmbed API + HTML parsing
- **Auto-Fetch OG Tags** - Otomatis mengambil title, image, description dari URL target
- **Dynamic Input** - Input URL, payload, dan config via CLI atau interactive mode
- **Auto-Download** - Payload otomatis terdownload saat victim membuka link
- **Cross-Platform** - Linux, macOS, dan Windows
- **Any Payload** - Support .apk, .exe, .pdf, .doc, .zip, dll
- **Admin Dashboard** - Monitor access logs secara real-time

## Support

| OS | Status | Python |
|----|--------|--------|
| Linux (Ubuntu, Kali, Parrot) | Full support | 3.7+ |
| macOS (Big Sur+) | Full support | 3.7+ |
| Windows 10/11 | Full support | 3.7+ |

## Quick Start

### Linux (Ubuntu/Kali/Parrot)

```bash
# Install dependencies
cd hyperlink-driveby-lab
pip3 install -r requirements.txt
chmod +x run.py

# Matikan server lama jika ada
kill $(lsof -t -i:8088) 2>/dev/null

# Jalankan
python3 run.py
```

### macOS

```bash
# Install dependencies
cd hyperlink-driveby-lab
pip3 install -r requirements.txt
chmod +x run.py

# Matikan server lama jika ada
kill $(lsof -t -i:8088) 2>/dev/null

# Jalankan
python3 run.py
```

### Windows

```cmd
# Install dependencies
cd hyperlink-driveby-lab
pip install -r requirements.txt

# Matikan server lama jika ada (run as Administrator)
netstat -ano | findstr :8088
taskkill /PID <PID> /F

# Jalankan
python run.py
```

## Interactive Mode

```bash
python3 run.py
```

Input yang diminta:

```
[1] TARGET URL
  Target URL: https://news.detik.com/berita/d-8579874/polisi-olah-tkp-kasus-warga-depok-diteror-tetangga-hingga-pagar-rusak

[2] PAYLOAD FILE
  Payload file: ./payload/document.pdf

[3] DOWNLOAD FILENAME
  Download filename: document.pdf

[4] SERVER PORT
  Port [8088]: 8088

[5] PUBLIC URL
  Choose [1/2/3]: 3
  Enter public URL: https://8226-104-28-163-39.ngrok-free.app

[6] CUSTOM PREVIEW (Optional)
  Custom title (blank=auto): 
  Custom description (blank=auto): 
```

**CATATAN PENTING**:
- Custom title/description **KOSONGKAN** jika ingin auto-fetch dari URL target
- Jika diisi, server akan pakai custom value dan TIDAK fetch dari URL

## CLI Mode

```bash
# Linux/macOS
python3 run.py \
  --url "https://news.detik.com/berita/d-8579874/polisi-olah-tkp" \
  --payload ./payload/document.pdf \
  --name "document.pdf" \
  --port 8088 \
  --public-url "https://8226-104-28-163-39.ngrok-free.app"

# Windows
python run.py ^
  --url "https://news.detik.com/berita/d-8579874/polisi-olah-tkp" ^
  --payload .\payload\document.pdf ^
  --name "document.pdf" ^
  --port 8088 ^
  --public-url "https://8226-104-28-163-39.ngrok-free.app"
```

## Setup Ngrok

### Linux
```bash
sudo snap install ngrok
ngrok authtoken YOUR_TOKEN
```

### macOS
```bash
brew install ngrok
ngrok authtoken YOUR_TOKEN
```

### Windows
```cmd
winget install ngrok
ngrok authtoken YOUR_TOKEN
```

### Jalankan Ngrok

```bash
# Linux/macOS/Windows
ngrok http 8088
```

## Cara Kerja

```
1. Attacker kirim link ke victim via WhatsApp/Telegram
                          ↓
2. WhatsApp/Telegram crawler ambil OG tags dari server
                          ↓
3. Server fetch OG tags dari URL target (Detik/YouTube/etc)
                          ↓
4. Server return HTML dengan OG tags → Thumbnail muncul di chat
                          ↓
5. Victim klik link → Buka server page
                          ↓
6. Auto-download payload + redirect ke URL target asli
```

## Supported Platforms

| Platform | oEmbed | OG Tags | Status |
|----------|--------|---------|--------|
| YouTube | Ya | - | Full support |
| Vimeo | Ya | - | Full support |
| Dailymotion | Ya | - | Full support |
| SoundCloud | Ya | - | Full support |
| Twitter/X | Ya | - | Full support |
| Detik | - | Ya | Full support |
| Kompas | - | Ya | Full support |
| GitHub | - | Ya | Full support |
| Situs lain | - | Ya | tergantung OG tags |

## Config

Config di-generate otomatis oleh `run.py`. Jika ingin edit manual:

```python
# config.py

# Target URL (URL yang akan di-preview)
TARGET_URL = "https://news.detik.com/berita/d-8579874/..."

# Payload
PAYLOAD_FILE = "document.pdf"
PAYLOAD_MIME = "application/pdf"
DOWNLOAD_NAME = "document.pdf"

# Server
PORT = 8088
PUBLIC_URL = "https://8226-104-28-163-39.ngrok-free.app"

# Custom OG (kosong = auto-fetch dari URL)
CUSTOM_TITLE = ""
CUSTOM_DESCRIPTION = ""
```

**PENTING**: Jika `CUSTOM_TITLE` atau `CUSTOM_DESCRIPTION` diisi, server TIDAK akan auto-fetch dari URL target. Biarkan kosong untuk auto-fetch.

## Architecture

```
hyperlink-driveby-lab/
├── run.py              # Main launcher (interactive + CLI)
├── server.py           # Flask server + universal OG fetcher
├── config.py           # Auto-generated config
├── requirements.txt    # Dependencies
├── templates/
│   ├── driveby.html    # Halaman utama (dynamic preview)
│   ├── admin.html      # Admin dashboard
│   └── ...
├── payload/            # Payload files
├── README.md           # Dokumentasi ini
└── SKILL.md            # Full build instructions
```

## Known Issues

### 1. Domain Identity
Link victim menggunakan domain ngrok/random, bukan domain target.
- WA/Telegram crawler tetap menampilkan OG tags dari URL target
- Victim melihat URL ngrok saat klik

### 2. Ngrok Free Tier
Ngrok free menampilkan warning page "Visit Site" sebelum redirect.

### 3. Browser Security
- Chrome memblokir auto-download APK
- iOS memblokir APK entirely
- Android 8+ minta izin install dari unknown source

### 4. HTTPS Requirement
Beberapa platform membutuhkan HTTPS untuk OG tags. Gunakan ngrok/Cloudflare Tunnel.

## Troubleshooting

### Server Won't Start

**Linux/macOS:**
```bash
lsof -i :8088
kill $(lsof -t -i:8088) 2>/dev/null
python3 run.py
```

**Windows:**
```cmd
netstat -ano | findstr :8088
taskkill /PID <PID> /F
python run.py
```

### OG Tags Tidak Muncul

```bash
# Linux/macOS
curl -A "WhatsApp/2.22.20.72" "http://localhost:8088/v?id=URL_TARGET"

# Windows (PowerShell)
curl -A "WhatsApp/2.22.20.72" "http://localhost:8088/v?id=URL_TARGET"
```

### Preview tidak muncul di WhatsApp
1. Pastikan URL target valid (bukan 404)
2. Pastikan HTTPS (ngrok)
3. Test dengan Facebook Debugger: https://developers.facebook.com/tools/debug/

## Testing

```bash
# Linux/macOS
curl -A "WhatsApp/2.22.20.72" "http://localhost:8088/v?id=https%3A%2F%2Fyoutube.com%2Fwatch%3Fv%3DVIDEO_ID"
curl http://localhost:8088/health
curl http://localhost:8088/api/logs

# Windows (PowerShell)
curl -A "WhatsApp/2.22.20.72" "http://localhost:8088/v?id=https%3A%2F%2Fyoutube.com%2Fwatch%3Fv%3DVIDEO_ID"
curl http://localhost:8088/health
curl http://localhost:8088/api/logs
```

## License

MIT License
