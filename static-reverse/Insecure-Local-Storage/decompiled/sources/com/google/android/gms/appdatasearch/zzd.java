package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<DocumentSection> {
    static void zza(DocumentSection documentSection, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, documentSection.zzNe, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, documentSection.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) documentSection.zzNf, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, documentSection.zzNg);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, documentSection.zzNh, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzae, reason: merged with bridge method [inline-methods] */
    public DocumentSection[] newArray(int i) {
        return new DocumentSection[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzt, reason: merged with bridge method [inline-methods] */
    public DocumentSection createFromParcel(Parcel parcel) {
        byte[] bArrZzr = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        int iZzg2 = -1;
        RegisterSectionInfo registerSectionInfo = null;
        String strZzo = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    registerSectionInfo = (RegisterSectionInfo) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, RegisterSectionInfo.CREATOR);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    bArrZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DocumentSection(iZzg, strZzo, registerSectionInfo, iZzg2, bArrZzr);
    }
}
