package com.newrdev.photolibrary.ui.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newrdev.photolibrary.BuildConfig;
import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.model.Photo;
import com.newrdev.photolibrary.ui.album.AlbumActivity;
import com.newrdev.photolibrary.ui.slideshow.SlideShowActivity;
import com.newrdev.photolibrary.util.Constants;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements HomeView {
    private HomePresenter presenter;
    private boolean albumsLoaded = false;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cloudRecyclerView) RecyclerView cloudRecyclerView;
    @BindView(R.id.localRecyclerView) RecyclerView localRecyclerView;
    @BindView(R.id.progressBarAlbum) ProgressBar albumProgressBar;
    @BindView(R.id.progressBarLocal) ProgressBar localProgressBar;
    @BindView(R.id.localEmptyTextView) TextView localEmptyTextView;
    @BindView(R.id.retryCloudFetchButton) Button retryCloudFetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        this.presenter = new HomePresenter();

        cloudRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        localRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @OnClick(R.id.retryCloudFetchButton)
    public void fetchCloudAlbums() {
        retryCloudFetchButton.setVisibility(View.GONE);
        albumProgressBar.setVisibility(View.VISIBLE);
        presenter.fetchCloudAlbums();
    }

    @Override
    public void onCloudFetchError() {
        albumProgressBar.setVisibility(View.GONE);
        cloudRecyclerView.setVisibility(View.GONE);
        retryCloudFetchButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCloudAlbumsFetched(List<Album> albums) {
        cloudRecyclerView.setVisibility(View.VISIBLE);
        cloudRecyclerView.setAdapter(new CloudAdapter(albums, this));
        albumProgressBar.setVisibility(View.GONE);
        albumsLoaded = true;
    }

    @Override
    public void onLocalPhotosFetched(List<Photo> photos) {
        if( photos.size() > 0 ) {
            localRecyclerView.setVisibility(View.VISIBLE);
            localEmptyTextView.setVisibility(View.GONE);

            localRecyclerView.setAdapter(new LocalPhotosAdapter(photos, this));
        } else {
            localRecyclerView.setVisibility(View.GONE);
            localEmptyTextView.setVisibility(View.VISIBLE);
        }

        localProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onAlbumClick(Album album) {
        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra(Constants.ALBUM_KEY, album);
        intent.putExtra(Constants.SLIDE_SHOW_TYPE_KEY, Constants.SlideShowType.CLOUD);

        this.startActivity(intent);
    }

    @Override
    public void onPhotoClick(List<Photo> photos, int photoPosition) {
        Intent intent = new Intent(this, SlideShowActivity.class);
        intent.putExtra(Constants.ALBUM_KEY, new ArrayList<>(photos));
        intent.putExtra(Constants.PHOTO_KEY, photoPosition);
        intent.putExtra(Constants.SLIDE_SHOW_TYPE_KEY, Constants.SlideShowType.LOCAL);

        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.presenter.bindView(this);

        if( !this.albumsLoaded ) {
            this.fetchCloudAlbums();
        }

        this.localProgressBar.setVisibility(View.VISIBLE);
        this.localRecyclerView.setVisibility(View.VISIBLE);
        this.localEmptyTextView.setVisibility(View.GONE);
        this.presenter.fetchLocalPhotos();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.presenter.bindView(this);
    }
}
