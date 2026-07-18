package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<ProxyRequest> {
    static void zza(ProxyRequest proxyRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, proxyRequest.zzzf, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, proxyRequest.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, proxyRequest.zzPq);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, proxyRequest.zzPr);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, proxyRequest.zzPs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, proxyRequest.zzPt, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzH, reason: merged with bridge method [inline-methods] */
    public ProxyRequest createFromParcel(Parcel parcel) {
        int iZzg = 0;
        Bundle bundleZzq = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        long jZzi = 0;
        byte[] bArrZzr = null;
        String strZzo = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    bArrZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzaa);
                    break;
                case 5:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
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
        return new ProxyRequest(iZzg2, strZzo, iZzg, jZzi, bArrZzr, bundleZzq);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzax, reason: merged with bridge method [inline-methods] */
    public ProxyRequest[] newArray(int i) {
        return new ProxyRequest[i];
    }
}
