package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class zzqd {
    private final List<zzpy> zzaPn = new ArrayList();

    public String getId() {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (zzpy zzpyVar : this.zzaPn) {
            if (z) {
                z = false;
            } else {
                sb.append("#");
            }
            sb.append(zzpyVar.getContainerId());
        }
        return sb.toString();
    }

    public List<zzpy> zzAf() {
        return this.zzaPn;
    }

    public zzqd zzb(zzpy zzpyVar) throws IllegalArgumentException {
        com.google.android.gms.common.internal.zzu.zzu(zzpyVar);
        Iterator<zzpy> it = this.zzaPn.iterator();
        while (it.hasNext()) {
            if (it.next().getContainerId().equals(zzpyVar.getContainerId())) {
                throw new IllegalArgumentException("The container is already being requested. " + zzpyVar.getContainerId());
            }
        }
        this.zzaPn.add(zzpyVar);
        return this;
    }
}
