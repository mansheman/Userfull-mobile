package com.google.android.gms.internal;

import android.support.v7.internal.widget.ActivityChooserView;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.UUID;

/* loaded from: classes.dex */
public final class zzod extends zznq<zzod> {
    private String zzaEJ;
    private int zzaEK;
    private int zzaEL;
    private String zzaEM;
    private String zzaEN;
    private boolean zzaEO;
    private boolean zzaEP;
    private boolean zzaEQ;

    public zzod() {
        this(false);
    }

    public zzod(boolean z) {
        this(z, zzwA());
    }

    public zzod(boolean z, int i) {
        com.google.android.gms.common.internal.zzu.zzbw(i);
        this.zzaEK = i;
        this.zzaEP = z;
    }

    static int zzwA() {
        UUID uuidRandomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (uuidRandomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits != 0) {
            return leastSignificantBits;
        }
        int mostSignificantBits = (int) (uuidRandomUUID.getMostSignificantBits() & 2147483647L);
        if (mostSignificantBits != 0) {
            return mostSignificantBits;
        }
        Log.e("GAv4", "UUID.randomUUID() returned 0.");
        return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    private void zzwH() {
        if (this.zzaEQ) {
            throw new IllegalStateException("ScreenViewInfo is immutable");
        }
    }

    public boolean isMutable() {
        return !this.zzaEQ;
    }

    public void setScreenName(String screenName) {
        zzwH();
        this.zzaEJ = screenName;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put("screenName", this.zzaEJ);
        map.put("interstitial", Boolean.valueOf(this.zzaEO));
        map.put("automatic", Boolean.valueOf(this.zzaEP));
        map.put("screenId", Integer.valueOf(this.zzaEK));
        map.put("referrerScreenId", Integer.valueOf(this.zzaEL));
        map.put("referrerScreenName", this.zzaEM);
        map.put("referrerUri", this.zzaEN);
        return zzy(map);
    }

    public void zzah(boolean z) {
        zzwH();
        this.zzaEP = z;
    }

    public void zzai(boolean z) {
        zzwH();
        this.zzaEO = z;
    }

    public int zzbn() {
        return this.zzaEK;
    }

    @Override // com.google.android.gms.internal.zznq
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public void zza(zzod zzodVar) {
        if (!TextUtils.isEmpty(this.zzaEJ)) {
            zzodVar.setScreenName(this.zzaEJ);
        }
        if (this.zzaEK != 0) {
            zzodVar.zzhK(this.zzaEK);
        }
        if (this.zzaEL != 0) {
            zzodVar.zzhL(this.zzaEL);
        }
        if (!TextUtils.isEmpty(this.zzaEM)) {
            zzodVar.zzdJ(this.zzaEM);
        }
        if (!TextUtils.isEmpty(this.zzaEN)) {
            zzodVar.zzdK(this.zzaEN);
        }
        if (this.zzaEO) {
            zzodVar.zzai(this.zzaEO);
        }
        if (this.zzaEP) {
            zzodVar.zzah(this.zzaEP);
        }
    }

    public void zzdJ(String str) {
        zzwH();
        this.zzaEM = str;
    }

    public void zzdK(String str) {
        zzwH();
        if (TextUtils.isEmpty(str)) {
            this.zzaEN = null;
        } else {
            this.zzaEN = str;
        }
    }

    public void zzhK(int i) {
        zzwH();
        this.zzaEK = i;
    }

    public void zzhL(int i) {
        zzwH();
        this.zzaEL = i;
    }

    public String zzwB() {
        return this.zzaEJ;
    }

    public int zzwC() {
        return this.zzaEL;
    }

    public String zzwD() {
        return this.zzaEM;
    }

    public String zzwE() {
        return this.zzaEN;
    }

    public void zzwF() {
        this.zzaEQ = true;
    }

    public boolean zzwG() {
        return this.zzaEO;
    }
}
