package com.google.android.gms.games.appcontent;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;

/* loaded from: classes.dex */
public final class AppContentTupleEntity implements SafeParcelable, AppContentTuple {
    public static final AppContentTupleEntityCreator CREATOR = new AppContentTupleEntityCreator();
    private final String mName;
    private final String mValue;
    private final int zzCY;

    AppContentTupleEntity(int versionCode, String name, String value) {
        this.zzCY = versionCode;
        this.mName = name;
        this.mValue = value;
    }

    public AppContentTupleEntity(AppContentTuple tuple) {
        this.zzCY = 1;
        this.mName = tuple.getName();
        this.mValue = tuple.getValue();
    }

    static int zza(AppContentTuple appContentTuple) {
        return zzt.hashCode(appContentTuple.getName(), appContentTuple.getValue());
    }

    static boolean zza(AppContentTuple appContentTuple, Object obj) {
        if (!(obj instanceof AppContentTuple)) {
            return false;
        }
        if (appContentTuple == obj) {
            return true;
        }
        AppContentTuple appContentTuple2 = (AppContentTuple) obj;
        return zzt.equal(appContentTuple2.getName(), appContentTuple.getName()) && zzt.equal(appContentTuple2.getValue(), appContentTuple.getValue());
    }

    static String zzb(AppContentTuple appContentTuple) {
        return zzt.zzt(appContentTuple).zzg("Name", appContentTuple.getName()).zzg("Value", appContentTuple.getValue()).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return zza(this, obj);
    }

    @Override // com.google.android.gms.games.appcontent.AppContentTuple
    public String getName() {
        return this.mName;
    }

    @Override // com.google.android.gms.games.appcontent.AppContentTuple
    public String getValue() {
        return this.mValue;
    }

    public int getVersionCode() {
        return this.zzCY;
    }

    public int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return zzb(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        AppContentTupleEntityCreator.zza(this, out, flags);
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* renamed from: zzsp, reason: merged with bridge method [inline-methods] */
    public AppContentTuple freeze() {
        return this;
    }
}
