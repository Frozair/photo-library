package com.newrdev.photolibrary.ui.album;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.newrdev.photolibrary.BuildConfig;
import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.util.Constants;

import timber.log.Timber;

/**
 * Created by rudolph on 10/4/16.
 */

public class AlbumActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Bundle extras = getIntent().getExtras();

        if( extras != null ) {
            Album album = (Album) extras.get(Constants.ALBUM_KEY);

            PhotosAdapter adapter = new PhotosAdapter(album.getPhotos());

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
    }
}
