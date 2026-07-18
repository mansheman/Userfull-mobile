package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class zzab {
    private final List<Command> zzLZ;
    private final long zzMa;
    private final long zzMb;
    private final int zzMc;
    private final boolean zzMd;
    private final String zzMe;
    private final Map<String, String> zzyn;

    public zzab(zzc zzcVar, Map<String, String> map, long j, boolean z) {
        this(zzcVar, map, j, z, 0L, 0, null);
    }

    public zzab(zzc zzcVar, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(zzcVar, map, j, z, j2, i, null);
    }

    public zzab(zzc zzcVar, Map<String, String> map, long j, boolean z, long j2, int i, List<Command> list) {
        String strZza;
        String strZza2;
        com.google.android.gms.common.internal.zzu.zzu(zzcVar);
        com.google.android.gms.common.internal.zzu.zzu(map);
        this.zzMb = j;
        this.zzMd = z;
        this.zzMa = j2;
        this.zzMc = i;
        this.zzLZ = list != null ? list : Collections.EMPTY_LIST;
        this.zzMe = zze(list);
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (zzj(entry.getKey()) && (strZza2 = zza(zzcVar, entry.getKey())) != null) {
                map2.put(strZza2, zzb(zzcVar, entry.getValue()));
            }
        }
        for (Map.Entry<String, String> entry2 : map.entrySet()) {
            if (!zzj(entry2.getKey()) && (strZza = zza(zzcVar, entry2.getKey())) != null) {
                map2.put(strZza, zzb(zzcVar, entry2.getValue()));
            }
        }
        if (!TextUtils.isEmpty(this.zzMe)) {
            zzam.zzb(map2, "_v", this.zzMe);
            if (this.zzMe.equals("ma4.0.0") || this.zzMe.equals("ma4.0.1")) {
                map2.remove("adid");
            }
        }
        this.zzyn = Collections.unmodifiableMap(map2);
    }

    public static zzab zza(zzc zzcVar, zzab zzabVar, Map<String, String> map) {
        return new zzab(zzcVar, map, zzabVar.zzjW(), zzabVar.zzjY(), zzabVar.zzjV(), zzabVar.zzjU(), zzabVar.zzjX());
    }

    private static String zza(zzc zzcVar, Object obj) {
        if (obj == null) {
            return null;
        }
        String string = obj.toString();
        if (string.startsWith("&")) {
            string = string.substring(1);
        }
        int length = string.length();
        if (length > 256) {
            string = string.substring(0, 256);
            zzcVar.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), string);
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    private static String zzb(zzc zzcVar, Object obj) {
        String string = obj == null ? "" : obj.toString();
        int length = string.length();
        if (length <= 8192) {
            return string;
        }
        String strSubstring = string.substring(0, 8192);
        zzcVar.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), strSubstring);
        return strSubstring;
    }

    private static String zze(List<Command> list) {
        String value;
        if (list != null) {
            for (Command command : list) {
                if ("appendVersion".equals(command.getId())) {
                    value = command.getValue();
                    break;
                }
            }
            value = null;
        } else {
            value = null;
        }
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        return value;
    }

    private static boolean zzj(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.toString().startsWith("&");
    }

    private String zzn(String str, String str2) {
        com.google.android.gms.common.internal.zzu.zzcj(str);
        com.google.android.gms.common.internal.zzu.zzb(!str.startsWith("&"), "Short param name required");
        String str3 = this.zzyn.get(str);
        return str3 != null ? str3 : str2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ht=").append(this.zzMb);
        if (this.zzMa != 0) {
            stringBuffer.append(", dbId=").append(this.zzMa);
        }
        if (this.zzMc != 0) {
            stringBuffer.append(", appUID=").append(this.zzMc);
        }
        ArrayList<String> arrayList = new ArrayList(this.zzyn.keySet());
        Collections.sort(arrayList);
        for (String str : arrayList) {
            stringBuffer.append(", ");
            stringBuffer.append(str);
            stringBuffer.append("=");
            stringBuffer.append(this.zzyn.get(str));
        }
        return stringBuffer.toString();
    }

    public int zzjU() {
        return this.zzMc;
    }

    public long zzjV() {
        return this.zzMa;
    }

    public long zzjW() {
        return this.zzMb;
    }

    public List<Command> zzjX() {
        return this.zzLZ;
    }

    public boolean zzjY() {
        return this.zzMd;
    }

    public long zzjZ() {
        return zzam.zzbj(zzn("_s", "0"));
    }

    public String zzka() {
        return zzn("_m", "");
    }

    public Map<String, String> zzn() {
        return this.zzyn;
    }
}
