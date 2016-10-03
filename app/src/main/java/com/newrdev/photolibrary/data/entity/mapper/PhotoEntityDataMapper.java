package com.newrdev.photolibrary.data.entity.mapper;

import android.util.ArrayMap;

import com.newrdev.photolibrary.data.entity.PhotoEntity;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.model.Photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by newrdev on 10/3/16.
 */

public class PhotoEntityDataMapper {
    public Collection<Album> transform(List<PhotoEntity> photoEntityList) {

        Map<Integer, Album> albumMap = new ArrayMap<>();

        for(PhotoEntity photoEntity : photoEntityList) {
            Integer albumId = photoEntity.getAlbumId();

            if( !albumMap.containsKey(albumId) ) {
                albumMap.put(albumId, new Album());
            }

            // add photo
            albumMap.get(albumId).getPhotos().add(this.transform(photoEntity));
        }

        return albumMap.values();
    }

    private Photo transform(PhotoEntity photoEntity) {
        Photo photo = new Photo();

        photo.setId(photoEntity.getId());
        photo.setTitle(photoEntity.getTitle());
        photo.setUrl(photoEntity.getUrl());
        photo.setThumbnailUrl(photoEntity.getThumbnailUrl());

        return photo;
    }
}
