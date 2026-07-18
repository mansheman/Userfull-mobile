/*
 * FRIDA TOOLKIT - FILESYSTEM MODULE
 * File read/write monitor, app sandbox explorer
 * Works on Android + iOS
 */

const { log, Color, dumpHex } = require('./core.js');

const FS_HOOKS = {

  // --- Android File I/O ---
  hookAndroidFileIO() {
    if (!Java.available) return;

    Java.perform(function() {
      const FileInputStream = Java.use('java.io.FileInputStream');
      const FileOutputStream = Java.use('java.io.FileOutputStream');
      const File = Java.use('java.io.File');
      const RandomAccessFile = Java.use('java.io.RandomAccessFile');

      // Read
      FileInputStream.$init.overload('java.lang.String').implementation = function(path) {
        log('FILE', Color.BLUE, `FileInputStream("${path}")`);
        return this.$init(path);
      };

      FileInputStream.$init.overload('java.io.File').implementation = function(file) {
        log('FILE', Color.BLUE, `FileInputStream(${file.getAbsolutePath()})`);
        return this.$init(file);
      };

      // Write
      FileOutputStream.$init.overload('java.lang.String').implementation = function(path) {
        log('FILE', Color.YELLOW, `FileOutputStream("${path}")`);
        return this.$init(path);
      };

      FileOutputStream.$init.overload('java.io.File').implementation = function(file) {
        log('FILE', Color.YELLOW, `FileOutputStream(${file.getAbsolutePath()})`);
        return this.$init(file);
      };

      FileOutputStream.$init.overload('java.lang.String', 'boolean').implementation = function(path, append) {
        log('FILE', Color.YELLOW, `FileOutputStream("${path}", append=${append})`);
        return this.$init(path, append);
      };

      // RandomAccessFile
      RandomAccessFile.$init.overload('java.lang.String', 'java.lang.String').implementation = function(file, mode) {
        log('FILE', Color.YELLOW, `RandomAccessFile("${file}", "${mode}")`);
        return this.$init(file, mode);
      };

      RandomAccessFile.read.overload('[B', 'int', 'int').implementation = function(b, off, len) {
        const result = this.read(b, off, len);
        if (result > 0) {
          log('FILE', Color.BLUE, `RandomAccessFile.read(${len}B) => ${result}`);
        }
        return result;
      };

      // File operations
      File.$init.overload('java.lang.String').implementation = function(path) {
        // Only log interesting paths
        if (path && (path.includes('.db') || path.includes('.xml') || path.includes('.so') || 
                     path.includes('.dex') || path.includes('.jar') || path.includes('key') ||
                     path.includes('token') || path.includes('config'))) {
          log('FILE', Color.CYAN, `File("${path}")`);
        }
        return this.$init(path);
      };

      File.delete.implementation = function() {
        log('FILE', Color.RED, `File.delete: ${this.getAbsolutePath()}`);
        return this.delete();
      };

      File.renameTo.implementation = function(dest) {
        log('FILE', Color.YELLOW, `File.renameTo: ${this.getAbsolutePath()} -> ${dest.getAbsolutePath()}`);
        return this.renameTo(dest);
      };

      File.mkdirs.implementation = function() {
        log('FILE', Color.YELLOW, `File.mkdirs: ${this.getAbsolutePath()}`);
        return this.mkdirs();
      };
    });

    log('HOOK', Color.MAGENTA, 'Android File I/O hooks installed');
  },

  // --- Android SharedPreferences File ---
  hookPrefsFile() {
    if (!Java.available) return;

    Java.perform(function() {
      const ContextImpl = Java.use('android.app.ContextImpl');
      
      ContextImpl.getSharedPreferences.overload('java.io.File', 'int').implementation = function(file, mode) {
        log('PREFS', Color.CYAN, `getSharedPreferences file=${file.getAbsolutePath()} mode=${mode}`);
        return this.getSharedPreferences(file, mode);
      };

      ContextImpl.getSharedPreferences.overload('java.lang.String', 'int').implementation = function(name, mode) {
        log('PREFS', Color.CYAN, `getSharedPreferences("${name}", ${mode})`);
        return this.getSharedPreferences(name, mode);
      };
    });
    log('HOOK', Color.MAGENTA, 'SharedPreferences file hooks installed');
  },

  // --- iOS File Operations ---
  hookiOSFileIO() {
    if (Java.available) return;

    const NSData = ObjC.classes.NSData;
    const NSString = ObjC.classes.NSString;

    // NSData writeToFile
    try {
      Interceptor.attach(NSData['- writeToFile:atomically:'].implementation, {
        onEnter(args) {
          const path = new ObjC.Object(args[2]).toString();
          const len = this.length();
          log('FILE', Color.YELLOW, `writeToFile "${path}" (${len}B)`);
        }
      });

      Interceptor.attach(NSData['- writeToFile:options:error:'].implementation, {
        onEnter(args) {
          const path = new ObjC.Object(args[2]).toString();
          log('FILE', Color.YELLOW, `writeToFile "${path}"`);
        }
      });
    } catch (e) {}

    // NSData initWithContentsOfFile
    try {
      Interceptor.attach(NSData['- initWithContentsOfFile:'].implementation, {
        onEnter(args) {
          const path = new ObjC.Object(args[2]).toString();
          log('FILE', Color.BLUE, `initWithContentsOfFile "${path}"`);
        }
      });

      Interceptor.attach(NSData['- initWithContentsOfURL:'].implementation, {
        onEnter(args) {
          const url = new ObjC.Object(args[2]);
          log('FILE', Color.BLUE, `initWithContentsOfURL "${url}"`);
        }
      });
    } catch (e) {}

    // NSString writeToFile
    try {
      Interceptor.attach(NSString['- writeToFile:atomically:encoding:error:'].implementation, {
        onEnter(args) {
          const path = new ObjC.Object(args[2]).toString();
          log('FILE', Color.YELLOW, `NSString.writeToFile "${path}"`);
        }
      });

      Interceptor.attach(NSString['- initWithContentsOfFile:encoding:error:'].implementation, {
        onEnter(args) {
          const path = new ObjC.Object(args[2]).toString();
          log('FILE', Color.BLUE, `NSString.initWithContentsOfFile "${path}"`);
        }
      });
    } catch (e) {}

    // NSFileManager
    try {
      const NSFileManager = ObjC.classes.NSFileManager;
      
      Interceptor.attach(NSFileManager['- fileExistsAtPath:'].implementation, {
        onEnter(args) {
          const path = new ObjC.Object(args[2]).toString();
          log('FILE', Color.CYAN, `fileExistsAtPath "${path}"`);
        }
      });

      Interceptor.attach(NSFileManager['- isReadableFileAtPath:'].implementation, {
        onEnter(args) {
          const path = new ObjC.Object(args[2]).toString();
          log('FILE', Color.CYAN, `isReadableFileAtPath "${path}"`);
        }
      });
    } catch (e) {}

    log('HOOK', Color.MAGENTA, 'iOS File I/O hooks installed');
  },

  // --- Android Database Explorer ---
  exploreDatabases() {
    if (!Java.available) return;

    Java.perform(function() {
      const Context = Java.use('android.app.ActivityThread').currentApplication().getApplicationContext();
      
      // List all databases
      try {
        const dbList = Context.databaseList();
        log('DB', Color.MAGENTA, `Database files:`);
        for (let i = 0; i < dbList.length; i++) {
          const dbPath = Context.getDatabasePath(dbList[i]);
          log('DB', Color.CYAN, `  ${dbList[i]} -> ${dbPath.getAbsolutePath()}`);
          
          // Try to open and list tables
          try {
            const db = Java.use('android.database.sqlite.SQLiteDatabase').openDatabase(dbPath.getAbsolutePath(), null, 0);
            const cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            while (cursor.moveToNext()) {
              log('DB', Color.CYAN, `    Table: ${cursor.getString(0)}`);
            }
            cursor.close();
            db.close();
          } catch (e) {}
        }
      } catch (e) {}

      // List files in app directory
      try {
        const filesDir = Context.getFilesDir();
        log('FS', Color.MAGENTA, `Files Dir: ${filesDir.getAbsolutePath()}`);
        listDirRecursive(filesDir, 1);
      } catch (e) {}

      try {
        const cacheDir = Context.getCacheDir();
        log('FS', Color.MAGENTA, `Cache Dir: ${cacheDir.getAbsolutePath()}`);
      } catch (e) {}

      try {
        const extDir = Context.getExternalFilesDir(null);
        if (extDir) {
          log('FS', Color.MAGENTA, `External Files: ${extDir.getAbsolutePath()}`);
        }
      } catch (e) {}
    });

    log('HOOK', Color.MAGENTA, 'Database exploration done');
  },

  // --- iOS Sandbox Explorer ---
  exploreiOSSandbox() {
    if (Java.available) return;

    const paths = [
      ObjC.classes.NSBundle.mainBundle().bundlePath().toString(),
      ObjC.classes.NSSearchPathForDirectoriesInDomains(9, 1, true) // NSLibraryDirectory
        .objectAtIndex_(0).toString(),
      ObjC.classes.NSSearchPathForDirectoriesInDomains(13, 1, true) // NSDocumentDirectory
        .objectAtIndex_(0).toString(),
      ObjC.classes.NSTemporaryDirectory().toString(),
    ];

    log('FS', Color.MAGENTA, 'iOS Sandbox:');
    for (const p of paths) {
      log('FS', Color.CYAN, `  ${p}`);
      try {
        const fm = ObjC.classes.NSFileManager.defaultManager();
        const contents = fm.contentsOfDirectoryAtPath_error_(p, NULL);
        if (contents) {
          const count = contents.count();
          for (let i = 0; i < Math.min(count, 30); i++) {
            log('FS', Color.CYAN, `    ${contents.objectAtIndex_(i)}`);
          }
          if (count > 30) log('FS', Color.CYAN, `    ... +${count - 30} more`);
        }
      } catch (e) {}
    }
  },
};

