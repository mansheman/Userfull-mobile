package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<PolylineOptions> {
    static void zza(PolylineOptions polylineOptions, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, polylineOptions.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, polylineOptions.getPoints(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, polylineOptions.getWidth());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, polylineOptions.getColor());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, polylineOptions.getZIndex());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, polylineOptions.isVisible());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, polylineOptions.isGeodesic());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeS, reason: merged with bridge method [inline-methods] */
    public PolylineOptions createFromParcel(Parcel parcel) {
        float fZzl = 0.0f;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList arrayListZzc = null;
        boolean zZzc2 = false;
        int iZzg = 0;
        float fZzl2 = 0.0f;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, LatLng.CREATOR);
                    break;
                case 3:
                    fZzl2 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 6:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 7:
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
        return new PolylineOptions(iZzg2, arrayListZzc, fZzl2, iZzg, fZzl, zZzc2, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhs, reason: merged with bridge method [inline-methods] */
    public PolylineOptions[] newArray(int i) {
        return new PolylineOptions[i];
    }
}
