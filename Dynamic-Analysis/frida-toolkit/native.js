/*
 * FRIDA TOOLKIT - NATIVE MODULE
 * Native library hooking, memory scanning, anti-debug bypass
 * Works on Android + iOS
 */

const { log, Color, dumpHex } = require('./core.js');

const NATIVE_HOOKS = {

  // --- Anti-Debug Bypass ---
  bypassAntiDebug() {
    // ptrace
    const ptrace = Module.findExportByName(null, 'ptrace');
    if (ptrace) {
      Interceptor.attach(ptrace, {
        onEnter(args) {
          const request = args[0].toInt32();
          if (request === 0) { // PTRACE_TRACEME
            log('ANTIDBG', Color.RED, `ptrace(PTRACE_TRACEME) - BLOCKED`);
            this.blocked = true;
          }
        },
        onLeave(retval) {
          if (this.blocked) {
            retval.replace(0); // Return success
            log('ANTIDBG', Color.GREEN, `ptrace bypass: returned 0`);
          }
        }
      });
    }

    // fork
    const forkPtr = Module.findExportByName(null, 'fork');
    if (forkPtr) {
      Interceptor.attach(forkPtr, {
        onEnter(args) {
          log('ANTIDBG', Color.RED, 'fork() detected');
        },
        onLeave(retval) {
          log('ANTIDBG', Color.YELLOW, `fork returned: ${retval}`);
        }
      });
    }

    // sysctl (iOS jailbreak/anti-debug)
    const sysctl = Module.findExportByName(null, 'sysctl');
    if (sysctl) {
      Interceptor.attach(sysctl, {
        onEnter(args) {
          try {
            const name = args[0].readPointer();
            if (name) {
              const n0 = name.readInt(); // MIB[0]
              const n1 = name.add(4).readInt(); // MIB[1]
              const n2 = name.add(8).readInt(); // MIB[2]
              
              // CTL_KERN=1, KERN_PROC=14, KERN_PROC_PID=1 => process info
              if (n0 === 1 && n1 === 14) {
                log('ANTIDBG', Color.RED, `sysctl(KERN_PROC) - anti-debug check`);
              }
            }
          } catch (e) {}
        }
      });
    }

    // getppid
    const getppid = Module.findExportByName(null, 'getppid');
    if (getppid) {
      Interceptor.attach(getppid, {
        onLeave(retval) {
          log('ANTIDBG', Color.CYAN, `getppid() = ${retval}`);
        }
      });
    }

    log('HOOK', Color.MAGENTA, 'Anti-debug bypass installed');
  },

  // --- Anti-Tamper / Integrity Check ---
  bypassIntegrity() {
    // Android: PackageManager signature check
    if (Java.available) {
      Java.perform(function() {
        try {
          const PackageManager = Java.use('android.content.pm.PackageManager');
          PackageManager.getPackageInfo.overload('java.lang.String', 'int').implementation = function(packageName, flags) {
            const GET_SIGNATURES = 64;
            if (flags & GET_SIGNATURES) {
              log('INTEGRITY', Color.RED, `getPackageInfo(${packageName}, GET_SIGNATURES) - integrity check`);
              // Return original - let it pass unless you want to spoof
            }
            return this.getPackageInfo(packageName, flags);
          };
        } catch (e) {}
      });
    }

    // Frida detection bypass
    bypassFridaDetection();

    log('HOOK', Color.MAGENTA, 'Integrity check bypass installed');
  },

  // --- JNI Hooking ---
  hookJNI() {
    if (!Java.available) return;

    const GetStringUTFChars = Module.findExportByName('libart.so', 'GetStringUTFChars') || 
                               Module.findExportByName('libdvm.so', 'GetStringUTFChars');
    
    if (GetStringUTFChars) {
      Interceptor.attach(GetStringUTFChars, {
        onLeave(retval) {
          try {
            const str = retval.readUtf8String();
            if (str && str.length > 0 && str.length < 500) {
              log('JNI', Color.CYAN, `GetStringUTFChars => "${str}"`);
            }
          } catch (e) {}
        }
      });
    }

    // NewStringUTF
    const NewStringUTF = Module.findExportByName('libart.so', 'NewStringUTF') || 
                          Module.findExportByName('libdvm.so', 'NewStringUTF');
    if (NewStringUTF) {
      Interceptor.attach(NewStringUTF, {
        onEnter(args) {
          try {
            const str = args[1].readUtf8String();
            if (str && str.length > 0 && str.length < 500) {
              log('JNI', Color.YELLOW, `NewStringUTF("${str}")`);
            }
          } catch (e) {}
        }
      });
    }

    // FindClass
    const FindClass = Module.findExportByName('libart.so', 'FindClass') || 
                       Module.findExportByName('libdvm.so', 'FindClass');
    if (FindClass) {
      Interceptor.attach(FindClass, {
        onEnter(args) {
          try {
            const name = args[1].readUtf8String();
            log('JNI', Color.MAGENTA, `FindClass("${name}")`);
          } catch (e) {}
        }
      });
    }

    // GetStaticMethodID / GetMethodID
    const GetStaticMethodID = Module.findExportByName('libart.so', 'GetStaticMethodID');
    if (GetStaticMethodID) {
      Interceptor.attach(GetStaticMethodID, {
        onEnter(args) {
          try {
            const name = args[2].readUtf8String();
            const sig = args[3].readUtf8String();
            log('JNI', Color.MAGENTA, `GetStaticMethodID("${name}", "${sig}")`);
          } catch (e) {}
        }
      });
    }

    const GetMethodID = Module.findExportByName('libart.so', 'GetMethodID');
    if (GetMethodID) {
      Interceptor.attach(GetMethodID, {
        onEnter(args) {
          try {
            const name = args[2].readUtf8String();
            const sig = args[3].readUtf8String();
            log('JNI', Color.MAGENTA, `GetMethodID("${name}", "${sig}")`);
          } catch (e) {}
        }
      });
    }

    // CallStaticMethod, CallMethod variants
    const CallStaticVoidMethod = Module.findExportByName('libart.so', 'CallStaticVoidMethodV');
    if (CallStaticVoidMethod) {
      Interceptor.attach(CallStaticVoidMethod, {
        onEnter(args) {
          log('JNI', Color.CYAN, `CallStaticVoidMethod`);
        }
      });
    }

    log('HOOK', Color.MAGENTA, 'JNI hooks installed');
  },

  // --- Memory Operations ---
  hookMemoryOps() {
    // malloc
    const malloc = Module.findExportByName(null, 'malloc');
    const calloc = Module.findExportByName(null, 'calloc');
    const realloc = Module.findExportByName(null, 'realloc');
    const free = Module.findExportByName(null, 'free');

    // Only log large allocations to avoid noise
    if (malloc) {
      Interceptor.attach(malloc, {
        onEnter(args) {
          const size = args[0].toInt32();
          this.size = size;
        },
        onLeave(retval) {
          if (this.size > 1024 * 100) { // >100KB
            log('MEM', Color.CYAN, `malloc(${this.size}) => ${retval}`);
          }
        }
      });
    }

    // mprotect
    const mprotect = Module.findExportByName(null, 'mprotect');
    if (mprotect) {
      Interceptor.attach(mprotect, {
        onEnter(args) {
          const addr = args[0];
          const len = args[1].toInt32();
          const prot = args[2].toInt32();
          
          const protStr = [];
          if (prot & 1) protStr.push('READ');
          if (prot & 2) protStr.push('WRITE');
          if (prot & 4) protStr.push('EXEC');
          
          log('MEM', Color.YELLOW, `mprotect(${addr}, ${len}, ${protStr.join('|')})`);
          
          // Detect unpacking / dynamic code loading
          if (prot & 4) {
            log('MEM', Color.RED, `  [EXEC] Possible unpacking!`);
          }
        }
      });
    }

    // mmap
    const mmap = Module.findExportByName(null, 'mmap');
    if (mmap) {
      Interceptor.attach(mmap, {
        onEnter(args) {
          const len = args[1].toInt32();
          const prot = args[2].toInt32();
          const flags = args[3].toInt32();
          
          const protStr = [];
          if (prot & 1) protStr.push('READ');
          if (prot & 2) protStr.push('WRITE');
          if (prot & 4) protStr.push('EXEC');
          
          if (flags & 0x20) { // MAP_ANONYMOUS
            log('MEM', Color.CYAN, `mmap(anon, ${len}, ${protStr.join('|')})`);
          }
        },
        onLeave(retval) {
          // log allocated region
        }
      });
    }

    log('HOOK', Color.MAGENTA, 'Memory operation hooks installed');
  },

  // --- String Operations (Common obfuscation target) ---
  hookStringOps() {
    const strcmp = Module.findExportByName(null, 'strcmp');
    const strncmp = Module.findExportByName(null, 'strncmp');
    const strstr = Module.findExportByName(null, 'strstr');

    if (strcmp) {
      Interceptor.attach(strcmp, {
        onEnter(args) {
          try {
            const s1 = args[0].readUtf8String();
            const s2 = args[1].readUtf8String();
            if (s1 && s2) {
              log('STR', Color.CYAN, `strcmp("${s1.substring(0, 80)}", "${s2.substring(0, 80)}")`);
            }
          } catch (e) {}
        }
      });
    }

    if (strncmp) {
      Interceptor.attach(strncmp, {
        onEnter(args) {
          try {
            const s1 = args[0].readUtf8String();
            const s2 = args[1].readUtf8String();
            const n = args[2].toInt32();
            if (s1 && s2) {
              log('STR', Color.CYAN, `strncmp("${s1.substring(0, 80)}", "${s2.substring(0, 80)}", ${n})`);
            }
          } catch (e) {}
        }
      });
    }

    if (strstr) {
      Interceptor.attach(strstr, {
        onEnter(args) {
          try {
            const haystack = args[0].readUtf8String();
            const needle = args[1].readUtf8String();
            if (haystack && needle) {
              log('STR', Color.CYAN, `strstr("${haystack}", "${needle}")`);
            }
          } catch (e) {}
        }
      });
    }

    log('HOOK', Color.MAGENTA, 'String operation hooks installed');
  },

  // --- File Operations (native level) ---
  hookFileOps() {
    const fopen = Module.findExportByName(null, 'fopen');
    const open = Module.findExportByName(null, 'open');
    const openat = Module.findExportByName(null, 'openat');
    const unlink = Module.findExportByName(null, 'unlink');

    if (fopen) {
      Interceptor.attach(fopen, {
        onEnter(args) {
          try {
            const path = args[0].readUtf8String();
            const mode = args[1].readUtf8String();
            log('FILE', Color.BLUE, `fopen("${path}", "${mode}")`);
          } catch (e) {}
        }
      });
    }

    if (open) {
      Interceptor.attach(open, {
        onEnter(args) {
          try {
            const path = args[0].readUtf8String();
            const flags = args[1].toInt32();
            const flagStr = [];
            if (flags & 0x0) flagStr.push('O_RDONLY');
            if (flags & 0x1) flagStr.push('O_WRONLY');
            if (flags & 0x2) flagStr.push('O_RDWR');
            if (flags & 0x200) flagStr.push('O_CREAT');
            if (flags & 0x400) flagStr.push('O_TRUNC');
            if (flags & 0x8) flagStr.push('O_APPEND');
            log('FILE', Color.BLUE, `open("${path}", ${flagStr.join('|')})`);
          } catch (e) {}
        }
      });
    }

    if (unlink) {
      Interceptor.attach(unlink, {
        onEnter(args) {
          try {
            const path = args[0].readUtf8String();
            log('FILE', Color.RED, `unlink("${path}")`);
          } catch (e) {}
        }
      });
    }

    log('HOOK', Color.MAGENTA, 'Native file operation hooks installed');
  },

  // --- Crypto (Native) ---
  hookNativeCrypto() {
    // AES
    const AES_set_encrypt_key = Module.findExportByName(null, 'AES_set_encrypt_key');
    if (AES_set_encrypt_key) {
      Interceptor.attach(AES_set_encrypt_key, {
        onEnter(args) {
          const keyLen = args[1].toInt32();
          log('NCRYPTO', Color.CYAN, `AES_set_encrypt_key bits=${keyLen}`);
          log('NCRYPTO', Color.CYAN, `  Key:\n${dumpHex(args[0].readByteArray(keyLen / 8))}`);
        }
      });
    }

    const AES_cbc_encrypt = Module.findExportByName(null, 'AES_cbc_encrypt');
    if (AES_cbc_encrypt) {
      Interceptor.attach(AES_cbc_encrypt, {
        onEnter(args) {
          const len = args[4].toInt32();
          const enc = args[5].toInt32(); // 1 = encrypt, 0 = decrypt
          log('NCRYPTO', Color.CYAN, `AES_cbc_encrypt len=${len} ${enc ? 'ENCRYPT' : 'DECRYPT'}`);
          log('NCRYPTO', Color.CYAN, `  IV:\n${dumpHex(args[3].readByteArray(16))}`);
          if (len > 0 && len < 1024) {
            log('NCRYPTO', Color.CYAN, `  In:\n${dumpHex(args[0].readByteArray(Math.min(len, 128)))}`);
          }
        },
        onLeave(retval) {
          // Out: retval (or in args) - same buffer
        }
      });
    }

    // OpenSSL EVP
    const EVP_EncryptInit_ex = Module.findExportByName(null, 'EVP_EncryptInit_ex');
    if (EVP_EncryptInit_ex) {
      Interceptor.attach(EVP_EncryptInit_ex, {
        onEnter(args) {
          const cipher = args[1];
          if (cipher && !cipher.isNull()) {
            try {
              const name = cipher.add(8).readPointer().readUtf8String(); // nid/cipher name
              log('NCRYPTO', Color.CYAN, `EVP_EncryptInit_ex cipher=${name}`);
            } catch (e) {}
          }
        }
      });
    }

    // Android KeyMaster
    const keymaster = Module.findExportByName(null, 'keymaster_open');
    if (!keymaster) {
      // Try libkeymaster_messages
      try {
        const km = Process.findModuleByName('libkeymaster_messages.so');
        if (km) log('NCRYPTO', Color.MAGENTA, 'KeyMaster library found');
      } catch (e) {}
    }

    // BoringSSL
    const HMAC_Init_ex = Module.findExportByName(null, 'HMAC_Init_ex');
    if (HMAC_Init_ex) {
      Interceptor.attach(HMAC_Init_ex, {
        onEnter(args) {
          log('NCRYPTO', Color.CYAN, 'HMAC_Init_ex');
        }
      });
    }

    log('HOOK', Color.MAGENTA, 'Native crypto hooks installed');
  },
};

