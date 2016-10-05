package com.newrdev.photolibrary.data.net;

import com.newrdev.photolibrary.data.job.DownloadPhoto;
import com.newrdev.photolibrary.data.model.Photo;

import java.util.List;

import rx.Observable;

/**
 * Created by rudolph on 10/5/16.
 */

public class PhotoService {
    public static Observable<Photo> downloadPhoto(Photo photo) {
        return new DownloadPhoto(photo).run();
    }

    public static Observable downloadAllPhotos(List<Photo> photo) {
        return null;
    }
}
