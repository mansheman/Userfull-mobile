package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.PlayerEntity;

/* loaded from: classes.dex */
public class ParticipantEntityCreator implements Parcelable.Creator<ParticipantEntity> {
    static void zza(ParticipantEntity participantEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, participantEntity.getParticipantId(), false);
        zzb.zzc(parcel, 1000, participantEntity.getVersionCode());
        zzb.zza(parcel, 2, participantEntity.getDisplayName(), false);
        zzb.zza(parcel, 3, (Parcelable) participantEntity.getIconImageUri(), i, false);
        zzb.zza(parcel, 4, (Parcelable) participantEntity.getHiResImageUri(), i, false);
        zzb.zzc(parcel, 5, participantEntity.getStatus());
        zzb.zza(parcel, 6, participantEntity.zzsr(), false);
        zzb.zza(parcel, 7, participantEntity.isConnectedToRoom());
        zzb.zza(parcel, 8, (Parcelable) participantEntity.getPlayer(), i, false);
        zzb.zzc(parcel, 9, participantEntity.getCapabilities());
        zzb.zza(parcel, 10, (Parcelable) participantEntity.getResult(), i, false);
        zzb.zza(parcel, 11, participantEntity.getIconImageUrl(), false);
        zzb.zza(parcel, 12, participantEntity.getHiResImageUrl(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdJ */
    public ParticipantEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        Uri uri = null;
        Uri uri2 = null;
        int iZzg2 = 0;
        String strZzo3 = null;
        boolean zZzc = false;
        PlayerEntity playerEntity = null;
        int iZzg3 = 0;
        ParticipantResult participantResult = null;
        String strZzo4 = null;
        String strZzo5 = null;
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
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    zZzc = zza.zzc(parcel, iZzaa);
                    break;
                case 8:
                    playerEntity = (PlayerEntity) zza.zza(parcel, iZzaa, PlayerEntity.CREATOR);
                    break;
                case 9:
                    iZzg3 = zza.zzg(parcel, iZzaa);
                    break;
                case 10:
                    participantResult = (ParticipantResult) zza.zza(parcel, iZzaa, ParticipantResult.CREATOR);
                    break;
                case 11:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 12:
                    strZzo5 = zza.zzo(parcel, iZzaa);
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
        return new ParticipantEntity(iZzg, strZzo, strZzo2, uri, uri2, iZzg2, strZzo3, zZzc, playerEntity, iZzg3, participantResult, strZzo4, strZzo5);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfS, reason: merged with bridge method [inline-methods] */
    public ParticipantEntity[] newArray(int i) {
        return new ParticipantEntity[i];
    }
}
