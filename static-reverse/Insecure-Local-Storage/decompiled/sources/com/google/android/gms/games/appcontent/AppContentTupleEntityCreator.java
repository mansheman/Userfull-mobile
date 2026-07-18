package com.google.android.gms.games.appcontent;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class AppContentTupleEntityCreator implements Parcelable.Creator<AppContentTupleEntity> {
    static void zza(AppContentTupleEntity appContentTupleEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, appContentTupleEntity.getName(), false);
        zzb.zzc(parcel, 1000, appContentTupleEntity.getVersionCode());
        zzb.zza(parcel, 2, appContentTupleEntity.getValue(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdB, reason: merged with bridge method [inline-methods] */
    public AppContentTupleEntity createFromParcel(Parcel parcel) {
        String strZzo = null;
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        String strZzo2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
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
        return new AppContentTupleEntity(iZzg, strZzo2, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfx, reason: merged with bridge method [inline-methods] */
    public AppContentTupleEntity[] newArray(int i) {
        return new AppContentTupleEntity[i];
    }
}
