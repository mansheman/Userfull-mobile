package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* loaded from: classes.dex */
class zzad extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.ENCODE.toString();
    private static final String zzaLE = com.google.android.gms.internal.zzae.ARG0.toString();
    private static final String zzaLF = com.google.android.gms.internal.zzae.NO_PADDING.toString();
    private static final String zzaLG = com.google.android.gms.internal.zzae.INPUT_FORMAT.toString();
    private static final String zzaLH = com.google.android.gms.internal.zzae.OUTPUT_FORMAT.toString();

    public zzad() {
        super(ID, zzaLE);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzE(Map<String, zzag.zza> map) {
        byte[] bArrDecode;
        String strEncodeToString;
        zzag.zza zzaVar = map.get(zzaLE);
        if (zzaVar == null || zzaVar == zzdf.zzzQ()) {
            return zzdf.zzzQ();
        }
        String strZzg = zzdf.zzg(zzaVar);
        zzag.zza zzaVar2 = map.get(zzaLG);
        String strZzg2 = zzaVar2 == null ? "text" : zzdf.zzg(zzaVar2);
        zzag.zza zzaVar3 = map.get(zzaLH);
        String strZzg3 = zzaVar3 == null ? "base16" : zzdf.zzg(zzaVar3);
        zzag.zza zzaVar4 = map.get(zzaLF);
        int i = (zzaVar4 == null || !zzdf.zzk(zzaVar4).booleanValue()) ? 2 : 3;
        try {
            if ("text".equals(strZzg2)) {
                bArrDecode = strZzg.getBytes();
            } else if ("base16".equals(strZzg2)) {
                bArrDecode = zzk.zzee(strZzg);
            } else if ("base64".equals(strZzg2)) {
                bArrDecode = Base64.decode(strZzg, i);
            } else {
                if (!"base64url".equals(strZzg2)) {
                    zzbg.zzaz("Encode: unknown input format: " + strZzg2);
                    return zzdf.zzzQ();
                }
                bArrDecode = Base64.decode(strZzg, i | 8);
            }
            if ("base16".equals(strZzg3)) {
                strEncodeToString = zzk.zzi(bArrDecode);
            } else if ("base64".equals(strZzg3)) {
                strEncodeToString = Base64.encodeToString(bArrDecode, i);
            } else {
                if (!"base64url".equals(strZzg3)) {
                    zzbg.zzaz("Encode: unknown output format: " + strZzg3);
                    return zzdf.zzzQ();
                }
                strEncodeToString = Base64.encodeToString(bArrDecode, i | 8);
            }
            return zzdf.zzI(strEncodeToString);
        } catch (IllegalArgumentException e) {
            zzbg.zzaz("Encode: invalid input:");
            return zzdf.zzzQ();
        }
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzyh() {
        return true;
    }
}
