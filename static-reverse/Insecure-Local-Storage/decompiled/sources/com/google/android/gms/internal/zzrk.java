package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
class zzrk implements Cloneable {
    private zzri<?, ?> zzaWb;
    private Object zzaWc;
    private List<zzrp> zzaWd = new ArrayList();

    zzrk() {
    }

    private byte[] toByteArray() throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        byte[] bArr = new byte[zzB()];
        zza(zzrg.zzA(bArr));
        return bArr;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzrk)) {
            return false;
        }
        zzrk zzrkVar = (zzrk) o;
        if (this.zzaWc != null && zzrkVar.zzaWc != null) {
            if (this.zzaWb == zzrkVar.zzaWb) {
                return !this.zzaWb.zzaVV.isArray() ? this.zzaWc.equals(zzrkVar.zzaWc) : this.zzaWc instanceof byte[] ? Arrays.equals((byte[]) this.zzaWc, (byte[]) zzrkVar.zzaWc) : this.zzaWc instanceof int[] ? Arrays.equals((int[]) this.zzaWc, (int[]) zzrkVar.zzaWc) : this.zzaWc instanceof long[] ? Arrays.equals((long[]) this.zzaWc, (long[]) zzrkVar.zzaWc) : this.zzaWc instanceof float[] ? Arrays.equals((float[]) this.zzaWc, (float[]) zzrkVar.zzaWc) : this.zzaWc instanceof double[] ? Arrays.equals((double[]) this.zzaWc, (double[]) zzrkVar.zzaWc) : this.zzaWc instanceof boolean[] ? Arrays.equals((boolean[]) this.zzaWc, (boolean[]) zzrkVar.zzaWc) : Arrays.deepEquals((Object[]) this.zzaWc, (Object[]) zzrkVar.zzaWc);
            }
            return false;
        }
        if (this.zzaWd != null && zzrkVar.zzaWd != null) {
            return this.zzaWd.equals(zzrkVar.zzaWd);
        }
        try {
            return Arrays.equals(toByteArray(), zzrkVar.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    int zzB() {
        int iZzB = 0;
        if (this.zzaWc != null) {
            return this.zzaWb.zzQ(this.zzaWc);
        }
        Iterator<zzrp> it = this.zzaWd.iterator();
        while (true) {
            int i = iZzB;
            if (!it.hasNext()) {
                return i;
            }
            iZzB = it.next().zzB() + i;
        }
    }

    /* renamed from: zzBM, reason: merged with bridge method [inline-methods] */
    public final zzrk clone() {
        zzrk zzrkVar = new zzrk();
        try {
            zzrkVar.zzaWb = this.zzaWb;
            if (this.zzaWd == null) {
                zzrkVar.zzaWd = null;
            } else {
                zzrkVar.zzaWd.addAll(this.zzaWd);
            }
            if (this.zzaWc != null) {
                if (this.zzaWc instanceof zzrn) {
                    zzrkVar.zzaWc = ((zzrn) this.zzaWc).clone();
                } else if (this.zzaWc instanceof byte[]) {
                    zzrkVar.zzaWc = ((byte[]) this.zzaWc).clone();
                } else if (this.zzaWc instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.zzaWc;
                    byte[][] bArr2 = new byte[bArr.length][];
                    zzrkVar.zzaWc = bArr2;
                    for (int i = 0; i < bArr.length; i++) {
                        bArr2[i] = (byte[]) bArr[i].clone();
                    }
                } else if (this.zzaWc instanceof boolean[]) {
                    zzrkVar.zzaWc = ((boolean[]) this.zzaWc).clone();
                } else if (this.zzaWc instanceof int[]) {
                    zzrkVar.zzaWc = ((int[]) this.zzaWc).clone();
                } else if (this.zzaWc instanceof long[]) {
                    zzrkVar.zzaWc = ((long[]) this.zzaWc).clone();
                } else if (this.zzaWc instanceof float[]) {
                    zzrkVar.zzaWc = ((float[]) this.zzaWc).clone();
                } else if (this.zzaWc instanceof double[]) {
                    zzrkVar.zzaWc = ((double[]) this.zzaWc).clone();
                } else if (this.zzaWc instanceof zzrn[]) {
                    zzrn[] zzrnVarArr = (zzrn[]) this.zzaWc;
                    zzrn[] zzrnVarArr2 = new zzrn[zzrnVarArr.length];
                    zzrkVar.zzaWc = zzrnVarArr2;
                    for (int i2 = 0; i2 < zzrnVarArr.length; i2++) {
                        zzrnVarArr2[i2] = zzrnVarArr[i2].clone();
                    }
                }
            }
            return zzrkVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    void zza(zzrg zzrgVar) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (this.zzaWc != null) {
            this.zzaWb.zza(this.zzaWc, zzrgVar);
            return;
        }
        Iterator<zzrp> it = this.zzaWd.iterator();
        while (it.hasNext()) {
            it.next().zza(zzrgVar);
        }
    }

    void zza(zzrp zzrpVar) {
        this.zzaWd.add(zzrpVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T> T zzb(zzri<?, T> zzriVar) {
        if (this.zzaWc == null) {
            this.zzaWb = zzriVar;
            this.zzaWc = zzriVar.zzx(this.zzaWd);
            this.zzaWd = null;
        } else if (this.zzaWb != zzriVar) {
            throw new IllegalStateException("Tried to getExtension with a differernt Extension.");
        }
        return (T) this.zzaWc;
    }
}
