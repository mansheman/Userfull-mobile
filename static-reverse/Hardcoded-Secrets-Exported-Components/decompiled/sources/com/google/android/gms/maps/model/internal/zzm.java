package com.google.android.gms.maps.model.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzm implements Parcelable.Creator<MarkerOptionsParcelable> {
    static void zza(MarkerOptionsParcelable markerOptionsParcelable, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, markerOptionsParcelable.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) markerOptionsParcelable.zzvO(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfd, reason: merged with bridge method [inline-methods] */
    public MarkerOptionsParcelable createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        BitmapDescriptorParcelable bitmapDescriptorParcelable = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    bitmapDescriptorParcelable = (BitmapDescriptorParcelable) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, BitmapDescriptorParcelable.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new MarkerOptionsParcelable(iZzg, bitmapDescriptorParcelable);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhE, reason: merged with bridge method [inline-methods] */
    public MarkerOptionsParcelable[] newArray(int i) {
        return new MarkerOptionsParcelable[i];
    }
}
