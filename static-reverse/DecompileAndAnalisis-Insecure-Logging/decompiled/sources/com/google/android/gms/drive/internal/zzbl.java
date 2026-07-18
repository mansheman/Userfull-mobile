package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;

/* loaded from: classes.dex */
public class zzbl implements Parcelable.Creator<RemoveEventListenerRequest> {
    static void zza(RemoveEventListenerRequest removeEventListenerRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, removeEventListenerRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) removeEventListenerRequest.zzacT, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, removeEventListenerRequest.zzaca);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbp, reason: merged with bridge method [inline-methods] */
    public RemoveEventListenerRequest createFromParcel(Parcel parcel) {
        int iZzg;
        DriveId driveId;
        int iZzg2;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DriveId driveId2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    int i3 = i;
                    driveId = driveId2;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i3;
                    break;
                case 2:
                    DriveId driveId3 = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    iZzg2 = i2;
                    iZzg = i;
                    driveId = driveId3;
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    driveId = driveId2;
                    iZzg2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iZzg = i;
                    driveId = driveId2;
                    iZzg2 = i2;
                    break;
            }
            i2 = iZzg2;
            driveId2 = driveId;
            i = iZzg;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new RemoveEventListenerRequest(i2, driveId2, i);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzde, reason: merged with bridge method [inline-methods] */
    public RemoveEventListenerRequest[] newArray(int i) {
        return new RemoveEventListenerRequest[i];
    }
}
