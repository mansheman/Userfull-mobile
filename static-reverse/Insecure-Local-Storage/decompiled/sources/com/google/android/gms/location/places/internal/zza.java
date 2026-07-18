package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.location.places.internal.AutocompletePredictionEntity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<AutocompletePredictionEntity> {
    static void zza(AutocompletePredictionEntity autocompletePredictionEntity, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, autocompletePredictionEntity.zzakM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, autocompletePredictionEntity.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, autocompletePredictionEntity.zzazK, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, autocompletePredictionEntity.zzazo, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, autocompletePredictionEntity.zzaAe, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, autocompletePredictionEntity.zzaAf);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzex, reason: merged with bridge method [inline-methods] */
    public AutocompletePredictionEntity createFromParcel(Parcel parcel) {
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList<Integer> arrayListZzB = null;
        String strZzo = null;
        String strZzo2 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    arrayListZzB = com.google.android.gms.common.internal.safeparcel.zza.zzB(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, AutocompletePredictionEntity.SubstringEntity.CREATOR);
                    break;
                case 5:
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
        return new AutocompletePredictionEntity(iZzg2, strZzo2, strZzo, arrayListZzB, arrayListZzc, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgW, reason: merged with bridge method [inline-methods] */
    public AutocompletePredictionEntity[] newArray(int i) {
        return new AutocompletePredictionEntity[i];
    }
}
