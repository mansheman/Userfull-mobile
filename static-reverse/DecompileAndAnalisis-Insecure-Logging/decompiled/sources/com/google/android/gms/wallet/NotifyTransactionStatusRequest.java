package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;

/* loaded from: classes.dex */
public final class NotifyTransactionStatusRequest implements SafeParcelable {
    public static final Parcelable.Creator<NotifyTransactionStatusRequest> CREATOR = new zzm();
    int status;
    final int zzCY;
    String zzaQm;
    String zzaRw;

    public final class Builder {
        private Builder() {
        }

        public NotifyTransactionStatusRequest build() {
            zzu.zzb(!TextUtils.isEmpty(NotifyTransactionStatusRequest.this.zzaQm), "googleTransactionId is required");
            zzu.zzb(NotifyTransactionStatusRequest.this.status >= 1 && NotifyTransactionStatusRequest.this.status <= 8, "status is an unrecognized value");
            return NotifyTransactionStatusRequest.this;
        }

        public Builder setDetailedReason(String detailedReason) {
            NotifyTransactionStatusRequest.this.zzaRw = detailedReason;
            return this;
        }

        public Builder setGoogleTransactionId(String googleTransactionId) {
            NotifyTransactionStatusRequest.this.zzaQm = googleTransactionId;
            return this;
        }

        public Builder setStatus(int status) {
            NotifyTransactionStatusRequest.this.status = status;
            return this;
        }
    }

    public interface Status {
        public static final int SUCCESS = 1;

        public interface Error {
            public static final int AVS_DECLINE = 7;
            public static final int BAD_CARD = 4;
            public static final int BAD_CVC = 3;
            public static final int DECLINED = 5;
            public static final int FRAUD_DECLINE = 8;
            public static final int OTHER = 6;
            public static final int UNKNOWN = 2;
        }
    }

    NotifyTransactionStatusRequest() {
        this.zzCY = 1;
    }

    NotifyTransactionStatusRequest(int versionCode, String googleTransactionId, int status, String detailedReason) {
        this.zzCY = versionCode;
        this.zzaQm = googleTransactionId;
        this.status = status;
        this.zzaRw = detailedReason;
    }

    public static Builder newBuilder() {
        NotifyTransactionStatusRequest notifyTransactionStatusRequest = new NotifyTransactionStatusRequest();
        notifyTransactionStatusRequest.getClass();
        return new Builder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getDetailedReason() {
        return this.zzaRw;
    }

    public String getGoogleTransactionId() {
        return this.zzaQm;
    }

    public int getStatus() {
        return this.status;
    }

    public int getVersionCode() {
        return this.zzCY;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zzm.zza(this, out, flags);
    }
}
