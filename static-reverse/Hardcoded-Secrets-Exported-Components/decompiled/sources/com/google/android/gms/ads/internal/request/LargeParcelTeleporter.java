package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzlg;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public final class LargeParcelTeleporter implements SafeParcelable {
    public static final Parcelable.Creator<LargeParcelTeleporter> CREATOR = new zzk();
    final int zzCY;
    ParcelFileDescriptor zzCZ;
    private Parcelable zzDa;
    private boolean zzDb;

    LargeParcelTeleporter(int versionCode, ParcelFileDescriptor parcelFileDescriptor) {
        this.zzCY = versionCode;
        this.zzCZ = parcelFileDescriptor;
        this.zzDa = null;
        this.zzDb = true;
    }

    public LargeParcelTeleporter(SafeParcelable teleportee) {
        this.zzCY = 1;
        this.zzCZ = null;
        this.zzDa = teleportee;
        this.zzDb = false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (this.zzCZ == null) {
            Parcel parcelObtain = Parcel.obtain();
            try {
                this.zzDa.writeToParcel(parcelObtain, 0);
                byte[] bArrMarshall = parcelObtain.marshall();
                parcelObtain.recycle();
                this.zzCZ = zzf(bArrMarshall);
            } catch (Throwable th) {
                parcelObtain.recycle();
                throw th;
            }
        }
        zzk.zza(this, dest, flags);
    }

    public <T extends SafeParcelable> T zza(Parcelable.Creator<T> creator) throws IOException {
        if (this.zzDb) {
            if (this.zzCZ == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaz("File descriptor is empty, returning null.");
                return null;
            }
            DataInputStream dataInputStream = new DataInputStream(new ParcelFileDescriptor.AutoCloseInputStream(this.zzCZ));
            try {
                try {
                    byte[] bArr = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr, 0, bArr.length);
                    zzlg.zzb(dataInputStream);
                    Parcel parcelObtain = Parcel.obtain();
                    try {
                        parcelObtain.unmarshall(bArr, 0, bArr.length);
                        parcelObtain.setDataPosition(0);
                        this.zzDa = creator.createFromParcel(parcelObtain);
                        parcelObtain.recycle();
                        this.zzDb = false;
                    } catch (Throwable th) {
                        parcelObtain.recycle();
                        throw th;
                    }
                } catch (IOException e) {
                    throw new IllegalStateException("Could not read from parcel file descriptor", e);
                }
            } catch (Throwable th2) {
                zzlg.zzb(dataInputStream);
                throw th2;
            }
        }
        return (T) this.zzDa;
    }

    protected <T> ParcelFileDescriptor zzf(final byte[] bArr) throws IOException {
        try {
            ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe = ParcelFileDescriptor.createPipe();
            final ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptorArrCreatePipe[1]);
            new Thread(new Runnable() { // from class: com.google.android.gms.ads.internal.request.LargeParcelTeleporter.1
                @Override // java.lang.Runnable
                public void run() throws IOException {
                    DataOutputStream dataOutputStream = new DataOutputStream(autoCloseOutputStream);
                    try {
                        dataOutputStream.writeInt(bArr.length);
                        dataOutputStream.write(bArr);
                    } catch (IOException e) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzb("Error transporting the ad response", e);
                        zzo.zzby().zzc((Throwable) e, true);
                    } finally {
                        zzlg.zzb(dataOutputStream);
                    }
                }
            }).start();
            return parcelFileDescriptorArrCreatePipe[0];
        } catch (IOException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error transporting the ad response", e);
            zzo.zzby().zzc((Throwable) e, true);
            return null;
        }
    }
}
