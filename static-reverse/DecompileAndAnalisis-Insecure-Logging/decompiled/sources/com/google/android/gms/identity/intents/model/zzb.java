package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<UserAddress> {
    static void zza(UserAddress userAddress, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, userAddress.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, userAddress.name, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, userAddress.zzawA, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, userAddress.zzawB, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, userAddress.zzawC, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, userAddress.zzawD, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, userAddress.zzawE, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, userAddress.zzawF, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, userAddress.zzawG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, userAddress.zzEr, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, userAddress.zzawH, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, userAddress.zzawI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, userAddress.zzawJ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, userAddress.zzawK);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, userAddress.zzawL, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, userAddress.zzawM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzea, reason: merged with bridge method [inline-methods] */
    public UserAddress createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        String strZzo6 = null;
        String strZzo7 = null;
        String strZzo8 = null;
        String strZzo9 = null;
        String strZzo10 = null;
        String strZzo11 = null;
        String strZzo12 = null;
        boolean zZzc = false;
        String strZzo13 = null;
        String strZzo14 = null;
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
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    strZzo7 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    strZzo8 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 10:
                    strZzo9 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 11:
                    strZzo10 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 12:
                    strZzo11 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 13:
                    strZzo12 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 14:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 15:
                    strZzo13 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 16:
                    strZzo14 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new UserAddress(iZzg, strZzo, strZzo2, strZzo3, strZzo4, strZzo5, strZzo6, strZzo7, strZzo8, strZzo9, strZzo10, strZzo11, strZzo12, zZzc, strZzo13, strZzo14);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgm, reason: merged with bridge method [inline-methods] */
    public UserAddress[] newArray(int i) {
        return new UserAddress[i];
    }
}
