package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* loaded from: classes.dex */
public class ParticipantResultCreator implements Parcelable.Creator<ParticipantResult> {
    static void zza(ParticipantResult participantResult, Parcel parcel, int i) {
        int iZzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, participantResult.getParticipantId(), false);
        zzb.zzc(parcel, 1000, participantResult.getVersionCode());
        zzb.zzc(parcel, 2, participantResult.getResult());
        zzb.zzc(parcel, 3, participantResult.getPlacing());
        zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzdK, reason: merged with bridge method [inline-methods] */
    public ParticipantResult createFromParcel(Parcel parcel) {
        int iZzg = 0;
        int iZzab = zza.zzab(parcel);
        String strZzo = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = zza.zzaa(parcel);
            switch (zza.zzbA(iZzaa)) {
                case 1:
                    strZzo = zza.zzo(parcel, iZzaa);
                    break;
                case 2:
                    iZzg2 = zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    iZzg = zza.zzg(parcel, iZzaa);
                    break;
                case 1000:
                    iZzg3 = zza.zzg(parcel, iZzaa);
                    break;
                default:
                    zza.zzb(parcel, iZzaa);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new ParticipantResult(iZzg3, strZzo, iZzg2, iZzg);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzfT, reason: merged with bridge method [inline-methods] */
    public ParticipantResult[] newArray(int i) {
        return new ParticipantResult[i];
    }
}
