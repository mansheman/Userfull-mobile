package com.google.android.gms.location.places.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.NearbyAlertRequest;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlaceRequest;
import com.google.android.gms.location.places.UserDataType;
import com.google.android.gms.location.places.internal.zzg;
import com.google.android.gms.location.places.internal.zzh;
import com.google.android.gms.location.places.personalized.PlaceAlias;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.List;

/* loaded from: classes.dex */
public interface zzf extends IInterface {

    public static abstract class zza extends Binder implements zzf {

        /* renamed from: com.google.android.gms.location.places.internal.zzf$zza$zza, reason: collision with other inner class name */
        private static class C0172zza implements zzf {
            private IBinder zznF;

            C0172zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(AddPlaceRequest addPlaceRequest, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (addPlaceRequest != null) {
                        parcelObtain.writeInt(1);
                        addPlaceRequest.writeToParcel(parcelObtain, 0);
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
                    this.zznF.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(NearbyAlertRequest nearbyAlertRequest, PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
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
                    this.zznF.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(PlaceReport placeReport, PlacesParams placesParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
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
                    this.zznF.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(PlaceRequest placeRequest, PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
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
                    this.zznF.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(UserDataType userDataType, LatLngBounds latLngBounds, List<String> list, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (userDataType != null) {
                        parcelObtain.writeInt(1);
                        userDataType.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (latLngBounds != null) {
                        parcelObtain.writeInt(1);
                        latLngBounds.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStringList(list);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
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
                    this.zznF.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(PlaceAlias placeAlias, String str, String str2, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (placeAlias != null) {
                        parcelObtain.writeInt(1);
                        placeAlias.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(LatLng latLng, PlaceFilter placeFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (latLng != null) {
                        parcelObtain.writeInt(1);
                        latLng.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
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
                    this.zznF.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(LatLngBounds latLngBounds, int i, String str, PlaceFilter placeFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (latLngBounds != null) {
                        parcelObtain.writeInt(1);
                        latLngBounds.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
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
                    this.zznF.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(String str, int i, int i2, int i3, PlacesParams placesParams, zzg zzgVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzgVar != null ? zzgVar.asBinder() : null);
                    this.zznF.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(String str, int i, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(String str, PlacesParams placesParams, zzg zzgVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    parcelObtain.writeString(str);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzgVar != null ? zzgVar.asBinder() : null);
                    this.zznF.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(String str, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    parcelObtain.writeString(str);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(String str, LatLngBounds latLngBounds, AutocompleteFilter autocompleteFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    parcelObtain.writeString(str);
                    if (latLngBounds != null) {
                        parcelObtain.writeInt(1);
                        latLngBounds.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (autocompleteFilter != null) {
                        parcelObtain.writeInt(1);
                        autocompleteFilter.writeToParcel(parcelObtain, 0);
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
                    this.zznF.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zza(List<String> list, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    parcelObtain.writeStringList(list);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zzb(PlaceFilter placeFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
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
                    this.zznF.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zzb(PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
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
                    this.zznF.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zzb(String str, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    parcelObtain.writeString(str);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.location.places.internal.zzf
            public void zzb(List<String> list, PlacesParams placesParams, zzh zzhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    parcelObtain.writeStringList(list);
                    if (placesParams != null) {
                        parcelObtain.writeInt(1);
                        placesParams.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzhVar != null ? zzhVar.asBinder() : null);
                    this.zznF.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static zzf zzca(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzf)) ? new C0172zza(iBinder) : (zzf) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 2:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(data) : null, data.readInt(), data.readString(), data.readInt() != 0 ? PlaceFilter.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 3:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readString(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 4:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlaceFilter.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 5:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zzb(data.readInt() != 0 ? PlaceFilter.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 6:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zzb(data.readString(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 7:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.createStringArrayList(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 8:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? UserDataType.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(data) : null, data.createStringArrayList(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 9:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? PlaceRequest.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null);
                    return true;
                case 10:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null);
                    return true;
                case 11:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? NearbyAlertRequest.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null);
                    return true;
                case 12:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zzb(data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null);
                    return true;
                case 13:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readString(), data.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? AutocompleteFilter.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 14:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? AddPlaceRequest.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 15:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? PlaceReport.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null);
                    return true;
                case 16:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readInt() != 0 ? PlaceAlias.CREATOR.createFromParcel(data) : null, data.readString(), data.readString(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 17:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zzb(data.createStringArrayList(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 18:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readString(), data.readInt(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzh.zza.zzcc(data.readStrongBinder()));
                    return true;
                case 19:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readString(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzg.zza.zzcb(data.readStrongBinder()));
                    return true;
                case 20:
                    data.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(data.readString(), data.readInt(), data.readInt(), data.readInt(), data.readInt() != 0 ? PlacesParams.CREATOR.createFromParcel(data) : null, zzg.zza.zzcb(data.readStrongBinder()));
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void zza(AddPlaceRequest addPlaceRequest, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(NearbyAlertRequest nearbyAlertRequest, PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException;

    void zza(PlaceReport placeReport, PlacesParams placesParams) throws RemoteException;

    void zza(PlaceRequest placeRequest, PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException;

    void zza(UserDataType userDataType, LatLngBounds latLngBounds, List<String> list, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException;

    void zza(PlaceAlias placeAlias, String str, String str2, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(LatLng latLng, PlaceFilter placeFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(LatLngBounds latLngBounds, int i, String str, PlaceFilter placeFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(String str, int i, int i2, int i3, PlacesParams placesParams, zzg zzgVar) throws RemoteException;

    void zza(String str, int i, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(String str, PlacesParams placesParams, zzg zzgVar) throws RemoteException;

    void zza(String str, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(String str, LatLngBounds latLngBounds, AutocompleteFilter autocompleteFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zza(List<String> list, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zzb(PlaceFilter placeFilter, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zzb(PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException;

    void zzb(String str, PlacesParams placesParams, zzh zzhVar) throws RemoteException;

    void zzb(List<String> list, PlacesParams placesParams, zzh zzhVar) throws RemoteException;
}
