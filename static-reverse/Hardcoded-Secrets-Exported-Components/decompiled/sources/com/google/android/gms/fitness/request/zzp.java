package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataType;

/* loaded from: classes.dex */
public class zzp implements Parcelable.Creator<ListSubscriptionsRequest> {
    static void zza(ListSubscriptionsRequest listSubscriptionsRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) listSubscriptionsRequest.getDataType(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, listSubscriptionsRequest.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, listSubscriptionsRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, listSubscriptionsRequest.getPackageName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcO, reason: merged with bridge method [inline-methods] */
    public ListSubscriptionsRequest createFromParcel(Parcel parcel) {
        String strZzo;
        IBinder iBinderZzp;
        DataType dataType;
        int iZzg;
        String str = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        IBinder iBinder = null;
        DataType dataType2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = i;
                    IBinder iBinder2 = iBinder;
                    dataType = (DataType) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataType.CREATOR);
                    strZzo = str;
                    iBinderZzp = iBinder2;
                    break;
                case 2:
                    dataType = dataType2;
                    iZzg = i;
                    String str2 = str;
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    strZzo = str2;
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    iBinderZzp = iBinder;
                    dataType = dataType2;
                    iZzg = i;
                    break;
                case 1000:
                    String str3 = str;
                    iBinderZzp = iBinder;
                    dataType = dataType2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    strZzo = str3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    strZzo = str;
                    iBinderZzp = iBinder;
                    dataType = dataType2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            dataType2 = dataType;
            iBinder = iBinderZzp;
            str = strZzo;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ListSubscriptionsRequest(i, dataType2, iBinder, str);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeI, reason: merged with bridge method [inline-methods] */
    public ListSubscriptionsRequest[] newArray(int i) {
        return new ListSubscriptionsRequest[i];
    }
}
