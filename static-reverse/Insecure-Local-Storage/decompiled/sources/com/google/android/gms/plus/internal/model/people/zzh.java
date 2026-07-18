package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.plus.internal.model.people.PersonEntity;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<PersonEntity.OrganizationsEntity> {
    static void zza(PersonEntity.OrganizationsEntity organizationsEntity, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        Set<Integer> set = organizationsEntity.zzaHQ;
        if (set.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, organizationsEntity.zzCY);
        }
        if (set.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, organizationsEntity.zzaJs, true);
        }
        if (set.contains(3)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, organizationsEntity.zzakM, true);
        }
        if (set.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, organizationsEntity.zzaIn, true);
        }
        if (set.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, organizationsEntity.zzaJt, true);
        }
        if (set.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, organizationsEntity.mName, true);
        }
        if (set.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, organizationsEntity.zzaJu);
        }
        if (set.contains(8)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, organizationsEntity.zzaID, true);
        }
        if (set.contains(9)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, organizationsEntity.zzadv, true);
        }
        if (set.contains(10)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 10, organizationsEntity.zzSq);
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfT, reason: merged with bridge method [inline-methods] */
    public PersonEntity.OrganizationsEntity createFromParcel(Parcel parcel) {
        int iZzg = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        HashSet hashSet = new HashSet();
        String strZzo2 = null;
        boolean zZzc = false;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        String strZzo6 = null;
        String strZzo7 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(1);
                    break;
                case 2:
                    strZzo7 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(2);
                    break;
                case 3:
                    strZzo6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(3);
                    break;
                case 4:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(4);
                    break;
                case 5:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(5);
                    break;
                case 6:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(6);
                    break;
                case 7:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    hashSet.add(7);
                    break;
                case 8:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(8);
                    break;
                case 9:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(9);
                    break;
                case 10:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(10);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PersonEntity.OrganizationsEntity(hashSet, iZzg2, strZzo7, strZzo6, strZzo5, strZzo4, strZzo3, zZzc, strZzo2, strZzo, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziJ, reason: merged with bridge method [inline-methods] */
    public PersonEntity.OrganizationsEntity[] newArray(int i) {
        return new PersonEntity.OrganizationsEntity[i];
    }
}
