package com.google.android.gms.internal;

import android.os.Binder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@zzgd
/* loaded from: classes.dex */
public class zzbw {
    private final Collection<zzbs> zzty = new ArrayList();
    private final Collection<zzbv<String>> zztz = new ArrayList();
    private final Collection<zzbv<String>> zztA = new ArrayList();

    public void zza(zzbs zzbsVar) {
        this.zzty.add(zzbsVar);
    }

    public void zza(zzbv<String> zzbvVar) {
        this.zztz.add(zzbvVar);
    }

    public void zzb(zzbv<String> zzbvVar) {
        this.zztA.add(zzbvVar);
    }

    public List<String> zzda() {
        ArrayList arrayList = new ArrayList();
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator<zzbv<String>> it = this.zztA.iterator();
            while (it.hasNext()) {
                String str = it.next().zzcZ().get();
                if (str != null) {
                    arrayList.add(str);
                }
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public List<String> zzdb() {
        ArrayList arrayList = new ArrayList();
        Iterator<zzbv<String>> it = this.zztz.iterator();
        while (it.hasNext()) {
            String str = it.next().get();
            if (str != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }
}
