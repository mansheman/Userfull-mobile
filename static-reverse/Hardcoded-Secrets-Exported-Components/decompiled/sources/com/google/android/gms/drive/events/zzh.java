package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<ProgressEvent> {
    static void zza(ProgressEvent progressEvent, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, progressEvent.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) progressEvent.zzacT, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, progressEvent.zzwS);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, progressEvent.zzaeg);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, progressEvent.zzaeh);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, progressEvent.zzSq);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaz, reason: merged with bridge method [inline-methods] */
    public ProgressEvent createFromParcel(Parcel parcel) {
        long jZzi = 0;
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DriveId driveId = null;
        long jZzi2 = 0;
        int iZzg2 = 0;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    break;
                case 3:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 5:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 6:
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
        return new ProgressEvent(iZzg3, driveId, iZzg2, jZzi2, jZzi, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzci, reason: merged with bridge method [inline-methods] */
    public ProgressEvent[] newArray(int i) {
        return new ProgressEvent[i];
    }
}
