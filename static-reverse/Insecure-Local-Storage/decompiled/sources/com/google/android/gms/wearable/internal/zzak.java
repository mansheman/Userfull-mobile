package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.wearable.ConnectionConfiguration;

/* loaded from: classes.dex */
public class zzak implements Parcelable.Creator<GetConfigResponse> {
    static void zza(GetConfigResponse getConfigResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, getConfigResponse.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, getConfigResponse.statusCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) getConfigResponse.zzaUu, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhc, reason: merged with bridge method [inline-methods] */
    public GetConfigResponse createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ConnectionConfiguration connectionConfiguration = null;
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
                    connectionConfiguration = (ConnectionConfiguration) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ConnectionConfiguration.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GetConfigResponse(iZzg2, iZzg, connectionConfiguration);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzkk, reason: merged with bridge method [inline-methods] */
    public GetConfigResponse[] newArray(int i) {
        return new GetConfigResponse[i];
    }
}
