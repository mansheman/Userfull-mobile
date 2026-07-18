package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.Session;

/* loaded from: classes.dex */
public class zzx implements Parcelable.Creator<SessionStartRequest> {
    static void zza(SessionStartRequest sessionStartRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) sessionStartRequest.getSession(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, sessionStartRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, sessionStartRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, sessionStartRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcW, reason: merged with bridge method [inline-methods] */
    public SessionStartRequest createFromParcel(Parcel parcel) {
        String strZzo;
        IBinder iBinderZzp;
        Session session;
        int iZzg;
        String str = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        IBinder iBinder = null;
        Session session2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = i;
                    IBinder iBinder2 = iBinder;
                    session = (Session) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Session.CREATOR);
                    strZzo = str;
                    iBinderZzp = iBinder2;
                    break;
                case 2:
                    session = session2;
                    iZzg = i;
                    String str2 = str;
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    strZzo = str2;
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    iBinderZzp = iBinder;
                    session = session2;
                    iZzg = i;
                    break;
                case 1000:
                    String str3 = str;
                    iBinderZzp = iBinder;
                    session = session2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    strZzo = str3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    strZzo = str;
                    iBinderZzp = iBinder;
                    session = session2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            session2 = session;
            iBinder = iBinderZzp;
            str = strZzo;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SessionStartRequest(i, session2, iBinder, str);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeR, reason: merged with bridge method [inline-methods] */
    public SessionStartRequest[] newArray(int i) {
        return new SessionStartRequest[i];
    }
}
