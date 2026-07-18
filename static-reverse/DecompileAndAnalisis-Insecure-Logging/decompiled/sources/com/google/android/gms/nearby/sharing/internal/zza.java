package com.google.android.gms.nearby.sharing.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.nearby.sharing.AppContentReceivedResult;

/* loaded from: classes.dex */
public interface zza extends IInterface {

    /* renamed from: com.google.android.gms.nearby.sharing.internal.zza$zza, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0223zza extends Binder implements zza {

        /* renamed from: com.google.android.gms.nearby.sharing.internal.zza$zza$zza, reason: collision with other inner class name */
        private static class C0224zza implements zza {
            private IBinder zznF;

            C0224zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.nearby.sharing.internal.zza
            public void zza(AppContentReceivedResult appContentReceivedResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.sharing.internal.IAppContentReceiver");
                    if (appContentReceivedResult != null) {
                        parcelObtain.writeInt(1);
                        appContentReceivedResult.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.nearby.sharing.internal.zza
            public String zzdT(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.sharing.internal.IAppContentReceiver");
                    parcelObtain.writeString(str);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zza zzdh(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.sharing.internal.IAppContentReceiver");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zza)) ? new C0224zza(iBinder) : (zza) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.nearby.sharing.internal.IAppContentReceiver");
                    String strZzdT = zzdT(data.readString());
                    reply.writeNoException();
                    reply.writeString(strZzdT);
                    return true;
                case 2:
                    data.enforceInterface("com.google.android.gms.nearby.sharing.internal.IAppContentReceiver");
                    zza(data.readInt() != 0 ? AppContentReceivedResult.CREATOR.createFromParcel(data) : null);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.nearby.sharing.internal.IAppContentReceiver");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void zza(AppContentReceivedResult appContentReceivedResult) throws RemoteException;

    String zzdT(String str) throws RemoteException;
}
