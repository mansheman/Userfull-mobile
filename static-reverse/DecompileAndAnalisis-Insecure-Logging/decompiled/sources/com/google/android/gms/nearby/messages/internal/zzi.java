package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.nearby.messages.Strategy;

/* loaded from: classes.dex */
public class zzi implements Parcelable.Creator<PublishRequest> {
    static void zza(PublishRequest publishRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, publishRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) publishRequest.zzaGb, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) publishRequest.zzaGc, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, publishRequest.zzxa(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, publishRequest.zzayp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, publishRequest.zzaGe, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfu, reason: merged with bridge method [inline-methods] */
    public PublishRequest createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        IBinder iBinderZzp = null;
        Strategy strategy = null;
        MessageWrapper messageWrapper = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    messageWrapper = (MessageWrapper) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, MessageWrapper.CREATOR);
                    break;
                case 3:
                    strategy = (Strategy) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Strategy.CREATOR);
                    break;
                case 4:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 5:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
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
        return new PublishRequest(iZzg, messageWrapper, strategy, iBinderZzp, strZzo2, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzij, reason: merged with bridge method [inline-methods] */
    public PublishRequest[] newArray(int i) {
        return new PublishRequest[i];
    }
}
