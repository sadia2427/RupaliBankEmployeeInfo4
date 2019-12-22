package com.example.rupalibankemployeeinfo.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rupalibankemployeeinfo.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{


    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_gallery_adapter, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder( GalleryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{
        public GalleryViewHolder( View itemView) {
            super(itemView);
        }
    }
}
