package com.google.android.gms.drive.realtime.internal.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<ParcelableEventList> {
    static void zza(ParcelableEventList parcelableEventList, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, parcelableEventList.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, parcelableEventList.zzoB, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) parcelableEventList.zzaiQ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, parcelableEventList.zzaiR);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 5, parcelableEventList.zzaiS, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzca, reason: merged with bridge method [inline-methods] */
    public ParcelableEventList createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        ArrayList<String> arrayListZzC = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DataHolder dataHolder = null;
        ArrayList arrayListZzc = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, ParcelableEvent.CREATOR);
                    break;
                case 3:
                    dataHolder = (DataHolder) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataHolder.CREATOR);
                    break;
                case 4:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 5:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ParcelableEventList(iZzg, arrayListZzc, dataHolder, zZzc, arrayListZzC);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdQ, reason: merged with bridge method [inline-methods] */
    public ParcelableEventList[] newArray(int i) {
        return new ParcelableEventList[i];
    }
}
