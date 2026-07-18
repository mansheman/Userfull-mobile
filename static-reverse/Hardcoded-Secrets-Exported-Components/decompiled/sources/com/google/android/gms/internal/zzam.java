package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzal;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class zzam extends zzal {
    private static AdvertisingIdClient zznn = null;
    private static CountDownLatch zzno = new CountDownLatch(1);
    private static boolean zznp;
    private boolean zznq;

    class zza {
        private String zznr;
        private boolean zzns;

        public zza(String str, boolean z) {
            this.zznr = str;
            this.zzns = z;
        }

        public String getId() {
            return this.zznr;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzns;
        }
    }

    private static final class zzb implements Runnable {
        private Context zznu;

        public zzb(Context context) {
            this.zznu = context.getApplicationContext();
            if (this.zznu == null) {
                this.zznu = context;
            }
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalStateException {
            try {
                AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(this.zznu);
                advertisingIdClient.start();
                synchronized (zzam.class) {
                    if (zzam.zznn == null) {
                        AdvertisingIdClient unused = zzam.zznn = advertisingIdClient;
                    } else {
                        advertisingIdClient.finish();
                    }
                }
            } catch (GooglePlayServicesNotAvailableException e) {
                boolean unused2 = zzam.zznp = true;
            } catch (GooglePlayServicesRepairableException e2) {
            } catch (IOException e3) {
            }
            zzam.zzno.countDown();
        }
    }

    protected zzam(Context context, zzap zzapVar, zzaq zzaqVar, boolean z) {
        super(context, zzapVar, zzaqVar);
        this.zznq = z;
    }

    public static zzam zza(String str, Context context, boolean z) {
        zzah zzahVar = new zzah();
        zza(str, context, zzahVar);
        if (z) {
            synchronized (zzam.class) {
                if (zznn == null) {
                    new Thread(new zzb(context)).start();
                }
            }
        }
        return new zzam(context, zzahVar, new zzas(239), z);
    }

    zza zzY() throws IOException {
        zza zzaVar;
        synchronized (zzam.class) {
            try {
                if (!zzno.await(2L, TimeUnit.SECONDS)) {
                    zzaVar = new zza(null, false);
                } else if (zznn == null) {
                    zzaVar = new zza(null, false);
                } else {
                    AdvertisingIdClient.Info info = zznn.getInfo();
                    zzaVar = new zza(zzk(info.getId()), info.isLimitAdTrackingEnabled());
                }
            } catch (InterruptedException e) {
                zzaVar = new zza(null, false);
            }
        }
        return zzaVar;
    }

    @Override // com.google.android.gms.internal.zzal, com.google.android.gms.internal.zzak
    protected void zzc(Context context) {
        super.zzc(context);
        try {
            if (zznp || !this.zznq) {
                zza(24, zze(context));
            } else {
                zza zzaVarZzY = zzY();
                String id = zzaVarZzY.getId();
                if (id != null) {
                    zza(28, zzaVarZzY.isLimitAdTrackingEnabled() ? 1L : 0L);
                    zza(26, 5L);
                    zza(24, id);
                }
            }
        } catch (zzal.zza e) {
        } catch (IOException e2) {
        }
    }
}
