package com.example.photo;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.photo.Entity.Favorite;
import com.example.photo.Entity.ItemImage;
import com.example.photo.util.GlideApp;
import com.example.photo.util.ImageServerUtil;

import java.util.List;
/*
RecyclerView适配器
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private List<ItemImage> mItemList;
    private Context mContext;
    private int resourceId;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View imageItemView;//test
        ImageView Image;
        TextView Author;
        TextView Name;
        TextView Star;
        ImageView favorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItemView=itemView;
            Image=itemView.findViewById(R.id.preview_image);
            Author=itemView.findViewById(R.id.preview_author);
            Name=itemView.findViewById(R.id.preview_name);
            Star=itemView.findViewById(R.id.preview_star_num);
            favorite=itemView.findViewById(R.id.preview_favorite);
        }
     }

    public ImageAdapter(Context mContext, int resourceId,List<ItemImage> mItemList) {
        this.mItemList = mItemList;
        this.mContext = mContext;
        this.resourceId = resourceId;//layout_view
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(resourceId,parent,false);
        ViewHolder holder=new ViewHolder(view);
        boolean star=false;
        Intent intent=new Intent(view.getContext(),ImageDetail.class);
        //点item进入详情界面
        holder.imageItemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                ItemImage image=mItemList.get(position);//得到item,送图片资源id
                Favorite favorite=new Favorite();
                favorite.setPic_url(image.getUrl());
                favorite.setUsername(PhotoshowActivity.getUsername());

                Thread t1=new Thread(() -> {
                    boolean isStar=ImageServerUtil.checkStar(favorite);
                    System.out.println(isStar);
                    intent.putExtra("isStar",isStar);
                    //跳转
                });
                t1.start();
                Thread t2=new Thread(() -> {
                    boolean isFavorite=ImageServerUtil.checkFavorite(favorite);
                    intent.putExtra("isFavorite",isFavorite);
                    System.out.println(isFavorite);
                });
                t2.start();
                while(t1.isAlive()||t2.isAlive()){

                }
                intent.putExtra("image_url",image.getUrl());
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions options=new RequestOptions().skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.ALL);
        ItemImage item=mItemList.get(position);
        //holder.Image.setImageResource(item.getImageId());
        //holder.Image.setImageURI(Uri.parse(item.getUrl()));
        Glide.with(mContext).load(item.getUrl()).apply(options).into(holder.Image);
        holder.Author.setText(item.getAuthor());
        holder.Star.setText(String.valueOf(item.getStar()));
        holder.Name.setText(item.getImageName());
        if(item.isUserFavorite()){
            holder.favorite.setImageResource(R.drawable.v_heart_x16);
        }else{
            holder.favorite.setImageResource(R.drawable.v_heart_outline_primary_x16);
        }
    }
    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
