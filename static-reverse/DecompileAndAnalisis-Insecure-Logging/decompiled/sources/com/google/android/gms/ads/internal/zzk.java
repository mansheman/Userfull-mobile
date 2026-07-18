package com.google.android.gms.ads.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<InterstitialAdParameterParcel> {
    static void zza(InterstitialAdParameterParcel interstitialAdParameterParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, interstitialAdParameterParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, interstitialAdParameterParcel.zzoU);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, interstitialAdParameterParcel.zzoV);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
    public InterstitialAdParameterParcel createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        boolean zZzc2 = false;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new InterstitialAdParameterParcel(iZzg, zZzc2, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzf, reason: merged with bridge method [inline-methods] */
    public InterstitialAdParameterParcel[] newArray(int i) {
        return new InterstitialAdParameterParcel[i];
    }
}
