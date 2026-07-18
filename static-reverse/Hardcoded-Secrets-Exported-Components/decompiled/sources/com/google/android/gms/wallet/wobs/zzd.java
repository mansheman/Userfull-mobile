package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<LoyaltyPointsBalance> {
    static void zza(LoyaltyPointsBalance loyaltyPointsBalance, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, loyaltyPointsBalance.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, loyaltyPointsBalance.zzaSB);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, loyaltyPointsBalance.zzaSC, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, loyaltyPointsBalance.zzaSD);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, loyaltyPointsBalance.zzaQD, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, loyaltyPointsBalance.zzaSE);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, loyaltyPointsBalance.zzaSF);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgB, reason: merged with bridge method [inline-methods] */
    public LoyaltyPointsBalance createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        double dZzm = 0.0d;
        long jZzi = 0;
        int iZzg2 = -1;
        String strZzo2 = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    dZzm = com.google.android.gms.common.internal.safeparcel.zza.zzm(parcel, iZzaa);
                    break;
                case 5:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 7:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new LoyaltyPointsBalance(iZzg3, iZzg, strZzo2, dZzm, strZzo, jZzi, iZzg2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjG, reason: merged with bridge method [inline-methods] */
    public LoyaltyPointsBalance[] newArray(int i) {
        return new LoyaltyPointsBalance[i];
    }
}
