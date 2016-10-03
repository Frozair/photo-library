package com.newrdev.photolibrary.ui.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newrdev.photolibrary.BuildConfig;
import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.presenter = new HomePresenterImpl();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onCloudAlbumsFetched(Collection<Album> albums) {
        Timber.d("Total albums: %d", albums.size());
    }

    @Override
    public void onLocalPhotosFetched() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        this.presenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.presenter.bindView(this);
    }

    @OnClick(R.id.button)
    public void click() {
        Timber.d("Going to fetch albums");
        this.presenter.fetchCloudAlbums();
    }
}
