---
name: mobsf-automation
description: Use ONLY when the user wants to scan APK/IPA files with MobSF, analyze mobile app security, query existing MobSF scan results, or perform mobile security assessments. Triggers on keywords: MobSF, APK scan, IPA scan, mobile security, static analysis, android security, ios security, malware analysis, mobile penetration testing, InsecureBankv2, analisis APK.
---

# MobSF Automation Skill

## Overview

This skill provides automated workflows for Mobile Security Framework (MobSF) analysis. It covers three modes of operation:

1. **Scan new files** - Upload and scan APK/IPA via MCP or REST API
2. **Query existing scans** - Retrieve and analyze past scan results from the database
3. **Generate reports** - Create structured security assessment reports

## Environment

| Setting | Value |
|---------|-------|
| MobSF URL | `http://127.0.0.1:8000` |
| REST API Key | `1f9059ff7684102e8827b52f7bad153e94e67f92997aaff92db693ca34575418` |
| Database | `C:\Users\LENOVO\.MobSF\db.sqlite3` |
| Username / Password | `mobsf` / `mobsf` |
| MobSF Home | `C:\Users\LENOVO\OneDrive\Documents\New OpenCode Project\mobsf\` |
| MCP Server | `C:\Users\LENOVO\OneDrive\Documents\New OpenCode Project\mobsf-mcp-server\` |
| Virtual Env | `C:\Users\LENOVO\AppData\Local\pypoetry\Cache\virtualenvs\mobsf-C-OBTTUg-py3.12\` |
| JAVA_HOME | `C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot` |

## Workflow 1: Scan New APK/IPA

### Via MCP (preferred for single files)
Setelah user menyebut file APK/IPA, gunakan tool `scanFile` dari MCP server MobSF.
MCP akan otomatis upload -> scan -> return summary report.

### Via REST API (for automation/batch)
```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
$env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")
$venv = "C:\Users\LENOVO\AppData\Local\pypoetry\Cache\virtualenvs\mobsf-C-OBTTUg-py3.12"
& "$venv\Scripts\python.exe" scripts/api_test.py --scan "<path_to_apk>"
```

## Workflow 2: Query Existing Scan Results

### List recent scans
```powershell
$venv\Scripts\python.exe scripts/query_scan.py
```

### Get detailed findings for a specific scan
```powershell
$venv\Scripts\python.exe scripts/query_scan.py --findings <MD5_HASH>
```

### Query directly from database (for custom analysis)
Database path: `C:\Users\LENOVO\.MobSF\db.sqlite3`

Key tables:
- `StaticAnalyzer_staticanalyzerandroid` - Android scan results
- `StaticAnalyzer_staticanalyzerios` - iOS scan results
- `StaticAnalyzer_recentscansdb` - Scan history/log

Key fields in scan results:
- `MANIFEST_ANALYSIS` - JSON array of manifest findings (severity, title, description)
- `PERMISSIONS` - JSON dict of permissions with status (dangerous/normal)
- `CERTIFICATE_ANALYSIS` - Certificate info (dict)
- `SECRETS` - JSON array of hardcoded secrets
- `TRACKERS` - JSON dict of detected trackers
- `NETWORK_SECURITY` - Network security findings
- `EXPORTED_COUNT` - Exported components count
- `EXPORTED_ACTIVITIES` - List of exported activities
- `APKID` - APKiD analysis results
- `SBOM` - Software Bill of Materials

## Workflow 3: Security Report Generation

### Structured Analysis Report
When generating a security report, organize findings by:

1. **Summary** - App name, package, version, hash
2. **Critical/High Findings** - Manifest issues, exported components, debug flags
3. **Permissions Analysis** - Dangerous permissions with impact explanation
4. **Code Quality** - Hardcoded secrets, certificate issues
5. **Privacy** - Trackers, data collection
6. **Network** - Network security configuration
7. **Recommendations** - Actionable fixes per finding

### Severity Levels
| Icon | Level | Meaning |
|------|-------|---------|
| HIGH | Critical risk requiring immediate fix |
| WARNING | Moderate risk, should be addressed |
| INFO | Informational, best practice |

## Workflow 4: MCP Integration Management

### Start MCP Server Manually
```powershell
$env:Path = "C:\Program Files\nodejs;" + $env:Path
cd "C:\Users\LENOVO\OneDrive\Documents\New OpenCode Project\mobsf-mcp-server"
npx tsx server.ts
```

### Verify MCP Server Status
MCP server runs via opencode's MCP infrastructure. No manual management needed when opencode is configured correctly.

### OpenCode Config Location
`C:\Users\LENOVO\OneDrive\Documents\New OpenCode Project\opencode.json`

## Common Query Patterns

### Find all high-severity manifest findings
```python
import sqlite3, json
conn = sqlite3.connect(r'C:\Users\LENOVO\.MobSF\db.sqlite3')
conn.row_factory = sqlite3.Row
row = conn.execute("SELECT MANIFEST_ANALYSIS, APP_NAME FROM StaticAnalyzer_staticanalyzerandroid WHERE MD5 = ?", (md5,)).fetchone()
findings = json.loads(row['MANIFEST_ANALYSIS'])
high = [f for f in findings if f.get('severity') == 'high']
for f in high:
    print(f"[HIGH] {f['title']}")
conn.close()
```

### Find apps with dangerous permissions
```python
import sqlite3, json
conn = sqlite3.connect(r'C:\Users\LENOVO\.MobSF\db.sqlite3')
conn.row_factory = sqlite3.Row
rows = conn.execute("SELECT APP_NAME, PACKAGE_NAME, PERMISSIONS FROM StaticAnalyzer_staticanalyzerandroid").fetchall()
for r in rows:
    perms = json.loads(r['PERMISSIONS'] or '{}')
    dangerous = [p for p, info in perms.items() if info.get('status') == 'dangerous']
    if dangerous:
        print(f"{r['APP_NAME']}: {len(dangerous)} dangerous permissions")
        for p in dangerous:
            print(f"  - {p}")
conn.close()
```

## Notes

- Always ensure MobSF is running before executing queries or API calls
- The MCP server provides `scanFile` tool only — for existing scan analysis, use database queries
- Java PATH must be set correctly for JADX decompilation to work
- wkhtmltopdf must be in PATH for PDF report generation
- Database is SQLite at `~/.MobSF/db.sqlite3` — can be queried with any SQLite tool
