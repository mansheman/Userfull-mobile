package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.tagmanager.zzcx;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.apache.http.impl.client.DefaultHttpClient;

/* loaded from: classes.dex */
class zzby implements zzau {
    private static final String zzKg = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", "gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time");
    private final Context mContext;
    private final zzb zzaMs;
    private volatile zzac zzaMt;
    private final zzav zzaMu;
    private final String zzaMv;
    private long zzaMw;
    private final int zzaMx;
    private zzlb zzpw;

    class zza implements zzcx.zza {
        zza() {
        }

        @Override // com.google.android.gms.tagmanager.zzcx.zza
        public void zza(zzaq zzaqVar) {
            zzby.this.zzq(zzaqVar.zzyO());
        }

        @Override // com.google.android.gms.tagmanager.zzcx.zza
        public void zzb(zzaq zzaqVar) {
            zzby.this.zzq(zzaqVar.zzyO());
            zzbg.zzaB("Permanent failure dispatching hitId: " + zzaqVar.zzyO());
        }

        @Override // com.google.android.gms.tagmanager.zzcx.zza
        public void zzc(zzaq zzaqVar) {
            long jZzyP = zzaqVar.zzyP();
            if (jZzyP == 0) {
                zzby.this.zzd(zzaqVar.zzyO(), zzby.this.zzpw.currentTimeMillis());
            } else if (jZzyP + 14400000 < zzby.this.zzpw.currentTimeMillis()) {
                zzby.this.zzq(zzaqVar.zzyO());
                zzbg.zzaB("Giving up on failed hitId: " + zzaqVar.zzyO());
            }
        }
    }

    class zzb extends SQLiteOpenHelper {
        private long zzaMA;
        private boolean zzaMz;

