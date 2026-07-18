package com.google.android.gms.games.quest;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class MilestoneEntityCreator implements Parcelable.Creator<MilestoneEntity> {
    static void zza(MilestoneEntity milestoneEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, milestoneEntity.getMilestoneId(), false);
        zzb.zzc(parcel, 1000, milestoneEntity.getVersionCode());
        zzb.zza(parcel, 2, milestoneEntity.getCurrentProgress());
        zzb.zza(parcel, 3, milestoneEntity.getTargetProgress());
        zzb.zza(parcel, 4, milestoneEntity.getCompletionRewardData(), false);
        zzb.zzc(parcel, 5, milestoneEntity.getState());
        zzb.zza(parcel, 6, milestoneEntity.getEventId(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdO, reason: merged with bridge method [inline-methods] */
    public MilestoneEntity createFromParcel(Parcel parcel) {
        long jZzi = 0;
        int iZzg = 0;
        String strZzo = null;
        int iZzab = zza.zzab(parcel);
        byte[] bArrZzr = null;
        long jZzi2 = 0;
        String strZzo2 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    jZzi2 = zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    bArrZzr = zza.zzr(parcel, iZzaa);
                    break;
                case 5:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new MilestoneEntity(iZzg2, strZzo2, jZzi2, jZzi, bArrZzr, iZzg, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfX, reason: merged with bridge method [inline-methods] */
    public MilestoneEntity[] newArray(int i) {
        return new MilestoneEntity[i];
    }
}
