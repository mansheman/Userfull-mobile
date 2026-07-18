package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzkx;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.LoyaltyPoints;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzj implements Parcelable.Creator<LoyaltyWalletObject> {
    static void zza(LoyaltyWalletObject loyaltyWalletObject, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, loyaltyWalletObject.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, loyaltyWalletObject.zzhI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, loyaltyWalletObject.zzaQM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, loyaltyWalletObject.zzaQN, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, loyaltyWalletObject.zzaQO, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, loyaltyWalletObject.zzaBb, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, loyaltyWalletObject.zzaQP, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, loyaltyWalletObject.zzaQQ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, loyaltyWalletObject.zzaQR, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, loyaltyWalletObject.zzaQS, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, loyaltyWalletObject.zzaQT, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 12, loyaltyWalletObject.state);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 13, loyaltyWalletObject.zzaQU, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, (Parcelable) loyaltyWalletObject.zzaQV, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 15, loyaltyWalletObject.zzaQW, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, loyaltyWalletObject.zzaQY, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, loyaltyWalletObject.zzaQX, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 19, loyaltyWalletObject.zzaRa);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 18, loyaltyWalletObject.zzaQZ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 21, loyaltyWalletObject.zzaRc, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 20, loyaltyWalletObject.zzaRb, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 23, (Parcelable) loyaltyWalletObject.zzaRe, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 22, loyaltyWalletObject.zzaRd, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgj, reason: merged with bridge method [inline-methods] */
    public LoyaltyWalletObject createFromParcel(Parcel parcel) {
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
        int iZzg2 = 0;
        ArrayList arrayListZzoP = zzkx.zzoP();
        TimeInterval timeInterval = null;
        ArrayList arrayListZzoP2 = zzkx.zzoP();
        String strZzo11 = null;
        String strZzo12 = null;
        ArrayList arrayListZzoP3 = zzkx.zzoP();
        boolean zZzc = false;
        ArrayList arrayListZzoP4 = zzkx.zzoP();
        ArrayList arrayListZzoP5 = zzkx.zzoP();
        ArrayList arrayListZzoP6 = zzkx.zzoP();
        LoyaltyPoints loyaltyPoints = null;
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
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 13:
                    arrayListZzoP = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, WalletObjectMessage.CREATOR);
                    break;
                case 14:
                    timeInterval = (TimeInterval) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, TimeInterval.CREATOR);
                    break;
                case 15:
                    arrayListZzoP2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, LatLng.CREATOR);
                    break;
                case 16:
                    strZzo11 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 17:
                    strZzo12 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 18:
                    arrayListZzoP3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, LabelValueRow.CREATOR);
                    break;
                case 19:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 20:
                    arrayListZzoP4 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, UriData.CREATOR);
                    break;
                case 21:
                    arrayListZzoP5 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, TextModuleData.CREATOR);
                    break;
                case 22:
                    arrayListZzoP6 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, UriData.CREATOR);
                    break;
                case 23:
                    loyaltyPoints = (LoyaltyPoints) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LoyaltyPoints.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new LoyaltyWalletObject(iZzg, strZzo, strZzo2, strZzo3, strZzo4, strZzo5, strZzo6, strZzo7, strZzo8, strZzo9, strZzo10, iZzg2, arrayListZzoP, timeInterval, arrayListZzoP2, strZzo11, strZzo12, arrayListZzoP3, zZzc, arrayListZzoP4, arrayListZzoP5, arrayListZzoP6, loyaltyPoints);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjl, reason: merged with bridge method [inline-methods] */
    public LoyaltyWalletObject[] newArray(int i) {
        return new LoyaltyWalletObject[i];
    }
}
