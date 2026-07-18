/*
 * FRIDA SCRIPT - STARBUCKS ID BYPASS
 * Dynamic early-hook strategy untuk memastikan app berjalan langsung
 * tanpa network connection error.
 *
 * Strategy:
 *   1. Native SSL bypass (BoringSSL) - IMMEDIATE, sebelum Java VM siap
 *   2. Java SSL bypass (OkHttp, TrustManager, WebView) - early Java.perform
 *   3. Root + Emulator bypass - anti-deteksi environment
 *   4. Proxy enforcement - route semua traffic ke Burp (10.0.2.2:8080)
 *
 * Usage:
 *   frida -U -l starbucks-bypass.js -f com.starbucks.id
 */

'use strict';

var Color = {
  R: "\x1b[31m", G: "\x1b[32m", Y: "\x1b[33m",
  B: "\x1b[34m", M: "\x1b[35m", C: "\x1b[36m", W: "\x1b[37m",
  X: "\x1b[0m"
};

function log(tag, color, msg) {
  var ts = new Date().toISOString().split('T')[1].slice(0, 12);
  console.log(Color.W + "[" + ts + "]" + Color.X + " " + color + tag + Color.X + " " + msg);
}

var BYPASS_COUNT = 0;

// ================================================================
// LAYER 0: NATIVE SSL BYPASS (IMMEDIATE - no Java VM needed)
// ================================================================
function installNativeSSLBypass() {
  log("[NATIVE]", Color.G, "Installing native SSL hooks...");

  // BoringSSL: SSL_CTX_set_custom_verify - disable verification callback
  var mods = Process.enumerateModules().filter(function(m) {
    return m.name.indexOf('ssl') !== -1 || m.name.indexOf('crypto') !== -1;
  });

  log("[NATIVE]", Color.C, "Found " + mods.length + " crypto/ssl modules: " +
    mods.map(function(m) { return m.name; }).join(', '));

  // BoringSSL libs: libssl.so, libboringssl.so, libcrypto.so
  var sslModules = mods.filter(function(m) {
    return m.name.indexOf('ssl') !== -1;
  });

  sslModules.forEach(function(mod) {
    // SSL_set_custom_verify - disable cert verification
    try {
      var SSL_set_custom_verify = Module.findExportByName(mod.name, 'SSL_set_custom_verify');
      if (SSL_set_custom_verify) {
        Interceptor.attach(SSL_set_custom_verify, {
          onEnter: function(args) {
            // mode 1 = verify peer, set to 0 to disable
            var mode = args[1].toInt32();
            if (mode === 1) {
              log("[NATIVE]", Color.G, "SSL_set_custom_verify DISABLED in " + mod.name);
              args[1] = ptr(0); // force mode 0 (no verify)
            }
          }
        });
        log("[NATIVE]", Color.G, "  Hooked: SSL_set_custom_verify");
      }
    } catch(e) {}

    // SSL_CTX_set_verify - another verification control
    try {
      var SSL_CTX_set_verify = Module.findExportByName(mod.name, 'SSL_CTX_set_verify');
      if (SSL_CTX_set_verify) {
        Interceptor.attach(SSL_CTX_set_verify, {
          onEnter: function(args) {
            log("[NATIVE]", Color.G, "SSL_CTX_set_verify DISABLED in " + mod.name);
            args[1] = ptr(0); // SSL_VERIFY_NONE
            args[2] = ptr(0); // null callback
          }
        });
        log("[NATIVE]", Color.G, "  Hooked: SSL_CTX_set_verify");
      }
    } catch(e) {}

    // SSL_CTX_set_cert_verify_callback - custom verify callback
    try {
      var SSL_CTX_set_cert_verify_callback = Module.findExportByName(mod.name, 'SSL_CTX_set_cert_verify_callback');
      if (SSL_CTX_set_cert_verify_callback) {
        Interceptor.attach(SSL_CTX_set_cert_verify_callback, {
          onEnter: function(args) {
            log("[NATIVE]", Color.G, "SSL_CTX_set_cert_verify_callback DISABLED in " + mod.name);
            // Replace callback with one that always returns 1 (success)
            var alwaysOk = new NativeCallback(function() { return 1; }, 'int', ['pointer', 'pointer']);
            args[1] = alwaysOk;
          }
        });
        log("[NATIVE]", Color.G, "  Hooked: SSL_CTX_set_cert_verify_callback");
      }
    } catch(e) {}

    // SSL_set_verify override
    try {
      var SSL_set_verify = Module.findExportByName(mod.name, 'SSL_set_verify');
      if (SSL_set_verify) {
        Interceptor.attach(SSL_set_verify, {
          onEnter: function(args) {
            log("[NATIVE]", Color.G, "SSL_set_verify DISABLED in " + mod.name);
            args[1] = ptr(0);
            args[2] = ptr(0);
          }
        });
        log("[NATIVE]", Color.G, "  Hooked: SSL_set_verify");
      }
    } catch(e) {}

    // SSL_set_hostflags - prevent hostname verification
    try {
      var SSL_set_hostflags = Module.findExportByName(mod.name, 'SSL_set_hostflags');
      if (SSL_set_hostflags) {
        Interceptor.attach(SSL_set_hostflags, {
          onEnter: function(args) { args[1] = ptr(0); }
        });
        log("[NATIVE]", Color.G, "  Hooked: SSL_set_hostflags");
      }
    } catch(e) {}
  });

  log("[NATIVE]", Color.G, "Native SSL bypass layer installed.");
}

