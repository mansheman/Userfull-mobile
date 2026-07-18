package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAccountAccessor extends IInterface {

    public static abstract class zza extends Binder implements IAccountAccessor {

        /* renamed from: com.google.android.gms.common.internal.IAccountAccessor$zza$zza, reason: collision with other inner class name */
        private static class C0034zza implements IAccountAccessor {
            private IBinder zznF;

            C0034zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.common.internal.IAccountAccessor
            public Account getAccount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IAccountAccessor");
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static IAccountAccessor zzaD(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAccountAccessor)) ? new C0034zza(iBinder) : (IAccountAccessor) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 2:
                    data.enforceInterface("com.google.android.gms.common.internal.IAccountAccessor");
                    Account account = getAccount();
                    reply.writeNoException();
                    if (account == null) {
                        reply.writeInt(0);
                        return true;
                    }
                    reply.writeInt(1);
                    account.writeToParcel(reply, 1);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.common.internal.IAccountAccessor");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    Account getAccount() throws RemoteException;
}
