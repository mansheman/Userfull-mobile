package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class GameEntityCreator implements Parcelable.Creator<GameEntity> {
    static void zza(GameEntity gameEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, gameEntity.getApplicationId(), false);
        zzb.zza(parcel, 2, gameEntity.getDisplayName(), false);
        zzb.zza(parcel, 3, gameEntity.getPrimaryCategory(), false);
        zzb.zza(parcel, 4, gameEntity.getSecondaryCategory(), false);
        zzb.zza(parcel, 5, gameEntity.getDescription(), false);
        zzb.zza(parcel, 6, gameEntity.getDeveloperName(), false);
        zzb.zza(parcel, 7, (Parcelable) gameEntity.getIconImageUri(), i, false);
        zzb.zza(parcel, 8, (Parcelable) gameEntity.getHiResImageUri(), i, false);
        zzb.zza(parcel, 9, (Parcelable) gameEntity.getFeaturedImageUri(), i, false);
        zzb.zza(parcel, 10, gameEntity.zzrC());
        zzb.zza(parcel, 11, gameEntity.zzrE());
        zzb.zza(parcel, 12, gameEntity.zzrF(), false);
        zzb.zzc(parcel, 13, gameEntity.zzrG());
        zzb.zzc(parcel, 14, gameEntity.getAchievementTotalCount());
        zzb.zzc(parcel, 15, gameEntity.getLeaderboardCount());
        zzb.zza(parcel, 17, gameEntity.isTurnBasedMultiplayerEnabled());
        zzb.zza(parcel, 16, gameEntity.isRealTimeMultiplayerEnabled());
        zzb.zzc(parcel, 1000, gameEntity.getVersionCode());
        zzb.zza(parcel, 19, gameEntity.getHiResImageUrl(), false);
        zzb.zza(parcel, 18, gameEntity.getIconImageUrl(), false);
        zzb.zza(parcel, 21, gameEntity.isMuted());
        zzb.zza(parcel, 20, gameEntity.getFeaturedImageUrl(), false);
        zzb.zza(parcel, 23, gameEntity.areSnapshotsEnabled());
        zzb.zza(parcel, 22, gameEntity.zzrD());
        zzb.zza(parcel, 25, gameEntity.hasGamepadSupport());
        zzb.zza(parcel, 24, gameEntity.getThemeColor(), false);
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdr, reason: merged with bridge method [inline-methods] */
    public GameEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        String strZzo6 = null;
        Uri uri = null;
        Uri uri2 = null;
        Uri uri3 = null;
        boolean zZzc = false;
        boolean zZzc2 = false;
        String strZzo7 = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        int iZzg4 = 0;
        boolean zZzc3 = false;
        boolean zZzc4 = false;
        String strZzo8 = null;
        String strZzo9 = null;
        String strZzo10 = null;
        boolean zZzc5 = false;
        boolean zZzc6 = false;
        boolean zZzc7 = false;
        String strZzo11 = null;
        boolean zZzc8 = false;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    strZzo5 = zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    strZzo6 = zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    uri = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 8:
                    uri2 = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 9:
                    uri3 = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 10:
                    zZzc = zza.zzc(parcel, iZzaa);
                    break;
                case 11:
                    zZzc2 = zza.zzc(parcel, iZzaa);
                    break;
                case 12:
                    strZzo7 = zza.zzo(parcel, iZzaa);
                    break;
                case 13:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 14:
                    iZzg3 = zza.zzg(parcel, iZzaa);
                    break;
                case 15:
                    iZzg4 = zza.zzg(parcel, iZzaa);
                    break;
                case 16:
                    zZzc3 = zza.zzc(parcel, iZzaa);
                    break;
                case 17:
                    zZzc4 = zza.zzc(parcel, iZzaa);
                    break;
                case 18:
                    strZzo8 = zza.zzo(parcel, iZzaa);
                    break;
                case 19:
                    strZzo9 = zza.zzo(parcel, iZzaa);
                    break;
                case 20:
                    strZzo10 = zza.zzo(parcel, iZzaa);
                    break;
                case 21:
                    zZzc5 = zza.zzc(parcel, iZzaa);
                    break;
                case 22:
                    zZzc6 = zza.zzc(parcel, iZzaa);
                    break;
                case 23:
                    zZzc7 = zza.zzc(parcel, iZzaa);
                    break;
                case 24:
                    strZzo11 = zza.zzo(parcel, iZzaa);
                    break;
                case 25:
                    zZzc8 = zza.zzc(parcel, iZzaa);
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
        return new GameEntity(iZzg, strZzo, strZzo2, strZzo3, strZzo4, strZzo5, strZzo6, uri, uri2, uri3, zZzc, zZzc2, strZzo7, iZzg2, iZzg3, iZzg4, zZzc3, zZzc4, strZzo8, strZzo9, strZzo10, zZzc5, zZzc6, zZzc7, strZzo11, zZzc8);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfm, reason: merged with bridge method [inline-methods] */
    public GameEntity[] newArray(int i) {
        return new GameEntity[i];
    }
}
