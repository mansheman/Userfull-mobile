package com.google.android.gms.ads.internal.client;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface zzv extends IInterface {

    public static abstract class zza extends Binder implements zzv {

        /* renamed from: com.google.android.gms.ads.internal.client.zzv$zza$zza, reason: collision with other inner class name */
        private static class C0011zza implements zzv {
            private IBinder zznF;

            C0011zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.ads.internal.client.zzv
            public void zza(String str, MobileAdsSettingsParcel mobileAdsSettingsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
                    parcelObtain.writeString(str);
                    if (mobileAdsSettingsParcel != null) {
                        parcelObtain.writeInt(1);
                        mobileAdsSettingsParcel.writeToParcel(parcelObtain, 0);
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
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
        }

        public static zzv zzo(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzv)) ? new C0011zza(iBinder) : (zzv) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
                    zza(data.readString(), data.readInt() != 0 ? MobileAdsSettingsParcel.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void zza(String str, MobileAdsSettingsParcel mobileAdsSettingsParcel) throws RemoteException;
}
