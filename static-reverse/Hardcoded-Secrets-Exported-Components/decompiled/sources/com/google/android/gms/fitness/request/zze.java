package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataSet;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<DataInsertRequest> {
    static void zza(DataInsertRequest dataInsertRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) dataInsertRequest.zzqK(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataInsertRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, dataInsertRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, dataInsertRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, dataInsertRequest.zzqY());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcE, reason: merged with bridge method [inline-methods] */
    public DataInsertRequest createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        IBinder iBinderZzp = null;
        DataSet dataSet = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    dataSet = (DataSet) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataSet.CREATOR);
                    break;
                case 2:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
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
        return new DataInsertRequest(iZzg, dataSet, iBinderZzp, strZzo, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzey, reason: merged with bridge method [inline-methods] */
    public DataInsertRequest[] newArray(int i) {
        return new DataInsertRequest[i];
    }
}
