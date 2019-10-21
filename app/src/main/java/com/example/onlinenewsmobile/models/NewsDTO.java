package com.example.onlinenewsmobile.models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class NewsDTO implements Serializable {
    private String title;
    private String description;
    private String link;
    private String imageLink;
    private Bitmap imageBitmap;
    private int categoryId;
    private String categoryName;
    private String rssLink;
    private String newspaper;
    private String newspaperLink;
    private boolean isMark;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public String getNewspaperLink() {
        return newspaperLink;
    }

    public void setNewspaperLink(String newspaperLink) {
        this.newspaperLink = newspaperLink;
    }

    public boolean isMark() {
        return isMark;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
