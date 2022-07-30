package com.example.photo;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<Item.ViewHolder>{
    private List<Item> mItemList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Image;
        TextView  Author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.);
            Author = author;
        }
    }
}
