package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzl implements Parcelable.Creator<PlaceRequest> {
    static void zza(PlaceRequest placeRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placeRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) placeRequest.zzuG(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, placeRequest.getInterval());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, placeRequest.getPriority());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, placeRequest.getExpirationTime());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzev, reason: merged with bridge method [inline-methods] */
    public PlaceRequest createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        PlaceFilter placeFilter = null;
        long jZzi = PlaceRequest.zzazM;
        int iZzg2 = 102;
        long jZzi2 = Long.MAX_VALUE;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 2:
                    placeFilter = (PlaceFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PlaceFilter.CREATOR);
                    break;
                case 3:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
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
        return new PlaceRequest(iZzg, placeFilter, jZzi, iZzg2, jZzi2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgT, reason: merged with bridge method [inline-methods] */
    public PlaceRequest[] newArray(int i) {
        return new PlaceRequest[i];
    }
}
