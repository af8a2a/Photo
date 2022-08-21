package com.example.photo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.photo.Entity.Comment;
import com.example.photo.Entity.Favorite;
import com.example.photo.Entity.ItemImage;
import com.example.photo.util.ImageServerUtil;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> mItemList;
    private int resourceId;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.comment_username);
            Text=itemView.findViewById(R.id.comment_text);
        }
    }

    public CommentAdapter(int resourceId,List<Comment> mItemList,Context context) {
        this.mItemList = mItemList;
        this.resourceId = resourceId;//layout_view
        mContext=context;
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
        Comment item=mItemList.get(position);
        holder.Text.setText(item.getComment_text());
        holder.Name.setText(item.getUsername());
    }
    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}