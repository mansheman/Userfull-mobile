package com.google.android.gms.games.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class ConnectionInfoCreator implements Parcelable.Creator<ConnectionInfo> {
    static void zza(ConnectionInfo connectionInfo, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, connectionInfo.zzsr(), false);
        zzb.zzc(parcel, 1000, connectionInfo.getVersionCode());
        zzb.zzc(parcel, 2, connectionInfo.zzss());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdC, reason: merged with bridge method [inline-methods] */
    public ConnectionInfo createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = zza.zzab(parcel);
        String strZzo = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    iZzg = zza.zzg(parcel, iZzaa);
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
        return new ConnectionInfo(iZzg2, strZzo, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfB, reason: merged with bridge method [inline-methods] */
    public ConnectionInfo[] newArray(int i) {
        return new ConnectionInfo[i];
    }
}
