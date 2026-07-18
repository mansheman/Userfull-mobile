package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<DataHolder> {
    static void zza(DataHolder dataHolder, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, dataHolder.zzng(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataHolder.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable[]) dataHolder.zznh(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, dataHolder.getStatusCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, dataHolder.zznb(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzS, reason: merged with bridge method [inline-methods] */
    public DataHolder createFromParcel(Parcel parcel) {
        int iZzg = 0;
        Bundle bundleZzq = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        CursorWindow[] cursorWindowArr = null;
        String[] strArrZzA = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strArrZzA = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
                    break;
                case 2:
                    cursorWindowArr = (CursorWindow[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, CursorWindow.CREATOR);
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        DataHolder dataHolder = new DataHolder(iZzg2, strArrZzA, cursorWindowArr, iZzg, bundleZzq);
        dataHolder.zznf();
        return dataHolder;
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbj, reason: merged with bridge method [inline-methods] */
    public DataHolder[] newArray(int i) {
        return new DataHolder[i];
    }
}
