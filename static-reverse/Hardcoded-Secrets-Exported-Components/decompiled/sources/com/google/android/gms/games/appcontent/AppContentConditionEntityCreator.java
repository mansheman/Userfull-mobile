package com.google.android.gms.games.appcontent;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class AppContentConditionEntityCreator implements Parcelable.Creator<AppContentConditionEntity> {
    static void zza(AppContentConditionEntity appContentConditionEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, appContentConditionEntity.zzse(), false);
        zzb.zzc(parcel, 1000, appContentConditionEntity.getVersionCode());
        zzb.zza(parcel, 2, appContentConditionEntity.zzsf(), false);
        zzb.zza(parcel, 3, appContentConditionEntity.zzsg(), false);
        zzb.zza(parcel, 4, appContentConditionEntity.zzsh(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdz, reason: merged with bridge method [inline-methods] */
    public AppContentConditionEntity createFromParcel(Parcel parcel) {
        Bundle bundleZzq = null;
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    bundleZzq = zza.zzq(parcel, iZzaa);
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
        return new AppContentConditionEntity(iZzg, strZzo3, strZzo2, strZzo, bundleZzq);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfv, reason: merged with bridge method [inline-methods] */
    public AppContentConditionEntity[] newArray(int i) {
        return new AppContentConditionEntity[i];
    }
}
