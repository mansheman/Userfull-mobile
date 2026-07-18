package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.media.MediaRouteProviderProtocol;

/* loaded from: classes.dex */
public class zzan extends zzd {
    protected boolean zzIx;
    protected int zzKR;
    protected String zzLU;
    protected String zzLV;
    protected int zzLX;
    protected boolean zzML;
    protected boolean zzMM;
    protected boolean zzMN;

    public zzan(zzf zzfVar) {
        super(zzfVar);
    }

    private static int zzbo(String str) {
        String lowerCase = str.toLowerCase();
        if ("verbose".equals(lowerCase)) {
            return 0;
        }
        if ("info".equals(lowerCase)) {
            return 1;
        }
        if ("warning".equals(lowerCase)) {
            return 2;
        }
        return MediaRouteProviderProtocol.SERVICE_DATA_ERROR.equals(lowerCase) ? 3 : -1;
    }

    public int getLogLevel() {
        zzia();
        return this.zzKR;
    }

    void zza(zzaa zzaaVar) {
        int iZzbo;
        zzaT("Loading global XML config values");
        if (zzaaVar.zzjK()) {
            String strZzjL = zzaaVar.zzjL();
            this.zzLU = strZzjL;
            zzb("XML config - app name", strZzjL);
        }
        if (zzaaVar.zzjM()) {
            String strZzjN = zzaaVar.zzjN();
            this.zzLV = strZzjN;
            zzb("XML config - app version", strZzjN);
        }
        if (zzaaVar.zzjO() && (iZzbo = zzbo(zzaaVar.zzjP())) >= 0) {
            this.zzKR = iZzbo;
            zza("XML config - log level", Integer.valueOf(iZzbo));
        }
        if (zzaaVar.zzjQ()) {
            int iZzjR = zzaaVar.zzjR();
            this.zzLX = iZzjR;
            this.zzMM = true;
            zzb("XML config - dispatch period (sec)", Integer.valueOf(iZzjR));
        }
        if (zzaaVar.zzjS()) {
            boolean zZzjT = zzaaVar.zzjT();
            this.zzIx = zZzjT;
            this.zzMN = true;
            zzb("XML config - dry run", Boolean.valueOf(zZzjT));
        }
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhn() throws PackageManager.NameNotFoundException {
        zzkI();
    }

    public String zzjL() {
        zzia();
        return this.zzLU;
    }

    public String zzjN() {
        zzia();
        return this.zzLV;
    }

    public boolean zzjO() {
        zzia();
        return this.zzML;
    }

    public boolean zzjQ() {
        zzia();
        return this.zzMM;
    }

    public boolean zzjS() {
        zzia();
        return this.zzMN;
    }

    public boolean zzjT() {
        zzia();
        return this.zzIx;
    }

    public int zzkH() {
        zzia();
        return this.zzLX;
    }

    protected void zzkI() throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo;
        int i;
        zzaa zzaaVarZzab;
        Context context = getContext();
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 129);
        } catch (PackageManager.NameNotFoundException e) {
            zzd("PackageManager doesn't know about the app package", e);
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            zzaW("Couldn't get ApplicationInfo to load global config");
            return;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle == null || (i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource")) <= 0 || (zzaaVarZzab = new zzz(zzhM()).zzab(i)) == null) {
            return;
        }
        zza(zzaaVarZzab);
    }
}
