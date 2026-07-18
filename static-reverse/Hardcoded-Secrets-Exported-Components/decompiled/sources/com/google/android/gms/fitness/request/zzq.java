package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzq implements Parcelable.Creator<ReadRawRequest> {
    static void zza(ReadRawRequest readRawRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, readRawRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, readRawRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, readRawRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, readRawRequest.zzrf(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, readRawRequest.zzra());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, readRawRequest.zzqZ());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcP, reason: merged with bridge method [inline-methods] */
    public ReadRawRequest createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        boolean zZzc2 = false;
        String strZzo = null;
        IBinder iBinderZzp = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSourceQueryParams.CREATOR);
                    break;
                case 4:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
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
        return new ReadRawRequest(iZzg, iBinderZzp, strZzo, arrayListZzc, zZzc2, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeJ, reason: merged with bridge method [inline-methods] */
    public ReadRawRequest[] newArray(int i) {
        return new ReadRawRequest[i];
    }
}
