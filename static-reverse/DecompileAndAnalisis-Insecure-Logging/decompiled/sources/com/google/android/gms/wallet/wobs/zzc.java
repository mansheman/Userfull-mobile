package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzkx;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<LabelValueRow> {
    static void zza(LabelValueRow labelValueRow, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, labelValueRow.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, labelValueRow.zzaSx, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, labelValueRow.zzaSy, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, labelValueRow.zzaSz, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgA, reason: merged with bridge method [inline-methods] */
    public LabelValueRow createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayListZzoP = zzkx.zzoP();
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzoP = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, LabelValue.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new LabelValueRow(iZzg, strZzo2, strZzo, arrayListZzoP);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjF, reason: merged with bridge method [inline-methods] */
    public LabelValueRow[] newArray(int i) {
        return new LabelValueRow[i];
    }
}