// --- Frida Detection Bypass ---
function bypassFridaDetection() {
  // Common Frida detection bypass
  // fopen(/proc/self/maps) detection
  const strstr_ptr = Module.findExportByName(null, 'strstr');
  if (strstr_ptr) {
    Interceptor.attach(strstr_ptr, {
      onEnter(args) {
        try {
          const haystack = args[0].readUtf8String();
          const needle = args[1].readUtf8String();
          if (needle === 'frida' || needle === 'gum-js' || needle === 'linjector') {
            log('DETECT', Color.RED, `Frida detection via strstr("${needle}") - BLOCKED`);
            args[1] = Memory.allocUtf8String('FAKE_DETECT_BYPASS');
          }
        } catch (e) {}
      }
    });
  }

  // pthread_create hook to detect anti-debug threads
  const pthread_create = Module.findExportByName(null, 'pthread_create');
  if (pthread_create) {
    Interceptor.attach(pthread_create, {
      onEnter(args) {
        log('THREAD', Color.MAGENTA, 'pthread_create called');
      }
    });
  }

  // dladdr / dlsym scans
  if (Java.available) {
    Java.perform(function() {
      try {
        const Debug = Java.use('android.os.Debug');
        Debug.isDebuggerConnected.implementation = function() {
          log('DETECT', Color.RED, 'Debug.isDebuggerConnected() - returning false');
          return false;
        };
      } catch (e) {}
    });
  }
}

