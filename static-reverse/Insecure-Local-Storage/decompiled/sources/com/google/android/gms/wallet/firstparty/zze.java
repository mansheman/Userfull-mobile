package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<GetInstrumentsResponse> {
    static void zza(GetInstrumentsResponse getInstrumentsResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, getInstrumentsResponse.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, getInstrumentsResponse.zzaRM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, getInstrumentsResponse.zzaRN, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgu, reason: merged with bridge method [inline-methods] */
    public GetInstrumentsResponse createFromParcel(Parcel parcel) {
        String[] strArrZzA = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        byte[][] bArrZzs = (byte[][]) null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strArrZzA = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
                    break;
                case 3:
                    bArrZzs = com.google.android.gms.common.internal.safeparcel.zza.zzs(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GetInstrumentsResponse(iZzg, strArrZzA, bArrZzs);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjw, reason: merged with bridge method [inline-methods] */
    public GetInstrumentsResponse[] newArray(int i) {
        return new GetInstrumentsResponse[i];
    }
}
