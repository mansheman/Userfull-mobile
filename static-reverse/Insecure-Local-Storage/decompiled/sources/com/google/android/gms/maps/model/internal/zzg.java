package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface zzg extends IInterface {

    public static abstract class zza extends Binder implements zzg {

        /* renamed from: com.google.android.gms.maps.model.internal.zzg$zza$zza, reason: collision with other inner class name */
        private static class C0213zza implements zzg {
            private IBinder zznF;

            C0213zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.maps.model.internal.zzg
            public int getActiveLevelIndex() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzg
            public int getDefaultLevelIndex() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzg
            public List<IBinder> getLevels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.zznF.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createBinderArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzg
            public int hashCodeRemote() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.zznF.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzg
            public boolean isUnderground() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    this.zznF.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzg
            public boolean zzb(zzg zzgVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    parcelObtain.writeStrongBinder(zzgVar != null ? zzgVar.asBinder() : null);
                    this.zznF.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zzg zzcN(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzg)) ? new C0213zza(iBinder) : (zzg) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    int activeLevelIndex = getActiveLevelIndex();
                    reply.writeNoException();
                    reply.writeInt(activeLevelIndex);
                    return true;
                case 2:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    int defaultLevelIndex = getDefaultLevelIndex();
                    reply.writeNoException();
                    reply.writeInt(defaultLevelIndex);
                    return true;
                case 3:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    List<IBinder> levels = getLevels();
                    reply.writeNoException();
                    reply.writeBinderList(levels);
                    return true;
                case 4:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    boolean zIsUnderground = isUnderground();
                    reply.writeNoException();
                    reply.writeInt(zIsUnderground ? 1 : 0);
                    return true;
                case 5:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    boolean zZzb = zzb(zzcN(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeInt(zZzb ? 1 : 0);
                    return true;
                case 6:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    int iHashCodeRemote = hashCodeRemote();
                    reply.writeNoException();
                    reply.writeInt(iHashCodeRemote);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    int getActiveLevelIndex() throws RemoteException;

    int getDefaultLevelIndex() throws RemoteException;

    List<IBinder> getLevels() throws RemoteException;

    int hashCodeRemote() throws RemoteException;

    boolean isUnderground() throws RemoteException;

    boolean zzb(zzg zzgVar) throws RemoteException;
}
