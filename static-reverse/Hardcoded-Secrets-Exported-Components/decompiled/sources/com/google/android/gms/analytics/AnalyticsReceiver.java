package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzu;

/* loaded from: classes.dex */
public final class AnalyticsReceiver extends BroadcastReceiver {
    static PowerManager.WakeLock zzIc;
    static Boolean zzId;
    static Object zzoW = new Object();

    public static boolean zzT(Context context) {
        zzu.zzu(context);
        if (zzId != null) {
            return zzId.booleanValue();
        }
        boolean zZza = zzam.zza(context, (Class<? extends BroadcastReceiver>) AnalyticsReceiver.class, false);
        zzId = Boolean.valueOf(zZza);
        return zZza;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws PackageManager.NameNotFoundException {
        zzf zzfVarZzV = zzf.zzV(context);
        zzaf zzafVarZzhQ = zzfVarZzV.zzhQ();
        String action = intent.getAction();
        if (zzfVarZzV.zzhR().zziW()) {
            zzafVarZzhQ.zza("Device AnalyticsReceiver got", action);
        } else {
            zzafVarZzhQ.zza("Local AnalyticsReceiver got", action);
        }
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zZzU = AnalyticsService.zzU(context);
            Intent intent2 = new Intent(context, (Class<?>) AnalyticsService.class);
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (zzoW) {
                context.startService(intent2);
                if (zZzU) {
                    try {
                        PowerManager powerManager = (PowerManager) context.getSystemService("power");
                        if (zzIc == null) {
                            zzIc = powerManager.newWakeLock(1, "Analytics WakeLock");
                            zzIc.setReferenceCounted(false);
                        }
                        zzIc.acquire(1000L);
                    } catch (SecurityException e) {
                        zzafVarZzhQ.zzaW("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                }
            }
        }
    }
}
