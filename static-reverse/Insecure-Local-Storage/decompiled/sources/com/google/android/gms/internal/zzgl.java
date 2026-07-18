package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzgk;
import java.util.WeakHashMap;

@zzgd
/* loaded from: classes.dex */
public final class zzgl {
    private WeakHashMap<Context, zza> zzEH = new WeakHashMap<>();

    private class zza {
        public final long zzEI = com.google.android.gms.ads.internal.zzo.zzbz().currentTimeMillis();
        public final zzgk zzEJ;

        public zza(zzgk zzgkVar) {
            this.zzEJ = zzgkVar;
        }

        public boolean hasExpired() {
            return zzbz.zzuw.get().longValue() + this.zzEI < com.google.android.gms.ads.internal.zzo.zzbz().currentTimeMillis();
        }
    }

    public zzgk zzC(Context context) {
        zza zzaVar = this.zzEH.get(context);
        zzgk zzgkVarZzfJ = (zzaVar == null || zzaVar.hasExpired() || !zzbz.zzuv.get().booleanValue()) ? new zzgk.zza(context).zzfJ() : new zzgk.zza(context, zzaVar.zzEJ).zzfJ();
        this.zzEH.put(context, new zza(zzgkVarZzfJ));
        return zzgkVarZzfJ;
    }
}
