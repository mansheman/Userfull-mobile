package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class TurnBasedMatchEntityCreator implements Parcelable.Creator<TurnBasedMatchEntity> {
    static void zza(TurnBasedMatchEntity turnBasedMatchEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, (Parcelable) turnBasedMatchEntity.getGame(), i, false);
        zzb.zza(parcel, 2, turnBasedMatchEntity.getMatchId(), false);
        zzb.zza(parcel, 3, turnBasedMatchEntity.getCreatorId(), false);
        zzb.zza(parcel, 4, turnBasedMatchEntity.getCreationTimestamp());
        zzb.zza(parcel, 5, turnBasedMatchEntity.getLastUpdaterId(), false);
        zzb.zza(parcel, 6, turnBasedMatchEntity.getLastUpdatedTimestamp());
        zzb.zza(parcel, 7, turnBasedMatchEntity.getPendingParticipantId(), false);
        zzb.zzc(parcel, 8, turnBasedMatchEntity.getStatus());
        zzb.zzc(parcel, 10, turnBasedMatchEntity.getVariant());
        zzb.zzc(parcel, 11, turnBasedMatchEntity.getVersion());
        zzb.zza(parcel, 12, turnBasedMatchEntity.getData(), false);
        zzb.zzc(parcel, 13, turnBasedMatchEntity.getParticipants(), false);
        zzb.zza(parcel, 14, turnBasedMatchEntity.getRematchId(), false);
        zzb.zza(parcel, 15, turnBasedMatchEntity.getPreviousMatchData(), false);
        zzb.zza(parcel, 17, turnBasedMatchEntity.getAutoMatchCriteria(), false);
        zzb.zzc(parcel, 16, turnBasedMatchEntity.getMatchNumber());
        zzb.zzc(parcel, 1000, turnBasedMatchEntity.getVersionCode());
        zzb.zza(parcel, 19, turnBasedMatchEntity.isLocallyModified());
        zzb.zzc(parcel, 18, turnBasedMatchEntity.getTurnStatus());
        zzb.zza(parcel, 21, turnBasedMatchEntity.getDescriptionParticipantId(), false);
        zzb.zza(parcel, 20, turnBasedMatchEntity.getDescription(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdN, reason: merged with bridge method [inline-methods] */
    public TurnBasedMatchEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        GameEntity gameEntity = null;
        String strZzo = null;
        String strZzo2 = null;
        long jZzi = 0;
        String strZzo3 = null;
        long jZzi2 = 0;
        String strZzo4 = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        int iZzg4 = 0;
        byte[] bArrZzr = null;
        ArrayList arrayListZzc = null;
        String strZzo5 = null;
        byte[] bArrZzr2 = null;
        int iZzg5 = 0;
        Bundle bundleZzq = null;
        int iZzg6 = 0;
        boolean zZzc = false;
        String strZzo6 = null;
        String strZzo7 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    gameEntity = (GameEntity) zza.zza(parcel, iZzaa, GameEntity.CREATOR);
                    break;
                case 2:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 5:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    jZzi2 = zza.zzi(parcel, iZzaa);
                    break;
                case 7:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 10:
                    iZzg3 = zza.zzg(parcel, iZzaa);
                    break;
                case 11:
                    iZzg4 = zza.zzg(parcel, iZzaa);
                    break;
                case 12:
                    bArrZzr = zza.zzr(parcel, iZzaa);
                    break;
                case 13:
                    arrayListZzc = zza.zzc(parcel, iZzaa, ParticipantEntity.CREATOR);
                    break;
                case 14:
                    strZzo5 = zza.zzo(parcel, iZzaa);
                    break;
                case 15:
                    bArrZzr2 = zza.zzr(parcel, iZzaa);
                    break;
                case 16:
                    iZzg5 = zza.zzg(parcel, iZzaa);
                    break;
                case 17:
                    bundleZzq = zza.zzq(parcel, iZzaa);
                    break;
                case 18:
                    iZzg6 = zza.zzg(parcel, iZzaa);
                    break;
                case 19:
                    zZzc = zza.zzc(parcel, iZzaa);
                    break;
                case 20:
                    strZzo6 = zza.zzo(parcel, iZzaa);
                    break;
                case 21:
                    strZzo7 = zza.zzo(parcel, iZzaa);
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
        return new TurnBasedMatchEntity(iZzg, gameEntity, strZzo, strZzo2, jZzi, strZzo3, jZzi2, strZzo4, iZzg2, iZzg3, iZzg4, bArrZzr, arrayListZzc, strZzo5, bArrZzr2, iZzg5, bundleZzq, iZzg6, zZzc, strZzo6, strZzo7);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfW, reason: merged with bridge method [inline-methods] */
    public TurnBasedMatchEntity[] newArray(int i) {
        return new TurnBasedMatchEntity[i];
    }
}
