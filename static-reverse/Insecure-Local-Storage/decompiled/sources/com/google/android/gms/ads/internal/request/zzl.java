package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.zza;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzbb;
import com.google.android.gms.internal.zzbe;
import com.google.android.gms.internal.zzbr;
import com.google.android.gms.internal.zzbz;
import com.google.android.gms.internal.zzdg;
import com.google.android.gms.internal.zzdh;
import com.google.android.gms.internal.zzdl;
import com.google.android.gms.internal.zzdt;
import com.google.android.gms.internal.zzgd;
import com.google.android.gms.internal.zzgg;
import com.google.android.gms.internal.zzha;
import com.google.android.gms.internal.zzhh;
import com.google.android.gms.internal.zzhx;
import com.google.android.gms.internal.zzid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
/* loaded from: classes.dex */
public class zzl extends zzhh {
    private final Context mContext;
    private final Object zzBr = new Object();
    private final zza.InterfaceC0014zza zzCd;
    private final AdRequestInfoParcel.zza zzCe;
    private zzdt.zzd zzDl;
    static final long zzDf = TimeUnit.SECONDS.toMillis(10);
    private static final Object zzoW = new Object();
    private static boolean zzDg = false;
    private static zzdt zzDh = null;
    private static zzdh zzDi = null;
    private static zzdl zzDj = null;
    private static zzdg zzDk = null;

    public static class zza implements zzdt.zzb<zzbb> {
        @Override // com.google.android.gms.internal.zzdt.zzb
        /* renamed from: zza, reason: merged with bridge method [inline-methods] */
        public void zzc(zzbb zzbbVar) {
            zzl.zzd(zzbbVar);
        }
    }

    public static class zzb implements zzdt.zzb<zzbb> {
        @Override // com.google.android.gms.internal.zzdt.zzb
        /* renamed from: zza, reason: merged with bridge method [inline-methods] */
        public void zzc(zzbb zzbbVar) {
            zzl.zzc(zzbbVar);
        }
    }

