package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzt implements Parcelable.Creator<Value> {
    static void zza(Value value, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, value.getFormat());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, value.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, value.isSet());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, value.zzqI());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, value.zzqO(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, value.zzqP(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcy, reason: merged with bridge method [inline-methods] */
    public Value createFromParcel(Parcel parcel) {
        Bundle bundleZzq = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        float fZzl = 0.0f;
        String strZzo = null;
        int iZzg = 0;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 3:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 4:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
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
        return new Value(iZzg2, iZzg, zZzc, fZzl, strZzo, bundleZzq);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzes, reason: merged with bridge method [inline-methods] */
    public Value[] newArray(int i) {
        return new Value[i];
    }
}
