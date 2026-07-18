package com.google.android.gms.analytics.internal;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.internal.zzns;
import java.lang.Thread;

/* loaded from: classes.dex */
public class zzf {
    private static zzf zzJC;
    private final Context mContext;
    private final Context zzJD;
    private final zzr zzJE;
    private final zzaf zzJF;
    private final zzns zzJG;
    private final zzb zzJH;
    private final zzv zzJI;
    private final zzan zzJJ;
    private final zzai zzJK;
    private final GoogleAnalytics zzJL;
    private final zzn zzJM;
    private final zza zzJN;
    private final zzk zzJO;
    private final zzu zzJP;
    private final zzlb zzpw;

    protected zzf(zzg zzgVar) {
        Context applicationContext = zzgVar.getApplicationContext();
        com.google.android.gms.common.internal.zzu.zzb(applicationContext, "Application context can't be null");
        com.google.android.gms.common.internal.zzu.zzb(applicationContext instanceof Application, "getApplicationContext didn't return the application");
        Context contextZzic = zzgVar.zzic();
        com.google.android.gms.common.internal.zzu.zzu(contextZzic);
        this.mContext = applicationContext;
        this.zzJD = contextZzic;
        this.zzpw = zzgVar.zzh(this);
        this.zzJE = zzgVar.zzg(this);
        zzaf zzafVarZzf = zzgVar.zzf(this);
        zzafVarZzf.zza();
        this.zzJF = zzafVarZzf;
        if (zzhR().zziW()) {
            zzhQ().zzaV("Google Analytics " + zze.VERSION + " is starting up.");
        } else {
            zzhQ().zzaV("Google Analytics " + zze.VERSION + " is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4");
        }
        zzai zzaiVarZzq = zzgVar.zzq(this);
        zzaiVarZzq.zza();
        this.zzJK = zzaiVarZzq;
        zzan zzanVarZze = zzgVar.zze(this);
        zzanVarZze.zza();
        this.zzJJ = zzanVarZze;
        zzb zzbVarZzl = zzgVar.zzl(this);
        zzn zznVarZzd = zzgVar.zzd(this);
        zza zzaVarZzc = zzgVar.zzc(this);
        zzk zzkVarZzb = zzgVar.zzb(this);
        zzu zzuVarZza = zzgVar.zza(this);
        zzns zznsVarZzW = zzgVar.zzW(applicationContext);
        zznsVarZzW.zza(zzib());
        this.zzJG = zznsVarZzW;
        GoogleAnalytics googleAnalyticsZzi = zzgVar.zzi(this);
        zznVarZzd.zza();
        this.zzJM = zznVarZzd;
        zzaVarZzc.zza();
        this.zzJN = zzaVarZzc;
        zzkVarZzb.zza();
        this.zzJO = zzkVarZzb;
        zzuVarZza.zza();
        this.zzJP = zzuVarZza;
        zzv zzvVarZzp = zzgVar.zzp(this);
        zzvVarZzp.zza();
        this.zzJI = zzvVarZzp;
        zzbVarZzl.zza();
        this.zzJH = zzbVarZzl;
        if (zzhR().zziW()) {
            zzhQ().zzb("Device AnalyticsService version", zze.VERSION);
        }
        googleAnalyticsZzi.zza();
        this.zzJL = googleAnalyticsZzi;
        zzbVarZzl.start();
    }

    public static zzf zzV(Context context) {
        com.google.android.gms.common.internal.zzu.zzu(context);
        if (zzJC == null) {
            synchronized (zzf.class) {
                if (zzJC == null) {
                    zzlb zzlbVarZzoQ = zzld.zzoQ();
                    long jElapsedRealtime = zzlbVarZzoQ.elapsedRealtime();
                    zzf zzfVar = new zzf(new zzg(context.getApplicationContext()));
                    zzJC = zzfVar;
                    GoogleAnalytics.zzhj();
                    long jElapsedRealtime2 = zzlbVarZzoQ.elapsedRealtime() - jElapsedRealtime;
                    long jLongValue = zzy.zzLP.get().longValue();
                    if (jElapsedRealtime2 > jLongValue) {
                        zzfVar.zzhQ().zzc("Slow initialization (ms)", Long.valueOf(jElapsedRealtime2), Long.valueOf(jLongValue));
                    }
                }
            }
        }
        return zzJC;
    }

    private void zza(zzd zzdVar) {
        com.google.android.gms.common.internal.zzu.zzb(zzdVar, "Analytics service not created/initialized");
        com.google.android.gms.common.internal.zzu.zzb(zzdVar.isInitialized(), "Analytics service not initialized");
    }

    public Context getContext() {
        return this.mContext;
    }

    public void zzhO() {
        zzns.zzhO();
    }

    public zzlb zzhP() {
        return this.zzpw;
    }

    public zzaf zzhQ() {
        zza(this.zzJF);
        return this.zzJF;
    }

    public zzr zzhR() {
        return this.zzJE;
    }

    public zzns zzhS() {
        com.google.android.gms.common.internal.zzu.zzu(this.zzJG);
        return this.zzJG;
    }

    public zzv zzhT() {
        zza(this.zzJI);
        return this.zzJI;
    }

    public zzai zzhU() {
        zza(this.zzJK);
        return this.zzJK;
    }

    public zzk zzhX() {
        zza(this.zzJO);
        return this.zzJO;
    }

    public zzu zzhY() {
        return this.zzJP;
    }

    public zzb zzhl() {
        zza(this.zzJH);
        return this.zzJH;
    }

    public zzan zzhm() {
        zza(this.zzJJ);
        return this.zzJJ;
    }

    protected Thread.UncaughtExceptionHandler zzib() {
        return new Thread.UncaughtExceptionHandler() { // from class: com.google.android.gms.analytics.internal.zzf.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable error) {
                zzaf zzafVarZzid = zzf.this.zzid();
                if (zzafVarZzid != null) {
                    zzafVarZzid.zze("Job execution failed", error);
                }
            }
        };
    }

    public Context zzic() {
        return this.zzJD;
    }

    public zzaf zzid() {
        return this.zzJF;
    }

    public GoogleAnalytics zzie() {
        com.google.android.gms.common.internal.zzu.zzu(this.zzJL);
        com.google.android.gms.common.internal.zzu.zzb(this.zzJL.isInitialized(), "Analytics instance not initialized");
        return this.zzJL;
    }

    public zzai zzif() {
        if (this.zzJK == null || !this.zzJK.isInitialized()) {
            return null;
        }
        return this.zzJK;
    }

    public zza zzig() {
        zza(this.zzJN);
        return this.zzJN;
    }

    public zzn zzih() {
        zza(this.zzJM);
        return this.zzJM;
    }
}
