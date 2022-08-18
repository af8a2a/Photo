package com.example.photo.util;

import androidx.annotation.NonNull;

import com.example.photo.Entity.ItemImage;
import com.example.photo.Entity.JsonUtil.ImageJson;
import com.example.photo.Entity.JsonUtil.ImageServerUploadBackJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUploader {
    @Deprecated
    public static void getToken(Callback callback){
        OkHttpClient client=new OkHttpClient();
        //JSONObject json=new JSONObject();
        //RequestBody requestBody=new FormBody.Builder().build();
        Request request=new Request.Builder().url("http://39.108.13.67:/img/token")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void upload(String path,ImageJson json){
        //todo
        File file = new File(path);

        OkHttpClient client=new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "file",
                        file.getName(),
                        MultipartBody.create(MediaType.parse("multipart/form-data"), file)
                ).build();

        Request request=new Request.Builder().url("https://img.ski/api/v1/upload")
                .addHeader("Authorization","Bearer 1|nCKcl9R4t56sZtKPH1q9vGjRMdb9d0xSRVbzJ7N2")
                .addHeader("Accept","application/json")
                .addHeader("Content-Type","multipart/form-data").post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String res = response.body().string();
                    Gson gson=new Gson();
                    ImageServerUploadBackJson imageList = gson.fromJson(res,ImageServerUploadBackJson.class);
                    System.out.println(imageList.getData().getLinks().getUrl());
                    json.setPic_url(imageList.getData().getLinks().getUrl());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ImageServerUtil.addImage(json);
                        }
                    }).start();
                }
            }
        });
    }

}
