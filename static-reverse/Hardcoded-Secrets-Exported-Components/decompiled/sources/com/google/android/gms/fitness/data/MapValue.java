package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;

/* loaded from: classes.dex */
public class MapValue implements SafeParcelable {
    public static final Parcelable.Creator<MapValue> CREATOR = new zzl();
    private final int zzCY;
    private final int zzakB;
    private final float zzakF;

    public MapValue(int format, float value) {
        this(1, format, value);
    }

    MapValue(int versionCode, int format, float value) {
        this.zzCY = versionCode;
        this.zzakB = format;
        this.zzakF = value;
    }

    private boolean zza(MapValue mapValue) {
        if (this.zzakB != mapValue.zzakB) {
            return false;
        }
        switch (this.zzakB) {
            case 2:
                if (asFloat() != mapValue.asFloat()) {
                    break;
                }
                break;
            default:
                if (this.zzakF != mapValue.zzakF) {
                    break;
                }
                break;
        }
        return true;
    }

    public static MapValue zzc(float f) {
        return new MapValue(2, f);
    }

    public float asFloat() {
        zzu.zza(this.zzakB == 2, "Value is not in float format");
        return this.zzakF;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        return this == o || ((o instanceof MapValue) && zza((MapValue) o));
    }

    int getFormat() {
        return this.zzakB;
    }

    int getVersionCode() {
        return this.zzCY;
    }

    public int hashCode() {
        return (int) this.zzakF;
    }

    public String toString() {
        switch (this.zzakB) {
            case 2:
                return Float.toString(asFloat());
            default:
                return "unknown";
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        zzl.zza(this, dest, flags);
    }

    float zzqI() {
        return this.zzakF;
    }
}
