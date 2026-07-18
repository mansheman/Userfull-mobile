package com.google.android.gms.nearby.bootstrap.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.nearby.bootstrap.Device;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<SendDataRequest> {
    static void zza(SendDataRequest sendDataRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) sendDataRequest.zzwM(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, sendDataRequest.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, sendDataRequest.getData(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, sendDataRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfk, reason: merged with bridge method [inline-methods] */
    public SendDataRequest createFromParcel(Parcel parcel) {
        IBinder iBinderZzp;
        byte[] bArrZzr;
        Device device;
        int iZzg;
        IBinder iBinder = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        byte[] bArr = null;
        Device device2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = i;
                    byte[] bArr2 = bArr;
                    device = (Device) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Device.CREATOR);
                    iBinderZzp = iBinder;
                    bArrZzr = bArr2;
                    break;
                case 2:
                    device = device2;
                    iZzg = i;
                    IBinder iBinder2 = iBinder;
                    bArrZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzaa);
                    iBinderZzp = iBinder2;
                    break;
                case 3:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    bArrZzr = bArr;
                    device = device2;
                    iZzg = i;
                    break;
                case 1000:
                    IBinder iBinder3 = iBinder;
                    bArrZzr = bArr;
                    device = device2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iBinderZzp = iBinder3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iBinderZzp = iBinder;
                    bArrZzr = bArr;
                    device = device2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            device2 = device;
            bArr = bArrZzr;
            iBinder = iBinderZzp;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SendDataRequest(i, device2, bArr, iBinder);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhS, reason: merged with bridge method [inline-methods] */
    public SendDataRequest[] newArray(int i) {
        return new SendDataRequest[i];
    }
}
