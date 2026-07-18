package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.analytics.internal.zzac;
import java.util.Collections;

/* loaded from: classes.dex */
public class zzi extends zzd {
    private final zza zzJW;
    private zzac zzJX;
    private final zzt zzJY;
    private zzaj zzJZ;

    protected class zza implements ServiceConnection {
        private volatile zzac zzKb;
        private volatile boolean zzKc;

        protected zza() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder binder) {
            com.google.android.gms.common.internal.zzu.zzbY("AnalyticsServiceConnection.onServiceConnected");
            synchronized (this) {
                try {
                    if (binder == null) {
                        zzi.this.zzaX("Service connected with null binder");
                        return;
                    }
                    final zzac zzacVarZzac = null;
                    try {
                        String interfaceDescriptor = binder.getInterfaceDescriptor();
                        if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(interfaceDescriptor)) {
                            zzacVarZzac = zzac.zza.zzac(binder);
                            zzi.this.zzaT("Bound to IAnalyticsService interface");
                        } else {
                            zzi.this.zze("Got binder with a wrong descriptor", interfaceDescriptor);
                        }
                    } catch (RemoteException e) {
                        zzi.this.zzaX("Service connect failed to get IAnalyticsService");
                    }
                    if (zzacVarZzac == null) {
                        try {
                            com.google.android.gms.common.stats.zzb.zzoO().zza(zzi.this.getContext(), zzi.this.zzJW);
                        } catch (IllegalArgumentException e2) {
                        }
                    } else if (this.zzKc) {
                        this.zzKb = zzacVarZzac;
                    } else {
                        zzi.this.zzaW("onServiceConnected received after the timeout limit");
                        zzi.this.zzhS().zze(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzi.zza.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (zzi.this.isConnected()) {
                                    return;
                                }
                                zzi.this.zzaU("Connected to service after a timeout");
                                zzi.this.zza(zzacVarZzac);
                            }
                        });
                    }
                } finally {
                    notifyAll();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(final ComponentName name) {
            com.google.android.gms.common.internal.zzu.zzbY("AnalyticsServiceConnection.onServiceDisconnected");
            zzi.this.zzhS().zze(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzi.zza.2
                @Override // java.lang.Runnable
                public void run() {
                    zzi.this.onServiceDisconnected(name);
                }
            });
        }

        public zzac zzip() {
            zzac zzacVar = null;
            zzi.this.zzhO();
            Intent intent = new Intent("com.google.android.gms.analytics.service.START");
            intent.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.analytics.service.AnalyticsService"));
            Context context = zzi.this.getContext();
            intent.putExtra("app_package_name", context.getPackageName());
            com.google.android.gms.common.stats.zzb zzbVarZzoO = com.google.android.gms.common.stats.zzb.zzoO();
            synchronized (this) {
                this.zzKb = null;
                this.zzKc = true;
                boolean zZza = zzbVarZzoO.zza(context, intent, zzi.this.zzJW, 129);
                zzi.this.zza("Bind to service requested", Boolean.valueOf(zZza));
                if (zZza) {
                    try {
                        wait(zzi.this.zzhR().zzjs());
                    } catch (InterruptedException e) {
                        zzi.this.zzaW("Wait for service connect was interrupted");
                    }
                    this.zzKc = false;
                    zzacVar = this.zzKb;
                    this.zzKb = null;
                    if (zzacVar == null) {
                        zzi.this.zzaX("Successfully bound to service but never got onServiceConnected callback");
                    }
                } else {
                    this.zzKc = false;
                }
            }
            return zzacVar;
        }
    }

    protected zzi(zzf zzfVar) {
        super(zzfVar);
        this.zzJZ = new zzaj(zzfVar.zzhP());
        this.zzJW = new zza();
        this.zzJY = new zzt(zzfVar) { // from class: com.google.android.gms.analytics.internal.zzi.1
            @Override // com.google.android.gms.analytics.internal.zzt
            public void run() {
                zzi.this.zzio();
            }
        };
    }

    private void onDisconnect() {
        zzhl().zzhJ();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceDisconnected(ComponentName name) {
        zzhO();
        if (this.zzJX != null) {
            this.zzJX = null;
            zza("Disconnected from device AnalyticsService", name);
            onDisconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zza(zzac zzacVar) {
        zzhO();
        this.zzJX = zzacVar;
        zzin();
        zzhl().onServiceConnected();
    }

    private void zzin() {
        this.zzJZ.start();
        this.zzJY.zzt(zzhR().zzjr());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzio() {
        zzhO();
        if (isConnected()) {
            zzaT("Inactivity, disconnecting from device AnalyticsService");
            disconnect();
        }
    }

    public boolean connect() {
        zzhO();
        zzia();
        if (this.zzJX != null) {
            return true;
        }
        zzac zzacVarZzip = this.zzJW.zzip();
        if (zzacVarZzip == null) {
            return false;
        }
        this.zzJX = zzacVarZzip;
        zzin();
        return true;
    }

    public void disconnect() {
        zzhO();
        zzia();
        try {
            com.google.android.gms.common.stats.zzb.zzoO().zza(getContext(), this.zzJW);
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e2) {
        }
        if (this.zzJX != null) {
            this.zzJX = null;
            onDisconnect();
        }
    }

    public boolean isConnected() {
        zzhO();
        zzia();
        return this.zzJX != null;
    }

    public boolean zzb(zzab zzabVar) {
        com.google.android.gms.common.internal.zzu.zzu(zzabVar);
        zzhO();
        zzia();
        zzac zzacVar = this.zzJX;
        if (zzacVar == null) {
            return false;
        }
        try {
            zzacVar.zza(zzabVar.zzn(), zzabVar.zzjW(), zzabVar.zzjY() ? zzhR().zzjk() : zzhR().zzjl(), Collections.emptyList());
            zzin();
            return true;
        } catch (RemoteException e) {
            zzaT("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhn() {
    }

    public boolean zzim() {
        zzhO();
        zzia();
        zzac zzacVar = this.zzJX;
        if (zzacVar == null) {
            return false;
        }
        try {
            zzacVar.zzhG();
            zzin();
            return true;
        } catch (RemoteException e) {
            zzaT("Failed to clear hits from AnalyticsService");
            return false;
        }
    }
}
