package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzns;

/* loaded from: classes.dex */
public class zzc {
    private final zzf zzJy;

    protected zzc(zzf zzfVar) {
        com.google.android.gms.common.internal.zzu.zzu(zzfVar);
        this.zzJy = zzfVar;
    }

    private void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        zzaf zzafVarZzid = this.zzJy != null ? this.zzJy.zzid() : null;
        if (zzafVarZzid != null) {
            zzafVarZzid.zza(i, str, obj, obj2, obj3);
            return;
        }
        String str2 = zzy.zzLb.get();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc(str, obj, obj2, obj3));
        }
    }

    protected static String zzc(String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            str = "";
        }
        String strZzi = zzi(obj);
        String strZzi2 = zzi(obj2);
        String strZzi3 = zzi(obj3);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(strZzi)) {
            sb.append(str2);
            sb.append(strZzi);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(strZzi2)) {
            sb.append(str2);
            sb.append(strZzi2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(strZzi3)) {
            sb.append(str2);
            sb.append(strZzi3);
        }
        return sb.toString();
    }

    private static String zzi(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Boolean) {
            return obj == Boolean.TRUE ? "true" : "false";
        }
        return obj instanceof Throwable ? ((Throwable) obj).toString() : obj.toString();
    }

    protected Context getContext() {
        return this.zzJy.getContext();
    }

    public void zza(String str, Object obj) {
        zza(2, str, obj, null, null);
    }

    public void zza(String str, Object obj, Object obj2) {
        zza(2, str, obj, obj2, null);
    }

    public void zza(String str, Object obj, Object obj2, Object obj3) {
        zza(3, str, obj, obj2, obj3);
    }

    public void zzaT(String str) {
        zza(2, str, null, null, null);
    }

    public void zzaU(String str) {
        zza(3, str, null, null, null);
    }

    public void zzaV(String str) {
        zza(4, str, null, null, null);
    }

    public void zzaW(String str) {
        zza(5, str, null, null, null);
    }

    public void zzaX(String str) {
        zza(6, str, null, null, null);
    }

    public void zzb(String str, Object obj) {
        zza(3, str, obj, null, null);
    }

    public void zzb(String str, Object obj, Object obj2) {
        zza(3, str, obj, obj2, null);
    }

    public void zzb(String str, Object obj, Object obj2, Object obj3) {
        zza(5, str, obj, obj2, obj3);
    }

    public void zzc(String str, Object obj) {
        zza(4, str, obj, null, null);
    }

    public void zzc(String str, Object obj, Object obj2) {
        zza(5, str, obj, obj2, null);
    }

    public void zzd(String str, Object obj) {
        zza(5, str, obj, null, null);
    }

    public void zzd(String str, Object obj, Object obj2) {
        zza(6, str, obj, obj2, null);
    }

    public void zze(String str, Object obj) {
        zza(6, str, obj, null, null);
    }

    public zzf zzhM() {
        return this.zzJy;
    }

    protected void zzhN() {
        if (zzhR().zziW()) {
            throw new IllegalStateException("Call only supported on the client side");
        }
    }

    protected void zzhO() {
        this.zzJy.zzhO();
    }

    protected zzlb zzhP() {
        return this.zzJy.zzhP();
    }

    protected zzaf zzhQ() {
        return this.zzJy.zzhQ();
    }

    protected zzr zzhR() {
        return this.zzJy.zzhR();
    }

    protected zzns zzhS() {
        return this.zzJy.zzhS();
    }

    protected zzv zzhT() {
        return this.zzJy.zzhT();
    }

    protected zzai zzhU() {
        return this.zzJy.zzhU();
    }

    protected zzn zzhV() {
        return this.zzJy.zzih();
    }

    protected zza zzhW() {
        return this.zzJy.zzig();
    }

    protected zzk zzhX() {
        return this.zzJy.zzhX();
    }

    protected zzu zzhY() {
        return this.zzJy.zzhY();
    }

    public boolean zzhZ() {
        return Log.isLoggable(zzy.zzLb.get(), 2);
    }

    public GoogleAnalytics zzhg() {
        return this.zzJy.zzie();
    }

    protected zzb zzhl() {
        return this.zzJy.zzhl();
    }

    protected zzan zzhm() {
        return this.zzJy.zzhm();
    }
}
