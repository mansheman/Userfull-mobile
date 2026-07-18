package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangesAvailableEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.ProgressEvent;
import com.google.android.gms.drive.events.QueryResultEventParcelable;

/* loaded from: classes.dex */
public class zzaz implements Parcelable.Creator<OnEventResponse> {
    static void zza(OnEventResponse onEventResponse, Parcel parcel, int i) {
        int iZzac = com.google.android.gms.common.internal.safeparcel.zzb.zzac(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, onEventResponse.zzCY);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, onEventResponse.zzaca);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) onEventResponse.zzagj, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, (Parcelable) onEventResponse.zzagk, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) onEventResponse.zzagl, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable) onEventResponse.zzagm, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable) onEventResponse.zzagn, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzH(parcel, iZzac);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzbe, reason: merged with bridge method [inline-methods] */
    public OnEventResponse createFromParcel(Parcel parcel) {
        int iZzg = 0;
        ProgressEvent progressEvent = null;
        int iZzab = com.google.android.gms.common.internal.safeparcel.zza.zzab(parcel);
        ChangesAvailableEvent changesAvailableEvent = null;
        QueryResultEventParcelable queryResultEventParcelable = null;
        CompletionEvent completionEvent = null;
        ChangeEvent changeEvent = null;
        int iZzg2 = 0;
        while (parcel.dataPosition() < iZzab) {
            int iZzaa = com.google.android.gms.common.internal.safeparcel.zza.zzaa(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(iZzaa)) {
                case 1:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 2:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzaa);
                    break;
                case 3:
                    changeEvent = (ChangeEvent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ChangeEvent.CREATOR);
                    break;
                case 4:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzaa);
                    break;
                case 5:
                    completionEvent = (CompletionEvent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, CompletionEvent.CREATOR);
                    break;
                case 6:
                    queryResultEventParcelable = (QueryResultEventParcelable) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, QueryResultEventParcelable.CREATOR);
                    break;
                case 7:
                    changesAvailableEvent = (ChangesAvailableEvent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ChangesAvailableEvent.CREATOR);
                    break;
                case 8:
                    progressEvent = (ProgressEvent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzaa, ProgressEvent.CREATOR);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzab) {
            throw new zza.C0036zza("Overread allowed size end=" + iZzab, parcel);
        }
        return new OnEventResponse(iZzg2, iZzg, changeEvent, completionEvent, queryResultEventParcelable, changesAvailableEvent, progressEvent);
    }

    @Override // android.os.Parcelable.Creator
    /* renamed from: zzcT, reason: merged with bridge method [inline-methods] */
    public OnEventResponse[] newArray(int i) {
        return new OnEventResponse[i];
    }
}
