package com.example.photo.Entity;

public class ImageJson {
    private Integer pid;
    private String author;
    private String title;
    private String pic_url;

    public ImageJson(Integer pid, String author, String title, String picUrl) {
        this.pid = pid;
        this.author = author;
        this.title = title;
        pic_url = picUrl;
    }

    public ImageJson() {
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return pic_url;
    }

    public void setPicUrl(String picUrl) {
        pic_url = picUrl;
    }
}
