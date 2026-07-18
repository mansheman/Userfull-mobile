/*
 * FRIDA TOOLKIT - APP-SPECIFIC HOOKER
 * Usage: frida -U -l app-hooker.js -f com.example.app --no-pause
 * 
 * Hook templates for common app analysis patterns:
 *   - Login / Auth interception
 *   - Token & API key extraction
 *   - Encryption/decryption capture
 *   - Database query logging
 *   - WebView JavaScript injection
 *   - Deep link / Intent capture
 */

'use strict';

var Color = {
  R: "\x1b[31m", G: "\x1b[32m", Y: "\x1b[33m",
  B: "\x1b[34m", M: "\x1b[35m", C: "\x1b[36m", W: "\x1b[37m",
  X: "\x1b[0m"
};

function tag(color, label, msg) {
  var ts = new Date().toISOString().split('T')[1].slice(0, 12);
  console.log(Color.W + "[" + ts + "]" + Color.X + " " + color + "[" + label + "]" + Color.X + " " + msg);
}

// ================================================================
// CONFIG: Edit these to match your target app
// ================================================================
var CONFIG = {
  // Set to true to enable each module
  ENABLE_AUTH: true,          // Login/register interception
  ENABLE_TOKEN: true,         // Token/API key extraction
  ENABLE_CRYPTO: true,        // Encryption/decryption capture
  ENABLE_DATABASE: true,      // Database read/write
  ENABLE_WEBVIEW: true,       // WebView JS injection + URL capture
  ENABLE_INTENT: true,        // Deep link + Intent data
  ENABLE_SHAREDPREFS: true,   // SharedPreferences read/write
  ENABLE_API: true,           // Retrofit/OkHttp API calls
  ENABLE_CLIPBOARD: true,     // Clipboard access
  ENABLE_CERT_PINNING: true,  // SSL pinning bypass
  ENABLE_ROOT_CHECK: true,    // Root/jailbreak detection bypass
  ENABLE_EMULATOR_CHECK: true,// Emulator detection bypass

  // WebView: inject custom JS into all WebViews
  WEBVIEW_JS: '(function(){' +
    '  console.log("[FRIDA] WebView JS injected");' +
    '  var origFetch = window.fetch;' +
    '  window.fetch = function() {' +
    '    console.log("[FRIDA-FETCH] " + arguments[0]);' +
    '    return origFetch.apply(this, arguments);' +
    '  };' +
    '})();',

  // SharedPreferences files to monitor (empty = all)
  SP_FILTER: ['auth', 'token', 'session', 'user', 'credential', 'secret', 'key', 'login'],

  // Keywords to highlight in logs (case-insensitive)
  KEYWORDS: ['password', 'token', 'secret', 'key', 'auth', 'bearer', 'api_key', 'credential',
             'pin', 'otp', 'session', 'cookie', 'jwt', 'signature', 'private'],
};

// ================================================================
// UTILITIES
// ================================================================
function dumpHex(buffer, len) {
  len = len || 128;
  if (typeof buffer === 'string') {
    var bytes = [];
    for (var i = 0; i < buffer.length; i++) bytes.push(buffer.charCodeAt(i));
    buffer = bytes;
  }
  var result = '';
  for (var i = 0; i < Math.min(buffer.length, len); i += 16) {
    var hex = '', ascii = '';
    for (var j = i; j < Math.min(i + 16, buffer.length); j++) {
      var b = (typeof buffer[j] === 'number') ? buffer[j] : (buffer[j] & 0xff);
      hex += (b < 16 ? '0' : '') + b.toString(16) + ' ';
      ascii += (b >= 32 && b < 127) ? String.fromCharCode(b) : '.';
    }
    result += '  ' + i.toString(16).padStart(8, '0') + ': ' + hex.padEnd(48) + ' ' + ascii + '\n';
  }
  return result;
}

function highlight(msg) {
  var lower = msg.toLowerCase();
  for (var i = 0; i < CONFIG.KEYWORDS.length; i++) {
    var kw = CONFIG.KEYWORDS[i];
    if (lower.indexOf(kw) !== -1) {
      return Color.R + Color.W + msg + Color.X;
    }
  }
  return msg;
}

