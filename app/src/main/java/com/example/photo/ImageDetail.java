package com.example.photo;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;
import com.github.piasy.biv.view.ImageSaveCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutionException;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImageDetail extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearLayout;
    private ImageView btn_download;
    private ImageView btn_share;
    private ImageView btn_commend;
    private ImageView btn_comment;
    private ImageView btn_favorite;
    private BigImageView imageView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        setContentView(R.layout.activity_image_detail);

        //显示图片
        imageView = findViewById(R.id.image);
        url = getIntent().getStringExtra("image_url");

        imageView.setProgressIndicator(new ProgressPieIndicator());

        imageView.setImageViewFactory(new GlideImageViewFactory());
        imageView.setImageSaveCallback(new ImageSaveCallback() {
            @Override
            public void onSuccess(String uri) {
                Toast.makeText(ImageDetail.this,
                        "Success",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Throwable t) {
                t.printStackTrace();
                Toast.makeText(ImageDetail.this,
                        "Fail",
                        Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setImageLoaderCallback(new ImageLoader.Callback() {
            @Override
            public void onCacheHit(int imageType, File image) {
            }

            @Override
            public void onCacheMiss(int imageType, File image) {
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(File image) {
            }

            @Override
            public void onFail(Exception e) {
            }
        });
        imageView.showImage(Uri.parse(url));

        //设置点击
        linearLayout = findViewById(R.id.showimage);
        btn_download = findViewById(R.id.download);
        btn_share = findViewById(R.id.share);
        btn_commend = findViewById(R.id.commend);
        btn_comment = findViewById(R.id.comment);
        btn_favorite = findViewById(R.id.favorite);
        linearLayout.setOnClickListener(this);
        btn_commend.setOnClickListener(this);
        btn_comment.setOnClickListener(this);
        btn_download.setOnClickListener(this);
        btn_favorite.setOnClickListener(this);
        btn_share.setOnClickListener(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download: {
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
