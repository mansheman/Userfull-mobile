package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzs implements Parcelable.Creator<PlacesParams> {
    static void zza(PlacesParams placesParams, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, placesParams.zzaAZ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placesParams.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, placesParams.zzaBa, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, placesParams.zzaBb, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, placesParams.zzazX, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, placesParams.zzaBc, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, placesParams.zzaBd);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeB, reason: merged with bridge method [inline-methods] */
    public PlacesParams createFromParcel(Parcel parcel) {
        int iZzg = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PlacesParams(iZzg2, strZzo5, strZzo4, strZzo3, strZzo2, strZzo, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhb, reason: merged with bridge method [inline-methods] */
    public PlacesParams[] newArray(int i) {
        return new PlacesParams[i];
    }
}
