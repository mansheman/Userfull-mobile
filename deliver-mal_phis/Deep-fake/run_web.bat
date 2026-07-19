@echo off
title Deep Fake Web Server
cd /d "%~dp0"

echo ========================================
echo   Deep Fake Web Server (HTTPS)
echo   Open in browser:
echo   https://192.168.100.13:5000
echo.
echo   NOTE: Use HTTPS (not HTTP) for camera access
echo   Ignore the "Not Secure" warning in browser
echo ========================================
echo.

"%~dp0venv\Scripts\python.exe" web_deepfake.py
pause
