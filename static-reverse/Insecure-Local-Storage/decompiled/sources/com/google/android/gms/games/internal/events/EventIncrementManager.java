package com.google.android.gms.games.internal.events;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public abstract class EventIncrementManager {
    private final AtomicReference<EventIncrementCache> zzasM = new AtomicReference<>();

    public void flush() {
        EventIncrementCache eventIncrementCache = this.zzasM.get();
        if (eventIncrementCache != null) {
            eventIncrementCache.flush();
        }
    }

    public void zzp(String str, int i) {
        EventIncrementCache eventIncrementCacheZzsS = this.zzasM.get();
        if (eventIncrementCacheZzsS == null) {
            eventIncrementCacheZzsS = zzsS();
            if (!this.zzasM.compareAndSet(null, eventIncrementCacheZzsS)) {
                eventIncrementCacheZzsS = this.zzasM.get();
            }
        }
        eventIncrementCacheZzsS.zzw(str, i);
    }

    protected abstract EventIncrementCache zzsS();
}
