package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzu;

/* loaded from: classes.dex */
public abstract class zzc {
    protected final DataHolder zzWu;
    protected int zzYs;
    private int zzYt;

    public zzc(DataHolder dataHolder, int i) {
        this.zzWu = (DataHolder) zzu.zzu(dataHolder);
        zzbf(i);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc zzcVar = (zzc) obj;
        return zzt.equal(Integer.valueOf(zzcVar.zzYs), Integer.valueOf(this.zzYs)) && zzt.equal(Integer.valueOf(zzcVar.zzYt), Integer.valueOf(this.zzYt)) && zzcVar.zzWu == this.zzWu;
    }

    protected boolean getBoolean(String column) {
        return this.zzWu.zze(column, this.zzYs, this.zzYt);
    }

    protected byte[] getByteArray(String column) {
        return this.zzWu.zzg(column, this.zzYs, this.zzYt);
    }

    protected float getFloat(String column) {
        return this.zzWu.zzf(column, this.zzYs, this.zzYt);
    }

    protected int getInteger(String column) {
        return this.zzWu.zzc(column, this.zzYs, this.zzYt);
    }

    protected long getLong(String column) {
        return this.zzWu.zzb(column, this.zzYs, this.zzYt);
    }

    protected String getString(String column) {
        return this.zzWu.zzd(column, this.zzYs, this.zzYt);
    }

    public int hashCode() {
        return zzt.hashCode(Integer.valueOf(this.zzYs), Integer.valueOf(this.zzYt), this.zzWu);
    }

    public boolean isDataValid() {
        return !this.zzWu.isClosed();
    }

    protected void zza(String str, CharArrayBuffer charArrayBuffer) {
        this.zzWu.zza(str, this.zzYs, this.zzYt, charArrayBuffer);
    }

    public boolean zzbV(String str) {
        return this.zzWu.zzbV(str);
    }

    protected Uri zzbW(String str) {
        return this.zzWu.zzh(str, this.zzYs, this.zzYt);
    }

    protected boolean zzbX(String str) {
        return this.zzWu.zzi(str, this.zzYs, this.zzYt);
    }

    protected void zzbf(int i) {
        zzu.zzU(i >= 0 && i < this.zzWu.getCount());
        this.zzYs = i;
        this.zzYt = this.zzWu.zzbh(this.zzYs);
    }

    protected int zzne() {
        return this.zzYs;
    }
}
