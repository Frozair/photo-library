package com.newrdev.photolibrary.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;
import com.newrdev.photolibrary.data.model.Photo;

import java.util.List;

/**
 * Created by rudolph on 10/5/16.
 */

public class LocalPhotosAdapter extends RecyclerView.Adapter<LocalPhotosAdapter.ViewHolder> {
    private List<Photo> photos;
    private HomeView homeView;

    public LocalPhotosAdapter(List<Photo> photos, HomeView homeView) {
        this.photos = photos;
        this.homeView = homeView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cloud_album_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Photo photo = photos.get(position);
        String text = "Photo " + photo.getId().toString();
        holder.textView.setText(text);
        holder.progressBar.setVisibility(View.VISIBLE);

        Glide.with(holder.itemView.getContext())
                .load(photo.getThumbnailUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.thumbnail);


        holder.photoIndex = photos.indexOf(photo);
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView thumbnail;
        public int photoIndex;
        public ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.titleTextView);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalPhotosAdapter.this.homeView.onPhotoClick(photos, photoIndex);
                }
            });
        }

    }
}
