package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzay implements Parcelable.Creator<NodeParcelable> {
    static void zza(NodeParcelable nodeParcelable, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, nodeParcelable.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, nodeParcelable.getId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, nodeParcelable.getDisplayName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, nodeParcelable.getHopCount());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, nodeParcelable.isNearby());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhj, reason: merged with bridge method [inline-methods] */
    public NodeParcelable createFromParcel(Parcel parcel) {
        String strZzo = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
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
        return new NodeParcelable(iZzg2, strZzo2, strZzo, iZzg, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzkr, reason: merged with bridge method [inline-methods] */
    public NodeParcelable[] newArray(int i) {
        return new NodeParcelable[i];
    }
}
