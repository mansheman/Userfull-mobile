package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
/* loaded from: classes.dex */
public final class zzdf {
    public static final zzdg zzvV = new zzdg() { // from class: com.google.android.gms.internal.zzdf.1
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) {
        }
    };
    public static final zzdg zzvW = new zzdg() { // from class: com.google.android.gms.internal.zzdf.2
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) {
            String str = map.get("urls");
            if (TextUtils.isEmpty(str)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("URLs missing in canOpenURLs GMSG.");
                return;
            }
            String[] strArrSplit = str.split(",");
            HashMap map2 = new HashMap();
            PackageManager packageManager = zzidVar.getContext().getPackageManager();
            for (String str2 : strArrSplit) {
                String[] strArrSplit2 = str2.split(";", 2);
                map2.put(str2, Boolean.valueOf(packageManager.resolveActivity(new Intent(strArrSplit2.length > 1 ? strArrSplit2[1].trim() : "android.intent.action.VIEW", Uri.parse(strArrSplit2[0].trim())), 65536) != null));
            }
            zzidVar.zzc("openableURLs", map2);
        }
    };
    public static final zzdg zzvX = new zzdg() { // from class: com.google.android.gms.internal.zzdf.3
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) throws JSONException {
            PackageManager packageManager = zzidVar.getContext().getPackageManager();
            try {
                try {
                    JSONArray jSONArray = new JSONObject(map.get("data")).getJSONArray("intents");
                    JSONObject jSONObject = new JSONObject();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        try {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            String strOptString = jSONObject2.optString("id");
                            String strOptString2 = jSONObject2.optString("u");
                            String strOptString3 = jSONObject2.optString("i");
                            String strOptString4 = jSONObject2.optString("m");
                            String strOptString5 = jSONObject2.optString("p");
                            String strOptString6 = jSONObject2.optString("c");
                            jSONObject2.optString("f");
                            jSONObject2.optString("e");
                            Intent intent = new Intent();
                            if (!TextUtils.isEmpty(strOptString2)) {
                                intent.setData(Uri.parse(strOptString2));
                            }
                            if (!TextUtils.isEmpty(strOptString3)) {
                                intent.setAction(strOptString3);
                            }
                            if (!TextUtils.isEmpty(strOptString4)) {
                                intent.setType(strOptString4);
                            }
                            if (!TextUtils.isEmpty(strOptString5)) {
                                intent.setPackage(strOptString5);
                            }
                            if (!TextUtils.isEmpty(strOptString6)) {
                                String[] strArrSplit = strOptString6.split("/", 2);
                                if (strArrSplit.length == 2) {
                                    intent.setComponent(new ComponentName(strArrSplit[0], strArrSplit[1]));
                                }
                            }
                            try {
                                jSONObject.put(strOptString, packageManager.resolveActivity(intent, 65536) != null);
                            } catch (JSONException e) {
                                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error constructing openable urls response.", e);
                            }
                        } catch (JSONException e2) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error parsing the intent data.", e2);
                        }
                    }
                    zzidVar.zzb("openableIntents", jSONObject);
                } catch (JSONException e3) {
                    zzidVar.zzb("openableIntents", new JSONObject());
                }
            } catch (JSONException e4) {
                zzidVar.zzb("openableIntents", new JSONObject());
            }
        }
    };
    public static final zzdg zzvY = new zzdg() { // from class: com.google.android.gms.internal.zzdf.4
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) {
            zzan zzanVarZzgH;
            String str = map.get("u");
            if (str == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("URL missing from click GMSG.");
                return;
            }
            Uri uri = Uri.parse(str);
            try {
                zzanVarZzgH = zzidVar.zzgH();
            } catch (zzao e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Unable to append parameter to URL: " + str);
            }
            Uri uriZza = (zzanVarZzgH == null || !zzanVarZzgH.zzb(uri)) ? uri : zzanVarZzgH.zza(uri, zzidVar.getContext());
            new zzhp(zzidVar.getContext(), zzidVar.zzgI().zzGG, uriZza.toString()).zzgi();
        }
    };
    public static final zzdg zzvZ = new zzdg() { // from class: com.google.android.gms.internal.zzdf.5
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) {
            com.google.android.gms.ads.internal.overlay.zzc zzcVarZzgD = zzidVar.zzgD();
            if (zzcVarZzgD != null) {
                zzcVarZzgD.close();
                return;
            }
            com.google.android.gms.ads.internal.overlay.zzc zzcVarZzgE = zzidVar.zzgE();
            if (zzcVarZzgE != null) {
                zzcVarZzgE.close();
            } else {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("A GMSG tried to close something that wasn't an overlay.");
            }
        }
    };
    public static final zzdg zzwa = new zzdg() { // from class: com.google.android.gms.internal.zzdf.6
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) {
            zzidVar.zzC("1".equals(map.get("custom_close")));
        }
    };
    public static final zzdg zzwb = new zzdg() { // from class: com.google.android.gms.internal.zzdf.7
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) {
            String str = map.get("u");
            if (str == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("URL missing from httpTrack GMSG.");
            } else {
                new zzhp(zzidVar.getContext(), zzidVar.zzgI().zzGG, str).zzgi();
            }
        }
    };
    public static final zzdg zzwc = new zzdg() { // from class: com.google.android.gms.internal.zzdf.8
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaA("Received log message: " + map.get("string"));
        }
    };
    public static final zzdg zzwd = new zzdg() { // from class: com.google.android.gms.internal.zzdf.9
        @Override // com.google.android.gms.internal.zzdg
        public void zza(zzid zzidVar, Map<String, String> map) throws NumberFormatException {
            String str = map.get("tx");
            String str2 = map.get("ty");
            String str3 = map.get("td");
            try {
                int i = Integer.parseInt(str);
                int i2 = Integer.parseInt(str2);
                int i3 = Integer.parseInt(str3);
                zzan zzanVarZzgH = zzidVar.zzgH();
                if (zzanVarZzgH != null) {
                    zzanVarZzgH.zzab().zza(i, i2, i3);
                }
            } catch (NumberFormatException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not parse touch parameters from gmsg.");
            }
        }
    };
    public static final zzdg zzwe = new zzdo();
    public static final zzdg zzwf = new zzds();
}
