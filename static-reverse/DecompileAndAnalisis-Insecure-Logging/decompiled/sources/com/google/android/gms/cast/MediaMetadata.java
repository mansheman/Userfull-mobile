package com.google.android.gms.cast;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.internal.zzjz;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class MediaMetadata {
    public static final int MEDIA_TYPE_GENERIC = 0;
    public static final int MEDIA_TYPE_MOVIE = 1;
    public static final int MEDIA_TYPE_MUSIC_TRACK = 3;
    public static final int MEDIA_TYPE_PHOTO = 4;
    public static final int MEDIA_TYPE_TV_SHOW = 2;
    public static final int MEDIA_TYPE_USER = 100;
    private final Bundle zzRN;
    private int zzRO;
    private final List<WebImage> zzvi;
    private static final String[] zzRL = {null, "String", "int", "double", "ISO-8601 date String"};
    public static final String KEY_CREATION_DATE = "com.google.android.gms.cast.metadata.CREATION_DATE";
    public static final String KEY_RELEASE_DATE = "com.google.android.gms.cast.metadata.RELEASE_DATE";
    public static final String KEY_BROADCAST_DATE = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
    public static final String KEY_TITLE = "com.google.android.gms.cast.metadata.TITLE";
    public static final String KEY_SUBTITLE = "com.google.android.gms.cast.metadata.SUBTITLE";
    public static final String KEY_ARTIST = "com.google.android.gms.cast.metadata.ARTIST";
    public static final String KEY_ALBUM_ARTIST = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
    public static final String KEY_ALBUM_TITLE = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
    public static final String KEY_COMPOSER = "com.google.android.gms.cast.metadata.COMPOSER";
    public static final String KEY_DISC_NUMBER = "com.google.android.gms.cast.metadata.DISC_NUMBER";
    public static final String KEY_TRACK_NUMBER = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
    public static final String KEY_SEASON_NUMBER = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
    public static final String KEY_EPISODE_NUMBER = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
    public static final String KEY_SERIES_TITLE = "com.google.android.gms.cast.metadata.SERIES_TITLE";
    public static final String KEY_STUDIO = "com.google.android.gms.cast.metadata.STUDIO";
    public static final String KEY_WIDTH = "com.google.android.gms.cast.metadata.WIDTH";
    public static final String KEY_HEIGHT = "com.google.android.gms.cast.metadata.HEIGHT";
    public static final String KEY_LOCATION_NAME = "com.google.android.gms.cast.metadata.LOCATION_NAME";
    public static final String KEY_LOCATION_LATITUDE = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
    public static final String KEY_LOCATION_LONGITUDE = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
    private static final zza zzRM = new zza().zzb(KEY_CREATION_DATE, "creationDateTime", 4).zzb(KEY_RELEASE_DATE, "releaseDate", 4).zzb(KEY_BROADCAST_DATE, "originalAirdate", 4).zzb(KEY_TITLE, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, 1).zzb(KEY_SUBTITLE, "subtitle", 1).zzb(KEY_ARTIST, "artist", 1).zzb(KEY_ALBUM_ARTIST, "albumArtist", 1).zzb(KEY_ALBUM_TITLE, "albumName", 1).zzb(KEY_COMPOSER, "composer", 1).zzb(KEY_DISC_NUMBER, "discNumber", 2).zzb(KEY_TRACK_NUMBER, "trackNumber", 2).zzb(KEY_SEASON_NUMBER, "season", 2).zzb(KEY_EPISODE_NUMBER, "episode", 2).zzb(KEY_SERIES_TITLE, "seriesTitle", 1).zzb(KEY_STUDIO, "studio", 1).zzb(KEY_WIDTH, "width", 2).zzb(KEY_HEIGHT, "height", 2).zzb(KEY_LOCATION_NAME, "location", 1).zzb(KEY_LOCATION_LATITUDE, "latitude", 3).zzb(KEY_LOCATION_LONGITUDE, "longitude", 3);

    private static class zza {
        private final Map<String, String> zzRP = new HashMap();
        private final Map<String, String> zzRQ = new HashMap();
        private final Map<String, Integer> zzRR = new HashMap();

        public zza zzb(String str, String str2, int i) {
            this.zzRP.put(str, str2);
            this.zzRQ.put(str2, str);
            this.zzRR.put(str, Integer.valueOf(i));
            return this;
        }

        public String zzbw(String str) {
            return this.zzRP.get(str);
        }

        public String zzbx(String str) {
            return this.zzRQ.get(str);
        }

        public int zzby(String str) {
            Integer num = this.zzRR.get(str);
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }
    }

    public MediaMetadata() {
        this(0);
    }

    public MediaMetadata(int mediaType) {
        this.zzvi = new ArrayList();
        this.zzRN = new Bundle();
        this.zzRO = mediaType;
    }

    private void zza(JSONObject jSONObject, String... strArr) throws JSONException {
        try {
            for (String str : strArr) {
                if (this.zzRN.containsKey(str)) {
                    switch (zzRM.zzby(str)) {
                        case 1:
                        case 4:
                            jSONObject.put(zzRM.zzbw(str), this.zzRN.getString(str));
                            break;
                        case 2:
                            jSONObject.put(zzRM.zzbw(str), this.zzRN.getInt(str));
                            break;
                        case 3:
                            jSONObject.put(zzRM.zzbw(str), this.zzRN.getDouble(str));
                            break;
                    }
                }
            }
            for (String str2 : this.zzRN.keySet()) {
                if (!str2.startsWith("com.google.")) {
                    Object obj = this.zzRN.get(str2);
                    if (obj instanceof String) {
                        jSONObject.put(str2, obj);
                    } else if (obj instanceof Integer) {
                        jSONObject.put(str2, obj);
                    } else if (obj instanceof Double) {
                        jSONObject.put(str2, obj);
                    }
                }
            }
        } catch (JSONException e) {
        }
    }

    private void zzb(JSONObject jSONObject, String... strArr) throws JSONException {
        HashSet hashSet = new HashSet(Arrays.asList(strArr));
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (!"metadataType".equals(next)) {
                    String strZzbx = zzRM.zzbx(next);
                    if (strZzbx == null) {
                        Object obj = jSONObject.get(next);
                        if (obj instanceof String) {
                            this.zzRN.putString(next, (String) obj);
                        } else if (obj instanceof Integer) {
                            this.zzRN.putInt(next, ((Integer) obj).intValue());
                        } else if (obj instanceof Double) {
                            this.zzRN.putDouble(next, ((Double) obj).doubleValue());
                        }
                    } else if (hashSet.contains(strZzbx)) {
                        try {
                            Object obj2 = jSONObject.get(next);
                            if (obj2 != null) {
                                switch (zzRM.zzby(strZzbx)) {
                                    case 1:
                                        if (!(obj2 instanceof String)) {
                                            break;
                                        } else {
                                            this.zzRN.putString(strZzbx, (String) obj2);
                                            break;
                                        }
                                    case 2:
                                        if (!(obj2 instanceof Integer)) {
                                            break;
                                        } else {
                                            this.zzRN.putInt(strZzbx, ((Integer) obj2).intValue());
                                            break;
                                        }
                                    case 3:
                                        if (!(obj2 instanceof Double)) {
                                            break;
                                        } else {
                                            this.zzRN.putDouble(strZzbx, ((Double) obj2).doubleValue());
                                            break;
                                        }
                                    case 4:
                                        if ((obj2 instanceof String) && zzjz.zzbK((String) obj2) != null) {
                                            this.zzRN.putString(strZzbx, (String) obj2);
                                            break;
                                        } else {
                                            break;
                                        }
                                }
                            }
                        } catch (JSONException e) {
                        }
                    }
                }
            }
        } catch (JSONException e2) {
        }
    }

    private boolean zzb(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if ((obj instanceof Bundle) && (obj2 instanceof Bundle) && !zzb((Bundle) obj, (Bundle) obj2)) {
                return false;
            }
            if (obj == null) {
                if (obj2 != null || !bundle2.containsKey(str)) {
                    return false;
                }
            } else if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    private void zzf(String str, int i) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("null and empty keys are not allowed");
        }
        int iZzby = zzRM.zzby(str);
        if (iZzby != i && iZzby != 0) {
            throw new IllegalArgumentException("Value for " + str + " must be a " + zzRL[i]);
        }
    }

    public void addImage(WebImage image) {
        this.zzvi.add(image);
    }

    public void clear() {
        this.zzRN.clear();
        this.zzvi.clear();
    }

    public void clearImages() {
        this.zzvi.clear();
    }

    public boolean containsKey(String key) {
        return this.zzRN.containsKey(key);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MediaMetadata)) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) other;
        return zzb(this.zzRN, mediaMetadata.zzRN) && this.zzvi.equals(mediaMetadata.zzvi);
    }

    public Calendar getDate(String key) throws IllegalArgumentException {
        zzf(key, 4);
        String string = this.zzRN.getString(key);
        if (string != null) {
            return zzjz.zzbK(string);
        }
        return null;
    }

    public String getDateAsString(String key) throws IllegalArgumentException {
        zzf(key, 4);
        return this.zzRN.getString(key);
    }

    public double getDouble(String key) throws IllegalArgumentException {
        zzf(key, 3);
        return this.zzRN.getDouble(key);
    }

    public List<WebImage> getImages() {
        return this.zzvi;
    }

    public int getInt(String key) throws IllegalArgumentException {
        zzf(key, 2);
        return this.zzRN.getInt(key);
    }

    public int getMediaType() {
        return this.zzRO;
    }

    public String getString(String key) throws IllegalArgumentException {
        zzf(key, 1);
        return this.zzRN.getString(key);
    }

    public boolean hasImages() {
        return (this.zzvi == null || this.zzvi.isEmpty()) ? false : true;
    }

    public int hashCode() {
        int iHashCode = 17;
        Iterator<String> it = this.zzRN.keySet().iterator();
        while (true) {
            int i = iHashCode;
            if (!it.hasNext()) {
                return (i * 31) + this.zzvi.hashCode();
            }
            iHashCode = this.zzRN.get(it.next()).hashCode() + (i * 31);
        }
    }

    public Set<String> keySet() {
        return this.zzRN.keySet();
    }

    public void putDate(String key, Calendar value) throws IllegalArgumentException {
        zzf(key, 4);
        this.zzRN.putString(key, zzjz.zza(value));
    }

    public void putDouble(String key, double value) throws IllegalArgumentException {
        zzf(key, 3);
        this.zzRN.putDouble(key, value);
    }

    public void putInt(String key, int value) throws IllegalArgumentException {
        zzf(key, 2);
        this.zzRN.putInt(key, value);
    }

    public void putString(String key, String value) throws IllegalArgumentException {
        zzf(key, 1);
        this.zzRN.putString(key, value);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("metadataType", this.zzRO);
        } catch (JSONException e) {
        }
        zzjz.zza(jSONObject, this.zzvi);
        switch (this.zzRO) {
            case 0:
                zza(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_SUBTITLE, KEY_RELEASE_DATE);
                return jSONObject;
            case 1:
                zza(jSONObject, KEY_TITLE, KEY_STUDIO, KEY_SUBTITLE, KEY_RELEASE_DATE);
                return jSONObject;
            case 2:
                zza(jSONObject, KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE);
                return jSONObject;
            case 3:
                zza(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_ALBUM_TITLE, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE);
                return jSONObject;
            case 4:
                zza(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE);
                return jSONObject;
            default:
                zza(jSONObject, new String[0]);
                return jSONObject;
        }
    }

    public void zzf(JSONObject jSONObject) throws JSONException {
        clear();
        this.zzRO = 0;
        try {
            this.zzRO = jSONObject.getInt("metadataType");
        } catch (JSONException e) {
        }
        zzjz.zza(this.zzvi, jSONObject);
        switch (this.zzRO) {
            case 0:
                zzb(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_SUBTITLE, KEY_RELEASE_DATE);
                break;
            case 1:
                zzb(jSONObject, KEY_TITLE, KEY_STUDIO, KEY_SUBTITLE, KEY_RELEASE_DATE);
                break;
            case 2:
                zzb(jSONObject, KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE);
                break;
            case 3:
                zzb(jSONObject, KEY_TITLE, KEY_ALBUM_TITLE, KEY_ARTIST, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE);
                break;
            case 4:
                zzb(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE);
                break;
            default:
                zzb(jSONObject, new String[0]);
                break;
        }
    }
}