function dumpObj(obj, depth) {
  depth = depth || 2;
  if (depth <= 0) return '...';
  if (obj === null) return 'null';
  if (obj === undefined) return 'undefined';
  if (typeof obj === 'string') return '"' + obj + '"';
  if (typeof obj === 'number' || typeof obj === 'boolean') return String(obj);
  if (Array.isArray(obj)) {
    var items = [];
    for (var i = 0; i < Math.min(obj.length, 10); i++) items.push(dumpObj(obj[i], depth - 1));
    if (obj.length > 10) items.push('...');
    return '[' + items.join(', ') + ']';
  }
  try {
    var keys = Object.keys(obj);
    var pairs = [];
    for (var i = 0; i < Math.min(keys.length, 10); i++) {
      pairs.push(keys[i] + ': ' + dumpObj(obj[keys[i]], depth - 1));
    }
    if (keys.length > 10) pairs.push('...');
    return '{' + pairs.join(', ') + '}';
  } catch (e) {
    return String(obj);
  }
}

// ================================================================
// AUTH HOOKS
// ================================================================
function hookAuth() {
  Java.perform(function() {
    // Hook EditText.getText() - capture password fields
    try {
      var EditText = Java.use('android.widget.EditText');
      var origGetText = EditText.getText;
      EditText.getText.implementation = function() {
        var result = origGetText.call(this);
        var str = result ? result.toString() : '';
        if (str.length > 0 && str.length < 128) {
          try {
            var inputType = this.getInputType();
            var isPassword = (inputType & 0x00000081) !== 0; // TYPE_TEXT_VARIATION_PASSWORD
            if (isPassword) {
              tag(Color.R, 'PASSWORD', 'Captured: "' + str + '"');
            }
          } catch (e) {}
        }
        return result;
      };
    } catch (e) {}

    // Hook Button onClick - detect login/register/submit buttons
    try {
      var Button = Java.use('android.widget.Button');
      var origPerformClick = Button.performClick;
      Button.performClick.implementation = function() {
        var text = '';
        try { text = this.getText().toString(); } catch (e) {}
        if (text.length > 0) {
          tag(Color.Y, 'BUTTON', 'Clicked: "' + text + '"');
        }
        return origPerformClick.call(this);
      };
    } catch (e) {}

    // Hook android.util.Log - capture app logs
    try {
      var Log = Java.use('android.util.Log');
      var levels = ['v', 'd', 'i', 'w', 'e'];
      for (var l = 0; l < levels.length; l++) {
        (function(level) {
          Log[level].overload('java.lang.String', 'java.lang.String').implementation = function(tagStr, msg) {
            var s = tagStr + ': ' + msg;
            if (s.indexOf('token') !== -1 || s.indexOf('auth') !== -1 || s.indexOf('pass') !== -1 ||
                s.indexOf('key') !== -1 || s.indexOf('secret') !== -1) {
              tag(Color.M, 'LOG:' + level.toUpperCase(), s);
            }
            return Log[level].call(this, tagStr, msg);
          };
        })(levels[l]);
      }
    } catch (e) {}

    tag(Color.G, 'HOOK', 'Auth hooks installed');
  });
}

// ================================================================
// TOKEN EXTRACTION
// ================================================================
function hookToken() {
  Java.perform(function() {
    // Hook all SharedPreferences getString - find tokens
    try {
      var SP = Java.use('android.app.SharedPreferencesImpl');
      SP.getString.overload('java.lang.String', 'java.lang.String').implementation = function(key, def) {
        var result = SP.getString.call(this, key, def);
        if (result && result !== def && (key.toLowerCase().indexOf('token') !== -1 ||
            key.toLowerCase().indexOf('auth') !== -1 || key.toLowerCase().indexOf('session') !== -1 ||
            key.toLowerCase().indexOf('bearer') !== -1 || key.toLowerCase().indexOf('jwt') !== -1 ||
            key.toLowerCase().indexOf('key') !== -1)) {
          tag(Color.C, 'TOKEN-SP', key + ' = "' + result + '" | file=' + this.mFile.value);
        }
        return result;
      };
    } catch (e) {}

    // Hook AccountManager - account credentials
    try {
      var AccountManager = Java.use('android.accounts.AccountManager');
      AccountManager.getPassword.implementation = function(account) {
        var pwd = AccountManager.getPassword.call(this, account);
        tag(Color.R, 'ACCOUNT', 'AccountManager.getPassword type=' + account.type + ' name=' + account.name);
        tag(Color.R, 'ACCOUNT', '  Password: "' + pwd + '"');
        return pwd;
      };

      AccountManager.getAuthToken.overload('android.accounts.Account', 'java.lang.String', 'boolean', 'android.accounts.AccountManagerCallback', 'android.os.Handler').implementation = function(account, authTokenType, notifyAuthFailure, callback, handler) {
        tag(Color.C, 'ACCOUNT', 'getAuthToken type=' + account.type + ' authType=' + authTokenType);
        return AccountManager.getAuthToken.call(this, account, authTokenType, notifyAuthFailure, callback, handler);
      };
    } catch (e) {}

    // Hook Base64 decode - common for token decoding
    try {
      var Base64 = Java.use('android.util.Base64');
      Base64.decode.overload('java.lang.String', 'int').implementation = function(str, flags) {
        if (str && str.indexOf('.') !== -1 && str.length > 30) {
          tag(Color.C, 'B64-DECODE', 'JWT-like string decoded [' + str.length + ' chars]');
        }
        return Base64.decode.call(this, str, flags);
      };
    } catch (e) {}

    tag(Color.G, 'HOOK', 'Token hooks installed');
  });
}

