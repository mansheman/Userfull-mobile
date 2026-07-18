package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzi implements Parcelable.Creator<LocationSettingsStates> {
    static void zza(LocationSettingsStates locationSettingsStates, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, locationSettingsStates.isGpsUsable());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, locationSettingsStates.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, locationSettingsStates.isNetworkLocationUsable());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, locationSettingsStates.isBleUsable());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, locationSettingsStates.isGpsPresent());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, locationSettingsStates.isNetworkLocationPresent());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, locationSettingsStates.isBlePresent());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, locationSettingsStates.zzus());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeh, reason: merged with bridge method [inline-methods] */
    public LocationSettingsStates createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        boolean zZzc2 = false;
        boolean zZzc3 = false;
        boolean zZzc4 = false;
        boolean zZzc5 = false;
        boolean zZzc6 = false;
        boolean zZzc7 = false;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    zZzc7 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 2:
                    zZzc6 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    zZzc5 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    zZzc4 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 5:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 7:
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
        return new LocationSettingsStates(iZzg, zZzc7, zZzc6, zZzc5, zZzc4, zZzc3, zZzc2, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgz, reason: merged with bridge method [inline-methods] */
    public LocationSettingsStates[] newArray(int i) {
        return new LocationSettingsStates[i];
    }
}
