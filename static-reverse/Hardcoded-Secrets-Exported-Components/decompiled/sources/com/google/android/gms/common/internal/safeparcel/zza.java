package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.internal.view.SupportMenu;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class zza {

    /* renamed from: com.google.android.gms.common.internal.safeparcel.zza$zza, reason: collision with other inner class name */
    public static class C0036zza extends RuntimeException {
        public C0036zza(String str, Parcel parcel) {
            super(str + " Parcel: pos=" + parcel.dataPosition() + " size=" + parcel.dataSize());
        }
    }

    public static String[] zzA(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        String[] strArrCreateStringArray = parcel.createStringArray();
        parcel.setDataPosition(iZza + iDataPosition);
        return strArrCreateStringArray;
    }

    public static ArrayList<Integer> zzB(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        int i2 = parcel.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(Integer.valueOf(parcel.readInt()));
        }
        parcel.setDataPosition(iDataPosition + iZza);
        return arrayList;
    }

    public static ArrayList<String> zzC(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
        parcel.setDataPosition(iZza + iDataPosition);
        return arrayListCreateStringArrayList;
    }

    public static Parcel zzD(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.appendFrom(parcel, iDataPosition, iZza);
        parcel.setDataPosition(iZza + iDataPosition);
        return parcelObtain;
    }

    public static Parcel[] zzE(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        int i2 = parcel.readInt();
        Parcel[] parcelArr = new Parcel[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = parcel.readInt();
            if (i4 != 0) {
                int iDataPosition2 = parcel.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(parcel, iDataPosition2, i4);
                parcelArr[i3] = parcelObtain;
                parcel.setDataPosition(i4 + iDataPosition2);
            } else {
                parcelArr[i3] = null;
            }
        }
        parcel.setDataPosition(iDataPosition + iZza);
        return parcelArr;
    }

    public static int zza(Parcel parcel, int i) {
        return (i & SupportMenu.CATEGORY_MASK) != -65536 ? (i >> 16) & 65535 : parcel.readInt();
    }

    public static <T extends Parcelable> T zza(Parcel parcel, int i, Parcelable.Creator<T> creator) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        T tCreateFromParcel = creator.createFromParcel(parcel);
        parcel.setDataPosition(iZza + iDataPosition);
        return tCreateFromParcel;
    }

    private static void zza(Parcel parcel, int i, int i2) {
        int iZza = zza(parcel, i);
        if (iZza != i2) {
            throw new C0036zza("Expected size " + i2 + " got " + iZza + " (0x" + Integer.toHexString(iZza) + ")", parcel);
        }
    }

    private static void zza(Parcel parcel, int i, int i2, int i3) {
        if (i2 != i3) {
            throw new C0036zza("Expected size " + i3 + " got " + i2 + " (0x" + Integer.toHexString(i2) + ")", parcel);
        }
    }

    public static void zza(Parcel parcel, int i, List list, ClassLoader classLoader) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return;
        }
        parcel.readList(list, classLoader);
        parcel.setDataPosition(iZza + iDataPosition);
    }

    public static int zzaa(Parcel parcel) {
        return parcel.readInt();
    }

    public static int zzab(Parcel parcel) {
        int iZzaa = zzaa(parcel);
        int iZza = zza(parcel, iZzaa);
        int iDataPosition = parcel.dataPosition();
        if (zzbA(iZzaa) != 20293) {
            throw new C0036zza("Expected object header. Got 0x" + Integer.toHexString(iZzaa), parcel);
        }
        int i = iDataPosition + iZza;
        if (i < iDataPosition || i > parcel.dataSize()) {
            throw new C0036zza("Size read is invalid start=" + iDataPosition + " end=" + i, parcel);
        }
        return i;
    }

    public static void zzb(Parcel parcel, int i) {
        parcel.setDataPosition(zza(parcel, i) + parcel.dataPosition());
    }

    public static <T> T[] zzb(Parcel parcel, int i, Parcelable.Creator<T> creator) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        T[] tArr = (T[]) parcel.createTypedArray(creator);
        parcel.setDataPosition(iZza + iDataPosition);
        return tArr;
    }

    public static int zzbA(int i) {
        return 65535 & i;
    }

    public static <T> ArrayList<T> zzc(Parcel parcel, int i, Parcelable.Creator<T> creator) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        ArrayList<T> arrayListCreateTypedArrayList = parcel.createTypedArrayList(creator);
        parcel.setDataPosition(iZza + iDataPosition);
        return arrayListCreateTypedArrayList;
    }

    public static boolean zzc(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readInt() != 0;
    }

    public static Boolean zzd(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        if (iZza == 0) {
            return null;
        }
        zza(parcel, i, iZza, 4);
        return Boolean.valueOf(parcel.readInt() != 0);
    }

    public static byte zze(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return (byte) parcel.readInt();
    }

    public static short zzf(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return (short) parcel.readInt();
    }

    public static int zzg(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readInt();
    }

    public static Integer zzh(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        if (iZza == 0) {
            return null;
        }
        zza(parcel, i, iZza, 4);
        return Integer.valueOf(parcel.readInt());
    }

    public static long zzi(Parcel parcel, int i) {
        zza(parcel, i, 8);
        return parcel.readLong();
    }

    public static Long zzj(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        if (iZza == 0) {
            return null;
        }
        zza(parcel, i, iZza, 8);
        return Long.valueOf(parcel.readLong());
    }

    public static BigInteger zzk(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        byte[] bArrCreateByteArray = parcel.createByteArray();
        parcel.setDataPosition(iZza + iDataPosition);
        return new BigInteger(bArrCreateByteArray);
    }

    public static float zzl(Parcel parcel, int i) {
        zza(parcel, i, 4);
        return parcel.readFloat();
    }

    public static double zzm(Parcel parcel, int i) {
        zza(parcel, i, 8);
        return parcel.readDouble();
    }

    public static BigDecimal zzn(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        byte[] bArrCreateByteArray = parcel.createByteArray();
        int i2 = parcel.readInt();
        parcel.setDataPosition(iZza + iDataPosition);
        return new BigDecimal(new BigInteger(bArrCreateByteArray), i2);
    }

    public static String zzo(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        String string = parcel.readString();
        parcel.setDataPosition(iZza + iDataPosition);
        return string;
    }

    public static IBinder zzp(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        IBinder strongBinder = parcel.readStrongBinder();
        parcel.setDataPosition(iZza + iDataPosition);
        return strongBinder;
    }

    public static Bundle zzq(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(iZza + iDataPosition);
        return bundle;
    }

    public static byte[] zzr(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        byte[] bArrCreateByteArray = parcel.createByteArray();
        parcel.setDataPosition(iZza + iDataPosition);
        return bArrCreateByteArray;
    }

    public static byte[][] zzs(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return (byte[][]) null;
        }
        int i2 = parcel.readInt();
        byte[][] bArr = new byte[i2][];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = parcel.createByteArray();
        }
        parcel.setDataPosition(iDataPosition + iZza);
        return bArr;
    }

    public static boolean[] zzt(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        boolean[] zArrCreateBooleanArray = parcel.createBooleanArray();
        parcel.setDataPosition(iZza + iDataPosition);
        return zArrCreateBooleanArray;
    }

    public static int[] zzu(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        int[] iArrCreateIntArray = parcel.createIntArray();
        parcel.setDataPosition(iZza + iDataPosition);
        return iArrCreateIntArray;
    }

    public static long[] zzv(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        long[] jArrCreateLongArray = parcel.createLongArray();
        parcel.setDataPosition(iZza + iDataPosition);
        return jArrCreateLongArray;
    }

    public static BigInteger[] zzw(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        int i2 = parcel.readInt();
        BigInteger[] bigIntegerArr = new BigInteger[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bigIntegerArr[i3] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(iDataPosition + iZza);
        return bigIntegerArr;
    }

    public static float[] zzx(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        float[] fArrCreateFloatArray = parcel.createFloatArray();
        parcel.setDataPosition(iZza + iDataPosition);
        return fArrCreateFloatArray;
    }

    public static double[] zzy(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        double[] dArrCreateDoubleArray = parcel.createDoubleArray();
        parcel.setDataPosition(iZza + iDataPosition);
        return dArrCreateDoubleArray;
    }

    public static BigDecimal[] zzz(Parcel parcel, int i) {
        int iZza = zza(parcel, i);
        int iDataPosition = parcel.dataPosition();
        if (iZza == 0) {
            return null;
        }
        int i2 = parcel.readInt();
        BigDecimal[] bigDecimalArr = new BigDecimal[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            byte[] bArrCreateByteArray = parcel.createByteArray();
            bigDecimalArr[i3] = new BigDecimal(new BigInteger(bArrCreateByteArray), parcel.readInt());
        }
        parcel.setDataPosition(iDataPosition + iZza);
        return bigDecimalArr;
    }
}
