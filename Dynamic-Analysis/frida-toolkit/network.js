/*
 * FRIDA TOOLKIT - NETWORK MODULE
 * SSL Pinning bypass, HTTP/HTTPS traffic capture
 * Works on Android + iOS
 */

const { log, Color, dumpHex } = require('./core.js');

// --- SSL Pinning Bypass ---
function sslUnpinning() {

  // Android: TrustManager override
  if (Java.available) {
    Java.perform(function() {
      try {
        const TrustManager = Java.registerClass({
          name: 'com.frida.BypassTrustManager',
          implements: [Java.use('javax.net.ssl.X509TrustManager')],
          methods: {
            checkClientTrusted: function(chain, authType) {},
            checkServerTrusted: function(chain, authType) {},
            getAcceptedIssuers: function() { return []; }
          }
        });

        const SSLContext = Java.use('javax.net.ssl.SSLContext');
        const TrustManagerFactory = Java.use('javax.net.ssl.TrustManagerFactory');
        const KeyManagerFactory = Java.use('javax.net.ssl.KeyManagerFactory');

        const sslCtx = SSLContext.getInstance('TLS');
        const bypassTM = [TrustManager.$new()];
        sslCtx.init(null, bypassTM, Java.use('java.security.SecureRandom').$new());

        const HttpsURLConnection = Java.use('javax.net.ssl.HttpsURLConnection');
        HttpsURLConnection.setDefaultSSLSocketFactory.overload('javax.net.ssl.SSLSocketFactory').implementation = function(factory) {
          log('SSL', Color.GREEN, 'HttpsURLConnection.setDefaultSSLSocketFactory called - bypassed');
          return;
        };

        // OkHttp3 CertificatePinner bypass
        try {
          const CertificatePinner = Java.use('okhttp3.CertificatePinner');
          CertificatePinner.check.overload('java.lang.String', 'java.util.List').implementation = function(hostname, peerCertificates) {
            log('SSL', Color.GREEN, `OkHttp3 CertificatePinner.check bypassed for ${hostname}`);
            return;
          };
        } catch (e) {}

        // TrustManagerImpl bypass
        try {
          const TrustManagerImpl = Java.use('com.android.org.conscrypt.TrustManagerImpl');
          TrustManagerImpl.verifyChain.implementation = function(untrustedChain, trustAnchorChain, host, clientSession, algo, endpoint) {
            log('SSL', Color.GREEN, `TrustManagerImpl.verifyChain bypassed for ${host}`);
            return untrustedChain;
          };
        } catch (e) {}

        // WebView SSL bypass
        try {
          const WebViewClient = Java.use('android.webkit.WebViewClient');
          WebViewClient.onReceivedSslError.implementation = function(view, handler, error) {
            log('SSL', Color.GREEN, `WebView onReceivedSslError - PROCEEDING`);
            handler.proceed();
          };
        } catch (e) {}

        log('SSL', Color.GREEN, 'Android SSL Pinning bypass installed');
      } catch (e) {
        log('SSL', Color.RED, `Android SSL bypass error: ${e}`);
      }
    });
  }

  // iOS: NSURLSession + SecureTransport hook
  if (!Java.available) {
    // Hook SSLSetSessionOption to weaken SSL
    const SSLSetSessionOption = Module.findExportByName('Security', 'SSLSetSessionOption');
    if (SSLSetSessionOption) {
      Interceptor.attach(SSLSetSessionOption, {
        onEnter(args) {
          // kSSLSessionOptionBreakOnServerAuth
          log('SSL', Color.GREEN, 'iOS SSLSetSessionOption hooked');
        }
      });
    }

    // NSURLSession auth challenge bypass
    try {
      const NSURLSession = ObjC.classes.NSURLSession;
      // Delegate method
      const block = new ObjC.Block({
        retType: 'void',
        argTypes: ['object', 'object', 'object', 'object', 'object', 'object'],
        implementation: function() {
          const challenge = new ObjC.Object(arguments[2]);
          const completionHandler = arguments[4];
          
          log('SSL', Color.GREEN, 'iOS auth challenge CONTINUING with any cert');
          
          const disposition = 2; // NSURLSessionAuthChallengeUseCredential
          const credential = ObjC.classes.NSURLCredential.credentialForTrust_(
            challenge.protectionSpace().serverTrust()
          );
          
          const handler = new ObjC.Block(completionHandler);
          handler.implementation(disposition, credential);
        }
      });
    } catch (e) {
      log('SSL', Color.RED, `iOS SSL bypass: ${e}`);
    }
  }
}

