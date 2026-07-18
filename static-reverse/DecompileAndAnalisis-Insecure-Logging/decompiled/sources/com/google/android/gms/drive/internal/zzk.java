package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<CreateFileIntentSenderRequest> {
    static void zza(CreateFileIntentSenderRequest createFileIntentSenderRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, createFileIntentSenderRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) createFileIntentSenderRequest.zzaeA, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, createFileIntentSenderRequest.zzacR);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, createFileIntentSenderRequest.zzadv, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) createFileIntentSenderRequest.zzady, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, createFileIntentSenderRequest.zzaeB, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaJ, reason: merged with bridge method [inline-methods] */
    public CreateFileIntentSenderRequest createFromParcel(Parcel parcel) {
        int iZzg = 0;
        Integer numZzh = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DriveId driveId = null;
        String strZzo = null;
        MetadataBundle metadataBundle = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    metadataBundle = (MetadataBundle) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, MetadataBundle.CREATOR);
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    break;
                case 6:
                    numZzh = com.google.android.gms.common.internal.safeparcel.zza.zzh(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CreateFileIntentSenderRequest(iZzg2, metadataBundle, iZzg, strZzo, driveId, numZzh);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcu, reason: merged with bridge method [inline-methods] */
    public CreateFileIntentSenderRequest[] newArray(int i) {
        return new CreateFileIntentSenderRequest[i];
    }
}
