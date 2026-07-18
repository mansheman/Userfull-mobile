package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzim;
import com.google.android.gms.internal.zzin;
import com.google.android.gms.internal.zzio;
import com.google.android.gms.internal.zzip;
import com.google.android.gms.internal.zzno;
import com.google.android.gms.internal.zznu;
import com.google.android.gms.internal.zznx;
import com.google.android.gms.internal.zzny;
import com.google.android.gms.internal.zznz;
import com.google.android.gms.internal.zzoa;
import com.google.android.gms.internal.zzob;
import com.google.android.gms.internal.zzoc;
import com.google.android.gms.internal.zzod;
import com.google.android.gms.internal.zzoe;
import com.google.android.gms.internal.zzof;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class zzb extends com.google.android.gms.analytics.internal.zzc implements zznu {
    private static DecimalFormat zzIk;
    private final zzf zzIa;
    private final Uri zzIl;
    private final boolean zzIm;
    private final boolean zzIn;
    private final String zztd;

    public zzb(zzf zzfVar, String str) {
        this(zzfVar, str, true, false);
    }

    public zzb(zzf zzfVar, String str, boolean z, boolean z2) {
        super(zzfVar);
        zzu.zzcj(str);
        this.zzIa = zzfVar;
        this.zztd = str;
        this.zzIm = z;
        this.zzIn = z2;
        this.zzIl = zzaK(this.zztd);
    }

    static String zza(double d) {
        if (zzIk == null) {
            zzIk = new DecimalFormat("0.######");
        }
        return zzIk.format(d);
    }

    private static void zza(Map<String, String> map, String str, double d) {
        if (d != 0.0d) {
            map.put(str, zza(d));
        }
    }

    private static void zza(Map<String, String> map, String str, int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        map.put(str, i + "x" + i2);
    }

    private static void zza(Map<String, String> map, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        map.put(str, str2);
    }

    private static void zza(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, "1");
        }
    }

    static Uri zzaK(String str) {
        zzu.zzcj(str);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    public static Map<String, String> zzc(zzno zznoVar) {
        HashMap map = new HashMap();
        zzio zzioVar = (zzio) zznoVar.zzd(zzio.class);
        if (zzioVar != null) {
            for (Map.Entry<String, Object> entry : zzioVar.zzhv().entrySet()) {
                String strZzh = zzh(entry.getValue());
                if (strZzh != null) {
                    map.put(entry.getKey(), strZzh);
                }
            }
        }
        zzip zzipVar = (zzip) zznoVar.zzd(zzip.class);
        if (zzipVar != null) {
            zza(map, "t", zzipVar.zzhw());
            zza(map, "cid", zzipVar.getClientId());
            zza(map, "uid", zzipVar.getUserId());
            zza(map, "sc", zzipVar.zzhz());
            zza(map, "sf", zzipVar.zzhB());
            zza(map, "ni", zzipVar.zzhA());
            zza(map, "adid", zzipVar.zzhx());
            zza(map, "ate", zzipVar.zzhy());
        }
        zzod zzodVar = (zzod) zznoVar.zzd(zzod.class);
        if (zzodVar != null) {
            zza(map, "cd", zzodVar.zzwB());
            zza(map, "a", zzodVar.zzbn());
            zza(map, "dr", zzodVar.zzwE());
        }
        zzob zzobVar = (zzob) zznoVar.zzd(zzob.class);
        if (zzobVar != null) {
            zza(map, "ec", zzobVar.zzwy());
            zza(map, "ea", zzobVar.getAction());
            zza(map, "el", zzobVar.getLabel());
            zza(map, "ev", zzobVar.getValue());
        }
        zzny zznyVar = (zzny) zznoVar.zzd(zzny.class);
        if (zznyVar != null) {
            zza(map, "cn", zznyVar.getName());
            zza(map, "cs", zznyVar.getSource());
            zza(map, "cm", zznyVar.zzwj());
            zza(map, "ck", zznyVar.zzwk());
            zza(map, "cc", zznyVar.getContent());
            zza(map, "ci", zznyVar.getId());
            zza(map, "anid", zznyVar.zzwl());
            zza(map, "gclid", zznyVar.zzwm());
            zza(map, "dclid", zznyVar.zzwn());
            zza(map, "aclid", zznyVar.zzwo());
        }
        zzoc zzocVar = (zzoc) zznoVar.zzd(zzoc.class);
        if (zzocVar != null) {
            zza(map, "exd", zzocVar.getDescription());
            zza(map, "exf", zzocVar.zzwz());
        }
        zzoe zzoeVar = (zzoe) zznoVar.zzd(zzoe.class);
        if (zzoeVar != null) {
            zza(map, "sn", zzoeVar.zzwI());
            zza(map, "sa", zzoeVar.getAction());
            zza(map, "st", zzoeVar.getTarget());
        }
        zzof zzofVar = (zzof) zznoVar.zzd(zzof.class);
        if (zzofVar != null) {
            zza(map, "utv", zzofVar.zzwJ());
            zza(map, "utt", zzofVar.getTimeInMillis());
            zza(map, "utc", zzofVar.zzwy());
            zza(map, "utl", zzofVar.getLabel());
        }
        zzim zzimVar = (zzim) zznoVar.zzd(zzim.class);
        if (zzimVar != null) {
            for (Map.Entry<Integer, String> entry2 : zzimVar.zzht().entrySet()) {
                String strZzO = zzc.zzO(entry2.getKey().intValue());
                if (!TextUtils.isEmpty(strZzO)) {
                    map.put(strZzO, entry2.getValue());
                }
            }
        }
        zzin zzinVar = (zzin) zznoVar.zzd(zzin.class);
        if (zzinVar != null) {
            for (Map.Entry<Integer, Double> entry3 : zzinVar.zzhu().entrySet()) {
                String strZzQ = zzc.zzQ(entry3.getKey().intValue());
                if (!TextUtils.isEmpty(strZzQ)) {
                    map.put(strZzQ, zza(entry3.getValue().doubleValue()));
                }
            }
        }
        zzoa zzoaVar = (zzoa) zznoVar.zzd(zzoa.class);
        if (zzoaVar != null) {
            ProductAction productActionZzwu = zzoaVar.zzwu();
            if (productActionZzwu != null) {
                for (Map.Entry<String, String> entry4 : productActionZzwu.build().entrySet()) {
                    if (entry4.getKey().startsWith("&")) {
                        map.put(entry4.getKey().substring(1), entry4.getValue());
                    } else {
                        map.put(entry4.getKey(), entry4.getValue());
                    }
                }
            }
            Iterator<Promotion> it = zzoaVar.zzwx().iterator();
            int i = 1;
            while (it.hasNext()) {
                map.putAll(it.next().zzaQ(zzc.zzU(i)));
                i++;
            }
            Iterator<Product> it2 = zzoaVar.zzwv().iterator();
            int i2 = 1;
            while (it2.hasNext()) {
                map.putAll(it2.next().zzaQ(zzc.zzS(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry<String, List<Product>> entry5 : zzoaVar.zzww().entrySet()) {
                List<Product> value = entry5.getValue();
                String strZzX = zzc.zzX(i3);
                Iterator<Product> it3 = value.iterator();
                int i4 = 1;
                while (it3.hasNext()) {
                    map.putAll(it3.next().zzaQ(strZzX + zzc.zzV(i4)));
                    i4++;
                }
                if (!TextUtils.isEmpty(entry5.getKey())) {
                    map.put(strZzX + "nm", entry5.getKey());
                }
                i3++;
            }
        }
        zznz zznzVar = (zznz) zznoVar.zzd(zznz.class);
        if (zznzVar != null) {
            zza(map, "ul", zznzVar.getLanguage());
            zza(map, "sd", zznzVar.zzwp());
            zza(map, "sr", zznzVar.zzwq(), zznzVar.zzwr());
            zza(map, "vp", zznzVar.zzws(), zznzVar.zzwt());
        }
        zznx zznxVar = (zznx) zznoVar.zzd(zznx.class);
        if (zznxVar != null) {
            zza(map, "an", zznxVar.zzjL());
            zza(map, "aid", zznxVar.zzsK());
            zza(map, "aiid", zznxVar.zzwi());
            zza(map, "av", zznxVar.zzjN());
        }
        return map;
    }

    private static String zzh(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return str;
        }
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.doubleValue() != 0.0d) {
                return zza(d.doubleValue());
            }
            return null;
        }
        if (!(obj instanceof Boolean)) {
            return String.valueOf(obj);
        }
        if (obj != Boolean.FALSE) {
            return "1";
        }
        return null;
    }

    private static String zzz(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.zznu
    public void zzb(zzno zznoVar) {
        zzu.zzu(zznoVar);
        zzu.zzb(zznoVar.zzvU(), "Can't deliver not submitted measurement");
        zzu.zzbZ("deliver should be called on worker thread");
        zzno zznoVarZzvP = zznoVar.zzvP();
        zzip zzipVar = (zzip) zznoVarZzvP.zze(zzip.class);
        if (TextUtils.isEmpty(zzipVar.zzhw())) {
            zzhQ().zzg(zzc(zznoVarZzvP), "Ignoring measurement without type");
            return;
        }
        if (TextUtils.isEmpty(zzipVar.getClientId())) {
            zzhQ().zzg(zzc(zznoVarZzvP), "Ignoring measurement without client id");
            return;
        }
        if (this.zzIa.zzie().getAppOptOut()) {
            return;
        }
        double dZzhB = zzipVar.zzhB();
        if (zzam.zza(dZzhB, zzipVar.getClientId())) {
            zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(dZzhB));
            return;
        }
        Map<String, String> mapZzc = zzc(zznoVarZzvP);
        mapZzc.put("v", "1");
        mapZzc.put("_v", zze.zzJB);
        mapZzc.put("tid", this.zztd);
        if (this.zzIa.zzie().isDryRunEnabled()) {
            zzc("Dry run is enabled. GoogleAnalytics would have sent", zzz(mapZzc));
            return;
        }
        HashMap map = new HashMap();
        zzam.zzb(map, "uid", zzipVar.getUserId());
        zznx zznxVar = (zznx) zznoVar.zzd(zznx.class);
        if (zznxVar != null) {
            zzam.zzb(map, "an", zznxVar.zzjL());
            zzam.zzb(map, "aid", zznxVar.zzsK());
            zzam.zzb(map, "av", zznxVar.zzjN());
            zzam.zzb(map, "aiid", zznxVar.zzwi());
        }
        mapZzc.put("_s", String.valueOf(zzhl().zza(new zzh(0L, zzipVar.getClientId(), this.zztd, !TextUtils.isEmpty(zzipVar.zzhx()), 0L, map))));
        zzhl().zza(new zzab(zzhQ(), mapZzc, zznoVar.zzvS(), true));
    }

    @Override // com.google.android.gms.internal.zznu
    public Uri zzhe() {
        return this.zzIl;
    }
}
