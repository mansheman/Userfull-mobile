package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzv implements Parcelable.Creator<SessionReadRequest> {
    static void zza(SessionReadRequest sessionReadRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, sessionReadRequest.getSessionName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, sessionReadRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, sessionReadRequest.getSessionId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, sessionReadRequest.zzkt());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, sessionReadRequest.zzqs());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, sessionReadRequest.getDataTypes(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, sessionReadRequest.getDataSources(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, sessionReadRequest.zzro());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, sessionReadRequest.zzqZ());
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 9, sessionReadRequest.getExcludedPackages(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, sessionReadRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, sessionReadRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcU, reason: merged with bridge method [inline-methods] */
    public SessionReadRequest createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        long jZzi = 0;
        long jZzi2 = 0;
        ArrayList arrayListZzc = null;
        ArrayList arrayListZzc2 = null;
        boolean zZzc = false;
        boolean zZzc2 = false;
        ArrayList<String> arrayListZzC = null;
        IBinder iBinderZzp = null;
        String strZzo3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 5:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataType.CREATOR);
                    break;
                case 6:
                    arrayListZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 7:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 8:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 9:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 10:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 11:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SessionReadRequest(iZzg, strZzo, strZzo2, jZzi, jZzi2, arrayListZzc, arrayListZzc2, zZzc, zZzc2, arrayListZzC, iBinderZzp, strZzo3);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeP, reason: merged with bridge method [inline-methods] */
    public SessionReadRequest[] newArray(int i) {
        return new SessionReadRequest[i];
    }
}
