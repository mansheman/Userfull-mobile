package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<ComparisonFilter> {
    static void zza(ComparisonFilter comparisonFilter, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, comparisonFilter.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) comparisonFilter.zzahQ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) comparisonFilter.zzahR, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbH, reason: merged with bridge method [inline-methods] */
    public ComparisonFilter createFromParcel(Parcel parcel) {
        MetadataBundle metadataBundle;
        Operator operator;
        int iZzg;
        MetadataBundle metadataBundle2 = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Operator operator2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Operator operator3 = (Operator) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Operator.CREATOR);
                    iZzg = i;
                    metadataBundle = metadataBundle2;
                    operator = operator3;
                    break;
                case 2:
                    metadataBundle = (MetadataBundle) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, MetadataBundle.CREATOR);
                    operator = operator2;
                    iZzg = i;
                    break;
                case 1000:
                    MetadataBundle metadataBundle3 = metadataBundle2;
                    operator = operator2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    metadataBundle = metadataBundle3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    metadataBundle = metadataBundle2;
                    operator = operator2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            operator2 = operator;
            metadataBundle2 = metadataBundle;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ComparisonFilter(i, operator2, metadataBundle2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdw, reason: merged with bridge method [inline-methods] */
    public ComparisonFilter[] newArray(int i) {
        return new ComparisonFilter[i];
    }
}
