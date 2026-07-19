import os
import ssl
import threading
import uuid

import cv2
import insightface
import numpy as np
from flask import Flask, render_template, Response, request, jsonify, make_response
from flask_cors import CORS

app = Flask(__name__)
app.config["MAX_CONTENT_LENGTH"] = 16 * 1024 * 1024
CORS(app)

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
MODELS_DIR = os.path.join(BASE_DIR, "models")
INSWAPPER_PATH = os.path.join(MODELS_DIR, "inswapper_128.onnx")

face_analyser = None
face_swapper_model = None
process_lock = threading.Lock()

# Use local model cache (no dependency on Deep-Live-Cam)
os.environ["INSIGHTFACE_HOME"] = os.path.join(BASE_DIR, "models")

# Per-device session storage
sessions = {}
sessions_lock = threading.Lock()

COOKIE_NAME = "df_session"


DET_INTERVAL = 8
DET_SKIP_AFTER_MISS = 6  # if no face N times, skip detection


def get_session():
    sid = request.cookies.get(COOKIE_NAME)
    with sessions_lock:
        if sid and sid in sessions:
            return sid, sessions[sid]
        sid = str(uuid.uuid4())
        sessions[sid] = {
            "source_face": None,
            "frame_count": 0,
            "cached_faces": [],
            "miss_count": 0,
        }
        return sid, sessions[sid]


def set_source_face(sid, face):
    with sessions_lock:
        if sid in sessions:
            s = sessions[sid]
            s["source_face"] = face
            s["frame_count"] = 0
            s["cached_faces"] = []
            s["miss_count"] = 0


def reset_source_face(sid):
    with sessions_lock:
        if sid in sessions:
            s = sessions[sid]
            s["source_face"] = None
            s["frame_count"] = 0
            s["cached_faces"] = []
            s["miss_count"] = 0


def get_face_analyser():
    global face_analyser
    if face_analyser is None:
        face_analyser = insightface.app.FaceAnalysis(
            name="buffalo_l",
            providers=["CPUExecutionProvider"],
        )
        face_analyser.prepare(ctx_id=0)
    return face_analyser


def get_face_swapper():
    global face_swapper_model
    if face_swapper_model is None:
        face_swapper_model = insightface.model_zoo.get_model(
            INSWAPPER_PATH,
            providers=["DmlExecutionProvider", "CPUExecutionProvider"],
        )
    return face_swapper_model


def detect_faces(frame):
    return get_face_analyser().get(frame)


@app.route("/")
def index():
    sid, _ = get_session()
    resp = make_response(render_template("index.html"))
    resp.set_cookie(COOKIE_NAME, sid, max_age=86400 * 30)
    return resp


@app.route("/upload", methods=["POST"])
def upload_source():
    if "image" not in request.files:
        return jsonify({"error": "No image uploaded"}), 400
    sid = get_session()[0]
    file = request.files["image"]
    img_bytes = file.read()
    img_array = np.frombuffer(img_bytes, np.uint8)
    img = cv2.imdecode(img_array, cv2.IMREAD_COLOR)
    if img is None:
        return jsonify({"error": "Invalid image"}), 400
    faces = detect_faces(img)
    if not faces:
        return jsonify({"error": "No face detected in image"}), 400
    set_source_face(sid, faces[0])
    resp = jsonify({"status": "ok", "face_detected": True})
    resp.set_cookie(COOKIE_NAME, sid, max_age=86400 * 30)
    return resp


@app.route("/process", methods=["POST"])
def process_client_frame():
    if "image" not in request.files:
        return jsonify({"error": "No image"}), 400
    sid, session = get_session()
    file = request.files["image"]
    img_bytes = file.read()
    img_array = np.frombuffer(img_bytes, np.uint8)
    frame = cv2.imdecode(img_array, cv2.IMREAD_COLOR)
    if frame is None:
        return jsonify({"error": "Invalid image"}), 400
    current_source = session.get("source_face")
    if current_source is not None:
        with process_lock:
            fcount = session["frame_count"]
            do_detect = fcount % DET_INTERVAL == 0
            if session["miss_count"] >= DET_SKIP_AFTER_MISS:
                do_detect = False
            if do_detect:
                faces = detect_faces(frame)
                session["cached_faces"] = faces
                if not faces:
                    session["miss_count"] += 1
                else:
                    session["miss_count"] = 0
            session["frame_count"] += 1
            target_faces = session["cached_faces"]
            if target_faces:
                swapper = get_face_swapper()
                result = frame.copy()
                for face in target_faces:
                    result = swapper.get(result, face, current_source, paste_back=True)
                frame = result
    ret, jpeg = cv2.imencode(".jpg", frame, [cv2.IMWRITE_JPEG_QUALITY, 70])
    if not ret:
        return jsonify({"error": "Encoding failed"}), 500
    return Response(jpeg.tobytes(), mimetype="image/jpeg")


@app.route("/reset")
def reset_source():
    sid = get_session()[0]
    reset_source_face(sid)
    return jsonify({"status": "ok"})


# Cleanup old sessions periodically
def cleanup_old_sessions():
    threading.Timer(3600, cleanup_old_sessions).start()
    with sessions_lock:
        sessions.clear()  # simple: clear all; new ones created on next request


if __name__ == "__main__":
    print("Loading models...")
    _ = get_face_analyser()
    _ = get_face_swapper()
    print("Models loaded.")

    cert_file = os.path.join(os.path.dirname(os.path.abspath(__file__)), "server.crt")
    key_file = os.path.join(os.path.dirname(os.path.abspath(__file__)), "server.key")

    if os.path.exists(cert_file) and os.path.exists(key_file):
        context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
        context.load_cert_chain(cert_file, key_file)
        print("Server: https://192.168.100.13:5000")
        app.run(host="0.0.0.0", port=5000, ssl_context=context, debug=False, threaded=True)
    else:
        print("Server (no SSL - camera only on localhost): http://192.168.100.13:5000")
        app.run(host="0.0.0.0", port=5000, debug=False, threaded=True)
