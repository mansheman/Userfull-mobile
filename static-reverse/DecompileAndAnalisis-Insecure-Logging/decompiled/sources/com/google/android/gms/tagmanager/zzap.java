package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/* loaded from: classes.dex */
class zzap extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.HASH.toString();
    private static final String zzaLE = com.google.android.gms.internal.zzae.ARG0.toString();
    private static final String zzaLK = com.google.android.gms.internal.zzae.ALGORITHM.toString();
    private static final String zzaLG = com.google.android.gms.internal.zzae.INPUT_FORMAT.toString();

    public zzap() {
        super(ID, zzaLE);
    }

    private byte[] zzd(String str, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzE(Map<String, zzag.zza> map) {
        byte[] bArrZzee;
        zzag.zza zzaVar = map.get(zzaLE);
        if (zzaVar == null || zzaVar == zzdf.zzzQ()) {
            return zzdf.zzzQ();
        }
        String strZzg = zzdf.zzg(zzaVar);
        zzag.zza zzaVar2 = map.get(zzaLK);
        String strZzg2 = zzaVar2 == null ? "MD5" : zzdf.zzg(zzaVar2);
        zzag.zza zzaVar3 = map.get(zzaLG);
        String strZzg3 = zzaVar3 == null ? "text" : zzdf.zzg(zzaVar3);
        if ("text".equals(strZzg3)) {
            bArrZzee = strZzg.getBytes();
        } else {
            if (!"base16".equals(strZzg3)) {
                zzbg.zzaz("Hash: unknown input format: " + strZzg3);
                return zzdf.zzzQ();
            }
            bArrZzee = zzk.zzee(strZzg);
        }
        try {
            return zzdf.zzI(zzk.zzi(zzd(strZzg2, bArrZzee)));
        } catch (NoSuchAlgorithmException e) {
            zzbg.zzaz("Hash: unknown algorithm: " + strZzg2);
            return zzdf.zzzQ();
        }
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzyh() {
        return true;
    }
}
