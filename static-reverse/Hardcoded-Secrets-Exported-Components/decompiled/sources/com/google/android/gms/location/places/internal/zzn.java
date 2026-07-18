package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzn implements Parcelable.Creator<PlaceLocalization> {
    static void zza(PlaceLocalization placeLocalization, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, placeLocalization.name, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placeLocalization.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, placeLocalization.zzaAM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, placeLocalization.zzaAN, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, placeLocalization.zzaAO, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 5, placeLocalization.zzaAP, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeA, reason: merged with bridge method [inline-methods] */
    public PlaceLocalization createFromParcel(Parcel parcel) {
        ArrayList<String> arrayListZzC = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PlaceLocalization(iZzg, strZzo4, strZzo3, strZzo2, strZzo, arrayListZzC);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzha, reason: merged with bridge method [inline-methods] */
    public PlaceLocalization[] newArray(int i) {
        return new PlaceLocalization[i];
    }
}
