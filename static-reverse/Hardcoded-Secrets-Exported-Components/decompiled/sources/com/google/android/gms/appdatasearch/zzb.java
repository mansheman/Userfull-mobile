package com.google.android.gms.appdatasearch;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzb implements Parcelable.Creator<DocumentContents> {
    static void zza(DocumentContents documentContents, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 1, (Parcelable[]) documentContents.zzMS, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1000, documentContents.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, documentContents.zzMT, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, documentContents.zzMU);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) documentContents.account, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzac, reason: merged with bridge method [inline-methods] */
    public DocumentContents[] newArray(int i) {
        return new DocumentContents[i];
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzr, reason: merged with bridge method [inline-methods] */
    public DocumentContents createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        Account account = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        String strZzo = null;
        DocumentSection[] documentSectionArr = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    documentSectionArr = (DocumentSection[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, DocumentSection.CREATOR);
                    break;
                case 2:
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzaa);
                    break;
                case 4:
                    account = (Account) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, Account.CREATOR);
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
        return new DocumentContents(iZzg, documentSectionArr, strZzo, zZzc, account);
    }
}
