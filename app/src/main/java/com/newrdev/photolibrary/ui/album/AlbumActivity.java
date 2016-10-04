package com.newrdev.photolibrary.ui.album;

import android.app.Activity;
import android.os.Bundle;

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

        if( extras != null )
        {
            Album album = (Album) extras.get(Constants.ALBUM_KEY);
            Timber.i("Album %d has %d photos", album.getId(), album.getPhotos().size());
        }
    }
}
