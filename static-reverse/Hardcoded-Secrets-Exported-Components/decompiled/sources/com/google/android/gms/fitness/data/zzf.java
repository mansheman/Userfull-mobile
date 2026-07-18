package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<DataSource> {
    static void zza(DataSource dataSource, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) dataSource.getDataType(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataSource.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, dataSource.getName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, dataSource.getType());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) dataSource.getDevice(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) dataSource.zzqB(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, dataSource.getStreamName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcn, reason: merged with bridge method [inline-methods] */
    public DataSource createFromParcel(Parcel parcel) {
        int iZzg = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Application application = null;
        Device device = null;
        String strZzo2 = null;
        DataType dataType = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    dataType = (DataType) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataType.CREATOR);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    device = (Device) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Device.CREATOR);
                    break;
                case 5:
                    application = (Application) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Application.CREATOR);
                    break;
                case 6:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
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
        return new DataSource(iZzg2, dataType, strZzo2, iZzg, device, application, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeg, reason: merged with bridge method [inline-methods] */
    public DataSource[] newArray(int i) {
        return new DataSource[i];
    }
}
