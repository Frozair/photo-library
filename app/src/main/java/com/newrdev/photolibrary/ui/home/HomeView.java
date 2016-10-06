package com.newrdev.photolibrary.ui.home;

import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.model.Photo;

import java.util.Collection;
import java.util.List;

/**
 * Created by rudolph on 10/3/16.
 */

public interface HomeView {
    void onCloudAlbumsFetched(List<Album> albums);
    void onLocalPhotosFetched(List<Photo> photos);
    void onAlbumClick(Album album);
    void onPhotoClick(List<Photo> photos, int photoPosition);
    void onCloudFetchError();
}
