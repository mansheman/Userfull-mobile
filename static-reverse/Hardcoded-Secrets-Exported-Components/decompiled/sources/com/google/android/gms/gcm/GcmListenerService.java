package com.google.android.gms.gcm;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.media.MediaRouteProviderProtocol;
import android.util.Log;

/* loaded from: classes.dex */
public abstract class GcmListenerService extends Service {
    private int zzavB;
    private final Object zzqt = new Object();
    private int zzavC = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzk(Intent intent) {
        String str;
        try {
            if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra("message_type");
                str = stringExtra == null ? GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE : stringExtra;
                switch (str) {
                    case "gcm":
                        zzs(intent.getExtras());
                        break;
                    case "deleted_messages":
                        onDeletedMessages();
                        break;
                    case "send_event":
                        onMessageSent(intent.getStringExtra("google.message_id"));
                        break;
                    case "send_error":
                        onSendError(intent.getStringExtra("google.message_id"), intent.getStringExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR));
                        break;
                    default:
                        Log.w("GcmListenerService", "Received message with unknown type: " + str);
                        break;
                }
                synchronized (this.zzqt) {
                    this.zzavC--;
                    if (this.zzavC == 0) {
                        zzgf(this.zzavB);
                    }
                }
            }
        } finally {
            GcmReceiver.completeWakefulIntent(intent);
        }
    }

    private void zzs(Bundle bundle) {
        bundle.remove("message_type");
        bundle.remove("android.support.content.wakelockid");
        if (zza.zzt(bundle)) {
            zza.zzar(this).zzu(bundle);
            return;
        }
        String string = bundle.getString("from");
        bundle.remove("from");
        onMessageReceived(string, bundle);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    public void onDeletedMessages() {
    }

    public void onMessageReceived(String from, Bundle data) {
    }

    public void onMessageSent(String msgId) {
    }

    public void onSendError(String msgId, String error) {
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.google.android.gms.gcm.GcmListenerService$2] */
    @Override // android.app.Service
    public final int onStartCommand(final Intent intent, int flags, int startId) {
        synchronized (this.zzqt) {
            this.zzavB = startId;
            this.zzavC++;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.google.android.gms.gcm.GcmListenerService.1
                @Override // java.lang.Runnable
                public void run() {
                    GcmListenerService.this.zzk(intent);
                }
            });
            return 3;
        }
        new AsyncTask<Void, Void, Void>() { // from class: com.google.android.gms.gcm.GcmListenerService.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
            public Void doInBackground(Void... voidArr) {
                GcmListenerService.this.zzk(intent);
                return null;
            }
        }.execute(new Void[0]);
        return 3;
    }

    boolean zzgf(int i) {
        return stopSelfResult(i);
    }
}
