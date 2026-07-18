package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zziy;

/* loaded from: classes.dex */
public interface zziz extends IInterface {

    public static abstract class zza extends Binder implements zziz {

        /* renamed from: com.google.android.gms.internal.zziz$zza$zza, reason: collision with other inner class name */
        private static class C0105zza implements zziz {
            private IBinder zznF;

            C0105zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.internal.zziz
            public void zza(zziy zziyVar, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.appinvite.internal.IAppInviteService");
                    parcelObtain.writeStrongBinder(zziyVar != null ? zziyVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.internal.zziz
            public void zzb(zziy zziyVar, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.appinvite.internal.IAppInviteService");
                    parcelObtain.writeStrongBinder(zziyVar != null ? zziyVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zziz zzai(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appinvite.internal.IAppInviteService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zziz)) ? new C0105zza(iBinder) : (zziz) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.appinvite.internal.IAppInviteService");
                    zzb(zziy.zza.zzah(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface("com.google.android.gms.appinvite.internal.IAppInviteService");
                    zza(zziy.zza.zzah(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.appinvite.internal.IAppInviteService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void zza(zziy zziyVar, String str) throws RemoteException;

    void zzb(zziy zziyVar, String str) throws RemoteException;
}
