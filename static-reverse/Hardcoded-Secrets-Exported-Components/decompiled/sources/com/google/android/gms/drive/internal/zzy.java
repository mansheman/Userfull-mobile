package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.drive.internal.zzr;

/* loaded from: classes.dex */
public class zzy implements DrivePreferencesApi {

    private class zza extends zzd {
        private final zza.zzb<DrivePreferencesApi.FileUploadPreferencesResult> zzOs;

        private zza(zza.zzb<DrivePreferencesApi.FileUploadPreferencesResult> zzbVar) {
            this.zzOs = zzbVar;
        }

        @Override // com.google.android.gms.drive.internal.zzd, com.google.android.gms.drive.internal.zzal
        public void zza(OnDeviceUsagePreferenceResponse onDeviceUsagePreferenceResponse) throws RemoteException {
            this.zzOs.zzm(new zzb(Status.zzXP, onDeviceUsagePreferenceResponse.zzpL()));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.drive.internal.zzd, com.google.android.gms.drive.internal.zzal
        public void zzt(Status status) throws RemoteException {
            this.zzOs.zzm(new zzb(status, null));
        }
    }

    private class zzb implements DrivePreferencesApi.FileUploadPreferencesResult {
        private final Status zzOt;
        private final FileUploadPreferences zzafw;

        private zzb(Status status, FileUploadPreferences fileUploadPreferences) {
            this.zzOt = status;
            this.zzafw = fileUploadPreferences;
        }

        @Override // com.google.android.gms.drive.DrivePreferencesApi.FileUploadPreferencesResult
        public FileUploadPreferences getFileUploadPreferences() {
            return this.zzafw;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.zzOt;
        }
    }

    private abstract class zzc extends zzr<DrivePreferencesApi.FileUploadPreferencesResult> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzz, reason: merged with bridge method [inline-methods] */
        public DrivePreferencesApi.FileUploadPreferencesResult createFailedResult(Status status) {
            return new zzb(status, null);
        }
    }

    @Override // com.google.android.gms.drive.DrivePreferencesApi
    public PendingResult<DrivePreferencesApi.FileUploadPreferencesResult> getFileUploadPreferences(GoogleApiClient apiClient) {
        return apiClient.zza((GoogleApiClient) new zzc(apiClient) { // from class: com.google.android.gms.drive.internal.zzy.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
            public void zza(zzs zzsVar) throws RemoteException {
                zzsVar.zzpB().zzd(new zza(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DrivePreferencesApi
    public PendingResult<Status> setFileUploadPreferences(GoogleApiClient apiClient, FileUploadPreferences fileUploadPreferences) {
        if (!(fileUploadPreferences instanceof FileUploadPreferencesImpl)) {
            throw new IllegalArgumentException("Invalid preference value");
        }
        final FileUploadPreferencesImpl fileUploadPreferencesImpl = (FileUploadPreferencesImpl) fileUploadPreferences;
        return apiClient.zzb(new zzr.zza(apiClient) { // from class: com.google.android.gms.drive.internal.zzy.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
            public void zza(zzs zzsVar) throws RemoteException {
                zzsVar.zzpB().zza(new SetFileUploadPreferencesRequest(fileUploadPreferencesImpl), new zzbq(this));
            }
        });
    }
}
