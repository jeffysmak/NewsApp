package com.electrosoft.newsapplication.pojos;


import java.io.Serializable;

public class Post implements Serializable {

    private int id;
    private String title;
    private String date;
    private String image_url;
    private String content;
    private String postLink;


    public Post(int id, String title, String date, String image_url,String content,String postLink)
    {
        this.id = id;
        this.title = title;
        this.date = date;
        this.image_url = image_url;
        this.content = content;
        this.postLink = postLink;
    }
    public int getId() {
        return id;
    }

    public String getPostLink() {
        return postLink;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getTitle() {
        return title;
    }
}
