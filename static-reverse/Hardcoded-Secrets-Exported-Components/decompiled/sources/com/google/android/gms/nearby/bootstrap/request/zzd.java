package com.google.android.gms.nearby.bootstrap.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.nearby.bootstrap.Device;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<DisconnectRequest> {
    static void zza(DisconnectRequest disconnectRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) disconnectRequest.zzwM(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, disconnectRequest.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, disconnectRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfi, reason: merged with bridge method [inline-methods] */
    public DisconnectRequest createFromParcel(Parcel parcel) {
        IBinder iBinderZzp;
        Device device;
        int iZzg;
        IBinder iBinder = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Device device2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Device device3 = (Device) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Device.CREATOR);
                    iZzg = i;
                    iBinderZzp = iBinder;
                    device = device3;
                    break;
                case 2:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    device = device2;
                    iZzg = i;
                    break;
                case 1000:
                    IBinder iBinder2 = iBinder;
                    device = device2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iBinderZzp = iBinder2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iBinderZzp = iBinder;
                    device = device2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            device2 = device;
            iBinder = iBinderZzp;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DisconnectRequest(i, device2, iBinder);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhQ, reason: merged with bridge method [inline-methods] */
    public DisconnectRequest[] newArray(int i) {
        return new DisconnectRequest[i];
    }
}
