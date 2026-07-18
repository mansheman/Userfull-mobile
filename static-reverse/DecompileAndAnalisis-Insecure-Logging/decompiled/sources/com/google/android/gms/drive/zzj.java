package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzj implements Parcelable.Creator<UserMetadata> {
    static void zza(UserMetadata userMetadata, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, userMetadata.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, userMetadata.zzadH, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, userMetadata.zzadI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, userMetadata.zzadJ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, userMetadata.zzadK);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, userMetadata.zzadL, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzau, reason: merged with bridge method [inline-methods] */
    public UserMetadata createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new UserMetadata(iZzg, strZzo4, strZzo3, strZzo2, zZzc, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcd, reason: merged with bridge method [inline-methods] */
    public UserMetadata[] newArray(int i) {
        return new UserMetadata[i];
    }
}
