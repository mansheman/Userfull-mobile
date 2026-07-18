package com.google.android.gms.cast.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.LaunchOptions;

/* loaded from: classes.dex */
public interface zzi extends IInterface {

    public static abstract class zza extends Binder implements zzi {

        /* renamed from: com.google.android.gms.cast.internal.zzi$zza$zza, reason: collision with other inner class name */
        private static class C0031zza implements zzi {
            private IBinder zznF;

            C0031zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void disconnect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.zznF.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zza(double d, double d2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeDouble(d);
                    parcelObtain.writeDouble(d2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.zznF.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zza(String str, LaunchOptions launchOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeString(str);
                    if (launchOptions != null) {
                        parcelObtain.writeInt(1);
                        launchOptions.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zza(String str, String str2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zza(String str, byte[] bArr, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zza(boolean z, double d, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeDouble(d);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    this.zznF.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zzbG(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeString(str);
                    this.zznF.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zzbH(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeString(str);
                    this.zznF.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zzbI(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeString(str);
                    this.zznF.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zzf(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.zznF.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zzlN() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.zznF.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zzlY() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    this.zznF.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.cast.internal.zzi
            public void zzr(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.cast.internal.ICastDeviceController");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.zznF.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static zzi zzax(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.internal.ICastDeviceController");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzi)) ? new C0031zza(iBinder) : (zzi) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    disconnect();
                    return true;
                case 2:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zzf(data.readString(), data.readInt() != 0);
                    return true;
                case 3:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zzr(data.readString(), data.readString());
                    return true;
                case 4:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zzlY();
                    return true;
                case 5:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zzbG(data.readString());
                    return true;
                case 6:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zzlN();
                    return true;
                case 7:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zza(data.readDouble(), data.readDouble(), data.readInt() != 0);
                    return true;
                case 8:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zza(data.readInt() != 0, data.readDouble(), data.readInt() != 0);
                    return true;
                case 9:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zza(data.readString(), data.readString(), data.readLong());
                    return true;
                case 10:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zza(data.readString(), data.createByteArray(), data.readLong());
                    return true;
                case 11:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zzbH(data.readString());
                    return true;
                case 12:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zzbI(data.readString());
                    return true;
                case 13:
                    data.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceController");
                    zza(data.readString(), data.readInt() != 0 ? LaunchOptions.CREATOR.createFromParcel(data) : null);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.cast.internal.ICastDeviceController");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void disconnect() throws RemoteException;

    void zza(double d, double d2, boolean z) throws RemoteException;

    void zza(String str, LaunchOptions launchOptions) throws RemoteException;

    void zza(String str, String str2, long j) throws RemoteException;

    void zza(String str, byte[] bArr, long j) throws RemoteException;

    void zza(boolean z, double d, boolean z2) throws RemoteException;

    void zzbG(String str) throws RemoteException;

    void zzbH(String str) throws RemoteException;

    void zzbI(String str) throws RemoteException;

    void zzf(String str, boolean z) throws RemoteException;

    void zzlN() throws RemoteException;

    void zzlY() throws RemoteException;

    void zzr(String str, String str2) throws RemoteException;
}
