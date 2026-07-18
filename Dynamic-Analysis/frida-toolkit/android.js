/*
 * FRIDA TOOLKIT - ANDROID MODULE
 * Hooks: Activity lifecycle, Intents, SharedPreferences, SQLite, 
 *        WebView, Crypto, Clipboard, Notifications, BroadcastReceiver
 */

const { log, Color, dumpHex, dumpObj } = require('./core.js');

const ANDROID_HOOKS = {

  // --- Activity Lifecycle ---
  hookActivityLifecycle(full) {
    Java.perform(function() {
      const Activity = Java.use('android.app.Activity');
      
      ['onCreate', 'onStart', 'onResume', 'onPause', 'onStop', 'onDestroy', 'onRestart'].forEach(
        method => {
          if (!full && ['onStart', 'onResume', 'onPause', 'onStop', 'onRestart'].includes(method)) return;
          
          Activity[method].overload('android.os.Bundle').implementation = function(bundle) {
            log('ACTIVITY', Color.GREEN, `${method}(bundle=${dumpObj(bundle)}) -> ${this.$className}`);
            return this[method](bundle);
          };
        }
      );
      
      Activity.onCreate.overload('android.os.Bundle').implementation = function(bundle) {
        log('ACTIVITY', Color.GREEN, `onCreate(bundle) -> ${this.$className}`);
        
        try {
          const intent = this.getIntent();
          if (intent) {
            const action = intent.getAction();
            const data = intent.getDataString();
            log('ACTIVITY', Color.CYAN, `  Intent Action: ${action}, Data: ${data}`);
            
            const extras = intent.getExtras();
            if (extras && extras.keySet()) {
              const keys = extras.keySet();
              const iter = keys.iterator();
              while (iter.hasNext()) {
                const key = iter.next();
                const val = extras.get(key);
                log('ACTIVITY', Color.CYAN, `  Extra: ${key} = ${dumpObj(val)}`);
              }
            }
          }
        } catch (e) {}
        
        return this.onCreate(bundle);
      };
    });
    log('HOOK', Color.MAGENTA, 'Activity lifecycle hooks installed');
  },

  // --- SharedPreferences ---
  hookSharedPreferences() {
    Java.perform(function() {
      const SP = Java.use('android.app.SharedPreferencesImpl');
      const EditorImpl = Java.use('android.app.SharedPreferencesImpl$EditorImpl');

      SP.getString.overload('java.lang.String', 'java.lang.String').implementation = function(key, def) {
        const result = this.getString(key, def);
        log('SP-READ', Color.BLUE, `getString("${key}", "${def}") = "${result}" | file=${this.mFile.value}`);
        log('SP-READ', Color.BLUE, `  Callstack:\n${getBacktrace()}`);
        return result;
      };

      SP.getInt.overload('java.lang.String', 'int').implementation = function(key, def) {
        const result = this.getInt(key, def);
        log('SP-READ', Color.BLUE, `getInt("${key}", ${def}) = ${result}`);
        return result;
      };

      SP.getBoolean.overload('java.lang.String', 'boolean').implementation = function(key, def) {
        const result = this.getBoolean(key, def);
        log('SP-READ', Color.BLUE, `getBoolean("${key}", ${def}) = ${result}`);
        return result;
      };

      SP.getLong.overload('java.lang.String', 'long').implementation = function(key, def) {
        const result = this.getLong(key, def);
        log('SP-READ', Color.BLUE, `getLong("${key}", ${def}) = ${result}`);
        return result;
      };

      EditorImpl.putString.overload('java.lang.String', 'java.lang.String').implementation = function(key, val) {
        log('SP-WRITE', Color.YELLOW, `putString("${key}", "${val}")`);
        return this.putString(key, val);
      };

      EditorImpl.putInt.overload('java.lang.String', 'int').implementation = function(key, val) {
        log('SP-WRITE', Color.YELLOW, `putInt("${key}", ${val})`);
        return this.putInt(key, val);
      };

      EditorImpl.putBoolean.overload('java.lang.String', 'boolean').implementation = function(key, val) {
        log('SP-WRITE', Color.YELLOW, `putBoolean("${key}", ${val})`);
        return this.putBoolean(key, val);
      };

      EditorImpl.commit.implementation = function() {
        log('SP-WRITE', Color.YELLOW, `commit() called`);
        return this.commit();
      };

      EditorImpl.apply.implementation = function() {
        log('SP-WRITE', Color.YELLOW, `apply() called`);
        return this.apply();
      };
    });
    log('HOOK', Color.MAGENTA, 'SharedPreferences hooks installed');
  },

  // --- SQLite Database ---
  hookSQLite() {
    Java.perform(function() {
      const SQLiteDatabase = Java.use('android.database.sqlite.SQLiteDatabase');

      SQLiteDatabase.execSQL.overload('java.lang.String').implementation = function(sql) {
        const upper = sql.toUpperCase().trim();
        if (upper.startsWith('INSERT') || upper.startsWith('UPDATE') || upper.startsWith('DELETE') || upper.startsWith('DROP') || upper.startsWith('ALTER')) {
          log('SQLITE', Color.YELLOW, `execSQL: ${sql}`);
        }
        return this.execSQL(sql);
      };

      SQLiteDatabase.rawQuery.overload('java.lang.String', '[Ljava.lang.String;').implementation = function(sql, args) {
        log('SQLITE', Color.CYAN, `rawQuery: ${sql} | args=${dumpObj(args)}`);
        return this.rawQuery(sql, args);
      };

      SQLiteDatabase.insert.overload('java.lang.String', 'java.lang.String', 'android.content.ContentValues').implementation = function(table, nullColumnHack, values) {
        log('SQLITE', Color.YELLOW, `insert table="${table}"`);
        if (values) {
          const set = values.valueSet();
          const iter = set.iterator();
          while (iter.hasNext()) {
            const entry = iter.next();
            log('SQLITE', Color.YELLOW, `  ${entry.getKey()} = ${dumpObj(entry.getValue())}`);
          }
        }
        return this.insert(table, nullColumnHack, values);
      };

      SQLiteDatabase.update.overload('java.lang.String', 'android.content.ContentValues', 'java.lang.String', '[Ljava.lang.String;').implementation = function(table, values, where, whereArgs) {
        log('SQLITE', Color.YELLOW, `update table="${table}" where="${where}"`);
        return this.update(table, values, where, whereArgs);
      };

      SQLiteDatabase.delete.overload('java.lang.String', 'java.lang.String', '[Ljava.lang.String;').implementation = function(table, where, whereArgs) {
        log('SQLITE', Color.YELLOW, `delete table="${table}" where="${where}"`);
        return this.delete(table, where, whereArgs);
      };
    });
    log('HOOK', Color.MAGENTA, 'SQLite hooks installed');
  },

  // --- WebView ---
  hookWebView() {
    Java.perform(function() {
      const WebView = Java.use('android.webkit.WebView');

      WebView.loadUrl.overload('java.lang.String').implementation = function(url) {
        log('WEBVIEW', Color.CYAN, `loadUrl("${url}")`);
        return this.loadUrl(url);
      };

      WebView.loadUrl.overload('java.lang.String', 'java.util.Map').implementation = function(url, headers) {
        log('WEBVIEW', Color.CYAN, `loadUrl("${url}") | headers=${dumpObj(headers)}`);
        return this.loadUrl(url, headers);
      };

      WebView.loadData.overload('java.lang.String', 'java.lang.String', 'java.lang.String').implementation = function(data, mime, enc) {
        log('WEBVIEW', Color.CYAN, `loadData len=${data.length} mime=${mime}`);
        return this.loadData(data, mime, enc);
      };

      WebView.loadDataWithBaseURL.overload('java.lang.String', 'java.lang.String', 'java.lang.String', 'java.lang.String', 'java.lang.String').implementation = function(baseUrl, data, mime, enc, histUrl) {
        log('WEBVIEW', Color.CYAN, `loadDataWithBaseURL base="${baseUrl}" dataLen=${data.length}`);
        return this.loadDataWithBaseURL(baseUrl, data, mime, enc, histUrl);
      };

      try {
        const WebViewClient = Java.use('android.webkit.WebViewClient');
        WebViewClient.onPageStarted.implementation = function(view, url, favicon) {
          log('WEBVIEW', Color.BLUE, `onPageStarted "${url}"`);
          return this.onPageStarted(view, url, favicon);
        };
        WebViewClient.onPageFinished.implementation = function(view, url) {
          log('WEBVIEW', Color.GREEN, `onPageFinished "${url}"`);
          return this.onPageFinished(view, url);
        };
      } catch (e) {}

      // EvaluateJavascript interception
      WebView.evaluateJavascript.overload('java.lang.String', 'android.webkit.ValueCallback').implementation = function(script, callback) {
        log('WEBVIEW', Color.MAGENTA, `evaluateJavascript: ${script.substring(0, 200)}`);
        return this.evaluateJavascript(script, callback);
      };
    });
    log('HOOK', Color.MAGENTA, 'WebView hooks installed');
  },

  // --- Crypto ---
  hookCrypto() {
    Java.perform(function() {
      const Cipher = Java.use('javax.crypto.Cipher');

      Cipher.getInstance.overload('java.lang.String').implementation = function(transformation) {
        log('CRYPTO', Color.CYAN, `Cipher.getInstance("${transformation}")`);
        return this.getInstance(transformation);
      };

      Cipher.init.overload('int', 'java.security.Key').implementation = function(mode, key) {
        const modes = {1: 'ENCRYPT', 2: 'DECRYPT', 3: 'WRAP', 4: 'UNWRAP'};
        const algo = key.getAlgorithm();
        const format = key.getFormat();
        log('CRYPTO', Color.CYAN, `Cipher.init(${modes[mode] || mode}) algo=${algo} format=${format}`);
        
        try {
          const encoded = key.getEncoded();
          log('CRYPTO', Color.CYAN, `  Key:\n${dumpHex(encoded, 64)}`);
        } catch (e) {}
        
        return this.init(mode, key);
      };

      Cipher.doFinal.overload('[B').implementation = function(input) {
        log('CRYPTO', Color.YELLOW, `Cipher.doFinal(${input.length} bytes)`);
        log('CRYPTO', Color.YELLOW, `  Input:\n${dumpHex(input, 64)}`);
        const result = this.doFinal(input);
        log('CRYPTO', Color.YELLOW, `  Output:\n${dumpHex(result, 64)}`);
        return result;
      };

      const Mac = Java.use('javax.crypto.Mac');
      Mac.getInstance.overload('java.lang.String').implementation = function(algo) {
        log('CRYPTO', Color.CYAN, `Mac.getInstance("${algo}")`);
        return this.getInstance(algo);
      };

      Mac.doFinal.overload('[B').implementation = function(input) {
        log('CRYPTO', Color.CYAN, `Mac.doFinal(${input.length} bytes)`);
        const result = this.doFinal(input);
        log('CRYPTO', Color.CYAN, `  HMAC:\n${dumpHex(result)}`);
        return result;
      };

      const MessageDigest = Java.use('java.security.MessageDigest');
      MessageDigest.getInstance.overload('java.lang.String').implementation = function(algo) {
        log('CRYPTO', Color.CYAN, `MessageDigest.getInstance("${algo}")`);
        return this.getInstance(algo);
      };

      MessageDigest.digest.overload('[B').implementation = function(input) {
        log('CRYPTO', Color.CYAN, `MessageDigest.digest(${input.length} bytes)`);
        const result = this.digest(input);
        log('CRYPTO', Color.CYAN, `  Digest:\n${dumpHex(result)}`);
        return result;
      };
    });
    log('HOOK', Color.MAGENTA, 'Crypto hooks installed');
  },

  // --- Clipboard ---
  hookClipboard() {
    Java.perform(function() {
      const ClipboardManager = Java.use('android.content.ClipboardManager');
      
      ClipboardManager.setPrimaryClip.implementation = function(clip) {
        log('CLIPBOARD', Color.YELLOW, `setPrimaryClip()`);
        if (clip) {
          for (let i = 0; i < clip.getItemCount(); i++) {
            const item = clip.getItemAt(i);
            const text = item.getText();
            const uri = item.getUri();
            log('CLIPBOARD', Color.YELLOW, `  Item[${i}]: text="${text}" uri=${uri}`);
          }
        }
        return this.setPrimaryClip(clip);
      };
    });
    log('HOOK', Color.MAGENTA, 'Clipboard hooks installed');
  },

  // --- Notifications ---
  hookNotifications() {
    Java.perform(function() {
      const NotificationManager = Java.use('android.app.NotificationManager');
      
      NotificationManager.notify.overload('java.lang.String', 'int', 'android.app.Notification').implementation = function(tag, id, notif) {
        log('NOTIF', Color.YELLOW, `notify tag="${tag}" id=${id}`);
        try {
          if (notif.tickerText) log('NOTIF', Color.YELLOW, `  ticker="${notif.tickerText.value}"`);
        } catch (e) {}
        return this.notify(tag, id, notif);
      };

      NotificationManager.notify.overload('int', 'android.app.Notification').implementation = function(id, notif) {
        log('NOTIF', Color.YELLOW, `notify id=${id}`);
        return this.notify(id, notif);
      };
    });
    log('HOOK', Color.MAGENTA, 'Notification hooks installed');
  },

  // --- BroadcastReceiver ---
  hookBroadcasts() {
    Java.perform(function() {
      const Context = Java.use('android.content.Context');
      
      Context.sendBroadcast.overload('android.content.Intent').implementation = function(intent) {
        log('BROADCAST', Color.CYAN, `sendBroadcast action="${intent.getAction()}"`);
        dumpIntentExtras(intent);
        return this.sendBroadcast(intent);
      };

      Context.sendBroadcast.overload('android.content.Intent', 'java.lang.String').implementation = function(intent, perm) {
        log('BROADCAST', Color.CYAN, `sendBroadcast action="${intent.getAction()}" perm="${perm}"`);
        return this.sendBroadcast(intent, perm);
      };

      Context.sendOrderedBroadcast.overload('android.content.Intent', 'java.lang.String').implementation = function(intent, perm) {
        log('BROADCAST', Color.CYAN, `sendOrderedBroadcast action="${intent.getAction()}"`);
        return this.sendOrderedBroadcast(intent, perm);
      };
    });
    log('HOOK', Color.MAGENTA, 'BroadcastReceiver hooks installed');
  },

  // --- Intent dumping ---
  hookIntents() {
    Java.perform(function() {
      const Activity = Java.use('android.app.Activity');
      
      Activity.startActivity.overload('android.content.Intent').implementation = function(intent) {
        log('INTENT', Color.CYAN, `startActivity action="${intent.getAction()}" component="${intent.getComponent()}"`);
        dumpIntentExtras(intent);
        return this.startActivity(intent);
      };

      Activity.startActivityForResult.overload('android.content.Intent', 'int').implementation = function(intent, code) {
        log('INTENT', Color.CYAN, `startActivityForResult action="${intent.getAction()}" code=${code}`);
        dumpIntentExtras(intent);
        return this.startActivityForResult(intent, code);
      };
    });
    log('HOOK', Color.MAGENTA, 'Intent hooks installed');
  },

  // --- Runtime.exec ---
  hookRuntime() {
    Java.perform(function() {
      const Runtime = Java.use('java.lang.Runtime');
      
      Runtime.exec.overloads.forEach(function(overload) {
        overload.implementation = function() {
          let cmd = '';
          for (let i = 0; i < arguments.length; i++) {
            cmd += (Array.isArray(arguments[i]) ? arguments[i].join(' ') : String(arguments[i])) + ' ';
          }
          log('EXEC', Color.RED, `Runtime.exec: ${cmd.trim()}`);
          return overload.apply(this, arguments);
        };
      });

      const ProcessBuilder = Java.use('java.lang.ProcessBuilder');
      ProcessBuilder.start.implementation = function() {
        const cmd = this.command();
        log('EXEC', Color.RED, `ProcessBuilder.start: ${cmd}`);
        return this.start();
      };
    });
    log('HOOK', Color.MAGENTA, 'Runtime.exec hooks installed');
  },

  // --- OkHttp ---
  hookOkHttp() {
    Java.perform(function() {
      try {
        const RealCall = Java.use('okhttp3.RealCall');
        
        RealCall.execute.implementation = function() {
          const req = this.request();
          log('OKHTTP', Color.CYAN, `${req.method()} ${req.url()}`);
          dumpHeaders(req);
          const resp = this.execute();
          log('OKHTTP', Color.GREEN, `  Response: ${resp.code()} ${resp.message()}`);
          return resp;
        };

        RealCall.enqueue.implementation = function(callback) {
          const req = this.request();
          log('OKHTTP', Color.CYAN, `${req.method()} ${req.url()}`);
          return this.enqueue(callback);
        };
        log('HOOK', Color.MAGENTA, 'OkHttp hooks installed');
      } catch (e) {
        log('HOOK', Color.RED, `OkHttp not found: ${e}`);
      }
    });
  },

  // --- Retrofit ---
  hookRetrofit() {
    Java.perform(function() {
      try {
        const OkHttpCall = Java.use('retrofit2.OkHttpCall');
        
        OkHttpCall.execute.implementation = function() {
          log('RETROFIT', Color.CYAN, `API Call executed`);
          return this.execute();
        };
        log('HOOK', Color.MAGENTA, 'Retrofit hooks installed');
      } catch (e) {}
    });
  },

  // --- Dump all app info ---
  dumpAppInfo() {
    Java.perform(function() {
      const Context = Java.use('android.app.ActivityThread').currentApplication().getApplicationContext();
      const pm = Context.getPackageManager();
      const packageName = Context.getPackageName();
      const pi = pm.getPackageInfo(packageName, 0);
      
      log('APP', Color.MAGENTA, `Package: ${pi.packageName}`);
      log('APP', Color.MAGENTA, `Version: ${pi.versionName} (${pi.versionCode})`);
      log('APP', Color.MAGENTA, `Data Dir: ${Context.getDataDir().getAbsolutePath()}`);
      log('APP', Color.MAGENTA, `Files Dir: ${Context.getFilesDir().getAbsolutePath()}`);
      log('APP', Color.MAGENTA, `Cache Dir: ${Context.getCacheDir().getAbsolutePath()}`);
      
      try {
        const am = Java.use('android.app.ActivityManager');
        const memInfo = Java.use('android.os.Debug$MemoryInfo').$new();
        Java.use('android.os.Debug').getMemoryInfo(memInfo);
        log('APP', Color.MAGENTA, `PID: ${Process.id}`);
      } catch (e) {}
    });
  },

  // --- Keystore / KeyChain ---
  hookKeyStore() {
    Java.perform(function() {
      try {
        const KeyStore = Java.use('java.security.KeyStore');
        KeyStore.load.overload('java.security.KeyStore$LoadStoreParameter').implementation = function(param) {
          log('KEYSTORE', Color.CYAN, `KeyStore.load with parameters`);
          return this.load(param);
        };

        KeyStore.getEntry.overload('java.lang.String', 'java.security.KeyStore$ProtectionParameter').implementation = function(alias, param) {
          log('KEYSTORE', Color.CYAN, `KeyStore.getEntry alias="${alias}"`);
          return this.getEntry(alias, param);
        };

        const AndroidKeyStore = Java.use('android.security.keystore.AndroidKeyStoreSpi');
        AndroidKeyStore.engineGetCertificateChain.implementation = function(alias) {
          log('KEYSTORE', Color.CYAN, `AndroidKeyStore.engineGetCertificateChain alias="${alias}"`);
          return this.engineGetCertificateChain(alias);
        };
      } catch (e) {
        log('KEYSTORE', Color.RED, `KeyStore hooks: ${e}`);
      }
    });
  },

  // --- DexClassLoader - dynamic code loading ---
  hookClassLoader() {
    Java.perform(function() {
      const DexClassLoader = Java.use('dalvik.system.DexClassLoader');
      
      DexClassLoader.$init.overload('java.lang.String', 'java.lang.String', 'java.lang.String', 'java.lang.ClassLoader').implementation = function(dexPath, optDir, libPath, parent) {
        log('CLASSLOADER', Color.RED, `DexClassLoader dexPath="${dexPath}"`);
        return this.$init(dexPath, optDir, libPath, parent);
      };

      try {
        const PathClassLoader = Java.use('dalvik.system.PathClassLoader');
        PathClassLoader.$init.overload('java.lang.String', 'java.lang.ClassLoader').implementation = function(path, parent) {
          log('CLASSLOADER', Color.RED, `PathClassLoader path="${path}"`);
          return this.$init(path, parent);
        };
      } catch (e) {}

      try {
        const BaseDexClassLoader = Java.use('dalvik.system.BaseDexClassLoader');
        BaseDexClassLoader.$init.overload('java.lang.String', 'java.io.File', 'java.lang.String', 'java.lang.ClassLoader').implementation = function(dexPath, optDir, libPath, parent) {
          log('CLASSLOADER', Color.RED, `BaseDexClassLoader dexPath="${dexPath}"`);
          return this.$init(dexPath, optDir, libPath, parent);
        };
      } catch (e) {}
    });
    log('HOOK', Color.MAGENTA, 'ClassLoader hooks installed');
  },
};

