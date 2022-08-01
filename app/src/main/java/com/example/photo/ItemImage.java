package com.example.photo;

public class ItemImage {
    private String ImageName;
    private String Author;
    private int imageId;

    public ItemImage() {

    }

    public ItemImage(String imageName, String author, int imageId) {
        ImageName = imageName;
        Author = author;
        this.imageId = imageId;
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
