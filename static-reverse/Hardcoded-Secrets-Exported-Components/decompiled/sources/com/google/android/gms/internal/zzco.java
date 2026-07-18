package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.internal.zzck;
import java.util.List;

/* loaded from: classes.dex */
public interface zzco extends IInterface {

    public static abstract class zza extends Binder implements zzco {

        /* renamed from: com.google.android.gms.internal.zzco$zza$zza, reason: collision with other inner class name */
        private static class C0077zza implements zzco {
            private IBinder zznF;

            C0077zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.internal.zzco
            public String getBody() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzco
            public List getImages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzco
            public double zzdA() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readDouble();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzco
            public String zzdB() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzco
            public String zzdC() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzco
            public com.google.android.gms.dynamic.zzd zzdD() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzd.zza.zzbg(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzco
            public String zzdx() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzco
            public zzck zzdy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzck.zza.zzt(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzco
            public String zzdz() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    this.zznF.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
        }

        public static zzco zzw(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzco)) ? new C0077zza(iBinder) : (zzco) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 2:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    com.google.android.gms.dynamic.zzd zzdVarZzdD = zzdD();
                    reply.writeNoException();
                    reply.writeStrongBinder(zzdVarZzdD != null ? zzdVarZzdD.asBinder() : null);
                    return true;
                case 3:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    String strZzdx = zzdx();
                    reply.writeNoException();
                    reply.writeString(strZzdx);
                    return true;
                case 4:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    List images = getImages();
                    reply.writeNoException();
                    reply.writeList(images);
                    return true;
                case 5:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    String body = getBody();
                    reply.writeNoException();
                    reply.writeString(body);
                    return true;
                case 6:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    zzck zzckVarZzdy = zzdy();
                    reply.writeNoException();
                    reply.writeStrongBinder(zzckVarZzdy != null ? zzckVarZzdy.asBinder() : null);
                    return true;
                case 7:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    String strZzdz = zzdz();
                    reply.writeNoException();
                    reply.writeString(strZzdz);
                    return true;
                case 8:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    double dZzdA = zzdA();
                    reply.writeNoException();
                    reply.writeDouble(dZzdA);
                    return true;
                case 9:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    String strZzdB = zzdB();
                    reply.writeNoException();
                    reply.writeString(strZzdB);
                    return true;
                case 10:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    String strZzdC = zzdC();
                    reply.writeNoException();
                    reply.writeString(strZzdC);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.ads.internal.formats.client.INativeAppInstallAd");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    String getBody() throws RemoteException;

    List getImages() throws RemoteException;

    double zzdA() throws RemoteException;

    String zzdB() throws RemoteException;

    String zzdC() throws RemoteException;

    com.google.android.gms.dynamic.zzd zzdD() throws RemoteException;

    String zzdx() throws RemoteException;

    zzck zzdy() throws RemoteException;

    String zzdz() throws RemoteException;
}
