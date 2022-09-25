package com.example.photo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photo.Entity.Comment;
import com.example.photo.Entity.JsonUtil.ImageJson;
import com.example.photo.util.ImageServerUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommentListActivity extends AppCompatActivity {
    private String url;
    private RecyclerView recyclerView;
    private boolean start=false;
    private CommentAdapter adapter;
    private List<Comment> commentList=new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        url=getIntent().getStringExtra("url");
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(),"加载中",Toast.LENGTH_SHORT).show());
                load();
                runOnUiThread(() -> Toast.makeText(getApplicationContext(),"加载完成",Toast.LENGTH_SHORT).show());
            }
        });
        t.start();
        while(t.isAlive());
        //while(!start);
        recyclerView=findViewById(R.id.comment_list);
        refreshLayout=findViewById(R.id.comment_refresh);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        adapter=new CommentAdapter(R.layout.comment_item,commentList,this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(() -> refresh());
        floatingActionButton=findViewById(R.id.comment_addButton);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(),addComment.class);
            intent.putExtra("url",url);
            startActivity(intent);
        });
        new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> refresh());
        }).start();
    }
    private void load(){
        Comment comment=new Comment();
        comment.setPic_url(url);
        ImageServerUtil.getComment(comment, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String res=response.body().string();
                    Gson gson=new Gson();
                    List<Comment> comments = gson.fromJson(res, new TypeToken<List<Comment>>() {
                    }.getType());
                    commentList.addAll(comments);
                    start=true;
                }
            }
        });
    }
    private void refresh(){
        commentList.clear();
        new Thread(() -> {
            runOnUiThread(() -> Toast.makeText(getApplicationContext(),"加载中",Toast.LENGTH_SHORT).show());
            load();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(),"加载完成",Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

}