package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.analytics.AnalyticsService;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.internal.zzio;
import com.google.android.gms.internal.zzip;
import com.google.android.gms.internal.zzno;
import com.google.android.gms.internal.zzns;
import com.google.android.gms.internal.zznx;
import com.google.android.gms.internal.zzny;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
class zzl extends zzd {
    private boolean mStarted;
    private final zzj zzKn;
    private final zzah zzKo;
    private final zzag zzKp;
    private final zzi zzKq;
    private long zzKr;
    private final zzt zzKs;
    private final zzt zzKt;
    private final zzaj zzKu;
    private long zzKv;
    private boolean zzKw;

    protected zzl(zzf zzfVar, zzg zzgVar) {
        super(zzfVar);
        com.google.android.gms.common.internal.zzu.zzu(zzgVar);
        this.zzKr = Long.MIN_VALUE;
        this.zzKp = zzgVar.zzk(zzfVar);
        this.zzKn = zzgVar.zzm(zzfVar);
        this.zzKo = zzgVar.zzn(zzfVar);
        this.zzKq = zzgVar.zzo(zzfVar);
        this.zzKu = new zzaj(zzhP());
        this.zzKs = new zzt(zzfVar) { // from class: com.google.android.gms.analytics.internal.zzl.1
            @Override // com.google.android.gms.analytics.internal.zzt
            public void run() {
                zzl.this.zziA();
            }
        };
        this.zzKt = new zzt(zzfVar) { // from class: com.google.android.gms.analytics.internal.zzl.2
            @Override // com.google.android.gms.analytics.internal.zzt
            public void run() {
                zzl.this.zziB();
            }
        };
    }

    private void zza(zzh zzhVar, zzny zznyVar) {
        com.google.android.gms.common.internal.zzu.zzu(zzhVar);
        com.google.android.gms.common.internal.zzu.zzu(zznyVar);
        com.google.android.gms.analytics.zza zzaVar = new com.google.android.gms.analytics.zza(zzhM());
        zzaVar.zzaI(zzhVar.zzij());
        zzaVar.enableAdvertisingIdCollection(zzhVar.zzik());
        zzno zznoVarZzhc = zzaVar.zzhc();
        zzip zzipVar = (zzip) zznoVarZzhc.zze(zzip.class);
        zzipVar.zzaN("data");
        zzipVar.zzF(true);
        zznoVarZzhc.zzb(zznyVar);
        zzio zzioVar = (zzio) zznoVarZzhc.zze(zzio.class);
        zznx zznxVar = (zznx) zznoVarZzhc.zze(zznx.class);
        for (Map.Entry<String, String> entry : zzhVar.zzn().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("an".equals(key)) {
                zznxVar.setAppName(value);
            } else if ("av".equals(key)) {
                zznxVar.setAppVersion(value);
            } else if ("aid".equals(key)) {
                zznxVar.setAppId(value);
            } else if ("aiid".equals(key)) {
                zznxVar.setAppInstallerId(value);
            } else if ("uid".equals(key)) {
                zzipVar.setUserId(value);
            } else {
                zzioVar.set(key, value);
            }
        }
        zzb("Sending installation campaign to", zzhVar.zzij(), zznyVar);
        zznoVarZzhc.zzL(zzhU().zzkk());
        zznoVarZzhc.zzvT();
    }

