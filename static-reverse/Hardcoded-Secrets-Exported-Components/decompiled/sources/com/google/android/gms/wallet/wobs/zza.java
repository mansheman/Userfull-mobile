package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzkx;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<CommonWalletObject> {
    static void zza(CommonWalletObject commonWalletObject, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, commonWalletObject.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, commonWalletObject.zzhI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, commonWalletObject.zzaQT, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, commonWalletObject.name, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, commonWalletObject.zzaQN, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, commonWalletObject.zzaQP, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, commonWalletObject.zzaQQ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, commonWalletObject.zzaQR, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, commonWalletObject.zzaQS, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 10, commonWalletObject.state);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 11, commonWalletObject.zzaQU, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, (Parcelable) commonWalletObject.zzaQV, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 13, commonWalletObject.zzaQW, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, commonWalletObject.zzaQX, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, commonWalletObject.zzaQY, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, commonWalletObject.zzaRa);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 16, commonWalletObject.zzaQZ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 19, commonWalletObject.zzaRc, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 18, commonWalletObject.zzaRb, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 20, commonWalletObject.zzaRd, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgy, reason: merged with bridge method [inline-methods] */
    public CommonWalletObject createFromParcel(Parcel parcel) {
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
        int iZzg2 = 0;
        ArrayList arrayListZzoP = zzkx.zzoP();
        TimeInterval timeInterval = null;
        ArrayList arrayListZzoP2 = zzkx.zzoP();
        String strZzo9 = null;
        String strZzo10 = null;
        ArrayList arrayListZzoP3 = zzkx.zzoP();
        boolean zZzc = false;
        ArrayList arrayListZzoP4 = zzkx.zzoP();
        ArrayList arrayListZzoP5 = zzkx.zzoP();
        ArrayList arrayListZzoP6 = zzkx.zzoP();
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
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 11:
                    arrayListZzoP = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, WalletObjectMessage.CREATOR);
                    break;
                case 12:
                    timeInterval = (TimeInterval) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, TimeInterval.CREATOR);
                    break;
                case 13:
                    arrayListZzoP2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, LatLng.CREATOR);
                    break;
                case 14:
                    strZzo9 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 15:
                    strZzo10 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 16:
                    arrayListZzoP3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, LabelValueRow.CREATOR);
                    break;
                case 17:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 18:
                    arrayListZzoP4 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, UriData.CREATOR);
                    break;
                case 19:
                    arrayListZzoP5 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, TextModuleData.CREATOR);
                    break;
                case 20:
                    arrayListZzoP6 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, UriData.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CommonWalletObject(iZzg, strZzo, strZzo2, strZzo3, strZzo4, strZzo5, strZzo6, strZzo7, strZzo8, iZzg2, arrayListZzoP, timeInterval, arrayListZzoP2, strZzo9, strZzo10, arrayListZzoP3, zZzc, arrayListZzoP4, arrayListZzoP5, arrayListZzoP6);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjD, reason: merged with bridge method [inline-methods] */
    public CommonWalletObject[] newArray(int i) {
        return new CommonWalletObject[i];
    }
}
