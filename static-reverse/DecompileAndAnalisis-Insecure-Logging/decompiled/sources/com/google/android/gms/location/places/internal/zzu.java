package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.location.places.internal.AutocompletePredictionEntity;

/* loaded from: classes.dex */
public class zzu implements Parcelable.Creator<AutocompletePredictionEntity.SubstringEntity> {
    static void zza(AutocompletePredictionEntity.SubstringEntity substringEntity, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, substringEntity.mOffset);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, substringEntity.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, substringEntity.mLength);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeC, reason: merged with bridge method [inline-methods] */
    public AutocompletePredictionEntity.SubstringEntity createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg2 = 0;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
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
        return new AutocompletePredictionEntity.SubstringEntity(iZzg3, iZzg2, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhc, reason: merged with bridge method [inline-methods] */
    public AutocompletePredictionEntity.SubstringEntity[] newArray(int i) {
        return new AutocompletePredictionEntity.SubstringEntity[i];
    }
}
