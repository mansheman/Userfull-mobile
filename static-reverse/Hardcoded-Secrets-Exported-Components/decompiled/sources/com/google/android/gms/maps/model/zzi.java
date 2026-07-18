package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzi implements Parcelable.Creator<StreetViewPanoramaCamera> {
    static void zza(StreetViewPanoramaCamera streetViewPanoramaCamera, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, streetViewPanoramaCamera.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, streetViewPanoramaCamera.zoom);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, streetViewPanoramaCamera.tilt);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, streetViewPanoramaCamera.bearing);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeT, reason: merged with bridge method [inline-methods] */
    public StreetViewPanoramaCamera createFromParcel(Parcel parcel) {
        float fZzl = 0.0f;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        float fZzl2 = 0.0f;
        int iZzg = 0;
        float fZzl3 = 0.0f;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    fZzl2 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 3:
                    fZzl3 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 4:
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
        return new StreetViewPanoramaCamera(iZzg, fZzl2, fZzl3, fZzl);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzht, reason: merged with bridge method [inline-methods] */
    public StreetViewPanoramaCamera[] newArray(int i) {
        return new StreetViewPanoramaCamera[i];
    }
}
