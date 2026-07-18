package com.google.android.gms.ads.internal.formats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<NativeAdOptionsParcel> {
    static void zza(NativeAdOptionsParcel nativeAdOptionsParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, nativeAdOptionsParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, nativeAdOptionsParcel.zzvC);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, nativeAdOptionsParcel.zzvD);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, nativeAdOptionsParcel.zzvE);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzf, reason: merged with bridge method [inline-methods] */
    public NativeAdOptionsParcel createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        boolean zZzc2 = false;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
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
        return new NativeAdOptionsParcel(iZzg2, zZzc2, iZzg, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzr, reason: merged with bridge method [inline-methods] */
    public NativeAdOptionsParcel[] newArray(int i) {
        return new NativeAdOptionsParcel[i];
    }
}
