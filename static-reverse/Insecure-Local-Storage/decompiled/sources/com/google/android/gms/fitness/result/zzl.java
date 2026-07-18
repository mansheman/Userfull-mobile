package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzl implements Parcelable.Creator<SyncInfoResult> {
    static void zza(SyncInfoResult syncInfoResult, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) syncInfoResult.getStatus(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, syncInfoResult.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, syncInfoResult.zzrz());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdp, reason: merged with bridge method [inline-methods] */
    public SyncInfoResult createFromParcel(Parcel parcel) {
        long jZzi;
        Status status;
        int iZzg;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Status status2 = null;
        long j = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    long j2 = j;
                    status = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
                    iZzg = i;
                    jZzi = j2;
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    status = status2;
                    iZzg = i;
                    break;
                case 1000:
                    long j3 = j;
                    status = status2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    jZzi = j3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    jZzi = j;
                    status = status2;
                    iZzg = i;
                    break;
            }
            status2 = status;
            i = iZzg;
            j = jZzi;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SyncInfoResult(i, status2, j);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfk, reason: merged with bridge method [inline-methods] */
    public SyncInfoResult[] newArray(int i) {
        return new SyncInfoResult[i];
    }
}
