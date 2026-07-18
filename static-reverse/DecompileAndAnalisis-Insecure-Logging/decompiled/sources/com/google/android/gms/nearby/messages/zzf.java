package com.google.android.gms.nearby.messages;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<Strategy> {
    static void zza(Strategy strategy, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, strategy.zzaFT);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, strategy.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, strategy.zzaFU);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, strategy.zzaFV);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, strategy.zzaFW);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, strategy.zzaFX);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, strategy.zzaFY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfr, reason: merged with bridge method [inline-methods] */
    public Strategy createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg2 = 0;
        boolean zZzc = false;
        int iZzg3 = 0;
        int iZzg4 = 0;
        int iZzg5 = 0;
        int iZzg6 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 5:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg6 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new Strategy(iZzg6, iZzg5, iZzg4, iZzg3, zZzc, iZzg2, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzig, reason: merged with bridge method [inline-methods] */
    public Strategy[] newArray(int i) {
        return new Strategy[i];
    }
}
