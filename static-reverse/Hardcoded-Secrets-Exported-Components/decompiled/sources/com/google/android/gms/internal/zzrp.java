package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes.dex */
final class zzrp {
    final int tag;
    final byte[] zzaWg;

    zzrp(int i, byte[] bArr) {
        this.tag = i;
        this.zzaWg = bArr;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzrp)) {
            return false;
        }
        zzrp zzrpVar = (zzrp) o;
        return this.tag == zzrpVar.tag && Arrays.equals(this.zzaWg, zzrpVar.zzaWg);
    }

    public int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzaWg);
    }

    int zzB() {
        return 0 + zzrg.zzkO(this.tag) + this.zzaWg.length;
    }

    void zza(zzrg zzrgVar) throws IOException {
        zzrgVar.zzkN(this.tag);
        zzrgVar.zzD(this.zzaWg);
    }
}
