package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.Subscription;

/* loaded from: classes.dex */
public class zzac implements Parcelable.Creator<SubscribeRequest> {
    static void zza(SubscribeRequest subscribeRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) subscribeRequest.zzrr(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, subscribeRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, subscribeRequest.zzrs());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, subscribeRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, subscribeRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdb, reason: merged with bridge method [inline-methods] */
    public SubscribeRequest createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        IBinder iBinderZzp = null;
        Subscription subscription = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    subscription = (Subscription) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Subscription.CREATOR);
                    break;
                case 2:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 4:
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
        return new SubscribeRequest(iZzg, subscription, zZzc, iBinderZzp, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeW, reason: merged with bridge method [inline-methods] */
    public SubscribeRequest[] newArray(int i) {
        return new SubscribeRequest[i];
    }
}
