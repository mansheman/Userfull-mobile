package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<BitmapDescriptorParcelable> {
    static void zza(BitmapDescriptorParcelable bitmapDescriptorParcelable, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, bitmapDescriptorParcelable.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, bitmapDescriptorParcelable.getType());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, bitmapDescriptorParcelable.getParameters(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) bitmapDescriptorParcelable.getBitmap(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfa, reason: merged with bridge method [inline-methods] */
    public BitmapDescriptorParcelable createFromParcel(Parcel parcel) {
        Bitmap bitmap = null;
        byte bZze = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Bundle bundleZzq = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    bZze = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 3:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 4:
                    bitmap = (Bitmap) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Bitmap.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new BitmapDescriptorParcelable(iZzg, bZze, bundleZzq, bitmap);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhA, reason: merged with bridge method [inline-methods] */
    public BitmapDescriptorParcelable[] newArray(int i) {
        return new BitmapDescriptorParcelable[i];
    }
}
