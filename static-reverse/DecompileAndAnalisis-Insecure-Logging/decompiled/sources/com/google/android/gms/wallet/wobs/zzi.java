package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzi implements Parcelable.Creator<WalletObjectMessage> {
    static void zza(WalletObjectMessage walletObjectMessage, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, walletObjectMessage.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, walletObjectMessage.zzaSG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, walletObjectMessage.zzCI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) walletObjectMessage.zzaSJ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) walletObjectMessage.zzaSK, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) walletObjectMessage.zzaSL, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgG, reason: merged with bridge method [inline-methods] */
    public WalletObjectMessage createFromParcel(Parcel parcel) {
        UriData uriData = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        UriData uriData2 = null;
        TimeInterval timeInterval = null;
        String strZzo = null;
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
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    timeInterval = (TimeInterval) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, TimeInterval.CREATOR);
                    break;
                case 5:
                    uriData2 = (UriData) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, UriData.CREATOR);
                    break;
                case 6:
                    uriData = (UriData) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, UriData.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new WalletObjectMessage(iZzg, strZzo2, strZzo, timeInterval, uriData2, uriData);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjL, reason: merged with bridge method [inline-methods] */
    public WalletObjectMessage[] newArray(int i) {
        return new WalletObjectMessage[i];
    }
}
