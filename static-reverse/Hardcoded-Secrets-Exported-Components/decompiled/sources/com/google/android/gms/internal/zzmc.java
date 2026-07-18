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
import com.google.android.gms.internal.zzmn;

/* loaded from: classes.dex */
public class zzmc extends zzlw<zzmn> {

    static abstract class zza<R extends Result> extends zza.AbstractC0032zza<R, zzmc> {
        public zza(GoogleApiClient googleApiClient) {
            super(Fitness.zzajB, googleApiClient);
        }
    }

    public static class zzb implements Api.zza<zzmc, Api.ApiOptions.NoOptions> {
        @Override // com.google.android.gms.common.api.Api.zza
        public int getPriority() {
            return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }

        @Override // com.google.android.gms.common.api.Api.zza
        /* renamed from: zzm, reason: merged with bridge method [inline-methods] */
        public zzmc zza(Context context, Looper looper, com.google.android.gms.common.internal.zze zzeVar, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzmc(context, looper, zzeVar, connectionCallbacks, onConnectionFailedListener);
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

    public zzmc(Context context, Looper looper, com.google.android.gms.common.internal.zze zzeVar, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 55, connectionCallbacks, onConnectionFailedListener, zzeVar);
    }

    @Override // com.google.android.gms.common.internal.zzi
    protected String getServiceDescriptor() {
        return "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi";
    }

    @Override // com.google.android.gms.common.internal.zzi
    protected String getStartServiceAction() {
        return "com.google.android.gms.fitness.SensorsApi";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.internal.zzi
    /* renamed from: zzbn, reason: merged with bridge method [inline-methods] */
    public zzmn zzT(IBinder iBinder) {
        return zzmn.zza.zzby(iBinder);
    }
}
