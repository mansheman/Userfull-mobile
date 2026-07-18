package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.Contents;

/* loaded from: classes.dex */
public class SnapshotContentsEntityCreator implements Parcelable.Creator<SnapshotContentsEntity> {
    static void zza(SnapshotContentsEntity snapshotContentsEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, (Parcelable) snapshotContentsEntity.zzpe(), i, false);
        zzb.zzc(parcel, 1000, snapshotContentsEntity.getVersionCode());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdR, reason: merged with bridge method [inline-methods] */
    public SnapshotContentsEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        Contents contents = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    contents = (Contents) zza.zza(parcel, iZzaa, Contents.CREATOR);
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
        return new SnapshotContentsEntity(iZzg, contents);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgb, reason: merged with bridge method [inline-methods] */
    public SnapshotContentsEntity[] newArray(int i) {
        return new SnapshotContentsEntity[i];
    }
}
