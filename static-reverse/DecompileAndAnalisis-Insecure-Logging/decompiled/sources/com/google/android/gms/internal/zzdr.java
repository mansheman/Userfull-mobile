package com.google.android.gms.internal;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@zzgd
/* loaded from: classes.dex */
public class zzdr {
    private static final Set<String> zzwF = Collections.synchronizedSet(new HashSet());
    private static final DecimalFormat zzwG = new DecimalFormat("#,###");
    private zzid zzoA;
    private File zzwH;
    private boolean zzwI;

    public zzdr(zzid zzidVar) {
        this.zzoA = zzidVar;
        File cacheDir = zzidVar.getContext().getCacheDir();
        if (cacheDir == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Context.getCacheDir() returned null");
            return;
        }
        this.zzwH = new File(cacheDir, "admobVideoStreams");
        if (!this.zzwH.isDirectory() && !this.zzwH.mkdirs()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not create preload cache directory at " + this.zzwH.getAbsolutePath());
            this.zzwH = null;
        } else {
            if (this.zzwH.setReadable(true, false) && this.zzwH.setExecutable(true, false)) {
                return;
            }
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not set cache file permissions at " + this.zzwH.getAbsolutePath());
            this.zzwH = null;
        }
    }

    private String zzX(String str) {
        return com.google.android.gms.ads.internal.client.zzk.zzcA().zzax(str);
    }

    private File zza(File file) {
        return new File(this.zzwH, file.getName() + ".done");
    }

