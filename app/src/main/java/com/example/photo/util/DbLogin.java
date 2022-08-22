package com.example.photo.util;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DbLogin{
    public static void login(String username,String password,Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();
        //登录服务器API
        Request request=new Request.Builder().url("http://39.108.13.67:8848/user/login").post(requestBody).build();
        //发送HTTP请求
        client.newCall(request).enqueue(callback);
    }
    public static void register(String username,String password,Callback callback){
        OkHttpClient client=new OkHttpClient();
        //JSONObject json=new JSONObject();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();
        //注册请求
        Request request=new Request.Builder().url("http://39.108.13.67:8848/user/register").post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void reset(String username,String password,Callback callback){
        OkHttpClient client=new OkHttpClient();
        //重置密码请求
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();
        Request request=new Request.Builder().url("http://39.108.13.67:8848/user/reset").post(requestBody).build();
        client.newCall(request).enqueue(callback);

    }

}
