package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.plus.internal.model.people.PersonEntity;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<PersonEntity.CoverEntity> {
    static void zza(PersonEntity.CoverEntity coverEntity, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        Set<Integer> set = coverEntity.zzaHQ;
        if (set.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, coverEntity.zzCY);
        }
        if (set.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) coverEntity.zzaJj, i, true);
        }
        if (set.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) coverEntity.zzaJk, i, true);
        }
        if (set.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, coverEntity.zzaJl);
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfO, reason: merged with bridge method [inline-methods] */
    public PersonEntity.CoverEntity createFromParcel(Parcel parcel) {
        PersonEntity.CoverEntity.CoverPhotoEntity coverPhotoEntity = null;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        HashSet hashSet = new HashSet();
        PersonEntity.CoverEntity.CoverInfoEntity coverInfoEntity = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(1);
                    break;
                case 2:
                    PersonEntity.CoverEntity.CoverInfoEntity coverInfoEntity2 = (PersonEntity.CoverEntity.CoverInfoEntity) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PersonEntity.CoverEntity.CoverInfoEntity.CREATOR);
                    hashSet.add(2);
                    coverInfoEntity = coverInfoEntity2;
                    break;
                case 3:
                    PersonEntity.CoverEntity.CoverPhotoEntity coverPhotoEntity2 = (PersonEntity.CoverEntity.CoverPhotoEntity) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PersonEntity.CoverEntity.CoverPhotoEntity.CREATOR);
                    hashSet.add(3);
                    coverPhotoEntity = coverPhotoEntity2;
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(4);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PersonEntity.CoverEntity(hashSet, iZzg2, coverInfoEntity, coverPhotoEntity, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziE, reason: merged with bridge method [inline-methods] */
    public PersonEntity.CoverEntity[] newArray(int i) {
        return new PersonEntity.CoverEntity[i];
    }
}
