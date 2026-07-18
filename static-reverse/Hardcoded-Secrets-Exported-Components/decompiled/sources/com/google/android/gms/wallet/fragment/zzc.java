package com.google.android.gms.wallet.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<WalletFragmentStyle> {
    static void zza(WalletFragmentStyle walletFragmentStyle, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, walletFragmentStyle.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, walletFragmentStyle.zzaSm, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, walletFragmentStyle.zzaSn);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgx, reason: merged with bridge method [inline-methods] */
    public WalletFragmentStyle createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Bundle bundleZzq = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 3:
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
        return new WalletFragmentStyle(iZzg2, bundleZzq, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjA, reason: merged with bridge method [inline-methods] */
    public WalletFragmentStyle[] newArray(int i) {
        return new WalletFragmentStyle[i];
    }
}
