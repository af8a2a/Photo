package com.example.photo;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.photo.Entity.ImageJson;
import com.example.photo.Entity.ItemImage;
import com.example.photo.util.ImageServerUtil;
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
    private String[] titles = null;
    private String[] authors = null;
    private List<ItemImage> newsList = new ArrayList<>();
    private RecyclerView lvNewsList;
    private ImageAdapter newsAdapter;
    private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoshow);

        //不再返回登录界面
        MainActivity.mActivityInstance.finish();

        //initData();
        loadData_server();
        if(newsList.isEmpty()) System.out.println(1);
        lvNewsList = findViewById(R.id.photo_list);
        newsAdapter = new ImageAdapter(PhotoshowActivity.this, R.layout.carditem, newsList);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        lvNewsList.setLayoutManager(llm);
        lvNewsList.setAdapter(newsAdapter);
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
                        news.setUrl(imageList.get(i).getPicUrl());
                        news.setImageName(imageList.get(i).getTitle());
                        news.setAuthor(imageList.get(i).getAuthor());
                        newsList.add(news);
                    }
                }else{
                    Log.d(TAG, "onResponse: error");
                }
            }
        });
    }
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
    }
    private void initNav(){
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_download:{

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
                       loadData_server();
                       newsAdapter.notifyDataSetChanged();
                       refreshLayout.setRefreshing(false);
                   }
               });
           }
       }).start();

    }
}