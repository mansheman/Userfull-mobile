package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.wearable.internal.zzat;
import com.google.android.gms.wearable.internal.zzbj;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
final class zzau<T> {
    private final Map<T, zzbl<T>> zzakE = new HashMap();

    private static class zza<T> extends zzbj.zzb<Status> {
        private WeakReference<Map<T, zzbl<T>>> zzaUA;
        private WeakReference<T> zzaUB;

        zza(Map<T, zzbl<T>> map, T t, zza.zzb<Status> zzbVar) {
            super(zzbVar);
            this.zzaUA = new WeakReference<>(map);
            this.zzaUB = new WeakReference<>(t);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.wearable.internal.zza, com.google.android.gms.wearable.internal.zzar
        public void zza(Status status) {
            Map<T, zzbl<T>> map = this.zzaUA.get();
            T t = this.zzaUB.get();
            if (!status.getStatus().isSuccess() && map != null && t != null) {
                synchronized (map) {
                    zzbl<T> zzblVarRemove = map.remove(t);
                    if (zzblVarRemove != null) {
                        zzblVarRemove.clear();
                    }
                }
            }
            zzP(status);
        }
    }

    private static class zzb<T> extends zzbj.zzb<Status> {
        private WeakReference<Map<T, zzbl<T>>> zzaUA;
        private WeakReference<T> zzaUB;

        zzb(Map<T, zzbl<T>> map, T t, zza.zzb<Status> zzbVar) {
            super(zzbVar);
            this.zzaUA = new WeakReference<>(map);
            this.zzaUB = new WeakReference<>(t);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.wearable.internal.zza, com.google.android.gms.wearable.internal.zzar
        public void zza(Status status) {
            Map<T, zzbl<T>> map = this.zzaUA.get();
            T t = this.zzaUB.get();
            if (status.getStatus().getStatusCode() == 4002 && map != null && t != null) {
                synchronized (map) {
                    zzbl<T> zzblVarRemove = map.remove(t);
                    if (zzblVarRemove != null) {
                        zzblVarRemove.clear();
                    }
                }
            }
            zzP(status);
        }
    }

    zzau() {
    }

    public boolean isEmpty() {
        boolean zIsEmpty;
        synchronized (this.zzakE) {
            zIsEmpty = this.zzakE.isEmpty();
        }
        return zIsEmpty;
    }

    public void zza(zzbk zzbkVar, zza.zzb<Status> zzbVar, T t) throws RemoteException {
        synchronized (this.zzakE) {
            zzbl<T> zzblVarRemove = this.zzakE.remove(t);
            if (zzblVarRemove == null) {
                zzbVar.zzm(new Status(4002));
            } else {
                zzblVarRemove.clear();
                zzbkVar.zznM().zza(new zzb(this.zzakE, t, zzbVar), new RemoveListenerRequest(zzblVarRemove));
            }
        }
    }

    public void zza(zzbk zzbkVar, zza.zzb<Status> zzbVar, T t, zzbl<T> zzblVar) throws RemoteException {
        synchronized (this.zzakE) {
            if (this.zzakE.get(t) != null) {
                zzbVar.zzm(new Status(4001));
                return;
            }
            this.zzakE.put(t, zzblVar);
            try {
                zzbkVar.zznM().zza(new zza(this.zzakE, t, zzbVar), new AddListenerRequest(zzblVar));
            } catch (RemoteException e) {
                this.zzakE.remove(t);
                throw e;
            }
        }
    }

    public void zzb(zzbk zzbkVar) {
        synchronized (this.zzakE) {
            zzbj.zzo zzoVar = new zzbj.zzo();
            for (Map.Entry<T, zzbl<T>> entry : this.zzakE.entrySet()) {
                zzbl<T> value = entry.getValue();
                if (value != null) {
                    value.clear();
                    if (zzbkVar.isConnected()) {
                        try {
                            zzbkVar.zznM().zza(zzoVar, new RemoveListenerRequest(value));
                            if (Log.isLoggable("WearableClient", 2)) {
                                Log.d("WearableClient", "disconnect: removed: " + entry.getKey() + "/" + value);
                            }
                        } catch (RemoteException e) {
                            Log.w("WearableClient", "disconnect: Didn't remove: " + entry.getKey() + "/" + value);
                        }
                    }
                }
            }
            this.zzakE.clear();
        }
    }

    public void zzdR(IBinder iBinder) {
        synchronized (this.zzakE) {
            zzat zzatVarZzdQ = zzat.zza.zzdQ(iBinder);
            zzbj.zzo zzoVar = new zzbj.zzo();
            for (Map.Entry<T, zzbl<T>> entry : this.zzakE.entrySet()) {
                zzbl<T> value = entry.getValue();
                try {
                    zzatVarZzdQ.zza(zzoVar, new AddListenerRequest(value));
                    if (Log.isLoggable("WearableClient", 2)) {
                        Log.d("WearableClient", "onPostInitHandler: added: " + entry.getKey() + "/" + value);
                    }
                } catch (RemoteException e) {
                    Log.d("WearableClient", "onPostInitHandler: Didn't add: " + entry.getKey() + "/" + value);
                }
            }
        }
    }
}
