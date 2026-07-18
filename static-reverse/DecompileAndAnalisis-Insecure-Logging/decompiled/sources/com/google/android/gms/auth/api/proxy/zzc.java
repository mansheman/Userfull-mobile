package com.google.android.gms.auth.api.proxy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<ProxyResponse> {
    static void zza(ProxyResponse proxyResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, proxyResponse.zzPu);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, proxyResponse.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) proxyResponse.zzPv, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, proxyResponse.zzPw);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, proxyResponse.zzPt, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, proxyResponse.zzPs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzI, reason: merged with bridge method [inline-methods] */
    public ProxyResponse createFromParcel(Parcel parcel) {
        byte[] bArrZzr = null;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Bundle bundleZzq = null;
        PendingIntent pendingIntent = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    pendingIntent = (PendingIntent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PendingIntent.CREATOR);
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 5:
                    bArrZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ProxyResponse(iZzg3, iZzg2, pendingIntent, iZzg, bundleZzq, bArrZzr);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzay, reason: merged with bridge method [inline-methods] */
    public ProxyResponse[] newArray(int i) {
        return new ProxyResponse[i];
    }
}
