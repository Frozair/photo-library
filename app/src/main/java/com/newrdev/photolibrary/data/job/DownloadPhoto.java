package com.newrdev.photolibrary.data.job;

import com.newrdev.photolibrary.PhotoLibraryApplication;
import com.newrdev.photolibrary.data.model.Photo;
import com.newrdev.photolibrary.data.net.ApiService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * Created by rudolph on 10/5/16.
 */

public class DownloadPhoto implements Job<Photo> {
    private Photo photo;

    public DownloadPhoto(Photo photo) {
        this.photo = photo;

    }

    @Override
    public Observable<Photo> run() {
        return Observable.just(photo).map(new Func1<Photo, Photo>() {
            @Override
            public Photo call(Photo photo) {

                File rootFile = new File(PhotoLibraryApplication.getInstance().getExternalFilesDir(null), "photos");

                if(!rootFile.exists() && !rootFile.mkdirs()) {
                    throw Exceptions.propagate(new Throwable("Could not write, please ensure enabled the proper permissions."));
                }

                String originalImagePath = rootFile.getPath() + File.separator + photo.getId() + "_original";
                String thumbnailPath = rootFile.getPath() + File.separator + photo.getId() + "_thumb";

                if( !download(photo.getUrl(), originalImagePath) ) {
                    throw Exceptions.propagate(new Throwable("Could not download the Photo."));
                }

                if( !download(photo.getThumbnailUrl(), thumbnailPath) )
                {
                    throw Exceptions.propagate(new Throwable("Could not download the Photo."));
                }

                photo.setUrl(originalImagePath);
                photo.setThumbnailUrl(thumbnailPath);

                return photo;
            }
        });
    }

    private boolean download(final String url, final String path)
    {
        return ApiService.getApi()
                .downloadFile(url)
                .map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody responseBody) {
                        return writeResponseBodyToDisk(responseBody, path);
                    }
                }).toBlocking().first();
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String filePath) {
        try {
            File imageFile = new File(filePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(imageFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
