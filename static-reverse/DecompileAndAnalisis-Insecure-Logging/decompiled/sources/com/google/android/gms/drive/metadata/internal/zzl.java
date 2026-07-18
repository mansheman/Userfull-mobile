package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes.dex */
public class zzl extends zzi<DriveId> implements SearchableCollectionMetadataField<DriveId> {
    public zzl(int i) {
        super("parents", Collections.emptySet(), Arrays.asList("parentsExtra", "dbInstanceId", "parentsExtraHolder"), i);
    }

    private void zzc(DataHolder dataHolder) {
        synchronized (dataHolder) {
            DataHolder dataHolder2 = (DataHolder) dataHolder.zznb().getParcelable("parentsExtraHolder");
            if (dataHolder2 == null) {
                return;
            }
            try {
                int count = dataHolder.getCount();
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>(count);
                HashMap map = new HashMap(count);
                for (int i = 0; i < count; i++) {
                    int iZzbh = dataHolder.zzbh(i);
                    ParentDriveIdSet parentDriveIdSet = new ParentDriveIdSet();
                    arrayList.add(parentDriveIdSet);
                    map.put(Long.valueOf(dataHolder.zzb("sqlId", i, iZzbh)), parentDriveIdSet);
                }
                Bundle bundleZznb = dataHolder2.zznb();
                String string = bundleZznb.getString("childSqlIdColumn");
                String string2 = bundleZznb.getString("parentSqlIdColumn");
                String string3 = bundleZznb.getString("parentResIdColumn");
                int count2 = dataHolder2.getCount();
                for (int i2 = 0; i2 < count2; i2++) {
                    int iZzbh2 = dataHolder2.zzbh(i2);
                    ((ParentDriveIdSet) map.get(Long.valueOf(dataHolder2.zzb(string, i2, iZzbh2)))).zza(new PartialDriveId(dataHolder2.zzd(string3, i2, iZzbh2), dataHolder2.zzb(string2, i2, iZzbh2), 1));
                }
                dataHolder.zznb().putParcelableArrayList("parentsExtra", arrayList);
            } finally {
                dataHolder2.close();
                dataHolder.zznb().remove("parentsExtraHolder");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.zzb, com.google.android.gms.drive.metadata.zza
    /* renamed from: zzd */
    public Collection<DriveId> zzc(DataHolder dataHolder, int i, int i2) {
        Bundle bundleZznb = dataHolder.zznb();
        ArrayList parcelableArrayList = bundleZznb.getParcelableArrayList("parentsExtra");
        if (parcelableArrayList == null) {
            if (bundleZznb.getParcelable("parentsExtraHolder") != null) {
                zzc(dataHolder);
                parcelableArrayList = bundleZznb.getParcelableArrayList("parentsExtra");
            }
            if (parcelableArrayList == null) {
                return null;
            }
        }
        return ((ParentDriveIdSet) parcelableArrayList.get(i)).zzC(bundleZznb.getLong("dbInstanceId"));
    }

    public void zzd(DataHolder dataHolder) {
        Bundle bundleZznb = dataHolder.zznb();
        if (bundleZznb == null) {
            return;
        }
        synchronized (dataHolder) {
            DataHolder dataHolder2 = (DataHolder) bundleZznb.getParcelable("parentsExtraHolder");
            if (dataHolder2 != null) {
                dataHolder2.close();
                bundleZznb.remove("parentsExtraHolder");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.internal.zzi, com.google.android.gms.drive.metadata.zza
    /* renamed from: zzo */
    public Collection<DriveId> zzj(Bundle bundle) {
        Collection collectionZzj = super.zzj(bundle);
        if (collectionZzj == null) {
            return null;
        }
        return new HashSet(collectionZzj);
    }
}
