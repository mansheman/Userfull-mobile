package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzi implements Parcelable.Creator<RegisterSectionInfo> {
    static void zza(RegisterSectionInfo registerSectionInfo, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, registerSectionInfo.name, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, registerSectionInfo.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, registerSectionInfo.zzNs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, registerSectionInfo.zzNt);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, registerSectionInfo.weight);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, registerSectionInfo.zzNu);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, registerSectionInfo.zzNv, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable[]) registerSectionInfo.zzNw, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, registerSectionInfo.zzNx, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, registerSectionInfo.zzNy, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzak, reason: merged with bridge method [inline-methods] */
    public RegisterSectionInfo[] newArray(int i) {
        return new RegisterSectionInfo[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzx, reason: merged with bridge method [inline-methods] */
    public RegisterSectionInfo createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 1;
        int[] iArrZzu = null;
        Feature[] featureArr = null;
        String strZzo2 = null;
        boolean zZzc2 = false;
        String strZzo3 = null;
        String strZzo4 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    featureArr = (Feature[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, Feature.CREATOR);
                    break;
                case 8:
                    iArrZzu = com.google.android.gms.common.internal.safeparcel.zza.zzu(parcel, iZzaa);
                    break;
                case 11:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new RegisterSectionInfo(iZzg2, strZzo4, strZzo3, zZzc2, iZzg, zZzc, strZzo2, featureArr, iArrZzu, strZzo);
    }
}
