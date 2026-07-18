package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzo implements Parcelable.Creator<RawDataSet> {
    static void zza(RawDataSet rawDataSet, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, rawDataSet.zzakH);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, rawDataSet.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, rawDataSet.zzakJ);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, rawDataSet.zzakK, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, rawDataSet.zzajU);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcu, reason: merged with bridge method [inline-methods] */
    public RawDataSet createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList arrayListZzc = null;
        int iZzg = 0;
        int iZzg2 = 0;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, RawDataPoint.CREATOR);
                    break;
                case 4:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new RawDataSet(iZzg3, iZzg2, iZzg, arrayListZzc, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzen, reason: merged with bridge method [inline-methods] */
    public RawDataSet[] newArray(int i) {
        return new RawDataSet[i];
    }
}
