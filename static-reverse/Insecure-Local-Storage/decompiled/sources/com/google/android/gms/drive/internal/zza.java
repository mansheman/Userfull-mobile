package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.ChangesAvailableOptions;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<AddEventListenerRequest> {
    static void zza(AddEventListenerRequest addEventListenerRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, addEventListenerRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) addEventListenerRequest.zzacT, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, addEventListenerRequest.zzaca);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) addEventListenerRequest.zzadO, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaB, reason: merged with bridge method [inline-methods] */
    public AddEventListenerRequest createFromParcel(Parcel parcel) {
        ChangesAvailableOptions changesAvailableOptions;
        int iZzg;
        DriveId driveId;
        int iZzg2;
        ChangesAvailableOptions changesAvailableOptions2 = null;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        DriveId driveId2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    ChangesAvailableOptions changesAvailableOptions3 = changesAvailableOptions2;
                    iZzg = i;
                    driveId = driveId2;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    changesAvailableOptions = changesAvailableOptions3;
                    break;
                case 2:
                    iZzg2 = i2;
                    int i3 = i;
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    changesAvailableOptions = changesAvailableOptions2;
                    iZzg = i3;
                    break;
                case 3:
                    driveId = driveId2;
                    iZzg2 = i2;
                    ChangesAvailableOptions changesAvailableOptions4 = changesAvailableOptions2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    changesAvailableOptions = changesAvailableOptions4;
                    break;
                case 4:
                    changesAvailableOptions = (ChangesAvailableOptions) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ChangesAvailableOptions.CREATOR);
                    iZzg = i;
                    driveId = driveId2;
                    iZzg2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    changesAvailableOptions = changesAvailableOptions2;
                    iZzg = i;
                    driveId = driveId2;
                    iZzg2 = i2;
                    break;
            }
            i2 = iZzg2;
            driveId2 = driveId;
            i = iZzg;
            changesAvailableOptions2 = changesAvailableOptions;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AddEventListenerRequest(i2, driveId2, i, changesAvailableOptions2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcl, reason: merged with bridge method [inline-methods] */
    public AddEventListenerRequest[] newArray(int i) {
        return new AddEventListenerRequest[i];
    }
}
