package com.google.android.gms.ads.internal.client;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface zzu extends IInterface {

    public static abstract class zza extends Binder implements zzu {

        /* renamed from: com.google.android.gms.ads.internal.client.zzu$zza$zza, reason: collision with other inner class name */
        private static class C0010zza implements zzu {
            private IBinder zznF;

            C0010zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.ads.internal.client.zzu
            public long getValue() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.ICorrelationIdProvider");
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.ads.internal.client.ICorrelationIdProvider");
        }

        public static zzu zzn(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.ICorrelationIdProvider");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzu)) ? new C0010zza(iBinder) : (zzu) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.ads.internal.client.ICorrelationIdProvider");
                    long value = getValue();
                    reply.writeNoException();
                    reply.writeLong(value);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.ads.internal.client.ICorrelationIdProvider");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    long getValue() throws RemoteException;
}
