package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<FavaDiagnosticsEntity> {
    static void zza(FavaDiagnosticsEntity favaDiagnosticsEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, favaDiagnosticsEntity.zzCY);
        zzb.zza(parcel, 2, favaDiagnosticsEntity.zzaby, false);
        zzb.zzc(parcel, 3, favaDiagnosticsEntity.zzabz);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzad, reason: merged with bridge method [inline-methods] */
    public FavaDiagnosticsEntity createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new FavaDiagnosticsEntity(iZzg2, strZzo, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbD, reason: merged with bridge method [inline-methods] */
    public FavaDiagnosticsEntity[] newArray(int i) {
        return new FavaDiagnosticsEntity[i];
    }
}
