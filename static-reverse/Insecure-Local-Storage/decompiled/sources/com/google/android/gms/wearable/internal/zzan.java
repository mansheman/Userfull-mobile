package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzan implements Parcelable.Creator<GetDataItemResponse> {
    static void zza(GetDataItemResponse getDataItemResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, getDataItemResponse.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, getDataItemResponse.statusCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) getDataItemResponse.zzaUx, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhf, reason: merged with bridge method [inline-methods] */
    public GetDataItemResponse createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DataItemParcelable dataItemParcelable = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    dataItemParcelable = (DataItemParcelable) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataItemParcelable.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GetDataItemResponse(iZzg2, iZzg, dataItemParcelable);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzkn, reason: merged with bridge method [inline-methods] */
    public GetDataItemResponse[] newArray(int i) {
        return new GetDataItemResponse[i];
    }
}
