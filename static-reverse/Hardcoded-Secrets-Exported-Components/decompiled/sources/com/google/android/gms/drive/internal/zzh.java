package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.Contents;

/* loaded from: classes.dex */
public class zzh implements Parcelable.Creator<CloseContentsRequest> {
    static void zza(CloseContentsRequest closeContentsRequest, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, closeContentsRequest.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) closeContentsRequest.zzaes, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, closeContentsRequest.zzaew, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, closeContentsRequest.zzaeu);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaH, reason: merged with bridge method [inline-methods] */
    public CloseContentsRequest createFromParcel(Parcel parcel) {
        int iZzg;
        Boolean boolZzd;
        Contents contents;
        int iZzg2;
        Boolean bool = null;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Contents contents2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    int i3 = i;
                    boolZzd = bool;
                    contents = contents2;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i3;
                    break;
                case 2:
                    iZzg2 = i2;
                    Boolean bool2 = bool;
                    contents = (Contents) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Contents.CREATOR);
                    iZzg = i;
                    boolZzd = bool2;
                    break;
                case 3:
                    contents = contents2;
                    iZzg2 = i2;
                    int i4 = i;
                    boolZzd = com.google.android.gms.common.internal.safeparcel.zza.zzd(parcel, iZzaa);
                    iZzg = i4;
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    boolZzd = bool;
                    contents = contents2;
                    iZzg2 = i2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iZzg = i;
                    boolZzd = bool;
                    contents = contents2;
                    iZzg2 = i2;
                    break;
            }
            i2 = iZzg2;
            contents2 = contents;
            bool = boolZzd;
            i = iZzg;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new CloseContentsRequest(i2, contents2, bool, i);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcr, reason: merged with bridge method [inline-methods] */
    public CloseContentsRequest[] newArray(int i) {
        return new CloseContentsRequest[i];
    }
}
