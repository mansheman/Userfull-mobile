package com.google.android.gms.common.internal;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/* loaded from: classes.dex */
public final class zzy extends Button {
    public zzy(Context context) {
        this(context, null);
    }

    public zzy(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.buttonStyle);
    }

    private void zza(Resources resources) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        float f = resources.getDisplayMetrics().density;
        setMinHeight((int) ((f * 48.0f) + 0.5f));
        setMinWidth((int) ((f * 48.0f) + 0.5f));
    }

    private int zzb(int i, int i2, int i3) {
        switch (i) {
            case 0:
                return i2;
            case 1:
                return i3;
            default:
                throw new IllegalStateException("Unknown color scheme: " + i);
        }
    }

    private void zzb(Resources resources, int i, int i2) {
        int iZzb;
        switch (i) {
            case 0:
            case 1:
                iZzb = zzb(i2, com.google.android.gms.R.drawable.common_signin_btn_text_dark, com.google.android.gms.R.drawable.common_signin_btn_text_light);
                break;
            case 2:
                iZzb = zzb(i2, com.google.android.gms.R.drawable.common_signin_btn_icon_dark, com.google.android.gms.R.drawable.common_signin_btn_icon_light);
                break;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
        if (iZzb == -1) {
            throw new IllegalStateException("Could not find background resource!");
        }
        setBackgroundDrawable(resources.getDrawable(iZzb));
    }

    private void zzc(Resources resources, int i, int i2) {
        setTextColor(resources.getColorStateList(zzb(i2, com.google.android.gms.R.color.common_signin_btn_text_dark, com.google.android.gms.R.color.common_signin_btn_text_light)));
        switch (i) {
            case 0:
                setText(resources.getString(com.google.android.gms.R.string.common_signin_button_text));
                return;
            case 1:
                setText(resources.getString(com.google.android.gms.R.string.common_signin_button_text_long));
                return;
            case 2:
                setText((CharSequence) null);
                return;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
    }

    public void zza(Resources resources, int i, int i2) {
        zzu.zza(i >= 0 && i < 3, "Unknown button size %d", Integer.valueOf(i));
        zzu.zza(i2 >= 0 && i2 < 2, "Unknown color scheme %s", Integer.valueOf(i2));
        zza(resources);
        zzb(resources, i, i2);
        zzc(resources, i, i2);
    }
}
