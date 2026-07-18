package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzw implements Parcelable.Creator<SessionRegistrationRequest> {
    static void zza(SessionRegistrationRequest sessionRegistrationRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) sessionRegistrationRequest.zzrg(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, sessionRegistrationRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, sessionRegistrationRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, sessionRegistrationRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, sessionRegistrationRequest.zzrp());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcV, reason: merged with bridge method [inline-methods] */
    public SessionRegistrationRequest createFromParcel(Parcel parcel) {
        int iZzg = 0;
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        IBinder iBinderZzp = null;
        PendingIntent pendingIntent = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    pendingIntent = (PendingIntent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PendingIntent.CREATOR);
                    break;
                case 2:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SessionRegistrationRequest(iZzg2, pendingIntent, iBinderZzp, strZzo, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeQ, reason: merged with bridge method [inline-methods] */
    public SessionRegistrationRequest[] newArray(int i) {
        return new SessionRegistrationRequest[i];
    }
}
