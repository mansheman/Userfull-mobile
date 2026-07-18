package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.Path;
import android.net.Uri;
import android.widget.ImageView;

/* loaded from: classes.dex */
public final class zzki extends ImageView {
    private Uri zzZF;
    private int zzZG;
    private int zzZH;
    private zza zzZI;
    private int zzZJ;
    private float zzZK;

    public interface zza {
        Path zzk(int i, int i2);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.zzZI != null) {
            canvas.clipPath(this.zzZI.zzk(getWidth(), getHeight()));
        }
        super.onDraw(canvas);
        if (this.zzZH != 0) {
            canvas.drawColor(this.zzZH);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth;
        int measuredHeight;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        switch (this.zzZJ) {
            case 1:
                measuredHeight = getMeasuredHeight();
                measuredWidth = (int) (measuredHeight * this.zzZK);
                break;
            case 2:
                measuredWidth = getMeasuredWidth();
                measuredHeight = (int) (measuredWidth / this.zzZK);
                break;
            default:
                return;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    public void zzbo(int i) {
        this.zzZG = i;
    }

    public void zzi(Uri uri) {
        this.zzZF = uri;
    }

    public int zznr() {
        return this.zzZG;
    }
}
