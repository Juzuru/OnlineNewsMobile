package com.example.onlinenewsmobile.models.KA;

import java.io.Serializable;

public class SearchNewsDTO implements Serializable {
    private long id;
    private String title;
    private String postedTime;
    private String newspaper;
    private String image;

    public SearchNewsDTO() {
    }

    public SearchNewsDTO(long id, String title, String timer, String newspaper, String imgTitle) {
        this.id = id;
        this.title = title;
        this.postedTime = timer;
        this.newspaper = newspaper;
        this.image = imgTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
