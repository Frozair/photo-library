package com.newrdev.photolibrary.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newrdev.photolibrary.R;
import com.newrdev.photolibrary.data.model.Album;

import java.util.List;

import timber.log.Timber;

/**
 * Created by newrdev on 10/4/16.
 */

public class CloudRecyclerAdapter extends RecyclerView.Adapter<CloudRecyclerAdapter.ViewHolder> {
    private List<Album> albums;
    private HomeView homeView;

    public CloudRecyclerAdapter(List<Album> albums, HomeView homeView) {
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

        Glide.with(holder.itemView.getContext())
                .load(album.getPhotos().get(0).getThumbnailUrl())
                .into(holder.imageView);


        holder.album = album;
    }

    @Override
    public int getItemCount() {
        return this.albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public Album album;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.titleTextView);
            imageView = (ImageView) view.findViewById(R.id.previewImageView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloudRecyclerAdapter.this.homeView.onAlbumClick(album);
                }
            });
        }

    }
}
