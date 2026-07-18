package com.google.android.gms.ads.internal.purchase;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzfe;
import com.google.android.gms.internal.zzfg;
import com.google.android.gms.internal.zzgd;

@zzgd
/* loaded from: classes.dex */
public class zze extends zzfg.zza implements ServiceConnection {
    private final Activity mActivity;
    private zzb zzAE;
    zzh zzAF;
    private zzk zzAH;
    private Context zzAM;
    private zzfe zzAN;
    private zzf zzAO;
    private zzj zzAP;
    private String zzAQ = null;

    public zze(Activity activity) {
        this.mActivity = activity;
        this.zzAF = zzh.zzy(this.mActivity.getApplicationContext());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0037 A[Catch: RemoteException -> 0x003f, all -> 0x004d, Merged into TryCatch #1 {all -> 0x004d, RemoteException -> 0x003f, blocks: (B:5:0x0006, B:7:0x0011, B:9:0x0016, B:12:0x0021, B:15:0x0037, B:18:0x0040), top: B:23:0x0006 }, TRY_ENTER, TRY_LEAVE] */
    @Override // com.google.android.gms.internal.zzfg
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onActivityResult(int r6, int r7, android.content.Intent r8) {
        /*
            r5 = this;
            r4 = 0
            r0 = 1001(0x3e9, float:1.403E-42)
            if (r6 != r0) goto L36
            r0 = 0
            com.google.android.gms.ads.internal.purchase.zzi r1 = com.google.android.gms.ads.internal.zzo.zzbF()     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            int r1 = r1.zzd(r8)     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            r2 = -1
            if (r7 != r2) goto L37
            com.google.android.gms.ads.internal.zzo.zzbF()     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            if (r1 != 0) goto L37
            com.google.android.gms.ads.internal.purchase.zzk r2 = r5.zzAH     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            java.lang.String r3 = r5.zzAQ     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            boolean r2 = r2.zza(r3, r7, r8)     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            if (r2 == 0) goto L21
            r0 = 1
        L21:
            com.google.android.gms.internal.zzfe r2 = r5.zzAN     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            r2.recordPlayBillingResolution(r1)     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            android.app.Activity r1 = r5.mActivity     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            r1.finish()     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            com.google.android.gms.internal.zzfe r1 = r5.zzAN     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            java.lang.String r1 = r1.getProductId()     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            r5.zza(r1, r0, r7, r8)     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            r5.zzAQ = r4
        L36:
            return
        L37:
            com.google.android.gms.ads.internal.purchase.zzh r2 = r5.zzAF     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            com.google.android.gms.ads.internal.purchase.zzf r3 = r5.zzAO     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            r2.zza(r3)     // Catch: android.os.RemoteException -> L3f java.lang.Throwable -> L4d
            goto L21
        L3f:
            r0 = move-exception
            java.lang.String r0 = "Fail to process purchase result."
            com.google.android.gms.ads.internal.util.client.zzb.zzaC(r0)     // Catch: java.lang.Throwable -> L4d
            android.app.Activity r0 = r5.mActivity     // Catch: java.lang.Throwable -> L4d
            r0.finish()     // Catch: java.lang.Throwable -> L4d
            r5.zzAQ = r4
            goto L36
        L4d:
            r0 = move-exception
            r5.zzAQ = r4
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.purchase.zze.onActivityResult(int, int, android.content.Intent):void");
    }

    @Override // com.google.android.gms.internal.zzfg
    public void onCreate() {
        GInAppPurchaseManagerInfoParcel gInAppPurchaseManagerInfoParcelZzc = GInAppPurchaseManagerInfoParcel.zzc(this.mActivity.getIntent());
        this.zzAP = gInAppPurchaseManagerInfoParcelZzc.zzAA;
        this.zzAH = gInAppPurchaseManagerInfoParcelZzc.zzqe;
        this.zzAN = gInAppPurchaseManagerInfoParcelZzc.zzAy;
        this.zzAE = new zzb(this.mActivity.getApplicationContext());
        this.zzAM = gInAppPurchaseManagerInfoParcelZzc.zzAz;
        if (this.mActivity.getResources().getConfiguration().orientation == 2) {
            this.mActivity.setRequestedOrientation(zzo.zzbx().zzgq());
        } else {
            this.mActivity.setRequestedOrientation(zzo.zzbx().zzgr());
        }
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
        this.mActivity.bindService(intent, this, 1);
    }

    @Override // com.google.android.gms.internal.zzfg
    public void onDestroy() {
        this.mActivity.unbindService(this);
        this.zzAE.destroy();
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName name, IBinder service) throws IntentSender.SendIntentException {
        this.zzAE.zzK(service);
        try {
            this.zzAQ = this.zzAH.zzfi();
            Bundle bundleZzb = this.zzAE.zzb(this.mActivity.getPackageName(), this.zzAN.getProductId(), this.zzAQ);
            PendingIntent pendingIntent = (PendingIntent) bundleZzb.getParcelable("BUY_INTENT");
            if (pendingIntent == null) {
                int iZzb = zzo.zzbF().zzb(bundleZzb);
                this.zzAN.recordPlayBillingResolution(iZzb);
                zza(this.zzAN.getProductId(), false, iZzb, null);
                this.mActivity.finish();
            } else {
                this.zzAO = new zzf(this.zzAN.getProductId(), this.zzAQ);
                this.zzAF.zzb(this.zzAO);
                Integer num = 0;
                Integer num2 = 0;
                Integer num3 = 0;
                this.mActivity.startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), num.intValue(), num2.intValue(), num3.intValue());
            }
        } catch (IntentSender.SendIntentException | RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Error when connecting in-app billing service", e);
            this.mActivity.finish();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName name) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaA("In-app billing service disconnected.");
        this.zzAE.destroy();
    }

    protected void zza(String str, boolean z, int i, Intent intent) {
        if (this.zzAP != null) {
            this.zzAP.zza(str, z, i, intent, this.zzAO);
        }
    }
}
