package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzbb implements Parcelable.Creator<OnListEntriesResponse> {
    static void zza(OnListEntriesResponse onListEntriesResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, onListEntriesResponse.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) onListEntriesResponse.zzagp, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, onListEntriesResponse.zzaeL);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbg, reason: merged with bridge method [inline-methods] */
    public OnListEntriesResponse createFromParcel(Parcel parcel) {
        boolean zZzc;
        DataHolder dataHolder;
        int iZzg;
        boolean z = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DataHolder dataHolder2 = null;
        int i = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    boolean z2 = z;
                    dataHolder = dataHolder2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    zZzc = z2;
                    break;
                case 2:
                    DataHolder dataHolder3 = (DataHolder) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataHolder.CREATOR);
                    iZzg = i;
                    zZzc = z;
                    dataHolder = dataHolder3;
                    break;
                case 3:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    dataHolder = dataHolder2;
                    iZzg = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    zZzc = z;
                    dataHolder = dataHolder2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            dataHolder2 = dataHolder;
            z = zZzc;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new OnListEntriesResponse(i, dataHolder2, z);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcV, reason: merged with bridge method [inline-methods] */
    public OnListEntriesResponse[] newArray(int i) {
        return new OnListEntriesResponse[i];
    }
}
