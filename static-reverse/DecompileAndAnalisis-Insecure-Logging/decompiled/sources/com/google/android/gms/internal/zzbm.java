package com.google.android.gms.internal;

import android.util.Base64OutputStream;
import com.google.android.gms.internal.zzbp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.PriorityQueue;

/* loaded from: classes.dex */
public class zzbm {
    private final int zzrM;
    private final zzbl zzrO = new zzbo();
    private final int zzrL = 6;
    private final int zzrN = 0;

    static class zza {
        ByteArrayOutputStream zzrQ = new ByteArrayOutputStream(4096);
        Base64OutputStream zzrR = new Base64OutputStream(this.zzrQ, 10);

        /* JADX WARN: Multi-variable type inference failed */
        public String toString() throws IOException {
            String string;
            try {
                this.zzrR.close();
            } catch (IOException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("HashManager: Unable to convert to Base64.", e);
            }
            try {
                this.zzrQ.close();
                string = this.zzrQ.toString();
            } catch (IOException e2) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("HashManager: Unable to convert to Base64.", e2);
                string = "";
            } finally {
                this.zzrQ = null;
                this.zzrR = null;
            }
            return string;
        }

        public void write(byte[] data) throws IOException {
            this.zzrR.write(data);
        }
    }

    public zzbm(int i) {
        this.zzrM = i;
    }

    private String zzz(String str) {
        String[] strArrSplit = str.split("\n");
        if (strArrSplit.length == 0) {
            return "";
        }
        zza zzaVarZzcv = zzcv();
        Arrays.sort(strArrSplit, new Comparator<String>() { // from class: com.google.android.gms.internal.zzbm.1
            @Override // java.util.Comparator
            public int compare(String s1, String s2) {
                return s2.length() - s1.length();
            }
        });
        for (int i = 0; i < strArrSplit.length && i < this.zzrM; i++) {
            if (strArrSplit[i].trim().length() != 0) {
                try {
                    zzaVarZzcv.write(this.zzrO.zzy(strArrSplit[i]));
                } catch (IOException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Error while writing hash to byteStream", e);
                }
            }
        }
        return zzaVarZzcv.toString();
    }

    String zzA(String str) {
        String[] strArrSplit = str.split("\n");
        if (strArrSplit.length == 0) {
            return "";
        }
        zza zzaVarZzcv = zzcv();
        PriorityQueue priorityQueue = new PriorityQueue(this.zzrM, new Comparator<zzbp.zza>() { // from class: com.google.android.gms.internal.zzbm.2
            @Override // java.util.Comparator
            /* renamed from: zza, reason: merged with bridge method [inline-methods] */
            public int compare(zzbp.zza zzaVar, zzbp.zza zzaVar2) {
                return (int) (zzaVar.value - zzaVar2.value);
            }
        });
        for (String str2 : strArrSplit) {
            String[] strArrZzC = zzbn.zzC(str2);
            if (strArrZzC.length >= this.zzrL) {
                zzbp.zza(strArrZzC, this.zzrM, this.zzrL, (PriorityQueue<zzbp.zza>) priorityQueue);
            }
        }
        Iterator it = priorityQueue.iterator();
        while (it.hasNext()) {
            try {
                zzaVarZzcv.write(this.zzrO.zzy(((zzbp.zza) it.next()).zzrT));
            } catch (IOException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error while writing hash to byteStream", e);
            }
        }
        return zzaVarZzcv.toString();
    }

    public String zza(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next().toLowerCase(Locale.US));
            stringBuffer.append('\n');
        }
        switch (this.zzrN) {
            case 0:
                return zzA(stringBuffer.toString());
            case 1:
                return zzz(stringBuffer.toString());
            default:
                return "";
        }
    }

    zza zzcv() {
        return new zza();
    }
}
