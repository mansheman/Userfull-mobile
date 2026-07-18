package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<ConnectionConfiguration> {
    static void zza(ConnectionConfiguration connectionConfiguration, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, connectionConfiguration.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, connectionConfiguration.getName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, connectionConfiguration.getAddress(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, connectionConfiguration.getType());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, connectionConfiguration.getRole());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, connectionConfiguration.isEnabled());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, connectionConfiguration.isConnected());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, connectionConfiguration.zzAP(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, connectionConfiguration.zzAQ());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, connectionConfiguration.getNodeId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgI, reason: merged with bridge method [inline-methods] */
    public ConnectionConfiguration createFromParcel(Parcel parcel) {
        String strZzo = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo2 = null;
        boolean zZzc2 = false;
        boolean zZzc3 = false;
        int iZzg = 0;
        int iZzg2 = 0;
        String strZzo3 = null;
        String strZzo4 = null;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 6:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 7:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 8:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 10:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ConnectionConfiguration(iZzg3, strZzo4, strZzo3, iZzg2, iZzg, zZzc3, zZzc2, strZzo2, zZzc, strZzo);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjN, reason: merged with bridge method [inline-methods] */
    public ConnectionConfiguration[] newArray(int i) {
        return new ConnectionConfiguration[i];
    }
}
