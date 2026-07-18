package com.google.android.gms.nearby.sharing.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<TrustedDevicesRequest> {
    static void zza(TrustedDevicesRequest trustedDevicesRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, trustedDevicesRequest.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, trustedDevicesRequest.zzaGv, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, trustedDevicesRequest.zzaGw, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, trustedDevicesRequest.zzxa(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfF, reason: merged with bridge method [inline-methods] */
    public TrustedDevicesRequest createFromParcel(Parcel parcel) {
        IBinder iBinderZzp = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        byte[] bArrZzr = null;
        String strZzo = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    bArrZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzaa);
                    break;
                case 4:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new TrustedDevicesRequest(iZzg, strZzo, bArrZzr, iBinderZzp);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziu, reason: merged with bridge method [inline-methods] */
    public TrustedDevicesRequest[] newArray(int i) {
        return new TrustedDevicesRequest[i];
    }
}
