package com.google.android.gms.internal;

import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.SearchAdRequestParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.plus.PlusShare;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
/* loaded from: classes.dex */
public final class zzgg {
    private static final SimpleDateFormat zzDJ = new SimpleDateFormat("yyyyMMdd");

    private static String zzG(int i) {
        return String.format(Locale.US, "#%06x", Integer.valueOf(16777215 & i));
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x01c3 A[PHI: r16
  0x01c3: PHI (r16v3 int) = (r16v2 int), (r16v5 int) binds: [B:61:0x014b, B:66:0x015c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.google.android.gms.ads.internal.request.AdResponseParcel zza(android.content.Context r32, com.google.android.gms.ads.internal.request.AdRequestInfoParcel r33, java.lang.String r34) {
        /*
            Method dump skipped, instructions count: 465
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgg.zza(android.content.Context, com.google.android.gms.ads.internal.request.AdRequestInfoParcel, java.lang.String):com.google.android.gms.ads.internal.request.AdResponseParcel");
    }

    public static JSONObject zza(AdRequestInfoParcel adRequestInfoParcel, zzgk zzgkVar, Location location, zzbr zzbrVar, String str, String str2, List<String> list) {
        try {
            HashMap map = new HashMap();
            if (list.size() > 0) {
                map.put("eid", TextUtils.join(",", list));
            }
            if (adRequestInfoParcel.zzCl != null) {
                map.put("ad_pos", adRequestInfoParcel.zzCl);
            }
            zza((HashMap<String, Object>) map, adRequestInfoParcel.zzCm);
            map.put("format", adRequestInfoParcel.zzpN.zzsm);
            if (adRequestInfoParcel.zzpN.width == -1) {
                map.put("smart_w", "full");
            }
            if (adRequestInfoParcel.zzpN.height == -2) {
                map.put("smart_h", "auto");
            }
            if (adRequestInfoParcel.zzpN.zzso != null) {
                StringBuilder sb = new StringBuilder();
                for (AdSizeParcel adSizeParcel : adRequestInfoParcel.zzpN.zzso) {
                    if (sb.length() != 0) {
                        sb.append("|");
                    }
                    sb.append(adSizeParcel.width == -1 ? (int) (adSizeParcel.widthPixels / zzgkVar.zzCy) : adSizeParcel.width);
                    sb.append("x");
                    sb.append(adSizeParcel.height == -2 ? (int) (adSizeParcel.heightPixels / zzgkVar.zzCy) : adSizeParcel.height);
                }
                map.put("sz", sb);
            }
            if (adRequestInfoParcel.zzCs != 0) {
                map.put("native_version", Integer.valueOf(adRequestInfoParcel.zzCs));
                map.put("native_templates", adRequestInfoParcel.zzqd);
                map.put("native_image_orientation", zzc(adRequestInfoParcel.zzqb));
                if (!adRequestInfoParcel.zzCG.isEmpty()) {
                    map.put("native_custom_templates", adRequestInfoParcel.zzCG);
                }
            }
            map.put("slotname", adRequestInfoParcel.zzpG);
            map.put("pn", adRequestInfoParcel.applicationInfo.packageName);
            if (adRequestInfoParcel.zzCn != null) {
                map.put("vc", Integer.valueOf(adRequestInfoParcel.zzCn.versionCode));
            }
            map.put("ms", str2);
            map.put("seq_num", adRequestInfoParcel.zzCp);
            map.put("session_id", adRequestInfoParcel.zzCq);
            map.put("js", adRequestInfoParcel.zzpJ.zzGG);
            zza((HashMap<String, Object>) map, zzgkVar);
            map.put("fdz", new Integer(zzbrVar.zzcX()));
            map.put("platform", Build.MANUFACTURER);
            map.put("submodel", Build.MODEL);
            if (adRequestInfoParcel.zzCm.versionCode >= 2 && adRequestInfoParcel.zzCm.zzsf != null) {
                zza((HashMap<String, Object>) map, adRequestInfoParcel.zzCm.zzsf);
            }
            if (adRequestInfoParcel.versionCode >= 2) {
                map.put("quality_signals", adRequestInfoParcel.zzCr);
            }
            if (adRequestInfoParcel.versionCode >= 4 && adRequestInfoParcel.zzCu) {
                map.put("forceHttps", Boolean.valueOf(adRequestInfoParcel.zzCu));
            }
            if (adRequestInfoParcel.versionCode >= 4 && adRequestInfoParcel.zzCt != null) {
                map.put("content_info", adRequestInfoParcel.zzCt);
            }
            if (adRequestInfoParcel.versionCode >= 5) {
                map.put("u_sd", Float.valueOf(adRequestInfoParcel.zzCy));
                map.put("sh", Integer.valueOf(adRequestInfoParcel.zzCx));
                map.put("sw", Integer.valueOf(adRequestInfoParcel.zzCw));
            } else {
                map.put("u_sd", Float.valueOf(zzgkVar.zzCy));
                map.put("sh", Integer.valueOf(zzgkVar.zzCx));
                map.put("sw", Integer.valueOf(zzgkVar.zzCw));
            }
            if (adRequestInfoParcel.versionCode >= 6) {
                if (!TextUtils.isEmpty(adRequestInfoParcel.zzCz)) {
                    try {
                        map.put("view_hierarchy", new JSONObject(adRequestInfoParcel.zzCz));
                    } catch (JSONException e) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzd("Problem serializing view hierarchy to JSON", e);
                    }
                }
                if (zzbz.zzun.get().booleanValue() && adRequestInfoParcel.zzCA) {
                    map.put("ga_hid", Integer.valueOf(adRequestInfoParcel.zzCB));
                    map.put("ga_cid", adRequestInfoParcel.zzCC);
                }
                map.put("correlation_id", Long.valueOf(adRequestInfoParcel.zzCD));
            }
            if (adRequestInfoParcel.versionCode >= 7) {
                map.put("request_id", adRequestInfoParcel.zzCE);
            }
            zza((HashMap<String, Object>) map, str);
            if (com.google.android.gms.ads.internal.util.client.zzb.zzL(2)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaB("Ad Request JSON: " + com.google.android.gms.ads.internal.zzo.zzbv().zzy(map).toString(2));
            }
            return com.google.android.gms.ads.internal.zzo.zzbv().zzy(map);
        } catch (JSONException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Problem serializing ad request to JSON: " + e2.getMessage());
            return null;
        }
    }

    private static void zza(HashMap<String, Object> map, Location location) {
        HashMap map2 = new HashMap();
        Float fValueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
        Long lValueOf = Long.valueOf(location.getTime() * 1000);
        Long lValueOf2 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
        Long lValueOf3 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
        map2.put("radius", fValueOf);
        map2.put("lat", lValueOf2);
        map2.put("long", lValueOf3);
        map2.put("time", lValueOf);
        map.put("uule", map2);
    }

    private static void zza(HashMap<String, Object> map, AdRequestParcel adRequestParcel) {
        String strZzgh = zzhg.zzgh();
        if (strZzgh != null) {
            map.put("abf", strZzgh);
        }
        if (adRequestParcel.zzrX != -1) {
            map.put("cust_age", zzDJ.format(new Date(adRequestParcel.zzrX)));
        }
        if (adRequestParcel.extras != null) {
            map.put("extras", adRequestParcel.extras);
        }
        if (adRequestParcel.zzrY != -1) {
            map.put("cust_gender", Integer.valueOf(adRequestParcel.zzrY));
        }
        if (adRequestParcel.zzrZ != null) {
            map.put("kw", adRequestParcel.zzrZ);
        }
        if (adRequestParcel.zzsb != -1) {
            map.put("tag_for_child_directed_treatment", Integer.valueOf(adRequestParcel.zzsb));
        }
        if (adRequestParcel.zzsa) {
            map.put("adtest", "on");
        }
        if (adRequestParcel.versionCode >= 2) {
            if (adRequestParcel.zzsc) {
                map.put("d_imp_hdr", 1);
            }
            if (!TextUtils.isEmpty(adRequestParcel.zzsd)) {
                map.put("ppid", adRequestParcel.zzsd);
            }
            if (adRequestParcel.zzse != null) {
                zza(map, adRequestParcel.zzse);
            }
        }
        if (adRequestParcel.versionCode >= 3 && adRequestParcel.zzsg != null) {
            map.put(PlusShare.KEY_CALL_TO_ACTION_URL, adRequestParcel.zzsg);
        }
        if (adRequestParcel.versionCode >= 5) {
            if (adRequestParcel.zzsi != null) {
                map.put("custom_targeting", adRequestParcel.zzsi);
            }
            if (adRequestParcel.zzsj != null) {
                map.put("category_exclusions", adRequestParcel.zzsj);
            }
            if (adRequestParcel.zzsk != null) {
                map.put("request_agent", adRequestParcel.zzsk);
            }
        }
    }

    private static void zza(HashMap<String, Object> map, SearchAdRequestParcel searchAdRequestParcel) {
        String str;
        String str2 = null;
        if (Color.alpha(searchAdRequestParcel.zzth) != 0) {
            map.put("acolor", zzG(searchAdRequestParcel.zzth));
        }
        if (Color.alpha(searchAdRequestParcel.backgroundColor) != 0) {
            map.put("bgcolor", zzG(searchAdRequestParcel.backgroundColor));
        }
        if (Color.alpha(searchAdRequestParcel.zzti) != 0 && Color.alpha(searchAdRequestParcel.zztj) != 0) {
            map.put("gradientto", zzG(searchAdRequestParcel.zzti));
            map.put("gradientfrom", zzG(searchAdRequestParcel.zztj));
        }
        if (Color.alpha(searchAdRequestParcel.zztk) != 0) {
            map.put("bcolor", zzG(searchAdRequestParcel.zztk));
        }
        map.put("bthick", Integer.toString(searchAdRequestParcel.zztl));
        switch (searchAdRequestParcel.zztm) {
            case 0:
                str = "none";
                break;
            case 1:
                str = "dashed";
                break;
            case 2:
                str = "dotted";
                break;
            case 3:
                str = "solid";
                break;
            default:
                str = null;
                break;
        }
        if (str != null) {
            map.put("btype", str);
        }
        switch (searchAdRequestParcel.zztn) {
            case 0:
                str2 = "light";
                break;
            case 1:
                str2 = "medium";
                break;
            case 2:
                str2 = "dark";
                break;
        }
        if (str2 != null) {
            map.put("callbuttoncolor", str2);
        }
        if (searchAdRequestParcel.zzto != null) {
            map.put("channel", searchAdRequestParcel.zzto);
        }
        if (Color.alpha(searchAdRequestParcel.zztp) != 0) {
            map.put("dcolor", zzG(searchAdRequestParcel.zztp));
        }
        if (searchAdRequestParcel.zztq != null) {
            map.put("font", searchAdRequestParcel.zztq);
        }
        if (Color.alpha(searchAdRequestParcel.zztr) != 0) {
            map.put("hcolor", zzG(searchAdRequestParcel.zztr));
        }
        map.put("headersize", Integer.toString(searchAdRequestParcel.zzts));
        if (searchAdRequestParcel.zztt != null) {
            map.put("q", searchAdRequestParcel.zztt);
        }
    }

    private static void zza(HashMap<String, Object> map, zzgk zzgkVar) {
        map.put("am", Integer.valueOf(zzgkVar.zzEn));
        map.put("cog", zzx(zzgkVar.zzEo));
        map.put("coh", zzx(zzgkVar.zzEp));
        if (!TextUtils.isEmpty(zzgkVar.zzEq)) {
            map.put("carrier", zzgkVar.zzEq);
        }
        map.put("gl", zzgkVar.zzEr);
        if (zzgkVar.zzEs) {
            map.put("simulator", 1);
        }
        map.put("ma", zzx(zzgkVar.zzEt));
        map.put("sp", zzx(zzgkVar.zzEu));
        map.put("hl", zzgkVar.zzEv);
        if (!TextUtils.isEmpty(zzgkVar.zzEw)) {
            map.put("mv", zzgkVar.zzEw);
        }
        map.put("muv", Integer.valueOf(zzgkVar.zzEx));
        if (zzgkVar.zzEy != -2) {
            map.put("cnt", Integer.valueOf(zzgkVar.zzEy));
        }
        map.put("gnt", Integer.valueOf(zzgkVar.zzEz));
        map.put("pt", Integer.valueOf(zzgkVar.zzEA));
        map.put("rm", Integer.valueOf(zzgkVar.zzEB));
        map.put("riv", Integer.valueOf(zzgkVar.zzEC));
        Bundle bundle = new Bundle();
        bundle.putInt("active_network_state", zzgkVar.zzEG);
        bundle.putBoolean("active_network_metered", zzgkVar.zzEF);
        map.put("connectivity", bundle);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("is_charging", zzgkVar.zzEE);
        bundle2.putDouble("battery_level", zzgkVar.zzED);
        map.put("battery", bundle2);
    }

    private static void zza(HashMap<String, Object> map, String str) {
        if (str != null) {
            HashMap map2 = new HashMap();
            map2.put("token", str);
            map.put("pan", map2);
        }
    }

    private static String zzc(NativeAdOptionsParcel nativeAdOptionsParcel) {
        switch (nativeAdOptionsParcel != null ? nativeAdOptionsParcel.zzvD : 0) {
            case 1:
                return "portrait";
            case 2:
                return "landscape";
            default:
                return "any";
        }
    }

    private static Integer zzx(boolean z) {
        return Integer.valueOf(z ? 1 : 0);
    }
}
