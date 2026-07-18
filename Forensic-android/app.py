#!/usr/bin/env python3
from flask import Flask, jsonify, send_file
from flask_cors import CORS
import subprocess
import json
import re
import io
from datetime import datetime

app = Flask(__name__, static_folder='static', static_url_path='')
CORS(app)

ADB_TIMEOUT = 60


# ── Helpers ────────────────────────────────────────────────────────────

def run_adb(args):
    """Run ADB command list, return stdout string."""
    result = subprocess.run(args, capture_output=True, text=True, timeout=ADB_TIMEOUT)
    if result.returncode != 0 and result.stderr:
        raise RuntimeError(result.stderr.strip())
    return result.stdout


def parse_devices(output):
    """Parse 'adb devices -l' → list of {serial, model, status}."""
    devices = []
    lines = output.strip().split('\n')
    if not lines or 'error' in lines[0].lower():
        return devices

    for line in lines[1:]:  # skip header
        line = line.strip()
        if not line:
            continue
        parts = line.split()
        if len(parts) < 2:
            continue

        serial = parts[0]
        status = parts[1]
        model = ''
        for p in parts[2:]:
            if p.startswith('model:'):
                _, _, model = p.partition(':')
                break
        devices.append({'serial': serial, 'model': model, 'status': status})
    return devices


def parse_content_rows(output):
    """
    Parse 'adb shell content query' output.
    Format per baris:  Row: <N>  key1=val1, key2=val2, …
    Commas inside values are handled by splitting only on ', ' followed by
    a word character sequence that ends with '='.
    Returns list of dicts with original column names.
    """
    if not output or output.strip().startswith('No result'):
        return []
    if output.strip().startswith('Error'):
        return []

    rows = []
    for line in output.strip().split('\n'):
        line = line.strip()
        if not line.startswith('Row:'):
            continue

        # Strip "Row: N, " prefix
        remaining = re.sub(r'^Row:\s*\d+,\s*', '', line)
        if not remaining:
            continue

        # Split on ', ' only when the next token is a key=value pair
        # This preserves commas that are part of a value (e.g. SMS body)
        pairs = re.split(r',\s*(?=\w+=)', remaining)

        record = {}
        for pair_str in pairs:
            pair_str = pair_str.strip()
            if not pair_str:
                continue
            eq = pair_str.find('=')
            if eq > 0:
                key = pair_str[:eq].strip()
                val = pair_str[eq + 1:].strip()
                if val == 'null':
                    val = None
                record[key] = val
        if record:
            rows.append(record)
    return rows


# ── Data-type parsers ──────────────────────────────────────────────────

def parse_contacts(output):
    rows = parse_content_rows(output)
    field_map = {'display_name': 'name', 'data1': 'phone', 'number': 'phone'}
    result = []
    for row in rows:
        mapped = {}
        for k, v in row.items():
            mapped[field_map.get(k, k)] = v
        if mapped.get('name') or mapped.get('phone'):
            result.append({'name': mapped.get('name') or '',
                           'phone': mapped.get('phone') or ''})
    return result


def parse_sms(output):
    rows = parse_content_rows(output)
    result = []
    for row in rows:
        sms = {}
        sms['address'] = row.get('address', '')
        sms['body'] = row.get('body', '')
        sms['date'] = row.get('date', '')
        sms['type'] = row.get('type', '')
        result.append(sms)
    return result


def parse_call_logs(output):
    rows = parse_content_rows(output)
    result = []
    for row in rows:
        call = {}
        call['number'] = row.get('number', '')
        call['date'] = row.get('date', '')
        call['duration'] = row.get('duration', '')
        call['type'] = row.get('type', '')
        result.append(call)
    return result


def parse_packages(output):
    pkgs = []
    for line in output.strip().split('\n'):
        line = line.strip()
        if line.startswith('package:'):
            pkgs.append(line[8:])  # strip "package:" prefix
    return sorted(pkgs)


def parse_wifi(output):
    """Extract unique SSID values from 'dumpsys wifi' output."""
    ssids = set()
    # Pattern 1:  SSID: "MyWiFi"
    for m in re.finditer(r'SSID[:\s]+"([^"]+)"', output):
        ssid = m.group(1).strip()
        if ssid and ssid.lower() not in ('<unknown ssid>', '<unknown>', ''):
            ssids.add(ssid)
    # Pattern 2:  SSID: MyWiFi  (unquoted)
    for m in re.finditer(r'(?<![<\w])SSID[:\s]+(\S+)', output):
        ssid = m.group(1).strip().strip('"')
        if ssid and ssid.lower() not in ('<unknown ssid>', '<unknown>', '') and len(ssid) > 1:
            ssids.add(ssid)
    # Pattern 3:  XML-like  <string name="SSID">"MyWiFi"</string>
    for m in re.finditer(r'<string\s+name="SSID">"?([^"<]+)"?</string>', output):
        ssid = m.group(1).strip().strip('"')
        if ssid and ssid.lower() not in ('<unknown ssid>', '<unknown>', ''):
            ssids.add(ssid)
    return sorted(ssids)


# ── Extract all data for a device ──────────────────────────────────────

def extract_data(serial):
    base = ['adb', '-s', serial, 'shell']

    contacts_out = run_adb(base + ['content', 'query', '--uri',
                           'content://contacts/phones'])
    contacts = parse_contacts(contacts_out)

    sms_out = run_adb(base + ['content', 'query', '--uri', 'content://sms'])
    sms = parse_sms(sms_out)

    calls_out = run_adb(base + ['content', 'query', '--uri',
                        'content://call_log/calls'])
    call_logs = parse_call_logs(calls_out)

    pkgs_out = run_adb(base + ['pm', 'list', 'packages'])
    apps = parse_packages(pkgs_out)

    wifi_out = run_adb(base + ['dumpsys', 'wifi'])
    wifi = parse_wifi(wifi_out)

    return {
        'contacts': contacts,
        'sms': sms,
        'call_logs': call_logs,
        'apps': apps,
        'wifi': wifi,
    }


# ── Routes ─────────────────────────────────────────────────────────────

@app.route('/')
def index():
    return app.send_static_file('index.html')


@app.route('/api/devices', methods=['GET'])
def get_devices():
    try:
        output = run_adb(['adb', 'devices', '-l'])
        devices = parse_devices(output)
        return jsonify({'devices': devices})
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/extract/<serial>', methods=['POST'])
def extract(serial):
    try:
        data = extract_data(serial)
        return jsonify(data)
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/export/<serial>', methods=['GET'])
def export(serial):
    try:
        data = extract_data(serial)
        ts = datetime.now().strftime('%Y%m%d_%H%M%S')
        filename = f'evidence_{serial}_{ts}.json'

        buf = io.StringIO()
        json.dump(data, buf, indent=2)
        buf.seek(0)

        return send_file(
            io.BytesIO(buf.getvalue().encode('utf-8')),
            as_attachment=True,
            download_name=filename,
            mimetype='application/json',
        )
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=False)
