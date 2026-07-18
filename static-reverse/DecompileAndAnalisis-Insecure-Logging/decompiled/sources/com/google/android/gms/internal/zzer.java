package com.google.android.gms.internal;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.google.android.gms.R;
import java.util.Map;

@zzgd
/* loaded from: classes.dex */
public class zzer extends zzeu {
    private final Context mContext;
    private final Map<String, String> zzyn;

    public zzer(zzid zzidVar, Map<String, String> map) {
        super(zzidVar, "storePicture");
        this.zzyn = map;
        this.mContext = zzidVar.zzgB();
    }

    public void execute() {
        if (this.mContext == null) {
            zzae("Activity context is not available");
            return;
        }
        if (!com.google.android.gms.ads.internal.zzo.zzbv().zzK(this.mContext).zzcS()) {
            zzae("Feature is not supported by the device.");
            return;
        }
        final String str = this.zzyn.get("iurl");
        if (TextUtils.isEmpty(str)) {
            zzae("Image url cannot be empty.");
            return;
        }
        if (!URLUtil.isValidUrl(str)) {
            zzae("Invalid image url: " + str);
            return;
        }
        final String strZzad = zzad(str);
        if (!com.google.android.gms.ads.internal.zzo.zzbv().zzav(strZzad)) {
            zzae("Image type not recognized: " + strZzad);
            return;
        }
        AlertDialog.Builder builderZzJ = com.google.android.gms.ads.internal.zzo.zzbv().zzJ(this.mContext);
        builderZzJ.setTitle(com.google.android.gms.ads.internal.zzo.zzby().zzc(R.string.store_picture_title, "Save image"));
        builderZzJ.setMessage(com.google.android.gms.ads.internal.zzo.zzby().zzc(R.string.store_picture_message, "Allow Ad to store image in Picture gallery?"));
        builderZzJ.setPositiveButton(com.google.android.gms.ads.internal.zzo.zzby().zzc(R.string.accept, "Accept"), new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzer.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ((DownloadManager) zzer.this.mContext.getSystemService("download")).enqueue(zzer.this.zzf(str, strZzad));
                } catch (IllegalStateException e) {
                    zzer.this.zzae("Could not store picture.");
                }
            }
        });
        builderZzJ.setNegativeButton(com.google.android.gms.ads.internal.zzo.zzby().zzc(R.string.decline, "Decline"), new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzer.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                zzer.this.zzae("User canceled the download.");
            }
        });
        builderZzJ.create().show();
    }

    String zzad(String str) {
        return Uri.parse(str).getLastPathSegment();
    }

    DownloadManager.Request zzf(String str, String str2) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, str2);
        com.google.android.gms.ads.internal.zzo.zzbx().zza(request);
        return request;
    }
}
