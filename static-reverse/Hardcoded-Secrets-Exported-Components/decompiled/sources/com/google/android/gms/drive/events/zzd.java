package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveSpace;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<ChangesAvailableOptions> {
    static void zza(ChangesAvailableOptions changesAvailableOptions, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, changesAvailableOptions.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, changesAvailableOptions.zzadP);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, changesAvailableOptions.zzadQ);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, changesAvailableOptions.zzadR, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzax, reason: merged with bridge method [inline-methods] */
    public ChangesAvailableOptions createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList arrayListZzc = null;
        int iZzg = 0;
        int iZzg2 = 0;
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
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DriveSpace.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ChangesAvailableOptions(iZzg2, iZzg, zZzc, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcg, reason: merged with bridge method [inline-methods] */
    public ChangesAvailableOptions[] newArray(int i) {
        return new ChangesAvailableOptions[i];
    }
}
