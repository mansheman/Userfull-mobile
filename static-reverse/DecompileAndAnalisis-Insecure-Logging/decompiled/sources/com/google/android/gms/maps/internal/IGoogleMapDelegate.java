package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.internal.ILocationSourceDelegate;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.internal.zzb;
import com.google.android.gms.maps.internal.zzd;
import com.google.android.gms.maps.internal.zze;
import com.google.android.gms.maps.internal.zzf;
import com.google.android.gms.maps.internal.zzg;
import com.google.android.gms.maps.internal.zzh;
import com.google.android.gms.maps.internal.zzj;
import com.google.android.gms.maps.internal.zzk;
import com.google.android.gms.maps.internal.zzl;
import com.google.android.gms.maps.internal.zzm;
import com.google.android.gms.maps.internal.zzn;
import com.google.android.gms.maps.internal.zzo;
import com.google.android.gms.maps.internal.zzp;
import com.google.android.gms.maps.internal.zzq;
import com.google.android.gms.maps.internal.zzw;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.CameraUpdateParcelable;
import com.google.android.gms.maps.model.internal.GroundOverlayOptionsParcelable;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import com.google.android.gms.maps.model.internal.MarkerOptionsParcelable;
import com.google.android.gms.maps.model.internal.zze;
import com.google.android.gms.maps.model.internal.zzf;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzi;
import com.google.android.gms.maps.model.internal.zzj;
import com.google.android.gms.maps.model.internal.zzk;

/* loaded from: classes.dex */
public interface IGoogleMapDelegate extends IInterface {

    public static abstract class zza extends Binder implements IGoogleMapDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IGoogleMapDelegate$zza$zza, reason: collision with other inner class name */
        private static class C0178zza implements IGoogleMapDelegate {
            private IBinder zznF;

