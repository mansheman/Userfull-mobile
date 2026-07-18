package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzg implements Parcelable.Creator<LocationSettingsRequest> {
    static void zza(LocationSettingsRequest locationSettingsRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, locationSettingsRequest.zzrj(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, locationSettingsRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, locationSettingsRequest.zzup());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, locationSettingsRequest.zzuq());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, locationSettingsRequest.zzur());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzef, reason: merged with bridge method [inline-methods] */
    public LocationSettingsRequest createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList arrayListZzc = null;
        boolean zZzc2 = false;
        boolean zZzc3 = false;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, LocationRequest.CREATOR);
                    break;
                case 2:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
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
        return new LocationSettingsRequest(iZzg, arrayListZzc, zZzc3, zZzc2, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgx, reason: merged with bridge method [inline-methods] */
    public LocationSettingsRequest[] newArray(int i) {
        return new LocationSettingsRequest[i];
    }
}
