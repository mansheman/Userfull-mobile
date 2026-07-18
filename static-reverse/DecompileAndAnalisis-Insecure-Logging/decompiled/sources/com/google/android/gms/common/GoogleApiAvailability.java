package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;

/* loaded from: classes.dex */
public class GoogleApiAvailability {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final GoogleApiAvailability zzVI = new GoogleApiAvailability();

    GoogleApiAvailability() {
    }

    public static GoogleApiAvailability getInstance() {
        return zzVI;
    }

    public Dialog getErrorDialog(Activity activity, int errorCode, int requestCode) {
        return GooglePlayServicesUtil.getErrorDialog(errorCode, activity, requestCode);
    }

    public Dialog getErrorDialog(Activity activity, int errorCode, int requestCode, DialogInterface.OnCancelListener cancelListener) {
        return GooglePlayServicesUtil.getErrorDialog(errorCode, activity, requestCode, cancelListener);
    }

    public PendingIntent getErrorResolutionPendingIntent(Context context, int errorCode, int requestCode) {
        return GooglePlayServicesUtil.getErrorPendingIntent(errorCode, context, requestCode);
    }

    public final String getErrorString(int errorCode) {
        return GooglePlayServicesUtil.getErrorString(errorCode);
    }

    public String getOpenSourceSoftwareLicenseInfo(Context context) {
        return GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(context);
    }

    public int isGooglePlayServicesAvailable(Context context) throws PackageManager.NameNotFoundException {
        int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (GooglePlayServicesUtil.zze(context, iIsGooglePlayServicesAvailable)) {
            return 18;
        }
        return iIsGooglePlayServicesAvailable;
    }

    public final boolean isUserResolvableError(int errorCode) {
        return GooglePlayServicesUtil.isUserRecoverableError(errorCode);
    }

    public boolean showErrorDialogFragment(Activity activity, int errorCode, int requestCode) {
        return GooglePlayServicesUtil.showErrorDialogFragment(errorCode, activity, requestCode);
    }

    public boolean showErrorDialogFragment(Activity activity, int errorCode, int requestCode, DialogInterface.OnCancelListener cancelListener) {
        return GooglePlayServicesUtil.showErrorDialogFragment(errorCode, activity, requestCode, cancelListener);
    }

    public void showErrorNotification(Context context, int errorCode) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        GooglePlayServicesUtil.showErrorNotification(errorCode, context);
    }

    public void zzZ(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException, PackageManager.NameNotFoundException {
        GooglePlayServicesUtil.zzY(context);
    }
}
