package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.internal.widget.ActivityChooserView;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.internal.zzmi;

/* loaded from: classes.dex */
public class zzlx extends zzlw<zzmi> {

    static abstract class zza<R extends Result> extends zza.AbstractC0032zza<R, zzlx> {
        public zza(GoogleApiClient googleApiClient) {
            super(Fitness.zzajw, googleApiClient);
        }
    }

    public static class zzb implements Api.zza<zzlx, Api.ApiOptions.NoOptions> {
        @Override // com.google.android.gms.common.api.Api.zza
        public int getPriority() {
            return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }

        @Override // com.google.android.gms.common.api.Api.zza
        /* renamed from: zzh, reason: merged with bridge method [inline-methods] */
        public zzlx zza(Context context, Looper looper, com.google.android.gms.common.internal.zze zzeVar, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzlx(context, looper, zzeVar, connectionCallbacks, onConnectionFailedListener);
        }
    }

    static abstract class zzc extends zza<Status> {
        zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
        public Status createFailedResult(Status status) {
            com.google.android.gms.common.internal.zzu.zzV(!status.isSuccess());
            return status;
        }
    }

    public zzlx(Context context, Looper looper, com.google.android.gms.common.internal.zze zzeVar, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 59, connectionCallbacks, onConnectionFailedListener, zzeVar);
    }

    @Override // com.google.android.gms.common.internal.zzi
    protected String getServiceDescriptor() {
        return "com.google.android.gms.fitness.internal.IGoogleFitBleApi";
    }

    @Override // com.google.android.gms.common.internal.zzi
    protected String getStartServiceAction() {
        return "com.google.android.gms.fitness.BleApi";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.internal.zzi
    /* renamed from: zzbi, reason: merged with bridge method [inline-methods] */
    public zzmi zzT(IBinder iBinder) {
        return zzmi.zza.zzbt(iBinder);
    }
}
