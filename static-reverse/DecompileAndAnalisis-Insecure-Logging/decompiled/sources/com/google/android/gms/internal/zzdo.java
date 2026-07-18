package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.media.MediaRouteProviderProtocol;
import android.text.TextUtils;
import android.view.MotionEvent;
import java.util.Map;
import java.util.WeakHashMap;
import org.json.JSONObject;

@zzgd
/* loaded from: classes.dex */
public final class zzdo implements zzdg {
    private final Map<zzid, Integer> zzwA = new WeakHashMap();

    private static int zza(Context context, Map<String, String> map, String str, int i) {
        String str2 = map.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            return com.google.android.gms.ads.internal.client.zzk.zzcA().zzb(context, Integer.parseInt(str2));
        } catch (NumberFormatException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not parse " + str + " in a video GMSG: " + str2);
            return i;
        }
    }

    @Override // com.google.android.gms.internal.zzdg
    public void zza(zzid zzidVar, Map<String, String> map) {
        com.google.android.gms.ads.internal.overlay.zzh zzhVarZzeq;
        String str = map.get("action");
        if (str == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Action missing from video GMSG.");
            return;
        }
        if (com.google.android.gms.ads.internal.util.client.zzb.zzL(3)) {
            JSONObject jSONObject = new JSONObject(map);
            jSONObject.remove("google.afma.Notify_dt");
            com.google.android.gms.ads.internal.util.client.zzb.zzay("Video GMSG: " + str + " " + jSONObject.toString());
        }
        if ("background".equals(str)) {
            String str2 = map.get("color");
            if (TextUtils.isEmpty(str2)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Color parameter missing from color video GMSG.");
                return;
            }
            try {
                int color = Color.parseColor(str2);
                com.google.android.gms.ads.internal.overlay.zzc zzcVarZzgD = zzidVar.zzgD();
                if (zzcVarZzgD == null || (zzhVarZzeq = zzcVarZzgD.zzeq()) == null) {
                    this.zzwA.put(zzidVar, Integer.valueOf(color));
                } else {
                    zzhVarZzeq.setBackgroundColor(color);
                }
                return;
            } catch (IllegalArgumentException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Invalid color parameter in video GMSG.");
                return;
            }
        }
        com.google.android.gms.ads.internal.overlay.zzc zzcVarZzgD2 = zzidVar.zzgD();
        if (zzcVarZzgD2 == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not get ad overlay for a video GMSG.");
            return;
        }
        boolean zEquals = "new".equals(str);
        boolean zEquals2 = "position".equals(str);
        if (zEquals || zEquals2) {
            Context context = zzidVar.getContext();
            int iZza = zza(context, map, "x", 0);
            int iZza2 = zza(context, map, "y", 0);
            int iZza3 = zza(context, map, "w", -1);
            int iZza4 = zza(context, map, "h", -1);
            if (!zEquals || zzcVarZzgD2.zzeq() != null) {
                zzcVarZzgD2.zzd(iZza, iZza2, iZza3, iZza4);
                return;
            }
            zzcVarZzgD2.zze(iZza, iZza2, iZza3, iZza4);
            if (this.zzwA.containsKey(zzidVar)) {
                zzcVarZzgD2.zzeq().setBackgroundColor(this.zzwA.get(zzidVar).intValue());
                return;
            }
            return;
        }
        com.google.android.gms.ads.internal.overlay.zzh zzhVarZzeq2 = zzcVarZzgD2.zzeq();
        if (zzhVarZzeq2 == null) {
            com.google.android.gms.ads.internal.overlay.zzh.zzd(zzidVar);
            return;
        }
        if ("click".equals(str)) {
            Context context2 = zzidVar.getContext();
            int iZza5 = zza(context2, map, "x", 0);
            int iZza6 = zza(context2, map, "y", 0);
            long jUptimeMillis = SystemClock.uptimeMillis();
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, iZza5, iZza6, 0);
            zzhVarZzeq2.zzc(motionEventObtain);
            motionEventObtain.recycle();
            return;
        }
        if ("currentTime".equals(str)) {
            String str3 = map.get("time");
            if (str3 == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Time parameter missing from currentTime video GMSG.");
                return;
            }
            try {
                zzhVarZzeq2.seekTo((int) (Float.parseFloat(str3) * 1000.0f));
                return;
            } catch (NumberFormatException e2) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not parse time parameter from currentTime video GMSG: " + str3);
                return;
            }
        }
        if ("hide".equals(str)) {
            zzhVarZzeq2.setVisibility(4);
            return;
        }
        if ("load".equals(str)) {
            zzhVarZzeq2.zzeH();
            return;
        }
        if ("muted".equals(str)) {
            if (Boolean.parseBoolean(map.get("muted"))) {
                zzhVarZzeq2.zzeI();
                return;
            } else {
                zzhVarZzeq2.zzeJ();
                return;
            }
        }
        if ("pause".equals(str)) {
            zzhVarZzeq2.pause();
            return;
        }
        if ("play".equals(str)) {
            zzhVarZzeq2.play();
            return;
        }
        if ("show".equals(str)) {
            zzhVarZzeq2.setVisibility(0);
            return;
        }
        if ("src".equals(str)) {
            zzhVarZzeq2.zzah(map.get("src"));
            return;
        }
        if (!MediaRouteProviderProtocol.CLIENT_DATA_VOLUME.equals(str)) {
            if ("watermark".equals(str)) {
                zzhVarZzeq2.zzeK();
                return;
            } else {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Unknown video action: " + str);
                return;
            }
        }
        String str4 = map.get(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME);
        if (str4 == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Level parameter missing from volume video GMSG.");
            return;
        }
        try {
            zzhVarZzeq2.zza(Float.parseFloat(str4));
        } catch (NumberFormatException e3) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not parse volume parameter from volume video GMSG: " + str4);
        }
    }
}
