package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzbp implements Parcelable.Creator<SetResourceParentsRequest> {
    static void zza(SetResourceParentsRequest setResourceParentsRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, setResourceParentsRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) setResourceParentsRequest.zzagv, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, setResourceParentsRequest.zzagw, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbt, reason: merged with bridge method [inline-methods] */
    public SetResourceParentsRequest createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc;
        DriveId driveId;
        int iZzg;
        ArrayList arrayList = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        DriveId driveId2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    ArrayList arrayList2 = arrayList;
                    driveId = driveId2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    arrayListZzc = arrayList2;
                    break;
                case 2:
                    DriveId driveId3 = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    iZzg = i;
                    arrayListZzc = arrayList;
                    driveId = driveId3;
                    break;
                case 3:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DriveId.CREATOR);
                    driveId = driveId2;
                    iZzg = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    arrayListZzc = arrayList;
                    driveId = driveId2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            driveId2 = driveId;
            arrayList = arrayListZzc;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SetResourceParentsRequest(i, driveId2, arrayList);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdi, reason: merged with bridge method [inline-methods] */
    public SetResourceParentsRequest[] newArray(int i) {
        return new SetResourceParentsRequest[i];
    }
}
