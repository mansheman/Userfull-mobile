package com.google.android.gms.games.achievement;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.PlayerEntity;

/* loaded from: classes.dex */
public class AchievementEntityCreator implements Parcelable.Creator<AchievementEntity> {
    static void zza(AchievementEntity achievementEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, achievementEntity.getAchievementId(), false);
        zzb.zzc(parcel, 2, achievementEntity.getType());
        zzb.zza(parcel, 3, achievementEntity.getName(), false);
        zzb.zza(parcel, 4, achievementEntity.getDescription(), false);
        zzb.zza(parcel, 5, (Parcelable) achievementEntity.getUnlockedImageUri(), i, false);
        zzb.zza(parcel, 6, achievementEntity.getUnlockedImageUrl(), false);
        zzb.zza(parcel, 7, (Parcelable) achievementEntity.getRevealedImageUri(), i, false);
        zzb.zza(parcel, 8, achievementEntity.getRevealedImageUrl(), false);
        zzb.zzc(parcel, 9, achievementEntity.getTotalSteps());
        zzb.zza(parcel, 10, achievementEntity.getFormattedTotalSteps(), false);
        zzb.zza(parcel, 11, (Parcelable) achievementEntity.getPlayer(), i, false);
        zzb.zzc(parcel, 12, achievementEntity.getState());
        zzb.zzc(parcel, 13, achievementEntity.getCurrentSteps());
        zzb.zza(parcel, 14, achievementEntity.getFormattedCurrentSteps(), false);
        zzb.zza(parcel, 15, achievementEntity.getLastUpdatedTimestamp());
        zzb.zza(parcel, 16, achievementEntity.getXpValue());
        zzb.zzc(parcel, 1000, achievementEntity.getVersionCode());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdv, reason: merged with bridge method [inline-methods] */
    public AchievementEntity createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        int iZzg2 = 0;
        String strZzo2 = null;
        String strZzo3 = null;
        Uri uri = null;
        String strZzo4 = null;
        Uri uri2 = null;
        String strZzo5 = null;
        int iZzg3 = 0;
        String strZzo6 = null;
        PlayerEntity playerEntity = null;
        int iZzg4 = 0;
        int iZzg5 = 0;
        String strZzo7 = null;
        long jZzi = 0;
        long jZzi2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 5:
                    uri = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 6:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    uri2 = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 8:
                    strZzo5 = zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    iZzg3 = zza.zzg(parcel, iZzaa);
                    break;
                case 10:
                    strZzo6 = zza.zzo(parcel, iZzaa);
                    break;
                case 11:
                    playerEntity = (PlayerEntity) zza.zza(parcel, iZzaa, PlayerEntity.CREATOR);
                    break;
                case 12:
                    iZzg4 = zza.zzg(parcel, iZzaa);
                    break;
                case 13:
                    iZzg5 = zza.zzg(parcel, iZzaa);
                    break;
                case 14:
                    strZzo7 = zza.zzo(parcel, iZzaa);
                    break;
                case 15:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 16:
                    jZzi2 = zza.zzi(parcel, iZzaa);
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
        return new AchievementEntity(iZzg, strZzo, iZzg2, strZzo2, strZzo3, uri, strZzo4, uri2, strZzo5, iZzg3, strZzo6, playerEntity, iZzg4, iZzg5, strZzo7, jZzi, jZzi2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfr, reason: merged with bridge method [inline-methods] */
    public AchievementEntity[] newArray(int i) {
        return new AchievementEntity[i];
    }
}
