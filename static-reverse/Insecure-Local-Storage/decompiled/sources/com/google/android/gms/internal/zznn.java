package com.google.android.gms.internal;

import android.net.Uri;
import android.text.TextUtils;
import android.util.LogPrinter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class zznn implements zznu {
    private static final Uri zzaDR;
    private final LogPrinter zzaDS = new LogPrinter(4, "GA/LogCatTransport");

    static {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("local");
        zzaDR = builder.build();
    }

    @Override // com.google.android.gms.internal.zznu
    public void zzb(zzno zznoVar) {
        ArrayList arrayList = new ArrayList(zznoVar.zzvQ());
        Collections.sort(arrayList, new Comparator<zznq>() { // from class: com.google.android.gms.internal.zznn.1
            @Override // java.util.Comparator
            /* renamed from: zza, reason: merged with bridge method [inline-methods] */
            public int compare(zznq zznqVar, zznq zznqVar2) {
                return zznqVar.getClass().getCanonicalName().compareTo(zznqVar2.getClass().getCanonicalName());
            }
        });
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String string = ((zznq) it.next()).toString();
            if (!TextUtils.isEmpty(string)) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(string);
            }
        }
        this.zzaDS.println(sb.toString());
    }

    @Override // com.google.android.gms.internal.zznu
    public Uri zzhe() {
        return zzaDR;
    }
}
