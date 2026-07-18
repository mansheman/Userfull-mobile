package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes.dex */
class zzce extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.REGEX_GROUP.toString();
    private static final String zzaMO = com.google.android.gms.internal.zzae.ARG0.toString();
    private static final String zzaMP = com.google.android.gms.internal.zzae.ARG1.toString();
    private static final String zzaMQ = com.google.android.gms.internal.zzae.IGNORE_CASE.toString();
    private static final String zzaMR = com.google.android.gms.internal.zzae.GROUP.toString();

    public zzce() {
        super(ID, zzaMO, zzaMP);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzE(Map<String, zzag.zza> map) {
        int iIntValue;
        zzag.zza zzaVar = map.get(zzaMO);
        zzag.zza zzaVar2 = map.get(zzaMP);
        if (zzaVar == null || zzaVar == zzdf.zzzQ() || zzaVar2 == null || zzaVar2 == zzdf.zzzQ()) {
            return zzdf.zzzQ();
        }
        int i = zzdf.zzk(map.get(zzaMQ)).booleanValue() ? 66 : 64;
        zzag.zza zzaVar3 = map.get(zzaMR);
        if (zzaVar3 != null) {
            Long lZzi = zzdf.zzi(zzaVar3);
            if (lZzi == zzdf.zzzL()) {
                return zzdf.zzzQ();
            }
            iIntValue = lZzi.intValue();
            if (iIntValue < 0) {
                return zzdf.zzzQ();
            }
        } else {
            iIntValue = 1;
        }
        try {
            String strZzg = zzdf.zzg(zzaVar);
            String strGroup = null;
            Matcher matcher = Pattern.compile(zzdf.zzg(zzaVar2), i).matcher(strZzg);
            if (matcher.find() && matcher.groupCount() >= iIntValue) {
                strGroup = matcher.group(iIntValue);
            }
            return strGroup == null ? zzdf.zzzQ() : zzdf.zzI(strGroup);
        } catch (PatternSyntaxException e) {
            return zzdf.zzzQ();
        }
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzyh() {
        return true;
    }
}
