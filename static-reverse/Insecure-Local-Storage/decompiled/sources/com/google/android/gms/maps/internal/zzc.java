package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.model.internal.zzd;

/* loaded from: classes.dex */
public interface zzc extends IInterface {

    public static abstract class zza extends Binder implements zzc {

        /* renamed from: com.google.android.gms.maps.internal.zzc$zza$zza, reason: collision with other inner class name */
        private static class C0188zza implements zzc {
            private IBinder zznF;

            C0188zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.maps.internal.zzc
            public IMapViewDelegate zza(com.google.android.gms.dynamic.zzd zzdVar, GoogleMapOptions googleMapOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    parcelObtain.writeStrongBinder(zzdVar != null ? zzdVar.asBinder() : null);
                    if (googleMapOptions != null) {
                        parcelObtain.writeInt(1);
                        googleMapOptions.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IMapViewDelegate.zza.zzcm(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.zzc
            public IStreetViewPanoramaViewDelegate zza(com.google.android.gms.dynamic.zzd zzdVar, StreetViewPanoramaOptions streetViewPanoramaOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    parcelObtain.writeStrongBinder(zzdVar != null ? zzdVar.asBinder() : null);
                    if (streetViewPanoramaOptions != null) {
                        parcelObtain.writeInt(1);
                        streetViewPanoramaOptions.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IStreetViewPanoramaViewDelegate.zza.zzcI(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.zzc
            public void zzd(com.google.android.gms.dynamic.zzd zzdVar, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    parcelObtain.writeStrongBinder(zzdVar != null ? zzdVar.asBinder() : null);
                    parcelObtain.writeInt(i);
                    this.zznF.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.zzc
            public void zzq(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    parcelObtain.writeStrongBinder(zzdVar != null ? zzdVar.asBinder() : null);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.zzc
            public IMapFragmentDelegate zzr(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    parcelObtain.writeStrongBinder(zzdVar != null ? zzdVar.asBinder() : null);
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IMapFragmentDelegate.zza.zzcl(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.zzc
            public IStreetViewPanoramaFragmentDelegate zzs(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    parcelObtain.writeStrongBinder(zzdVar != null ? zzdVar.asBinder() : null);
                    this.zznF.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IStreetViewPanoramaFragmentDelegate.zza.zzcH(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.zzc
            public ICameraUpdateFactoryDelegate zzvC() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    this.zznF.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICameraUpdateFactoryDelegate.zza.zzce(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.zzc
            public com.google.android.gms.maps.model.internal.zzd zzvD() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    this.zznF.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzcK(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zzc zzcg(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzc)) ? new C0188zza(iBinder) : (zzc) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    zzq(zzd.zza.zzbg(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    IMapFragmentDelegate iMapFragmentDelegateZzr = zzr(zzd.zza.zzbg(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeStrongBinder(iMapFragmentDelegateZzr != null ? iMapFragmentDelegateZzr.asBinder() : null);
                    return true;
                case 3:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    IMapViewDelegate iMapViewDelegateZza = zza(zzd.zza.zzbg(data.readStrongBinder()), data.readInt() != 0 ? GoogleMapOptions.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    reply.writeStrongBinder(iMapViewDelegateZza != null ? iMapViewDelegateZza.asBinder() : null);
                    return true;
                case 4:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegateZzvC = zzvC();
                    reply.writeNoException();
                    reply.writeStrongBinder(iCameraUpdateFactoryDelegateZzvC != null ? iCameraUpdateFactoryDelegateZzvC.asBinder() : null);
                    return true;
                case 5:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    com.google.android.gms.maps.model.internal.zzd zzdVarZzvD = zzvD();
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzvD != null ? zzdVarZzvD.asBinder() : null);
                    return true;
                case 6:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    zzd(zzd.zza.zzbg(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegateZza = zza(zzd.zza.zzbg(data.readStrongBinder()), data.readInt() != 0 ? StreetViewPanoramaOptions.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    reply.writeStrongBinder(iStreetViewPanoramaViewDelegateZza != null ? iStreetViewPanoramaViewDelegateZza.asBinder() : null);
                    return true;
                case 8:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegateZzs = zzs(zzd.zza.zzbg(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeStrongBinder(iStreetViewPanoramaFragmentDelegateZzs != null ? iStreetViewPanoramaFragmentDelegateZzs.asBinder() : null);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.ICreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IMapViewDelegate zza(com.google.android.gms.dynamic.zzd zzdVar, GoogleMapOptions googleMapOptions) throws RemoteException;

    IStreetViewPanoramaViewDelegate zza(com.google.android.gms.dynamic.zzd zzdVar, StreetViewPanoramaOptions streetViewPanoramaOptions) throws RemoteException;

    void zzd(com.google.android.gms.dynamic.zzd zzdVar, int i) throws RemoteException;

    void zzq(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException;

    IMapFragmentDelegate zzr(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException;

    IStreetViewPanoramaFragmentDelegate zzs(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException;

    ICameraUpdateFactoryDelegate zzvC() throws RemoteException;

    com.google.android.gms.maps.model.internal.zzd zzvD() throws RemoteException;
}