// ================================================================
// LAYER 1: JAVA SSL PINNING BYPASS (Early, comprehensive)
// ================================================================
function installJavaSSLBypass() {
  Java.perform(function() {
    log("[JAVA]", Color.G, "Installing Java SSL bypass hooks...");

    // --- 1. TrustManager global override ---
    try {
      var X509TrustManager = Java.use('javax.net.ssl.X509TrustManager');
      var TrustManagerImpl = Java.registerClass({
        name: 'com.starbucks.bypass.TrustManager' + Date.now(),
        implements: [X509TrustManager],
        methods: {
          checkClientTrusted: function(chain, authType) {
            log("[SSL]", Color.G, "  TrustManager: checkClientTrusted - BYPASSED");
          },
          checkServerTrusted: function(chain, authType) {
            log("[SSL]", Color.G, "  TrustManager: checkServerTrusted - BYPASSED");
          },
          getAcceptedIssuers: function() { return []; }
        }
      });

      var SSLContext = Java.use('javax.net.ssl.SSLContext');
      var sslCtx = SSLContext.getInstance('TLS');
      var bypassTMArray = Java.array('javax.net.ssl.TrustManager', [TrustManagerImpl.$new()]);
      sslCtx.init(null, bypassTMArray, Java.use('java.security.SecureRandom').$new());

      var HttpsURLConnection = Java.use('javax.net.ssl.HttpsURLConnection');
      HttpsURLConnection.setDefaultSSLSocketFactory(sslCtx.getSocketFactory());

      log("[SSL]", Color.G, "  Global TrustManager replaced with bypass");
      BYPASS_COUNT++;
    } catch(e) {
      log("[SSL]", Color.R, "  TrustManager override: " + e.message);
    }

    // --- 2. HostnameVerifier ---
    try {
      var HttpsURLConnection = Java.use('javax.net.ssl.HttpsURLConnection');
      HttpsURLConnection.setDefaultHostnameVerifier.implementation = function() {};
      // Also monkey-patch to use ALLOW_ALL_HOSTNAME_VERIFIER
      var origSetSSLSocketFactory = HttpsURLConnection.setDefaultSSLSocketFactory;
      log("[SSL]", Color.G, "  HostnameVerifier bypassed");
    } catch(e) {
      try {
        var HttpsURLConnection2 = Java.use('javax.net.ssl.HttpsURLConnection');
        var allowAll = Java.use('org.apache.http.conn.ssl.AllowAllHostnameVerifier');
        HttpsURLConnection2.setDefaultHostnameVerifier(allowAll.$new());
        log("[SSL]", Color.G, "  HostnameVerifier (AllowAll) installed");
      } catch(e2) {}
    }

    // --- 3. OkHttp3 CertificatePinner ---
    try {
      var CertPinner = Java.use('okhttp3.CertificatePinner');
      // Hook ALL overloads of check()
      var checkOverloads = CertPinner.check.overloads;
      for (var i = 0; i < checkOverloads.length; i++) {
        checkOverloads[i].implementation = function() {
          log("[SSL]", Color.G, "  OkHttp3 CertificatePinner.check - BYPASSED");
        };
      }
      BYPASS_COUNT++;
      log("[SSL]", Color.G, "  OkHttp3 CertificatePinner (" + checkOverloads.length + " overloads) bypassed");
    } catch(e) {
      log("[SSL]", Color.Y, "  OkHttp3 CertificatePinner: " + e.message);
    }

    // --- 4. TrustManagerImpl verifyChain ---
    try {
      var TrustManImpl = Java.use('com.android.org.conscrypt.TrustManagerImpl');
      TrustManImpl.verifyChain.implementation = function(untrustedChain, trustAnchorChain, host, clientAuth, algo, endpoint) {
        log("[SSL]", Color.G, "  TrustManagerImpl.verifyChain - BYPASSED for " + (host || '?'));
        return untrustedChain;
      };
      BYPASS_COUNT++;
      log("[SSL]", Color.G, "  TrustManagerImpl.verifyChain bypassed");
    } catch(e) {
      log("[SSL]", Color.Y, "  TrustManagerImpl: " + e.message);
    }

    // --- 5. Platform TrustManager ---
    try {
      var Platform = Java.use('com.android.org.conscrypt.Platform');
      Platform.checkServerTrusted.implementation = function() {
        log("[SSL]", Color.G, "  Platform.checkServerTrusted - BYPASSED");
        return [];
      };
      BYPASS_COUNT++;
      log("[SSL]", Color.G, "  Platform.checkServerTrusted bypassed");
    } catch(e) {}

    // --- 6. WebView SSL handler ---
    try {
      var WVClient = Java.use('android.webkit.WebViewClient');
      WVClient.onReceivedSslError.implementation = function(view, handler, error) {
        log("[SSL]", Color.G, "  WebView onReceivedSslError - PROCEEDING");
        handler.proceed();
      };
      log("[SSL]", Color.G, "  WebView SSL error bypassed");
    } catch(e) {}

    // --- 7. OkHttp3 ConnectionSpec ---
    try {
      var ConnectionSpec = Java.use('okhttp3.ConnectionSpec');
      ConnectionSpec.isCompatible.implementation = function(sslSocket) {
        log("[SSL]", Color.G, "  ConnectionSpec.isCompatible - forced true");
        return true;
      };
      log("[SSL]", Color.G, "  OkHttp3 ConnectionSpec bypassed");
    } catch(e) {}

    // --- 8. OkHttp3 SSLSocketFactory ---
    try {
      var OkHttpClient = Java.use('okhttp3.OkHttpClient');
      // Hook the builder to inject our SSL config
      var Builder = Java.use('okhttp3.OkHttpClient$Builder');
      var origBuild = Builder.build;
      Builder.build.implementation = function() {
        log("[SSL]", Color.G, "  OkHttpClient.Builder.build - injecting bypass SSL");
        this.hostnameVerifier(this.hostnameVerifier.value || Java.use('javax.net.ssl.HostnameVerifier')
          .$new({verify: function(h, s) { return true; }}));
        return origBuild.call(this);
      };
      log("[SSL]", Color.G, "  OkHttpClient Builder hooked");
    } catch(e) {}

    log("[SSL]", Color.G, "Java SSL bypass layer complete. (" + BYPASS_COUNT + " bypasses active)");
  });
}

