package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzn implements Parcelable.Creator<TileOverlayOptions> {
    static void zza(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, tileOverlayOptions.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, tileOverlayOptions.zzvL(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, tileOverlayOptions.isVisible());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, tileOverlayOptions.getZIndex());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, tileOverlayOptions.getFadeIn());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeY, reason: merged with bridge method [inline-methods] */
    public TileOverlayOptions createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        IBinder iBinderZzp = null;
        float fZzl = 0.0f;
        boolean zZzc2 = true;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 3:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 5:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new TileOverlayOptions(iZzg, iBinderZzp, zZzc, fZzl, zZzc2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhy, reason: merged with bridge method [inline-methods] */
    public TileOverlayOptions[] newArray(int i) {
        return new TileOverlayOptions[i];
    }
}
