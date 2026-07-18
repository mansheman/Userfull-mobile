package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<UnpublishRequest> {
    static void zza(UnpublishRequest unpublishRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, unpublishRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) unpublishRequest.zzaGb, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, unpublishRequest.zzxa(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, unpublishRequest.zzayp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, unpublishRequest.zzaGe, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfw, reason: merged with bridge method [inline-methods] */
    public UnpublishRequest createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        IBinder iBinderZzp = null;
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
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
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
        return new UnpublishRequest(iZzg, messageWrapper, iBinderZzp, strZzo2, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzil, reason: merged with bridge method [inline-methods] */
    public UnpublishRequest[] newArray(int i) {
        return new UnpublishRequest[i];
    }
}
