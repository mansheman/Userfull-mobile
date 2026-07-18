package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.internal.player.MostRecentGameInfoEntity;

/* loaded from: classes.dex */
public class PlayerEntityCreator implements Parcelable.Creator<PlayerEntity> {
    static void zza(PlayerEntity playerEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, playerEntity.getPlayerId(), false);
        zzb.zza(parcel, 2, playerEntity.getDisplayName(), false);
        zzb.zza(parcel, 3, (Parcelable) playerEntity.getIconImageUri(), i, false);
        zzb.zza(parcel, 4, (Parcelable) playerEntity.getHiResImageUri(), i, false);
        zzb.zza(parcel, 5, playerEntity.getRetrievedTimestamp());
        zzb.zzc(parcel, 6, playerEntity.zzrK());
        zzb.zza(parcel, 7, playerEntity.getLastPlayedWithTimestamp());
        zzb.zza(parcel, 8, playerEntity.getIconImageUrl(), false);
        zzb.zza(parcel, 9, playerEntity.getHiResImageUrl(), false);
        zzb.zza(parcel, 14, playerEntity.getTitle(), false);
        zzb.zza(parcel, 15, (Parcelable) playerEntity.zzrL(), i, false);
        zzb.zza(parcel, 16, (Parcelable) playerEntity.getLevelInfo(), i, false);
        zzb.zzc(parcel, 1000, playerEntity.getVersionCode());
        zzb.zza(parcel, 19, playerEntity.zzrJ());
        zzb.zza(parcel, 18, playerEntity.isProfileVisible());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzds */
    public PlayerEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        Uri uri = null;
        Uri uri2 = null;
        long jZzi = 0;
        int iZzg2 = 0;
        long jZzi2 = 0;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        MostRecentGameInfoEntity mostRecentGameInfoEntity = null;
        PlayerLevelInfo playerLevelInfo = null;
        boolean zZzc = false;
        boolean zZzc2 = false;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    uri = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 4:
                    uri2 = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 5:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 6:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 7:
                    jZzi2 = zza.zzi(parcel, iZzaa);
                    break;
                case 8:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 14:
                    strZzo5 = zza.zzo(parcel, iZzaa);
                    break;
                case 15:
                    mostRecentGameInfoEntity = (MostRecentGameInfoEntity) zza.zza(parcel, iZzaa, MostRecentGameInfoEntity.CREATOR);
                    break;
                case 16:
                    playerLevelInfo = (PlayerLevelInfo) zza.zza(parcel, iZzaa, PlayerLevelInfo.CREATOR);
                    break;
                case 18:
                    zZzc = zza.zzc(parcel, iZzaa);
                    break;
                case 19:
                    zZzc2 = zza.zzc(parcel, iZzaa);
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
        return new PlayerEntity(iZzg, strZzo, strZzo2, uri, uri2, jZzi, iZzg2, jZzi2, strZzo3, strZzo4, strZzo5, mostRecentGameInfoEntity, playerLevelInfo, zZzc, zZzc2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfo, reason: merged with bridge method [inline-methods] */
    public PlayerEntity[] newArray(int i) {
        return new PlayerEntity[i];
    }
}
