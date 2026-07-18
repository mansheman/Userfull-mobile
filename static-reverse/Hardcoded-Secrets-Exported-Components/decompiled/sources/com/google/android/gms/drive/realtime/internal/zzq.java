package com.google.android.gms.drive.realtime.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzq implements Parcelable.Creator<ParcelableIndexReference> {
    static void zza(ParcelableIndexReference parcelableIndexReference, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, parcelableIndexReference.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, parcelableIndexReference.zzaiw, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, parcelableIndexReference.mIndex);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, parcelableIndexReference.zzaix);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, parcelableIndexReference.zzaiy);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbW, reason: merged with bridge method [inline-methods] */
    public ParcelableIndexReference createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo = null;
        int iZzg = -1;
        int iZzg2 = 0;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
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
        return new ParcelableIndexReference(iZzg3, strZzo, iZzg2, zZzc, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdM, reason: merged with bridge method [inline-methods] */
    public ParcelableIndexReference[] newArray(int i) {
        return new ParcelableIndexReference[i];
    }
}
