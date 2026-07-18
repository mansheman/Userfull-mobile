package com.google.android.gms.drive.internal;

import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.drive.RealtimeDocumentSyncRequest;
import com.google.android.gms.drive.internal.zzal;
import com.google.android.gms.drive.internal.zzam;

/* loaded from: classes.dex */
public interface zzak extends IInterface {

    public static abstract class zza extends Binder implements zzak {

        /* renamed from: com.google.android.gms.drive.internal.zzak$zza$zza, reason: collision with other inner class name */
        private static class C0043zza implements zzak {
            private IBinder zznF;

            C0043zza(IBinder iBinder) {
                this.zznF = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.zznF;
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public IntentSender zza(CreateFileIntentSenderRequest createFileIntentSenderRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFileIntentSenderRequest != null) {
                        parcelObtain.writeInt(1);
                        createFileIntentSenderRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? (IntentSender) IntentSender.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public IntentSender zza(OpenFileIntentSenderRequest openFileIntentSenderRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (openFileIntentSenderRequest != null) {
                        parcelObtain.writeInt(1);
                        openFileIntentSenderRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? (IntentSender) IntentSender.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public DriveServiceResponse zza(OpenContentsRequest openContentsRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (openContentsRequest != null) {
                        parcelObtain.writeInt(1);
                        openContentsRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? DriveServiceResponse.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(RealtimeDocumentSyncRequest realtimeDocumentSyncRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (realtimeDocumentSyncRequest != null) {
                        parcelObtain.writeInt(1);
                        realtimeDocumentSyncRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(AddEventListenerRequest addEventListenerRequest, zzam zzamVar, String str, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (addEventListenerRequest != null) {
                        parcelObtain.writeInt(1);
                        addEventListenerRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzamVar != null ? zzamVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(AddPermissionRequest addPermissionRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (addPermissionRequest != null) {
                        parcelObtain.writeInt(1);
                        addPermissionRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(AuthorizeAccessRequest authorizeAccessRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (authorizeAccessRequest != null) {
                        parcelObtain.writeInt(1);
                        authorizeAccessRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(CancelPendingActionsRequest cancelPendingActionsRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (cancelPendingActionsRequest != null) {
                        parcelObtain.writeInt(1);
                        cancelPendingActionsRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(CheckResourceIdsExistRequest checkResourceIdsExistRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (checkResourceIdsExistRequest != null) {
                        parcelObtain.writeInt(1);
                        checkResourceIdsExistRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(CloseContentsAndUpdateMetadataRequest closeContentsAndUpdateMetadataRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (closeContentsAndUpdateMetadataRequest != null) {
                        parcelObtain.writeInt(1);
                        closeContentsAndUpdateMetadataRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(CloseContentsRequest closeContentsRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (closeContentsRequest != null) {
                        parcelObtain.writeInt(1);
                        closeContentsRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(CreateContentsRequest createContentsRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createContentsRequest != null) {
                        parcelObtain.writeInt(1);
                        createContentsRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(CreateFileRequest createFileRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFileRequest != null) {
                        parcelObtain.writeInt(1);
                        createFileRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(CreateFolderRequest createFolderRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFolderRequest != null) {
                        parcelObtain.writeInt(1);
                        createFolderRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(DeleteResourceRequest deleteResourceRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (deleteResourceRequest != null) {
                        parcelObtain.writeInt(1);
                        deleteResourceRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(DisconnectRequest disconnectRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (disconnectRequest != null) {
                        parcelObtain.writeInt(1);
                        disconnectRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.zznF.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(FetchThumbnailRequest fetchThumbnailRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (fetchThumbnailRequest != null) {
                        parcelObtain.writeInt(1);
                        fetchThumbnailRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(GetChangesRequest getChangesRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (getChangesRequest != null) {
                        parcelObtain.writeInt(1);
                        getChangesRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(GetDriveIdFromUniqueIdentifierRequest getDriveIdFromUniqueIdentifierRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (getDriveIdFromUniqueIdentifierRequest != null) {
                        parcelObtain.writeInt(1);
                        getDriveIdFromUniqueIdentifierRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(GetMetadataRequest getMetadataRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (getMetadataRequest != null) {
                        parcelObtain.writeInt(1);
                        getMetadataRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(GetPermissionsRequest getPermissionsRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (getPermissionsRequest != null) {
                        parcelObtain.writeInt(1);
                        getPermissionsRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(ListParentsRequest listParentsRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (listParentsRequest != null) {
                        parcelObtain.writeInt(1);
                        listParentsRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(LoadRealtimeRequest loadRealtimeRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (loadRealtimeRequest != null) {
                        parcelObtain.writeInt(1);
                        loadRealtimeRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(QueryRequest queryRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (queryRequest != null) {
                        parcelObtain.writeInt(1);
                        queryRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(QueryRequest queryRequest, zzam zzamVar, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (queryRequest != null) {
                        parcelObtain.writeInt(1);
                        queryRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzamVar != null ? zzamVar.asBinder() : null);
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(RemoveEventListenerRequest removeEventListenerRequest, zzam zzamVar, String str, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (removeEventListenerRequest != null) {
                        parcelObtain.writeInt(1);
                        removeEventListenerRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzamVar != null ? zzamVar.asBinder() : null);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(RemovePermissionRequest removePermissionRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (removePermissionRequest != null) {
                        parcelObtain.writeInt(1);
                        removePermissionRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(SetDrivePreferencesRequest setDrivePreferencesRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (setDrivePreferencesRequest != null) {
                        parcelObtain.writeInt(1);
                        setDrivePreferencesRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(SetFileUploadPreferencesRequest setFileUploadPreferencesRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (setFileUploadPreferencesRequest != null) {
                        parcelObtain.writeInt(1);
                        setFileUploadPreferencesRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(SetResourceParentsRequest setResourceParentsRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (setResourceParentsRequest != null) {
                        parcelObtain.writeInt(1);
                        setResourceParentsRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(TrashResourceRequest trashResourceRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (trashResourceRequest != null) {
                        parcelObtain.writeInt(1);
                        trashResourceRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(UnsubscribeResourceRequest unsubscribeResourceRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (unsubscribeResourceRequest != null) {
                        parcelObtain.writeInt(1);
                        unsubscribeResourceRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(UntrashResourceRequest untrashResourceRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (untrashResourceRequest != null) {
                        parcelObtain.writeInt(1);
                        untrashResourceRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(UpdateMetadataRequest updateMetadataRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (updateMetadataRequest != null) {
                        parcelObtain.writeInt(1);
                        updateMetadataRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(UpdatePermissionRequest updatePermissionRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (updatePermissionRequest != null) {
                        parcelObtain.writeInt(1);
                        updatePermissionRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zza(zzam zzamVar, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    parcelObtain.writeStrongBinder(zzamVar != null ? zzamVar.asBinder() : null);
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zzb(QueryRequest queryRequest, zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (queryRequest != null) {
                        parcelObtain.writeInt(1);
                        queryRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zzb(zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zzc(zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zzd(zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zze(zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.google.android.gms.drive.internal.zzak
            public void zzf(zzal zzalVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    parcelObtain.writeStrongBinder(zzalVar != null ? zzalVar.asBinder() : null);
                    this.zznF.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static zzak zzaO(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IDriveService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof zzak)) ? new C0043zza(iBinder) : (zzak) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? GetMetadataRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? QueryRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? UpdateMetadataRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? CreateContentsRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? CreateFileRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? CreateFolderRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    DriveServiceResponse driveServiceResponseZza = zza(parcel.readInt() != 0 ? OpenContentsRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (driveServiceResponseZza != null) {
                        parcel2.writeInt(1);
                        driveServiceResponseZza.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? CloseContentsRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 10:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    IntentSender intentSenderZza = zza(parcel.readInt() != 0 ? OpenFileIntentSenderRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (intentSenderZza != null) {
                        parcel2.writeInt(1);
                        intentSenderZza.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 11:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    IntentSender intentSenderZza2 = zza(parcel.readInt() != 0 ? CreateFileIntentSenderRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (intentSenderZza2 != null) {
                        parcel2.writeInt(1);
                        intentSenderZza2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 12:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? AuthorizeAccessRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? ListParentsRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? AddEventListenerRequest.CREATOR.createFromParcel(parcel) : null, zzam.zza.zzaQ(parcel.readStrongBinder()), parcel.readString(), zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? RemoveEventListenerRequest.CREATOR.createFromParcel(parcel) : null, zzam.zza.zzaQ(parcel.readStrongBinder()), parcel.readString(), zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 16:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? DisconnectRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? TrashResourceRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 18:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? CloseContentsAndUpdateMetadataRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zzb(parcel.readInt() != 0 ? QueryRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 24:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? DeleteResourceRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? LoadRealtimeRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 28:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? SetResourceParentsRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 29:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? GetDriveIdFromUniqueIdentifierRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 30:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? CheckResourceIdsExistRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 31:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zzb(zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 32:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zzc(zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 33:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? SetDrivePreferencesRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 34:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? RealtimeDocumentSyncRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 35:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zzd(zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 36:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? SetFileUploadPreferencesRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 37:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? CancelPendingActionsRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 38:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? UntrashResourceRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 41:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zze(zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 42:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? FetchThumbnailRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 43:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zzf(zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 44:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? GetChangesRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 46:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? UnsubscribeResourceRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 47:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? GetPermissionsRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 48:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? AddPermissionRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 49:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? UpdatePermissionRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 50:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? RemovePermissionRequest.CREATOR.createFromParcel(parcel) : null, zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 51:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(parcel.readInt() != 0 ? QueryRequest.CREATOR.createFromParcel(parcel) : null, zzam.zza.zzaQ(parcel.readStrongBinder()), zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 52:
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    zza(zzam.zza.zzaQ(parcel.readStrongBinder()), zzal.zza.zzaP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.drive.internal.IDriveService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    IntentSender zza(CreateFileIntentSenderRequest createFileIntentSenderRequest) throws RemoteException;

    IntentSender zza(OpenFileIntentSenderRequest openFileIntentSenderRequest) throws RemoteException;

    DriveServiceResponse zza(OpenContentsRequest openContentsRequest, zzal zzalVar) throws RemoteException;

    void zza(RealtimeDocumentSyncRequest realtimeDocumentSyncRequest, zzal zzalVar) throws RemoteException;

    void zza(AddEventListenerRequest addEventListenerRequest, zzam zzamVar, String str, zzal zzalVar) throws RemoteException;

    void zza(AddPermissionRequest addPermissionRequest, zzal zzalVar) throws RemoteException;

    void zza(AuthorizeAccessRequest authorizeAccessRequest, zzal zzalVar) throws RemoteException;

    void zza(CancelPendingActionsRequest cancelPendingActionsRequest, zzal zzalVar) throws RemoteException;

    void zza(CheckResourceIdsExistRequest checkResourceIdsExistRequest, zzal zzalVar) throws RemoteException;

    void zza(CloseContentsAndUpdateMetadataRequest closeContentsAndUpdateMetadataRequest, zzal zzalVar) throws RemoteException;

    void zza(CloseContentsRequest closeContentsRequest, zzal zzalVar) throws RemoteException;

    void zza(CreateContentsRequest createContentsRequest, zzal zzalVar) throws RemoteException;

    void zza(CreateFileRequest createFileRequest, zzal zzalVar) throws RemoteException;

    void zza(CreateFolderRequest createFolderRequest, zzal zzalVar) throws RemoteException;

    void zza(DeleteResourceRequest deleteResourceRequest, zzal zzalVar) throws RemoteException;

    void zza(DisconnectRequest disconnectRequest) throws RemoteException;

    void zza(FetchThumbnailRequest fetchThumbnailRequest, zzal zzalVar) throws RemoteException;

    void zza(GetChangesRequest getChangesRequest, zzal zzalVar) throws RemoteException;

    void zza(GetDriveIdFromUniqueIdentifierRequest getDriveIdFromUniqueIdentifierRequest, zzal zzalVar) throws RemoteException;

    void zza(GetMetadataRequest getMetadataRequest, zzal zzalVar) throws RemoteException;

    void zza(GetPermissionsRequest getPermissionsRequest, zzal zzalVar) throws RemoteException;

    void zza(ListParentsRequest listParentsRequest, zzal zzalVar) throws RemoteException;

    void zza(LoadRealtimeRequest loadRealtimeRequest, zzal zzalVar) throws RemoteException;

    void zza(QueryRequest queryRequest, zzal zzalVar) throws RemoteException;

    void zza(QueryRequest queryRequest, zzam zzamVar, zzal zzalVar) throws RemoteException;

    void zza(RemoveEventListenerRequest removeEventListenerRequest, zzam zzamVar, String str, zzal zzalVar) throws RemoteException;

    void zza(RemovePermissionRequest removePermissionRequest, zzal zzalVar) throws RemoteException;

    void zza(SetDrivePreferencesRequest setDrivePreferencesRequest, zzal zzalVar) throws RemoteException;

    void zza(SetFileUploadPreferencesRequest setFileUploadPreferencesRequest, zzal zzalVar) throws RemoteException;

    void zza(SetResourceParentsRequest setResourceParentsRequest, zzal zzalVar) throws RemoteException;

    void zza(TrashResourceRequest trashResourceRequest, zzal zzalVar) throws RemoteException;

    void zza(UnsubscribeResourceRequest unsubscribeResourceRequest, zzal zzalVar) throws RemoteException;

    void zza(UntrashResourceRequest untrashResourceRequest, zzal zzalVar) throws RemoteException;

    void zza(UpdateMetadataRequest updateMetadataRequest, zzal zzalVar) throws RemoteException;

    void zza(UpdatePermissionRequest updatePermissionRequest, zzal zzalVar) throws RemoteException;

    void zza(zzal zzalVar) throws RemoteException;

    void zza(zzam zzamVar, zzal zzalVar) throws RemoteException;

    void zzb(QueryRequest queryRequest, zzal zzalVar) throws RemoteException;

    void zzb(zzal zzalVar) throws RemoteException;

    void zzc(zzal zzalVar) throws RemoteException;

    void zzd(zzal zzalVar) throws RemoteException;

    void zze(zzal zzalVar) throws RemoteException;

    void zzf(zzal zzalVar) throws RemoteException;
}