// ================================================================
// CRYPTO HOOKS
// ================================================================
function hookCrypto() {
  Java.perform(function() {
    // Cipher
    var Cipher = Java.use('javax.crypto.Cipher');

    Cipher.init.overload('int', 'java.security.Key').implementation = function(mode, key) {
      var modes = {1: 'ENCRYPT', 2: 'DECRYPT'};
      tag(Color.M, 'CRYPTO', 'Cipher.init(' + (modes[mode] || mode) + ') algo=' + key.getAlgorithm());
      try {
        var encoded = key.getEncoded();
        tag(Color.M, 'CRYPTO', '  Key:\n' + dumpHex(encoded, 32));
      } catch (e) {}
      return Cipher.init.call(this, mode, key);
    };

    Cipher.doFinal.overload('[B').implementation = function(input) {
      var result = Cipher.doFinal.call(this, input);
      tag(Color.M, 'CRYPTO', 'Cipher.doFinal in=' + input.length + 'B out=' + result.length + 'B');
      try {
        var str = Java.use('java.lang.String').$new(result, 'UTF-8');
        if (str.length() > 0 && str.length() < 500) {
          tag(Color.M, 'CRYPTO', '  Decrypted: ' + highlight(str.toString()));
        }
      } catch (e) {}
      return result;
    };

    // Mac (HMAC)
    var Mac = Java.use('javax.crypto.Mac');
    Mac.doFinal.overload('[B').implementation = function(input) {
      var result = Mac.doFinal.call(this, input);
      tag(Color.M, 'HMAC', 'Mac.doFinal in=' + input.length + 'B out=' + result.length + 'B');
      tag(Color.M, 'HMAC', '  Result:\n' + dumpHex(result, 32));
      return result;
    };

    // MessageDigest
    var Digest = Java.use('java.security.MessageDigest');
    Digest.digest.overload('[B').implementation = function(input) {
      var result = Digest.digest.call(this, input);
      var algo = this.getAlgorithm();
      tag(Color.M, 'HASH', 'MessageDigest.' + algo + ' in=' + input.length + 'B');
      tag(Color.M, 'HASH', '  Result:\n' + dumpHex(result, result.length));
      return result;
    };

    // SecureRandom
    var SecureRandom = Java.use('java.security.SecureRandom');
    SecureRandom.nextBytes.implementation = function(bytes) {
      tag(Color.M, 'RANDOM', 'SecureRandom.nextBytes ' + bytes.length + 'B');
      return SecureRandom.nextBytes.call(this, bytes);
    };

    tag(Color.G, 'HOOK', 'Crypto hooks installed');
  });
}

