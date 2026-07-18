package com.google.android.gms.ads.internal.overlay;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.InterstitialAdParameterParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<AdOverlayInfoParcel> {
    static void zza(AdOverlayInfoParcel adOverlayInfoParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adOverlayInfoParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) adOverlayInfoParcel.zzzB, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, adOverlayInfoParcel.zzex(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, adOverlayInfoParcel.zzey(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, adOverlayInfoParcel.zzez(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, adOverlayInfoParcel.zzeA(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, adOverlayInfoParcel.zzzG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, adOverlayInfoParcel.zzzH);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adOverlayInfoParcel.zzzI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, adOverlayInfoParcel.zzeC(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 11, adOverlayInfoParcel.orientation);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 12, adOverlayInfoParcel.zzzK);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, adOverlayInfoParcel.zzzf, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, (Parcelable) adOverlayInfoParcel.zzpJ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, adOverlayInfoParcel.zzeB(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, (Parcelable) adOverlayInfoParcel.zzzN, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, adOverlayInfoParcel.zzzM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzh, reason: merged with bridge method [inline-methods] */
    public AdOverlayInfoParcel createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        AdLauncherIntentInfoParcel adLauncherIntentInfoParcel = null;
        IBinder iBinderZzp = null;
        IBinder iBinderZzp2 = null;
        IBinder iBinderZzp3 = null;
        IBinder iBinderZzp4 = null;
        String strZzo = null;
        boolean zZzc = false;
        String strZzo2 = null;
        IBinder iBinderZzp5 = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        String strZzo3 = null;
        VersionInfoParcel versionInfoParcel = null;
        IBinder iBinderZzp6 = null;
        String strZzo4 = null;
        InterstitialAdParameterParcel interstitialAdParameterParcel = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    adLauncherIntentInfoParcel = (AdLauncherIntentInfoParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, AdLauncherIntentInfoParcel.CREATOR);
                    break;
                case 3:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 4:
                    iBinderZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 5:
                    iBinderZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 6:
                    iBinderZzp4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 7:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 9:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 10:
                    iBinderZzp5 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 11:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 12:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 13:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 14:
                    versionInfoParcel = (VersionInfoParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, VersionInfoParcel.CREATOR);
                    break;
                case 15:
                    iBinderZzp6 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 16:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 17:
                    interstitialAdParameterParcel = (InterstitialAdParameterParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, InterstitialAdParameterParcel.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AdOverlayInfoParcel(iZzg, adLauncherIntentInfoParcel, iBinderZzp, iBinderZzp2, iBinderZzp3, iBinderZzp4, strZzo, zZzc, strZzo2, iBinderZzp5, iZzg2, iZzg3, strZzo3, versionInfoParcel, iBinderZzp6, strZzo4, interstitialAdParameterParcel);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzw, reason: merged with bridge method [inline-methods] */
    public AdOverlayInfoParcel[] newArray(int i) {
        return new AdOverlayInfoParcel[i];
    }
}
