package com.google.android.gms.games.internal.api;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.games.internal.GamesClientImpl;

/* loaded from: classes.dex */
public final class NotificationsImpl implements Notifications {

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$1, reason: invalid class name */
    class AnonymousClass1 extends Games.BaseGamesApiMethodImpl<Notifications.GameMuteStatusChangeResult> {
        final /* synthetic */ String zzarm;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzd((zza.zzb<Notifications.GameMuteStatusChangeResult>) this, this.zzarm, true);
        }

        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzaf, reason: merged with bridge method [inline-methods] */
        public Notifications.GameMuteStatusChangeResult createFailedResult(final Status status) {
            return new Notifications.GameMuteStatusChangeResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.1.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$2, reason: invalid class name */
    class AnonymousClass2 extends Games.BaseGamesApiMethodImpl<Notifications.GameMuteStatusChangeResult> {
        final /* synthetic */ String zzarm;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzd((zza.zzb<Notifications.GameMuteStatusChangeResult>) this, this.zzarm, false);
        }

        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzaf, reason: merged with bridge method [inline-methods] */
        public Notifications.GameMuteStatusChangeResult createFailedResult(final Status status) {
            return new Notifications.GameMuteStatusChangeResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.2.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$3, reason: invalid class name */
    class AnonymousClass3 extends Games.BaseGamesApiMethodImpl<Notifications.GameMuteStatusLoadResult> {
        final /* synthetic */ String zzarm;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzo(this, this.zzarm);
        }

        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzag, reason: merged with bridge method [inline-methods] */
        public Notifications.GameMuteStatusLoadResult createFailedResult(final Status status) {
            return new Notifications.GameMuteStatusLoadResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.3.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$4, reason: invalid class name */
    class AnonymousClass4 extends ContactSettingLoadImpl {
        final /* synthetic */ boolean zzaqy;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzh(this, this.zzaqy);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$5, reason: invalid class name */
    class AnonymousClass5 extends ContactSettingUpdateImpl {
        final /* synthetic */ boolean zzarq;
        final /* synthetic */ Bundle zzarr;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zza(this, this.zzarq, this.zzarr);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.NotificationsImpl$6, reason: invalid class name */
    class AnonymousClass6 extends InboxCountImpl {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzh(this);
        }
    }

    private static abstract class ContactSettingLoadImpl extends Games.BaseGamesApiMethodImpl<Notifications.ContactSettingLoadResult> {
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzah, reason: merged with bridge method [inline-methods] */
        public Notifications.ContactSettingLoadResult createFailedResult(final Status status) {
            return new Notifications.ContactSettingLoadResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.ContactSettingLoadImpl.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class ContactSettingUpdateImpl extends Games.BaseGamesApiMethodImpl<Status> {
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
        public Status createFailedResult(Status status) {
            return status;
        }
    }

    private static abstract class InboxCountImpl extends Games.BaseGamesApiMethodImpl<Notifications.InboxCountResult> {
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzai, reason: merged with bridge method [inline-methods] */
        public Notifications.InboxCountResult createFailedResult(final Status status) {
            return new Notifications.InboxCountResult() { // from class: com.google.android.gms.games.internal.api.NotificationsImpl.InboxCountImpl.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    @Override // com.google.android.gms.games.Notifications
    public void clear(GoogleApiClient apiClient, int notificationTypes) {
        GamesClientImpl gamesClientImplZzb = Games.zzb(apiClient, false);
        if (gamesClientImplZzb != null) {
            gamesClientImplZzb.zzfD(notificationTypes);
        }
    }

    @Override // com.google.android.gms.games.Notifications
    public void clearAll(GoogleApiClient apiClient) {
        clear(apiClient, 31);
    }
}
