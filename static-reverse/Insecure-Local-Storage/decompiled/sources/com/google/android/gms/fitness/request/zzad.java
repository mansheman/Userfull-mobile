package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzad implements Parcelable.Creator<UnclaimBleDeviceRequest> {
    static void zza(UnclaimBleDeviceRequest unclaimBleDeviceRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, unclaimBleDeviceRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, unclaimBleDeviceRequest.getDeviceAddress(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, unclaimBleDeviceRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, unclaimBleDeviceRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdc, reason: merged with bridge method [inline-methods] */
    public UnclaimBleDeviceRequest createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        IBinder iBinderZzp = null;
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 4:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
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
        return new UnclaimBleDeviceRequest(iZzg, strZzo2, iBinderZzp, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeX, reason: merged with bridge method [inline-methods] */
    public UnclaimBleDeviceRequest[] newArray(int i) {
        return new UnclaimBleDeviceRequest[i];
    }
}
