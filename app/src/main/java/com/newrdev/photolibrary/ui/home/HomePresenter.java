package com.newrdev.photolibrary.ui.home;

import com.newrdev.photolibrary.ui.base.BasePresenter;

/**
 * Created by rudolph on 10/3/16.
 */

public interface HomePresenter extends BasePresenter<HomeView> {
    void fetchCloudAlbums();
    void fetchLocalPhotos();
}
