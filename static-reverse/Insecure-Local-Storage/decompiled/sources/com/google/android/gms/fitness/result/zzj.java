package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.SessionDataSet;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzj implements Parcelable.Creator<SessionReadResult> {
    static void zza(SessionReadResult sessionReadResult, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, sessionReadResult.getSessions(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, sessionReadResult.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, sessionReadResult.zzry(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) sessionReadResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdn, reason: merged with bridge method [inline-methods] */
    public SessionReadResult createFromParcel(Parcel parcel) {
        Status status = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        ArrayList arrayListZzc2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, Session.CREATOR);
                    break;
                case 2:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, SessionDataSet.CREATOR);
                    break;
                case 3:
                    status = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
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
        return new SessionReadResult(iZzg, arrayListZzc2, arrayListZzc, status);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfi, reason: merged with bridge method [inline-methods] */
    public SessionReadResult[] newArray(int i) {
        return new SessionReadResult[i];
    }
}
