package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzm implements Parcelable.Creator<ParcelableGeofence> {
    static void zza(ParcelableGeofence parcelableGeofence, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, parcelableGeofence.getRequestId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, parcelableGeofence.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, parcelableGeofence.getExpirationTime());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, parcelableGeofence.zzuA());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, parcelableGeofence.getLatitude());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, parcelableGeofence.getLongitude());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, parcelableGeofence.zzuB());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, parcelableGeofence.zzuC());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, parcelableGeofence.getNotificationResponsiveness());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 9, parcelableGeofence.zzuD());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzem, reason: merged with bridge method [inline-methods] */
    public ParcelableGeofence createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        int iZzg2 = 0;
        short sZzf = 0;
        double dZzm = 0.0d;
        double dZzm2 = 0.0d;
        float fZzl = 0.0f;
        long jZzi = 0;
        int iZzg3 = 0;
        int iZzg4 = -1;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    sZzf = com.google.android.gms.common.internal.safeparcel.zza.zzf(parcel, iZzaa);
                    break;
                case 4:
                    dZzm = com.google.android.gms.common.internal.safeparcel.zza.zzm(parcel, iZzaa);
                    break;
                case 5:
                    dZzm2 = com.google.android.gms.common.internal.safeparcel.zza.zzm(parcel, iZzaa);
                    break;
                case 6:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 7:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 9:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
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
        return new ParcelableGeofence(iZzg, strZzo, iZzg2, sZzf, dZzm, dZzm2, fZzl, jZzi, iZzg3, iZzg4);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgI, reason: merged with bridge method [inline-methods] */
    public ParcelableGeofence[] newArray(int i) {
        return new ParcelableGeofence[i];
    }
}
