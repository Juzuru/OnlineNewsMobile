package com.example.onlinenewsmobile.models.KA;

import java.io.Serializable;

public class TopNewsHighReadTimes implements Serializable {
    private long id;
    private String image;
    private String title;
    private int readTimes;
    private String category;
    private long categoryId;
    private long newspaperId;
    private String link;
    private String newspaperName;

    public TopNewsHighReadTimes() {
    }

    public TopNewsHighReadTimes(long id, String image, String title, int readTimes, String category, long categoryId, long newspaperId, String link) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.readTimes = readTimes;
        this.category = category;
        this.categoryId = categoryId;
        this.newspaperId = newspaperId;
        this.link = link;
    }

    public String getNewspaperName() {
        return newspaperName;
    }

    public void setNewspaperName(String newspaperName) {
        this.newspaperName = newspaperName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(int readTimes) {
        this.readTimes = readTimes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getNewspaperId() {
        return newspaperId;
    }

    public void setNewspaperId(long newspaperId) {
        this.newspaperId = newspaperId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
