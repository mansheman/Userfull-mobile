package com.google.android.gms.plus.internal.model.moments;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<MomentEntity> {
    static void zza(MomentEntity momentEntity, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        Set<Integer> set = momentEntity.zzaHQ;
        if (set.contains(1)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, momentEntity.zzCY);
        }
        if (set.contains(2)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, momentEntity.zzKI, true);
        }
        if (set.contains(4)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) momentEntity.zzaIL, i, true);
        }
        if (set.contains(5)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, momentEntity.zzaID, true);
        }
        if (set.contains(6)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) momentEntity.zzaIM, i, true);
        }
        if (set.contains(7)) {
            com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, momentEntity.zzEl, true);
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfL, reason: merged with bridge method [inline-methods] */
    public MomentEntity createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        HashSet hashSet = new HashSet();
        int iZzg = 0;
        ItemScopeEntity itemScopeEntity = null;
        String strZzo2 = null;
        ItemScopeEntity itemScopeEntity2 = null;
        String strZzo3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    hashSet.add(1);
                    break;
                case 2:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(2);
                    break;
                case 3:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
                case 4:
                    ItemScopeEntity itemScopeEntity3 = (ItemScopeEntity) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ItemScopeEntity.CREATOR);
                    hashSet.add(4);
                    itemScopeEntity2 = itemScopeEntity3;
                    break;
                case 5:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(5);
                    break;
                case 6:
                    ItemScopeEntity itemScopeEntity4 = (ItemScopeEntity) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ItemScopeEntity.CREATOR);
                    hashSet.add(6);
                    itemScopeEntity = itemScopeEntity4;
                    break;
                case 7:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    hashSet.add(7);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new MomentEntity(hashSet, iZzg, strZzo3, itemScopeEntity2, strZzo2, itemScopeEntity, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziB, reason: merged with bridge method [inline-methods] */
    public MomentEntity[] newArray(int i) {
        return new MomentEntity[i];
    }
}
