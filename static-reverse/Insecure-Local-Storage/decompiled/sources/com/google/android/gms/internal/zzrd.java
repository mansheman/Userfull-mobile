package com.google.android.gms.internal;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzrd extends zzrh<zzrd> {
    public String[] zzaVE;
    public int[] zzaVF;
    public byte[][] zzaVG;

    public zzrd() {
        zzBq();
    }

    public static zzrd zzx(byte[] bArr) throws zzrm {
        return (zzrd) zzrn.zza(new zzrd(), bArr);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzrd)) {
            return false;
        }
        zzrd zzrdVar = (zzrd) o;
        if (zzrl.equals(this.zzaVE, zzrdVar.zzaVE) && zzrl.equals(this.zzaVF, zzrdVar.zzaVF) && zzrl.zza(this.zzaVG, zzrdVar.zzaVG)) {
            return zza(zzrdVar);
        }
        return false;
    }

    public int hashCode() {
        return ((((((zzrl.hashCode(this.zzaVE) + 527) * 31) + zzrl.hashCode(this.zzaVF)) * 31) + zzrl.zza(this.zzaVG)) * 31) + zzBI();
    }

    @Override // com.google.android.gms.internal.zzrh, com.google.android.gms.internal.zzrn
    protected int zzB() {
        int length;
        int iZzB = super.zzB();
        if (this.zzaVE == null || this.zzaVE.length <= 0) {
            length = iZzB;
        } else {
            int iZzfj = 0;
            int i = 0;
            for (int i2 = 0; i2 < this.zzaVE.length; i2++) {
                String str = this.zzaVE[i2];
                if (str != null) {
                    i++;
                    iZzfj += zzrg.zzfj(str);
                }
            }
            length = iZzB + iZzfj + (i * 1);
        }
        if (this.zzaVF != null && this.zzaVF.length > 0) {
            int iZzkJ = 0;
            for (int i3 = 0; i3 < this.zzaVF.length; i3++) {
                iZzkJ += zzrg.zzkJ(this.zzaVF[i3]);
            }
            length = length + iZzkJ + (this.zzaVF.length * 1);
        }
        if (this.zzaVG == null || this.zzaVG.length <= 0) {
            return length;
        }
        int iZzC = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.zzaVG.length; i5++) {
            byte[] bArr = this.zzaVG[i5];
            if (bArr != null) {
                i4++;
                iZzC += zzrg.zzC(bArr);
            }
        }
        return length + iZzC + (i4 * 1);
    }

    public zzrd zzBq() {
        this.zzaVE = zzrq.zzaWm;
        this.zzaVF = zzrq.zzaWh;
        this.zzaVG = zzrq.zzaWn;
        this.zzaVU = null;
        this.zzaWf = -1;
        return this;
    }

    @Override // com.google.android.gms.internal.zzrh, com.google.android.gms.internal.zzrn
    public void zza(zzrg zzrgVar) throws IOException {
        if (this.zzaVE != null && this.zzaVE.length > 0) {
            for (int i = 0; i < this.zzaVE.length; i++) {
                String str = this.zzaVE[i];
                if (str != null) {
                    zzrgVar.zzb(1, str);
                }
            }
        }
        if (this.zzaVF != null && this.zzaVF.length > 0) {
            for (int i2 = 0; i2 < this.zzaVF.length; i2++) {
                zzrgVar.zzy(2, this.zzaVF[i2]);
            }
        }
        if (this.zzaVG != null && this.zzaVG.length > 0) {
            for (int i3 = 0; i3 < this.zzaVG.length; i3++) {
                byte[] bArr = this.zzaVG[i3];
                if (bArr != null) {
                    zzrgVar.zza(3, bArr);
                }
            }
        }
        super.zza(zzrgVar);
    }

    @Override // com.google.android.gms.internal.zzrn
    /* renamed from: zzz, reason: merged with bridge method [inline-methods] */
    public zzrd zzb(zzrf zzrfVar) throws IOException {
        while (true) {
            int iZzBr = zzrfVar.zzBr();
            switch (iZzBr) {
                case 0:
                    break;
                case 10:
                    int iZzb = zzrq.zzb(zzrfVar, 10);
                    int length = this.zzaVE == null ? 0 : this.zzaVE.length;
                    String[] strArr = new String[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzaVE, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = zzrfVar.readString();
                        zzrfVar.zzBr();
                        length++;
                    }
                    strArr[length] = zzrfVar.readString();
                    this.zzaVE = strArr;
                    break;
                case 16:
                    int iZzb2 = zzrq.zzb(zzrfVar, 16);
                    int length2 = this.zzaVF == null ? 0 : this.zzaVF.length;
                    int[] iArr = new int[iZzb2 + length2];
                    if (length2 != 0) {
                        System.arraycopy(this.zzaVF, 0, iArr, 0, length2);
                    }
                    while (length2 < iArr.length - 1) {
                        iArr[length2] = zzrfVar.zzBu();
                        zzrfVar.zzBr();
                        length2++;
                    }
                    iArr[length2] = zzrfVar.zzBu();
                    this.zzaVF = iArr;
                    break;
                case 18:
                    int iZzkC = zzrfVar.zzkC(zzrfVar.zzBy());
                    int position = zzrfVar.getPosition();
                    int i = 0;
                    while (zzrfVar.zzBD() > 0) {
                        zzrfVar.zzBu();
                        i++;
                    }
                    zzrfVar.zzkE(position);
                    int length3 = this.zzaVF == null ? 0 : this.zzaVF.length;
                    int[] iArr2 = new int[i + length3];
                    if (length3 != 0) {
                        System.arraycopy(this.zzaVF, 0, iArr2, 0, length3);
                    }
                    while (length3 < iArr2.length) {
                        iArr2[length3] = zzrfVar.zzBu();
                        length3++;
                    }
                    this.zzaVF = iArr2;
                    zzrfVar.zzkD(iZzkC);
                    break;
                case 26:
                    int iZzb3 = zzrq.zzb(zzrfVar, 26);
                    int length4 = this.zzaVG == null ? 0 : this.zzaVG.length;
                    byte[][] bArr = new byte[iZzb3 + length4][];
                    if (length4 != 0) {
                        System.arraycopy(this.zzaVG, 0, bArr, 0, length4);
                    }
                    while (length4 < bArr.length - 1) {
                        bArr[length4] = zzrfVar.readBytes();
                        zzrfVar.zzBr();
                        length4++;
                    }
                    bArr[length4] = zzrfVar.readBytes();
                    this.zzaVG = bArr;
                    break;
                default:
                    if (!zza(zzrfVar, iZzBr)) {
                        break;
                    } else {
                        break;
                    }
            }
        }
        return this;
    }
}
