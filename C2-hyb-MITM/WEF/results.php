<?php
// Results proxy - forwards command output to dashboard:4443
$post_data = http_build_query($_POST);
$url = escapeshellarg('http://127.0.0.1:4443/results');
$data = escapeshellarg($post_data);
shell_exec("curl -s --connect-timeout 3 -X POST {$url} -d {$data} >/dev/null 2>&1 &");
echo 'OK';
