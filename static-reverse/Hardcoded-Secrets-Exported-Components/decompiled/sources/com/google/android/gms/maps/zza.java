package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.maps.model.CameraPosition;

/* loaded from: classes.dex */
public class zza implements Parcelable.Creator<GoogleMapOptions> {
    static void zza(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, googleMapOptions.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, googleMapOptions.zzvj());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, googleMapOptions.zzvk());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, googleMapOptions.getMapType());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) googleMapOptions.getCamera(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, googleMapOptions.zzvl());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, googleMapOptions.zzvm());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, googleMapOptions.zzvn());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, googleMapOptions.zzvo());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, googleMapOptions.zzvp());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, googleMapOptions.zzvq());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, googleMapOptions.zzvr());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, googleMapOptions.zzvs());
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzeI, reason: merged with bridge method [inline-methods] */
    public GoogleMapOptions createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        byte bZze = -1;
        byte bZze2 = -1;
        int iZzg2 = 0;
        CameraPosition cameraPosition = null;
        byte bZze3 = -1;
        byte bZze4 = -1;
        byte bZze5 = -1;
        byte bZze6 = -1;
        byte bZze7 = -1;
        byte bZze8 = -1;
        byte bZze9 = -1;
        byte bZze10 = -1;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    bZze = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 3:
                    bZze2 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 5:
                    cameraPosition = (CameraPosition) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, CameraPosition.CREATOR);
                    break;
                case 6:
                    bZze3 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 7:
                    bZze4 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 8:
                    bZze5 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 9:
                    bZze6 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 10:
                    bZze7 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 11:
                    bZze8 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 12:
                    bZze9 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
                case 13:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
                case 14:
                    bZze10 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new GoogleMapOptions(iZzg, bZze, bZze2, iZzg2, cameraPosition, bZze3, bZze4, bZze5, bZze6, bZze7, bZze8, bZze9, bZze10);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzhi, reason: merged with bridge method [inline-methods] */
    public GoogleMapOptions[] newArray(int i) {
        return new GoogleMapOptions[i];
    }
}
