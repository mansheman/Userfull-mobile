/*
 * FRIDA TOOLKIT - iOS MODULE
 * Hooks: NSUserDefaults, Keychain, NSURLSession, UIViewController, 
 *        UIAlertController, WKWebView, NSFileManager, NSNotificationCenter
 */

const { log, Color, dumpHex, dumpObj } = require('./core.js');

// --- Objective-C utilities ---
function objc_log(clazz, sel, args, retval) {
  let msg = `[${clazz} ${sel}]`;
  if (args && args.length > 1) {
    msg += ' args:';
    for (let i = 2; i < args.length; i++) {
      try {
        msg += ` ${ObjC.Object(args[i])}`;
      } catch (e) {
        msg += ` ${args[i]}`;
      }
    }
  }
  if (retval !== undefined) {
    try {
      msg += ` => ${ObjC.Object(retval)}`;
    } catch (e) {
      msg += ` => ${retval}`;
    }
  }
  log('OBJC', Color.CYAN, msg);
}

const IOS_HOOKS = {

  // --- NSUserDefaults ---
  hookNSUserDefaults() {
    const NSUserDefaults = ObjC.classes.NSUserDefaults;

    const origSet = NSUserDefaults['- setObject:forKey:'].implementation;
    Interceptor.attach(origSet, {
      onEnter(args) {
        const key = new ObjC.Object(args[3]).toString();
        const val = new ObjC.Object(args[2]);
        log('NSDEFAULTS', Color.YELLOW, `setObject forKey="${key}"`);
        try {
          if (val.isKindOfClass_(ObjC.classes.NSData)) {
            log('NSDEFAULTS', Color.YELLOW, `  Data (${val.length()} bytes):\n${dumpHex(val.bytes(), Math.min(val.length(), 128))}`);
          } else {
            log('NSDEFAULTS', Color.YELLOW, `  Value: ${val}`);
          }
        } catch (e) {}
      }
    });

    const origGet = NSUserDefaults['- objectForKey:'].implementation;
    Interceptor.attach(origGet, {
      onEnter(args) {
        const key = new ObjC.Object(args[2]).toString();
        log('NSDEFAULTS', Color.BLUE, `objectForKey("${key}")`);
      },
      onLeave(retval) {
        try {
          const val = new ObjC.Object(retval);
          if (val && !val.isNull()) {
            log('NSDEFAULTS', Color.BLUE, `  => ${val}`);
          }
        } catch (e) {}
      }
    });

    const origSetBool = NSUserDefaults['- setBool:forKey:'].implementation;
    Interceptor.attach(origSetBool, {
      onEnter(args) {
        const key = new ObjC.Object(args[3]).toString();
        log('NSDEFAULTS', Color.YELLOW, `setBool(${args[2]}) forKey="${key}"`);
      }
    });

    const origSetInt = NSUserDefaults['- setInteger:forKey:'].implementation;
    Interceptor.attach(origSetInt, {
      onEnter(args) {
        const key = new ObjC.Object(args[3]).toString();
        log('NSDEFAULTS', Color.YELLOW, `setInteger(${args[2]}) forKey="${key}"`);
      }
    });

    log('HOOK', Color.MAGENTA, 'NSUserDefaults hooks installed');
  },

  // --- Keychain ---
  hookKeychain() {
    const SecItemAdd = Module.findExportByName('Security', 'SecItemAdd');
    const SecItemCopyMatching = Module.findExportByName('Security', 'SecItemCopyMatching');
    const SecItemUpdate = Module.findExportByName('Security', 'SecItemUpdate');
    const SecItemDelete = Module.findExportByName('Security', 'SecItemDelete');

    if (SecItemAdd) {
      Interceptor.attach(SecItemAdd, {
        onEnter(args) {
          const dict = new ObjC.Object(args[0]);
          log('KEYCHAIN', Color.YELLOW, `SecItemAdd: ${dumpCFDict(dict)}`);
        },
        onLeave(retval) {
          log('KEYCHAIN', Color.GREEN, `  => OSStatus=${retval}`);
        }
      });
    }

    if (SecItemCopyMatching) {
      Interceptor.attach(SecItemCopyMatching, {
        onEnter(args) {
          const dict = new ObjC.Object(args[0]);
          log('KEYCHAIN', Color.BLUE, `SecItemCopyMatching: ${dumpCFDict(dict)}`);
        },
        onLeave(retval) {
          log('KEYCHAIN', Color.BLUE, `  => OSStatus=${retval}`);
          if (retval.toInt32() === 0) {
            try {
              const result = new ObjC.Object(this.context.x1);
              if (result && !result.isNull()) {
                if (result.isKindOfClass_(ObjC.classes.NSData)) {
                  log('KEYCHAIN', Color.BLUE, `  Data:\n${dumpHex(result.bytes(), Math.min(result.length(), 128))}`);
                } else if (result.isKindOfClass_(ObjC.classes.NSDictionary)) {
                  log('KEYCHAIN', Color.BLUE, `  Result: ${dumpCFDict(result)}`);
                } else {
                  log('KEYCHAIN', Color.BLUE, `  Result: ${result}`);
                }
              }
            } catch (e) {}
          }
        }
      });
    }

    if (SecItemUpdate) {
      Interceptor.attach(SecItemUpdate, {
        onEnter(args) {
          const query = new ObjC.Object(args[0]);
          const attrs = new ObjC.Object(args[1]);
          log('KEYCHAIN', Color.YELLOW, `SecItemUpdate query=${dumpCFDict(query)} attrs=${dumpCFDict(attrs)}`);
        }
      });
    }

    if (SecItemDelete) {
      Interceptor.attach(SecItemDelete, {
        onEnter(args) {
          const query = new ObjC.Object(args[0]);
          log('KEYCHAIN', Color.RED, `SecItemDelete: ${dumpCFDict(query)}`);
        }
      });
    }

    log('HOOK', Color.MAGENTA, 'Keychain hooks installed');
  },

  // --- NSURLSession ---
  hookNSURLSession() {
    const NSURLSession = ObjC.classes.NSURLSession;
    const NSURLConnection = ObjC.classes.NSURLConnection;

    // Modern NSURLSession
    const origDataTask = NSURLSession['- dataTaskWithRequest:'].implementation;
    Interceptor.attach(origDataTask, {
      onEnter(args) {
        const request = new ObjC.Object(args[2]);
        const url = request.URL().absoluteString();
        const method = request.HTTPMethod();
        log('NSURL', Color.CYAN, `${method ? method.toString() : 'GET'} ${url}`);
        
        try {
          const headers = request.allHTTPHeaderFields();
          if (headers) {
            const keys = headers.allKeys();
            const count = keys.count();
            for (let i = 0; i < count; i++) {
              const k = keys.objectAtIndex_(i);
              log('NSURL', Color.CYAN, `  ${k}: ${headers.objectForKey_(k)}`);
            }
          }
        } catch (e) {}

        try {
          const body = request.HTTPBody();
          if (body && body.length() > 0) {
            log('NSURL', Color.CYAN, `  Body (${body.length()} bytes):\n${dumpHex(body.bytes(), Math.min(body.length(), 256))}`);
          }
        } catch (e) {}
      }
    });

    const origDataTaskCompletion = NSURLSession['- dataTaskWithRequest:completionHandler:'].implementation;
    Interceptor.attach(origDataTaskCompletion, {
      onEnter(args) {
        const request = new ObjC.Object(args[2]);
        log('NSURL', Color.CYAN, `${request.HTTPMethod()} ${request.URL().absoluteString()} [completion]`);
      }
    });

    // Legacy NSURLConnection
    try {
      const origSendSync = NSURLConnection['+ sendSynchronousRequest:returningResponse:error:'].implementation;
      Interceptor.attach(origSendSync, {
        onEnter(args) {
          const request = new ObjC.Object(args[2]);
          log('NSURLCON', Color.CYAN, `sendSync ${request.HTTPMethod()} ${request.URL().absoluteString()}`);
        },
        onLeave(retval) {
          try {
            const data = new ObjC.Object(retval);
            if (data && data.length && data.length() > 0) {
              log('NSURLCON', Color.CYAN, `  Response ${data.length()} bytes`);
            }
          } catch (e) {}
        }
      });
    } catch (e) {}

    log('HOOK', Color.MAGENTA, 'NSURLSession hooks installed');
  },

  // --- UIViewController lifecycle ---
  hookViewControllers() {
    const UIViewController = ObjC.classes.UIViewController;

    ['- viewDidLoad', '- viewWillAppear:', '- viewDidAppear:', '- viewWillDisappear:', '- viewDidDisappear:'].forEach(sel => {
      Interceptor.attach(UIViewController[sel].implementation, {
        onEnter(args) {
          log('VC', Color.GREEN, `${sel} -> ${this.constructor && this.constructor.$className || '?'}`);
        }
      });
    });

    log('HOOK', Color.MAGENTA, 'UIViewController hooks installed');
  },

  // --- UIAlertController ---
  hookAlerts() {
    const UIAlertController = ObjC.classes.UIAlertController;

    const orig = UIAlertController['+ alertControllerWithTitle:message:preferredStyle:'].implementation;
    Interceptor.attach(orig, {
      onEnter(args) {
        const title = new ObjC.Object(args[2]).toString();
        const message = new ObjC.Object(args[3]).toString();
        log('ALERT', Color.YELLOW, `Title: "${title}"`);
        if (message && message !== 'null') {
          log('ALERT', Color.YELLOW, `  Message: "${message}"`);
        }
      }
    });

    Interceptor.attach(UIAlertController['- addAction:'].implementation, {
      onEnter(args) {
        const action = new ObjC.Object(args[2]);
        if (action) {
          const title = action.title();
          log('ALERT', Color.YELLOW, `  Action: "${title}"`);
        }
      }
    });

    log('HOOK', Color.MAGENTA, 'UIAlertController hooks installed');
  },

  // --- WKWebView ---
  hookWKWebView() {
    let WKWebView;
    try {
      WKWebView = ObjC.classes.WKWebView;
    } catch (e) {
      log('WEBVIEW', Color.RED, 'WKWebView not available');
      return;
    }

    Interceptor.attach(WKWebView['- loadRequest:'].implementation, {
      onEnter(args) {
        const request = new ObjC.Object(args[2]);
        const url = request.URL().absoluteString();
        log('WEBVIEW', Color.CYAN, `loadRequest: ${url}`);
      }
    });

    Interceptor.attach(WKWebView['- loadHTMLString:baseURL:'].implementation, {
      onEnter(args) {
        const html = new ObjC.Object(args[2]).toString();
        const base = new ObjC.Object(args[3]);
        log('WEBVIEW', Color.CYAN, `loadHTMLString len=${html.length} baseURL=${base}`);
      }
    });

    try {
      Interceptor.attach(WKWebView['- evaluateJavaScript:completionHandler:'].implementation, {
        onEnter(args) {
          const js = new ObjC.Object(args[2]).toString();
          log('WEBVIEW', Color.MAGENTA, `evaluateJavaScript: ${js.substring(0, 200)}`);
        }
      });
    } catch (e) {}

    log('HOOK', Color.MAGENTA, 'WKWebView hooks installed');
  },

  // --- NSFileManager ---
  hookFileManager() {
    const NSFileManager = ObjC.classes.NSFileManager;

    const origCreateFile = NSFileManager['- createFileAtPath:contents:attributes:'].implementation;
    Interceptor.attach(origCreateFile, {
      onEnter(args) {
        const path = new ObjC.Object(args[2]).toString();
        log('FILE', Color.YELLOW, `createFileAtPath: ${path}`);
      }
    });

    const origCopy = NSFileManager['- copyItemAtPath:toPath:error:'].implementation;
    Interceptor.attach(origCopy, {
      onEnter(args) {
        const src = new ObjC.Object(args[2]).toString();
        const dst = new ObjC.Object(args[3]).toString();
        log('FILE', Color.YELLOW, `copyItem "${src}" -> "${dst}"`);
      }
    });

    const origMove = NSFileManager['- moveItemAtPath:toPath:error:'].implementation;
    Interceptor.attach(origMove, {
      onEnter(args) {
        const src = new ObjC.Object(args[2]).toString();
        const dst = new ObjC.Object(args[3]).toString();
        log('FILE', Color.YELLOW, `moveItem "${src}" -> "${dst}"`);
      }
    });

    const origRemove = NSFileManager['- removeItemAtPath:error:'].implementation;
    Interceptor.attach(origRemove, {
      onEnter(args) {
        const path = new ObjC.Object(args[2]).toString();
        log('FILE', Color.RED, `removeItemAtPath: ${path}`);
      }
    });

    const origContents = NSFileManager['- contentsAtPath:'].implementation;
    Interceptor.attach(origContents, {
      onEnter(args) {
        const path = new ObjC.Object(args[2]).toString();
        log('FILE', Color.BLUE, `contentsAtPath: ${path}`);
      }
    });

    log('HOOK', Color.MAGENTA, 'NSFileManager hooks installed');
  },

  // --- NSNotificationCenter ---
  hookNotificationCenter() {
    const NSNotificationCenter = ObjC.classes.NSNotificationCenter;
    const nc = ObjC.classes.NSNotificationCenter.defaultCenter();

    const origPost = NSNotificationCenter['- postNotificationName:object:'].implementation;
    Interceptor.attach(origPost, {
      onEnter(args) {
        const name = new ObjC.Object(args[2]).toString();
        const obj = new ObjC.Object(args[3]);
        log('NOTIFCENTER', Color.CYAN, `postNotification: "${name}" object=${obj}`);
      }
    });

    const origPostWithUserInfo = NSNotificationCenter['- postNotificationName:object:userInfo:'].implementation;
    Interceptor.attach(origPostWithUserInfo, {
      onEnter(args) {
        const name = new ObjC.Object(args[2]).toString();
        const userInfo = new ObjC.Object(args[4]);
        log('NOTIFCENTER', Color.CYAN, `postNotification: "${name}" userInfo=${dumpCFDict(userInfo)}`);
      }
    });

    log('HOOK', Color.MAGENTA, 'NSNotificationCenter hooks installed');
  },

  // --- Crypto (CommonCrypto) ---
  hookCommonCrypto() {
    const CCCrypt = Module.findExportByName('libcommonCrypto.dylib', 'CCCrypt');
    const CC_SHA256 = Module.findExportByName('libcommonCrypto.dylib', 'CC_SHA256');
    const CCHmac = Module.findExportByName('libcommonCrypto.dylib', 'CCHmac');

    if (CCCrypt) {
      Interceptor.attach(CCCrypt, {
        onEnter(args) {
          const op = args[0].toInt32();
          const alg = args[1].toInt32();
          const ops = {0: 'ENCRYPT', 1: 'DECRYPT'};
          const algs = {0: 'AES128', 1: 'DES', 2: '3DES', 3: 'CAST', 4: 'RC4', 5: 'RC2'};
          const keyLen = args[4].toInt32();
          log('CRYPTO', Color.CYAN, `CCCrypt(${ops[op] || op}, ${algs[alg] || alg}) keyLen=${keyLen}`);
          
          if (args[2] && !args[2].isNull()) {
            log('CRYPTO', Color.CYAN, `  Key:\n${dumpHex(args[5].readByteArray(Math.min(keyLen, 32)))}`);
          }
          if (args[3] && !args[3].isNull()) {
            log('CRYPTO', Color.CYAN, `  IV:\n${dumpHex(args[6].readByteArray(16))}`);
          }
          const dataIn = args[7];
          const dataLen = args[8].toInt32();
          if (dataLen > 0) {
            log('CRYPTO', Color.CYAN, `  Input (${dataLen} bytes):\n${dumpHex(dataIn.readByteArray(Math.min(dataLen, 64)))}`);
          }
        },
        onLeave(retval) {
          log('CRYPTO', Color.CYAN, `  => OSStatus=${retval}`);
        }
      });
    }

    if (CC_SHA256) {
      Interceptor.attach(CC_SHA256, {
        onEnter(args) {
          const len = args[1].toInt32();
          log('CRYPTO', Color.CYAN, `CC_SHA256(${len} bytes)`);
          if (len > 0) {
            log('CRYPTO', Color.CYAN, `  Input:\n${dumpHex(args[0].readByteArray(Math.min(len, 64)))}`);
          }
        },
        onLeave(retval) {
          try {
            log('CRYPTO', Color.CYAN, `  Digest:\n${dumpHex(retval.readByteArray(32))}`);
          } catch (e) {}
        }
      });
    }

    log('HOOK', Color.MAGENTA, 'CommonCrypto hooks installed');
  },

  // --- Method Swizzling ---
  hookSwizzling() {
    const origExchange = ObjC.classes.NSObject['+ exchangeInstanceMethod1:method2:'];
    if (origExchange) {
      Interceptor.attach(origExchange.implementation, {
        onEnter(args) {
          log('SWIZZLE', Color.RED, 'method_exchangeImplementations detected!');
          try { log('SWIZZLE', Color.RED, `  ${new ObjC.Object(args[2])}`); } catch(e) {}
          try { log('SWIZZLE', Color.RED, `  ${new ObjC.Object(args[3])}`); } catch(e) {}
        }
      });
    }
  },

  // --- Dylib Loading ---
  hookDylib() {
    const dlopen = Module.findExportByName(null, 'dlopen');
    if (dlopen) {
      Interceptor.attach(dlopen, {
        onEnter(args) {
          const path = args[0].readUtf8String();
          log('DLOPEN', Color.MAGENTA, `dlopen("${path}")`);
        }
      });
    }
  },

  // --- Pasteboard ---
  hookPasteboard() {
    const UIPasteboard = ObjC.classes.UIPasteboard;

    Interceptor.attach(UIPasteboard['+ generalPasteboard'].implementation, {
      onLeave(retval) {
        log('PASTEBOARD', Color.BLUE, 'generalPasteboard accessed');
      }
    });

    const origSetStr = UIPasteboard['- setString:'].implementation;
    Interceptor.attach(origSetStr, {
      onEnter(args) {
        const str = new ObjC.Object(args[2]).toString();
        log('PASTEBOARD', Color.YELLOW, `setString: "${str}"`);
      }
    });

    const origGetStr = UIPasteboard['- string'].implementation;
    Interceptor.attach(origGetStr, {
      onLeave(retval) {
        try {
          const str = new ObjC.Object(retval).toString();
          log('PASTEBOARD', Color.BLUE, `string => "${str}"`);
        } catch (e) {}
      }
    });

    Im;
    log('HOOK', Color.MAGENTA, 'UIPasteboard hooks installed');
  },

  // --- Process Info ---
  dumpProcessInfo() {
    const bundle = ObjC.classes.NSBundle.mainBundle();
    const infoDict = bundle.infoDictionary();
    
    log('PROC', Color.MAGENTA, `Bundle ID: ${infoDict.objectForKey_('CFBundleIdentifier')}`);
    log('PROC', Color.MAGENTA, `Version: ${infoDict.objectForKey_('CFBundleShortVersionString')}`);
    log('PROC', Color.MAGENTA, `Executable: ${bundle.executablePath()}`);
    log('PROC', Color.MAGENTA, `Bundle Path: ${bundle.bundlePath()}`);
    
    const home = ObjC.classes.NSProcessInfo.processInfo().environment().objectForKey_('HOME');
    log('PROC', Color.MAGENTA, `HOME: ${home}`);
    log('PROC', Color.MAGENTA, `PID: ${Process.id}`);
    
    try {
      const tmp = ObjC.classes.NSTemporaryDirectory();
      log('PROC', Color.MAGENTA, `TMP: ${tmp}`);
    } catch (e) {}
  },
};