    private void zza(final String str, final File file) {
        com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new Runnable() { // from class: com.google.android.gms.internal.zzdr.3
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put("event", "precacheCanceled");
                map.put("src", str);
                if (file != null) {
                    map.put("cachedSrc", file.getAbsolutePath());
                }
                zzdr.this.zzoA.zzc("onPrecacheEvent", map);
            }
        });
    }

    private void zza(final String str, final File file, final int i) {
        com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new Runnable() { // from class: com.google.android.gms.internal.zzdr.2
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put("event", "precacheComplete");
                map.put("src", str);
                map.put("cachedSrc", file.getAbsolutePath());
                map.put("totalBytes", Integer.toString(i));
                zzdr.this.zzoA.zzc("onPrecacheEvent", map);
            }
        });
    }

    private void zza(final String str, final File file, final int i, final int i2) {
        com.google.android.gms.ads.internal.util.client.zza.zzGF.post(new Runnable() { // from class: com.google.android.gms.internal.zzdr.1
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put("event", "precacheProgress");
                map.put("src", str);
                map.put("cachedSrc", file.getAbsolutePath());
                map.put("bytesLoaded", Integer.toString(i));
                map.put("totalBytes", Integer.toString(i2));
                zzdr.this.zzoA.zzc("onPrecacheEvent", map);
            }
        });
    }

    private static void zzb(File file) throws IOException {
        if (file.isFile()) {
            file.setLastModified(System.currentTimeMillis());
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
    }

    public void abort() {
        this.zzwI = true;
    }

    public boolean zzW(String str) throws IOException {
        FileOutputStream fileOutputStream;
        boolean z;
        int responseCode;
        if (this.zzwH == null) {
            zza(str, null);
            return false;
        }
        while (zzdQ() > zzbz.zztO.get().intValue()) {
            if (!zzdR()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Unable to expire stream cache");
                zza(str, null);
                return false;
            }
        }
        File file = new File(this.zzwH, zzX(str));
        File fileZza = zza(file);
        if (file.isFile() && fileZza.isFile()) {
            int length = (int) file.length();
            com.google.android.gms.ads.internal.util.client.zzb.zzay("Stream cache hit at " + str);
            zza(str, file, length);
            return true;
        }
        String str2 = this.zzwH.getAbsolutePath() + str;
        synchronized (zzwF) {
            if (zzwF.contains(str2)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaC("Stream cache already in progress at " + str);
                zza(str, file);
                z = false;
            } else {
                zzwF.add(str2);
                try {
                    URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
                    int iIntValue = zzbz.zztS.get().intValue();
                    uRLConnectionOpenConnection.setConnectTimeout(iIntValue);
                    uRLConnectionOpenConnection.setReadTimeout(iIntValue);
                    if ((uRLConnectionOpenConnection instanceof HttpURLConnection) && (responseCode = ((HttpURLConnection) uRLConnectionOpenConnection).getResponseCode()) >= 400) {
                        throw new IOException("HTTP status code " + responseCode + " at " + str);
                    }
                    int contentLength = uRLConnectionOpenConnection.getContentLength();
                    if (contentLength < 0) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaC("Stream cache aborted, missing content-length header at " + str);
                        zza(str, file);
                        zzwF.remove(str2);
                        z = false;
                    } else {
                        String str3 = zzwG.format(contentLength);
                        int iIntValue2 = zzbz.zztP.get().intValue();
                        if (contentLength > iIntValue2) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Content length " + str3 + " exceeds limit at " + str);
                            zza(str, file);
                            zzwF.remove(str2);
                            z = false;
                        } else {
                            com.google.android.gms.ads.internal.util.client.zzb.zzay("Caching " + str3 + " bytes from " + str);
                            ReadableByteChannel readableByteChannelNewChannel = Channels.newChannel(uRLConnectionOpenConnection.getInputStream());
                            fileOutputStream = new FileOutputStream(file);
                            try {
                                FileChannel channel = fileOutputStream.getChannel();
                                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START);
                                zzlb zzlbVarZzbz = com.google.android.gms.ads.internal.zzo.zzbz();
                                long jCurrentTimeMillis = zzlbVarZzbz.currentTimeMillis();
                                zzhq zzhqVar = new zzhq(zzbz.zztR.get().longValue());
                                long jLongValue = zzbz.zztQ.get().longValue();
                                int i = 0;
                                while (true) {
                                    int i2 = readableByteChannelNewChannel.read(byteBufferAllocate);
                                    if (i2 >= 0) {
                                        i += i2;
                                        if (i > iIntValue2) {
                                            throw new IOException("stream cache file size limit exceeded");
                                        }
                                        byteBufferAllocate.flip();
                                        while (channel.write(byteBufferAllocate) > 0) {
                                        }
                                        byteBufferAllocate.clear();
                                        if (zzlbVarZzbz.currentTimeMillis() - jCurrentTimeMillis > 1000 * jLongValue) {
                                            throw new IOException("stream cache time limit exceeded");
                                        }
                                        if (this.zzwI) {
                                            throw new IOException("abort requested");
                                        }
                                        if (zzhqVar.tryAcquire()) {
                                            zza(str, file, i, contentLength);
                                        }
                                    } else {
                                        fileOutputStream.close();
                                        if (com.google.android.gms.ads.internal.util.client.zzb.zzL(3)) {
                                            com.google.android.gms.ads.internal.util.client.zzb.zzay("Preloaded " + zzwG.format(i) + " bytes from " + str);
                                        }
                                        file.setReadable(true, false);
                                        zzb(fileZza);
                                        zza(str, file, i);
                                        zzwF.remove(str2);
                                        z = true;
                                    }
                                }
                            } catch (IOException e) {
                                e = e;
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e2) {
                                } catch (NullPointerException e3) {
                                }
                                if (this.zzwI) {
                                    com.google.android.gms.ads.internal.util.client.zzb.zzaA("Preload aborted for URL \"" + str + "\"");
                                } else {
                                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Preload failed for URL \"" + str + "\"", e);
                                }
                                if (file.exists() && !file.delete()) {
                                    com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not delete partial cache file at " + file.getAbsolutePath());
                                }
                                zza(str, file);
                                zzwF.remove(str2);
                                z = false;
                                return z;
                            }
                        }
                    }
                } catch (IOException e4) {
                    e = e4;
                    fileOutputStream = null;
                }
            }
        }
        return z;
    }

    public int zzdQ() {
        int i = 0;
        if (this.zzwH != null) {
            for (File file : this.zzwH.listFiles()) {
                if (!file.getName().endsWith(".done")) {
                    i++;
                }
            }
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean zzdR() {
        /*
            r11 = this;
            r6 = 0
            java.io.File r0 = r11.zzwH
            if (r0 != 0) goto L6
        L5:
            return r6
        L6:
            r5 = 0
            r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            java.io.File r0 = r11.zzwH
            java.io.File[] r8 = r0.listFiles()
            int r9 = r8.length
            r7 = r6
        L14:
            if (r7 >= r9) goto L33
            r4 = r8[r7]
            java.lang.String r0 = r4.getName()
            java.lang.String r1 = ".done"
            boolean r0 = r0.endsWith(r1)
            if (r0 != 0) goto L4c
            long r0 = r4.lastModified()
            int r10 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r10 >= 0) goto L4c
            r2 = r4
        L2d:
            int r3 = r7 + 1
            r7 = r3
            r5 = r2
            r2 = r0
            goto L14
        L33:
            if (r5 == 0) goto L4a
            boolean r0 = r5.delete()
            java.io.File r1 = r11.zza(r5)
            boolean r2 = r1.isFile()
            if (r2 == 0) goto L48
            boolean r1 = r1.delete()
            r0 = r0 & r1
        L48:
            r6 = r0
            goto L5
        L4a:
            r0 = r6
            goto L48
        L4c:
            r0 = r2
            r2 = r5
            goto L2d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdr.zzdR():boolean");
    }
}
