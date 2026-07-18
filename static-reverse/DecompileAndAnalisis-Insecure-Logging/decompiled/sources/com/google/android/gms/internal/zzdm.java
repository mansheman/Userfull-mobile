package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

@zzgd
/* loaded from: classes.dex */
public class zzdm implements zzdg {
    static final Map<String, Integer> zzwy = new HashMap();
    private final com.google.android.gms.ads.internal.zzd zzww;
    private final zzep zzwx;

    static {
        zzwy.put("resize", 1);
        zzwy.put("playVideo", 2);
        zzwy.put("storePicture", 3);
        zzwy.put("createCalendarEvent", 4);
        zzwy.put("setOrientationProperties", 5);
        zzwy.put("closeResizedAd", 6);
    }

    public zzdm(com.google.android.gms.ads.internal.zzd zzdVar, zzep zzepVar) {
        this.zzww = zzdVar;
        this.zzwx = zzepVar;
    }

    @Override // com.google.android.gms.internal.zzdg
    public void zza(zzid zzidVar, Map<String, String> map) {
        int iIntValue = zzwy.get(map.get("a")).intValue();
        if (iIntValue != 5 && this.zzww != null && !this.zzww.zzbd()) {
            this.zzww.zzo(null);
        }
        switch (iIntValue) {
            case 1:
                this.zzwx.zzh(map);
                break;
            case 2:
            default:
                com.google.android.gms.ads.internal.util.client.zzb.zzaA("Unknown MRAID command called.");
                break;
            case 3:
                new zzer(zzidVar, map).execute();
                break;
            case 4:
                new zzeo(zzidVar, map).execute();
                break;
            case 5:
                new zzeq(zzidVar, map).execute();
                break;
            case 6:
                this.zzwx.zzn(true);
                break;
        }
    }
}
