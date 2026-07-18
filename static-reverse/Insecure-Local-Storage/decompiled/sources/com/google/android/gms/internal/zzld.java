package com.google.android.gms.internal;

import android.os.SystemClock;

/* loaded from: classes.dex */
public final class zzld implements zzlb {
    private static zzld zzacK;

    public static synchronized zzlb zzoQ() {
        if (zzacK == null) {
            zzacK = new zzld();
        }
        return zzacK;
    }

    @Override // com.google.android.gms.internal.zzlb
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override // com.google.android.gms.internal.zzlb
    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }
}
