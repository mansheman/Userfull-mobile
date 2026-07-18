package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.query.Filter;
import java.util.List;

/* loaded from: classes.dex */
public class zzg implements zzf<Boolean> {
    private Boolean zzaid = false;

    private zzg() {
    }

    public static boolean zza(Filter filter) {
        if (filter == null) {
            return false;
        }
        return ((Boolean) filter.zza(new zzg())).booleanValue();
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    public /* synthetic */ Boolean zzb(com.google.android.gms.drive.metadata.zzb zzbVar, Object obj) {
        return zzc((com.google.android.gms.drive.metadata.zzb<com.google.android.gms.drive.metadata.zzb>) zzbVar, (com.google.android.gms.drive.metadata.zzb) obj);
    }

    public <T> Boolean zzc(com.google.android.gms.drive.metadata.zzb<T> zzbVar, T t) {
        return this.zzaid;
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public <T> Boolean zzb(Operator operator, MetadataField<T> metadataField, T t) {
        return this.zzaid;
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public Boolean zzb(Operator operator, List<Boolean> list) {
        return this.zzaid;
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    /* renamed from: zzcD, reason: merged with bridge method [inline-methods] */
    public Boolean zzcC(String str) {
        if (!str.isEmpty()) {
            this.zzaid = true;
        }
        return this.zzaid;
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    /* renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public Boolean zzv(Boolean bool) {
        return this.zzaid;
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    /* renamed from: zze, reason: merged with bridge method [inline-methods] */
    public <T> Boolean zzd(MetadataField<T> metadataField, T t) {
        return this.zzaid;
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    /* renamed from: zzf, reason: merged with bridge method [inline-methods] */
    public Boolean zze(MetadataField<?> metadataField) {
        return this.zzaid;
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    /* renamed from: zzqg, reason: merged with bridge method [inline-methods] */
    public Boolean zzqf() {
        return this.zzaid;
    }

    @Override // com.google.android.gms.drive.query.internal.zzf
    /* renamed from: zzqh, reason: merged with bridge method [inline-methods] */
    public Boolean zzqe() {
        return this.zzaid;
    }
}
