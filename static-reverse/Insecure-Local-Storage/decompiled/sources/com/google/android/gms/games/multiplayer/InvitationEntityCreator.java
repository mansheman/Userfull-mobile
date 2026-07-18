package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class InvitationEntityCreator implements Parcelable.Creator<InvitationEntity> {
    static void zza(InvitationEntity invitationEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, (Parcelable) invitationEntity.getGame(), i, false);
        zzb.zzc(parcel, 1000, invitationEntity.getVersionCode());
        zzb.zza(parcel, 2, invitationEntity.getInvitationId(), false);
        zzb.zza(parcel, 3, invitationEntity.getCreationTimestamp());
        zzb.zzc(parcel, 4, invitationEntity.getInvitationType());
        zzb.zza(parcel, 5, (Parcelable) invitationEntity.getInviter(), i, false);
        zzb.zzc(parcel, 6, invitationEntity.getParticipants(), false);
        zzb.zzc(parcel, 7, invitationEntity.getVariant());
        zzb.zzc(parcel, 8, invitationEntity.getAvailableAutoMatchSlots());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdI */
    public InvitationEntity createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc = null;
        int iZzg = 0;
        int iZzab = zza.zzab(parcel);
        long jZzi = 0;
        int iZzg2 = 0;
        ParticipantEntity participantEntity = null;
        int iZzg3 = 0;
        String strZzo = null;
        GameEntity gameEntity = null;
        int iZzg4 = 0;
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
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    iZzg3 = zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    participantEntity = (ParticipantEntity) zza.zza(parcel, iZzaa, ParticipantEntity.CREATOR);
                    break;
                case 6:
                    arrayListZzc = zza.zzc(parcel, iZzaa, ParticipantEntity.CREATOR);
                    break;
                case 7:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg4 = zza.zzg(parcel, iZzaa);
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new InvitationEntity(iZzg4, gameEntity, strZzo, jZzi, iZzg3, participantEntity, arrayListZzc, iZzg2, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfR, reason: merged with bridge method [inline-methods] */
    public InvitationEntity[] newArray(int i) {
        return new InvitationEntity[i];
    }
}