        zzb(Context context, String str) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
            this.zzaMA = 0L;
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0048  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private boolean zza(java.lang.String r11, android.database.sqlite.SQLiteDatabase r12) throws java.lang.Throwable {
            /*
                r10 = this;
                r8 = 0
                r9 = 0
                java.lang.String r1 = "SQLITE_MASTER"
                r0 = 1
                java.lang.String[] r2 = new java.lang.String[r0]     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L45
                r0 = 0
                java.lang.String r3 = "name"
                r2[r0] = r3     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L45
                java.lang.String r3 = "name=?"
                r0 = 1
                java.lang.String[] r4 = new java.lang.String[r0]     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L45
                r0 = 0
                r4[r0] = r11     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L45
                r5 = 0
                r6 = 0
                r7 = 0
                r0 = r12
                android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L45
                boolean r0 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteException -> L53
                if (r1 == 0) goto L25
                r1.close()
            L25:
                return r0
            L26:
                r0 = move-exception
                r0 = r9
            L28:
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f
                r1.<init>()     // Catch: java.lang.Throwable -> L4f
                java.lang.String r2 = "Error querying for table "
                java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L4f
                java.lang.StringBuilder r1 = r1.append(r11)     // Catch: java.lang.Throwable -> L4f
                java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L4f
                com.google.android.gms.tagmanager.zzbg.zzaC(r1)     // Catch: java.lang.Throwable -> L4f
                if (r0 == 0) goto L43
                r0.close()
            L43:
                r0 = r8
                goto L25
            L45:
                r0 = move-exception
            L46:
                if (r9 == 0) goto L4b
                r9.close()
            L4b:
                throw r0
            L4c:
                r0 = move-exception
                r9 = r1
                goto L46
            L4f:
                r1 = move-exception
                r9 = r0
                r0 = r1
                goto L46
            L53:
                r0 = move-exception
                r0 = r1
                goto L28
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzby.zzb.zza(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
        }

        private void zzc(SQLiteDatabase sQLiteDatabase) {
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
            HashSet hashSet = new HashSet();
            try {
                for (String str : cursorRawQuery.getColumnNames()) {
                    hashSet.add(str);
                }
                cursorRawQuery.close();
                if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_time") || !hashSet.remove("hit_first_send_time")) {
                    throw new SQLiteException("Database column missing");
                }
                if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } catch (Throwable th) {
                cursorRawQuery.close();
                throw th;
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public SQLiteDatabase getWritableDatabase() {
            if (this.zzaMz && this.zzaMA + 3600000 > zzby.this.zzpw.currentTimeMillis()) {
                throw new SQLiteException("Database creation failed");
            }
            SQLiteDatabase writableDatabase = null;
            this.zzaMz = true;
            this.zzaMA = zzby.this.zzpw.currentTimeMillis();
            try {
                writableDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                zzby.this.mContext.getDatabasePath(zzby.this.zzaMv).delete();
            }
            if (writableDatabase == null) {
                writableDatabase = super.getWritableDatabase();
            }
            this.zzaMz = false;
            return writableDatabase;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            zzal.zzbe(db.getPath());
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(SQLiteDatabase db) throws SQLException {
            if (Build.VERSION.SDK_INT < 15) {
                Cursor cursorRawQuery = db.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    cursorRawQuery.moveToFirst();
                } finally {
                    cursorRawQuery.close();
                }
            }
            if (zza("gtm_hits", db)) {
                zzc(db);
            } else {
                db.execSQL(zzby.zzKg);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    zzby(zzav zzavVar, Context context) {
        this(zzavVar, context, "gtm_urls.db", 2000);
    }

    zzby(zzav zzavVar, Context context, String str, int i) {
        this.mContext = context.getApplicationContext();
        this.zzaMv = str;
        this.zzaMu = zzavVar;
        this.zzpw = zzld.zzoQ();
        this.zzaMs = new zzb(this.mContext, this.zzaMv);
        this.zzaMt = new zzcx(new DefaultHttpClient(), this.mContext, new zza());
        this.zzaMw = 0L;
        this.zzaMx = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzd(long j, long j2) {
        SQLiteDatabase sQLiteDatabaseZzer = zzer("Error opening database for getNumStoredHits.");
        if (sQLiteDatabaseZzer == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_first_send_time", Long.valueOf(j2));
        try {
            sQLiteDatabaseZzer.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
        } catch (SQLiteException e) {
            zzbg.zzaC("Error setting HIT_FIRST_DISPATCH_TIME for hitId: " + j);
            zzq(j);
        }
    }

    private SQLiteDatabase zzer(String str) {
        try {
            return this.zzaMs.getWritableDatabase();
        } catch (SQLiteException e) {
            zzbg.zzaC(str);
            return null;
        }
    }

    private void zzh(long j, String str) {
        SQLiteDatabase sQLiteDatabaseZzer = zzer("Error opening database for putHit");
        if (sQLiteDatabaseZzer == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_time", Long.valueOf(j));
        contentValues.put("hit_url", str);
        contentValues.put("hit_first_send_time", (Integer) 0);
        try {
            sQLiteDatabaseZzer.insert("gtm_hits", null, contentValues);
            this.zzaMu.zzan(false);
        } catch (SQLiteException e) {
            zzbg.zzaC("Error storing hit");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzq(long j) {
        zzf(new String[]{String.valueOf(j)});
    }

    private void zzzb() throws Throwable {
        int iZzzc = (zzzc() - this.zzaMx) + 1;
        if (iZzzc > 0) {
            List<String> listZziY = zziY(iZzzc);
            zzbg.zzaB("Store full, deleting " + listZziY.size() + " hits to make room.");
            zzf((String[]) listZziY.toArray(new String[0]));
        }
    }

    @Override // com.google.android.gms.tagmanager.zzau
    public void dispatch() throws Throwable {
        zzbg.zzaB("GTM Dispatch running...");
        if (this.zzaMt.zzyH()) {
            List<zzaq> listZziZ = zziZ(40);
            if (listZziZ.isEmpty()) {
                zzbg.zzaB("...nothing to dispatch");
                this.zzaMu.zzan(true);
            } else {
                this.zzaMt.zzr(listZziZ);
                if (zzzd() > 0) {
                    zzcu.zzzz().dispatch();
                }
            }
        }
    }

    void zzf(String[] strArr) {
        SQLiteDatabase sQLiteDatabaseZzer;
        if (strArr == null || strArr.length == 0 || (sQLiteDatabaseZzer = zzer("Error opening database for deleteHits.")) == null) {
            return;
        }
        try {
            sQLiteDatabaseZzer.delete("gtm_hits", String.format("HIT_ID in (%s)", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))), strArr);
            this.zzaMu.zzan(zzzc() == 0);
        } catch (SQLiteException e) {
            zzbg.zzaC("Error deleting hits");
        }
    }

    @Override // com.google.android.gms.tagmanager.zzau
    public void zzg(long j, String str) throws Throwable {
        zzis();
        zzzb();
        zzh(j, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    java.util.List<java.lang.String> zziY(int r14) throws java.lang.Throwable {
        /*
            r13 = this;
            r10 = 0
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            if (r14 > 0) goto Lf
            java.lang.String r0 = "Invalid maxHits specified. Skipping"
            com.google.android.gms.tagmanager.zzbg.zzaC(r0)
            r0 = r9
        Le:
            return r0
        Lf:
            java.lang.String r0 = "Error opening database for peekHitIds."
            android.database.sqlite.SQLiteDatabase r0 = r13.zzer(r0)
            if (r0 != 0) goto L19
            r0 = r9
            goto Le
        L19:
            java.lang.String r1 = "gtm_hits"
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: android.database.sqlite.SQLiteException -> L5c java.lang.Throwable -> L7e
            r3 = 0
            java.lang.String r4 = "hit_id"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L5c java.lang.Throwable -> L7e
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.String r7 = "%s ASC"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch: android.database.sqlite.SQLiteException -> L5c java.lang.Throwable -> L7e
            r11 = 0
            java.lang.String r12 = "hit_id"
            r8[r11] = r12     // Catch: android.database.sqlite.SQLiteException -> L5c java.lang.Throwable -> L7e
            java.lang.String r7 = java.lang.String.format(r7, r8)     // Catch: android.database.sqlite.SQLiteException -> L5c java.lang.Throwable -> L7e
            java.lang.String r8 = java.lang.Integer.toString(r14)     // Catch: android.database.sqlite.SQLiteException -> L5c java.lang.Throwable -> L7e
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: android.database.sqlite.SQLiteException -> L5c java.lang.Throwable -> L7e
            boolean r0 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L86 android.database.sqlite.SQLiteException -> L88
            if (r0 == 0) goto L55
        L43:
            r0 = 0
            long r2 = r1.getLong(r0)     // Catch: java.lang.Throwable -> L86 android.database.sqlite.SQLiteException -> L88
            java.lang.String r0 = java.lang.String.valueOf(r2)     // Catch: java.lang.Throwable -> L86 android.database.sqlite.SQLiteException -> L88
            r9.add(r0)     // Catch: java.lang.Throwable -> L86 android.database.sqlite.SQLiteException -> L88
            boolean r0 = r1.moveToNext()     // Catch: java.lang.Throwable -> L86 android.database.sqlite.SQLiteException -> L88
            if (r0 != 0) goto L43
        L55:
            if (r1 == 0) goto L5a
            r1.close()
        L5a:
            r0 = r9
            goto Le
        L5c:
            r0 = move-exception
            r1 = r10
        L5e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L86
            r2.<init>()     // Catch: java.lang.Throwable -> L86
            java.lang.String r3 = "Error in peekHits fetching hitIds: "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> L86
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L86
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch: java.lang.Throwable -> L86
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L86
            com.google.android.gms.tagmanager.zzbg.zzaC(r0)     // Catch: java.lang.Throwable -> L86
            if (r1 == 0) goto L5a
            r1.close()
            goto L5a
        L7e:
            r0 = move-exception
            r1 = r10
        L80:
            if (r1 == 0) goto L85
            r1.close()
        L85:
            throw r0
        L86:
            r0 = move-exception
            goto L80
        L88:
            r0 = move-exception
            goto L5e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzby.zziY(int):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.google.android.gms.tagmanager.zzaq> zziZ(int r17) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 384
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzby.zziZ(int):java.util.List");
    }

    int zzis() {
        long jCurrentTimeMillis = this.zzpw.currentTimeMillis();
        if (jCurrentTimeMillis <= this.zzaMw + 86400000) {
            return 0;
        }
        this.zzaMw = jCurrentTimeMillis;
        SQLiteDatabase sQLiteDatabaseZzer = zzer("Error opening database for deleteStaleHits.");
        if (sQLiteDatabaseZzer == null) {
            return 0;
        }
        int iDelete = sQLiteDatabaseZzer.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.zzpw.currentTimeMillis() - 2592000000L)});
        this.zzaMu.zzan(zzzc() == 0);
        return iDelete;
    }

    int zzzc() {
        Cursor cursorRawQuery = null;
        SQLiteDatabase sQLiteDatabaseZzer = zzer("Error opening database for getNumStoredHits.");
        try {
            if (sQLiteDatabaseZzer != null) {
                try {
                    cursorRawQuery = sQLiteDatabaseZzer.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                    i = cursorRawQuery.moveToFirst() ? (int) cursorRawQuery.getLong(0) : 0;
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } catch (SQLiteException e) {
                    zzbg.zzaC("Error getting numStoredHits");
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                }
            }
            return i;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    int zzzd() throws java.lang.Throwable {
        /*
            r10 = this;
            r8 = 0
            r9 = 0
            java.lang.String r0 = "Error opening database for getNumStoredHits."
            android.database.sqlite.SQLiteDatabase r0 = r10.zzer(r0)
            if (r0 != 0) goto Lb
        La:
            return r8
        Lb:
            java.lang.String r1 = "gtm_hits"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: android.database.sqlite.SQLiteException -> L2f java.lang.Throwable -> L3d
            r3 = 0
            java.lang.String r4 = "hit_id"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L2f java.lang.Throwable -> L3d
            r3 = 1
            java.lang.String r4 = "hit_first_send_time"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L2f java.lang.Throwable -> L3d
            java.lang.String r3 = "hit_first_send_time=0"
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch: android.database.sqlite.SQLiteException -> L2f java.lang.Throwable -> L3d
            int r0 = r1.getCount()     // Catch: java.lang.Throwable -> L44 android.database.sqlite.SQLiteException -> L4b
            if (r1 == 0) goto L2d
            r1.close()
        L2d:
            r8 = r0
            goto La
        L2f:
            r0 = move-exception
            r0 = r9
        L31:
            java.lang.String r1 = "Error getting num untried hits"
            com.google.android.gms.tagmanager.zzbg.zzaC(r1)     // Catch: java.lang.Throwable -> L47
            if (r0 == 0) goto L4e
            r0.close()
            r0 = r8
            goto L2d
        L3d:
            r0 = move-exception
        L3e:
            if (r9 == 0) goto L43
            r9.close()
        L43:
            throw r0
        L44:
            r0 = move-exception
            r9 = r1
            goto L3e
        L47:
            r1 = move-exception
            r9 = r0
            r0 = r1
            goto L3e
        L4b:
            r0 = move-exception
            r0 = r1
            goto L31
        L4e:
            r0 = r8
            goto L2d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzby.zzzd():int");
    }
}
