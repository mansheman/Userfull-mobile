package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* loaded from: classes.dex */
public class zzg implements Parcelable.Creator<CloseContentsAndUpdateMetadataRequest> {
    static void zza(CloseContentsAndUpdateMetadataRequest closeContentsAndUpdateMetadataRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, closeContentsAndUpdateMetadataRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) closeContentsAndUpdateMetadataRequest.zzaeq, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) closeContentsAndUpdateMetadataRequest.zzaer, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) closeContentsAndUpdateMetadataRequest.zzaes, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, closeContentsAndUpdateMetadataRequest.zzado);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, closeContentsAndUpdateMetadataRequest.zzadn, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, closeContentsAndUpdateMetadataRequest.zzaet);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, closeContentsAndUpdateMetadataRequest.zzaeu);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, closeContentsAndUpdateMetadataRequest.zzaev);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaG, reason: merged with bridge method [inline-methods] */
    public CloseContentsAndUpdateMetadataRequest createFromParcel(Parcel parcel) {
        String strZzo = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        int iZzg2 = 0;
        boolean zZzc2 = false;
        Contents contents = null;
        MetadataBundle metadataBundle = null;
        DriveId driveId = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    break;
                case 3:
                    metadataBundle = (MetadataBundle) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, MetadataBundle.CREATOR);
                    break;
                case 4:
                    contents = (Contents) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Contents.CREATOR);
                    break;
                case 5:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 9:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CloseContentsAndUpdateMetadataRequest(iZzg3, driveId, metadataBundle, contents, zZzc2, strZzo, iZzg2, iZzg, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcq, reason: merged with bridge method [inline-methods] */
    public CloseContentsAndUpdateMetadataRequest[] newArray(int i) {
        return new CloseContentsAndUpdateMetadataRequest[i];
    }
}
