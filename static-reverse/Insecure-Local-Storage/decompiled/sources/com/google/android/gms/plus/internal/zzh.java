package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<PlusSession> {
    static void zza(PlusSession plusSession, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, plusSession.getAccountName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, plusSession.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, plusSession.zzxx(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, plusSession.zzxy(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, plusSession.zzxz(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, plusSession.zzxA(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, plusSession.zzxB(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, plusSession.zzlB(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, plusSession.zzxC(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, (Parcelable) plusSession.zzxD(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfJ, reason: merged with bridge method [inline-methods] */
    public PlusSession createFromParcel(Parcel parcel) {
        PlusCommonExtras plusCommonExtras = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String[] strArrZzA = null;
        String[] strArrZzA2 = null;
        String[] strArrZzA3 = null;
        String strZzo5 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strArrZzA3 = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
                    break;
                case 3:
                    strArrZzA2 = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
                    break;
                case 4:
                    strArrZzA = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
                    break;
                case 5:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    plusCommonExtras = (PlusCommonExtras) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PlusCommonExtras.CREATOR);
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
        return new PlusSession(iZzg, strZzo5, strArrZzA3, strArrZzA2, strArrZzA, strZzo4, strZzo3, strZzo2, strZzo, plusCommonExtras);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziz, reason: merged with bridge method [inline-methods] */
    public PlusSession[] newArray(int i) {
        return new PlusSession[i];
    }
}
