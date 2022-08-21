package com.example.photo.Entity;
/*
用户的头像
data_id:数据库的自增id
username:用户名
user_img:图片url
 */
public class UserImage {
    private Integer data_id;
    private String username;
    private String user_img;

    public UserImage() {
    }

    public Integer getData_id() {
        return data_id;
    }

    public void setData_id(Integer data_id) {
        this.data_id = data_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }
}