package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.ChangeSequenceNumber;
import com.google.android.gms.drive.DriveId;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzat implements Parcelable.Creator<OnChangesResponse> {
    static void zza(OnChangesResponse onChangesResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, onChangesResponse.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) onChangesResponse.zzaga, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, onChangesResponse.zzagb, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) onChangesResponse.zzagc, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, onChangesResponse.zzagd);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaY, reason: merged with bridge method [inline-methods] */
    public OnChangesResponse createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        ChangeSequenceNumber changeSequenceNumber = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList arrayListZzc = null;
        DataHolder dataHolder = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    dataHolder = (DataHolder) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataHolder.CREATOR);
                    break;
                case 3:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DriveId.CREATOR);
                    break;
                case 4:
                    changeSequenceNumber = (ChangeSequenceNumber) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ChangeSequenceNumber.CREATOR);
                    break;
                case 5:
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
        return new OnChangesResponse(iZzg, dataHolder, arrayListZzc, changeSequenceNumber, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcN, reason: merged with bridge method [inline-methods] */
    public OnChangesResponse[] newArray(int i) {
        return new OnChangesResponse[i];
    }
}
