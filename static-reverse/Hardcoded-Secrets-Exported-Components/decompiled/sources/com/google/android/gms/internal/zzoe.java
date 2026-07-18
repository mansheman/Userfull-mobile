package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class zzoe extends zznq<zzoe> {
    public String zzaER;
    public String zzaES;
    public String zzuO;

    public String getAction() {
        return this.zzuO;
    }

    public String getTarget() {
        return this.zzaES;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put("network", this.zzaER);
        map.put("action", this.zzuO);
        map.put("target", this.zzaES);
        return zzy(map);
    }

    @Override // com.google.android.gms.internal.zznq
    public void zza(zzoe zzoeVar) {
        if (!TextUtils.isEmpty(this.zzaER)) {
            zzoeVar.zzdL(this.zzaER);
        }
        if (!TextUtils.isEmpty(this.zzuO)) {
            zzoeVar.zzdH(this.zzuO);
        }
        if (TextUtils.isEmpty(this.zzaES)) {
            return;
        }
        zzoeVar.zzdM(this.zzaES);
    }

    public void zzdH(String str) {
        this.zzuO = str;
    }

    public void zzdL(String str) {
        this.zzaER = str;
    }

    public void zzdM(String str) {
        this.zzaES = str;
    }

    public String zzwI() {
        return this.zzaER;
    }
}
