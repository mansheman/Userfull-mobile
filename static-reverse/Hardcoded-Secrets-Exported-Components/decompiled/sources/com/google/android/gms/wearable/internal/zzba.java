package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzba implements Parcelable.Creator<PackageStorageInfo> {
    static void zza(PackageStorageInfo packageStorageInfo, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, packageStorageInfo.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, packageStorageInfo.packageName, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, packageStorageInfo.label, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, packageStorageInfo.zzaUN);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhl, reason: merged with bridge method [inline-methods] */
    public PackageStorageInfo createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        long jZzi = 0;
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PackageStorageInfo(iZzg, strZzo2, strZzo, jZzi);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzkt, reason: merged with bridge method [inline-methods] */
    public PackageStorageInfo[] newArray(int i) {
        return new PackageStorageInfo[i];
    }
}
