package com.google.android.gms.ads.internal.reward.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.reward.client.zzb;
import com.google.android.gms.ads.internal.reward.client.zzc;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzgd;
import com.google.android.gms.internal.zzgn;

@zzgd
/* loaded from: classes.dex */
public class zzf extends com.google.android.gms.dynamic.zzg<zzc> {
    public zzf() {
        super("com.google.android.gms.ads.reward.RewardedVideoAdCreatorImpl");
    }

    private zzb zzb(Context context, zzee zzeeVar) {
        try {
            return zzb.zza.zzX(zzak(context).zza(com.google.android.gms.dynamic.zze.zzw(context), zzeeVar, 7571000));
        } catch (RemoteException | zzg.zza e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not get remote RewardedVideoAd.", e);
            return null;
        }
    }

    public zzb zza(Context context, zzee zzeeVar) {
        zzb zzbVarZzb;
        if (zzk.zzcA().zzP(context) && (zzbVarZzb = zzb(context, zzeeVar)) != null) {
            return zzbVarZzb;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzay("Using RewardedVideoAd from the client jar.");
        return new zzgn(context, zzeeVar, new VersionInfoParcel(7571000, 7571000, true));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.zzg
    /* renamed from: zzaa, reason: merged with bridge method [inline-methods] */
    public zzc zzd(IBinder iBinder) {
        return zzc.zza.zzY(iBinder);
    }
}
