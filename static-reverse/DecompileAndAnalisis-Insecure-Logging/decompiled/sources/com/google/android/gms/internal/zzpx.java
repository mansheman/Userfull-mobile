package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;
import java.io.IOException;

/* loaded from: classes.dex */
public interface zzpx {

    public static final class zza extends zzrh<zza> {
        public long zzaOZ;
        public zzaf.zzj zzaPa;
        public zzaf.zzf zziO;

        public zza() {
            zzzY();
        }

        public static zza zzs(byte[] bArr) throws zzrm {
            return (zza) zzrn.zza(new zza(), bArr);
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) o;
            if (this.zzaOZ != zzaVar.zzaOZ) {
                return false;
            }
            if (this.zziO == null) {
                if (zzaVar.zziO != null) {
                    return false;
                }
            } else if (!this.zziO.equals(zzaVar.zziO)) {
                return false;
            }
            if (this.zzaPa == null) {
                if (zzaVar.zzaPa != null) {
                    return false;
                }
            } else if (!this.zzaPa.equals(zzaVar.zzaPa)) {
                return false;
            }
            return zza(zzaVar);
        }

        public int hashCode() {
            return (((((this.zziO == null ? 0 : this.zziO.hashCode()) + ((((int) (this.zzaOZ ^ (this.zzaOZ >>> 32))) + 527) * 31)) * 31) + (this.zzaPa != null ? this.zzaPa.hashCode() : 0)) * 31) + zzBI();
        }

        @Override // com.google.android.gms.internal.zzrh, com.google.android.gms.internal.zzrn
        protected int zzB() {
            int iZzB = super.zzB() + zzrg.zzd(1, this.zzaOZ);
            if (this.zziO != null) {
                iZzB += zzrg.zzc(2, this.zziO);
            }
            return this.zzaPa != null ? iZzB + zzrg.zzc(3, this.zzaPa) : iZzB;
        }

        @Override // com.google.android.gms.internal.zzrh, com.google.android.gms.internal.zzrn
        public void zza(zzrg zzrgVar) throws IOException {
            zzrgVar.zzb(1, this.zzaOZ);
            if (this.zziO != null) {
                zzrgVar.zza(2, this.zziO);
            }
            if (this.zzaPa != null) {
                zzrgVar.zza(3, this.zzaPa);
            }
            super.zza(zzrgVar);
        }

        @Override // com.google.android.gms.internal.zzrn
        /* renamed from: zzu, reason: merged with bridge method [inline-methods] */
        public zza zzb(zzrf zzrfVar) throws IOException {
            while (true) {
                int iZzBr = zzrfVar.zzBr();
                switch (iZzBr) {
                    case 0:
                        break;
                    case 8:
                        this.zzaOZ = zzrfVar.zzBt();
                        break;
                    case 18:
                        if (this.zziO == null) {
                            this.zziO = new zzaf.zzf();
                        }
                        zzrfVar.zza(this.zziO);
                        break;
                    case 26:
                        if (this.zzaPa == null) {
                            this.zzaPa = new zzaf.zzj();
                        }
                        zzrfVar.zza(this.zzaPa);
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

        public zza zzzY() {
            this.zzaOZ = 0L;
            this.zziO = null;
            this.zzaPa = null;
            this.zzaVU = null;
            this.zzaWf = -1;
            return this;
        }
    }
}
