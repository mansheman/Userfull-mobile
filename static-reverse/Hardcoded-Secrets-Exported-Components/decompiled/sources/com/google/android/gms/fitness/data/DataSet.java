package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class DataSet implements SafeParcelable {
    public static final Parcelable.Creator<DataSet> CREATOR = new zze();
    private final int zzCY;
    private final DataType zzajF;
    private final DataSource zzajG;
    private boolean zzajU;
    private final List<DataPoint> zzakb;
    private final List<DataSource> zzakc;

    DataSet(int versionCode, DataSource dataSource, DataType dataType, List<RawDataPoint> dataPoints, List<DataSource> uniqueDataSources, boolean serverHasMoreData) {
        this.zzajU = false;
        this.zzCY = versionCode;
        this.zzajG = dataSource;
        this.zzajF = dataSource.getDataType();
        this.zzajU = serverHasMoreData;
        this.zzakb = new ArrayList(dataPoints.size());
        this.zzakc = versionCode < 2 ? Collections.singletonList(dataSource) : uniqueDataSources;
        Iterator<RawDataPoint> it = dataPoints.iterator();
        while (it.hasNext()) {
            this.zzakb.add(new DataPoint(this.zzakc, it.next()));
        }
    }

    public DataSet(DataSource dataSource) {
        this.zzajU = false;
        this.zzCY = 3;
        this.zzajG = (DataSource) zzu.zzu(dataSource);
        this.zzajF = dataSource.getDataType();
        this.zzakb = new ArrayList();
        this.zzakc = new ArrayList();
        this.zzakc.add(this.zzajG);
    }

    public DataSet(RawDataSet dataSet, List<DataSource> uniqueDataSources) {
        this.zzajU = false;
        this.zzCY = 3;
        this.zzajG = (DataSource) zzb(uniqueDataSources, dataSet.zzakH);
        this.zzajF = this.zzajG.getDataType();
        this.zzakc = uniqueDataSources;
        this.zzajU = dataSet.zzajU;
        List<RawDataPoint> list = dataSet.zzakK;
        this.zzakb = new ArrayList(list.size());
        Iterator<RawDataPoint> it = list.iterator();
        while (it.hasNext()) {
            this.zzakb.add(new DataPoint(this.zzakc, it.next()));
        }
    }

    public static DataSet create(DataSource dataSource) {
        zzu.zzb(dataSource, "DataSource should be specified");
        return new DataSet(dataSource);
    }

    private boolean zza(DataSet dataSet) {
        return com.google.android.gms.common.internal.zzt.equal(getDataType(), dataSet.getDataType()) && com.google.android.gms.common.internal.zzt.equal(this.zzajG, dataSet.zzajG) && com.google.android.gms.common.internal.zzt.equal(this.zzakb, dataSet.zzakb) && this.zzajU == dataSet.zzajU;
    }

    private static <T> T zzb(List<T> list, int i) {
        if (i < 0 || i >= list.size()) {
            return null;
        }
        return list.get(i);
    }

    public void add(DataPoint dataPoint) {
        DataSource dataSource = dataPoint.getDataSource();
        zzu.zzb(dataSource.getStreamIdentifier().equals(this.zzajG.getStreamIdentifier()), "Conflicting data sources found %s vs %s", dataSource, this.zzajG);
        dataPoint.zzqx();
        zzb(dataPoint);
    }

    public void addAll(Iterable<DataPoint> dataPoints) {
        Iterator<DataPoint> it = dataPoints.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    public DataPoint createDataPoint() {
        return DataPoint.create(this.zzajG);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        return o == this || ((o instanceof DataSet) && zza((DataSet) o));
    }

    public List<DataPoint> getDataPoints() {
        return Collections.unmodifiableList(this.zzakb);
    }

    public DataSource getDataSource() {
        return this.zzajG;
    }

    public DataType getDataType() {
        return this.zzajG.getDataType();
    }

    int getVersionCode() {
        return this.zzCY;
    }

    public int hashCode() {
        return com.google.android.gms.common.internal.zzt.hashCode(this.zzajG);
    }

    public boolean isEmpty() {
        return this.zzakb.isEmpty();
    }

    public String toString() {
        List<RawDataPoint> listZzqz = zzqz();
        Object[] objArr = new Object[2];
        objArr[0] = this.zzajG.toDebugString();
        Object obj = listZzqz;
        if (this.zzakb.size() >= 10) {
            obj = String.format("%d data points, first 5: %s", Integer.valueOf(this.zzakb.size()), listZzqz.subList(0, 5));
        }
        objArr[1] = obj;
        return String.format("DataSet{%s %s}", objArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        zze.zza(this, parcel, flags);
    }

    public void zzb(DataPoint dataPoint) {
        this.zzakb.add(dataPoint);
        DataSource originalDataSource = dataPoint.getOriginalDataSource();
        if (originalDataSource == null || this.zzakc.contains(originalDataSource)) {
            return;
        }
        this.zzakc.add(originalDataSource);
    }

    public void zzb(Iterable<DataPoint> iterable) {
        Iterator<DataPoint> it = iterable.iterator();
        while (it.hasNext()) {
            zzb(it.next());
        }
    }

    List<RawDataPoint> zzk(List<DataSource> list) {
        ArrayList arrayList = new ArrayList(this.zzakb.size());
        Iterator<DataPoint> it = this.zzakb.iterator();
        while (it.hasNext()) {
            arrayList.add(new RawDataPoint(it.next(), list));
        }
        return arrayList;
    }

    List<DataSource> zzqA() {
        return this.zzakc;
    }

    public boolean zzqr() {
        return this.zzajU;
    }

    List<RawDataPoint> zzqz() {
        return zzk(this.zzakc);
    }
}
