package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<LoyaltyPoints> {
    static void zza(LoyaltyPoints loyaltyPoints, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, loyaltyPoints.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, loyaltyPoints.label, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) loyaltyPoints.zzaSA, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, loyaltyPoints.type, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) loyaltyPoints.zzaQV, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgC, reason: merged with bridge method [inline-methods] */
    public LoyaltyPoints createFromParcel(Parcel parcel) {
        TimeInterval timeInterval = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        LoyaltyPointsBalance loyaltyPointsBalance = null;
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    loyaltyPointsBalance = (LoyaltyPointsBalance) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LoyaltyPointsBalance.CREATOR);
                    break;
                case 4:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    timeInterval = (TimeInterval) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, TimeInterval.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new LoyaltyPoints(iZzg, strZzo2, loyaltyPointsBalance, strZzo, timeInterval);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjH, reason: merged with bridge method [inline-methods] */
    public LoyaltyPoints[] newArray(int i) {
        return new LoyaltyPoints[i];
    }
}
