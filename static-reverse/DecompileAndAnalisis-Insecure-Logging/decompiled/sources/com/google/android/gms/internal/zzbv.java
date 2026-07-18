package com.google.android.gms.internal;

import android.content.SharedPreferences;

@zzgd
/* loaded from: classes.dex */
public abstract class zzbv<T> implements zzbs {
    private final String zztw;
    private final T zztx;

    private zzbv(String str, T t) {
        this.zztw = str;
        this.zztx = t;
        com.google.android.gms.ads.internal.zzo.zzbD().zza(this);
    }

    public static zzbv<String> zzO(String str) {
        zzbv<String> zzbvVarZzc = zzc(str, (String) null);
        com.google.android.gms.ads.internal.zzo.zzbD().zza(zzbvVarZzc);
        return zzbvVarZzc;
    }

    public static zzbv<String> zzP(String str) {
        zzbv<String> zzbvVarZzc = zzc(str, (String) null);
        com.google.android.gms.ads.internal.zzo.zzbD().zzb(zzbvVarZzc);
        return zzbvVarZzc;
    }

    public static zzbv<Integer> zza(String str, int i) {
        return new zzbv<Integer>(str, Integer.valueOf(i)) { // from class: com.google.android.gms.internal.zzbv.2
            @Override // com.google.android.gms.internal.zzbv
            /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
            public Integer zza(SharedPreferences sharedPreferences) {
                return Integer.valueOf(sharedPreferences.getInt(getKey(), zzcY().intValue()));
            }

            @Override // com.google.android.gms.internal.zzbv
            public zzkf<Integer> zzcZ() {
                return zzkf.zza(getKey(), zzcY());
            }
        };
    }

    public static zzbv<Boolean> zza(String str, Boolean bool) {
        return new zzbv<Boolean>(str, bool) { // from class: com.google.android.gms.internal.zzbv.1
            @Override // com.google.android.gms.internal.zzbv
            /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
            public Boolean zza(SharedPreferences sharedPreferences) {
                return Boolean.valueOf(sharedPreferences.getBoolean(getKey(), zzcY().booleanValue()));
            }

            @Override // com.google.android.gms.internal.zzbv
            public zzkf<Boolean> zzcZ() {
                return zzkf.zzg(getKey(), zzcY().booleanValue());
            }
        };
    }

    public static zzbv<Long> zzb(String str, long j) {
        return new zzbv<Long>(str, Long.valueOf(j)) { // from class: com.google.android.gms.internal.zzbv.3
            @Override // com.google.android.gms.internal.zzbv
            public zzkf<Long> zzcZ() {
                return zzkf.zza(getKey(), zzcY());
            }

            @Override // com.google.android.gms.internal.zzbv
            /* renamed from: zzd, reason: merged with bridge method [inline-methods] */
            public Long zza(SharedPreferences sharedPreferences) {
                return Long.valueOf(sharedPreferences.getLong(getKey(), zzcY().longValue()));
            }
        };
    }

    public static zzbv<String> zzc(String str, String str2) {
        return new zzbv<String>(str, str2) { // from class: com.google.android.gms.internal.zzbv.4
            @Override // com.google.android.gms.internal.zzbv
            public zzkf<String> zzcZ() {
                return zzkf.zzs(getKey(), zzcY());
            }

            @Override // com.google.android.gms.internal.zzbv
            /* renamed from: zze, reason: merged with bridge method [inline-methods] */
            public String zza(SharedPreferences sharedPreferences) {
                return sharedPreferences.getString(getKey(), zzcY());
            }
        };
    }

    public T get() {
        return (T) com.google.android.gms.ads.internal.zzo.zzbE().zzc(this);
    }

    public String getKey() {
        return this.zztw;
    }

    protected abstract T zza(SharedPreferences sharedPreferences);

    public T zzcY() {
        return this.zztx;
    }

    public abstract zzkf<T> zzcZ();
}
