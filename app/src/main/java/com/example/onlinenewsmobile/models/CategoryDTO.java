package com.example.onlinenewsmobile.models;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private int id;
    private String name;
    private boolean visible;

    private int newspaperId;
    private String newspaper;
    private String rssLink;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public int getNewspaperId() {
        return newspaperId;
    }

    public void setNewspaperId(int newspaperId) {
        this.newspaperId = newspaperId;
    }
}
