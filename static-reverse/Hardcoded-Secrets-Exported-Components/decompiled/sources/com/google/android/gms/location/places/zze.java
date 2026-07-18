package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<NearbyAlertRequest> {
    static void zza(NearbyAlertRequest nearbyAlertRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, nearbyAlertRequest.zzuC());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, nearbyAlertRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, nearbyAlertRequest.zzuF());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) nearbyAlertRequest.zzuG(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) nearbyAlertRequest.zzuH(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeq, reason: merged with bridge method [inline-methods] */
    public NearbyAlertRequest createFromParcel(Parcel parcel) {
        NearbyAlertFilter nearbyAlertFilter = null;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg2 = -1;
        PlaceFilter placeFilter = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    placeFilter = (PlaceFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PlaceFilter.CREATOR);
                    break;
                case 4:
                    nearbyAlertFilter = (NearbyAlertFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, NearbyAlertFilter.CREATOR);
                    break;
                case 1000:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new NearbyAlertRequest(iZzg3, iZzg, iZzg2, placeFilter, nearbyAlertFilter);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgM, reason: merged with bridge method [inline-methods] */
    public NearbyAlertRequest[] newArray(int i) {
        return new NearbyAlertRequest[i];
    }
}
