package com.newrdev.photolibrary.ui.slideshow;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Photo;

import java.util.List;

/**
 * Created by newrdev on 10/4/16.
 */

public class PhotosPagerAdapter extends PagerAdapter {
    private List<Photo> photos;
    private SlideShowView slideShowView;

    public PhotosPagerAdapter(List<Photo> photos, SlideShowView slideShowView)
    {
        this.photos = photos;
        this.slideShowView = slideShowView;
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.photo_holder_view, container, false);

        System.out.println("Instantiating item: " + position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Photo photo = this.photos.get(position);

        Glide.with(itemView.getContext())
                .load(photo.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideShowView.onSavePhotoClick();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

}
