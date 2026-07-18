package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.nearby.messages.MessageFilter;
import com.google.android.gms.nearby.messages.Strategy;

/* loaded from: classes.dex */
public class zzj implements Parcelable.Creator<SubscribeRequest> {
    static void zza(SubscribeRequest subscribeRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, subscribeRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, subscribeRequest.zzxb(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) subscribeRequest.zzaGc, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, subscribeRequest.zzxa(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) subscribeRequest.zzaGg, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) subscribeRequest.zzaGh, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, subscribeRequest.zzaGi);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, subscribeRequest.zzayp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, subscribeRequest.zzaGe, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfv, reason: merged with bridge method [inline-methods] */
    public SubscribeRequest createFromParcel(Parcel parcel) {
        int iZzg = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo2 = null;
        PendingIntent pendingIntent = null;
        MessageFilter messageFilter = null;
        IBinder iBinderZzp = null;
        Strategy strategy = null;
        IBinder iBinderZzp2 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iBinderZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 3:
                    strategy = (Strategy) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Strategy.CREATOR);
                    break;
                case 4:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 5:
                    messageFilter = (MessageFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, MessageFilter.CREATOR);
                    break;
                case 6:
                    pendingIntent = (PendingIntent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PendingIntent.CREATOR);
                    break;
                case 7:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SubscribeRequest(iZzg2, iBinderZzp2, strategy, iBinderZzp, messageFilter, pendingIntent, iZzg, strZzo2, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzik, reason: merged with bridge method [inline-methods] */
    public SubscribeRequest[] newArray(int i) {
        return new SubscribeRequest[i];
    }
}
