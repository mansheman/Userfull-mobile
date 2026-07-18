package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzj implements Parcelable.Creator<UsageInfo> {
    static void zza(UsageInfo usageInfo, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) usageInfo.zzNH, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, usageInfo.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, usageInfo.zzNI);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, usageInfo.zzNJ);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, usageInfo.zztt, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) usageInfo.zzNK, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, usageInfo.zzNL);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, usageInfo.zzNM);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, usageInfo.zzNN);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzan, reason: merged with bridge method [inline-methods] */
    public UsageInfo[] newArray(int i) {
        return new UsageInfo[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzy, reason: merged with bridge method [inline-methods] */
    public UsageInfo createFromParcel(Parcel parcel) {
        DocumentContents documentContents = null;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        long jZzi = 0;
        int iZzg2 = -1;
        boolean zZzc = false;
        String strZzo = null;
        int iZzg3 = 0;
        DocumentId documentId = null;
        int iZzg4 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    documentId = (DocumentId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DocumentId.CREATOR);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    documentContents = (DocumentContents) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DocumentContents.CREATOR);
                    break;
                case 6:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 7:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new UsageInfo(iZzg4, documentId, jZzi, iZzg3, strZzo, documentContents, zZzc, iZzg2, iZzg);
    }
}
