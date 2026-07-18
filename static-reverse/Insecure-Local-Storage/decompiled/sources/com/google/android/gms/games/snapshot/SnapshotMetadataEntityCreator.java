package com.google.android.gms.games.snapshot;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.PlayerEntity;

/* loaded from: classes.dex */
public class SnapshotMetadataEntityCreator implements Parcelable.Creator<SnapshotMetadataEntity> {
    static void zza(SnapshotMetadataEntity snapshotMetadataEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, (Parcelable) snapshotMetadataEntity.getGame(), i, false);
        zzb.zzc(parcel, 1000, snapshotMetadataEntity.getVersionCode());
        zzb.zza(parcel, 2, (Parcelable) snapshotMetadataEntity.getOwner(), i, false);
        zzb.zza(parcel, 3, snapshotMetadataEntity.getSnapshotId(), false);
        zzb.zza(parcel, 5, (Parcelable) snapshotMetadataEntity.getCoverImageUri(), i, false);
        zzb.zza(parcel, 6, snapshotMetadataEntity.getCoverImageUrl(), false);
        zzb.zza(parcel, 7, snapshotMetadataEntity.getTitle(), false);
        zzb.zza(parcel, 8, snapshotMetadataEntity.getDescription(), false);
        zzb.zza(parcel, 9, snapshotMetadataEntity.getLastModifiedTimestamp());
        zzb.zza(parcel, 10, snapshotMetadataEntity.getPlayedTime());
        zzb.zza(parcel, 11, snapshotMetadataEntity.getCoverImageAspectRatio());
        zzb.zza(parcel, 12, snapshotMetadataEntity.getUniqueName(), false);
        zzb.zza(parcel, 13, snapshotMetadataEntity.hasChangePending());
        zzb.zza(parcel, 14, snapshotMetadataEntity.getProgressValue());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdU, reason: merged with bridge method [inline-methods] */
    public SnapshotMetadataEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        String strZzo = null;
        Uri uri = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        long jZzi = 0;
        long jZzi2 = 0;
        float fZzl = 0.0f;
        String strZzo5 = null;
        boolean zZzc = false;
        long jZzi3 = 0;
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
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    uri = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 6:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 10:
                    jZzi2 = zza.zzi(parcel, iZzaa);
                    break;
                case 11:
                    fZzl = zza.zzl(parcel, iZzaa);
                    break;
                case 12:
                    strZzo5 = zza.zzo(parcel, iZzaa);
                    break;
                case 13:
                    zZzc = zza.zzc(parcel, iZzaa);
                    break;
                case 14:
                    jZzi3 = zza.zzi(parcel, iZzaa);
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
        return new SnapshotMetadataEntity(iZzg, gameEntity, playerEntity, strZzo, uri, strZzo2, strZzo3, strZzo4, jZzi, jZzi2, fZzl, strZzo5, zZzc, jZzi3);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzge, reason: merged with bridge method [inline-methods] */
    public SnapshotMetadataEntity[] newArray(int i) {
        return new SnapshotMetadataEntity[i];
    }
}
