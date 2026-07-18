package com.google.android.gms.games.internal.player;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class MostRecentGameInfoEntityCreator implements Parcelable.Creator<MostRecentGameInfoEntity> {
    static void zza(MostRecentGameInfoEntity mostRecentGameInfoEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, mostRecentGameInfoEntity.zztu(), false);
        zzb.zzc(parcel, 1000, mostRecentGameInfoEntity.getVersionCode());
        zzb.zza(parcel, 2, mostRecentGameInfoEntity.zztv(), false);
        zzb.zza(parcel, 3, mostRecentGameInfoEntity.zztw());
        zzb.zza(parcel, 4, (Parcelable) mostRecentGameInfoEntity.zztx(), i, false);
        zzb.zza(parcel, 5, (Parcelable) mostRecentGameInfoEntity.zzty(), i, false);
        zzb.zza(parcel, 6, (Parcelable) mostRecentGameInfoEntity.zztz(), i, false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdG, reason: merged with bridge method [inline-methods] */
    public MostRecentGameInfoEntity createFromParcel(Parcel parcel) {
        Uri uri = null;
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        long jZzi = 0;
        Uri uri2 = null;
        Uri uri3 = null;
        String strZzo = null;
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
                case 3:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 4:
                    uri3 = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 5:
                    uri2 = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 6:
                    uri = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
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
        return new MostRecentGameInfoEntity(iZzg, strZzo2, strZzo, jZzi, uri3, uri2, uri);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfO, reason: merged with bridge method [inline-methods] */
    public MostRecentGameInfoEntity[] newArray(int i) {
        return new MostRecentGameInfoEntity[i];
    }
}
