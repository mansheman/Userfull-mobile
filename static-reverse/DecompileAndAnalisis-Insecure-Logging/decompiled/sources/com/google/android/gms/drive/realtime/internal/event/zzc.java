package com.google.android.gms.drive.realtime.internal.event;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<ParcelableEvent> {
    static void zza(ParcelableEvent parcelableEvent, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, parcelableEvent.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, parcelableEvent.zzFE, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, parcelableEvent.zzEO, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 4, parcelableEvent.zzaiC, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, parcelableEvent.zzaiD);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, parcelableEvent.zzaiw, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, parcelableEvent.zzaiG, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable) parcelableEvent.zzaiH, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, (Parcelable) parcelableEvent.zzaiI, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, (Parcelable) parcelableEvent.zzaiJ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, (Parcelable) parcelableEvent.zzaiK, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, (Parcelable) parcelableEvent.zzaiL, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, (Parcelable) parcelableEvent.zzaiM, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, (Parcelable) parcelableEvent.zzaiN, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, (Parcelable) parcelableEvent.zzaiO, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, parcelableEvent.zzaiF);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, parcelableEvent.zzaiE);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 18, (Parcelable) parcelableEvent.zzaiP, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbZ, reason: merged with bridge method [inline-methods] */
    public ParcelableEvent createFromParcel(Parcel parcel) {
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int iZzg = 0;
        String strZzo = null;
        String strZzo2 = null;
        ArrayList<String> arrayListZzC = null;
        boolean zZzc = false;
        boolean zZzc2 = false;
        boolean zZzc3 = false;
        String strZzo3 = null;
        String strZzo4 = null;
        TextInsertedDetails textInsertedDetails = null;
        TextDeletedDetails textDeletedDetails = null;
        ValuesAddedDetails valuesAddedDetails = null;
        ValuesRemovedDetails valuesRemovedDetails = null;
        ValuesSetDetails valuesSetDetails = null;
        ValueChangedDetails valueChangedDetails = null;
        ReferenceShiftedDetails referenceShiftedDetails = null;
        ObjectChangedDetails objectChangedDetails = null;
        FieldChangedDetails fieldChangedDetails = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    arrayListZzC = com.google.android.gms.common.internal.safeparcel.zza.zzC(parcel, iZzaa);
                    break;
                case 5:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 6:
                    strZzo3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 7:
                    strZzo4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 8:
                    textInsertedDetails = (TextInsertedDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, TextInsertedDetails.CREATOR);
                    break;
                case 9:
                    textDeletedDetails = (TextDeletedDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, TextDeletedDetails.CREATOR);
                    break;
                case 10:
                    valuesAddedDetails = (ValuesAddedDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ValuesAddedDetails.CREATOR);
                    break;
                case 11:
                    valuesRemovedDetails = (ValuesRemovedDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ValuesRemovedDetails.CREATOR);
                    break;
                case 12:
                    valuesSetDetails = (ValuesSetDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ValuesSetDetails.CREATOR);
                    break;
                case 13:
                    valueChangedDetails = (ValueChangedDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ValueChangedDetails.CREATOR);
                    break;
                case 14:
                    referenceShiftedDetails = (ReferenceShiftedDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ReferenceShiftedDetails.CREATOR);
                    break;
                case 15:
                    objectChangedDetails = (ObjectChangedDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ObjectChangedDetails.CREATOR);
                    break;
                case 16:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 17:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 18:
                    fieldChangedDetails = (FieldChangedDetails) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, FieldChangedDetails.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ParcelableEvent(iZzg, strZzo, strZzo2, arrayListZzC, zZzc, zZzc2, zZzc3, strZzo3, strZzo4, textInsertedDetails, textDeletedDetails, valuesAddedDetails, valuesRemovedDetails, valuesSetDetails, valueChangedDetails, referenceShiftedDetails, objectChangedDetails, fieldChangedDetails);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdP, reason: merged with bridge method [inline-methods] */
    public ParcelableEvent[] newArray(int i) {
        return new ParcelableEvent[i];
    }
}
