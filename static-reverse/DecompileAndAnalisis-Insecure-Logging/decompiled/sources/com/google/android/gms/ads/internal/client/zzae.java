package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzae implements Parcelable.Creator<SearchAdRequestParcel> {
    static void zza(SearchAdRequestParcel searchAdRequestParcel, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, searchAdRequestParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, searchAdRequestParcel.zzth);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, searchAdRequestParcel.backgroundColor);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, searchAdRequestParcel.zzti);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, searchAdRequestParcel.zztj);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, searchAdRequestParcel.zztk);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, searchAdRequestParcel.zztl);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, searchAdRequestParcel.zztm);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 9, searchAdRequestParcel.zztn);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, searchAdRequestParcel.zzto, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 11, searchAdRequestParcel.zztp);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, searchAdRequestParcel.zztq, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 13, searchAdRequestParcel.zztr);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 14, searchAdRequestParcel.zzts);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, searchAdRequestParcel.zztt, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zze, reason: merged with bridge method [inline-methods] */
    public SearchAdRequestParcel createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        int iZzg2 = 0;
        int iZzg3 = 0;
        int iZzg4 = 0;
        int iZzg5 = 0;
        int iZzg6 = 0;
        int iZzg7 = 0;
        int iZzg8 = 0;
        int iZzg9 = 0;
        String strZzo = null;
        int iZzg10 = 0;
        String strZzo2 = null;
        int iZzg11 = 0;
        int iZzg12 = 0;
        String strZzo3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    iZzg5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    iZzg6 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 7:
                    iZzg7 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 8:
                    iZzg8 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 9:
                    iZzg9 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 10:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 11:
                    iZzg10 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 12:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 13:
                    iZzg11 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 14:
                    iZzg12 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 15:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SearchAdRequestParcel(iZzg, iZzg2, iZzg3, iZzg4, iZzg5, iZzg6, iZzg7, iZzg8, iZzg9, strZzo, iZzg10, strZzo2, iZzg11, iZzg12, strZzo3);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzo, reason: merged with bridge method [inline-methods] */
    public SearchAdRequestParcel[] newArray(int i) {
        return new SearchAdRequestParcel[i];
    }
}
