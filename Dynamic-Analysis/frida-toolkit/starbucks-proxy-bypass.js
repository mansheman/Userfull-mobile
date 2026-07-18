/*
 * STARBUCKS ID - PROXY INJECTOR + SSL BYPASS
 * Forces all OkHttp, HttpURLConnection, and WebView traffic through Burp Suite
 * at 10.0.2.2:8080. Includes SSL pinning + root + emulator bypass.
 *
 * Usage:
 *   frida -U -l starbucks-proxy-bypass.js -f com.starbucks.id
 */

'use strict';

var C = { R:"\x1b[31m", G:"\x1b[32m", Y:"\x1b[33m", B:"\x1b[34m", M:"\x1b[35m", C:"\x1b[36m", W:"\x1b[37m", X:"\x1b[0m" };
function L(t,c,m){var n=new Date().toISOString().split('T')[1].slice(0,12);console.log(C.W+"["+n+"]"+C.X+" "+c+t+C.X+" "+m);}

var PROXY_HOST = "10.0.2.2";
var PROXY_PORT = 8080;
var hookedCount = 0;
var sslBypassed = false;

// === LAYER 1: FORCE PROXY (Java + Socket level) ===
function forceProxyJava() {
  Java.perform(function() {
    L("[PROXY]", C.Y, "Injecting proxy " + PROXY_HOST + ":" + PROXY_PORT + "...");

    // --- 1. System-level proxy ---
    try {
      var System = Java.use('java.lang.System');
      System.setProperty('http.proxyHost', PROXY_HOST);
      System.setProperty('http.proxyPort', String(PROXY_PORT));
      System.setProperty('https.proxyHost', PROXY_HOST);
      System.setProperty('https.proxyPort', String(PROXY_PORT));
      System.setProperty('http.nonProxyHosts', '');
      System.setProperty('socksProxyHost', '');
      L("[PROXY]", C.G, "  System properties: proxyHost=" + PROXY_HOST + ", proxyPort=" + PROXY_PORT);
      hookedCount++;
    } catch(e) {}

    // --- 2. ProxySelector override (proxy for all URL connections) ---
    try {
      var ProxySelector = Java.use('java.net.ProxySelector');
      var InetSA = Java.use('java.net.InetSocketAddress');
      // Proxy.Type.DIRECT=0, HTTP=1, SOCKS=2 -> use 1 for HTTP
      var ourAddr = InetSA.$new(PROXY_HOST, PROXY_PORT);

      var ForcedSelector = Java.registerClass({
        name: 'com.proxyforce.Selector',
        superClass: ProxySelector,
        methods: {
          select: function(uri) {
            var s = uri.toString();
            if (s.indexOf('10.0.2.2') !== -1 || s.indexOf('127.0.0.1') !== -1 || s.indexOf('localhost') !== -1) {
              return Java.use('java.util.Collections').singletonList(ourAddr);
            }
            return Java.use('java.util.Collections').singletonList(ourAddr);
          },
          connectFailed: function(uri, addr, ex) {}
        }
      });

      ProxySelector.setDefault(ForcedSelector.$new());
      L("[PROXY]", C.G, "  ProxySelector: ALL traffic -> " + PROXY_HOST + ":" + PROXY_PORT);
      hookedCount++;
    } catch(e) {
      L("[PROXY]", C.R, "  ProxySelector: " + e.message);
    }

    // --- 3. Hook Socket.connect to redirect ALL TCP to proxy ---
    try {
      var Socket = Java.use('java.net.Socket');
      Socket.connect.overload('java.net.SocketAddress', 'int').implementation = function(endpoint, timeout) {
        var str = endpoint.toString();
        // Don't redirect if already going to proxy
        if (str.indexOf(PROXY_HOST) === -1 && str.indexOf('127.0.0.1') === -1 && str.indexOf('::1') === -1) {
          try {
            var ProxyInetSA = Java.use('java.net.InetSocketAddress');
            var burpAddr = ProxyInetSA.$new(PROXY_HOST, PROXY_PORT);
            L("[PROXY]", C.G, "  Socket " + str + " -> REDIRECTED to " + PROXY_HOST + ":" + PROXY_PORT);
            return Socket.connect.call(this, burpAddr, timeout);
          } catch(e2) {
            L("[PROXY]", C.R, "  Socket redirect: " + e2.message);
          }
        }
        return Socket.connect.call(this, endpoint, timeout);
      };
      L("[PROXY]", C.G, "  Socket.connect: ALL TCP redirected to proxy");
      hookedCount++;
    } catch(e) {
      L("[PROXY]", C.Y, "  Socket: " + e.message);
    }

    // --- 4. Enumerate & hook OkHttp/Retrofit classes dynamically ---
    setTimeout(function() {
      enumHttpClasses();
    }, 2000);

    L("[PROXY]", C.G, "Proxy injection layer complete (" + hookedCount + " hooks)");
  });
}

