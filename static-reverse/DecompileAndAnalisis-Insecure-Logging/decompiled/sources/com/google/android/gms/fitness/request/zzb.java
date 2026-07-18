package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.BleDevice;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<ClaimBleDeviceRequest> {
    static void zza(ClaimBleDeviceRequest claimBleDeviceRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, claimBleDeviceRequest.getDeviceAddress(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, claimBleDeviceRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) claimBleDeviceRequest.zzqT(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, claimBleDeviceRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, claimBleDeviceRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcB, reason: merged with bridge method [inline-methods] */
    public ClaimBleDeviceRequest createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        IBinder iBinderZzp = null;
        BleDevice bleDevice = null;
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    bleDevice = (BleDevice) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, BleDevice.CREATOR);
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
        return new ClaimBleDeviceRequest(iZzg, strZzo2, bleDevice, iBinderZzp, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzev, reason: merged with bridge method [inline-methods] */
    public ClaimBleDeviceRequest[] newArray(int i) {
        return new ClaimBleDeviceRequest[i];
    }
}
