package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<Permission> {
    static void zza(Permission permission, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, permission.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, permission.zzpo(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, permission.zzpp());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, permission.zzpq(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, permission.zzpr(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, permission.getRole());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, permission.zzps());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzas, reason: merged with bridge method [inline-methods] */
    public Permission createFromParcel(Parcel parcel) {
        String strZzo = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        int iZzg2 = 0;
        String strZzo3 = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 7:
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
        return new Permission(iZzg3, strZzo3, iZzg2, strZzo2, strZzo, iZzg, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcb, reason: merged with bridge method [inline-methods] */
    public Permission[] newArray(int i) {
        return new Permission[i];
    }
}
