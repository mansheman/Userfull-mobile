package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<Bucket> {
    static void zza(Bucket bucket, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, bucket.zzkt());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, bucket.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, bucket.zzqs());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) bucket.getSession(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, bucket.zzqq());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, bucket.getDataSets(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, bucket.getBucketType());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, bucket.zzqr());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzck, reason: merged with bridge method [inline-methods] */
    public Bucket createFromParcel(Parcel parcel) {
        long jZzi = 0;
        ArrayList arrayListZzc = null;
        boolean zZzc = false;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        int iZzg2 = 0;
        Session session = null;
        long jZzi2 = 0;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 3:
                    session = (Session) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Session.CREATOR);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, DataSet.CREATOR);
                    break;
                case 6:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 7:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new Bucket(iZzg3, jZzi2, jZzi, session, iZzg2, arrayListZzc, iZzg, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzec, reason: merged with bridge method [inline-methods] */
    public Bucket[] newArray(int i) {
        return new Bucket[i];
    }
}
