package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<ChannelEventParcelable> {
    static void zza(ChannelEventParcelable channelEventParcelable, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, channelEventParcelable.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) channelEventParcelable.zzaTP, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, channelEventParcelable.type);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, channelEventParcelable.zzaTN);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, channelEventParcelable.zzaTO);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgO, reason: merged with bridge method [inline-methods] */
    public ChannelEventParcelable createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ChannelImpl channelImpl = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        int iZzg4 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    channelImpl = (ChannelImpl) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ChannelImpl.CREATOR);
                    break;
                case 3:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ChannelEventParcelable(iZzg4, channelImpl, iZzg3, iZzg2, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzjV, reason: merged with bridge method [inline-methods] */
    public ChannelEventParcelable[] newArray(int i) {
        return new ChannelEventParcelable[i];
    }
}
