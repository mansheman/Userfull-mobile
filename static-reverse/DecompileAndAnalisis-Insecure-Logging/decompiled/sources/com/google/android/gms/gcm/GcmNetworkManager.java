package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzu;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class GcmNetworkManager {
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_RESCHEDULE = 1;
    public static final int RESULT_SUCCESS = 0;
    private static GcmNetworkManager zzavE;
    private Context mContext;
    private final PendingIntent mPendingIntent;

    private GcmNetworkManager(Context context) {
        this.mContext = context;
        this.mPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent(), 0);
    }

    public static GcmNetworkManager getInstance(Context context) {
        if (zzavE == null) {
            zzavE = new GcmNetworkManager(context.getApplicationContext());
        }
        return zzavE;
    }

    static void zzcY(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Must provide a valid tag.");
        }
        if (100 < str.length()) {
            throw new IllegalArgumentException("Tag is larger than max permissible tag length (100)");
        }
    }

    private void zzcZ(String str) {
        boolean z = true;
        zzu.zzb(str, "GcmTaskService must not be null.");
        Intent intent = new Intent(GcmTaskService.SERVICE_ACTION_EXECUTE_TASK);
        intent.setPackage(this.mContext.getPackageName());
        List<ResolveInfo> listQueryIntentServices = this.mContext.getPackageManager().queryIntentServices(intent, 0);
        zzu.zzb((listQueryIntentServices == null || listQueryIntentServices.size() == 0) ? false : true, "There is no GcmTaskService component registered within this package. Have you extended GcmTaskService correctly?");
        Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (it.next().serviceInfo.name.equals(str)) {
                break;
            }
        }
        zzu.zzb(z, "The GcmTaskService class you provided " + str + " does not seem to support receiving com.google.android.gms.gcm.ACTION_TASK_READY.");
    }

    private Intent zztR() {
        int iZzat = GoogleCloudMessaging.zzat(this.mContext);
        if (iZzat < GoogleCloudMessaging.zzavP) {
            Log.e("GcmNetworkManager", "Google Play Services is not available, dropping GcmNetworkManager request. code=" + iZzat);
            return null;
        }
        Intent intent = new Intent("com.google.android.gms.gcm.ACTION_SCHEDULE");
        intent.setPackage(GoogleCloudMessaging.zzas(this.mContext));
        intent.putExtra("app", this.mPendingIntent);
        return intent;
    }

    public void cancelAllTasks(Class<? extends GcmTaskService> gcmTaskService) {
        zzcZ(gcmTaskService.getName());
        Intent intentZztR = zztR();
        if (intentZztR == null) {
            return;
        }
        intentZztR.putExtra("scheduler_action", "CANCEL_ALL");
        intentZztR.putExtra("component", new ComponentName(this.mContext, gcmTaskService));
        this.mContext.sendBroadcast(intentZztR);
    }

    public void cancelTask(String tag, Class<? extends GcmTaskService> gcmTaskService) {
        zzcY(tag);
        zzcZ(gcmTaskService.getName());
        Intent intentZztR = zztR();
        if (intentZztR == null) {
            return;
        }
        intentZztR.putExtra("scheduler_action", "CANCEL_TASK");
        intentZztR.putExtra("tag", tag);
        intentZztR.putExtra("component", new ComponentName(this.mContext, gcmTaskService));
        this.mContext.sendBroadcast(intentZztR);
    }

    public void schedule(Task task) {
        zzcZ(task.getServiceName());
        Intent intentZztR = zztR();
        if (intentZztR == null) {
            return;
        }
        Bundle extras = intentZztR.getExtras();
        extras.putString("scheduler_action", "SCHEDULE_TASK");
        task.toBundle(extras);
        intentZztR.putExtras(extras);
        this.mContext.sendBroadcast(intentZztR);
    }
}