// ================================================================
// DATABASE HOOKS
// ================================================================
function hookDatabase() {
  Java.perform(function() {
    var SQLiteDatabase = Java.use('android.database.sqlite.SQLiteDatabase');

    SQLiteDatabase.rawQuery.overload('java.lang.String', '[Ljava.lang.String;').implementation = function(sql, args) {
      var upper = sql.toUpperCase().trim();
      if (upper.indexOf('SELECT') === 0) {
        tag(Color.B, 'DB-READ', 'query: ' + sql);
        if (args) tag(Color.B, 'DB-READ', '  args: ' + dumpObj(args));
      }
      return this.rawQuery(sql, args);
    };

    SQLiteDatabase.execSQL.overload('java.lang.String').implementation = function(sql) {
      var upper = sql.toUpperCase().trim();
      if (upper.indexOf('INSERT') === 0 || upper.indexOf('UPDATE') === 0 ||
          upper.indexOf('DELETE') === 0 || upper.indexOf('CREATE') === 0) {
        tag(Color.Y, 'DB-WRITE', sql);
      }
      return this.execSQL(sql);
    };

    SQLiteDatabase.insert.overload('java.lang.String', 'java.lang.String', 'android.content.ContentValues').implementation = function(table, nullHack, values) {
      tag(Color.Y, 'DB-INSERT', 'table=' + table);
      if (values) {
        try {
          var set = values.valueSet();
          var iter = set.iterator();
          while (iter.hasNext()) {
            var entry = iter.next();
            try {
              var k = entry.getKey();
              var v = entry.getValue();
              tag(Color.Y, 'DB-INSERT', '  ' + highlight(k + '=' + (v ? v.toString() : 'null')));
            } catch (e2) {}
          }
        } catch (e) {}
      }
      return this.insert(table, nullHack, values);
    };

    // Room Database
    try {
      var SupportSQLiteQuery = Java.use('androidx.sqlite.db.SupportSQLiteQuery');
      var FrameworkSQLiteDatabase = Java.use('androidx.sqlite.db.framework.FrameworkSQLiteDatabase');
      FrameworkSQLiteDatabase.query.overload('androidx.sqlite.db.SupportSQLiteQuery').implementation = function(query) {
        tag(Color.B, 'ROOM', 'query: ' + query.getSql());
        return FrameworkSQLiteDatabase.query.call(this, query);
      };
    } catch (e) {}

    // Realm
    try {
      var RealmQuery = Java.use('io.realm.RealmQuery');
      RealmQuery.findAll.implementation = function() {
        tag(Color.B, 'REALM', 'findAll called');
        return RealmQuery.findAll.call(this);
      };
    } catch (e) {}

    tag(Color.G, 'HOOK', 'Database hooks installed');
  });
}

// ================================================================
// WEBVIEW HOOKS
// ================================================================
function hookWebView() {
  Java.perform(function() {
    var WebView = Java.use('android.webkit.WebView');

    WebView.loadUrl.overload('java.lang.String').implementation = function(url) {
      tag(Color.C, 'WEBVIEW', 'loadUrl: ' + highlight(url));
      return WebView.loadUrl.call(this, url);
    };

    WebView.loadDataWithBaseURL.overload('java.lang.String', 'java.lang.String', 'java.lang.String', 'java.lang.String', 'java.lang.String').implementation = function(baseUrl, data, mimeType, encoding, historyUrl) {
      tag(Color.C, 'WEBVIEW', 'loadData base=' + baseUrl + ' len=' + data.length);
      return WebView.loadDataWithBaseURL.call(this, baseUrl, data, mimeType, encoding, historyUrl);
    };

    // Intercept JS evaluation
    WebView.evaluateJavascript.overload('java.lang.String', 'android.webkit.ValueCallback').implementation = function(script, callback) {
      tag(Color.M, 'WEBVIEW-JS', script.substring(0, 200));
      return WebView.evaluateJavascript.call(this, script, callback);
    };

    // Page events
    try {
      var WebViewClient = Java.use('android.webkit.WebViewClient');
      WebViewClient.onPageStarted.implementation = function(view, url, favicon) {
        tag(Color.B, 'WEBVIEW', 'onPageStarted: ' + url);
        return WebViewClient.onPageStarted.call(this, view, url, favicon);
      };
      WebViewClient.onPageFinished.implementation = function(view, url) {
        tag(Color.G, 'WEBVIEW', 'onPageFinished: ' + url);
        // Inject JS on page load
        if (CONFIG.WEBVIEW_JS) {
          try { view.evaluateJavascript(CONFIG.WEBVIEW_JS, null); } catch (e) {}
        }
        return WebViewClient.onPageFinished.call(this, view, url);
      };
    } catch (e) {}

    // WebView SSL error - proceed anyway
    try {
      WebViewClient.onReceivedSslError.implementation = function(view, handler, error) {
        tag(Color.Y, 'WEBVIEW', 'SSL error - proceeding');
        handler.proceed();
      };
    } catch (e) {}

    tag(Color.G, 'HOOK', 'WebView hooks installed');
  });
}

