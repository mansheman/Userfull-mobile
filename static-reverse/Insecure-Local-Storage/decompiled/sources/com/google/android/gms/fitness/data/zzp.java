package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzp implements Parcelable.Creator<Session> {
    static void zza(Session session, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, session.zzkt());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, session.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, session.zzqs());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, session.getName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, session.getIdentifier(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, session.getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, session.zzqq());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable) session.zzqB(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, session.zzqJ(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcv, reason: merged with bridge method [inline-methods] */
    public Session createFromParcel(Parcel parcel) {
        long jZzi = 0;
        int iZzg = 0;
        Long lZzj = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Application application = null;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        long jZzi2 = 0;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    application = (Application) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Application.CREATOR);
                    break;
                case 9:
                    lZzj = com.google.android.gms.common.internal.safeparcel.zza.zzj(parcel, iZzaa);
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
        return new Session(iZzg2, jZzi2, jZzi, strZzo3, strZzo2, strZzo, iZzg, application, lZzj);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzep, reason: merged with bridge method [inline-methods] */
    public Session[] newArray(int i) {
        return new Session[i];
    }
}
