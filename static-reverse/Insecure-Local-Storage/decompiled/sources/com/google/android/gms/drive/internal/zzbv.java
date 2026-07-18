package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;

/* loaded from: classes.dex */
public class zzbv implements Parcelable.Creator<UpdatePermissionRequest> {
    static void zza(UpdatePermissionRequest updatePermissionRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, updatePermissionRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) updatePermissionRequest.zzacT, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, updatePermissionRequest.zzadz, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, updatePermissionRequest.zzagx);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, updatePermissionRequest.zzaen);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, updatePermissionRequest.zzadn, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzby, reason: merged with bridge method [inline-methods] */
    public UpdatePermissionRequest createFromParcel(Parcel parcel) {
        String strZzo = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        DriveId driveId = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new UpdatePermissionRequest(iZzg2, driveId, strZzo2, iZzg, zZzc, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdn, reason: merged with bridge method [inline-methods] */
    public UpdatePermissionRequest[] newArray(int i) {
        return new UpdatePermissionRequest[i];
    }
}
