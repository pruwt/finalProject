package com.example.finalproject;


//class to represent each article
public class Article {
    private String title;


    private int imageResId;
    private String content;

    public Article(String title, String content ,int imageResId){
        this.title = title;
        this.imageResId = imageResId;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

//    public String getDescription() {
//        return description;
//    }
public String getContent() {
    return content;
}
    public int getImageResId() {
        return imageResId;
    }




}


