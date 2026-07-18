package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<LocationRequestInternal> {
    static void zza(LocationRequestInternal locationRequestInternal, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) locationRequestInternal.zzamz, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, locationRequestInternal.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, locationRequestInternal.zzazb);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, locationRequestInternal.zzazc);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, locationRequestInternal.zzazd);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, locationRequestInternal.zzaze, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, locationRequestInternal.mTag, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzek, reason: merged with bridge method [inline-methods] */
    public LocationRequestInternal createFromParcel(Parcel parcel) {
        String strZzo = null;
        boolean zZzc = true;
        boolean zZzc2 = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        List<ClientIdentity> listZzc = LocationRequestInternal.zzaza;
        boolean zZzc3 = true;
        LocationRequest locationRequest = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    locationRequest = (LocationRequest) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LocationRequest.CREATOR);
                    break;
                case 2:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 5:
                    listZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, ClientIdentity.CREATOR);
                    break;
                case 6:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
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
        return new LocationRequestInternal(iZzg, locationRequest, zZzc2, zZzc3, zZzc, listZzc, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgE, reason: merged with bridge method [inline-methods] */
    public LocationRequestInternal[] newArray(int i) {
        return new LocationRequestInternal[i];
    }
}
