package com.example.photo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.photo.Entity.Comment;
import com.example.photo.util.ImageServerUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class addComment extends AppCompatActivity {
    private String url;
    private EditText comment_text;
    private Button sumbit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        url=getIntent().getStringExtra("url");
        comment_text=findViewById(R.id.et_comment);
        sumbit=findViewById(R.id.btn_submit);
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment=new Comment();
                comment.setPic_url(url);
                comment.setUsername(PhotoshowActivity.getUsername());
                comment.setComment_text(comment_text.getText().toString());
                ImageServerUtil.addComment(comment, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        finish();
                    }
                });
            }
        });
    }
}