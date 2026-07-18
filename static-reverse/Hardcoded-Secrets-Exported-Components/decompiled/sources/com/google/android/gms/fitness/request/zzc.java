package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataType;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<DailyTotalRequest> {
    static void zza(DailyTotalRequest dailyTotalRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, dailyTotalRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, dailyTotalRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) dailyTotalRequest.getDataType(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, dailyTotalRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcC, reason: merged with bridge method [inline-methods] */
    public DailyTotalRequest createFromParcel(Parcel parcel) {
        String strZzo;
        DataType dataType;
        IBinder iBinderZzp;
        int iZzg;
        String str = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        DataType dataType2 = null;
        IBinder iBinder = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = i;
                    DataType dataType3 = dataType2;
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    strZzo = str;
                    dataType = dataType3;
                    break;
                case 2:
                    iBinderZzp = iBinder;
                    iZzg = i;
                    String str2 = str;
                    dataType = (DataType) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataType.CREATOR);
                    strZzo = str2;
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    dataType = dataType2;
                    iBinderZzp = iBinder;
                    iZzg = i;
                    break;
                case 1000:
                    String str3 = str;
                    dataType = dataType2;
                    iBinderZzp = iBinder;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    strZzo = str3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    strZzo = str;
                    dataType = dataType2;
                    iBinderZzp = iBinder;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            iBinder = iBinderZzp;
            dataType2 = dataType;
            str = strZzo;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new DailyTotalRequest(i, iBinder, dataType2, str);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzew, reason: merged with bridge method [inline-methods] */
    public DailyTotalRequest[] newArray(int i) {
        return new DailyTotalRequest[i];
    }
}
