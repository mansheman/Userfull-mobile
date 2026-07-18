package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzj implements Parcelable.Creator<PlacePhotoResult> {
    static void zza(PlacePhotoResult placePhotoResult, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) placePhotoResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placePhotoResult.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) placePhotoResult.zzazJ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzet, reason: merged with bridge method [inline-methods] */
    public PlacePhotoResult createFromParcel(Parcel parcel) {
        BitmapTeleporter bitmapTeleporter;
        Status status;
        int iZzg;
        BitmapTeleporter bitmapTeleporter2 = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Status status3 = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
                    iZzg = i;
                    bitmapTeleporter = bitmapTeleporter2;
                    status = status3;
                    break;
                case 2:
                    bitmapTeleporter = (BitmapTeleporter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, BitmapTeleporter.CREATOR);
                    status = status2;
                    iZzg = i;
                    break;
                case 1000:
                    BitmapTeleporter bitmapTeleporter3 = bitmapTeleporter2;
                    status = status2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    bitmapTeleporter = bitmapTeleporter3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    bitmapTeleporter = bitmapTeleporter2;
                    status = status2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            status2 = status;
            bitmapTeleporter2 = bitmapTeleporter;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PlacePhotoResult(i, status2, bitmapTeleporter2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgR, reason: merged with bridge method [inline-methods] */
    public PlacePhotoResult[] newArray(int i) {
        return new PlacePhotoResult[i];
    }
}
