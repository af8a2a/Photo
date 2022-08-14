package com.example.photo.Entity;

public class ItemImage {
    private String ImageName;
    private String Author;
    private int imageId;
    private String url;
    public ItemImage() {

    }

    public ItemImage(String imageName, String author, int imageId,String url) {
        ImageName = imageName;
        Author = author;
        this.imageId = imageId;
        this.url=url;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
