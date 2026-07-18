package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.internal.zzey;

@zzgd
/* loaded from: classes.dex */
public final class zzew extends com.google.android.gms.dynamic.zzg<zzey> {
    private static final zzew zzAr = new zzew();

    private static final class zza extends Exception {
        public zza(String str) {
            super(str);
        }
    }

    private zzew() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    public static zzex zzb(Activity activity) {
        zzex zzexVarZzd;
        try {
            if (zzc(activity)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzay("Using AdOverlay from the client jar.");
                zzexVarZzd = new com.google.android.gms.ads.internal.overlay.zzc(activity);
            } else {
                zzexVarZzd = zzAr.zzd(activity);
            }
            return zzexVarZzd;
        } catch (zza e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC(e.getMessage());
            return null;
        }
    }

    private static boolean zzc(Activity activity) throws zza {
        Intent intent = activity.getIntent();
        if (intent.hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
            return intent.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false);
        }
        throw new zza("Ad overlay requires the useClientJar flag in intent extras.");
    }

    private zzex zzd(Activity activity) {
        try {
            return zzex.zza.zzI(zzak(activity).zzc(com.google.android.gms.dynamic.zze.zzw(activity)));
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not create remote AdOverlay.", e);
            return null;
        } catch (zzg.zza e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not create remote AdOverlay.", e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.zzg
    /* renamed from: zzH, reason: merged with bridge method [inline-methods] */
    public zzey zzd(IBinder iBinder) {
        return zzey.zza.zzJ(iBinder);
    }
}
