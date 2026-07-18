package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<LogicalFilter> {
    static void zza(LogicalFilter logicalFilter, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, logicalFilter.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) logicalFilter.zzahQ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, logicalFilter.zzaif, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbO, reason: merged with bridge method [inline-methods] */
    public LogicalFilter createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc;
        Operator operator;
        int iZzg;
        ArrayList arrayList = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Operator operator2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Operator operator3 = (Operator) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Operator.CREATOR);
                    iZzg = i;
                    arrayListZzc = arrayList;
                    operator = operator3;
                    break;
                case 2:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, FilterHolder.CREATOR);
                    operator = operator2;
                    iZzg = i;
                    break;
                case 1000:
                    ArrayList arrayList2 = arrayList;
                    operator = operator2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    arrayListZzc = arrayList2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    arrayListZzc = arrayList;
                    operator = operator2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            operator2 = operator;
            arrayList = arrayListZzc;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new LogicalFilter(i, operator2, arrayList);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdD, reason: merged with bridge method [inline-methods] */
    public LogicalFilter[] newArray(int i) {
        return new LogicalFilter[i];
    }
}
