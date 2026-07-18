package com.google.android.gms.games.snapshot;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class SnapshotMetadataChangeCreator implements Parcelable.Creator<SnapshotMetadataChangeEntity> {
    static void zza(SnapshotMetadataChangeEntity snapshotMetadataChangeEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, snapshotMetadataChangeEntity.getDescription(), false);
        zzb.zzc(parcel, 1000, snapshotMetadataChangeEntity.getVersionCode());
        zzb.zza(parcel, 2, snapshotMetadataChangeEntity.getPlayedTimeMillis(), false);
        zzb.zza(parcel, 4, (Parcelable) snapshotMetadataChangeEntity.getCoverImageUri(), i, false);
        zzb.zza(parcel, 5, (Parcelable) snapshotMetadataChangeEntity.zztQ(), i, false);
        zzb.zza(parcel, 6, snapshotMetadataChangeEntity.getProgressValue(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdT, reason: merged with bridge method [inline-methods] */
    public SnapshotMetadataChangeEntity createFromParcel(Parcel parcel) {
        Long lZzj = null;
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        Uri uri = null;
        BitmapTeleporter bitmapTeleporter = null;
        Long lZzj2 = null;
        String strZzo = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    lZzj2 = zza.zzj(parcel, iZzaa);
                    break;
                case 4:
                    uri = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 5:
                    bitmapTeleporter = (BitmapTeleporter) zza.zza(parcel, iZzaa, BitmapTeleporter.CREATOR);
                    break;
                case 6:
                    lZzj = zza.zzj(parcel, iZzaa);
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
        return new SnapshotMetadataChangeEntity(iZzg, strZzo, lZzj2, bitmapTeleporter, uri, lZzj);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgd, reason: merged with bridge method [inline-methods] */
    public SnapshotMetadataChangeEntity[] newArray(int i) {
        return new SnapshotMetadataChangeEntity[i];
    }
}
