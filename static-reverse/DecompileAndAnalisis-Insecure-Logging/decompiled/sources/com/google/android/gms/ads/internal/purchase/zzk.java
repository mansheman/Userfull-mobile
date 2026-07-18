package com.google.android.gms.ads.internal.purchase;

import android.content.Intent;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.internal.zzgd;

@zzgd
/* loaded from: classes.dex */
public class zzk {
    private final String zzsU;

    public zzk(String str) {
        this.zzsU = str;
    }

    public boolean zza(String str, int i, Intent intent) {
        if (str == null || intent == null) {
            return false;
        }
        String strZze = zzo.zzbF().zze(intent);
        String strZzf = zzo.zzbF().zzf(intent);
        if (strZze == null || strZzf == null) {
            return false;
        }
        if (!str.equals(zzo.zzbF().zzai(strZze))) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Developer payload not match.");
            return false;
        }
        if (this.zzsU == null || zzl.zzc(this.zzsU, strZze, strZzf)) {
            return true;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaC("Fail to verify signature.");
        return false;
    }

    public String zzfi() {
        return zzo.zzbv().zzgn();
    }
}
