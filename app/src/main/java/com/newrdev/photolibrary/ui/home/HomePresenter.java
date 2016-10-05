package com.newrdev.photolibrary.ui.home;

import com.newrdev.photolibrary.data.entity.PhotoEntity;
import com.newrdev.photolibrary.data.entity.mapper.PhotoEntityDataMapper;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.model.Photo;
import com.newrdev.photolibrary.data.net.ApiService;
import com.newrdev.photolibrary.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rudolph on 10/3/16.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    public void fetchCloudAlbums() {
        ApiService.getApi()
                .getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FetchCloudAlbumsSubscriber());
    }

    public void fetchLocalPhotos() {
        this.view().onLocalPhotosFetched(Realm.getDefaultInstance().where(Photo.class).findAll());
    }

    private final class FetchCloudAlbumsSubscriber extends Subscriber<List<PhotoEntity>> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<PhotoEntity> photoEntities) {
            PhotoEntityDataMapper mapper = new PhotoEntityDataMapper();
            Collection<Album> albums = mapper.transform(photoEntities);

            HomePresenter.this.view().onCloudAlbumsFetched(new ArrayList<>(albums));
        }
    }
}
