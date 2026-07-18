package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzor;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.nearby.connection.AppMetadata;

/* loaded from: classes.dex */
public interface zzos extends IInterface {

    public static abstract class zza extends Binder implements zzos {

        /* renamed from: com.google.android.gms.internal.zzos$zza$zza, reason: collision with other inner class name */
        private static class C0149zza implements zzos {
            private IBinder zznF;

            C0149zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.internal.zzos
            public void zzE(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_POST_BOX, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zzN(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeLong(j);
                    this.zznF.transact(1002, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zzO(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_FLOOR, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zzP(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_POINT_OF_INTEREST, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public String zzQ(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_POSTAL_CODE, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zza(zzor zzorVar, int i, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeStrongBinder(zzorVar != null ? zzorVar.asBinder() : null);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.zznF.transact(Place.TYPE_COUNTRY, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zza(zzor zzorVar, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeStrongBinder(zzorVar != null ? zzorVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_LOCALITY, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zza(zzor zzorVar, String str, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeStrongBinder(zzorVar != null ? zzorVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.zznF.transact(Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zza(zzor zzorVar, String str, AppMetadata appMetadata, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeStrongBinder(zzorVar != null ? zzorVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    if (appMetadata != null) {
                        parcelObtain.writeInt(1);
                        appMetadata.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.zznF.transact(1001, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zza(zzor zzorVar, String str, String str2, byte[] bArr, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeStrongBinder(zzorVar != null ? zzorVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_GEOCODE, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zza(zzor zzorVar, String str, byte[] bArr, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeStrongBinder(zzorVar != null ? zzorVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_INTERSECTION, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zza(String[] strArr, byte[] bArr, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_NATURAL_FEATURE, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zzb(String[] strArr, byte[] bArr, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_NEIGHBORHOOD, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zzf(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_COLLOQUIAL_AREA, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public void zzg(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.zznF.transact(Place.TYPE_POLITICAL, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zzos
            public String zzwS() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    this.zznF.transact(Place.TYPE_POSTAL_CODE_PREFIX, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zzos zzdc(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzos)) ? new C0149zza(iBinder) : (zzos) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1001:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zza(zzor.zza.zzdb(data.readStrongBinder()), data.readString(), data.readInt() != 0 ? AppMetadata.CREATOR.createFromParcel(data) : null, data.readLong(), data.readLong());
                    reply.writeNoException();
                    return true;
                case 1002:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zzN(data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_3 /* 1003 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zza(zzor.zza.zzdb(data.readStrongBinder()), data.readString(), data.readLong(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_COLLOQUIAL_AREA /* 1004 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zzf(data.readString(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_COUNTRY /* 1005 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zza(zzor.zza.zzdb(data.readStrongBinder()), data.readInt(), data.readLong(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_FLOOR /* 1006 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zzO(data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_GEOCODE /* 1007 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zza(zzor.zza.zzdb(data.readStrongBinder()), data.readString(), data.readString(), data.createByteArray(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_INTERSECTION /* 1008 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zza(zzor.zza.zzdb(data.readStrongBinder()), data.readString(), data.createByteArray(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_LOCALITY /* 1009 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zza(zzor.zza.zzdb(data.readStrongBinder()), data.readString(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_NATURAL_FEATURE /* 1010 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zza(data.createStringArray(), data.createByteArray(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_NEIGHBORHOOD /* 1011 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zzb(data.createStringArray(), data.createByteArray(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_POLITICAL /* 1012 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zzg(data.readString(), data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_POINT_OF_INTEREST /* 1013 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zzP(data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_POST_BOX /* 1014 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    zzE(data.readLong());
                    reply.writeNoException();
                    return true;
                case Place.TYPE_POSTAL_CODE /* 1015 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    String strZzQ = zzQ(data.readLong());
                    reply.writeNoException();
                    reply.writeString(strZzQ);
                    return true;
                case Place.TYPE_POSTAL_CODE_PREFIX /* 1016 */:
                    data.enforceInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    String strZzwS = zzwS();
                    reply.writeNoException();
                    reply.writeString(strZzwS);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void zzE(long j) throws RemoteException;

    void zzN(long j) throws RemoteException;

    void zzO(long j) throws RemoteException;

    void zzP(long j) throws RemoteException;

    String zzQ(long j) throws RemoteException;

    void zza(zzor zzorVar, int i, long j, long j2) throws RemoteException;

    void zza(zzor zzorVar, String str, long j) throws RemoteException;

    void zza(zzor zzorVar, String str, long j, long j2) throws RemoteException;

    void zza(zzor zzorVar, String str, AppMetadata appMetadata, long j, long j2) throws RemoteException;

    void zza(zzor zzorVar, String str, String str2, byte[] bArr, long j) throws RemoteException;

    void zza(zzor zzorVar, String str, byte[] bArr, long j) throws RemoteException;

    void zza(String[] strArr, byte[] bArr, long j) throws RemoteException;

    void zzb(String[] strArr, byte[] bArr, long j) throws RemoteException;

    void zzf(String str, long j) throws RemoteException;

    void zzg(String str, long j) throws RemoteException;

    String zzwS() throws RemoteException;
}
