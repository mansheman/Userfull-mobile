package com.google.android.gms.ads.internal.request;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<AdRequestInfoParcel> {
    static void zza(AdRequestInfoParcel adRequestInfoParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adRequestInfoParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, adRequestInfoParcel.zzCl, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) adRequestInfoParcel.zzCm, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) adRequestInfoParcel.zzpN, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, adRequestInfoParcel.zzpG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) adRequestInfoParcel.applicationInfo, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable) adRequestInfoParcel.zzCn, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, adRequestInfoParcel.zzCo, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adRequestInfoParcel.zzCp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, adRequestInfoParcel.zzCq, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, (Parcelable) adRequestInfoParcel.zzpJ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, adRequestInfoParcel.zzCr, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 13, adRequestInfoParcel.zzCs);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 14, adRequestInfoParcel.zzqd, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, adRequestInfoParcel.zzCt, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, (Parcelable) adRequestInfoParcel.zzCv, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, adRequestInfoParcel.zzCu);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 19, adRequestInfoParcel.zzCx);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 18, adRequestInfoParcel.zzCw);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 21, adRequestInfoParcel.zzCz, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 20, adRequestInfoParcel.zzCy);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 23, adRequestInfoParcel.zzCB);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 22, adRequestInfoParcel.zzCA);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 25, adRequestInfoParcel.zzCD);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 24, adRequestInfoParcel.zzCC, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 27, adRequestInfoParcel.zzCF, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 26, adRequestInfoParcel.zzCE, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 29, (Parcelable) adRequestInfoParcel.zzqb, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 28, adRequestInfoParcel.zzpF, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 30, adRequestInfoParcel.zzCG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzC, reason: merged with bridge method [inline-methods] */
    public AdRequestInfoParcel[] newArray(int i) {
        return new AdRequestInfoParcel[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzj, reason: merged with bridge method [inline-methods] */
    public AdRequestInfoParcel createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        Bundle bundleZzq = null;
        AdRequestParcel adRequestParcel = null;
        AdSizeParcel adSizeParcel = null;
        String strZzo = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        VersionInfoParcel versionInfoParcel = null;
        Bundle bundleZzq2 = null;
        int iZzg2 = 0;
        ArrayList<String> arrayListZzC = null;
        Bundle bundleZzq3 = null;
        boolean zZzc = false;
        Messenger messenger = null;
        int iZzg3 = 0;
        int iZzg4 = 0;
        float fZzl = 0.0f;
        String strZzo5 = null;
        boolean zZzc2 = false;
        int iZzg5 = 0;
        String strZzo6 = null;
        long jZzi = 0;
        String strZzo7 = null;
        ArrayList<String> arrayListZzC2 = null;
        String strZzo8 = null;
        NativeAdOptionsParcel nativeAdOptionsParcel = null;
        ArrayList<String> arrayListZzC3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 3:
                    adRequestParcel = (AdRequestParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, AdRequestParcel.CREATOR);
                    break;
                case 4:
                    adSizeParcel = (AdSizeParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, AdSizeParcel.CREATOR);
                    break;
                case 5:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    applicationInfo = (ApplicationInfo) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ApplicationInfo.CREATOR);
                    break;
                case 7:
                    packageInfo = (PackageInfo) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PackageInfo.CREATOR);
                    break;
                case 8:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 10:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 11:
                    versionInfoParcel = (VersionInfoParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, VersionInfoParcel.CREATOR);
                    break;
                case 12:
                    bundleZzq2 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 13:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 14:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 15:
                    bundleZzq3 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 16:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 17:
                    messenger = (Messenger) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Messenger.CREATOR);
                    break;
                case 18:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 19:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 20:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 21:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 22:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 23:
                    iZzg5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 24:
                    strZzo6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 25:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 26:
                    strZzo7 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 27:
                    arrayListZzC2 = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 28:
                    strZzo8 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 29:
                    nativeAdOptionsParcel = (NativeAdOptionsParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, NativeAdOptionsParcel.CREATOR);
                    break;
                case 30:
                    arrayListZzC3 = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AdRequestInfoParcel(iZzg, bundleZzq, adRequestParcel, adSizeParcel, strZzo, applicationInfo, packageInfo, strZzo2, strZzo3, strZzo4, versionInfoParcel, bundleZzq2, iZzg2, arrayListZzC, bundleZzq3, zZzc, messenger, iZzg3, iZzg4, fZzl, strZzo5, zZzc2, iZzg5, strZzo6, jZzi, strZzo7, arrayListZzC2, strZzo8, nativeAdOptionsParcel, arrayListZzC3);
    }
}