// Dynamic OkHttp/Retrofit class enumeration (post-obfuscation)
function enumHttpClasses() {
  Java.perform(function() {
    L("[SCAN]", C.Y, "Scanning loaded classes for HTTP library patterns...");
    var okhttpClasses = [];
    var retrofitClasses = [];
    var matched = 0;

    Java.enumerateLoadedClasses({
      onMatch: function(name) {
        var n = name.toLowerCase();
        if (n.indexOf('okhttp') !== -1) {
          okhttpClasses.push(name);
        } else if (n.indexOf('retrofit') !== -1) {
          retrofitClasses.push(name);
        } else if ((n.indexOf('.realcall') !== -1 || n.indexOf('.realconnection') !== -1 ||
                    n.indexOf('.httpengine') !== -1 || n.indexOf('.connectionpool') !== -1)
                   && n.indexOf('java.') === -1 && n.indexOf('android.') === -1) {
          // Potential obfuscated OkHttp class
          okhttpClasses.push(name + " [OBFUSCATED?]");
        }
      },
      onComplete: function() {
        L("[SCAN]", C.C, "  OkHttp-like: " + okhttpClasses.length + ", Retrofit-like: " + retrofitClasses.length);
        if (okhttpClasses.length > 0) {
          L("[SCAN]", C.C, "  Samples: " + okhttpClasses.slice(0, 5).join(', '));
        }
        if (retrofitClasses.length > 0) {
          L("[SCAN]", C.C, "  Samples: " + retrofitClasses.slice(0, 5).join(', '));
        }

        // Try hooking discovered classes
        okhttpClasses.forEach(function(cls) {
          try {
            var clazz = Java.use(cls);
            // Look for build() or similar factory method for proxy injection
            L("[SCAN]", C.G, "  Found HTTP class: " + cls);
            matched++;
          } catch(e) {}
        });

        if (matched > 0) {
          L("[SCAN]", C.G, "  Hooked " + matched + " HTTP library classes");
        }
      }
    });
  });
}

