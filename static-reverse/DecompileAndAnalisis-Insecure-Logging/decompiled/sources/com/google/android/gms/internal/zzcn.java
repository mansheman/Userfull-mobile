package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* loaded from: classes.dex */
public interface zzcn extends IInterface {

    public static abstract class zza extends Binder implements zzcn {

        /* renamed from: com.google.android.gms.internal.zzcn$zza$zza, reason: collision with other inner class name */
        private static class C0076zza implements zzcn {
            private IBinder zznF;

            C0076zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.internal.zzcn
            public IBinder zza(com.google.android.gms.dynamic.zzd zzdVar, com.google.android.gms.dynamic.zzd zzdVar2, com.google.android.gms.dynamic.zzd zzdVar3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
                    parcelObtain.writeStrongBinder(zzdVar != null ? zzdVar.asBinder() : null);
                    parcelObtain.writeStrongBinder(zzdVar2 != null ? zzdVar2.asBinder() : null);
                    parcelObtain.writeStrongBinder(zzdVar3 != null ? zzdVar3.asBinder() : null);
                    parcelObtain.writeInt(i);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zzcn zzv(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzcn)) ? new C0076zza(iBinder) : (zzcn) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
                    IBinder iBinderZza = zza(zzd.zza.zzbg(data.readStrongBinder()), zzd.zza.zzbg(data.readStrongBinder()), zzd.zza.zzbg(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    reply.writeStrongBinder(iBinderZza);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.ads.internal.formats.client.INativeAdViewDelegateCreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IBinder zza(com.google.android.gms.dynamic.zzd zzdVar, com.google.android.gms.dynamic.zzd zzdVar2, com.google.android.gms.dynamic.zzd zzdVar3, int i) throws RemoteException;
}
