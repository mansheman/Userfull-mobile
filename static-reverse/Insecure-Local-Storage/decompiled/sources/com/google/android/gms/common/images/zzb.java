package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<WebImage> {
    static void zza(WebImage webImage, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, webImage.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) webImage.getUrl(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, webImage.getWidth());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, webImage.getHeight());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzT, reason: merged with bridge method [inline-methods] */
    public WebImage createFromParcel(Parcel parcel) {
        int iZzg;
        int iZzg2;
        Uri uri;
        int iZzg3;
        int i = 0;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        Uri uri2 = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    int i4 = i;
                    iZzg2 = i2;
                    uri = uri2;
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i4;
                    break;
                case 2:
                    iZzg3 = i3;
                    int i5 = i2;
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Uri.CREATOR);
                    iZzg = i;
                    iZzg2 = i5;
                    break;
                case 3:
                    uri = uri2;
                    iZzg3 = i3;
                    int i6 = i;
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg = i6;
                    break;
                case 4:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    iZzg2 = i2;
                    uri = uri2;
                    iZzg3 = i3;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    iZzg = i;
                    iZzg2 = i2;
                    uri = uri2;
                    iZzg3 = i3;
                    break;
            }
            i3 = iZzg3;
            uri2 = uri;
            i2 = iZzg2;
            i = iZzg;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new WebImage(i3, uri2, i2, i);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbn, reason: merged with bridge method [inline-methods] */
    public WebImage[] newArray(int i) {
        return new WebImage[i];
    }
}
