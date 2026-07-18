package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.game.Acls;

/* loaded from: classes.dex */
public final class AclsImpl implements Acls {

    /* renamed from: com.google.android.gms.games.internal.api.AclsImpl$2, reason: invalid class name */
    class AnonymousClass2 extends LoadNotifyAclImpl {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzg(this);
        }
    }

    /* renamed from: com.google.android.gms.games.internal.api.AclsImpl$3, reason: invalid class name */
    class AnonymousClass3 extends UpdateNotifyAclImpl {
        final /* synthetic */ String zzaqF;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.zza.AbstractC0032zza
        public void zza(GamesClientImpl gamesClientImpl) throws RemoteException {
            gamesClientImpl.zzn(this, this.zzaqF);
        }
    }

    private static abstract class LoadNotifyAclImpl extends Games.BaseGamesApiMethodImpl<Acls.LoadAclResult> {
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzU, reason: merged with bridge method [inline-methods] */
        public Acls.LoadAclResult createFailedResult(Status status) {
            return AclsImpl.zzS(status);
        }
    }

    private static abstract class UpdateNotifyAclImpl extends Games.BaseGamesApiMethodImpl<Status> {
        @Override // com.google.android.gms.common.api.AbstractPendingResult
        /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
        public Status createFailedResult(Status status) {
            return status;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Acls.LoadAclResult zzS(final Status status) {
        return new Acls.LoadAclResult() { // from class: com.google.android.gms.games.internal.api.AclsImpl.1
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
