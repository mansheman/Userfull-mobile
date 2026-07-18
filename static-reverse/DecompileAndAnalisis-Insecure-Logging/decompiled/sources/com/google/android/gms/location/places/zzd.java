package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<NearbyAlertFilter> {
    static void zza(NearbyAlertFilter nearbyAlertFilter, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 1, nearbyAlertFilter.zzazu, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, nearbyAlertFilter.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, nearbyAlertFilter.zzazs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, nearbyAlertFilter.zzazv, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzep, reason: merged with bridge method [inline-methods] */
    public NearbyAlertFilter createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        ArrayList<Integer> arrayListZzB = null;
        ArrayList<String> arrayListZzC = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 2:
                    arrayListZzB = com.google.android.gms.common.internal.safeparcel.zza.zzB(parcel, iZzaa);
                    break;
                case 3:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, UserDataType.CREATOR);
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
        return new NearbyAlertFilter(iZzg, arrayListZzC, arrayListZzB, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgL, reason: merged with bridge method [inline-methods] */
    public NearbyAlertFilter[] newArray(int i) {
        return new NearbyAlertFilter[i];
    }
}
