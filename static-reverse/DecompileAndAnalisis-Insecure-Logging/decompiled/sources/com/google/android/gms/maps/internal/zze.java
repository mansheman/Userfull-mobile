package com.google.android.gms.maps.internal;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.internal.zzi;

/* loaded from: classes.dex */
public interface zze extends IInterface {

    public static abstract class zza extends Binder implements zze {

        /* renamed from: com.google.android.gms.maps.internal.zze$zza$zza, reason: collision with other inner class name */
        private static class C0190zza implements zze {
            private IBinder zznF;

            C0190zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.maps.internal.zze
            public Bitmap zza(com.google.android.gms.maps.model.internal.zzi zziVar, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowRenderer");
                    parcelObtain.writeStrongBinder(zziVar != null ? zziVar.asBinder() : null);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zze zzcj(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IInfoWindowRenderer");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zze)) ? new C0190zza(iBinder) : (zze) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowRenderer");
                    Bitmap bitmapZza = zza(zzi.zza.zzcP(data.readStrongBinder()), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (bitmapZza == null) {
                        reply.writeInt(0);
                        return true;
                    }
                    reply.writeInt(1);
                    bitmapZza.writeToParcel(reply, 1);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IInfoWindowRenderer");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    Bitmap zza(com.google.android.gms.maps.model.internal.zzi zziVar, int i, int i2) throws RemoteException;
}
