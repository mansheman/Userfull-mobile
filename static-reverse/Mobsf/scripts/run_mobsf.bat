@echo off
echo ============================================
echo   MobSF - Mobile Security Framework v4.5.1
echo ============================================
echo.
echo Server  : http://127.0.0.1:8000
echo Login   : mobsf / mobsf
echo ============================================
echo.

set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

cd /d "%~dp0"
call "C:\Users\LENOVO\AppData\Local\pypoetry\Cache\virtualenvs\mobsf-C-OBTTUg-py3.12\Scripts\python.exe" start_server.py
pause
