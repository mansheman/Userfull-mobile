# MobSF - Mobile Security Framework

> **Instalasi lengkap + integrasi MCP + otomatisasi analisis**
> Platform: Windows 11 x64 | MobSF v4.5.1 | Python 3.12

---

## Quick Start

```batch
# 1. Jalankan MobSF
scripts\run_mobsf.bat

# 2. Buka browser
http://127.0.0.1:8000

# 3. Login
Username: mobsf
Password: mobsf
```

---

## Struktur Dokumentasi

```
Mobsf/
├── README.md                    # Overview (file ini)
├── INSTALL.md                   # Panduan instalasi lengkap step-by-step
├── SKILL.md                     # OpenCode skill untuk otomatisasi
├── prompt.txt                   # Prompt siap pakai untuk analisis
├── config/
│   ├── opencode.json            # Konfigurasi MCP server untuk opencode
│   ├── .env                     # Environment MobSF API key
│   └── config.py                # Konfigurasi kustom MobSF (JAVA_DIRECTORY, dll.)
├── scripts/
│   ├── run_mobsf.bat            # Script startup MobSF (klik 2x)
│   ├── start_server.py          # Entry point Python untuk MobSF
│   ├── query_scan.py            # Query hasil scan dari SQLite database
│   └── api_test.py              # Test MobSF REST API
├── requirements/
│   ├── requirements-py.txt      # 97 Python packages (pip freeze)
│   └── requirements-node.json   # Node.js packages (MCP server)
├── mcp-server/
│   ├── server.ts                # MCP server source (modified untuk npm SDK)
│   ├── package.json             # Node dependencies
│   └── tsconfig.json            # TypeScript config
└── tools/
    └── tool_versions.txt        # Semua versi tools + install commands
```

---

## Komponen Utama

| Komponen | Path | Port/URL |
|----------|------|----------|
| **MobSF Server** | `C:\...\New OpenCode Project\mobsf\` | `127.0.0.1:8000` |
| **MCP Server** | `C:\...\New OpenCode Project\mobsf-mcp-server\` | stdio (via opencode) |
| **Database** | `C:\Users\LENOVO\.MobSF\db.sqlite3` | SQLite |
| **Uploads** | `C:\Users\LENOVO\.MobSF\uploads\` | - |
| **Downloads** | `C:\Users\LENOVO\.MobSF\downloads\` | - |
| **Tools** | `C:\Users\LENOVO\.MobSF\tools\` | JADX, etc. |

---

## Tools & Versi

| Tool | Versi | Path |
|------|-------|------|
| Python | 3.12.10 | `C:\Users\LENOVO\AppData\Local\Programs\Python\Python312\` |
| Git | 2.55.0.2 | `C:\Program Files\Git\` |
| OpenJDK (Temurin) | 21.0.11+10 | `C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot\` |
| Node.js | 24.18.0 LTS | `C:\Program Files\nodejs\` |
| wkhtmltopdf | 0.12.6-1 | `C:\Program Files\wkhtmltopdf\` |
| Poetry | 1.8.4 | pip global |
| MobSF | 4.5.1 | poetry venv |

---

## Mode Analisis

### 1. Web UI (Browser)
Buka `http://127.0.0.1:8000` -> Upload APK/IPA -> Lihat report interaktif

### 2. REST API (Automation)
```bash
python scripts/api_test.py                        # Test koneksi
python scripts/api_test.py --scan path/to/app.apk  # Upload & scan
```

### 3. Database Query (Analisis Batch)
```bash
python scripts/query_scan.py                       # List scan terbaru
python scripts/query_scan.py <MD5_HASH>            # Detail scan
python scripts/query_scan.py --findings <MD5>      # Semua temuan
```

### 4. MCP via opencode (AI-Powered)
Upload file APK/IPA langsung dari conversation:
> "Scan file C:\path\to\suspicious.apk"

---

## Kredensial Default

| Kredensial | Value |
|---|---|
| Username | `mobsf` |
| Password | `mobsf` |
| REST API Key | `1f9059ff7684102e8827b52f7bad153e94e67f92997aaff92db693ca34575418` |

---

## Related Links

- [MobSF Official Docs](https://mobsf.github.io/docs/)
- [MobSF GitHub](https://github.com/MobSF/Mobile-Security-Framework-MobSF)
- [MCP Server GitHub](https://github.com/pullkitsan/mobsf-mcp-server)
- [MobSF Live Demo](https://mobsf.live)
- [OpenCode Docs](https://opencode.ai)
