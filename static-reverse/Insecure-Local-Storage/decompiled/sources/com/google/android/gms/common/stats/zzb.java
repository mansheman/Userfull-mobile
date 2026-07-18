package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.common.stats.zzc;
import com.google.android.gms.internal.zzla;
import com.google.android.gms.internal.zzll;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class zzb {
    private static zzb zzack;
    private final List<String> zzacl;
    private final List<String> zzacm;
    private final List<String> zzacn;
    private final List<String> zzaco;
    private zze zzacq;
    private static final Object zzaaJ = new Object();
    private static final ComponentName zzacp = new ComponentName("com.google.android.gms", "com.google.android.gms.common.stats.GmsCoreStatsService");

    private zzb() {
        if (getLogLevel() == zzd.zzacz) {
            this.zzacl = Collections.EMPTY_LIST;
            this.zzacm = Collections.EMPTY_LIST;
            this.zzacn = Collections.EMPTY_LIST;
            this.zzaco = Collections.EMPTY_LIST;
            return;
        }
        String str = zzc.zza.zzacu.get();
        this.zzacl = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        String str2 = zzc.zza.zzacv.get();
        this.zzacm = str2 == null ? Collections.EMPTY_LIST : Arrays.asList(str2.split(","));
        String str3 = zzc.zza.zzacw.get();
        this.zzacn = str3 == null ? Collections.EMPTY_LIST : Arrays.asList(str3.split(","));
        String str4 = zzc.zza.zzacx.get();
        this.zzaco = str4 == null ? Collections.EMPTY_LIST : Arrays.asList(str4.split(","));
        this.zzacq = new zze(1024, zzc.zza.zzacy.get().longValue());
    }

    private int getLogLevel() {
        try {
            return zzla.zziW() ? zzc.zza.zzact.get().intValue() : zzd.zzacz;
        } catch (SecurityException e) {
            return zzd.zzacz;
        }
    }

    private void zza(Context context, ServiceConnection serviceConnection, String str, Intent intent, int i) {
        ConnectionEvent connectionEvent;
        if (com.google.android.gms.common.internal.zzd.zzZR) {
            String strZzb = zzb(serviceConnection);
            if (zza(context, str, intent, strZzb, i)) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                String strZzl = (getLogLevel() & zzd.zzacD) != 0 ? zzll.zzl(3, 5) : null;
                long nativeHeapAllocatedSize = (getLogLevel() & zzd.zzacF) != 0 ? Debug.getNativeHeapAllocatedSize() : 0L;
                if (i == 1 || i == 4) {
                    connectionEvent = new ConnectionEvent(jCurrentTimeMillis, i, null, null, null, null, strZzl, strZzb, SystemClock.elapsedRealtime(), nativeHeapAllocatedSize);
                } else {
                    ServiceInfo serviceInfoZzb = zzb(context, intent);
                    connectionEvent = new ConnectionEvent(jCurrentTimeMillis, i, zzll.zzaj(context), str, serviceInfoZzb.processName, serviceInfoZzb.name, strZzl, strZzb, SystemClock.elapsedRealtime(), nativeHeapAllocatedSize);
                }
                context.startService(new Intent().setComponent(zzacp).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", connectionEvent));
            }
        }
    }

    private boolean zza(Context context, Intent intent) {
        ComponentName component = intent.getComponent();
        if (component == null || (com.google.android.gms.common.internal.zzd.zzZR && "com.google.android.gms".equals(component.getPackageName()))) {
            return false;
        }
        return zzla.zzi(context, component.getPackageName());
    }

    private boolean zza(Context context, String str, Intent intent, String str2, int i) {
        int logLevel = getLogLevel();
        if (logLevel == zzd.zzacz || this.zzacq == null) {
            return false;
        }
        if (i == 4 || i == 1) {
            return this.zzacq.zzcq(str2);
        }
        ServiceInfo serviceInfoZzb = zzb(context, intent);
        if (serviceInfoZzb == null) {
            Log.w("ConnectionTracker", String.format("Client %s made an invalid request %s", str, intent.toUri(0)));
            return false;
        }
        String strZzaj = zzll.zzaj(context);
        String str3 = serviceInfoZzb.processName;
        String str4 = serviceInfoZzb.name;
        if (this.zzacl.contains(strZzaj) || this.zzacm.contains(str) || this.zzacn.contains(str3) || this.zzaco.contains(str4)) {
            return false;
        }
        if (str3.equals(strZzaj) && (logLevel & zzd.zzacE) != 0) {
            return false;
        }
        this.zzacq.zzcp(str2);
        return true;
    }

    private static ServiceInfo zzb(Context context, Intent intent) {
        List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(intent, 128);
        if (listQueryIntentServices == null || listQueryIntentServices.size() == 0) {
            Log.w("ConnectionTracker", String.format("There are no handler of this intent: %s\n Stack trace: %s", intent.toUri(0), zzll.zzl(3, 20)));
            return null;
        }
        if (listQueryIntentServices.size() > 1) {
            Log.w("ConnectionTracker", String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", intent.toUri(0), zzll.zzl(3, 20)));
            Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
            if (it.hasNext()) {
                Log.w("ConnectionTracker", it.next().serviceInfo.name);
                return null;
            }
        }
        return listQueryIntentServices.get(0).serviceInfo;
    }

    private String zzb(ServiceConnection serviceConnection) {
        return String.valueOf((Process.myPid() << 32) | System.identityHashCode(serviceConnection));
    }

    public static zzb zzoO() {
        synchronized (zzaaJ) {
            if (zzack == null) {
                zzack = new zzb();
            }
        }
        return zzack;
    }

    public void zza(Context context, ServiceConnection serviceConnection) {
        zza(context, serviceConnection, (String) null, (Intent) null, 1);
        context.unbindService(serviceConnection);
    }

    public void zza(Context context, ServiceConnection serviceConnection, String str, Intent intent) {
        zza(context, serviceConnection, str, intent, 3);
    }

    public boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int i) {
        return zza(context, context.getClass().getName(), intent, serviceConnection, i);
    }

    public boolean zza(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i) {
        if (zza(context, intent)) {
            Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        zza(context, serviceConnection, str, intent, 2);
        return context.bindService(intent, serviceConnection, i);
    }

    public void zzb(Context context, ServiceConnection serviceConnection) {
        zza(context, serviceConnection, (String) null, (Intent) null, 4);
    }
}
