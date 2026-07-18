/*
 * FRIDA DYNAMIC INSTRUMENTATION TOOLKIT - ALL-IN-ONE
 * Target: macOS -> Android/iOS Emulator & Physical Device
 * 
 * Usage:
 *   frida -U -l all-in-one.js -f com.example.app --no-pause          # Android USB
 *   frida -U -l all-in-one.js "App Name"                              # iOS USB
 *   frida -R -l all-in-one.js -f com.example.app --no-pause          # Remote
 * 
 *   # Via CLI helper (Python):
 *   python3 toolkit.py --target android --device usb --app com.example.app
 *   python3 toolkit.py --target ios --device usb --app "App Name"
 * 
 * Features:
 *   - SSL Pinning bypass
 *   - HTTP/HTTPS traffic capture
 *   - Activity/ViewController lifecycle
 *   - SharedPreferences / NSUserDefaults
 *   - SQLite / CoreData queries
 *   - Keychain access monitoring
 *   - Crypto operations
 *   - Anti-debug bypass
 *   - Native library hooking
 *   - File system monitoring
 *   - JNI hooks
 *   - and more...
 */

'use strict';

const Color = {
  RESET:  "\x1b[0m",
  RED:    "\x1b[31m",
  GREEN:  "\x1b[32m",
  YELLOW: "\x1b[33m",
  BLUE:   "\x1b[34m",
  MAGENTA:"\x1b[35m",
  CYAN:   "\x1b[36m",
  WHITE:  "\x1b[37m",
  BOLD:   "\x1b[1m",
};

const BANNER = `
╔══════════════════════════════════════════════════════════════╗
║       FRIDA DYNAMIC INSTRUMENTATION TOOLKIT v1.0            ║
║       macOS -> Emulator & Physical Device                   ║
╚══════════════════════════════════════════════════════════════╝`;

console.log(Color.CYAN + BANNER + Color.RESET);

// ============================================================
// UTILITIES
// ============================================================
function dumpHex(buffer, len) {
  len = len || 256;
  if (typeof buffer === 'string') {
    const bytes = [];
    for (let i = 0; i < buffer.length; i++) bytes.push(buffer.charCodeAt(i));
    buffer = bytes;
  }
  if (buffer instanceof ArrayBuffer) {
    buffer = Array.from(new Uint8Array(buffer));
  }
  let result = '';
  for (let i = 0; i < Math.min(buffer.length, len); i += 16) {
    const chunk = [];
    for (let j = i; j < Math.min(i + 16, buffer.length); j++) {
      chunk.push(buffer[j]);
    }
    const hex = chunk.map(b => {
      const n = typeof b === 'number' ? b : (b & 0xff);
      return n.toString(16).padStart(2, '0');
    }).join(' ');
    const ascii = chunk.map(b => {
      const n = typeof b === 'number' ? b : (b & 0xff);
      return (n >= 32 && n < 127) ? String.fromCharCode(n) : '.';
    }).join('');
    result += `  ${i.toString(16).padStart(8, '0')}: ${hex.padEnd(48)} ${ascii}\n`;
  }
  return result;
}

function dumpObj(obj, depth) {
  depth = depth || 3;
  if (depth <= 0) return '...';
  if (obj === null) return 'null';
  if (obj === undefined) return 'undefined';
  if (typeof obj === 'number' || typeof obj === 'boolean') return String(obj);
  if (typeof obj === 'string') return `"${obj}"`;
  if (Array.isArray(obj)) {
    return '[' + obj.slice(0, 20).map(v => dumpObj(v, depth - 1)).join(', ') + 
           (obj.length > 20 ? ', ...' : '') + ']';
  }
  try {
    let keys = Object.keys(obj);
    return '{' + keys.slice(0, 10).map(k => `${k}: ${dumpObj(obj[k], depth - 1)}`).join(', ') +
           (keys.length > 10 ? ', ...' : '') + '}';
  } catch (e) {
    return String(obj);
  }
}

function log(tag, color, msg) {
  const ts = new Date().toISOString().split('T')[1].slice(0, 12);
  console.log(`${Color.BOLD}[${ts}]${Color.RESET} ${color}[${tag}]${Color.RESET} ${msg}`);
}

function banner(title) {
  console.log(`\n${Color.BOLD}${Color.MAGENTA}--- ${title} ---${Color.RESET}`);
}

// ============================================================
// PLATFORM DETECTION
// ============================================================
const isAndroid = Java.available;
const isIOS = !isAndroid && typeof ObjC !== 'undefined';
let platform = 'Unknown';
if (isAndroid) platform = 'Android';
else if (isIOS) platform = 'iOS';
log('SYS', Color.GREEN, `Detected platform: ${platform} | PID: ${Process.id}`);

// ============================================================
// HELPERS
// ============================================================
function tryHook(fn, name) {
  try {
    fn();
    log('HOOK', Color.GREEN, `[OK] ${name}`);
    return true;
  } catch (e) {
    log('HOOK', Color.RED, `[FAIL] ${name}: ${e.message || e}`);
    return false;
  }
}