// --- HTTP Traffic Hook ---
function hookHTTPTraffic() {
  if (Java.available) {
    Java.perform(function() {
      // OkHttp3
      try {
        const RealCall = Java.use('okhttp3.RealCall');
        const Buffer = Java.use('okio.Buffer');

        RealCall.execute.implementation = function() {
          const request = this.request();
          const url = request.url().toString();
          const method = request.method();
          
          log('HTTP', Color.CYAN, `>> ${method} ${url}`);
          
          try {
            const headers = request.headers();
            const names = headers.names();
            const iter = names.iterator();
            while (iter.hasNext()) {
              const name = iter.next();
              log('HTTP', Color.CYAN, `   ${name}: ${headers.values(name)}`);
            }
          } catch (e) {}

          // Request body
          try {
            const body = request.body();
            if (body) {
              const buf = Buffer.$new();
              body.writeTo(buf);
              const bodyStr = buf.readUtf8();
              if (bodyStr.length > 0) {
                log('HTTP', Color.YELLOW, `   Body: ${bodyStr.substring(0, 500)}`);
              }
            }
          } catch (e) {}

          const response = this.execute();
          
          log('HTTP', Color.GREEN, `<< ${response.code()} ${response.message()}`);
          
          try {
            const respHeaders = response.headers();
            const names = respHeaders.names();
            const iter = names.iterator();
            while (iter.hasNext()) {
              const name = iter.next();
              log('HTTP', Color.GREEN, `   ${name}: ${respHeaders.values(name)}`);
            }
          } catch (e) {}

          // Response body - peek
          try {
            const respBody = response.body();
            if (respBody) {
              const source = respBody.source();
              const contentType = respBody.contentType();
              if (contentType && contentType.toString().includes('json')) {
                source.request(Java.use('java.lang.Long').$new(1024 * 4).longValue());
                const clone = source.buffer().clone();
                const bodyStr = clone.readUtf8();
                log('HTTP', Color.GREEN, `   Body: ${bodyStr.substring(0, 500)}`);
              }
            }
          } catch (e) {}

          return response;
        };
        log('NET', Color.MAGENTA, 'OkHttp3 traffic hooks installed');
      } catch (e) {}

      // HttpURLConnection
      try {
        const HttpURLConnection = Java.use('java.net.HttpURLConnection');

        HttpURLConnection.getInputStream.implementation = function() {
          const conn = this;
          log('HTTP', Color.CYAN, `>> ${conn.getRequestMethod()} ${conn.getURL()}`);
          return this.getInputStream();
        };

        HttpURLConnection.getOutputStream.implementation = function() {
          const conn = this;
          log('HTTP', Color.YELLOW, `>> ${conn.getRequestMethod()} ${conn.getURL()} (output)`);
          return this.getOutputStream();
        };

        log('NET', Color.MAGENTA, 'HttpURLConnection hooks installed');
      } catch (e) {}

      // Cronet / AndroidAsync
      try {
        const CronetUrlRequest = Java.use('org.chromium.net.impl.CronetUrlRequest');
        CronetUrlRequest.start.implementation = function() {
          log('CRONET', Color.CYAN, `Request started`);
          return this.start();
        };
      } catch (e) {}
    });
  }

  // iOS: NSURLSession
  if (!Java.available) {
    const NSURLSession = ObjC.classes.NSURLSession;

    const origDataTask = NSURLSession['- dataTaskWithRequest:'].implementation;
    Interceptor.attach(origDataTask, {
      onEnter(args) {
        const request = new ObjC.Object(args[2]);
        const url = request.URL().absoluteString().toString();
        const method = request.HTTPMethod().toString();
        
        log('NSURL', Color.CYAN, `>> ${method} ${url}`);
        
        try {
          const headers = request.allHTTPHeaderFields();
          if (headers) {
            const keys = headers.allKeys();
            const count = keys.count();
            for (let i = 0; i < count; i++) {
              const k = keys.objectAtIndex_(i);
              log('NSURL', Color.CYAN, `   ${k}: ${headers.objectForKey_(k)}`);
            }
          }
        } catch (e) {}
        
        try {
          const body = request.HTTPBody();
          if (body && body.length() > 0) {
            const len = Math.min(body.length(), 512);
            const bytes = body.bytes().readByteArray(len);
            const str = new Uint8Array(bytes);
            log('NSURL', Color.YELLOW, `   Body (${body.length()}B):\n${dumpHex(str, len)}`);
          }
        } catch (e) {}
      }
    });

    // Hook NSURLConnection
    try {
      const NSURLConnection = ObjC.classes.NSURLConnection;
      const origInit = NSURLConnection['- initWithRequest:delegate:'].implementation;
      Interceptor.attach(origInit, {
        onEnter(args) {
          const request = new ObjC.Object(args[2]);
          log('NSURLCON', Color.CYAN, `>> ${request.HTTPMethod()} ${request.URL().absoluteString()}`);
        }
      });
    } catch (e) {}

    log('NET', Color.MAGENTA, 'iOS NSURLSession hooks installed');
  }
}

