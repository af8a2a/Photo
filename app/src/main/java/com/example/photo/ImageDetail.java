package com.example.photo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImageDetail extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout linearLayout;
    private ImageView btn_download;
    private ImageView btn_share;
    private ImageView btn_commend;
    private ImageView btn_comment;
    private ImageView btn_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        setContentView(R.layout.activity_image_detail);

        //显示图片
        BigImageView imageView=(BigImageView)findViewById(R.id.image);
        String url=getIntent().getStringExtra("image_url");
        imageView.showImage(Uri.parse(url));
        //设置点击
        linearLayout=findViewById(R.id.showimage);
        btn_download=findViewById(R.id.download);
        btn_share=findViewById(R.id.share);
        btn_commend=findViewById(R.id.commend);
        btn_comment=findViewById(R.id.comment);
        btn_favorite=findViewById(R.id.favorite);
        linearLayout.setOnClickListener(this);
        btn_commend.setOnClickListener(this);
        btn_comment.setOnClickListener(this);
        btn_download.setOnClickListener(this);
        btn_favorite.setOnClickListener(this);
        btn_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.download:{
                    //todo
                Toast.makeText(this,"下载!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.favorite:{
                //todo
                Toast.makeText(this,"收藏!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.commend:{
                // TODO
                Toast.makeText(this,"点赞!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.comment:{
                //todo
                Toast.makeText(this,"评论!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.share:{
                //todo
                Toast.makeText(this,"分享!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.showimage:{
                //todo
                Toast.makeText(this,"放大!",Toast.LENGTH_SHORT).show();
            }
            default:
                break;
        }
    }
}
