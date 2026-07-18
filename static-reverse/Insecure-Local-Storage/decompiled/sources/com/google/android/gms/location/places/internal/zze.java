package com.google.android.gms.location.places.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.location.places.NearbyAlertRequest;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlaceRequest;
import com.google.android.gms.location.places.internal.zzh;

/* loaded from: classes.dex */
public interface zze extends IInterface {

    public static abstract class zza extends Binder implements zze {

        /* renamed from: com.google.android.gms.location.places.internal.zze$zza$zza, reason: collision with other inner class name */
        private static class C0171zza implements zze {
            private IBinder zznF;

            C0171zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.location.places.internal.zze
            public void zza(NearbyAlertRequest nearbyAlertRequest, PlacesParams placesParams, PendingIntent pendingIntent, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    if (nearbyAlertRequest != null) {
                        parcelObtain.writeInt(1);
                        nearbyAlertRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcelObtain.writeInt(1);
                        pendingIntent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zze
            public void zza(PlaceFilter placeFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    if (placeFilter != null) {
                        parcelObtain.writeInt(1);
                        placeFilter.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zze
            public void zza(PlaceReport placeReport, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    if (placeReport != null) {
                        parcelObtain.writeInt(1);
                        placeReport.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zze
            public void zza(PlaceRequest placeRequest, PlacesParams placesParams, PendingIntent pendingIntent, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    if (placeRequest != null) {
                        parcelObtain.writeInt(1);
                        placeRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcelObtain.writeInt(1);
                        pendingIntent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zze
            public void zza(PlacesParams placesParams, PendingIntent pendingIntent, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcelObtain.writeInt(1);
                        pendingIntent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zze
            public void zzb(PlacesParams placesParams, PendingIntent pendingIntent, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcelObtain.writeInt(1);
                        pendingIntent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zze zzbZ(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zze)) ? new C0171zza(iBinder) : (zze) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 2:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    zza(data.readInt() != 0 ? PlaceRequest.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    zza(data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    zza(data.readInt() != 0 ? NearbyAlertRequest.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    zzb(data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    zza(data.readInt() != 0 ? PlaceFilter.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    zza(data.readInt() != 0 ? PlaceReport.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void zza(NearbyAlertRequest nearbyAlertRequest, PlacesParams placesParams, PendingIntent pendingIntent, zzh zzhVar) throws RemoteException;

    void zza(PlaceFilter placeFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(PlaceReport placeReport, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(PlaceRequest placeRequest, PlacesParams placesParams, PendingIntent pendingIntent, zzh zzhVar) throws RemoteException;

    void zza(PlacesParams placesParams, PendingIntent pendingIntent, zzh zzhVar) throws RemoteException;

    void zzb(PlacesParams placesParams, PendingIntent pendingIntent, zzh zzhVar) throws RemoteException;
}
