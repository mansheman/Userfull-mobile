# MobSF Installation Guide - Windows 11 x64

> **Complete step-by-step installation dari awal hingga akhir**
> Terakhir diupdate: 2026-07-13 | MobSF v4.5.1

---

## Prasyarat Sistem

- Windows 10/11 64-bit
- RAM: minimum 4GB (rekomendasi 8GB+)
- Disk: ~3GB untuk MobSF + dependencies
- Koneksi internet (untuk download tools & packages)

---

## Step 1: Install System Tools via winget

### 1.1 Git
```powershell
winget install --id Git.Git -e --source winget --accept-package-agreements
```
- Versi: 2.55.0.2
- Path: `C:\Program Files\Git\`

### 1.2 OpenJDK 21 (Eclipse Temurin)
```powershell
winget install --id EclipseAdoptium.Temurin.21.JDK -e --source winget --accept-package-agreements
```
- Versi: 21.0.11+10
- Path: `C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot\`

### 1.3 Node.js LTS
```powershell
winget install --id OpenJS.NodeJS.LTS -e --source winget --accept-package-agreements
```
- Versi: 24.18.0
- Path: `C:\Program Files\nodejs\`

### 1.4 wkhtmltopdf (untuk PDF report)
```powershell
winget install --id wkhtmltopdf.wkhtmltox -e --source winget --accept-package-agreements
```
- Versi: 0.12.6-1
- Path: `C:\Program Files\wkhtmltopdf\`

### Verifikasi Instalasi
```powershell
# Refresh PATH (restart terminal atau:)
$env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")

# Verify
git --version
java -version
node --version
npm --version
```

---

## Step 2: Python Environment

Python 3.12+ sudah harus terinstal. Jika belum:
```powershell
winget install --id Python.Python.3.12 -e --source winget
```

### 2.1 Install Poetry (package manager)
```powershell
python -m pip install --no-cache-dir poetry==1.8.4
```

---

## Step 3: Clone & Install MobSF

### 3.1 Clone Repository
```powershell
git clone --depth 1 https://github.com/MobSF/Mobile-Security-Framework-MobSF.git mobsf
cd mobsf
```

### 3.2 Install Dependencies via Poetry
```powershell
poetry lock
poetry install --only main --no-root --no-interaction --no-ansi
poetry install --only main --no-interaction --no-ansi
```
> **Catatan:** Poetry akan membuat virtualenv otomatis di:
> `C:\Users\<user>\AppData\Local\pypoetry\Cache\virtualenvs\mobsf-*\`

### 3.3 Konfigurasi JAVA_DIRECTORY
Edit file `C:\Users\<user>\.MobSF\config.py` (dibuat otomatis setelah MobSF pertama dijalankan):
```python
JAVA_DIRECTORY = os.getenv('MOBSF_JAVA_DIRECTORY',
    'C:/Program Files/Eclipse Adoptium/jdk-21.0.11.10-hotspot/bin/')
```

### 3.4 Setup Database
```powershell
$env:DJANGO_SUPERUSER_USERNAME = "mobsf"
$env:DJANGO_SUPERUSER_PASSWORD = "mobsf"

poetry run python manage.py makemigrations
poetry run python manage.py makemigrations StaticAnalyzer
poetry run python manage.py migrate
poetry run python manage.py createsuperuser --noinput --email "admin@mobsf.local"
poetry run python manage.py create_roles
```

### 3.5 Create Startup Script (`start_server.py`)
```python
from mobsf.__main__ import main
main()
```

### 3.6 Create Startup Batch (`run_mobsf.bat`)
```batch
@echo off
echo ============================================
echo   MobSF - Mobile Security Framework v4.5.1
echo ============================================
echo Server  : http://127.0.0.1:8000
echo Login   : mobsf / mobsf
echo ============================================

set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

cd /d "%~dp0"
call "<virtualenv_path>\Scripts\python.exe" start_server.py
pause
```

### 3.7 Verifikasi
```powershell
# Jalankan MobSF
.\run_mobsf.bat

# Buka browser
# http://127.0.0.1:8000
# Login: mobsf / mobsf
```

---

## Step 4: Install MCP Server (Integrasi AI)

### 4.1 Clone MCP Server
```powershell
git clone https://github.com/pullkitsan/mobsf-mcp-server.git mobsf-mcp-server
cd mobsf-mcp-server
```

### 4.2 Install Dependencies
```powershell
# Set PATH untuk Node.js
$env:Path = "C:\Program Files\nodejs;" + $env:Path

