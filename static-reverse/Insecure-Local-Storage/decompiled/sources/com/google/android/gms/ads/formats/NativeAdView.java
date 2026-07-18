package com.google.android.gms.ads.formats;

import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzcm;

/* loaded from: classes.dex */
public abstract class NativeAdView extends FrameLayout {
    private final FrameLayout zznV;
    private final zzcm zznW;

    public NativeAdView(Context context) {
        super(context);
        this.zznV = zzm(context);
        this.zznW = zzaI();
    }

    public NativeAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.zznV = zzm(context);
        this.zznW = zzaI();
    }

    public NativeAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.zznV = zzm(context);
        this.zznW = zzaI();
    }

    public NativeAdView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.zznV = zzm(context);
        this.zznW = zzaI();
    }

    private zzcm zzaI() {
        zzu.zzb(this.zznV, "createDelegate must be called after mOverlayFrame has been created");
        return zzk.zzcE().zza(this.zznV.getContext(), this, this.zznV);
    }

    private FrameLayout zzm(Context context) {
        FrameLayout frameLayoutZzn = zzn(context);
        frameLayoutZzn.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(frameLayoutZzn);
        return frameLayoutZzn;
    }

    @Override // android.view.ViewGroup
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        super.bringChildToFront(this.zznV);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void bringChildToFront(View child) {
        super.bringChildToFront(child);
        if (this.zznV != child) {
            super.bringChildToFront(this.zznV);
        }
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        super.removeAllViews();
        super.addView(this.zznV);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View child) {
        if (this.zznV == child) {
            return;
        }
        super.removeView(child);
    }

    public void setNativeAd(NativeAd ad) {
        try {
            this.zznW.zzb((zzd) ad.zzaH());
        } catch (RemoteException e) {
            zzb.zzb("Unable to call setNativeAd on delegate", e);
        }
    }

    protected void zza(String str, View view) {
        try {
            this.zznW.zza(str, zze.zzw(view));
        } catch (RemoteException e) {
            zzb.zzb("Unable to call setAssetView on delegate", e);
        }
    }

    protected View zzm(String str) {
        try {
            zzd zzdVarZzS = this.zznW.zzS(str);
            if (zzdVarZzS != null) {
                return (View) zze.zzn(zzdVarZzS);
            }
        } catch (RemoteException e) {
            zzb.zzb("Unable to call getAssetView on delegate", e);
        }
        return null;
    }

    FrameLayout zzn(Context context) {
        return new FrameLayout(context);
    }
}
