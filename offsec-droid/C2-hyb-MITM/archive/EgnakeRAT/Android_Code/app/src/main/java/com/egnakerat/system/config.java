package com.egnakerat.system;

public class config {
    public static String IP = "10.0.0.1";
    public static String port = "8000";
    public static boolean icon = true;
    public static String PASSPHRASE = "EgnakeRAT_v2_SecureKey_2026";

    public static final long HEARTBEAT_INTERVAL = 30000;
    public static final long RECONNECT_DELAY = 5000;
    public static final long MAX_RECONNECT_DELAY = 60000;
    public static final long MAX_FILE_SIZE = 50 * 1024 * 1024;
}
