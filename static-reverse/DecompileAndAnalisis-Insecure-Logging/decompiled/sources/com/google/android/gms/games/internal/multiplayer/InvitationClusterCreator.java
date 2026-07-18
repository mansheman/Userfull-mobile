package com.google.android.gms.games.internal.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.multiplayer.InvitationEntity;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class InvitationClusterCreator implements Parcelable.Creator<ZInvitationCluster> {
    static void zza(ZInvitationCluster zInvitationCluster, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, zInvitationCluster.zzto(), false);
        zzb.zzc(parcel, 1000, zInvitationCluster.getVersionCode());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdF, reason: merged with bridge method [inline-methods] */
    public ZInvitationCluster createFromParcel(Parcel parcel) {
        int iZzab = zza.zzab(parcel);
        int iZzg = 0;
        ArrayList arrayListZzc = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    arrayListZzc = zza.zzc(parcel, iZzaa, InvitationEntity.CREATOR);
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
        return new ZInvitationCluster(iZzg, arrayListZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfM, reason: merged with bridge method [inline-methods] */
    public ZInvitationCluster[] newArray(int i) {
        return new ZInvitationCluster[i];
    }
}
