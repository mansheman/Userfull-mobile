package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class zzca extends zzak {
    private static final String zzaLE = com.google.android.gms.internal.zzae.ARG0.toString();
    private static final String zzaMC = com.google.android.gms.internal.zzae.ARG1.toString();

    public zzca(String str) {
        super(str, zzaLE, zzaMC);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzE(Map<String, zzag.zza> map) {
        Iterator<zzag.zza> it = map.values().iterator();
        while (it.hasNext()) {
            if (it.next() == zzdf.zzzQ()) {
                return zzdf.zzI(false);
            }
        }
        zzag.zza zzaVar = map.get(zzaLE);
        zzag.zza zzaVar2 = map.get(zzaMC);
        return zzdf.zzI(Boolean.valueOf((zzaVar == null || zzaVar2 == null) ? false : zza(zzaVar, zzaVar2, map)));
    }

    protected abstract boolean zza(zzag.zza zzaVar, zzag.zza zzaVar2, Map<String, zzag.zza> map);

    @Override // com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ String zzyM() {
        return super.zzyM();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ Set zzyN() {
        return super.zzyN();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzyh() {
        return true;
    }
}
