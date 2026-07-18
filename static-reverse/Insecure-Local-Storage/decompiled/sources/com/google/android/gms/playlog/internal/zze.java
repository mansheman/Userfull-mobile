package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<PlayLoggerContext> {
    static void zza(PlayLoggerContext playLoggerContext, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, playLoggerContext.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, playLoggerContext.packageName, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, playLoggerContext.zzaGP);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, playLoggerContext.zzaGQ);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, playLoggerContext.zzaGR, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, playLoggerContext.zzaGS, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, playLoggerContext.zzaGT);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, playLoggerContext.zzaGU, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, playLoggerContext.zzaGV);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfH, reason: merged with bridge method [inline-methods] */
    public PlayLoggerContext createFromParcel(Parcel parcel) {
        String strZzo = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        boolean zZzc2 = true;
        String strZzo2 = null;
        String strZzo3 = null;
        int iZzg = 0;
        int iZzg2 = 0;
        String strZzo4 = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 8:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PlayLoggerContext(iZzg3, strZzo4, iZzg2, iZzg, strZzo3, strZzo2, zZzc2, strZzo, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zziw, reason: merged with bridge method [inline-methods] */
    public PlayLoggerContext[] newArray(int i) {
        return new PlayLoggerContext[i];
    }
}
