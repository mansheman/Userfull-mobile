package com.google.android.gms.location.places.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzk implements Parcelable.Creator<PlaceImpl> {
    static void zza(PlaceImpl placeImpl, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, placeImpl.getId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, placeImpl.zzuT(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) placeImpl.zzuV(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) placeImpl.getLatLng(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, placeImpl.zzuO());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) placeImpl.getViewport(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, placeImpl.zzuU(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable) placeImpl.getWebsiteUri(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, placeImpl.zzuR());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, placeImpl.getRating());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 11, placeImpl.getPriceLevel());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, placeImpl.zzuS());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, placeImpl.zzuN(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, placeImpl.getAddress(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, placeImpl.getPhoneNumber(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 17, placeImpl.zzuQ(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, placeImpl.zzuP(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, placeImpl.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 19, placeImpl.getName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 18, placeImpl.zzaAE);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 20, placeImpl.getPlaceTypes(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzey, reason: merged with bridge method [inline-methods] */
    public PlaceImpl createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        ArrayList<Integer> arrayListZzB = null;
        ArrayList<Integer> arrayListZzB2 = null;
        Bundle bundleZzq = null;
        String strZzo2 = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        ArrayList<String> arrayListZzC = null;
        LatLng latLng = null;
        float fZzl = 0.0f;
        LatLngBounds latLngBounds = null;
        String strZzo6 = null;
        Uri uri = null;
        boolean zZzc = false;
        float fZzl2 = 0.0f;
        int iZzg2 = 0;
        long jZzi = 0;
        boolean zZzc2 = false;
        PlaceLocalization placeLocalization = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    bundleZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzaa);
                    break;
                case 3:
                    placeLocalization = (PlaceLocalization) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, PlaceLocalization.CREATOR);
                    break;
                case 4:
                    latLng = (LatLng) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LatLng.CREATOR);
                    break;
                case 5:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 6:
                    latLngBounds = (LatLngBounds) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, LatLngBounds.CREATOR);
                    break;
                case 7:
                    strZzo6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 9:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 10:
                    fZzl2 = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzaa);
                    break;
                case 11:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 12:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzaa);
                    break;
                case 13:
                    arrayListZzB2 = com.google.android.gms.common.internal.safeparcel.zza.zzB(parcel, iZzaa);
                    break;
                case 14:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 15:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 16:
                    strZzo5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 17:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 18:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 19:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 20:
                    arrayListZzB = com.google.android.gms.common.internal.safeparcel.zza.zzB(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new PlaceImpl(iZzg, strZzo, arrayListZzB, arrayListZzB2, bundleZzq, strZzo2, strZzo3, strZzo4, strZzo5, arrayListZzC, latLng, fZzl, latLngBounds, strZzo6, uri, zZzc, fZzl2, iZzg2, jZzi, zZzc2, placeLocalization);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzgY, reason: merged with bridge method [inline-methods] */
    public PlaceImpl[] newArray(int i) {
        return new PlaceImpl[i];
    }
}
