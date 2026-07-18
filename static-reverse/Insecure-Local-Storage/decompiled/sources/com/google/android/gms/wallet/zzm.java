package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzm implements Parcelable.Creator<NotifyTransactionStatusRequest> {
    static void zza(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, notifyTransactionStatusRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, notifyTransactionStatusRequest.zzaQm, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, notifyTransactionStatusRequest.status);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, notifyTransactionStatusRequest.zzaRw, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgm, reason: merged with bridge method [inline-methods] */
    public NotifyTransactionStatusRequest createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo2 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
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
        return new NotifyTransactionStatusRequest(iZzg2, strZzo2, iZzg, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjo, reason: merged with bridge method [inline-methods] */
    public NotifyTransactionStatusRequest[] newArray(int i) {
        return new NotifyTransactionStatusRequest[i];
    }
}
