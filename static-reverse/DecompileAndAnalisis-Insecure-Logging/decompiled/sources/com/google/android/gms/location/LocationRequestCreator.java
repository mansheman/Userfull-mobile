package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.internal.widget.ActivityChooserView;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class LocationRequestCreator implements Parcelable.Creator<LocationRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(LocationRequest locationRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, locationRequest.mPriority);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, locationRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, locationRequest.zzaxU);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, locationRequest.zzaxV);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, locationRequest.zzamB);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, locationRequest.zzaxz);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, locationRequest.zzaxW);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, locationRequest.zzaxX);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, locationRequest.zzaxY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public LocationRequest createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        int iZzg2 = 102;
        long jZzi = 3600000;
        long jZzi2 = 600000;
        boolean zZzc = false;
        long jZzi3 = Long.MAX_VALUE;
        int iZzg3 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        float fZzl = 0.0f;
        long jZzi4 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 5:
                    jZzi3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 6:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 7:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 8:
                    jZzi4 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
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
        return new LocationRequest(iZzg, iZzg2, jZzi, jZzi2, zZzc, jZzi3, iZzg3, fZzl, jZzi4);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public LocationRequest[] newArray(int size) {
        return new LocationRequest[size];
    }
}
