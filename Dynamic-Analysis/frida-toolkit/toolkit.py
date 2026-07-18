#!/usr/bin/env python3
"""
FRIDA DYNAMIC INSTRUMENTATION TOOLKIT - CLI Runner
===================================================
Target: macOS -> Android/iOS Emulator & Physical Device

Usage:
  # Android (USB Device)
  python3 toolkit.py --android --device usb --app com.example.app
  python3 toolkit.py --android --device usb --spawn com.example.app

  # iOS (USB Device)
  python3 toolkit.py --ios --device usb --app "AppName"
  python3 toolkit.py --ios --device usb --spawn com.example.app

  # Custom script
  python3 toolkit.py --android --device usb --app com.example.app --module network
  python3 toolkit.py --android --device usb --app com.example.app --module android

  # Interactive (no auto-hooks, manual control)
  python3 toolkit.py --android --device usb --app com.example.app --interactive

  # List devices
  python3 toolkit.py --list-devices
  python3 toolkit.py --list-apps

  # SSL Unpinning only
  python3 toolkit.py --android --device usb --app com.example.app --ssl-bypass

Modules:
  all-in-one  : All hooks (default)
  android     : Android-specific hooks only
  ios         : iOS-specific hooks only
  network     : SSL bypass + traffic capture
  native      : Anti-debug + native hooks
  filesystem  : File I/O monitoring
"""

import argparse
import subprocess
import os
import sys
import json
import shutil
from pathlib import Path

TOOLKIT_DIR = Path(__file__).parent.resolve()
SCRIPTS = {
    "all-in-one": TOOLKIT_DIR / "all-in-one.js",
    "android": TOOLKIT_DIR / "android.js",
    "ios": TOOLKIT_DIR / "ios.js",
    "network": TOOLKIT_DIR / "network.js",
    "native": TOOLKIT_DIR / "native.js",
    "filesystem": TOOLKIT_DIR / "filesystem.js",
    "app-hooker": TOOLKIT_DIR / "app-hooker.js",
}


def _find_tool(name):
    """Find a frida tool, searching common venv paths."""
    path = shutil.which(name)
    if path:
        return path
    # Search common venv locations
    candidates = [
        Path(sys.prefix) / "bin" / name,
        Path.home() / "venv" / "bin" / name,
    ]
    for c in candidates:
        if c.exists():
            return str(c)
    return name  # fallback


def find_frida():
    """Check if frida is installed."""
    path = _find_tool("frida")
    if not shutil.which(path):
        print(f"[ERROR] Frida not found. Install with: pip install frida-tools")
        sys.exit(1)
    return path


def run_frida(args):
    """Execute Frida command."""
    cmd = [find_frida()]
    cmd.extend(args)
    print(f"[CMD] {' '.join(cmd)}")
    print("-" * 60)
    try:
        subprocess.run(cmd)
    except KeyboardInterrupt:
        print("\n[INFO] Interrupted by user")


def run_frida_ps(args):
    """Execute frida-ps command."""
    fps = _find_tool("frida-ps")
    cmd = [fps] + args
    print(f"[CMD] {' '.join(cmd)}")
    print("-" * 60)
    try:
        subprocess.run(cmd)
    except KeyboardInterrupt:
        print("\n[INFO] Interrupted by user")


def list_devices():
    """List all Frida-visible devices."""
    lsdev = _find_tool("frida-ls-devices")
    print(f"[CMD] {lsdev}")
    print("-" * 60)
    subprocess.run([lsdev])


def list_apps(device="usb"):
    """List installed applications."""
    flag = "-Ua" if device == "usb" else "-Ra"
    run_frida_ps([flag])


def main():
    parser = argparse.ArgumentParser(
        description="Frida Dynamic Instrumentation Toolkit for macOS",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  %(prog)s --android --app com.example.app
  %(prog)s --ios --app "MyApp"
  %(prog)s --android --app com.example.app --module network
  %(prog)s --android --app com.example.app --ssl-bypass
  %(prog)s --list-devices
  %(prog)s --list-apps
        """
    )
    
    # Platform
    platform_group = parser.add_mutually_exclusive_group()
    platform_group.add_argument("--android", action="store_true", help="Target Android device")
    platform_group.add_argument("--ios", action="store_true", help="Target iOS device")
    
    # Device connection
    parser.add_argument("--device", choices=["usb", "remote"], default="usb",
                        help="Device connection type (default: usb)")
    parser.add_argument("--host", default=None, help="Remote host (for --device remote)")
    
    # Target
    target_group = parser.add_mutually_exclusive_group()
    target_group.add_argument("--app", help="Attach to running app (package name or app name)")
    target_group.add_argument("--spawn", help="Spawn and attach to app")
    target_group.add_argument("--pid", type=int, help="Attach to process by PID")
    target_group.add_argument("--process", help="Attach to process by name")
    
    # Module
    parser.add_argument("--module", choices=list(SCRIPTS.keys()), default="all-in-one",
                        help="Instrumentation module to load (default: all-in-one)")
    
    # Options
    parser.add_argument("--ssl-bypass", action="store_true",
                        help="SSL pinning bypass only")
    parser.add_argument("--pause", action="store_true",
                        help="Pause after spawn (default: resume immediately)")
    parser.add_argument("--interactive", action="store_true",
                        help="Interactive mode (no auto hooks)")
    parser.add_argument("--script-path", help="Custom Frida script path")
    
    # Discovery
    parser.add_argument("--list-devices", action="store_true", help="List Frida devices")
    parser.add_argument("--list-apps", action="store_true", help="List installed apps")
    
    # Extra
    parser.add_argument("--frida-args", nargs="*", default=[], help="Extra frida arguments")
    
    args = parser.parse_args()
    
    # Discovery commands
    if args.list_devices:
        list_devices()
        return
    
    if args.list_apps:
        list_apps(args.device)
        return
    
    # Validate target
    if not args.app and not args.spawn and not args.pid and not args.process and not args.android and not args.ios:
        print("[INFO] No target specified. Use --app, --spawn, --pid, or --process")
        print("[INFO] Use --list-devices or --list-apps for discovery")
        parser.print_help()
        return
    
    # Build Frida command
    frida_args = []
    
    # Device
    if args.device == "usb":
        frida_args.append("-U")
    elif args.device == "remote":
        if args.host:
            frida_args.extend(["-H", args.host])
        else:
            frida_args.append("-R")
    
    # Script
    if args.script_path:
        script_path = args.script_path
    elif args.ssl_bypass:
        script_path = str(SCRIPTS["network"])
    else:
        script_path = str(SCRIPTS[args.module])
    
    frida_args.extend(["-l", script_path])
    
    # Interactive mode overrides module hooks
    if args.interactive:
        frida_args.append("--runtime=v8")
    
    # Pause (Frida 17 default is no-pause; only add --pause if requested)
    if args.spawn and args.pause:
        frida_args.append("--pause")
    
    # Target
    if args.spawn:
        frida_args.extend(["-f", args.spawn])
    elif args.pid:
        frida_args.extend(["-p", str(args.pid)])
    elif args.process:
        frida_args.extend(["-n", args.process])
    elif args.app:
        frida_args.extend(["-n", args.app])
    
    # Extra frida args
    frida_args.extend(args.frida_args)
    
    # Run
    run_frida(frida_args)


if __name__ == "__main__":
    main()
