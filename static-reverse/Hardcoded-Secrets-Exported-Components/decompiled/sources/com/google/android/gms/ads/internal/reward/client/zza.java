package com.google.android.gms.ads.internal.reward.client;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface zza extends IInterface {

    /* renamed from: com.google.android.gms.ads.internal.reward.client.zza$zza, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0018zza extends Binder implements zza {

        /* renamed from: com.google.android.gms.ads.internal.reward.client.zza$zza$zza, reason: collision with other inner class name */
        private static class C0019zza implements zza {
            private IBinder zznF;

            C0019zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.ads.internal.reward.client.zza
            public int getAmount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.reward.client.IRewardItem");
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.ads.internal.reward.client.zza
            public String getType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.reward.client.IRewardItem");
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public AbstractBinderC0018zza() {
            attachInterface(this, "com.google.android.gms.ads.internal.reward.client.IRewardItem");
        }

        public static zza zzW(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.reward.client.IRewardItem");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zza)) ? new C0019zza(iBinder) : (zza) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.ads.internal.reward.client.IRewardItem");
                    String type = getType();
                    reply.writeNoException();
                    reply.writeString(type);
                    return true;
                case 2:
                    data.enforceInterface("com.google.android.gms.ads.internal.reward.client.IRewardItem");
                    int amount = getAmount();
                    reply.writeNoException();
                    reply.writeInt(amount);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.ads.internal.reward.client.IRewardItem");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    int getAmount() throws RemoteException;

    String getType() throws RemoteException;
}
