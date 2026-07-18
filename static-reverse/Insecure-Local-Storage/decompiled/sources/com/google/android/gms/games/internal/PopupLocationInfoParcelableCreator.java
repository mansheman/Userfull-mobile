package com.google.android.gms.games.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class PopupLocationInfoParcelableCreator implements Parcelable.Creator<PopupLocationInfoParcelable> {
    static void zza(PopupLocationInfoParcelable popupLocationInfoParcelable, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, popupLocationInfoParcelable.zztc(), false);
        zzb.zzc(parcel, 1000, popupLocationInfoParcelable.getVersionCode());
        zzb.zza(parcel, 2, popupLocationInfoParcelable.getWindowToken(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdD, reason: merged with bridge method [inline-methods] */
    public PopupLocationInfoParcelable createFromParcel(Parcel parcel) {
        IBinder iBinderZzp = null;
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        Bundle bundleZzq = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    bundleZzq = zza.zzq(parcel, iZzaa);
                    break;
                case 2:
                    iBinderZzp = zza.zzp(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PopupLocationInfoParcelable(iZzg, bundleZzq, iBinderZzp);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfE, reason: merged with bridge method [inline-methods] */
    public PopupLocationInfoParcelable[] newArray(int i) {
        return new PopupLocationInfoParcelable[i];
    }
}
