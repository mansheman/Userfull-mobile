package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/* loaded from: classes.dex */
public class zza extends zzd {
    public static boolean zzJk;
    private AdvertisingIdClient.Info zzJl;
    private final zzaj zzJm;
    private String zzJn;
    private boolean zzJo;
    private Object zzJp;

    zza(zzf zzfVar) {
        super(zzfVar);
        this.zzJo = false;
        this.zzJp = new Object();
        this.zzJm = new zzaj(zzfVar.zzhP());
    }

    private boolean zza(AdvertisingIdClient.Info info, AdvertisingIdClient.Info info2) {
        String strZziQ;
        String id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        String strZziP = zzhV().zziP();
        synchronized (this.zzJp) {
            if (!this.zzJo) {
                this.zzJn = zzhF();
                this.zzJo = true;
            } else if (TextUtils.isEmpty(this.zzJn)) {
                String id2 = info != null ? info.getId() : null;
                if (id2 == null) {
                    return zzaS(id + strZziP);
                }
                this.zzJn = zzaR(id2 + strZziP);
            }
            String strZzaR = zzaR(id + strZziP);
            if (TextUtils.isEmpty(strZzaR)) {
                return false;
            }
            if (strZzaR.equals(this.zzJn)) {
                return true;
            }
            if (TextUtils.isEmpty(this.zzJn)) {
                strZziQ = strZziP;
            } else {
                zzaT("Resetting the client id because Advertising Id changed.");
                strZziQ = zzhV().zziQ();
                zza("New client Id", strZziQ);
            }
            return zzaS(id + strZziQ);
        }
    }

    private static String zzaR(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigestZzbl = zzam.zzbl("MD5");
        if (messageDigestZzbl == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestZzbl.digest(str.getBytes())));
    }

    private boolean zzaS(String str) throws NoSuchAlgorithmException, IOException {
        try {
            String strZzaR = zzaR(str);
            zzaT("Storing hashed adid.");
            FileOutputStream fileOutputStreamOpenFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            fileOutputStreamOpenFileOutput.write(strZzaR.getBytes());
            fileOutputStreamOpenFileOutput.close();
            this.zzJn = strZzaR;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    private synchronized AdvertisingIdClient.Info zzhD() {
        if (this.zzJm.zzv(1000L)) {
            this.zzJm.start();
            AdvertisingIdClient.Info infoZzhE = zzhE();
            if (zza(this.zzJl, infoZzhE)) {
                this.zzJl = infoZzhE;
            } else {
                zzaX("Failed to reset client id on adid change. Not using adid");
                this.zzJl = new AdvertisingIdClient.Info("", false);
            }
        }
        return this.zzJl;
    }

    public String zzhC() {
        zzia();
        AdvertisingIdClient.Info infoZzhD = zzhD();
        String id = infoZzhD != null ? infoZzhD.getId() : null;
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return id;
    }

    protected AdvertisingIdClient.Info zzhE() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException e) {
            zzaW("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Throwable th) {
            if (zzJk) {
                return null;
            }
            zzJk = true;
            zzd("Error getting advertiser id", th);
            return null;
        }
    }

    protected String zzhF() throws IOException {
        String str = null;
        try {
            FileInputStream fileInputStreamOpenFileInput = getContext().openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int i = fileInputStreamOpenFileInput.read(bArr, 0, 128);
            if (fileInputStreamOpenFileInput.available() > 0) {
                zzaW("Hash file seems corrupted, deleting it.");
                fileInputStreamOpenFileInput.close();
                getContext().deleteFile("gaClientIdData");
            } else if (i <= 0) {
                zzaT("Hash file is empty.");
                fileInputStreamOpenFileInput.close();
            } else {
                String str2 = new String(bArr, 0, i);
                try {
                    fileInputStreamOpenFileInput.close();
                    str = str2;
                } catch (FileNotFoundException e) {
                    str = str2;
                } catch (IOException e2) {
                    str = str2;
                    e = e2;
                    zzd("Error reading Hash file, deleting it", e);
                    getContext().deleteFile("gaClientIdData");
                    return str;
                }
            }
        } catch (FileNotFoundException e3) {
        } catch (IOException e4) {
            e = e4;
        }
        return str;
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhn() {
    }

    public boolean zzhy() {
        zzia();
        AdvertisingIdClient.Info infoZzhD = zzhD();
        return (infoZzhD == null || infoZzhD.isLimitAdTrackingEnabled()) ? false : true;
    }
}
