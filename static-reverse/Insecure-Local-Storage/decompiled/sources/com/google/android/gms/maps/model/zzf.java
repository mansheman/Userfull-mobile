package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<MarkerOptions> {
    static void zza(MarkerOptions markerOptions, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, markerOptions.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) markerOptions.getPosition(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, markerOptions.getTitle(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, markerOptions.getSnippet(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, markerOptions.zzvJ(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, markerOptions.getAnchorU());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, markerOptions.getAnchorV());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, markerOptions.isDraggable());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, markerOptions.isVisible());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, markerOptions.isFlat());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, markerOptions.getRotation());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, markerOptions.getInfoWindowAnchorU());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, markerOptions.getInfoWindowAnchorV());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, markerOptions.getAlpha());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeQ, reason: merged with bridge method [inline-methods] */
    public MarkerOptions createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        LatLng latLng = null;
        String strZzo = null;
        String strZzo2 = null;
        IBinder iBinderZzp = null;
        float fZzl = 0.0f;
        float fZzl2 = 0.0f;
        boolean zZzc = false;
        boolean zZzc2 = false;
        boolean zZzc3 = false;
        float fZzl3 = 0.0f;
        float fZzl4 = 0.5f;
        float fZzl5 = 0.0f;
        float fZzl6 = 1.0f;
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
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 6:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 7:
                    fZzl2 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 8:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 9:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 10:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 11:
                    fZzl3 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 12:
                    fZzl4 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 13:
                    fZzl5 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 14:
                    fZzl6 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new MarkerOptions(iZzg, latLng, strZzo, strZzo2, iBinderZzp, fZzl, fZzl2, zZzc, zZzc2, zZzc3, fZzl3, fZzl4, fZzl5, fZzl6);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhq, reason: merged with bridge method [inline-methods] */
    public MarkerOptions[] newArray(int i) {
        return new MarkerOptions[i];
    }
}
