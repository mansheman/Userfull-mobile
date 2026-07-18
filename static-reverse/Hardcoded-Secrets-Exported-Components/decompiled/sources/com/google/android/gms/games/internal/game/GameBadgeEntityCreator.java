package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class GameBadgeEntityCreator implements Parcelable.Creator<GameBadgeEntity> {
    static void zza(GameBadgeEntity gameBadgeEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, gameBadgeEntity.getType());
        zzb.zzc(parcel, 1000, gameBadgeEntity.getVersionCode());
        zzb.zza(parcel, 2, gameBadgeEntity.getTitle(), false);
        zzb.zza(parcel, 3, gameBadgeEntity.getDescription(), false);
        zzb.zza(parcel, 4, (Parcelable) gameBadgeEntity.getIconImageUri(), i, false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdE, reason: merged with bridge method [inline-methods] */
    public GameBadgeEntity createFromParcel(Parcel parcel) {
        int iZzg = 0;
        Uri uri = null;
        int iZzab = zza.zzab(parcel);
        String strZzo = null;
        String strZzo2 = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    uri = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 1000:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GameBadgeEntity(iZzg2, iZzg, strZzo2, strZzo, uri);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfJ, reason: merged with bridge method [inline-methods] */
    public GameBadgeEntity[] newArray(int i) {
        return new GameBadgeEntity[i];
    }
}