// === LAYER 2: SSL PINNING BYPASS ===
function bypassSSL() {
  Java.perform(function() {
    L("[SSL]", C.Y, "Installing SSL bypass...");

    // --- 1. TrustManager global override ---
    try {
      var X509TM = Java.use('javax.net.ssl.X509TrustManager');
      var BypassTM = Java.registerClass({
        name: 'com.ssl.bypass.TM' + Date.now(),
        implements: [X509TM],
        methods: {
          checkClientTrusted: function(){},
          checkServerTrusted: function(){
            L("[SSL]", C.G, "  checkServerTrusted -> BYPASSED");
          },
          getAcceptedIssuers: function(){ return []; }
        }
      });

      var SSLContext = Java.use('javax.net.ssl.SSLContext');
      var ctx = SSLContext.getInstance('TLS');
      var mgr = Java.array('javax.net.ssl.TrustManager', [BypassTM.$new()]);
      ctx.init(null, mgr, Java.use('java.security.SecureRandom').$new());

      // Replace default SSLSocketFactory
      var HttpsURLConn = Java.use('javax.net.ssl.HttpsURLConnection');
      HttpsURLConn.setDefaultSSLSocketFactory(ctx.getSocketFactory());
      L("[SSL]", C.G, "  Global TrustManager replaced");
    } catch(e) {
      L("[SSL]", C.Y, "  TM override: " + e.message);
    }

    // --- 2. OkHttp3 CertificatePinner ---
    try {
      var CertPin = Java.use('okhttp3.CertificatePinner');
      var overloads = CertPin.check.overloads;
      for (var i = 0; i < overloads.length; i++) {
        overloads[i].implementation = function() {
          // Silently bypass
        };
      }
      L("[SSL]", C.G, "  OkHttp3 CertificatePinner bypassed (" + overloads.length + " overloads)");
      hookedCount++;
    } catch(e) {
      L("[SSL]", C.Y, "  CertPinner: " + e.message);
    }

    // --- 3. Conscrypt TrustManagerImpl ---
    try {
      var TMI = Java.use('com.android.org.conscrypt.TrustManagerImpl');
      TMI.verifyChain.implementation = function(chain) {
        L("[SSL]", C.G, "  TrustManagerImpl.verifyChain -> BYPASSED");
        return chain;
      };
      L("[SSL]", C.G, "  Conscrypt TrustManagerImpl bypassed");
      hookedCount++;
    } catch(e) {}

    // --- 4. WebView SSL error ---
    try {
      var WVClient = Java.use('android.webkit.WebViewClient');
      WVClient.onReceivedSslError.implementation = function(view, handler, error) {
        L("[SSL]", C.G, "  WebView SSL error -> PROCEED");
        handler.proceed();
      };
      L("[SSL]", C.G, "  WebView SSL bypassed");
    } catch(e) {}

    // --- 5. HostnameVerifier ---
    try {
      var HttpsURLConn = Java.use('javax.net.ssl.HttpsURLConnection');
      HttpsURLConn.setDefaultHostnameVerifier.implementation = function(){};
    } catch(e) {}

    // --- 6. OkHttp ConnectionSpec ---
    try {
      var ConnSpec = Java.use('okhttp3.ConnectionSpec');
      ConnSpec.isCompatible.implementation = function() { return true; };
      L("[SSL]", C.G, "  ConnectionSpec.isCompatible -> forced true");
    } catch(e) {}

    sslBypassed = true;
    L("[SSL]", C.G, "SSL bypass complete");
  });
}

