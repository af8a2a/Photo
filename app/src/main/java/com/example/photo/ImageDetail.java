package com.example.photo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImageDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ImageView imageView=findViewById(R.id.image);
        int imageResourceId=getIntent().getIntExtra("image_url",0);
        imageView.setImageResource(imageResourceId);
    }
}