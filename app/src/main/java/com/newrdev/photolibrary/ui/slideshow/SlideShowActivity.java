package com.newrdev.photolibrary.ui.slideshow;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.model.Photo;
import com.newrdev.photolibrary.data.net.PhotoService;
import com.newrdev.photolibrary.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by newrdev on 10/4/16.
 */

public class SlideShowActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, SlideShowView {
    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.pageCount) TextView pageCountTextView;
    @BindView(R.id.title) TextView titleTextView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private SlideShowPresenter presenter;
    private List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        ButterKnife.bind(this);
        this.presenter = new SlideShowPresenter();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if( extras != null ) {

            photos = extras.getParcelableArrayList(Constants.ALBUM_KEY);

            int position = extras.getInt(Constants.PHOTO_KEY);

            PhotosPagerAdapter adapter = new PhotosPagerAdapter(photos, this);
            pager.setAdapter(adapter);
            pager.addOnPageChangeListener(this);
            pager.setCurrentItem(position);

            displayPhoto(position);
        }

    }

    @OnClick(R.id.downloadCloudButton)
    public void downloadPhoto() {
        int pos = pager.getCurrentItem();
        this.presenter.downloadAndSavePhoto(this.photos.get(pos));
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

    private void displayPhoto(int position) {
        pageCountTextView.setText((position + 1) + "/" + photos.size());

        Photo photo = photos.get(position);
        titleTextView.setText(photo.getTitle());

        getSupportActionBar().setTitle("Photo " + photo.getId());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
