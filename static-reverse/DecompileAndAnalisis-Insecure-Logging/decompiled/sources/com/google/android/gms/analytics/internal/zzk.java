package com.google.android.gms.analytics.internal;

import com.google.android.gms.internal.zznx;

/* loaded from: classes.dex */
public class zzk extends zzd {
    private final zznx zzKm;

    zzk(zzf zzfVar) {
        super(zzfVar);
        this.zzKm = new zznx();
    }

    public void zzhi() {
        zzan zzanVarZzhm = zzhm();
        String strZzjL = zzanVarZzhm.zzjL();
        if (strZzjL != null) {
            this.zzKm.setAppName(strZzjL);
        }
        String strZzjN = zzanVarZzhm.zzjN();
        if (strZzjN != null) {
            this.zzKm.setAppVersion(strZzjN);
        }
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhn() {
        zzhS().zzwc().zza(this.zzKm);
        zzhi();
    }

    public zznx zzix() {
        zzia();
        return this.zzKm;
    }
}