// ================================================================
// INTENT HOOKS
// ================================================================
function hookIntent() {
  Java.perform(function() {
    var Activity = Java.use('android.app.Activity');

    Activity.startActivity.overload('android.content.Intent').implementation = function(intent) {
      tag(Color.C, 'INTENT', 'startActivity action=' + intent.getAction());
      dumpIntent(intent);
      return Activity.startActivity.call(this, intent);
    };

    Activity.onCreate.overload('android.os.Bundle').implementation = function(bundle) {
      tag(Color.G, 'ACTIVITY', 'onCreate -> ' + this.$className);
      try {
        var intent = this.getIntent();
        if (intent) {
          tag(Color.C, 'INTENT-LAUNCH', 'action=' + intent.getAction() + ' data=' + intent.getDataString());
          dumpIntent(intent);
        }
      } catch (e) {}
      return Activity.onCreate.call(this, bundle);
    };

    // Register receiver
    try {
      var Context = Java.use('android.content.ContextWrapper');
      Context.registerReceiver.overload('android.content.BroadcastReceiver', 'android.content.IntentFilter').implementation = function(receiver, filter) {
        tag(Color.C, 'INTENT', 'registerReceiver');
        return Context.registerReceiver.call(this, receiver, filter);
      };
    } catch (e) {}
  });

  function dumpIntent(intent) {
    try {
      var extras = intent.getExtras();
      if (extras && extras.keySet()) {
        var keys = extras.keySet();
        var iter = keys.iterator();
        while (iter.hasNext()) {
          var key = iter.next();
          var val = extras.get(key);
          tag(Color.C, 'INTENT', '  ' + highlight(key + ' = ' + dumpObj(val)));
        }
      }
    } catch (e) {}
  }

  tag(Color.G, 'HOOK', 'Intent hooks installed');
}

// ================================================================
// SHAREDPREFS HOOKS
// ================================================================
function hookSharedPrefs() {
  Java.perform(function() {
    var SP = Java.use('android.app.SharedPreferencesImpl');
    var Editor = Java.use('android.app.SharedPreferencesImpl$EditorImpl');

    // Read
    SP.getString.overload('java.lang.String', 'java.lang.String').implementation = function(key, def) {
      var result = SP.getString.call(this, key, def);
      if (shouldLogSP(key)) {
        tag(Color.B, 'SP-READ', key + ' = "' + result + '"');
      }
      return result;
    };

    SP.getInt.overload('java.lang.String', 'int').implementation = function(key, def) {
      var result = SP.getInt.call(this, key, def);
      if (shouldLogSP(key)) tag(Color.B, 'SP-READ', key + ' = ' + result);
      return result;
    };

    SP.getBoolean.overload('java.lang.String', 'boolean').implementation = function(key, def) {
      var result = SP.getBoolean.call(this, key, def);
      if (shouldLogSP(key)) tag(Color.B, 'SP-READ', key + ' = ' + result);
      return result;
    };

    // Write
    Editor.putString.overload('java.lang.String', 'java.lang.String').implementation = function(key, val) {
      if (shouldLogSP(key)) {
        tag(Color.Y, 'SP-WRITE', highlight(key + ' = "' + val + '"'));
      }
      return Editor.putString.call(this, key, val);
    };

    Editor.putInt.overload('java.lang.String', 'int').implementation = function(key, val) {
      if (shouldLogSP(key)) tag(Color.Y, 'SP-WRITE', key + ' = ' + val);
      return Editor.putInt.call(this, key, val);
    };

    Editor.putBoolean.overload('java.lang.String', 'boolean').implementation = function(key, val) {
      if (shouldLogSP(key)) tag(Color.Y, 'SP-WRITE', key + ' = ' + val);
      return Editor.putBoolean.call(this, key, val);
    };

    Editor.commit.implementation = function() {
      tag(Color.Y, 'SP', 'commit()');
      return Editor.commit.call(this);
    };

    Editor.apply.implementation = function() {
      tag(Color.Y, 'SP', 'apply()');
      return Editor.apply.call(this);
    };
  });

  function shouldLogSP(key) {
    if (!key) return false;
    if (CONFIG.SP_FILTER.length === 0) return true;
    var lower = key.toLowerCase();
    for (var i = 0; i < CONFIG.SP_FILTER.length; i++) {
      if (lower.indexOf(CONFIG.SP_FILTER[i]) !== -1) return true;
    }
    return false;
  }

  tag(Color.G, 'HOOK', 'SharedPreferences hooks installed');
}

