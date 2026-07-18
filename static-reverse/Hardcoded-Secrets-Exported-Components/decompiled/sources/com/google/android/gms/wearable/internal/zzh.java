package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<CapabilityInfoParcelable> {
    static void zza(CapabilityInfoParcelable capabilityInfoParcelable, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, capabilityInfoParcelable.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, capabilityInfoParcelable.getName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, capabilityInfoParcelable.zzBa(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgN, reason: merged with bridge method [inline-methods] */
    public CapabilityInfoParcelable createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
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
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, NodeParcelable.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CapabilityInfoParcelable(iZzg, strZzo, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjS, reason: merged with bridge method [inline-methods] */
    public CapabilityInfoParcelable[] newArray(int i) {
        return new CapabilityInfoParcelable[i];
    }
}
