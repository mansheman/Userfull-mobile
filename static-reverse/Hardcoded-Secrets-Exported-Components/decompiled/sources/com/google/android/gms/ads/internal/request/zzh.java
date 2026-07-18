package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<AdResponseParcel> {
    static void zza(AdResponseParcel adResponseParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adResponseParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, adResponseParcel.zzzG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, adResponseParcel.zzCI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 4, adResponseParcel.zzxF, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, adResponseParcel.errorCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 6, adResponseParcel.zzxG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, adResponseParcel.zzCJ);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, adResponseParcel.zzCK);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adResponseParcel.zzCL);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 10, adResponseParcel.zzCM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, adResponseParcel.zzxJ);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 12, adResponseParcel.orientation);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, adResponseParcel.zzCN, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, adResponseParcel.zzCO);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, adResponseParcel.zzCP, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 19, adResponseParcel.zzCR, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 18, adResponseParcel.zzCQ);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 21, adResponseParcel.zzCS, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 23, adResponseParcel.zzsp);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 22, adResponseParcel.zzCT);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 25, adResponseParcel.zzCU);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 24, adResponseParcel.zzCu);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 27, adResponseParcel.zzCW);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 26, adResponseParcel.zzCV);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 28, (Parcelable) adResponseParcel.zzCX, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzD, reason: merged with bridge method [inline-methods] */
    public AdResponseParcel[] newArray(int i) {
        return new AdResponseParcel[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzk, reason: merged with bridge method [inline-methods] */
    public AdResponseParcel createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        ArrayList<String> arrayListZzC = null;
        int iZzg2 = 0;
        ArrayList<String> arrayListZzC2 = null;
        long jZzi = 0;
        boolean zZzc = false;
        long jZzi2 = 0;
        ArrayList<String> arrayListZzC3 = null;
        long jZzi3 = 0;
        int iZzg3 = 0;
        String strZzo3 = null;
        long jZzi4 = 0;
        String strZzo4 = null;
        boolean zZzc2 = false;
        String strZzo5 = null;
        String strZzo6 = null;
        boolean zZzc3 = false;
        boolean zZzc4 = false;
        boolean zZzc5 = false;
        boolean zZzc6 = false;
        boolean zZzc7 = false;
        int iZzg4 = 0;
        LargeParcelTeleporter largeParcelTeleporter = null;
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
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 5:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    arrayListZzC2 = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 7:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 8:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 9:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 10:
                    arrayListZzC3 = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 11:
                    jZzi3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 12:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 13:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 14:
                    jZzi4 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 15:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 16:
                case 17:
                case 20:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
                case 18:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 19:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 21:
                    strZzo6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 22:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 23:
                    zZzc4 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 24:
                    zZzc5 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 25:
                    zZzc6 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 26:
                    zZzc7 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 27:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 28:
                    largeParcelTeleporter = (LargeParcelTeleporter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LargeParcelTeleporter.CREATOR);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AdResponseParcel(iZzg, strZzo, strZzo2, arrayListZzC, iZzg2, arrayListZzC2, jZzi, zZzc, jZzi2, arrayListZzC3, jZzi3, iZzg3, strZzo3, jZzi4, strZzo4, zZzc2, strZzo5, strZzo6, zZzc3, zZzc4, zZzc5, zZzc6, zZzc7, iZzg4, largeParcelTeleporter);
    }
}
