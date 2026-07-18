package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.server.response.FieldMappingDictionary;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzd implements Parcelable.Creator<FieldMappingDictionary.Entry> {
    static void zza(FieldMappingDictionary.Entry entry, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, entry.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, entry.className, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, entry.zzabT, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzak, reason: merged with bridge method [inline-methods] */
    public FieldMappingDictionary.Entry createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, FieldMappingDictionary.FieldMapPair.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new FieldMappingDictionary.Entry(iZzg, strZzo, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbK, reason: merged with bridge method [inline-methods] */
    public FieldMappingDictionary.Entry[] newArray(int i) {
        return new FieldMappingDictionary.Entry[i];
    }
}
