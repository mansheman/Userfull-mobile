package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzjh;

/* loaded from: classes.dex */
public interface zzji extends IInterface {

    public static abstract class zza extends Binder implements zzji {

        /* renamed from: com.google.android.gms.internal.zzji$zza$zza, reason: collision with other inner class name */
        private static class C0109zza implements zzji {
            private IBinder zznF;

            C0109zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.internal.zzji
            public void zza(Account account, int i, zzjh zzjhVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.auth.api.accountstatus.internal.IAccountStatusService");
                    if (account != null) {
                        parcelObtain.writeInt(1);
                        account.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(zzjhVar != null ? zzjhVar.asBinder() : null);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zzji zzao(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.accountstatus.internal.IAccountStatusService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzji)) ? new C0109zza(iBinder) : (zzji) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.auth.api.accountstatus.internal.IAccountStatusService");
                    zza(data.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(data) : null, data.readInt(), zzjh.zza.zzan(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.auth.api.accountstatus.internal.IAccountStatusService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void zza(Account account, int i, zzjh zzjhVar) throws RemoteException;
}
