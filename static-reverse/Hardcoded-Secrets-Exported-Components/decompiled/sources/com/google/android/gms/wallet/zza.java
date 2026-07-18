package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<Address> {
    static void zza(Address address, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, address.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, address.name, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, address.zzawA, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, address.zzawB, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, address.zzawC, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, address.zzEr, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, address.zzaQd, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, address.zzaQe, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, address.zzawH, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, address.zzawJ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, address.zzawK);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, address.zzawL, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzga, reason: merged with bridge method [inline-methods] */
    public Address createFromParcel(Parcel parcel) {
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
        boolean zZzc = false;
        String strZzo10 = null;
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
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 12:
                    strZzo10 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new Address(iZzg, strZzo, strZzo2, strZzo3, strZzo4, strZzo5, strZzo6, strZzo7, strZzo8, strZzo9, zZzc, strZzo10);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjc, reason: merged with bridge method [inline-methods] */
    public Address[] newArray(int i) {
        return new Address[i];
    }
}
