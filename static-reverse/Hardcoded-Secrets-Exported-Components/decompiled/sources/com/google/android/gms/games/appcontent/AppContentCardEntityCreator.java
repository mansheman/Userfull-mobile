package com.google.android.gms.games.appcontent;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AppContentCardEntityCreator implements Parcelable.Creator<AppContentCardEntity> {
    static void zza(AppContentCardEntity appContentCardEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, appContentCardEntity.getActions(), false);
        zzb.zzc(parcel, 1000, appContentCardEntity.getVersionCode());
        zzb.zzc(parcel, 2, appContentCardEntity.zzrZ(), false);
        zzb.zzc(parcel, 3, appContentCardEntity.zzrO(), false);
        zzb.zza(parcel, 4, appContentCardEntity.zzrP(), false);
        zzb.zzc(parcel, 5, appContentCardEntity.zzsa());
        zzb.zza(parcel, 6, appContentCardEntity.getDescription(), false);
        zzb.zza(parcel, 7, appContentCardEntity.getExtras(), false);
        zzb.zza(parcel, 10, appContentCardEntity.zzsb(), false);
        zzb.zza(parcel, 11, appContentCardEntity.getTitle(), false);
        zzb.zzc(parcel, 12, appContentCardEntity.zzsc());
        zzb.zza(parcel, 13, appContentCardEntity.getType(), false);
        zzb.zza(parcel, 14, appContentCardEntity.getId(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdy, reason: merged with bridge method [inline-methods] */
    public AppContentCardEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        ArrayList arrayListZzc2 = null;
        ArrayList arrayListZzc3 = null;
        String strZzo = null;
        int iZzg2 = 0;
        String strZzo2 = null;
        Bundle bundleZzq = null;
        String strZzo3 = null;
        String strZzo4 = null;
        int iZzg3 = 0;
        String strZzo5 = null;
        String strZzo6 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzc = zza.zzc(parcel, iZzaa, AppContentActionEntity.CREATOR);
                    break;
                case 2:
                    arrayListZzc2 = zza.zzc(parcel, iZzaa, AppContentAnnotationEntity.CREATOR);
                    break;
                case 3:
                    arrayListZzc3 = zza.zzc(parcel, iZzaa, AppContentConditionEntity.CREATOR);
                    break;
                case 4:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    bundleZzq = zza.zzq(parcel, iZzaa);
                    break;
                case 10:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 11:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 12:
                    iZzg3 = zza.zzg(parcel, iZzaa);
                    break;
                case 13:
                    strZzo5 = zza.zzo(parcel, iZzaa);
                    break;
                case 14:
                    strZzo6 = zza.zzo(parcel, iZzaa);
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
        return new AppContentCardEntity(iZzg, arrayListZzc, arrayListZzc2, arrayListZzc3, strZzo, iZzg2, strZzo2, bundleZzq, strZzo3, strZzo4, iZzg3, strZzo5, strZzo6);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfu, reason: merged with bridge method [inline-methods] */
    public AppContentCardEntity[] newArray(int i) {
        return new AppContentCardEntity[i];
    }
}
