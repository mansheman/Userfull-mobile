package com.google.android.gms.ads.internal.purchase;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<GInAppPurchaseManagerInfoParcel> {
    static void zza(GInAppPurchaseManagerInfoParcel gInAppPurchaseManagerInfoParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, gInAppPurchaseManagerInfoParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, gInAppPurchaseManagerInfoParcel.zzfb(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, gInAppPurchaseManagerInfoParcel.zzfc(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, gInAppPurchaseManagerInfoParcel.zzfd(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, gInAppPurchaseManagerInfoParcel.zzfa(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzi, reason: merged with bridge method [inline-methods] */
    public GInAppPurchaseManagerInfoParcel createFromParcel(Parcel parcel) {
        IBinder iBinderZzp = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        IBinder iBinderZzp2 = null;
        IBinder iBinderZzp3 = null;
        IBinder iBinderZzp4 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
                case 3:
                    iBinderZzp4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 4:
                    iBinderZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 5:
                    iBinderZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 6:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GInAppPurchaseManagerInfoParcel(iZzg, iBinderZzp4, iBinderZzp3, iBinderZzp2, iBinderZzp);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzx, reason: merged with bridge method [inline-methods] */
    public GInAppPurchaseManagerInfoParcel[] newArray(int i) {
        return new GInAppPurchaseManagerInfoParcel[i];
    }
}