npm install
npm install @modelcontextprotocol/sdk
```

### 4.3 Modifikasi server.ts (import fix)
Server asli menggunakan `./sdk/src/...` yang memerlukan clone SDK terpisah.
Modifikasi import agar menggunakan npm package:

```typescript
// BEFORE (original):
import { Server } from "./sdk/src/server/index.js";
import { StdioServerTransport } from "./sdk/src/server/stdio.js";
import { CallToolRequestSchema, ListToolsRequestSchema, InitializeRequestSchema } from "./sdk/src/types.js";

// AFTER (modified):
import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { CallToolRequestSchema, ListToolsRequestSchema, InitializeRequestSchema } from "@modelcontextprotocol/sdk/types.js";
```

### 4.4 Buat .env
```
MOBSF_URL=http://localhost:8000
MOBSF_API_KEY=1f9059ff7684102e8827b52f7bad153e94e67f92997aaff92db693ca34575418
```
> API Key didapat dari output MobSF saat pertama kali start.

### 4.5 Test MCP Server
```powershell
npx tsx server.ts
# Output: "Server connected and ready."
```

---

## Step 5: Konfigurasi OpenCode MCP

### 5.1 Buat `opencode.json`
Di root project:
```json
{
  "$schema": "https://opencode.ai/config.json",
  "mcp": {
    "mobsf": {
      "type": "local",
      "command": [
        "C:\\Program Files\\nodejs\\npx.cmd",
        "tsx",
        "server.ts"
      ],
      "cwd": "C:\\Users\\LENOVO\\OneDrive\\Documents\\New OpenCode Project\\mobsf-mcp-server",
      "environment": {
        "MOBSF_URL": "http://localhost:8000",
        "MOBSF_API_KEY": "1f9059ff7684102e8827b52f7bad153e94e67f92997aaff92db693ca34575418"
      },
      "enabled": true
    }
  }
}
```

### 5.2 Restart OpenCode
Setelah menyimpan config, **restart opencode** agar MCP server terbaca.

---

## Step 6: Verifikasi End-to-End

### 6.1 MobSF Web UI
```powershell
# Buka http://127.0.0.1:8000
# Upload APK -> Static Analysis -> Lihat report
```

### 6.2 REST API
```powershell
python scripts/api_test.py
```

### 6.3 Database Query
```powershell
python scripts/query_scan.py
python scripts/query_scan.py 5ee4829065640f9c936ac861d1650ffc
python scripts/query_scan.py --findings 5ee4829065640f9c936ac861d1650ffc
```

### 6.4 MCP via opencode
Setelah restart opencode, tool `scanFile` akan tersedia:
> "Scan file C:\path\to\app.apk"

---

## Troubleshooting

### "JDK 8+ is not available"
```powershell
# Set JAVA_HOME atau edit config.py
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot
```
Atau edit `C:\Users\<user>\.MobSF\config.py`:
```python
JAVA_DIRECTORY = 'C:/Program Files/Eclipse Adoptium/jdk-21.0.11.10-hotspot/bin/'
```

### "npm/node not recognized"
```powershell
$env:Path = "C:\Program Files\nodejs;" + $env:Path
```

### "poetry not found"
```powershell
# Poetry harus diinstall secara global
python -m pip install poetry==1.8.4
# Atau gunakan full path ke poetry di virtualenv
```

### Port 8000 already in use
```powershell
# Cek proses yang menggunakan port 8000
netstat -ano | findstr ":8000"
# Kill proses jika perlu
taskkill /PID <PID> /F
```

### MCP Server fails to start
```powershell
# Test MCP server secara manual
cd C:\path\to\mobsf-mcp-server
npx tsx server.ts
# Pastikan MobSF sudah running di port 8000
```

---

## Path Lengkap

| Komponen | Absolute Path |
|----------|--------------|
| MobSF root | `C:\Users\LENOVO\OneDrive\Documents\New OpenCode Project\mobsf\` |
| MCP server | `C:\Users\LENOVO\OneDrive\Documents\New OpenCode Project\mobsf-mcp-server\` |
| Database | `C:\Users\LENOVO\.MobSF\db.sqlite3` |
| Config | `C:\Users\LENOVO\.MobSF\config.py` |
| Uploads | `C:\Users\LENOVO\.MobSF\uploads\` |
| Poetry venv | `C:\Users\LENOVO\AppData\Local\pypoetry\Cache\virtualenvs\mobsf-C-OBTTUg-py3.12\` |
| Node global | `C:\Program Files\nodejs\` |
| Java | `C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot\` |
| wkhtmltopdf | `C:\Program Files\wkhtmltopdf\bin\` |
| OpenCode config | `C:\Users\LENOVO\OneDrive\Documents\New OpenCode Project\opencode.json` |
