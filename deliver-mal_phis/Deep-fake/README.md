# web-deepfake

Real-time face swap web server. Each device uses its own camera via browser — no client installation required.

## How it works

1. User opens `https://<server-ip>:5000` in browser
2. Browser captures webcam frames via `getUserMedia`
3. Frames are sent to the server (`POST /process`)
4. Server detects faces (CPU) and swaps them (DirectML GPU)
5. Processed frame returned as JPEG and displayed in browser
6. Each device has its own session — separate target face per device

## Requirements

- Windows with NVIDIA GPU (DirectML support)
- Python 3.10
- Webcam (built-in or USB)
- Other devices on same LAN (phone, laptop, etc.)

## Setup from scratch

```powershell
# 1. Create project folder and venv
mkdir web-deepfake
cd web-deepfake
python -m venv venv

# 2. Install dependencies
venv\Scripts\pip install flask flask-cors opencv-python insightface onnxruntime-directml

# 3. Download models
#    - inswapper_128.onnx (554 MB):
#      https://huggingface.co/ashinsp/FaceFusionModels/resolve/main/inswapper_128.onnx
#    - Place it in models/inswapper_128.onnx
#
#    - buffalo_l (auto-downloaded on first run or copy from ~/.insightface/models/buffalo_l/)
#    - Place in models/buffalo_l/

# 4. Generate self-signed SSL certificate (required for browser camera access)
python -c "
import datetime, ipaddress
from cryptography import x509
from cryptography.x509.oid import NameOID
from cryptography.hazmat.primitives import hashes, serialization
from cryptography.hazmat.primitives.asymmetric import rsa

key = rsa.generate_private_key(public_exponent=65537, key_size=2048)
subject = issuer = x509.Name([x509.NameAttribute(NameOID.COMMON_NAME, 'YOUR_LAN_IP')])
now = datetime.datetime.now(datetime.timezone.utc)
cert = x509.CertificateBuilder().subject_name(subject).issuer_name(issuer)\
    .public_key(key.public_key()).serial_number(x509.random_serial_number())\
    .not_valid_before(now).not_valid_after(now + datetime.timedelta(days=365))\
    .add_extension(x509.SubjectAlternativeName([
        x509.DNSName('localhost'),
        x509.IPAddress(ipaddress.IPv4Address('YOUR_LAN_IP'))
    ]), critical=False).sign(key, hashes.SHA256())

with open('server.key', 'wb') as f:
    f.write(key.private_bytes(serialization.Encoding.PEM,
            serialization.PrivateFormat.TraditionalOpenSSL,
            serialization.NoEncryption()))
with open('server.crt', 'wb') as f:
    f.write(cert.public_bytes(serialization.Encoding.PEM))
print('Certificate created for YOUR_LAN_IP')
"

# 5. Create templates/index.html (see file in this repo)

# 6. Create run_web.bat (see file in this repo)

# 7. Run server
run_web.bat
```

## Usage

1. Double-click `run_web.bat`
2. From any device on the same LAN, open `https://192.168.100.13:5000`
3. Click **Advanced → Proceed to ... (unsafe)** (self-signed cert warning)
4. Click **Mulai Kamera** → allow camera access
5. Upload a face photo → click **Terapkan Wajah**
6. Each device uploads its own target face — independent sessions

## Architecture

- **Frontend**: Plain HTML/CSS/JS, no frameworks. Captures webcam via `getUserMedia`, sends JPEG frames via `fetch()` POST to `/process`.
- **Backend**: Flask (Python). Face detection via insightface (CPU), face swap via inswapper_128.onnx (DirectML GPU).
- **Session**: Per-device cookie-based session. Each device has its own target face and face detection cache.
- **Protocol**: HTTP REST (not WebSocket). Frames are JPEG-encoded for network transfer.

## Endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/` | HTML page |
| POST | `/upload` | Upload target face photo (multipart `image`) |
| POST | `/process` | Send frame (multipart `image`) → returns swapped JPEG |
| GET | `/reset` | Clear target face for current session |

## Performance notes

- Face detection runs on **CPU** every 8 frames (cached in between)
- Face swap runs on **DirectML GPU**
- Recommended client resolution: 480×360
- Expected FPS: 10-20 on RTX 4050 (varies by network latency)
