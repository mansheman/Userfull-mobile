package com.google.android.gms.internal;

/* loaded from: classes.dex */
public class zzhq {
    private long zzGC;
    private long zzGD = Long.MIN_VALUE;
    private Object zzqt = new Object();

    public zzhq(long j) {
        this.zzGC = j;
    }

    public boolean tryAcquire() {
        boolean z;
        synchronized (this.zzqt) {
            long jElapsedRealtime = com.google.android.gms.ads.internal.zzo.zzbz().elapsedRealtime();
            if (this.zzGD + this.zzGC > jElapsedRealtime) {
                z = false;
            } else {
                this.zzGD = jElapsedRealtime;
                z = true;
            }
        }
        return z;
    }
}
