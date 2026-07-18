package com.google.android.gms.games;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class PlayerLevelInfoCreator implements Parcelable.Creator<PlayerLevelInfo> {
    static void zza(PlayerLevelInfo playerLevelInfo, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, playerLevelInfo.getCurrentXpTotal());
        zzb.zzc(parcel, 1000, playerLevelInfo.getVersionCode());
        zzb.zza(parcel, 2, playerLevelInfo.getLastLevelUpTimestamp());
        zzb.zza(parcel, 3, (Parcelable) playerLevelInfo.getCurrentLevel(), i, false);
        zzb.zza(parcel, 4, (Parcelable) playerLevelInfo.getNextLevel(), i, false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdu, reason: merged with bridge method [inline-methods] */
    public PlayerLevelInfo createFromParcel(Parcel parcel) {
        long jZzi = 0;
        PlayerLevel playerLevel = null;
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        PlayerLevel playerLevel2 = null;
        long jZzi2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    jZzi2 = zza.zzi(parcel, iZzaa);
                    break;
                case 2:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    playerLevel2 = (PlayerLevel) zza.zza(parcel, iZzaa, PlayerLevel.CREATOR);
                    break;
                case 4:
                    playerLevel = (PlayerLevel) zza.zza(parcel, iZzaa, PlayerLevel.CREATOR);
                    break;
                case 1000:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PlayerLevelInfo(iZzg, jZzi2, jZzi, playerLevel2, playerLevel);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfq, reason: merged with bridge method [inline-methods] */
    public PlayerLevelInfo[] newArray(int i) {
        return new PlayerLevelInfo[i];
    }
}
