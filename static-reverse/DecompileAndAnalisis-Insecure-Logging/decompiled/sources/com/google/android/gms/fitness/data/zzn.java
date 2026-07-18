package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzn implements Parcelable.Creator<RawDataPoint> {
    static void zza(RawDataPoint rawDataPoint, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, rawDataPoint.zzajV);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, rawDataPoint.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, rawDataPoint.zzajW);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable[]) rawDataPoint.zzajX, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, rawDataPoint.zzakH);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, rawDataPoint.zzakI);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, rawDataPoint.zzajZ);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, rawDataPoint.zzaka);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzct, reason: merged with bridge method [inline-methods] */
    public RawDataPoint createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        long jZzi = 0;
        long jZzi2 = 0;
        Value[] valueArr = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        long jZzi3 = 0;
        long jZzi4 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 2:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    valueArr = (Value[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, Value.CREATOR);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    jZzi3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 7:
                    jZzi4 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
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
        return new RawDataPoint(iZzg, jZzi, jZzi2, valueArr, iZzg2, iZzg3, jZzi3, jZzi4);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzem, reason: merged with bridge method [inline-methods] */
    public RawDataPoint[] newArray(int i) {
        return new RawDataPoint[i];
    }
}
