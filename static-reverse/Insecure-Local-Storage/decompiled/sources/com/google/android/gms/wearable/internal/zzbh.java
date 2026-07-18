package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzbh implements Parcelable.Creator<StorageInfoResponse> {
    static void zza(StorageInfoResponse storageInfoResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, storageInfoResponse.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, storageInfoResponse.statusCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, storageInfoResponse.zzaUN);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, storageInfoResponse.zzaUP, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhq, reason: merged with bridge method [inline-methods] */
    public StorageInfoResponse createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        long jZzi = 0;
        ArrayList arrayListZzc = null;
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
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, PackageStorageInfo.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new StorageInfoResponse(iZzg2, iZzg, jZzi, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzky, reason: merged with bridge method [inline-methods] */
    public StorageInfoResponse[] newArray(int i) {
        return new StorageInfoResponse[i];
    }
}
