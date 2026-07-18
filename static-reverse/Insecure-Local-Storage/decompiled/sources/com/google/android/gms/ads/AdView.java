package com.google.android.gms.ads;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.internal.client.zzy;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;

/* loaded from: classes.dex */
public final class AdView extends ViewGroup {
    private final zzy zznP;

    public AdView(Context context) {
        super(context);
        this.zznP = new zzy(this);
    }

    public AdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.zznP = new zzy(this, attrs, false);
    }

    public AdView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.zznP = new zzy(this, attrs, false);
    }

    public void destroy() {
        this.zznP.destroy();
    }

    public AdListener getAdListener() {
        return this.zznP.getAdListener();
    }

    public AdSize getAdSize() {
        return this.zznP.getAdSize();
    }

    public String getAdUnitId() {
        return this.zznP.getAdUnitId();
    }

    public InAppPurchaseListener getInAppPurchaseListener() {
        return this.zznP.getInAppPurchaseListener();
    }

    public String getMediationAdapterClassName() {
        return this.zznP.getMediationAdapterClassName();
    }

    public void loadAd(AdRequest adRequest) {
        this.zznP.zza(adRequest.zzaF());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getVisibility() == 8) {
            return;
        }
        int measuredWidth = childAt.getMeasuredWidth();
        int measuredHeight = childAt.getMeasuredHeight();
        int i = ((right - left) - measuredWidth) / 2;
        int i2 = ((bottom - top) - measuredHeight) / 2;
        childAt.layout(i, i2, measuredWidth + i, measuredHeight + i2);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthInPixels;
        int heightInPixels = 0;
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getVisibility() == 8) {
            AdSize adSize = getAdSize();
            if (adSize != null) {
                Context context = getContext();
                widthInPixels = adSize.getWidthInPixels(context);
                heightInPixels = adSize.getHeightInPixels(context);
            } else {
                widthInPixels = 0;
            }
        } else {
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            widthInPixels = childAt.getMeasuredWidth();
            heightInPixels = childAt.getMeasuredHeight();
        }
        setMeasuredDimension(View.resolveSize(Math.max(widthInPixels, getSuggestedMinimumWidth()), widthMeasureSpec), View.resolveSize(Math.max(heightInPixels, getSuggestedMinimumHeight()), heightMeasureSpec));
    }

    public void pause() {
        this.zznP.pause();
    }

    public void resume() {
        this.zznP.resume();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAdListener(AdListener adListener) {
        this.zznP.setAdListener(adListener);
        if (adListener != 0 && (adListener instanceof com.google.android.gms.ads.internal.client.zza)) {
            this.zznP.zza((com.google.android.gms.ads.internal.client.zza) adListener);
        } else if (adListener == 0) {
            this.zznP.zza((com.google.android.gms.ads.internal.client.zza) null);
        }
    }

    public void setAdSize(AdSize adSize) {
        this.zznP.setAdSizes(adSize);
    }

    public void setAdUnitId(String adUnitId) {
        this.zznP.setAdUnitId(adUnitId);
    }

    public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        this.zznP.setInAppPurchaseListener(inAppPurchaseListener);
    }

    public void setPlayStorePurchaseParams(PlayStorePurchaseListener playStorePurchaseListener, String publicKey) {
        this.zznP.setPlayStorePurchaseParams(playStorePurchaseListener, publicKey);
    }
}
