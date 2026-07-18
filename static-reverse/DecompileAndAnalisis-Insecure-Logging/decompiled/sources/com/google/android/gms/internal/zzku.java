package com.google.android.gms.internal;

import java.util.LinkedHashMap;

/* loaded from: classes.dex */
public class zzku<K, V> {
    private int size;
    private final LinkedHashMap<K, V> zzabn;
    private int zzabo;
    private int zzabp;
    private int zzabq;
    private int zzabr;
    private int zzabs;
    private int zzabt;

    public zzku(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.zzabo = i;
        this.zzabn = new LinkedHashMap<>(0, 0.75f, true);
    }

    private int zzc(K k, V v) {
        int iSizeOf = sizeOf(k, v);
        if (iSizeOf < 0) {
            throw new IllegalStateException("Negative size: " + k + "=" + v);
        }
        return iSizeOf;
    }

    protected V create(K key) {
        return null;
    }

    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final V get(K k) {
        V v;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v2 = this.zzabn.get(k);
            if (v2 != null) {
                this.zzabs++;
                return v2;
            }
            this.zzabt++;
            V vCreate = create(k);
            if (vCreate == null) {
                return null;
            }
            synchronized (this) {
                this.zzabq++;
                v = (V) this.zzabn.put(k, vCreate);
                if (v != null) {
                    this.zzabn.put(k, v);
                } else {
                    this.size += zzc(k, vCreate);
                }
            }
            if (v != null) {
                entryRemoved(false, k, vCreate, v);
                return v;
            }
            trimToSize(this.zzabo);
            return vCreate;
        }
    }

    public final V put(K key, V value) {
        V vPut;
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.zzabp++;
            this.size += zzc(key, value);
            vPut = this.zzabn.put(key, value);
            if (vPut != null) {
                this.size -= zzc(key, vPut);
            }
        }
        if (vPut != null) {
            entryRemoved(false, key, vPut, value);
        }
        trimToSize(this.zzabo);
        return vPut;
    }

    public final synchronized int size() {
        return this.size;
    }

    protected int sizeOf(K key, V value) {
        return 1;
    }

    public final synchronized String toString() {
        String str;
        synchronized (this) {
            int i = this.zzabs + this.zzabt;
            str = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.zzabo), Integer.valueOf(this.zzabs), Integer.valueOf(this.zzabt), Integer.valueOf(i != 0 ? (this.zzabs * 100) / i : 0));
        }
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0031, code lost:
    
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void trimToSize(int r5) {
        /*
            r4 = this;
        L0:
            monitor-enter(r4)
            int r0 = r4.size     // Catch: java.lang.Throwable -> L32
            if (r0 < 0) goto L11
            java.util.LinkedHashMap<K, V> r0 = r4.zzabn     // Catch: java.lang.Throwable -> L32
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L35
            int r0 = r4.size     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L35
        L11:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L32
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L32
            r1.<init>()     // Catch: java.lang.Throwable -> L32
            java.lang.Class r2 = r4.getClass()     // Catch: java.lang.Throwable -> L32
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Throwable -> L32
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L32
            java.lang.String r2 = ".sizeOf() is reporting inconsistent results!"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L32
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L32
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L32
            throw r0     // Catch: java.lang.Throwable -> L32
        L32:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L32
            throw r0
        L35:
            int r0 = r4.size     // Catch: java.lang.Throwable -> L32
            if (r0 <= r5) goto L41
            java.util.LinkedHashMap<K, V> r0 = r4.zzabn     // Catch: java.lang.Throwable -> L32
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L43
        L41:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L32
            return
        L43:
            java.util.LinkedHashMap<K, V> r0 = r4.zzabn     // Catch: java.lang.Throwable -> L32
            java.util.Set r0 = r0.entrySet()     // Catch: java.lang.Throwable -> L32
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L32
            java.lang.Object r0 = r0.next()     // Catch: java.lang.Throwable -> L32
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch: java.lang.Throwable -> L32
            java.lang.Object r1 = r0.getKey()     // Catch: java.lang.Throwable -> L32
            java.lang.Object r0 = r0.getValue()     // Catch: java.lang.Throwable -> L32
            java.util.LinkedHashMap<K, V> r2 = r4.zzabn     // Catch: java.lang.Throwable -> L32
            r2.remove(r1)     // Catch: java.lang.Throwable -> L32
            int r2 = r4.size     // Catch: java.lang.Throwable -> L32
            int r3 = r4.zzc(r1, r0)     // Catch: java.lang.Throwable -> L32
            int r2 = r2 - r3
            r4.size = r2     // Catch: java.lang.Throwable -> L32
            int r2 = r4.zzabr     // Catch: java.lang.Throwable -> L32
            int r2 = r2 + 1
            r4.zzabr = r2     // Catch: java.lang.Throwable -> L32
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L32
            r2 = 1
            r3 = 0
            r4.entryRemoved(r2, r1, r0, r3)
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzku.trimToSize(int):void");
    }
}