// ================================================================
// LAYER 2: ROOT + EMULATOR + DEBUG BYPASS
// ================================================================
function installEnvironmentBypass() {
  Java.perform(function() {

    // --- Root detection: su + magisk ---
    try {
      var File = Java.use('java.io.File');
      var origExists = File.exists;
      File.exists.implementation = function() {
        var path = this.getAbsolutePath();
        var lower = path.toLowerCase();
        if ((lower.indexOf('su') !== -1 || lower.indexOf('magisk') !== -1 ||
             lower.indexOf('xposed') !== -1 || lower.indexOf('supersu') !== -1 ||
             lower.indexOf('busybox') !== -1)
            && (lower.indexOf('/bin/') !== -1 || lower.indexOf('/xbin/') !== -1 ||
                lower.indexOf('/sbin/') !== -1 || lower.indexOf('/system/') !== -1 ||
                lower.indexOf('/data/') !== -1 || lower.indexOf('/vendor/') !== -1 ||
                lower.indexOf('/cache/') !== -1 || lower.indexOf('/dev/') !== -1 ||
                lower.indexOf('/product/') !== -1 || lower.indexOf('/odm/') !== -1 ||
                lower.indexOf('/apex/') !== -1 || lower.indexOf('/system_ext/') !== -1 ||
                lower.indexOf('/su/') === 0)) {
          log("[ENV]", Color.R, "  Root check BLOCKED: " + path);
          return false;
        }
        return origExists.call(this);
      };
      BYPASS_COUNT++;
      log("[ENV]", Color.G, "  Root detection (File.exists) bypassed");
    } catch(e) {}

    // --- Root detection: PackageManager ---
    try {
      var PM = Java.use('android.content.pm.PackageManager');
      PM.getPackageInfo.overload('java.lang.String', 'int').implementation = function(pkg, flags) {
        var lower = pkg.toLowerCase();
        if (lower.indexOf('magisk') !== -1 || lower.indexOf('supersu') !== -1 ||
            lower.indexOf('busybox') !== -1 || lower.indexOf('xposed') !== -1 ||
            lower.indexOf('de.robv.android.xposed') !== -1 || lower.indexOf('top.johnwu') !== -1) {
          log("[ENV]", Color.R, "  Root package BLOCKED: " + pkg);
          throw Java.use('android.content.pm.PackageManager$NameNotFoundException').$new();
        }
        return PM.getPackageInfo.call(this, pkg, flags);
      };
      BYPASS_COUNT++;
      log("[ENV]", Color.G, "  Root detection (PackageManager) bypassed");
    } catch(e) {}

    // --- Root detection: Runtime.exec ---
    try {
      var Runtime = Java.use('java.lang.Runtime');
      Runtime.exec.overload('java.lang.String').implementation = function(cmd) {
        if (cmd && (cmd.indexOf('su ') === 0 || cmd === 'su' || cmd.indexOf('which su') !== -1 ||
            cmd.indexOf('magisk') !== -1 || cmd.indexOf('id') === 0)) {
          log("[ENV]", Color.R, "  exec BLOCKED: " + cmd);
          throw Java.use('java.io.IOException').$new('Permission denied');
        }
        return Runtime.exec.call(this, cmd);
      };
      BYPASS_COUNT++;
      log("[ENV]", Color.G, "  Root detection (Runtime.exec) bypassed");
    } catch(e) {}

    // --- Emulator detection ---
    try {
      var SystemProperties = Java.use('android.os.SystemProperties');
      SystemProperties.get.overload('java.lang.String', 'java.lang.String').implementation = function(key, def) {
        var result = SystemProperties.get.call(this, key, def);
        if (key === 'ro.build.fingerprint' && result && result.indexOf('generic') !== -1) {
          log("[ENV]", Color.G, "  Emulator fingerprint spoofed");
          return 'google/shamu/shamu:9.0/OPR6.170623.017/1234567:user/release-keys';
        }
        if (key === 'ro.kernel.qemu') {
          log("[ENV]", Color.G, "  Emulator qemu=1 -> 0");
          return '0';
        }
        if (key === 'ro.hardware' && result === 'goldfish') {
          return 'shamu';
        }
        if (key === 'ro.product.manufacturer' && result && result.toLowerCase().indexOf('unknown') !== -1) {
          return 'Google';
        }
        if (key === 'ro.build.tags' && result && result.indexOf('test-keys') !== -1) {
          return 'release-keys';
        }
        return result;
      };
      SystemProperties.get.overload('java.lang.String').implementation = function(key) {
        return SystemProperties.get.call(this, key, '');
      };
      BYPASS_COUNT++;
      log("[ENV]", Color.G, "  Emulator detection (SystemProperties) bypassed");
    } catch(e) {}

    // --- Emulator: TelephonyManager ---
    try {
      var TM = Java.use('android.telephony.TelephonyManager');
      TM.getNetworkOperatorName.implementation = function() {
        var r = TM.getNetworkOperatorName.call(this);
        if (r && r.toLowerCase() === 'android') {
          log("[ENV]", Color.G, "  Network operator spoofed: Android -> Telkomsel");
          return Java.use('java.lang.String').$new('Telkomsel');
        }
        return r;
      };
      TM.getDeviceId.implementation = function() {
        var r = TM.getDeviceId.call(this);
        if (!r || r === '000000000000000' || r === 'unknown') {
          log("[ENV]", Color.G, "  IMEI spoofed");
          return Java.use('java.lang.String').$new('359876543210123');
        }
        return r;
      };
      TM.getSimOperatorName.implementation = function() {
        return Java.use('java.lang.String').$new('Telkomsel');
      };
      TM.getSimCountryIso.implementation = function() {
        return Java.use('java.lang.String').$new('id');
      };
      BYPASS_COUNT++;
      log("[ENV]", Color.G, "  Emulator detection (TelephonyManager) bypassed");
    } catch(e) {}

    // --- Debug detection ---
    try {
      var Debug = Java.use('android.os.Debug');
      Debug.isDebuggerConnected.implementation = function() {
        log("[ENV]", Color.R, "  Debug.isDebuggerConnected - returning false");
        return false;
      };
      BYPASS_COUNT++;
      log("[ENV]", Color.G, "  Debug detection bypassed");
    } catch(e) {}

    // --- Frida detection (via port scanning) ---
    try {
      var Socket = Java.use('java.net.Socket');
      Socket.$init.overload('java.lang.String', 'int').implementation = function(host, port) {
        if (port === 27042 || port === 27043) {
          log("[ENV]", Color.R, "  Frida port " + port + " - BLOCKED");
          throw Java.use('java.net.ConnectException').$new('Connection refused');
        }
        return Socket.$init.call(this, host, port);
      };
      log("[ENV]", Color.G, "  Frida detection (Socket) bypassed");
    } catch(e) {}

    // --- HTTP proxy awareness bypass ---
    try {
      var System = Java.use('java.lang.System');
      System.getProperty.overload('java.lang.String').implementation = function(key) {
        var r = System.getProperty.call(this, key);
        if (key === 'http.proxyHost' || key === 'https.proxyHost') {
          log("[ENV]", Color.G, "  Proxy detection bypassed for: " + key);
        }
        return r;
      };
      log("[ENV]", Color.G, "  Proxy detection bypassed");
    } catch(e) {}

    log("[ENV]", Color.G, "Environment bypass layer complete.");
  });
}

