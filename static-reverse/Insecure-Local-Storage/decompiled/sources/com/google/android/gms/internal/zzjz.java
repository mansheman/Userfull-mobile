package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class zzjz {
    private static final com.google.android.gms.cast.internal.zzl zzQW = new com.google.android.gms.cast.internal.zzl("MetadataUtils");
    private static final String[] zzVs = {"Z", "+hh", "+hhmm", "+hh:mm"};
    private static final String zzVt = "yyyyMMdd'T'HHmmss" + zzVs[0];

    public static String zza(Calendar calendar) {
        if (calendar == null) {
            zzQW.zzb("Calendar object cannot be null", new Object[0]);
            return null;
        }
        String str = zzVt;
        if (calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0) {
            str = "yyyyMMdd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        String str2 = simpleDateFormat.format(calendar.getTime());
        return str2.endsWith("+0000") ? str2.replace("+0000", zzVs[0]) : str2;
    }

    public static void zza(List<WebImage> list, JSONObject jSONObject) throws JSONException {
        try {
            list.clear();
            JSONArray jSONArray = jSONObject.getJSONArray("images");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    list.add(new WebImage(jSONArray.getJSONObject(i)));
                } catch (IllegalArgumentException e) {
                }
            }
        } catch (JSONException e2) {
        }
    }

    public static void zza(JSONObject jSONObject, List<WebImage> list) throws JSONException {
        if (list == null || list.isEmpty()) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<WebImage> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toJson());
        }
        try {
            jSONObject.put("images", jSONArray);
        } catch (JSONException e) {
        }
    }

    public static Calendar zzbK(String str) {
        if (TextUtils.isEmpty(str)) {
            zzQW.zzb("Input string is empty or null", new Object[0]);
            return null;
        }
        String strZzbL = zzbL(str);
        if (TextUtils.isEmpty(strZzbL)) {
            zzQW.zzb("Invalid date format", new Object[0]);
            return null;
        }
        String strZzbM = zzbM(str);
        String str2 = "yyyyMMdd";
        if (!TextUtils.isEmpty(strZzbM)) {
            strZzbL = strZzbL + "T" + strZzbM;
            str2 = strZzbM.length() == "HHmmss".length() ? "yyyyMMdd'T'HHmmss" : zzVt;
        }
        Calendar gregorianCalendar = GregorianCalendar.getInstance();
        try {
            gregorianCalendar.setTime(new SimpleDateFormat(str2).parse(strZzbL));
            return gregorianCalendar;
        } catch (ParseException e) {
            zzQW.zzb("Error parsing string: %s", e.getMessage());
            return null;
        }
    }

    private static String zzbL(String str) {
        if (TextUtils.isEmpty(str)) {
            zzQW.zzb("Input string is empty or null", new Object[0]);
            return null;
        }
        try {
            return str.substring(0, "yyyyMMdd".length());
        } catch (IndexOutOfBoundsException e) {
            zzQW.zze("Error extracting the date: %s", e.getMessage());
            return null;
        }
    }

    private static String zzbM(String str) {
        if (TextUtils.isEmpty(str)) {
            zzQW.zzb("string is empty or null", new Object[0]);
            return null;
        }
        int iIndexOf = str.indexOf(84);
        int i = iIndexOf + 1;
        if (iIndexOf != "yyyyMMdd".length()) {
            zzQW.zzb("T delimeter is not found", new Object[0]);
            return null;
        }
        try {
            String strSubstring = str.substring(i);
            if (strSubstring.length() == "HHmmss".length()) {
                return strSubstring;
            }
            switch (strSubstring.charAt("HHmmss".length())) {
                case '+':
                case '-':
                    if (zzbN(strSubstring)) {
                        break;
                    }
                    break;
                case 'Z':
                    if (strSubstring.length() == "HHmmss".length() + zzVs[0].length()) {
                        break;
                    }
                    break;
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            zzQW.zzb("Error extracting the time substring: %s", e.getMessage());
            return null;
        }
    }

    private static boolean zzbN(String str) {
        int length = str.length();
        int length2 = "HHmmss".length();
        return length == zzVs[1].length() + length2 || length == zzVs[2].length() + length2 || length == length2 + zzVs[3].length();
    }
}
