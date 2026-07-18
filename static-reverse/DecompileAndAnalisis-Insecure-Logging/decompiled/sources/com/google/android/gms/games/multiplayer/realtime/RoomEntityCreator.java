package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class RoomEntityCreator implements Parcelable.Creator<RoomEntity> {
    static void zza(RoomEntity roomEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, roomEntity.getRoomId(), false);
        zzb.zzc(parcel, 1000, roomEntity.getVersionCode());
        zzb.zza(parcel, 2, roomEntity.getCreatorId(), false);
        zzb.zza(parcel, 3, roomEntity.getCreationTimestamp());
        zzb.zzc(parcel, 4, roomEntity.getStatus());
        zzb.zza(parcel, 5, roomEntity.getDescription(), false);
        zzb.zzc(parcel, 6, roomEntity.getVariant());
        zzb.zza(parcel, 7, roomEntity.getAutoMatchCriteria(), false);
        zzb.zzc(parcel, 8, roomEntity.getParticipants(), false);
        zzb.zzc(parcel, 9, roomEntity.getAutoMatchWaitEstimateSeconds());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdM */
    public RoomEntity createFromParcel(Parcel parcel) {
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        int iZzab = zza.zzab(parcel);
        long jZzi = 0;
        Bundle bundleZzq = null;
        int iZzg2 = 0;
        String strZzo = null;
        int iZzg3 = 0;
        String strZzo2 = null;
        String strZzo3 = null;
        int iZzg4 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    iZzg3 = zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 7:
                    bundleZzq = zza.zzq(parcel, iZzaa);
                    break;
                case 8:
                    arrayListZzc = zza.zzc(parcel, iZzaa, ParticipantEntity.CREATOR);
                    break;
                case 9:
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
        return new RoomEntity(iZzg4, strZzo3, strZzo2, jZzi, iZzg3, strZzo, iZzg2, bundleZzq, arrayListZzc, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfV, reason: merged with bridge method [inline-methods] */
    public RoomEntity[] newArray(int i) {
        return new RoomEntity[i];
    }
}