// --- Network Stack Trace ---
function hookNetworkStack() {
  if (Java.available) {
    Java.perform(function() {
      try {
        const InetAddress = Java.use('java.net.InetAddress');
        
        InetAddress.getAllByName.implementation = function(host) {
          log('DNS', Color.CYAN, `getAllByName("${host}")`);
          return this.getAllByName(host);
        };

        InetAddress.getByName.implementation = function(host) {
          log('DNS', Color.CYAN, `getByName("${host}")`);
          return this.getByName(host);
        };
      } catch (e) {}

      // Socket connections
      try {
        const Socket = Java.use('java.net.Socket');
        
        Socket.$init.overload('java.lang.String', 'int').implementation = function(host, port) {
          log('SOCKET', Color.MAGENTA, `Socket connect to ${host}:${port}`);
          return this.$init(host, port);
        };
      } catch (e) {}

      log('NET', Color.MAGENTA, 'DNS & Socket hooks installed');
    });
  }
}

// --- Inject Certificate (Android) ---
function injectCert(certPath) {
  if (!Java.available) return;
  
  Java.perform(function() {
    try {
      const FileInputStream = Java.use('java.io.FileInputStream');
      const CertificateFactory = Java.use('java.security.cert.CertificateFactory');
      const KeyStore = Java.use('java.security.KeyStore');
      
      const cf = CertificateFactory.getInstance('X.509');
      const cert = cf.generateCertificate(FileInputStream.$new(certPath));
      
      const ks = KeyStore.getInstance('AndroidCAStore');
      ks.load(null, null);
      ks.setCertificateEntry('frida-injected', cert);
      
      // Add to trust manager
      const TrustManagerFactory = Java.use('javax.net.ssl.TrustManagerFactory');
      const tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(ks);
      
      log('CERT', Color.GREEN, `Certificate injected: ${certPath}`);
    } catch (e) {
      log('CERT', Color.RED, `Cert injection error: ${e}`);
    }
  });
}

// --- Proxy Setup ---
function setProxy(host, port) {
  if (Java.available) {
    Java.perform(function() {
      try {
        const System = Java.use('java.lang.System');
        System.setProperty('http.proxyHost', host);
        System.setProperty('http.proxyPort', String(port));
        System.setProperty('https.proxyHost', host);
        System.setProperty('https.proxyPort', String(port));
        log('PROXY', Color.GREEN, `Proxy set: ${host}:${port}`);
      } catch (e) {}
    });
  }
}

// --- Packet Capture (raw socket capture style) ---
function rawPacketMonitor() {
  if (Java.available) {
    Java.perform(function() {
      try {
        const SocketImpl = Java.use('java.net.SocketImpl');
        
        SocketImpl.getOutputStream.implementation = function() {
          const addr = this.getInetAddress();
          const port = this.getPort();
          log('PACKET', Color.YELLOW, `Socket output to ${addr}:${port}`);
          return this.getOutputStream();
        };

        SocketImpl.getInputStream.implementation = function() {
          const addr = this.getInetAddress();
          const port = this.getPort();
          log('PACKET', Color.CYAN, `Socket input from ${addr}:${port}`);
          return this.getInputStream();
        };
      } catch (e) {}
    });
  }
}

function installAll() {
  sslUnpinning();
  hookHTTPTraffic();
  hookNetworkStack();
  rawPacketMonitor();
  
  log('TOOLKIT', Color.GREEN, 'All Network hooks installed!');
}

rpc.exports = {
  installAll,
  sslUnpinning,
  hookHTTPTraffic,
  hookNetworkStack,
  setProxy,
  injectCert,
  rawPacketMonitor,
};

console.log(`\n${'='.repeat(60)}`);
console.log(`  FRIDA TOOLKIT - NETWORK MODULE LOADED`);
console.log(`  rpc.exports.installAll()       - Install all hooks`);
console.log(`  rpc.exports.sslUnpinning()     - SSL pinning bypass`);
console.log(`  rpc.exports.setProxy(host,port)- Set HTTP proxy`);
console.log(`${'='.repeat(60)}\n`);
