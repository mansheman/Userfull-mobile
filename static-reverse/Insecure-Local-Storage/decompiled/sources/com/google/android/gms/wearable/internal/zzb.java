package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<AddListenerRequest> {
    static void zza(AddListenerRequest addListenerRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, addListenerRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, addListenerRequest.zzAT(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable[]) addListenerRequest.zzaTr, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, addListenerRequest.zzaTs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, addListenerRequest.zzaTt, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgK, reason: merged with bridge method [inline-methods] */
    public AddListenerRequest createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        IntentFilter[] intentFilterArr = null;
        IBinder iBinderZzp = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 3:
                    intentFilterArr = (IntentFilter[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, IntentFilter.CREATOR);
                    break;
                case 4:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AddListenerRequest(iZzg, iBinderZzp, intentFilterArr, strZzo2, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjP, reason: merged with bridge method [inline-methods] */
    public AddListenerRequest[] newArray(int i) {
        return new AddListenerRequest[i];
    }
}
