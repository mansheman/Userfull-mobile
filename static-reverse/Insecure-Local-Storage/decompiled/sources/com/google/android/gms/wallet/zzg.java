package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

/* loaded from: classes.dex */
public class zzg implements Parcelable.Creator<GiftCardWalletObject> {
    static void zza(GiftCardWalletObject giftCardWalletObject, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, giftCardWalletObject.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) giftCardWalletObject.zzaQz, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, giftCardWalletObject.zzaQA, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, giftCardWalletObject.pin, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, giftCardWalletObject.zzaQB, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, giftCardWalletObject.zzaQC);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, giftCardWalletObject.zzaQD, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, giftCardWalletObject.zzaQE);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, giftCardWalletObject.zzaQF, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgg, reason: merged with bridge method [inline-methods] */
    public GiftCardWalletObject createFromParcel(Parcel parcel) {
        long jZzi = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        long jZzi2 = 0;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        CommonWalletObject commonWalletObject = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    commonWalletObject = (CommonWalletObject) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, CommonWalletObject.CREATOR);
                    break;
                case 3:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 7:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 9:
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
        return new GiftCardWalletObject(iZzg, commonWalletObject, strZzo5, strZzo4, strZzo3, jZzi2, strZzo2, jZzi, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzji, reason: merged with bridge method [inline-methods] */
    public GiftCardWalletObject[] newArray(int i) {
        return new GiftCardWalletObject[i];
    }
}
