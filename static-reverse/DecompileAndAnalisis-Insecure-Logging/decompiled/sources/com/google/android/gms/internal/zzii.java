package com.google.android.gms.internal;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

@zzgd
/* loaded from: classes.dex */
public class zzii extends WebChromeClient {
    private final zzid zzoA;

    /* renamed from: com.google.android.gms.internal.zzii$7, reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] zzHD = new int[ConsoleMessage.MessageLevel.values().length];

        static {
            try {
                zzHD[ConsoleMessage.MessageLevel.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                zzHD[ConsoleMessage.MessageLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                zzHD[ConsoleMessage.MessageLevel.LOG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                zzHD[ConsoleMessage.MessageLevel.TIP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                zzHD[ConsoleMessage.MessageLevel.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public zzii(zzid zzidVar) {
        this.zzoA = zzidVar;
    }

    private static void zza(AlertDialog.Builder builder, String str, final JsResult jsResult) {
        builder.setMessage(str).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzii.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                jsResult.confirm();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzii.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                jsResult.cancel();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.google.android.gms.internal.zzii.1
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                jsResult.cancel();
            }
        }).create().show();
    }

    private static void zza(Context context, AlertDialog.Builder builder, String str, String str2, final JsPromptResult jsPromptResult) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        TextView textView = new TextView(context);
        textView.setText(str);
        final EditText editText = new EditText(context);
        editText.setText(str2);
        linearLayout.addView(textView);
        linearLayout.addView(editText);
        builder.setView(linearLayout).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzii.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                jsPromptResult.confirm(editText.getText().toString());
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzii.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                jsPromptResult.cancel();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.google.android.gms.internal.zzii.4
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                jsPromptResult.cancel();
            }
        }).create().show();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Context zzc(WebView webView) {
        if (!(webView instanceof zzid)) {
            return webView.getContext();
        }
        zzid zzidVar = (zzid) webView;
        Activity activityZzgB = zzidVar.zzgB();
        return activityZzgB == null ? zzidVar.getContext() : activityZzgB;
    }

    private final boolean zzha() {
        return com.google.android.gms.ads.internal.zzo.zzbv().zza(this.zzoA.getContext().getPackageManager(), this.zzoA.getContext().getPackageName(), "android.permission.ACCESS_FINE_LOCATION") || com.google.android.gms.ads.internal.zzo.zzbv().zza(this.zzoA.getContext().getPackageManager(), this.zzoA.getContext().getPackageName(), "android.permission.ACCESS_COARSE_LOCATION");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.webkit.WebChromeClient
    public final void onCloseWindow(WebView webView) {
        if (!(webView instanceof zzid)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Tried to close a WebView that wasn't an AdWebView.");
            return;
        }
        com.google.android.gms.ads.internal.overlay.zzc zzcVarZzgD = ((zzid) webView).zzgD();
        if (zzcVarZzgD == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Tried to close an AdWebView not associated with an overlay.");
        } else {
            zzcVarZzgD.close();
        }
    }

    @Override // android.webkit.WebChromeClient
    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String str = "JS: " + consoleMessage.message() + " (" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")";
        if (str.contains("Application Cache")) {
            return super.onConsoleMessage(consoleMessage);
        }
        switch (AnonymousClass7.zzHD[consoleMessage.messageLevel().ordinal()]) {
            case 1:
                com.google.android.gms.ads.internal.util.client.zzb.zzaz(str);
                break;
            case 2:
                com.google.android.gms.ads.internal.util.client.zzb.zzaC(str);
                break;
            case 3:
            case 4:
                com.google.android.gms.ads.internal.util.client.zzb.zzaA(str);
                break;
            case 5:
                com.google.android.gms.ads.internal.util.client.zzb.zzay(str);
                break;
            default:
                com.google.android.gms.ads.internal.util.client.zzb.zzaA(str);
                break;
        }
        return super.onConsoleMessage(consoleMessage);
    }

    @Override // android.webkit.WebChromeClient
    public final boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebView.WebViewTransport webViewTransport = (WebView.WebViewTransport) resultMsg.obj;
        WebView webView = new WebView(view.getContext());
        webView.setWebViewClient(this.zzoA.zzgF());
        webViewTransport.setWebView(webView);
        resultMsg.sendToTarget();
        return true;
    }

    @Override // android.webkit.WebChromeClient
    public final void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
        long j = 5242880 - totalUsedQuota;
        if (j <= 0) {
            quotaUpdater.updateQuota(currentQuota);
            return;
        }
        if (currentQuota == 0) {
            if (estimatedSize > j || estimatedSize > 1048576) {
                estimatedSize = 0;
            }
        } else if (estimatedSize == 0) {
            estimatedSize = Math.min(Math.min(131072L, j) + currentQuota, 1048576L);
        } else {
            if (estimatedSize <= Math.min(1048576 - currentQuota, j)) {
                currentQuota += estimatedSize;
            }
            estimatedSize = currentQuota;
        }
        quotaUpdater.updateQuota(estimatedSize);
    }

    @Override // android.webkit.WebChromeClient
    public final void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        if (callback != null) {
            callback.invoke(origin, zzha(), true);
        }
    }

    @Override // android.webkit.WebChromeClient
    public final void onHideCustomView() {
        com.google.android.gms.ads.internal.overlay.zzc zzcVarZzgD = this.zzoA.zzgD();
        if (zzcVarZzgD == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not get ad overlay when hiding custom view.");
        } else {
            zzcVarZzgD.zzer();
        }
    }

    @Override // android.webkit.WebChromeClient
    public final boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    @Override // android.webkit.WebChromeClient
    public final boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    @Override // android.webkit.WebChromeClient
    public final boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    @Override // android.webkit.WebChromeClient
    public final boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
        return zza(zzc(webView), url, message, defaultValue, null, result, true);
    }

    public final void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
        long j = 131072 + spaceNeeded;
        if (5242880 - totalUsedQuota < j) {
            quotaUpdater.updateQuota(0L);
        } else {
            quotaUpdater.updateQuota(j);
        }
    }

    @Override // android.webkit.WebChromeClient
    public final void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        zza(view, -1, customViewCallback);
    }

    protected final void zza(View view, int i, WebChromeClient.CustomViewCallback customViewCallback) {
        com.google.android.gms.ads.internal.overlay.zzc zzcVarZzgD = this.zzoA.zzgD();
        if (zzcVarZzgD == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaC("Could not get ad overlay when showing custom view.");
            customViewCallback.onCustomViewHidden();
        } else {
            zzcVarZzgD.zza(view, customViewCallback);
            zzcVarZzgD.setRequestedOrientation(i);
        }
    }

    protected boolean zza(Context context, String str, String str2, String str3, JsResult jsResult, JsPromptResult jsPromptResult, boolean z) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(str);
            if (z) {
                zza(context, builder, str2, str3, jsPromptResult);
            } else {
                zza(builder, str2, jsResult);
            }
            return true;
        } catch (WindowManager.BadTokenException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to display Dialog.", e);
            return true;
        }
    }
}