// --- Helpers ---
function dumpCFDict(dict) {
  if (!dict || dict.isNull()) return 'null';
  try {
    if (dict.isKindOfClass_(ObjC.classes.NSDictionary)) {
      let out = '{';
      const keys = dict.allKeys();
      const count = keys.count();
      for (let i = 0; i < Math.min(count, 20); i++) {
        const k = keys.objectAtIndex_(i);
        const v = dict.objectForKey_(k);
        out += `${k}: ${dumpCFValue(v)}, `;
      }
      if (count > 20) out += '...';
      return out + '}';
    } else {
      return dict.toString();
    }
  } catch (e) {
    return String(dict);
  }
}

function dumpCFValue(val) {
  if (!val || val.isNull()) return 'null';
  try {
    if (val.isKindOfClass_(ObjC.classes.NSData)) {
      return `Data(${val.length()}B)`;
    } else if (val.isKindOfClass_(ObjC.classes.NSString)) {
      return `"${val.toString()}"`;
    } else if (val.isKindOfClass_(ObjC.classes.NSNumber)) {
      return val.toString();
    } else if (val.isKindOfClass_(ObjC.classes.NSDate)) {
      return val.toString();
    } else {
      return val.toString();
    }
  } catch (e) {
    return String(val);
  }
}

function installAll() {
  IOS_HOOKS.hookNSUserDefaults();
  IOS_HOOKS.hookKeychain();
  IOS_HOOKS.hookNSURLSession();
  IOS_HOOKS.hookViewControllers();
  IOS_HOOKS.hookAlerts();
  IOS_HOOKS.hookFileManager();
  IOS_HOOKS.hookNotificationCenter();
  IOS_HOOKS.hookCommonCrypto();
  IOS_HOOKS.hookSwizzling();
  IOS_HOOKS.hookDylib();
  IOS_HOOKS.hookPasteboard();
  
  setTimeout(() => {
    IOS_HOOKS.hookWKWebView();
  }, 2000);
  
  setTimeout(() => {
    IOS_HOOKS.dumpProcessInfo();
  }, 500);
  
  log('TOOLKIT', Color.GREEN, 'All iOS hooks installed!');
}

rpc.exports = {
  installAll,
  IOS_HOOKS,
  dumpObj,
  dumpHex,
  dumpCFDict,
};

console.log(`\n${'='.repeat(60)}`);
console.log(`  FRIDA TOOLKIT - iOS MODULE LOADED`);
console.log(`  Available: rpc.exports.installAll()`);
console.log(`${'='.repeat(60)}\n`);
