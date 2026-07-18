package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.internal.widget.ActivityChooserView;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzad;
import com.google.android.gms.analytics.internal.zzal;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzd;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zznx;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/* loaded from: classes.dex */
public class Tracker extends zzd {
    private boolean zzIH;
    private final Map<String, String> zzII;
    private final zzad zzIJ;
    private final zza zzIK;
    private ExceptionReporter zzIL;
    private zzal zzIM;
    private final Map<String, String> zzyn;

    private class zza extends zzd implements GoogleAnalytics.zza {
        private boolean zzIV;
        private int zzIW;
        private long zzIX;
        private boolean zzIY;
        private long zzIZ;

        protected zza(zzf zzfVar) {
            super(zzfVar);
            this.zzIX = -1L;
        }

        private void zzhr() {
            if (this.zzIX >= 0 || this.zzIV) {
                zzhg().zza(Tracker.this.zzIK);
            } else {
                zzhg().zzb(Tracker.this.zzIK);
            }
        }

        public void enableAutoActivityTracking(boolean enabled) {
            this.zzIV = enabled;
            zzhr();
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.zzIX = sessionTimeout;
            zzhr();
        }

        @Override // com.google.android.gms.analytics.internal.zzd
        protected void zzhn() {
        }

        public synchronized boolean zzhq() {
            boolean z;
            z = this.zzIY;
            this.zzIY = false;
            return z;
        }

        boolean zzhs() {
            return zzhP().elapsedRealtime() >= this.zzIZ + Math.max(1000L, this.zzIX);
        }

