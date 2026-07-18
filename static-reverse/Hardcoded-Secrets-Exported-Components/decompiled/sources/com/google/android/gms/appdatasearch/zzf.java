package com.google.android.gms.appdatasearch;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<GetRecentContextCall.Request> {
    static void zza(GetRecentContextCall.Request request, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) request.zzNj, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, request.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, request.zzNk);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, request.zzNl);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, request.zzNm);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzag, reason: merged with bridge method [inline-methods] */
    public GetRecentContextCall.Request[] newArray(int i) {
        return new GetRecentContextCall.Request[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzv, reason: merged with bridge method [inline-methods] */
    public GetRecentContextCall.Request createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Account account = null;
        boolean zZzc2 = false;
        boolean zZzc3 = false;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    account = (Account) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Account.CREATOR);
                    break;
                case 2:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
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
        return new GetRecentContextCall.Request(iZzg, account, zZzc3, zZzc2, zZzc);
    }
}
