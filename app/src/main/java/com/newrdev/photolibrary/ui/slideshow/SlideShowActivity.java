package com.newrdev.photolibrary.ui.slideshow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.model.Photo;
import com.newrdev.photolibrary.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by newrdev on 10/4/16.
 */

public class SlideShowActivity extends Activity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.pageCount) TextView pageCountTextView;
    @BindView(R.id.title) TextView titleTextView;

    private List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if( extras != null ) {

            Album album = (Album) extras.get(Constants.ALBUM_KEY);
            photos = album.getPhotos();

            int position = extras.getInt(Constants.PHOTO_KEY);

            PhotosPagerAdapter adapter = new PhotosPagerAdapter(photos, this);
            pager.setAdapter(adapter);
            pager.addOnPageChangeListener(this);
            pager.setCurrentItem(position);

            displayPhoto(position);
        }
    }

    private void displayPhoto(int position) {
        pageCountTextView.setText((position + 1) + "/" + photos.size());

        Photo photo = photos.get(position);
        titleTextView.setText(photo.getTitle());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        displayPhoto(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
