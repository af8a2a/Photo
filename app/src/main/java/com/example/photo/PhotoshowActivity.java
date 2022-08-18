package com.example.photo;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.photo.Entity.JsonUtil.ImageJson;
import com.example.photo.Entity.ItemImage;
import com.example.photo.util.ImageServerUtil;
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
    private List<ItemImage> imageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private SwipeRefreshLayout refreshLayout;
    private static String username;
    private NavigationView navigationView;

    public static String getUsername() {
        return username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoshow);
        //不再返回登录界面
        MainActivity.mActivityInstance.finish();
        //获取传递的用户名
        username=getIntent().getStringExtra("username");
        //initData();
        //服务器加载数据
        loadData_server();
        recyclerView = findViewById(R.id.photo_list);
        imageAdapter = new ImageAdapter(PhotoshowActivity.this, R.layout.carditem, imageList);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(imageAdapter);
        //侧边初始化
        initNav();
        //刷新
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
                        if(!PhotoshowActivity.this.imageList.contains(imageList.get(i).getPid())) {
                            news.setUrl(imageList.get(i).getPic_url());
                            news.setImageName(imageList.get(i).getTitle());
                            news.setAuthor(imageList.get(i).getAuthor());
                            PhotoshowActivity.this.imageList.add(news);
                        }
                    }
                }else{

                }
            }
        });
    }

    private void initNav(){
        navigationView=findViewById(R.id.nav_view);
        View headView=navigationView.getHeaderView(0);
        ShapeableImageView icon = headView.findViewById(R.id.nav_icon_image);

        Glide.with(getApplicationContext()).load("https://pic.img.ski/1660520531.png").placeholder(R.drawable.v_history_black_x24).into(icon);
        TextView user=headView.findViewById(R.id.nav_user_text);
        user.setText("user:"+username);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_download:{
                    //todo
                        break;
                    }
                    case R.id.nav_favorite:{
                        //todo
                        break;
                    }
                    case R.id.nav_setting:{
                        //todo
                        break;
                    }
                    case R.id.nav_readhistory:{
                        //todo
                        break;

                    }
                    case R.id.nav_upload:{
                        Intent intent=new Intent(getApplicationContext(),UploadActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        /*Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 3);*/
                        break;
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
                   imageList.clear();
                   loadData_server();
                   Thread.sleep(2000);

               }catch(InterruptedException e){
               }
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       //todo
                       imageAdapter.notifyDataSetChanged();
                       refreshLayout.setRefreshing(false);
                   }
               });
           }
       }).start();

    }
}