package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<BitmapTeleporter> {
    static void zza(BitmapTeleporter bitmapTeleporter, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, bitmapTeleporter.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) bitmapTeleporter.zzCZ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, bitmapTeleporter.zzSq);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzR, reason: merged with bridge method [inline-methods] */
    public BitmapTeleporter createFromParcel(Parcel parcel) {
        int iZzg;
        ParcelFileDescriptor parcelFileDescriptor;
        int iZzg2;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    int i3 = i;
                    parcelFileDescriptor = parcelFileDescriptor2;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i3;
                    break;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ParcelFileDescriptor.CREATOR);
                    iZzg2 = i2;
                    iZzg = i;
                    parcelFileDescriptor = parcelFileDescriptor3;
                    break;
                case 3:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    parcelFileDescriptor = parcelFileDescriptor2;
                    iZzg2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iZzg = i;
                    parcelFileDescriptor = parcelFileDescriptor2;
                    iZzg2 = i2;
                    break;
            }
            i2 = iZzg2;
            parcelFileDescriptor2 = parcelFileDescriptor;
            i = iZzg;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new BitmapTeleporter(i2, parcelFileDescriptor2, i);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbe, reason: merged with bridge method [inline-methods] */
    public BitmapTeleporter[] newArray(int i) {
        return new BitmapTeleporter[i];
    }
}
