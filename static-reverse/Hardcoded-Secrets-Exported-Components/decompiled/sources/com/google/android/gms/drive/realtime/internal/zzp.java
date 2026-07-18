package com.google.android.gms.drive.realtime.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzp implements Parcelable.Creator<ParcelableCollaborator> {
    static void zza(ParcelableCollaborator parcelableCollaborator, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, parcelableCollaborator.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, parcelableCollaborator.zzais);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, parcelableCollaborator.zzait);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, parcelableCollaborator.zzFE, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, parcelableCollaborator.zzEO, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, parcelableCollaborator.zzadI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, parcelableCollaborator.zzaiu, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, parcelableCollaborator.zzaiv, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbV, reason: merged with bridge method [inline-methods] */
    public ParcelableCollaborator createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        boolean zZzc2 = false;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ParcelableCollaborator(iZzg, zZzc2, zZzc, strZzo5, strZzo4, strZzo3, strZzo2, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdL, reason: merged with bridge method [inline-methods] */
    public ParcelableCollaborator[] newArray(int i) {
        return new ParcelableCollaborator[i];
    }
}
