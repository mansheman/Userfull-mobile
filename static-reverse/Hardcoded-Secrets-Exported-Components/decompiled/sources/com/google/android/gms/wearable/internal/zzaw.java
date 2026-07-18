package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzaw implements Parcelable.Creator<MessageEventParcelable> {
    static void zza(MessageEventParcelable messageEventParcelable, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, messageEventParcelable.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, messageEventParcelable.getRequestId());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, messageEventParcelable.getPath(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, messageEventParcelable.getData(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, messageEventParcelable.getSourceNodeId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhi, reason: merged with bridge method [inline-methods] */
    public MessageEventParcelable createFromParcel(Parcel parcel) {
        int iZzg = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        byte[] bArrZzr = null;
        String strZzo2 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    bArrZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzaa);
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
        return new MessageEventParcelable(iZzg2, iZzg, strZzo2, bArrZzr, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzkq, reason: merged with bridge method [inline-methods] */
    public MessageEventParcelable[] newArray(int i) {
        return new MessageEventParcelable[i];
    }
}