// ============================================================
// ANDROID HOOKS
// ============================================================
function installAndroidHooks() {
  banner('ANDROID HOOKS');

  // --- SSL Pinning ---
  tryHook(bypassAndroidSSLPinning, 'SSL Pinning Bypass');

  // --- Activity Lifecycle ---
  tryHook(hookActivityLifecycle, 'Activity Lifecycle');

  // --- Intents ---
  tryHook(hookIntents, 'Intent Monitoring');

  // --- SharedPreferences ---
  tryHook(hookSharedPreferences, 'SharedPreferences');

  // --- SQLite ---
  tryHook(hookSQLiteDatabase, 'SQLite Database');

  // --- Crypto ---
  tryHook(hookJavaCrypto, 'Java Crypto');

  // --- WebView ---
  tryHook(hookWebView, 'WebView');

  // --- OkHttp / Retrofit ---
  setTimeout(() => {
    tryHook(hookOkHttp, 'OkHttp3 Traffic');
    tryHook(hookRetrofit, 'Retrofit API');
  }, 2000);

  // --- File I/O ---
  tryHook(hookFileIO, 'File Operations');

  // --- Runtime.exec ---
  tryHook(hookRuntimeExec, 'Runtime.exec');

  // --- Keystore ---
  tryHook(hookKeyStore, 'KeyStore');

  // --- ClassLoader ---
  tryHook(hookClassLoader, 'Dynamic Class Loading');

  // --- Clipboard ---
  tryHook(hookClipboard, 'Clipboard');

  // --- Broadcast ---
  tryHook(hookBroadcasts, 'BroadcastReceiver');

  // --- Dump Info ---
  setTimeout(() => tryHook(dumpAndroidAppInfo, 'App Info Dump'), 1000);
}

// ============================================================
// ANDROID SSL PINNING
// ============================================================
function bypassAndroidSSLPinning() {
  Java.perform(function() {
    const X509TrustManager = Java.use('javax.net.ssl.X509TrustManager');

    const TrustManager = Java.registerClass({
      name: 'com.toolkit.TrustManager',
      implements: [X509TrustManager],
      methods: {
        checkClientTrusted: function(chain, authType) {},
        checkServerTrusted: function(chain, authType) {},
        getAcceptedIssuers: function() { return []; }
      }
    });

    try {
      const SSLContext = Java.use('javax.net.ssl.SSLContext');
      const sslCtx = SSLContext.getInstance('TLS');
      sslCtx.init(null, [TrustManager.$new()], Java.use('java.security.SecureRandom').$new());
      
      const HttpsURLConnection = Java.use('javax.net.ssl.HttpsURLConnection');
      HttpsURLConnection.setDefaultSSLSocketFactory.implementation = function() {};
    } catch (e) {}

    try {
      const OkHttpPin = Java.use('okhttp3.CertificatePinner');
      OkHttpPin.check.overload('java.lang.String', 'java.util.List').implementation = function() {};
    } catch (e) {}

    try {
      const TrustManagerImpl = Java.use('com.android.org.conscrypt.TrustManagerImpl');
      TrustManagerImpl.verifyChain.implementation = function(chain) { return chain; };
    } catch (e) {}

    try {
      const WebViewClient = Java.use('android.webkit.WebViewClient');
      WebViewClient.onReceivedSslError.implementation = function(view, handler, error) {
        handler.proceed();
      };
    } catch (e) {}
  });
}

// ============================================================
// ANDROID ACTIVITY LIFECYCLE
// ============================================================
function hookActivityLifecycle() {
  Java.perform(function() {
    const Activity = Java.use('android.app.Activity');
    
    Activity.onCreate.overload('android.os.Bundle').implementation = function(bundle) {
      log('ACTIVITY', Color.GREEN, `onCreate() -> ${this.$className}`);
      try {
        const intent = this.getIntent();
        if (intent) {
          const action = intent.getAction();
          const data = intent.getDataString();
          if (action) log('ACTIVITY', Color.CYAN, `  Action: ${action}`);
          if (data) log('ACTIVITY', Color.CYAN, `  Data: ${data}`);
          dumpIntentExtras(intent);
        }
      } catch (e) {}
      return this.onCreate(bundle);
    };

    Activity.onResume.implementation = function() {
      log('ACTIVITY', Color.GREEN, `onResume() -> ${this.$className}`);
      return this.onResume();
    };

    Activity.onPause.implementation = function() {
      log('ACTIVITY', Color.GREEN, `onPause() -> ${this.$className}`);
      return this.onPause();
    };

    Activity.onDestroy.implementation = function() {
      log('ACTIVITY', Color.RED, `onDestroy() -> ${this.$className}`);
      return this.onDestroy();
    };
  });
}

// ============================================================
// ANDROID INTENTS
// ============================================================
function hookIntents() {
  Java.perform(function() {
    const Activity = Java.use('android.app.Activity');
    
    Activity.startActivity.overload('android.content.Intent').implementation = function(intent) {
      log('INTENT', Color.CYAN, `startActivity action="${intent.getAction()}" component="${intent.getComponent()}"`);
      dumpIntentExtras(intent);
      return this.startActivity(intent);
    };

    Activity.startActivityForResult.overload('android.content.Intent', 'int').implementation = function(intent, code) {
      log('INTENT', Color.CYAN, `startActivityForResult(${code}) action="${intent.getAction()}"`);
      dumpIntentExtras(intent);
      return this.startActivityForResult(intent, code);
    };

    Activity.setResult.overload('int', 'android.content.Intent').implementation = function(code, data) {
      log('INTENT', Color.CYAN, `setResult(${code})`);
      if (data) dumpIntentExtras(data);
      return this.setResult(code, data);
    };
  });
}

function dumpIntentExtras(intent) {
  try {
    const extras = intent.getExtras();
    if (extras && extras.keySet()) {
      const keys = extras.keySet();
      const iter = keys.iterator();
      while (iter.hasNext()) {
        const key = iter.next();
        log('INTENT', Color.CYAN, `  Extra: ${key} = ${dumpObj(extras.get(key))}`);
      }
    }
  } catch (e) {}
}

