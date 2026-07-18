package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.identity.intents.model.UserAddress;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<FullWallet> {
    static void zza(FullWallet fullWallet, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, fullWallet.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, fullWallet.zzaQm, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, fullWallet.zzaQn, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) fullWallet.zzaQo, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, fullWallet.zzaQp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) fullWallet.zzaQq, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable) fullWallet.zzaQr, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, fullWallet.zzaQs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, (Parcelable) fullWallet.zzaQt, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, (Parcelable) fullWallet.zzaQu, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, (Parcelable[]) fullWallet.zzaQv, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, (Parcelable) fullWallet.zzaQw, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzge, reason: merged with bridge method [inline-methods] */
    public FullWallet createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        ProxyCard proxyCard = null;
        String strZzo3 = null;
        Address address = null;
        Address address2 = null;
        String[] strArrZzA = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] instrumentInfoArr = null;
        PaymentMethodToken paymentMethodToken = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    proxyCard = (ProxyCard) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ProxyCard.CREATOR);
                    break;
                case 5:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    address = (Address) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Address.CREATOR);
                    break;
                case 7:
                    address2 = (Address) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Address.CREATOR);
                    break;
                case 8:
                    strArrZzA = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
                    break;
                case 9:
                    userAddress = (UserAddress) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, UserAddress.CREATOR);
                    break;
                case 10:
                    userAddress2 = (UserAddress) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, UserAddress.CREATOR);
                    break;
                case 11:
                    instrumentInfoArr = (InstrumentInfo[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, InstrumentInfo.CREATOR);
                    break;
                case 12:
                    paymentMethodToken = (PaymentMethodToken) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PaymentMethodToken.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new FullWallet(iZzg, strZzo, strZzo2, proxyCard, strZzo3, address, address2, strArrZzA, userAddress, userAddress2, instrumentInfoArr, paymentMethodToken);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjg, reason: merged with bridge method [inline-methods] */
    public FullWallet[] newArray(int i) {
        return new FullWallet[i];
    }
}
