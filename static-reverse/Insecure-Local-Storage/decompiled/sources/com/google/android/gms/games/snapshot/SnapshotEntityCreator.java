package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class SnapshotEntityCreator implements Parcelable.Creator<SnapshotEntity> {
    static void zza(SnapshotEntity snapshotEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, (Parcelable) snapshotEntity.getMetadata(), i, false);
        zzb.zzc(parcel, 1000, snapshotEntity.getVersionCode());
        zzb.zza(parcel, 3, (Parcelable) snapshotEntity.getSnapshotContents(), i, false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdS, reason: merged with bridge method [inline-methods] */
    public SnapshotEntity createFromParcel(Parcel parcel) {
        SnapshotContentsEntity snapshotContentsEntity;
        SnapshotMetadataEntity snapshotMetadataEntity;
        int iZzg;
        SnapshotContentsEntity snapshotContentsEntity2 = null;
        int iZzab = zza.zzab(parcel);
        int i = 0;
        SnapshotMetadataEntity snapshotMetadataEntity2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    SnapshotMetadataEntity snapshotMetadataEntity3 = (SnapshotMetadataEntity) zza.zza(parcel, iZzaa, SnapshotMetadataEntity.CREATOR);
                    iZzg = i;
                    snapshotContentsEntity = snapshotContentsEntity2;
                    snapshotMetadataEntity = snapshotMetadataEntity3;
                    break;
                case 3:
                    snapshotContentsEntity = (SnapshotContentsEntity) zza.zza(parcel, iZzaa, SnapshotContentsEntity.CREATOR);
                    snapshotMetadataEntity = snapshotMetadataEntity2;
                    iZzg = i;
                    break;
                case 1000:
                    SnapshotContentsEntity snapshotContentsEntity3 = snapshotContentsEntity2;
                    snapshotMetadataEntity = snapshotMetadataEntity2;
                    iZzg = zza.zzg(parcel, iZzaa);
                    snapshotContentsEntity = snapshotContentsEntity3;
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    snapshotContentsEntity = snapshotContentsEntity2;
                    snapshotMetadataEntity = snapshotMetadataEntity2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            snapshotMetadataEntity2 = snapshotMetadataEntity;
            snapshotContentsEntity2 = snapshotContentsEntity;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SnapshotEntity(i, snapshotMetadataEntity2, snapshotContentsEntity2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgc, reason: merged with bridge method [inline-methods] */
    public SnapshotEntity[] newArray(int i) {
        return new SnapshotEntity[i];
    }
}
