package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.CameraPosition;

/* loaded from: classes.dex */
public final class GoogleMapOptions implements SafeParcelable {
    public static final zza CREATOR = new zza();
    private final int zzCY;
    private Boolean zzaBI;
    private Boolean zzaBJ;
    private int zzaBK;
    private CameraPosition zzaBL;
    private Boolean zzaBM;
    private Boolean zzaBN;
    private Boolean zzaBO;
    private Boolean zzaBP;
    private Boolean zzaBQ;
    private Boolean zzaBR;
    private Boolean zzaBS;
    private Boolean zzaBT;

    public GoogleMapOptions() {
        this.zzaBK = -1;
        this.zzCY = 1;
    }

    GoogleMapOptions(int versionCode, byte zOrderOnTop, byte useViewLifecycleInFragment, int mapType, CameraPosition camera, byte zoomControlsEnabled, byte compassEnabled, byte scrollGesturesEnabled, byte zoomGesturesEnabled, byte tiltGesturesEnabled, byte rotateGesturesEnabled, byte liteMode, byte mapToolbarEnabled) {
        this.zzaBK = -1;
        this.zzCY = versionCode;
        this.zzaBI = com.google.android.gms.maps.internal.zza.zza(zOrderOnTop);
        this.zzaBJ = com.google.android.gms.maps.internal.zza.zza(useViewLifecycleInFragment);
        this.zzaBK = mapType;
        this.zzaBL = camera;
        this.zzaBM = com.google.android.gms.maps.internal.zza.zza(zoomControlsEnabled);
        this.zzaBN = com.google.android.gms.maps.internal.zza.zza(compassEnabled);
        this.zzaBO = com.google.android.gms.maps.internal.zza.zza(scrollGesturesEnabled);
        this.zzaBP = com.google.android.gms.maps.internal.zza.zza(zoomGesturesEnabled);
        this.zzaBQ = com.google.android.gms.maps.internal.zza.zza(tiltGesturesEnabled);
        this.zzaBR = com.google.android.gms.maps.internal.zza.zza(rotateGesturesEnabled);
        this.zzaBS = com.google.android.gms.maps.internal.zza.zza(liteMode);
        this.zzaBT = com.google.android.gms.maps.internal.zza.zza(mapToolbarEnabled);
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return null;
        }
        TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attrs, com.google.android.gms.R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_mapType)) {
            googleMapOptions.mapType(typedArrayObtainAttributes.getInt(com.google.android.gms.R.styleable.MapAttrs_mapType, -1));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_zOrderOnTop)) {
            googleMapOptions.zOrderOnTop(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_zOrderOnTop, false));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_useViewLifecycle)) {
            googleMapOptions.useViewLifecycleInFragment(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_useViewLifecycle, false));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_uiCompass)) {
            googleMapOptions.compassEnabled(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_uiCompass, true));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_uiRotateGestures)) {
            googleMapOptions.rotateGesturesEnabled(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_uiRotateGestures, true));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_uiScrollGestures)) {
            googleMapOptions.scrollGesturesEnabled(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_uiScrollGestures, true));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_uiTiltGestures)) {
            googleMapOptions.tiltGesturesEnabled(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_uiTiltGestures, true));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_uiZoomGestures)) {
            googleMapOptions.zoomGesturesEnabled(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_uiZoomGestures, true));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_uiZoomControls)) {
            googleMapOptions.zoomControlsEnabled(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_uiZoomControls, true));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_liteMode)) {
            googleMapOptions.liteMode(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_liteMode, false));
        }
        if (typedArrayObtainAttributes.hasValue(com.google.android.gms.R.styleable.MapAttrs_uiMapToolbar)) {
            googleMapOptions.mapToolbarEnabled(typedArrayObtainAttributes.getBoolean(com.google.android.gms.R.styleable.MapAttrs_uiMapToolbar, true));
        }
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attrs));
        typedArrayObtainAttributes.recycle();
        return googleMapOptions;
    }

    public GoogleMapOptions camera(CameraPosition camera) {
        this.zzaBL = camera;
        return this;
    }

    public GoogleMapOptions compassEnabled(boolean enabled) {
        this.zzaBN = Boolean.valueOf(enabled);
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CameraPosition getCamera() {
        return this.zzaBL;
    }

    public Boolean getCompassEnabled() {
        return this.zzaBN;
    }

    public Boolean getLiteMode() {
        return this.zzaBS;
    }

    public Boolean getMapToolbarEnabled() {
        return this.zzaBT;
    }

    public int getMapType() {
        return this.zzaBK;
    }

    public Boolean getRotateGesturesEnabled() {
        return this.zzaBR;
    }

    public Boolean getScrollGesturesEnabled() {
        return this.zzaBO;
    }

    public Boolean getTiltGesturesEnabled() {
        return this.zzaBQ;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.zzaBJ;
    }

    int getVersionCode() {
        return this.zzCY;
    }

    public Boolean getZOrderOnTop() {
        return this.zzaBI;
    }

    public Boolean getZoomControlsEnabled() {
        return this.zzaBM;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.zzaBP;
    }

    public GoogleMapOptions liteMode(boolean enabled) {
        this.zzaBS = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions mapToolbarEnabled(boolean enabled) {
        this.zzaBT = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions mapType(int mapType) {
        this.zzaBK = mapType;
        return this;
    }

    public GoogleMapOptions rotateGesturesEnabled(boolean enabled) {
        this.zzaBR = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions scrollGesturesEnabled(boolean enabled) {
        this.zzaBO = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions tiltGesturesEnabled(boolean enabled) {
        this.zzaBQ = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.zzaBJ = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zza.zza(this, out, flags);
    }

    public GoogleMapOptions zOrderOnTop(boolean zOrderOnTop) {
        this.zzaBI = Boolean.valueOf(zOrderOnTop);
        return this;
    }

    public GoogleMapOptions zoomControlsEnabled(boolean enabled) {
        this.zzaBM = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions zoomGesturesEnabled(boolean enabled) {
        this.zzaBP = Boolean.valueOf(enabled);
        return this;
    }

    byte zzvj() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBI);
    }

    byte zzvk() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBJ);
    }

    byte zzvl() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBM);
    }

    byte zzvm() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBN);
    }

    byte zzvn() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBO);
    }

    byte zzvo() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBP);
    }

    byte zzvp() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBQ);
    }

    byte zzvq() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBR);
    }

    byte zzvr() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBS);
    }

    byte zzvs() {
        return com.google.android.gms.maps.internal.zza.zze(this.zzaBT);
    }
}
