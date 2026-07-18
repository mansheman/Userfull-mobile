package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

@Deprecated
/* loaded from: classes.dex */
public class zzae {
    private static volatile Logger zzMk;

    static {
        setLogger(new zzs());
    }

    public static Logger getLogger() {
        return zzMk;
    }

    public static void setLogger(Logger logger) {
        zzMk = logger;
    }

    public static boolean zzL(int i) {
        return getLogger() != null && getLogger().getLogLevel() <= i;
    }

    public static void zzaA(String str) {
        zzaf zzafVarZzkc = zzaf.zzkc();
        if (zzafVarZzkc != null) {
            zzafVarZzkc.zzaV(str);
        } else if (zzL(1)) {
            Log.i(zzy.zzLb.get(), str);
        }
        Logger logger = zzMk;
        if (logger != null) {
            logger.info(str);
        }
    }

    public static void zzaB(String str) {
        zzaf zzafVarZzkc = zzaf.zzkc();
        if (zzafVarZzkc != null) {
            zzafVarZzkc.zzaT(str);
        } else if (zzL(0)) {
            Log.v(zzy.zzLb.get(), str);
        }
        Logger logger = zzMk;
        if (logger != null) {
            logger.verbose(str);
        }
    }

    public static void zzaC(String str) {
        zzaf zzafVarZzkc = zzaf.zzkc();
        if (zzafVarZzkc != null) {
            zzafVarZzkc.zzaW(str);
        } else if (zzL(2)) {
            Log.w(zzy.zzLb.get(), str);
        }
        Logger logger = zzMk;
        if (logger != null) {
            logger.warn(str);
        }
    }

    public static void zzf(String str, Object obj) {
        zzaf zzafVarZzkc = zzaf.zzkc();
        if (zzafVarZzkc != null) {
            zzafVarZzkc.zze(str, obj);
        } else if (zzL(3)) {
            Log.e(zzy.zzLb.get(), obj != null ? str + ":" + obj : str);
        }
        Logger logger = zzMk;
        if (logger != null) {
            logger.error(str);
        }
    }
}
