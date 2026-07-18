package com.google.android.gms.games;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class PlayerLevelCreator implements Parcelable.Creator<PlayerLevel> {
    static void zza(PlayerLevel playerLevel, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, playerLevel.getLevelNumber());
        zzb.zzc(parcel, 1000, playerLevel.getVersionCode());
        zzb.zza(parcel, 2, playerLevel.getMinXp());
        zzb.zza(parcel, 3, playerLevel.getMaxXp());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdt, reason: merged with bridge method [inline-methods] */
    public PlayerLevel createFromParcel(Parcel parcel) {
        long jZzi = 0;
        int iZzg = 0;
        int iZzab = zza.zzab(parcel);
        long jZzi2 = 0;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    jZzi2 = zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    jZzi = zza.zzi(parcel, iZzaa);
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
        return new PlayerLevel(iZzg2, iZzg, jZzi2, jZzi);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfp, reason: merged with bridge method [inline-methods] */
    public PlayerLevel[] newArray(int i) {
        return new PlayerLevel[i];
    }
}
