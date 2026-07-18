package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class zzl implements Parcelable.Creator<MaskedWalletRequest> {
    static void zza(MaskedWalletRequest maskedWalletRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, maskedWalletRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, maskedWalletRequest.zzaQn, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, maskedWalletRequest.zzaRi);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, maskedWalletRequest.zzaRj);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, maskedWalletRequest.zzaRk);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, maskedWalletRequest.zzaRl, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, maskedWalletRequest.zzaQg, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, maskedWalletRequest.zzaRm, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, (Parcelable) maskedWalletRequest.zzaQx, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, maskedWalletRequest.zzaRn);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, maskedWalletRequest.zzaRo);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, (Parcelable[]) maskedWalletRequest.zzaRp, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, maskedWalletRequest.zzaRq);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, maskedWalletRequest.zzaRr);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 15, maskedWalletRequest.zzaRs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, (List<Integer>) maskedWalletRequest.zzaRu, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, (Parcelable) maskedWalletRequest.zzaRt, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgl, reason: merged with bridge method [inline-methods] */
    public MaskedWalletRequest createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        boolean zZzc = false;
        boolean zZzc2 = false;
        boolean zZzc3 = false;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        Cart cart = null;
        boolean zZzc4 = false;
        boolean zZzc5 = false;
        CountrySpecification[] countrySpecificationArr = null;
        boolean zZzc6 = true;
        boolean zZzc7 = true;
        ArrayList arrayListZzc = null;
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = null;
        ArrayList<Integer> arrayListZzB = null;
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
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 5:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    cart = (Cart) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Cart.CREATOR);
                    break;
                case 10:
                    zZzc4 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 11:
                    zZzc5 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 12:
                    countrySpecificationArr = (CountrySpecification[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, CountrySpecification.CREATOR);
                    break;
                case 13:
                    zZzc6 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 14:
                    zZzc7 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 15:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, com.google.android.gms.identity.intents.model.CountrySpecification.CREATOR);
                    break;
                case 16:
                    paymentMethodTokenizationParameters = (PaymentMethodTokenizationParameters) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PaymentMethodTokenizationParameters.CREATOR);
                    break;
                case 17:
                    arrayListZzB = com.google.android.gms.common.internal.safeparcel.zza.zzB(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new MaskedWalletRequest(iZzg, strZzo, zZzc, zZzc2, zZzc3, strZzo2, strZzo3, strZzo4, cart, zZzc4, zZzc5, countrySpecificationArr, zZzc6, zZzc7, arrayListZzc, paymentMethodTokenizationParameters, arrayListZzB);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjn, reason: merged with bridge method [inline-methods] */
    public MaskedWalletRequest[] newArray(int i) {
        return new MaskedWalletRequest[i];
    }
}
