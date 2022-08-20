package com.example.photo.Entity;

public class ItemImage {
    private int pid;
    private String ImageName;
    private String Author;
    private String url;
    private int star;
    private boolean isUserFavorite;
    public ItemImage() {

    }

    public boolean isUserFavorite() {
        return isUserFavorite;
    }

    public void setUserFavorite(boolean userFavorite) {
        isUserFavorite = userFavorite;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public ItemImage(String imageName, String author, String url, int pid,int star) {
        ImageName = imageName;
        Author = author;
        this.url=url;
        this.pid=pid;
        this.star=star;
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
