package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<AdSizeParcel> {
    static void zza(AdSizeParcel adSizeParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adSizeParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, adSizeParcel.zzsm, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, adSizeParcel.height);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, adSizeParcel.heightPixels);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, adSizeParcel.zzsn);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, adSizeParcel.width);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, adSizeParcel.widthPixels);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable[]) adSizeParcel.zzso, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adSizeParcel.zzsp);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public AdSizeParcel createFromParcel(Parcel parcel) {
        AdSizeParcel[] adSizeParcelArr = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        int iZzg2 = 0;
        boolean zZzc2 = false;
        int iZzg3 = 0;
        int iZzg4 = 0;
        String strZzo = null;
        int iZzg5 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 7:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    adSizeParcelArr = (AdSizeParcel[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, AdSizeParcel.CREATOR);
                    break;
                case 9:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AdSizeParcel(iZzg5, strZzo, iZzg4, iZzg3, zZzc2, iZzg2, iZzg, adSizeParcelArr, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzl, reason: merged with bridge method [inline-methods] */
    public AdSizeParcel[] newArray(int i) {
        return new AdSizeParcel[i];
    }
}
