package com.google.android.gms.ads.internal.reward.client;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<RewardedVideoAdRequestParcel> {
    static void zza(RewardedVideoAdRequestParcel rewardedVideoAdRequestParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, rewardedVideoAdRequestParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) rewardedVideoAdRequestParcel.zzCm, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, rewardedVideoAdRequestParcel.zzpG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzH, reason: merged with bridge method [inline-methods] */
    public RewardedVideoAdRequestParcel[] newArray(int i) {
        return new RewardedVideoAdRequestParcel[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzn, reason: merged with bridge method [inline-methods] */
    public RewardedVideoAdRequestParcel createFromParcel(Parcel parcel) {
        String strZzo;
        AdRequestParcel adRequestParcel;
        int iZzg;
        String str = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        AdRequestParcel adRequestParcel2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    String str2 = str;
                    adRequestParcel = adRequestParcel2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    strZzo = str2;
                    break;
                case 2:
                    AdRequestParcel adRequestParcel3 = (AdRequestParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, AdRequestParcel.CREATOR);
                    iZzg = i;
                    strZzo = str;
                    adRequestParcel = adRequestParcel3;
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    adRequestParcel = adRequestParcel2;
                    iZzg = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    strZzo = str;
                    adRequestParcel = adRequestParcel2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            adRequestParcel2 = adRequestParcel;
            str = strZzo;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new RewardedVideoAdRequestParcel(i, adRequestParcel2, str);
    }
}
