package com.newrdev.photolibrary.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by newrdev on 10/3/16.
 */

public class Album {
    private Integer id;
    private List<Photo> photos;

    public Album() {
        photos = new ArrayList<>();
    }

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
}
