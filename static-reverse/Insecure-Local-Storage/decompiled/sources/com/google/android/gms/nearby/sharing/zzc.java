package com.google.android.gms.nearby.sharing;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* loaded from: classes.dex */
public class zzc implements Parcelable.Creator<SharedContent> {
    static void zza(SharedContent sharedContent, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, sharedContent.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, sharedContent.getUri(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable[]) sharedContent.zzxe(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, (Parcelable[]) sharedContent.zzxf(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfA, reason: merged with bridge method [inline-methods] */
    public SharedContent createFromParcel(Parcel parcel) {
        LocalContent[] localContentArr;
        ViewableItem[] viewableItemArr;
        String strZzo;
        int iZzg;
        LocalContent[] localContentArr2 = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        int i = 0;
        ViewableItem[] viewableItemArr2 = null;
        String str = null;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    LocalContent[] localContentArr3 = localContentArr2;
                    viewableItemArr = viewableItemArr2;
                    strZzo = str;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    localContentArr = localContentArr3;
                    break;
                case 2:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    localContentArr = localContentArr2;
                    viewableItemArr = viewableItemArr2;
                    strZzo = str;
                    iZzg = i;
                    break;
                case 3:
                    iZzg = i;
                    ViewableItem[] viewableItemArr3 = viewableItemArr2;
                    strZzo = com.google.android.gms.common.internal.safeparcel.zza.zzo(parcel, iZzaa);
                    localContentArr = localContentArr2;
                    viewableItemArr = viewableItemArr3;
                    break;
                case 8:
                    strZzo = str;
                    iZzg = i;
                    LocalContent[] localContentArr4 = localContentArr2;
                    viewableItemArr = (ViewableItem[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, ViewableItem.CREATOR);
                    localContentArr = localContentArr4;
                    break;
                case 9:
                    localContentArr = (LocalContent[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa, LocalContent.CREATOR);
                    viewableItemArr = viewableItemArr2;
                    strZzo = str;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            str = strZzo;
            viewableItemArr2 = viewableItemArr;
            localContentArr2 = localContentArr;
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new SharedContent(i, str, viewableItemArr2, localContentArr2);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzip, reason: merged with bridge method [inline-methods] */
    public SharedContent[] newArray(int i) {
        return new SharedContent[i];
    }
}
