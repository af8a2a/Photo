package com.example.photo.util;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageServerUtil {
    public static void getToken(Callback callback){
        Gson gson=new Gson();
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        RequestBody requestBody=new FormBody.Builder()
                .add("email","1125209282@qq.com")
                .add("password","12345qqaa")
                .add("refresh","0").build();

        Request request=new Request.Builder().url("https://pic.jitudisk.com/api/token").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void getImage(Callback callback){
        OkHttpClient client=new OkHttpClient();
        //JSONObject json=new JSONObject();
        //RequestBody requestBody=new FormBody.Builder().build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/get").get().build();
        client.newCall(request).enqueue(callback);
    }
}