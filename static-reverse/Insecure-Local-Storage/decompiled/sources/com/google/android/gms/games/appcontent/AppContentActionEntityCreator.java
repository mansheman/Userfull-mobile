package com.google.android.gms.games.appcontent;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AppContentActionEntityCreator implements Parcelable.Creator<AppContentActionEntity> {
    static void zza(AppContentActionEntity appContentActionEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, appContentActionEntity.zzrO(), false);
        zzb.zzc(parcel, 1000, appContentActionEntity.getVersionCode());
        zzb.zza(parcel, 2, appContentActionEntity.zzrP(), false);
        zzb.zza(parcel, 3, appContentActionEntity.getExtras(), false);
        zzb.zza(parcel, 6, appContentActionEntity.getType(), false);
        zzb.zza(parcel, 7, appContentActionEntity.getId(), false);
        zzb.zza(parcel, 8, (Parcelable) appContentActionEntity.zzrN(), i, false);
        zzb.zza(parcel, 9, appContentActionEntity.zzrQ(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdw, reason: merged with bridge method [inline-methods] */
    public AppContentActionEntity createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        AppContentAnnotationEntity appContentAnnotationEntity = null;
        String strZzo2 = null;
        String strZzo3 = null;
        Bundle bundleZzq = null;
        String strZzo4 = null;
        ArrayList arrayListZzc = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzc = zza.zzc(parcel, iZzaa, AppContentConditionEntity.CREATOR);
                    break;
                case 2:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    bundleZzq = zza.zzq(parcel, iZzaa);
                    break;
                case 6:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    appContentAnnotationEntity = (AppContentAnnotationEntity) zza.zza(parcel, iZzaa, AppContentAnnotationEntity.CREATOR);
                    break;
                case 9:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new AppContentActionEntity(iZzg, arrayListZzc, strZzo4, bundleZzq, strZzo3, strZzo2, appContentAnnotationEntity, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfs, reason: merged with bridge method [inline-methods] */
    public AppContentActionEntity[] newArray(int i) {
        return new AppContentActionEntity[i];
    }
}
