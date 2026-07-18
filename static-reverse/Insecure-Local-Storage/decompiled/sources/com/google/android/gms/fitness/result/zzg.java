package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataType;

/* loaded from: classes.dex */
public class zzg implements Parcelable.Creator<DataTypeResult> {
    static void zza(DataTypeResult dataTypeResult, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) dataTypeResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataTypeResult.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) dataTypeResult.getDataType(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdk, reason: merged with bridge method [inline-methods] */
    public DataTypeResult createFromParcel(Parcel parcel) {
        DataType dataType;
        Status status;
        int iZzg;
        DataType dataType2 = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Status status3 = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
                    iZzg = i;
                    dataType = dataType2;
                    status = status3;
                    break;
                case 3:
                    dataType = (DataType) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataType.CREATOR);
                    status = status2;
                    iZzg = i;
                    break;
                case 1000:
                    DataType dataType3 = dataType2;
                    status = status2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    dataType = dataType3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    dataType = dataType2;
                    status = status2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            status2 = status;
            dataType2 = dataType;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DataTypeResult(i, status2, dataType2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzff, reason: merged with bridge method [inline-methods] */
    public DataTypeResult[] newArray(int i) {
        return new DataTypeResult[i];
    }
}
