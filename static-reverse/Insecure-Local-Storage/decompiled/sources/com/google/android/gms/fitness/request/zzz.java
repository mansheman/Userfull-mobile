package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzz implements Parcelable.Creator<SessionUnregistrationRequest> {
    static void zza(SessionUnregistrationRequest sessionUnregistrationRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) sessionUnregistrationRequest.zzrg(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, sessionUnregistrationRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, sessionUnregistrationRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, sessionUnregistrationRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcY, reason: merged with bridge method [inline-methods] */
    public SessionUnregistrationRequest createFromParcel(Parcel parcel) {
        String strZzo;
        IBinder iBinderZzp;
        PendingIntent pendingIntent;
        int iZzg;
        String str = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        IBinder iBinder = null;
        PendingIntent pendingIntent2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = i;
                    IBinder iBinder2 = iBinder;
                    pendingIntent = (PendingIntent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PendingIntent.CREATOR);
                    strZzo = str;
                    iBinderZzp = iBinder2;
                    break;
                case 2:
                    pendingIntent = pendingIntent2;
                    iZzg = i;
                    String str2 = str;
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    strZzo = str2;
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    iBinderZzp = iBinder;
                    pendingIntent = pendingIntent2;
                    iZzg = i;
                    break;
                case 1000:
                    String str3 = str;
                    iBinderZzp = iBinder;
                    pendingIntent = pendingIntent2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    strZzo = str3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    strZzo = str;
                    iBinderZzp = iBinder;
                    pendingIntent = pendingIntent2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            pendingIntent2 = pendingIntent;
            iBinder = iBinderZzp;
            str = strZzo;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SessionUnregistrationRequest(i, pendingIntent2, iBinder, str);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeT, reason: merged with bridge method [inline-methods] */
    public SessionUnregistrationRequest[] newArray(int i) {
        return new SessionUnregistrationRequest[i];
    }
}
