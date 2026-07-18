package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class zzqf {

    public static class zza {
        private final zzag.zza zzaNw;
        private final Map<String, zzag.zza> zzaPy;

        private zza(Map<String, zzag.zza> map, zzag.zza zzaVar) {
            this.zzaPy = map;
            this.zzaNw = zzaVar;
        }

        public static zzb zzAm() {
            return new zzb();
        }

        public String toString() {
            return "Properties: " + zzAn() + " pushAfterEvaluate: " + this.zzaNw;
        }

        public Map<String, zzag.zza> zzAn() {
            return Collections.unmodifiableMap(this.zzaPy);
        }

        public void zza(String str, zzag.zza zzaVar) {
            this.zzaPy.put(str, zzaVar);
        }

        public zzag.zza zzzs() {
            return this.zzaNw;
        }
    }

    public static class zzb {
        private zzag.zza zzaNw;
        private final Map<String, zzag.zza> zzaPy;

        private zzb() {
            this.zzaPy = new HashMap();
        }

        public zza zzAo() {
            return new zza(this.zzaPy, this.zzaNw);
        }

        public zzb zzb(String str, zzag.zza zzaVar) {
            this.zzaPy.put(str, zzaVar);
            return this;
        }

        public zzb zzq(zzag.zza zzaVar) {
            this.zzaNw = zzaVar;
            return this;
        }
    }

    public static class zzc {
        private final String zzTQ;
        private final Map<String, List<zza>> zzaPA;
        private final int zzaPB;
        private final List<zze> zzaPz;

        private zzc(List<zze> list, Map<String, List<zza>> map, String str, int i) {
            this.zzaPz = Collections.unmodifiableList(list);
            this.zzaPA = Collections.unmodifiableMap(map);
            this.zzTQ = str;
            this.zzaPB = i;
        }

        public static zzd zzAp() {
            return new zzd();
        }

        public String getVersion() {
            return this.zzTQ;
        }

        public String toString() {
            return "Rules: " + zzAq() + "  Macros: " + this.zzaPA;
        }

        public List<zze> zzAq() {
            return this.zzaPz;
        }

        public Map<String, List<zza>> zzAr() {
            return this.zzaPA;
        }
    }

    public static class zzd {
        private String zzTQ;
        private final Map<String, List<zza>> zzaPA;
        private int zzaPB;
        private final List<zze> zzaPz;

        private zzd() {
            this.zzaPz = new ArrayList();
            this.zzaPA = new HashMap();
            this.zzTQ = "";
            this.zzaPB = 0;
        }

        public zzc zzAs() {
            return new zzc(this.zzaPz, this.zzaPA, this.zzTQ, this.zzaPB);
        }

        public zzd zzb(zze zzeVar) {
            this.zzaPz.add(zzeVar);
            return this;
        }

        public zzd zzc(zza zzaVar) {
            String strZzg = com.google.android.gms.tagmanager.zzdf.zzg(zzaVar.zzAn().get(zzae.INSTANCE_NAME.toString()));
            List<zza> arrayList = this.zzaPA.get(strZzg);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.zzaPA.put(strZzg, arrayList);
            }
            arrayList.add(zzaVar);
            return this;
        }

        public zzd zzeV(String str) {
            this.zzTQ = str;
            return this;
        }

        public zzd zzjb(int i) {
            this.zzaPB = i;
            return this;
        }
    }

    public static class zze {
        private final List<zza> zzaPC;
        private final List<zza> zzaPD;
        private final List<zza> zzaPE;
        private final List<zza> zzaPF;
        private final List<zza> zzaPG;
        private final List<zza> zzaPH;
        private final List<String> zzaPI;
        private final List<String> zzaPJ;
        private final List<String> zzaPK;
        private final List<String> zzaPL;

        private zze(List<zza> list, List<zza> list2, List<zza> list3, List<zza> list4, List<zza> list5, List<zza> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
            this.zzaPC = Collections.unmodifiableList(list);
            this.zzaPD = Collections.unmodifiableList(list2);
            this.zzaPE = Collections.unmodifiableList(list3);
            this.zzaPF = Collections.unmodifiableList(list4);
            this.zzaPG = Collections.unmodifiableList(list5);
            this.zzaPH = Collections.unmodifiableList(list6);
            this.zzaPI = Collections.unmodifiableList(list7);
            this.zzaPJ = Collections.unmodifiableList(list8);
            this.zzaPK = Collections.unmodifiableList(list9);
            this.zzaPL = Collections.unmodifiableList(list10);
        }

        public static zzf zzAt() {
            return new zzf();
        }

        public String toString() {
            return "Positive predicates: " + zzAu() + "  Negative predicates: " + zzAv() + "  Add tags: " + zzAw() + "  Remove tags: " + zzAx() + "  Add macros: " + zzAy() + "  Remove macros: " + zzAD();
        }

        public List<String> zzAA() {
            return this.zzaPJ;
        }

        public List<String> zzAB() {
            return this.zzaPK;
        }

        public List<String> zzAC() {
            return this.zzaPL;
        }

        public List<zza> zzAD() {
            return this.zzaPH;
        }

        public List<zza> zzAu() {
            return this.zzaPC;
        }

        public List<zza> zzAv() {
            return this.zzaPD;
        }

        public List<zza> zzAw() {
            return this.zzaPE;
        }

        public List<zza> zzAx() {
            return this.zzaPF;
        }

        public List<zza> zzAy() {
            return this.zzaPG;
        }

        public List<String> zzAz() {
            return this.zzaPI;
        }
    }

    public static class zzf {
        private final List<zza> zzaPC;
        private final List<zza> zzaPD;
        private final List<zza> zzaPE;
        private final List<zza> zzaPF;
        private final List<zza> zzaPG;
        private final List<zza> zzaPH;
        private final List<String> zzaPI;
        private final List<String> zzaPJ;
        private final List<String> zzaPK;
        private final List<String> zzaPL;

        private zzf() {
            this.zzaPC = new ArrayList();
            this.zzaPD = new ArrayList();
            this.zzaPE = new ArrayList();
            this.zzaPF = new ArrayList();
            this.zzaPG = new ArrayList();
            this.zzaPH = new ArrayList();
            this.zzaPI = new ArrayList();
            this.zzaPJ = new ArrayList();
            this.zzaPK = new ArrayList();
            this.zzaPL = new ArrayList();
        }

        public zze zzAE() {
            return new zze(this.zzaPC, this.zzaPD, this.zzaPE, this.zzaPF, this.zzaPG, this.zzaPH, this.zzaPI, this.zzaPJ, this.zzaPK, this.zzaPL);
        }

        public zzf zzd(zza zzaVar) {
            this.zzaPC.add(zzaVar);
            return this;
        }

        public zzf zze(zza zzaVar) {
            this.zzaPD.add(zzaVar);
            return this;
        }

        public zzf zzeW(String str) {
            this.zzaPK.add(str);
            return this;
        }

        public zzf zzeX(String str) {
            this.zzaPL.add(str);
            return this;
        }

        public zzf zzeY(String str) {
            this.zzaPI.add(str);
            return this;
        }

        public zzf zzeZ(String str) {
            this.zzaPJ.add(str);
            return this;
        }

        public zzf zzf(zza zzaVar) {
            this.zzaPE.add(zzaVar);
            return this;
        }

        public zzf zzg(zza zzaVar) {
            this.zzaPF.add(zzaVar);
            return this;
        }

        public zzf zzh(zza zzaVar) {
            this.zzaPG.add(zzaVar);
            return this;
        }

        public zzf zzi(zza zzaVar) {
            this.zzaPH.add(zzaVar);
            return this;
        }
    }

    public static class zzg extends Exception {
        public zzg(String str) {
            super(str);
        }
    }

    private static zzag.zza zza(int i, zzaf.zzf zzfVar, zzag.zza[] zzaVarArr, Set<Integer> set) throws zzg {
        int i2 = 0;
        if (set.contains(Integer.valueOf(i))) {
            zzeT("Value cycle detected.  Current value reference: " + i + ".  Previous value references: " + set + ".");
        }
        zzag.zza zzaVar = (zzag.zza) zza(zzfVar.zzic, i, "values");
        if (zzaVarArr[i] != null) {
            return zzaVarArr[i];
        }
        zzag.zza zzaVarZzo = null;
        set.add(Integer.valueOf(i));
        switch (zzaVar.type) {
            case 1:
            case 5:
            case 6:
            case 8:
                zzaVarZzo = zzaVar;
                break;
            case 2:
                zzaf.zzh zzhVarZzp = zzp(zzaVar);
                zzaVarZzo = zzo(zzaVar);
                zzaVarZzo.zziS = new zzag.zza[zzhVarZzp.zziD.length];
                int[] iArr = zzhVarZzp.zziD;
                int length = iArr.length;
                int i3 = 0;
                while (i2 < length) {
                    zzaVarZzo.zziS[i3] = zza(iArr[i2], zzfVar, zzaVarArr, set);
                    i2++;
                    i3++;
                }
                break;
            case 3:
                zzaVarZzo = zzo(zzaVar);
                zzaf.zzh zzhVarZzp2 = zzp(zzaVar);
                if (zzhVarZzp2.zziE.length != zzhVarZzp2.zziF.length) {
                    zzeT("Uneven map keys (" + zzhVarZzp2.zziE.length + ") and map values (" + zzhVarZzp2.zziF.length + ")");
                }
                zzaVarZzo.zziT = new zzag.zza[zzhVarZzp2.zziE.length];
                zzaVarZzo.zziU = new zzag.zza[zzhVarZzp2.zziE.length];
                int[] iArr2 = zzhVarZzp2.zziE;
                int length2 = iArr2.length;
                int i4 = 0;
                int i5 = 0;
                while (i4 < length2) {
                    zzaVarZzo.zziT[i5] = zza(iArr2[i4], zzfVar, zzaVarArr, set);
                    i4++;
                    i5++;
                }
                int[] iArr3 = zzhVarZzp2.zziF;
                int length3 = iArr3.length;
                int i6 = 0;
                while (i2 < length3) {
                    zzaVarZzo.zziU[i6] = zza(iArr3[i2], zzfVar, zzaVarArr, set);
                    i2++;
                    i6++;
                }
                break;
            case 4:
                zzaVarZzo = zzo(zzaVar);
                zzaVarZzo.zziV = com.google.android.gms.tagmanager.zzdf.zzg(zza(zzp(zzaVar).zziI, zzfVar, zzaVarArr, set));
                break;
            case 7:
                zzaVarZzo = zzo(zzaVar);
                zzaf.zzh zzhVarZzp3 = zzp(zzaVar);
                zzaVarZzo.zziZ = new zzag.zza[zzhVarZzp3.zziH.length];
                int[] iArr4 = zzhVarZzp3.zziH;
                int length4 = iArr4.length;
                int i7 = 0;
                while (i2 < length4) {
                    zzaVarZzo.zziZ[i7] = zza(iArr4[i2], zzfVar, zzaVarArr, set);
                    i2++;
                    i7++;
                }
                break;
        }
        if (zzaVarZzo == null) {
            zzeT("Invalid value: " + zzaVar);
        }
        zzaVarArr[i] = zzaVarZzo;
        set.remove(Integer.valueOf(i));
        return zzaVarZzo;
    }

    private static zza zza(zzaf.zzb zzbVar, zzaf.zzf zzfVar, zzag.zza[] zzaVarArr, int i) throws zzg {
        zzb zzbVarZzAm = zza.zzAm();
        for (int i2 : zzbVar.zzhN) {
            zzaf.zze zzeVar = (zzaf.zze) zza(zzfVar.zzid, Integer.valueOf(i2).intValue(), "properties");
            String str = (String) zza(zzfVar.zzib, zzeVar.key, "keys");
            zzag.zza zzaVar = (zzag.zza) zza(zzaVarArr, zzeVar.value, "values");
            if (zzae.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzbVarZzAm.zzq(zzaVar);
            } else {
                zzbVarZzAm.zzb(str, zzaVar);
            }
        }
        return zzbVarZzAm.zzAo();
    }

    private static zze zza(zzaf.zzg zzgVar, List<zza> list, List<zza> list2, List<zza> list3, zzaf.zzf zzfVar) {
        zzf zzfVarZzAt = zze.zzAt();
        for (int i : zzgVar.zzir) {
            zzfVarZzAt.zzd(list3.get(Integer.valueOf(i).intValue()));
        }
        for (int i2 : zzgVar.zzis) {
            zzfVarZzAt.zze(list3.get(Integer.valueOf(i2).intValue()));
        }
        for (int i3 : zzgVar.zzit) {
            zzfVarZzAt.zzf(list.get(Integer.valueOf(i3).intValue()));
        }
        for (int i4 : zzgVar.zziv) {
            zzfVarZzAt.zzeW(zzfVar.zzic[Integer.valueOf(i4).intValue()].zziR);
        }
        for (int i5 : zzgVar.zziu) {
            zzfVarZzAt.zzg(list.get(Integer.valueOf(i5).intValue()));
        }
        for (int i6 : zzgVar.zziw) {
            zzfVarZzAt.zzeX(zzfVar.zzic[Integer.valueOf(i6).intValue()].zziR);
        }
        for (int i7 : zzgVar.zzix) {
            zzfVarZzAt.zzh(list2.get(Integer.valueOf(i7).intValue()));
        }
        for (int i8 : zzgVar.zziz) {
            zzfVarZzAt.zzeY(zzfVar.zzic[Integer.valueOf(i8).intValue()].zziR);
        }
        for (int i9 : zzgVar.zziy) {
            zzfVarZzAt.zzi(list2.get(Integer.valueOf(i9).intValue()));
        }
        for (int i10 : zzgVar.zziA) {
            zzfVarZzAt.zzeZ(zzfVar.zzic[Integer.valueOf(i10).intValue()].zziR);
        }
        return zzfVarZzAt.zzAE();
    }

    private static <T> T zza(T[] tArr, int i, String str) throws zzg {
        if (i < 0 || i >= tArr.length) {
            zzeT("Index out of bounds detected: " + i + " in " + str);
        }
        return tArr[i];
    }

    public static zzc zzb(zzaf.zzf zzfVar) throws zzg {
        zzag.zza[] zzaVarArr = new zzag.zza[zzfVar.zzic.length];
        for (int i = 0; i < zzfVar.zzic.length; i++) {
            zza(i, zzfVar, zzaVarArr, new HashSet(0));
        }
        zzd zzdVarZzAp = zzc.zzAp();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < zzfVar.zzif.length; i2++) {
            arrayList.add(zza(zzfVar.zzif[i2], zzfVar, zzaVarArr, i2));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < zzfVar.zzig.length; i3++) {
            arrayList2.add(zza(zzfVar.zzig[i3], zzfVar, zzaVarArr, i3));
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < zzfVar.zzie.length; i4++) {
            zza zzaVarZza = zza(zzfVar.zzie[i4], zzfVar, zzaVarArr, i4);
            zzdVarZzAp.zzc(zzaVarZza);
            arrayList3.add(zzaVarZza);
        }
        for (zzaf.zzg zzgVar : zzfVar.zzih) {
            zzdVarZzAp.zzb(zza(zzgVar, arrayList, arrayList3, arrayList2, zzfVar));
        }
        zzdVarZzAp.zzeV(zzfVar.version);
        zzdVarZzAp.zzjb(zzfVar.zzip);
        return zzdVarZzAp.zzAs();
    }

    public static void zzc(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    private static void zzeT(String str) throws zzg {
        com.google.android.gms.tagmanager.zzbg.zzaz(str);
        throw new zzg(str);
    }

    public static zzag.zza zzo(zzag.zza zzaVar) {
        zzag.zza zzaVar2 = new zzag.zza();
        zzaVar2.type = zzaVar.type;
        zzaVar2.zzja = (int[]) zzaVar.zzja.clone();
        if (zzaVar.zzjb) {
            zzaVar2.zzjb = zzaVar.zzjb;
        }
        return zzaVar2;
    }

    private static zzaf.zzh zzp(zzag.zza zzaVar) throws zzg {
        if (((zzaf.zzh) zzaVar.zza(zzaf.zzh.zziB)) == null) {
            zzeT("Expected a ServingValue and didn't get one. Value is: " + zzaVar);
        }
        return (zzaf.zzh) zzaVar.zza(zzaf.zzh.zziB);
    }
}
