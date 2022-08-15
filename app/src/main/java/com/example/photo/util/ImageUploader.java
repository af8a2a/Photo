package com.example.photo.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

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
    public static void upload(String path){
        File file = new File(path);
        OkHttpClient client=new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "file",
                        file.getName(),
                        MultipartBody.create(MediaType.parse("multipart/form-data"), file)
                ).build();

        //JSONObject json=new JSONObject();
        Request request=new Request.Builder().url("https://img.ski/api/v1/upload").post(body)
                .addHeader("Authorization","Bearer 1|nCKcl9R4t56sZtKPH1q9vGjRMdb9d0xSRVbzJ7N2")
                .addHeader("Accept","application/json")
                .addHeader("Content-Type","multipart/form-data")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String res = response.body().string();
                    System.out.println(1);
                }
            }
        });
    }
}
