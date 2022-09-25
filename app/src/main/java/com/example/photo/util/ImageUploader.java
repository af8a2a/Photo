package com.example.photo.util;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.photo.Entity.ItemImage;
import com.example.photo.Entity.JsonUtil.ImageJson;
import com.example.photo.Entity.JsonUtil.ImageServerUploadBackJson;
import com.example.photo.PhotoshowActivity;
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
//上传图片的工具类
public class ImageUploader {
    //上传文件
    //异步版本
    public static void async_upload(String path,Callback callback){
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
        client.newCall(request).enqueue(callback);
    }
    //已经弃用的实现
    @Deprecated
    public static void upload(String path,ImageJson json){
        File file = new File(path);
        OkHttpClient client=new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "file",
                        file.getName(),
                        MultipartBody.create(MediaType.parse("multipart/form-data"), file)
                ).build();
        //参数解释
        //https://img.ski/api/v1/upload 是图床服务器的上传API
        //Authorization                 用户的上传Token
        //Accept                        获取类型
        //Content-Type                  文件的类型和网页的编码
        Request request=new Request.Builder().url("https://img.ski/api/v1/upload")
                .addHeader("Authorization","Bearer 1|nCKcl9R4t56sZtKPH1q9vGjRMdb9d0xSRVbzJ7N2")
                .addHeader("Accept","application/json")
                .addHeader("Content-Type","multipart/form-data").post(body)
                .build();
        try {
            Response response=client.newCall(request).execute();
            if(response.isSuccessful()){
                //post返回值
                String res = response.body().string();
                //Gson解析返回的json
                Gson gson=new Gson();
                ImageServerUploadBackJson imageList = gson.fromJson(res,ImageServerUploadBackJson.class);
                //System.out.println("success");
                json.setPic_url(imageList.getData().getLinks().getUrl());
                //开启线程将上传图床的图片返回的url更新至数据库
                Thread t = new Thread(() -> ImageServerUtil.addImage(json));
                t.start();
                while(t.isAlive());

                //等待信息同步
            }else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*client.newCall(request).enqueue(new Callback() {
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
                    System.out.println("success");
                    json.setPic_url(imageList.getData().getLinks().getUrl());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ImageServerUtil.addImage(json);
                        }
                    }).start();
                }else{
                    System.out.println("fail");
                }
            }
        });*/
    }

}