// ============================================================
// ANDROID SHARED PREFERENCES
// ============================================================
function hookSharedPreferences() {
  Java.perform(function() {
    const SP = Java.use('android.app.SharedPreferencesImpl');
    const Editor = Java.use('android.app.SharedPreferencesImpl$EditorImpl');

    const readMethods = ['getString', 'getInt', 'getBoolean', 'getLong', 'getFloat'];
    const writeMethods = ['putString', 'putInt', 'putBoolean', 'putLong', 'putFloat'];

    readMethods.forEach(function(method) {
      SP[method].overloads.forEach(function(overload) {
        overload.implementation = function() {
          const result = overload.apply(this, arguments);
          log('SP-READ', Color.BLUE, `${method}("${arguments[0]}") = ${dumpObj(result)}`);
          return result;
        };
      });
    });

    writeMethods.forEach(function(method) {
      Editor[method].overloads.forEach(function(overload) {
        overload.implementation = function() {
          log('SP-WRITE', Color.YELLOW, `${method}("${arguments[0]}", ${dumpObj(arguments[1])})`);
          return overload.apply(this, arguments);
        };
      });
    });

    Editor.commit.implementation = function() {
      log('SP-WRITE', Color.YELLOW, 'commit()');
      return this.commit();
    };

    Editor.apply.implementation = function() {
      log('SP-WRITE', Color.YELLOW, 'apply()');
      return this.apply();
    };
  });
}

// ============================================================
// ANDROID SQLite
// ============================================================
function hookSQLiteDatabase() {
  Java.perform(function() {
    const SQLiteDatabase = Java.use('android.database.sqlite.SQLiteDatabase');

    SQLiteDatabase.rawQuery.overload('java.lang.String', '[Ljava.lang.String;').implementation = function(sql, args) {
      const upper = sql.toUpperCase().trim();
      log('SQLITE', Color.CYAN, `rawQuery: ${sql}`);
      if (args) log('SQLITE', Color.CYAN, `  args: ${dumpObj(args)}`);
      return this.rawQuery(sql, args);
    };

    SQLiteDatabase.execSQL.overload('java.lang.String').implementation = function(sql) {
      const upper = sql.toUpperCase().trim();
      if (upper.startsWith('INSERT') || upper.startsWith('UPDATE') || upper.startsWith('DELETE') || 
          upper.startsWith('CREATE') || upper.startsWith('ALTER') || upper.startsWith('DROP')) {
        log('SQLITE', Color.YELLOW, `execSQL: ${sql}`);
      }
      return this.execSQL(sql);
    };

    SQLiteDatabase.insert.overload('java.lang.String', 'java.lang.String', 'android.content.ContentValues').implementation = function(table, nullColumnHack, values) {
      log('SQLITE', Color.YELLOW, `insert table="${table}"`);
      if (values) log('SQLITE', Color.YELLOW, `  values: ${dumpObj(values.valueSet())}`);
      return this.insert(table, nullColumnHack, values);
    };

    SQLiteDatabase.update.overload('java.lang.String', 'android.content.ContentValues', 'java.lang.String', '[Ljava.lang.String;').implementation = function(table, values, where, whereArgs) {
      log('SQLITE', Color.YELLOW, `update table="${table}" where="${where}"`);
      if (whereArgs) log('SQLITE', Color.YELLOW, `  whereArgs: ${dumpObj(whereArgs)}`);
      return this.update(table, values, where, whereArgs);
    };

    SQLiteDatabase.delete.overload('java.lang.String', 'java.lang.String', '[Ljava.lang.String;').implementation = function(table, where, whereArgs) {
      log('SQLITE', Color.YELLOW, `delete table="${table}" where="${where}"`);
      return this.delete(table, where, whereArgs);
    };
  });
}

// ============================================================
// ANDROID CRYPTO
// ============================================================
function hookJavaCrypto() {
  Java.perform(function() {
    const Cipher = Java.use('javax.crypto.Cipher');

    Cipher.getInstance.overload('java.lang.String').implementation = function(transform) {
      log('CRYPTO', Color.MAGENTA, `Cipher.getInstance("${transform}")`);
      return this.getInstance(transform);
    };

    Cipher.init.overload('int', 'java.security.Key').implementation = function(mode, key) {
      const modes = {1: 'ENCRYPT', 2: 'DECRYPT', 3: 'WRAP', 4: 'UNWRAP'};
      log('CRYPTO', Color.MAGENTA, `Cipher.init(${modes[mode] || mode}) algo=${key.getAlgorithm()}`);
      try {
        const encoded = key.getEncoded();
        if (encoded) log('CRYPTO', Color.MAGENTA, `  Key:\n${dumpHex(encoded, 32)}`);
      } catch (e) {}
      return this.init(mode, key);
    };

    Cipher.doFinal.overload('[B').implementation = function(input) {
      log('CRYPTO', Color.YELLOW, `Cipher.doFinal(${input.length}B)`);
      log('CRYPTO', Color.YELLOW, `  In:\n${dumpHex(input, 64)}`);
      const result = this.doFinal(input);
      log('CRYPTO', Color.YELLOW, `  Out:\n${dumpHex(result, 64)}`);
      return result;
    };

    const Mac = Java.use('javax.crypto.Mac');
    Mac.getInstance.overload('java.lang.String').implementation = function(algo) {
      log('CRYPTO', Color.MAGENTA, `Mac.getInstance("${algo}")`);
      return this.getInstance(algo);
    };

    Mac.doFinal.overload('[B').implementation = function(input) {
      const result = this.doFinal(input);
      log('CRYPTO', Color.MAGENTA, `Mac.doFinal => ${dumpObj(result)}`);
      return result;
    };

    const MessageDigest = Java.use('java.security.MessageDigest');
    MessageDigest.getInstance.overload('java.lang.String').implementation = function(algo) {
      log('CRYPTO', Color.MAGENTA, `MessageDigest.getInstance("${algo}")`);
      return this.getInstance(algo);
    };

    MessageDigest.digest.overload('[B').implementation = function(input) {
      const result = this.digest(input);
      log('CRYPTO', Color.MAGENTA, `MessageDigest.digest => ${dumpHex(result, 32)}`);
      return result;
    };
  });
}

