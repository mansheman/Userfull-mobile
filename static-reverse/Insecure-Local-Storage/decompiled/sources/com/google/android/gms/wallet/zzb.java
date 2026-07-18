package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<Cart> {
    static void zza(Cart cart, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, cart.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, cart.zzaQf, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, cart.zzaQg, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, cart.zzaQh, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgb, reason: merged with bridge method [inline-methods] */
    public Cart createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayList = new ArrayList();
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    arrayList = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, LineItem.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new Cart(iZzg, strZzo2, strZzo, arrayList);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjd, reason: merged with bridge method [inline-methods] */
    public Cart[] newArray(int i) {
        return new Cart[i];
    }
}
