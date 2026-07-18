package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzu;

/* loaded from: classes.dex */
public class CampaignTrackingReceiver extends BroadcastReceiver {
    static PowerManager.WakeLock zzIc;
    static Boolean zzId;
    static Object zzoW = new Object();

    public static boolean zzT(Context context) {
        zzu.zzu(context);
        if (zzId != null) {
            return zzId.booleanValue();
        }
        boolean zZza = zzam.zza(context, (Class<? extends BroadcastReceiver>) CampaignTrackingReceiver.class, true);
        zzId = Boolean.valueOf(zZza);
        return zZza;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        zzf zzfVarZzV = zzf.zzV(context);
        zzaf zzafVarZzhQ = zzfVarZzV.zzhQ();
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zzafVarZzhQ.zza("CampaignTrackingReceiver received", action);
        if (!"com.android.vending.INSTALL_REFERRER".equals(action) || TextUtils.isEmpty(stringExtra)) {
            zzafVarZzhQ.zzaW("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        boolean zZzU = CampaignTrackingService.zzU(context);
        if (!zZzU) {
            zzafVarZzhQ.zzaW("CampaignTrackingService not registered or disabled. Installation tracking not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzaL(stringExtra);
        if (zzfVarZzV.zzhR().zziW()) {
            zzafVarZzhQ.zzaX("Received unexpected installation campaign on package side");
            return;
        }
        Class<? extends CampaignTrackingService> clsZzhf = zzhf();
        zzu.zzu(clsZzhf);
        Intent intent2 = new Intent(context, clsZzhf);
        intent2.putExtra("referrer", stringExtra);
        synchronized (zzoW) {
            context.startService(intent2);
            if (zZzU) {
                try {
                    PowerManager powerManager = (PowerManager) context.getSystemService("power");
                    if (zzIc == null) {
                        zzIc = powerManager.newWakeLock(1, "Analytics campaign WakeLock");
                        zzIc.setReferenceCounted(false);
                    }
                    zzIc.acquire(1000L);
                } catch (SecurityException e) {
                    zzafVarZzhQ.zzaW("CampaignTrackingService service at risk of not starting. For more reliable installation campaign reports, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                }
            }
        }
    }

    protected void zzaL(String str) {
    }

    protected Class<? extends CampaignTrackingService> zzhf() {
        return CampaignTrackingService.class;
    }
}
