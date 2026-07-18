package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Set;

/* loaded from: classes.dex */
class zze {
    static MetadataField<?> zzb(MetadataBundle metadataBundle) {
        Set<MetadataField<?>> setZzpY = metadataBundle.zzpY();
        if (setZzpY.size() != 1) {
            throw new IllegalArgumentException("bundle should have exactly 1 populated field");
        }
        return setZzpY.iterator().next();
    }
}
