package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<FilterHolder> {
    static void zza(FilterHolder filterHolder, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) filterHolder.zzahU, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, filterHolder.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) filterHolder.zzahV, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) filterHolder.zzahW, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) filterHolder.zzahX, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) filterHolder.zzahY, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) filterHolder.zzahZ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable) filterHolder.zzaia, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable) filterHolder.zzaib, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, (Parcelable) filterHolder.zzaic, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbK, reason: merged with bridge method [inline-methods] */
    public FilterHolder createFromParcel(Parcel parcel) {
        OwnedByMeFilter ownedByMeFilter = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        FullTextSearchFilter fullTextSearchFilter = null;
        HasFilter hasFilter = null;
        MatchAllFilter matchAllFilter = null;
        InFilter inFilter = null;
        NotFilter notFilter = null;
        LogicalFilter logicalFilter = null;
        FieldOnlyFilter fieldOnlyFilter = null;
        ComparisonFilter comparisonFilter = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    comparisonFilter = (ComparisonFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ComparisonFilter.CREATOR);
                    break;
                case 2:
                    fieldOnlyFilter = (FieldOnlyFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, FieldOnlyFilter.CREATOR);
                    break;
                case 3:
                    logicalFilter = (LogicalFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LogicalFilter.CREATOR);
                    break;
                case 4:
                    notFilter = (NotFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, NotFilter.CREATOR);
                    break;
                case 5:
                    inFilter = (InFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, InFilter.CREATOR);
                    break;
                case 6:
                    matchAllFilter = (MatchAllFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, MatchAllFilter.CREATOR);
                    break;
                case 7:
                    hasFilter = (HasFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, HasFilter.CREATOR);
                    break;
                case 8:
                    fullTextSearchFilter = (FullTextSearchFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, FullTextSearchFilter.CREATOR);
                    break;
                case 9:
                    ownedByMeFilter = (OwnedByMeFilter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, OwnedByMeFilter.CREATOR);
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
        return new FilterHolder(iZzg, comparisonFilter, fieldOnlyFilter, logicalFilter, notFilter, inFilter, matchAllFilter, hasFilter, fullTextSearchFilter, ownedByMeFilter);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdz, reason: merged with bridge method [inline-methods] */
    public FilterHolder[] newArray(int i) {
        return new FilterHolder[i];
    }
}
