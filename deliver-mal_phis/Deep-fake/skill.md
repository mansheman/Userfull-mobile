# Skill: Build web-deepfake from scratch

Build a real-time face swap web server where each browser uses its own camera (getUserMedia) and sends frames to a Flask server for GPU-accelerated face swap.

## Constraints

- OS: Windows, NVIDIA GPU, CUDA driver 12.7 (no CUDA Toolkit)
- Face swap must use DirectML (onnxruntime-directml) — TensorRT/CUDA not available
- Face detection must use CPU — insightface buffalo_l crashes on DirectML
- Must work over LAN on other devices without client install
- Browser `getUserMedia` requires HTTPS (use self-signed cert)
- No WebSocket — use simple HTTP fetch POST

## Architecture

```
Browser (getUserMedia) --JPEG POST--> Flask /process --> face detect (CPU) --> swap (GPU) --> return JPEG
```

## Step 1: Project structure

```
web-deepfake/
├── venv/                    # Python virtual environment
├── models/
│   ├── inswapper_128.onnx   # Face swap model (554 MB)
│   └── buffalo_l/           # Face detection models (~200 MB)
├── templates/
│   └── index.html           # Frontend page
├── web_deepfake.py          # Flask server
├── run_web.bat              # Quick start
├── server.crt / server.key  # Self-signed SSL
├── requirements.txt
└── README.md
```

## Step 2: Virtual environment

```powershell
python -m venv venv
venv\Scripts\pip install flask flask-cors opencv-python insightface onnxruntime-directml
```

Key packages:
- `flask`, `flask-cors` — HTTP server
- `opencv-python` — image encoding/decoding
- `insightface` — face detection (CPU) + face swap model loader
- `onnxruntime-directml` — run inswapper on DirectML GPU

## Step 3: Models

Download `inswapper_128.onnx` (FP32) to `models/`.  
Copy `buffalo_l` to `models/buffalo_l/` so insightface finds it locally.  
Set `os.environ["INSIGHTFACE_HOME"]` to the models directory.

## Step 4: SSL certificate

Generate self-signed cert with:
- Common Name = LAN IP (e.g. `192.168.100.13`)
- SubjectAltName = `IPAddress` + `DNSName('localhost')`
- Must use `x509.IPAddress(ipaddress.IPv4Address(...))`, not `x509.IPAddress(...)` directly

Chrome requires the cert to have proper SAN for IP-based URLs.

## Step 5: Flask server (web_deepfake.py)

### Session management
Each browser gets a UUID cookie. Session stores:
- `source_face`: the uploaded target face (insightface Face object)
- `frame_count`: counter for detection interval
- `cached_faces`: last detected faces (reused between frames)
- `miss_count`: how many frames since last face found

### Face detection optimization
- Only run detection every 8 frames (`DET_INTERVAL = 8`)
- Skip detection if no face found for 6 consecutive frames (`DET_SKIP_AFTER_MISS = 6`)
- Use a global `process_lock` since insightface operations are not thread-safe

### Endpoints
- `POST /upload`: receive target face photo, detect face, store in session
- `POST /process`: receive frame JPEG, detect faces (or use cached), swap, return JPEG
- `GET /reset`: clear session's target face
- `GET /`: serve HTML page

### Provider setup
```python
# Face detection — CPU only
insightface.app.FaceAnalysis(name="buffalo_l", providers=["CPUExecutionProvider"])

# Face swap — DirectML GPU first, CPU fallback
insightface.model_zoo.get_model(path, providers=["DmlExecutionProvider", "CPUExecutionProvider"])
```

## Step 6: Frontend (templates/index.html)

Single HTML page with:
- `<video>` element showing raw webcam (hidden when result displays)
- `<img>` element showing swapped result
- Upload zone for target face photo
- Start/Stop camera buttons
- FPS counter

### Frame loop (JavaScript)
```javascript
async function loop() {
    if (stopRequested || isProcessing) { requestAnimationFrame(loop); return; }
    isProcessing = true;
    canvas.drawImage(video, 0, 0);
    blob = await canvas.toBlob('image/jpeg', 0.6);
    const controller = new AbortController();
    setTimeout(() => controller.abort(), 15000);  // 15s timeout
    const resp = await fetch('/process', { method: 'POST', body: formData, signal: controller.signal });
    const resultBlob = await resp.blob();
    img.src = URL.createObjectURL(resultBlob);
    // show img, hide video
    isProcessing = false;
    requestAnimationFrame(loop);
}
```

- Uses `requestAnimationFrame` recursive loop (not setInterval) — only sends next frame after previous one completes
- `AbortController` prevents hanging requests
- Never sends multiple frames concurrently (`isProcessing` flag)

### Camera access
```javascript
navigator.mediaDevices.getUserMedia({ video: { width: { ideal: 480 }, height: { ideal: 360 } } })
```
- Resolution 480×360 for good balance of quality and speed
- MUST be served over HTTPS (self-signed cert accepted via browser warning)

## Step 7: run_web.bat

```bat
@echo off
cd /d "%~dp0"
"%~dp0venv\Scripts\python.exe" web_deepfake.py
pause
```

## Common issues

| Issue | Fix |
|---|---|
| `getUserMedia` fails | Page must be served over HTTPS, even self-signed |
| Camera not starting | Accept cert warning first ("Proceed to site (unsafe)") |
| Slow/stuttering video | Lower resolution, increase DET_INTERVAL, reduce JPEG quality |
| "No face detected" | Upload a clear front-facing photo |
| Server unreachable from LAN | Check Windows Firewall inbound rule for port 5000 |
| Protobuf / corrupted model | Use `inswapper_128.onnx` (FP32), not `inswapper_128_fp16.onnx` |

## Key files

- `web_deepfake.py` — all server logic
- `templates/index.html` — single-file frontend
- `run_web.bat` — entry point
- `models/README.md` — model documentation
