package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzaf implements Parcelable.Creator<GetCapabilityResponse> {
    static void zza(GetCapabilityResponse getCapabilityResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, getCapabilityResponse.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, getCapabilityResponse.statusCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) getCapabilityResponse.zzaUr, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgX, reason: merged with bridge method [inline-methods] */
    public GetCapabilityResponse createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        CapabilityInfoParcelable capabilityInfoParcelable = null;
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
                    capabilityInfoParcelable = (CapabilityInfoParcelable) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, CapabilityInfoParcelable.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GetCapabilityResponse(iZzg2, iZzg, capabilityInfoParcelable);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzkf, reason: merged with bridge method [inline-methods] */
    public GetCapabilityResponse[] newArray(int i) {
        return new GetCapabilityResponse[i];
    }
}
