package com.google.android.gms.nearby.sharing.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.nearby.sharing.internal.zzc;

/* loaded from: classes.dex */
public interface zzd extends IInterface {

    public static abstract class zza extends Binder implements zzd {

        /* renamed from: com.google.android.gms.nearby.sharing.internal.zzd$zza$zza, reason: collision with other inner class name */
        private static class C0227zza implements zzd {
            private IBinder zznF;

            C0227zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.nearby.sharing.internal.zzd
            public void zza(ProvideContentRequest provideContentRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    if (provideContentRequest != null) {
                        parcelObtain.writeInt(1);
                        provideContentRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.nearby.sharing.internal.zzd
            public void zza(ReceiveContentRequest receiveContentRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    if (receiveContentRequest != null) {
                        parcelObtain.writeInt(1);
                        receiveContentRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.nearby.sharing.internal.zzd
            public void zza(StopProvidingContentRequest stopProvidingContentRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    if (stopProvidingContentRequest != null) {
                        parcelObtain.writeInt(1);
                        stopProvidingContentRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.nearby.sharing.internal.zzd
            public void zza(TrustedDevicesRequest trustedDevicesRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    if (trustedDevicesRequest != null) {
                        parcelObtain.writeInt(1);
                        trustedDevicesRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.nearby.sharing.internal.zzd
            public void zza(zzc zzcVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    parcelObtain.writeStrongBinder(zzcVar != null ? zzcVar.asBinder() : null);
                    this.zznF.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static zzd zzdk(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzd)) ? new C0227zza(iBinder) : (zzd) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    zza(parcel.readInt() != 0 ? ProvideContentRequest.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    zza(parcel.readInt() != 0 ? StopProvidingContentRequest.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    zza(parcel.readInt() != 0 ? TrustedDevicesRequest.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    zza(parcel.readInt() != 0 ? ReceiveContentRequest.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    zza(zzc.zza.zzdj(parcel.readStrongBinder()));
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.nearby.sharing.internal.INearbySharingService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zza(ProvideContentRequest provideContentRequest) throws RemoteException;

    void zza(ReceiveContentRequest receiveContentRequest) throws RemoteException;

    void zza(StopProvidingContentRequest stopProvidingContentRequest) throws RemoteException;

    void zza(TrustedDevicesRequest trustedDevicesRequest) throws RemoteException;

    void zza(zzc zzcVar) throws RemoteException;
}
