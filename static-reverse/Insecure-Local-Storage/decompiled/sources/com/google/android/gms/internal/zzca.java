package com.google.android.gms.internal;

import android.content.Context;
import android.os.Build;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class zzca {
    private Context mContext = null;
    private String zzqr = null;
    private boolean zzuy = zzbz.zztY.get().booleanValue();
    private String zzuD = zzbz.zzua.get();
    private int zzuA = 30;
    private int zzuB = 3;
    private int zzuC = 100;
    private int zzuz = zzbz.zztZ.get().intValue();
    private Map<String, String> zzuE = new LinkedHashMap();

    public zzca() {
        this.zzuE.put("s", "gmob_sdk");
        this.zzuE.put("v", "3");
        this.zzuE.put("os", Build.VERSION.RELEASE);
        this.zzuE.put("sdk", Build.VERSION.SDK);
        this.zzuE.put("device", com.google.android.gms.ads.internal.zzo.zzbv().zzgo());
    }

    Context getContext() {
        return this.mContext;
    }

    public zzca zzb(Context context, String str) {
        this.mContext = context;
        this.zzqr = str;
        this.zzuE.put("ua", com.google.android.gms.ads.internal.zzo.zzbv().zzf(context, str));
        try {
            this.zzuE.put("app", context.getApplicationContext().getPackageName());
        } catch (NullPointerException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Cannot get the application name. Set to null.");
            this.zzuE.put("app", null);
        }
        return this;
    }

    String zzbR() {
        return this.zzqr;
    }

    boolean zzdc() {
        return this.zzuy;
    }

    String zzdd() {
        return this.zzuD;
    }

    int zzde() {
        return this.zzuA;
    }

    int zzdf() {
        return this.zzuB;
    }

    int zzdg() {
        return this.zzuC;
    }

    int zzdh() {
        return this.zzuz;
    }

    Map<String, String> zzdi() {
        return this.zzuE;
    }
}
