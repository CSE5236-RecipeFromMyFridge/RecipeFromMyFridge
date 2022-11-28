package com.example.recipefrommyfridgeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class Cuisine implements Parcelable {

    public String enName, zhName;

    public Cuisine(String enName, String zhName) {
        this.enName = enName;
        this.zhName = zhName;
    }

    public Cuisine() {
    }

    protected Cuisine(Parcel in) {
        enName = in.readString();
        zhName = in.readString();
    }

    public static final Creator<Cuisine> CREATOR = new Creator<Cuisine>() {
        @Override
        public Cuisine createFromParcel(Parcel in) {
            return new Cuisine(in);
        }

        @Override
        public Cuisine[] newArray(int size) {
            return new Cuisine[size];
        }
    };

    public String getName() {
        if (Locale.getDefault().getLanguage().equals("en")) {
            return enName;
        } else {
            return zhName;
        }
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(enName);
        dest.writeString(zhName);
    }
}
