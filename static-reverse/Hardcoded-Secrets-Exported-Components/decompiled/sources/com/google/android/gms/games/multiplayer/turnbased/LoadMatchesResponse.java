package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.TurnBasedMatchTurnStatus;
import com.google.android.gms.games.multiplayer.InvitationBuffer;

/* loaded from: classes.dex */
public final class LoadMatchesResponse {
    private final InvitationBuffer zzauD;
    private final TurnBasedMatchBuffer zzauE;
    private final TurnBasedMatchBuffer zzauF;
    private final TurnBasedMatchBuffer zzauG;

    public LoadMatchesResponse(Bundle matchData) {
        DataHolder dataHolderZza = zza(matchData, 0);
        if (dataHolderZza != null) {
            this.zzauD = new InvitationBuffer(dataHolderZza);
        } else {
            this.zzauD = null;
        }
        DataHolder dataHolderZza2 = zza(matchData, 1);
        if (dataHolderZza2 != null) {
            this.zzauE = new TurnBasedMatchBuffer(dataHolderZza2);
        } else {
            this.zzauE = null;
        }
        DataHolder dataHolderZza3 = zza(matchData, 2);
        if (dataHolderZza3 != null) {
            this.zzauF = new TurnBasedMatchBuffer(dataHolderZza3);
        } else {
            this.zzauF = null;
        }
        DataHolder dataHolderZza4 = zza(matchData, 3);
        if (dataHolderZza4 != null) {
            this.zzauG = new TurnBasedMatchBuffer(dataHolderZza4);
        } else {
            this.zzauG = null;
        }
    }

    private static DataHolder zza(Bundle bundle, int i) {
        String strZzfG = TurnBasedMatchTurnStatus.zzfG(i);
        if (bundle.containsKey(strZzfG)) {
            return (DataHolder) bundle.getParcelable(strZzfG);
        }
        return null;
    }

    @Deprecated
    public void close() {
        release();
    }

    public TurnBasedMatchBuffer getCompletedMatches() {
        return this.zzauG;
    }

    public InvitationBuffer getInvitations() {
        return this.zzauD;
    }

    public TurnBasedMatchBuffer getMyTurnMatches() {
        return this.zzauE;
    }

    public TurnBasedMatchBuffer getTheirTurnMatches() {
        return this.zzauF;
    }

    public boolean hasData() {
        if (this.zzauD != null && this.zzauD.getCount() > 0) {
            return true;
        }
        if (this.zzauE != null && this.zzauE.getCount() > 0) {
            return true;
        }
        if (this.zzauF == null || this.zzauF.getCount() <= 0) {
            return this.zzauG != null && this.zzauG.getCount() > 0;
        }
        return true;
    }

    public void release() {
        if (this.zzauD != null) {
            this.zzauD.release();
        }
        if (this.zzauE != null) {
            this.zzauE.release();
        }
        if (this.zzauF != null) {
            this.zzauF.release();
        }
        if (this.zzauG != null) {
            this.zzauG.release();
        }
    }
}
