package com.example.photo.util;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.photo.MainActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DbLogin{
    private static final String ip="http://39.108.13.67";
    private static final String port=":8848";
    private static final String LOGIN_API="/user/login";
    private static final String REGISTER_API="/user/register";
    private static final String RESET_API="/user/reset";

    public static void login(String username,String password,Callback callback){
        OkHttpClient client=new OkHttpClient();
        //JSONObject json=new JSONObject();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();
        Request request=new Request.Builder().url("http://39.108.13.67:8848/user/login").post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }
    public static void register(String username,String password,Callback callback){
        OkHttpClient client=new OkHttpClient();
        //JSONObject json=new JSONObject();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .add("password",password).build();
        Request request=new Request.Builder().url("http://39.108.13.67:8848/user/register").post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

}
