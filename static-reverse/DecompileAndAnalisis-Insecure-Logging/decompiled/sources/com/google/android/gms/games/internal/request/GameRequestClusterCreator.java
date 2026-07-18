package com.google.android.gms.games.internal.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.request.GameRequestEntity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GameRequestClusterCreator implements Parcelable.Creator<GameRequestCluster> {
    static void zza(GameRequestCluster gameRequestCluster, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, gameRequestCluster.zztB(), false);
        zzb.zzc(parcel, 1000, gameRequestCluster.getVersionCode());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdH, reason: merged with bridge method [inline-methods] */
    public GameRequestCluster createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzc = zza.zzc(parcel, iZzaa, GameRequestEntity.CREATOR);
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
        return new GameRequestCluster(iZzg, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfP, reason: merged with bridge method [inline-methods] */
    public GameRequestCluster[] newArray(int i) {
        return new GameRequestCluster[i];
    }
}
