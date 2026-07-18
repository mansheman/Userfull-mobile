package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.PlayerEntity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GameRequestEntityCreator implements Parcelable.Creator<GameRequestEntity> {
    static void zza(GameRequestEntity gameRequestEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, (Parcelable) gameRequestEntity.getGame(), i, false);
        zzb.zzc(parcel, 1000, gameRequestEntity.getVersionCode());
        zzb.zza(parcel, 2, (Parcelable) gameRequestEntity.getSender(), i, false);
        zzb.zza(parcel, 3, gameRequestEntity.getData(), false);
        zzb.zza(parcel, 4, gameRequestEntity.getRequestId(), false);
        zzb.zzc(parcel, 5, gameRequestEntity.getRecipients(), false);
        zzb.zzc(parcel, 7, gameRequestEntity.getType());
        zzb.zza(parcel, 9, gameRequestEntity.getCreationTimestamp());
        zzb.zza(parcel, 10, gameRequestEntity.getExpirationTimestamp());
        zzb.zza(parcel, 11, gameRequestEntity.zztP(), false);
        zzb.zzc(parcel, 12, gameRequestEntity.getStatus());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdQ, reason: merged with bridge method [inline-methods] */
    public GameRequestEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        byte[] bArrZzr = null;
        String strZzo = null;
        ArrayList arrayListZzc = null;
        int iZzg2 = 0;
        long jZzi = 0;
        long jZzi2 = 0;
        Bundle bundleZzq = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    gameEntity = (GameEntity) zza.zza(parcel, iZzaa, GameEntity.CREATOR);
                    break;
                case 2:
                    playerEntity = (PlayerEntity) zza.zza(parcel, iZzaa, PlayerEntity.CREATOR);
                    break;
                case 3:
                    bArrZzr = zza.zzr(parcel, iZzaa);
                    break;
                case 4:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    arrayListZzc = zza.zzc(parcel, iZzaa, PlayerEntity.CREATOR);
                    break;
                case 7:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 9:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 10:
                    jZzi2 = zza.zzi(parcel, iZzaa);
                    break;
                case 11:
                    bundleZzq = zza.zzq(parcel, iZzaa);
                    break;
                case 12:
                    iZzg3 = zza.zzg(parcel, iZzaa);
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
        return new GameRequestEntity(iZzg, gameEntity, playerEntity, bArrZzr, strZzo, arrayListZzc, iZzg2, jZzi, jZzi2, bundleZzq, iZzg3);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfZ, reason: merged with bridge method [inline-methods] */
    public GameRequestEntity[] newArray(int i) {
        return new GameRequestEntity[i];
    }
}
