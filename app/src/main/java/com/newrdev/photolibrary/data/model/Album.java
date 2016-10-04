package com.newrdev.photolibrary.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newrdev on 10/3/16.
 */

public class Album implements Parcelable {
    private Integer id;
    private List<Photo> photos = new ArrayList<>();

    public Album() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeList(this.photos);
    }

    public static final Parcelable.Creator<Album> CREATOR  = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    private Album(Parcel in) {
        this.id = in.readInt();
        in.readList(this.photos, Photo.class.getClassLoader());
    }
}