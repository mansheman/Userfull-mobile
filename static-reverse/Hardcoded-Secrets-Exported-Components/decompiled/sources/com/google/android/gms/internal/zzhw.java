package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
class zzhw {
    private final Object zzGQ = new Object();
    private final List<Runnable> zzGR = new ArrayList();
    private final List<Runnable> zzGS = new ArrayList();
    private boolean zzGT = false;

    private void zzc(Runnable runnable) {
        zzhk.zza(runnable);
    }

    private void zzd(Runnable runnable) {
        com.google.android.gms.ads.internal.util.client.zza.zzGF.post(runnable);
    }

    public void zzb(Runnable runnable) {
        synchronized (this.zzGQ) {
            if (this.zzGT) {
                zzc(runnable);
            } else {
                this.zzGR.add(runnable);
            }
        }
    }

    public void zzgy() {
        synchronized (this.zzGQ) {
            if (this.zzGT) {
                return;
            }
            Iterator<Runnable> it = this.zzGR.iterator();
            while (it.hasNext()) {
                zzc(it.next());
            }
            Iterator<Runnable> it2 = this.zzGS.iterator();
            while (it2.hasNext()) {
                zzd(it2.next());
            }
            this.zzGR.clear();
            this.zzGS.clear();
            this.zzGT = true;
        }
    }
}
