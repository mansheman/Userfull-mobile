package com.google.android.gms.nearby.bootstrap.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zze implements Parcelable.Creator<EnableTargetRequest> {
    static void zza(EnableTargetRequest enableTargetRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, enableTargetRequest.getName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, enableTargetRequest.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, enableTargetRequest.getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, enableTargetRequest.zzwO(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, enableTargetRequest.zzwP(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, enableTargetRequest.zzqU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, enableTargetRequest.zzwK());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfj, reason: merged with bridge method [inline-methods] */
    public EnableTargetRequest createFromParcel(Parcel parcel) {
        byte bZze = 0;
        IBinder iBinderZzp = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        IBinder iBinderZzp2 = null;
        IBinder iBinderZzp3 = null;
        String strZzo = null;
        String strZzo2 = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    iBinderZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 4:
                    iBinderZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 5:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 6:
                    bZze = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 1000:
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
        return new EnableTargetRequest(iZzg, strZzo2, strZzo, bZze, iBinderZzp3, iBinderZzp2, iBinderZzp);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhR, reason: merged with bridge method [inline-methods] */
    public EnableTargetRequest[] newArray(int i) {
        return new EnableTargetRequest[i];
    }
}
