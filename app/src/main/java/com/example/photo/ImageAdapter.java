package com.example.photo;


import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private List<ItemImage> mItemList;
    private Context mContext;
    private int resourceId;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Author;
        TextView Name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image=itemView.findViewById(R.id.preview_image);
            Author=itemView.findViewById(R.id.preview_author);
            Name=itemView.findViewById(R.id.preview_name);
        }
     }

    public ImageAdapter(Context mContext, int resourceId,List<ItemImage> mItemList) {
        this.mItemList = mItemList;
        this.mContext = mContext;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(resourceId,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemImage item=mItemList.get(position);
        holder.Image.setImageResource(item.getImageId());
        holder.Author.setText(item.getAuthor());
        holder.Name.setText(item.getImageName());
    }
    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