// ============================================================
// ANDROID WEBVIEW
// ============================================================
function hookWebView() {
  Java.perform(function() {
    const WebView = Java.use('android.webkit.WebView');

    WebView.loadUrl.overload('java.lang.String').implementation = function(url) {
      log('WEBVIEW', Color.CYAN, `loadUrl("${url}")`);
      return this.loadUrl(url);
    };

    WebView.loadUrl.overload('java.lang.String', 'java.util.Map').implementation = function(url, headers) {
      log('WEBVIEW', Color.CYAN, `loadUrl("${url}") headers=${dumpObj(headers)}`);
      return this.loadUrl(url, headers);
    };

    WebView.loadDataWithBaseURL.overload('java.lang.String', 'java.lang.String', 'java.lang.String', 'java.lang.String', 'java.lang.String').implementation = function(baseUrl, data, mime, enc, histUrl) {
      log('WEBVIEW', Color.CYAN, `loadDataWithBaseURL base="${baseUrl}" len=${data.length}`);
      return this.loadDataWithBaseURL(baseUrl, data, mime, enc, histUrl);
    };

    WebView.evaluateJavascript.overload('java.lang.String', 'android.webkit.ValueCallback').implementation = function(script, callback) {
      log('WEBVIEW', Color.MAGENTA, `evaluateJavascript: ${script.substring(0, 150)}`);
      return this.evaluateJavascript(script, callback);
    };

    try {
      const WVClient = Java.use('android.webkit.WebViewClient');
      WVClient.onPageStarted.implementation = function(view, url, favicon) {
        log('WEBVIEW', Color.BLUE, `onPageStarted "${url}"`);
        return this.onPageStarted(view, url, favicon);
      };
      WVClient.onPageFinished.implementation = function(view, url) {
        log('WEBVIEW', Color.GREEN, `onPageFinished "${url}"`);
        return this.onPageFinished(view, url);
      };
    } catch (e) {}
  });
}

// ============================================================
// ANDROID OKHTTP
// ============================================================
function hookOkHttp() {
  Java.perform(function() {
    try {
      const RealCall = Java.use('okhttp3.RealCall');

      RealCall.execute.implementation = function() {
        const req = this.request();
        log('OKHTTP', Color.CYAN, `>> ${req.method()} ${req.url()}`);
        dumpOkHttpHeaders(req);
        dumpOkHttpBody(req);
        const resp = this.execute();
        log('OKHTTP', Color.GREEN, `<< ${resp.code()} ${resp.message()}`);
        return resp;
      };

      RealCall.enqueue.implementation = function(callback) {
        const req = this.request();
        log('OKHTTP', Color.CYAN, `>> (async) ${req.method()} ${req.url()}`);
        return this.enqueue(callback);
      };
    } catch (e) {}
  });
}

function dumpOkHttpHeaders(req) {
  try {
    const names = req.headers().names();
    const iter = names.iterator();
    while (iter.hasNext()) {
      const name = iter.next();
      log('OKHTTP', Color.CYAN, `  ${name}: ${req.headers().values(name)}`);
    }
  } catch (e) {}
}

function dumpOkHttpBody(req) {
  try {
    const body = req.body();
    if (body) {
      const Buffer = Java.use('okio.Buffer');
      const buf = Buffer.$new();
      body.writeTo(buf);
      const str = buf.readUtf8();
      if (str.length > 0) {
        log('OKHTTP', Color.YELLOW, `  Body: ${str.substring(0, 300)}`);
      }
    }
  } catch (e) {}
}

// ============================================================
// ANDROID RETROFIT
// ============================================================
function hookRetrofit() {
  Java.perform(function() {
    try {
      const OkHttpCall = Java.use('retrofit2.OkHttpCall');
      OkHttpCall.execute.implementation = function() {
        log('RETROFIT', Color.CYAN, 'API Call executed');
        return this.execute();
      };

      const ServiceMethod = Java.use('retrofit2.ServiceMethod');
      ServiceMethod.toResponse.implementation = function(body) {
        log('RETROFIT', Color.CYAN, `toResponse body=${dumpObj(body)}`);
        return this.toResponse(body);
      };
    } catch (e) {}
  });
}

// ============================================================
// ANDROID FILE I/O
// ============================================================
function hookFileIO() {
  Java.perform(function() {
    const FileInputStream = Java.use('java.io.FileInputStream');
    const FileOutputStream = Java.use('java.io.FileOutputStream');

    FileInputStream.$init.overload('java.lang.String').implementation = function(path) {
      log('FILE', Color.BLUE, `FileInputStream("${path}")`);
      return this.$init(path);
    };

    FileOutputStream.$init.overload('java.lang.String').implementation = function(path) {
      log('FILE', Color.YELLOW, `FileOutputStream("${path}")`);
      return this.$init(path);
    };

    FileOutputStream.$init.overload('java.lang.String', 'boolean').implementation = function(path, append) {
      log('FILE', Color.YELLOW, `FileOutputStream("${path}", append=${append})`);
      return this.$init(path, append);
    };
  });
}

// ============================================================
// ANDROID RUNTIME
// ============================================================
function hookRuntimeExec() {
  Java.perform(function() {
    const Runtime = Java.use('java.lang.Runtime');

    Runtime.exec.overloads.forEach(function(overload) {
      overload.implementation = function() {
        const cmd = Array.from(arguments).map(a =>
          Array.isArray(a) ? a.join(' ') : String(a)
        ).join(' ');
        log('EXEC', Color.RED, `Runtime.exec: ${cmd}`);
        return overload.apply(this, arguments);
      };
    });

    const ProcessBuilder = Java.use('java.lang.ProcessBuilder');
    ProcessBuilder.start.implementation = function() {
      log('EXEC', Color.RED, `ProcessBuilder.start: ${this.command()}`);
      return this.start();
    };
  });
}

