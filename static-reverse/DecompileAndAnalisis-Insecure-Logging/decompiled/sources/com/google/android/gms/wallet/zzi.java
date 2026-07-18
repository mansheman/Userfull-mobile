package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzi implements Parcelable.Creator<LineItem> {
    static void zza(LineItem lineItem, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, lineItem.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, lineItem.description, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, lineItem.zzaQI, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, lineItem.zzaQJ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, lineItem.zzaQf, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, lineItem.zzaQK);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, lineItem.zzaQg, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgi, reason: merged with bridge method [inline-methods] */
    public LineItem createFromParcel(Parcel parcel) {
        int iZzg = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 7:
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
        return new LineItem(iZzg2, strZzo5, strZzo4, strZzo3, strZzo2, iZzg, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjk, reason: merged with bridge method [inline-methods] */
    public LineItem[] newArray(int i) {
        return new LineItem[i];
    }
}
