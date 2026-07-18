package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<UserAddressRequest> {
    static void zza(UserAddressRequest userAddressRequest, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, userAddressRequest.getVersionCode());
        zzb.zzc(parcel, 2, userAddressRequest.zzawx, false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdY, reason: merged with bridge method [inline-methods] */
    public UserAddressRequest createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, CountrySpecification.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new UserAddressRequest(iZzg, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgk, reason: merged with bridge method [inline-methods] */
    public UserAddressRequest[] newArray(int i) {
        return new UserAddressRequest[i];
    }
}