function listDirRecursive(dir, depth) {
  if (depth <= 0) return;
  try {
    const files = dir.listFiles();
    if (files) {
      for (let i = 0; i < files.length; i++) {
        const f = files[i];
        const prefix = '  '.repeat(3 - depth);
        if (f.isDirectory()) {
          log('FS', Color.CYAN, `${prefix}[DIR] ${f.getName()}`);
          if (depth > 1) listDirRecursive(f, depth - 1);
        } else {
          const size = f.length();
          log('FS', Color.CYAN, `${prefix}${f.getName()} (${size}B)`);
        }
      }
    }
  } catch (e) {}
}

function installAll() {
  FS_HOOKS.hookAndroidFileIO();
  FS_HOOKS.hookPrefsFile();
  FS_HOOKS.hookiOSFileIO();
  
  setTimeout(() => {
    FS_HOOKS.exploreDatabases();
    FS_HOOKS.exploreiOSSandbox();
  }, 1500);
  
  log('TOOLKIT', Color.GREEN, 'All Filesystem hooks installed!');
}

rpc.exports = {
  installAll,
  FS_HOOKS,
  exploreDatabases: FS_HOOKS.exploreDatabases,
  exploreiOSSandbox: FS_HOOKS.exploreiOSSandbox,
};

console.log(`\n${'='.repeat(60)}`);
console.log(`  FRIDA TOOLKIT - FILESYSTEM MODULE LOADED`);
console.log(`  rpc.exports.installAll()         - Install all`);
console.log(`  rpc.exports.exploreDatabases()   - Dump Android DBs`);
console.log(`  rpc.exports.exploreiOSSandbox()  - Dump iOS sandbox`);
console.log(`${'='.repeat(60)}\n`);