// ============================================================
// ANDROID KEYSTORE
// ============================================================
function hookKeyStore() {
  Java.perform(function() {
    try {
      const KeyStore = Java.use('java.security.KeyStore');
      KeyStore.getEntry.overload('java.lang.String', 'java.security.KeyStore$ProtectionParameter').implementation = function(alias, param) {
        log('KEYSTORE', Color.CYAN, `getEntry alias="${alias}"`);
        return this.getEntry(alias, param);
      };
    } catch (e) {}

    try {
      const AndroidKeyStore = Java.use('android.security.keystore.AndroidKeyStoreSpi');
      AndroidKeyStore.engineGetCertificateChain.implementation = function(alias) {
        log('KEYSTORE', Color.CYAN, `engineGetCertificateChain alias="${alias}"`);
        return this.engineGetCertificateChain(alias);
      };
    } catch (e) {}
  });
}

// ============================================================
// ANDROID CLASSLOADER
// ============================================================
function hookClassLoader() {
  Java.perform(function() {
    try {
      const DexClassLoader = Java.use('dalvik.system.DexClassLoader');
      DexClassLoader.$init.overload('java.lang.String', 'java.lang.String', 'java.lang.String', 'java.lang.ClassLoader').implementation = function(dexPath, optDir, libPath, parent) {
        log('CLASSLOADER', Color.RED, `DexClassLoader dexPath="${dexPath}"`);
        return this.$init(dexPath, optDir, libPath, parent);
      };
    } catch (e) {}
  });
}

// ============================================================
// ANDROID CLIPBOARD
// ============================================================
function hookClipboard() {
  Java.perform(function() {
    try {
      const ClipboardManager = Java.use('android.content.ClipboardManager');
      ClipboardManager.setPrimaryClip.implementation = function(clip) {
        log('CLIPBOARD', Color.YELLOW, 'setPrimaryClip()');
        if (clip) {
          for (let i = 0; i < clip.getItemCount(); i++) {
            const item = clip.getItemAt(i);
            log('CLIPBOARD', Color.YELLOW, `  Item[${i}]: "${item.getText()}"`);
          }
        }
        return this.setPrimaryClip(clip);
      };
    } catch (e) {}
  });
}

// ============================================================
// ANDROID BROADCAST
// ============================================================
function hookBroadcasts() {
  Java.perform(function() {
    try {
      const Context = Java.use('android.content.Context');
      Context.sendBroadcast.overload('android.content.Intent').implementation = function(intent) {
        log('BROADCAST', Color.CYAN, `sendBroadcast action="${intent.getAction()}"`);
        return this.sendBroadcast(intent);
      };
    } catch (e) {}
  });
}

// ============================================================
// ANDROID DUMP INFO
// ============================================================
function dumpAndroidAppInfo() {
  Java.perform(function() {
    const Context = Java.use('android.app.ActivityThread').currentApplication().getApplicationContext();
    const pm = Context.getPackageManager();
    const pn = Context.getPackageName();
    const pi = pm.getPackageInfo(pn, 0);

    log('APP', Color.MAGENTA, '=== APPLICATION INFO ===');
    log('APP', Color.MAGENTA, `Package: ${pi.packageName}`);
    log('APP', Color.MAGENTA, `Version: ${pi.versionName} (${pi.versionCode})`);
    log('APP', Color.MAGENTA, `Data Dir: ${Context.getDataDir().getAbsolutePath()}`);
    log('APP', Color.MAGENTA, `Files Dir: ${Context.getFilesDir().getAbsolutePath()}`);
    log('APP', Color.MAGENTA, `Cache Dir: ${Context.getCacheDir().getAbsolutePath()}`);
    log('APP', Color.MAGENTA, `PID: ${Process.id}`);

    try {
      const permissions = pi.requestedPermissions;
      if (permissions) {
        log('APP', Color.MAGENTA, 'Permissions:');
        for (let i = 0; i < permissions.length; i++) {
          log('APP', Color.MAGENTA, `  ${permissions[i]}`);
        }
      }
    } catch (e) {}

    try {
      const dbList = Context.databaseList();
      log('APP', Color.MAGENTA, `Databases: ${dbList.join(', ')}`);
    } catch (e) {}
  });
}

// ============================================================
// iOS HOOKS
// ============================================================
function installiOSHooks() {
  banner('iOS HOOKS');

  tryHook(hookNSUserDefaults, 'NSUserDefaults');
  tryHook(hookiOSKeychain, 'Keychain');
  tryHook(hookNSURLSession, 'NSURLSession');
  tryHook(hookViewControllers, 'UIViewController');
  tryHook(hookiOSAlerts, 'UIAlertController');
  tryHook(hookiOSFileManager, 'NSFileManager');
  tryHook(hookiOSNotificationCenter, 'NSNotificationCenter');
  tryHook(hookCommonCrypto, 'CommonCrypto');
  tryHook(hookiOSDylib, 'dlopen');
  tryHook(hookiOSPasteboard, 'UIPasteboard');
  tryHook(hookiOSSwizzling, 'Method Swizzling');

  setTimeout(() => tryHook(hookWKWebView, 'WKWebView'), 2000);
  setTimeout(() => tryHook(dumpiOSAppInfo, 'App Info Dump'), 500);
}

