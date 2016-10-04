package com.newrdev.photolibrary.ui.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @BindView(R.id.cloudRecyclerView) RecyclerView cloudRecyclerView;
    @BindView(R.id.localRecyclerView) RecyclerView localRecyclerView;

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

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onCloudAlbumsFetched(List<Album> albums) {
        cloudRecyclerView.setAdapter(new CloudRecyclerAdapter(albums, this));
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

        // TODO - Check if we already cached this, and don't call this
        this.presenter.fetchCloudAlbums();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.presenter.bindView(this);
    }
}