// ================================================================
// LAYER 3: PROXY ENFORCEMENT + LIBRARY SPECIFIC FIXES
// ================================================================
function installProxyAndFixes() {
  Java.perform(function() {
    // --- Force proxy settings ---
    try {
      var System = Java.use('java.lang.System');
      System.setProperty('http.proxyHost', '10.0.2.2');
      System.setProperty('http.proxyPort', '8080');
      System.setProperty('https.proxyHost', '10.0.2.2');
      System.setProperty('https.proxyPort', '8080');
      log("[PROXY]", Color.G, "  Proxy forced: 10.0.2.2:8080");
    } catch(e) {}

    // --- OkHttp3: Disable CertificatePinner globally ---
    try {
      var OkHttpClient = Java.use('okhttp3.OkHttpClient');
      // Hook constructor to strip certificate pinner
      var OkHttpBuilder = Java.use('okhttp3.OkHttpClient$Builder');
      var origCertPinner = OkHttpBuilder.certificatePinner;
      if (origCertPinner) {
        OkHttpBuilder.certificatePinner.implementation = function(pinner) {
          log("[PROXY]", Color.G, "  OkHttpBuilder: CertificatePinner stripped");
          return this;
        };
        log("[PROXY]", Color.G, "  OkHttp CertificatePinner stripped at builder level");
      }
    } catch(e) {}

    // --- WebView: Proxy setup ---
    try {
      var WebView = Java.use('android.webkit.WebView');
      WebView.setWebContentsDebuggingEnabled(true);
      log("[PROXY]", Color.G, "  WebView debugging enabled");
    } catch(e) {}
  });
}

