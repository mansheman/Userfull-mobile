package com.google.android.gms.ads.internal.overlay;

import com.google.android.gms.internal.zzhl;

/* loaded from: classes.dex */
class zzm implements Runnable {
    private boolean zzAq = false;
    private zzh zzzl;

    zzm(zzh zzhVar) {
        this.zzzl = zzhVar;
    }

    public void cancel() {
        this.zzAq = true;
        zzhl.zzGk.removeCallbacks(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.zzAq) {
            return;
        }
        this.zzzl.zzeL();
        zzeY();
    }

    public void zzeY() {
        zzhl.zzGk.postDelayed(this, 250L);
    }
}