    public static class zzc implements zzdg {
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) {
            String str = map.get("request_id");
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Invalid request: " + map.get("errors"));
            zzl.zzDj.zzV(str);
        }
    }

    public zzl(Context context, AdRequestInfoParcel.zza zzaVar, zza.InterfaceC0014zza interfaceC0014zza) {
        this.zzCd = interfaceC0014zza;
        this.mContext = context;
        this.zzCe = zzaVar;
        synchronized (zzoW) {
            if (!zzDg) {
                zzDj = new zzdl();
                zzDi = new zzdh(context.getApplicationContext(), zzaVar.zzpJ);
                zzDk = new zzc();
                zzDh = new zzdt(this.mContext.getApplicationContext(), this.zzCe.zzpJ, zzbz.zztD.get(), new zzb(), new zza());
                zzDg = true;
            }
        }
    }

    private JSONObject zza(AdRequestInfoParcel adRequestInfoParcel, String str) {
        JSONObject jSONObjectZza;
        AdvertisingIdClient.Info advertisingIdInfo;
        Bundle bundle = adRequestInfoParcel.zzCm.extras.getBundle("sdk_less_server_data");
        String string = adRequestInfoParcel.zzCm.extras.getString("sdk_less_network_id");
        if (bundle == null || (jSONObjectZza = zzgg.zza(adRequestInfoParcel, zzo.zzbB().zzC(this.mContext), null, new zzbr(zzbz.zztD.get()), null, null, new ArrayList())) == null) {
            return null;
        }
        try {
            advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException | IllegalStateException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Cannot get advertising id info", e);
            advertisingIdInfo = null;
        }
        HashMap map = new HashMap();
        map.put("request_id", str);
        map.put("network_id", string);
        map.put("request_param", jSONObjectZza);
        map.put("data", bundle);
        if (advertisingIdInfo != null) {
            map.put("adid", advertisingIdInfo.getId());
            map.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 1 : 0));
        }
        try {
            return zzo.zzbv().zzy(map);
        } catch (JSONException e2) {
            return null;
        }
    }

    protected static void zzc(zzbb zzbbVar) {
        zzbbVar.zza("/loadAd", zzDj);
        zzbbVar.zza("/fetchHttpRequest", zzDi);
        zzbbVar.zza("/invalidRequest", zzDk);
    }

    protected static void zzd(zzbb zzbbVar) {
        zzbbVar.zzb("/loadAd", zzDj);
        zzbbVar.zzb("/fetchHttpRequest", zzDi);
        zzbbVar.zzb("/invalidRequest", zzDk);
    }

    private AdResponseParcel zzf(AdRequestInfoParcel adRequestInfoParcel) throws ExecutionException, InterruptedException, TimeoutException {
        final String string = UUID.randomUUID().toString();
        final JSONObject jSONObjectZza = zza(adRequestInfoParcel, string);
        if (jSONObjectZza == null) {
            return new AdResponseParcel(0);
        }
        long jElapsedRealtime = zzo.zzbz().elapsedRealtime();
        Future<JSONObject> futureZzU = zzDj.zzU(string);
        com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new Runnable() { // from class: com.google.android.gms.ads.internal.request.zzl.2
            @Override // java.lang.Runnable
            public void run() {
                zzl.this.zzDl = zzl.zzDh.zzdU();
                zzl.this.zzDl.zza(new zzhx.zzc<zzbe>() { // from class: com.google.android.gms.ads.internal.request.zzl.2.1
                    @Override // com.google.android.gms.internal.zzhx.zzc
                    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
                    public void zzc(zzbe zzbeVar) {
                        try {
                            zzbeVar.zza("AFMA_getAdapterLessMediationAd", jSONObjectZza);
                        } catch (Exception e) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error requesting an ad url", e);
                            zzl.zzDj.zzV(string);
                        }
                    }
                }, new zzhx.zza() { // from class: com.google.android.gms.ads.internal.request.zzl.2.2
                    @Override // com.google.android.gms.internal.zzhx.zza
                    public void run() {
                        zzl.zzDj.zzV(string);
                    }
                });
            }
        });
        try {
            JSONObject jSONObject = futureZzU.get(zzDf - (zzo.zzbz().elapsedRealtime() - jElapsedRealtime), TimeUnit.MILLISECONDS);
            if (jSONObject == null) {
                return new AdResponseParcel(-1);
            }
            AdResponseParcel adResponseParcelZza = zzgg.zza(this.mContext, adRequestInfoParcel, jSONObject.toString());
            return (adResponseParcelZza.errorCode == -3 || !TextUtils.isEmpty(adResponseParcelZza.zzCI)) ? adResponseParcelZza : new AdResponseParcel(3);
        } catch (InterruptedException e) {
            return new AdResponseParcel(-1);
        } catch (CancellationException e2) {
            return new AdResponseParcel(-1);
        } catch (ExecutionException e3) {
            return new AdResponseParcel(0);
        } catch (TimeoutException e4) {
            return new AdResponseParcel(2);
        }
    }

    @Override // com.google.android.gms.internal.zzhh
    public void onStop() {
        synchronized (this.zzBr) {
            com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new Runnable() { // from class: com.google.android.gms.ads.internal.request.zzl.3
                @Override // java.lang.Runnable
                public void run() {
                    if (zzl.this.zzDl != null) {
                        zzl.this.zzDl.release();
                        zzl.this.zzDl = null;
                    }
                }
            });
        }
    }

    @Override // com.google.android.gms.internal.zzhh
    public void zzdP() throws ExecutionException, InterruptedException, TimeoutException {
        com.google.android.gms.ads.internal.util.client.zzb.zzay("SdkLessAdLoaderBackgroundTask started.");
        AdRequestInfoParcel adRequestInfoParcel = new AdRequestInfoParcel(this.zzCe, null, null);
        AdResponseParcel adResponseParcelZzf = zzf(adRequestInfoParcel);
        final zzha.zza zzaVar = new zzha.zza(adRequestInfoParcel, adResponseParcelZzf, null, null, adResponseParcelZzf.errorCode, zzo.zzbz().elapsedRealtime(), adResponseParcelZzf.zzCO, null);
        com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new Runnable() { // from class: com.google.android.gms.ads.internal.request.zzl.1
            @Override // java.lang.Runnable
            public void run() {
                zzl.this.zzCd.zza(zzaVar);
                if (zzl.this.zzDl != null) {
                    zzl.this.zzDl.release();
                    zzl.this.zzDl = null;
                }
            }
        });
    }
}
