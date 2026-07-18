package com.google.android.gms.wearable.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import java.util.List;

/* loaded from: classes.dex */
public interface zzas extends IInterface {

    public static abstract class zza extends Binder implements zzas {

        /* renamed from: com.google.android.gms.wearable.internal.zzas$zza$zza, reason: collision with other inner class name */
        private static class C0240zza implements zzas {
            private IBinder zznF;

            C0240zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.wearable.internal.zzas
            public void onConnectedNodes(List<NodeParcelable> connectedNodes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    parcelObtain.writeTypedList(connectedNodes);
                    this.zznF.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.wearable.internal.zzas
            public void zza(AncsNotificationParcelable ancsNotificationParcelable) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (ancsNotificationParcelable != null) {
                        parcelObtain.writeInt(1);
                        ancsNotificationParcelable.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.wearable.internal.zzas
            public void zza(CapabilityInfoParcelable capabilityInfoParcelable) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (capabilityInfoParcelable != null) {
                        parcelObtain.writeInt(1);
                        capabilityInfoParcelable.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.wearable.internal.zzas
            public void zza(ChannelEventParcelable channelEventParcelable) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (channelEventParcelable != null) {
                        parcelObtain.writeInt(1);
                        channelEventParcelable.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.wearable.internal.zzas
            public void zza(MessageEventParcelable messageEventParcelable) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (messageEventParcelable != null) {
                        parcelObtain.writeInt(1);
                        messageEventParcelable.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.wearable.internal.zzas
            public void zza(NodeParcelable nodeParcelable) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (nodeParcelable != null) {
                        parcelObtain.writeInt(1);
                        nodeParcelable.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.wearable.internal.zzas
            public void zzac(DataHolder dataHolder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (dataHolder != null) {
                        parcelObtain.writeInt(1);
                        dataHolder.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.wearable.internal.zzas
            public void zzb(NodeParcelable nodeParcelable) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
                    if (nodeParcelable != null) {
                        parcelObtain.writeInt(1);
                        nodeParcelable.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.wearable.internal.IWearableListener");
        }

        public static zzas zzdP(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableListener");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzas)) ? new C0240zza(iBinder) : (zzas) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    zzac(parcel.readInt() != 0 ? DataHolder.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    zza(parcel.readInt() != 0 ? MessageEventParcelable.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    zza(parcel.readInt() != 0 ? NodeParcelable.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    zzb(parcel.readInt() != 0 ? NodeParcelable.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    onConnectedNodes(parcel.createTypedArrayList(NodeParcelable.CREATOR));
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    zza(parcel.readInt() != 0 ? AncsNotificationParcelable.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    zza(parcel.readInt() != 0 ? ChannelEventParcelable.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    zza(parcel.readInt() != 0 ? CapabilityInfoParcelable.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.wearable.internal.IWearableListener");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void onConnectedNodes(List<NodeParcelable> list) throws RemoteException;

    void zza(AncsNotificationParcelable ancsNotificationParcelable) throws RemoteException;

    void zza(CapabilityInfoParcelable capabilityInfoParcelable) throws RemoteException;

    void zza(ChannelEventParcelable channelEventParcelable) throws RemoteException;

    void zza(MessageEventParcelable messageEventParcelable) throws RemoteException;

    void zza(NodeParcelable nodeParcelable) throws RemoteException;

    void zzac(DataHolder dataHolder) throws RemoteException;

    void zzb(NodeParcelable nodeParcelable) throws RemoteException;
}