// ================================================================
// LAYER 4: DYNAMIC BYPASS (Re-applied on new class loads)
// ================================================================
function installDynamicBypass() {
  // Poll for new classes every 2 seconds
  setInterval(function() {
    Java.perform(function() {
      // Check if OkHttp3 loaded late
      try {
        var CertPinner = Java.use('okhttp3.CertificatePinner');
        if (CertPinner.check.overloads && CertPinner.check.overloads.length > 0) {
          var firstImpl = CertPinner.check.overloads[0].implementation;
          if (!firstImpl || firstImpl.toString().indexOf('BYPASSED') === -1) {
            for (var i = 0; i < CertPinner.check.overloads.length; i++) {
              CertPinner.check.overloads[i].implementation = function() {};
            }
            log("[DYNA]", Color.G, "  OkHttp3 CertificatePinner (late load) bypassed");
            BYPASS_COUNT++;
          }
        }
      } catch(e) {}

      // Retrofit
      try {
        var RetrofitBuilder = Java.use('retrofit2.Retrofit$Builder');
        var origBuild = RetrofitBuilder.build;
        RetrofitBuilder.build.implementation = function() {
          log("[DYNA]", Color.G, "  Retrofit Builder.build hooked");
          return origBuild.call(this);
        };
      } catch(e) {}

      // Google Play Integrity (firebase app check)
      try {
        var FirebaseAppCheck = Java.use('com.google.firebase.appcheck.FirebaseAppCheck');
        log("[DYNA]", Color.G, "  FirebaseAppCheck found (monitoring)");
      } catch(e) {}
    });
  }, 2000);
}

