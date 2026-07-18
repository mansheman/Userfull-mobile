package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<CameraPosition> {
    static void zza(CameraPosition cameraPosition, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, cameraPosition.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) cameraPosition.target, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, cameraPosition.zoom);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, cameraPosition.tilt);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, cameraPosition.bearing);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeL, reason: merged with bridge method [inline-methods] */
    public CameraPosition createFromParcel(Parcel parcel) {
        float fZzl = 0.0f;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        LatLng latLng = null;
        float fZzl2 = 0.0f;
        float fZzl3 = 0.0f;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    latLng = (LatLng) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LatLng.CREATOR);
                    break;
                case 3:
                    fZzl3 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 4:
                    fZzl2 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 5:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CameraPosition(iZzg, latLng, fZzl3, fZzl2, fZzl);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhl, reason: merged with bridge method [inline-methods] */
    public CameraPosition[] newArray(int i) {
        return new CameraPosition[i];
    }
}
