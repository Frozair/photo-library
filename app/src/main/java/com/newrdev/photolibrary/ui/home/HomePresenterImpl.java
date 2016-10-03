package com.newrdev.photolibrary.ui.home;

import com.newrdev.photolibrary.data.entity.PhotoEntity;
import com.newrdev.photolibrary.data.entity.mapper.PhotoEntityDataMapper;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.net.ApiService;
import com.newrdev.photolibrary.ui.base.BasePresenterImpl;

import java.util.Collection;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rudolph on 10/3/16.
 */

public class HomePresenterImpl extends BasePresenterImpl<HomeView> implements HomePresenter {

    private ApiService apiService;

    public HomePresenterImpl() {
        this.apiService = new ApiService();
    }

    @Override
    public void fetchCloudAlbums() {
        apiService.getApi()
                .getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FetchCloudAlbumsSubscriber());
    }

    @Override
    public void fetchLocalPhotos() {

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

            HomePresenterImpl.this.view().onCloudAlbumsFetched(albums);
        }
    }
}
