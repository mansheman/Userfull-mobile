package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.internal.zzft;
import com.google.android.gms.internal.zzha;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzgd
/* loaded from: classes.dex */
public class zzfx extends zzhh {
    private final zzfy zzBC;
    private Future<zzha> zzBD;
    private final zzft.zza zzBq;
    private final zzha.zza zzBs;
    private final AdResponseParcel zzBt;
    private final Object zzqt;

    public zzfx(Context context, com.google.android.gms.ads.internal.zzm zzmVar, zzbc zzbcVar, zzha.zza zzaVar, zzan zzanVar, zzft.zza zzaVar2) {
        this(zzaVar, zzaVar2, new zzfy(context, zzmVar, zzbcVar, new zzho(context), zzanVar, zzaVar));
    }

    zzfx(zzha.zza zzaVar, zzft.zza zzaVar2, zzfy zzfyVar) {
        this.zzqt = new Object();
        this.zzBs = zzaVar;
        this.zzBt = zzaVar.zzFs;
        this.zzBq = zzaVar2;
        this.zzBC = zzfyVar;
    }

    private zzha zzA(int i) {
        return new zzha(this.zzBs.zzFr.zzCm, null, null, i, null, null, this.zzBt.orientation, this.zzBt.zzxJ, this.zzBs.zzFr.zzCp, false, null, null, null, null, null, this.zzBt.zzCL, this.zzBs.zzpN, this.zzBt.zzCJ, this.zzBs.zzFo, this.zzBt.zzCO, this.zzBt.zzCP, this.zzBs.zzFl, null, this.zzBs.zzFr.zzCC);
    }

    @Override // com.google.android.gms.internal.zzhh
    public void onStop() {
        synchronized (this.zzqt) {
            if (this.zzBD != null) {
                this.zzBD.cancel(true);
            }
        }
    }

    @Override // com.google.android.gms.internal.zzhh
    public void zzdP() throws ExecutionException, InterruptedException, TimeoutException {
        int i;
        final zzha zzhaVarZzA;
        try {
            synchronized (this.zzqt) {
                this.zzBD = zzhk.zza(this.zzBC);
            }
            zzhaVarZzA = this.zzBD.get(60000L, TimeUnit.MILLISECONDS);
            i = -2;
        } catch (InterruptedException e) {
            zzhaVarZzA = null;
            i = -1;
        } catch (CancellationException e2) {
            zzhaVarZzA = null;
            i = -1;
        } catch (ExecutionException e3) {
            i = 0;
            zzhaVarZzA = null;
        } catch (TimeoutException e4) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Timed out waiting for native ad.");
            this.zzBD.cancel(true);
            i = 2;
            zzhaVarZzA = null;
        }
        if (zzhaVarZzA == null) {
            zzhaVarZzA = zzA(i);
        }
        zzhl.zzGk.post(new Runnable() { // from class: com.google.android.gms.internal.zzfx.1
            @Override // java.lang.Runnable
            public void run() {
                zzfx.this.zzBq.zzb(zzhaVarZzA);
            }
        });
    }
}
