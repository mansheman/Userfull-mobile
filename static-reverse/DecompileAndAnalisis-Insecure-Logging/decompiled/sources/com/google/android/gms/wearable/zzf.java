package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<PutDataRequest> {
    static void zza(PutDataRequest putDataRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, putDataRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) putDataRequest.getUri(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, putDataRequest.zzAR(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, putDataRequest.getData(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgJ, reason: merged with bridge method [inline-methods] */
    public PutDataRequest createFromParcel(Parcel parcel) {
        byte[] bArrZzr;
        Bundle bundleZzq;
        Uri uri;
        int iZzg;
        byte[] bArr = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Bundle bundle = null;
        Uri uri2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    byte[] bArr2 = bArr;
                    bundleZzq = bundle;
                    uri = uri2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    bArrZzr = bArr2;
                    break;
                case 2:
                    iZzg = i;
                    Bundle bundle2 = bundle;
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Uri.CREATOR);
                    bArrZzr = bArr;
                    bundleZzq = bundle2;
                    break;
                case 3:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    bArrZzr = bArr;
                    bundleZzq = bundle;
                    uri = uri2;
                    iZzg = i;
                    break;
                case 4:
                    uri = uri2;
                    iZzg = i;
                    byte[] bArr3 = bArr;
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    bArrZzr = bArr3;
                    break;
                case 5:
                    bArrZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzaa);
                    bundleZzq = bundle;
                    uri = uri2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            uri2 = uri;
            bundle = bundleZzq;
            bArr = bArrZzr;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PutDataRequest(i, uri2, bundle, bArr);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjO, reason: merged with bridge method [inline-methods] */
    public PutDataRequest[] newArray(int i) {
        return new PutDataRequest[i];
    }
}