// ================================================================
// API HOOKS (OkHttp + Retrofit + HttpURLConnection)
// ================================================================
function hookAPI() {
  Java.perform(function() {
    // OkHttp3
    try {
      var RealCall = Java.use('okhttp3.RealCall');
      var Buffer = Java.use('okio.Buffer');

      RealCall.execute.implementation = function() {
        var req = this.request();
        var url = req.url().toString();
        var method = req.method();
        tag(Color.C, 'HTTP', '>> ' + method + ' ' + url);
        dumpOkHttpHeaders(req);
        dumpOkHttpBody(req);

        var resp = RealCall.execute.call(this);
        tag(Color.C, 'HTTP', '<< ' + resp.code() + ' ' + resp.message());
        dumpOkHttpResponseBody(resp);
        return resp;
      };

      RealCall.enqueue.implementation = function(callback) {
        var req = this.request();
        tag(Color.C, 'HTTP', '>> (async) ' + req.method() + ' ' + req.url());
        return RealCall.enqueue.call(this, callback);
      };
    } catch (e) {}

    // Retrofit
    try {
      var OkHttpCall = Java.use('retrofit2.OkHttpCall');
      OkHttpCall.execute.implementation = function() {
        tag(Color.M, 'RETROFIT', 'API call executing');
        return OkHttpCall.execute.call(this);
      };

      var DefaultCallAdapterFactory = Java.use('retrofit2.DefaultCallAdapterFactory');
      var GsonConverterFactory = Java.use('retrofit2.converter.gson.GsonConverterFactory');

      // Try to find Retrofit interface methods
      try {
        var ServiceMethod = Java.use('retrofit2.ServiceMethod');
        ServiceMethod.toResponse.implementation = function(body) {
          var result = ServiceMethod.toResponse.call(this, body);
          try {
            if (body) {
              tag(Color.M, 'RETROFIT', 'Response body: ' + body.toString().substring(0, 300));
            }
          } catch (e) {}
          return result;
        };
      } catch (e) {}
    } catch (e) {}

    // HttpURLConnection
    try {
      var HttpURLConnection = Java.use('java.net.HttpURLConnection');
      HttpURLConnection.getInputStream.implementation = function() {
        tag(Color.C, 'HTTP', 'getInputStream ' + this.getURL());
        return HttpURLConnection.getInputStream.call(this);
      };
      HttpURLConnection.getOutputStream.implementation = function() {
        tag(Color.C, 'HTTP', 'getOutputStream ' + this.getURL());
        return HttpURLConnection.getOutputStream.call(this);
      };
    } catch (e) {}
  });

  function dumpOkHttpHeaders(req) {
    try {
      var names = req.headers().names();
      var iter = names.iterator();
      while (iter.hasNext()) {
        var name = iter.next();
        var values = req.headers().values(name);
        tag(Color.C, 'HTTP', highlight('  ' + name + ': ' + values));
      }
    } catch (e) {}
  }

  function dumpOkHttpBody(req) {
    try {
      var body = req.body();
      if (body) {
        var Buffer = Java.use('okio.Buffer');
        var buf = Buffer.$new();
        body.writeTo(buf);
        var str = buf.readUtf8();
        if (str.length > 0 && str.length < 2000) {
          tag(Color.C, 'HTTP', '  Body: ' + highlight(str));
        }
      }
    } catch (e) {}
  }

  function dumpOkHttpResponseBody(resp) {
    try {
      var respBody = resp.body();
      if (respBody) {
        var contentType = respBody.contentType();
        if (contentType && contentType.toString().indexOf('json') !== -1) {
          var source = respBody.source();
          try {
            source.request(Java.use('java.lang.Long').$new(4000).longValue());
            var clone = source.buffer().clone();
            var bodyStr = clone.readUtf8();
            if (bodyStr.length > 0) {
              tag(Color.C, 'HTTP', '  Response: ' + bodyStr.substring(0, 500));
            }
          } catch (e) {}
        }
      }
    } catch (e) {}
  }

  tag(Color.G, 'HOOK', 'API hooks installed');
}

// ================================================================
// CLIPBOARD HOOKS
// ================================================================
function hookClipboard() {
  Java.perform(function() {
    try {
      var ClipboardManager = Java.use('android.content.ClipboardManager');
      ClipboardManager.setPrimaryClip.implementation = function(clip) {
        tag(Color.Y, 'CLIPBOARD', 'setPrimaryClip');
        if (clip) {
          for (var i = 0; i < clip.getItemCount(); i++) {
            var item = clip.getItemAt(i);
            tag(Color.Y, 'CLIPBOARD', '  Item[' + i + ']: "' + item.getText() + '"');
          }
        }
        return ClipboardManager.setPrimaryClip.call(this, clip);
      };

      ClipboardManager.getPrimaryClip.implementation = function() {
        tag(Color.B, 'CLIPBOARD', 'getPrimaryClip');
        return ClipboardManager.getPrimaryClip.call(this);
      };
    } catch (e) {}
  });
  tag(Color.G, 'HOOK', 'Clipboard hooks installed');
}

