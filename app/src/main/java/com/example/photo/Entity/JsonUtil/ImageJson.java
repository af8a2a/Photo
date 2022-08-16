package com.example.photo.Entity.JsonUtil;

public class ImageJson {
    private Integer pid;
    private String author;
    private String title;
    private String pic_url;
    private int star=0;

    public ImageJson(Integer pid, String author, String title, String pic_url, int star) {
        this.pid = pid;
        this.author = author;
        this.title = title;
        this.pic_url = pic_url;
        this.star = star;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
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

}