        @Override // com.google.android.gms.analytics.GoogleAnalytics.zza
        public void zzn(Activity activity) {
            if (this.zzIW == 0 && zzhs()) {
                this.zzIY = true;
            }
            this.zzIW++;
            if (this.zzIV) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    Tracker.this.setCampaignParamsOnNextHit(intent.getData());
                }
                HashMap map = new HashMap();
                map.put("&t", "screenview");
                Tracker.this.set("&cd", Tracker.this.zzIM != null ? Tracker.this.zzIM.zzq(activity) : activity.getClass().getCanonicalName());
                if (TextUtils.isEmpty((CharSequence) map.get("&dr"))) {
                    String strZzp = Tracker.zzp(activity);
                    if (!TextUtils.isEmpty(strZzp)) {
                        map.put("&dr", strZzp);
                    }
                }
                Tracker.this.send(map);
            }
        }

        @Override // com.google.android.gms.analytics.GoogleAnalytics.zza
        public void zzo(Activity activity) {
            this.zzIW--;
            this.zzIW = Math.max(0, this.zzIW);
            if (this.zzIW == 0) {
                this.zzIZ = zzhP().elapsedRealtime();
            }
        }
    }

    Tracker(zzf analytics, String trackingId, zzad rateLimiter) {
        super(analytics);
        this.zzyn = new HashMap();
        this.zzII = new HashMap();
        if (trackingId != null) {
            this.zzyn.put("&tid", trackingId);
        }
        this.zzyn.put("useSecure", "1");
        this.zzyn.put("&a", Integer.toString(new Random().nextInt(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) + 1));
        if (rateLimiter == null) {
            this.zzIJ = new zzad("tracking");
        } else {
            this.zzIJ = rateLimiter;
        }
        this.zzIK = new zza(analytics);
    }

    private static void zza(Map<String, String> map, Map<String, String> map2) {
        zzu.zzu(map2);
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String strZzb = zzb(entry);
            if (strZzb != null) {
                map2.put(strZzb, entry.getValue());
            }
        }
    }

    private static boolean zza(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        entry.getValue();
        return key.startsWith("&") && key.length() >= 2;
    }

    private static String zzb(Map.Entry<String, String> entry) {
        if (zza(entry)) {
            return entry.getKey().substring(1);
        }
        return null;
    }

    private static void zzb(Map<String, String> map, Map<String, String> map2) {
        zzu.zzu(map2);
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String strZzb = zzb(entry);
            if (strZzb != null && !map2.containsKey(strZzb)) {
                map2.put(strZzb, entry.getValue());
            }
        }
    }

    private boolean zzho() {
        return this.zzIL != null;
    }

    static String zzp(Activity activity) {
        zzu.zzu(activity);
        Intent intent = activity.getIntent();
        if (intent == null) {
            return null;
        }
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (TextUtils.isEmpty(stringExtra)) {
            return null;
        }
        return stringExtra;
    }

    public void enableAdvertisingIdCollection(boolean enabled) {
        this.zzIH = enabled;
    }

    public void enableAutoActivityTracking(boolean enabled) {
        this.zzIK.enableAutoActivityTracking(enabled);
    }

    public void enableExceptionReporting(boolean enable) {
        synchronized (this) {
            if (zzho() == enable) {
                return;
            }
            if (enable) {
                this.zzIL = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), getContext());
                Thread.setDefaultUncaughtExceptionHandler(this.zzIL);
                zzaT("Uncaught exceptions will be reported to Google Analytics");
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.zzIL.zzhh());
                zzaT("Uncaught exceptions will not be reported to Google Analytics");
            }
        }
    }

    public String get(String key) {
        zzia();
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (this.zzyn.containsKey(key)) {
            return this.zzyn.get(key);
        }
        if (key.equals("&ul")) {
            return zzam.zza(Locale.getDefault());
        }
        if (key.equals("&cid")) {
            return zzhV().zziP();
        }
        if (key.equals("&sr")) {
            return zzhY().zzjF();
        }
        if (key.equals("&aid")) {
            return zzhX().zzix().zzsK();
        }
        if (key.equals("&an")) {
            return zzhX().zzix().zzjL();
        }
        if (key.equals("&av")) {
            return zzhX().zzix().zzjN();
        }
        if (key.equals("&aiid")) {
            return zzhX().zzix().zzwi();
        }
        return null;
    }

    public void send(Map<String, String> params) {
        final long jCurrentTimeMillis = zzhP().currentTimeMillis();
        if (zzhg().getAppOptOut()) {
            zzaU("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        final boolean zIsDryRunEnabled = zzhg().isDryRunEnabled();
        final HashMap map = new HashMap();
        zza(this.zzyn, map);
        zza(params, map);
        final boolean zZze = zzam.zze(this.zzyn.get("useSecure"), true);
        zzb(this.zzII, map);
        this.zzII.clear();
        final String str = map.get("t");
        if (TextUtils.isEmpty(str)) {
            zzhQ().zzg(map, "Missing hit type parameter");
            return;
        }
        final String str2 = map.get("tid");
        if (TextUtils.isEmpty(str2)) {
            zzhQ().zzg(map, "Missing tracking id parameter");
            return;
        }
        final boolean zZzhp = zzhp();
        synchronized (this) {
            if ("screenview".equalsIgnoreCase(str) || "pageview".equalsIgnoreCase(str) || "appview".equalsIgnoreCase(str) || TextUtils.isEmpty(str)) {
                int i = Integer.parseInt(this.zzyn.get("&a")) + 1;
                if (i >= Integer.MAX_VALUE) {
                    i = 1;
                }
                this.zzyn.put("&a", Integer.toString(i));
            }
        }
        zzhS().zze(new Runnable() { // from class: com.google.android.gms.analytics.Tracker.1
            @Override // java.lang.Runnable
            public void run() {
                if (Tracker.this.zzIK.zzhq()) {
                    map.put("sc", "start");
                }
                zzam.zzc(map, "cid", Tracker.this.zzhg().getClientId());
                String str3 = (String) map.get("sf");
                if (str3 != null) {
                    double dZza = zzam.zza(str3, 100.0d);
                    if (zzam.zza(dZza, (String) map.get("cid"))) {
                        Tracker.this.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(dZza));
                        return;
                    }
                }
                com.google.android.gms.analytics.internal.zza zzaVarZzhW = Tracker.this.zzhW();
                if (zZzhp) {
                    zzam.zzb((Map<String, String>) map, "ate", zzaVarZzhW.zzhy());
                    zzam.zzb((Map<String, String>) map, "adid", zzaVarZzhW.zzhC());
                } else {
                    map.remove("ate");
                    map.remove("adid");
                }
                zznx zznxVarZzix = Tracker.this.zzhX().zzix();
                zzam.zzb((Map<String, String>) map, "an", zznxVarZzix.zzjL());
                zzam.zzb((Map<String, String>) map, "av", zznxVarZzix.zzjN());
                zzam.zzb((Map<String, String>) map, "aid", zznxVarZzix.zzsK());
                zzam.zzb((Map<String, String>) map, "aiid", zznxVarZzix.zzwi());
                map.put("v", "1");
                map.put("_v", zze.zzJB);
                zzam.zzb((Map<String, String>) map, "ul", Tracker.this.zzhY().zzjE().getLanguage());
                zzam.zzb((Map<String, String>) map, "sr", Tracker.this.zzhY().zzjF());
                if (!(str.equals("transaction") || str.equals("item")) && !Tracker.this.zzIJ.zzkb()) {
                    Tracker.this.zzhQ().zzg(map, "Too many hits sent too quickly, rate limiting invoked");
                    return;
                }
                long jZzbj = zzam.zzbj((String) map.get("ht"));
                if (jZzbj == 0) {
                    jZzbj = jCurrentTimeMillis;
                }
                if (zIsDryRunEnabled) {
                    Tracker.this.zzhQ().zzc("Dry run enabled. Would have sent hit", new zzab(Tracker.this, map, jZzbj, zZze));
                    return;
                }
                String str4 = (String) map.get("cid");
                HashMap map2 = new HashMap();
                zzam.zza(map2, "uid", (Map<String, String>) map);
                zzam.zza(map2, "an", (Map<String, String>) map);
                zzam.zza(map2, "aid", (Map<String, String>) map);
                zzam.zza(map2, "av", (Map<String, String>) map);
                zzam.zza(map2, "aiid", (Map<String, String>) map);
                map.put("_s", String.valueOf(Tracker.this.zzhl().zza(new zzh(0L, str4, str2, TextUtils.isEmpty((CharSequence) map.get("adid")) ? false : true, 0L, map2))));
                Tracker.this.zzhl().zza(new zzab(Tracker.this, map, jZzbj, zZze));
            }
        });
    }

    public void set(String key, String value) {
        zzu.zzb(key, "Key should be non-null");
        if (TextUtils.isEmpty(key)) {
            return;
        }
        this.zzyn.put(key, value);
    }

    public void setAnonymizeIp(boolean anonymize) {
        set("&aip", zzam.zzH(anonymize));
    }

    public void setAppId(String appId) {
        set("&aid", appId);
    }

    public void setAppInstallerId(String appInstallerId) {
        set("&aiid", appInstallerId);
    }

    public void setAppName(String appName) {
        set("&an", appName);
    }

    public void setAppVersion(String appVersion) {
        set("&av", appVersion);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri == null || uri.isOpaque()) {
            return;
        }
        String queryParameter = uri.getQueryParameter("referrer");
        if (TextUtils.isEmpty(queryParameter)) {
            return;
        }
        Uri uri2 = Uri.parse("http://hostname/?" + queryParameter);
        String queryParameter2 = uri2.getQueryParameter("utm_id");
        if (queryParameter2 != null) {
            this.zzII.put("&ci", queryParameter2);
        }
        String queryParameter3 = uri2.getQueryParameter("anid");
        if (queryParameter3 != null) {
            this.zzII.put("&anid", queryParameter3);
        }
        String queryParameter4 = uri2.getQueryParameter("utm_campaign");
        if (queryParameter4 != null) {
            this.zzII.put("&cn", queryParameter4);
        }
        String queryParameter5 = uri2.getQueryParameter("utm_content");
        if (queryParameter5 != null) {
            this.zzII.put("&cc", queryParameter5);
        }
        String queryParameter6 = uri2.getQueryParameter("utm_medium");
        if (queryParameter6 != null) {
            this.zzII.put("&cm", queryParameter6);
        }
        String queryParameter7 = uri2.getQueryParameter("utm_source");
        if (queryParameter7 != null) {
            this.zzII.put("&cs", queryParameter7);
        }
        String queryParameter8 = uri2.getQueryParameter("utm_term");
        if (queryParameter8 != null) {
            this.zzII.put("&ck", queryParameter8);
        }
        String queryParameter9 = uri2.getQueryParameter("dclid");
        if (queryParameter9 != null) {
            this.zzII.put("&dclid", queryParameter9);
        }
        String queryParameter10 = uri2.getQueryParameter("gclid");
        if (queryParameter10 != null) {
            this.zzII.put("&gclid", queryParameter10);
        }
        String queryParameter11 = uri2.getQueryParameter("aclid");
        if (queryParameter11 != null) {
            this.zzII.put("&aclid", queryParameter11);
        }
    }

    public void setClientId(String clientId) {
        set("&cid", clientId);
    }

    public void setEncoding(String encoding) {
        set("&de", encoding);
    }

    public void setHostname(String hostname) {
        set("&dh", hostname);
    }

    public void setLanguage(String language) {
        set("&ul", language);
    }

    public void setLocation(String location) {
        set("&dl", location);
    }

    public void setPage(String page) {
        set("&dp", page);
    }

    public void setReferrer(String referrer) {
        set("&dr", referrer);
    }

    public void setSampleRate(double sampleRate) {
        set("&sf", Double.toString(sampleRate));
    }

    public void setScreenColors(String screenColors) {
        set("&sd", screenColors);
    }

    public void setScreenName(String screenName) {
        set("&cd", screenName);
    }

    public void setScreenResolution(int width, int height) {
        if (width >= 0 || height >= 0) {
            set("&sr", width + "x" + height);
        } else {
            zzaW("Invalid width or height. The values should be non-negative.");
        }
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.zzIK.setSessionTimeout(1000 * sessionTimeout);
    }

    public void setTitle(String title) {
        set("&dt", title);
    }

    public void setUseSecure(boolean useSecure) {
        set("useSecure", zzam.zzH(useSecure));
    }

    public void setViewportSize(String viewportSize) {
        set("&vp", viewportSize);
    }

    void zza(zzal zzalVar) {
        zzaT("Loading Tracker config values");
        this.zzIM = zzalVar;
        if (this.zzIM.zzky()) {
            String trackingId = this.zzIM.getTrackingId();
            set("&tid", trackingId);
            zza("trackingId loaded", trackingId);
        }
        if (this.zzIM.zzkz()) {
            String string = Double.toString(this.zzIM.zzkA());
            set("&sf", string);
            zza("Sample frequency loaded", string);
        }
        if (this.zzIM.zzkB()) {
            int sessionTimeout = this.zzIM.getSessionTimeout();
            setSessionTimeout(sessionTimeout);
            zza("Session timeout loaded", Integer.valueOf(sessionTimeout));
        }
        if (this.zzIM.zzkC()) {
            boolean zZzkD = this.zzIM.zzkD();
            enableAutoActivityTracking(zZzkD);
            zza("Auto activity tracking loaded", Boolean.valueOf(zZzkD));
        }
        if (this.zzIM.zzkE()) {
            boolean zZzkF = this.zzIM.zzkF();
            if (zZzkF) {
                set("&aip", "1");
            }
            zza("Anonymize ip loaded", Boolean.valueOf(zZzkF));
        }
        enableExceptionReporting(this.zzIM.zzkG());
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhn() {
        this.zzIK.zza();
        String strZzjL = zzhm().zzjL();
        if (strZzjL != null) {
            set("&an", strZzjL);
        }
        String strZzjN = zzhm().zzjN();
        if (strZzjN != null) {
            set("&av", strZzjN);
        }
    }

    boolean zzhp() {
        return this.zzIH;
    }
}
