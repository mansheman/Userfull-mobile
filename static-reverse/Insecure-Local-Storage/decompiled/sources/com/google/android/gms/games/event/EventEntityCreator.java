package com.google.android.gms.games.event;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.games.PlayerEntity;

/* loaded from: classes.dex */
public class EventEntityCreator implements Parcelable.Creator<EventEntity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(EventEntity eventEntity, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, eventEntity.getEventId(), false);
        zzb.zzc(parcel, 1000, eventEntity.getVersionCode());
        zzb.zza(parcel, 2, eventEntity.getName(), false);
        zzb.zza(parcel, 3, eventEntity.getDescription(), false);
        zzb.zza(parcel, 4, (Parcelable) eventEntity.getIconImageUri(), i, false);
        zzb.zza(parcel, 5, eventEntity.getIconImageUrl(), false);
        zzb.zza(parcel, 6, (Parcelable) eventEntity.getPlayer(), i, false);
        zzb.zza(parcel, 7, eventEntity.getValue());
        zzb.zza(parcel, 8, eventEntity.getFormattedValue(), false);
        zzb.zza(parcel, 9, eventEntity.isVisible());
        zzb.zzH(parcel, iZzac);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public EventEntity createFromParcel(Parcel parcel) {
        boolean zZzc = false;
        String strZzo = null;
        int iZzab = zza.zzab(parcel);
        long jZzi = 0;
        PlayerEntity playerEntity = null;
        String strZzo2 = null;
        Uri uri = null;
        String strZzo3 = null;
        String strZzo4 = null;
        String strZzo5 = null;
        int iZzg = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo5 = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    strZzo4 = zza.zzo(parcel, iZzaa);
                    break;
                case 3:
                    strZzo3 = zza.zzo(parcel, iZzaa);
                    break;
                case 4:
                    uri = (Uri) zza.zza(parcel, iZzaa, Uri.CREATOR);
                    break;
                case 5:
                    strZzo2 = zza.zzo(parcel, iZzaa);
                    break;
                case 6:
                    playerEntity = (PlayerEntity) zza.zza(parcel, iZzaa, PlayerEntity.CREATOR);
                    break;
                case 7:
                    jZzi = zza.zzi(parcel, iZzaa);
                    break;
                case 8:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 9:
                    zZzc = zza.zzc(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new EventEntity(iZzg, strZzo5, strZzo4, strZzo3, uri, strZzo2, playerEntity, jZzi, strZzo, zZzc);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public EventEntity[] newArray(int size) {
        return new EventEntity[size];
    }
}
