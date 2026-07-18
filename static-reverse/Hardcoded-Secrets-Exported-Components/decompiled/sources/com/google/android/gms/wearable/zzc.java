package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<Asset> {
    static void zza(Asset asset, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, asset.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, asset.getData(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, asset.getDigest(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) asset.zzaSN, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) asset.uri, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgH, reason: merged with bridge method [inline-methods] */
    public Asset createFromParcel(Parcel parcel) {
        Uri uri = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        String strZzo = null;
        byte[] bArrZzr = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    bArrZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    parcelFileDescriptor = (ParcelFileDescriptor) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ParcelFileDescriptor.CREATOR);
                    break;
                case 5:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new Asset(iZzg, bArrZzr, strZzo, parcelFileDescriptor, uri);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjM, reason: merged with bridge method [inline-methods] */
    public Asset[] newArray(int i) {
        return new Asset[i];
    }
}
