package com.newrdev.photolibrary.ui.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.newrdev.photolibrary.BuildConfig;
import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.ui.album.AlbumActivity;
import com.newrdev.photolibrary.util.Constants;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements HomeView {
    private HomePresenter presenter;
    private boolean albumsLoaded = false;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cloudRecyclerView) RecyclerView cloudRecyclerView;
    @BindView(R.id.localRecyclerView) RecyclerView localRecyclerView;
    @BindView(R.id.progressBarAlbum) ProgressBar albumProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        this.presenter = new HomePresenter();

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        cloudRecyclerView.setLayoutManager(horizontalLayoutManagaer);
//        localRecyclerView.setLayoutManager(horizontalLayoutManagaer);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onCloudAlbumsFetched(List<Album> albums) {
        cloudRecyclerView.setAdapter(new CloudAdapter(albums, this));
        this.albumProgressBar.setVisibility(View.GONE);
        this.albumsLoaded = true;
    }

    @Override
    public void onLocalPhotosFetched() {

    }

    @Override
    public void onAlbumClick(Album album) {
        Toast.makeText(this, "Album " + album.getId(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra(Constants.ALBUM_KEY, album);

        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.presenter.bindView(this);

        if( !this.albumsLoaded ) {
            this.albumProgressBar.setVisibility(View.VISIBLE);
            this.presenter.fetchCloudAlbums();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.presenter.bindView(this);
    }
}
