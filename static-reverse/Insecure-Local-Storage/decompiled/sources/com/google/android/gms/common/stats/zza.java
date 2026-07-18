package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<ConnectionEvent> {
    static void zza(ConnectionEvent connectionEvent, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, connectionEvent.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, connectionEvent.getTimeMillis());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, connectionEvent.zzoG(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, connectionEvent.zzoH(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, connectionEvent.zzoI(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, connectionEvent.zzoJ(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, connectionEvent.zzoK(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, connectionEvent.zzoN());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, connectionEvent.zzoM());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 12, connectionEvent.getEventType());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, connectionEvent.zzoL(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzam, reason: merged with bridge method [inline-methods] */
    public ConnectionEvent createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        long jZzi = 0;
        int iZzg2 = 0;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        String strZzo6 = null;
        long jZzi2 = 0;
        long jZzi3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                case 9:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
                case 4:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 10:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 11:
                    jZzi3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 12:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 13:
                    strZzo6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ConnectionEvent(iZzg, jZzi, iZzg2, strZzo, strZzo2, strZzo3, strZzo4, strZzo5, strZzo6, jZzi2, jZzi3);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbM, reason: merged with bridge method [inline-methods] */
    public ConnectionEvent[] newArray(int i) {
        return new ConnectionEvent[i];
    }
}
