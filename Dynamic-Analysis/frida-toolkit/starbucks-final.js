/*
 * STARBUCKS ID - FINAL BYPASS
 * App uses com.android.okhttp.* (OkHttp2 built-in) + WebView
 * Targets: SSL pinning, proxy redirect, root/emu/debug detection
 *
 * Usage: frida -U -l starbucks-final.js -f com.starbucks.id
 */

'use strict';
var C={R:"\x1b[31m",G:"\x1b[32m",Y:"\x1b[33m",B:"\x1b[34m",M:"\x1b[35m",C:"\x1b[36m",W:"\x1b[37m",X:"\x1b[0m"};
function L(t,c,m){console.log(C.W+"["+new Date().toISOString().split('T')[1].slice(0,12)+"]"+C.X+" "+c+t+C.X+" "+m);}

var H = "10.0.2.2", P = 8080, count = 0;
var BURP = Java.use('java.net.InetSocketAddress').$new(H, P);

// ================================================================
// LAYER 0: Global exception catch (prevent crash from our hooks)
// ================================================================
Java.perform(function() {
  try {
    var Thread = Java.use('java.lang.Thread');
    Thread.setDefaultUncaughtExceptionHandler.implementation = function(h) {};
  } catch(e) {}
});

// ================================================================
// LAYER 1: SOCKET REDIRECT (force ALL TCP through Burp)
// ================================================================
Java.perform(function() {
  try {
    var Socket = Java.use('java.net.Socket');
    var origConnect = Socket.connect;
    Socket.connect.overload('java.net.SocketAddress', 'int').implementation = function(ep, to) {
      var s = ep.toString();
      if (s.indexOf(H) === -1 && s.indexOf('127.0.0.1') === -1 && s.indexOf('::1') === -1) {
        L("[TUNNEL]", C.M, s + " -> " + H + ":" + P);
        return Socket.connect.call(this, BURP, to);
      }
      return Socket.connect.call(this, ep, to);
    };
    L("[CORE]", C.G, "Socket redirect: ALL TCP -> " + H + ":" + P);
    count++;
  } catch(e) {}
});

// ================================================================
// LAYER 2: OKHTTP2 SSL BYPASS (com.android.okhttp.*)
// ================================================================
Java.perform(function() {
  // --- CertificatePinner for Android's bundled OkHttp2 ---
  try {
    var CP = Java.use('com.android.okhttp.CertificatePinner');
    if (CP.check && CP.check.overloads) {
      for (var i = 0; i < CP.check.overloads.length; i++) {
        CP.check.overloads[i].implementation = function() {};
      }
      L("[SSL]", C.G, "OkHttp2 CertificatePinner: BYPASSED ("+CP.check.overloads.length+" overloads)");
      count++;
    }
  } catch(e) { L("[SSL]", C.Y, "OkHttp2 CertPin: "+e.message); }

  // --- OkHttp2 HostnameVerifier ---
  try {
    var OkHostname = Java.use('com.android.okhttp.internal.tls.OkHostnameVerifier');
    OkHostname.verify.implementation = function(h, s) { return true; };
    L("[SSL]", C.G, "OkHttp2 HostnameVerifier: BYPASSED");
    count++;
  } catch(e) {}

  // --- Conscrypt TrustManager ---
  try {
    var TMI = Java.use('com.android.org.conscrypt.TrustManagerImpl');
    TMI.verifyChain.implementation = function(c) { return c; };
    L("[SSL]", C.G, "Conscrypt verifyChain: BYPASSED");
    count++;
  } catch(e) {}

  // --- Global TrustManager replacement ---
  try {
    var X509TM = Java.use('javax.net.ssl.X509TrustManager');
    var BypassTM = Java.registerClass({
      name: 'com.frida.TMBypass' + Date.now(),
      implements: [X509TM],
      methods: {
        checkClientTrusted: function(){},
        checkServerTrusted: function(c,a){
          L("[SSL]", C.G, "TrustManager.checkServerTrusted -> OK");
        },
        getAcceptedIssuers: function(){ return []; }
      }
    });
    var ctx = Java.use('javax.net.ssl.SSLContext').getInstance('TLS');
    ctx.init(null, Java.array('javax.net.ssl.TrustManager',[BypassTM.$new()]),
      Java.use('java.security.SecureRandom').$new());
    Java.use('javax.net.ssl.HttpsURLConnection').setDefaultSSLSocketFactory(ctx.getSocketFactory());
    L("[SSL]", C.G, "Global TrustManager: REPLACED");
    count++;
  } catch(e) { L("[SSL]", C.Y, "Global TM: "+e.message); }

  // --- WebView SSL ---
  try {
    Java.use('android.webkit.WebViewClient').onReceivedSslError.implementation = function(v,h,e){
      L("[SSL]", C.G, "WebView SSL: PROCEED");
      h.proceed();
    };
    count++;
  } catch(e) {}

  L("[SSL]", C.G, "SSL bypass layer complete");
});

