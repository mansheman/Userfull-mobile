package com.google.android.gms.drive.events;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<CompletionEvent> {
    static void zza(CompletionEvent completionEvent, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, completionEvent.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) completionEvent.zzacT, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, completionEvent.zzOx, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) completionEvent.zzadT, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) completionEvent.zzadU, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) completionEvent.zzadV, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 7, completionEvent.zzadW, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, completionEvent.zzwS);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, completionEvent.zzadX, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzay, reason: merged with bridge method [inline-methods] */
    public CompletionEvent createFromParcel(Parcel parcel) {
        int iZzg = 0;
        IBinder iBinderZzp = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ArrayList<String> arrayListZzC = null;
        MetadataBundle metadataBundle = null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        String strZzo = null;
        DriveId driveId = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    driveId = (DriveId) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DriveId.CREATOR);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    parcelFileDescriptor2 = (ParcelFileDescriptor) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ParcelFileDescriptor.CREATOR);
                    break;
                case 5:
                    parcelFileDescriptor = (ParcelFileDescriptor) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ParcelFileDescriptor.CREATOR);
                    break;
                case 6:
                    metadataBundle = (MetadataBundle) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, MetadataBundle.CREATOR);
                    break;
                case 7:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 8:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 9:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CompletionEvent(iZzg2, driveId, strZzo, parcelFileDescriptor2, parcelFileDescriptor, metadataBundle, arrayListZzC, iZzg, iBinderZzp);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzch, reason: merged with bridge method [inline-methods] */
    public CompletionEvent[] newArray(int i) {
        return new CompletionEvent[i];
    }
}
