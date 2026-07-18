package com.google.android.gms.drive.realtime.internal.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzj implements Parcelable.Creator<ValuesRemovedDetails> {
    static void zza(ValuesRemovedDetails valuesRemovedDetails, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, valuesRemovedDetails.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, valuesRemovedDetails.mIndex);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, valuesRemovedDetails.zzaiA);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, valuesRemovedDetails.zzaiB);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, valuesRemovedDetails.zzaja, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, valuesRemovedDetails.zzajb);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcg, reason: merged with bridge method [inline-methods] */
    public ValuesRemovedDetails createFromParcel(Parcel parcel) {
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
        return new ValuesRemovedDetails(iZzg5, iZzg4, iZzg3, iZzg2, strZzo, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdW, reason: merged with bridge method [inline-methods] */
    public ValuesRemovedDetails[] newArray(int i) {
        return new ValuesRemovedDetails[i];
    }
}
