package com.google.android.gms.auth.api.credentials.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks;

/* loaded from: classes.dex */
public interface ICredentialsService extends IInterface {

    public static abstract class zza extends Binder implements ICredentialsService {

        /* renamed from: com.google.android.gms.auth.api.credentials.internal.ICredentialsService$zza$zza, reason: collision with other inner class name */
        private static class C0028zza implements ICredentialsService {
            private IBinder zznF;

            C0028zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.auth.api.credentials.internal.ICredentialsService
            public void performCredentialsDeleteOperation(ICredentialsCallbacks callbacks, DeleteRequest request) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    parcelObtain.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (request != null) {
                        parcelObtain.writeInt(1);
                        request.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.auth.api.credentials.internal.ICredentialsService
            public void performCredentialsRequestOperation(ICredentialsCallbacks callbacks, CredentialRequest request) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    parcelObtain.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (request != null) {
                        parcelObtain.writeInt(1);
                        request.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.auth.api.credentials.internal.ICredentialsService
            public void performCredentialsSaveOperation(ICredentialsCallbacks callbacks, SaveRequest request) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    parcelObtain.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    if (request != null) {
                        parcelObtain.writeInt(1);
                        request.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.auth.api.credentials.internal.ICredentialsService
            public void performDisableAutoSignInOperation(ICredentialsCallbacks callbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    parcelObtain.writeStrongBinder(callbacks != null ? callbacks.asBinder() : null);
                    this.zznF.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static ICredentialsService zzaq(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICredentialsService)) ? new C0028zza(iBinder) : (ICredentialsService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    performCredentialsRequestOperation(ICredentialsCallbacks.zza.zzap(parcel.readStrongBinder()), parcel.readInt() != 0 ? CredentialRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    performCredentialsSaveOperation(ICredentialsCallbacks.zza.zzap(parcel.readStrongBinder()), parcel.readInt() != 0 ? SaveRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    performCredentialsDeleteOperation(ICredentialsCallbacks.zza.zzap(parcel.readStrongBinder()), parcel.readInt() != 0 ? DeleteRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    performDisableAutoSignInOperation(ICredentialsCallbacks.zza.zzap(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void performCredentialsDeleteOperation(ICredentialsCallbacks iCredentialsCallbacks, DeleteRequest deleteRequest) throws RemoteException;

    void performCredentialsRequestOperation(ICredentialsCallbacks iCredentialsCallbacks, CredentialRequest credentialRequest) throws RemoteException;

    void performCredentialsSaveOperation(ICredentialsCallbacks iCredentialsCallbacks, SaveRequest saveRequest) throws RemoteException;

    void performDisableAutoSignInOperation(ICredentialsCallbacks iCredentialsCallbacks) throws RemoteException;
}
