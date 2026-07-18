package com.google.android.gms.fitness.service;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.zzj;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
class zzb implements SensorEventDispatcher {
    private final zzj zzamr;

    zzb(zzj zzjVar) {
        this.zzamr = (zzj) zzu.zzu(zzjVar);
    }

    @Override // com.google.android.gms.fitness.service.SensorEventDispatcher
    public void publish(DataPoint dataPoint) throws RemoteException {
        dataPoint.zzqx();
        this.zzamr.zzc(dataPoint);
    }

    @Override // com.google.android.gms.fitness.service.SensorEventDispatcher
    public void publish(List<DataPoint> dataPoints) throws RemoteException {
        Iterator<DataPoint> it = dataPoints.iterator();
        while (it.hasNext()) {
            publish(it.next());
        }
    }
}
