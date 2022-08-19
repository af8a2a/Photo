package com.example.photo.util;

import androidx.annotation.NonNull;

import com.example.photo.Entity.Favorite;
import com.example.photo.Entity.ItemImage;
import com.example.photo.Entity.JsonUtil.ImageJson;
import com.example.photo.Entity.UserImage;
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
//服务器杂项工具
//异步请求
public class ImageServerUtil {
    //获取服务器的图片列表信息
    public static void getImage(Callback callback){
        OkHttpClient client=new OkHttpClient();
        //get请求
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/get").get().build();
        client.newCall(request).enqueue(callback);
    }
    public static void addImage(ImageJson imageJson){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("author",imageJson.getAuthor())
                .add("title",imageJson.getTitle())
                .add("star","0")
                .add("pic_url",imageJson.getPic_url())
                .build();
        //添加图片信息到服务器
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/add").post(requestBody).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// 切记
//为了减少柱页面的代码，以下方法采用开启新线程同步请求
// 以下方法需要开启一个新的线程来进行此操作


//username:用户名
//pic_url 图片url
// 将图片收藏
    public static void addFavorite(Favorite favorite){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",favorite.getUsername())
                .add("pic_url",favorite.getPic_url())
                .build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/addFavorite").post(requestBody).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //将图片移除收藏列表
    public static void removeFavorite(Favorite favorite){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",favorite.getUsername())
                .add("pic_url",favorite.getPic_url())
                .build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/removeFavorite").post(requestBody).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //点赞图片
    public static void star(Favorite favorite){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",favorite.getUsername())
                .add("pic_url",favorite.getPic_url())
                .build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/star").post(requestBody).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //取消点赞
    public static void removeStar(Favorite favorite){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",favorite.getUsername())
                .add("pic_url",favorite.getPic_url())
                .build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/removeStar").post(requestBody).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询图片用户是否已点赞
    public static boolean checkStar(Favorite favorite){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",favorite.getUsername())
                .add("pic_url",favorite.getPic_url())
                .build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/checkStar").post(requestBody).build();
        try {
            Response response=client.newCall(request).execute();
            if(response.isSuccessful()){
                if(response.body().string().isEmpty()){
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
//查询图片用户是否已收藏
    public static boolean checkFavorite(Favorite favorite){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",favorite.getUsername())
                .add("pic_url",favorite.getPic_url())
                .build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/checkFavorite").post(requestBody).build();
        try {
            Response response=client.newCall(request).execute();
                if(response.body().string().isEmpty()){
                    return false;
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
//WIP 工作不稳定
//上传用户头像

    public static void UpdateUserAvatar(UserImage image){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",image.getUsername())
                .add("user_img",image.getUser_img())
                .build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/uploadAvatar").post(requestBody).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取用户头像URL

    public static String getUserAvatar(String username){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .build();
        Request request=new Request.Builder().url("http://39.108.13.67:8844/img/userimg").post(requestBody).build();
        try {
            Response response=client.newCall(request).execute();
            if(response.isSuccessful())
                return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
