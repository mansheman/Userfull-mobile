package com.google.android.gms.nearby.sharing.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.nearby.sharing.SharedContent;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzf implements Parcelable.Creator<ProvideContentRequest> {
    static void zza(ProvideContentRequest provideContentRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, provideContentRequest.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, provideContentRequest.zzaGp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, provideContentRequest.zzxi(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, provideContentRequest.zzaGr, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, provideContentRequest.zzaGs);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, provideContentRequest.zzxa(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfC, reason: merged with bridge method [inline-methods] */
    public ProvideContentRequest createFromParcel(Parcel parcel) {
        IBinder iBinderZzp = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        long jZzi = 0;
        ArrayList arrayListZzc = null;
        IBinder iBinderZzp2 = null;
        IBinder iBinderZzp3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iBinderZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 3:
                    iBinderZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, SharedContent.CREATOR);
                    break;
                case 5:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 6:
                    iBinderZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ProvideContentRequest(iZzg, iBinderZzp3, iBinderZzp2, arrayListZzc, jZzi, iBinderZzp);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzir, reason: merged with bridge method [inline-methods] */
    public ProvideContentRequest[] newArray(int i) {
        return new ProvideContentRequest[i];
    }
}