// ================================================================
// LAYER 3: ENVIRONMENT BYPASS
// ================================================================
Java.perform(function() {
  // Root via File.exists
  try {
    var File = Java.use('java.io.File');
    var orig = File.exists;
    File.exists.implementation = function() {
      var p = this.getAbsolutePath(), l = p.toLowerCase();
      var kw = ['su', 'magisk', 'xposed', 'supersu', 'busybox'];
      for (var i=0; i<kw.length; i++) {
        if (l.indexOf(kw[i])!==-1 && (l.indexOf('/bin/')!==-1||l.indexOf('/xbin/')!==-1||
          l.indexOf('/sbin/')!==-1||l.indexOf('/system/')!==-1||l.indexOf('/data/local/')!==-1||
          l.indexOf('/vendor/')!==-1||l.indexOf('/product/')!==-1||l.indexOf('/odm/')!==-1||
          l.indexOf('/apex/')!==-1||l.indexOf('/cache/')!==-1||l.indexOf('/dev/')!==-1||
          l.indexOf('/su/')===0||l.indexOf('/system_ext/')!==-1)){
          L("[ENV]",C.R,"Root: "+p+" -> false");
          return false;
        }
      }
      return orig.call(this);
    };
    count++;
    L("[ENV]", C.G, "Root File.exists: BYPASSED");
  } catch(e) {}

  // Root via Runtime.exec
  try {
    var RT = Java.use('java.lang.Runtime');
    RT.exec.overload('java.lang.String').implementation = function(c) {
      if(c&&(c.indexOf('su ')==0||c==='su'||c.indexOf('which su')!==-1||c.indexOf('magisk')!==-1)){
        L("[ENV]",C.R,"exec: "+c+" -> DENIED");
        throw Java.use('java.io.IOException').$new('Permission denied');
      }
      return RT.exec.call(this,c);
    };
    count++;
    L("[ENV]", C.G, "Root exec: BYPASSED");
  } catch(e) {}

  // Emulator
  try {
    var SP = Java.use('android.os.SystemProperties');
    SP.get.overload('java.lang.String','java.lang.String').implementation = function(k,d){
      var r = SP.get.call(this,k,d);
      if(k==='ro.kernel.qemu') return '0';
      if(k==='ro.build.tags') return 'release-keys';
      return r;
    };
    count++;
    L("[ENV]", C.G, "Emulator SystemProperties: SPOOFED");
  } catch(e) {}

  // Debug
  try {
    Java.use('android.os.Debug').isDebuggerConnected.implementation = function(){return false;};
    count++;
    L("[ENV]", C.G, "Debug: BYPASSED");
  } catch(e) {}

  // Frida detection (port scan)
  try {
    var S = Java.use('java.net.Socket');
    var origInit = S.$init.overload('java.lang.String','int');
    S.$init.overload('java.lang.String','int').implementation = function(h,p){
      if(p===27042||p===27043) throw Java.use('java.net.ConnectException').$new('refused');
      return origInit.call(this,h,p);
    };
    count++;
    L("[ENV]", C.G, "Frida port: BLOCKED");
  } catch(e) {}
});

