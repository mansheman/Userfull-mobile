package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzl implements Parcelable.Creator<PlaceLikelihoodEntity> {
    static void zza(PlaceLikelihoodEntity placeLikelihoodEntity, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) placeLikelihoodEntity.zzaAK, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placeLikelihoodEntity.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, placeLikelihoodEntity.zzaAL);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzez, reason: merged with bridge method [inline-methods] */
    public PlaceLikelihoodEntity createFromParcel(Parcel parcel) {
        float fZzl;
        PlaceImpl placeImpl;
        int iZzg;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        PlaceImpl placeImpl2 = null;
        float f = 0.0f;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    PlaceImpl placeImpl3 = (PlaceImpl) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PlaceImpl.CREATOR);
                    iZzg = i;
                    fZzl = f;
                    placeImpl = placeImpl3;
                    break;
                case 2:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    placeImpl = placeImpl2;
                    iZzg = i;
                    break;
                case 1000:
                    float f2 = f;
                    placeImpl = placeImpl2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    fZzl = f2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    fZzl = f;
                    placeImpl = placeImpl2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            placeImpl2 = placeImpl;
            f = fZzl;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PlaceLikelihoodEntity(i, placeImpl2, f);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgZ, reason: merged with bridge method [inline-methods] */
    public PlaceLikelihoodEntity[] newArray(int i) {
        return new PlaceLikelihoodEntity[i];
    }
}
