package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzl implements Parcelable.Creator<LocationRequestUpdateData> {
    static void zza(LocationRequestUpdateData locationRequestUpdateData, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, locationRequestUpdateData.zzazf);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, locationRequestUpdateData.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) locationRequestUpdateData.zzazg, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, locationRequestUpdateData.zzuy(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) locationRequestUpdateData.mPendingIntent, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, locationRequestUpdateData.zzuz(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzel, reason: merged with bridge method [inline-methods] */
    public LocationRequestUpdateData createFromParcel(Parcel parcel) {
        IBinder iBinderZzp = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        int iZzg2 = 1;
        PendingIntent pendingIntent = null;
        IBinder iBinderZzp2 = null;
        LocationRequestInternal locationRequestInternal = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    locationRequestInternal = (LocationRequestInternal) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LocationRequestInternal.CREATOR);
                    break;
                case 3:
                    iBinderZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 4:
                    pendingIntent = (PendingIntent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PendingIntent.CREATOR);
                    break;
                case 5:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
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
        return new LocationRequestUpdateData(iZzg, iZzg2, locationRequestInternal, iBinderZzp2, pendingIntent, iBinderZzp);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgF, reason: merged with bridge method [inline-methods] */
    public LocationRequestUpdateData[] newArray(int i) {
        return new LocationRequestUpdateData[i];
    }
}