// ================================================================
// LAYER 4: PROXY ENFORCEMENT (Java level)
// ================================================================
Java.perform(function() {
  // System properties
  try {
    var Sys = Java.use('java.lang.System');
    Sys.setProperty('http.proxyHost', H);
    Sys.setProperty('http.proxyPort', String(P));
    Sys.setProperty('https.proxyHost', H);
    Sys.setProperty('https.proxyPort', String(P));
    Sys.setProperty('http.nonProxyHosts', '');
    count++;
  } catch(e) {}

  // ProxySelector override (catches HttpURLConnection, WebView, etc.)
  try {
    var PS = Java.use('java.net.ProxySelector');
    var CustomPS = Java.registerClass({
      name: 'com.frida.ForceProxy' + Date.now(),
      superClass: PS,
      methods: {
        select: function(uri) {
          return Java.use('java.util.Collections').singletonList(BURP);
        },
        connectFailed: function(u,a,e) {}
      }
    });
    PS.setDefault(CustomPS.$new());
    L("[PROXY]", C.G, "ProxySelector: ALL -> " + H + ":" + P);
    count++;
  } catch(e) {}
});

// ================================================================
// LAYER 5: HTTP TRAFFIC MONITOR
// ================================================================
Java.perform(function() {
  // Hook HttpURLConnection (used via OkHttp2 adapter)
  try {
    var HttpURL = Java.use('java.net.HttpURLConnection');
    HttpURL.getInputStream.implementation = function() {
      L("[HTTP]", C.C, ">> " + this.getRequestMethod() + " " + this.getURL());
      return HttpURL.getInputStream.call(this);
    };
    count++;
  } catch(e) {}
});

// ================================================================
// LAYER 6: ACTIVITY MONITOR (verifikasi app mencapai MainActivity)
// ================================================================
Java.perform(function() {
  try {
    var Activity = Java.use('android.app.Activity');
    Activity.onCreate.overload('android.os.Bundle').implementation = function(b) {
      L("[APP]", C.G, "onCreate -> " + this.$className);
      return Activity.onCreate.call(this, b);
    };
    count++;
  } catch(e) {}
});

// ================================================================
// LAYER 7: DYNAMIC CLASS SCAN (log what HTTP libs are available)
// ================================================================
setTimeout(function() {
  Java.perform(function() {
    var ok3 = 0, r2 = 0;
    Java.enumerateLoadedClasses({
      onMatch: function(n) {
        if (n.indexOf('okhttp3')!==-1) ok3++;
        else if (n.indexOf('retrofit2')!==-1) r2++;
      },
      onComplete: function() {
        L("[INFO]", C.C, "OkHttp3 classes: " + ok3 + " | Retrofit2 classes: " + r2);
        if (ok3 > 0) {
          L("[INFO]", C.C, "  -> App uses modern OkHttp3 (might need additional hooks)");
        } else {
          L("[INFO]", C.C, "  -> App uses com.android.okhttp (bundled) + HttpURLConnection");
        }
      }
    });
  });
}, 3000);

// ================================================================
// FINAL
// ================================================================
setTimeout(function() {
  L("[READY]", C.M, "");
  L("[READY]", C.M, "============================================");
  L("[READY]", C.M, " STARBUCKS BYPASS READY (" + count + " hooks)");
  L("[READY]", C.M, " Proxy: " + H + ":" + P + " | SSL: OFF");
  L("[READY]", C.M, " Root: HIDDEN | Emu: SPOOFED | Debug: OFF");
  L("[READY]", C.M, " CHECK BURP -> Proxy -> HTTP History");
  L("[READY]", C.M, "============================================");
  L("[READY]", C.M, "");
}, 500);

rpc.exports = {
  status: function() { return count + " hooks | Proxy: " + H + ":" + P; }
};