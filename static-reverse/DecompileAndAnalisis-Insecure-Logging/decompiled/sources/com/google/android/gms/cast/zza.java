package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<ApplicationMetadata> {
    static void zza(ApplicationMetadata applicationMetadata, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, applicationMetadata.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, applicationMetadata.getApplicationId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, applicationMetadata.getName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, applicationMetadata.getImages(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 5, applicationMetadata.zzQw, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, applicationMetadata.getSenderAppIdentifier(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable) applicationMetadata.zzle(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzJ, reason: merged with bridge method [inline-methods] */
    public ApplicationMetadata createFromParcel(Parcel parcel) {
        Uri uri = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        ArrayList<String> arrayListZzC = null;
        ArrayList arrayListZzc = null;
        String strZzo2 = null;
        String strZzo3 = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa, WebImage.CREATOR);
                    break;
                case 5:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 6:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ApplicationMetadata(iZzg, strZzo3, strZzo2, arrayListZzc, arrayListZzC, strZzo, uri);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzaz, reason: merged with bridge method [inline-methods] */
    public ApplicationMetadata[] newArray(int i) {
        return new ApplicationMetadata[i];
    }
}
