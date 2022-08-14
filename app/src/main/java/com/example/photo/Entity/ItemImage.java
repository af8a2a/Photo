package com.example.photo.Entity;

public class ItemImage {
    private int pid;
    private String ImageName;
    private String Author;
    private String url;
    public ItemImage() {

    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public ItemImage(String imageName, String author, String url, int pid) {
        ImageName = imageName;
        Author = author;
        this.url=url;
        this.pid=pid;
    }
    public boolean equals(Object obj){
        if(obj==null){
            return true;
        }
        ItemImage img=(ItemImage)obj;
        if(pid==img.getPid()){
            return true;
        }
        return false;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

}
