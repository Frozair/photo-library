package com.newrdev.photolibrary.ui.album;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.newrdev.photolibrary.BuildConfig;
import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.model.Photo;
import com.newrdev.photolibrary.ui.slideshow.SlideShowActivity;
import com.newrdev.photolibrary.util.Constants;

import java.util.List;

import timber.log.Timber;

/**
 * Created by rudolph on 10/4/16.
 */

public class AlbumActivity extends Activity implements AlbumView{

    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Bundle extras = getIntent().getExtras();

        if( extras != null ) {
            this.album = (Album) extras.get(Constants.ALBUM_KEY);

            PhotosAdapter adapter = new PhotosAdapter(album.getPhotos(), this);

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onPhotoClick(int photoPosition) {

        Intent intent = new Intent(this, SlideShowActivity.class);
        intent.putExtra(Constants.ALBUM_KEY, album);
        intent.putExtra(Constants.PHOTO_KEY, photoPosition);

        this.startActivity(intent);
    }
}
