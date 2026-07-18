package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<LatLng> {
    static void zza(LatLng latLng, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, latLng.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, latLng.latitude);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, latLng.longitude);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeP, reason: merged with bridge method [inline-methods] */
    public LatLng createFromParcel(Parcel parcel) {
        double dZzm = 0.0d;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        double dZzm2 = 0.0d;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    dZzm2 = com.google.android.gms.common.internal.safeparcel.zza.zzm(parcel, iZzaa);
                    break;
                case 3:
                    dZzm = com.google.android.gms.common.internal.safeparcel.zza.zzm(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new LatLng(iZzg, dZzm2, dZzm);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhp, reason: merged with bridge method [inline-methods] */
    public LatLng[] newArray(int i) {
        return new LatLng[i];
    }
}
