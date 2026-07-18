package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<DriveId> {
    static void zza(DriveId driveId, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, driveId.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, driveId.zzadd, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, driveId.zzade);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, driveId.zzacO);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, driveId.zzadf);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzap, reason: merged with bridge method [inline-methods] */
    public DriveId createFromParcel(Parcel parcel) {
        long jZzi = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        int iZzg2 = -1;
        long jZzi2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 5:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DriveId(iZzg, strZzo, jZzi2, jZzi, iZzg2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbU, reason: merged with bridge method [inline-methods] */
    public DriveId[] newArray(int i) {
        return new DriveId[i];
    }
}
