package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zza;

/* loaded from: classes.dex */
abstract class zzd<R extends Result> extends zza.AbstractC0032zza<R, CredentialsClientImpl> {
    zzd(GoogleApiClient googleApiClient) {
        super(Auth.CLIENT_KEY_CREDENTIALS_API, googleApiClient);
    }

    protected abstract void zza(Context context, ICredentialsService iCredentialsService) throws RemoteException;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
    public final void zza(CredentialsClientImpl credentialsClientImpl) throws RemoteException {
        zza(credentialsClientImpl.getContext(), credentialsClientImpl.zznM());
    }
}
