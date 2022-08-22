package com.example.photo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.photo.Entity.Favorite;
import com.example.photo.databinding.ActivityMainBinding;
import com.example.photo.util.ImageServerUtil;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;
import com.github.piasy.biv.view.ImageSaveCallback;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
/*
展示图片
可以进行点赞，保存，收藏，放大等操作

 */
public class ImageDetail extends AppCompatActivity  {
    private BottomNavigationView bottomNavigationView;
    private ActivityMainBinding binding;
    private LinearLayout linearLayout;
    private ImageView btn_download;
    private ImageView btn_share;
    private ImageView btn_commend;
    private ImageView btn_comment;
    private ImageView btn_favorite;
    private BigImageView imageView;
    private String url;
    private boolean favoriteState=false;
    private boolean commendState=false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        //binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_image_detail);
        linearLayout = findViewById(R.id.showimage);
        /*
        btn_download = findViewById(R.id.nav_btm_download);
        btn_share = findViewById(R.id.nav_btm_share);
        btn_commend = findViewById(R.id.nav_btm_comment);
        btn_comment = findViewById(R.id.nav_btm_comment);
        btn_favorite = findViewById(R.id.nav_btm_favorite);
        bottomNavigationMenuView=findViewById(R.id.nav_btm);
       */
        bottomNavigationView=findViewById(R.id.nav_btm);
        favoriteState=getIntent().getBooleanExtra("isFavorite",false);
        commendState=getIntent().getBooleanExtra("isStar",false);
        //显示图片

        imageView = findViewById(R.id.image);
        url = getIntent().getStringExtra("image_url");
        //开源库BigImageViewer的初始化
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
/*
        linearLayout.setOnClickListener(this);
        btn_commend.setOnClickListener(this);
        btn_comment.setOnClickListener(this);
        btn_download.setOnClickListener(this);
        btn_favorite.setOnClickListener(this);
        btn_share.setOnClickListener(this);
*/
        init_nav();

    }
    //保存图片
    private void saveImage(String url){
        try {
            //Glide下载图片
            File file=Glide.with(getApplicationContext()).download(url).submit().get();
            String imagePath = file.getAbsolutePath();
            String imageName=file.getName();
            //插入至系统相册
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), imagePath, imageName, null);
            //广播通知更新v
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        runOnUiThread(() -> Toast.makeText(getApplicationContext(),"下载完成!",Toast.LENGTH_SHORT).show());

    }
    //点击进入时初始化图标
    private void init(){
        if(favoriteState==true){
            btn_favorite.setImageResource(R.drawable.v_heart_primary_x48);

        }else{
            btn_favorite.setImageResource(R.drawable.v_heart_outline_primary_x48);
        }
        if(commendState==true){

        }else{

        }

    }
    private void init_nav(){
        Menu menu=bottomNavigationView.getMenu();
        MenuItem fav=menu.findItem(R.id.nav_btm_favorite);
        MenuItem commend=menu.findItem(R.id.nav_btm_star);
        if(favoriteState==true){
            fav.setIcon(R.drawable.v_heart_primary_x48);

        }else{
            fav.setIcon(R.drawable.v_heart_outline_primary_x48);
        }
        if(commendState==true){
            commend.setIcon(R.drawable.v_thumb_up_primary_x48);

        }else{
            commend.setIcon(R.drawable.good);
        }
        Favorite favorite=new Favorite();
        favorite.setUsername(PhotoshowActivity.getUsername());
        favorite.setPic_url(url);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_btm_download: {
                        new Thread(() -> saveImage(url)).start();
                        Toast.makeText(getApplicationContext(),"开始下载!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_btm_favorite:{
                        if(favoriteState==false){
                            btn_favorite.setImageResource(R.drawable.v_heart_primary_x48);
                            new Thread(() -> {
                                ImageServerUtil.addFavorite(favorite);
                            }).start();
                        }else{
                            btn_favorite.setImageResource(R.drawable.v_heart_outline_primary_x48);
                            new Thread(()->{
                                ImageServerUtil.removeFavorite(favorite);
                            }).start();
                        }
                        favoriteState=!favoriteState;
                        Toast.makeText(getApplicationContext(),"收藏!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_btm_star:{

                        if(commendState==false){
                            new Thread(() -> {
                                ImageServerUtil.star(favorite);
                            }).start();
                            Toast.makeText(getApplicationContext(),"点赞",Toast.LENGTH_SHORT).show();
                        }else{
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ImageServerUtil.removeStar(favorite);
                                }
                            }).start();
                            Toast.makeText(getApplicationContext(),"取消点赞",Toast.LENGTH_SHORT).show();;
                        }
                        commendState=!commendState;
                        break;
                    }
                    case R.id.nav_btm_comment:{
                        Intent intent=new Intent(getApplicationContext(),CommentListActivity.class);
                        intent.putExtra("url",url);
                        startActivity(intent);
                        //Toast.makeText(this,"评论!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //复制图片url至剪贴板
                    case R.id.nav_btm_share:{
                        //todo
                        //粘贴板
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData mClipData = ClipData.newPlainText("Label", url);
                        cm.setPrimaryClip(mClipData);
                        Toast.makeText(getApplicationContext(),"分享!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public void onClick(View view) {
        Favorite favorite=new Favorite();
        favorite.setUsername(PhotoshowActivity.getUsername());
        favorite.setPic_url(url);
        BottomNavigationItemView itemView=(BottomNavigationItemView)view;
        @SuppressLint("RestrictedApi") MenuItem item=itemView.getItemData();
        switch (item.getItemId()) {
            case R.id.nav_btm_download: {
                new Thread(() -> saveImage(url)).start();
                 Toast.makeText(this,"开始下载!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_btm_favorite:{
                if(favoriteState==false){
                    btn_favorite.setImageResource(R.drawable.v_heart_primary_x48);
                    new Thread(() -> {
                        ImageServerUtil.addFavorite(favorite);
                    }).start();
                }else{
                    btn_favorite.setImageResource(R.drawable.v_heart_outline_primary_x48);
                    new Thread(()->{
                        ImageServerUtil.removeFavorite(favorite);
                    }).start();
                }
                favoriteState=!favoriteState;
                Toast.makeText(this,"收藏!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_btm_star:{

                if(commendState==false){
                    new Thread(() -> {
                        ImageServerUtil.star(favorite);
                    }).start();
                    Toast.makeText(this,"点赞",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ImageServerUtil.removeStar(favorite);
                        }
                    }).start();
                    Toast.makeText(this,"取消点赞",Toast.LENGTH_SHORT).show();;
                }
                commendState=!commendState;
                break;
            }
            case R.id.nav_btm_comment:{
                Intent intent=new Intent(getApplicationContext(),CommentListActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                //Toast.makeText(this,"评论!",Toast.LENGTH_SHORT).show();
                break;
            }
            //复制图片url至剪贴板
            case R.id.nav_btm_share:{
                //todo
                //粘贴板
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", url);
                cm.setPrimaryClip(mClipData);
                Toast.makeText(this,"分享!",Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
    }

}
