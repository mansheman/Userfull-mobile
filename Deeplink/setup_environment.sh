#!/usr/bin/env bash
# =============================================================================
# OVAA Lab - Environment Setup Script
# =============================================================================
# Clones, configures, and builds the OVAA APK for exploitation.
#
# Usage:
#   ./setup_environment.sh
#
# Prerequisites:
#   - Java JDK 17+ (set JAVA_HOME if not detected)
#   - Android SDK (set ANDROID_SDK_ROOT if not at ~/Android/Sdk)
#   - git, adb
# =============================================================================

set -euo pipefail

RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; NC='\033[0m'
log_pass() { echo -e "${GREEN}[✓]${NC} $1"; }
log_fail() { echo -e "${RED}[✗]${NC} $1"; }
log_info() { echo -e "${YELLOW}[*]${NC} $1"; }

# ─── Detect Environment ───────────────────────────────────────

detect_java() {
    if [ -n "${JAVA_HOME:-}" ] && [ -x "${JAVA_HOME}/bin/javac" ]; then
        echo "${JAVA_HOME}"
        return
    fi
    # Try common paths
    for jdk in /usr/lib/jvm/java-21-openjdk-amd64 /usr/lib/jvm/java-17-openjdk-amd64 /usr/lib/jvm/default-java; do
        if [ -x "${jdk}/bin/javac" ]; then
            echo "${jdk}"
            return
        fi
    done
    # Try which
    local javac_path
    javac_path=$(which javac 2>/dev/null || true)
    if [ -n "${javac_path}" ]; then
        dirname "$(dirname "$(readlink -f "${javac_path}")")"
        return
    fi
    echo ""
}

detect_sdk() {
    if [ -n "${ANDROID_SDK_ROOT:-}" ] && [ -d "${ANDROID_SDK_ROOT}/platforms" ]; then
        echo "${ANDROID_SDK_ROOT}"
        return
    fi
    for sdk in ~/Android/Sdk /opt/android-sdk /usr/local/android-sdk; do
        if [ -d "${sdk}/platforms" ]; then
            echo "${sdk}"
            return
        fi
    done
    echo ""
}

# ─── Main ─────────────────────────────────────────────────────

main() {
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║        OVAA LAB - ENVIRONMENT SETUP                         ║"
    echo "╚══════════════════════════════════════════════════════════════╝"

    # Detect Java
    JAVA_HOME_DETECTED=$(detect_java)
    if [ -z "${JAVA_HOME_DETECTED}" ]; then
        log_fail "JDK 17+ not found. Set JAVA_HOME and try again."
        exit 1
    fi
    export JAVA_HOME="${JAVA_HOME_DETECTED}"
    log_pass "Java: ${JAVA_HOME} ($("${JAVA_HOME}/bin/java" -version 2>&1 | head -1))"

    # Detect Android SDK
    SDK_ROOT=$(detect_sdk)
    if [ -z "${SDK_ROOT}" ]; then
        log_fail "Android SDK not found. Set ANDROID_SDK_ROOT and try again."
        exit 1
    fi
    export ANDROID_SDK_ROOT="${SDK_ROOT}"
    log_pass "Android SDK: ${ANDROID_SDK_ROOT}"

    # Check ADB
    if ! command -v adb &>/dev/null; then
        log_fail "adb not found in PATH. Install platform-tools."
        exit 1
    fi
    log_pass "ADB: $(which adb)"

    # Verify emulator
    if ! adb devices 2>/dev/null | grep -q "device$"; then
        log_fail "No emulator/device connected."
        exit 1
    fi
    log_pass "Emulator connected"

    # Clone OVAA
    WORKDIR="/tmp/ovaa"
    if [ ! -d "${WORKDIR}" ]; then
        log_info "Cloning OVAA repository..."
        git clone https://github.com/oversecured/ovaa.git "${WORKDIR}"
    else
        log_info "OVAA already cloned at ${WORKDIR}"
    fi

    # Configure SDK path
    echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${WORKDIR}/local.properties"
    log_pass "local.properties configured"

    # Accept licenses
    if [ -x "${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager" ]; then
        log_info "Accepting SDK licenses..."
        yes | "${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager" --licenses >/dev/null 2>&1 || true
    fi

    # Make gradlew executable
    chmod +x "${WORKDIR}/gradlew" 2>/dev/null || true

    # Add cleartext traffic support for network capture demo
    log_info "Patching AndroidManifest for cleartext traffic (for demo)..."
    local manifest="${WORKDIR}/app/src/main/AndroidManifest.xml"
    if ! grep -q "usesCleartextTraffic" "${manifest}"; then
        sed -i 's/android:allowBackup="true"/android:allowBackup="true" android:usesCleartextTraffic="true"/' "${manifest}"
        log_pass "Added usesCleartextTraffic=\"true\""
    else
        log_info "Cleartext already enabled"
    fi

    # Build APK
    log_info "Building APK (this may take a few minutes)..."
    (cd "${WORKDIR}" && ./gradlew assembleDebug --no-daemon 2>&1 | tail -5)

    # Verify APK
    local apk="${WORKDIR}/app/build/outputs/apk/debug/app-debug.apk"
    if [ ! -f "${apk}" ]; then
        log_fail "APK build failed!"
        exit 1
    fi
    log_pass "APK built: $(ls -lh "${apk}" | awk '{print $5}')"

    # Install APK
    log_info "Installing APK to emulator..."
    adb install -r "${apk}" 2>&1

    # Verify installation
    if adb shell pm list packages 2>/dev/null | grep -q "oversecured.ovaa"; then
        log_pass "OVAA installed successfully"
    else
        log_fail "Installation failed"
        exit 1
    fi

    # Restore original manifest (no cleartext) if you want clean version
    # Uncomment below to revert:
    # cd "${WORKDIR}" && git checkout app/src/main/AndroidManifest.xml

    echo ""
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                    SETUP COMPLETE                           ║"
    echo "╠══════════════════════════════════════════════════════════════╣"
    echo "║  OVAA Repo:   ${WORKDIR}"
    echo "║  APK:         ${apk}"
    echo "║  Package:     oversecured.ovaa"
    echo "╠══════════════════════════════════════════════════════════════╣"
    echo "║  Next: Run ./full_exploit.sh to execute all test scenarios  ║"
    echo "║        Open second terminal: python3 steal_server.py        ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
}

main "$@"