    private boolean zzba(String str) {
        return getContext().checkCallingOrSelfPermission(str) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zziA() {
        zzb(new zzw() { // from class: com.google.android.gms.analytics.internal.zzl.4
            @Override // com.google.android.gms.analytics.internal.zzw
            public void zzc(Throwable th) {
                zzl.this.zziG();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zziB() {
        try {
            this.zzKn.zzis();
            zziG();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        this.zzKt.zzt(zzhR().zzjy());
    }

    private boolean zziH() {
        if (this.zzKw) {
            return false;
        }
        return (!zzhR().zziW() || zzhR().zziX()) && zziN() > 0;
    }

    private void zziI() {
        zzv zzvVarZzhT = zzhT();
        if (zzvVarZzhT.zzjG() && !zzvVarZzhT.zzbp()) {
            long jZzit = zzit();
            if (jZzit == 0 || Math.abs(zzhP().currentTimeMillis() - jZzit) > zzhR().zzjg()) {
                return;
            }
            zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzhR().zzjf()));
            zzvVarZzhT.zzjH();
        }
    }

    private void zziJ() {
        long jMin;
        zziI();
        long jZziN = zziN();
        long jZzkm = zzhU().zzkm();
        if (jZzkm != 0) {
            jMin = jZziN - Math.abs(zzhP().currentTimeMillis() - jZzkm);
            if (jMin <= 0) {
                jMin = Math.min(zzhR().zzjd(), jZziN);
            }
        } else {
            jMin = Math.min(zzhR().zzjd(), jZziN);
        }
        zza("Dispatch scheduled (ms)", Long.valueOf(jMin));
        if (!this.zzKs.zzbp()) {
            this.zzKs.zzt(jMin);
        } else {
            this.zzKs.zzu(Math.max(1L, jMin + this.zzKs.zzjD()));
        }
    }

    private void zziK() {
        zziL();
        zziM();
    }

    private void zziL() {
        if (this.zzKs.zzbp()) {
            zzaT("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzKs.cancel();
    }

    private void zziM() {
        zzv zzvVarZzhT = zzhT();
        if (zzvVarZzhT.zzbp()) {
            zzvVarZzhT.cancel();
        }
    }

    private void zziy() {
        Context context = zzhM().getContext();
        if (!AnalyticsReceiver.zzT(context)) {
            zzaW("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!AnalyticsService.zzU(context)) {
            zzaX("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzT(context)) {
            zzaW("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        } else {
            if (CampaignTrackingService.zzU(context)) {
                return;
            }
            zzaW("CampaignTrackingService is not registered or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
    }

    protected void onServiceConnected() {
        zzhO();
        if (zzhR().zziW()) {
            return;
        }
        zziD();
    }

    void start() {
        zzia();
        com.google.android.gms.common.internal.zzu.zza(!this.mStarted, "Analytics backend already started");
        this.mStarted = true;
        if (!zzhR().zziW()) {
            zziy();
        }
        zzhS().zze(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzl.3
            @Override // java.lang.Runnable
            public void run() {
                zzl.this.zziz();
            }
        });
    }

    public void zzG(boolean z) {
        zziG();
    }

    public long zza(zzh zzhVar, boolean z) {
        long jZza;
        com.google.android.gms.common.internal.zzu.zzu(zzhVar);
        zzia();
        zzhO();
        try {
            try {
                this.zzKn.beginTransaction();
                this.zzKn.zza(zzhVar.zzii(), zzhVar.getClientId());
                jZza = this.zzKn.zza(zzhVar.zzii(), zzhVar.getClientId(), zzhVar.zzij());
                if (z) {
                    zzhVar.zzn(1 + jZza);
                } else {
                    zzhVar.zzn(jZza);
                }
                this.zzKn.zzb(zzhVar);
                this.zzKn.setTransactionSuccessful();
                try {
                    this.zzKn.endTransaction();
                } catch (SQLiteException e) {
                    zze("Failed to end transaction", e);
                }
            } catch (SQLiteException e2) {
                zze("Failed to update Analytics property", e2);
                jZza = -1;
            }
            return jZza;
        } finally {
            try {
                this.zzKn.endTransaction();
            } catch (SQLiteException e3) {
                zze("Failed to end transaction", e3);
            }
        }
    }

    public void zza(zzab zzabVar) {
        com.google.android.gms.common.internal.zzu.zzu(zzabVar);
        zzns.zzhO();
        zzia();
        if (this.zzKw) {
            zzaU("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", zzabVar);
        }
        zzab zzabVarZzf = zzf(zzabVar);
        zziC();
        if (this.zzKq.zzb(zzabVarZzf)) {
            zzaU("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        if (zzhR().zziW()) {
            zzhQ().zza(zzabVarZzf, "Service unavailable on package side");
            return;
        }
        try {
            this.zzKn.zzc(zzabVarZzf);
            zziG();
        } catch (SQLiteException e) {
            zze("Delivery failed to save hit to a database", e);
            zzhQ().zza(zzabVarZzf, "deliver: failed to insert hit to database");
        }
    }

    public void zza(final zzw zzwVar, final long j) {
        zzns.zzhO();
        zzia();
        long jZzkm = zzhU().zzkm();
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(jZzkm != 0 ? Math.abs(zzhP().currentTimeMillis() - jZzkm) : -1L));
        if (!zzhR().zziW()) {
            zziC();
        }
        try {
            if (zziE()) {
                zzhS().zze(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzl.5
                    @Override // java.lang.Runnable
                    public void run() {
                        zzl.this.zza(zzwVar, j);
                    }
                });
                return;
            }
            zzhU().zzkn();
            zziG();
            if (zzwVar != null) {
                zzwVar.zzc(null);
            }
            if (this.zzKv != j) {
                this.zzKp.zzkf();
            }
        } catch (Throwable th) {
            zze("Local dispatch failed", th);
            zzhU().zzkn();
            zziG();
            if (zzwVar != null) {
                zzwVar.zzc(th);
            }
        }
    }

    public void zzb(zzw zzwVar) {
        zza(zzwVar, this.zzKv);
    }

    public void zzbb(String str) {
        com.google.android.gms.common.internal.zzu.zzcj(str);
        zzhO();
        zzhN();
        zzny zznyVarZza = zzam.zza(zzhQ(), str);
        if (zznyVarZza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String strZzko = zzhU().zzko();
        if (str.equals(strZzko)) {
            zzaW("Ignoring duplicate install campaign");
            return;
        }
        if (!TextUtils.isEmpty(strZzko)) {
            zzd("Ignoring multiple install campaigns. original, new", strZzko, str);
            return;
        }
        zzhU().zzbf(str);
        if (zzhU().zzkl().zzv(zzhR().zzjB())) {
            zzd("Campaign received too late, ignoring", zznyVarZza);
            return;
        }
        zzb("Received installation campaign", zznyVarZza);
        Iterator<zzh> it = this.zzKn.zzr(0L).iterator();
        while (it.hasNext()) {
            zza(it.next(), zznyVarZza);
        }
    }

    protected void zzc(zzh zzhVar) {
        zzhO();
        zzb("Sending first hit to property", zzhVar.zzij());
        if (zzhU().zzkl().zzv(zzhR().zzjB())) {
            return;
        }
        String strZzko = zzhU().zzko();
        if (TextUtils.isEmpty(strZzko)) {
            return;
        }
        zzny zznyVarZza = zzam.zza(zzhQ(), strZzko);
        zzb("Found relevant installation campaign", zznyVarZza);
        zza(zzhVar, zznyVarZza);
    }

    zzab zzf(zzab zzabVar) {
        Pair<String, Long> pairZzks;
        if (!TextUtils.isEmpty(zzabVar.zzka()) || (pairZzks = zzhU().zzkp().zzks()) == null) {
            return zzabVar;
        }
        String str = ((Long) pairZzks.second) + ":" + ((String) pairZzks.first);
        HashMap map = new HashMap(zzabVar.zzn());
        map.put("_m", str);
        return zzab.zza(this, zzabVar, map);
    }

    public void zzhG() {
        zzns.zzhO();
        zzia();
        if (!zzhR().zziW()) {
            zzaT("Delete all hits from local store");
            try {
                this.zzKn.zziq();
                this.zzKn.zzir();
                zziG();
            } catch (SQLiteException e) {
                zzd("Failed to delete hits from store", e);
            }
        }
        zziC();
        if (this.zzKq.zzim()) {
            zzaT("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    public void zzhJ() {
        zzns.zzhO();
        zzia();
        zzaT("Service disconnected");
    }

    void zzhL() {
        zzhO();
        this.zzKv = zzhP().currentTimeMillis();
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhn() {
        this.zzKn.zza();
        this.zzKo.zza();
        this.zzKq.zza();
    }

    protected void zziC() {
        if (this.zzKw || !zzhR().zziY() || this.zzKq.isConnected()) {
            return;
        }
        if (this.zzKu.zzv(zzhR().zzjt())) {
            this.zzKu.start();
            zzaT("Connecting to service");
            if (this.zzKq.connect()) {
                zzaT("Connected to service");
                this.zzKu.clear();
                onServiceConnected();
            }
        }
    }

    public void zziD() {
        zzns.zzhO();
        zzia();
        zzhN();
        if (!zzhR().zziY()) {
            zzaW("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zzKq.isConnected()) {
            zzaT("Service not connected");
            return;
        }
        if (this.zzKn.isEmpty()) {
            return;
        }
        zzaT("Dispatching local hits to device AnalyticsService");
        while (true) {
            try {
                List<zzab> listZzp = this.zzKn.zzp(zzhR().zzjh());
                if (listZzp.isEmpty()) {
                    zziG();
                    return;
                }
                while (!listZzp.isEmpty()) {
                    zzab zzabVar = listZzp.get(0);
                    if (!this.zzKq.zzb(zzabVar)) {
                        zziG();
                        return;
                    }
                    listZzp.remove(zzabVar);
                    try {
                        this.zzKn.zzq(zzabVar.zzjV());
                    } catch (SQLiteException e) {
                        zze("Failed to remove hit that was send for delivery", e);
                        zziK();
                        return;
                    }
                }
            } catch (SQLiteException e2) {
                zze("Failed to read hits from store", e2);
                zziK();
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0066, code lost:
    
        zzaT("Store is empty, nothing to dispatch");
        zziK();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006e, code lost:
    
        r12.zzKn.setTransactionSuccessful();
        r12.zzKn.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0079, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x007a, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zziK();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f9, code lost:
    
        if (r12.zzKq.isConnected() == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0103, code lost:
    
        if (zzhR().zziW() != false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0105, code lost:
    
        zzaT("Service connected, sending hits to the service");
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x010e, code lost:
    
        if (r8.isEmpty() != false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0110, code lost:
    
        r0 = r8.get(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x011d, code lost:
    
        if (r12.zzKq.zzb(r0) != false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x011f, code lost:
    
        r0 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0126, code lost:
    
        if (r12.zzKo.zzkg() == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0128, code lost:
    
        r9 = r12.zzKo.zzf(r8);
        r10 = r9.iterator();
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0137, code lost:
    
        if (r10.hasNext() == false) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0139, code lost:
    
        r4 = java.lang.Math.max(r4, r10.next().longValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0148, code lost:
    
        r4 = java.lang.Math.max(r4, r0.zzjV());
        r8.remove(r0);
        zzb("Hit sent do device AnalyticsService for delivery", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0158, code lost:
    
        r12.zzKn.zzq(r0.zzjV());
        r3.add(java.lang.Long.valueOf(r0.zzjV()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x016d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x016e, code lost:
    
        zze("Failed to remove hit that was send for delivery", r0);
        zziK();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0176, code lost:
    
        r12.zzKn.setTransactionSuccessful();
        r12.zzKn.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0182, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0183, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zziK();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x018d, code lost:
    
        r8.removeAll(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0190, code lost:
    
        r12.zzKn.zzd(r9);
        r3.addAll(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0198, code lost:
    
        r0 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x019d, code lost:
    
        if (r3.isEmpty() == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x019f, code lost:
    
        r12.zzKn.setTransactionSuccessful();
        r12.zzKn.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01ab, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01ac, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zziK();
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01b6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01b7, code lost:
    
        zze("Failed to remove successfully uploaded hits", r0);
        zziK();
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01bf, code lost:
    
        r12.zzKn.setTransactionSuccessful();
        r12.zzKn.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01cb, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01cc, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zziK();
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01d6, code lost:
    
        r12.zzKn.setTransactionSuccessful();
        r12.zzKn.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01e3, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01e4, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zziK();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0205, code lost:
    
        r0 = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean zziE() {
        /*
            Method dump skipped, instructions count: 520
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzl.zziE():boolean");
    }

    public void zziF() {
        zzns.zzhO();
        zzia();
        zzaU("Sync dispatching local hits");
        long j = this.zzKv;
        if (!zzhR().zziW()) {
            zziC();
        }
        do {
            try {
            } catch (Throwable th) {
                zze("Sync local dispatch failed", th);
                zziG();
                return;
            }
        } while (zziE());
        zzhU().zzkn();
        zziG();
        if (this.zzKv != j) {
            this.zzKp.zzkf();
        }
    }

    public void zziG() {
        boolean zIsConnected;
        zzhM().zzhO();
        zzia();
        if (!zziH()) {
            this.zzKp.unregister();
            zziK();
            return;
        }
        if (this.zzKn.isEmpty()) {
            this.zzKp.unregister();
            zziK();
            return;
        }
        if (zzy.zzLI.get().booleanValue()) {
            zIsConnected = true;
        } else {
            this.zzKp.zzkd();
            zIsConnected = this.zzKp.isConnected();
        }
        if (zIsConnected) {
            zziJ();
        } else {
            zziK();
            zziI();
        }
    }

    public long zziN() {
        if (this.zzKr != Long.MIN_VALUE) {
            return this.zzKr;
        }
        return zzhm().zzjQ() ? zzhm().zzkH() * 1000 : zzhR().zzje();
    }

    public void zziO() {
        zzia();
        zzhO();
        this.zzKw = true;
        this.zzKq.disconnect();
        zziG();
    }

    public long zzit() {
        zzns.zzhO();
        zzia();
        try {
            return this.zzKn.zzit();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0L;
        }
    }

    protected void zziz() {
        zzia();
        zzhU().zzkk();
        if (!zzba("android.permission.ACCESS_NETWORK_STATE")) {
            zzaX("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zziO();
        }
        if (!zzba("android.permission.INTERNET")) {
            zzaX("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zziO();
        }
        if (AnalyticsService.zzU(getContext())) {
            zzaT("AnalyticsService registered in the app manifest and enabled");
        } else if (zzhR().zziW()) {
            zzaX("Device AnalyticsService not registered! Hits will not be delivered reliably.");
        } else {
            zzaW("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzKw && !zzhR().zziW() && !this.zzKn.isEmpty()) {
            zziC();
        }
        zziG();
    }

    public void zzs(long j) {
        zzns.zzhO();
        zzia();
        if (j < 0) {
            j = 0;
        }
        this.zzKr = j;
        zziG();
    }
}
