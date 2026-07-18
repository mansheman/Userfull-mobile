package com.google.android.gms.games.appcontent;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AppContentSectionEntityCreator implements Parcelable.Creator<AppContentSectionEntity> {
    static void zza(AppContentSectionEntity appContentSectionEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, appContentSectionEntity.getActions(), false);
        zzb.zzc(parcel, 1000, appContentSectionEntity.getVersionCode());
        zzb.zzc(parcel, 3, appContentSectionEntity.zzsj(), false);
        zzb.zza(parcel, 4, appContentSectionEntity.zzrP(), false);
        zzb.zza(parcel, 5, appContentSectionEntity.getExtras(), false);
        zzb.zza(parcel, 6, appContentSectionEntity.zzsb(), false);
        zzb.zza(parcel, 7, appContentSectionEntity.getTitle(), false);
        zzb.zza(parcel, 8, appContentSectionEntity.getType(), false);
        zzb.zza(parcel, 9, appContentSectionEntity.getId(), false);
        zzb.zza(parcel, 10, appContentSectionEntity.zzsk(), false);
        zzb.zzc(parcel, 14, appContentSectionEntity.zzrZ(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdA, reason: merged with bridge method [inline-methods] */
    public AppContentSectionEntity createFromParcel(Parcel parcel) {
        ArrayList arrayListZzc = null;
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        Bundle bundleZzq = null;
        String strZzo6 = null;
        ArrayList arrayListZzc2 = null;
        ArrayList arrayListZzc3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzc3 = zza.zzc(parcel, iZzaa, AppContentActionEntity.CREATOR);
                    break;
                case 3:
                    arrayListZzc2 = zza.zzc(parcel, iZzaa, AppContentCardEntity.CREATOR);
                    break;
                case 4:
                    strZzo6 = zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    bundleZzq = zza.zzq(parcel, iZzaa);
                    break;
                case 6:
                    strZzo5 = zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 10:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 14:
                    arrayListZzc = zza.zzc(parcel, iZzaa, AppContentAnnotationEntity.CREATOR);
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
        return new AppContentSectionEntity(iZzg, arrayListZzc3, arrayListZzc2, strZzo6, bundleZzq, strZzo5, strZzo4, strZzo3, strZzo2, strZzo, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfw, reason: merged with bridge method [inline-methods] */
    public AppContentSectionEntity[] newArray(int i) {
        return new AppContentSectionEntity[i];
    }
}
