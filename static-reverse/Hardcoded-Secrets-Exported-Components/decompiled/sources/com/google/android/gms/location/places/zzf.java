package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<PlaceFilter> {
    static void zza(PlaceFilter placeFilter, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, placeFilter.zzazs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placeFilter.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, placeFilter.zzazC);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, placeFilter.zzazv, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 6, placeFilter.zzazu, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzer, reason: merged with bridge method [inline-methods] */
    public PlaceFilter createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList<String> arrayListZzC = null;
        ArrayList<Integer> arrayListZzB = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzB = com.google.android.gms.common.internal.safeparcel.zza.zzB(parcel, iZzaa);
                    break;
                case 3:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, UserDataType.CREATOR);
                    break;
                case 6:
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
        return new PlaceFilter(iZzg, arrayListZzB, zZzc, arrayListZzC, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgN, reason: merged with bridge method [inline-methods] */
    public PlaceFilter[] newArray(int i) {
        return new PlaceFilter[i];
    }
}
