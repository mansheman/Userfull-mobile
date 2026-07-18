package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;

/* loaded from: classes.dex */
public class zzbh implements Parcelable.Creator<OpenContentsRequest> {
    static void zza(OpenContentsRequest openContentsRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, openContentsRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) openContentsRequest.zzaeq, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, openContentsRequest.zzacS);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, openContentsRequest.zzagr);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbm, reason: merged with bridge method [inline-methods] */
    public OpenContentsRequest createFromParcel(Parcel parcel) {
        int iZzg;
        int iZzg2;
        DriveId driveId;
        int iZzg3;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DriveId driveId2 = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    int i4 = i;
                    iZzg2 = i2;
                    driveId = driveId2;
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i4;
                    break;
                case 2:
                    iZzg3 = i3;
                    int i5 = i2;
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    iZzg = i;
                    iZzg2 = i5;
                    break;
                case 3:
                    driveId = driveId2;
                    iZzg3 = i3;
                    int i6 = i;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i6;
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg2 = i2;
                    driveId = driveId2;
                    iZzg3 = i3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iZzg = i;
                    iZzg2 = i2;
                    driveId = driveId2;
                    iZzg3 = i3;
                    break;
            }
            i3 = iZzg3;
            driveId2 = driveId;
            i2 = iZzg2;
            i = iZzg;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new OpenContentsRequest(i3, driveId2, i2, i);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdb, reason: merged with bridge method [inline-methods] */
    public OpenContentsRequest[] newArray(int i) {
        return new OpenContentsRequest[i];
    }
}