// ============================================================
// iOS NSUSERDEFAULTS
// ============================================================
function hookNSUserDefaults() {
  const NSUserDefaults = ObjC.classes.NSUserDefaults;

  Interceptor.attach(NSUserDefaults['- setObject:forKey:'].implementation, {
    onEnter(args) {
      const key = new ObjC.Object(args[3]).toString();
      const val = new ObjC.Object(args[2]);
      log('NSDEFAULTS', Color.YELLOW, `setObject forKey="${key}"`);
      try {
        if (val.isKindOfClass_(ObjC.classes.NSData)) {
          log('NSDEFAULTS', Color.YELLOW, `  Data: ${dumpHex(val.bytes(), Math.min(val.length(), 64))}`);
        } else {
          log('NSDEFAULTS', Color.YELLOW, `  Value: ${val}`);
        }
      } catch (e) {}
    }
  });

  Interceptor.attach(NSUserDefaults['- objectForKey:'].implementation, {
    onEnter(args) {
      log('NSDEFAULTS', Color.BLUE, `objectForKey("${new ObjC.Object(args[2])}")`);
    },
    onLeave(retval) {
      try {
        const val = new ObjC.Object(retval);
        if (val && !val.isNull()) log('NSDEFAULTS', Color.BLUE, `  => ${val}`);
      } catch (e) {}
    }
  });

  Interceptor.attach(NSUserDefaults['- setBool:forKey:'].implementation, {
    onEnter(args) {
      log('NSDEFAULTS', Color.YELLOW, `setBool(${args[2]}) forKey="${new ObjC.Object(args[3])}"`);
    }
  });
}

// ============================================================
// iOS KEYCHAIN
// ============================================================
function hookiOSKeychain() {
  const SecItemAdd = Module.findExportByName('Security', 'SecItemAdd');
  const SecItemCopyMatching = Module.findExportByName('Security', 'SecItemCopyMatching');
  const SecItemDelete = Module.findExportByName('Security', 'SecItemDelete');

  if (SecItemAdd) {
    Interceptor.attach(SecItemAdd, {
      onEnter(args) {
        log('KEYCHAIN', Color.YELLOW, `SecItemAdd: ${dumpNSDict(new ObjC.Object(args[0]))}`);
      }
    });
  }

  if (SecItemCopyMatching) {
    Interceptor.attach(SecItemCopyMatching, {
      onEnter(args) {
        log('KEYCHAIN', Color.BLUE, `SecItemCopyMatching: ${dumpNSDict(new ObjC.Object(args[0]))}`);
      },
      onLeave(retval) {
        if (retval.toInt32() === 0) {
          try {
            const result = new ObjC.Object(this.context.x1);
            if (result && !result.isNull()) {
              if (result.isKindOfClass_(ObjC.classes.NSData)) {
                log('KEYCHAIN', Color.BLUE, `  Data (${result.length()}B):\n${dumpHex(result.bytes(), Math.min(result.length(), 64))}`);
              } else if (result.isKindOfClass_(ObjC.classes.NSDictionary)) {
                log('KEYCHAIN', Color.BLUE, `  Result: ${dumpNSDict(result)}`);
              }
            }
          } catch (e) {}
        }
      }
    });
  }

  if (SecItemDelete) {
    Interceptor.attach(SecItemDelete, {
      onEnter(args) {
        log('KEYCHAIN', Color.RED, `SecItemDelete: ${dumpNSDict(new ObjC.Object(args[0]))}`);
      }
    });
  }
}

function dumpNSDict(dict) {
  if (!dict || dict.isNull()) return 'null';
  try {
    if (dict.isKindOfClass_(ObjC.classes.NSDictionary)) {
      const keys = dict.allKeys();
      const count = keys.count();
      let out = '{';
      for (let i = 0; i < Math.min(count, 10); i++) {
        const k = keys.objectAtIndex_(i);
        const v = dict.objectForKey_(k);
        try {
          if (v.isKindOfClass_(ObjC.classes.NSData)) {
            out += `${k}: Data(${v.length()}B), `;
          } else {
            out += `${k}: ${v}, `;
          }
        } catch (e) {
          out += `${k}: ?, `;
        }
      }
      return out + '}';
    }
    return dict.toString();
  } catch (e) {
    return '?';
  }
}

// ============================================================
// iOS NSURLSESSION
// ============================================================
function hookNSURLSession() {
  const NSURLSession = ObjC.classes.NSURLSession;

  Interceptor.attach(NSURLSession['- dataTaskWithRequest:'].implementation, {
    onEnter(args) {
      const request = new ObjC.Object(args[2]);
      const url = request.URL().absoluteString().toString();
      const method = request.HTTPMethod().toString();
      log('NSURL', Color.CYAN, `${method} ${url}`);

      try {
        const headers = request.allHTTPHeaderFields();
        if (headers) {
          const keys = headers.allKeys();
          for (let i = 0; i < keys.count(); i++) {
            const k = keys.objectAtIndex_(i);
            log('NSURL', Color.CYAN, `  ${k}: ${headers.objectForKey_(k)}`);
          }
        }
      } catch (e) {}

      try {
        const body = request.HTTPBody();
        if (body && body.length() > 0) {
          log('NSURL', Color.YELLOW, `  Body: ${body.bytes().readUtf8String(body.length())}`);
        }
      } catch (e) {}
    }
  });
}

// ============================================================
// iOS UIVIEWCONTROLLER
// ============================================================
function hookViewControllers() {
  const VC = ObjC.classes.UIViewController;

  Interceptor.attach(VC['- viewDidLoad'].implementation, {
    onEnter(args) {
      log('VC', Color.GREEN, `viewDidLoad -> ${this.$className || '?'}`);
    }
  });

  Interceptor.attach(VC['- viewWillAppear:'].implementation, {
    onEnter(args) {
      log('VC', Color.GREEN, `viewWillAppear -> ${this.$className || '?'}`);
    }
  });

  Interceptor.attach(VC['- viewDidDisappear:'].implementation, {
    onEnter(args) {
      log('VC', Color.GREEN, `viewDidDisappear -> ${this.$className || '?'}`);
    }
  });
}

