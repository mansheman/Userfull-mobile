package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.ads.formats.NativeAd;

@zzgd
/* loaded from: classes.dex */
public class zzcl implements NativeAd.Image {
    private final Uri mUri;
    private final zzck zzvI;
    private final Drawable zzvJ;

    public zzcl(zzck zzckVar) {
        com.google.android.gms.dynamic.zzd zzdVarZzdw;
        Uri uri = null;
        this.zzvI = zzckVar;
        try {
            zzdVarZzdw = this.zzvI.zzdw();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get drawable.", e);
        }
        Drawable drawable = zzdVarZzdw != null ? (Drawable) com.google.android.gms.dynamic.zze.zzn(zzdVarZzdw) : null;
        this.zzvJ = drawable;
        try {
            uri = this.zzvI.getUri();
        } catch (RemoteException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get uri.", e2);
        }
        this.mUri = uri;
    }

    @Override // com.google.android.gms.ads.formats.NativeAd.Image
    public Drawable getDrawable() {
        return this.zzvJ;
    }

    @Override // com.google.android.gms.ads.formats.NativeAd.Image
    public Uri getUri() {
        return this.mUri;
    }
}
