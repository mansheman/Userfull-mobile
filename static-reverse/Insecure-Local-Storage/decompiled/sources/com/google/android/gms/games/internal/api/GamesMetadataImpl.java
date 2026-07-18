package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.internal.GamesClientImpl;

/* loaded from: classes.dex */
public final class GamesMetadataImpl implements GamesMetadata {

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$2, reason: invalid class name */
    class AnonymousClass2 extends LoadGameInstancesImpl {
        final /* synthetic */ String zzaqA;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzj(this, this.zzaqA);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$3, reason: invalid class name */
    class AnonymousClass3 extends LoadGameSearchSuggestionsImpl {
        final /* synthetic */ String zzaqR;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzk(this, this.zzaqR);
        }
    }

    private static abstract class LoadGameInstancesImpl extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGameInstancesResult> {
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzX, reason: merged with bridge method [inline-methods] */
        public GamesMetadata.LoadGameInstancesResult createFailedResult(final Status status) {
            return new GamesMetadata.LoadGameInstancesResult() { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGameInstancesImpl.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }

                @Override // com.google.android.gms.common.api.Releasable
                public void release() {
                }
            };
        }
    }

    private static abstract class LoadGameSearchSuggestionsImpl extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGameSearchSuggestionsResult> {
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzY, reason: merged with bridge method [inline-methods] */
        public GamesMetadata.LoadGameSearchSuggestionsResult createFailedResult(final Status status) {
            return new GamesMetadata.LoadGameSearchSuggestionsResult() { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGameSearchSuggestionsImpl.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }

                @Override // com.google.android.gms.common.api.Releasable
                public void release() {
                }
            };
        }
    }

    private static abstract class LoadGamesImpl extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGamesResult> {
        private LoadGamesImpl(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzZ, reason: merged with bridge method [inline-methods] */
        public GamesMetadata.LoadGamesResult createFailedResult(final Status status) {
            return new GamesMetadata.LoadGamesResult() { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGamesImpl.1
                @Override // com.google.android.gms.games.GamesMetadata.LoadGamesResult
                public GameBuffer getGames() {
                    return new GameBuffer(DataHolder.zzbi(14));
                }

                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }

                @Override // com.google.android.gms.common.api.Releasable
                public void release() {
                }
            };
        }
    }

    @Override // com.google.android.gms.games.GamesMetadata
    public Game getCurrentGame(GoogleApiClient apiClient) {
        return Games.zzd(apiClient).zzsy();
    }

    @Override // com.google.android.gms.games.GamesMetadata
    public PendingResult<GamesMetadata.LoadGamesResult> loadGame(GoogleApiClient apiClient) {
        return apiClient.zza((GoogleApiClient) new LoadGamesImpl(apiClient) { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
            public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
                gamesClientImpl.zzf(this);
            }
        });
    }
}
