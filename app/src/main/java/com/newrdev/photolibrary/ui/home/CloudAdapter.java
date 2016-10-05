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

import java.util.List;

/**
 * Created by newrdev on 10/4/16.
 */

public class CloudAdapter extends RecyclerView.Adapter<CloudAdapter.ViewHolder> {
    private List<Album> albums;
    private HomeView homeView;

    public CloudAdapter(List<Album> albums, HomeView homeView) {
        this.albums = albums;
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
        Album album = albums.get(position);
        String text = "Album " + album.getId().toString();
        holder.textView.setText(text);
        holder.progressBar.setVisibility(View.VISIBLE);

        Glide.with(holder.itemView.getContext())
                .load(album.getPhotos().get(0).getThumbnailUrl())
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


        holder.album = album;
    }

    @Override
    public int getItemCount() {
        return this.albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView thumbnail;
        public Album album;
        public ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.titleTextView);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloudAdapter.this.homeView.onAlbumClick(album);
                }
            });
        }

    }
}