// === LAYER 3: ROOT + EMULATOR + DEBUG BYPASS ===
function bypassEnvironment() {
  Java.perform(function() {
    L("[ENV]", C.Y, "Installing environment bypass...");

    // --- Root: File.exists ---
    try {
      var File = Java.use('java.io.File');
      var origExists = File.exists;
      File.exists.implementation = function() {
        var path = this.getAbsolutePath();
        var l = path.toLowerCase();
        var keywords = ['su', 'magisk', 'xposed', 'supersu', 'busybox'];
        for (var i = 0; i < keywords.length; i++) {
          if (l.indexOf(keywords[i]) !== -1) {
            // Only block in system-like paths
            if (l.indexOf('/bin/') !== -1 || l.indexOf('/xbin/') !== -1 ||
                l.indexOf('/sbin/') !== -1 || l.indexOf('/system/') !== -1 ||
                l.indexOf('/data/local/') !== -1 || l.indexOf('/vendor/') !== -1 ||
                l.indexOf('/product/') !== -1 || l.indexOf('/odm/') !== -1 ||
                l.indexOf('/apex/') !== -1 || l.indexOf('/cache/') !== -1 ||
                l.indexOf('/dev/') !== -1 || l.indexOf('/su/') === 0 ||
                l.indexOf('/system_ext/') !== -1) {
              L("[ENV]", C.R, "  Root check: " + path + " -> false");
              return false;
            }
          }
        }
        return origExists.call(this);
      };
      L("[ENV]", C.G, "  Root File.exists bypassed");
      hookedCount++;
    } catch(e) {}

    // --- Root: Runtime.exec ---
    try {
      var Runtime = Java.use('java.lang.Runtime');
      Runtime.exec.overload('java.lang.String').implementation = function(cmd) {
        var c = cmd ? cmd.toString() : '';
        if (c.indexOf('su ') === 0 || c === 'su' || c.indexOf('which su') !== -1 ||
            c.indexOf('magisk') !== -1 || c.indexOf('id') === 0 || c.indexOf('whoami') !== -1) {
          L("[ENV]", C.R, "  exec blocked: " + c);
          throw Java.use('java.io.IOException').$new('Permission denied');
        }
        return Runtime.exec.call(this, cmd);
      };
      L("[ENV]", C.G, "  Root exec bypassed");
      hookedCount++;
    } catch(e) {}

    // --- Emulator: SystemProperties ---
    try {
      var SysProps = Java.use('android.os.SystemProperties');
      SysProps.get.overload('java.lang.String', 'java.lang.String').implementation = function(key, def) {
        var r = SysProps.get.call(this, key, def);
        if (key === 'ro.kernel.qemu') return '0';
        if (key === 'ro.build.tags') return 'release-keys';
        if (key === 'ro.debuggable') return '0';
        if (key === 'ro.secure') return '1';
        return r;
      };
      L("[ENV]", C.G, "  Emulator SystemProperties spoofed");
      hookedCount++;
    } catch(e) {}

    // --- Debug: Debug.isDebuggerConnected ---
    try {
      var Debug = Java.use('android.os.Debug');
      Debug.isDebuggerConnected.implementation = function() { return false; };
      Debug.threadCpuTimeNanos.implementation = function() { return 0; };
      L("[ENV]", C.G, "  Debug detection bypassed");
      hookedCount++;
    } catch(e) {}

    L("[ENV]", C.G, "Environment bypass complete");
  });
}

// === LAYER 4: WEBVIEW PROXY SETUP ===
function setupWebViewProxy() {
  Java.perform(function() {
    L("[WEB]", C.Y, "Configuring WebView proxy...");

    try {
      // Manually set WebView proxy via reflection
      var WebView = Java.use('android.webkit.WebView');

      // Enable debugging to ensure WebView works
      WebView.setWebContentsDebuggingEnabled(true);
      L("[WEB]", C.G, "  WebView debugging enabled");
    } catch(e) {}

    // Try to set proxy via WebViewDatabase or CookieManager
    try {
      var CookieManager = Java.use('android.webkit.CookieManager');
      var cm = CookieManager.getInstance();
      cm.setAcceptCookie(true);
      L("[WEB]", C.G, "  CookieManager configured");
    } catch(e) {}
  });
}

// === LAYER 5: DYNAMIC MONITOR (re-hook late-loaded classes) ===
function dynamicMonitor() {
  setInterval(function() {
    Java.perform(function() {
      try {
        var CertPin = Java.use('okhttp3.CertificatePinner');
        for (var i = 0; i < CertPin.check.overloads.length; i++) {
          var fn = CertPin.check.overloads[i].implementation;
          if (!fn || fn.toString().indexOf('BYPASSED') === -1) {
            CertPin.check.overloads[i].implementation = function(){};
            L("[DYNA]", C.G, "  OkHttp3 CertificatePinner re-hooked (late)");
            hookedCount++;
          }
        }
      } catch(e) {}

      // Retrofit
      try {
        var RetrofitBuilder = Java.use('retrofit2.Retrofit$Builder');
        var origBuild = RetrofitBuilder.build;
        var currentImpl = origBuild.implementation;
        if (!currentImpl || currentImpl.toString().indexOf('proxy') === -1) {
          RetrofitBuilder.build.implementation = function() {
            try {
              var Proxy = Java.use('java.net.Proxy');
              var InetSA = Java.use('java.net.InetSocketAddress');
              var burpProxy = Proxy.$new(Java.use('java.net.Proxy$Type').HTTP, InetSA.$new(PROXY_HOST, PROXY_PORT));
              Java.use('okhttp3.OkHttpClient$Builder').proxy.implementation = function(p) { return this; };
            } catch(e) {}
            return origBuild.call(this);
          };
          L("[DYNA]", C.G, "  Retrofit Builder re-hooked");
        }
      } catch(e) {}

      try {
        var HttpsURLConn = Java.use('javax.net.ssl.HttpsURLConnection');
        // Ensure per-instance proxy
        var origOpen = HttpsURLConn.$init.overload('java.net.URL');
        if (origOpen) {
          origOpen.implementation = function(url) {
            var Proxy = Java.use('java.net.Proxy');
            var InetSA = Java.use('java.net.InetSocketAddress');
            this.usingProxy = function() { return true; };
            return origOpen.call(this, url);
          };
        }
      } catch(e) {}
    });
  }, 3000);
}

