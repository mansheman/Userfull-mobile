package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<AccountChangeEvent> {
    static void zza(AccountChangeEvent accountChangeEvent, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, accountChangeEvent.mVersion);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, accountChangeEvent.zzOw);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, accountChangeEvent.zzOx, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, accountChangeEvent.zzOy);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, accountChangeEvent.zzOz);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, accountChangeEvent.zzOA, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzap, reason: merged with bridge method [inline-methods] */
    public AccountChangeEvent[] newArray(int i) {
        return new AccountChangeEvent[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzz, reason: merged with bridge method [inline-methods] */
    public AccountChangeEvent createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        long jZzi = 0;
        int iZzg2 = 0;
        String strZzo2 = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
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
        return new AccountChangeEvent(iZzg3, jZzi, strZzo2, iZzg2, iZzg, strZzo);
    }
}
