package com.example.adriaalvarez.android_boilerplate.models;

import java.util.Date;

/**
 * Created by adria.alvarez on 08/06/2017.
 */

public class Image {
    private int id;
    private String url;
    private String author;
    private String tags;
    private Date creationDate;

    public Image() {}

    public Image(int id, String url, String author, String tags, Date creationDate) {
        this.id = id;
        this.url = url;
        this.author = author;
        this.tags = tags;
        this.creationDate = creationDate;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
