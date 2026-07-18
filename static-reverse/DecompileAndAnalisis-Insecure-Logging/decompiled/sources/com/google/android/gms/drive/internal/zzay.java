package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DrivePreferences;

/* loaded from: classes.dex */
public class zzay implements Parcelable.Creator<OnDrivePreferencesResponse> {
    static void zza(OnDrivePreferencesResponse onDrivePreferencesResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, onDrivePreferencesResponse.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) onDrivePreferencesResponse.zzagi, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbd, reason: merged with bridge method [inline-methods] */
    public OnDrivePreferencesResponse createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        DrivePreferences drivePreferences = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    drivePreferences = (DrivePreferences) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DrivePreferences.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new OnDrivePreferencesResponse(iZzg, drivePreferences);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcS, reason: merged with bridge method [inline-methods] */
    public OnDrivePreferencesResponse[] newArray(int i) {
        return new OnDrivePreferencesResponse[i];
    }
}
