package com.google.android.gms.drive.realtime.internal.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzi implements Parcelable.Creator<ValuesAddedDetails> {
    static void zza(ValuesAddedDetails valuesAddedDetails, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, valuesAddedDetails.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, valuesAddedDetails.mIndex);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, valuesAddedDetails.zzaiA);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, valuesAddedDetails.zzaiB);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, valuesAddedDetails.zzaiY, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, valuesAddedDetails.zzaiZ);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcf, reason: merged with bridge method [inline-methods] */
    public ValuesAddedDetails createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        int iZzg4 = 0;
        int iZzg5 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
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
        return new ValuesAddedDetails(iZzg5, iZzg4, iZzg3, iZzg2, strZzo, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdV, reason: merged with bridge method [inline-methods] */
    public ValuesAddedDetails[] newArray(int i) {
        return new ValuesAddedDetails[i];
    }
}
