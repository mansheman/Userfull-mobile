package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzg implements Parcelable.Creator<GetRecentContextCall.Response> {
    static void zza(GetRecentContextCall.Response response, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, response.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) response.zzNn, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, response.zzNo, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, response.zzNp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzah, reason: merged with bridge method [inline-methods] */
    public GetRecentContextCall.Response[] newArray(int i) {
        return new GetRecentContextCall.Response[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzw, reason: merged with bridge method [inline-methods] */
    public GetRecentContextCall.Response createFromParcel(Parcel parcel) {
        String[] strArrZzA;
        ArrayList arrayListZzc;
        Status status;
        int iZzg;
        String[] strArr = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        ArrayList arrayList = null;
        Status status2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = i;
                    ArrayList arrayList2 = arrayList;
                    status = (Status) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Status.CREATOR);
                    strArrZzA = strArr;
                    arrayListZzc = arrayList2;
                    break;
                case 2:
                    status = status2;
                    iZzg = i;
                    String[] strArr2 = strArr;
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, UsageInfo.CREATOR);
                    strArrZzA = strArr2;
                    break;
                case 3:
                    strArrZzA = com.google.android.gms.common.internal.safeparcel.zza.zzA(parcel, iZzaa);
                    arrayListZzc = arrayList;
                    status = status2;
                    iZzg = i;
                    break;
                case 1000:
                    String[] strArr3 = strArr;
                    arrayListZzc = arrayList;
                    status = status2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    strArrZzA = strArr3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    strArrZzA = strArr;
                    arrayListZzc = arrayList;
                    status = status2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            status2 = status;
            arrayList = arrayListZzc;
            strArr = strArrZzA;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GetRecentContextCall.Response(i, status2, arrayList, strArr);
    }
}