            C0178zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public com.google.android.gms.maps.model.internal.zze addCircle(CircleOptions options) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (options != null) {
                        parcelObtain.writeInt(1);
                        options.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zze.zza.zzcL(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public com.google.android.gms.maps.model.internal.zzf addGroundOverlay(GroundOverlayOptions options) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (options != null) {
                        parcelObtain.writeInt(1);
                        options.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzf.zza.zzcM(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public com.google.android.gms.maps.model.internal.zzf addGroundOverlay2(GroundOverlayOptions options, GroundOverlayOptionsParcelable parcelableOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (options != null) {
                        parcelObtain.writeInt(1);
                        options.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (parcelableOptions != null) {
                        parcelObtain.writeInt(1);
                        parcelableOptions.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzf.zza.zzcM(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public com.google.android.gms.maps.model.internal.zzi addMarker(MarkerOptions options) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (options != null) {
                        parcelObtain.writeInt(1);
                        options.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzi.zza.zzcP(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public com.google.android.gms.maps.model.internal.zzi addMarker2(MarkerOptions options, MarkerOptionsParcelable parcelableOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (options != null) {
                        parcelObtain.writeInt(1);
                        options.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (parcelableOptions != null) {
                        parcelObtain.writeInt(1);
                        parcelableOptions.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzi.zza.zzcP(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public com.google.android.gms.maps.model.internal.zzj addPolygon(PolygonOptions options) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (options != null) {
                        parcelObtain.writeInt(1);
                        options.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzj.zza.zzcQ(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public IPolylineDelegate addPolyline(PolylineOptions options) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (options != null) {
                        parcelObtain.writeInt(1);
                        options.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IPolylineDelegate.zza.zzcR(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public com.google.android.gms.maps.model.internal.zzk addTileOverlay(TileOverlayOptions options) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (options != null) {
                        parcelObtain.writeInt(1);
                        options.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzk.zza.zzcS(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void animateCamera(com.google.android.gms.dynamic.zzd update) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(update != null ? update.asBinder() : null);
                    this.zznF.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void animateCamera2(CameraUpdateParcelable update) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (update != null) {
                        parcelObtain.writeInt(1);
                        update.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void animateCameraWithCallback(com.google.android.gms.dynamic.zzd update, zzb callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(update != null ? update.asBinder() : null);
                    parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.zznF.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void animateCameraWithCallback2(CameraUpdateParcelable update, zzb callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (update != null) {
                        parcelObtain.writeInt(1);
                        update.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.zznF.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void animateCameraWithDurationAndCallback(com.google.android.gms.dynamic.zzd update, int durationMs, zzb callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(update != null ? update.asBinder() : null);
                    parcelObtain.writeInt(durationMs);
                    parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.zznF.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void animateCameraWithDurationAndCallback2(CameraUpdateParcelable update, int durationMs, zzb callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (update != null) {
                        parcelObtain.writeInt(1);
                        update.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(durationMs);
                    parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.zznF.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void clear() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public CameraPosition getCameraPosition() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? CameraPosition.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public com.google.android.gms.maps.model.internal.zzg getFocusedBuilding() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return zzg.zza.zzcN(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void getMapAsync(zzm callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.zznF.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public int getMapType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public float getMaxZoomLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public float getMinZoomLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public Location getMyLocation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public IProjectionDelegate getProjection() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IProjectionDelegate.zza.zzcE(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public IUiSettingsDelegate getUiSettings() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IUiSettingsDelegate.zza.zzcJ(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public boolean isBuildingsEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public boolean isIndoorEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public boolean isMyLocationEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public boolean isTrafficEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void moveCamera(com.google.android.gms.dynamic.zzd update) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(update != null ? update.asBinder() : null);
                    this.zznF.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void moveCamera2(CameraUpdateParcelable update) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (update != null) {
                        parcelObtain.writeInt(1);
                        update.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void onCreate(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void onDestroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void onLowMemory() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void onPause() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void onResume() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void onSaveInstanceState(Bundle outState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (outState != null) {
                        parcelObtain.writeInt(1);
                        outState.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        outState.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setBuildingsEnabled(boolean enabled) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeInt(enabled ? 1 : 0);
                    this.zznF.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setContentDescription(String description) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeString(description);
                    this.zznF.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public boolean setIndoorEnabled(boolean enabled) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeInt(enabled ? 1 : 0);
                    this.zznF.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setInfoWindowAdapter(zzd adapter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(adapter != null ? adapter.asBinder() : null);
                    this.zznF.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setInfoWindowRenderer(zze infoWindowRenderer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(infoWindowRenderer != null ? infoWindowRenderer.asBinder() : null);
                    this.zznF.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setLocationSource(ILocationSourceDelegate source) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(source != null ? source.asBinder() : null);
                    this.zznF.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setMapType(int type) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeInt(type);
                    this.zznF.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setMyLocationEnabled(boolean enabled) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeInt(enabled ? 1 : 0);
                    this.zznF.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnCameraChangeListener(zzf listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnIndoorStateChangeListener(zzg listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnInfoWindowClickListener(zzh listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnMapClickListener(zzj listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnMapLoadedCallback(zzk callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.zznF.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnMapLongClickListener(zzl listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnMarkerClickListener(zzn listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnMarkerDragListener(zzo listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnMyLocationButtonClickListener(zzp listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setOnMyLocationChangeListener(zzq listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.zznF.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setPadding(int left, int top, int right, int bottom) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeInt(left);
                    parcelObtain.writeInt(top);
                    parcelObtain.writeInt(right);
                    parcelObtain.writeInt(bottom);
                    this.zznF.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void setTrafficEnabled(boolean enabled) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeInt(enabled ? 1 : 0);
                    this.zznF.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void snapshot(zzw callback, com.google.android.gms.dynamic.zzd bitmap) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    parcelObtain.writeStrongBinder(bitmap != null ? bitmap.asBinder() : null);
                    this.zznF.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public void stopAnimation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
            public boolean useViewLifecycleWhenInFragment() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zznF.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static IGoogleMapDelegate zzch(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IGoogleMapDelegate)) ? new C0178zza(iBinder) : (IGoogleMapDelegate) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    CameraPosition cameraPosition = getCameraPosition();
                    parcel2.writeNoException();
                    if (cameraPosition == null) {
                        parcel2.writeInt(0);
                        return true;
                    }
                    parcel2.writeInt(1);
                    cameraPosition.writeToParcel(parcel2, 1);
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    float maxZoomLevel = getMaxZoomLevel();
                    parcel2.writeNoException();
                    parcel2.writeFloat(maxZoomLevel);
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    float minZoomLevel = getMinZoomLevel();
                    parcel2.writeNoException();
                    parcel2.writeFloat(minZoomLevel);
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    moveCamera(zzd.zza.zzbg(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCamera(zzd.zza.zzbg(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCameraWithCallback(zzd.zza.zzbg(parcel.readStrongBinder()), zzb.zza.zzcf(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCameraWithDurationAndCallback(zzd.zza.zzbg(parcel.readStrongBinder()), parcel.readInt(), zzb.zza.zzcf(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    stopAnimation();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IPolylineDelegate iPolylineDelegateAddPolyline = addPolyline(parcel.readInt() != 0 ? PolylineOptions.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iPolylineDelegateAddPolyline != null ? iPolylineDelegateAddPolyline.asBinder() : null);
                    return true;
                case 10:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zzj zzjVarAddPolygon = addPolygon(parcel.readInt() != 0 ? PolygonOptions.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(zzjVarAddPolygon != null ? zzjVarAddPolygon.asBinder() : null);
                    return true;
                case 11:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zzi zziVarAddMarker = addMarker(parcel.readInt() != 0 ? MarkerOptions.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(zziVarAddMarker != null ? zziVarAddMarker.asBinder() : null);
                    return true;
                case 12:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zzf zzfVarAddGroundOverlay = addGroundOverlay(parcel.readInt() != 0 ? GroundOverlayOptions.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(zzfVarAddGroundOverlay != null ? zzfVarAddGroundOverlay.asBinder() : null);
                    return true;
                case 13:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zzk zzkVarAddTileOverlay = addTileOverlay(parcel.readInt() != 0 ? TileOverlayOptions.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(zzkVarAddTileOverlay != null ? zzkVarAddTileOverlay.asBinder() : null);
                    return true;
                case 14:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    clear();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    int mapType = getMapType();
                    parcel2.writeNoException();
                    parcel2.writeInt(mapType);
                    return true;
                case 16:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setMapType(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean zIsTrafficEnabled = isTrafficEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsTrafficEnabled ? 1 : 0);
                    return true;
                case 18:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setTrafficEnabled(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean zIsIndoorEnabled = isIndoorEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsIndoorEnabled ? 1 : 0);
                    return true;
                case 20:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean indoorEnabled = setIndoorEnabled(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(indoorEnabled ? 1 : 0);
                    return true;
                case 21:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean zIsMyLocationEnabled = isMyLocationEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsMyLocationEnabled ? 1 : 0);
                    return true;
                case 22:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setMyLocationEnabled(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    Location myLocation = getMyLocation();
                    parcel2.writeNoException();
                    if (myLocation == null) {
                        parcel2.writeInt(0);
                        return true;
                    }
                    parcel2.writeInt(1);
                    myLocation.writeToParcel(parcel2, 1);
                    return true;
                case 24:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setLocationSource(ILocationSourceDelegate.zza.zzck(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 25:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IUiSettingsDelegate uiSettings = getUiSettings();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(uiSettings != null ? uiSettings.asBinder() : null);
                    return true;
                case 26:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IProjectionDelegate projection = getProjection();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(projection != null ? projection.asBinder() : null);
                    return true;
                case 27:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnCameraChangeListener(zzf.zza.zzcn(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 28:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMapClickListener(zzj.zza.zzcr(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 29:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMapLongClickListener(zzl.zza.zzct(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 30:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMarkerClickListener(zzn.zza.zzcv(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 31:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMarkerDragListener(zzo.zza.zzcw(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 32:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnInfoWindowClickListener(zzh.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 33:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setInfoWindowAdapter(zzd.zza.zzci(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 35:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zze zzeVarAddCircle = addCircle(parcel.readInt() != 0 ? CircleOptions.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(zzeVarAddCircle != null ? zzeVarAddCircle.asBinder() : null);
                    return true;
                case 36:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMyLocationChangeListener(zzq.zza.zzcy(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 37:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMyLocationButtonClickListener(zzp.zza.zzcx(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 38:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    snapshot(zzw.zza.zzcF(parcel.readStrongBinder()), zzd.zza.zzbg(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 39:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setPadding(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 40:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean zIsBuildingsEnabled = isBuildingsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsBuildingsEnabled ? 1 : 0);
                    return true;
                case 41:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setBuildingsEnabled(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMapLoadedCallback(zzk.zza.zzcs(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 44:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zzg focusedBuilding = getFocusedBuilding();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(focusedBuilding != null ? focusedBuilding.asBinder() : null);
                    return true;
                case 45:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnIndoorStateChangeListener(zzg.zza.zzco(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 53:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    getMapAsync(zzm.zza.zzcu(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 54:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onCreate(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onResume();
                    parcel2.writeNoException();
                    return true;
                case 56:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onPause();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onDestroy();
                    parcel2.writeNoException();
                    return true;
                case 58:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onLowMemory();
                    parcel2.writeNoException();
                    return true;
                case 59:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    boolean zUseViewLifecycleWhenInFragment = useViewLifecycleWhenInFragment();
                    parcel2.writeNoException();
                    parcel2.writeInt(zUseViewLifecycleWhenInFragment ? 1 : 0);
                    return true;
                case 60:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                    onSaveInstanceState(bundle);
                    parcel2.writeNoException();
                    if (bundle == null) {
                        parcel2.writeInt(0);
                        return true;
                    }
                    parcel2.writeInt(1);
                    bundle.writeToParcel(parcel2, 1);
                    return true;
                case 61:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setContentDescription(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 64:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    moveCamera2(parcel.readInt() != 0 ? CameraUpdateParcelable.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCamera2(parcel.readInt() != 0 ? CameraUpdateParcelable.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCameraWithCallback2(parcel.readInt() != 0 ? CameraUpdateParcelable.CREATOR.createFromParcel(parcel) : null, zzb.zza.zzcf(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 67:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCameraWithDurationAndCallback2(parcel.readInt() != 0 ? CameraUpdateParcelable.CREATOR.createFromParcel(parcel) : null, parcel.readInt(), zzb.zza.zzcf(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 68:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zzi zziVarAddMarker2 = addMarker2(parcel.readInt() != 0 ? MarkerOptions.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? MarkerOptionsParcelable.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(zziVarAddMarker2 != null ? zziVarAddMarker2.asBinder() : null);
                    return true;
                case 69:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setInfoWindowRenderer(zze.zza.zzcj(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 70:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zzf zzfVarAddGroundOverlay2 = addGroundOverlay2(parcel.readInt() != 0 ? GroundOverlayOptions.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? GroundOverlayOptionsParcelable.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(zzfVarAddGroundOverlay2 != null ? zzfVarAddGroundOverlay2.asBinder() : null);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    com.google.android.gms.maps.model.internal.zze addCircle(CircleOptions circleOptions) throws RemoteException;

    com.google.android.gms.maps.model.internal.zzf addGroundOverlay(GroundOverlayOptions groundOverlayOptions) throws RemoteException;

    com.google.android.gms.maps.model.internal.zzf addGroundOverlay2(GroundOverlayOptions groundOverlayOptions, GroundOverlayOptionsParcelable groundOverlayOptionsParcelable) throws RemoteException;

    com.google.android.gms.maps.model.internal.zzi addMarker(MarkerOptions markerOptions) throws RemoteException;

    com.google.android.gms.maps.model.internal.zzi addMarker2(MarkerOptions markerOptions, MarkerOptionsParcelable markerOptionsParcelable) throws RemoteException;

    com.google.android.gms.maps.model.internal.zzj addPolygon(PolygonOptions polygonOptions) throws RemoteException;

    IPolylineDelegate addPolyline(PolylineOptions polylineOptions) throws RemoteException;

    com.google.android.gms.maps.model.internal.zzk addTileOverlay(TileOverlayOptions tileOverlayOptions) throws RemoteException;

    void animateCamera(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException;

    void animateCamera2(CameraUpdateParcelable cameraUpdateParcelable) throws RemoteException;

    void animateCameraWithCallback(com.google.android.gms.dynamic.zzd zzdVar, zzb zzbVar) throws RemoteException;

    void animateCameraWithCallback2(CameraUpdateParcelable cameraUpdateParcelable, zzb zzbVar) throws RemoteException;

    void animateCameraWithDurationAndCallback(com.google.android.gms.dynamic.zzd zzdVar, int i, zzb zzbVar) throws RemoteException;

    void animateCameraWithDurationAndCallback2(CameraUpdateParcelable cameraUpdateParcelable, int i, zzb zzbVar) throws RemoteException;

    void clear() throws RemoteException;

    CameraPosition getCameraPosition() throws RemoteException;

    com.google.android.gms.maps.model.internal.zzg getFocusedBuilding() throws RemoteException;

    void getMapAsync(zzm zzmVar) throws RemoteException;

    int getMapType() throws RemoteException;

    float getMaxZoomLevel() throws RemoteException;

    float getMinZoomLevel() throws RemoteException;

    Location getMyLocation() throws RemoteException;

    IProjectionDelegate getProjection() throws RemoteException;

    IUiSettingsDelegate getUiSettings() throws RemoteException;

    boolean isBuildingsEnabled() throws RemoteException;

    boolean isIndoorEnabled() throws RemoteException;

    boolean isMyLocationEnabled() throws RemoteException;

    boolean isTrafficEnabled() throws RemoteException;

    void moveCamera(com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException;

    void moveCamera2(CameraUpdateParcelable cameraUpdateParcelable) throws RemoteException;

    void onCreate(Bundle bundle) throws RemoteException;

    void onDestroy() throws RemoteException;

    void onLowMemory() throws RemoteException;

    void onPause() throws RemoteException;

    void onResume() throws RemoteException;

    void onSaveInstanceState(Bundle bundle) throws RemoteException;

    void setBuildingsEnabled(boolean z) throws RemoteException;

    void setContentDescription(String str) throws RemoteException;

    boolean setIndoorEnabled(boolean z) throws RemoteException;

    void setInfoWindowAdapter(zzd zzdVar) throws RemoteException;

    void setInfoWindowRenderer(zze zzeVar) throws RemoteException;

    void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) throws RemoteException;

    void setMapType(int i) throws RemoteException;

    void setMyLocationEnabled(boolean z) throws RemoteException;

    void setOnCameraChangeListener(zzf zzfVar) throws RemoteException;

    void setOnIndoorStateChangeListener(zzg zzgVar) throws RemoteException;

    void setOnInfoWindowClickListener(zzh zzhVar) throws RemoteException;

    void setOnMapClickListener(zzj zzjVar) throws RemoteException;

    void setOnMapLoadedCallback(zzk zzkVar) throws RemoteException;

    void setOnMapLongClickListener(zzl zzlVar) throws RemoteException;

    void setOnMarkerClickListener(zzn zznVar) throws RemoteException;

    void setOnMarkerDragListener(zzo zzoVar) throws RemoteException;

    void setOnMyLocationButtonClickListener(zzp zzpVar) throws RemoteException;

    void setOnMyLocationChangeListener(zzq zzqVar) throws RemoteException;

    void setPadding(int i, int i2, int i3, int i4) throws RemoteException;

    void setTrafficEnabled(boolean z) throws RemoteException;

    void snapshot(zzw zzwVar, com.google.android.gms.dynamic.zzd zzdVar) throws RemoteException;

    void stopAnimation() throws RemoteException;

    boolean useViewLifecycleWhenInFragment() throws RemoteException;
}
