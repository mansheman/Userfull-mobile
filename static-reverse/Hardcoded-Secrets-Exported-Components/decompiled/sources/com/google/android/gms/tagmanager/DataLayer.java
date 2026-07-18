package com.google.android.gms.tagmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    static final String[] zzaLf = "gtm.lifetime".toString().split("\\.");
    private static final Pattern zzaLg = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<zzb, Integer> zzaLh;
    private final Map<String, Object> zzaLi;
    private final ReentrantLock zzaLj;
    private final LinkedList<Map<String, Object>> zzaLk;
    private final zzc zzaLl;
    private final CountDownLatch zzaLm;

    static final class zza {
        public final Object zzGK;
        public final String zztw;

        zza(String str, Object obj) {
            this.zztw = str;
            this.zzGK = obj;
        }

        public boolean equals(Object o) {
            if (!(o instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) o;
            return this.zztw.equals(zzaVar.zztw) && this.zzGK.equals(zzaVar.zzGK);
        }

        public int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.zztw.hashCode()), Integer.valueOf(this.zzGK.hashCode())});
        }

        public String toString() {
            return "Key: " + this.zztw + " value: " + this.zzGK.toString();
        }
    }

    interface zzb {
        void zzF(Map<String, Object> map);
    }

    interface zzc {

        public interface zza {
            void zzo(List<zza> list);
        }

        void zza(zza zzaVar);

        void zza(List<zza> list, long j);

        void zzep(String str);
    }

    DataLayer() {
        this(new zzc() { // from class: com.google.android.gms.tagmanager.DataLayer.1
            @Override // com.google.android.gms.tagmanager.DataLayer.zzc
            public void zza(zzc.zza zzaVar) {
                zzaVar.zzo(new ArrayList());
            }

            @Override // com.google.android.gms.tagmanager.DataLayer.zzc
            public void zza(List<zza> list, long j) {
            }

            @Override // com.google.android.gms.tagmanager.DataLayer.zzc
            public void zzep(String str) {
            }
        });
    }

    DataLayer(zzc persistentStore) {
        this.zzaLl = persistentStore;
        this.zzaLh = new ConcurrentHashMap<>();
        this.zzaLi = new HashMap();
        this.zzaLj = new ReentrantLock();
        this.zzaLk = new LinkedList<>();
        this.zzaLm = new CountDownLatch(1);
        zzyy();
    }

    public static List<Object> listOf(Object... objects) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objects) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    public static Map<String, Object> mapOf(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        HashMap map = new HashMap();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= objects.length) {
                return map;
            }
            if (!(objects[i2] instanceof String)) {
                throw new IllegalArgumentException("key is not a string: " + objects[i2]);
            }
            map.put((String) objects[i2], objects[i2 + 1]);
            i = i2 + 2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzH(Map<String, Object> map) {
        this.zzaLj.lock();
        try {
            this.zzaLk.offer(map);
            if (this.zzaLj.getHoldCount() == 1) {
                zzyz();
            }
            zzI(map);
        } finally {
            this.zzaLj.unlock();
        }
    }

    private void zzI(Map<String, Object> map) {
        Long lZzJ = zzJ(map);
        if (lZzJ == null) {
            return;
        }
        List<zza> listZzL = zzL(map);
        listZzL.remove("gtm.lifetime");
        this.zzaLl.zza(listZzL, lZzJ.longValue());
    }

    private Long zzJ(Map<String, Object> map) {
        Object objZzK = zzK(map);
        if (objZzK == null) {
            return null;
        }
        return zzeo(objZzK.toString());
    }

    private Object zzK(Map<String, Object> map) {
        String[] strArr = zzaLf;
        int length = strArr.length;
        int i = 0;
        Map<String, Object> map2 = map;
        while (i < length) {
            String str = strArr[i];
            if (!(map2 instanceof Map)) {
                return null;
            }
            i++;
            map2 = map2.get(str);
        }
        return map2;
    }

    private List<zza> zzL(Map<String, Object> map) {
        ArrayList arrayList = new ArrayList();
        zza(map, "", arrayList);
        return arrayList;
    }

    private void zzM(Map<String, Object> map) {
        synchronized (this.zzaLi) {
            for (String str : map.keySet()) {
                zzc(zzj(str, map.get(str)), this.zzaLi);
            }
        }
        zzN(map);
    }

    private void zzN(Map<String, Object> map) {
        Iterator<zzb> it = this.zzaLh.keySet().iterator();
        while (it.hasNext()) {
            it.next().zzF(map);
        }
    }

    private void zza(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String str2 = str + (str.length() == 0 ? "" : ".") + entry.getKey();
            if (entry.getValue() instanceof Map) {
                zza((Map) entry.getValue(), str2, collection);
            } else if (!str2.equals("gtm.lifetime")) {
                collection.add(new zza(str2, entry.getValue()));
            }
        }
    }

    static Long zzeo(String str) throws NumberFormatException {
        long j;
        Matcher matcher = zzaLg.matcher(str);
        if (!matcher.matches()) {
            zzbg.zzaA("unknown _lifetime: " + str);
            return null;
        }
        try {
            j = Long.parseLong(matcher.group(1));
        } catch (NumberFormatException e) {
            zzbg.zzaC("illegal number in _lifetime value: " + str);
            j = 0;
        }
        if (j <= 0) {
            zzbg.zzaA("non-positive _lifetime: " + str);
            return null;
        }
        String strGroup = matcher.group(2);
        if (strGroup.length() == 0) {
            return Long.valueOf(j);
        }
        switch (strGroup.charAt(0)) {
            case 'd':
                break;
            case 'h':
                break;
            case 'm':
                break;
            case 's':
                break;
            default:
                zzbg.zzaC("unknown units in _lifetime: " + str);
                break;
        }
        return null;
    }

    private void zzyy() {
        this.zzaLl.zza(new zzc.zza() { // from class: com.google.android.gms.tagmanager.DataLayer.2
            @Override // com.google.android.gms.tagmanager.DataLayer.zzc.zza
            public void zzo(List<zza> list) {
                for (zza zzaVar : list) {
                    DataLayer.this.zzH(DataLayer.this.zzj(zzaVar.zztw, zzaVar.zzGK));
                }
                DataLayer.this.zzaLm.countDown();
            }
        });
    }

    private void zzyz() {
        int i = 0;
        do {
            int i2 = i;
            Map<String, Object> mapPoll = this.zzaLk.poll();
            if (mapPoll == null) {
                return;
            }
            zzM(mapPoll);
            i = i2 + 1;
        } while (i <= 500);
        this.zzaLk.clear();
        throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
    }

    public Object get(String key) {
        synchronized (this.zzaLi) {
            Map<String, Object> map = this.zzaLi;
            String[] strArrSplit = key.split("\\.");
            int length = strArrSplit.length;
            Map<String, Object> map2 = map;
            int i = 0;
            while (i < length) {
                String str = strArrSplit[i];
                if (!(map2 instanceof Map)) {
                    return null;
                }
                Object obj = map2.get(str);
                if (obj == null) {
                    return null;
                }
                i++;
                map2 = obj;
            }
            return map2;
        }
    }

    public void push(String key, Object value) throws InterruptedException {
        push(zzj(key, value));
    }

    public void push(Map<String, Object> update) throws InterruptedException {
        try {
            this.zzaLm.await();
        } catch (InterruptedException e) {
            zzbg.zzaC("DataLayer.push: unexpected InterruptedException");
        }
        zzH(update);
    }

    public void pushEvent(String eventName, Map<String, Object> update) throws InterruptedException {
        HashMap map = new HashMap(update);
        map.put("event", eventName);
        push(map);
    }

    public String toString() {
        String string;
        synchronized (this.zzaLi) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : this.zzaLi.entrySet()) {
                sb.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", entry.getKey(), entry.getValue()));
            }
            string = sb.toString();
        }
        return string;
    }

    void zza(zzb zzbVar) {
        this.zzaLh.put(zzbVar, 0);
    }

    void zzb(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                return;
            }
            Object obj = list.get(i2);
            if (obj instanceof List) {
                if (!(list2.get(i2) instanceof List)) {
                    list2.set(i2, new ArrayList());
                }
                zzb((List) obj, (List) list2.get(i2));
            } else if (obj instanceof Map) {
                if (!(list2.get(i2) instanceof Map)) {
                    list2.set(i2, new HashMap());
                }
                zzc((Map) obj, (Map) list2.get(i2));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i2, obj);
            }
            i = i2 + 1;
        }
    }

    void zzc(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                zzb((List) obj, (List) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                zzc((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    void zzen(String str) throws InterruptedException {
        push(str, null);
        this.zzaLl.zzep(str);
    }

    Map<String, Object> zzj(String str, Object obj) {
        HashMap map = new HashMap();
        String[] strArrSplit = str.toString().split("\\.");
        int i = 0;
        HashMap map2 = map;
        while (i < strArrSplit.length - 1) {
            HashMap map3 = new HashMap();
            map2.put(strArrSplit[i], map3);
            i++;
            map2 = map3;
        }
        map2.put(strArrSplit[strArrSplit.length - 1], obj);
        return map;
    }
}
