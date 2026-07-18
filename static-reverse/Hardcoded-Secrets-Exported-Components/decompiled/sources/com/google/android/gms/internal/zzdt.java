package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzbb;
import com.google.android.gms.internal.zzhx;
import com.google.android.gms.search.SearchAuth;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@zzgd
/* loaded from: classes.dex */
public class zzdt {
    private final Context mContext;
    private final VersionInfoParcel zzoM;
    private final Object zzqt;
    private final String zzwO;
    private zzb<zzbb> zzwP;
    private zzb<zzbb> zzwQ;
    private zze zzwR;
    private int zzwS;

    /* renamed from: com.google.android.gms.internal.zzdt$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ zze zzwT;

        /* renamed from: com.google.android.gms.internal.zzdt$1$1, reason: invalid class name and collision with other inner class name */
        class C00851 implements zzbb.zza {
            final /* synthetic */ zzbb zzwV;

            C00851(zzbb zzbbVar) {
                this.zzwV = zzbbVar;
            }

            @Override // com.google.android.gms.internal.zzbb.zza
            public void zzcf() {
                new Timer().schedule(new TimerTask() { // from class: com.google.android.gms.internal.zzdt.1.1.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        synchronized (zzdt.this.zzqt) {
                            if (AnonymousClass1.this.zzwT.getStatus() == -1 || AnonymousClass1.this.zzwT.getStatus() == 1) {
                                return;
                            }
                            AnonymousClass1.this.zzwT.reject();
                            zzhl.runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzdt.1.1.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    C00851.this.zzwV.destroy();
                                }
                            });
                            com.google.android.gms.ads.internal.util.client.zzb.zzaB("Could not receive loaded message in a timely manner. Rejecting.");
                        }
                    }
                }, zza.zzxd);
            }
        }

        AnonymousClass1(zze zzeVar) {
            this.zzwT = zzeVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            final zzbb zzbbVarZza = zzdt.this.zza(zzdt.this.mContext, zzdt.this.zzoM);
            zzbbVarZza.zza(new C00851(zzbbVarZza));
            zzbbVarZza.zza("/jsLoaded", new zzdg() { // from class: com.google.android.gms.internal.zzdt.1.2
                @Override // com.google.android.gms.internal.zzdg
                public void zza(zzid zzidVar, Map<String, String> map) {
                    synchronized (zzdt.this.zzqt) {
                        if (AnonymousClass1.this.zzwT.getStatus() == -1 || AnonymousClass1.this.zzwT.getStatus() == 1) {
                            return;
                        }
                        zzdt.this.zzwS = 0;
                        zzdt.this.zzwP.zzc(zzbbVarZza);
                        AnonymousClass1.this.zzwT.zzg(zzbbVarZza);
                        zzdt.this.zzwR = AnonymousClass1.this.zzwT;
                        com.google.android.gms.ads.internal.util.client.zzb.zzaB("Successfully loaded JS Engine.");
                    }
                }
            });
            final zzhr zzhrVar = new zzhr();
            zzdg zzdgVar = new zzdg() { // from class: com.google.android.gms.internal.zzdt.1.3
                @Override // com.google.android.gms.internal.zzdg
                public void zza(zzid zzidVar, Map<String, String> map) {
                    synchronized (zzdt.this.zzqt) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaA("JS Engine is requesting an update");
                        if (zzdt.this.zzwS == 0) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzaA("Starting reload.");
                            zzdt.this.zzwS = 2;
                            zzdt.this.zzdT();
                        }
                        zzbbVarZza.zzb("/requestReload", (zzdg) zzhrVar.get());
                    }
                }
            };
            zzhrVar.set(zzdgVar);
            zzbbVarZza.zza("/requestReload", zzdgVar);
            if (zzdt.this.zzwO.endsWith(".js")) {
                zzbbVarZza.zzr(zzdt.this.zzwO);
            } else if (zzdt.this.zzwO.startsWith("<html>")) {
                zzbbVarZza.zzt(zzdt.this.zzwO);
            } else {
                zzbbVarZza.zzs(zzdt.this.zzwO);
            }
            new Timer().schedule(new TimerTask() { // from class: com.google.android.gms.internal.zzdt.1.4
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    synchronized (zzdt.this.zzqt) {
                        if (AnonymousClass1.this.zzwT.getStatus() == -1 || AnonymousClass1.this.zzwT.getStatus() == 1) {
                            return;
                        }
                        AnonymousClass1.this.zzwT.reject();
                        zzhl.runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzdt.1.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                zzbbVarZza.destroy();
                            }
                        });
                        com.google.android.gms.ads.internal.util.client.zzb.zzaB("Could not receive loaded message in a timely manner. Rejecting.");
                    }
                }
            }, zza.zzxc);
        }
    }

    static class zza {
        static int zzxc = 60000;
        static int zzxd = SearchAuth.StatusCodes.AUTH_DISABLED;
    }

    public interface zzb<T> {
        void zzc(T t);
    }

    public static class zzc<T> implements zzb<T> {
        @Override // com.google.android.gms.internal.zzdt.zzb
        public void zzc(T t) {
        }
    }

    public static class zzd extends zzhy<zzbe> {
        private final Object zzqt = new Object();
        private final zze zzxe;
        private boolean zzxf;

        public zzd(zze zzeVar) {
            this.zzxe = zzeVar;
        }

        public void release() {
            synchronized (this.zzqt) {
                if (this.zzxf) {
                    return;
                }
                this.zzxf = true;
                zza(new zzhx.zzc<zzbe>() { // from class: com.google.android.gms.internal.zzdt.zzd.1
                    @Override // com.google.android.gms.internal.zzhx.zzc
                    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
                    public void zzc(zzbe zzbeVar) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaB("Ending javascript session.");
                        ((zzbf) zzbeVar).zzcg();
                    }
                }, new zzhx.zzb());
                zza(new zzhx.zzc<zzbe>() { // from class: com.google.android.gms.internal.zzdt.zzd.2
                    @Override // com.google.android.gms.internal.zzhx.zzc
                    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
                    public void zzc(zzbe zzbeVar) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaB("Releasing engine reference.");
                        zzd.this.zzxe.zzdW();
                    }
                }, new zzhx.zza() { // from class: com.google.android.gms.internal.zzdt.zzd.3
                    @Override // com.google.android.gms.internal.zzhx.zza
                    public void run() {
                        zzd.this.zzxe.zzdW();
                    }
                });
            }
        }
    }

    public static class zze extends zzhy<zzbb> {
        private zzb<zzbb> zzwQ;
        private final Object zzqt = new Object();
        private boolean zzxh = false;
        private int zzxi = 0;

        public zze(zzb<zzbb> zzbVar) {
            this.zzwQ = zzbVar;
        }

        public zzd zzdV() {
            final zzd zzdVar = new zzd(this);
            synchronized (this.zzqt) {
                zza(new zzhx.zzc<zzbb>() { // from class: com.google.android.gms.internal.zzdt.zze.1
                    @Override // com.google.android.gms.internal.zzhx.zzc
                    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
                    public void zzc(zzbb zzbbVar) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaB("Getting a new session for JS Engine.");
                        zzdVar.zzg(zzbbVar.zzce());
                    }
                }, new zzhx.zza() { // from class: com.google.android.gms.internal.zzdt.zze.2
                    @Override // com.google.android.gms.internal.zzhx.zza
                    public void run() {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaB("Rejecting reference for JS Engine.");
                        zzdVar.reject();
                    }
                });
                com.google.android.gms.common.internal.zzu.zzU(this.zzxi >= 0);
                this.zzxi++;
            }
            return zzdVar;
        }

        protected void zzdW() {
            synchronized (this.zzqt) {
                com.google.android.gms.common.internal.zzu.zzU(this.zzxi >= 1);
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Releasing 1 reference for JS Engine");
                this.zzxi--;
                zzdY();
            }
        }

        public void zzdX() {
            synchronized (this.zzqt) {
                com.google.android.gms.common.internal.zzu.zzU(this.zzxi >= 0);
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Releasing root reference. JS Engine will be destroyed once other references are released.");
                this.zzxh = true;
                zzdY();
            }
        }

        protected void zzdY() {
            synchronized (this.zzqt) {
                com.google.android.gms.common.internal.zzu.zzU(this.zzxi >= 0);
                if (this.zzxh && this.zzxi == 0) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaB("No reference is left (including root). Cleaning up engine.");
                    zza(new zzhx.zzc<zzbb>() { // from class: com.google.android.gms.internal.zzdt.zze.3
                        @Override // com.google.android.gms.internal.zzhx.zzc
                        /* renamed from: zza, reason: merged with bridge method [inline-methods] */
                        public void zzc(final zzbb zzbbVar) {
                            zzhl.runOnUiThread(new Runnable() { // from class: com.google.android.gms.internal.zzdt.zze.3.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    zze.this.zzwQ.zzc(zzbbVar);
                                    zzbbVar.destroy();
                                }
                            });
                        }
                    }, new zzhx.zzb());
                } else {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaB("There are still references to the engine. Not destroying.");
                }
            }
        }
    }

    public zzdt(Context context, VersionInfoParcel versionInfoParcel, String str) {
        this.zzqt = new Object();
        this.zzwS = 1;
        this.zzwO = str;
        this.mContext = context.getApplicationContext();
        this.zzoM = versionInfoParcel;
        this.zzwP = new zzc();
        this.zzwQ = new zzc();
    }

    public zzdt(Context context, VersionInfoParcel versionInfoParcel, String str, zzb<zzbb> zzbVar, zzb<zzbb> zzbVar2) {
        this(context, versionInfoParcel, str);
        this.zzwP = zzbVar;
        this.zzwQ = zzbVar2;
    }

    private zze zzdS() {
        zze zzeVar = new zze(this.zzwQ);
        zzhl.runOnUiThread(new AnonymousClass1(zzeVar));
        return zzeVar;
    }

    protected zzbb zza(Context context, VersionInfoParcel versionInfoParcel) {
        return new zzbd(context, versionInfoParcel);
    }

    protected zze zzdT() {
        final zze zzeVarZzdS = zzdS();
        zzeVarZzdS.zza(new zzhx.zzc<zzbb>() { // from class: com.google.android.gms.internal.zzdt.2
            @Override // com.google.android.gms.internal.zzhx.zzc
            /* renamed from: zza, reason: merged with bridge method [inline-methods] */
            public void zzc(zzbb zzbbVar) {
                synchronized (zzdt.this.zzqt) {
                    zzdt.this.zzwS = 0;
                    if (zzdt.this.zzwR != null && zzeVarZzdS != zzdt.this.zzwR) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaB("New JS engine is loaded, marking previous one as destroyable.");
                        zzdt.this.zzwR.zzdX();
                    }
                    zzdt.this.zzwR = zzeVarZzdS;
                }
            }
        }, new zzhx.zza() { // from class: com.google.android.gms.internal.zzdt.3
            @Override // com.google.android.gms.internal.zzhx.zza
            public void run() {
                synchronized (zzdt.this.zzqt) {
                    zzdt.this.zzwS = 1;
                    com.google.android.gms.ads.internal.util.client.zzb.zzaB("Failed loading new engine. Marking new engine destroyable.");
                    zzeVarZzdS.zzdX();
                }
            }
        });
        return zzeVarZzdS;
    }

    public zzd zzdU() {
        zzd zzdVarZzdV;
        synchronized (this.zzqt) {
            if (this.zzwR == null || this.zzwR.getStatus() == -1) {
                this.zzwS = 2;
                this.zzwR = zzdT();
                zzdVarZzdV = this.zzwR.zzdV();
            } else if (this.zzwS == 0) {
                zzdVarZzdV = this.zzwR.zzdV();
            } else if (this.zzwS == 1) {
                this.zzwS = 2;
                zzdT();
                zzdVarZzdV = this.zzwR.zzdV();
            } else {
                zzdVarZzdV = this.zzwS == 2 ? this.zzwR.zzdV() : this.zzwR.zzdV();
            }
        }
        return zzdVarZzdV;
    }
}