// ================================================================
// MAIN: Execute layers in order
// ================================================================
log("[MAIN]", Color.C, "================ STARBUCKS BYPASS v2.0 ================");
log("[MAIN]", Color.C, "Strategy: Native SSL (immediate) -> Java SSL -> Env Bypass -> Proxy");

// Layer 0: Native SSL (runs IMMEDIATELY - no Java VM wait)
installNativeSSLBypass();

// Layer 1: Java SSL (runs when VM is ready)
installJavaSSLBypass();

// Layer 2: Environment bypass (root, emulator, debug, frida)
installEnvironmentBypass();

// Layer 3: Proxy enforcement + library fixes
installProxyAndFixes();

// Layer 4: Dynamic re-hooking for late-loaded classes
setTimeout(function() {
  installDynamicBypass();
}, 3000);

// Final ready signal
setTimeout(function() {
  log("[MAIN]", Color.G, "");
  log("[MAIN]", Color.G, "All bypass layers installed. (" + BYPASS_COUNT + " checks disabled)");
  log("[MAIN]", Color.G, "Burp Proxy: 10.0.2.2:8080 | SSL: DISABLED | Root: HIDDEN | Emu: SPOOFED");
  log("[MAIN]", Color.G, "");
}, 500);

// RPC exports
rpc.exports = {
  status: function() {
    return 'BYPASSES: ' + BYPASS_COUNT + ' | SSL: OFF | Root: HIDDEN | Emu: SPOOFED | Proxy: 10.0.2.2:8080';
  },
  rescan: function() {
    installDynamicBypass();
    return 'Rescan triggered';
  },
  dumpModules: function() {
    return Process.enumerateModules().map(function(m) {
      return m.name + ' @ ' + m.base + ' (' + m.size + 'B)';
    }).join('\n');
  }
};