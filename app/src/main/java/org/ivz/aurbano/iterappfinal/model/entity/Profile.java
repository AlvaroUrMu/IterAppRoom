package org.ivz.aurbano.iterappfinal.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile",
        indices = {@Index(value = "name", unique = true)},
        foreignKeys = {@ForeignKey(entity = Country.class, parentColumns = "id", childColumns = "idcountry", onDelete = ForeignKey.CASCADE)})

public class Profile implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "idcountry")
    public long idcountry;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "url")
    public String url;

    public Profile(){}

    protected Profile(Parcel in){
        id = in.readLong();
        name = in.readString();
        idcountry = in.readLong();
        city = in.readString();
        date = in.readString();
        url = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public boolean isValid() {
        return !(name.isEmpty() || city.isEmpty()  || date.isEmpty() || url.isEmpty() || idcountry <= 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(idcountry);
        dest.writeString(city);
        dest.writeString(date);
        dest.writeString(url);
    }
}
