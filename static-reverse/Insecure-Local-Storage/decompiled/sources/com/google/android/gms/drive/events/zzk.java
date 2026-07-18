package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<QueryResultEventParcelable> {
    static void zza(QueryResultEventParcelable queryResultEventParcelable, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, queryResultEventParcelable.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) queryResultEventParcelable.zzWu, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, queryResultEventParcelable.zzaei);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, queryResultEventParcelable.zzaej);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaA, reason: merged with bridge method [inline-methods] */
    public QueryResultEventParcelable createFromParcel(Parcel parcel) {
        int iZzg;
        boolean zZzc;
        DataHolder dataHolder;
        int iZzg2;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DataHolder dataHolder2 = null;
        boolean z = false;
        int i2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    int i3 = i;
                    zZzc = z;
                    dataHolder = dataHolder2;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i3;
                    break;
                case 2:
                    iZzg2 = i2;
                    boolean z2 = z;
                    dataHolder = (DataHolder) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataHolder.CREATOR);
                    iZzg = i;
                    zZzc = z2;
                    break;
                case 3:
                    dataHolder = dataHolder2;
                    iZzg2 = i2;
                    int i4 = i;
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    iZzg = i4;
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    zZzc = z;
                    dataHolder = dataHolder2;
                    iZzg2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iZzg = i;
                    zZzc = z;
                    dataHolder = dataHolder2;
                    iZzg2 = i2;
                    break;
            }
            i2 = iZzg2;
            dataHolder2 = dataHolder;
            z = zZzc;
            i = iZzg;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new QueryResultEventParcelable(i2, dataHolder2, z, i);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcj, reason: merged with bridge method [inline-methods] */
    public QueryResultEventParcelable[] newArray(int i) {
        return new QueryResultEventParcelable[i];
    }
}