// ============================================================
// iOS UIALERTCONTROLLER
// ============================================================
function hookiOSAlerts() {
  const Alert = ObjC.classes.UIAlertController;

  Interceptor.attach(Alert['+ alertControllerWithTitle:message:preferredStyle:'].implementation, {
    onEnter(args) {
      const title = new ObjC.Object(args[2]);
      const message = new ObjC.Object(args[3]);
      log('ALERT', Color.YELLOW, `Title: "${title}"`);
      if (message && message.toString() !== 'null') {
        log('ALERT', Color.YELLOW, `  Msg: "${message}"`);
      }
    }
  });

  Interceptor.attach(Alert['- addAction:'].implementation, {
    onEnter(args) {
      const action = new ObjC.Object(args[2]);
      if (action) log('ALERT', Color.YELLOW, `  Action: "${action.title()}"`);
    }
  });
}

// ============================================================
// iOS WKWEBVIEW
// ============================================================
function hookWKWebView() {
  try {
    const WK = ObjC.classes.WKWebView;

    Interceptor.attach(WK['- loadRequest:'].implementation, {
      onEnter(args) {
        const request = new ObjC.Object(args[2]);
        log('WEBVIEW', Color.CYAN, `loadRequest: ${request.URL().absoluteString()}`);
      }
    });

    Interceptor.attach(WK['- evaluateJavaScript:completionHandler:'].implementation, {
      onEnter(args) {
        const js = new ObjC.Object(args[2]).toString();
        log('WEBVIEW', Color.MAGENTA, `evaluateJS: ${js.substring(0, 200)}`);
      }
    });
  } catch (e) {}
}

// ============================================================
// iOS NSFILEMANAGER
// ============================================================
function hookiOSFileManager() {
  const FM = ObjC.classes.NSFileManager;

  Interceptor.attach(FM['- createFileAtPath:contents:attributes:'].implementation, {
    onEnter(args) {
      log('FILE', Color.YELLOW, `createFile: ${new ObjC.Object(args[2])}`);
    }
  });

  Interceptor.attach(FM['- copyItemAtPath:toPath:error:'].implementation, {
    onEnter(args) {
      log('FILE', Color.YELLOW, `copyItem: ${new ObjC.Object(args[2])} -> ${new ObjC.Object(args[3])}`);
    }
  });

  Interceptor.attach(FM['- removeItemAtPath:error:'].implementation, {
    onEnter(args) {
      log('FILE', Color.RED, `removeItem: ${new ObjC.Object(args[2])}`);
    }
  });

  Interceptor.attach(FM['- contentsAtPath:'].implementation, {
    onEnter(args) {
      log('FILE', Color.BLUE, `contentsAtPath: ${new ObjC.Object(args[2])}`);
    }
  });
}

// ============================================================
// iOS NSNotificationCenter
// ============================================================
function hookiOSNotificationCenter() {
  const NC = ObjC.classes.NSNotificationCenter;

  Interceptor.attach(NC['- postNotificationName:object:'].implementation, {
    onEnter(args) {
      log('NOTIF', Color.CYAN, `postNotification: "${new ObjC.Object(args[2])}"`);
    }
  });
}

// ============================================================
// iOS CommonCrypto
// ============================================================
function hookCommonCrypto() {
  const CCCrypt = Module.findExportByName('libcommonCrypto.dylib', 'CCCrypt');
  if (CCCrypt) {
    Interceptor.attach(CCCrypt, {
      onEnter(args) {
        const op = args[0].toInt32();
        const ops = {0: 'ENCRYPT', 1: 'DECRYPT'};
        const keyLen = args[4].toInt32();
        const dataLen = args[8].toInt32();
        log('CRYPTO', Color.CYAN, `CCCrypt(${ops[op] || op}) keyLen=${keyLen} dataLen=${dataLen}`);
        if (keyLen > 0 && keyLen <= 64) {
          log('CRYPTO', Color.CYAN, `  Key:\n${dumpHex(args[5].readByteArray(keyLen))}`);
        }
      }
    });
  }
}

// ============================================================
// iOS DYLIB
// ============================================================
function hookiOSDylib() {
  const dlopen = Module.findExportByName(null, 'dlopen');
  if (dlopen) {
    Interceptor.attach(dlopen, {
      onEnter(args) {
        log('DLOPEN', Color.MAGENTA, `dlopen("${args[0].readUtf8String()}")`);
      }
    });
  }
}

// ============================================================
// iOS PASTEBOARD
// ============================================================
function hookiOSPasteboard() {
  const PB = ObjC.classes.UIPasteboard;

  Interceptor.attach(PB['- setString:'].implementation, {
    onEnter(args) {
      log('PASTEBOARD', Color.YELLOW, `setString: "${new ObjC.Object(args[2])}"`);
    }
  });

  Interceptor.attach(PB['- string'].implementation, {
    onLeave(retval) {
      try {
        log('PASTEBOARD', Color.BLUE, `string => "${new ObjC.Object(retval)}"`);
      } catch (e) {}
    }
  });
}

// ============================================================
// iOS SWIZZLING DETECTION
// ============================================================
function hookiOSSwizzling() {
  try {
    const orig = ObjC.classes.NSObject['+ exchangeInstanceMethod1:method2:'];
    if (orig) {
      Interceptor.attach(orig.implementation, {
        onEnter(args) {
          log('SWIZZLE', Color.RED, 'method_exchangeImplementations detected!');
        }
      });
    }
  } catch (e) {}
}

