package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* loaded from: classes.dex */
public class zzd {
    SharedPreferences zzaxq;
    Context zzpH;

    public zzd(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    public zzd(Context context, String str) {
        this.zzpH = context;
        this.zzaxq = context.getSharedPreferences(str, 4);
        zzde(str + "-no-backup");
    }

    private void zzde(String str) {
        File file = new File(new ContextCompat().getNoBackupFilesDir(this.zzpH), str);
        if (file.exists()) {
            return;
        }
        try {
            if (!file.createNewFile() || isEmpty()) {
                return;
            }
            Log.i("InstanceID/Store", "App restored, clearing state");
            InstanceIDListenerService.zza(this.zzpH, this);
        } catch (IOException e) {
            if (Log.isLoggable("InstanceID/Store", 3)) {
                Log.d("InstanceID/Store", "Error creating file in no backup dir: " + e.getMessage());
            }
        }
    }

    private String zzf(String str, String str2, String str3) {
        return str + "|T|" + str2 + "|" + str3;
    }

    synchronized String get(String key) {
        return this.zzaxq.getString(key, null);
    }

    synchronized String get(String subtype, String key) {
        return this.zzaxq.getString(subtype + "|S|" + key, null);
    }

    boolean isEmpty() {
        return this.zzaxq.getAll().isEmpty();
    }

    public synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String strZzf = zzf(str, str2, str3);
        SharedPreferences.Editor editorEdit = this.zzaxq.edit();
        editorEdit.putString(strZzf, str4);
        editorEdit.putString("appVersion", str5);
        editorEdit.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000));
        editorEdit.commit();
    }

    public synchronized void zzdf(String str) {
        SharedPreferences.Editor editorEdit = this.zzaxq.edit();
        for (String str2 : this.zzaxq.getAll().keySet()) {
            if (str2.startsWith(str)) {
                editorEdit.remove(str2);
            }
        }
        editorEdit.commit();
    }

    public KeyPair zzdg(String str) {
        return zzdj(str);
    }

    void zzdh(String str) {
        zzdf(str + "|");
    }

    public void zzdi(String str) {
        zzdf(str + "|T|");
    }

    KeyPair zzdj(String str) throws NoSuchAlgorithmException {
        String str2 = get(str, "|P|");
        String str3 = get(str, "|K|");
        if (str3 == null) {
            return null;
        }
        try {
            byte[] bArrDecode = Base64.decode(str2, 8);
            byte[] bArrDecode2 = Base64.decode(str3, 8);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return new KeyPair(keyFactory.generatePublic(new X509EncodedKeySpec(bArrDecode)), keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bArrDecode2)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            Log.w("InstanceID/Store", "Invalid key stored " + e);
            InstanceIDListenerService.zza(this.zzpH, this);
            return null;
        }
    }

    synchronized KeyPair zze(String str, long j) {
        KeyPair keyPairZzud;
        keyPairZzud = zza.zzud();
        this.zzaxq.edit().putString(str + "|P|", InstanceID.zzm(keyPairZzud.getPublic().getEncoded())).putString(str + "|K|", InstanceID.zzm(keyPairZzud.getPrivate().getEncoded())).putString(str + "|S|cre", Long.toString(j)).commit();
        return keyPairZzud;
    }

    public synchronized String zzg(String str, String str2, String str3) {
        return this.zzaxq.getString(zzf(str, str2, str3), null);
    }

    public synchronized void zzh(String str, String str2, String str3) {
        String strZzf = zzf(str, str2, str3);
        SharedPreferences.Editor editorEdit = this.zzaxq.edit();
        editorEdit.remove(strZzf);
        editorEdit.commit();
    }

    public synchronized void zzul() {
        this.zzaxq.edit().clear().commit();
    }
}