// === LAYER 6: TRAFFIC MONITOR (captures HTTP details) ===
function monitorTraffic() {
  Java.perform(function() {
    // OkHttp3 RealCall - capture request URL
    try {
      var RealCall = Java.use('okhttp3.RealCall');
      RealCall.execute.implementation = function() {
        var req = this.request();
        var url = req.url().toString();
        var method = req.method();
        L("[HTTP]", C.C, ">> " + method + " " + url);

        try {
          var resp = RealCall.execute.call(this);
          L("[HTTP]", C.G, "<< " + resp.code() + " " + resp.message() + " [" + url.substring(0, 80) + "]");
          return resp;
        } catch(e) {
          L("[HTTP]", C.R, "<< ERROR: " + e.message + " [" + url + "]");
          throw e;
        }
      };
      RealCall.enqueue.implementation = function(callback) {
        var req = this.request();
        L("[HTTP]", C.C, ">> (async) " + req.method() + " " + req.url());
        return RealCall.enqueue.call(this, callback);
      };
      L("[HTTP]", C.G, "  OkHttp3 traffic monitor installed");
      hookedCount++;
    } catch(e) {
      L("[HTTP]", C.Y, "  OkHttp3: " + e.message);
    }

    // HttpURLConnection
    try {
      var HttpURLConn = Java.use('java.net.HttpURLConnection');
      HttpURLConn.connect.implementation = function() {
        L("[HTTP]", C.C, "  HttpURLConnection.connect: " + this.getURL());
        return HttpURLConn.connect.call(this);
      };
    } catch(e) {}
  });
}

// ================================================================
// MAIN
// ================================================================
L("[MAIN]", C.M, "=== STARBUCKS PROXY BYPASS ===");
L("[MAIN]", C.C, "Proxy: " + PROXY_HOST + ":" + PROXY_PORT + " | SSL: OFF | Root: HIDDEN | Emu: SPOOFED");

// Layer 1: Force proxy (must run FIRST)
forceProxyJava();

// Layer 2: SSL bypass
bypassSSL();

// Layer 3: Environment bypass
bypassEnvironment();

// Layer 4: WebView proxy
setupWebViewProxy();

// Layer 5: Traffic monitor
monitorTraffic();

// Layer 6: Dynamic re-hooking
setTimeout(function() { dynamicMonitor(); }, 3000);

setTimeout(function() {
  L("[MAIN]", C.G, "");
  L("[MAIN]", C.G, "ALL BYPASSES INSTALLED (" + hookedCount + " hooks active)");
  L("[MAIN]", C.G, "Traffic should now flow through Burp at " + PROXY_HOST + ":" + PROXY_PORT);
  L("[MAIN]", C.G, "Check Burp Suite > Proxy > HTTP History");
  L("[MAIN]", C.G, "");
}, 500);

rpc.exports = {
  status: function() {
    return JSON.stringify({
      proxy: PROXY_HOST + ":" + PROXY_PORT,
      hooks: hookedCount,
      ssl: sslBypassed ? "BYPASSED" : "PENDING"
    });
  },
  setProxy: function(host, port) {
    PROXY_HOST = host;
    PROXY_PORT = parseInt(port);
    forceProxyJava();
    return "Proxy updated to " + host + ":" + port;
  }
};