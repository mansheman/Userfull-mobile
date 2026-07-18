package com.google.android.gms.drive.realtime.internal.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<ReferenceShiftedDetails> {
    static void zza(ReferenceShiftedDetails referenceShiftedDetails, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, referenceShiftedDetails.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, referenceShiftedDetails.zzaiT, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, referenceShiftedDetails.zzaiU, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, referenceShiftedDetails.zzaiV);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, referenceShiftedDetails.zzaiW);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcb, reason: merged with bridge method [inline-methods] */
    public ReferenceShiftedDetails createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg2 = 0;
        String strZzo2 = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
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
        return new ReferenceShiftedDetails(iZzg3, strZzo2, strZzo, iZzg2, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdR, reason: merged with bridge method [inline-methods] */
    public ReferenceShiftedDetails[] newArray(int i) {
        return new ReferenceShiftedDetails[i];
    }
}
