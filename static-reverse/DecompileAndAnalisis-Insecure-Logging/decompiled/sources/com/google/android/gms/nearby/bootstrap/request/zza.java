package com.google.android.gms.nearby.bootstrap.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.nearby.bootstrap.Device;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<ConnectRequest> {
    static void zza(ConnectRequest connectRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) connectRequest.zzwM(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, connectRequest.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, connectRequest.getName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, connectRequest.getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, connectRequest.zzwO(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, connectRequest.zzwP(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, connectRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, connectRequest.zzwK());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, connectRequest.zzwN());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, connectRequest.getToken(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzff, reason: merged with bridge method [inline-methods] */
    public ConnectRequest createFromParcel(Parcel parcel) {
        byte bZze = 0;
        IBinder iBinderZzp = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        long jZzi = 0;
        IBinder iBinderZzp2 = null;
        IBinder iBinderZzp3 = null;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        Device device = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    device = (Device) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Device.CREATOR);
                    break;
                case 2:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    iBinderZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 5:
                    iBinderZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 6:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 7:
                    bZze = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 8:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 9:
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
        return new ConnectRequest(iZzg, device, strZzo3, strZzo2, bZze, jZzi, strZzo, iBinderZzp3, iBinderZzp2, iBinderZzp);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhN, reason: merged with bridge method [inline-methods] */
    public ConnectRequest[] newArray(int i) {
        return new ConnectRequest[i];
    }
}
