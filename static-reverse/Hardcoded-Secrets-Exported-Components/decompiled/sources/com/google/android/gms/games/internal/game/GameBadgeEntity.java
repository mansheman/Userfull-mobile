package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;

/* loaded from: classes.dex */
public final class GameBadgeEntity extends GamesDowngradeableSafeParcel implements GameBadge {
    public static final GameBadgeEntityCreator CREATOR = new GameBadgeEntityCreatorCompat();
    private final int zzCY;
    private int zzSq;
    private String zzadv;
    private String zzakM;
    private Uri zzanf;

    static final class GameBadgeEntityCreatorCompat extends GameBadgeEntityCreator {
        GameBadgeEntityCreatorCompat() {
        }

        @Override // com.google.android.gms.games.internal.game.GameBadgeEntityCreator, android.os.Parcelable.Creator
        /* renamed from: zzdE */
        public GameBadgeEntity createFromParcel(Parcel parcel) {
            if (GameBadgeEntity.zzd(GameBadgeEntity.zznE()) || GameBadgeEntity.zzca(GameBadgeEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            return new GameBadgeEntity(1, i, string, string2, string3 == null ? null : Uri.parse(string3));
        }
    }

    GameBadgeEntity(int versionCode, int type, String title, String description, Uri iconImageUri) {
        this.zzCY = versionCode;
        this.zzSq = type;
        this.zzadv = title;
        this.zzakM = description;
        this.zzanf = iconImageUri;
    }

    public GameBadgeEntity(GameBadge gameBadge) {
        this.zzCY = 1;
        this.zzSq = gameBadge.getType();
        this.zzadv = gameBadge.getTitle();
        this.zzakM = gameBadge.getDescription();
        this.zzanf = gameBadge.getIconImageUri();
    }

    static int zza(GameBadge gameBadge) {
        return zzt.hashCode(Integer.valueOf(gameBadge.getType()), gameBadge.getTitle(), gameBadge.getDescription(), gameBadge.getIconImageUri());
    }

    static boolean zza(GameBadge gameBadge, Object obj) {
        if (!(obj instanceof GameBadge)) {
            return false;
        }
        if (gameBadge == obj) {
            return true;
        }
        GameBadge gameBadge2 = (GameBadge) obj;
        return zzt.equal(Integer.valueOf(gameBadge2.getType()), gameBadge.getTitle()) && zzt.equal(gameBadge2.getDescription(), gameBadge.getIconImageUri());
    }

    static String zzb(GameBadge gameBadge) {
        return zzt.zzt(gameBadge).zzg("Type", Integer.valueOf(gameBadge.getType())).zzg("Title", gameBadge.getTitle()).zzg("Description", gameBadge.getDescription()).zzg("IconImageUri", gameBadge.getIconImageUri()).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return zza(this, obj);
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public String getDescription() {
        return this.zzakM;
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public Uri getIconImageUri() {
        return this.zzanf;
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public String getTitle() {
        return this.zzadv;
    }

    @Override // com.google.android.gms.games.internal.game.GameBadge
    public int getType() {
        return this.zzSq;
    }

    public int getVersionCode() {
        return this.zzCY;
    }

    public int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return zzb(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (!zznF()) {
            GameBadgeEntityCreator.zza(this, dest, flags);
            return;
        }
        dest.writeInt(this.zzSq);
        dest.writeString(this.zzadv);
        dest.writeString(this.zzakM);
        dest.writeString(this.zzanf == null ? null : this.zzanf.toString());
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* renamed from: zzti, reason: merged with bridge method [inline-methods] */
    public GameBadge freeze() {
        return this;
    }
}
