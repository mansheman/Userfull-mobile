"""
MobSF Scan Query Tool
=====================
Query hasil scan statik dari database MobSF SQLite.
Gunakan untuk analisis hasil scan yang sudah ada.

Usage:
  python query_scan.py                    # List recent scans
  python query_scan.py <MD5_HASH>         # Detail satu scan
  python query_scan.py --findings <MD5>   # Semua temuan security
"""

import sqlite3
import json
import sys
import os

DB_PATH = os.path.join(os.path.expanduser("~"), ".MobSF", "db.sqlite3")


def get_connection():
    if not os.path.exists(DB_PATH):
        print(f"[ERROR] Database not found: {DB_PATH}")
        print("Pastikan MobSF sudah pernah dijalankan dan melakukan scan.")
        sys.exit(1)
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    return conn


def list_recent_scans(limit=10):
    conn = get_connection()
    rows = conn.execute("""
        SELECT MD5, FILE_NAME, APP_NAME, PACKAGE_NAME, VERSION_NAME, TIMESTAMP
        FROM StaticAnalyzer_recentscansdb
        ORDER BY TIMESTAMP DESC LIMIT ?
    """, (limit,)).fetchall()
    conn.close()

    print(f"\n{'='*70}")
    print(f"  Recent MobSF Scans (last {limit})")
    print(f"{'='*70}")
    for r in rows:
        print(f"  MD5:  {r['MD5']}")
        print(f"  App:  {r['APP_NAME']} ({r['PACKAGE_NAME']})")
        print(f"  File: {r['FILE_NAME']} | v{r['VERSION_NAME']}")
        print(f"  Time: {r['TIMESTAMP']}")
        print(f"  {'-'*60}")
    return rows


def get_scan_detail(md5_hash):
    conn = get_connection()
    row = conn.execute(
        "SELECT * FROM StaticAnalyzer_staticanalyzerandroid WHERE MD5 = ?",
        (md5_hash,)
    ).fetchone()
    conn.close()

    if not row:
        print(f"[ERROR] No scan found for MD5: {md5_hash}")
        return

    d = dict(row)

    print(f"\n{'='*70}")
    print(f"  Scan Detail: {d.get('APP_NAME', 'N/A')}")
    print(f"{'='*70}")
    print(f"  Package      : {d.get('PACKAGE_NAME')}")
    print(f"  Version      : {d.get('VERSION_NAME')}")
    print(f"  MD5          : {d.get('MD5')}")
    print(f"  SHA1         : {d.get('SHA1')}")
    print(f"  SHA256       : {d.get('SHA256')}")
    print(f"  Size         : {d.get('SIZE')}")
    print(f"  Main Activity: {d.get('MAIN_ACTIVITY')}")
    print(f"  Min SDK      : {d.get('MIN_SDK')}")
    print(f"  Target SDK   : {d.get('TARGET_SDK')}")
    print(f"  Version Code : {d.get('VERSION_CODE')}")
    return d


def get_findings(md5_hash):
    conn = get_connection()
    row = conn.execute(
        "SELECT * FROM StaticAnalyzer_staticanalyzerandroid WHERE MD5 = ?",
        (md5_hash,)
    ).fetchone()
    conn.close()

    if not row:
        print(f"[ERROR] No scan found for MD5: {md5_hash}")
        return

    d = dict(row)

    # Manifest Analysis
    manifest = d.get('MANIFEST_ANALYSIS', '[]')
    if manifest and manifest != '[]':
        print(f"\n{'='*70}")
        print(f"  MANIFEST ANALYSIS FINDINGS")
        print(f"{'='*70}")
        manifest = json.loads(manifest) if isinstance(manifest, str) else manifest
        for item in manifest if isinstance(manifest, list) else []:
            sev = item.get('severity', 'info').upper()
            print(f"\n  [{sev}] {item.get('title', '')}")
            print(f"  Rule: {item.get('rule', '')}")
            print(f"  {item.get('description', '')[:200]}")

    # Certificate Analysis
    print(f"\n{'='*70}")
    print(f"  CERTIFICATE ANALYSIS")
    print(f"{'='*70}")
    import ast
    cert = d.get('CERTIFICATE_ANALYSIS', '')
    if cert:
        try:
            cert = ast.literal_eval(cert)
            print(f"  {cert.get('certificate_info', cert)[:500]}")
        except:
            print(f"  {str(cert)[:500]}")

    # Permissions
    print(f"\n{'='*70}")
    print(f"  PERMISSIONS")
    print(f"{'='*70}")
    perms = d.get('PERMISSIONS', '{}')
    if perms and perms != '{}':
        perms = json.loads(perms) if isinstance(perms, str) else perms
        for perm, info in perms.items():
            status = info.get('status', '?')
            desc = info.get('description', info.get('info', ''))
            print(f"  [{status.upper()}] {perm}")
            print(f"         {desc}")

    # Exported Components
    print(f"\n{'='*70}")
    print(f"  EXPORTED COMPONENTS")
    print(f"{'='*70}")
    ec = d.get('EXPORTED_COUNT', '')
    if ec:
        if isinstance(ec, str):
            ec = json.loads(ec)
        print(f"  Activities : {ec.get('exported_activities', '?')}")
        print(f"  Services   : {ec.get('exported_services', '?')}")
        print(f"  Receivers  : {ec.get('exported_receivers', '?')}")
        print(f"  Providers  : {ec.get('exported_providers', '?')}")

    ea = d.get('EXPORTED_ACTIVITIES', '[]')
    if ea and ea != '[]':
        ea = json.loads(ea) if isinstance(ea, str) else ea
        print(f"\n  Exported Activities:")
        for a in ea:
            print(f"    - {a}")

    # Trackers
    print(f"\n{'='*70}")
    print(f"  TRACKERS")
    print(f"{'='*70}")
    trackers = d.get('TRACKERS', '{}')
    if trackers and trackers != '{}':
        trackers = json.loads(trackers) if isinstance(trackers, str) else trackers
        print(f"  Total detected: {trackers.get('detected_trackers', 0)}")
        for t in trackers.get('trackers', []):
            print(f"    - {t.get('name', '?')} ({t.get('categories', '?')})")

    # Network Security
    print(f"\n{'='*70}")
    print(f"  NETWORK SECURITY")
    print(f"{'='*70}")
    ns = d.get('NETWORK_SECURITY', '{}')
    if ns and ns != '{}':
        ns = json.loads(ns) if isinstance(ns, str) else ns
        print(json.dumps(ns, indent=2, default=str))

    # Secrets
    print(f"\n{'='*70}")
    print(f"  HARDCODED SECRETS")
    print(f"{'='*70}")
    secrets = d.get('SECRETS', '[]')
    if secrets and secrets != '[]':
        secrets = json.loads(secrets) if isinstance(secrets, str) else secrets
        for s in secrets[:10] if isinstance(secrets, list) else []:
            print(f"  - {s}")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        list_recent_scans()
    elif sys.argv[1] == "--findings" and len(sys.argv) > 2:
        get_findings(sys.argv[2])
    else:
        get_scan_detail(sys.argv[1])
