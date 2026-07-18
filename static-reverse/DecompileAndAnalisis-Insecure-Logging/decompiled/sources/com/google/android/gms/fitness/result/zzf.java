package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<DataStatsResult> {
    static void zza(DataStatsResult dataStatsResult, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) dataStatsResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataStatsResult.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, dataStatsResult.zzrx(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdj, reason: merged with bridge method [inline-methods] */
    public DataStatsResult createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc;
        Status status;
        int iZzg;
        ArrayList arrayList = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Status status3 = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
                    iZzg = i;
                    arrayListZzc = arrayList;
                    status = status3;
                    break;
                case 2:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSourceStatsResult.CREATOR);
                    status = status2;
                    iZzg = i;
                    break;
                case 1000:
                    ArrayList arrayList2 = arrayList;
                    status = status2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    arrayListZzc = arrayList2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    arrayListZzc = arrayList;
                    status = status2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            status2 = status;
            arrayList = arrayListZzc;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DataStatsResult(i, status2, arrayList);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfe, reason: merged with bridge method [inline-methods] */
    public DataStatsResult[] newArray(int i) {
        return new DataStatsResult[i];
    }
}
