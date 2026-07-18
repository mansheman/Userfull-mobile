package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<StreetViewPanoramaOptions> {
    static void zza(StreetViewPanoramaOptions streetViewPanoramaOptions, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, streetViewPanoramaOptions.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) streetViewPanoramaOptions.getStreetViewPanoramaCamera(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, streetViewPanoramaOptions.getPanoramaId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) streetViewPanoramaOptions.getPosition(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, streetViewPanoramaOptions.getRadius(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, streetViewPanoramaOptions.zzvy());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, streetViewPanoramaOptions.zzvo());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, streetViewPanoramaOptions.zzvz());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, streetViewPanoramaOptions.zzvA());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, streetViewPanoramaOptions.zzvk());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeJ, reason: merged with bridge method [inline-methods] */
    public StreetViewPanoramaOptions createFromParcel(Parcel parcel) {
        Integer numZzh = null;
        byte bZze = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        byte bZze2 = 0;
        byte bZze3 = 0;
        byte bZze4 = 0;
        byte bZze5 = 0;
        LatLng latLng = null;
        String strZzo = null;
        StreetViewPanoramaCamera streetViewPanoramaCamera = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    streetViewPanoramaCamera = (StreetViewPanoramaCamera) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, StreetViewPanoramaCamera.CREATOR);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    latLng = (LatLng) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LatLng.CREATOR);
                    break;
                case 5:
                    numZzh = com.google.android.gms.common.internal.safeparcel.zza.zzh(parcel, iZzaa);
                    break;
                case 6:
                    bZze5 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 7:
                    bZze4 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 8:
                    bZze3 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 9:
                    bZze2 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 10:
                    bZze = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new StreetViewPanoramaOptions(iZzg, streetViewPanoramaCamera, strZzo, latLng, numZzh, bZze5, bZze4, bZze3, bZze2, bZze);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhj, reason: merged with bridge method [inline-methods] */
    public StreetViewPanoramaOptions[] newArray(int i) {
        return new StreetViewPanoramaOptions[i];
    }
}
