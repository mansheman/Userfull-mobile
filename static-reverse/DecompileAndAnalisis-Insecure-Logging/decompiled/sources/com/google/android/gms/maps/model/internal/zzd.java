package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* loaded from: classes.dex */
public interface zzd extends IInterface {

    public static abstract class zza extends Binder implements zzd {

        /* renamed from: com.google.android.gms.maps.model.internal.zzd$zza$zza, reason: collision with other inner class name */
        private static class C0210zza implements zzd {
            private IBinder zznF;

            C0210zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.maps.model.internal.zzd
            public com.google.android.gms.dynamic.zzd zzb(Bitmap bitmap) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    if (bitmap != null) {
                        parcelObtain.writeInt(1);
                        bitmap.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzbg(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzd
            public com.google.android.gms.dynamic.zzd zzdu(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcelObtain.writeString(str);
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzbg(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzd
            public com.google.android.gms.dynamic.zzd zzdv(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcelObtain.writeString(str);
                    this.zznF.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzbg(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzd
            public com.google.android.gms.dynamic.zzd zzdw(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcelObtain.writeString(str);
                    this.zznF.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzbg(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzd
            public com.google.android.gms.dynamic.zzd zzh(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcelObtain.writeFloat(f);
                    this.zznF.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzbg(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzd
            public com.google.android.gms.dynamic.zzd zzhD(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    parcelObtain.writeInt(i);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzbg(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.model.internal.zzd
            public com.google.android.gms.dynamic.zzd zzvN() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    this.zznF.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzbg(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zzd zzcK(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzd)) ? new C0210zza(iBinder) : (zzd) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    com.google.android.gms.dynamic.zzd zzdVarZzhD = zzhD(data.readInt());
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzhD != null ? zzdVarZzhD.asBinder() : null);
                    return true;
                case 2:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    com.google.android.gms.dynamic.zzd zzdVarZzdu = zzdu(data.readString());
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzdu != null ? zzdVarZzdu.asBinder() : null);
                    return true;
                case 3:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    com.google.android.gms.dynamic.zzd zzdVarZzdv = zzdv(data.readString());
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzdv != null ? zzdVarZzdv.asBinder() : null);
                    return true;
                case 4:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    com.google.android.gms.dynamic.zzd zzdVarZzvN = zzvN();
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzvN != null ? zzdVarZzvN.asBinder() : null);
                    return true;
                case 5:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    com.google.android.gms.dynamic.zzd zzdVarZzh = zzh(data.readFloat());
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzh != null ? zzdVarZzh.asBinder() : null);
                    return true;
                case 6:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    com.google.android.gms.dynamic.zzd zzdVarZzb = zzb(data.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzb != null ? zzdVarZzb.asBinder() : null);
                    return true;
                case 7:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    com.google.android.gms.dynamic.zzd zzdVarZzdw = zzdw(data.readString());
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzdw != null ? zzdVarZzdw.asBinder() : null);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    com.google.android.gms.dynamic.zzd zzb(Bitmap bitmap) throws RemoteException;

    com.google.android.gms.dynamic.zzd zzdu(String str) throws RemoteException;

    com.google.android.gms.dynamic.zzd zzdv(String str) throws RemoteException;

    com.google.android.gms.dynamic.zzd zzdw(String str) throws RemoteException;

    com.google.android.gms.dynamic.zzd zzh(float f) throws RemoteException;

    com.google.android.gms.dynamic.zzd zzhD(int i) throws RemoteException;

    com.google.android.gms.dynamic.zzd zzvN() throws RemoteException;
}
