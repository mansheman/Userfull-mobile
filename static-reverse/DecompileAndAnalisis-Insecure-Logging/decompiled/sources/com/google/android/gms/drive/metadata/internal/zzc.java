package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.metadata.CustomPropertyKey;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<CustomProperty> {
    static void zza(CustomProperty customProperty, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, customProperty.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) customProperty.zzagG, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, customProperty.mValue, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbB, reason: merged with bridge method [inline-methods] */
    public CustomProperty createFromParcel(Parcel parcel) {
        String strZzo;
        CustomPropertyKey customPropertyKey;
        int iZzg;
        String str = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        CustomPropertyKey customPropertyKey2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    String str2 = str;
                    customPropertyKey = customPropertyKey2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    strZzo = str2;
                    break;
                case 2:
                    CustomPropertyKey customPropertyKey3 = (CustomPropertyKey) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, CustomPropertyKey.CREATOR);
                    iZzg = i;
                    strZzo = str;
                    customPropertyKey = customPropertyKey3;
                    break;
                case 3:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    customPropertyKey = customPropertyKey2;
                    iZzg = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    strZzo = str;
                    customPropertyKey = customPropertyKey2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            customPropertyKey2 = customPropertyKey;
            str = strZzo;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CustomProperty(i, customPropertyKey2, str);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdq, reason: merged with bridge method [inline-methods] */
    public CustomProperty[] newArray(int i) {
        return new CustomProperty[i];
    }
}
