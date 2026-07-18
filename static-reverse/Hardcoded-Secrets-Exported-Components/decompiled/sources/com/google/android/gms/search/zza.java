package com.google.android.gms.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<GoogleNowAuthState> {
    static void zza(GoogleNowAuthState googleNowAuthState, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, googleNowAuthState.getAuthCode(), false);
        zzb.zzc(parcel, 1000, googleNowAuthState.zzCY);
        zzb.zza(parcel, 2, googleNowAuthState.getAccessToken(), false);
        zzb.zza(parcel, 3, googleNowAuthState.getNextAllowedTimeMillis());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfX, reason: merged with bridge method [inline-methods] */
    public GoogleNowAuthState createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        long jZzi = 0;
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GoogleNowAuthState(iZzg, strZzo2, strZzo, jZzi);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziN, reason: merged with bridge method [inline-methods] */
    public GoogleNowAuthState[] newArray(int i) {
        return new GoogleNowAuthState[i];
    }
}
