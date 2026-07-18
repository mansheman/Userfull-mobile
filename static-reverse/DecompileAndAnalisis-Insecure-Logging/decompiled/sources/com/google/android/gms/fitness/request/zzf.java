package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<DataReadRequest> {
    static void zza(DataReadRequest dataReadRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, dataReadRequest.getDataTypes(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, dataReadRequest.getDataSources(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, dataReadRequest.zzkt());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, dataReadRequest.zzqs());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, dataReadRequest.getAggregatedDataTypes(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, dataReadRequest.getAggregatedDataSources(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, dataReadRequest.getBucketType());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, dataReadRequest.zzrb());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, (Parcelable) dataReadRequest.getActivityDataSource(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 10, dataReadRequest.getLimit());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, dataReadRequest.zzra());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, dataReadRequest.zzqZ());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, dataReadRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, dataReadRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 16, dataReadRequest.zzrc(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataReadRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcF, reason: merged with bridge method [inline-methods] */
    public DataReadRequest createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        ArrayList arrayListZzc2 = null;
        long jZzi = 0;
        long jZzi2 = 0;
        ArrayList arrayListZzc3 = null;
        ArrayList arrayListZzc4 = null;
        int iZzg2 = 0;
        long jZzi3 = 0;
        DataSource dataSource = null;
        int iZzg3 = 0;
        boolean zZzc = false;
        boolean zZzc2 = false;
        IBinder iBinderZzp = null;
        String strZzo = null;
        ArrayList arrayListZzc5 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataType.CREATOR);
                    break;
                case 2:
                    arrayListZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 3:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 5:
                    arrayListZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataType.CREATOR);
                    break;
                case 6:
                    arrayListZzc4 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 7:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    jZzi3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 9:
                    dataSource = (DataSource) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 10:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 12:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 13:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 14:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 15:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 16:
                    arrayListZzc5 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, Device.CREATOR);
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
        return new DataReadRequest(iZzg, arrayListZzc, arrayListZzc2, jZzi, jZzi2, arrayListZzc3, arrayListZzc4, iZzg2, jZzi3, dataSource, iZzg3, zZzc, zZzc2, iBinderZzp, strZzo, arrayListZzc5);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzez, reason: merged with bridge method [inline-methods] */
    public DataReadRequest[] newArray(int i) {
        return new DataReadRequest[i];
    }
}
