package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* loaded from: classes.dex */
public interface zzey extends IInterface {

    public static abstract class zza extends Binder implements zzey {

        /* renamed from: com.google.android.gms.internal.zzey$zza$zza, reason: collision with other inner class name */
        private static class C0093zza implements zzey {
            private IBinder zznF;

            C0093zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.internal.zzey
            public IBinder zzc(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
                    parcelObtain.writeStrongBinder(zzdVar != null ? zzdVar.asBinder() : null);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zzey zzJ(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzey)) ? new C0093zza(iBinder) : (zzey) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
                    IBinder iBinderZzc = zzc(zzd.zza.zzbg(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeStrongBinder(iBinderZzc);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IBinder zzc(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException;
}
