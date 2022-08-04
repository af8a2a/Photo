package com.example.photo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PhotoshowActivity extends AppCompatActivity {
    private String[] titles = null;
    private String[] authors = null;
    private List<ItemImage> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoshow);

        initData();
        ImageAdapter newsAdapter = new ImageAdapter(PhotoshowActivity.this, R.layout.carditem, newsList);
        RecyclerView lvNewsList = findViewById(R.id.photo_list);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        lvNewsList.setLayoutManager(llm);
        lvNewsList.setAdapter(newsAdapter);
    }
    private void initData() {
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
}