// ============================================================
// iOS DUMP INFO
// ============================================================
function dumpiOSAppInfo() {
  const bundle = ObjC.classes.NSBundle.mainBundle();
  const info = bundle.infoDictionary();

  log('APP', Color.MAGENTA, '=== APP INFO ===');
  log('APP', Color.MAGENTA, `Bundle: ${info.objectForKey_('CFBundleIdentifier')}`);
  log('APP', Color.MAGENTA, `Version: ${info.objectForKey_('CFBundleShortVersionString')}`);
  log('APP', Color.MAGENTA, `Path: ${bundle.bundlePath()}`);
  log('APP', Color.MAGENTA, `Executable: ${bundle.executablePath()}`);

  try {
    const home = ObjC.classes.NSProcessInfo.processInfo().environment().objectForKey_('HOME');
    log('APP', Color.MAGENTA, `HOME: ${home}`);
  } catch (e) {}

  log('APP', Color.MAGENTA, `PID: ${Process.id}`);
}

// ============================================================
// NATIVE HOOKS (Android + iOS)
// ============================================================
// ============================================================
// NATIVE HOOKS (Android + iOS)
// ============================================================
function installNativeHooks() {
  banner('NATIVE HOOKS');

  tryHook(function() {
    var ptrace = Module.getGlobalExportByName('ptrace');
    if (ptrace) {
      Interceptor.attach(ptrace, {
        onEnter: function(args) {
          if (args[0].toInt32() === 0) {
            log('ANTIDBG', Color.RED, 'ptrace(PTRACE_TRACEME) - BLOCKED');
            this.bypass = true;
          }
        },
        onLeave: function(retval) {
          if (this.bypass) retval.replace(0);
        }
      });
    }
    var strstr = Module.getGlobalExportByName('strstr');
    if (strstr) {
      Interceptor.attach(strstr, {
        onEnter: function(args) {
          try {
            var needle = args[1].readUtf8String();
            if (needle && (needle.indexOf('frida') !== -1 || needle.indexOf('gum-js') !== -1)) {
              log('ANTIDBG', Color.RED, 'strstr frida detection BLOCKED');
              args[1] = Memory.allocUtf8String('NOT_FOUND');
            }
          } catch (e) {}
        }
      });
    }
  }, 'Anti-Debug Bypass');

  tryHook(function() {
    var libc = Process.findModuleByName('libc.so');
    if (libc) {
      var openPtr = libc.getExportByName('open');
      if (openPtr) {
        Interceptor.attach(openPtr, {
          onEnter: function(args) {
            try {
              var path = args[0].readUtf8String();
              if (path && path.indexOf('/dev/') !== 0 && path.indexOf('/proc/') !== 0) {
                log('NATIVE', Color.BLUE, 'open("' + path + '")');
              }
            } catch (e) {}
          }
        });
      }
    }
  }, 'File Open');

  tryHook(function() {
    var aes = Module.getGlobalExportByName('AES_cbc_encrypt');
    if (aes) {
      Interceptor.attach(aes, {
        onEnter: function(args) {
          var len = args[4].toInt32();
          var enc = args[5].toInt32();
          log('NCRYPTO', Color.CYAN, 'AES_cbc_encrypt len=' + len + ' ' + (enc ? 'ENCRYPT' : 'DECRYPT'));
        }
      });
    }
  }, 'Native Crypto');

  tryHook(function() {
    var strcmp = Module.getGlobalExportByName('strcmp');
    if (strcmp) {
      Interceptor.attach(strcmp, {
        onEnter: function(args) {
          try {
            var s1 = args[0].readUtf8String();
            var s2 = args[1].readUtf8String();
            if (s1 && s2 && s1.length < 100 && s2.length < 100) {
              log('STR', Color.CYAN, 'strcmp("' + s1 + '", "' + s2 + '")');
            }
          } catch (e) {}
        }
      });
    }
  }, 'String Operations');
}

// ============================================================
// MAIN
// ============================================================
log('SYS', Color.GREEN, `Frida version: ${Frida.version}`);
log('SYS', Color.GREEN, `Script runtime: ${Script.runtime}`);

if (isAndroid) {
  installAndroidHooks();
  installNativeHooks();
} else if (isIOS) {
  installiOSHooks();
  installNativeHooks();
} else {
  log('SYS', Color.RED, 'Unsupported platform! Only Android & iOS supported.');
}

// --- RPC Exports for interactive use ---
rpc.exports = {
  // Dump info
  dumpAndroidInfo: dumpAndroidAppInfo,
  dumpiOSInfo: dumpiOSAppInfo,
  
  // SSL
  sslBypass: bypassAndroidSSLPinning,
  
  // Utility
  dumpHex: function(ptr, len) {
    return dumpHex(ptr.readByteArray(len), len);
  },
  
  // Scan memory
  scanModules: function() {
    const mods = [];
    Process.enumerateModules().forEach(function(m) {
      mods.push({ name: m.name, base: m.base.toString(), size: m.size, path: m.path });
    });
    return mods;
  },
  
  // Platform
  getPlatform: function() {
    return { platform: platform, pid: Process.id };
  },

  // List classes
  listClasses: function(filter) {
    if (!Java.available) return [];
    const classes = [];
    Java.perform(function() {
      Java.enumerateLoadedClasses({
        onMatch: function(className) {
          if (!filter || className.toLowerCase().includes(filter.toLowerCase())) {
            classes.push(className);
          }
        },
        onComplete: function() {}
      });
    });
    return classes;
  },

  // Hook custom method
  hookMethod: function(className, methodName) {
    if (!Java.available) return 'Java not available';
    Java.perform(function() {
      const clazz = Java.use(className);
      clazz[methodName].overloads.forEach(function(overload) {
        overload.implementation = function() {
          log('CUSTOM', Color.CYAN, `${className}.${methodName} called`);
          return overload.apply(this, arguments);
        };
      });
    });
    return `Hooked ${className}.${methodName}`;
  },
};

log('TOOLKIT', Color.GREEN, `\nAll hooks installed. Ready.\n`);
