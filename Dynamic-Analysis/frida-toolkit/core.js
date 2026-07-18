/*
 * FRIDA DYNAMIC INSTRUMENTATION TOOLKIT - CORE
 * Target: macOS host -> Android/iOS Emulator & Physical Device
 * Usage: frida -U -l core.js -f <package> --no-pause
 */

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

function log(tag, color, msg) {
  console.log(`[${Color.BOLD}${color}${tag}${Color.RESET}] ${msg}`);
}

function dumpHex(buffer, len) {
  len = len || 256;
  if (buffer instanceof ArrayBuffer) {
    return dumpHex(new Uint8Array(buffer), len);
  }
  if (typeof buffer === 'string') {
    return dumpHex(new Uint8Array(
      new ArrayBuffer(buffer.length)).map((_, i) => buffer.charCodeAt(i)), len);
  }
  const bytes = Array.from(buffer.slice(0, len));
  let result = '';
  for (let i = 0; i < bytes.length; i += 16) {
    const chunk = bytes.slice(i, i + 16);
    const hex = chunk.map(b => b.toString(16).padStart(2, '0')).join(' ');
    const ascii = chunk.map(b => (b >= 32 && b < 127) ? String.fromCharCode(b) : '.').join('');
    result += `  ${i.toString(16).padStart(8, '0')}: ${hex.padEnd(48)} ${ascii}\n`;
  }
  return result;
}

function dumpObj(obj, depth) {
  depth = depth || 3;
  if (depth <= 0) return '...';
  if (obj === null) return 'null';
  if (obj === undefined) return 'undefined';
  if (typeof obj === 'string') return `"${obj}"`;
  if (typeof obj === 'number' || typeof obj === 'boolean') return String(obj);
  if (Array.isArray(obj)) {
    return '[' + obj.slice(0, 20).map(v => dumpObj(v, depth - 1)).join(', ') + 
           (obj.length > 20 ? ', ...' : '') + ']';
  }
  try {
    let keys = [];
    for (const k of Object.keys(obj)) keys.push(k);
    return '{' + keys.slice(0, 10).map(k => `${k}: ${dumpObj(obj[k], depth - 1)}`).join(', ') +
           (keys.length > 10 ? ', ...' : '') + '}';
  } catch (e) {
    return String(obj);
  }
}

function getCaller() {
  try {
    return Thread.backtrace(this.context, Backtracer.ACCURATE)
      .map(DebugSymbol.fromAddress).slice(2, 5).join('\n         ');
  } catch (e) {
    return 'N/A';
  }
}

module.exports = {
  log, Color, dumpHex, dumpObj, getCaller
};
