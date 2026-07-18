package com.google.android.gms.common.api;

import com.google.android.gms.common.data.DataHolder;

/* loaded from: classes.dex */
public abstract class zzc implements Releasable, Result {
    protected final Status zzOt;
    protected final DataHolder zzWu;

    protected zzc(DataHolder dataHolder) {
        this(dataHolder, new Status(dataHolder.getStatusCode()));
    }

    protected zzc(DataHolder dataHolder, Status status) {
        this.zzOt = status;
        this.zzWu = dataHolder;
    }

    @Override // com.google.android.gms.common.api.Result
    public Status getStatus() {
        return this.zzOt;
    }

    @Override // com.google.android.gms.common.api.Releasable
    public void release() {
        if (this.zzWu != null) {
            this.zzWu.close();
        }
    }
}
