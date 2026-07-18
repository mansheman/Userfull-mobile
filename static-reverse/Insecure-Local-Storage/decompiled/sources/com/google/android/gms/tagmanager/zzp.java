package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.AbstractPendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.internal.zzpx;
import com.google.android.gms.internal.zzqa;
import com.google.android.gms.internal.zzqe;
import com.google.android.gms.internal.zzqf;
import com.google.android.gms.tagmanager.zzbf;
import com.google.android.gms.tagmanager.zzcb;
import com.google.android.gms.tagmanager.zzo;

/* loaded from: classes.dex */
public class zzp extends AbstractPendingResult<ContainerHolder> {
    private final Context mContext;
    private final Looper zzWt;
    private long zzaKD;
    private final TagManager zzaKK;
    private final zzd zzaKN;
    private final zzcd zzaKO;
    private final int zzaKP;
    private zzf zzaKQ;
    private zzqa zzaKR;
    private volatile zzo zzaKS;
    private volatile boolean zzaKT;
    private zzaf.zzj zzaKU;
    private String zzaKV;
    private zze zzaKW;
    private zza zzaKX;
    private final String zzaKy;
    private final zzlb zzpw;

    interface zza {
        boolean zzb(Container container);
    }

    private class zzb implements zzbf<zzpx.zza> {
        private zzb() {
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        /* renamed from: zza, reason: merged with bridge method [inline-methods] */
        public void zzz(zzpx.zza zzaVar) {
            zzaf.zzj zzjVar;
            if (zzaVar.zzaPa != null) {
                zzjVar = zzaVar.zzaPa;
            } else {
                zzaf.zzf zzfVar = zzaVar.zziO;
                zzjVar = new zzaf.zzj();
                zzjVar.zziO = zzfVar;
                zzjVar.zziN = null;
                zzjVar.zziP = zzfVar.version;
            }
            zzp.this.zza(zzjVar, zzaVar.zzaOZ, true);
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        public void zza(zzbf.zza zzaVar) {
            if (zzp.this.zzaKT) {
                return;
            }
            zzp.this.zzR(0L);
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        public void zzyv() {
        }
    }

    private class zzc implements zzbf<zzaf.zzj> {
        private zzc() {
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        public void zza(zzbf.zza zzaVar) {
            synchronized (zzp.this) {
                if (!zzp.this.isReady()) {
                    if (zzp.this.zzaKS != null) {
                        zzp.this.setResult(zzp.this.zzaKS);
                    } else {
                        zzp.this.setResult(zzp.this.createFailedResult(Status.zzXS));
                    }
                }
            }
            zzp.this.zzR(3600000L);
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
        public void zzz(zzaf.zzj zzjVar) {
            synchronized (zzp.this) {
                if (zzjVar.zziO == null) {
                    if (zzp.this.zzaKU.zziO == null) {
                        zzbg.zzaz("Current resource is null; network resource is also null");
                        zzp.this.zzR(3600000L);
                        return;
                    }
                    zzjVar.zziO = zzp.this.zzaKU.zziO;
                }
                zzp.this.zza(zzjVar, zzp.this.zzpw.currentTimeMillis(), false);
                zzbg.zzaB("setting refresh time to current time: " + zzp.this.zzaKD);
                if (!zzp.this.zzyu()) {
                    zzp.this.zza(zzjVar);
                }
            }
        }

        @Override // com.google.android.gms.tagmanager.zzbf
        public void zzyv() {
        }
    }

    private class zzd implements zzo.zza {
        private zzd() {
        }

        @Override // com.google.android.gms.tagmanager.zzo.zza
        public void zzej(String str) {
            zzp.this.zzej(str);
        }

        @Override // com.google.android.gms.tagmanager.zzo.zza
        public String zzyo() {
            return zzp.this.zzyo();
        }

        @Override // com.google.android.gms.tagmanager.zzo.zza
        public void zzyq() {
            if (zzp.this.zzaKO.zzkb()) {
                zzp.this.zzR(0L);
            }
        }
    }

    interface zze extends Releasable {
        void zza(zzbf<zzaf.zzj> zzbfVar);

        void zzem(String str);

        void zzf(long j, String str);
    }

    interface zzf extends Releasable {
        void zza(zzbf<zzpx.zza> zzbfVar);

        void zzb(zzpx.zza zzaVar);

        zzqf.zzc zziR(int i);

        void zzyw();
    }

    zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzf zzfVar, zze zzeVar, zzqa zzqaVar, zzlb zzlbVar, zzcd zzcdVar) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.zzaKK = tagManager;
        this.zzWt = looper == null ? Looper.getMainLooper() : looper;
        this.zzaKy = str;
        this.zzaKP = i;
        this.zzaKQ = zzfVar;
        this.zzaKW = zzeVar;
        this.zzaKR = zzqaVar;
        this.zzaKN = new zzd();
        this.zzaKU = new zzaf.zzj();
        this.zzpw = zzlbVar;
        this.zzaKO = zzcdVar;
        if (zzyu()) {
            zzej(zzcb.zzzf().zzzh());
        }
    }

    public zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzs zzsVar) {
        this(context, tagManager, looper, str, i, new zzcn(context, str), new zzcm(context, str, zzsVar), new zzqa(context), zzld.zzoQ(), new zzbe(30, 900000L, 5000L, "refreshing", zzld.zzoQ()));
        this.zzaKR.zzeU(zzsVar.zzyx());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void zzR(long j) {
        if (this.zzaKW == null) {
            zzbg.zzaC("Refresh requested, but no network load scheduler.");
        } else {
            this.zzaKW.zzf(j, this.zzaKU.zziP);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void zza(zzaf.zzj zzjVar) {
        if (this.zzaKQ != null) {
            zzpx.zza zzaVar = new zzpx.zza();
            zzaVar.zzaOZ = this.zzaKD;
            zzaVar.zziO = new zzaf.zzf();
            zzaVar.zzaPa = zzjVar;
            this.zzaKQ.zzb(zzaVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void zza(zzaf.zzj zzjVar, long j, boolean z) {
        if (z) {
            if (!this.zzaKT) {
            }
        }
        if (!isReady() || this.zzaKS == null) {
        }
        this.zzaKU = zzjVar;
        this.zzaKD = j;
        zzR(Math.max(0L, Math.min(43200000L, (this.zzaKD + 43200000) - this.zzpw.currentTimeMillis())));
        Container container = new Container(this.mContext, this.zzaKK.getDataLayer(), this.zzaKy, j, zzjVar);
        if (this.zzaKS == null) {
            this.zzaKS = new zzo(this.zzaKK, this.zzWt, container, this.zzaKN);
        } else {
            this.zzaKS.zza(container);
        }
        if (!isReady() && this.zzaKX.zzb(container)) {
            setResult(this.zzaKS);
        }
    }

    private void zzam(final boolean z) {
        this.zzaKQ.zza(new zzb());
        this.zzaKW.zza(new zzc());
        zzqf.zzc zzcVarZziR = this.zzaKQ.zziR(this.zzaKP);
        if (zzcVarZziR != null) {
            this.zzaKS = new zzo(this.zzaKK, this.zzWt, new Container(this.mContext, this.zzaKK.getDataLayer(), this.zzaKy, 0L, zzcVarZziR), this.zzaKN);
        }
        this.zzaKX = new zza() { // from class: com.google.android.gms.tagmanager.zzp.3
            @Override // com.google.android.gms.tagmanager.zzp.zza
            public boolean zzb(Container container) {
                return z ? container.getLastRefreshTime() + 43200000 >= zzp.this.zzpw.currentTimeMillis() : !container.isDefault();
            }
        };
        if (zzyu()) {
            this.zzaKW.zzf(0L, "");
        } else {
            this.zzaKQ.zzyw();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean zzyu() {
        zzcb zzcbVarZzzf = zzcb.zzzf();
        return (zzcbVarZzzf.zzzg() == zzcb.zza.CONTAINER || zzcbVarZzzf.zzzg() == zzcb.zza.CONTAINER_DEBUG) && this.zzaKy.equals(zzcbVarZzzf.getContainerId());
    }

    public void load(final String resourceIdParameterName) {
        this.zzaKR.zza(this.zzaKy, this.zzaKP != -1 ? Integer.valueOf(this.zzaKP) : null, resourceIdParameterName, new zzqa.zza() { // from class: com.google.android.gms.tagmanager.zzp.1
            @Override // com.google.android.gms.internal.zzqa.zza
            public void zza(zzqe zzqeVar) {
                if (zzqeVar.getStatus() != Status.zzXP) {
                    zzbg.zzaz("Load request failed for the container " + zzp.this.zzaKy);
                    zzp.this.setResult(zzp.this.createFailedResult(Status.zzXR));
                    return;
                }
                zzqf.zzc zzcVarZzAk = zzqeVar.zzAg().zzAk();
                if (zzcVarZzAk == null) {
                    zzbg.zzaz("Response doesn't have the requested container");
                    zzp.this.setResult(zzp.this.createFailedResult(new Status(8, "Response doesn't have the requested container", null)));
                } else {
                    zzp.this.zzaKS = new zzo(zzp.this.zzaKK, zzp.this.zzWt, new Container(zzp.this.mContext, zzp.this.zzaKK.getDataLayer(), zzp.this.zzaKy, zzqeVar.zzAg().zzAl(), zzcVarZzAk), new zzo.zza() { // from class: com.google.android.gms.tagmanager.zzp.1.1
                        @Override // com.google.android.gms.tagmanager.zzo.zza
                        public void zzej(String str) {
                            zzp.this.zzej(str);
                        }

                        @Override // com.google.android.gms.tagmanager.zzo.zza
                        public String zzyo() {
                            return zzp.this.zzyo();
                        }

                        @Override // com.google.android.gms.tagmanager.zzo.zza
                        public void zzyq() {
                            if (zzp.this.zzaKO.zzkb()) {
                                zzp.this.load(resourceIdParameterName);
                            }
                        }
                    });
                    zzp.this.setResult(zzp.this.zzaKS);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.AbstractPendingResult
    /* renamed from: zzaU, reason: merged with bridge method [inline-methods] */
    public ContainerHolder createFailedResult(Status status) {
        if (this.zzaKS != null) {
            return this.zzaKS;
        }
        if (status == Status.zzXS) {
            zzbg.zzaz("timer expired: setting result to failure");
        }
        return new zzo(status);
    }

    synchronized void zzej(String str) {
        this.zzaKV = str;
        if (this.zzaKW != null) {
            this.zzaKW.zzem(str);
        }
    }

    synchronized String zzyo() {
        return this.zzaKV;
    }

    public void zzyr() {
        zzqf.zzc zzcVarZziR = this.zzaKQ.zziR(this.zzaKP);
        if (zzcVarZziR != null) {
            setResult(new zzo(this.zzaKK, this.zzWt, new Container(this.mContext, this.zzaKK.getDataLayer(), this.zzaKy, 0L, zzcVarZziR), new zzo.zza() { // from class: com.google.android.gms.tagmanager.zzp.2
                @Override // com.google.android.gms.tagmanager.zzo.zza
                public void zzej(String str) {
                    zzp.this.zzej(str);
                }

                @Override // com.google.android.gms.tagmanager.zzo.zza
                public String zzyo() {
                    return zzp.this.zzyo();
                }

                @Override // com.google.android.gms.tagmanager.zzo.zza
                public void zzyq() {
                    zzbg.zzaC("Refresh ignored: container loaded as default only.");
                }
            }));
        } else {
            zzbg.zzaz("Default was requested, but no default container was found");
            setResult(createFailedResult(new Status(10, "Default was requested, but no default container was found", null)));
        }
        this.zzaKW = null;
        this.zzaKQ = null;
    }

    public void zzys() {
        zzam(false);
    }

    public void zzyt() {
        zzam(true);
    }
}
