package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<PlusCommonExtras> {
    static void zza(PlusCommonExtras plusCommonExtras, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, plusCommonExtras.zzxv(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, plusCommonExtras.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, plusCommonExtras.zzxw(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfI, reason: merged with bridge method [inline-methods] */
    public PlusCommonExtras createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 1000:
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
        return new PlusCommonExtras(iZzg, strZzo2, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziy, reason: merged with bridge method [inline-methods] */
    public PlusCommonExtras[] newArray(int i) {
        return new PlusCommonExtras[i];
    }
}
