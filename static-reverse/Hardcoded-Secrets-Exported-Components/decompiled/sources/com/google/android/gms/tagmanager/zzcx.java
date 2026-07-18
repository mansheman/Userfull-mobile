package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

/* loaded from: classes.dex */
class zzcx implements zzac {
    private final Context zzaNE;
    private final String zzaNV = zza("GoogleTagManager", "4.00", Build.VERSION.RELEASE, zzc(Locale.getDefault()), Build.MODEL, Build.ID);
    private final HttpClient zzaNW;
    private zza zzaNX;

    public interface zza {
        void zza(zzaq zzaqVar);

        void zzb(zzaq zzaqVar);

        void zzc(zzaq zzaqVar);
    }

    zzcx(HttpClient httpClient, Context context, zza zzaVar) {
        this.zzaNE = context.getApplicationContext();
        this.zzaNW = httpClient;
        this.zzaNX = zzaVar;
    }

    private void zza(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws IllegalStateException, IOException {
        int iAvailable;
        StringBuffer stringBuffer = new StringBuffer();
        for (Header header : httpEntityEnclosingRequest.getAllHeaders()) {
            stringBuffer.append(header.toString()).append("\n");
        }
        stringBuffer.append(httpEntityEnclosingRequest.getRequestLine().toString()).append("\n");
        if (httpEntityEnclosingRequest.getEntity() != null) {
            try {
                InputStream content = httpEntityEnclosingRequest.getEntity().getContent();
                if (content != null && (iAvailable = content.available()) > 0) {
                    byte[] bArr = new byte[iAvailable];
                    content.read(bArr);
                    stringBuffer.append("POST:\n");
                    stringBuffer.append(new String(bArr)).append("\n");
                }
            } catch (IOException e) {
                zzbg.zzaB("Error Writing hit to log...");
            }
        }
        zzbg.zzaB(stringBuffer.toString());
    }

    static String zzc(Locale locale) {
        if (locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(locale.getLanguage().toLowerCase());
        if (locale.getCountry() != null && locale.getCountry().length() != 0) {
            sb.append("-").append(locale.getCountry().toLowerCase());
        }
        return sb.toString();
    }

    private HttpEntityEnclosingRequest zzd(URL url) {
        BasicHttpEntityEnclosingRequest basicHttpEntityEnclosingRequest;
        URISyntaxException e;
        try {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("GET", url.toURI().toString());
            try {
                basicHttpEntityEnclosingRequest.addHeader("User-Agent", this.zzaNV);
            } catch (URISyntaxException e2) {
                e = e2;
                zzbg.zzaC("Exception sending hit: " + e.getClass().getSimpleName());
                zzbg.zzaC(e.getMessage());
                return basicHttpEntityEnclosingRequest;
            }
        } catch (URISyntaxException e3) {
            basicHttpEntityEnclosingRequest = null;
            e = e3;
        }
        return basicHttpEntityEnclosingRequest;
    }

    String zza(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", str, str2, str3, str4, str5, str6);
    }

    URL zzd(zzaq zzaqVar) {
        try {
            return new URL(zzaqVar.zzyQ());
        } catch (MalformedURLException e) {
            zzbg.zzaz("Error trying to parse the GTM url.");
            return null;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzac
    public void zzr(List<zzaq> list) throws IllegalStateException, IOException {
        boolean z;
        int iMin = Math.min(list.size(), 40);
        boolean z2 = true;
        int i = 0;
        while (i < iMin) {
            zzaq zzaqVar = list.get(i);
            URL urlZzd = zzd(zzaqVar);
            if (urlZzd == null) {
                zzbg.zzaC("No destination: discarding hit.");
                this.zzaNX.zzb(zzaqVar);
                z = z2;
            } else {
                HttpEntityEnclosingRequest httpEntityEnclosingRequestZzd = zzd(urlZzd);
                if (httpEntityEnclosingRequestZzd == null) {
                    this.zzaNX.zzb(zzaqVar);
                    z = z2;
                } else {
                    HttpHost httpHost = new HttpHost(urlZzd.getHost(), urlZzd.getPort(), urlZzd.getProtocol());
                    httpEntityEnclosingRequestZzd.addHeader("Host", httpHost.toHostString());
                    zza(httpEntityEnclosingRequestZzd);
                    if (z2) {
                        try {
                            zzbl.zzaJ(this.zzaNE);
                            z2 = false;
                        } catch (ClientProtocolException e) {
                            zzbg.zzaC("ClientProtocolException sending hit; discarding hit...");
                            this.zzaNX.zzb(zzaqVar);
                            z = z2;
                        } catch (IOException e2) {
                            zzbg.zzaC("Exception sending hit: " + e2.getClass().getSimpleName());
                            zzbg.zzaC(e2.getMessage());
                            this.zzaNX.zzc(zzaqVar);
                            z = z2;
                        }
                    }
                    HttpResponse httpResponseExecute = this.zzaNW.execute(httpHost, httpEntityEnclosingRequestZzd);
                    int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
                    HttpEntity entity = httpResponseExecute.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                    if (statusCode != 200) {
                        zzbg.zzaC("Bad response: " + httpResponseExecute.getStatusLine().getStatusCode());
                        this.zzaNX.zzc(zzaqVar);
                    } else {
                        this.zzaNX.zza(zzaqVar);
                    }
                    z = z2;
                }
            }
            i++;
            z2 = z;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzac
    public boolean zzyH() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzaNE.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzbg.zzaB("...no network connectivity");
        return false;
    }
}
