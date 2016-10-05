package com.newrdev.photolibrary.ui.slideshow;

import com.newrdev.photolibrary.data.model.Photo;
import com.newrdev.photolibrary.data.net.PhotoService;
import com.newrdev.photolibrary.ui.base.BasePresenter;

import io.realm.Realm;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rudolph on 10/5/16.
 */

public class SlideShowPresenter extends BasePresenter<SlideShowView> {

    public void downloadAndSavePhoto(Photo photo)
    {
        PhotoService.downloadPhoto(photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Photo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Photo photo) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();

                        realm.copyToRealmOrUpdate(photo);

                        realm.commitTransaction();
                    }
                });
    }
}
