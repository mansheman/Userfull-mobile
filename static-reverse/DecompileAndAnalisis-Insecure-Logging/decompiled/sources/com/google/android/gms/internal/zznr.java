package com.google.android.gms.internal;

import com.google.android.gms.internal.zznr;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class zznr<T extends zznr> {
    private final zzns zzaEe;
    protected final zzno zzaEf;
    private final List<zznp> zzaEg;

    protected zznr(zzns zznsVar, zzlb zzlbVar) {
        com.google.android.gms.common.internal.zzu.zzu(zznsVar);
        this.zzaEe = zznsVar;
        this.zzaEg = new ArrayList();
        zzno zznoVar = new zzno(this, zzlbVar);
        zznoVar.zzvZ();
        this.zzaEf = zznoVar;
    }

    protected void zza(zzno zznoVar) {
    }

    protected void zzd(zzno zznoVar) {
        Iterator<zznp> it = this.zzaEg.iterator();
        while (it.hasNext()) {
            it.next().zza(this, zznoVar);
        }
    }

    public zzno zzhc() {
        zzno zznoVarZzvP = this.zzaEf.zzvP();
        zzd(zznoVarZzvP);
        return zznoVarZzvP;
    }

    protected zzns zzvX() {
        return this.zzaEe;
    }

    public zzno zzwa() {
        return this.zzaEf;
    }

    public List<zznu> zzwb() {
        return this.zzaEf.zzvR();
    }
}
