package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.query.internal.FilterHolder;

/* loaded from: classes.dex */
public class zzbj implements Parcelable.Creator<OpenFileIntentSenderRequest> {
    static void zza(OpenFileIntentSenderRequest openFileIntentSenderRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, openFileIntentSenderRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, openFileIntentSenderRequest.zzadv, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, openFileIntentSenderRequest.zzadw, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) openFileIntentSenderRequest.zzady, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) openFileIntentSenderRequest.zzagt, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbn, reason: merged with bridge method [inline-methods] */
    public OpenFileIntentSenderRequest createFromParcel(Parcel parcel) {
        FilterHolder filterHolder = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        DriveId driveId = null;
        String[] strArrZzA = null;
        String strZzo = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strArrZzA = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
                    break;
                case 4:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    break;
                case 5:
                    filterHolder = (FilterHolder) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, FilterHolder.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new OpenFileIntentSenderRequest(iZzg, strZzo, strArrZzA, driveId, filterHolder);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdc, reason: merged with bridge method [inline-methods] */
    public OpenFileIntentSenderRequest[] newArray(int i) {
        return new OpenFileIntentSenderRequest[i];
    }
}
