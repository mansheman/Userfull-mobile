package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* loaded from: classes.dex */
public class ReadRawResult implements Result, SafeParcelable {
    public static final Parcelable.Creator<ReadRawResult> CREATOR = new zzi();
    private final int zzCY;
    private final DataHolder zzWu;

    ReadRawResult(int versionCode, DataHolder dataHolder) {
        this.zzCY = versionCode;
        this.zzWu = dataHolder;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.api.Result
    public Status getStatus() {
        return new Status(this.zzWu.getStatusCode());
    }

    int getVersionCode() {
        return this.zzCY;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        zzi.zza(this, dest, flags);
    }

    DataHolder zzpx() {
        return this.zzWu;
    }
}