// ================================================================
// SSL PINNING BYPASS
// ================================================================
function bypassCertPinning() {
  Java.perform(function() {
    // HttpsURLConnection
    try {
      var HttpsURLConnection = Java.use('javax.net.ssl.HttpsURLConnection');
      HttpsURLConnection.setDefaultSSLSocketFactory.overload('javax.net.ssl.SSLSocketFactory').implementation = function() {};
    } catch (e) {}

    // OkHttp3
    try {
      var CertificatePinner = Java.use('okhttp3.CertificatePinner');
      CertificatePinner.check.overload('java.lang.String', 'java.util.List').implementation = function() {};
    } catch (e) {}

    // TrustManager
    try {
      var TrustManagerImpl = Java.use('com.android.org.conscrypt.TrustManagerImpl');
      TrustManagerImpl.verifyChain.implementation = function(chain) { return chain; };
    } catch (e) {}

    tag(Color.G, 'HOOK', 'SSL pinning bypass installed');
  });
}

// ================================================================
// ROOT / JAILBREAK DETECTION BYPASS
// ================================================================
function bypassRootCheck() {
  Java.perform(function() {
    // Common root detection methods

    // Check for su binary
    try {
      var File = Java.use('java.io.File');
      var origExists = File.exists;
      File.exists.implementation = function() {
        var path = this.getAbsolutePath();
        var lower = path.toLowerCase();
        if (lower.indexOf('su') !== -1 && (lower.indexOf('bin') !== -1 || lower.indexOf('xbin') !== -1)) {
          tag(Color.R, 'ROOT-CHECK', 'Blocked: File.exists("' + path + '") -> false');
          return false;
        }
        if (lower.indexOf('magisk') !== -1) {
          tag(Color.R, 'ROOT-CHECK', 'Blocked: File.exists("' + path + '") -> false');
          return false;
        }
        return origExists.call(this);
      };
    } catch (e) {}

    // Check Build.TAGS for test-keys
    try {
      var Build = Java.use('android.os.Build');
      var origTags = Build.TAGS;
      // Can't easily override static final field, use SystemProperties instead
    } catch (e) {}

    // Check PackageManager for root apps
    try {
      var PackageManager = Java.use('android.content.pm.PackageManager');
      PackageManager.getPackageInfo.overload('java.lang.String', 'int').implementation = function(pkg, flags) {
        var lower = pkg.toLowerCase();
        if (lower.indexOf('magisk') !== -1 || lower.indexOf('supersu') !== -1 ||
            lower.indexOf('busybox') !== -1 || lower.indexOf('xposed') !== -1) {
          tag(Color.R, 'ROOT-CHECK', 'Blocked: getPackageInfo("' + pkg + '")');
          var NameNotFound = Java.use('android.content.pm.PackageManager$NameNotFoundException');
          throw NameNotFound.$new();
        }
        return PackageManager.getPackageInfo.call(this, pkg, flags);
      };
    } catch (e) {}

    // Runtime.exec - block su/sudo commands
    try {
      var Runtime = Java.use('java.lang.Runtime');
      Runtime.exec.overload('java.lang.String').implementation = function(cmd) {
        if (cmd && (cmd.indexOf('su') === 0 || cmd.indexOf('which su') !== -1)) {
          tag(Color.R, 'ROOT-CHECK', 'Blocked: exec("' + cmd + '")');
          throw Java.use('java.io.IOException').$new('Permission denied');
        }
        return Runtime.exec.call(this, cmd);
      };
    } catch (e) {}

    tag(Color.G, 'HOOK', 'Root detection bypass installed');
  });
}

