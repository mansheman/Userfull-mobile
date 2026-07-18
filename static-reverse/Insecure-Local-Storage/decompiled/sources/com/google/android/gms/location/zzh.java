package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<LocationSettingsResult> {
    static void zza(LocationSettingsResult locationSettingsResult, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) locationSettingsResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, locationSettingsResult.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) locationSettingsResult.getLocationSettingsStates(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeg, reason: merged with bridge method [inline-methods] */
    public LocationSettingsResult createFromParcel(Parcel parcel) {
        LocationSettingsStates locationSettingsStates;
        Status status;
        int iZzg;
        LocationSettingsStates locationSettingsStates2 = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Status status3 = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
                    iZzg = i;
                    locationSettingsStates = locationSettingsStates2;
                    status = status3;
                    break;
                case 2:
                    locationSettingsStates = (LocationSettingsStates) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LocationSettingsStates.CREATOR);
                    status = status2;
                    iZzg = i;
                    break;
                case 1000:
                    LocationSettingsStates locationSettingsStates3 = locationSettingsStates2;
                    status = status2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    locationSettingsStates = locationSettingsStates3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    locationSettingsStates = locationSettingsStates2;
                    status = status2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            status2 = status;
            locationSettingsStates2 = locationSettingsStates;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new LocationSettingsResult(i, status2, locationSettingsStates2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgy, reason: merged with bridge method [inline-methods] */
    public LocationSettingsResult[] newArray(int i) {
        return new LocationSettingsResult[i];
    }
}