// --- Helpers ---
function dumpIntentExtras(intent) {
  try {
    const extras = intent.getExtras();
    if (extras && extras.keySet()) {
      const keys = extras.keySet();
      const iter = keys.iterator();
      while (iter.hasNext()) {
        const key = iter.next();
        const val = extras.get(key);
        log('INTENT', Color.CYAN, `  Extra: ${key} = ${dumpObj(val)}`);
      }
    }
  } catch (e) {}
}

function dumpHeaders(request) {
  try {
    const headers = request.headers();
    const names = headers.names();
    const iter = names.iterator();
    while (iter.hasNext()) {
      const name = iter.next();
      const values = headers.values(name);
      log('OKHTTP', Color.CYAN, `  ${name}: ${values}`);
    }
  } catch (e) {}
}

function getBacktrace() {
  try {
    const Exception = Java.use('java.lang.Exception');
    const e = Exception.$new();
    const stack = e.getStackTrace();
    let out = '';
    for (let i = 0; i < Math.min(stack.length, 8); i++) {
      out += `    ${stack[i].toString()}\n`;
    }
    return out;
  } catch (e) {
    return 'N/A';
  }
}

// --- Install all ---
function installAll() {
  ANDROID_HOOKS.hookActivityLifecycle(true);
  ANDROID_HOOKS.hookSharedPreferences();
  ANDROID_HOOKS.hookSQLite();
  ANDROID_HOOKS.hookWebView();
  ANDROID_HOOKS.hookCrypto();
  ANDROID_HOOKS.hookClipboard();
  ANDROID_HOOKS.hookIntents();
  ANDROID_HOOKS.hookRuntime();
  ANDROID_HOOKS.hookKeyStore();
  ANDROID_HOOKS.hookClassLoader();
  
  setTimeout(() => {
    ANDROID_HOOKS.hookOkHttp();
    ANDROID_HOOKS.hookRetrofit();
    ANDROID_HOOKS.hookNotifications();
    ANDROID_HOOKS.hookBroadcasts();
  }, 2000);
  
  setTimeout(() => {
    ANDROID_HOOKS.dumpAppInfo();
  }, 1000);
  
  log('TOOLKIT', Color.GREEN, 'All Android hooks installed!');
}

rpc.exports = {
  installAll,
  ANDROID_HOOKS,
  dumpObj,
  dumpHex,
};

console.log(`\n${'='.repeat(60)}`);
console.log(`  FRIDA TOOLKIT - ANDROID MODULE LOADED`);
console.log(`  Available: rpc.exports.installAll()`);
console.log(`${'='.repeat(60)}\n`);