// ================================================================
// EMULATOR DETECTION BYPASS
// ================================================================
function bypassEmulatorCheck() {
  Java.perform(function() {
    // Build.FINGERPRINT / Build.MODEL
    try {
      var Build = Java.use('android.os.Build');
      // Hook SystemProperties.get instead
      var SystemProperties = Java.use('android.os.SystemProperties');
      SystemProperties.get.overload('java.lang.String', 'java.lang.String').implementation = function(key, def) {
        var result = SystemProperties.get.call(this, key, def);
        if (key === 'ro.build.fingerprint' && result && result.indexOf('generic') !== -1) {
          tag(Color.R, 'EMULATOR', 'Spoofing fingerprint: generic -> google/shamu');
        }
        if (key === 'ro.kernel.qemu') {
          tag(Color.R, 'EMULATOR', 'Spoofing qemu=1 -> 0');
          return '0';
        }
        return result;
      };
    } catch (e) {}

    // Check for emulator-specific files
    try {
      var File = Java.use('java.io.File');
      var origExists2 = File.exists;
      // already hooked above, add additional paths
    } catch (e) {}

    // TelephonyManager - check network type
    try {
      var TelephonyManager = Java.use('android.telephony.TelephonyManager');
      TelephonyManager.getNetworkOperatorName.implementation = function() {
        var result = TelephonyManager.getNetworkOperatorName.call(this);
        if (result && result.toLowerCase() === 'android') {
          tag(Color.R, 'EMULATOR', 'Spoofing network operator: Android -> Telkomsel');
          return Java.use('java.lang.String').$new('Telkomsel');
        }
        return result;
      };

      TelephonyManager.getDeviceId.implementation = function() {
        var result = TelephonyManager.getDeviceId.call(this);
        if (!result || result === '000000000000000') {
          tag(Color.R, 'EMULATOR', 'Spoofing IMEI');
          return Java.use('java.lang.String').$new('359876543210123');
        }
        return result;
      };
    } catch (e) {}

    tag(Color.G, 'HOOK', 'Emulator detection bypass installed');
  });
}

// ================================================================
// APP INFO DUMP
// ================================================================
function dumpAppInfo() {
  setTimeout(function() {
    Java.perform(function() {
      try {
        var ctx = Java.use('android.app.ActivityThread').currentApplication().getApplicationContext();
        var pm = ctx.getPackageManager();
        var pn = ctx.getPackageName();
        var pi = pm.getPackageInfo(pn, 0);

        console.log('\n' + Color.C + '=== APP INFO ===' + Color.X);
        console.log('  Package : ' + pi.packageName);
        console.log('  Version : ' + pi.versionName + ' (' + pi.versionCode + ')');
        console.log('  Data Dir: ' + ctx.getDataDir().getAbsolutePath());
        console.log('  PID     : ' + Process.id);

        // List databases
        var dbList = ctx.databaseList();
        console.log('  Databases:');
        for (var i = 0; i < dbList.length; i++) {
          var dbPath = ctx.getDatabasePath(dbList[i]);
          console.log('    ' + dbList[i] + ' -> ' + dbPath.getAbsolutePath());
        }

        // List permissions
        var permissions = pi.requestedPermissions;
        if (permissions) {
          console.log('  Permissions (' + permissions.length + '):');
          for (var j = 0; j < permissions.length; j++) {
            console.log('    ' + permissions[j]);
          }
        }
      } catch (e) {}
    });
    tag(Color.G, 'HOOK', 'App info dumped');
  }, 1500);
}

// ================================================================
// MAIN - INSTALL ALL
// ================================================================
console.log(Color.C + '\n╔══════════════════════════════════════════════╗' + Color.X);
console.log(Color.C + '║     APP-SPECIFIC HOOKER v1.0                ║' + Color.X);
console.log(Color.C + '╚══════════════════════════════════════════════╝\n' + Color.X);

tag(Color.G, 'SYS', 'Platform: ' + (Java.available ? 'Android' : 'iOS') + ' | PID: ' + Process.id);

if (!Java.available) {
  tag(Color.R, 'SYS', 'This tool is for Android only. Use ios.js for iOS.');
} else {
  if (CONFIG.ENABLE_AUTH) hookAuth();
  if (CONFIG.ENABLE_TOKEN) hookToken();
  if (CONFIG.ENABLE_CRYPTO) hookCrypto();
  if (CONFIG.ENABLE_DATABASE) hookDatabase();
  if (CONFIG.ENABLE_WEBVIEW) hookWebView();
  if (CONFIG.ENABLE_INTENT) hookIntent();
  if (CONFIG.ENABLE_SHAREDPREFS) hookSharedPrefs();
  if (CONFIG.ENABLE_API) hookAPI();
  if (CONFIG.ENABLE_CLIPBOARD) hookClipboard();
  if (CONFIG.ENABLE_CERT_PINNING) bypassCertPinning();
  if (CONFIG.ENABLE_ROOT_CHECK) bypassRootCheck();
  if (CONFIG.ENABLE_EMULATOR_CHECK) bypassEmulatorCheck();

  dumpAppInfo();
}

tag(Color.G, 'SYS', 'All hooks installed. Waiting for app activity...\n');

rpc.exports = {
  config: CONFIG,
  dumpAppInfo: dumpAppInfo,
};
