package com.google.android.gms.internal;

import java.security.MessageDigest;

/* loaded from: classes.dex */
public class zzbo extends zzbl {
    private MessageDigest zzrS;

    byte[] zza(String[] strArr) {
        byte[] bArr = new byte[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            bArr[i] = zzj(zzbn.zzB(strArr[i]));
        }
        return bArr;
    }

    byte zzj(int i) {
        return (byte) ((((i & 255) ^ ((65280 & i) >> 8)) ^ ((16711680 & i) >> 16)) ^ (((-16777216) & i) >> 24));
    }

    @Override // com.google.android.gms.internal.zzbl
    public byte[] zzy(String str) {
        byte[] bArr;
        byte[] bArrZza = zza(str.split(" "));
        this.zzrS = zzcu();
        synchronized (this.zzqt) {
            if (this.zzrS == null) {
                bArr = new byte[0];
            } else {
                this.zzrS.reset();
                this.zzrS.update(bArrZza);
                byte[] bArrDigest = this.zzrS.digest();
                bArr = new byte[bArrDigest.length <= 4 ? bArrDigest.length : 4];
                System.arraycopy(bArrDigest, 0, bArr, 0, bArr.length);
            }
        }
        return bArr;
    }
}
