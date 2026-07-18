package com.google.android.gms.ads.internal.client;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<AdRequestParcel> {
    static void zza(AdRequestParcel adRequestParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adRequestParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, adRequestParcel.zzrX);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, adRequestParcel.extras, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, adRequestParcel.zzrY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 5, adRequestParcel.zzrZ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, adRequestParcel.zzsa);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, adRequestParcel.zzsb);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, adRequestParcel.zzsc);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adRequestParcel.zzsd, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, (Parcelable) adRequestParcel.zzse, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, (Parcelable) adRequestParcel.zzsf, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, adRequestParcel.zzsg, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, adRequestParcel.zzsh, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, adRequestParcel.zzsi, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 15, adRequestParcel.zzsj, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, adRequestParcel.zzsk, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public AdRequestParcel createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        long jZzi = 0;
        Bundle bundleZzq = null;
        int iZzg2 = 0;
        ArrayList<String> arrayListZzC = null;
        boolean zZzc = false;
        int iZzg3 = 0;
        boolean zZzc2 = false;
        String strZzo = null;
        SearchAdRequestParcel searchAdRequestParcel = null;
        Location location = null;
        String strZzo2 = null;
        Bundle bundleZzq2 = null;
        Bundle bundleZzq3 = null;
        ArrayList<String> arrayListZzC2 = null;
        String strZzo3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 6:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 7:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 9:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 10:
                    searchAdRequestParcel = (SearchAdRequestParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, SearchAdRequestParcel.CREATOR);
                    break;
                case 11:
                    location = (Location) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Location.CREATOR);
                    break;
                case 12:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 13:
                    bundleZzq2 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 14:
                    bundleZzq3 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 15:
                    arrayListZzC2 = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 16:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AdRequestParcel(iZzg, jZzi, bundleZzq, iZzg2, arrayListZzC, zZzc, iZzg3, zZzc2, strZzo, searchAdRequestParcel, location, strZzo2, bundleZzq2, bundleZzq3, arrayListZzC2, strZzo3);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzk, reason: merged with bridge method [inline-methods] */
    public AdRequestParcel[] newArray(int i) {
        return new AdRequestParcel[i];
    }
}
