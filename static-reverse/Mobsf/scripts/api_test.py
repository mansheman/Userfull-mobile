"""
MobSF REST API Test Tool
========================
Test koneksi dan endpoint REST API MobSF.

Usage:
  python api_test.py                        # Test semua endpoint
  python api_test.py --scan <APK_PATH>      # Upload & scan APK via API
"""

import requests
import json
import sys
import os

MOBSF_URL = "http://127.0.0.1:8000"
API_KEY = "1f9059ff7684102e8827b52f7bad153e94e67f92997aaff92db693ca34575418"

HEADERS = {"Authorization": API_KEY}


def test_version():
    """Test basic connectivity"""
    try:
        r = requests.get(f"{MOBSF_URL}/", timeout=5)
        print(f"[OK] Server reachable (HTTP {r.status_code})")
        return True
    except Exception as e:
        print(f"[FAIL] Cannot reach server: {e}")
        return False


def test_api_upload(file_path):
    """Upload APK/IPA via REST API"""
    if not os.path.exists(file_path):
        print(f"[FAIL] File not found: {file_path}")
        return

    print(f"[INFO] Uploading: {file_path}")
    with open(file_path, "rb") as f:
        r = requests.post(
            f"{MOBSF_URL}/api/v1/upload",
            files={"file": f},
            headers={"Authorization": API_KEY},
            timeout=60
        )

    if r.status_code == 200:
        data = r.json()
        print(f"[OK] Uploaded! Hash: {data.get('hash')}")
        print(f"     File: {data.get('file_name')}")
        return data.get("hash")
    else:
        print(f"[FAIL] Upload failed: HTTP {r.status_code}")
        print(r.text[:500])
        return None


def test_api_scan(md5_hash, file_name, scan_type="apk"):
    """Trigger scan via REST API"""
    print(f"[INFO] Scanning {file_name} (hash: {md5_hash})")
    r = requests.post(
        f"{MOBSF_URL}/api/v1/scan",
        data={"hash": md5_hash, "scan_type": scan_type, "file_name": file_name},
        headers={"Authorization": API_KEY},
        timeout=120
    )
    if r.status_code == 200:
        print(f"[OK] Scan completed!")
        return True
    else:
        print(f"[FAIL] Scan failed: HTTP {r.status_code}")
        return False


def test_api_report(md5_hash):
    """Fetch JSON report"""
    print(f"[INFO] Fetching report for hash: {md5_hash}")
    r = requests.post(
        f"{MOBSF_URL}/api/v1/report_json",
        data={"hash": md5_hash},
        headers={"Authorization": API_KEY},
        timeout=30
    )
    if r.status_code == 200:
        data = r.json()
        print(f"[OK] Report received!")
        print(f"     App: {data.get('app_name')}")
        print(f"     Package: {data.get('package_name')}")
        print(f"     Keys in report: {len(data)}")
        return data
    else:
        print(f"[FAIL] Report fetch failed: HTTP {r.status_code}")
        return None


def test_api_existing_scan(md5_hash):
    """Query existing scan results"""
    print(f"[INFO] Querying existing scan: {md5_hash}")
    r = requests.post(
        f"{MOBSF_URL}/api/v1/report_json",
        data={"hash": md5_hash},
        headers={"Authorization": API_KEY},
        timeout=30
    )
    if r.status_code == 200:
        data = r.json()
        print(f"[OK] Found!")
        print(f"     App: {data.get('app_name')} | {data.get('package_name')}")
        print(f"     Size: {data.get('size')}")
        print(f"     Permissions: {len(data.get('permissions', {}))}")
        manif = data.get('manifest_analysis', [])
        print(f"     Manifest Findings: {len(manif) if isinstance(manif, list) else 0}")
        return data
    else:
        print(f"[FAIL] HTTP {r.status_code}")
        return None


if __name__ == "__main__":
    print(f"\n{'='*60}")
    print(f"  MobSF REST API Test")
    print(f"  URL: {MOBSF_URL}")
    print(f"{'='*60}\n")

    if not test_version():
        print("\n[FATAL] Server tidak berjalan. Jalankan MobSF dulu:")
        print("  run_mobsf.bat")
        sys.exit(1)

    print()

    if len(sys.argv) >= 3 and sys.argv[1] == "--scan":
        # Full scan flow
        file_path = sys.argv[2]
        md5 = test_api_upload(file_path)
        if md5:
            fname = os.path.basename(file_path)
            ext = os.path.splitext(file_path)[1].lower()
            stype = "ios" if ext == ".ipa" else "apk"
            if test_api_scan(md5, fname, stype):
                test_api_report(md5)
    else:
        # Test with existing scan
        test_api_existing_scan("5ee4829065640f9c936ac861d1650ffc")
        print(f"\n{'='*60}")
        print(f"  Usage: python api_test.py --scan <path_to.apk>")
        print(f"         python api_test.py                 (test existing)")
        print(f"{'='*60}")
