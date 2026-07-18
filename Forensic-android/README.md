# Android Forensic Tool

Alat forensik digital untuk ekstraksi data dari perangkat Android via ADB (Android Debug Bridge). Backend Flask + frontend single-page dark-theme.

**Fitur:**
- Deteksi perangkat Android terhubung
- Ekstrak kontak, SMS, call log, daftar aplikasi, riwayat WiFi
- Tampilan dashboard dengan tab per kategori
- Pencarian/filter data real-time
- Ekspor hasil ke file JSON (`evidence_<serial>_<timestamp>.json`)

## Prasyarat

| Komponen | Keterangan |
|---|---|
| Python | 3.10+ |
| ADB | Android Debug Bridge di `$PATH` |
| Android | USB Debugging aktif, host authorized |

Cek ADB:
```bash
adb --version
adb devices -l
```

## Struktur

```
/home/syariful/android-forensic/
├── app.py              # Flask backend (API endpoints + ADB wrapper)
├── requirements.txt    # Python dependencies
├── SKILL.md            # Dokumentasi teknis lengkap
├── README.md           # File ini
├── static/
│   └── index.html      # Frontend single-page (dark theme)
└── venv/               # Python virtual environment
```

## Instalasi

```bash
cd /home/syariful/android-forensic

# Buat virtual env
python3 -m venv venv

# Install dependensi
./venv/bin/pip install flask flask-cors

# (sudah dilakukan)
```

## Menjalankan

```bash
./venv/bin/python app.py
```

Buka browser: **http://localhost:5000**

Output:
```
 * Running on http://0.0.0.0:5000
```

Stop: `Ctrl+C`

## Cara Pakai

1. **Hubungkan** perangkat Android via USB (debugging aktif)
2. **Buka** `http://localhost:5000`
3. **Klik** perangkat di sidebar kiri
4. Tunggu proses ekstraksi — data muncul di tab:
   - **Kontak** — nama & nomor telepon
   - **SMS** — inbox/sent/draft/outbox
   - **Call Log** — incoming/outgoing/missed
   - **Apps** — daftar aplikasi terinstal
   - **WiFi** — riwayat SSID tersimpan
5. **Cari** data via kolom search
6. **Ekspor** semua data ke JSON via tombol Export

## API

| Method | Endpoint | Fungsi |
|---|---|---|
| `GET` | `/` | Halaman utama |
| `GET` | `/api/devices` | Daftar perangkat terdeteksi |
| `POST` | `/api/extract/<serial>` | Ekstrak data perangkat |
| `GET` | `/api/export/<serial>` | Download hasil sebagai JSON |

## Tech Stack

- **Backend:** Python 3, Flask, Flask-CORS
- **Frontend:** Vanilla JS, Vanilla CSS, Google Fonts (Inter)
- **Komunikasi:** ADB via `subprocess`
- **Tema:** Dark theme (CSS variables)

## Lisensi

Hanya untuk tujuan forensik dan edukasi. Gunakan pada perangkat yang Anda miliki atau memiliki izin resmi.
