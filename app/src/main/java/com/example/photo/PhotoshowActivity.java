package com.example.photo;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.photo.Entity.ImageJson;
import com.example.photo.Entity.ItemImage;
import com.example.photo.util.ImageServerUtil;
import com.example.photo.util.ImageUploader;
import com.github.piasy.biv.loader.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PhotoshowActivity extends AppCompatActivity {
    private List<ItemImage> newsList = new ArrayList<>();
    private RecyclerView lvNewsList;
    private ImageAdapter newsAdapter;
    private SwipeRefreshLayout refreshLayout;
    private String username;
    private NavigationView navigationView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            //Log.e(this.getClass().getName(), "Result:" + data.toString());
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(
                        uri,
                        filePathColumns,
                        null,
                        null,
                        null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
// 获取图片的存储路径
                String filePath = cursor.getString(columnIndex);
                ImageUploader.upload(filePath);
// 获取数据完毕后, 关闭游标
                cursor.close();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoshow);
        //不再返回登录界面
        MainActivity.mActivityInstance.finish();

        username=getIntent().getStringExtra("username");
        //initData();
        loadData_server();
        if(newsList.isEmpty()) System.out.println(1);
        lvNewsList = findViewById(R.id.photo_list);
        newsAdapter = new ImageAdapter(PhotoshowActivity.this, R.layout.carditem, newsList);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        lvNewsList.setLayoutManager(llm);
        lvNewsList.setAdapter(newsAdapter);
        initNav();
        refreshLayout=findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

    }
    private void loadData_server(){
        Gson gson=new Gson();
        ImageServerUtil.getImage(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String res = response.body().string();
                    List<ImageJson> imageList = gson.fromJson(res, new TypeToken<List<ImageJson>>() {}.getType());
                    for (int i = 0; i < imageList.size(); i++) {
                        ItemImage news = new ItemImage();
                        if(!newsList.contains(imageList.get(i).getPid())) {
                            news.setUrl(imageList.get(i).getPicUrl());
                            news.setImageName(imageList.get(i).getTitle());
                            news.setAuthor(imageList.get(i).getAuthor());
                            newsList.add(news);
                        }
                    }
                }else{
                }
            }
        });
    }
    /*
    @Deprecated
    private void initData() {;
        int length;
        titles = getResources().getStringArray(R.array.titles);
        authors = getResources().getStringArray(R.array.authors);
        TypedArray images = getResources().obtainTypedArray(R.array.images);
        
        length = Math.min(titles.length, authors.length);
        for (int i = 0; i < length; i++) {
            ItemImage news = new ItemImage();
            news.setImageName(titles[i]);
            news.setAuthor(authors[i]);
            news.setImageId(images.getResourceId(i, 0));
            newsList.add(news);
        }
    }*/
    private void initNav(){
        navigationView=findViewById(R.id.nav_view);
        View headView=navigationView.getHeaderView(0);
        ShapeableImageView icon = headView.findViewById(R.id.nav_icon_image);
        Glide.with(getApplicationContext()).load("https://pic.img.ski/1660445688.png").placeholder(R.drawable.v_history_black_x24).into(icon);
        TextView user=headView.findViewById(R.id.nav_user_text);
        user.setText("user:"+username);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_download:{
                    //todo
                    }
                    case R.id.nav_upload:{
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 2);

                    }
                }
                return false;
            }
        });
    }
    private void refreshList(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Thread.sleep(2000);

               }catch(InterruptedException e){
               }
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       //todo
                       //loadData_server();

                       newsAdapter.notifyDataSetChanged();
                       refreshLayout.setRefreshing(false);
                   }
               });
           }
       }).start();

    }
}