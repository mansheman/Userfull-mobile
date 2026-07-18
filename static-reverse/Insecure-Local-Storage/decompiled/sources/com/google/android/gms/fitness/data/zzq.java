package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzq implements Parcelable.Creator<SessionDataSet> {
    static void zza(SessionDataSet sessionDataSet, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable) sessionDataSet.getSession(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, sessionDataSet.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) sessionDataSet.zzqK(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcw, reason: merged with bridge method [inline-methods] */
    public SessionDataSet createFromParcel(Parcel parcel) {
        DataSet dataSet;
        Session session;
        int iZzg;
        DataSet dataSet2 = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        Session session2 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    Session session3 = (Session) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Session.CREATOR);
                    iZzg = i;
                    dataSet = dataSet2;
                    session = session3;
                    break;
                case 2:
                    dataSet = (DataSet) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, DataSet.CREATOR);
                    session = session2;
                    iZzg = i;
                    break;
                case 1000:
                    DataSet dataSet3 = dataSet2;
                    session = session2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    dataSet = dataSet3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    dataSet = dataSet2;
                    session = session2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            session2 = session;
            dataSet2 = dataSet;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SessionDataSet(i, session2, dataSet2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeq, reason: merged with bridge method [inline-methods] */
    public SessionDataSet[] newArray(int i) {
        return new SessionDataSet[i];
    }
}
