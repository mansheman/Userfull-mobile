package com.google.android.gms.internal;

import android.support.v7.internal.widget.ActivityChooserView;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzrf {
    private final byte[] buffer;
    private int zzaVK;
    private int zzaVL;
    private int zzaVM;
    private int zzaVN;
    private int zzaVO;
    private int zzaVQ;
    private int zzaVP = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int zzaVR = 64;
    private int zzaVS = 67108864;

    private zzrf(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzaVK = i;
        this.zzaVL = i + i2;
        this.zzaVN = i;
    }

    private void zzBC() {
        this.zzaVL += this.zzaVM;
        int i = this.zzaVL;
        if (i <= this.zzaVP) {
            this.zzaVM = 0;
        } else {
            this.zzaVM = i - this.zzaVP;
            this.zzaVL -= this.zzaVM;
        }
    }

    public static long zzV(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public static zzrf zza(byte[] bArr, int i, int i2) {
        return new zzrf(bArr, i, i2);
    }

    public static int zzkB(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public static zzrf zzz(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    public int getPosition() {
        return this.zzaVN - this.zzaVK;
    }

    public byte[] readBytes() throws IOException {
        int iZzBy = zzBy();
        if (iZzBy > this.zzaVL - this.zzaVN || iZzBy <= 0) {
            return zzkF(iZzBy);
        }
        byte[] bArr = new byte[iZzBy];
        System.arraycopy(this.buffer, this.zzaVN, bArr, 0, iZzBy);
        this.zzaVN = iZzBy + this.zzaVN;
        return bArr;
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(zzBB());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(zzBA());
    }

    public String readString() throws IOException {
        int iZzBy = zzBy();
        if (iZzBy > this.zzaVL - this.zzaVN || iZzBy <= 0) {
            return new String(zzkF(iZzBy), "UTF-8");
        }
        String str = new String(this.buffer, this.zzaVN, iZzBy, "UTF-8");
        this.zzaVN = iZzBy + this.zzaVN;
        return str;
    }

    public int zzBA() throws IOException {
        return (zzBF() & 255) | ((zzBF() & 255) << 8) | ((zzBF() & 255) << 16) | ((zzBF() & 255) << 24);
    }

    public long zzBB() throws IOException {
        return ((zzBF() & 255) << 8) | (zzBF() & 255) | ((zzBF() & 255) << 16) | ((zzBF() & 255) << 24) | ((zzBF() & 255) << 32) | ((zzBF() & 255) << 40) | ((zzBF() & 255) << 48) | ((zzBF() & 255) << 56);
    }

    public int zzBD() {
        if (this.zzaVP == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzaVP - this.zzaVN;
    }

    public boolean zzBE() {
        return this.zzaVN == this.zzaVL;
    }

    public byte zzBF() throws IOException {
        if (this.zzaVN == this.zzaVL) {
            throw zzrm.zzBN();
        }
        byte[] bArr = this.buffer;
        int i = this.zzaVN;
        this.zzaVN = i + 1;
        return bArr[i];
    }

    public int zzBr() throws IOException {
        if (zzBE()) {
            this.zzaVO = 0;
            return 0;
        }
        this.zzaVO = zzBy();
        if (this.zzaVO == 0) {
            throw zzrm.zzBQ();
        }
        return this.zzaVO;
    }

    public void zzBs() throws IOException {
        int iZzBr;
        do {
            iZzBr = zzBr();
            if (iZzBr == 0) {
                return;
            }
        } while (zzkA(iZzBr));
    }

    public long zzBt() throws IOException {
        return zzBz();
    }

    public int zzBu() throws IOException {
        return zzBy();
    }

    public boolean zzBv() throws IOException {
        return zzBy() != 0;
    }

    public int zzBw() throws IOException {
        return zzkB(zzBy());
    }

    public long zzBx() throws IOException {
        return zzV(zzBz());
    }

    public int zzBy() throws IOException {
        byte bZzBF = zzBF();
        if (bZzBF >= 0) {
            return bZzBF;
        }
        int i = bZzBF & 127;
        byte bZzBF2 = zzBF();
        if (bZzBF2 >= 0) {
            return i | (bZzBF2 << 7);
        }
        int i2 = i | ((bZzBF2 & 127) << 7);
        byte bZzBF3 = zzBF();
        if (bZzBF3 >= 0) {
            return i2 | (bZzBF3 << 14);
        }
        int i3 = i2 | ((bZzBF3 & 127) << 14);
        byte bZzBF4 = zzBF();
        if (bZzBF4 >= 0) {
            return i3 | (bZzBF4 << 21);
        }
        int i4 = i3 | ((bZzBF4 & 127) << 21);
        byte bZzBF5 = zzBF();
        int i5 = i4 | (bZzBF5 << 28);
        if (bZzBF5 >= 0) {
            return i5;
        }
        for (int i6 = 0; i6 < 5; i6++) {
            if (zzBF() >= 0) {
                return i5;
            }
        }
        throw zzrm.zzBP();
    }

    public long zzBz() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & 127) << i;
            if ((zzBF() & 128) == 0) {
                return j;
            }
        }
        throw zzrm.zzBP();
    }

    public void zza(zzrn zzrnVar) throws IOException {
        int iZzBy = zzBy();
        if (this.zzaVQ >= this.zzaVR) {
            throw zzrm.zzBT();
        }
        int iZzkC = zzkC(iZzBy);
        this.zzaVQ++;
        zzrnVar.zzb(this);
        zzkz(0);
        this.zzaVQ--;
        zzkD(iZzkC);
    }

    public void zza(zzrn zzrnVar, int i) throws IOException {
        if (this.zzaVQ >= this.zzaVR) {
            throw zzrm.zzBT();
        }
        this.zzaVQ++;
        zzrnVar.zzb(this);
        zzkz(zzrq.zzD(i, 4));
        this.zzaVQ--;
    }

    public boolean zzkA(int i) throws IOException {
        switch (zzrq.zzkU(i)) {
            case 0:
                zzBu();
                return true;
            case 1:
                zzBB();
                return true;
            case 2:
                zzkG(zzBy());
                return true;
            case 3:
                zzBs();
                zzkz(zzrq.zzD(zzrq.zzkV(i), 4));
                return true;
            case 4:
                return false;
            case 5:
                zzBA();
                return true;
            default:
                throw zzrm.zzBS();
        }
    }

    public int zzkC(int i) throws zzrm {
        if (i < 0) {
            throw zzrm.zzBO();
        }
        int i2 = this.zzaVN + i;
        int i3 = this.zzaVP;
        if (i2 > i3) {
            throw zzrm.zzBN();
        }
        this.zzaVP = i2;
        zzBC();
        return i3;
    }

    public void zzkD(int i) {
        this.zzaVP = i;
        zzBC();
    }

    public void zzkE(int i) {
        if (i > this.zzaVN - this.zzaVK) {
            throw new IllegalArgumentException("Position " + i + " is beyond current " + (this.zzaVN - this.zzaVK));
        }
        if (i < 0) {
            throw new IllegalArgumentException("Bad position " + i);
        }
        this.zzaVN = this.zzaVK + i;
    }

    public byte[] zzkF(int i) throws IOException {
        if (i < 0) {
            throw zzrm.zzBO();
        }
        if (this.zzaVN + i > this.zzaVP) {
            zzkG(this.zzaVP - this.zzaVN);
            throw zzrm.zzBN();
        }
        if (i > this.zzaVL - this.zzaVN) {
            throw zzrm.zzBN();
        }
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, this.zzaVN, bArr, 0, i);
        this.zzaVN += i;
        return bArr;
    }

    public void zzkG(int i) throws IOException {
        if (i < 0) {
            throw zzrm.zzBO();
        }
        if (this.zzaVN + i > this.zzaVP) {
            zzkG(this.zzaVP - this.zzaVN);
            throw zzrm.zzBN();
        }
        if (i > this.zzaVL - this.zzaVN) {
            throw zzrm.zzBN();
        }
        this.zzaVN += i;
    }

    public void zzkz(int i) throws zzrm {
        if (this.zzaVO != i) {
            throw zzrm.zzBR();
        }
    }

    public byte[] zzx(int i, int i2) {
        if (i2 == 0) {
            return zzrq.zzaWo;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzaVK + i, bArr, 0, i2);
        return bArr;
    }
}
