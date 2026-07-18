package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.location.places.personalized.internal.TestDataImpl;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<PlaceUserData> {
    static void zza(PlaceUserData placeUserData, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, placeUserData.zzvb(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placeUserData.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, placeUserData.getPlaceId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, placeUserData.zzve(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, placeUserData.zzvc(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, placeUserData.zzvd(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeG, reason: merged with bridge method [inline-methods] */
    public PlaceUserData createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayListZzc2 = null;
        ArrayList arrayListZzc3 = null;
        String strZzo = null;
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    arrayListZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, TestDataImpl.CREATOR);
                    break;
                case 6:
                    arrayListZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, PlaceAlias.CREATOR);
                    break;
                case 7:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, HereContent.CREATOR);
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
        return new PlaceUserData(iZzg, strZzo2, strZzo, arrayListZzc3, arrayListZzc2, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhg, reason: merged with bridge method [inline-methods] */
    public PlaceUserData[] newArray(int i) {
        return new PlaceUserData[i];
    }
}
