package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<AncsNotificationParcelable> {
    static void zza(AncsNotificationParcelable ancsNotificationParcelable, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, ancsNotificationParcelable.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, ancsNotificationParcelable.getId());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, ancsNotificationParcelable.zzsK(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, ancsNotificationParcelable.zzAU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, ancsNotificationParcelable.zzAV(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, ancsNotificationParcelable.getTitle(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, ancsNotificationParcelable.zzsb(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, ancsNotificationParcelable.getDisplayName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, ancsNotificationParcelable.zzAW());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, ancsNotificationParcelable.zzAX());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, ancsNotificationParcelable.zzAY());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, ancsNotificationParcelable.zzAZ());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgM, reason: merged with bridge method [inline-methods] */
    public AncsNotificationParcelable createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        int iZzg2 = 0;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        String strZzo6 = null;
        byte bZze = 0;
        byte bZze2 = 0;
        byte bZze3 = 0;
        byte bZze4 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    strZzo6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    bZze = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 10:
                    bZze2 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 11:
                    bZze3 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 12:
                    bZze4 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AncsNotificationParcelable(iZzg, iZzg2, strZzo, strZzo2, strZzo3, strZzo4, strZzo5, strZzo6, bZze, bZze2, bZze3, bZze4);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjR, reason: merged with bridge method [inline-methods] */
    public AncsNotificationParcelable[] newArray(int i) {
        return new AncsNotificationParcelable[i];
    }
}
