package com.google.android.gms.location.places.internal;

import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzrd;
import com.google.android.gms.internal.zzrm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public abstract class zzt extends com.google.android.gms.common.data.zzc {
    private final String TAG;

    public zzt(DataHolder dataHolder, int i) {
        super(dataHolder, i);
        this.TAG = "SafeDataBufferRef";
    }

    protected String zzB(String str, String str2) {
        return (!zzbV(str) || zzbX(str)) ? str2 : getString(str);
    }

    protected <E extends SafeParcelable> E zza(String str, Parcelable.Creator<E> creator) {
        byte[] bArrZzc = zzc(str, null);
        if (bArrZzc == null) {
            return null;
        }
        return (E) com.google.android.gms.common.internal.safeparcel.zzc.zza(bArrZzc, creator);
    }

    protected <E extends SafeParcelable> List<E> zza(String str, Parcelable.Creator<E> creator, List<E> list) {
        byte[] bArrZzc = zzc(str, null);
        if (bArrZzc == null) {
            return list;
        }
        try {
            zzrd zzrdVarZzx = zzrd.zzx(bArrZzc);
            if (zzrdVarZzx.zzaVG == null) {
                return list;
            }
            ArrayList arrayList = new ArrayList(zzrdVarZzx.zzaVG.length);
            byte[][] bArr = zzrdVarZzx.zzaVG;
            for (byte[] bArr2 : bArr) {
                arrayList.add(com.google.android.gms.common.internal.safeparcel.zzc.zza(bArr2, creator));
            }
            return arrayList;
        } catch (zzrm e) {
            if (!Log.isLoggable("SafeDataBufferRef", 6)) {
                return list;
            }
            Log.e("SafeDataBufferRef", "Cannot parse byte[]", e);
            return list;
        }
    }

    protected List<Integer> zza(String str, List<Integer> list) {
        byte[] bArrZzc = zzc(str, null);
        if (bArrZzc == null) {
            return list;
        }
        try {
            zzrd zzrdVarZzx = zzrd.zzx(bArrZzc);
            if (zzrdVarZzx.zzaVF == null) {
                return list;
            }
            ArrayList arrayList = new ArrayList(zzrdVarZzx.zzaVF.length);
            for (int i = 0; i < zzrdVarZzx.zzaVF.length; i++) {
                arrayList.add(Integer.valueOf(zzrdVarZzx.zzaVF[i]));
            }
            return arrayList;
        } catch (zzrm e) {
            if (!Log.isLoggable("SafeDataBufferRef", 6)) {
                return list;
            }
            Log.e("SafeDataBufferRef", "Cannot parse byte[]", e);
            return list;
        }
    }

    protected float zzb(String str, float f) {
        return (!zzbV(str) || zzbX(str)) ? f : getFloat(str);
    }

    protected List<String> zzb(String str, List<String> list) {
        byte[] bArrZzc = zzc(str, null);
        if (bArrZzc == null) {
            return list;
        }
        try {
            zzrd zzrdVarZzx = zzrd.zzx(bArrZzc);
            return zzrdVarZzx.zzaVE != null ? Arrays.asList(zzrdVarZzx.zzaVE) : list;
        } catch (zzrm e) {
            if (!Log.isLoggable("SafeDataBufferRef", 6)) {
                return list;
            }
            Log.e("SafeDataBufferRef", "Cannot parse byte[]", e);
            return list;
        }
    }

    protected byte[] zzc(String str, byte[] bArr) {
        return (!zzbV(str) || zzbX(str)) ? bArr : getByteArray(str);
    }

    protected boolean zzh(String str, boolean z) {
        return (!zzbV(str) || zzbX(str)) ? z : getBoolean(str);
    }

    protected int zzz(String str, int i) {
        return (!zzbV(str) || zzbX(str)) ? i : getInteger(str);
    }
}
