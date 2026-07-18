package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzv implements Parcelable.Creator<ResolveAccountRequest> {
    static void zza(ResolveAccountRequest resolveAccountRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, resolveAccountRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) resolveAccountRequest.getAccount(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, resolveAccountRequest.getSessionId());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzX, reason: merged with bridge method [inline-methods] */
    public ResolveAccountRequest createFromParcel(Parcel parcel) {
        int iZzg;
        Account account;
        int iZzg2;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Account account2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    int i3 = i;
                    account = account2;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i3;
                    break;
                case 2:
                    Account account3 = (Account) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Account.CREATOR);
                    iZzg2 = i2;
                    iZzg = i;
                    account = account3;
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    account = account2;
                    iZzg2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iZzg = i;
                    account = account2;
                    iZzg2 = i2;
                    break;
            }
            i2 = iZzg2;
            account2 = account;
            i = iZzg;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ResolveAccountRequest(i2, account2, i);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbx, reason: merged with bridge method [inline-methods] */
    public ResolveAccountRequest[] newArray(int i) {
        return new ResolveAccountRequest[i];
    }
}
