package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<DataReadResult> {
    static void zza(DataReadResult dataReadResult, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel, 1, dataReadResult.zzrv(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataReadResult.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) dataReadResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel, 3, dataReadResult.zzru(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, dataReadResult.zzrt());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, dataReadResult.zzqA(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, dataReadResult.zzrw(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdg, reason: merged with bridge method [inline-methods] */
    public DataReadResult createFromParcel(Parcel parcel) {
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayListZzc2 = null;
        Status status = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, arrayList, getClass().getClassLoader());
                    break;
                case 2:
                    status = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
                    break;
                case 3:
                    com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, arrayList2, getClass().getClassLoader());
                    break;
                case 5:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    arrayListZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 7:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataType.CREATOR);
                    break;
                case 1000:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DataReadResult(iZzg2, arrayList, status, arrayList2, iZzg, arrayListZzc2, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfb, reason: merged with bridge method [inline-methods] */
    public DataReadResult[] newArray(int i) {
        return new DataReadResult[i];
    }
}
