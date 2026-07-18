package com.google.android.gms.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.drive.DriveFile;

/* loaded from: classes.dex */
class zza {
    static zza zzavF;
    private Context mContext;

    /* renamed from: com.google.android.gms.gcm.zza$zza, reason: collision with other inner class name */
    private class C0067zza extends IllegalArgumentException {
        private C0067zza(String str) {
            super(str);
        }
    }

    private zza(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private void zza(String str, Notification notification) {
        if (Log.isLoggable("GcmNotification", 3)) {
            Log.d("GcmNotification", "Showing notification");
        }
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        if (TextUtils.isEmpty(str)) {
            str = "GCM-Notification:" + SystemClock.uptimeMillis();
        }
        notificationManager.notify(str, 0, notification);
    }

    static synchronized zza zzar(Context context) {
        if (zzavF == null) {
            zzavF = new zza(context);
        }
        return zzavF;
    }

    static String zzb(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    private int zzda(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new C0067zza("Missing icon");
        }
        Resources resources = this.mContext.getResources();
        int identifier = resources.getIdentifier(str, "drawable", this.mContext.getPackageName());
        if (identifier == 0 && (identifier = resources.getIdentifier(str, "mipmap", this.mContext.getPackageName())) == 0) {
            throw new C0067zza("Icon resource not found: " + str);
        }
        return identifier;
    }

    private Uri zzdb(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if ("default".equals(str)) {
            return RingtoneManager.getDefaultUri(2);
        }
        throw new C0067zza("Invalid sound: " + str);
    }

    static boolean zzt(Bundle bundle) {
        return zzb(bundle, "gcm.n.title") != null;
    }

    private Notification zzv(Bundle bundle) {
        String strZzb = zzb(bundle, "gcm.n.title");
        if (TextUtils.isEmpty(strZzb)) {
            throw new C0067zza("Missing title");
        }
        String strZzb2 = zzb(bundle, "gcm.n.body");
        int iZzda = zzda(zzb(bundle, "gcm.n.icon"));
        Uri uriZzdb = zzdb(zzb(bundle, "gcm.n.sound"));
        PendingIntent pendingIntentZzw = zzw(bundle);
        if (Build.VERSION.SDK_INT >= 11) {
            Notification.Builder contentText = new Notification.Builder(this.mContext).setAutoCancel(true).setSmallIcon(iZzda).setContentTitle(strZzb).setContentText(strZzb2);
            if (Build.VERSION.SDK_INT >= 21) {
                String strZzb3 = zzb(bundle, "gcm.n.color");
                if (!TextUtils.isEmpty(strZzb3)) {
                    contentText.setColor(Color.parseColor(strZzb3));
                }
            }
            if (uriZzdb != null) {
                contentText.setSound(uriZzdb);
            }
            if (pendingIntentZzw != null) {
                contentText.setContentIntent(pendingIntentZzw);
            }
            return Build.VERSION.SDK_INT >= 16 ? contentText.build() : contentText.getNotification();
        }
        Notification notification = new Notification();
        notification.flags |= 16;
        notification.icon = iZzda;
        if (uriZzdb != null) {
            notification.sound = uriZzdb;
        }
        if (pendingIntentZzw == null) {
            Intent intent = new Intent();
            intent.setPackage("com.google.example.invalidpackage");
            pendingIntentZzw = PendingIntent.getBroadcast(this.mContext, 0, intent, 0);
        }
        notification.setLatestEventInfo(this.mContext, strZzb, strZzb2, pendingIntentZzw);
        return notification;
    }

    private PendingIntent zzw(Bundle bundle) {
        String strZzb = zzb(bundle, "gcm.n.click_action");
        if (TextUtils.isEmpty(strZzb)) {
            return null;
        }
        Intent intent = new Intent(strZzb);
        intent.setPackage(this.mContext.getPackageName());
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        intent.putExtras(bundle);
        for (String str : bundle.keySet()) {
            if (str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) {
                intent.removeExtra(str);
            }
        }
        return PendingIntent.getActivity(this.mContext, 0, intent, 1073741824);
    }

    boolean zzu(Bundle bundle) {
        try {
            zza(zzb(bundle, "gcm.n.tag"), zzv(bundle));
            return true;
        } catch (C0067zza e) {
            Log.w("GcmNotification", "Failed to show notification: " + e.getMessage());
            return false;
        }
    }
}
