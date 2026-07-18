package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;

/* loaded from: classes.dex */
public class zzah implements Parcelable.Creator<GetMetadataRequest> {
    static void zza(GetMetadataRequest getMetadataRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, getMetadataRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) getMetadataRequest.zzaeq, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, getMetadataRequest.zzafN);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaT, reason: merged with bridge method [inline-methods] */
    public GetMetadataRequest createFromParcel(Parcel parcel) {
        boolean zZzc;
        DriveId driveId;
        int iZzg;
        boolean z = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DriveId driveId2 = null;
        int i = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    boolean z2 = z;
                    driveId = driveId2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    zZzc = z2;
                    break;
                case 2:
                    DriveId driveId3 = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    iZzg = i;
                    zZzc = z;
                    driveId = driveId3;
                    break;
                case 3:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    driveId = driveId2;
                    iZzg = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    zZzc = z;
                    driveId = driveId2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            driveId2 = driveId;
            z = zZzc;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GetMetadataRequest(i, driveId2, z);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcI, reason: merged with bridge method [inline-methods] */
    public GetMetadataRequest[] newArray(int i) {
        return new GetMetadataRequest[i];
    }
}
