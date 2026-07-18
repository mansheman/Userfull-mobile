package com.google.android.gms.fitness.internal.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.service.FitnessSensorServiceRequest;
import com.google.android.gms.internal.zzmg;
import com.google.android.gms.internal.zzmu;

/* loaded from: classes.dex */
public interface zzc extends IInterface {

    public static abstract class zza extends Binder implements zzc {
        public zza() {
            attachInterface(this, "com.google.android.gms.fitness.internal.service.IFitnessSensorService");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.fitness.internal.service.IFitnessSensorService");
                    zza(parcel.readInt() != 0 ? FitnessDataSourcesRequest.CREATOR.createFromParcel(parcel) : null, zzmg.zza.zzbr(parcel.readStrongBinder()));
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.fitness.internal.service.IFitnessSensorService");
                    zza(parcel.readInt() != 0 ? FitnessSensorServiceRequest.CREATOR.createFromParcel(parcel) : null, zzmu.zza.zzbF(parcel.readStrongBinder()));
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.fitness.internal.service.IFitnessSensorService");
                    zza(parcel.readInt() != 0 ? FitnessUnregistrationRequest.CREATOR.createFromParcel(parcel) : null, zzmu.zza.zzbF(parcel.readStrongBinder()));
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.fitness.internal.service.IFitnessSensorService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zza(FitnessDataSourcesRequest fitnessDataSourcesRequest, zzmg zzmgVar) throws RemoteException;

    void zza(FitnessUnregistrationRequest fitnessUnregistrationRequest, zzmu zzmuVar) throws RemoteException;

    void zza(FitnessSensorServiceRequest fitnessSensorServiceRequest, zzmu zzmuVar) throws RemoteException;
}
