package com.google.android.gms.fitness.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataSource;

/* loaded from: classes.dex */
public class zzg implements Parcelable.Creator<DataSourceQueryParams> {
    static void zza(DataSourceQueryParams dataSourceQueryParams, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) dataSourceQueryParams.zzajG, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataSourceQueryParams.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, dataSourceQueryParams.zzOw);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, dataSourceQueryParams.zzajW);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, dataSourceQueryParams.zzamd);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, dataSourceQueryParams.zzalY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, dataSourceQueryParams.zzame);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcG, reason: merged with bridge method [inline-methods] */
    public DataSourceQueryParams createFromParcel(Parcel parcel) {
        long jZzi = 0;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DataSource dataSource = null;
        int iZzg2 = 0;
        long jZzi2 = 0;
        long jZzi3 = 0;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    dataSource = (DataSource) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 2:
                    jZzi3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 5:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DataSourceQueryParams(iZzg3, dataSource, jZzi3, jZzi2, jZzi, iZzg2, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeA, reason: merged with bridge method [inline-methods] */
    public DataSourceQueryParams[] newArray(int i) {
        return new DataSourceQueryParams[i];
    }
}
