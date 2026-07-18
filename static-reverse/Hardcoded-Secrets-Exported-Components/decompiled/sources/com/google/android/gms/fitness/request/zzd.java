package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<DataDeleteRequest> {
    static void zza(DataDeleteRequest dataDeleteRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, dataDeleteRequest.zzkt());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dataDeleteRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, dataDeleteRequest.zzqs());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, dataDeleteRequest.getDataSources(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, dataDeleteRequest.getDataTypes(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, dataDeleteRequest.getSessions(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, dataDeleteRequest.zzqV());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, dataDeleteRequest.zzqW());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, dataDeleteRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, dataDeleteRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcD, reason: merged with bridge method [inline-methods] */
    public DataDeleteRequest createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        long jZzi = 0;
        long jZzi2 = 0;
        ArrayList arrayListZzc = null;
        ArrayList arrayListZzc2 = null;
        ArrayList arrayListZzc3 = null;
        boolean zZzc = false;
        boolean zZzc2 = false;
        IBinder iBinderZzp = null;
        String strZzo = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 2:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSource.CREATOR);
                    break;
                case 4:
                    arrayListZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataType.CREATOR);
                    break;
                case 5:
                    arrayListZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, Session.CREATOR);
                    break;
                case 6:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 7:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 8:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 9:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
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
        return new DataDeleteRequest(iZzg, jZzi, jZzi2, arrayListZzc, arrayListZzc2, arrayListZzc3, zZzc, zZzc2, iBinderZzp, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzex, reason: merged with bridge method [inline-methods] */
    public DataDeleteRequest[] newArray(int i) {
        return new DataDeleteRequest[i];
    }
}
