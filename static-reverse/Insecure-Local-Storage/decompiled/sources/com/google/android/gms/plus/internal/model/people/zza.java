package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.plus.internal.model.people.PersonEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<PersonEntity> {
    static void zza(PersonEntity personEntity, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        Set<Integer> set = personEntity.zzaHQ;
        if (set.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, personEntity.zzCY);
        }
        if (set.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, personEntity.zzaIO, true);
        }
        if (set.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) personEntity.zzaIP, i, true);
        }
        if (set.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, personEntity.zzaIQ, true);
        }
        if (set.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, personEntity.zzaIR, true);
        }
        if (set.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, personEntity.zzaIS);
        }
        if (set.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable) personEntity.zzaIT, i, true);
        }
        if (set.contains(8)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, personEntity.zzaIU, true);
        }
        if (set.contains(9)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, personEntity.zzadI, true);
        }
        if (set.contains(12)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 12, personEntity.zzsC);
        }
        if (set.contains(14)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, personEntity.zzKI, true);
        }
        if (set.contains(15)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, (Parcelable) personEntity.zzaIV, i, true);
        }
        if (set.contains(16)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, personEntity.zzaIW);
        }
        if (set.contains(19)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 19, (Parcelable) personEntity.zzaIX, i, true);
        }
        if (set.contains(18)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 18, personEntity.zzRA, true);
        }
        if (set.contains(21)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 21, personEntity.zzaIZ);
        }
        if (set.contains(20)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 20, personEntity.zzaIY, true);
        }
        if (set.contains(23)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 23, personEntity.zzaJb, true);
        }
        if (set.contains(22)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 22, personEntity.zzaJa, true);
        }
        if (set.contains(25)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 25, personEntity.zzaJd);
        }
        if (set.contains(24)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 24, personEntity.zzaJc);
        }
        if (set.contains(27)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 27, personEntity.zzF, true);
        }
        if (set.contains(26)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 26, personEntity.zzaJe, true);
        }
        if (set.contains(29)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 29, personEntity.zzaJg);
        }
        if (set.contains(28)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 28, personEntity.zzaJf, true);
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfM, reason: merged with bridge method [inline-methods] */
    public PersonEntity createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        HashSet hashSet = new HashSet();
        int iZzg = 0;
        String strZzo = null;
        PersonEntity.AgeRangeEntity ageRangeEntity = null;
        String strZzo2 = null;
        String strZzo3 = null;
        int iZzg2 = 0;
        PersonEntity.CoverEntity coverEntity = null;
        String strZzo4 = null;
        String strZzo5 = null;
        int iZzg3 = 0;
        String strZzo6 = null;
        PersonEntity.ImageEntity imageEntity = null;
        boolean zZzc = false;
        String strZzo7 = null;
        PersonEntity.NameEntity nameEntity = null;
        String strZzo8 = null;
        int iZzg4 = 0;
        ArrayList arrayListZzc = null;
        ArrayList arrayListZzc2 = null;
        int iZzg5 = 0;
        int iZzg6 = 0;
        String strZzo9 = null;
        String strZzo10 = null;
        ArrayList arrayListZzc3 = null;
        boolean zZzc2 = false;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(1);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(2);
                    break;
                case 3:
                    PersonEntity.AgeRangeEntity ageRangeEntity2 = (PersonEntity.AgeRangeEntity) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PersonEntity.AgeRangeEntity.CREATOR);
                    hashSet.add(3);
                    ageRangeEntity = ageRangeEntity2;
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(4);
                    break;
                case 5:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(5);
                    break;
                case 6:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(6);
                    break;
                case 7:
                    PersonEntity.CoverEntity coverEntity2 = (PersonEntity.CoverEntity) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PersonEntity.CoverEntity.CREATOR);
                    hashSet.add(7);
                    coverEntity = coverEntity2;
                    break;
                case 8:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(8);
                    break;
                case 9:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(9);
                    break;
                case 10:
                case 11:
                case 13:
                case 17:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
                case 12:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(12);
                    break;
                case 14:
                    strZzo6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(14);
                    break;
                case 15:
                    PersonEntity.ImageEntity imageEntity2 = (PersonEntity.ImageEntity) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PersonEntity.ImageEntity.CREATOR);
                    hashSet.add(15);
                    imageEntity = imageEntity2;
                    break;
                case 16:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    hashSet.add(16);
                    break;
                case 18:
                    strZzo7 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(18);
                    break;
                case 19:
                    PersonEntity.NameEntity nameEntity2 = (PersonEntity.NameEntity) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PersonEntity.NameEntity.CREATOR);
                    hashSet.add(19);
                    nameEntity = nameEntity2;
                    break;
                case 20:
                    strZzo8 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(20);
                    break;
                case 21:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(21);
                    break;
                case 22:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, PersonEntity.OrganizationsEntity.CREATOR);
                    hashSet.add(22);
                    break;
                case 23:
                    arrayListZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, PersonEntity.PlacesLivedEntity.CREATOR);
                    hashSet.add(23);
                    break;
                case 24:
                    iZzg5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(24);
                    break;
                case 25:
                    iZzg6 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(25);
                    break;
                case 26:
                    strZzo9 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(26);
                    break;
                case 27:
                    strZzo10 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(27);
                    break;
                case 28:
                    arrayListZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, PersonEntity.UrlsEntity.CREATOR);
                    hashSet.add(28);
                    break;
                case 29:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    hashSet.add(29);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PersonEntity(hashSet, iZzg, strZzo, ageRangeEntity, strZzo2, strZzo3, iZzg2, coverEntity, strZzo4, strZzo5, iZzg3, strZzo6, imageEntity, zZzc, strZzo7, nameEntity, strZzo8, iZzg4, arrayListZzc, arrayListZzc2, iZzg5, iZzg6, strZzo9, strZzo10, arrayListZzc3, zZzc2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziC, reason: merged with bridge method [inline-methods] */
    public PersonEntity[] newArray(int i) {
        return new PersonEntity[i];
    }
}
