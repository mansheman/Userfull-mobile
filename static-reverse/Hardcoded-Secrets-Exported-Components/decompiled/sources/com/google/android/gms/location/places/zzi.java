package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzi implements Parcelable.Creator<PlacePhotoMetadataResult> {
    static void zza(PlacePhotoMetadataResult placePhotoMetadataResult, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) placePhotoMetadataResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placePhotoMetadataResult.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) placePhotoMetadataResult.zzazH, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzes, reason: merged with bridge method [inline-methods] */
    public PlacePhotoMetadataResult createFromParcel(Parcel parcel) {
        DataHolder dataHolder;
        Status status;
        int iZzg;
        DataHolder dataHolder2 = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Status status3 = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
                    iZzg = i;
                    dataHolder = dataHolder2;
                    status = status3;
                    break;
                case 2:
                    dataHolder = (DataHolder) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataHolder.CREATOR);
                    status = status2;
                    iZzg = i;
                    break;
                case 1000:
                    DataHolder dataHolder3 = dataHolder2;
                    status = status2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    dataHolder = dataHolder3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    dataHolder = dataHolder2;
                    status = status2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            status2 = status;
            dataHolder2 = dataHolder;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PlacePhotoMetadataResult(i, status2, dataHolder2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgQ, reason: merged with bridge method [inline-methods] */
    public PlacePhotoMetadataResult[] newArray(int i) {
        return new PlacePhotoMetadataResult[i];
    }
}
