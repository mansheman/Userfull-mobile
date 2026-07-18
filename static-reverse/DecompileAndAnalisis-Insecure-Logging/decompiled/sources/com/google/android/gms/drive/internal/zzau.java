package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.Contents;

/* loaded from: classes.dex */
public class zzau implements Parcelable.Creator<OnContentsResponse> {
    static void zza(OnContentsResponse onContentsResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, onContentsResponse.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) onContentsResponse.zzafa, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, onContentsResponse.zzage);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaZ, reason: merged with bridge method [inline-methods] */
    public OnContentsResponse createFromParcel(Parcel parcel) {
        boolean zZzc;
        Contents contents;
        int iZzg;
        boolean z = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Contents contents2 = null;
        int i = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    boolean z2 = z;
                    contents = contents2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    zZzc = z2;
                    break;
                case 2:
                    Contents contents3 = (Contents) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Contents.CREATOR);
                    iZzg = i;
                    zZzc = z;
                    contents = contents3;
                    break;
                case 3:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    contents = contents2;
                    iZzg = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    zZzc = z;
                    contents = contents2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            contents2 = contents;
            z = zZzc;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new OnContentsResponse(i, contents2, z);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcO, reason: merged with bridge method [inline-methods] */
    public OnContentsResponse[] newArray(int i) {
        return new OnContentsResponse[i];
    }
}
