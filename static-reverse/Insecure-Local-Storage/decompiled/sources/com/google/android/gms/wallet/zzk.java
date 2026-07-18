package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.identity.intents.model.UserAddress;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<MaskedWallet> {
    static void zza(MaskedWallet maskedWallet, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, maskedWallet.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, maskedWallet.zzaQm, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, maskedWallet.zzaQn, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, maskedWallet.zzaQs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, maskedWallet.zzaQp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) maskedWallet.zzaQq, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable) maskedWallet.zzaQr, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable[]) maskedWallet.zzaRf, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, (Parcelable[]) maskedWallet.zzaRg, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, (Parcelable) maskedWallet.zzaQt, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, (Parcelable) maskedWallet.zzaQu, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, (Parcelable[]) maskedWallet.zzaQv, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgk, reason: merged with bridge method [inline-methods] */
    public MaskedWallet createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        String[] strArrZzA = null;
        String strZzo3 = null;
        Address address = null;
        Address address2 = null;
        LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
        OfferWalletObject[] offerWalletObjectArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] instrumentInfoArr = null;
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
                    strArrZzA = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
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
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, LoyaltyWalletObject.CREATOR);
                    break;
                case 9:
                    offerWalletObjectArr = (OfferWalletObject[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, OfferWalletObject.CREATOR);
                    break;
                case 10:
                    userAddress = (UserAddress) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, UserAddress.CREATOR);
                    break;
                case 11:
                    userAddress2 = (UserAddress) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, UserAddress.CREATOR);
                    break;
                case 12:
                    instrumentInfoArr = (InstrumentInfo[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, InstrumentInfo.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new MaskedWallet(iZzg, strZzo, strZzo2, strArrZzA, strZzo3, address, address2, loyaltyWalletObjectArr, offerWalletObjectArr, userAddress, userAddress2, instrumentInfoArr);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjm, reason: merged with bridge method [inline-methods] */
    public MaskedWallet[] newArray(int i) {
        return new MaskedWallet[i];
    }
}
