package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzap implements Parcelable.Creator<LoadRealtimeRequest> {
    static void zza(LoadRealtimeRequest loadRealtimeRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, loadRealtimeRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) loadRealtimeRequest.zzacT, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, loadRealtimeRequest.zzafQ);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 4, loadRealtimeRequest.zzafR, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, loadRealtimeRequest.zzafS);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) loadRealtimeRequest.zzafT, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaX, reason: merged with bridge method [inline-methods] */
    public LoadRealtimeRequest createFromParcel(Parcel parcel) {
        DataHolder dataHolder = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList<String> arrayListZzC = null;
        boolean zZzc2 = false;
        DriveId driveId = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    break;
                case 3:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 5:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    dataHolder = (DataHolder) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataHolder.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new LoadRealtimeRequest(iZzg, driveId, zZzc2, arrayListZzC, zZzc, dataHolder);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcM, reason: merged with bridge method [inline-methods] */
    public LoadRealtimeRequest[] newArray(int i) {
        return new LoadRealtimeRequest[i];
    }
}
