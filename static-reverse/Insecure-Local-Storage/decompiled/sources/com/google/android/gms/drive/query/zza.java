package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<Query> {
    static void zza(Query query, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, query.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) query.zzahG, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, query.zzahH, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) query.zzahI, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 5, query.zzahJ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, query.zzahK);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, query.zzadR, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbF, reason: merged with bridge method [inline-methods] */
    public Query createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList<String> arrayListZzC = null;
        SortOrder sortOrder = null;
        String strZzo = null;
        LogicalFilter logicalFilter = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    logicalFilter = (LogicalFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LogicalFilter.CREATOR);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    sortOrder = (SortOrder) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, SortOrder.CREATOR);
                    break;
                case 5:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 6:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 7:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DriveSpace.CREATOR);
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
        return new Query(iZzg, logicalFilter, strZzo, sortOrder, arrayListZzC, zZzc, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdu, reason: merged with bridge method [inline-methods] */
    public Query[] newArray(int i) {
        return new Query[i];
    }
}
