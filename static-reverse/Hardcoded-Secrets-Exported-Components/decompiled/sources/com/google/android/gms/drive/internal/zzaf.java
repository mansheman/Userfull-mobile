package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.ChangeSequenceNumber;
import com.google.android.gms.drive.DriveSpace;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzaf implements Parcelable.Creator<GetChangesRequest> {
    static void zza(GetChangesRequest getChangesRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, getChangesRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) getChangesRequest.zzafJ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, getChangesRequest.zzafK);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, getChangesRequest.zzadR, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaR, reason: merged with bridge method [inline-methods] */
    public GetChangesRequest createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc;
        int iZzg;
        ChangeSequenceNumber changeSequenceNumber;
        int iZzg2;
        ArrayList arrayList = null;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ChangeSequenceNumber changeSequenceNumber2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    ArrayList arrayList2 = arrayList;
                    iZzg = i;
                    changeSequenceNumber = changeSequenceNumber2;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    arrayListZzc = arrayList2;
                    break;
                case 2:
                    iZzg2 = i2;
                    int i3 = i;
                    changeSequenceNumber = (ChangeSequenceNumber) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ChangeSequenceNumber.CREATOR);
                    arrayListZzc = arrayList;
                    iZzg = i3;
                    break;
                case 3:
                    changeSequenceNumber = changeSequenceNumber2;
                    iZzg2 = i2;
                    ArrayList arrayList3 = arrayList;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    arrayListZzc = arrayList3;
                    break;
                case 4:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DriveSpace.CREATOR);
                    iZzg = i;
                    changeSequenceNumber = changeSequenceNumber2;
                    iZzg2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    arrayListZzc = arrayList;
                    iZzg = i;
                    changeSequenceNumber = changeSequenceNumber2;
                    iZzg2 = i2;
                    break;
            }
            i2 = iZzg2;
            changeSequenceNumber2 = changeSequenceNumber;
            i = iZzg;
            arrayList = arrayListZzc;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GetChangesRequest(i2, changeSequenceNumber2, i, arrayList);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcG, reason: merged with bridge method [inline-methods] */
    public GetChangesRequest[] newArray(int i) {
        return new GetChangesRequest[i];
    }
}
