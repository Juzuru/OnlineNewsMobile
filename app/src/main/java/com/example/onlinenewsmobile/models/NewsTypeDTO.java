package com.example.onlinenewsmobile.models;

import java.io.Serializable;

public class NewsTypeDTO implements Serializable {
    private String name;
    private String newspaper;
    private String rssLink;

    public NewsTypeDTO(String name, String newspaper, String rssLink) {
        this.name = name;
        this.newspaper = newspaper;
        this.rssLink = rssLink;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
