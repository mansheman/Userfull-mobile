package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class zznx extends zznq<zznx> {
    private String zzLU;
    private String zzLV;
    private String zzaEw;
    private String zzaEx;

    public void setAppId(String value) {
        this.zzaEw = value;
    }

    public void setAppInstallerId(String value) {
        this.zzaEx = value;
    }

    public void setAppName(String value) {
        this.zzLU = value;
    }

    public void setAppVersion(String value) {
        this.zzLV = value;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put("appName", this.zzLU);
        map.put("appVersion", this.zzLV);
        map.put("appId", this.zzaEw);
        map.put("appInstallerId", this.zzaEx);
        return zzy(map);
    }

    @Override // com.google.android.gms.internal.zznq
    public void zza(zznx zznxVar) {
        if (!TextUtils.isEmpty(this.zzLU)) {
            zznxVar.setAppName(this.zzLU);
        }
        if (!TextUtils.isEmpty(this.zzLV)) {
            zznxVar.setAppVersion(this.zzLV);
        }
        if (!TextUtils.isEmpty(this.zzaEw)) {
            zznxVar.setAppId(this.zzaEw);
        }
        if (TextUtils.isEmpty(this.zzaEx)) {
            return;
        }
        zznxVar.setAppInstallerId(this.zzaEx);
    }

    public String zzjL() {
        return this.zzLU;
    }

    public String zzjN() {
        return this.zzLV;
    }

    public String zzsK() {
        return this.zzaEw;
    }

    public String zzwi() {
        return this.zzaEx;
    }
}
