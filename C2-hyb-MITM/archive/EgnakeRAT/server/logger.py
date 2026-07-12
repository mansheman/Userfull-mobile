import os
import logging
from datetime import datetime

try:
    from rich.console import Console
    from rich.theme import Theme
    RICH_AVAILABLE = True
except ImportError:
    RICH_AVAILABLE = False

LOG_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "logs")


class Logger:
    def __init__(self, name: str = "EgnakeRAT"):
        os.makedirs(LOG_DIR, exist_ok=True)
        self.name = name
        self.file_logger = logging.getLogger(name)
        self.file_logger.setLevel(logging.DEBUG)

        if not self.file_logger.handlers:
            log_file = os.path.join(LOG_DIR, f"EgnakeRAT_{datetime.now():%Y%m%d}.log")
            fh = logging.FileHandler(log_file, encoding="utf-8")
            fh.setLevel(logging.DEBUG)
            fh.setFormatter(logging.Formatter(
                "[%(asctime)s] [%(levelname)s] %(message)s",
                datefmt="%Y-%m-%d %H:%M:%S"))
            self.file_logger.addHandler(fh)

        if RICH_AVAILABLE:
            self.console = Console(theme=Theme({
                "info": "cyan",
                "success": "bold green",
                "warning": "bold yellow",
                "error": "bold red",
                "critical": "bold white on red",
                "device": "bold magenta",
                "cmd": "bold blue",
            }))
        else:
            self.console = None

    def _print(self, style, prefix, message):
        if self.console:
            try:
                self.console.print(f"[{style}][{prefix}][/{style}] {message}")
            except UnicodeEncodeError:
                print(f"[{prefix}] {message}")
        else:
            print(f"[{prefix}] {message}")

    def info(self, message):
        self._print("info", "INFO", message)
        self.file_logger.info(message)

    def success(self, message):
        self._print("success", "+", message)
        self.file_logger.info(message)

    def warning(self, message):
        self._print("warning", "WARNING", message)
        self.file_logger.warning(message)

    def error(self, message):
        self._print("error", "ERROR", message)
        self.file_logger.error(message)

    def critical(self, message):
        self._print("critical", "CRITICAL", message)
        self.file_logger.critical(message)

    def device(self, device_id, message):
        short = device_id[:8] if len(device_id) > 8 else device_id
        self._print("device", short, message)
        self.file_logger.info(f"[{device_id}] {message}")

    def command(self, device_id, cmd):
        short = device_id[:8] if len(device_id) > 8 else device_id
        self._print("cmd", f"{short}", f"Command: {cmd}")
        self.file_logger.info(f"[{device_id}] Command: {cmd}")

    def banner(self):
        banner_text = "\n    EgnakeRAT - Advanced Remote Administration Tool\n"
        if self.console:
            try:
                self.console.print(f"[bold red]{banner_text}[/bold red]")
            except UnicodeEncodeError:
                print(banner_text)
        else:
            print(banner_text)


log = Logger()
