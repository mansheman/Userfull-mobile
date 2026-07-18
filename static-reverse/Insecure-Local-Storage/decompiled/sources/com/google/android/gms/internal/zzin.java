package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzin extends zznq<zzin> {
    private Map<Integer, Double> zzJb = new HashMap(4);

    public String toString() {
        HashMap map = new HashMap();
        for (Map.Entry<Integer, Double> entry : this.zzJb.entrySet()) {
            map.put("metric" + entry.getKey(), entry.getValue());
        }
        return zzy(map);
    }

    @Override // com.google.android.gms.internal.zznq
    public void zza(zzin zzinVar) {
        zzinVar.zzJb.putAll(this.zzJb);
    }

    public Map<Integer, Double> zzhu() {
        return Collections.unmodifiableMap(this.zzJb);
    }
}
