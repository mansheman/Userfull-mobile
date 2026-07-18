package com.google.android.gms.cast.internal;

import android.os.SystemClock;
import android.support.v7.media.MediaRouteProviderProtocol;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.cast.TextTrackStyle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class zzm extends zzc {
    private static final String NAMESPACE = zzf.zzbE("com.google.cast.media");
    private final List<zzp> zzTo;
    private long zzUY;
    private MediaStatus zzUZ;
    private final zzp zzVa;
    private final zzp zzVb;
    private final zzp zzVc;
    private final zzp zzVd;
    private final zzp zzVe;
    private final zzp zzVf;
    private final zzp zzVg;
    private final zzp zzVh;
    private final zzp zzVi;
    private final zzp zzVj;
    private final zzp zzVk;
    private final zzp zzVl;
    private final zzp zzVm;
    private final zzp zzVn;

    public zzm(String str) {
        super(NAMESPACE, "MediaControlChannel", str, 1000L);
        this.zzVa = new zzp(86400000L);
        this.zzVb = new zzp(86400000L);
        this.zzVc = new zzp(86400000L);
        this.zzVd = new zzp(86400000L);
        this.zzVe = new zzp(86400000L);
        this.zzVf = new zzp(86400000L);
        this.zzVg = new zzp(86400000L);
        this.zzVh = new zzp(86400000L);
        this.zzVi = new zzp(86400000L);
        this.zzVj = new zzp(86400000L);
        this.zzVk = new zzp(86400000L);
        this.zzVl = new zzp(86400000L);
        this.zzVm = new zzp(86400000L);
        this.zzVn = new zzp(86400000L);
        this.zzTo = new ArrayList();
        this.zzTo.add(this.zzVa);
        this.zzTo.add(this.zzVb);
        this.zzTo.add(this.zzVc);
        this.zzTo.add(this.zzVd);
        this.zzTo.add(this.zzVe);
        this.zzTo.add(this.zzVf);
        this.zzTo.add(this.zzVg);
        this.zzTo.add(this.zzVh);
        this.zzTo.add(this.zzVi);
        this.zzTo.add(this.zzVj);
        this.zzTo.add(this.zzVk);
        this.zzTo.add(this.zzVl);
        this.zzTo.add(this.zzVm);
        this.zzTo.add(this.zzVn);
        zzmc();
    }

    private void zza(long j, JSONObject jSONObject) throws JSONException {
        int iZza;
        boolean z = true;
        boolean zZzB = this.zzVa.zzB(j);
        boolean z2 = this.zzVe.zzme() && !this.zzVe.zzB(j);
        if ((!this.zzVf.zzme() || this.zzVf.zzB(j)) && (!this.zzVg.zzme() || this.zzVg.zzB(j))) {
            z = false;
        }
        int i = z2 ? 2 : 0;
        if (z) {
            i |= 1;
        }
        if (zZzB || this.zzUZ == null) {
            this.zzUZ = new MediaStatus(jSONObject);
            this.zzUY = SystemClock.elapsedRealtime();
            iZza = 31;
        } else {
            iZza = this.zzUZ.zza(jSONObject, i);
        }
        if ((iZza & 1) != 0) {
            this.zzUY = SystemClock.elapsedRealtime();
            onStatusUpdated();
        }
        if ((iZza & 2) != 0) {
            this.zzUY = SystemClock.elapsedRealtime();
            onStatusUpdated();
        }
        if ((iZza & 4) != 0) {
            onMetadataUpdated();
        }
        if ((iZza & 8) != 0) {
            onQueueStatusUpdated();
        }
        if ((iZza & 16) != 0) {
            onPreloadStatusUpdated();
        }
        Iterator<zzp> it = this.zzTo.iterator();
        while (it.hasNext()) {
            it.next().zzc(j, 0);
        }
    }

    private void zzmc() {
        this.zzUY = 0L;
        this.zzUZ = null;
        Iterator<zzp> it = this.zzTo.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    public long getApproximateStreamPosition() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo == null || this.zzUY == 0) {
            return 0L;
        }
        double playbackRate = this.zzUZ.getPlaybackRate();
        long streamPosition = this.zzUZ.getStreamPosition();
        int playerState = this.zzUZ.getPlayerState();
        if (playbackRate == 0.0d || playerState != 2) {
            return streamPosition;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.zzUY;
        long j = jElapsedRealtime < 0 ? 0L : jElapsedRealtime;
        if (j == 0) {
            return streamPosition;
        }
        long streamDuration = mediaInfo.getStreamDuration();
        long j2 = streamPosition + ((long) (j * playbackRate));
        if (streamDuration <= 0 || j2 <= streamDuration) {
            streamDuration = j2 < 0 ? 0L : j2;
        }
        return streamDuration;
    }

    public MediaInfo getMediaInfo() {
        if (this.zzUZ == null) {
            return null;
        }
        return this.zzUZ.getMediaInfo();
    }

    public MediaStatus getMediaStatus() {
        return this.zzUZ;
    }

    public long getStreamDuration() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo != null) {
            return mediaInfo.getStreamDuration();
        }
        return 0L;
    }

    protected void onMetadataUpdated() {
    }

    protected void onPreloadStatusUpdated() {
    }

    protected void onQueueStatusUpdated() {
    }

    protected void onStatusUpdated() {
    }

    public long zza(zzo zzoVar) throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject();
        long jZzlK = zzlK();
        this.zzVh.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject.put("requestId", jZzlK);
            jSONObject.put("type", "GET_STATUS");
            if (this.zzUZ != null) {
                jSONObject.put("mediaSessionId", this.zzUZ.zzls());
            }
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, double d, JSONObject jSONObject) throws IllegalStateException, JSONException, IOException, IllegalArgumentException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVf.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", zzls());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("level", d);
            jSONObject2.put(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, int i, MediaQueueItem[] mediaQueueItemArr, int i2, Integer num, JSONObject jSONObject) throws IllegalStateException, JSONException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVl.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "QUEUE_UPDATE");
            jSONObject2.put("mediaSessionId", zzls());
            if (i != 0) {
                jSONObject2.put("currentItemId", i);
            }
            if (i2 != 0) {
                jSONObject2.put("jump", i2);
            }
            if (mediaQueueItemArr != null && mediaQueueItemArr.length > 0) {
                JSONArray jSONArray = new JSONArray();
                for (int i3 = 0; i3 < mediaQueueItemArr.length; i3++) {
                    jSONArray.put(i3, mediaQueueItemArr[i3].toJson());
                }
                jSONObject2.put("items", jSONArray);
            }
            if (num != null) {
                switch (num.intValue()) {
                    case 0:
                        jSONObject2.put("repeatMode", "REPEAT_OFF");
                        break;
                    case 1:
                        jSONObject2.put("repeatMode", "REPEAT_ALL");
                        break;
                    case 2:
                        jSONObject2.put("repeatMode", "REPEAT_SINGLE");
                        break;
                    case 3:
                        jSONObject2.put("repeatMode", "REPEAT_ALL_AND_SHUFFLE");
                        break;
                }
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, long j, int i, JSONObject jSONObject) throws IllegalStateException, JSONException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVe.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "SEEK");
            jSONObject2.put("mediaSessionId", zzls());
            jSONObject2.put("currentTime", zzf.zzA(j));
            if (i == 1) {
                jSONObject2.put("resumeState", "PLAYBACK_START");
            } else if (i == 2) {
                jSONObject2.put("resumeState", "PLAYBACK_PAUSE");
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, MediaInfo mediaInfo, boolean z, long j, long[] jArr, JSONObject jSONObject) throws JSONException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVa.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "LOAD");
            jSONObject2.put("media", mediaInfo.toJson());
            jSONObject2.put("autoplay", z);
            jSONObject2.put("currentTime", zzf.zzA(j));
            if (jArr != null) {
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < jArr.length; i++) {
                    jSONArray.put(i, jArr[i]);
                }
                jSONObject2.put("activeTrackIds", jSONArray);
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, TextTrackStyle textTrackStyle) throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject();
        long jZzlK = zzlK();
        this.zzVj.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject.put("requestId", jZzlK);
            jSONObject.put("type", "EDIT_TRACKS_INFO");
            if (textTrackStyle != null) {
                jSONObject.put("textTrackStyle", textTrackStyle.toJson());
            }
            jSONObject.put("mediaSessionId", zzls());
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, JSONObject jSONObject) throws JSONException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVb.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "PAUSE");
            jSONObject2.put("mediaSessionId", zzls());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, boolean z, JSONObject jSONObject) throws IllegalStateException, JSONException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVg.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", zzls());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("muted", z);
            jSONObject2.put(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, int[] iArr, int i, JSONObject jSONObject) throws IllegalStateException, JSONException, IOException, IllegalArgumentException {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("itemIdsToReorder must not be null or empty.");
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVn.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "QUEUE_REORDER");
            jSONObject2.put("mediaSessionId", zzls());
            JSONArray jSONArray = new JSONArray();
            for (int i2 = 0; i2 < iArr.length; i2++) {
                jSONArray.put(i2, iArr[i2]);
            }
            jSONObject2.put("itemIds", jSONArray);
            if (i != 0) {
                jSONObject2.put("insertBefore", i);
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, int[] iArr, JSONObject jSONObject) throws IllegalStateException, JSONException, IOException, IllegalArgumentException {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("itemIdsToRemove must not be null or empty.");
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVm.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "QUEUE_REMOVE");
            jSONObject2.put("mediaSessionId", zzls());
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < iArr.length; i++) {
                jSONArray.put(i, iArr[i]);
            }
            jSONObject2.put("itemIds", jSONArray);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, long[] jArr) throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject();
        long jZzlK = zzlK();
        this.zzVi.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject.put("requestId", jZzlK);
            jSONObject.put("type", "EDIT_TRACKS_INFO");
            jSONObject.put("mediaSessionId", zzls());
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < jArr.length; i++) {
                jSONArray.put(i, jArr[i]);
            }
            jSONObject.put("activeTrackIds", jSONArray);
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, MediaQueueItem[] mediaQueueItemArr, int i, int i2, JSONObject jSONObject) throws JSONException, IOException, IllegalArgumentException {
        if (mediaQueueItemArr == null || mediaQueueItemArr.length == 0) {
            throw new IllegalArgumentException("items must not be null or empty.");
        }
        if (i < 0 || i >= mediaQueueItemArr.length) {
            throw new IllegalArgumentException("Invalid startIndex: " + i);
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVa.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "QUEUE_LOAD");
            JSONArray jSONArray = new JSONArray();
            for (int i3 = 0; i3 < mediaQueueItemArr.length; i3++) {
                jSONArray.put(i3, mediaQueueItemArr[i3].toJson());
            }
            jSONObject2.put("items", jSONArray);
            switch (i2) {
                case 0:
                    jSONObject2.put("repeatMode", "REPEAT_OFF");
                    break;
                case 1:
                    jSONObject2.put("repeatMode", "REPEAT_ALL");
                    break;
                case 2:
                    jSONObject2.put("repeatMode", "REPEAT_SINGLE");
                    break;
                case 3:
                    jSONObject2.put("repeatMode", "REPEAT_ALL_AND_SHUFFLE");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid repeat mode: " + i2);
            }
            jSONObject2.put("startIndex", i);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zza(zzo zzoVar, MediaQueueItem[] mediaQueueItemArr, int i, JSONObject jSONObject) throws IllegalStateException, JSONException, IOException {
        if (mediaQueueItemArr == null || mediaQueueItemArr.length == 0) {
            throw new IllegalArgumentException("itemsToInsert must not be null or empty.");
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVk.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "QUEUE_INSERT");
            jSONObject2.put("mediaSessionId", zzls());
            JSONArray jSONArray = new JSONArray();
            for (int i2 = 0; i2 < mediaQueueItemArr.length; i2++) {
                jSONArray.put(i2, mediaQueueItemArr[i2].toJson());
            }
            jSONObject2.put("items", jSONArray);
            if (i != 0) {
                jSONObject2.put("insertBefore", i);
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    public long zzb(zzo zzoVar, JSONObject jSONObject) throws JSONException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVd.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "STOP");
            jSONObject2.put("mediaSessionId", zzls());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    @Override // com.google.android.gms.cast.internal.zzd
    public void zzb(long j, int i) {
        Iterator<zzp> it = this.zzTo.iterator();
        while (it.hasNext()) {
            it.next().zzc(j, i);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzd
    public final void zzbB(String str) throws JSONException {
        this.zzUi.zzb("message received: %s", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("type");
            long jOptLong = jSONObject.optLong("requestId", -1L);
            if (string.equals("MEDIA_STATUS")) {
                JSONArray jSONArray = jSONObject.getJSONArray("status");
                if (jSONArray.length() > 0) {
                    zza(jOptLong, jSONArray.getJSONObject(0));
                    return;
                }
                this.zzUZ = null;
                onStatusUpdated();
                onMetadataUpdated();
                onQueueStatusUpdated();
                onPreloadStatusUpdated();
                this.zzVh.zzc(jOptLong, 0);
                return;
            }
            if (string.equals("INVALID_PLAYER_STATE")) {
                this.zzUi.zzf("received unexpected error: Invalid Player State.", new Object[0]);
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("customData");
                Iterator<zzp> it = this.zzTo.iterator();
                while (it.hasNext()) {
                    it.next().zzc(jOptLong, 2100, jSONObjectOptJSONObject);
                }
                return;
            }
            if (string.equals("LOAD_FAILED")) {
                this.zzVa.zzc(jOptLong, 2100, jSONObject.optJSONObject("customData"));
                return;
            }
            if (string.equals("LOAD_CANCELLED")) {
                this.zzVa.zzc(jOptLong, RemoteMediaPlayer.STATUS_CANCELED, jSONObject.optJSONObject("customData"));
                return;
            }
            if (string.equals("INVALID_REQUEST")) {
                this.zzUi.zzf("received unexpected error: Invalid Request.", new Object[0]);
                JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("customData");
                Iterator<zzp> it2 = this.zzTo.iterator();
                while (it2.hasNext()) {
                    it2.next().zzc(jOptLong, 2100, jSONObjectOptJSONObject2);
                }
            }
        } catch (JSONException e) {
            this.zzUi.zzf("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
        }
    }

    public long zzc(zzo zzoVar, JSONObject jSONObject) throws IllegalStateException, JSONException, IOException {
        JSONObject jSONObject2 = new JSONObject();
        long jZzlK = zzlK();
        this.zzVc.zza(jZzlK, zzoVar);
        zzQ(true);
        try {
            jSONObject2.put("requestId", jZzlK);
            jSONObject2.put("type", "PLAY");
            jSONObject2.put("mediaSessionId", zzls());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), jZzlK, (String) null);
        return jZzlK;
    }

    @Override // com.google.android.gms.cast.internal.zzc, com.google.android.gms.cast.internal.zzd
    public void zzlJ() {
        super.zzlJ();
        zzmc();
    }

    public long zzls() throws IllegalStateException {
        if (this.zzUZ == null) {
            throw new IllegalStateException("No current media session");
        }
        return this.zzUZ.zzls();
    }

    @Override // com.google.android.gms.cast.internal.zzc
    protected boolean zzz(long j) {
        boolean z;
        Iterator<zzp> it = this.zzTo.iterator();
        while (it.hasNext()) {
            it.next().zzd(j, RemoteMediaPlayer.STATUS_TIMED_OUT);
        }
        synchronized (zzp.zzVr) {
            Iterator<zzp> it2 = this.zzTo.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                if (it2.next().zzme()) {
                    z = true;
                    break;
                }
            }
        }
        return z;
    }
}