// --- Module Scanner ---
function scanLoadedModules() {
  log('SCAN', Color.MAGENTA, 'Loaded modules:');
  Process.enumerateModules().forEach(function(mod) {
    log('SCAN', Color.CYAN, `  ${mod.name} @ ${mod.base} (${mod.size}) - ${mod.path}`);
  });
}

// --- Hook by pattern ---
function hookByPattern(pattern, numArgs) {
  numArgs = numArgs || 4;
  const matches = new ModuleMap(m => {
    const ranges = Process.enumerateRanges('r-x').filter(r => {
      try {
        const bytes = r.base.readByteArray(r.size);
        return true;
      } catch (e) { return false; }
    });
    return [];
  });
  
  Memory.scan(Process.pageSize ? ptr(0) : Module.findBaseAddress('libc.so'), 
    Process.pageSize * 100000, pattern, {
    onMatch(address, size) {
      log('PATTERN', Color.YELLOW, `Pattern match at ${address}`);
      Interceptor.attach(address, {
        onEnter(args) {
          log('PATTERN', Color.CYAN, `Hooked function called`);
          for (let i = 0; i < numArgs; i++) {
            try {
              log('PATTERN', Color.CYAN, `  arg[${i}]: ${args[i]}`);
            } catch (e) {}
          }
        }
      });
    },
    onComplete() {
      log('PATTERN', Color.GREEN, 'Pattern scan complete');
    }
  });
}

function installAll() {
  NATIVE_HOOKS.bypassAntiDebug();
  NATIVE_HOOKS.bypassIntegrity();
  NATIVE_HOOKS.hookJNI();
  NATIVE_HOOKS.hookMemoryOps();
  NATIVE_HOOKS.hookStringOps();
  NATIVE_HOOKS.hookFileOps();
  NATIVE_HOOKS.hookNativeCrypto();
  
  setTimeout(() => scanLoadedModules(), 1000);
  
  log('TOOLKIT', Color.GREEN, 'All Native hooks installed!');
}

rpc.exports = {
  installAll,
  NATIVE_HOOKS,
  scanLoadedModules,
  bypassFridaDetection,
  hookByPattern,
};

console.log(`\n${'='.repeat(60)}`);
console.log(`  FRIDA TOOLKIT - NATIVE MODULE LOADED`);
console.log(`  rpc.exports.installAll()       - Install all`);
console.log(`  rpc.exports.scanLoadedModules() - Dump modules`);
console.log(`${'='.repeat(60)}\n`);
