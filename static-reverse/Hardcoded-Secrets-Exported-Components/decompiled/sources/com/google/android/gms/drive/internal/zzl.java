package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* loaded from: classes.dex */
public class zzl implements Parcelable.Creator<CreateFileRequest> {
    static void zza(CreateFileRequest createFileRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, createFileRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) createFileRequest.zzaeC, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) createFileRequest.zzaeA, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) createFileRequest.zzaes, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, createFileRequest.zzaeB, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, createFileRequest.zzaen);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, createFileRequest.zzadn, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, createFileRequest.zzaeD);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 9, createFileRequest.zzaeE);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaK, reason: merged with bridge method [inline-methods] */
    public CreateFileRequest createFromParcel(Parcel parcel) {
        int iZzg = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg2 = 0;
        boolean zZzc = false;
        Integer numZzh = null;
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
                    numZzh = com.google.android.gms.common.internal.safeparcel.zza.zzh(parcel, iZzaa);
                    break;
                case 6:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 7:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 9:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CreateFileRequest(iZzg3, driveId, metadataBundle, contents, numZzh, zZzc, strZzo, iZzg2, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcv, reason: merged with bridge method [inline-methods] */
    public CreateFileRequest[] newArray(int i) {
        return new CreateFileRequest[i];
    }
}
