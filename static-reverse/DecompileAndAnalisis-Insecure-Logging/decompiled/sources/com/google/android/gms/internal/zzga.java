package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
/* loaded from: classes.dex */
public class zzga implements zzfy.zza<com.google.android.gms.ads.internal.formats.zze> {
    private final boolean zzBY;
    private final boolean zzBZ;

    public zzga(boolean z, boolean z2) {
        this.zzBY = z;
        this.zzBZ = z2;
    }

    @Override // com.google.android.gms.internal.zzfy.zza
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public com.google.android.gms.ads.internal.formats.zze zza(zzfy zzfyVar, JSONObject jSONObject) throws ExecutionException, JSONException, InterruptedException {
        List<zzhv<com.google.android.gms.ads.internal.formats.zzc>> listZza = zzfyVar.zza(jSONObject, "images", true, this.zzBY, this.zzBZ);
        zzhv<com.google.android.gms.ads.internal.formats.zzc> zzhvVarZza = zzfyVar.zza(jSONObject, "secondary_image", false, this.zzBY);
        zzhv<com.google.android.gms.ads.internal.formats.zza> zzhvVarZze = zzfyVar.zze(jSONObject);
        ArrayList arrayList = new ArrayList();
        Iterator<zzhv<com.google.android.gms.ads.internal.formats.zzc>> it = listZza.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().get());
        }
        return new com.google.android.gms.ads.internal.formats.zze(jSONObject.getString("headline"), arrayList, jSONObject.getString("body"), zzhvVarZza.get(), jSONObject.getString("call_to_action"), jSONObject.getString("advertiser"), zzhvVarZze.get());
    }
}
