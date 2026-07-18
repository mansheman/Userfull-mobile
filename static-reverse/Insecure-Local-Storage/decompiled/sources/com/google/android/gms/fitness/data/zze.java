package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<DataSet> {
    static void zza(DataSet dataSet, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) dataSet.getDataSource(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataSet.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) dataSet.getDataType(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel, 3, dataSet.zzqz(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, dataSet.zzqA(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, dataSet.zzqr());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcm, reason: merged with bridge method [inline-methods] */
    public DataSet createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList arrayList = new ArrayList();
        DataType dataType = null;
        DataSource dataSource = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    dataSource = (DataSource) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 2:
                    dataType = (DataType) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataType.CREATOR);
                    break;
                case 3:
                    com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, arrayList, getClass().getClassLoader());
                    break;
                case 4:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 5:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DataSet(iZzg, dataSource, dataType, arrayList, arrayListZzc, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzef, reason: merged with bridge method [inline-methods] */
    public DataSet[] newArray(int i) {
        return new DataSet[i];
    }
}
