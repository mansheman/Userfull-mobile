package com.google.android.gms.drive.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Pair;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.ChangesAvailableEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.CompletionListener;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.ProgressEvent;
import com.google.android.gms.drive.events.QueryResultEventParcelable;
import com.google.android.gms.drive.internal.zzam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class zzac extends zzam.zza {
    private final int zzaca;
    private final com.google.android.gms.drive.events.zzf zzafC;
    private final zza zzafD;
    private final List<Integer> zzafE = new ArrayList();

    private static class zza extends Handler {
        private final Context mContext;

        private zza(Looper looper, Context context) {
            super(looper);
            this.mContext = context;
        }

        private static void zza(com.google.android.gms.drive.events.zzl zzlVar, QueryResultEventParcelable queryResultEventParcelable) {
            DataHolder dataHolderZzpx = queryResultEventParcelable.zzpx();
            if (dataHolderZzpx != null) {
                final MetadataBuffer metadataBuffer = new MetadataBuffer(dataHolderZzpx);
                zzlVar.zza(new com.google.android.gms.drive.events.zzj() { // from class: com.google.android.gms.drive.internal.zzac.zza.1
                });
            }
            if (queryResultEventParcelable.zzpy()) {
                zzlVar.zzck(queryResultEventParcelable.zzpz());
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Pair pair = (Pair) msg.obj;
                    com.google.android.gms.drive.events.zzf zzfVar = (com.google.android.gms.drive.events.zzf) pair.first;
                    DriveEvent driveEvent = (DriveEvent) pair.second;
                    switch (driveEvent.getType()) {
                        case 1:
                            ((ChangeListener) zzfVar).onChange((ChangeEvent) driveEvent);
                            break;
                        case 2:
                            ((CompletionListener) zzfVar).onCompletion((CompletionEvent) driveEvent);
                            break;
                        case 3:
                            zza((com.google.android.gms.drive.events.zzl) zzfVar, (QueryResultEventParcelable) driveEvent);
                            break;
                        case 4:
                            ((com.google.android.gms.drive.events.zzc) zzfVar).zza((ChangesAvailableEvent) driveEvent);
                            break;
                        case 5:
                        case 6:
                            ((com.google.android.gms.drive.events.zzi) zzfVar).zza((ProgressEvent) driveEvent);
                            break;
                        default:
                            zzx.zzu("EventCallback", "Unexpected event: " + driveEvent);
                            break;
                    }
                default:
                    zzx.zze(this.mContext, "EventCallback", "Don't know how to handle this event");
                    break;
            }
        }

        public void zza(com.google.android.gms.drive.events.zzf zzfVar, DriveEvent driveEvent) {
            sendMessage(obtainMessage(1, new Pair(zzfVar, driveEvent)));
        }
    }

    public zzac(Looper looper, Context context, int i, com.google.android.gms.drive.events.zzf zzfVar) {
        this.zzaca = i;
        this.zzafC = zzfVar;
        this.zzafD = new zza(looper, context);
    }

    @Override // com.google.android.gms.drive.internal.zzam
    public void zzc(OnEventResponse onEventResponse) throws RemoteException {
        DriveEvent driveEventZzpO = onEventResponse.zzpO();
        com.google.android.gms.common.internal.zzu.zzU(this.zzaca == driveEventZzpO.getType());
        com.google.android.gms.common.internal.zzu.zzU(this.zzafE.contains(Integer.valueOf(driveEventZzpO.getType())));
        this.zzafD.zza(this.zzafC, driveEventZzpO);
    }

    public void zzcA(int i) {
        this.zzafE.add(Integer.valueOf(i));
    }

    public boolean zzcB(int i) {
        return this.zzafE.contains(Integer.valueOf(i));
    }
}
